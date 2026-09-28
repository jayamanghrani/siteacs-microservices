package com.jm.common.repository;

import com.jm.common.entity.OtpLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OtpLogRepository extends JpaRepository<OtpLog, Long> {
    Optional<OtpLog> findTopByEmailOrderByIdDesc(String email);
}