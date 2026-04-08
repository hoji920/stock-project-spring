package com.dmu.stock.client.naver;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsAnalysisRequestDto {
    private String query;
    private List<String> urls;
}
