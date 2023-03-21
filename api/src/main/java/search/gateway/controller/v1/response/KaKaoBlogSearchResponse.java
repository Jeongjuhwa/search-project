package search.gateway.controller.v1.response;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import search.gateway.controller.v1.response.SearchApiResponse.Data;


@Getter
public class KaKaoBlogSearchResponse {

    private Meta meta;
    private List<Documents> documents;

    public SearchApiResponse convertToSearchApiResponse() {
        List<Data> dataList = documents.stream()
                .map(d -> Data.of(d.getTitle(), d.getContents(), d.getUrl(), d.getBlogname(),
                        d.datetime)).collect(
                        Collectors.toList());
        return SearchApiResponse.of(meta.getPageable_count(), dataList);
    }


    @Getter
    static class Meta{
        private Integer total_count;
        private Integer pageable_count;
        private Boolean is_end;
    }
    @Getter
    static class Documents{
        private String title;
        private String contents;
        private String url;
        private String blogname;
        private String thumbnail;
        private String datetime;
    }

}
