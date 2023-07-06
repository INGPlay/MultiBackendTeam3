package com.plan.tour.exception.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse(PathMapErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getErrorMessage();
    }
}
