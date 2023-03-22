package search.gateway.component;

import search.gateway.controller.v1.response.KaKaoBlogSearchResponse;
import search.gateway.utils.KaKaoUrlBuilder;
import search.response.PagingResponse;
import search.support.RestTemplateAbstractClass;
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
import search.gateway.controller.v1.response.SearchApiResponse;
import search.enums.SortType;

@Component
@Transactional
@Slf4j
@Qualifier("kakao")
public class KaKaoSenderService extends RestTemplateAbstractClass implements SearchSenderService{

    @Value("${kakao.domain}")
    private String kakaoSearchDomain;
    @Value("${kakao.restApiKey}")
    private String kakaoRestApiKey;
    private final NaverSenderService naverSenderService;

    public KaKaoSenderService(RestTemplate restTemplate, NaverSenderService naverSenderService) {
        super(restTemplate);
        this.naverSenderService = naverSenderService;
    }

    @Override
    public PagingResponse<SearchApiResponse> getBlog(String query, SortType sortType, Integer page, Integer size) {
        URI blogUrl = KaKaoUrlBuilder.getBlogUrl(kakaoSearchDomain, query, sortType, page, size);
        PagingResponse<SearchApiResponse> searchApiResponse;
        try {
            KaKaoBlogSearchResponse response = send(blogUrl, KaKaoBlogSearchResponse.class, HttpMethod.GET, new HttpEntity<>(defaultHeaders()));
            searchApiResponse = PagingResponse.of(response.getTotalElements(), page, size, response.convertToSearchApiResponse());
        }catch (Exception e){
            //웹훅
            log.info("카카오 블로그 검색 실패: "+ e.getMessage());
            searchApiResponse = naverSenderService.getBlog(query,sortType,page,size);
        }

        return searchApiResponse;
    }

    @Override
    protected HttpHeaders defaultHeaders() {
        HttpHeaders headers = super.defaultHeaders();
        headers.set("Authorization", kakaoRestApiKey);
        return headers;
    }
}
