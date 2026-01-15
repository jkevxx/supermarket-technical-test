package com.example.supermarket.techtest.repository;

import com.example.supermarket.techtest.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
