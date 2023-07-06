package com.plan.tour.exception.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PathMapErrorCode {
    NOT_FOUND(404, "NOT FOUND"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    JSON_PARSE_ERROR(500, "JSON_PARSE_ERROR"),
    INTERNAL_ERROR(500, "INTERNAL_SERVER_ERROR");


    private int status;
    private String errorMessage;
}
