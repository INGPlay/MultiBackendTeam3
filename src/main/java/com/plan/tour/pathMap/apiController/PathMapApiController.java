package com.plan.tour.pathMap.apiController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.plan.tour.pathMap.apiController.requestJson.SubmitCommentDto;
import com.plan.tour.pathMap.apiController.requestJson.UpdatePathMapDto;
import com.plan.tour.pathMap.domain.favorite.FavoriteDto;
import com.plan.tour.pathMap.domain.pathmap.*;
import com.plan.tour.pathMap.domain.pathmap.paging.PagingResponse;
import com.plan.tour.pathMap.domain.pathmap.paging.PathThreadPageDto;
import com.plan.tour.pathMap.domain.pathmap.response.CommentResponse;
import com.plan.tour.pathMap.domain.pathmap.response.MarkInfoResponse;
import com.plan.tour.pathMap.domain.pathmap.response.PathInfoResponse;
import com.plan.tour.exception.exception.UnauthorizedException;
import com.plan.tour.pathMap.service.FavoriteService;
import com.plan.tour.pathMap.service.PathMapService;
import com.plan.tour.security.domain.context.UserContext;
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
    public ResponseEntity<Map<String, Object>> submitPathMap(@RequestBody Map<String, String> requestJson,
                                                             @AuthenticationPrincipal UserContext userContext) throws ParseException {

        String title = requestJson.get("title");
        String markers = requestJson.get("markers");

        log.info("title : {}", title);

        Long pathId = pathMapService.insertPath(userContext.getUsername(), title, markers);

        HashMap<String, Object> response = new HashMap<>();
        response.put("response", "OK");
        response.put("pathId", pathId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 수정
    @PutMapping
    public ResponseEntity<Map<String, Object>> updatePathMap(@RequestBody UpdatePathMapDto updatePathMapDto,
                                                             @AuthenticationPrincipal UserContext userContext) throws ParseException {


        PathInfoResponse pathInfo = pathMapService.getPathInfo(updatePathMapDto.getPathId());
        if (!isPathMapAuthor(userContext.getUsername(), pathInfo.getPathId())){
            throw new UnauthorizedException();
        }

        String title = updatePathMapDto.getTitle();
        String markers = updatePathMapDto.getMarkers();
        Long pathId = updatePathMapDto.getPathId();

        log.info("title : {}, request : {}, pathId : {}", title, markers, pathId);

        pathMapService.updatePath(pathId, title, markers);

        HashMap<String, Object> response = new HashMap<>();
        response.put("response", "OK");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private boolean isPathMapAuthor(String username, Long pathId){
        PathInfoResponse pathInfo = pathMapService.getPathInfo(pathId);

        if (pathInfo.getUsername().equals(username)){
            return true;
        } else {
            return false;
        }
    }

    // 삭제
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deletePathMap(@RequestBody Map<String, Long> requestJson,
                                                             @AuthenticationPrincipal UserContext userContext){

        Long pathId = requestJson.get("pathId");

        PathInfoResponse pathInfo = pathMapService.getPathInfo(pathId);
        if (!isPathMapAuthor(userContext.getUsername(), pathInfo.getPathId())){
            throw new UnauthorizedException();
        }

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
            throw new UnauthorizedException();
        }

        FavoriteDto favoriteDto = new FavoriteDto(userContext.getUsername(), pathId);

        boolean isFavorite = favoriteService.isFavorite(favoriteDto);

        HashMap<String, Object> response = new HashMap<>();
        response.put("response", "OK");
        response.put("isFavorite", isFavorite);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/favorite")
    public ResponseEntity<Map<String, Object>> toggleFavorite(@RequestBody Map<String, Long> requestJson,
                                                              @AuthenticationPrincipal UserContext userContext){

        Long pathId = requestJson.get("pathId");

        FavoriteDto favoriteDto = new FavoriteDto(userContext.getUsername(), pathId);

        favoriteService.toggleFavorite(favoriteDto);

        HashMap<String, Object> response = new HashMap<>();
        response.put("response", "OK");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 댓글 관련 ---------------------------------
    @GetMapping("/comment")
    public ResponseEntity<List<CommentResponse>> getCommentList(@RequestParam Long pathId){

        List<CommentResponse> commentResponses = pathMapService.selectPathCommentList(pathId);

        return new ResponseEntity<>(commentResponses, HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<Map<String, Object>> submitComment(@RequestBody SubmitCommentDto submitCommentDto,
                                                             @AuthenticationPrincipal UserContext userContext){

        log.info("{}, {}", submitCommentDto.getComment(), submitCommentDto.getPathId());
        InsertPathCommentDto insertPathCommentDto = new InsertPathCommentDto(
                submitCommentDto.getPathId(), userContext.getUsername(), submitCommentDto.getComment()
        );

        pathMapService.insertPathComment(insertPathCommentDto);

        HashMap<String, Object> response = new HashMap<>();
        response.put("response", "OK");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/comment")
    public ResponseEntity<Map<String, Object>> deleteComment(@RequestBody Map<String, Long> requestJson,
                                                             @AuthenticationPrincipal UserContext userContext){

        Long commentId = requestJson.get("commentId");

        if (!pathMapService.selectPathComment(commentId).getUsername().equals(userContext.getUsername())){
            throw new UnauthorizedException();
        }

        // 계정 검증 후 삭제
        pathMapService.deletePathComment(commentId);

        HashMap<String, Object> response = new HashMap<>();
        response.put("response", "OK");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
