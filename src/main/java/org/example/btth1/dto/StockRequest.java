package org.example.btth1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockRequest {

    @NotBlank(message = "SKU không được để trống")
    private String sku;

    @Positive(message = "Số lượng phải là số dương (> 0)")
    private Integer quantity;
}