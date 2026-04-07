package com.dmu.stock.controller;

import com.dmu.stock.client.hantu.HantuDto;
import com.dmu.stock.common.ApiResponse;
import com.dmu.stock.common.SuccessType;
import com.dmu.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
