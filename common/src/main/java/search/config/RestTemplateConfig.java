package search.config;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;


@Configuration
public class RestTemplateConfig {

    private final int KEEP_ALIVE_DURATION = 2 * 1000;
    private final Integer maxTotal = 200;
    private final Integer defaultMaxPerRoute = 50;
    private final Integer readTimeout = 300000;
    private final Integer connectionTimeout = 10000;

    @Bean
    @Order(-1)
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder, HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory) {
        return restTemplateBuilder.requestFactory(() -> httpComponentsClientHttpRequestFactory).build();
    }

    @Bean
    @Order(-1)
    public RestTemplateBuilder restTemplateBuilder(RestTemplateBuilderConfigurer configurer) {
        return configurer.configure(new RestTemplateBuilder()).messageConverters(getMessageConverters())
                .setConnectTimeout(Duration.ofMillis(connectionTimeout)).setReadTimeout(Duration.ofMillis(readTimeout));
    }

    @Bean
    @Order(-1)
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory(final CloseableHttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClient);
        clientHttpRequestFactory.setConnectionRequestTimeout(readTimeout);
        return clientHttpRequestFactory;
    }

    @Bean
    @Order(-1)
    public CloseableHttpClient httpClient(final PoolingHttpClientConnectionManager connManager, final RequestConfig requestConfig) {
        return HttpClients.custom()
                .setConnectionManager(connManager)
                .evictIdleConnections(3L, TimeUnit.SECONDS)
                .setDefaultRequestConfig(requestConfig)
                .setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {
                    @Override
                    public long getKeepAliveDuration(HttpResponse response, HttpContext context) {

                        HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(
                                HTTP.CONN_KEEP_ALIVE));
                        while (it.hasNext()) {
                            HeaderElement he = it.nextElement();
                            String param = he.getName();
                            String value = he.getValue();
                            if (value != null && "timeout".equalsIgnoreCase(param)) {
                                try {
                                    return Long.parseLong(value) * 1000;
                                } catch (NumberFormatException ignore) {
                                }
                            }
                        }
                        return KEEP_ALIVE_DURATION;
                    }
                })
                .disableCookieManagement()
                .build();
    }

    @Bean
    @Order(-1)
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .register("http", PlainConnectionSocketFactory.getSocketFactory()).build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connectionManager.setMaxTotal(maxTotal);
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        connectionManager.closeIdleConnections(3000L, TimeUnit.MILLISECONDS);
        connectionManager.setDefaultSocketConfig(SocketConfig.custom()
                .setTcpNoDelay(true)
                .setSoKeepAlive(true)
                .setSoLinger(200)
                .setSoReuseAddress(true)
                .setSoTimeout(readTimeout)
                .build()
        );
        return connectionManager;
    }

    @Bean
    @Order(-1)
    public RequestConfig requestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setConnectionRequestTimeout(connectionTimeout)
                .setSocketTimeout(readTimeout)
                .build();
    }

    private List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(
                StandardCharsets.UTF_8);
        stringHttpMessageConverter.setWriteAcceptCharset(false);
        messageConverters.add(stringHttpMessageConverter);
        // org.springframework.util.LinkedMultiValueMap 관련 처리
        FormHttpMessageConverter converter = new FormHttpMessageConverter();
        converter.setSupportedMediaTypes(List.of(
                MediaType.APPLICATION_FORM_URLENCODED // 카페24
                , MediaType.MULTIPART_FORM_DATA // 카카오
                , MediaType.APPLICATION_JSON
        ));
        messageConverters.add(converter);
        return messageConverters;
    }

}
