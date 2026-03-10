package com.example.supermarket.techtest.dto;

import com.example.supermarket.techtest.dto.deserializer.SaleDetailDtoDeserializerModulated;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonDeserialize(using = SaleDetailDtoDeserializerModulated.class)
public class SalesDetailDTO {
    private Long id;
    private String productName;
    private Integer amountProd;
    private Double price;
    private Double subtotal;
}
