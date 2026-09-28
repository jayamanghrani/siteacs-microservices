package com.jm.common.repository;

import com.jm.common.entity.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ContractorRepository extends JpaRepository<Contractor, String> {

    List<Contractor> findByEndDateBeforeAndStatus(LocalDate current, String status);

    Optional<Contractor> findBypersonalEmailId(String personalEmailId);


/*Spring Data JPA, method-KE-NAAM ko "parse" karके, khud SQL-query generate kar leता hai —
Method-naam ke parts, Entity ke field-names se EXACTLY match karne chahिए:

  Spring isको isа "todता/parse" karта hai:

    findBy               → "find" karna hai
    ExpiryDateBefore     → WHERE expiry_date < ?
    And                  → AND
    Status               → status = ?

    Automatically generate hoती query:

    sql
    SELECT * FROM contractor WHERE expiry_date < ? AND status = ?*/

    //Agar naam mismatch ho (jaise Entity mein field expiry_date naम se defined ho, code mein),
    // app-startup pe hi error aएगа — "no property found" jaisा.
    // Ye ek achhа "fail-fast" behavior hai — galат query silently nahi chalеgी,
    // startup pe hi pakड़ी jाएगи
}
