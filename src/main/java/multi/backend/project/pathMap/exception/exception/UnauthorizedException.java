package multi.backend.project.pathMap.exception.exception;

import lombok.Getter;
import multi.backend.project.pathMap.exception.response.PathMapErrorCode;

@Getter
public class UnauthorizedException extends RuntimeException {

    private final PathMapErrorCode pathMapErrorCode;

    public UnauthorizedException() {
        super(PathMapErrorCode.UNAUTHORIZED.getErrorMessage());
        this.pathMapErrorCode = PathMapErrorCode.UNAUTHORIZED;
    }
}
