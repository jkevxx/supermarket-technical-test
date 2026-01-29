package com.example.supermarket.techtest.service;

import com.example.supermarket.techtest.dto.ProductDTO;
import com.example.supermarket.techtest.exception.NotFoundException;
import com.example.supermarket.techtest.mapper.Mapper;
import com.example.supermarket.techtest.model.Product;
import com.example.supermarket.techtest.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductDTO> getProducts() {
        return productRepository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public ProductDTO getProduct(Long id) {
        Product productFound = productRepository.findById(id)
                .orElseThrow( () -> new NotFoundException("Product not Found"));

        return Mapper.toDTO(productFound);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = Product.builder()
                .name(productDTO.getName())
                .category(productDTO.getCategory())
                .price(productDTO.getPrice())
                .amount(productDTO.getAmount())
                .build();

        return Mapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow( () -> new NotFoundException("Product not Found"));

        product.setName(productDTO.getName());
        product.setCategory(productDTO.getCategory());
        product.setPrice(productDTO.getPrice());
        product.setAmount(productDTO.getAmount());

        return Mapper.toDTO(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)){
            throw new NotFoundException("Product not Found");
        }

        productRepository.deleteById(id);
    }
}
