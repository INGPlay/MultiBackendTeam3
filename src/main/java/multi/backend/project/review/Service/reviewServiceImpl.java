package multi.backend.project.review.Sevice;

import multi.backend.project.review.Mapper.reviewMapper;
import multi.backend.project.review.VO.Review_CommentVO;
import multi.backend.project.review.vo.reviewVO;
import multi.backend.project.review.paging.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service("reviewService")
public class reviewServiceImpl implements multi.backend.project.review.Sevice.reviewService {

    @Autowired
    public reviewMapper mapper;

    @Override
    public int insertReview(reviewVO vo) {
        return mapper.insertReview(vo);
    }

    @Override
    public int isUser(String user_name) {
        return mapper.isUser(user_name);
    }

    @Override
    public int getUserId(String user_name) {
        return mapper.getUserId(user_name);
    }


    @Override
    public List<reviewVO> selectReviewAll() {
        return mapper.selectReviewAll();
    }

    @Override
    public reviewVO selectReviewOne(int review_id) {
        return mapper.selectReviewOne(review_id);
    }

    @Override
    public List<reviewVO> getListWithPaging(Criteria cri) {
        return mapper.getListWithPaging(cri);
    }


    @Override
    public int updateReview(reviewVO vo) {
        return mapper.updateReview(vo);
    }

    @Override
    public int deleteReview(int id ) {
        return mapper.deleteReview(id);
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
        return mapper.updateReview_recommends(vo);
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
    public int insert_Rerecomment(Review_CommentVO vo) {
        return mapper.insertRerecomment(vo);
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
