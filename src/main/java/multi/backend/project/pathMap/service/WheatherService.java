package multi.backend.project.pathMap.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.pathMap.domain.wheather.WheatherResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class WheatherService {
    private final MessageSource messageSource;
    private final RestTemplate restTemplate;
    private final JSONParser jsonParser;

    private String getWheatherKey(){
        return messageSource.getMessage("keys.tour.info.encode", null, null);
    }

    /**
     * 현재 시간을 기준으로 URI 생성
     * @param nx
     * @param ny
     * @return
     */
    public URI getCurrentForecastWheatherURI(int nx, int ny){
        return getForecastWheatherURI(getCurrentBaseDate(), nx, ny);
    }


    public URI getForecastWheatherURI(String baseDate, int nx, int ny){
        // UriComponentsBuilder.encode() 에서 일부 특수문자(+, \, ...)가 인코딩이 안됨
        // build(true)로 인코딩을 막음
        URI uri = UriComponentsBuilder
                .fromUriString("http://apis.data.go.kr")
                .path("/1360000/VilageFcstInfoService_2.0/getVilageFcst")
                .queryParam("serviceKey", getWheatherKey())
                .queryParam("numOfRows", 1000)
                .queryParam("pageNo", 1)
                .queryParam("dataType", "JSON")
                .queryParam("base_date", baseDate)
                .queryParam("base_time", "0500")
                .queryParam("nx", nx)
                .queryParam("ny", ny)
                .encode(StandardCharsets.UTF_8)
                .build(true).toUri();

        return uri;
    }


    private String getCurrentBaseDate(){
        LocalDate currentDate = LocalDate.now();

        // 5시 이전이라면 이전 날짜를 반환하게 한다
        if (LocalTime.now().getHour() < 5) {
            currentDate = currentDate.minusDays(1);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String currentBaseDate = currentDate.format(formatter);

        return currentBaseDate;
    }

    public JSONArray requestItemArray(URI uri){
        ResponseEntity<String> forEntity = restTemplate.getForEntity(uri, String.class);

        try {
            JSONObject parse = (JSONObject) jsonParser.parse(forEntity.getBody());

            JSONObject response = (JSONObject) parse.get("response");
            JSONObject body = (JSONObject) response.get("body");
            JSONObject items = (JSONObject) body.get("items");
            JSONArray itemArray = (JSONArray) items.get("item");

            return itemArray;

        } catch (ParseException e){
            e.printStackTrace();
        }

        return null;
    }

    public List<WheatherResponse> requestWheatherResponse(URI uri){
        JSONArray jsonArray = requestItemArray(uri);

        HashMap<String, WheatherResponse> wheatherResponseMaps = new HashMap<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);

            String forecastDateTime = (String) jsonObject.get("fcstDate") + (String) jsonObject.get("fcstTime");

            // Map에 없는 키인 경우, 추가한다
            if (!wheatherResponseMaps.containsKey(forecastDateTime)) {
                WheatherResponse wheatherResponse = new WheatherResponse();
                wheatherResponse.setForecastDateTime(forecastDateTime);

                wheatherResponseMaps.put(forecastDateTime, wheatherResponse);
            }

            WheatherResponse wheatherResponse = wheatherResponseMaps.get(forecastDateTime);

            String category = (String) jsonObject.get("category");
            String forecastValue = (String) jsonObject.get("fcstValue");

            switch (category) {
                case "POP":        // 강수확률
                    wheatherResponse.setRainProbability(forecastValue);

                    break;

                case "PTY":        // 강수형태 : 없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4)

                    wheatherResponse.setRainForm(forecastValue);
                    break;

                /**
                 * -, null, 0 : "강수없음"
                 * 0.1 ~ 1.0mm 미만 : "1.0mm 미만"
                 * 1.0mm 이상 30.0mm 미만 : 실수값 + "mm" ("1.0mm" ~ "29.9mm")
                 * 30.0 mm 이상 50.0 mm 미만 : "30.0~50.0mm"
                 * 50.0 mm 이상 : "50.0mm 이상"
                 */
                case "PCP":        // 1시간 강수량

                    wheatherResponse.setRainAmount(forecastValue);
                    break;

                /**
                 * -, null, 0 : "적설없음"
                 * 0.1 ~ 1.0cm 미만 : "1.0cm 미만"
                 * 1.0cm 이상 5.0cm 미만 : 실수값 + "cm" ("1.0cm" ~ "4.9cm")
                 * 5.0 cm 이상 : "5.0cm 이상"
                 */
                case "SNO":        // 1시간 신적설

                    wheatherResponse.setSnowAmount(forecastValue);
                    break;

                case "SKY":        // 하늘상태 : 맑음(1), 구름많음(3), 흐림(4)

                    wheatherResponse.setSkyForm(forecastValue);
                    break;

                case "TMP":        // 1시간 기온 (C)

                    wheatherResponse.setHourTemparature(forecastValue);
                    break;

                case "TMN":        // 일 최저기온 (0600 에 갱신)

                    wheatherResponse.setDayMinTemparature(forecastValue);
                    break;

                case "TMX":        // 일 최고기온 (1500 에 갱신)

                    wheatherResponse.setDayMaxTemparature(forecastValue);
                    break;

                default:

                    break;
            }


        }

        // 날짜, 시간 순으로 정렬
        ArrayList<WheatherResponse> result = new ArrayList<>(wheatherResponseMaps.values());
        Collections.sort(result, (o1, o2) -> {
            return o1.getForecastDateTime().compareTo(o2.getForecastDateTime());
        });

        return result;
    }

}
