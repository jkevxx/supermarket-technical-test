package com.example.supermarket.techtest.mapper;

import com.example.supermarket.techtest.dto.OfficeDTO;
import com.example.supermarket.techtest.dto.ProductDTO;
import com.example.supermarket.techtest.dto.SaleDTO;
import com.example.supermarket.techtest.dto.SalesDetailDTO;
import com.example.supermarket.techtest.model.Office;
import com.example.supermarket.techtest.model.Product;
import com.example.supermarket.techtest.model.Sale;

import java.util.stream.Collectors;

public class Mapper {

    public static ProductDTO toDTO(Product p){
        if (p == null) return null;

        return ProductDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .category(p.getCategory())
                .price(p.getPrice())
                .amount(p.getAmount())
                .build();
    }

    public static SaleDTO toDTO(Sale sale){
        if (sale == null) return null;

        var detail = sale.getDetail().stream().map( det ->
                SalesDetailDTO.builder()
                        .id(det.getProduct().getId())
                        .productName(det.getProduct().getName())
                        .amountProd(det.getAmountProd())
                        .price(det.getPrice())
                        .subtotal(det.getPrice() * det.getAmountProd())
                        .build()
        ).collect(Collectors.toList());

        var total = detail.stream()
                .map(SalesDetailDTO::getSubtotal)
                .reduce(0.0, Double::sum);

        return SaleDTO.builder()
                .id(sale.getId())
                .date(sale.getDate())
                .idOffice(sale.getOffice().getId())
                .status(sale.getStatus())
                .details(detail)
                .total(total)
                .build();
    }

    public static OfficeDTO toDTO(Office o){
        if (o == null) return null;

        return OfficeDTO.builder()
                .id(o.getId())
                .name(o.getName())
                .direction(o.getDirection())
                .build();

    }
}
