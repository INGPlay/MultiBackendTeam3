package multi.backend.project.review.VO;


import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Date;


@Data
@Alias("commentVO")
public class Review_CommentVO {
    private int comment_id; // 댓글 번호
    private int review_id; // 게시글 번호
    private Date create_date; // 댓글 단 날짜
    private Date update_date; // 댓글 수정 날짜
    private String content; // 댓글 내용
    private int comment_group;// 루트 댓글
    private int comment_depth; // 댓글 깊이
    private int user_id; // 유저 아이디
    private String user_name; // 유저 이름


    public Review_CommentVO() {

    }

    public Review_CommentVO(int comment_id, int review_id, Date created_date, Date update_date, String content, int comment_group, int comment_depth, int user_id) {
        this.comment_id = comment_id;
        this.review_id = review_id;
        this.create_date = created_date;
        this.update_date = update_date;
        this.content = content;
        this.comment_group = comment_group;
        this.comment_depth = comment_depth;
        this.user_id = user_id;
    }
}
