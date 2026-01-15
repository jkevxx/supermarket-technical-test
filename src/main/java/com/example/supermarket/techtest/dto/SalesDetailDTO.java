package com.example.supermarket.techtest.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesDetailDTO {
    private Long id;
    private String productName;
    private Integer amountProd;
    private Double price;
    private Double subtotal;
}
