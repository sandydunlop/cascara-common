package io.github.qishr.cascara.common.diagnostic;

import java.net.URI;

import io.github.qishr.cascara.common.diagnostic.Diagnostic.Level;

public interface Reporter {

    Reporter setLevel(Level level);

    Reporter setLogger(ReportWriter logger);

    Reporter setCollector(ReportCollector collector);

    /// Reports an trace message through the reporter.
    ///
    /// @param m The message to report.
    void trace(Object... m);

    /// Reports an debug message through the reporter.
    ///
    /// @param m The message to report.
    void debug(Object... m);

    /// Reports an informational message through the reporter.
    ///
    /// @param m The message to report.
    void info(Object... m);

    /// Reports a warning message including location information.
    ///
    /// @param m The warning message to report.
    void warn(Object... m);

    /// Reports an error message including location information.
    ///
    /// @param m The error message to report.
    void error(Object... m);

    void infoAt(int line, int column, URI uri, Object... m);
    void warnAt(int line, int column, URI uri, Object... m);
    void errorAt(int line, int column, URI uri, Object... m);
}