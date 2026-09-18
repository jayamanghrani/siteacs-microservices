package com.jm.common.entity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="onboarding_api_client")
@Getter
@Setter
public class OnboardingApiClient {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String clientId;

    @Column(nullable = false)
    private String clientSecretHash;

    private String scope;
    private boolean active = true;
    private LocalDateTime createdAt = LocalDateTime.now();
}
