package com.dmu.stock.service;

import com.dmu.stock.client.hantu.HantuDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    @Value("${hantu.appkey}")
    private String appKey;

    @Value("${hantu.appsecret}")
    private String appSecret;

    private final WebClient webClient;

    private String accessToken; // 메모리에 토큰 저장
    private LocalDateTime expiryTime; // 만료 시간 체크용

    // 다른 서비스들이 호출할 메소드
    public String getValidToken() {
        // 토큰이 없거나, 만료시간이 1분 남았을 때 새로 갱신
        if (accessToken == null || LocalDateTime.now().isAfter(expiryTime.minusMinutes(1))) {
            refreshAccessToken();
        }
        return accessToken;
    }

    private void refreshAccessToken() {
        // 요청 바디(Body) 생성
        Map<String, String> body = new HashMap<>();
        body.put("grant_type", "client_credentials");
        body.put("appkey", appKey);
        body.put("appsecret", appSecret);

        log.info("한투 API 토큰 갱신 중...");

        HantuDto.TokenResponse res = webClient.post()
                .uri("https://openapi.koreainvestment.com:9443/oauth2/tokenP")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(HantuDto.TokenResponse.class)
                .block();

        this.accessToken = res.getAccess_token();
        log.info("토큰 발급 성공! 유효시간: {}초", res.getExpires_in());
        // 유효 시간을 계산해서 저장 (보통 86400초 등)
        this.expiryTime = LocalDateTime.now().plusSeconds(res.getExpires_in());
    }
}