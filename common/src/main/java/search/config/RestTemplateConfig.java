package search.config;

import com.google.common.collect.Lists;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;


@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {
    private static final Integer READ_TIME_OUT = 300000;
    private static final Integer CONNECTION_TIME_OUT = 10000;


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.requestFactory(HttpComponentsClientHttpRequestFactory::new).build();
    }
    @Bean
    public RestTemplateBuilder restTemplateBuilder(RestTemplateBuilderConfigurer configurer) {
        return configurer.configure(new RestTemplateBuilder()).messageConverters(getMessageConverters())
                .setConnectTimeout(Duration.ofMillis(CONNECTION_TIME_OUT)).setReadTimeout(Duration.ofMillis(READ_TIME_OUT));
    }

    private List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = Lists.newArrayList();
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        stringHttpMessageConverter.setWriteAcceptCharset(false);
        messageConverters.add(stringHttpMessageConverter);
        return messageConverters;
    }

}
