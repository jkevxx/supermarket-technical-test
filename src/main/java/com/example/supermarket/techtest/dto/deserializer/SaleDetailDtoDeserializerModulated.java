package com.example.supermarket.techtest.dto.deserializer;

import com.example.supermarket.techtest.config.deserializer.BaseTypeSafeDeserializer;
import com.example.supermarket.techtest.dto.SalesDetailDTO;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

public class SaleDetailDtoDeserializerModulated extends BaseTypeSafeDeserializer<SalesDetailDTO> {


    @Override
    protected SalesDetailDTO createInstance() {
        return new SalesDetailDTO();
    }

    @Override
    protected void deserializeFields(JsonNode node, SalesDetailDTO dto, Map<String, String> errors, DeserializationContext ctxt) {
        dto.setProductName(validateString(node, "productName", errors));
        dto.setAmountProd(validateInteger(node, "amountProd", errors));
        dto.setPrice(validateDouble(node, "price", errors));
//        dto.setSubtotal(validateDouble(node, "subtotal", errors));
    }
}
