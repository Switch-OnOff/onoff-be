package com.switchteam.onoff.domain.property.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PropertyLocationResponseDto {
    private Long PropertyId;
    private String roadAddress;
    private double lat;
    private double lng;
}
