package search.gateway.controller.v1.response;

import lombok.Getter;

@Getter
public class SearchApiResponse {
    private String title;
    private String contents;
    private String url;
    private String blogName;
    private String dateTime;

    public static SearchApiResponse of(String title, String contents, String url, String blogName, String dateTime){
        SearchApiResponse instance = new SearchApiResponse();
        instance.title = title;
        instance.contents = contents;
        instance.url = url;
        instance.blogName =blogName;
        instance.dateTime =dateTime;
        return instance;
    }


}
