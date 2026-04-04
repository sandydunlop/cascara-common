package io.github.qishr.cascara.common.diagnostic;

import java.net.URI;

public interface ReportCollector {
    void collect(Diagnostic diagnostic);
    void clear(URI uri);
}
