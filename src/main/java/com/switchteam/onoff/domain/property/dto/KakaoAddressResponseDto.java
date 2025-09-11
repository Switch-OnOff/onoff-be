package com.switchteam.onoff.domain.property.dto;

import java.util.List;

public record KakaoAddressResponseDto(List<Document> documents) {

    public record Document(String x, String y) {}
}