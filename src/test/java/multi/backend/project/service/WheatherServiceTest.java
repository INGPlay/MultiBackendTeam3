package multi.backend.project.service;

import lombok.extern.slf4j.Slf4j;
import multi.backend.project.pathMap.domain.wheather.WheatherResponse;
import multi.backend.project.pathMap.service.WheatherService;
import org.json.simple.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.List;

@Slf4j
@SpringBootTest
class WheatherServiceTest {

    @Autowired private WheatherService wheatherService;

    @Test
    void testWheaterURI(){
        URI forecastWheatherURI = wheatherService.getForecastWheatherURI("20230608", 55, 127);
        log.info("{}", forecastWheatherURI);
    }

    @Test
    void testCurrentWheatherURI(){
        URI currentForecastWheatherURI = wheatherService.getCurrentForecastWheatherURI(55, 127);
        log.info("{}", currentForecastWheatherURI);
    }

    @Test
    void testRequestItemArray(){
        URI currentForecastWheatherURI = wheatherService.getCurrentForecastWheatherURI(55, 127);

        JSONArray jsonArray = wheatherService.requestItemArray(currentForecastWheatherURI);

        log.info("{}", jsonArray);
    }

    @Test
    void testRequest(){
        URI currentForecastWheatherURI = wheatherService.getCurrentForecastWheatherURI(55, 127);

        List<WheatherResponse> wheatherResponses = wheatherService.requestWheatherResponse(currentForecastWheatherURI);

        wheatherResponses.forEach(r -> {
            log.info("{}, {}", r.getForecastDateTime(), r.getHourTemparature());
        });
    }

}