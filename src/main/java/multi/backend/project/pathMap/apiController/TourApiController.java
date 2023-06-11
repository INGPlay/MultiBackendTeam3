package multi.backend.project.pathMap.apiController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.pathMap.domain.tour.*;
import multi.backend.project.pathMap.service.TourInfoService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/tour")
@RequiredArgsConstructor
public class TourApiController {
    private final TourInfoService tourInfoService;

    @GetMapping("/location")
    public ResponseEntity<List<TourInfoResponse>> getTourInfoBasedLocation(TourInfoLocationRequest tourInfoLocationRequest)
            throws NotFoundException {

        LocationBaseDto locationBaseDto = new LocationBaseDto(
                tourInfoLocationRequest.getPosX(),
                tourInfoLocationRequest.getPosY(),
                tourInfoLocationRequest.getRadius()
        );

        PageDto pageDto = new PageDto(
                tourInfoLocationRequest.getPageSize(),
                tourInfoLocationRequest.getPageNo()
        );

        URI tourInfoUri = tourInfoService.getTourInfoURIBasedLocation(locationBaseDto, pageDto, ContentType.RESTAURANT);

        List<TourInfoResponse> tourInfoResponses = tourInfoService.requestTourInfo(tourInfoUri);


        return new ResponseEntity<>(tourInfoResponses, HttpStatus.OK);
    }
}
