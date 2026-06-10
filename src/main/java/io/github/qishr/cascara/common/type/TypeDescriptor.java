package io.github.qishr.cascara.common.type;

import io.github.qishr.cascara.common.service.ServiceProvider;

public interface TypeDescriptor extends ServiceProvider {
    Class<?> getJavaType();
}
