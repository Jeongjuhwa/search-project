package search.gateway.component;

import search.gateway.controller.v1.response.BlogResponse;
import search.enums.SortType;
import search.response.PagingResponse;

public interface RestApiSender {

    PagingResponse<BlogResponse> getBlog(String query, SortType sortType, Integer page, Integer size);

}
