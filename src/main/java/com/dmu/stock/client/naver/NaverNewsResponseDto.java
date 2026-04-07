package com.dmu.stock.client.naver;


import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class NaverNewsResponseDto {
    private String lastBuildDate; // 검색 결과 생성 시간
    private int total;            // 총 검색 결과 개수
    private int start;            // 시작 위치
    private int display;          // 한 번에 표시할 결과 개수
    private List<NaverNewsItem> items; // 실제 뉴스 리스트

    @Getter
    @Setter
    public static class NaverNewsItem {
        private String title;         // 뉴스 제목
        private String originallink;  // 언론사 직접 링크
        private String link;          // 네이버 뉴스 링크
        private String description;   // 뉴스 요약 내용
        private String pubDate;       // 게시 일자
    }
}
