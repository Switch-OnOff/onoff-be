package com.switchteam.onoff.domain.grants.controller;

import com.switchteam.onoff.domain.grants.domain.Grants;
import com.switchteam.onoff.domain.grants.dto.request.GrantValidateRequest;
import com.switchteam.onoff.domain.grants.dto.response.GrantCheckResponse;
import com.switchteam.onoff.domain.grants.dto.response.GrantValidateResponse;
import com.switchteam.onoff.domain.grants.service.GrantsService;
import com.switchteam.onoff.global.common.CustomApiResponse;
import com.switchteam.onoff.global.common.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class GrantsController {

    private final GrantsService grantsService;

    @GetMapping("/api/grants/top5")
    public ResponseEntity<CustomApiResponse<List<Map<String, Object>>>> getTop5Grants() {
        List<Map<String, Object>> grantNames = grantsService.getTop5GrantsNames();
        return ResponseEntity.ok(CustomApiResponse.success(
                SuccessCode.GRANT_TOP5_FETCH_SUCCESS, grantNames));
    }



    //필터 여러개 선택하는 경우
    //@PostMapping("/api/grants/filter")
    //public ResponseEntity<CustomApiResponse<List<Grants>>> GrantsFilter(@RequestBody GrantsFilterRequest grantsFilterRequest) {
    //    List<Grants> filteredGrants = grantsService.filterGrants(grantsFilterRequest);
    //    return ResponseEntity.ok(CustomApiResponse.success(
    //            SuccessCode.GRANT_FILTER_SUCCESS, filteredGrants));
    //}


    @PostMapping("/api/grants/{serviceId}/validate")
    public ResponseEntity<CustomApiResponse<GrantValidateResponse>> grantValidate(
            @PathVariable Long serviceId,
            @RequestBody GrantValidateRequest grantValidateRequest) {

        GrantValidateResponse grantValidateResponse = grantsService.grantsValidate(serviceId, grantValidateRequest);

        return ResponseEntity.ok(CustomApiResponse.success(
                SuccessCode.GRANT_VALIDATE_SUCCESS, grantValidateResponse));
    }


    @GetMapping("/api/grants/{serviceId}/checkList")
    public ResponseEntity<CustomApiResponse<GrantCheckResponse>> checkGrantsList(@PathVariable Long serviceId) {
        GrantCheckResponse grantsList = grantsService.checkGrantsList(serviceId);
        return ResponseEntity.ok(CustomApiResponse.success(
                SuccessCode.GRANT_CHECK_SUCCESS, grantsList));

    }

    @GetMapping("/api/grants/search")
    public ResponseEntity<CustomApiResponse<List<Grants>>> searchGrants(
            @RequestParam String keyword) {
        List<Grants> searchedGrants = grantsService.searchByKeyword(keyword);
        return ResponseEntity.ok(
                CustomApiResponse.success(SuccessCode.GRANT_SEARCH_SUCCESS, searchedGrants)
        );
    }

    @GetMapping("/api/grants/filter")
    public ResponseEntity<CustomApiResponse<List<Grants>>> filterGrants(
            @RequestParam(required = false) String serviceStatus,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String industry) {

        List<Grants> filteredGrants = grantsService.filterGrants(serviceStatus, location, industry);
        return ResponseEntity.ok(
                CustomApiResponse.success(SuccessCode.GRANT_FILTER_SUCCESS, filteredGrants)
        );
    }

}
