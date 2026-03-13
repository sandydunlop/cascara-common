package io.github.qishr.cascara.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ReflectionUtils {
    public static Class<?> getGenericTypeOfListField(Field field) {
        // Check if the field is a List type
        if (List.class.isAssignableFrom(field.getType())) {
            // Get the generic type of the field
            Type genericFieldType = field.getGenericType();
            // Check if it is a ParameterizedType
            if (genericFieldType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericFieldType;
                // Get the actual type arguments
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments.length > 0) {
                    // Return the raw class of the first actual type argument
                    return (Class<?>) actualTypeArguments[0];
                }
            }
        }
        // Return null if it's not a List or doesn't have a generic type
        return null;
    }

    public static Class<?> getGenericTypeOfMapKey(Field field) {
        return getGenericType(field, 0);
    }

    public static Class<?> getGenericTypeOfMapValue(Field field) {
        return getGenericType(field, 1);
    }

    private static Class<?> getGenericType(Field field, int index) {
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType pt) {
            Type[] actualTypeArguments = pt.getActualTypeArguments();
            if (actualTypeArguments.length > index) {
                Type typeArg = actualTypeArguments[index];
                if (typeArg instanceof Class<?>) {
                    return (Class<?>) typeArg;
                }
            }
        }
        return String.class; // Fallback to String if type cannot be determined
    }
}

