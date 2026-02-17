package com.example.supermarket.techtest.dto;

import com.example.supermarket.techtest.config.deserializer.UniversalTypeDeserializer;
import com.example.supermarket.techtest.dto.deserializer.ProductDTODeserializer;
import com.example.supermarket.techtest.dto.deserializer.ProductDTODeserializerModulated;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@JsonDeserialize(using = ProductDTODeserializer.class)
//@JsonDeserialize(using = UniversalTypeDeserializer.class)
@JsonDeserialize(using = ProductDTODeserializerModulated.class)
public class ProductDTO {
    private Long id;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "category is required")
    private String category;

    @NotNull(message = "price is required")
    @Positive(message = "price must be greater than 0")
    private Double price;

    @Min(value = 0, message = "amount cannot be negative")
    @NotNull(message = "amount is required")
    private Integer amount;
}
