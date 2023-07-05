package multi.backend.project.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:messages/email.properties")
public class EmailConfig {

    private final int PORT = 465;

    @Value("${smtp.server}")
    private String smtpServer;
    @Value("${smtp.mail.id}")
    private String id;
    @Value("${smtp.mail.password}")
    private String password;

    private final JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

    @Bean
    public JavaMailSender javaMailSender(){

        javaMailSender.setHost(smtpServer);
        javaMailSender.setUsername(id);
        javaMailSender.setPassword(password);
        javaMailSender.setPort(PORT);
        javaMailSender.setJavaMailProperties(getMailProperties());
        javaMailSender.setDefaultEncoding("UTF-8");

        return javaMailSender;
    }

    private Properties getMailProperties(){
        Properties properties = new Properties();
        properties.put("mail.smtp.socketFactory.port", PORT);
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.starttls.required", true);
        properties.put("mail.smtp.socketFactory.fallback", false);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        return properties;
    }
}
