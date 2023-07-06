package com.plan.tour.pathMap.apiController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.plan.tour.pathMap.service.TourCodeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestApiController {
    private final TourCodeService tourCodeService;

    @GetMapping("/tour")
    public String tourApi(){

        tourCodeService.initAreaCode();

        return "콘솔창에 오류가 안떴다면 ok";
    }
}
