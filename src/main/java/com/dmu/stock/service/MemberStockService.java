package com.dmu.stock.service;

import com.dmu.stock.dto.StockRequestDto;
import com.dmu.stock.dto.StockResponseDto;
import com.dmu.stock.entity.Member;
import com.dmu.stock.entity.UserStock;
import com.dmu.stock.exception.CustomException;
import com.dmu.stock.exception.ErrorType;
import com.dmu.stock.repository.MemberRepository;
import com.dmu.stock.repository.MemberStockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberStockService {
    private final MemberStockRepository memberStockRepository;
    private final MemberRepository memberRepository;

    /**
     * member별 관심종목 등록
     * @param request
     * @return
     */
    @Transactional
    public StockResponseDto saveMemberStock(StockRequestDto request){
        //유저 아이디로 유저 찾고 없으면 새로 등록
        Member member = memberRepository.findByMemberId(request.getMemberId())
                .orElseGet(() -> {
                    try{
                        Member newMember = Member.builder()
                                .memberId(request.getMemberId())
                                .memberName(request.getMemberName())
                                .build();
                        return memberRepository.save(newMember);
                    } catch (Exception e) {
                        throw new CustomException(ErrorType.DATABASE_ERROR);
                    }
                });
        UserStock userStock = UserStock.builder()
                .stockCode(request.getStockCode())
                .avgPrice(request.getAvgPrice())
                .quantity(request.getQuantity())
                .member(member)
                .build();
        UserStock saveStock = memberStockRepository.save(userStock);

        return StockResponseDto.builder()
                .stockCode(saveStock.getStockCode())
                .avgPrice(saveStock.getAvgPrice())
                .quantity(saveStock.getQuantity())
                .totalAmount(saveStock.getAvgPrice() * saveStock.getQuantity())
                .message("종목 등록이 완료되었습니다.")
                .build();
    }

    /**
     * 관심 종목 조회
     * @param memberId
     * @return
     */
    @Transactional
    public List<StockResponseDto> getMemberStock(String memberId){
        List<UserStock> getStock = memberStockRepository.findByMemberId(memberId);
//                .orElseThrow(() -> new CustomException(ErrorType.STOCK_NOT_FOUND));

        return getStock.stream()
                .map(stock -> StockResponseDto.builder()
                        .stockCode(stock.getStockCode())
                        .avgPrice(stock.getAvgPrice())
                        .quantity(stock.getQuantity())
                        .totalAmount(stock.getAvgPrice() * stock.getQuantity())
                        .build())
                .toList();

    }
}
