package search.gateway.controller.v1.response;

import java.util.List;
import lombok.Getter;

@Getter
//사용하는 곳 없음
public class SearchResponse<T> {

    private MetaInfo meta;
    private List<T> data;

    public static <T> SearchResponse<T> of(MetaInfo meta, List<T> data){
        SearchResponse<T> searchResponse = new SearchResponse();
        searchResponse.meta = meta;
        searchResponse.data =data;
        return searchResponse;
    }


    @Getter
    static class MetaInfo{
        private Integer totalElements;
        private Integer pageableCount;
        private Boolean isEnd;
        public static MetaInfo of(Integer totalElements, Integer pageableCount, Boolean isEnd){
            MetaInfo instance = new MetaInfo();
            instance.totalElements =totalElements;
            instance.pageableCount =pageableCount;
            instance.isEnd = isEnd;
            return instance;
        }

    }

}
