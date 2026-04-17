package io.github.qishr.cascara.common.diagnostic;

import java.net.URI;
import java.util.Arrays;

import io.github.qishr.cascara.common.diagnostic.Diagnostic.Level;

public class SimpleReporter implements Reporter {
    private Level level = Level.INFO;
    private ReportWriter logger = null;
    private ReportCollector collector = null;
    private boolean disableSystemOutput = false;
    private boolean disableFlush = true;

    public SimpleReporter(ReportWriter logger) {
        this.logger = logger;
    }

    public SimpleReporter() {
        // Nothing to see here
    }

    @Override
    public SimpleReporter setLevel(Level level) {
        this.level = level;
        return this;
    }

    @Override
    public SimpleReporter setLogger(ReportWriter logger) {
        this.logger = logger;
        return this;
    }

    @Override
    public SimpleReporter setCollector(ReportCollector collector) {
        this.collector = collector;
        return this;
    }

    public SimpleReporter setDisableSystemOutput(boolean b) {
        this.disableSystemOutput = b;
        return this;
    }

    public SimpleReporter setDisableFlush(boolean b) {
        this.disableFlush = b;
        return this;
    }

    /// Reports a trace message through the reporter.
    /// @param msg The message to report.
    @Override
    public void trace(Object... msg) {
        String message = format(msg);
        if (level == Level.TRACE) {
            print(Level.TRACE, message, 0, 0, null);
        }
    }

    /// Reports a debug message through the reporter.
    /// @param msg The message to report.
    @Override
    public void debug(Object... msg) {
        String message = format(msg);
        if (level.compareTo(Level.DEBUG) >= 0) {
            print(Level.DEBUG, message, 0, 0, null);
        }
    }

    /// Reports an informational message through the reporter.
    /// @param msg The message to report.
    @Override
    public void info(Object... msg) {
        String message = format(msg);
        if (level.compareTo(Level.INFO) >= 0) {
            print(Level.INFO, message, 0, 0, null);
        }
    }

    /// Reports a warning message including location information.
    /// @param msg The warning message to report.
    @Override
    public void warn(Object... msg) {
        String message = format(msg);
        if (level.compareTo(Level.WARNING) >= 0) {
            print(Level.WARNING, message, 0, 0, null);
        }
    }

    /// Reports an error message including location information.
    /// @param msg The error message to report.
    @Override
    public void error(Object... msg) {
        String message = format(msg);
        print(Level.ERROR, message, 0, 0, null);
    }

    //
    // With Location
    //

    @Override
    public void infoAt(int line, int column, URI uri, Object... msg) {
        String message = format(msg);
        if (collector == null) {
            if (level.compareTo(Level.INFO) >= 0) {
                print(Level.INFO, message, line, column, uri);
            }
        } else {
            collect(Level.INFO, message, line, column, uri);
        }
    }

    @Override
    public void warnAt(int line, int column, URI uri, Object... msg) {
        String message = format(msg);
        collect(Level.WARNING, message, line, column, uri);
        if (collector == null) {
            if (level.compareTo(Level.WARNING) >= 0) {
                print(Level.WARNING, message, line, column, uri);
            }
        } else {
            collect(Level.WARNING, message, line, column, uri);
        }
    }

    @Override
    public void errorAt(int line, int column, URI uri, Object... msg) {
        String message = format(msg);
        if (collector == null) {
            print(Level.ERROR, message, line, column, uri);
        } else {
            collect(Level.ERROR, message, line, column, uri);
        }
    }

    //
    //
    //

    private void collect(Level level, String msg, int line, int column, URI uri) {
        if (collector != null) {
            Diagnostic diagnostic = new Diagnostic(level, msg, line, column, uri);
            collector.collect(diagnostic);
        }
    }

    private void print(Level kind, String message, int line, int column, URI uri) {
        if (uri == null) {
            write ("[" + kind + "] " + message + "\n");
        } else {
            if (line > 0) {
                write ("[" + kind + "] " + message + " at " + uri + ":" + line + ":" + column+ "\n");
            } else {
                write ("[" + kind + "] " + message + " in file: " + uri + "\n");
            }
        }
    }

    private void write(String text) {
        if (logger != null) {
            logger.write(text);
        } else if (!disableSystemOutput) {
            System.out.print(text);
            if (!disableFlush) {
                System.out.flush();
            }
        }
    }

    private String format(Object... s) {
        if (s == null || s.length == 0) {
            return "Null or empty message";
        } else if (s[0] instanceof String m) {
            if (s.length == 1) {
                return m;
            } else {
                return String.format(m, Arrays.<Object>copyOfRange(s, 1, s.length));
            }
        } else {
            return "First parameter must be of type String";
        }
    }
}
