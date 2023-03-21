package search.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private String code;
    private String message;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorResponse of(ApiException apiException) {
        return new ErrorResponse(apiException.getCode(), apiException.getMessage());
    }


    public static ErrorResponse of(ErrorCode apiErrorCode) {
        return new ErrorResponse(apiErrorCode.toString(), apiErrorCode.getMessage());
    }
}
