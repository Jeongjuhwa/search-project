package search.response;

import java.util.List;
import lombok.Getter;

@Getter
public class PagingResponse <T>{

    private long totalElements;
    private int totalPage;
    private int currentPage;
    private int currentSize;
    private List<T> data;

    public static <T>PagingResponse<T> of(long totalElements, int currentPage, int currentSize, List<T> data){
        PagingResponse<T> instance = new PagingResponse<>();
        instance.totalElements = totalElements;
        instance.totalPage = (int) Math.ceil((double) totalElements / (double) currentSize);
        instance.currentPage = currentPage;
        instance.currentSize = currentSize;
        instance.data =data;
        return instance;
    }

}
