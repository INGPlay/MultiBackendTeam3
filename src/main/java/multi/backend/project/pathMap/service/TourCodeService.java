package multi.backend.project.pathMap.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.pathMap.apiController.response.CodeResponse;
import multi.backend.project.pathMap.domain.area.InsertAreaLargeDto;
import multi.backend.project.pathMap.domain.area.InsertAreaSmallDto;
import multi.backend.project.pathMap.mapper.AreaMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TourCodeService {

    private final AreaMapper areaMapper;
    private final MessageSource messageSource;
    private final RestTemplate restTemplate;
    private final JSONParser jsonParser;

    private String getTourKey(){
        return messageSource.getMessage("keys.tour.info.encode", null, null);
    }

    @Transactional
    public void InitAreaCode() {
        List<CodeResponse> largeCodeResponses = requestCodeURI(getAreaCodeURI());

        largeCodeResponses.forEach(largeAreaResponse -> {
            InsertAreaLargeDto insertAreaLargeDto = new InsertAreaLargeDto(
                    largeAreaResponse.getCode(),
                    largeAreaResponse.getName()
            );
            areaMapper.insertAreaLarge(insertAreaLargeDto);

            List<CodeResponse> smallCodeResponses = requestCodeURI(getAreaCodeURI(largeAreaResponse.getCode()));
            smallCodeResponses.forEach(smallAreaResponse -> {
                InsertAreaSmallDto insertAreaSmallDto = new InsertAreaSmallDto(
                        largeAreaResponse.getCode(),
                        smallAreaResponse.getCode(),
                        smallAreaResponse.getName()
                );
                areaMapper.insertAreaSmall(insertAreaSmallDto);
            });
        });
    }

    /**
     *
     * @param uri
     * @return
     */
    public List<CodeResponse> requestCodeURI(URI uri) {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(uri, String.class);

        List<CodeResponse> codeResponses = new ArrayList<>();
        try {
//            log.info(forEntity.getBody());
            JSONObject parse = (JSONObject)jsonParser.parse(forEntity.getBody());
            JSONObject response = (JSONObject)parse.get("response");
            JSONObject body = (JSONObject)response.get("body");

            JSONObject items = (JSONObject)body.get("items");
            JSONArray itemArray = (JSONArray)items.get("item");

//            log.info("{}", itemArray);

            for (int i = 0; i < itemArray.size(); i++){
                JSONObject row = (JSONObject) itemArray.get(i);

//                Long rnum = (Long) row.get("rnum");
                String code = (String) row.get("code");
                String name = (String) row.get("name");

                CodeResponse codeResponse = new CodeResponse(code, name);
                codeResponses.add(codeResponse);
                log.info("{}, {}", code, name);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return codeResponses;
    }

    public URI getAreaCodeURI(){
        // UriComponentsBuilder.encode() 에서 일부 특수문자(+, \, ...)가 인코딩이 안됨
        // build(true)로 인코딩을 막음
        URI uri = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr")
                .path("/B551011/KorService1/areaCode1")
                .queryParam("serviceKey", getTourKey())
                .queryParam("numOfRows", 100)
                .queryParam("pageNo", 1)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "TestApp")
//                .queryParam("areaCode", 39) <--- 이거 없으면 도 단위 지역코드 반환
                .queryParam("_type", "json")
                .encode(StandardCharsets.UTF_8)
                .build(true).toUri();

        log.info("{}", uri);

        return uri;
    }


    /**
     * 시군구 단위
     * @param areaCode
     * @return
     */
    public URI getAreaCodeURI(String areaCode){
        // UriComponentsBuilder.encode() 에서 일부 특수문자(+, \, ...)가 인코딩이 안됨
        // build(true)로 인코딩을 막음
        URI uri = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr")
                .path("/B551011/KorService1/areaCode1")
                .queryParam("serviceKey", getTourKey())
                .queryParam("numOfRows", 100)
                .queryParam("pageNo", 1)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "TestApp")
                .queryParam("areaCode", areaCode)
                .queryParam("_type", "json")
                .encode(StandardCharsets.UTF_8)
                .build(true).toUri();

        log.info("{}", uri);

        return uri;
    }

    /**
     *
     * @return
     */
    public URI getServiceCodeURI(){
        // UriComponentsBuilder.encode() 에서 일부 특수문자(+, \, ...)가 인코딩이 안됨
        // build(true)로 인코딩을 막음
        URI uri = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr")
                .path("/B551011/KorService1/categoryCode1")
                .queryParam("serviceKey", getTourKey())
//                .queryParam("numOfRows", 100)
//                .queryParam("pageNo", 1)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "TestApp")
                .queryParam("_type", "json")
                .encode(StandardCharsets.UTF_8)
                .build(true).toUri();

        log.info("{}", uri);

        return uri;
    }

    public URI getServiceCodeURI(String cat1){
        // UriComponentsBuilder.encode() 에서 일부 특수문자(+, \, ...)가 인코딩이 안됨
        // build(true)로 인코딩을 막음
        URI uri = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr")
                .path("/B551011/KorService1/categoryCode1")
                .queryParam("serviceKey", getTourKey())
//                .queryParam("numOfRows", 100)
//                .queryParam("pageNo", 1)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "TestApp")
                .queryParam("_type", "json")
                .queryParam("cat1", cat1)
                .encode(StandardCharsets.UTF_8)
                .build(true).toUri();

        log.info("{}", uri);

        return uri;
    }

    public URI getServiceCodeURI(String cat1, String cat2){
        // UriComponentsBuilder.encode() 에서 일부 특수문자(+, \, ...)가 인코딩이 안됨
        // build(true)로 인코딩을 막음
        URI uri = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr")
                .path("/B551011/KorService1/categoryCode1")
                .queryParam("serviceKey", getTourKey())
//                .queryParam("numOfRows", 100)
//                .queryParam("pageNo", 1)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "TestApp")
                .queryParam("_type", "json")
                .queryParam("cat1", cat1)
                .queryParam("cat2", cat2)
                .encode(StandardCharsets.UTF_8)
                .build(true).toUri();

        log.info("{}", uri);

        return uri;
    }

    public URI getServiceCodeURI(String cat1, String cat2, String cat3){
        // UriComponentsBuilder.encode() 에서 일부 특수문자(+, \, ...)가 인코딩이 안됨
        // build(true)로 인코딩을 막음
        URI uri = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr")
                .path("/B551011/KorService1/categoryCode1")
                .queryParam("serviceKey", getTourKey())
//                .queryParam("numOfRows", 100)
//                .queryParam("pageNo", 1)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "TestApp")
                .queryParam("_type", "json")
                .queryParam("cat1", cat1)
                .queryParam("cat2", cat2)
                .queryParam("cat3", cat3)
                .encode(StandardCharsets.UTF_8)
                .build(true).toUri();

        log.info("{}", uri);

        return uri;
    }
}
