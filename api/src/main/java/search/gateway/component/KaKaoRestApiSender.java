package search.gateway.component;

import search.gateway.controller.v1.response.KaKaoBlogSearchResponse;
import search.gateway.utils.KaKaoUrlBuilder;
import search.response.PagingResponse;
import search.support.RestApiSenderSupporter;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import search.gateway.controller.v1.response.BlogResponse;
import search.enums.SortType;

@Component
@Transactional
@Slf4j
@Qualifier("kakao")
public class KaKaoRestApiSender extends RestApiSenderSupporter implements RestApiSender {

    @Value("${kakao.domain}")
    private String kakaoSearchDomain;
    @Value("${kakao.restApiKey}")
    private String kakaoRestApiKey;
    private final NaverRestApiSender naverRestApiSender;

    public KaKaoRestApiSender(RestTemplate restTemplate, NaverRestApiSender naverRestApiSender) {
        super(restTemplate);
        this.naverRestApiSender = naverRestApiSender;
    }

    @Override
    public PagingResponse<BlogResponse> getBlog(String query, SortType sortType, Integer page, Integer size) {
        URI blogUrl = KaKaoUrlBuilder.getBlogUrl(kakaoSearchDomain, query, sortType, page, size);
        PagingResponse<BlogResponse> blogResponse;
        try {
            KaKaoBlogSearchResponse response = send(blogUrl, KaKaoBlogSearchResponse.class, HttpMethod.GET, new HttpEntity<>(defaultHeaders()));
            blogResponse = PagingResponse.of(response.getTotalElements(), page, size, response.convertToSearchApiResponse());
        }catch (Exception e){
            //웹훅
            log.info("카카오 블로그 검색 실패: "+ e.getMessage());
            blogResponse = naverRestApiSender.getBlog(query,sortType,page,size);
        }

        return blogResponse;
    }

    @Override
    protected HttpHeaders defaultHeaders() {
        HttpHeaders headers = super.defaultHeaders();
        headers.set("Authorization", kakaoRestApiKey);
        return headers;
    }
}
