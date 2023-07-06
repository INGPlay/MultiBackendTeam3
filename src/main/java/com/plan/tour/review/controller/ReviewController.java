package com.plan.tour.review.controller;


import lombok.extern.log4j.Log4j2;
import com.plan.tour.review.service.ReviewServiceImpl;
import com.plan.tour.review.vo.PlaceVO;
import com.plan.tour.review.vo.Review_CommentVO;
import com.plan.tour.review.vo.ReviewVO;
import com.plan.tour.review.commonUtil.CommonUtil;
import com.plan.tour.review.paging.Criteria;
import com.plan.tour.review.paging.PagingVO;
import com.plan.tour.security.domain.context.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.util.*;


@RequestMapping("/review")
@Log4j2
@org.springframework.stereotype.Controller
public class ReviewController {

    @Resource(name = "reviewService")
    private ReviewServiceImpl service;

    @Autowired
    private CommonUtil util;

    //    게시글 insert
    @PostMapping("/write")

    public String insertReiew(Model m, @ModelAttribute ReviewVO review, @RequestParam("placeName")String contentName,
                              @RequestParam("mfilename")MultipartFile mf, HttpSession session
                             , @AuthenticationPrincipal UserContext ux){
        // 파일 업로드 처리
        ServletContext app = session.getServletContext();
        String upDir = app.getRealPath("/resources/upload");
        File dir = new File(upDir);

        if(!dir.exists()){
            dir.mkdirs();
        }
        //파일명 파일크기 알아내기
        if(!mf.isEmpty()){
            String fname = mf.getOriginalFilename();
            long fsize = mf.getSize();
            UUID uid = UUID.randomUUID();
            String filename = uid.toString()+""+fname;
            log.info("fname= "+fname+ "filename="+filename+", uuid= "+uid);

            review.setOriginFilename(fname);
            review.setFilename(filename);
            review.setFilesize(fsize);

            //업로드처리
            try{
                mf.transferTo(new File(upDir, filename));
                log.info("upDir : "+upDir);
            }catch (IOException e){
                log.error("파일 업로드 에러"+e);
            }

        }
        int n = service.insertReview(review, ux.getUsername(), contentName);
        String str= (n>0)? "게시글이 등록되었습니다":"게시글 등록 실패하였습니다";
        String loc = (n>0)? "/review/list":"javascript:history.back()";
        return util.addMsgLoc(m,str,loc);

    }

    //  게시글 삽입 폼 이동
    @GetMapping("/write")
    public String reviewEdit(@AuthenticationPrincipal UserContext ux, Model m){
        m.addAttribute("user_name",ux.getUsername());
        return "review/Write";
    }

    // 게시글 상세보기
    @GetMapping("/view")
    public String reviewForm(Model m, HttpServletRequest seq,@RequestParam(value = "redirect_id",required = false,defaultValue ="0") String rid,@AuthenticationPrincipal UserContext ux){
        ReviewVO vo= service.selectReviewOne(Integer.parseInt(seq.getParameter("review_id")),rid);
        String result = (vo.getUser_name().equals(ux.getUsername())|| service.getUserId(ux.getUsername())==1)? "yes":"no";
        m.addAttribute("vo", vo);
        m.addAttribute("result",result);
        m.addAttribute("ConnectUserName",service.getUserId(ux.getUsername()));
        m.addAttribute("PlaceName",service.getPlaceName(vo.getContentId()));
        return "review/ReviewView";
    }

    @PostMapping(value="/view", produces="application/json")
    @ResponseBody
    public Map<String,Integer> updateReview_view(@RequestParam("review_id") int review_id,@AuthenticationPrincipal UserContext ux){
        ReviewVO vo = service.selectReviewOne(review_id,"1");
        Map<String,Integer> map= service.updateReview_recommends(vo, service.getUserId(ux.getUsername()));
        return map;
    }

    /* 댓글 조회 */
    @GetMapping(value="/comment", produces="application/json")
    @ResponseBody
    public Map<String,Object> selectComment(Model m,@RequestParam("review_id") String review_id,@RequestParam(value = "sort",defaultValue = "1")String sort,@AuthenticationPrincipal UserContext ux){
        m.addAttribute("connectUserName",ux.getUsername());
        List<Review_CommentVO> commentList = service.selectReviewComment(Integer.parseInt(review_id),Integer.parseInt(sort));
        Map<String, Object> map = new HashMap<>();
        map.put("commentList",commentList);
        map.put("totalCount",service.getTotalRecoment(Integer.parseInt(review_id)));
        return map;
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

    //    게시글 수정&삭제 폼 이동
    @PostMapping("/edit")
    public String editForm(Model m , @ModelAttribute ReviewVO vo){
        m.addAttribute("vo",vo);
        m.addAttribute("PlaceName",service.getPlaceName(vo.getContentId()));
        return "review/Edit";
    }

    @PostMapping("/update")
    public String updateReview(Model m, @ModelAttribute ReviewVO vo, @AuthenticationPrincipal UserContext ux){
        ReviewVO rvo = service.updateReview(vo,ux.getUsername());
        m.addAttribute("vo",rvo);
        m.addAttribute("PlaceName",service.getPlaceName(rvo.getContentId()));
        m.addAttribute("result","yes");
        return "review/ReviewView";
    }

    @PostMapping(value="/recomment",produces="application/json")
    @ResponseBody
    public String recomment(@RequestBody Review_CommentVO vo,@AuthenticationPrincipal UserContext ux) throws Exception{
        service.insert_Rerecomment(vo,ux.getUsername());
        return "";
    }

    @PostMapping("/delete")
    public String deleteReview(Model m , @RequestParam("review_id") String reviewId,@AuthenticationPrincipal UserContext ux){
        int n = service.deleteReview(Integer.parseInt(reviewId),ux.getUsername());
        String str = (n>0)? "정상적으로 삭제 완료":"삭제 실패하였습니다";
        String loc = (n>0)? "/review/list":"javascript:history.back()";
        return util.addMsgLoc(m,str,loc);
    }

    // 장소 id 가져오기
    @PostMapping(value = "/search" , produces="application/json")
    @ResponseBody
    public List<PlaceVO> searchPlaceId(@RequestParam("placeName") String placeName){
        List<PlaceVO> vo = service.getPlaceId(placeName);
        return vo;
    }

    // ============================================================================ 서비스 계층으로 옮긴것

    //  게시글 전체 출력
    @GetMapping("/list" )
    public String listReview(Model m, Criteria cri, @RequestParam(value="select", required = false, defaultValue = "1") String select,
                             @RequestParam(value = "searchType" ,required = false,defaultValue = "") String searchType,
                             @RequestParam(value="contentId", required = false) List<String> contentId,
                             @RequestParam(value = "keyword", required = false)String keyword) {

        int totalCount=0;
        if(searchType.isEmpty() || searchType == ""){
            totalCount = service.getTotalCount(); // 전체 게시글 수
        }else if(searchType.equals("3")){
            totalCount = service.getSearchPlaceTotalCount(searchType,contentId);
        }
        else{
            totalCount = service.getSearchTotalCount(searchType,keyword);
        }
        cri.setSort(Integer.parseInt(select));

        PagingVO vo=new PagingVO(cri,totalCount);

        m.addAttribute("list",service.getListWithPaging(cri,searchType,contentId,keyword));
        m.addAttribute("pageMaker", vo);
        m.addAttribute("select",vo.getCri().getSort() );
        m.addAttribute("searchType",searchType);
        m.addAttribute("contentId",contentId);
        m.addAttribute("keyword",keyword);

        return "review/Review";
    }



}
