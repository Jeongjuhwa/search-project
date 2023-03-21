package search.gateway.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BlogSelectedEvent {

    private String query;
}
