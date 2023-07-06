package com.plan.tour.pathMap.domain.tour;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TourInfoLocationRequest {
    private Double posX;
    private Double posY;
    private Integer radius;
    private Integer pageSize;
    private Integer pageNo;
    private String contentTypeCode;
}
