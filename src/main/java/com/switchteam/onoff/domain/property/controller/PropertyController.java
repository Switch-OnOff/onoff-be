package com.switchteam.onoff.domain.property.controller;

import com.switchteam.onoff.domain.property.dto.PropertyCardDto;
import com.switchteam.onoff.domain.property.service.PropertyService;
import com.switchteam.onoff.global.common.CustomApiResponse;
import com.switchteam.onoff.global.common.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Tag(name = "매물 데이터 API", description = "매물 데이터 관리를 담당하는 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/property")
public class PropertyController {
    private final PropertyService propertyService;


    @GetMapping("/card_list")
    @Operation
    public ResponseEntity<CustomApiResponse<List<PropertyCardDto>>> getCardList(){
        List<PropertyCardDto> cardList = propertyService.getCardDataList();
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.PROPERTY_CARD_DATA_SUCCESS, cardList));
    }

    @GetMapping("/card_list/{id}")
    public ResponseEntity<CustomApiResponse<PropertyCardDto>> getCardDataById(@PathVariable Long id) {
        PropertyCardDto propertyCardDto = propertyService.findCardDataById(id);
        return ResponseEntity.ok(
                CustomApiResponse.success(SuccessCode.PROPERTY_CARD_DATA_BY_ID_SUCCESS, propertyCardDto)
        );
    }
}
