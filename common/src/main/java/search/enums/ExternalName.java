package search.enums;

import lombok.Getter;

@Getter
public enum ExternalName {

    KAKAO("카카오"),
    NAVER("네이버")
    ;

    private final String desc;

    ExternalName(String desc) {
        this.desc = desc;
    }
}
