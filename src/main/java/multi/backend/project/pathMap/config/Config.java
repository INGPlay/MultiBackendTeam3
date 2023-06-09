package multi.backend.project.pathMap.config;

import org.json.simple.parser.JSONParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public JSONParser jsonParser(){
        return new JSONParser();
    }
}
