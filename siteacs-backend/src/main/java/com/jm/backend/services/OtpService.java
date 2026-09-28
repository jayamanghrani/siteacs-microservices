package com.jm.backend.services;

import com.jm.backend.exception.InvalidTokenException;
import com.jm.common.entity.OtpLog;
import com.jm.common.repository.OtpLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {

    private static final Logger logger = LoggerFactory.getLogger(OtpService.class);
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private OtpLogRepository otpLogRepository;

    @Autowired
    private EmailService emailService;

    public void generateAndSendOtp(String email) {
        String otp = String.valueOf(100000 + new Random().nextInt(900000));  // 6-digit OTP

        OtpLog otpLog = new OtpLog();
        otpLog.setEmail(email);
        otpLog.setOtpHash(encoder.encode(otp));
        otpLog.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        otpLogRepository.save(otpLog);

        emailService.sendOtpEmail(email, otp);
        logger.info("OTP generated and sent to: {}", email);
    }

    public boolean verifyOtp(String email, String enteredOtp) {
        logger.info("Verifying OTP for email: {}", email);
        OtpLog otpLog = otpLogRepository.findTopByEmailOrderByIdDesc(email)
                .orElseThrow(() -> new InvalidTokenException("No OTP found for this email"));

        if (otpLog.isVerified()) {
            throw new InvalidTokenException("OTP already used");
        }
        if (otpLog.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("OTP expired, please request a new one");
        }
        if (otpLog.getAttemptCount() >= 3) {
            throw new InvalidTokenException("Too many attempts, please request a new OTP");
        }
        logger.info("verified otp log: {}", otpLog);

        otpLog.setAttemptCount(otpLog.getAttemptCount() + 1);

        if (!encoder.matches(enteredOtp, otpLog.getOtpHash())) {
            otpLogRepository.save(otpLog);
            throw new InvalidTokenException("Invalid OTP");
        }

        otpLog.setVerified(true);
        otpLogRepository.save(otpLog);
        return true;
    }
}