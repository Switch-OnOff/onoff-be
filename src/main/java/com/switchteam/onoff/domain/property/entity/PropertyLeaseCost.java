package com.switchteam.onoff.domain.property.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "property_lease_costs")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class PropertyLeaseCost {

    @Id
    private Long id;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "deposit")
    private int deposit;

    @Column(name = "mgmt_fee")
    private int mgmtFee;

    @Column(name = "premium")
    private int premium;

    @Column(name = "rent")
    private int rent;

    @Column(name = "sale_price")
    private int salePrice;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "property_id")
    private Property property;
}
