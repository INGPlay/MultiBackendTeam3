package multi.backend.project.exception.exception;

import lombok.Getter;
import multi.backend.project.exception.response.PathMapErrorCode;

@Getter
public class UnauthorizedException extends RuntimeException {

    private final PathMapErrorCode pathMapErrorCode;

    public UnauthorizedException() {
        super(PathMapErrorCode.UNAUTHORIZED.getErrorMessage());
        this.pathMapErrorCode = PathMapErrorCode.UNAUTHORIZED;
    }
}
