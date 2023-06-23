package multi.backend.project.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.security.domain.RegisterDto;
import multi.backend.project.security.domain.RegisterForm;
import multi.backend.project.security.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginForm(){
        return "user/loginForm";
    }

    @GetMapping("/register")
    public String registerForm(Model model){

        model.addAttribute("registerForm", new RegisterForm("", "", "", "", ""));

        return "user/registerForm";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerForm") RegisterForm registerForm,
                           BindingResult bindingResult,
                           Model model){

//        log.info("{}", bindingResult);

        if (!registerForm.getPassword().equals(registerForm.getPasswordCheck())){
            bindingResult.reject("NotMatch.passwordCheck", null, "비밀번호와 일치하지 않습니다. 다시 확인해주세요.");
        }

        if (bindingResult.hasErrors()){
            log.info("{}", bindingResult);

            return "user/registerForm";
        }

        // Validation 예정
        log.info("{}, {}, {}, {}, {}",
                registerForm.getUsername(), registerForm.getPassword(), registerForm.getPasswordCheck(), registerForm.getEmail(), registerForm.getPhone());
        
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername(registerForm.getUsername());
        registerDto.setPassword(registerForm.getPassword());
        registerDto.setEmail(registerForm.getEmail());
        registerDto.setPhone(registerForm.getPhone());

        userService.registerUser(registerDto);

        return "redirect:/login";
    }
}
