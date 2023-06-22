package multi.backend.project.review.Controller;


import lombok.extern.log4j.Log4j2;
import multi.backend.project.review.Sevice.reviewServiceImpl;
import multi.backend.project.review.vo.reviewVO;
import multi.backend.project.review.paging.Criteria;
import multi.backend.project.review.paging.pagingVO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String listReview(Model m, Criteria cri, @RequestParam(value="select", required = false, defaultValue = "1") String select, HttpSession session){
        pagingVO vo;

        int totalCount =  this.service.getTotalCount(); // 전체 게시글 수
        cri.setSort(Integer.parseInt(select));

        m.addAttribute("list",service.getListWithPaging(cri));
        m.addAttribute("pageMaker", vo =new pagingVO(cri,totalCount));
        m.addAttribute("select",vo.getCri().getSort() );
        //session.setAttribute("pageVO",vo);

        return "review/review";
    }
    @GetMapping("/view")
    public String reviewForm(Model m, HttpServletRequest seq,@RequestParam(value = "redirect_id",required = false,defaultValue ="0") String rid){
        String id;
        reviewVO vo;
        if(rid.isEmpty() || rid.equals("0")){
            id= seq.getParameter("review_id");
            //System.out.println("초기 id" + id);
            vo = service.selectReviewOne(Integer.valueOf(id)); // user 찾기
            int n = service.updateReview_views(vo); // 조회수 증가
        }else{
            id=rid;
            //System.out.println("추천 id" + id);
            vo = service.selectReviewOne(Integer.valueOf(id)); // user 찾기
        }

        m.addAttribute("vo",vo);
        // pagingVO page = (pagingVO) session.getAttribute("pageVO");
        //m.addAttribute("page",page);
        return "review/review_view";
    }

    @PostMapping("/view")
    public String reviewForm(Model m, @ModelAttribute reviewVO vo, RedirectAttributes reb){
        int n = service.updateReview_recommends(vo);
        reb.addAttribute("redirect_id",vo.getReview_id());
        return "redirect:/review/view";
    }

    //  게시글 삽입 폼 이동
    @GetMapping("/write")
    public String reviewEdit(HttpSession session){
        return "review/write";
    }

    //    게시글 insert
    @PostMapping("/write")
    public String insertReiew(Model m, @ModelAttribute reviewVO review){
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
            review.setUser_id(userid);

            for (int i = 0; i < 500; i++){
                int n = service.insertReview(review);
                System.out.println("정상적으로 삽입 완료");
            }
        }
        return "redirect:/review/list";
    }
    //    게시글 수정&삭제 폼 이동
    @PostMapping("/edit")
    public String editForm(Model m , @ModelAttribute reviewVO vo, HttpSession session){
        m.addAttribute("vo",vo);
        //System.out.println(vo.getUser_name());
        return "review/edit";
    }

    @PostMapping("/delete")
    public String deleteReview(Model m , HttpServletRequest seq){
        //pagingVO page = (pagingVO) session.getAttribute("pageVO");
        //int select = page.getCri().getSort();
        String id = seq.getParameter("review_id");
        int n = service.deleteReview(Integer.parseInt(id));
        String str = (n>0)? "정상적으로 삭제 완료":"삭제 실패 지져스";
        //return "redirect:/review/list?pageNum="+page.getCri().getPageNum()+"&select="+select;
        return "redirect:/review/list";
    }

    @PostMapping("/update")
    public String updateReview(Model m, @ModelAttribute reviewVO vo, HttpSession session, HttpServletRequest seq){
        m.addAttribute("vo",vo);
        int n = service.updateReview(vo);
        //pagingVO page = (pagingVO) session.getAttribute("pageVO");
        //int select = page.getCri().getSort();
        //return "redirect:/review/list?pageNum="+page.getCri().getPageNum()+"&select="+select;
        return "review/review_view";
    }
}
