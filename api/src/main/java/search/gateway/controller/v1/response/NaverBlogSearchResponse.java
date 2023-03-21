package search.gateway.controller.v1.response;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import search.gateway.controller.v1.response.SearchApiResponse.Data;


@Getter
public class NaverBlogSearchResponse {

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
