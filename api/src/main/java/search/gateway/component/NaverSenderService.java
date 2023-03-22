package search.gateway.component;

import search.gateway.controller.v1.response.SearchApiResponse;
import search.gateway.controller.v1.response.NaverBlogSearchResponse;
import search.gateway.utils.NaverUrlBuilder;
import search.response.PagingResponse;
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
import search.enums.SortType;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
@Qualifier("naver")
public class NaverSenderService implements SearchSenderService {

    @Value("${naver.domain}")
    private String naverSearchDomain;
    @Value("${naver.clientId}")
    private String naverClientId;
    @Value("${naver.clientSecret}")
    private String naverClientSecret;

    private final RestTemplate restTemplate;

    @Override
    public PagingResponse<SearchApiResponse> getBlog(String query, SortType sortType, Integer page, Integer size) {
        URI blogUrl = NaverUrlBuilder.getBlogUrl(naverSearchDomain, query, sortType, page, size);
        PagingResponse<SearchApiResponse> searchApiResponse;

        NaverBlogSearchResponse response = send(blogUrl, NaverBlogSearchResponse.class, HttpMethod.GET, new HttpEntity<>(defaultNaverHeaders()));
        response.validate();
        searchApiResponse = PagingResponse.of(response.getTotal(),page, size, response.convertToSearchApiResponse());
        return searchApiResponse;
    }
    private <T> T send(URI requestUrl, Type responseType, HttpMethod httpMethod, HttpEntity entity){
        ResponseEntity<String> response = restTemplate.exchange(requestUrl, httpMethod, entity,
                String.class);

        return GsonHelper.fromJson(response.getBody(), responseType);
    }

    private HttpHeaders defaultNaverHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.ALL));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Naver-Client-Id", naverClientId);
        headers.set("X-Naver-Client-Secret", naverClientSecret);
        return headers;
    }

}
