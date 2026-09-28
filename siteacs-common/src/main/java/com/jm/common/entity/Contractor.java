package com.jm.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "contractor")
@Transactional
@Data
public class Contractor {

    @Id
    @Column(name ="CNUM")
    private String cnum;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PERSONAL_EMAIL_ID")
    private String personalEmailId;

    @Column(name = "MANAGER_EMAIL_ID")
    private String managerEmail;

    @Column(name = "DATE_OF_JOINING")
    private LocalDate dateOfJoining;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "EMP_TYPE")
    private String empType;

    @Column(name = "CITY")
    private String city;

    @Column(name = "CAMPUS")
    private String campus;

    @Column(name = "BUILDING")
    private String building;

    @Column(name = "FLOOR")
    private String floor;

    @Column(name = "BUSINESS_UNIT")
    private String businessUnit;

    @Column(name = "AGENCY_NAME")
    private String agencyName;

    @Column(name = "STATUS")
    private String status;
}
