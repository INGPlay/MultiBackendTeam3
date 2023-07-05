package multi.backend.project.review.Service;

import multi.backend.project.review.Mapper.reviewMapper;
import multi.backend.project.review.VO.PlaceVO;
import multi.backend.project.review.VO.ResponseVO;
import multi.backend.project.review.VO.Review_CommentVO;
import multi.backend.project.review.VO.reviewVO;
import multi.backend.project.review.paging.Criteria;
import multi.backend.project.review.paging.pagingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service("reviewService")
public class reviewServiceImpl implements multi.backend.project.review.Service.reviewService{

    @Autowired
    public reviewMapper mapper;

    @Override
    @Transactional
    public int insertReview(reviewVO vo,String user_Name,String contentName) {
        vo.setUser_id(mapper.getUserId(user_Name));
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
    public reviewVO selectReviewOne(int review_id,String rid) {
        if (rid.isEmpty() || rid.equals("0")) {
            reviewVO vo = mapper.selectReviewOne(Integer.valueOf(review_id)); // 해당 게시글 찾기
            mapper.updateReview_views(vo); // 조회수 증가
        }
        return mapper.selectReviewOne(review_id);
    }

    @Override
    @Transactional
    public reviewVO updateReview(reviewVO vo,String ux) {
        if((!ux.equals(vo.getUser_name()) || (getUserId(ux))!= 1)){return vo;}
        mapper.updateReview(vo);
        reviewVO rvo = mapper.selectReviewOne(vo.getReview_id());
        return rvo;
    }

    @Override
    @Transactional
    public int deleteReview(int id,String userName) {
        reviewVO vo = mapper.selectReviewOne(id);
        if((vo.getUser_name().equals(userName)) ||(getUserId(userName)==1)) {
            return mapper.deleteReview(id);
        }
        return 0;
    }


    //---------------------------------------------------------------------------------- 리펙토링 완료










    @Override
    @Transactional
    public int updateReview_views(reviewVO vo) {
        return mapper.updateReview_views(vo);
    }


    @Override
    @Transactional
    public int getTotalCount() {
        return this.mapper.getTotalCount();
    }

    @Override
    @Transactional
    public int updateReview_recommends(reviewVO vo) {
        mapper.updateReview_recommends(vo);
        reviewVO resultvo = mapper.selectReviewOne(vo.getReview_id());
        return resultvo.getReview_recommends();
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
    @Transactional
    public List<reviewVO> getListWithPaging(Criteria cri,String searchType, List<String> contentId, String keyword) {
        if(searchType.equals("3")&& contentId == null){
           searchType="4";
        }
        List<reviewVO> vo = mapper.getListWithPaging(cri, searchType, keyword, contentId);
        return vo;
    }


}
