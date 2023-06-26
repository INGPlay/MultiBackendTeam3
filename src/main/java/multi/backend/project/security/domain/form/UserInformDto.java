package multi.backend.project.security.domain.form;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInformDto {
    private String username;
    private String email;
    private String phone;
}
