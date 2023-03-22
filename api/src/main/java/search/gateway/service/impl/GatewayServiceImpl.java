package search.gateway.service.impl;

import search.gateway.component.ExternalSearchChannelSelector;
import search.gateway.component.SearchSenderService;
import search.gateway.controller.v1.response.SearchApiResponse;
import search.gateway.event.BlogSelectedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import search.gateway.service.GatewayService;
import search.enums.ExternalName;
import search.enums.SortType;
import search.response.PagingResponse;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class GatewayServiceImpl implements GatewayService {

    private final ExternalSearchChannelSelector externalSearchChannelSelector;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional(readOnly = true)
    public PagingResponse<SearchApiResponse> getBlog(String query, SortType sortType, ExternalName externalName, Integer page,
            Integer size) {

        SearchSenderService searchSenderService = externalSearchChannelSelector.select(externalName);

        applicationEventPublisher.publishEvent(new BlogSelectedEvent(query));

        return searchSenderService.getBlog(query, sortType, page, size);
    }

}
