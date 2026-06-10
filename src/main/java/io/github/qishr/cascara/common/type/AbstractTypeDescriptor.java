package io.github.qishr.cascara.common.type;

import io.github.qishr.cascara.common.util.Properties;

public abstract class AbstractTypeDescriptor implements TypeDescriptor {
    protected Properties properties = new Properties();

    private Class<?> javaType;

    protected AbstractTypeDescriptor(Class<?> type) {
        this.javaType = type;
        properties.set("javaType", type.getName());
    }

    public Properties getServiceProperties() {
        return properties;
    }

    public Class<?> getJavaType() {
        return javaType;
    }
}
