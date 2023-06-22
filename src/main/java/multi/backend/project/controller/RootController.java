package multi.backend.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Controller
public class RootController {

    @GetMapping
    public String testJsp(){
        return "index1";
    }

    @GetMapping("/map")
    public String testMap(){
        return "map";
    }


    @GetMapping("home/main_login")
    public String testlogin(){

        return "home/main_login";
    }


    @GetMapping("home/main_my")
    public String testmy(){
        return "home/main_my";
    }

    @GetMapping("home/main_rank")
    public String testrank(){
        return "home/main_rank";
    }





}
