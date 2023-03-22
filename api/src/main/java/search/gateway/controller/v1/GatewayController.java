package search.gateway.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import search.gateway.controller.v1.response.SearchApiResponse;
import search.gateway.service.GatewayService;
import search.enums.ExternalName;
import search.enums.SortType;
import search.response.PagingResponse;

@RestController
@RequestMapping("/api/v1/gateway")
@RequiredArgsConstructor
public class GatewayController {
    private final GatewayService gatewayService;

    @GetMapping(value = "/blog")
    public ResponseEntity<PagingResponse<SearchApiResponse>> getBlog(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "ACCURACY") SortType sortType,
            @RequestParam(required = false, defaultValue = "KAKAO") ExternalName externalName,
            @RequestParam(required = false, defaultValue = "1")Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ){

        return ResponseEntity.ok(gatewayService.getBlog(query, sortType, externalName, page,size));
    }

}
