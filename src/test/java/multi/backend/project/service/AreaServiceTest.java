package multi.backend.project.service;

import lombok.extern.slf4j.Slf4j;
import multi.backend.project.apiController.response.CodeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class AreaServiceTest {

    @Autowired private AreaService areaService;

    @Test
    void category(){

        URI serviceCodeURI = areaService.getServiceCodeURI();
        List<CodeResponse> codeResponses = areaService.requestCodeURI(serviceCodeURI);

        codeResponses.forEach(r -> {
            log.info("code : {}, name : {}", r.getCode(), r.getName());
        });
    }
}