package multi.backend.project.pathMap.apiController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.pathMap.domain.wheather.WheatherResponse;
import multi.backend.project.pathMap.service.WheatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/wheather")
@RequiredArgsConstructor
public class WheatherApiController {

    private final WheatherService wheatherService;

    @GetMapping
    public ResponseEntity<List<WheatherResponse>> getCurrentWheather(@RequestParam Integer posX,
                                                                     @RequestParam Integer posY){

        // 들어오는 XY와 아래 서비스 코드의 XY는 기준이 반대로 됨
        URI currentForecastWheatherURI = wheatherService.getCurrentForecastWheatherURI(posX, posY);

        log.info("{}", currentForecastWheatherURI);

        List<WheatherResponse> wheatherResponses = wheatherService.requestWheatherResponse(currentForecastWheatherURI);

        return new ResponseEntity<>(wheatherResponses, HttpStatus.OK);
    }
}
