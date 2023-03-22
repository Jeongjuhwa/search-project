package search.gateway.controller.v1.response;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Getter;
import search.enums.NaverErrorCode;
import search.exception.ApiException;
import search.exception.ErrorCode;
import search.gateway.controller.v1.response.SearchApiResponse.Data;


@Getter
public class NaverBlogSearchResponse {
    private String errorMessage;
    private NaverErrorCode errorCode;
    private String lastBuildDate;
    private Integer total;
    private Integer start;
    private Integer display;
    private List<Items> items;

    public SearchApiResponse convertToSearchApiResponse() {
        List<Data> dataList = items.stream()
                .map(i -> Data.of(i.getTitle(), i.getDescription(), i.getLink(), i.bloggername,
                        i.postdate)).collect(
                        Collectors.toList());
        return SearchApiResponse.of(total, dataList);
    }

    public void validate() {
        if(Objects.nonNull(errorCode)){
            switch (this.errorCode){
                case SE01:
                case SE02:
                case SE03:
                case SE04:
                case SE06:
                    throw ApiException.of(ErrorCode.BAD_REQUEST, errorCode.getMessage());
                case SE05:
                    throw ApiException.of(ErrorCode.NOT_FOUNT, errorCode.getMessage());
                default:
                    throw ApiException.of(ErrorCode.INTERNAL_SERVER_ERROR);
            }
        }
    }


    @Getter
    static class Items{
        private String title;
        private String link;
        private String description;
        private String bloggername;
        private String bloggerlink;
        private String postdate;
    }
}
