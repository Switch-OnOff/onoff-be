package com.switchteam.onoff.domain.property.service;

import com.switchteam.onoff.domain.property.dto.PropertyCardDto;

import java.util.List;
import java.util.Optional;

public interface PropertyService {

    // 카드 데이터 리스트로 가져오기
    List<PropertyCardDto> getCardDataList();

    // id로 카드 데이터 가져오기
    PropertyCardDto findCardDataById(Long id);
}
