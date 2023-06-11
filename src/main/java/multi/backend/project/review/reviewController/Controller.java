package multi.backend.project.review.reviewController;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/test")
    public String reviewForm(Model mod){
        mod.addAttribute("");
        return "review";
    }

    @GetMapping("/edit")
    public String reviewEdit(){
        return "edit";
    }

}
