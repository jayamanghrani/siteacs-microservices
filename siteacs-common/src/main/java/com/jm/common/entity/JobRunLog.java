package com.jm.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_run_log")
@Getter
@Setter
public class JobRunLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobName;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private int inserted;
    private int updated;
    private int failed;
    private String status;   // SUCCESS / FAILED


}
