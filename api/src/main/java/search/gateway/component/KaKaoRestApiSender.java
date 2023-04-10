package search.gateway.component;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
    @CircuitBreaker(name = "circuit-test", fallbackMethod = "fallbackGetBlog")
    public PagingResponse<BlogResponse> getBlog(String query, SortType sortType, Integer page, Integer size) {
        URI blogUrl = KaKaoUrlBuilder.getBlogUrl(kakaoSearchDomain, query, sortType, page, size);
        PagingResponse<BlogResponse> blogResponse;

        KaKaoBlogSearchResponse response = send(blogUrl, KaKaoBlogSearchResponse.class, HttpMethod.GET, new HttpEntity<>(defaultHeaders()));
        blogResponse = PagingResponse.of(response.getTotalElements(), page, size, response.convertToSearchApiResponse());

        return blogResponse;
    }

    private PagingResponse<BlogResponse> fallbackGetBlog(String query, SortType sortType, Integer page, Integer size, Throwable throwable){
        log.info(throwable.getMessage());
        return naverRestApiSender.getBlog(query, sortType, page, size);
    }

    @Override
    protected HttpHeaders defaultHeaders() {
        HttpHeaders headers = super.defaultHeaders();
        headers.set("Authorization", kakaoRestApiKey);
        return headers;
    }
}
