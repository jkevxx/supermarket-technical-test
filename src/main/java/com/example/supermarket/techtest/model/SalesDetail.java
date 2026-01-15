package com.example.supermarket.techtest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SalesDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Sale sale; // this is the foreign key

    @ManyToOne
    private Product product; // this is the foreign key

    private Integer amountProd;

    private Double price;
}
