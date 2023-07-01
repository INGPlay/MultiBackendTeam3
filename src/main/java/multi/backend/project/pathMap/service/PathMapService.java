package multi.backend.project.pathMap.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.pathMap.domain.pathmap.*;
import multi.backend.project.pathMap.domain.pathmap.paging.PagingResponse;
import multi.backend.project.pathMap.domain.pathmap.paging.PathThreadPageDto;
import multi.backend.project.pathMap.domain.pathmap.response.CommentResponse;
import multi.backend.project.pathMap.domain.pathmap.response.MarkInfoResponse;
import multi.backend.project.pathMap.domain.pathmap.response.PathInfoResponse;
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
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PathMapService {

    private final PathMapMapper pathMapMapper;

    private final JSONParser jsonParser;


    // 동적 쿼리
    @Transactional
    public PagingResponse<PathInfoResponse> getPathInfoList(PathThreadPageDto pathThreadPageDto){
        List<PathInfoResponse> pathInfoResponses = pathMapMapper.selectPathInfoList(pathThreadPageDto);

        return new PagingResponse<PathInfoResponse>(pathInfoResponses, pathThreadPageDto.getPage(), pathThreadPageDto.getSize());
    }

    @Transactional
    public PathInfoResponse getPathInfo(Long pathId){
        PathInfoResponse pathInfoResponse = pathMapMapper.selectPathInfo(pathId);

        return new PathInfoResponse(
                pathInfoResponse.getPathId(),
                pathInfoResponse.getUsername(),
                pathInfoResponse.getCreateDate(),
                pathInfoResponse.getUpdateDate(),
                handleNullOrEmpty(pathInfoResponse.getPathTitle()),
                pathInfoResponse.getPathViews(),
                pathInfoResponse.getPathRecommends()
        );
    }

    @Transactional
    public List<MarkInfoResponse> getMarkInfoList(Long pathId){
        List<MarkInfoResponse> markInfoResponses = pathMapMapper.selectMarkInfoByPathId(pathId);

        return markInfoResponses.stream().map(response -> {
            return new MarkInfoResponse(
                    handleNullOrEmpty(response.getTitle()),
                    handleNullOrEmpty(response.getAddr1()),
                    handleNullOrEmpty(response.getAddr2()),
                    response.getContentId(),
                    response.getContentTypeId(),
                    response.getContentType(),
                    handleNullOrEmpty(response.getFirstImageURI()),
                    handleNullOrEmpty(response.getFirstImageURI2()),
                    response.getPosX(),
                    response.getPosY(),
                    handleNullOrEmpty(response.getTel()),
                    response.getPlaceOrder()
            );
        }).collect(Collectors.toList());
    }

    @Transactional
    public Long insertPath(String username, String title, String requestJson) throws ParseException {

        log.info("username : {}, title : {}", username, title);

        // 트랜잭션 안에서 같은 pathId를 보장하기 위해
        Long pathId = pathMapMapper.getPathmapNextval();

        log.info("asdfasdf : {}", pathId);
        pathMapMapper.insertPathMap(pathId, username, handleNullOrEmpty(title));
        insertMarks(pathId, requestJson);

        return pathId;
    }

    @Transactional
    private void insertMarks(Long pathId, String markers) throws ParseException {
        JSONArray request = (JSONArray) jsonParser.parse(markers);

        Long markCount = pathMapMapper.getMarkCount();

        List<Map<String, Object>> markInfoRequests = new ArrayList<>();
        for (int i = 0; i < request.size(); i++){
            JSONObject info = (JSONObject) request.get(i);

            HashMap<String, Object> markInfoRequest = new HashMap<>();
            markInfoRequest.put("pathId", pathId);
            markInfoRequest.put("markId", ++markCount);
            markInfoRequest.put("title", handleNullOrEmpty((String) info.get("title")));
            markInfoRequest.put("addr1", handleNullOrEmpty((String) info.get("addr1")));
            markInfoRequest.put("addr2", handleNullOrEmpty((String) info.get("addr2")));
            markInfoRequest.put("contentId", (Long) info.get("contentId"));
            markInfoRequest.put("contentTypeId", (String) info.get("contentTypeId"));
            markInfoRequest.put("contentType", (String) info.get("contentType"));
            markInfoRequest.put("firstImageURI", handleNullOrEmpty((String) info.get("firstImageURI")));
            markInfoRequest.put("firstImageURI2", handleNullOrEmpty((String) info.get("firstImageURI2")));
            markInfoRequest.put("posX", (Double) info.get("posX"));
            markInfoRequest.put("posY", (Double) info.get("posY"));
            markInfoRequest.put("tel", handleNullOrEmpty((String) info.get("tel")));
            markInfoRequest.put("placeOrder", i);

            markInfoRequests.add(markInfoRequest);
        }

        log.info("{}", markInfoRequests);

        pathMapMapper.insertMarksBatch(markInfoRequests);
    }

    @Transactional
    public void updatePath(Long pathId, String title, String markers) throws ParseException {

        log.info("pathId : {}, title : {}", pathId, title);

        pathMapMapper.updatePathMap(pathId, title);
        updateMarks(pathId, markers);
    }
    
    // 조회수 추가
    public void plusPathViews(Long pathId){

        pathMapMapper.updatePathMapViews(pathId);
    }

    private void updateMarks(Long pathId, String markers) throws ParseException {
        // 삭제하고
        pathMapMapper.deleteMarksInPath(pathId);
        
        // 새로 삽입
        insertMarks(pathId, markers);
    }

    @Transactional
    public void deletePath(Long pathId){
        // path 삭제
        pathMapMapper.deletePathMap(pathId);
    }


    @Transactional
    public List<CommentResponse> selectComment(Long pathId){
        List<CommentResponse> commentResponses = pathMapMapper.selectPathComment(pathId);

        return commentResponses;
    }

    @Transactional
    public void insertPathComment(InsertPathCommentDto insertPathCommentDto){
        pathMapMapper.insertPathComment(insertPathCommentDto);
    }

    @Transactional
    public void deletePathComment(Long commentid){
        pathMapMapper.deletePathComment(commentid);
    }

    private static String handleNullOrEmpty(String string){
        if (string == null){
            return "";
        }

        string = string.trim();
        if (string.isEmpty()){
            return string;
        }

        return string;
    }
}
