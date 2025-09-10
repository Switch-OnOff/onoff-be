package com.switchteam.onoff.domain.property.service;

import com.switchteam.onoff.domain.property.dto.PropertyCardDto;
import com.switchteam.onoff.domain.property.dto.PropertyCreateRequest;
import com.switchteam.onoff.domain.property.entity.Property;
import com.switchteam.onoff.domain.property.entity.PropertyLeaseCost;
import com.switchteam.onoff.domain.property.entity.PropertyLocation;
import com.switchteam.onoff.domain.property.repository.PropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;
    private final KakaoGeocodingService kakaoGeocodingService;

    @Override
    @Transactional
    public Long createProperty(PropertyCreateRequest request){
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

}
