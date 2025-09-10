package com.switchteam.onoff.domain.grants.controller;

import com.switchteam.onoff.domain.grants.domain.Grants;
import com.switchteam.onoff.domain.grants.dto.request.GrantValidateRequest;
import com.switchteam.onoff.domain.grants.dto.response.GrantCheckResponse;
import com.switchteam.onoff.domain.grants.dto.response.GrantValidateResponse;
import com.switchteam.onoff.domain.grants.service.GrantsService;
import com.switchteam.onoff.global.common.CustomApiResponse;
import com.switchteam.onoff.global.common.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "지원금 관련 API", description = "지원금 관련 API입니다")
@RestController
@RequiredArgsConstructor
public class GrantsController {

    private final GrantsService grantsService;

    @GetMapping("/api/grants/top5")
    @Operation(summary = "인기 지원금", description = "TOP5 지원금 보여줄때 사용하는 API")
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
    @Operation(summary = "지원여부 결정", description = "내 상황 입력하고 지원받을 수 있는지 확인할때 사용하는 API")
    public ResponseEntity<CustomApiResponse<GrantValidateResponse>> grantValidate(
            @PathVariable Long serviceId,
            @RequestBody GrantValidateRequest grantValidateRequest) {

        GrantValidateResponse grantValidateResponse = grantsService.grantsValidate(serviceId, grantValidateRequest);

        return ResponseEntity.ok(CustomApiResponse.success(
                SuccessCode.GRANT_VALIDATE_SUCCESS, grantValidateResponse));
    }


    @GetMapping("/api/grants/{serviceId}/checkList")
    @Operation(summary = "선정기준과 구비서류", description = "지원을 받을 있다면 선정기준과 구비여부 리스트 보여주는 API")
    public ResponseEntity<CustomApiResponse<GrantCheckResponse>> checkGrantsList(@PathVariable Long serviceId) {
        GrantCheckResponse grantsList = grantsService.checkGrantsList(serviceId);
        return ResponseEntity.ok(CustomApiResponse.success(
                SuccessCode.GRANT_CHECK_SUCCESS, grantsList));

    }

    @GetMapping("/api/grants/search")
    @Operation(summary = "지원금 검색", description = "지원금 검색시 사용할 수 있는 API")
    public ResponseEntity<CustomApiResponse<List<Grants>>> searchGrants(
            @RequestParam String keyword) {
        List<Grants> searchedGrants = grantsService.searchByKeyword(keyword);
        return ResponseEntity.ok(
                CustomApiResponse.success(SuccessCode.GRANT_SEARCH_SUCCESS, searchedGrants)
        );
    }

    @GetMapping("/api/grants/filter")
    @Operation(summary = "지원금 필터", description = "항목별로 기준 1개씩 선택 후 활용할 수 있는 API")
    public ResponseEntity<CustomApiResponse<List<Grants>>> filterGrants(
            @RequestParam(required = false) String serviceStatus,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String industry) {

        List<Grants> filteredGrants = grantsService.filterGrants(serviceStatus, location, industry);
        return ResponseEntity.ok(
                CustomApiResponse.success(SuccessCode.GRANT_FILTER_SUCCESS, filteredGrants)
        );
    }

    @GetMapping("/api/grants/{serviceId}")
    @Operation(summary = "지원금 상세 조회", description = "serviceId로 지원금의 모든 정보를 조회하는 API")
    public ResponseEntity<CustomApiResponse<Grants>> getGrantById(@PathVariable Long serviceId) {
        Grants grant = grantsService.getGrantById(serviceId);
        return ResponseEntity.ok(
                CustomApiResponse.success(SuccessCode.GRANT_DETAIL_SUCCESS, grant)
        );
    }


}
