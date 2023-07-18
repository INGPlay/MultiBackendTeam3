package com.plan.tour.security.domain.certify;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CertifyEmailDto {
    private String uuid;
    private String key;
}
