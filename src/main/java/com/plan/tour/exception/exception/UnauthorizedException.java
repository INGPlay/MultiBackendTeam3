package com.plan.tour.exception.exception;

import lombok.Getter;
import com.plan.tour.exception.response.PathMapErrorCode;

@Getter
public class UnauthorizedException extends RuntimeException {

    private final PathMapErrorCode pathMapErrorCode;

    public UnauthorizedException() {
        super(PathMapErrorCode.UNAUTHORIZED.getErrorMessage());
        this.pathMapErrorCode = PathMapErrorCode.UNAUTHORIZED;
    }
}
