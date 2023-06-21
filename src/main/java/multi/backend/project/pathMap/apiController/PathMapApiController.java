package multi.backend.project.pathMap.apiController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.pathMap.domain.pathmap.*;
import multi.backend.project.pathMap.domain.pathmap.paging.PathPagingResponse;
import multi.backend.project.pathMap.domain.pathmap.paging.PathThreadPageDto;
import multi.backend.project.pathMap.service.PathMapService;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/pathmap")
@RequiredArgsConstructor
public class PathMapApiController {

    private final PathMapService pathMapService;

    // 생성
    @PostMapping
    public ResponseEntity<Map<String, Object>> submitPathMap(@RequestParam String title,
                                                            @RequestParam String request) throws ParseException {

        log.info("title : {}", title);

        pathMapService.insertPath("나", title, request);

        HashMap<String, Object> response = new HashMap<>();
        response.put("response", "OK");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 수정
    @PutMapping
    public ResponseEntity<Map<String, Object>> updatePathMap(@RequestParam String title,
                                                             @RequestParam String request,
                                                             @RequestParam Long pathId) throws ParseException {

        log.info("title : {}, request : {}, pathId : {}", title, request, pathId);

        pathMapService.updatePath(pathId, title, request);

        HashMap<String, Object> response = new HashMap<>();
        response.put("response", "OK");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 삭제
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deletePathMap(@RequestParam Long pathId){

        log.info("pathId : {}", pathId);

        pathMapService.deletePath(pathId);

        HashMap<String, Object> response = new HashMap<>();
        response.put("response", "OK");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 조회
    @GetMapping
    public ResponseEntity<PathPagingResponse<PathInfoResponse>> selectPathInfoList(@RequestParam(defaultValue = "1") int page,
                                                                                 @RequestParam(defaultValue = "10") int size,
                                                                                 @RequestParam(defaultValue = "createDate") String orderBy,
                                                                                 @RequestParam(defaultValue = "") String searchWord,
                                                                                   @RequestParam(defaultValue = "title") String searchOption){

        log.info("page: {}, size: {}, orderBy: {}, searchWord: {}, searchOption: {}", page, size, orderBy, searchWord, searchOption);

        PathThreadPageDto pathThreadPageDto = new PathThreadPageDto(page, size, orderBy, searchWord, searchOption);

        PathPagingResponse<PathInfoResponse> pathList = pathMapService.getPathInfoList(pathThreadPageDto);

        return new ResponseEntity<>(pathList, HttpStatus.OK);
    }

    @GetMapping("/{pathId}")
    public ResponseEntity<Map<String, Object>> selectPathInfoDetail(@PathVariable Long pathId){

        String pathTitle = pathMapService.getPathInfo(pathId).getPathTitle();
        List<MarkInfoResponse> markInfoList = pathMapService.getMarkInfoList(pathId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("title", pathTitle);
        responseMap.put("infoList", markInfoList);

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    // 댓글 관련 ---------------------------------
    @GetMapping("/comment")
    public ResponseEntity<List<CommentResponse>> getCommentList(@RequestParam Long pathId){

        List<CommentResponse> commentResponses = pathMapService.selectComment(pathId);

        return new ResponseEntity<>(commentResponses, HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<Map<String, Object>> submitComment(@RequestParam String comment,
                                                             @RequestParam Long pathId){

        InsertPathCommentDto insertPathCommentDto = new InsertPathCommentDto(pathId, "나", comment);

        pathMapService.insertPathComment(insertPathCommentDto);

        HashMap<String, Object> response = new HashMap<>();
        response.put("response", "OK");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/comment")
    public ResponseEntity<Map<String, Object>> deleteComment(@RequestParam Long commentId){
        
        // 계정 검증 후 삭제
        
        pathMapService.deletePathComment(commentId);

        HashMap<String, Object> response = new HashMap<>();
        response.put("response", "OK");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
