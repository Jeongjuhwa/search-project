package search.gateway.component;

import search.gateway.controller.v1.response.BlogResponse;
import search.gateway.controller.v1.response.NaverBlogSearchResponse;
import search.gateway.utils.NaverUrlBuilder;
import search.response.PagingResponse;
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
import search.enums.SortType;
import search.support.RestApiSenderSupporter;

@Component
@Transactional
@Slf4j
@Qualifier("naver")
public class NaverRestApiSender extends RestApiSenderSupporter implements RestApiSender {

    @Value("${naver.domain}")
    private String naverSearchDomain;
    @Value("${naver.clientId}")
    private String naverClientId;
    @Value("${naver.clientSecret}")
    private String naverClientSecret;

    public NaverRestApiSender(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public PagingResponse<BlogResponse> getBlog(String query, SortType sortType, Integer page, Integer size) {
        URI blogUrl = NaverUrlBuilder.getBlogUrl(naverSearchDomain, query, sortType, page, size);
        PagingResponse<BlogResponse> blogResponse;

        NaverBlogSearchResponse response = send(blogUrl, NaverBlogSearchResponse.class, HttpMethod.GET, new HttpEntity<>(defaultHeaders()));
        response.validate();
        blogResponse = PagingResponse.of(response.getTotal(),page, size, response.convertToSearchApiResponse());
        return blogResponse;
    }

    @Override
    protected HttpHeaders defaultHeaders() {
        HttpHeaders headers = super.defaultHeaders();
        headers.set("X-Naver-Client-Id", naverClientId);
        headers.set("X-Naver-Client-Secret", naverClientSecret);
        return headers;
    }
}
