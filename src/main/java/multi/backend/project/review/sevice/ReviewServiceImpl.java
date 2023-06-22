package multi.backend.project.review.sevice;

import multi.backend.project.review.mapper.ReviewMapper;
import multi.backend.project.review.paging.Criteria;
import multi.backend.project.review.vo.ReviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    public ReviewMapper mapper;

    @Override
    public int insertReview(ReviewVO vo) {
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
    public List<ReviewVO> selectReviewAll() {
        return mapper.selectReviewAll();
    }

    @Override
    public ReviewVO selectReviewOne(int review_id) {
        return mapper.selectReviewOne(review_id);
    }

    @Override
    public List<ReviewVO> getListWithPaging(Criteria cri) {
        return mapper.getListWithPaging(cri);
    }


    @Override
    public int updateReview(ReviewVO vo) {
        return mapper.updateReview(vo);
    }

    @Override
    public int deleteReview(int id ) {
        return mapper.deleteReview(id);
    }

    @Override
    public int updateReview_views(ReviewVO vo) {
        return mapper.updateReview_views(vo);
    }


    @Override
    public int getTotalCount() {
        return this.mapper.getTotalCount();
    }

    @Override
    public int updateReview_recommends(ReviewVO vo) {
        return mapper.updateReview_recommends(vo);
    }
}
