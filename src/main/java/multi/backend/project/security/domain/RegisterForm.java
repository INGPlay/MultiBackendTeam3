package multi.backend.project.security.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class RegisterForm {

    @NotNull
    @Size(min = 4, max = 10)
    @Pattern(regexp = "^([a-z0-9]*)$")
    private String username;

    @NotNull
    @Size(min = 4, max = 20)
    @Pattern(regexp = "^([A-Za-z0-9!@#$%]*)$")
    private String password;

    @NotNull
    @Size(min = 4, max = 20)
    @Pattern(regexp = "^([A-Za-z0-9!@#$%]*)$")
    private String passwordCheck;

    @NotNull
//    @Size(min = 4, max = 20)
    @Email()
    private String email;

    @NotNull
    @Size(min = 10, max = 12)
    @Pattern(regexp = "^([0-9]*)$")
    private String phone;
}
