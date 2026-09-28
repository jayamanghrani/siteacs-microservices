package com.jm.batch.util;

import com.jm.common.entity.Contractor;

public class ContractorValidationUtil {

    private ContractorValidationUtil() {}

    public static void validate(Contractor c) {
        if (c.getPersonalEmailId() == null || !c.getPersonalEmailId().contains("@")) {
            throw new IllegalArgumentException("Invalid email for contractor id: " + c.getCnum());
        }
        if (c.getManagerEmail() == null || !c.getManagerEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid manager email for id: " + c.getCnum());
        }
    }
}
