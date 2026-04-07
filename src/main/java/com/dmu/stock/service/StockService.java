package com.dmu.stock.service;

import com.dmu.stock.client.hantu.HantuClient;
import com.dmu.stock.client.hantu.HantuDto;
import com.dmu.stock.client.naver.NaverNewsClient;
import com.dmu.stock.client.naver.NaverNewsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockService {

    private final HantuClient hantuClient;
    private final AuthService authService;
    private final NaverNewsClient naverNewsClient;

    public HantuDto.PriceResponse getStockInfo(String stockCode){
        String validToken = authService.getValidToken();
        HantuDto.PriceResponse stockPrice = hantuClient.getStockPrice(stockCode, validToken);
        return stockPrice;
    }
    public List<String> getNewsByName(String stockCode){
        List<String> urls = naverNewsClient.searchNewsName(stockCode);
        return urls;
    }
    public List<String> getNews(String query, int amount){
        List<String> urls = naverNewsClient.searchNews(query,amount,"sim");
        return urls;
    }
}
