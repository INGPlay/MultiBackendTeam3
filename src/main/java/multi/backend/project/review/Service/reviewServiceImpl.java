package multi.backend.project.review.Service;

import multi.backend.project.review.Mapper.reviewMapper;
import multi.backend.project.review.VO.PlaceVO;
import multi.backend.project.review.VO.Review_CommentVO;
import multi.backend.project.review.VO.reviewVO;
import multi.backend.project.review.paging.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service("reviewService")
public class reviewServiceImpl implements multi.backend.project.review.Service.reviewService{

    @Autowired
    public reviewMapper mapper;

    @Override
    @Transactional
    public int insertReview(reviewVO vo,String user_Name,String contentName) {
        vo.setUser_id(mapper.getUserId(user_Name));
        int n = checkContentName(vo.getContentId());
        if(n<=0){
            int i = mapper.insertPlace(new PlaceVO(vo.getContentId(),contentName));
        }
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


    /*------------------------------------------------------------------------------------------------------------------*/
    @Override
    public List<reviewVO> getListWithPaging(Criteria cri) {
        return mapper.getListWithPaging(cri);
    }


    @Override
    @Transactional
    public reviewVO updateReview(reviewVO vo,String ux) {
        if((!ux.equals(vo.getUser_name()) || (getUserId(ux))!= 1)){
            return vo;
        }
        mapper.updateReview(vo);
        reviewVO rvo = mapper.selectReviewOne(vo.getReview_id());
        return rvo;
    }

    @Override
    public int deleteReview(int id,String userName) {
        reviewVO vo = mapper.selectReviewOne(id);
        if((vo.getUser_name().equals(userName)) ||(getUserId(userName)==1)) {
            return mapper.deleteReview(id);
        }
        return 0;
    }

    @Override
    public int updateReview_views(reviewVO vo) {
        return mapper.updateReview_views(vo);
    }


    @Override
    public int getTotalCount() {
        return this.mapper.getTotalCount();
    }

    @Override
    public int updateReview_recommends(reviewVO vo) {
        mapper.updateReview_recommends(vo);
        reviewVO resultvo = mapper.selectReviewOne(vo.getReview_id());
        return resultvo.getReview_recommends();
    }

    @Override
    public int insert_recommends(Review_CommentVO vo) {
        return mapper.insert_recommends(vo);
    }

    @Override
    public List<Review_CommentVO> selectReviewComment(int review_id,int sort) {
        return mapper.selectReviewComment(review_id,sort);
    }

    @Override
    public int deleteComment(int id) {
        return mapper.deleteComment(id);
    }

    @Override
    public Review_CommentVO findReComment(int i) {
        return mapper.findReComment(i);
    }

    @Override
    public int insert_Rerecomment(Review_CommentVO vo,String userName) {
        Review_CommentVO pvo = findReComment(vo.getComment_id());
        int totalNum = getTotalRecommentCount(pvo.getComment_group());
        pvo.setUser_id(getUserId(userName));
        pvo.setContent(vo.getContent());
        pvo.setComment_depth(pvo.getComment_depth()+totalNum);
        return mapper.insertRerecomment(pvo);
    }

    @Override
    public int update_comment_group(Review_CommentVO vo) {
        return mapper.update_comment_group(vo);
    }

    @Override
    public int getTotalRecommentCount(int comment_group) {
        return mapper.getTotalRecommentCount(comment_group);
    }
}
