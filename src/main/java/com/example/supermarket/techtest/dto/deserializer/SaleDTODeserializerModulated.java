package com.example.supermarket.techtest.dto.deserializer;

import com.example.supermarket.techtest.config.deserializer.BaseTypeSafeDeserializer;
import com.example.supermarket.techtest.dto.SaleDTO;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

public class SaleDTODeserializerModulated extends BaseTypeSafeDeserializer<SaleDTO> {


    @Override
    protected SaleDTO createInstance() {
        return new SaleDTO();
    }

    @Override
    protected void deserializeFields(JsonNode node, SaleDTO dto, Map<String, String> errors) {

        dto.setDate(validateLocalDate(node, "date", errors));
        dto.setStatus(validateString(node, "status", errors));
        dto.setIdOffice(validateLong(node, "idOffice", errors));
        dto.setTotal(validateDouble(node, "total", errors));
        
    }
}
