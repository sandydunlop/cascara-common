package io.github.qishr.cascara.common.type;

public abstract class AbstractScalarDescriptor extends AbstractTypeDescriptor implements ScalarDescriptor {
    protected static final String SCHEMA_TYPE = "type";
    protected static final String SCHEMA_FORMAT = "format";
    protected static final String SCHEMA_CONTENT_ENCODING = "contentEncoding";

    protected AbstractScalarDescriptor(Class<?> type, String schemaType, String format) {
        this(type, schemaType, format, null);
    }

    protected AbstractScalarDescriptor(Class<?> type, String schemaType, String format, String contentEncoding) {
        super(type);

        if (schemaType != null && !schemaType.isEmpty()) {
            properties.set(SCHEMA_TYPE, schemaType);
        }
        if (format != null && !format.isEmpty()) {
            properties.set(SCHEMA_FORMAT, format);
        }
        if (contentEncoding != null && !contentEncoding.isEmpty()) {
            properties.set(SCHEMA_CONTENT_ENCODING, format);
        }
    }

    public String getType() {
        return properties.getString(SCHEMA_TYPE);
    }

    public String getFormat() {
        return properties.getString(SCHEMA_FORMAT);
    }

    public String getContentEncoding() {
        return properties.getString(SCHEMA_CONTENT_ENCODING);
    }
}
