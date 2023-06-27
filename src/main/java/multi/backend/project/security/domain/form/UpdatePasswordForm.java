package multi.backend.project.security.domain.form;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class UpdatePasswordForm {

    @NotNull
    @Size(min = 4, max = 20)
    @Pattern(regexp = "^([A-Za-z0-9!@#$%]*)$")
    private String currentPassword;

    @NotNull
    @Size(min = 4, max = 20)
    @Pattern(regexp = "^([A-Za-z0-9!@#$%]*)$")
    private String newPassword;

//    @NotNull
//    @Size(min = 4, max = 20)
//    @Pattern(regexp = "^([A-Za-z0-9!@#$%]*)$")
    private String newPasswordCheck;
}
