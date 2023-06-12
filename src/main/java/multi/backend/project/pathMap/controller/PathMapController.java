package multi.backend.project.pathMap.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.pathMap.service.PathMapService;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/pathmap")
@RequiredArgsConstructor
public class PathMapController {

    private final PathMapService pathMapService;

    @GetMapping
    public String basic(){
        return "pathmap/pathmap";
    }

    @PostMapping
    @ResponseBody
    public HttpEntity<String> submitPathMap(@RequestParam String title,
                                            @RequestParam String request) throws ParseException {

        log.info(title, request);
        pathMapService.insertPath("나", title, request);

        return new HttpEntity<>("dd");
    }
}
