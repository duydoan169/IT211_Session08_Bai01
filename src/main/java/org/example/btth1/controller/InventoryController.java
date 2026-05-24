package org.example.btth1.controller;

import org.example.btth1.dto.InventoryReport;
import org.example.btth1.dto.StockRequest;
import org.example.btth1.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/stock-in")
    public ResponseEntity<String> stockIn(
            @RequestHeader("X-User") String username,
            @RequestHeader("X-Role") String role,
            @Valid @RequestBody StockRequest request) {

        inventoryService.stockIn(request.getSku(), request.getQuantity(), username, role);
        return ResponseEntity.ok("Nhập kho thành công. SKU: " + request.getSku() + ", Số lượng: +" + request.getQuantity());
    }

    @PostMapping("/stock-out")
    public ResponseEntity<String> stockOut(
            @RequestHeader("X-User") String username,
            @RequestHeader("X-Role") String role,
            @Valid @RequestBody StockRequest request) {

        inventoryService.stockOut(request.getSku(), request.getQuantity(), username, role);
        return ResponseEntity.ok("Xuất kho thành công. SKU: " + request.getSku() + ", Số lượng: -" + request.getQuantity());
    }

    @GetMapping("/inspect")
    public ResponseEntity<InventoryReport> inspect(
            @RequestHeader("X-User") String username,
            @RequestHeader("X-Role") String role) {

        return ResponseEntity.ok(inventoryService.inspectInventory(username, role));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @RequestHeader("X-User") String username,
            @RequestHeader("X-Role") String role,
            @PathVariable Long id) {

        inventoryService.deleteProduct(id, username, role);
        return ResponseEntity.ok("Xóa sản phẩm thành công. ID: " + id);
    }
}
