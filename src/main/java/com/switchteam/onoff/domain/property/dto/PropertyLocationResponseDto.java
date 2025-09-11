package com.switchteam.onoff.domain.property.dto;

import lombok.*;

@AllArgsConstructor
@Getter @Setter
@NoArgsConstructor
public class PropertyLocationResponseDto {
    private Long id;
    private double lat;
    private double lng;
}
