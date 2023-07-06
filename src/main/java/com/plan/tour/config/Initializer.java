package com.plan.tour.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.plan.tour.security.domain.RegisterDto;
import com.plan.tour.security.service.UserService;
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
