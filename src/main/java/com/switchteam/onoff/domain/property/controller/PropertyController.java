package com.switchteam.onoff.domain.property.controller;

import com.switchteam.onoff.domain.property.dto.*;
import com.switchteam.onoff.domain.property.service.PropertyService;
import com.switchteam.onoff.global.common.CustomApiResponse;
import com.switchteam.onoff.global.common.SuccessCode;
import com.switchteam.onoff.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "매물 데이터 API", description = "매물 데이터 관리를 담당하는 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/property")
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping("/")
    @Operation(summary = "매물 정보 등록", description = "매물 정보를 등록합니다.")
    public ResponseEntity<CustomApiResponse<Long>> insertProperty(@RequestBody PropertyCreateRequestDto request){
        Long id = propertyService.createProperty(request);
        if(id == null){
            return ResponseEntity.ok(CustomApiResponse.error(ErrorCode.PROPERTY_CREATE_ERROR));
        }
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.PROPERTY_INSERT_SUCCESS, id));
    }


    @GetMapping("/card_list")
    @Operation(summary = "카드 리스트 조회", description = "전체 카드 리스트를 조회합니다.")
    public ResponseEntity<CustomApiResponse<List<PropertyCardDto>>> getCardList(){
        List<PropertyCardDto> cardList = propertyService.getCardDataList();
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.PROPERTY_CARD_DATA_SUCCESS, cardList));
    }

    @GetMapping("/card_list/{id}")
    @Operation(summary = "단일 카드 데이터 조회", description = "해당 id의 카드 데이터를 불러옵니다.")
    public ResponseEntity<CustomApiResponse<PropertyCardDto>> getCardDataById(@PathVariable Long id) {
        PropertyCardDto propertyCardDto = propertyService.findCardDataById(id);
        return ResponseEntity.ok(
                CustomApiResponse.success(SuccessCode.PROPERTY_CARD_DATA_BY_ID_SUCCESS, propertyCardDto)
        );
    }

    @GetMapping("/location")
    @Operation(summary = "매물 위치 데이터 리스트 조회", description = "전체 매물의 위치를 조회합니다.")
    public ResponseEntity<CustomApiResponse<List<PropertyLocationResponseDto>>> getPropertyLocation(){
        List<PropertyLocationResponseDto> propertyLocationResponseDtoList = propertyService.getPropertyLocationDataList();
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.PROPERTY_LOCATION_SUCCESS, propertyLocationResponseDtoList));
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "매물 상세정보 조회", description = "해당 id의 매물 상세정보를 조회합니다.")
    public ResponseEntity<CustomApiResponse<PropertyDetailDto>> getPropertyDetail(@PathVariable Long id) {
        PropertyDetailDto dto = propertyService.getPropertyDetailById(id);
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.PROPERTY_DETAIL_SUCCESS, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "매물 데이터 삭제", description = "해당 id의 매물 데이터를 삭제합니다.")
    public ResponseEntity<CustomApiResponse<Void>> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.ok(CustomApiResponse.success((SuccessCode.DELETE_PROPERTY_SUCCESS)));
    }

    @PostMapping("/validate")
    @Operation(summary = "진위 여부 확인", description = "사용자의 데이터를 통해 진위 여부를 확인합니다.")
    public ResponseEntity<CustomApiResponse<Boolean>> isValid(@RequestBody ValidateRequestDto request){
        boolean valid = propertyService.isValid(request);
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.VALIDATE_SUCCESS, valid));
    }
}
