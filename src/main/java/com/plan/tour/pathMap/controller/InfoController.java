package com.plan.tour.pathMap.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/info")
@RequiredArgsConstructor
public class InfoController {
    // 세부사항 페이지
    @GetMapping("/place/{contentTypeId}/{contentId}")
    public String viewInfo(@PathVariable String contentTypeId,
                           @PathVariable Long contentId,
                           Model model){

        model.addAttribute("contentTypeId", contentTypeId);
        model.addAttribute("contentId", contentId);

        return "info/place";
    }

    @GetMapping("/wheather/{posX}/{posY}")
    public String viewWheather(@PathVariable Integer posX,
                               @PathVariable Integer posY,
                               Model model){

        model.addAttribute("posX", posX);
        model.addAttribute("posY", posY);

        return "info/wheather";
    }
}
