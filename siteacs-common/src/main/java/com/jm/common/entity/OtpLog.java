package com.jm.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "otp_log")
public class OtpLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String otpHash;    // OTP ko bhi hash karke store karenge, plaintext nahi
    private LocalDateTime expiresAt;
    private boolean verified = false;
    private int attemptCount = 0;

    // Getters and Setters
}