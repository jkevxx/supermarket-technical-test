package com.example.supermarket.techtest.dto.deserializer;

import com.example.supermarket.techtest.config.deserializer.BaseTypeSafeDeserializer;
import com.example.supermarket.techtest.dto.OfficeDTO;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

public class OfficeDTODeserializerModulated extends BaseTypeSafeDeserializer<OfficeDTO> {

    @Override
    protected OfficeDTO createInstance() {
        return new OfficeDTO();
    }

    @Override
    protected void deserializeFields(JsonNode node, OfficeDTO dto, Map<String, String> errors, DeserializationContext ctxt) {
        dto.setName(validateString(node, "name", errors));
        dto.setDirection(validateString(node, "direction", errors));
    }
}
