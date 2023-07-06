package com.plan.tour.review.vo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Date;

@Data
@Alias("reviewVO")
public class ReviewVO {
    private int review_id; // 글 번호
    private int user_id ; // 작성자 id
    private String user_name; // 작성자 닉네임
    private String review_title; // 제목
    private String review_content; // 내용
    private Date create_date; // 작성일
    private Date update_date; // 수정일
    private int review_views; // 조회수
    private int review_recommends; // 추천수
    private int contentId; // 장소 id
    private String contentName; // 장소 이름
    private String filename;// 물리적 파일명 uuid_파일명
    private String originFilename;// 원본 파일명
    private long filesize;
    private String old_filename;//수정용 파일


    @Override
    public String toString() {
        return "reviewVO{" +
                "review_id=" + review_id +
                ", user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", review_title='" + review_title + '\'' +
                ", review_content='" + review_content + '\'' +
                ", create_date=" + create_date +
                ", update_date=" + update_date +
                ", review_views=" + review_views +
                ", review_recommends=" + review_recommends +
                ", contentId=" + contentId +
                ", filename=" + filename +
                ", originFilename=" + originFilename +
                ", filesize=" + filesize +
                ", old_filename=" + old_filename +
                '}';
    }

}
