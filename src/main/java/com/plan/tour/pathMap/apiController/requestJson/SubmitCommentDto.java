package com.plan.tour.pathMap.apiController.requestJson;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SubmitCommentDto {
    private String comment;
    private Long pathId;
}
