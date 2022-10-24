package com.pool.model.resume;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteItem {
    private String description;
    private Integer quantity;
    private Double unitPrice;
    private Double total;
}
