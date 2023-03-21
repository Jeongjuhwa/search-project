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
public class ExternalSearchChannelSelector {

    private static Map<ExternalName, SearchSenderService> EXTERNAL_SEARCH_MAP;

    @Qualifier("kakao")
    private final SearchSenderService kakaoSenderService;
    @Qualifier("naver")
    private final SearchSenderService naverSenderService;

    @PostConstruct
    public void postConstruct(){
        EXTERNAL_SEARCH_MAP = ImmutableMap.<ExternalName, SearchSenderService>builder()
                .put(ExternalName.KAKAO, kakaoSenderService)
                .put(ExternalName.NAVER, naverSenderService)
                .build();
    }

    public SearchSenderService select(ExternalName externalName){
        return Optional.ofNullable(EXTERNAL_SEARCH_MAP.get(externalName)).orElseThrow(() -> ApiException.of(
                ErrorCode.INTERNAL_SERVER_ERROR));
    }

}
