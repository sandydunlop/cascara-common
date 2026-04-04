package io.github.qishr.cascara.common.lang.processor;

import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.common.lang.LanguageOptions;

public interface Processor {
    /// Sets the reporter for communicating mapping warnings or errors.
    Processor setReporter(Reporter reporter);

    /// Sets the language-specific options (e.g., indentation, Unicode support).
    Processor setOptions(LanguageOptions<?> options);
}
