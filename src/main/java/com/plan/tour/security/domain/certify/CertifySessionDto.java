package com.plan.tour.security.domain.certify;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CertifySessionDto {
    private String key;

    private int repeat = 0;
    private CertifyStatus certifyStatus = CertifyStatus.PROCESSING;

    public void plusRepeat(){
        this.repeat += 1;
        if (this.repeat >= 3){
            certifyStatus = CertifyStatus.BANNED;
        }
    }

    public CertifySessionDto(String key) {
        this.key = key;
    }
}
