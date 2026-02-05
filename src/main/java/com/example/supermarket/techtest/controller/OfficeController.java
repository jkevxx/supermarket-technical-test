package com.example.supermarket.techtest.controller;

import com.example.supermarket.techtest.dto.ApiResponse;
import com.example.supermarket.techtest.dto.OfficeDTO;
import com.example.supermarket.techtest.service.IOfficeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OfficeController {

    @Autowired
    private IOfficeService officeService;

    @GetMapping("/offices")
    public ResponseEntity<ApiResponse<List<OfficeDTO>>> getOffices(){

        List<OfficeDTO> offices = officeService.getOffices();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Offices retrieved successfully",
                        offices
                )
        );
    }

    @GetMapping("/office/{id}")
    public ResponseEntity<ApiResponse<OfficeDTO>> getOffice(@PathVariable Long id){

        OfficeDTO officeFound = officeService.getOffice(id);

        ApiResponse<OfficeDTO> response = new ApiResponse<>(
                HttpStatus.FOUND.value(),
                "Office found successfully",
                officeFound
        );

        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @PostMapping("/office")
    public ResponseEntity<ApiResponse<OfficeDTO>> createOffice(@Valid @RequestBody OfficeDTO officeDTO){

        OfficeDTO officeCreated = officeService.createOffice(officeDTO);

        ApiResponse<OfficeDTO> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Office Created",
                officeCreated
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/office/{id}")
    public ResponseEntity<ApiResponse<OfficeDTO>> updateOffice(@PathVariable Long id, @Valid @RequestBody OfficeDTO officeDTO){

        OfficeDTO officeUpdated = officeService.updateOffice(id, officeDTO);

        ApiResponse<OfficeDTO> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Office Updated",
                officeUpdated
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/office/{id}")
    public ResponseEntity<ApiResponse<?>> deleteOffice(@PathVariable Long id){

        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Office Deleted",
                new ArrayList<String>()
        );

        officeService.deleteOffice(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
