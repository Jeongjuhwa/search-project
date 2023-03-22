package search.gateway.controller.v1.response;

import lombok.Getter;

@Getter
public class BlogResponse {
    private String title;
    private String contents;
    private String url;
    private String blogName;
    private String dateTime;

    public static BlogResponse of(String title, String contents, String url, String blogName, String dateTime){
        BlogResponse instance = new BlogResponse();
        instance.title = title;
        instance.contents = contents;
        instance.url = url;
        instance.blogName =blogName;
        instance.dateTime =dateTime;
        return instance;
    }


}
