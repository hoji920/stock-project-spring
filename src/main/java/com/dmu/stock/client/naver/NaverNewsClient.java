package com.dmu.stock.client.naver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class NaverNewsClient {

    private final WebClient webClient;

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    /**
     * 종목명으로 뉴스 검색 후 URL 리스트 반환
     */
    public List<String> searchNewsUrls(String stockName) {
        log.info("네이버 뉴스 검색 API 호출 - 키워드: {}", stockName);

        NaverNewsResponseDto response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("openapi.naver.com")
                        .path("/v1/search/news.json")
                        .queryParam("query", stockName + " 공급망")
                        .queryParam("display", 5) // 5개
                        .queryParam("sort", "sim") // 유사도 순
                        .build())
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .retrieve()
                .bodyToMono(NaverNewsResponseDto.class)
                .block();

        if (response == null || response.getItems() == null) {
            return Collections.emptyList();
        }

        // URL(link)만 리스트로 뽑아서 반환
        return response.getItems().stream()
                .map(NaverNewsResponseDto.NaverNewsItem::getLink)
                .collect(Collectors.toList());
    }
}
