package com.switchteam.onoff.domain.grants.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grants")
@Getter
@NoArgsConstructor
public class Grants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "service_name", nullable = false, length = 255)
    private String serviceName;

    @Column(length = 255)
    @Nullable
    private String industry;

    @Column(length = 255)
    private String location;

    @Column(name = "service_status", length = 255)
    private String serviceStatus;

    @Column(name = "selection_criteria", columnDefinition = "TEXT")
    @Nullable
    private String selectionCriteria;

    @Column(name = "required_documents", columnDefinition = "TEXT")
    @Nullable
    private String requiredDocuments;

    @Column(name="service_type", length = 255)
    private String serviceType;

    @Nullable
    @Column(name = "service_contents", columnDefinition = "TEXT")
    private String serviceContents;

    @Column(name = "service_link", length = 255)
    private String serviceLink;



}
