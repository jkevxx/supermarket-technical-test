package com.example.supermarket.techtest.controller;

import com.example.supermarket.techtest.dto.ApiResponse;
import com.example.supermarket.techtest.dto.ProductDTO;
import com.example.supermarket.techtest.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

//    @GetMapping("/product/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<ProductDTO> getProduct(){
//        try {
//            ResponseEntity.ok(productService)
//        }
//    }

    @PostMapping("/product")
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(@Valid @RequestBody ProductDTO productDTO){

        ProductDTO createdProduct = productService.createProduct(productDTO);

        ApiResponse<ProductDTO> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Product created successfully",
                createdProduct
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("/product/{id}")
    @ResponseStatus(value = HttpStatus.OK, reason = "Product updated")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
    }

    @DeleteMapping("/product/{id}")
    @ResponseStatus(value = HttpStatus.OK, reason = "Product deleted")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long id){
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
    }

}
