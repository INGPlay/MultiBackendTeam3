package com.plan.tour.pathMap.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.plan.tour.pathMap.domain.tour.ContentType;
import org.apache.ibatis.javassist.NotFoundException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class TourInfoDetailService {
    private final MessageSource messageSource;
    private final RestTemplate restTemplate;
    private final JSONParser jsonParser;

    private String getTourKey(){
        return messageSource.getMessage("keys.tour.info.encode", null, null);
    }

    public URI getTourInfoDetail(String contentTypeId, Long contentId){
        // UriComponentsBuilder.encode() 에서 일부 특수문자(+, \, ...)가 인코딩이 안됨
        // build(true)로 인코딩을 막음
        URI uri = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr")
                .path("/B551011/KorService1/detailIntro1")
                .queryParam("serviceKey", getTourKey())
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "TestApp")
                .queryParam("_type", "json")
                .queryParam("contentTypeId", contentTypeId)
                .queryParam("contentId", contentId)
                .encode(StandardCharsets.UTF_8)
                .build(true).toUri();

        log.info("{}", uri);

        return uri;
    }

    public JSONObject requestTourInfoDetail(URI uri) throws NotFoundException {
        JSONArray tourInfos = requestItemArray(uri);

        JSONObject tourInfo = (JSONObject) tourInfos.get(0);

        return tourInfo;
    }

    private JSONArray requestItemArray(URI uri) throws NotFoundException {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(uri, String.class);

        try {
            JSONObject parse = (JSONObject) jsonParser.parse(forEntity.getBody());


            JSONObject response = (JSONObject) parse.get("response");
            JSONObject body = (JSONObject) response.get("body");
            JSONObject items = (JSONObject) body.get("items");
            JSONArray itemArray = (JSONArray) items.get("item");

            return itemArray;

        }  catch (Exception e){
            throw new NotFoundException("찾은 관광정보가 없습니다.");
        }
    }

    /**
     * 관광타입 : contentTypeId
     * - 관광지 : 12
     * - 문화시설 : 14
     * - 행사/공연/축제 : 15
     * - 여행코스 : 25
     * - 레포츠 : 28
     * - 숙박 : 32
     * - 쇼핑 : 38
     * - 음식점 : 39
     */
    private String getContentTypeById(String contentTypeId){
        switch (contentTypeId){
            case "12" :
                return ContentType.TOUR_SPOT.getName();

            case "14" :
                return ContentType.CURTURE_SITE.getName();

            case "15" :
                return ContentType.FESTIVAL.getName();

            case "25" :
                return ContentType.TOUR_COURSE.getName();

            case "28" :
                return ContentType.LEPORTS.getName();

            case "32" :
                return ContentType.ACCOMODATION.getName();

            case "38" :
                return ContentType.SHOPPING.getName();

            case "39" :
                return ContentType.RESTAURANT.getName();

            default:
                return null;
        }
    }
}
