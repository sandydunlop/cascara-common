package io.github.qishr.cascara.common.service;

public class CascaraServiceMetadata {
    private Class<?> type;
    private Object instance;

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }
}
