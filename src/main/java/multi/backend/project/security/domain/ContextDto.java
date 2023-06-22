package multi.backend.project.security.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContextDto {
    private final String username;
    private final String password;
}
