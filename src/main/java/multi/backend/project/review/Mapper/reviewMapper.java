package multi.backend.project.review.Mapper;

import multi.backend.project.review.VO.PlaceVO;
import multi.backend.project.review.VO.ResponseVO;
import multi.backend.project.review.VO.Review_CommentVO;
import multi.backend.project.review.VO.reviewVO;
import multi.backend.project.review.paging.Criteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface reviewMapper {

    List<reviewVO> selectReviewAll(Map<String,Integer> map);



    //    1. insert ( 게시글 추가하기 )
    int insertReview(reviewVO vo);

    //    1_1 insert를 위한 user_id 가져오기
    int getUserId(String user_name);

    // 1_2. place 테이블 체크
    int checkContentName(int contentId);

    // i_3. place 테이블 추가
    int insertPlace(PlaceVO vo);

    // 1_4. contentId로 contentName 가져오기
    String getPlaceName(int contentId);

    //    2. Read (전체 게시판 목록 가져오기)
    List<reviewVO> selectReviewAll();

    //    2_1. Read (특정 게시글 가져오기)
    reviewVO selectReviewOne(int user_id);

    //    2_2. 페이징 적용한 게시판 목록 가져오기
    List<reviewVO> getListWithPaging(@Param("cri") Criteria cri,@Param("searchType")String searchType,@Param("keyword") String keyword,@Param("contentId") List<String> contentId);

    //    3. Update (게시글 수정하기)
    int updateReview(reviewVO vo);

    //    4. delete (게시글 삭제하기)
    int deleteReview(int id);

    //    5. 조회수 증가
    int updateReview_views(reviewVO vo);

    //    6. 총 게시글 수
    int getTotalCount();

    //    7. 추천수 증가
    int updateReview_recommends(reviewVO vo);


// ========================================

    //     1. 댓글 추가
    int insert_recommends(Review_CommentVO vo);

    //     2. 댓글 조회
    List<Review_CommentVO> selectReviewComment(@Param("review_id") int review_id, @Param("sort") int sort);

    //      3. 댓글 삭제
    int deleteComment(int id);

    // 4. 특정 댓글 찾기
    Review_CommentVO findReComment(int i);

    int insertRerecomment(Review_CommentVO vo);

    //
    int update_comment_group(Review_CommentVO vo);

    //
    int getTotalRecommentCount(int comment_group);

    // 장소 아이디 존재 여부 확인
    int checkPlaceId(String placeName);

    // 장소 아이디 가져오기
    List<PlaceVO> getPlaceId(String placeName);

    // 제목 검색과 글쓴이 검색 총 결과 개수 가져오기
    int getSearchTotalCount(@Param("searchType") String searchType, @Param("keyword") String keyword);

    int getSearchPlaceTotalCount(ResponseVO vo);
}
