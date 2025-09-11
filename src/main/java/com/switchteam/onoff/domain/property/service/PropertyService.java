package com.switchteam.onoff.domain.property.service;

import com.switchteam.onoff.domain.property.dto.PropertyCardDto;
import com.switchteam.onoff.domain.property.dto.PropertyCreateRequestDto;
import com.switchteam.onoff.domain.property.dto.PropertyLocationResponseDto;
import com.switchteam.onoff.domain.property.dto.ValidateRequestDto;

import java.util.List;

public interface PropertyService {

    // 매물 상세 데이터 insert
    Long createProperty(PropertyCreateRequestDto propertyCreateRequestDto);

    // 카드 데이터 리스트로 가져오기
    List<PropertyCardDto> getCardDataList();

    // id로 카드 데이터 가져오기
    PropertyCardDto findCardDataById(Long id);

    List<PropertyLocationResponseDto> getPropertyLocationDataList();

    boolean isValid(ValidateRequestDto request);

    void deleteProperty(Long id);
}
