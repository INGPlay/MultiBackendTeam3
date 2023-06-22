package multi.backend.project.review.mapper;

import multi.backend.project.review.paging.Criteria;
import multi.backend.project.review.vo.ReviewVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReviewMapper {


//    1. insert ( 게시글 추가하기 )
    int insertReview(ReviewVO vo);

//    1_2. insert 하기 위해 user 존재 여부 확인
    int isUser(String user_name);

//    1_3 insert를 위한 user_id 가져오기
    int getUserId(String user_name);

//    2. Read (전체 게시판 목록 가져오기)
    List<ReviewVO> selectReviewAll();

//    2_1. Read (특정 게시글 가져오기)
    ReviewVO selectReviewOne(int user_id);

//    2_2. 페이징 적용한 게시판 목록 가져오기
    List<ReviewVO> getListWithPaging(Criteria cri);

//    3. Update (게시글 수정하기)
    int updateReview(ReviewVO vo);

//    4. delete (게시글 삭제하기)
    int deleteReview(int id);

//    5. 조회수 증가
    int updateReview_views(ReviewVO vo);

//    6. 총 게시글 수
    int getTotalCount();

//    7. 추천수 증가
    int updateReview_recommends(ReviewVO vo);




}
