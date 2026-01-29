package com.example.supermarket.techtest.dto;

import com.example.supermarket.techtest.exception.TypeMismatchValidationException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@JsonDeserialize
public class ProductDTODeserializer extends JsonDeserializer<ProductDTO> {

    @Override
    public ProductDTO deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {

        JsonNode node = jp.getCodec().readTree(jp);
        ProductDTO dto = new ProductDTO();
        Map<String, String> errors = new HashMap<>();

        // Validate and set name
        if (node.has("name")) {
            if (node.get("name").isTextual()) {
                dto.setName(node.get("name").asText());
            } else if (!node.get("name").isNull()) {
                errors.put("name", "name must be a valid String");
            }
        }

        // Validate and set category
        if (node.has("category")) {
            if (node.get("category").isTextual()) {
                dto.setCategory(node.get("category").asText());
            } else if (!node.get("category").isNull()) {
                errors.put("category", "category must be a valid String");
            }
        }

        // Validate and set price
        if (node.has("price")) {
            if (node.get("price").isNumber()) {
                dto.setPrice(node.get("price").asDouble());
            } else if (!node.get("price").isNull()) {
                errors.put("price", "price must be a valid Double");
            }
        }

        // Validate and set amount
        if (node.has("amount")) {
            if (node.get("amount").isInt()) {
                dto.setAmount(node.get("amount").asInt());
            } else if (!node.get("amount").isNull()) {
                errors.put("amount", "amount must be a valid Integer");
            }
        }

        // If there are type errors, throw custom exception
        if (!errors.isEmpty()) {
            throw new TypeMismatchValidationException(errors);
        }

        return dto;
    }
}
