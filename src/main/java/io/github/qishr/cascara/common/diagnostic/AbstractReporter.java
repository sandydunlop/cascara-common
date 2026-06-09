package io.github.qishr.cascara.common.diagnostic;

import java.io.PrintStream;
import java.net.URI;
import java.util.function.Consumer;

import io.github.qishr.cascara.common.diagnostic.Diagnostic.Level;
import io.github.qishr.cascara.common.diagnostic.code.DiagnosticCode;
import io.github.qishr.cascara.common.lang.token.Token;

public abstract class AbstractReporter<T extends AbstractReporter<?>> implements Reporter {
    protected Level level = Level.INFO;

    protected String source;

    /// Consumes diagnostics included in the current Level or more
    /// important, with ERROR being the most important.
    protected Consumer<Diagnostic> diagnosticCollector;

    // Consumes ERROR, WARN, and INFO diagnostics.
    protected Consumer<Diagnostic> problemCollector;

    protected Consumer<String> stringWriter;
    protected boolean disableSystemOutput = false;
    protected boolean disableFlush = true;

    protected AbstractReporter(Consumer<String> writer) {
        this.stringWriter = writer;
    }

    protected AbstractReporter() {
        // Nothing to see here
    }

    protected abstract T self();

    @Override
    public boolean collectsProblems() {
        return problemCollector != null;
    }

    @Override
    public T setLevel(Level level) {
        this.level = level;
        return self();
    }

    @Override
    public T setDiagnosticCollector(Consumer<Diagnostic> collector) {
        diagnosticCollector = collector;
        return self();
    }

    @Override
    public T setProblemCollector(Consumer<Diagnostic> collector) {
        problemCollector = collector;
        return self();
    }

    public T setDisableSystemOutput(boolean b) {
        disableSystemOutput = b;
        return self();
    }

    public T setDisableFlush(boolean b) {
        disableFlush = b;
        return self();
    }

    //
    // Exception
    //

    @Override
    public void error(LocalizableException e) {
        report(buildDiagnostic(source, Level.ERROR, e.getCause(), e.getCode(), e.getDetails()));
    }

    @Override
    public void error(LocalizableRuntimeException e) {
        if (e instanceof LocatableException locatable) {
            report(buildDiagnostic(
                locatable.getUri(),
                locatable.getLine(),
                locatable.getColumn(),
                LocatableException.UNKNOWN_COORD,
                LocatableException.UNKNOWN_COORD,
                source, Level.ERROR, e.getCause(), e.getCode(), e.getDetails()));
        } else {
            report(buildDiagnostic(source, Level.ERROR, e.getCause(), e.getCode(), e.getDetails()));
        }
    }

    //
    // Plain
    //

    @Override
    public void trace(String message, Object... args) {
        report(buildDiagnostic(source, Level.TRACE, message, args));
    }

    @Override
    public void debug(String message, Object... args) {
        report(buildDiagnostic(source, Level.DEBUG, message, args));
    }

    @Override
    public void info(DiagnosticCode code, Object... args) {
        report(buildDiagnostic(source, Level.INFO, null, code, args));
    }

    @Override
    public void warn(DiagnosticCode code, Object... args) {
        report(buildDiagnostic(source, Level.WARN, null, code, args));
    }

    @Override
    public void error(DiagnosticCode code, Object... args) {
        report(buildDiagnostic(source, Level.ERROR, null, code, args));
    }

    @Override
    public void error(Throwable cause, DiagnosticCode code, Object... args) {
        report(buildDiagnostic(source, Level.ERROR, cause, code, args));
    }

    //
    // With Location
    //

    @Override
    public void infoAt(int line, int column, DiagnosticCode code, Object... args) {
        report(buildDiagnostic(
            null, line, column,
            LocatableException.UNKNOWN_COORD,
            LocatableException.UNKNOWN_COORD,
            source, Level.INFO, null, code, args
        ));
    }

    @Override
    public void warnAt(int line, int column, DiagnosticCode code, Object... args) {
        report(buildDiagnostic(
            null, line, column,
            LocatableException.UNKNOWN_COORD,
            LocatableException.UNKNOWN_COORD,
            source, Level.WARN, null, code, args
        ));
    }

    @Override
    public void errorAt(int line, int column, DiagnosticCode code, Object... args) {
        report(buildDiagnostic(
            null, line, column,
            LocatableException.UNKNOWN_COORD,
            LocatableException.UNKNOWN_COORD,
            source, Level.ERROR, null, code, args
        ));
    }

    @Override
    public void errorAt(int line, int column, Throwable cause, DiagnosticCode code, Object... args) {
        report(buildDiagnostic(
            null, line, column,
            LocatableException.UNKNOWN_COORD,
            LocatableException.UNKNOWN_COORD,
            source, Level.ERROR, cause, code, args
        ));
    }

