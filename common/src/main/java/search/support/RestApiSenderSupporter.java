package search.support;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public abstract class RestApiSenderSupporter {

    private final RestTemplate restTemplate;

    protected  <T> T send(URI requestUrl, Type responseType, HttpMethod httpMethod, HttpEntity entity){
        ResponseEntity<String> response = restTemplate.exchange(requestUrl, httpMethod, entity,
                String.class);

        return GsonHelper.fromJson(response.getBody(), responseType);
    }

    protected HttpHeaders defaultHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.ALL));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
