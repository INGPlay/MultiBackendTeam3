package com.plan.tour.pathMap.apiController.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WrappingResponse<T> {
    private T response;
}
