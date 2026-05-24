package org.example.btth1.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryReport {
    private Long totalQuantity;
    private Double totalValue;
    private int productCount;
}
