package com.example.supermarket.techtest.config.deserializer;

import com.example.supermarket.techtest.exception.TypeMismatchValidationException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base deserializer that provides type-safe deserialization with comprehensive error collection.
 * Subclasses should implement the deserializeFields method to define field mappings.
 *
 * @param <T> The DTO type to deserialize
 */
public abstract class BaseTypeSafeDeserializer<T> extends JsonDeserializer<T> {


    @Override
    public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {

        JsonNode node = jp.getCodec().readTree(jp);
        T dto = createInstance();

        Map<String, String> errors = new HashMap<>();

        deserializeFields(node, dto, errors, ctxt);

        if (!errors.isEmpty()) {
            throw new TypeMismatchValidationException(errors);
        }

        return dto;
    }

    /**
     * Creates a new instance of the DTO.
     */
    protected abstract T createInstance();

    /**
     * Deserializes individual fields from the JSON node into the DTO.
     * Subclasses should use the helper methods to validate and set fields.
     */
    protected abstract void deserializeFields(JsonNode node, T dto, Map<String, String> errors, DeserializationContext ctxt);

    // Helper methods for common type validations

    protected <E> List<E> validateList(JsonNode node, String fieldName, Class<E> elementType, Map<String, String> errors, DeserializationContext ctxt) {
        if (node.has(fieldName)) {
            JsonNode listNode = node.get(fieldName);
            if (listNode.isArray()) {
                List<E> list = new ArrayList<>();
                for (int i = 0; i < listNode.size(); i++) {
                    try {
                        JsonNode itemNode = listNode.get(i);
                        // Use traverse to create a parser for the sub-node
                        JsonParser p = itemNode.traverse(ctxt.getParser().getCodec());
                        if (p.getCurrentToken() == null) {
                            p.nextToken();
                        }
                        E item = ctxt.readValue(p, elementType);
                        list.add(item);
                    } catch (Exception e) {
                        // Capture nested validation errors if they are TypeMismatchValidationException
                        if (e.getCause() instanceof TypeMismatchValidationException nestedEx) {
                            int finalI = i;
                            nestedEx.getErrors().forEach((key, value) ->
                                errors.put(fieldName + "[" + finalI + "]." + key, value));
                        } else {
                            errors.put(fieldName + "[" + i + "]", "Invalid " + elementType.getSimpleName() + " structure");
                        }
                    }
                }
                return list;
            } else if (!listNode.isNull()) {
                errors.put(fieldName, fieldName + " must be a valid array");
            }
        } else {
            errors.put(fieldName, fieldName + " is required");
        }
        return null;
    }

    protected String validateString(JsonNode node, String fieldName, Map<String, String> errors) {
        if (node.has(fieldName)) {
            if (node.get(fieldName).isTextual()) {
                return node.get(fieldName).asText();
            } else if (!node.get(fieldName).isNull()) {
                errors.put(fieldName, fieldName + " must be a valid string");
            }
        } else {
            errors.put(fieldName, fieldName + " is required");
        }

        return null;
    }

    protected Integer validateInteger(JsonNode node, String fieldName, Map<String, String> errors) {
        if (node.has(fieldName)) {
            if (node.get(fieldName).isInt()) {
                return node.get(fieldName).asInt();
            } else if (!node.get(fieldName).isNull()) {
                errors.put(fieldName, fieldName + " must be a valid integer");
            }
        } else {
            errors.put(fieldName, fieldName + " is required");
        }
        return null;
    }

    protected Long validateLong(JsonNode node, String fieldName, Map<String, String> errors) {
        if (node.has(fieldName)) {
            JsonNode fieldNode = node.get(fieldName);
            if (fieldNode.isLong() || fieldNode.isInt()) {
                return fieldNode.asLong();
            } else if (!fieldNode.isNull()) {
                errors.put(fieldName, fieldName + " must be a valid long");
            }
        } else {
            errors.put(fieldName, fieldName + " is required");
        }
        return null;
    }

    protected Double validateDouble(JsonNode node, String fieldName, Map<String, String> errors) {
        if (node.has(fieldName)) {
            if (node.get(fieldName).isNumber()) {
                return node.get(fieldName).asDouble();
            } else if (!node.get(fieldName).isNull()) {
                errors.put(fieldName, fieldName + " must be a valid double");
            }
        } else {
            errors.put(fieldName, fieldName + " is required");
        }
        return null;
    }

    protected Float validateFloat(JsonNode node, String fieldName, Map<String, String> errors) {
        if (node.has(fieldName)) {
            if (node.get(fieldName).isNumber()) {
                return node.get(fieldName).floatValue();
            } else if (!node.get(fieldName).isNull()) {
                errors.put(fieldName, fieldName + " must be a valid float");
            }
        } else {
            errors.put(fieldName, fieldName + " is required");
        }
        return null;
    }

    protected Boolean validateBoolean(JsonNode node, String fieldName, Map<String, String> errors) {
        if (node.has(fieldName)) {
            if (node.get(fieldName).isBoolean()) {
                return node.get(fieldName).asBoolean();
            } else if (!node.get(fieldName).isNull()) {
                errors.put(fieldName, fieldName + " must be a valid boolean");
            }
        } else {
            errors.put(fieldName, fieldName + " is required");
        }
        return null;
    }

    // LocalDate validation (ISO format: yyyy-MM-dd)
    protected LocalDate validateLocalDate(JsonNode node, String fieldName, Map<String, String> errors) {
        return validateLocalDate(node, fieldName, errors, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    // LocalDate validation with custom format
    protected LocalDate validateLocalDate(JsonNode node, String fieldName, Map<String, String> errors, DateTimeFormatter formatter) {
        if (node.has(fieldName)) {
            if (node.get(fieldName).isTextual()) {
                try {
                    return LocalDate.parse(node.get(fieldName).asText(), formatter);
                } catch (DateTimeParseException e) {
                    errors.put(fieldName, fieldName + " must be a valid date in format " + formatter);
                }
            } else if (!node.get(fieldName).isNull()) {
                errors.put(fieldName, fieldName + " must be a valid date string");
            }
        } else {
            errors.put(fieldName, fieldName + " is required");
        }
        return null;
    }
}
