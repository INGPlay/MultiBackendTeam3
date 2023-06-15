package multi.backend.project.pathMap.apiController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.pathMap.service.TourCodeService;
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

        tourCodeService.InitAreaCode();

        return "콘솔창에 오류가 안떴다면 ok";
    }
}
