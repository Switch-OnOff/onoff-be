package com.switchteam.onoff.domain.grants.domain;

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
    private String industry;

    @Column(length = 255)
    private String location;

    @Column(name = "service_status", length = 255)
    private String serviceStatus;

    @Lob
    @Column(name = "selection_criteria", columnDefinition = "TEXT")
    private String selectionCriteria;

    @Lob
    @Column(name = "required_documents", columnDefinition = "TEXT")
    private String requiredDocuments;

    @Column(name = "service_link", length = 1024)
    private String serviceLink;



}
