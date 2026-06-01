package io.github.qishr.cascara.common.type;

import io.github.qishr.cascara.common.util.Properties;

public abstract class AbstractTypeDescriptor implements TypeDescriptor {
    protected static final String TYPE_KEYWORD = "type";
    protected static final String FORMAT_KEYWORD = "format";

    private Properties capabilities = new Properties();

    protected AbstractTypeDescriptor(Class<?> type, String schemaType, String stringFormat) {
        capabilities.set("type", type.getName());
        if (schemaType != null && !schemaType.isEmpty()) {
            capabilities.set("schemaType", schemaType);
        }
        if (stringFormat != null && !stringFormat.isEmpty()) {
            capabilities.set("stringFormat", stringFormat);
        }
    }

    public Properties getCapabilities() {
        return capabilities;
    }
}
