package com.example.supermarket.techtest.service;

import com.example.supermarket.techtest.dto.SaleDTO;
import com.example.supermarket.techtest.dto.SalesDetailDTO;
import com.example.supermarket.techtest.exception.NotFoundException;
import com.example.supermarket.techtest.mapper.Mapper;
import com.example.supermarket.techtest.model.Office;
import com.example.supermarket.techtest.model.Product;
import com.example.supermarket.techtest.model.Sale;
import com.example.supermarket.techtest.model.SalesDetail;
import com.example.supermarket.techtest.repository.OfficeRepository;
import com.example.supermarket.techtest.repository.ProductRepository;
import com.example.supermarket.techtest.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService implements ISaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OfficeRepository officeRepository;

    @Override
    public List<SaleDTO> getSales() {
        return saleRepository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public SaleDTO createSale(SaleDTO saleDTO) {

        // validations
        if (saleDTO == null) throw new RuntimeException("SaleDTO is null");
        if (saleDTO.getIdOffice() == null) throw new RuntimeException("The office is missing");
        if (saleDTO.getDetails() == null || saleDTO.getDetails().isEmpty())
            throw new RuntimeException("It must have one product");

        // Searching office
        Office office = officeRepository.findById(saleDTO.getIdOffice()).orElse(null);

        if (office == null)
            throw new NotFoundException("Office not found");

        // creating sale
        Sale sale = new Sale();
        sale.setDate(saleDTO.getDate());
        sale.setStatus(saleDTO.getStatus());
        sale.setOffice(office);
        sale.setTotal(sale.getTotal());


        // Details list
        // here are the products
        List<SalesDetail> salesDetailList = new ArrayList<>();

        for (SalesDetailDTO salesDetailDTO : saleDTO.getDetails()) {
            Product product = productRepository.findByName(salesDetailDTO.getProductName()).orElse(null);
            if (product == null) throw new RuntimeException("Product not found " + salesDetailDTO.getProductName());

            SalesDetail salesDetail = new SalesDetail();
            salesDetail.setSale(sale);
            salesDetail.setProduct(product);
            salesDetail.setAmountProd(salesDetailDTO.getAmountProd());
            salesDetail.setPrice(salesDetailDTO.getPrice());

            salesDetailList.add(salesDetail);
        }

        sale.setDetail(salesDetailList);

        return Mapper.toDTO(saleRepository.save(sale));
    }

    @Override
    public SaleDTO updateSale(Long id, SaleDTO saleDTO) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sale not found"));

        sale.setDate(saleDTO.getDate());
        sale.setStatus(saleDTO.getStatus());
        sale.setTotal(sale.getTotal());

        Office office = officeRepository.findById(saleDTO.getIdOffice()).orElse(null);
        if (office == null) throw new NotFoundException("Office not found");

        sale.setOffice(office);

        return Mapper.toDTO(saleRepository.save(sale));
    }

    @Override
    public void deleteSale(Long id) {
        if (!saleRepository.existsById(id)) {
            throw new NotFoundException("Sale not found");
        }

        saleRepository.deleteById(id);
    }
}
