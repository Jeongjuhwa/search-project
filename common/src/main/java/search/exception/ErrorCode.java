package search.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "요청 형식이 잘못됐습니다"),
    MISSING_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "필수 정보가 존재하지 않습니다."),

    NOT_FOUND(HttpStatus.NOT_FOUND, "존재 하지 않는 요청입니다."),
    NOT_FOUND_CHANNEL(HttpStatus.NOT_FOUND, "요청하려는 채널이 연동되어있지 않습니다."),
    NOT_FOUND_TEAM(HttpStatus.NOT_FOUND, "존재하지 않는 팀입니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버오류 발생"),

    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "요청 메소드가 허용되지 않습니다.")
    ;

    private HttpStatus httpStatus;
    private String message;


    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
