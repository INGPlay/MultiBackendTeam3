package multi.backend.project.review.Service;

import multi.backend.project.review.Mapper.reviewMapper;
import multi.backend.project.review.VO.reviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("reviewService")
public class reviewServiceImpl implements reviewService {

    @Autowired
    public reviewMapper mapper;

    @Override
    public int insertReview(reviewVO vo) {
        return mapper.insertReview(vo);
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
    public int updateReview(reviewVO vo) {
        return mapper.updateReview(vo);
    }

    @Override
    public int deleteReview(int id) {
        return mapper.deleteReview(id);
    }

    @Override
    public int updateReview_views(reviewVO vo) {
        return 0;
    }

    @Override
    public int getTotalCount() {
        return this.mapper.getTotalCount();
    }
}
