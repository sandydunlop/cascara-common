package io.github.qishr.cascara.common.diagnostic;

import java.net.URI;

import io.github.qishr.cascara.common.diagnostic.Diagnostic.Level;

public class NullReporter implements Reporter {

    public NullReporter(ReportWriter logger) {
    }

    public NullReporter() {
        // Nothing to see here
    }

    @Override
    public NullReporter setLevel(Level level) {
        return this;
    }

    @Override
    public NullReporter setLogger(ReportWriter logger) {
        return this;
    }

    @Override
    public NullReporter setCollector(ReportCollector collector) {
        return this;
    }

    public NullReporter setDisableSystemOutput(boolean b) {
        return this;
    }

    @Override
    public void trace(Object... msg) {
    }

    @Override
    public void debug(Object... msg) {
    }

    @Override
    public void info(Object... msg) {
    }

    @Override
    public void warn(Object... msg) {
    }

    @Override
    public void error(Object... msg) {
    }

    //
    // With Location
    //

    @Override
    public void infoAt(int line, int column, URI uri, Object... msg) {
    }

    @Override
    public void warnAt(int line, int column, URI uri, Object... msg) {
    }

    @Override
    public void errorAt(int line, int column, URI uri, Object... msg) {
    }
}
