package com.example.supermarket.techtest.dto;

import com.example.supermarket.techtest.dto.deserializer.SaleDTODeserializerModulated;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonDeserialize(using = SaleDTODeserializerModulated.class)
public class SaleDTO {

    // sale data
    private Long id;
    private LocalDate date;
    private String status;

    // office data
    private Long idOffice;

    //details list
    private List<SalesDetailDTO> details;

    // sale total
    private Double total;
}
