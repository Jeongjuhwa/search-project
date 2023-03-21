package search.gateway.controller.v1.response;

import java.util.List;
import lombok.Getter;

@Getter
public class SearchApiResponse {

    private long totalElements;
    private List<Data> data;

    public static SearchApiResponse of(long totalElements, List<Data> data){
        SearchApiResponse instance = new SearchApiResponse();
        instance.totalElements = totalElements;
        instance.data =data;
        return instance;
    }


    @Getter
    static class Data{
        private String title;
        private String contents;
        private String url;
        private String blogName;
        private String dateTime;

        public static Data of(String title, String contents, String url, String blogName, String dateTime){
            Data instance = new Data();
            instance.title = title;
            instance.contents = contents;
            instance.url = url;
            instance.blogName =blogName;
            instance.dateTime =dateTime;
            return instance;
        }
    }


}
