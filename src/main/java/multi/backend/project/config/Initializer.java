package multi.backend.project.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.security.domain.RegisterDto;
import multi.backend.project.security.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Initializer implements InitializingBean {

    private final UserService userService;

    @Override
    public void afterPropertiesSet() throws Exception {

//         어드민 계정 등록

        if (userService.getUserByUsername("나") == null){
            userService.registerAdmin(new RegisterDto("나", "1111", "asdf@asdf", "01010101010"));

            log.info("[InitializaingBean] Admin 계정 생성 : '나'");
        }

    }
}
