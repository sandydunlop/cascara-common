package io.github.qishr.cascara.common.diagnostic;

import java.io.PrintStream;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import io.github.qishr.cascara.common.diagnostic.Diagnostic.Level;

public class GlobalReporter implements Reporter {
    private static final DateTimeFormatter TIME_FORMAT =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private static GlobalReporter globalInstance;

    private Class<?> clazz;
    private Level level = Level.INFO;
    private ReportStringWriter stringWriter;
    private ReportDiagnosticWriter diagnosticWriter;
    private ReportCollector collector;
    private boolean disableSystemOutput = false;
    private boolean disableFlush = true;


    // private GlobalReporter(ReportStringWriter writer) {
    //     this.stringWriter = writer;
    // }

    private GlobalReporter(Class<?> clazz) {
        this.clazz = clazz;
    }

    private GlobalReporter() {
        // Nothing to see here
    }

    public static GlobalReporter globalInstance() {
        if (globalInstance == null) {
            globalInstance = new GlobalReporter();
        }
        return globalInstance;
    }

    public static GlobalReporter forClass(Class<?> clazz) {
        globalInstance();
        GlobalReporter reporter = new GlobalReporter(clazz);
        return reporter;
    }

    @Override
    public GlobalReporter setLevel(Level level) {
        if (this != globalInstance) {
            throw new UnsupportedOperationException("The method setLevel in GlobalReporter may only be called on the global instance.");
        }
        this.level = level;
        return this;
    }

    @Deprecated
    public GlobalReporter setStringWriter(ReportStringWriter writer) {
        throw new UnsupportedOperationException("The method setStringWriter is not longer available.");
    }

    @Override
    public GlobalReporter setDiagnosticWriter(ReportDiagnosticWriter writer) {
        if (this != globalInstance) {
            throw new UnsupportedOperationException("The method setDiagnosticWriter in GlobalReporter may only be called on the global instance.");
        }
        this.diagnosticWriter = writer;
        return this;
    }

    @Override
    public GlobalReporter setCollector(ReportCollector collector) {
        if (this != globalInstance) {
            throw new UnsupportedOperationException("The method setCollector in GlobalReporter may only be called on the global instance.");
        }
        this.collector = collector;
        return this;
    }

    public GlobalReporter setDisableSystemOutput(boolean b) {
        if (this != globalInstance) {
            throw new UnsupportedOperationException("The method setDisableSystemOutput in GlobalReporter may only be called on the global instance.");
        }
        globalInstance.disableSystemOutput = b;
        return this;
    }

    public GlobalReporter setDisableFlush(boolean b) {
        if (this != globalInstance) {
            throw new UnsupportedOperationException("The method setDisableFlush in GlobalReporter may only be called on the global instance.");
        }
        globalInstance.disableFlush = b;
        return this;
    }




    /// Reports a trace message through the reporter.
    /// @param msg The message to report.
    @Override
    public void trace(Object... msg) {
        MsgAndEx mae = processParams(msg);
        report(Level.TRACE, 0, 0, null, mae.msg(), mae.ex());
    }

    /// Reports a debug message through the reporter.
    /// @param msg The message to report.
    @Override
    public void debug(Object... msg) {
        MsgAndEx mae = processParams(msg);
        report(Level.DEBUG, 0, 0, null, mae.msg(), mae.ex());
    }

    /// Reports an informational message through the reporter.
    /// @param msg The message to report.
    @Override
    public void info(Object... msg) {
        MsgAndEx mae = processParams(msg);
        report(Level.INFO, 0, 0, null, mae.msg(), mae.ex());
    }

    /// Reports a warning message including location information.
    /// @param msg The warning message to report.
    @Override
    public void warn(Object... msg) {
        MsgAndEx mae = processParams(msg);
        report(Level.WARNING, 0, 0, null, mae.msg(), mae.ex());
    }

