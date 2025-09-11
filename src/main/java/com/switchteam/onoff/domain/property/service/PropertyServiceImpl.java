package com.switchteam.onoff.domain.property.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.switchteam.onoff.domain.property.dto.PropertyCardDto;
import com.switchteam.onoff.domain.property.dto.PropertyCreateRequestDto;
import com.switchteam.onoff.domain.property.dto.PropertyLocationResponseDto;
import com.switchteam.onoff.domain.property.dto.ValidateRequestDto;
import com.switchteam.onoff.domain.property.entity.Property;
import com.switchteam.onoff.domain.property.entity.PropertyLeaseCost;
import com.switchteam.onoff.domain.property.entity.PropertyLocation;
import com.switchteam.onoff.domain.property.repository.PropertyRepository;
import com.switchteam.onoff.global.exception.CustomException;
import com.switchteam.onoff.global.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;
    private final KakaoGeocodingService kakaoGeocodingService;
    private final ObjectMapper objectMapper;
    private final WebClient odcloud;

    @Value("${odcloud.service-key}")
    private String serviceKey;

    @Override
    @Transactional
    public Long createProperty(PropertyCreateRequestDto request){
        Property property = Property.builder()
                .storeName(request.getStoreName())
                .industry(request.getIndustry())
                .shopType(request.getShopType())
                .transferType(request.getTransferType())
                .transferDate(request.getTransferDate())
                .currentFloor(request.getCurrentFloor())
                .totalFloor(request.getTotalFloor())
                .parkingType(request.getParkingType())
                .parkingCount(request.getParkingCount())
                .parkingPaid(request.isParkingPaid())
                .restroom(request.getRestroom())
                .deliveryLevel(request.getDeliveryLevel())
                .takeoutLevel(request.getTakeoutLevel())
                .supplyArea(request.getSupplyArea())
                .exclusiveArea(request.getExclusiveArea())
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .build();

        PropertyLeaseCost leaseCost = PropertyLeaseCost.builder()
                .transactionType(request.getTransactionType())
                .deposit(request.getDeposit())
                .mgmtFee(request.getMgmtFee())
                .premium(request.getPremium())
                .rent(request.getRent())
                .salePrice(request.getSalePrice())
                .build();

        var latLng = kakaoGeocodingService.geocode(request.getAddress()) // 인스턴스 메서드 호출
                .orElseThrow(() -> new IllegalArgumentException("좌표 조회 실패"));

        PropertyLocation location = PropertyLocation.builder()
                .address(request.getAddress())
                .lat(latLng.lat())
                .lng(latLng.lng())
                .build();

        leaseCost.setProperty(property);
        location.setProperty(property);
        property.setLeaseCost(leaseCost);
        property.setLocation(location);

        Property saved = propertyRepository.save(property);

        return saved.getId();
    }

    @Override
    public List<PropertyCardDto> getCardDataList(){
        return propertyRepository.findAllCards();
    }

    @Override
    public PropertyCardDto findCardDataById(Long id) {
        return propertyRepository.findCardById(id)
                .orElseThrow(() -> new EntityNotFoundException("Property not found with id=" + id));
    }

    @Override
    public List<PropertyLocationResponseDto> getPropertyLocationDataList(){
        return propertyRepository.findAllLocations();
    }


    @Override
    public void deleteProperty(Long id){
        propertyRepository.deleteById(id);
    }

    @Override
    public boolean isValid(ValidateRequestDto request) {
        // 0) 입력 정규화
        String bNo = nz(request.getBNo()).replaceAll("\\D", "");
        String startDt = nz(request.getStartDt()).replaceAll("\\D", "");
        String pNm = nz(request.getPNm()).trim();

        if (bNo.length() != 10) {
            throw new CustomException(ErrorCode.BNO_NOT_CORRECT);
        }
        if (startDt.length() != 8) {
            throw new CustomException(ErrorCode.START_DATE_NOT_CORRECT);
        }
        if (pNm.isEmpty()) {
            throw new CustomException(ErrorCode.PNM_IS_EMPTY);
        }

        // 1) body 구성
        Map<String, Object> business = new LinkedHashMap<>();
        business.put("b_no", bNo);
        business.put("start_dt", startDt);
        business.put("p_nm", pNm);
        business.put("p_nm2", "");
        business.put("b_nm", "");
        business.put("corp_no", "");
        business.put("b_sector", "");
        business.put("b_type", "");

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("businesses", Collections.singletonList(business));

        // 2) serviceKey 안전 처리 (trim)
        String keyTrim = nz(serviceKey).trim();

        // 로그 출력
        try {
            log.info("[ODCLOUD][REQ-BODY] {}", objectMapper.writeValueAsString(body));
        } catch (Exception ignore) {}
        log.info("[ODCLOUD][KEY raw] {}", keyTrim);

        // 3) API 호출
        String response;
        try {
            response = odcloud.post()
                    .uri(u -> u
                            .path("/api/nts-businessman/v1/validate")
                            // build(serviceKey) 형태로 전달해야 이중 인코딩 방지됨
                            .queryParam("serviceKey", "{key}")
                            .queryParam("returnType", "JSON")
                            .build(keyTrim)
                    )
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(body)
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            clientResponse -> clientResponse.bodyToMono(String.class).flatMap(msg -> {
                                log.error("[ODCLOUD][ERR] status={}, body={}", clientResponse.statusCode(), msg);
                                return Mono.error(new RuntimeException("ODCloud error: " + msg));
                            })
                    )
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.ODCLOUD_CALL_API_ERROR);
        }

        // 4) 응답 파싱
        try {
            JsonNode root = objectMapper.readTree(response);
            String statusCode = root.path("status_code").asText(null);
            if (!"OK".equalsIgnoreCase(statusCode)) {
                throw new CustomException(ErrorCode.ODCLOUD_NON_OK_ERROR);
            }

            JsonNode data = root.path("data");
            if (!data.isArray() || data.isEmpty()) {
                throw new CustomException(ErrorCode.ODCLOUD_DATA_MISSING_ERROR);
            }

            String valid = data.get(0).path("valid").asText(null); // "01"|"02"
            log.info("[ODCLOUD][RES] valid={}", valid);

            if (valid == null) {
                throw new CustomException(ErrorCode.ODCLOUD_VALID_MISSING_ERROR);
            }

            return "01".equals(valid);
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception e) {
            throw new CustomException(ErrorCode.ODCLOUD_PARSING_ERROR);
        }
    }

    private String nz(String v) { return v == null ? "" : v; }

}
