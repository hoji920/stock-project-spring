package com.dmu.stock.controller;

import com.dmu.stock.client.hantu.HantuDto;
import com.dmu.stock.client.naver.NaverNewsResponseDto;
import com.dmu.stock.common.ApiResponse;
import com.dmu.stock.common.SuccessType;
import com.dmu.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/{stockCode}")
    public ResponseEntity<ApiResponse<HantuDto.PriceResponse>> getStockInfo(@PathVariable String stockCode){
        HantuDto.PriceResponse stockInfo = stockService.getStockInfo(stockCode);
        return ResponseEntity.ok(ApiResponse.success(SuccessType.INQUERY_SUCCESS,stockInfo));
    }
    @GetMapping("/news/{stockCode}")
    public ResponseEntity<ApiResponse<List<String>>> getStockNews(@PathVariable String stockCode){
        List<String> stockNews = stockService.getNewsByName(stockCode);
        return ResponseEntity.ok(ApiResponse.success(SuccessType.INQUERY_SUCCESS,stockNews));
    }
    @GetMapping("/news/search")
    public ResponseEntity<ApiResponse<List<String>>> getStockNews(@RequestParam String query, @RequestParam int display){
        List<String> stockNews = stockService.getNews(query,display);
        return ResponseEntity.ok(ApiResponse.success(SuccessType.INQUERY_SUCCESS,stockNews));
    }
}
