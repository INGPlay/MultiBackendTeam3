package multi.backend.project.security.domain.certify;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CertifyEmailDto {
    private String uuid;
    private String key;
}
