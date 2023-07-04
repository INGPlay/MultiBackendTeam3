package multi.backend.project.security.domain.certify;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CertifySessionDto {
    private String key;

    private int repeat = 0;
    private boolean isCertified = false;

    public CertifySessionDto(String key) {
        this.key = key;
    }
}
