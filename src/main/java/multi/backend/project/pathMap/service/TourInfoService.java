package multi.backend.project.pathMap.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.pathMap.domain.area.KeywordDto;
import multi.backend.project.pathMap.domain.tour.ContentType;
import multi.backend.project.pathMap.domain.tour.LocationBaseDto;
import multi.backend.project.pathMap.domain.tour.PageDto;
import multi.backend.project.pathMap.domain.tour.TourInfoResponse;
import org.apache.ibatis.javassist.NotFoundException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TourInfoService {
    private final MessageSource messageSource;
    private final RestTemplate restTemplate;
    private final JSONParser jsonParser;

    private String getTourKey(){
        return messageSource.getMessage("keys.tour.info.encode", null, null);
    }

    public URI getTourInfoURI(){
        // UriComponentsBuilder.encode() 에서 일부 특수문자(+, \, ...)가 인코딩이 안됨
        // build(true)로 인코딩을 막음
        URI uri = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr")
                .path("/B551011/KorService1/areaBasedList1")
                .queryParam("serviceKey", getTourKey())
                .queryParam("numOfRows", 10)
                .queryParam("pageNo", 1)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "TestApp")
                .queryParam("_type", "json")
                .encode(StandardCharsets.UTF_8)
                .build(true).toUri();

        log.info("{}", uri);

        return uri;
    }

    public URI getTourInfoURIBasedLocation(LocationBaseDto locationBaseDto, PageDto pageDto){
        URI uri = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr")
                .path("/B551011/KorService1/locationBasedList1")
                .queryParam("serviceKey", getTourKey())
                .queryParam("numOfRows", pageDto.getPageSize())
                .queryParam("pageNo", pageDto.getPageNo())
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "TestApp")
                .queryParam("_type", "json")
                .queryParam("arrange", "S")
                .queryParam("mapX", locationBaseDto.getPosX())
                .queryParam("mapY", locationBaseDto.getPosY())
                .queryParam("radius", locationBaseDto.getRadius())
                .encode(StandardCharsets.UTF_8)
                .build(true).toUri();

        return uri;
    }

    public URI getTourInfoURIBasedLocation(LocationBaseDto locationBaseDto, PageDto pageDto, ContentType contentType){
        URI uri = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr")
                .path("/B551011/KorService1/locationBasedList1")
                .queryParam("serviceKey", getTourKey())
                .queryParam("numOfRows", pageDto.getPageSize())
                .queryParam("pageNo", pageDto.getPageNo())
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "TestApp")
                .queryParam("_type", "json")
                .queryParam("arrange", "S")
                .queryParam("mapX", locationBaseDto.getPosX())
                .queryParam("mapY", locationBaseDto.getPosY())
                .queryParam("radius", locationBaseDto.getRadius())
                .queryParam("contentTypeId", contentType.getCode())
                .encode(StandardCharsets.UTF_8)
                .build(true).toUri();

        return uri;
    }

    public URI getTourInfoURIBasedKeyword(KeywordDto keywordDto, PageDto pageDto, ContentType contentType){
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr")
                .path("/B551011/KorService1/searchKeyword1")
                .queryParam("serviceKey", getTourKey())
                .queryParam("numOfRows", pageDto.getPageSize())
                .queryParam("pageNo", pageDto.getPageNo())
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "TestApp")
                .queryParam("_type", "json")
                .queryParam("arrange", "R")     // O - 제목순, Q - 수정일순, R - 생성일순
                .queryParam("contentTypeId", contentType.getCode())
                .queryParam("keyword", URLEncoder.encode(keywordDto.getKeyword(), StandardCharsets.UTF_8));

        if (StringUtils.hasText(keywordDto.getLargeCode())){
            uriBuilder = uriBuilder.queryParam("areaCode", keywordDto.getLargeCode());

            if (StringUtils.hasText(keywordDto.getSmallCode())){
                uriBuilder = uriBuilder.queryParam("sigunguCode", keywordDto.getSmallCode());
            }
            
        }

        URI uri = uriBuilder
                .encode(StandardCharsets.UTF_8)
                .build(true).toUri();

        return uri;
    }


    public List<TourInfoResponse> requestTourInfo(URI uri) throws NotFoundException {
        JSONArray tourInfos = requestItemArray(uri);


        List<TourInfoResponse> tourInfoResponses = new ArrayList<>();
        for (int i = 0; i < tourInfos.size(); i++){

            JSONObject tourInfo = (JSONObject) tourInfos.get(i);

            TourInfoResponse tourInfoResponse = jsonToTourResponse(tourInfo);

            tourInfoResponses.add(tourInfoResponse);
        }

        return tourInfoResponses;
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

    private TourInfoResponse jsonToTourResponse(JSONObject tourInfo) {
        TourInfoResponse tourInfoResponse = new TourInfoResponse();

        // 이스케이프 처리해야 함
        tourInfoResponse.setTitle((String) tourInfo.get("title"));
        tourInfoResponse.setAddr1((String) tourInfo.get("addr1"));
        tourInfoResponse.setAddr2((String) tourInfo.get("addr2"));
        if (StringUtils.hasText((String) tourInfo.get("dist"))){
            tourInfoResponse.setDist(Double.parseDouble((String) tourInfo.get("dist")) );
        }
        tourInfoResponse.setTel((String) tourInfo.get("tel"));
        tourInfoResponse.setFirstImageURI((String) tourInfo.get("firstimage"));
        tourInfoResponse.setFirstImageURI2((String) tourInfo.get("firstimage2"));
        tourInfoResponse.setContentId(Long.parseLong((String) tourInfo.get("contentid")) );
        tourInfoResponse.setContentType(getContentTypeById((String) tourInfo.get("contenttypeid")));
        tourInfoResponse.setPosX(Double.parseDouble((String) tourInfo.get("mapx")));
        tourInfoResponse.setPosY(Double.parseDouble((String) tourInfo.get("mapy")) );
        return tourInfoResponse;
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
    private String getContentTypeById(String id){
        switch (id){
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
