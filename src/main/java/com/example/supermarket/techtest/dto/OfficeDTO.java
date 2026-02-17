package com.example.supermarket.techtest.dto;

import com.example.supermarket.techtest.config.deserializer.UniversalTypeDeserializer;
import com.example.supermarket.techtest.dto.deserializer.OfficeDTODeserializerModulated;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@JsonDeserialize(using = UniversalTypeDeserializer.class)
@JsonDeserialize(using = OfficeDTODeserializerModulated.class)
public class OfficeDTO {
    private Long id;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "direction is required")
    private String direction;
}
