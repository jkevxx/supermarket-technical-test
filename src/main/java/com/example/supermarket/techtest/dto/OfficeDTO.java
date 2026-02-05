package com.example.supermarket.techtest.dto;

import com.example.supermarket.techtest.config.deserializer.UniversalTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonDeserialize(using = UniversalTypeDeserializer.class)
public class OfficeDTO {
    private Long id;

    @NotBlank(message = "Office name is required")
    private String name;

    @NotBlank(message = "Office direction is required")
    private String direction;
}
