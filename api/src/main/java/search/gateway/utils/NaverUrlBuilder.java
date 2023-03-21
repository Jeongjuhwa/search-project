package search.gateway.utils;

import java.net.URI;
import org.springframework.web.util.UriComponentsBuilder;
import search.enums.SortType;

public class NaverUrlBuilder {

    private final static String naverBlogSearchPath = "/v1/search/blog.json";

    public static URI getBlogUrl(String domain, String query, SortType sortType, Integer page, Integer size){
        return UriComponentsBuilder.fromHttpUrl(domain)
                .path(naverBlogSearchPath)
                .queryParam("query", query)
                .queryParam("sort", sortType.convertTonaverSortType())
                .queryParam("start", page)
                .queryParam("display", size)
                .build().toUri();
    }

}
