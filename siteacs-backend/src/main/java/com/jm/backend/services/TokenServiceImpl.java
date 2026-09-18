package com.jm.backend.services;

import com.jm.common.entity.OnboardingApiClient;
import com.jm.backend.exception.ClientInactiveException;
import com.jm.backend.exception.InvalidCredentialsException;
import com.jm.common.repository.OnboardingApiClientRepo;
import com.jm.backend.serviceInterfaces.TokenService;
import com.jm.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtUtil jwtUtil;
    private final OnboardingApiClientRepo apiClientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String getToken(String clientId,String clientSecret) {

        OnboardingApiClient client = apiClientRepository
                .findByClientId(clientId)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid client ID or secret"));

        if (!client.isActive()) {
            throw new ClientInactiveException("This client is inactive. Contact admin.");
        }

        if (!passwordEncoder.matches(clientSecret, client.getClientSecretHash())) {
            //msg esa likho taaki attacker ko pata na chale
            // "client_id galat tha ya secret galat tha," jo unke liए guessing aasan bana deta.
            throw new InvalidCredentialsException("Invalid  client ID or secret");
        }

        // credentials valid
        return jwtUtil.generateToken(client.getClientId(), client.getScope());

    }

}
