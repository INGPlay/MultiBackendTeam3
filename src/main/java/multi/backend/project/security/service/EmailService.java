package multi.backend.project.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource("classpath:messages/email.properties")
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${smtp.mail.id}")
    private String id;

    public String sendMessageWithKey(String to) {
        String key = createKey();

        try {
            MimeMessage message = createCertificationMessage(to, key);
            javaMailSender.send(message);
        } catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException();
        }

        return createKey();
    }

    private MimeMessage createCertificationMessage(String to, String key) throws MessagingException, UnsupportedEncodingException {
        log.info("보내는 대상 : " + to);
        log.info("인증 번호 : " + key);
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject("이메일 인증 테스트");

        String messageContent = "" +
                "<div>sendMessage입니다.</div>";

        message.setText(messageContent, "UTF-8", "HTML");       // 형식
        message.setFrom(
                new InternetAddress(id, "i")
        );

        return message;
    }

    private String createKey(){
        Random random = new Random();

        int randomKey = random.nextInt(100);

        return Integer.toString(randomKey);
    }
}
