package multi.backend.project.review.Controller;

import lombok.extern.log4j.Log4j;
import multi.backend.project.review.Service.reviewServiceImpl;
import multi.backend.project.review.VO.reviewVO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


@RequestMapping("/review")
@org.springframework.stereotype.Controller
public class reviewController {

    @Resource(name = "reviewService")
    private reviewServiceImpl service;

    @GetMapping("/test")
    public String reviewForm(Model mod){
        mod.addAttribute("");
        return "review";
    }

    @GetMapping("/edit")
    public String reviewEdit(){
        return "edit";
    }

    @PostMapping("/write")
    public String insertReiew(Model m, @ModelAttribute reviewVO review, HttpSession session){
        System.out.print(review.toString());
        //-> 정상적으로 출력

        int n = service.insertReview(review);
        System.out.println(n);
        return "redirect:/review/test";
    }

}
