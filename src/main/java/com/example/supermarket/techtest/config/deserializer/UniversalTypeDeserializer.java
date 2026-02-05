package com.example.supermarket.techtest.config.deserializer;

import com.example.supermarket.techtest.exception.TypeMismatchValidationException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

// ask why do I need to implement T in my UniversalTypeDeserializer
public class UniversalTypeDeserializer extends JsonDeserializer<Object> implements ContextualDeserializer {

    private Class<?> targetClass;

    // Default constructor needed by Jackson
    public UniversalTypeDeserializer() {
    }

    // Private constructor used by createContextual to create a specific instance for the DTO
    private UniversalTypeDeserializer(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty beanProperty) throws JsonMappingException {
        // This captures the class type (e.g., ProductDTO.class) that is being deserialized
        return new UniversalTypeDeserializer(ctxt.getContextualType().getRawClass());
    }

    @Override
    public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = jp.getCodec().readTree(jp);
        Map<String, String> errors = new HashMap<>();

        // Create an instance of the DTO
        Object dtoInstance;
        try {
            dtoInstance = targetClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate DTO: " + targetClass.getName(), e);
        }

        // Iterate over all fields declared in the DTO class
        for (Field field : targetClass.getDeclaredFields()) {
            field.setAccessible(true); // Allow access to private fields

            String fieldName = field.getName();

            // Skip fields that are not in the JSON (unless you want to enforce presence here,
            // but usually @NotNull handles missing fields later)
            if (!node.has(fieldName)) {
                continue;
            }

            JsonNode jsonValue = node.get(fieldName);

            // Allow nulls here; @NotNull annotations will handle them in the next phase
            if (jsonValue.isNull()) {
                continue;
            }

            try {
                // Perform Type Validation and Assignment
                if (field.getType().equals(String.class)) {
                    if (jsonValue.isTextual()) {
                        field.set(dtoInstance, jsonValue.asText());
                    } else {
                        errors.put(fieldName, fieldName + " must be a valid String");
                    }
                }
                else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
                    if (jsonValue.isNumber()) {
                        field.set(dtoInstance, jsonValue.asDouble());
                    } else {
                        errors.put(fieldName, fieldName + " must be a valid Double");
                    }
                }
                else if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
                    // Check specifically for Integer range/type
                    if (jsonValue.canConvertToInt()) {
                        field.set(dtoInstance, jsonValue.asInt());
                    } else {
                        errors.put(fieldName, fieldName + " must be a valid Integer");
                    }
                }
                else if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
                    if (jsonValue.canConvertToLong()) {
                        field.set(dtoInstance, jsonValue.asLong());
                    } else {
                        errors.put(fieldName, fieldName + " must be a valid Long");
                    }
                }
                else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
                    if (jsonValue.isBoolean()) {
                        field.set(dtoInstance, jsonValue.asBoolean());
                    } else {
                        errors.put(fieldName, fieldName + " must be a valid Boolean");
                    }
                }
                // Add more types (BigDecimal, List, etc.) here if needed

            } catch (Exception e) {
                // Fallback error
                errors.put(fieldName, "Invalid format for " + fieldName);
            }

        }

        if (!errors.isEmpty()) {
            // Throw your existing custom exception
            throw new TypeMismatchValidationException(errors);
        }

        return dtoInstance;
    }
}
