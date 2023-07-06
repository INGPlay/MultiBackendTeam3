package com.plan.tour.pathMap.domain.area;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class InsertAreaSmallDto {
    private String largeCode;
    private String smallCode;
    private String smallName;
}
