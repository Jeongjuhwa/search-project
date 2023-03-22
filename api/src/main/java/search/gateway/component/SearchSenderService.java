package search.gateway.component;

import search.gateway.controller.v1.response.SearchApiResponse;
import search.enums.SortType;
import search.response.PagingResponse;

public interface SearchSenderService {

    PagingResponse<SearchApiResponse> getBlog(String query, SortType sortType, Integer page, Integer size);

}
