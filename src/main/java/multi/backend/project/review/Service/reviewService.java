package multi.backend.project.review.Service;

import multi.backend.project.review.VO.PlaceVO;
import multi.backend.project.review.VO.Review_CommentVO;
import multi.backend.project.review.VO.reviewVO;
import multi.backend.project.review.paging.Criteria;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


public interface reviewService {

    //    1. Create
    int insertReview(reviewVO vo,String User_id,String contentName);


    //  1_1 . insert 하기 위해 유저 id 가져오기
    int getUserId(String user_name);

    // 1_2. place 테이블 체크
    int checkContentName(int contentId);

    // 1_3. contentId로 contentName 가져오기
    String getPlaceName(int contentId);


    //    2_1. Read (특정 게시글 가져오기)
    reviewVO selectReviewOne(int review_id,String rid);

    //    2_2. 페이징 적용한 게시판 목록 가져오기
    List<reviewVO> getListWithPaging(Criteria cri,String searchType,List<String> contentId, String keyword);

    //    3. Update
    reviewVO updateReview(reviewVO vo,String name);

    //    4. delete
    int deleteReview(int id,String userName);

    //    5. 조회수 증가
    int updateReview_views(reviewVO boardVO);

    //    6. 총 게시글 수
    int getTotalCount();

    //  7. 추천수 증가
    Map<String,Integer> updateReview_recommends(reviewVO vo, int user_id);


// ================================================================================



    //     1. 댓글 추가
    int insert_recommends(Review_CommentVO vo);


    //     2. 댓글 조회
    List<Review_CommentVO> selectReviewComment(int review_id,int sort);

    //    3. 댓글 삭제

    int deleteComment(int id);

    // 4. 특정 댓글 찾기
    Review_CommentVO findReComment(int i);


    // 5. 대댓글 넣기
    int insert_Rerecomment(Review_CommentVO vo,String userName);


    // 6. 첫 댓글 깊이 증가
    int update_comment_group(Review_CommentVO vo);

    //
    int getTotalRecommentCount(int comment_group);

    List<PlaceVO> getPlaceId(String placeName);

    int getSearchTotalCount(String searchType, String keyword);
    int getSearchPlaceTotalCount(String searchType, List<String> keyword);


    int getTotalRecoment(int review_id);
}
