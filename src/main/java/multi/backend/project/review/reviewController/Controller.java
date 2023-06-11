package multi.backend.project.review.reviewController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/review")
@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/test")
    public String testJsp(){
        return "review";
    }


}
