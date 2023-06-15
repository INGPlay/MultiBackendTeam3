package multi.backend.project.pathMap.apiController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.pathMap.domain.area.AreaResponse;
import multi.backend.project.pathMap.domain.area.KeywordDto;
import multi.backend.project.pathMap.domain.area.TourInfoKeywordRequest;
import multi.backend.project.pathMap.domain.tour.*;
import multi.backend.project.pathMap.service.TourCodeService;
import multi.backend.project.pathMap.service.TourInfoService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final TourCodeService tourCodeService;

    @GetMapping("/area/code")
    public ResponseEntity<List<AreaResponse>> getAreaLargeCode(){
        List<AreaResponse> largeAreaResponse = tourCodeService.getLargeAreaResponse();

        return new ResponseEntity<>(largeAreaResponse, HttpStatus.OK);
    }

    @GetMapping("/area/code/{largeCode}")
    public ResponseEntity<List<AreaResponse>> getAreaSmallCode(@PathVariable String largeCode){
        List<AreaResponse> smallAreaResponse = tourCodeService.getSmallAreaResponse(largeCode);

        return new ResponseEntity<>(smallAreaResponse, HttpStatus.OK);
    }

    @GetMapping("/keyword")
    public ResponseEntity<List<TourInfoResponse>> getTourInfoBasedArea(TourInfoKeywordRequest tourInfoKeywordRequest) throws NotFoundException {

        KeywordDto keywordDto = new KeywordDto(
                tourInfoKeywordRequest.getKeyword(),
                tourInfoKeywordRequest.getAreaLargeCode(),
                tourInfoKeywordRequest.getAreaSmallCode()
        );

        PageDto pageDto = new PageDto(
                tourInfoKeywordRequest.getPageSize(),
                tourInfoKeywordRequest.getPageNo()
        );

        URI tourInfoURI = tourInfoService.getTourInfoURIBasedKeyword(keywordDto, pageDto, getContentTypeByCode(tourInfoKeywordRequest.getContentTypeCode()));

        List<TourInfoResponse> tourInfoResponses = tourInfoService.requestTourInfo(tourInfoURI);

        return new ResponseEntity<>(tourInfoResponses, HttpStatus.OK);
    }

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

        URI tourInfoUri = tourInfoService.getTourInfoURIBasedLocation(locationBaseDto, pageDto, getContentTypeByCode(tourInfoLocationRequest.getContentTypeCode()));
        log.info("위치기반 정보요청 URI : {}", tourInfoUri);

        List<TourInfoResponse> tourInfoResponses = tourInfoService.requestTourInfo(tourInfoUri);


        return new ResponseEntity<>(tourInfoResponses, HttpStatus.OK);
    }

    private static ContentType getContentTypeByCode(String code){

        if (code.equals(ContentType.TOUR_SPOT.getCode())){
            return ContentType.TOUR_SPOT;
        } else if (code.equals(ContentType.CURTURE_SITE.getCode())) {
            return ContentType.CURTURE_SITE;
        } else if (code.equals(ContentType.FESTIVAL.getCode())){
            return ContentType.FESTIVAL;
        } else if (code.equals(ContentType.TOUR_COURSE.getCode())){
            return ContentType.TOUR_COURSE;
        } else if (code.equals(ContentType.LEPORTS.getCode())){
            return ContentType.LEPORTS;
        } else if (code.equals(ContentType.ACCOMODATION.getCode())){
            return ContentType.ACCOMODATION;
        } else if (code.equals(ContentType.SHOPPING.getCode())){
            return ContentType.SHOPPING;
        } else if (code.equals(ContentType.RESTAURANT.getCode())){
            return ContentType.RESTAURANT;
        } else {
            assert false: "적절하지 않은 콘텐츠 코드입니다.";
        }

        return null;
    }
}
