package com.switchteam.onoff.domain.property.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "property_location")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class PropertyLocation {

    @Id
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "lat")
    private double lat;

    @Column(name = "lng")
    private double lng;

    @OneToOne
    @MapsId
    @JoinColumn(name = "property_id")
    private Property property;
}
