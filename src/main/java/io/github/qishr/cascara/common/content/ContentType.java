package io.github.qishr.cascara.common.content;

import java.util.List;

public interface ContentType {
    public String getCanonicalId();
    public String getCanonicalName();
    public List<String> getMimeTypes();
    public List<String> getSuffixes();
    public String getModuleId();
    public String toString();
}