    /// Reports an error message including location information.
    /// @param msg The error message to report.
    @Override
    public void error(Object... msg) {
        MsgAndEx mae = processParams(msg);
        report(Level.ERROR, 0, 0, null, mae.msg(), mae.ex());
    }

    //
    // With Location
    //

    @Override
    public void infoAt(int line, int column, URI uri, Object... msg) {
        MsgAndEx mae = processParams(msg);
        report(Level.INFO, line, column, uri, mae.msg(), mae.ex());
    }

    @Override
    public void warnAt(int line, int column, URI uri, Object... msg) {
        MsgAndEx mae = processParams(msg);
        report(Level.WARNING, line, column, uri, mae.msg(), mae.ex());
    }

    @Override
    public void errorAt(int line, int column, URI uri, Object... msg) {
        MsgAndEx mae = processParams(msg);
        report(Level.ERROR, line, column, uri, mae.msg(), mae.ex());
    }

    //
    //
    //

    private void report(Level level, int line, int column, URI uri, String message, Exception exception) {
        if (globalInstance.collector == null || (level != Level.WARNING && level != Level.ERROR)) {
            if (globalInstance.level.compareTo(level) >= 0) {
                writeString(level, line, column, uri, message, exception);
            }
            if (globalInstance.diagnosticWriter != null) {
                writeDiagnostic(level, line, column, uri, message, exception);
            }
        } else {
            collect(level, line, column, uri, message, exception);
        }
    }

    private void collect(Level level, int line, int column, URI uri, String message, Exception exception) {
        Diagnostic diagnostic = new Diagnostic(clazz, level, message, line, column, uri, exception);
        globalInstance.collector.collect(diagnostic);
    }

    private void writeDiagnostic(Level level, int line, int column, URI uri, String message, Exception exception) {
        Diagnostic diagnostic = new Diagnostic(clazz, level, message, line, column, uri, exception);
        globalInstance.diagnosticWriter.write(diagnostic);
    }

    private void writeString(Level level, int line, int column, URI uri, String message, Exception exception) {
        // Write to console / writer
        String timestamp = LocalDateTime.now().format(TIME_FORMAT);

        if (uri == null) {
            writeString (level, String.format("[%5s] [%s] [%s] %s\n", level, timestamp, clazz.getSimpleName(), message));
        } else {
            if (line > 0) {
                writeString (level, String.format("[%5s] [%s] [%s] %s at %s:%d\n", level, timestamp, clazz.getSimpleName(), message, uri, line));
                // writeString (level, "[" + level + "] " + message + " at " + uri + ":" + line + ":" + column+ "\n");
            } else {
                writeString (level, String.format("[%5s] [%s] [%s] %s in file %s\n", level, timestamp, clazz.getSimpleName(), message, uri));
                // writeString (level, "[" + level + "] " + message + " in file: " + uri + "\n");
            }
        }
    }

    private void writeString(Level level, String text) {
        PrintStream console = level == Level.ERROR ? System.err : System.out;
        if (!globalInstance.disableSystemOutput) {
            console.print(text);
            if (!disableFlush) {
                console.flush();
            }
        }
        if (globalInstance.stringWriter != null) {
            globalInstance.stringWriter.write(text);
        }
    }

    //
    //
    //

    private MsgAndEx processParams(Object... s) {
        if (s == null || s.length == 0) {
            return new MsgAndEx("Null or empty message", null);
        } else if (s[0] instanceof String m) {
            if (s.length == 1) {
                return new MsgAndEx(m, null);
            } else {
                Object last = s[s.length - 1];
                if (last instanceof Exception e) {
                    return new MsgAndEx(String.format(m, Arrays.<Object>copyOfRange(s, 1, s.length - 1)), e);
                } else {
                    return new MsgAndEx(String.format(m, Arrays.<Object>copyOfRange(s, 1, s.length)), null);
                }
            }
        } else {
            return new MsgAndEx("First parameter must be of type String", null);
        }
    }

    private static record MsgAndEx(String msg, Exception ex) {}
}
