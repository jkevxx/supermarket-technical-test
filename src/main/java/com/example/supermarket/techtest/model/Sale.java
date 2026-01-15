package com.example.supermarket.techtest.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "DATE")
    private LocalDate date;
    private String status;
    private Double total;

    @ManyToOne
    private Office office; // this is the foreign key

    @OneToMany(mappedBy = "sale")
    private List<SalesDetail> detail = new ArrayList<>();
}
