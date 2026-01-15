package com.example.supermarket.techtest.controller;

import com.example.supermarket.techtest.dto.OfficeDTO;
import com.example.supermarket.techtest.service.IOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OfficeController {

    @Autowired
    private IOfficeService officeService;

    @GetMapping("/offices")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OfficeDTO>> getOffices(){
        try {
            return ResponseEntity.ok(officeService.getOffices());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/office")
    @ResponseStatus(value = HttpStatus.CREATED, reason = "Office created")
    public ResponseEntity<OfficeDTO> createOffice(@RequestBody OfficeDTO officeDTO){
        try {
            return ResponseEntity.ok(officeService.createOffice(officeDTO));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/office/{id}")
    @ResponseStatus(value = HttpStatus.OK, reason = "Office updated")
    public ResponseEntity<OfficeDTO> updateOffice(@PathVariable Long id, @RequestBody OfficeDTO officeDTO){
        try {
            return ResponseEntity.ok(officeService.updateOffice(id, officeDTO));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/office/{id}")
    @ResponseStatus(value = HttpStatus.OK, reason = "Product deleted")
    public ResponseEntity<OfficeDTO> deleteOffice(@PathVariable Long id){
        try {
            officeService.deleteOffice(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
