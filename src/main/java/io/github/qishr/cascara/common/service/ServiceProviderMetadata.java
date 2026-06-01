package io.github.qishr.cascara.common.service;

import io.github.qishr.cascara.common.util.Properties;

public class ServiceProviderMetadata {
    private Class<? extends ServiceProvider> type;
    private Properties capabilities;
    private String location;

    public boolean getBooleanCapability(String capName) {
        if (capName == null || capName.isEmpty()) return false;
        return capabilities.getBoolean(capName, false);
    }

    public String getTitle() {
        if (capabilities == null) return null;
        return capabilities.getString("title");
    }

    public String getModuleName() {
        return type.getModule().getName();
    }

    public String getProviderName() {
        return type.getName();
    }

    public Class<? extends ServiceProvider> getType() {
        return type;
    }

    public void setType(Class<ServiceProvider> type) {
        this.type = type;
    }

    public Properties getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Properties capabilities) {
        this.capabilities = capabilities;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCapability(String name) {
        return capabilities.getString(name);
    }
}
