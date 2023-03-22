package search.gateway.service;

import search.gateway.controller.v1.response.SearchApiResponse;
import search.enums.ExternalName;
import search.enums.SortType;
import search.response.PagingResponse;

public interface GatewayService {

    PagingResponse<SearchApiResponse> getBlog(String query, SortType sortType, ExternalName externalName, Integer page, Integer size);

}
