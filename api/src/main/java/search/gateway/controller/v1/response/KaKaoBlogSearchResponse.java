package search.gateway.controller.v1.response;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;


@Getter
public class KaKaoBlogSearchResponse {

    private Meta meta;
    private List<Documents> documents;

    public long getTotalElements(){
        return this.meta.pageable_count;
    }

    public List<SearchApiResponse> convertToSearchApiResponse() {
        return documents.stream()
                .map(d -> SearchApiResponse.of(d.getTitle(), d.getContents(), d.getUrl(), d.getBlogname(),
                        d.datetime)).collect(
                        Collectors.toList());

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
