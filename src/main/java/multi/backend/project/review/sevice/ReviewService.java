package multi.backend.project.review.sevice;

import multi.backend.project.review.paging.Criteria;
import multi.backend.project.review.vo.ReviewVO;

import java.util.List;


public interface ReviewService {

    //    1. Create
    int insertReview(ReviewVO vo);

    //    1_2 insert하기 위해 유저 정보 존재 여부 확인
   int isUser(String user_name);

   //  1_3 . insert 하기 위해 유저 id 가져오기
    int getUserId(String user_name);

    //    2. Read
    List<ReviewVO> selectReviewAll();

    //    2_1. Read (특정 게시글 가져오기)
    ReviewVO selectReviewOne(int review_id);

    //    2_2. 페이징 적용한 게시판 목록 가져오기
    List<ReviewVO> getListWithPaging(Criteria cri);

    //    3. Update
    int updateReview(ReviewVO vo);

    //    4. delete
    int deleteReview(int id);

    //    5. 조회수 증가
    int updateReview_views(ReviewVO boardVO);

    //    6. 총 게시글 수
    int getTotalCount();

    //  7. 추천수 증가
    int updateReview_recommends(ReviewVO vo);
}
