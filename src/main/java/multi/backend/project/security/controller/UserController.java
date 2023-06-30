package multi.backend.project.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.security.domain.RegisterDto;
import multi.backend.project.security.domain.RegisterForm;
import multi.backend.project.security.domain.UserDto;
import multi.backend.project.security.domain.context.UserContext;
import multi.backend.project.security.domain.form.UpdatePasswordForm;
import multi.backend.project.security.domain.form.UserInformDto;
import multi.backend.project.security.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginForm(){
        return "user/loginView";
    }

    @GetMapping("/register")
    public String registerForm(Model model){

        model.addAttribute("registerForm", new RegisterForm("", "", "", "", ""));

//        return "user/registerForm";
        return "user/registerView";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerForm") RegisterForm registerForm,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

//        log.info("{}", bindingResult);

        // Validation
        if (!registerForm.getPassword().equals(registerForm.getPasswordCheck())){
            bindingResult.reject("NotMatch.passwordCheck", null, "비밀번호와 일치하지 않습니다.");
        }

        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername(registerForm.getUsername());
        registerDto.setPassword(registerForm.getPassword());
        registerDto.setEmail(registerForm.getEmail());
        registerDto.setPhone(registerForm.getPhone().replaceAll("\\s", ""));       // 공백 제거

        // 유니크 체크
        checkUniqueInformation(bindingResult, registerDto);

        if (bindingResult.hasErrors()){
            log.info("{}", bindingResult);

            return "user/registerView";
        }

//        log.info("{}, {}, {}, {}, {}",
//                registerForm.getUsername(), registerForm.getPassword(), registerForm.getPasswordCheck(), registerForm.getEmail(), registerForm.getPhone())

        userService.registerUser(registerDto);

        redirectAttributes.addAttribute("success", true);
        return "redirect:/user/login";
    }

    @GetMapping("/inform")
    public String inform(@AuthenticationPrincipal UserContext userContext,
                         Model model){
        UserDto userDto = userService.getUserByUsername(userContext.getUsername());

        UserInformDto userInformDto = new UserInformDto(userDto.getUsername(), userDto.getEmail(), userDto.getPhone());

        model.addAttribute("updatePasswordForm", new UpdatePasswordForm("", "", ""));
        model.addAttribute("userInform", userInformDto);
        return "user/userInform";
    }

    @PostMapping("/inform")
    public String updateInform(@Valid @ModelAttribute UpdatePasswordForm updatePasswordForm,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes,
                               @AuthenticationPrincipal UserContext userContext){

        String username = userContext.getUsername();

        // Validation
        // 필드 에러
        if (bindingResult.hasErrors()){
            log.info("{}", bindingResult);

            UserDto userDto = userService.getUserByUsername(userContext.getUsername());
            UserInformDto userInformDto = new UserInformDto(userDto.getUsername(), userDto.getEmail(), userDto.getPhone());

            model.addAttribute("userInform", userInformDto);

            return "user/userInform";
        }

        // 글로벌 에러
        if (!userService.checkUserPassword(userContext.getUsername(), updatePasswordForm.getCurrentPassword())){
            bindingResult.reject("NotMatch.currentPasswordCheck", null, "현재 비밀번호와 일치하지 않습니다. 다시 확인해주세요.");
        }

        if (!updatePasswordForm.getNewPassword().equals(updatePasswordForm.getNewPasswordCheck())){
            bindingResult.reject("NotMatch.passwordCheck", null, "비밀번호와 일치하지 않습니다. 다시 확인해주세요.");
        }

        if (bindingResult.hasErrors()){
            log.info("{}", bindingResult);
            bindingResult.getGlobalErrors().forEach(e -> {
                log.info("{}", e.getCode());
            });

            UserDto userDto = userService.getUserByUsername(userContext.getUsername());
            UserInformDto userInformDto = new UserInformDto(userDto.getUsername(), userDto.getEmail(), userDto.getPhone());

            model.addAttribute("userInform", userInformDto);

            return "user/userInform";
        }

        // 기존 비밀번호와 비교
        if (userService.checkUserPassword(userContext.getUsername(), updatePasswordForm.getNewPassword())){
            bindingResult.reject("Duplicated.newPassword", null, "현재 비밀번호와 새 비밀번호가 중복됩니다");
        }

        if (bindingResult.hasErrors()){
            log.info("{}", bindingResult);

            UserDto userDto = userService.getUserByUsername(userContext.getUsername());
            UserInformDto userInformDto = new UserInformDto(userDto.getUsername(), userDto.getEmail(), userDto.getPhone());

            model.addAttribute("userInform", userInformDto);

            return "user/userInform";
        }



        userService.updatePassword(userContext.getUsername(), updatePasswordForm);

        redirectAttributes.addAttribute("success", true);
        return "redirect:/user/inform";
    }


    private void checkUniqueInformation(BindingResult bindingResult, RegisterDto registerDto){

        UserDto userByUsername = userService.getUserByUsername(registerDto.getUsername());
        UserDto userByUserEmail = userService.getUserByUserEmail(registerDto.getEmail());
        UserDto userByUserPhone = userService.getUserByUserPhone(registerDto.getPhone());

        if (userByUsername != null){
            bindingResult.reject("UniqueCheck.username", null, "이미 존재하는 아이디입니다");
        }

        if (userByUserEmail != null){
            bindingResult.reject("UniqueCheck.email", null, "이미 등록된 이메일입니다");
        }

        if (userByUserPhone != null){
            bindingResult.reject("UniqueCheck.phone", null, "이미 등록된 전화번호입니다");
        }

    }
}
