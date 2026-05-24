package org.example.btth1.aspect;

import org.example.btth1.entity.InventoryLog;
import org.example.btth1.repository.InventoryLogRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final InventoryLogRepository inventoryLogRepository;

    @AfterReturning("execution(* org.example.btth1.service.InventoryService.stockIn(..)) && args(sku, quantity, username, role)")
    public void logStockIn(String sku, int quantity, String username, String role) {
        String detail = "[" + LocalDateTime.now() + "] - User: " + username + " performed STOCK_IN successfully. Quantity changed: +" + quantity;
        saveLog(username, "STOCK_IN", detail);
    }

    @AfterReturning("execution(* org.example.btth1.service.InventoryService.stockOut(..)) && args(sku, quantity, username, role)")
    public void logStockOut(String sku, int quantity, String username, String role) {
        String detail = "[" + LocalDateTime.now() + "] - User: " + username + " performed STOCK_OUT successfully. Quantity changed: -" + quantity;
        saveLog(username, "STOCK_OUT", detail);
    }

    private void saveLog(String username, String action, String detail) {
        InventoryLog log = InventoryLog.builder()
                .timestamp(LocalDateTime.now())
                .username(username)
                .action(action)
                .detail(detail)
                .build();
        inventoryLogRepository.save(log);
    }
}