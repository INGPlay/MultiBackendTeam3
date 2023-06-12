package multi.backend.project.pathMap.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.pathMap.mapper.PathMapMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PathMapService {

    private final PathMapMapper pathMapMapper;

    private final JSONParser jsonParser;
    @Transactional
    public void insertPath(String username, String title, String requestJson) throws ParseException {

        log.info("username : {}, title : {}", username, title);

        // 트랜잭션 안에서 같은 pathId를 보장하기 위해
        Long pathId = pathMapMapper.getPathmapNextval();

        pathMapMapper.insertPathMap(pathId, username, title);
        insertMarks(pathId, requestJson);
    }

    private void insertMarks(Long pathId, String requestJson) throws ParseException {
        JSONArray request = (JSONArray) jsonParser.parse(requestJson);

        List<Map<String, Object>> markInfoRequests = new ArrayList<>();
        for (int i = 0; i < request.size(); i++){
            JSONObject info = (JSONObject) request.get(i);

            HashMap<String, Object> markInfoRequest = new HashMap<>();
            markInfoRequest.put("pathId", pathId);
            markInfoRequest.put("title", (String) info.get("title"));
            markInfoRequest.put("addr1", (String) info.get("addr1"));
            markInfoRequest.put("addr2", (String) info.get("addr2"));
            markInfoRequest.put("contentId", (Long) info.get("contentId"));
            markInfoRequest.put("contentType", (String) info.get("contentType"));
            markInfoRequest.put("firstImageURI", (String) info.get("firstImageURI"));
            markInfoRequest.put("firstImageURI2", (String) info.get("firstImageURI2"));
            markInfoRequest.put("posX", (Double) info.get("posX"));
            markInfoRequest.put("posY", (Double) info.get("posY"));
            markInfoRequest.put("tel", (String) info.get("tel"));
            markInfoRequest.put("placeOrder", i);

            markInfoRequests.add(markInfoRequest);
        }

        log.info("{}", markInfoRequests);

        pathMapMapper.insertMarksBatch(markInfoRequests);
    }
}
