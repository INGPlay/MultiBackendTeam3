package multi.backend.project.security.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponse {
    private String id;
    private String username;
    private String email;
    private String phone;
    private String role;
}
