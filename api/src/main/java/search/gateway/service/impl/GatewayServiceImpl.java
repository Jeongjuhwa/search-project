package search.gateway.service.impl;

import search.gateway.component.RestApiSenderSelector;
import search.gateway.component.RestApiSender;
import search.gateway.controller.v1.response.BlogResponse;
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

    private final RestApiSenderSelector restApiSenderSelector;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional(readOnly = true)
    public PagingResponse<BlogResponse> getBlog(String query, SortType sortType, ExternalName externalName, Integer page,
            Integer size) {

        RestApiSender restApiSender = restApiSenderSelector.select(externalName);

        applicationEventPublisher.publishEvent(new BlogSelectedEvent(query));

        return restApiSender.getBlog(query, sortType, page, size);
    }

}
