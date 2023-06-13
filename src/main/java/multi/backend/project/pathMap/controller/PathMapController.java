package multi.backend.project.pathMap.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/pathmap")
@RequiredArgsConstructor
public class PathMapController {

    @GetMapping("/mark")
    public String basic(){
        return "pathmap/pathmap";
    }

    @GetMapping
    public String listPathMap(){
        return "pathmap/pathmapThread";
    }

    @GetMapping("/{pathId}")
    public String viewPathMap(@PathVariable Long pathId,
                              Model model){

        model.addAttribute("pathId", pathId);
        return "pathmap/pathmapPost";
    }
}
