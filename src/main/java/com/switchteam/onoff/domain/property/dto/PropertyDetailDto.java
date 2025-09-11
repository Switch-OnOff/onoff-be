package com.switchteam.onoff.domain.property.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@Getter @Setter
@NoArgsConstructor
public class PropertyDetailDto {
    private Long id;
    private String storeName;
    private String industry;
    private String shoType;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String transactionType;
    private Integer deposit;
    private Integer mgmtFee;
    private Integer premium;
    private Integer rent;
    private Integer salePrice;
    private String address;
    private double lat;
    private double lng;
}
