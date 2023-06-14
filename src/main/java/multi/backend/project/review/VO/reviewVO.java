package multi.backend.project.review.VO;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Date;

@Data
@Alias("reviewVO")
public class reviewVO {
    private int review_id; // 글 번호
    private String user_id ; // 작성자 id 
    private String review_title; // 제목
    private String review_content; // 내용
    private Date create_date; // 작성일
    private Date update_date; // 수정일
    private int review_views; // 조회수
    private int review_recommends; // 추천수

    @Override
    public String toString() {
        return "reviewVO{" +
                "review_id=" + review_id +
                ", user_id='" + user_id + '\'' +
                ", review_title='" + review_title + '\'' +
                ", review_content='" + review_content + '\'' +
                ", create_date=" + create_date +
                ", update_date=" + update_date +
                ", review_views=" + review_views +
                ", review_recommends=" + review_recommends +
                '}';
    }
}
