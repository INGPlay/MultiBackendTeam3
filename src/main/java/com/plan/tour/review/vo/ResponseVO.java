package com.plan.tour.review.vo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Alias("ResponseVO")
@Data
public class ResponseVO {
    private String searchType;
    private List<String> contentId;

    public ResponseVO(String searchType, List<String> contentId) {
        this.searchType = searchType;
        this.contentId = contentId;
    }
}
