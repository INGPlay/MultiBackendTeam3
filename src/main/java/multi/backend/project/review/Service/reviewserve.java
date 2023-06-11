package multi.backend.project.review.Service;

import multi.backend.project.review.VO.reviewVO;

import java.util.List;
import java.util.Map;

public interface reviewserve {

    //    1. Create
    int insertReview(reviewVO vo);

    //    2. Read
    List<reviewVO> selectReviewAll(Map<String,Integer> map);

    //    3. Update
    int updateReview(reviewVO vo);

    //    4. delete
    int deleteReview(int id);

    //    5. 조회수 증가
    int updateReview_views(reviewVO boardVO);
}
