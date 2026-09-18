package com.jm.common.repository;

import com.jm.common.entity.OnboardingApiClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OnboardingApiClientRepo extends JpaRepository<OnboardingApiClient, Long> {
     public Optional<OnboardingApiClient> findByClientId(String clientId);

}
