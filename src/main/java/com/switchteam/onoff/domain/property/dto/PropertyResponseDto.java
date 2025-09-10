package com.switchteam.onoff.domain.property.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PropertyResponseDto {
    private Long id;
    private String title;
    private String businessType;
    private String propertyType;
    private Date transferableDate;
    private int currentFloor;
    private int totalFloor;
    @Enumerated(EnumType.STRING)
    private String parking;
    @Enumerated(EnumType.STRING)
    private String toilet;
    private boolean delivery;
    private boolean takeout;
    private double supplyArea;
    private double exclusiveArea;
    private String description;
    private Date created_at;
    private Date updated_at;
}
