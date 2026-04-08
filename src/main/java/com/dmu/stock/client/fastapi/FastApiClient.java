package com.dmu.stock.client.fastapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class FastApiClient {
    @Bean
    public WebClient fastapiWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8000") // FastAPI 서버 주소
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}
