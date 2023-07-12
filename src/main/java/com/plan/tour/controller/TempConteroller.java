package com.plan.tour.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/temp")
public class TempConteroller {

    @GetMapping
    public String temp(){
        return "github in action";
    }
}
