package com.example.supermarket.techtest.dto.deserializer;

import com.example.supermarket.techtest.config.deserializer.BaseTypeSafeDeserializer;
import com.example.supermarket.techtest.dto.ProductDTO;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

public class ProductDTODeserializerModulated extends BaseTypeSafeDeserializer<ProductDTO> {

    @Override
    protected ProductDTO createInstance() {
        return new ProductDTO();
    }

    @Override
    protected void deserializeFields(JsonNode node, ProductDTO dto, Map<String, String> errors, DeserializationContext ctxt) {
        dto.setName(validateString(node, "name", errors));
        dto.setCategory(validateString(node, "category", errors));
        dto.setPrice(validateDouble(node, "price", errors));
        dto.setAmount(validateInteger(node, "amount", errors));
    }
}
