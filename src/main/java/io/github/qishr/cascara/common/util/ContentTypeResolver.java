package io.github.qishr.cascara.common.util;

import io.github.qishr.cascara.common.service.ServiceProvider;

public interface ContentTypeResolver extends ServiceProvider {
    ContentType resolve(String type);
}
