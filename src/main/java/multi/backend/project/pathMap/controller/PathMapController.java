package multi.backend.project.pathMap.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.pathMap.service.PathMapService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequestMapping("/pathmap")
@RequiredArgsConstructor
public class PathMapController {
    private final PathMapService pathMapService;

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
                              HttpServletRequest request,
                              HttpServletResponse response,
                              Model model){

        plusPathMapViews(pathId, request, response);

        model.addAttribute("pathId", pathId);
        return "pathmap/pathmapPost";
    }

    // 조히수
    private void plusPathMapViews(Long pathId, HttpServletRequest request, HttpServletResponse response) {
        // 조회수 중복 방지를 위한 쿠키
        Cookie[] cookies = request.getCookies();
        String cookieName = "pathmapViews";
        String cookiePath = "/pathmap";
        int cookieMaxAge = 60 * 60 * 24;

        Cookie viewCookie = null;
        for (Cookie cookie : cookies){
            if (cookie.getName().equals(cookieName)){
                viewCookie = cookie;
            }
        }

        if (viewCookie != null){

            if (!viewCookie.getValue().contains(pathId.toString())){

                viewCookie.setValue(viewCookie.getValue() + "_" + pathId);
                viewCookie.setPath(cookiePath);
                viewCookie.setMaxAge(cookieMaxAge);
                response.addCookie(viewCookie);

                pathMapService.plusPathViews(pathId);
            }

        } else {

            Cookie cookie = new Cookie(cookieName, pathId.toString());
            cookie.setPath(cookiePath);
            cookie.setMaxAge(cookieMaxAge);
            response.addCookie(cookie);

            pathMapService.plusPathViews(pathId);
        }
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
