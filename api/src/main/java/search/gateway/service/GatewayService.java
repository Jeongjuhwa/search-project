package search.gateway.service;

import search.gateway.controller.v1.response.BlogResponse;
import search.enums.ExternalName;
import search.enums.SortType;
import search.response.PagingResponse;

public interface GatewayService {

    PagingResponse<BlogResponse> getBlog(String query, SortType sortType, ExternalName externalName, Integer page, Integer size);

}
