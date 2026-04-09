package com.dmu.stock.dto;


import com.dmu.stock.entity.enums.StockType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;


@Getter
@Builder
public class StockResponseDto {
    private String stockCode;   // 저장된 종목
    private double avgPrice;    // 평단가
    private double quantity;    // 수량
    private double totalAmount; // (서비스에서 계산한) 총 매수 금액
    private String message;     // "성공적으로 저장되었습니다" 같은 메시지
}