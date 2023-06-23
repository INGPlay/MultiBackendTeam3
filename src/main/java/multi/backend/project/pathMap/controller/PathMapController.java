package multi.backend.project.pathMap.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import multi.backend.project.pathMap.service.PathMapService;
import multi.backend.project.security.domain.UserContext;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
                              @AuthenticationPrincipal UserContext userContext,
                              Model model){

        // 자기자신의 글만 수정이 가능하도록 함
        if (userContext != null){
            model.addAttribute("username", userContext.getUsername());
        }

        model.addAttribute("authorizedAuthor", isPathmapAuthor(userContext, pathId));




        plusPathMapViews(pathId, request, response);

        model.addAttribute("pathId", pathId);
        return "pathmap/pathmapPost";
    }



    // 수정 페이지
    @GetMapping("/update/{pathId}")
    public String updatePathMap(@PathVariable Long pathId,
                                @AuthenticationPrincipal UserContext userContext,
                                Model model){

        boolean isAuthor = isPathmapAuthor(userContext, pathId);
        if (!isAuthor){
            return "redirect:/pathmap";
        }

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

    // 그 게시글의 작성자가 이 사용자인가?
    private boolean isPathmapAuthor(UserContext userContext, Long pathId){

        if (userContext != null) {
            String username = userContext.getUsername();
            String authorName = pathMapService.getPathInfo(pathId).getUsername();

            return username.equals(authorName);

        } else {
            return false;
        }
    }

    // 조회수
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
}
