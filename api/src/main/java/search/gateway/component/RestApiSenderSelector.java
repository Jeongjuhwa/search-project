package search.gateway.component;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import search.enums.ExternalName;
import search.exception.ApiException;
import search.exception.ErrorCode;

@Component
@RequiredArgsConstructor
public class RestApiSenderSelector {

    private static Map<ExternalName, RestApiSender> EXTERNAL_SEARCH_MAP;

    @Qualifier("kakao")
    private final RestApiSender kakaoRestApiSender;
    @Qualifier("naver")
    private final RestApiSender naverRestApiSender;

    @PostConstruct
    public void postConstruct(){
        EXTERNAL_SEARCH_MAP = ImmutableMap.<ExternalName, RestApiSender>builder()
                .put(ExternalName.KAKAO, kakaoRestApiSender)
                .put(ExternalName.NAVER, naverRestApiSender)
                .build();
    }

    public RestApiSender select(ExternalName externalName){
        return Optional.ofNullable(EXTERNAL_SEARCH_MAP.get(externalName)).orElseThrow(() -> ApiException.of(
                ErrorCode.NOT_FOUND_CHANNEL));
    }

}
