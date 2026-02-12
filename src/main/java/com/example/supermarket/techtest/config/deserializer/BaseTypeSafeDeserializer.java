package com.example.supermarket.techtest.config.deserializer;

import com.example.supermarket.techtest.exception.TypeMismatchValidationException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Base deserializer that provides type-safe deserialization with comprehensive error collection.
 * Subclasses should implement the deserializeFields method to define field mappings.
 *
 * @param <T> The DTO type to deserialize
 */
public abstract class BaseTypeSafeDeserializer <T> extends JsonDeserializer<T> {


    @Override
    public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {

        JsonNode node = jp.getCodec().readTree(jp);
        T dto = createInstance();

        Map<String, String> errors = new HashMap<>();

        deserializeFields(node, dto, errors);

        if (!errors.isEmpty()) {
            throw new TypeMismatchValidationException(errors);
        }

        return dto;
    }

    /**
     * Creates a new instance of the DTO.
     * Subclasses must implement this method.
     */
    protected abstract T createInstance();

    /**
     * Deserializes individual fields from the JSON node into the DTO.
     * Subclasses should use the helper methods to validate and set fields.
     */
    protected abstract void deserializeFields(JsonNode node, T dto, Map<String, String> errors);

    // Helper methods for common type validations

    protected String validateString(JsonNode node, String fieldName, Map<String, String> errors) {
        if (node.has(fieldName)) {
            if (node.get(fieldName).isTextual()) {
                return node.get(fieldName).asText();
            } else if (!node.get(fieldName).isNull()) {
                errors.put(fieldName, fieldName + " must be a valid String");
            }
        }
        return errors.put(fieldName, "The " + fieldName + " is required");
    }

    protected Integer validateInteger(JsonNode node, String fieldName, Map<String, String> errors) {
        if (node.has(fieldName)) {
            if (node.get(fieldName).isInt()) {
                return node.get(fieldName).asInt();
            } else if (!node.get(fieldName).isNull()) {
                errors.put(fieldName, fieldName + " must be a valid Integer");
            }
        }
        return null;
    }

    protected Long validateLong(JsonNode node, String fieldName, Map<String, String> errors) {
        if (node.has(fieldName)) {
            JsonNode fieldNode = node.get(fieldName);
            if (fieldNode.isLong() || fieldNode.isInt()) {
                return fieldNode.asLong();
            } else if (!fieldNode.isNull()) {
                errors.put(fieldName, fieldName + " must be a valid Long");
            }
        }
        return null;
    }

    protected Double validateDouble(JsonNode node, String fieldName, Map<String, String> errors) {
        if (node.has(fieldName)) {
            if (node.get(fieldName).isNumber()) {
                return node.get(fieldName).asDouble();
            } else if (!node.get(fieldName).isNull()) {
                errors.put(fieldName, fieldName + " must be a valid Double");
            }
        }
        return null;
    }

    protected Float validateFloat(JsonNode node, String fieldName, Map<String, String> errors) {
        if (node.has(fieldName)) {
            if (node.get(fieldName).isNumber()) {
                return node.get(fieldName).floatValue();
            } else if (!node.get(fieldName).isNull()) {
                errors.put(fieldName, fieldName + " must be a valid Float");
            }
        }
        return null;
    }

    protected Boolean validateBoolean(JsonNode node, String fieldName, Map<String, String> errors) {
        if (node.has(fieldName)) {
            if (node.get(fieldName).isBoolean()) {
                return node.get(fieldName).asBoolean();
            } else if (!node.get(fieldName).isNull()) {
                errors.put(fieldName, fieldName + " must be a valid Boolean");
            }
        }
        return null;
    }
}
