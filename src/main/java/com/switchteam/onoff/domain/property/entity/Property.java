package com.switchteam.onoff.domain.property.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "property")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "industry")
    private String industry;

    @Column(name = "shop_type")
    private String shopType;

    @Column(name = "transfer_type")
    private String transferType;

    @Column(name = "transfer_date")
    private Date transferDate;

    @Column(name = "current_floor")
    private int currentFloor;

    @Column(name = "total_floor")
    private int totalFloor;

    @Column(name = "parking_type")
    private String parkingType;

    @Column(name = "parking_count")
    private int parkingCount;

    @Column(name = "parking_paid")
    private boolean parkingPaid;

    @Column(name = "restroom")
    private String restroom;

    @Column(name = "delivery_level")
    private String deliveryLevel;

    @Column(name = "takeout_level")
    private String takeoutLevel;

    @Column(name = "supply_area")
    private double supplyArea;

    @Column(name = "exclusive_area")
    private double exclusiveArea;

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, optional = false)
    private PropertyLeaseCost leaseCost;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, optional = false)
    private PropertyLocation location;
}
