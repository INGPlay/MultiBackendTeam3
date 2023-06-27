package multi.backend.project.pathMap.apiController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.pathMap.domain.favorite.FavoriteDto;
import multi.backend.project.pathMap.domain.pathmap.*;
import multi.backend.project.pathMap.domain.pathmap.paging.PagingResponse;
import multi.backend.project.pathMap.domain.pathmap.paging.PathThreadPageDto;
import multi.backend.project.pathMap.domain.pathmap.response.CommentResponse;
import multi.backend.project.pathMap.domain.pathmap.response.MarkInfoResponse;
import multi.backend.project.pathMap.domain.pathmap.response.PathInfoResponse;
import multi.backend.project.pathMap.service.FavoriteService;
import multi.backend.project.pathMap.service.PathMapService;
import multi.backend.project.security.domain.context.UserContext;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final FavoriteService favoriteService;

    // 생성
    @PostMapping
    public ResponseEntity<Map<String, Object>> submitPathMap(@RequestParam String title,
                                                             @RequestParam String request,
                                                             @AuthenticationPrincipal UserContext userContext) throws ParseException {

        log.info("title : {}", title);

        Long pathId = pathMapService.insertPath(userContext.getUsername(), title, request);

        HashMap<String, Object> response = new HashMap<>();
        response.put("response", "OK");
        response.put("pathId", pathId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 수정
    @PutMapping
    public ResponseEntity<Map<String, Object>> updatePathMap(@RequestParam String title,
                                                             @RequestParam String request,
                                                             @RequestParam Long pathId,
                                                             @AuthenticationPrincipal UserContext userContext) throws ParseException {

        log.info("title : {}, request : {}, pathId : {}", title, request, pathId);

        pathMapService.updatePath(pathId, title, request);

        HashMap<String, Object> response = new HashMap<>();
        response.put("response", "OK");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 삭제
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deletePathMap(@RequestParam Long pathId,
                                                             @AuthenticationPrincipal UserContext userContext){

        log.info("pathId : {}", pathId);

        pathMapService.deletePath(pathId);

        HashMap<String, Object> response = new HashMap<>();
        response.put("response", "OK");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 조회
    @GetMapping
    public ResponseEntity<PagingResponse<PathInfoResponse>> selectPathInfoList(@RequestParam(defaultValue = "1") int page,
                                                                               @RequestParam(defaultValue = "10") int size,
                                                                               @RequestParam(defaultValue = "createDate") String orderBy,
                                                                               @RequestParam(defaultValue = "") String searchWord,
                                                                               @RequestParam(defaultValue = "title") String searchOption,
                                                                               @RequestParam(defaultValue = "false") Boolean isFavorite,
                                                                               @AuthenticationPrincipal UserContext userContext){

        log.info("page: {}, size: {}, orderBy: {}, searchWord: {}, searchOption: {}, isFavorite: {}",
                page, size, orderBy, searchWord, searchOption, isFavorite);

        PathThreadPageDto pathThreadPageDto = new PathThreadPageDto(page, size, orderBy, searchWord, searchOption, isFavorite, "");

        if (isFavorite){
            pathThreadPageDto.setUsername(userContext.getUsername());
        }

        PagingResponse<PathInfoResponse> pathList = pathMapService.getPathInfoList(pathThreadPageDto);

        return new ResponseEntity<>(pathList, HttpStatus.OK);
    }

    // 게시글 조회
    @GetMapping("/{pathId}")
    public ResponseEntity<Map<String, Object>> selectPathInfoDetail(@PathVariable Long pathId){

        String pathTitle = pathMapService.getPathInfo(pathId).getPathTitle();
        List<MarkInfoResponse> markInfoList = pathMapService.getMarkInfoList(pathId);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("title", pathTitle);
        responseMap.put("infoList", markInfoList);

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    // 추천 관련 ------------------------------------
    @GetMapping("/favorite")
    public ResponseEntity<Map<String, Object>> isFavorite(@RequestParam Long pathId,
                                                          @AuthenticationPrincipal UserContext userContext){

        if (userContext == null){
            HashMap<String, Object> response = new HashMap<>();
            response.put("response", "Unauthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        FavoriteDto favoriteDto = new FavoriteDto(userContext.getUsername(), pathId);

        boolean isFavorite = favoriteService.isFavorite(favoriteDto);

        HashMap<String, Object> response = new HashMap<>();
        response.put("response", "OK");
        response.put("isFavorite", isFavorite);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/favorite")
    public ResponseEntity<Map<String, Object>> toggleFavorite(@RequestParam Long pathId,
                                                              @AuthenticationPrincipal UserContext userContext){

        FavoriteDto favoriteDto = new FavoriteDto(userContext.getUsername(), pathId);

        favoriteService.toggleFavorite(favoriteDto);

        HashMap<String, Object> response = new HashMap<>();
        response.put("response", "OK");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 댓글 관련 ---------------------------------
    @GetMapping("/comment")
    public ResponseEntity<List<CommentResponse>> getCommentList(@RequestParam Long pathId){

        List<CommentResponse> commentResponses = pathMapService.selectComment(pathId);

        return new ResponseEntity<>(commentResponses, HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<Map<String, Object>> submitComment(@RequestParam String comment,
                                                             @RequestParam Long pathId,
                                                             @AuthenticationPrincipal UserContext userContext){

        InsertPathCommentDto insertPathCommentDto = new InsertPathCommentDto(pathId, userContext.getUsername(), comment);

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
