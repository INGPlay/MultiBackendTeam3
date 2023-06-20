package multi.backend.project.review.Controller;


import lombok.extern.log4j.Log4j2;
import multi.backend.project.review.Service.reviewServiceImpl;
import multi.backend.project.review.VO.reviewVO;
import multi.backend.project.review.paging.Criteria;
import multi.backend.project.review.paging.pagingVO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RequestMapping("/review")
@Log4j2
@org.springframework.stereotype.Controller
public class reviewController {

    @Resource(name = "reviewService")
    private reviewServiceImpl service;



//  게시글 전체 출력
    @GetMapping("/list" )
    public String listReview(Model m, Criteria cri,HttpSession session,  @RequestParam(value="select", required = false, defaultValue = "1") String select){
        //System.out.println(select+"가 선택되었습니다");
        int totalCount =  this.service.getTotalCount(); // 전체 게시글 수
        cri.setSort(Integer.parseInt(select));
        //System.out.println(cri.getSort());
        m.addAttribute("list",service.getListWithPaging(cri));
        m.addAttribute("pageMaker", new pagingVO(cri,totalCount));
        m.addAttribute("select",select);
        return "review/review";
    }

    //  게시글 삽입 폼 이동
    @GetMapping("/write")
    public String reviewEdit(HttpSession session){
        return "review/write";
    }

//    게시글 insert
    @PostMapping("/write")
    public String insertReiew(Model m, @ModelAttribute reviewVO review, HttpSession session){
        //System.out.print(review.toString());
        //-> 정상적으로 출력

        // 유저 정보 존재 유무 확인
        String user_name = review.getUser_name();
        System.out.println(user_name);
        int isUser = service.isUser(user_name);
        System.out.println(isUser);
        if(isUser == 0){
            System.out.println("사용자 계정이 없습니다");
        }
        else {
            int userid = service.getUserId(user_name);
            System.out.println(userid);

            review.setUser_id(userid);

            for (int i = 0; i < 20; i++){
                int n = service.insertReview(review);
                 System.out.println("정상적으로 삽입 완료");
        }
        }
        return "redirect:/review/list";
    }



    @GetMapping("/view")
    public String reviewForm(Model m, HttpServletRequest seq,HttpSession session){
        String id = seq.getParameter("review_id");
        reviewVO vo = service.selectReviewOne(Integer.valueOf(id));
        int n = service.updateReview_views(vo);
        m.addAttribute("vo",vo);
        return "review/review_view";
    }




//    게시글 수정&삭제 폼 이동
    @PostMapping("/edit")
    public String editForm(Model m , @ModelAttribute reviewVO vo,HttpSession session){
        //System.out.println("수정폼 이동");
        m.addAttribute("vo",vo);
    return "review/edit";
    }

    @PostMapping("/delete")
    public String deleteReview(Model m , HttpServletRequest seq,HttpSession session){
        String id = seq.getParameter("review_id");
        int n = service.deleteReview(Integer.parseInt(id));
        String str = (n>0)? "정상적으로 삭제 완료":"삭제 실패 지져스";
        System.out.println(str);
        return "redirect:/review/list";
    }

    @PostMapping("/update")
    public String updateReview(Model m, @ModelAttribute reviewVO vo, HttpServletRequest seq,HttpSession session){
        System.out.println("불어온 vo");
        System.out.println(vo.toString());
        int n = service.updateReview(vo);
        return "redirect:/review/list";
    }
}
