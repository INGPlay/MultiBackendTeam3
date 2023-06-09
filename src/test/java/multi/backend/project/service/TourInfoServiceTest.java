package multi.backend.project.service;

import lombok.extern.slf4j.Slf4j;
import multi.backend.project.pathMap.domain.tour.ContentType;
import multi.backend.project.pathMap.domain.tour.LocationBaseDto;
import multi.backend.project.pathMap.domain.tour.PageDto;
import multi.backend.project.pathMap.domain.tour.TourInfoResponse;
import multi.backend.project.pathMap.service.TourInfoService;
import org.json.simple.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.List;

@Slf4j
@SpringBootTest
class TourInfoServiceTest {

    @Autowired private TourInfoService tourInfoService;

    @Test
    void getTourInfoURI() {
        log.info("{}", tourInfoService.getTourInfoURI());
    }

    @Test
    void getTourInfoBasedLocationURI(){
        LocationBaseDto locationBaseDto = new LocationBaseDto(126.981611, 37.568477, 1000);
        PageDto pageDto = new PageDto(10, 1);

        log.info("{}", tourInfoService.getTourInfoBasedLocation(locationBaseDto, pageDto, ContentType.RESTAURANT));
    }

    @Test
    void requestJSONArray(){
        LocationBaseDto locationBaseDto = new LocationBaseDto(126.981611, 37.568477, 1000);
        PageDto pageDto = new PageDto(10, 1);

        URI uri = tourInfoService.getTourInfoBasedLocation(locationBaseDto, pageDto, ContentType.RESTAURANT);

        JSONArray jsonArray = tourInfoService.requestItemArray(uri);
        log.info("{}", jsonArray);
    }

    @Test
    void requestTourInfoResponseLocationBased(){
        LocationBaseDto locationBaseDto = new LocationBaseDto(126.981611, 37.568477, 1000);
        PageDto pageDto = new PageDto(10, 1);

        URI uri = tourInfoService.getTourInfoBasedLocation(locationBaseDto, pageDto, ContentType.RESTAURANT);

        List<TourInfoResponse> tourInfoResponses = tourInfoService.requestTourInfo(uri);

        tourInfoResponses.forEach(r -> {
            log.info("이름 : {}, 주소1 : {}, 주소2 : {}, 거리 : {}, 아이디 : {}, 타입 : {}, 전화번호 : {}, X좌표 : {}, Y좌표 : {}, 이미지URI : {}, 이미지URI축소 : {}",
                    r.getTitle(),
                    r.getAddr1(),
                    r.getAddr2(),
                    r.getDist(),
                    r.getContentId(),
                    r.getContentType().getName(),
                    r.getTel(),
                    r.getPosX(),
                    r.getPosY(),
                    r.getFirstImageURI(),
                    r.getFirstImageURI2()
            );
        });
    }
}