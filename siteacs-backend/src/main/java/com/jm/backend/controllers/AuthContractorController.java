package com.jm.backend.controllers;

import com.jm.backend.dto.APIResponseDTO;
import com.jm.backend.dto.OtpRequestDto;
import com.jm.backend.dto.OtpVerifyDto;
import com.jm.backend.exception.InvalidCredentialsException;
import com.jm.backend.services.OtpService;
import com.jm.backend.util.JwtUtil;
import com.jm.common.repository.ContractorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthContractorController {

    Logger LOG = LoggerFactory.getLogger(AuthContractorController.class);

    @Autowired
    private OtpService otpService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ContractorRepository contractorRepository;

    @PostMapping("/otp/request")
    public ResponseEntity<APIResponseDTO> requestOtp(@RequestBody OtpRequestDto dto) {
        // Confirm karo ye email, contractor-table mein exist karta hai
        contractorRepository.findBypersonalEmailId(dto.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Email not registered"));

        otpService.generateAndSendOtp(dto.getEmail());
        return ResponseEntity.ok(new APIResponseDTO(true, "OTP sent to your email"));
    }

    @PostMapping("/otp/verify")
    public ResponseEntity<APIResponseDTO> verifyOtp(@RequestBody OtpVerifyDto dto) {
        LOG.info("Verifying OTP for email: {}", dto.getEmail());
        otpService.verifyOtp(dto.getEmail(), dto.getOtp());

        String token = jwtUtil.generateToken(dto.getEmail(), "CONTRACTOR");
        return ResponseEntity.ok(new APIResponseDTO(true, token));
    }
}