    //
    // With Location Including Offset
    //

    @Override
    public void infoAt(int line, int column, int start, int end, DiagnosticCode code, Object... args) {
        report(buildDiagnostic(null, line, column, start, end, source, Level.INFO, null, code, null, args));
    }

    @Override
    public void warnAt(int line, int column, int start, int end, DiagnosticCode code, Object... args) {
        report(buildDiagnostic(null, line, column, start, end, source, Level.WARN, null, code, null, args));
    }

    @Override
    public void errorAt(int line, int column, int start, int end, DiagnosticCode code, Object... args) {
        report(buildDiagnostic(null, line, column, start, end, source, Level.ERROR, null, code, args));
    }

    @Override
    public void errorAt(int line, int column, int start, int end, Throwable cause, DiagnosticCode code, Object... args) {
        report(buildDiagnostic(null, line, column, start, end, source, Level.ERROR, cause, code, args));
    }

    //
    // With Token
    //

    @Override
    public void infoAt(Token token, DiagnosticCode code, Object... args) {
        report(buildDiagnostic(token, source, Level.INFO, null, code, args));
    }

    @Override
    public void warnAt(Token token, DiagnosticCode code, Object... args) {
        report(buildDiagnostic(token, source, Level.WARN, null, code, args));
    }

    @Override
    public void errorAt(Token token, DiagnosticCode code, Object... args) {
        report(buildDiagnostic(token, source, Level.ERROR, null, code, args));
    }

    @Override
    public void errorAt(Token token, Throwable cause, DiagnosticCode code, Object... args) {
        report(buildDiagnostic(token, source, Level.ERROR, cause, code, args));
    }

    //
    //
    //

    protected abstract void writeString(Diagnostic diagnostic);

    protected Consumer<Diagnostic> getDiagnosticCollector() { return diagnosticCollector; }

    protected Consumer<Diagnostic> getProblemCollector() { return problemCollector; }

    protected Consumer<String> getStringWriter() { return stringWriter; }

    protected boolean disableSystemOutput() { return disableSystemOutput; }

    protected boolean disableFlush() { return disableFlush; }

    protected void report(Diagnostic diagnostic) {
        if (this.level.compareTo(diagnostic.getLevel()) >= 0) {
            writeString(diagnostic);
            if (getDiagnosticCollector() != null) {
                getDiagnosticCollector().accept(diagnostic);
            }
        }

        if (getProblemCollector() != null && isProblem(level)) {
            getProblemCollector().accept(diagnostic);
        }
    }

    protected void writeString(Level level, String message) {
        outputToConsole(level, message);
        if (getStringWriter() != null) {
            getStringWriter().accept(message);
        }
    }

    protected void outputToConsole(Level level, String message) {
        PrintStream console = level == Level.ERROR ? System.err : System.out;
        if (!disableSystemOutput()) {
            console.print(message);
            if (!disableFlush()) {
                console.flush();
            }
        }
    }

    //
    //
    //

    /// With message string
    protected Diagnostic buildDiagnostic(String source, Level level, String message, Object... args) {
        return new Diagnostic(
            null,
            LocatableException.UNKNOWN_COORD,
            LocatableException.UNKNOWN_COORD,
            LocatableException.UNKNOWN_COORD,
            LocatableException.UNKNOWN_COORD,
            source, level, null, null, message, args
        );
    }

    /// With diagnostic code, and cause
    protected Diagnostic buildDiagnostic(String source, Level level, Throwable cause, DiagnosticCode code, Object... args) {
        return new Diagnostic(
            null,
            LocatableException.UNKNOWN_COORD,
            LocatableException.UNKNOWN_COORD,
            LocatableException.UNKNOWN_COORD,
            LocatableException.UNKNOWN_COORD,
            source, level, cause, code, null, args
        );
    }

    /// With diagnostic code, location, and cause
    protected Diagnostic buildDiagnostic(URI uri, int line, int column, int start, int end, String source, Level level, Throwable cause, DiagnosticCode code, Object... args) {
        return new Diagnostic(uri, line, column, start, end, source, level, cause, code, null, args);
    }

    /// With diagnostic code, token, and cause
    protected Diagnostic buildDiagnostic(Token token, String source, Level level, Throwable cause, DiagnosticCode code, Object... args) {
        if (token == null) {
            throw new IllegalArgumentException("Token must not be null");
        }
        return new Diagnostic(null, token, source, level, cause, code, null, args);
    }

    private boolean isProblem(Level level) {
        return (level == Level.ERROR || level == Level.WARN || level == Level.INFO);
    }
}
