package com.dmu.stock.service;

import com.dmu.stock.client.hantu.HantuClient;
import com.dmu.stock.client.hantu.HantuDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockService {

    private final HantuClient hantuClient;
    private final AuthService authService;

    public HantuDto.PriceResponse getStockInfo(String stockCode){
        String validToken = authService.getValidToken();
        HantuDto.PriceResponse stockPrice = hantuClient.getStockPrice(stockCode, validToken);
        return stockPrice;
    }
}
