package io.github.qishr.cascara.common.content;

import java.io.IOException;
import java.net.URI;

public interface ContentLoader {
    ResourceContent getContent(URI uri) throws IOException;
}
