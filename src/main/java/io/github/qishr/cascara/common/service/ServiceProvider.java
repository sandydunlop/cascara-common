package io.github.qishr.cascara.common.service;

import io.github.qishr.cascara.common.util.Properties;

public interface ServiceProvider {
    default Properties getCapabilities() { return null; }
}
