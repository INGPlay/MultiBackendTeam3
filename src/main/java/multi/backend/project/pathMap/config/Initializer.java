package multi.backend.project.pathMap.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.pathMap.service.TourCodeService;
import multi.backend.project.security.domain.RegisterDto;
import multi.backend.project.security.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Initializer implements InitializingBean {
    private final TourCodeService tourCodeService;

    private final UserService userService;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("[InitializingBean] 지역코드 DB 등록 시작");

        //tourCodeService.initAreaCode();

        log.info("[InitializaingBean] 지역코드 DB 등록 완료");
        
        // 어드민 계정 등록
        //userService.registerAdmin(new RegisterDto("나", "1111", "asdf@asdf", "01010101010"));
    }
}
