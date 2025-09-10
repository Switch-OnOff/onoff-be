package com.switchteam.onoff.domain.property.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PropertyRequest {
    private PropertyResponseDto list;
    private PropertyLeaseCostsResponseDto leaseCosts;
    private PropertyLocationResponseDto location;
}
