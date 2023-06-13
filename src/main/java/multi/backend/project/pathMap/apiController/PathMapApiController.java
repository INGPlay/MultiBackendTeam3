package multi.backend.project.pathMap.apiController;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.pathMap.service.PathMapService;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/pathmap")
@RequiredArgsConstructor
public class PathMapApiController {

    private final PathMapService pathMapService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<Map<String, Object>> submitPathMap(@RequestParam String title,
                                             @RequestParam String request) throws ParseException {

        log.info(title, request);
        pathMapService.insertPath("나", title, request);

        HashMap<String, Object> response = new HashMap<>();
        response.put("response", "OK");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
