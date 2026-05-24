package org.example.btth1.service;

import org.example.btth1.dto.InventoryReport;
import org.example.btth1.entity.Product;
import org.example.btth1.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ProductRepository productRepository;

    @Transactional
    public void stockIn(String sku, int quantity, String username, String role) {
        int updated = productRepository.increaseQuantityBySku(sku, quantity);
        if (updated == 0) {
            throw new RuntimeException("Không tìm thấy sản phẩm với SKU: " + sku);
        }
    }

    @Transactional
    public void stockOut(String sku, int quantity, String username, String role) {
        int currentQty = productRepository.findQuantityBySku(sku)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với SKU: " + sku));

        if (currentQty < quantity) {
            throw new RuntimeException("Tồn kho không đủ. Hiện có: " + currentQty + ", Yêu cầu: " + quantity);
        }

        productRepository.decreaseQuantityBySku(sku, quantity);
    }

    @Transactional(readOnly = true)
    public InventoryReport inspectInventory(String username, String role) {
        List<Product> products = productRepository.findAll();
        Long totalQty = productRepository.sumAllQuantities();
        Double totalVal = productRepository.sumAllValues();

        return InventoryReport.builder()
                .totalQuantity(totalQty)
                .totalValue(totalVal)
                .productCount(products.size())
                .build();
    }

    @Transactional
    public void deleteProduct(Long id, String username, String role) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy sản phẩm với ID: " + id);
        }
        productRepository.deleteById(id);
    }
}