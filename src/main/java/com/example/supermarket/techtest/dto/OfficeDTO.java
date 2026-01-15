package com.example.supermarket.techtest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficeDTO {
    private Long id;
    private String name;
    private String direction;
}
