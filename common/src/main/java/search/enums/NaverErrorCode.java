package search.enums;

import lombok.Getter;

@Getter
public enum NaverErrorCode {
    SE01("잘못된 쿼리요청입니다."),
    SE02("부적절한 size 값입니다."),
    SE03("부적절한 page 값입니다."),
    SE04("부적절한 sort 값입니다."),
    SE06("잘못된 형식의 인코딩입니다."),
    SE05("존재하지 않는 검색 api 입니다."),
    SE99("시스템 에러")
    ;

    private final String message;

    NaverErrorCode(String message) {
        this.message = message;
    }
}
