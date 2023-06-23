package multi.backend.project.review.Sevice;

import multi.backend.project.review.VO.Review_CommentVO;

import multi.backend.project.review.vo.reviewVO;
import multi.backend.project.review.paging.Criteria;

import java.util.List;


public interface reviewService {

    //    1. Create
    int insertReview(reviewVO vo);

    //    1_2 insert하기 위해 유저 정보 존재 여부 확인
    int isUser(String user_name);

    //  1_3 . insert 하기 위해 유저 id 가져오기
    int getUserId(String user_name);

    //    2. Read
    List<reviewVO> selectReviewAll();

    //    2_1. Read (특정 게시글 가져오기)
    reviewVO selectReviewOne(int review_id);

    //    2_2. 페이징 적용한 게시판 목록 가져오기
    List<reviewVO> getListWithPaging(Criteria cri);

    //    3. Update
    int updateReview(reviewVO vo);

    //    4. delete
    int deleteReview(int id);

    //    5. 조회수 증가
    int updateReview_views(reviewVO boardVO);

    //    6. 총 게시글 수
    int getTotalCount();

    //  7. 추천수 증가
    int updateReview_recommends(reviewVO vo);



// ================================================================================

    //     1. 댓글 추가
    int insert_recommends(Review_CommentVO vo);


    //     2. 댓글 조회
    List<Review_CommentVO> selectReviewComment(int review_id);

    //    3. 댓글 삭제

    int deleteComment(int id);
}
