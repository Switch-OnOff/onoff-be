package com.switchteam.onoff.domain.grants.service;

import com.switchteam.onoff.domain.grants.domain.Grants;
import com.switchteam.onoff.domain.grants.dto.request.GrantValidateRequestDto;
import com.switchteam.onoff.domain.grants.dto.response.GrantCheckResponseDto;
import com.switchteam.onoff.domain.grants.dto.response.GrantValidateResponseDto;
import com.switchteam.onoff.domain.grants.repository.GrantsRepository;
import com.switchteam.onoff.global.exception.CustomException;
import com.switchteam.onoff.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GrantsService {

    private final GrantsRepository grantsRepository;

    public List<Map<String, Object>> getTop5GrantsNames() {
        return grantsRepository.findTop5ByOrderByServiceNameAsc()
                .stream()
                .map(g -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("serviceId", g.getServiceId());
                    m.put("serviceName", g.getServiceName());
                    return m;
                })
                .toList();
    }

    // public List<Grants> filterGrants(GrantsFilterRequest grantsFilterRequest) {
    //    return grantsRepository.searchGrantsByFilters(grantsFilterRequest);
    //}

    public GrantValidateResponseDto grantsValidate(Long serviceId, GrantValidateRequestDto grantValidateRequestDto) {
        Grants grant = grantsRepository.findById(serviceId)
                .orElseThrow(() -> new CustomException(ErrorCode.GRANT_NOT_FOUND));

        GrantValidateResponseDto grantValidateResponse = new GrantValidateResponseDto();

        // 현재 상황 검증
        if (grant.getServiceStatus() != null && !grant.getServiceStatus().contains(grantValidateRequestDto.getServiceStatus())) {
            grantValidateResponse.setValidStatus(false);
            return grantValidateResponse;
        }

        // 지역 검증
        if (grant.getLocation() != null && !grant.getLocation().contains(grantValidateRequestDto.getLocation())) {
            grantValidateResponse.setValidStatus(false);
            return grantValidateResponse;
        }

        // 업종 검증
        if (grant.getIndustry() != null && !grant.getIndustry().isEmpty()
                && !grant.getIndustry().contains(grantValidateRequestDto.getIndustry())) {
            grantValidateResponse.setValidStatus(false);
            return grantValidateResponse;
        }

        grantValidateResponse.setValidStatus(true);
        return grantValidateResponse;
    }

    public GrantCheckResponseDto checkGrantsList(Long serviceId) {
        Grants grant = grantsRepository.findById(serviceId)
                .orElseThrow(() -> new CustomException(ErrorCode.GRANT_NOT_FOUND));

        return GrantCheckResponseDto.builder()
                .selectionCriteria(grant.getSelectionCriteria())
                .requiredDocuments(grant.getRequiredDocuments())
                .build();
    }

    // 키워드 검색
    public List<Grants> searchByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.of(); // 빈 리스트 반환
        }
        return grantsRepository.findByServiceNameContaining(keyword);
    }

    // 필터 검색
    public List<Grants> filterGrants(String serviceStatus, String location, String industry) {
        return grantsRepository.searchGrantsByFilters(serviceStatus, location, industry);
    }

    public Grants getGrantById(Long serviceId) {
        return grantsRepository.findById(serviceId)
                .orElseThrow(() -> new CustomException(ErrorCode.GRANT_NOT_FOUND));
    }


}
