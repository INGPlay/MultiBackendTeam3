package multi.backend.project.pathMap.apiController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.pathMap.domain.pathmap.MarkInfoResponse;
import multi.backend.project.pathMap.domain.pathmap.PathInfoResponse;
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

    @GetMapping
    public ResponseEntity<List<PathInfoResponse>> selectPathInfoList(){

        List<PathInfoResponse> pathList = pathMapService.getPathInfoList();

        pathList.forEach(p -> {
            log.info("{} {}",p.getPathTitle(), p.getCreateDate());
        });
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
}
