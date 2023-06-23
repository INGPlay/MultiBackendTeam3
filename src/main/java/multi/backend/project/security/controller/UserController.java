package multi.backend.project.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.security.domain.RegisterDto;
import multi.backend.project.security.domain.RegisterForm;
import multi.backend.project.security.domain.UserDto;
import multi.backend.project.security.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
                           BindingResult bindingResult){

//        log.info("{}", bindingResult);

        // Validation
        if (!registerForm.getPassword().equals(registerForm.getPasswordCheck())){
            bindingResult.reject("NotMatch.passwordCheck", null, "비밀번호와 일치하지 않습니다. 다시 확인해주세요.");
        }

        if (bindingResult.hasErrors()){
            log.info("{}", bindingResult);

            return "user/registerView";
        }

        // 유니크 체크
        checkUniqueInformation(bindingResult, registerForm);
        if (bindingResult.hasErrors()){
            log.info("{}", bindingResult);

            return "user/registerView";
        }

//        log.info("{}, {}, {}, {}, {}",
//                registerForm.getUsername(), registerForm.getPassword(), registerForm.getPasswordCheck(), registerForm.getEmail(), registerForm.getPhone());

        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername(registerForm.getUsername());
        registerDto.setPassword(registerForm.getPassword());
        registerDto.setEmail(registerForm.getEmail());
        registerDto.setPhone(registerForm.getPhone());

        userService.registerUser(registerDto);

        return "redirect:/user/login";
    }

    private void checkUniqueInformation(BindingResult bindingResult, RegisterForm registerForm){

        UserDto userByUsername = userService.getUserByUsername(registerForm.getUsername());
        UserDto userByUserEmail = userService.getUserByUserEmail(registerForm.getEmail());
        UserDto userByUserPhone = userService.getUserByUserPhone(registerForm.getPhone());

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
