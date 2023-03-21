package search.gateway.component;

import search.gateway.controller.v1.response.KaKaoBlogSearchResponse;
import search.gateway.utils.KaKaoUrlBuilder;
import search.support.GsonHelper;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import search.gateway.controller.v1.response.SearchApiResponse;
import search.enums.SortType;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
@Qualifier("kakao")
public class KaKaoSenderService implements SearchSenderService{

    @Value("${kakao.domain}")
    private String kakaoSearchDomain;
    @Value("${kakao.restApiKey}")
    private String kakaoRestApiKey;
    private final RestTemplate restTemplate;
    private final NaverSenderService naverSenderService;

    @Override
    public SearchApiResponse getBlog(String query, SortType sortType, Integer page, Integer size) {
        URI blogUrl = KaKaoUrlBuilder.getBlogUrl(kakaoSearchDomain, query, sortType, page, size);
        SearchApiResponse searchApiResponse;
        try {
            KaKaoBlogSearchResponse response = send(blogUrl, KaKaoBlogSearchResponse.class, HttpMethod.GET, new HttpEntity<>(defaultKaKaoHeaders()));
            searchApiResponse = response.convertToSearchApiResponse();
        }catch (Exception e){
            log.info("카카오 블로그 검색 실패: "+ e.getMessage());
            searchApiResponse = naverSenderService.getBlog(query,sortType,page,size);
        }

        return searchApiResponse;
    }


    private <T> T send(URI requestUrl, Type responseType, HttpMethod httpMethod, HttpEntity entity){
        ResponseEntity<String> response = restTemplate.exchange(requestUrl, httpMethod, entity,
                    String.class);

        return GsonHelper.fromJson(response.getBody(), responseType);
    }

    private HttpHeaders defaultKaKaoHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.ALL));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", kakaoRestApiKey);
        return headers;
    }
}
