# Exception Handling in Nested Deserialization

This document explains the technical reasons why the previous exception handling logic in `BaseTypeSafeDeserializer.java` failed to capture nested validation errors and why a recursive search function was necessary.

## The Original Problem

The original implementation in the `validateList` method attempted to capture nested validation errors using a single-level check:

```java
try {
    E item = ctxt.readValue(p, elementType);
    list.add(item);
} catch (Exception e) {
    if (e.getCause() instanceof TypeMismatchValidationException nestedEx) {
        // Extract errors...
    } else {
        // Generic error...
    }
}
```

### Why `e.getCause()` Failed

When `ctxt.readValue(p, elementType)` executes, Jackson's deserialization process can wrap exceptions in several ways:

1.  **Direct Exception**: If the nested deserializer throws `TypeMismatchValidationException` (which extends `RuntimeException`), and Jackson doesn't wrap it at that specific point, `e` **is** the `TypeMismatchValidationException`. In this case, `e.getCause()` is `null`, and the `instanceof` check on the cause fails.
2.  **Multiple Wrapping**: Jackson often wraps exceptions inside a `JsonMappingException` or `InvocationTargetException`. Depending on the depth of the nesting and the internal state of the `DeserializationContext`, the actual validation exception might be several layers deep in the exception chain (e.g., `JsonMappingException` -> `RuntimeException` -> `TypeMismatchValidationException`).
3.  **Jackson Internal Handling**: Jackson's `readValue` method sometimes catches exceptions and rethrows them as specific Jackson exceptions, potentially losing the immediate "cause" relationship if you only check one level deep.

As observed in the logs:
```text
class com.example.supermarket.techtest.exception.TypeMismatchValidationException
null
```
The exception caught was already the `TypeMismatchValidationException`, so `getCause()` returned `null`, causing the logic to fall through to the "Invalid structure" generic error.

## The Solution: Recursive Validation Function

To make the deserializer robust, we implemented a recursive helper function:

```java
private TypeMismatchValidationException findTypeMismatchException(Throwable e) {
    if (e == null) {
        return null;
    }
    if (e instanceof TypeMismatchValidationException) {
        return (TypeMismatchValidationException) e;
    }
    return findTypeMismatchException(e.getCause());
}
```

### Why This is Necessary

1.  **Chain Traversal**: This function traverses the entire exception stack (the "cause chain"). It doesn't matter if the exception is the top-level object, the immediate cause, or buried three layers deep; the function will find it.
2.  **Decoupling from Jackson's Internal Behavior**: Jackson's exception-wrapping strategy can change between versions or even based on configuration (e.g., `DeserializationFeature`). By searching the chain, we decouple our error collection logic from the specific way Jackson chooses to wrap exceptions.
3.  **Unified Error Collection**: It allows the `validateList` method to consistently extract the `Map<String, String> errors` from any `TypeMismatchValidationException` triggered by a nested DTO, ensuring that the final response contains specific field-level errors (e.g., `details[0].price`) rather than a vague "Invalid structure" message.

## Conclusion

By moving the validation to a recursive function, we ensure that our custom validation framework can "see through" the various layers of exception wrapping provided by the framework, resulting in a much more accurate and helpful API response for the end user.
