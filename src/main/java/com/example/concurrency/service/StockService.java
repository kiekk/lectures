package com.example.concurrency.service;

import com.example.concurrency.domain.Stock;
import com.example.concurrency.repository.StockRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    /*
    synchronized 키워드를 사용하여 동시성 문제 해결
    @Transactional 이 선언되어 있을 경우에는 synchronized 를 사용해도 에러 발생
     */
//    @Transactional
    public synchronized void decrease(Long id, Long quantity) {
        /*
        1. Stock 가져오기
        2. 재고 감소
        3. 저장
         */

        Stock stock = stockRepository.findById(id).orElseThrow();

        stock.decrease(quantity);

        stockRepository.saveAndFlush(stock);
    }
}
