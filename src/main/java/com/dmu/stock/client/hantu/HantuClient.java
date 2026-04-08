package com.dmu.stock.client.hantu;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class HantuClient {
    private final WebClient webClient;

    @Value("${hantu.appkey}")
    String appKey;
    @Value("${hantu.appsecret}")
    String appSecret;

    // 현재가 조회하기 (삼성전자 종목번호: 005930)
    public HantuDto.PriceResponse getStockPrice(String stockCode, String token) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("openapi.koreainvestment.com")
                        .port(9443)
                        .path("/uapi/domestic-stock/v1/quotations/inquire-price")
                        .queryParam("FID_COND_SCRN_NO", "0000")
                        .queryParam("FID_INPUT_ISCD", stockCode)
                        .queryParam("FID_COND_MRKT_DIV_CODE", "J")
                        .build())
                .header("Content-Type","application/json; charset=utf-8")
                .header("authorization", "Bearer " + token)
                .header("appkey", appKey)
                .header("appsecret", appSecret)
                .header("tr_id", "FHKST01010100") // 현재가 조회용 ID
                .header("custtype", "P") //개인 회원
                .retrieve()
                .bodyToMono(HantuDto.PriceResponse.class)
                .block();
    }
}
