package com.switchteam.onoff.domain.property.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PropertyLeaseCostsRequestDto {
    private String transactionType;
    private int deposit;
    private int sale_price;
    private int monthlyRent;
    private int maintenanceFee;
    private Long premium;
}
