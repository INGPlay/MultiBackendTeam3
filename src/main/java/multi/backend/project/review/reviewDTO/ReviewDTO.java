package multi.backend.project.review.reviewDTO;

import lombok.Data;

@Data
public class ReviewDTO {
    private int review_id;
    private int user_id;
    private String review_title;
    private String review_content;
    private String create_date;
    private String update_date;
    private int review_views;
    private int review_recommends;

}
