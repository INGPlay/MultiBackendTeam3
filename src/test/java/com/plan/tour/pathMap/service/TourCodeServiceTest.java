package com.plan.tour.pathMap.service;

import lombok.extern.slf4j.Slf4j;
import com.plan.tour.pathMap.apiController.response.CodeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.List;

@Slf4j
@Transactional
@SpringBootTest
class TourCodeServiceTest {

    @Autowired private TourCodeService tourCodeService;

    @Test
    void category(){

        URI serviceCodeURI = tourCodeService.getServiceCodeURI();
        List<CodeResponse> codeResponses = tourCodeService.requestCodeURI(serviceCodeURI);

        codeResponses.forEach(r -> {
            log.info("code : {}, name : {}", r.getCode(), r.getName());
        });
    }
}