package multi.backend.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.apiController.response.AreaCodeResponse;
import multi.backend.project.domain.area.InsertAreaLargeDto;
import multi.backend.project.domain.area.InsertAreaSmallDto;
import multi.backend.project.mapper.AreaMapper;
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
public class AreaService {

    private final AreaMapper areaMapper;
    private final MessageSource messageSource;
    private final RestTemplate restTemplate;
    private final JSONParser jsonParser;

    @Transactional
    public void InitAreaCode() {
        List<AreaCodeResponse> largeAreaCodeResponses = getLargeAreaCodeResponses();

        largeAreaCodeResponses.forEach(largeAreaResponse -> {
            InsertAreaLargeDto insertAreaLargeDto = new InsertAreaLargeDto(
                    largeAreaResponse.getCode(),
                    largeAreaResponse.getName()
            );
            areaMapper.insertAreaLarge(insertAreaLargeDto);

            List<AreaCodeResponse> smallAreaCodeResponses = getSmallAreaCodeResponses(largeAreaResponse.getCode());
            smallAreaCodeResponses.forEach(smallAreaResponse -> {
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
     * 도 단위의 지역 코드 및 이름 반환
     * @return
     */
    private List<AreaCodeResponse> getLargeAreaCodeResponses(){

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

        List<AreaCodeResponse> areaCodeResponses = getAreaCodeResponses(uri);

        return areaCodeResponses;
    }

    /**
     * 시 단위의 지역 코드 및 이름 반환
     * @param areaCode 도단위 지역코드
     * @return
     */
    private List<AreaCodeResponse> getSmallAreaCodeResponses(Long areaCode){

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

        List<AreaCodeResponse> areaCodeResponses = getAreaCodeResponses(uri);

        return areaCodeResponses;
    }

    /**
     *
     * @param uri
     * @return
     */
    private List<AreaCodeResponse> getAreaCodeResponses(URI uri) {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(uri, String.class);

        List<AreaCodeResponse> areaCodeResponses = new ArrayList<>();
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
                Long code = Long.parseLong((String) row.get("code"));
                String name = (String) row.get("name");

                AreaCodeResponse areaCodeResponse = new AreaCodeResponse(code, name);
                areaCodeResponses.add(areaCodeResponse);
                log.info("{}, {}", code, name);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return areaCodeResponses;
    }

    private String getTourKey(){
        return messageSource.getMessage("keys.tour.info.encode", null, null);
    }
}
