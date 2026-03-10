package com.example.supermarket.techtest.controller;

import com.example.supermarket.techtest.dto.ApiResponse;
import com.example.supermarket.techtest.dto.SaleDTO;
import com.example.supermarket.techtest.service.ISaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SaleController {

    @Autowired
    private ISaleService saleService;

    @GetMapping("/sales")
    public ResponseEntity<ApiResponse<List<SaleDTO>>> getSales(){
        List<SaleDTO> sales = saleService.getSales();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Sales retrieved successfully",
                        sales
                )
        );
    }

    @GetMapping("/sale/{id}")
    public ResponseEntity<ApiResponse<SaleDTO>> getSale(@PathVariable Long id){
        SaleDTO saleFound = saleService.getSale(id);

        ApiResponse<SaleDTO> response = new ApiResponse<>(
                HttpStatus.FOUND.value(),
                "Sale found successfully",
                saleFound
        );

        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @PostMapping("/sale")
    public ResponseEntity<ApiResponse<SaleDTO>> createSale(@Valid @RequestBody SaleDTO saleDTO){

        SaleDTO saleCreated = saleService.createSale(saleDTO);

        ApiResponse<SaleDTO> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Sale created successfully",
                saleCreated
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/sale/{id}")
    public ResponseEntity<ApiResponse<SaleDTO>> updateSale(@PathVariable Long id, @Valid @RequestBody SaleDTO saleDTO){

        SaleDTO saleUpdated = saleService.updateSale(id, saleDTO);

        ApiResponse<SaleDTO> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Sale updated successfully",
                saleUpdated
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/sale/{id}")
    public ResponseEntity<ApiResponse<?>> deleteSale(@PathVariable Long id){

        saleService.deleteSale(id);

        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Sale deleted successfully",
                new ArrayList<String>()
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
