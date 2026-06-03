package io.github.qishr.cascara.common.util;

public class Property {
    Kind kind = Kind.STRING;
    String key;
    String value = null;

    public Property(String k) {
        key = k;
    }

    public Property(String k, String v) {
        key = k;
        value = v;
    }

    public String getKey() {
        return key;
    }

    public void setName(String k) {
        key = k;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public String getString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String v) {
        value = v;
        kind = Kind.STRING;
    }

    public void setValue(boolean v) {
        value = v ? "true" : "false";
        kind = Kind.BOOLEAN;
    }

    public void setValue(int v) {
        value = Long.toString(v);
        kind = Kind.NUMBER;
    }

    public void setValue(double v) {
        value = Double.toString(v);
        kind = Kind.NUMBER;
    }

    public double asDouble() {
        return asDouble(-1);
    }

    public double asDouble(double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public int asInt() {
        return asInt(-1);
    }

    public long asLong() {
        return asLong(-1);
    }

    public int asInt(int defaultValue) {
        return (int) asLong(defaultValue);
    }

    public long asLong(int defaultValue) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
        }
        try {
            return Double.valueOf(value).longValue();
        } catch (NumberFormatException e) {
        }
        return defaultValue;
    }

    public boolean asBoolean() {
        return asBoolean(false);
    }

    public boolean asBoolean(boolean defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return (value.equalsIgnoreCase("true") ||
             value.equalsIgnoreCase("yes"));
    }

    public boolean isEmpty() {
        return value == null || value.isEmpty();
    }

    public enum Kind {
        STRING,
        NUMBER,
        BOOLEAN
    }
}
