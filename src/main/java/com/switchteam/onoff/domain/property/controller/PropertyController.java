package com.switchteam.onoff.domain.property.controller;

import com.switchteam.onoff.domain.property.dto.PropertyCardDto;
import com.switchteam.onoff.domain.property.dto.PropertyCreateRequest;
import com.switchteam.onoff.domain.property.dto.ValidateRequest;
import com.switchteam.onoff.domain.property.service.PropertyService;
import com.switchteam.onoff.global.common.CustomApiResponse;
import com.switchteam.onoff.global.common.SuccessCode;
import com.switchteam.onoff.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Tag(name = "매물 데이터 API", description = "매물 데이터 관리를 담당하는 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/property")
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping("/")
    public ResponseEntity<CustomApiResponse<PropertyCreateRequest>> insertProperty(@RequestBody PropertyCreateRequest request){
        Long id = propertyService.createProperty(request);
        if(id == null){
            return ResponseEntity.ok(CustomApiResponse.error(ErrorCode.PROPERTY_CREATE_ERROR));
        }
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.PROPERTY_INSERT_SUCCESS));
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

    @PostMapping("/validate")
    @Operation(summary = "진위 여부 확인", description = "사용자의 데이터를 통해 진위 여부를 확인합니다.")
    public ResponseEntity<CustomApiResponse<Boolean>> isValid(@RequestBody ValidateRequest request){
        boolean valid = propertyService.isValid(request);
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.VALIDATE_SUCCESS, valid));
    }
}
