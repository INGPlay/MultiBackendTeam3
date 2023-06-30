package multi.backend.project.security.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class EmailServiceTest {

    @Autowired EmailService emailService;


    @Test
    void testEmail(){
        String key = emailService.sendMessageWithKey("de_fer@naver.com");
        log.info("test : {}", key);
    }

}