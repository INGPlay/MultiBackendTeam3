package multi.backend.project.review.Controller;


import lombok.extern.log4j.Log4j2;
import multi.backend.project.review.Service.reviewServiceImpl;
import multi.backend.project.review.VO.Review_CommentVO;
import multi.backend.project.review.VO.reviewVO;
import multi.backend.project.review.commonUtil.CommonUtil;
import multi.backend.project.review.paging.Criteria;
import multi.backend.project.review.paging.pagingVO;

import multi.backend.project.security.domain.context.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;


@RequestMapping("/review")
@Log4j2
@org.springframework.stereotype.Controller
public class reviewController {

    @Resource(name = "reviewService")
    private reviewServiceImpl service;

    @Autowired
    private CommonUtil util;



    //  게시글 전체 출력
    @GetMapping("/list" )
    public String listReview(Model m, Criteria cri, @RequestParam(value="select", required = false, defaultValue = "1") String select) {
        pagingVO vo;

        int totalCount =  this.service.getTotalCount(); // 전체 게시글 수
        cri.setSort(Integer.parseInt(select));

        m.addAttribute("list",service.getListWithPaging(cri));
        m.addAttribute("pageMaker", vo =new pagingVO(cri,totalCount));
        m.addAttribute("select",vo.getCri().getSort() );
        //session.setAttribute("pageVO",vo);

        return "review/review";
    }


    //  게시글 삽입 폼 이동
    @GetMapping("/write")
    public String reviewEdit(@AuthenticationPrincipal UserContext ux, Model m){
        m.addAttribute("user_name",ux.getUsername());
        return "review/write";
    }



    //    게시글 insert
    @PostMapping("/write")
    public String insertReiew(Model m,@ModelAttribute multi.backend.project.review.VO.reviewVO review,@AuthenticationPrincipal UserContext ux){
        int user_id = service.getUserId(ux.getUsername()); // userName으로 userID 찾아오기
        review.setUser_id(user_id);
        int n = service.insertReview(review);
        String str= (n>0)? "게시글이 등록되었습니다":"게시글 등록 실패하였습니다";
        String loc = (n>0)? "/review/list":"javascript:history.back()";
        return util.addMsgLoc(m,str,loc);
    }

    // 게시글 상세보기
    @GetMapping("/view")
    public String reviewForm(Model m, HttpServletRequest seq,@RequestParam(value = "redirect_id",required = false,defaultValue ="0") String rid,@AuthenticationPrincipal UserContext ux){
        String id;
        reviewVO vo;
        try {
            if (rid.isEmpty() || rid.equals("0")) {
                id = seq.getParameter("review_id");
                //System.out.println("초기 id" + id);
                vo = service.selectReviewOne(Integer.valueOf(id)); // 해당 게시글 찾기
                service.updateReview_views(vo); // 조회수 증가
            } else {
                id = rid;
                //System.out.println("추천 id" + id);
                vo = service.selectReviewOne(Integer.valueOf(id)); // 해당 게시글 찾기
            }
            String result = (vo.getUser_name().equals(ux.getUsername())|| service.getUserId(ux.getUsername())==1)? "yes":"no";
            m.addAttribute("vo", vo);
            m.addAttribute("result",result);
            m.addAttribute("ConnectUserName",service.getUserId(ux.getUsername()));
            System.out.println(ux.getUsername());
            System.out.println();
            return "review/review_view";
        }
        catch (NullPointerException e){
            String str = "로그인을 하셔야 해당 기능을 사용하실 수 있습니다";
            return util.addMasBack(m, str);
        }
    }

//    수정
    @PostMapping("/view")
    public String reviewForm(Model m, @ModelAttribute multi.backend.project.review.VO.reviewVO vo, RedirectAttributes reb){

        service.updateReview_recommends(vo);
        reb.addAttribute("redirect_id",vo.getReview_id());
        return "redirect:/review/view";
    }

/* 댓글 조회 */
    @GetMapping(value="/comment", produces="application/json")
    @ResponseBody
    public List<Review_CommentVO> selectComment(Model m,@RequestParam("review_id") String review_id,@RequestParam(value = "sort",defaultValue = "1")String sort,@AuthenticationPrincipal UserContext ux){
        m.addAttribute("connectUserName",ux.getUsername());
        List<Review_CommentVO> commentList = service.selectReviewComment(Integer.parseInt(review_id),Integer.parseInt(sort));
        return commentList;
    }


    @PostMapping(value="/insert", produces="application/json")
    @ResponseBody
    public String insertComment(@RequestBody Review_CommentVO vo,@AuthenticationPrincipal UserContext ux) throws Exception {
        vo.setUser_id(service.getUserId(ux.getUsername()));
        service.insert_recommends(vo);
        String str="결과";
        return str;
    }

    @PostMapping(value="/deleteComment", produces = "application/json")
    @ResponseBody
    public String deleteComment(@RequestParam("id")String id) throws Exception{
        int n = service.deleteComment(Integer.parseInt(id));
        String str=(n>0)?"성공":"실패";
        return str;
    }

    @PostMapping(value="/recomment",produces="application/json")
    @ResponseBody
    public String recomment(@RequestBody Review_CommentVO vo,@AuthenticationPrincipal UserContext ux) throws Exception{

        Review_CommentVO pvo = service.findReComment(vo.getComment_id());
        int totalNum = service.getTotalRecommentCount(pvo.getComment_group());
        pvo.setUser_id(service.getUserId(ux.getUsername()));
        pvo.setContent(vo.getContent());
        pvo.setComment_depth(pvo.getComment_depth()+totalNum);
        service.insert_Rerecomment(pvo);
        return "";
    }

    //    게시글 수정&삭제 폼 이동
    @PostMapping("/edit")
    public String editForm(Model m , @ModelAttribute multi.backend.project.review.VO.reviewVO vo, HttpSession session){
        m.addAttribute("vo",vo);
        return "review/edit";
    }

    @PostMapping("/delete")
    public String deleteReview(Model m , HttpServletRequest seq,@AuthenticationPrincipal UserContext ux){
        String reviewId = seq.getParameter("review_id");
        reviewVO vo = service.selectReviewOne(Integer.parseInt(reviewId));

        if((!ux.getUsername().equals(vo.getUser_name()) || (service.getUserId(ux.getUsername()))!= 1)){
            return util.addMasBack(m,"Error");
        }
        int n = service.deleteReview(Integer.parseInt(reviewId));
        String str = (n>0)? "정상적으로 삭제 완료":"삭제 실패하였습니다";
        String loc = (n>0)? "/review/list":"javascript:history.back()";
        return util.addMsgLoc(m,str,loc);
    }

    @PostMapping("/update")
    public String updateReview(Model m, @ModelAttribute multi.backend.project.review.VO.reviewVO vo,@AuthenticationPrincipal UserContext ux){
        if((!ux.getUsername().equals(vo.getUser_name()) || (service.getUserId(ux.getUsername()))!= 1)){
            return util.addMasBack(m,"Error");
        }
        m.addAttribute("vo",vo);
        service.updateReview(vo);

        return "review/review_view";
    }
}
