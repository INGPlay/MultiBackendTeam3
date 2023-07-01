package multi.backend.project.exception;

import lombok.extern.slf4j.Slf4j;
import multi.backend.project.exception.exception.UnauthorizedException;
import multi.backend.project.exception.response.PathMapErrorCode;
import multi.backend.project.exception.response.ErrorResponse;
import org.apache.ibatis.javassist.NotFoundException;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnAuthorizedException(UnauthorizedException exception){
        log.info("[ExceptionHandler] {}", exception.getMessage());
        return getErrorResponseEntity(exception.getPathMapErrorCode());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException exception){
        log.info("[ExceptionHandler] {}", exception.getMessage());
        return getErrorResponseEntity(PathMapErrorCode.NOT_FOUND);
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<ErrorResponse> handleParseException(ParseException exception){
        log.info("[ExceptionHandler] {}", exception.getMessage());
        return getErrorResponseEntity(PathMapErrorCode.JSON_PARSE_ERROR);
    }

    private static ResponseEntity<ErrorResponse> getErrorResponseEntity(PathMapErrorCode errorCode) {
        ErrorResponse errorResponse = new ErrorResponse(errorCode);

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatus()));
    }


}
