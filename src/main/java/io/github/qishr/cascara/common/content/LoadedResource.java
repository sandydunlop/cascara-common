package io.github.qishr.cascara.common.content;

import java.io.InputStream;

public final class LoadedResource {
    public final InputStream stream;
    public final String mimeType; // null for non-HTTP

    public LoadedResource(InputStream stream, String mimeType) {
        this.stream = stream;
        this.mimeType = mimeType;
    }
}
