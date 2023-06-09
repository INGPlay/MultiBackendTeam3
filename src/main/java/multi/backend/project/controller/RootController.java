package multi.backend.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Controller
public class RootController {

    @GetMapping
    public String testJsp(){
        return "test";
    }

    @GetMapping("/map")
    public String testMap(){
        return "map";
    }
}
