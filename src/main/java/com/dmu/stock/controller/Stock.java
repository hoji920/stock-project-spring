package com.dmu.stock.controller;

import com.dmu.stock.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/stock")
public class Stock {

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getStockInfo(@PathVariable String stockCode){
        return null;
    }
}
