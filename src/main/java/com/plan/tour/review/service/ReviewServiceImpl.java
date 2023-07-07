package com.plan.tour.review.service;

import com.plan.tour.review.mapper.ReviewMapper;
import com.plan.tour.review.vo.PlaceVO;
import com.plan.tour.review.vo.ResponseVO;
import com.plan.tour.review.vo.Review_CommentVO;
import com.plan.tour.review.vo.ReviewVO;
import com.plan.tour.review.paging.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;



@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    public ReviewMapper mapper;

    @Override
    @Transactional
    public int insertReview(ReviewVO vo, String user_Name, String contentName, String upDir, MultipartFile mf) {
        vo.setUser_id(mapper.getUserId(user_Name));
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
            vo.setOriginFilename(fname);
            vo.setFilename(filename);
            vo.setFilesize(fsize);
            //업로드처리
            try{
                mf.transferTo(new File(upDir, filename));
            }catch (IOException e){
            }
        }
        if(checkContentName(vo.getContentId())<=0){mapper.insertPlace(new PlaceVO(vo.getContentId(),contentName));}
        return mapper.insertReview(vo);
    }
    @Override
    @Transactional
    public int getUserId(String user_name) {
        return mapper.getUserId(user_name);
    }

    @Override
    @Transactional
    public int checkContentName(int contentId) {
        return mapper.checkContentName(contentId);
    }

    @Override
    @Transactional
    public String getPlaceName(int contentId) {
        return mapper.getPlaceName(contentId);
    }

    @Override
    @Transactional
    public ReviewVO selectReviewOne(int review_id, String rid) {
        if (rid.isEmpty() || rid.equals("0")) {
            ReviewVO vo = mapper.selectReviewOne(Integer.valueOf(review_id)); // 해당 게시글 찾기
            mapper.updateReview_views(vo); // 조회수 증가
        }
        return mapper.selectReviewOne(review_id);
    }

    @Override
    @Transactional
    public ReviewVO updateReview(ReviewVO vo, String ux,String upDir,MultipartFile mf) {
        if(getUserId(ux)!=1 && (!ux.equals(vo.getUser_name()))) {
            return mapper.selectReviewOne(vo.getReview_id());
        }
        ReviewVO oldvo = selectReviewOne(vo.getReview_id(),"1");

        File Fdir = new File(upDir);

        int result = 0;

        if(!Fdir.exists()){
            Fdir.mkdirs();
        }
        if(!mf.isEmpty()){
            result=1;
            if(oldvo.getFilename()!=null) {
                File dir = new File(upDir, oldvo.getFilename());
                if (dir.isFile()) {
                    dir.deleteOnExit();
                }
            }
            String fname = mf.getOriginalFilename();
            long fsize = mf.getSize();
            UUID uid = UUID.randomUUID();
            String filename = uid.toString()+""+fname;
            vo.setOriginFilename(fname);
            vo.setFilename(filename);
            vo.setFilesize(fsize);
            //업로드처리
            try{
                mf.transferTo(new File(upDir, filename));
            }catch (IOException e){
                return vo;
            }
        }else{
            result=0;
        }
        mapper.updateReview(vo,result);
        return mapper.selectReviewOne(vo.getReview_id());
    }

    // =================================================================================================================
    @Override
    @Transactional
    public int deleteReview(int id,String userName) {
        ReviewVO vo = mapper.selectReviewOne(id);
        if((vo.getUser_name().equals(userName)) ||(getUserId(userName)==1)) {
            return mapper.deleteReview(id);
        }
        return 0;
    }


    //---------------------------------------------------------------------------------- 리펙토링 완료




    @Override
    @Transactional
    public int updateReview_views(ReviewVO vo) {
        return mapper.updateReview_views(vo);
    }


    @Override
    @Transactional
    public int getTotalCount() {
        return this.mapper.getTotalCount();
    }

    @Override
    @Transactional
    public Map<String,Integer> updateReview_recommends(ReviewVO vo, int user_id) {

        int i = 0;
        if(mapper.selectRecommentCheck(vo.getReview_id(),user_id)!=0){
            mapper.delete_Review_recommend(vo.getReview_id(),user_id);
            i=2;
        }else{
            mapper.insert_Review_recommend(vo.getReview_id(),user_id);
            i = 1;
        }
        mapper.updateReview_recommends(vo,i);
        ReviewVO resultvo = mapper.selectReviewOne(vo.getReview_id());
        int result = (i==1)? 1:0;
        Map<String,Integer> map = new HashMap<>();
        map.put("review_recommends",resultvo.getReview_recommends());
        map.put("result",result);
        return map;
    }

    @Override
    @Transactional
    public int insert_recommends(Review_CommentVO vo) {
        return mapper.insert_recommends(vo);
    }

    @Override
    @Transactional
    public List<Review_CommentVO> selectReviewComment(int review_id,int sort) {
        return mapper.selectReviewComment(review_id,sort);
    }

    @Override
    @Transactional
    public int deleteComment(int id) {
        return mapper.deleteComment(id);
    }

    @Override
    @Transactional
    public Review_CommentVO findReComment(int i) {
        return mapper.findReComment(i);
    }

    @Override
    @Transactional
    public int insert_Rerecomment(Review_CommentVO vo,String userName) {
        Review_CommentVO pvo = findReComment(vo.getComment_id());
        int totalNum = getTotalRecommentCount(pvo.getComment_group());
        pvo.setUser_id(getUserId(userName));
        pvo.setContent(vo.getContent());
        pvo.setComment_depth(pvo.getComment_depth()+totalNum);
        return mapper.insertRerecomment(pvo);
    }

    @Override
    @Transactional
    public int update_comment_group(Review_CommentVO vo) {
        return mapper.update_comment_group(vo);
    }

    @Override
    @Transactional
    public int getTotalRecommentCount(int comment_group) {
        return mapper.getTotalRecommentCount(comment_group);
    }

    @Override
    @Transactional
    public List<PlaceVO> getPlaceId(String placeName) {
        int n = mapper.checkPlaceId(placeName);
        if(n<=0){
        }
        List<PlaceVO> vo = mapper.getPlaceId(placeName) ;
        return vo;
    }

    @Override
    @Transactional
    public int getSearchTotalCount(String searchType, String keyword) {
        return mapper.getSearchTotalCount(searchType, keyword);
    }
    @Override
    @Transactional
    public int getSearchPlaceTotalCount(String searchType, List<String> contentId) {
        if(contentId==null || contentId.isEmpty()  ) {
            return 0;
        }else{
            ResponseVO vo = new ResponseVO(searchType, contentId);
            return mapper.getSearchPlaceTotalCount(vo);
        }

        //return mapper.getTotalCount();
    }


    @Override
    public int getTotalRecoment(int review_id) {
        return mapper.getTotalRecomment(review_id);
    }


    @Override
    @Transactional
    public List<ReviewVO> getListWithPaging(Criteria cri, String searchType, List<String> contentId, String keyword) {
        if(searchType.equals("3")&& contentId == null){
           searchType="4";
        }
        List<ReviewVO> vo = mapper.getListWithPaging(cri, searchType, keyword, contentId);
        return vo;
    }


}
