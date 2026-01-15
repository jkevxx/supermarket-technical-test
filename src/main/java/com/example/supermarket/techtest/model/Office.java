package com.example.supermarket.techtest.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Office {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String direction;

    @OneToMany(mappedBy = "office", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sale> sales;
}
