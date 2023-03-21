package search.gateway.component;

import search.gateway.controller.v1.response.SearchApiResponse;
import search.enums.SortType;

public interface SearchSenderService {

    SearchApiResponse getBlog(String query, SortType sortType, Integer page, Integer size);

}
