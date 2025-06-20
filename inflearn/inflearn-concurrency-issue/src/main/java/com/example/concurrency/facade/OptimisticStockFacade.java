package com.example.concurrency.facade;

import com.example.concurrency.service.OptimisticLockStockService;
import org.springframework.stereotype.Service;

@Service
public class OptimisticStockFacade {

    private final OptimisticLockStockService optimisticLockStockService;

    public OptimisticStockFacade(OptimisticLockStockService optimisticLockStockService) {
        this.optimisticLockStockService = optimisticLockStockService;
    }

    public void decrease(Long id, Long quantity) throws InterruptedException {
        while (true) {
            try {
                optimisticLockStockService.decrease(id, quantity);
                break;
            } catch (Exception e) {
                Thread.sleep(50);
            }
        }
    }
}
