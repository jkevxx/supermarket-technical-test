package com.example.supermarket.techtest.controller;

import com.example.supermarket.techtest.dto.SaleDTO;
import com.example.supermarket.techtest.service.ISaleService;
import com.example.supermarket.techtest.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SaleController {

    @Autowired
    private ISaleService saleService;

    @GetMapping("/sales")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<SaleDTO>> getSales(){
        try {
            return ResponseEntity.ok(saleService.getSales());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/sale")
    @ResponseStatus(value = HttpStatus.OK, reason = "Sale created")
    public ResponseEntity<SaleDTO> createSale(@RequestBody SaleDTO saleDTO){
        try {
            return ResponseEntity.ok(saleService.createSale(saleDTO));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/sale/{id}")
    @ResponseStatus(value = HttpStatus.OK, reason = "Sale Updated")
    public ResponseEntity<SaleDTO> updateSale(@PathVariable Long id, @RequestBody SaleDTO saleDTO){
        try {
            return ResponseEntity.ok(saleService.updateSale(id, saleDTO));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/sale/{id}")
    @ResponseStatus(value = HttpStatus.OK, reason = "Sale Deleted")
    public ResponseEntity<SaleDTO> deleteSale(@PathVariable Long id){
        try {
            saleService.deleteSale(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
