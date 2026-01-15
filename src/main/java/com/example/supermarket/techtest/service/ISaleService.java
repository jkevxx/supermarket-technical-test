package com.example.supermarket.techtest.service;

import com.example.supermarket.techtest.dto.SaleDTO;

import java.util.List;

public interface ISaleService {

    List<SaleDTO> getSales();
    SaleDTO createSale(SaleDTO saleDTO);
    SaleDTO updateSale(Long id, SaleDTO saleDTO);
    void deleteSale(Long id);
}
