package com.plan.tour.pathMap.apiController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.plan.tour.pathMap.domain.area.AreaResponse;
import com.plan.tour.pathMap.domain.area.KeywordDto;
import com.plan.tour.pathMap.domain.area.TourInfoKeywordRequest;
import com.plan.tour.pathMap.domain.tour.*;
import com.plan.tour.pathMap.service.TourCodeService;
import com.plan.tour.pathMap.service.TourInfoDetailService;
import com.plan.tour.pathMap.service.TourInfoService;
import org.apache.ibatis.javassist.NotFoundException;
import org.json.simple.JSONObject;
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
    private final TourInfoDetailService tourInfoDetailService;

    @GetMapping("/area/code")
    public ResponseEntity<List<AreaResponse>> getAreaLargeCode(){
        List<AreaResponse> largeAreaResponse = tourCodeService.getLargeAreaResponse();

        System.out.println(1);
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
    // 장소 검색
//    @GetMapping("/place")
//    public ResponseEntity<List<TourInfoResponse>> getTourPlace(TourInfoKeywordRequest tour) throws NotFoundException{
//
//        return new ResponseEntity<>(tourInfoResponses, HttpStatus.OK);
//    }


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

    @GetMapping("/detail/{contentTypeId}/{contentId}")
    public ResponseEntity<JSONObject> getInfoDetail(@PathVariable String contentTypeId,
                                                    @PathVariable Long contentId) throws NotFoundException {


        URI uri = tourInfoDetailService.getTourInfoDetail(contentTypeId, contentId);

        JSONObject tourInfoDetail = tourInfoDetailService.requestTourInfoDetail(uri);

        return new ResponseEntity<>(tourInfoDetail, HttpStatus.OK);
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
        } else if (code.equals(ContentType.All.getCode())){
            return ContentType.All;
        } else {
            assert false: "적절하지 않은 콘텐츠 코드입니다.";
        }

        return null;
    }
}
