package com.switchteam.onoff.domain.property.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PropertyCardDto {
    private Long id;
    private String industry;
    private String transactionType;
    private Integer rent;
    private Integer deposit;
    private Integer salePrice;
    private int premium;
    private String address;
    private double exclusiveArea;
}
