package com.switchteam.onoff.domain.property.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PropertyCreateRequestDto {
    //Property -----------------------
    private Long userId;
    private String storeName;
    private String industry;
    private String shopType;
    private String transferType;
    private Date transferDate;
    private int currentFloor;
    private int totalFloor;
    private String parkingType;
    private int parkingCount;
    private boolean parkingPaid;
    private String restroom;
    private String deliveryLevel;
    private String takeoutLevel;
    private double supplyArea;
    private double exclusiveArea;
    private String description;

    //LeaseCost --------------------
    private String transactionType;
    private int deposit;
    private int mgmtFee;
    private int premium;
    private int rent;
    private int salePrice;

    //Location ---------------------
    private String address;

}
