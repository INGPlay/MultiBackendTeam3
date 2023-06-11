package multi.backend.project.review.Service;

import multi.backend.project.review.Mapper.reviewMapper;
import multi.backend.project.review.VO.reviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("reviewService")
public class reviewServiceImpl implements reviewserve {

    @Autowired
    public reviewMapper mapper;

    @Override
    public int insertReview(reviewVO vo) {
        return mapper.insertReview(vo);
    }

    @Override
    public List<reviewVO> selectReviewAll(Map<String, Integer> map) {
        return null;
    }

    @Override
    public int updateReview(reviewVO vo) {
        return 0;
    }

    @Override
    public int deleteReview(int id) {
        return 0;
    }

    @Override
    public int updateReview_views(reviewVO boardVO) {
        return 0;
    }
}
