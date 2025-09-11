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
    private Integer  deposit;

    @Column(name = "mgmt_fee")
    private Integer  mgmtFee;

    @Column(name = "premium")
    private Integer  premium;

    @Column(name = "rent")
    private Integer  rent;

    @Column(name = "sale_price")
    private Integer  salePrice;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "property_id")
    private Property property;
}
