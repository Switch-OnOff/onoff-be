package com.switchteam.onoff.domain.grants.service;

import com.switchteam.onoff.domain.grants.domain.Grants;
import com.switchteam.onoff.domain.grants.dto.request.GrantValidateRequest;
import com.switchteam.onoff.domain.grants.dto.request.GrantsFilterRequest;
import com.switchteam.onoff.domain.grants.dto.response.GrantCheckResponse;
import com.switchteam.onoff.domain.grants.dto.response.GrantValidateResponse;
import com.switchteam.onoff.domain.grants.repository.GrantsRepository;
import com.switchteam.onoff.global.exception.CustomException;
import com.switchteam.onoff.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GrantsService {

    private final GrantsRepository grantsRepository;

    public List<String> getTop5GrantsNames() {
        return grantsRepository.findTop5ByOrderByServiceNameAsc()
                .stream()
                .map(Grants::getServiceName)
                .toList();
    }

   // public List<Grants> filterGrants(GrantsFilterRequest grantsFilterRequest) {
    //    return grantsRepository.searchGrantsByFilters(grantsFilterRequest);
    //}

    public GrantValidateResponse grantsValidate(Long serviceId, GrantValidateRequest grantValidateRequest) {
        Grants grant = grantsRepository.findById(serviceId)
                .orElseThrow(() -> new CustomException(ErrorCode.GRANT_NOT_FOUND));

        GrantValidateResponse grantValidateResponse = new GrantValidateResponse();

        // 현재 상황 검증
        if (grant.getServiceStatus() != null && !grant.getServiceStatus().contains(grantValidateRequest.getServiceStatus())) {
            grantValidateResponse.setValidStatus(false);
            return grantValidateResponse;
        }

        // 지역 검증
        if (grant.getLocation() != null && !grant.getLocation().contains(grantValidateRequest.getLocation())) {
            grantValidateResponse.setValidStatus(false);
            return grantValidateResponse;
        }

        // 업종 검증
        if (grant.getIndustry() != null && !grant.getIndustry().isEmpty()
                && !grant.getIndustry().contains(grantValidateRequest.getIndustry())) {
            grantValidateResponse.setValidStatus(false);
            return grantValidateResponse;
        }

        grantValidateResponse.setValidStatus(true);
        return grantValidateResponse;
    }

    public GrantCheckResponse checkGrantsList(Long serviceId) {
        Grants grant = grantsRepository.findById(serviceId)
                .orElseThrow(() -> new CustomException(ErrorCode.GRANT_NOT_FOUND));

        return GrantCheckResponse.builder()
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







}
