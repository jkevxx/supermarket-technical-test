package com.example.supermarket.techtest.controller;

import com.example.supermarket.techtest.dto.ApiResponse;
import com.example.supermarket.techtest.dto.ProductDTO;
import com.example.supermarket.techtest.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/products")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProducts() {
        List<ProductDTO> products = productService.getProducts();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Products retrieved successfully",
                        products
                )
        );
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProduct(@PathVariable Long id){

        ProductDTO productFound = productService.getProduct(id);

        ApiResponse<ProductDTO> response = new ApiResponse<>(
                HttpStatus.FOUND.value(),
                "Product found successfully",
                productFound
        );

        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @PostMapping("/product")
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(@Valid @RequestBody ProductDTO productDTO){

        ProductDTO productCreated = productService.createProduct(productDTO);

        ApiResponse<ProductDTO> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Product created successfully",
                productCreated
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO){

        ProductDTO productUpdated = productService.updateProduct(id, productDTO);

        ApiResponse<ProductDTO> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Product updated successfully",
                productUpdated
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<ApiResponse<?>> deleteProduct(@PathVariable Long id){

        ApiResponse<?> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Product deleted successfully",
                new ArrayList<String>()
        );

            productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
