package com.plan.tour.pathMap.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class TourInfoDetailServiceTest {

    @Autowired TourInfoDetailService tourInfoDetailService;

    @Test
    void getTourInfoDetail() {
        tourInfoDetailService.getTourInfoDetail("15", 2674675L);
    }
}