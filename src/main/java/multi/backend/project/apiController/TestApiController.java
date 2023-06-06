package multi.backend.project.apiController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.service.AreaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestApiController {
    private final AreaService areaService;

    @GetMapping("/tour")
    public String tourApi(){

        areaService.InitAreaCode();

        return "콘솔창에 오류가 안떴다면 ok";
    }
}
