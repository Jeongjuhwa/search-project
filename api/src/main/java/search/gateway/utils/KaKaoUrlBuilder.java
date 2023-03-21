package search.gateway.utils;


import java.net.URI;
import org.springframework.web.util.UriComponentsBuilder;
import search.enums.SortType;

public class KaKaoUrlBuilder {
    private final static String kakaoBlogSearchPath = "/v2/search/blog";

    public static URI getBlogUrl(String domain, String query, SortType sortType, Integer page, Integer size){
        return UriComponentsBuilder.fromHttpUrl(domain)
                .path(kakaoBlogSearchPath)
                .queryParam("query", query)
                .queryParam("sort", sortType.convertToKakaoSortType())
                .queryParam("page", page)
                .queryParam("size", size)
                .build().toUri();
    }

}
