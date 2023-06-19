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

    // 패스맵 생성 페이지
    @GetMapping("/mark")
    public String basic(){
        return "pathmap/pathmap";
    }

    // 게시판 페이지
    @GetMapping
    public String listPathMap(){
        return "pathmap/pathmapThread";
    }

    // 게시글 페이지
    @GetMapping("/{pathId}")
    public String viewPathMap(@PathVariable Long pathId,
                              Model model){

        model.addAttribute("pathId", pathId);
        return "pathmap/pathmapPost";
    }

    // 수정 페이지
    @GetMapping("/update/{pathId}")
    public String updatePathMap(@PathVariable Long pathId,
                                Model model){

        model.addAttribute("pathId", pathId);
        return "pathmap/pathmapPostUpdate";
    }


    // 세부사항 페이지
    @GetMapping("/detail/{contentTypeId}/{contentId}")
    public String viewInfo(@PathVariable String contentTypeId,
                           @PathVariable Long contentId,
                           Model model){

        model.addAttribute("contentTypeId", contentTypeId);
        model.addAttribute("contentId", contentId);

        return "redirect:/info/place/" + contentTypeId + "/" + contentId;
    }
}
