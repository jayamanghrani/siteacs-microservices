package com.jm.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpVerifyDto {
    private String email;
    private String otp;

    // YE ZAROORI HAI - No-argument constructor
/*    Jackson, jab JSON se object banaне ki kosis karta hai,
            "empty-object-banao-phir-setters-chalao" wale-tarikе se karta hai —
    isके liए, NO-ARG-CONSTRUCTOR ZAROORI HAI.*/
    public OtpVerifyDto() {
    }

    public OtpVerifyDto(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }

}
