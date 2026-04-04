package io.github.qishr.cascara.common.lang.token;

public enum TokenCategory {
    KEYWORD,
    IDENTIFIER,
    TYPE_NAME,
    FUNCTION_NAME,
    FIELD_NAME,
    PARAMETER_NAME,

    STRING,
    NUMBER,
    BOOLEAN,
    NULL,

    COMMENT,
    DOC_COMMENT,

    OPERATOR,
    PUNCTUATION,
    DELIMITER,
    SYMBOL,

    WHITESPACE,
    NEWLINE,

    STRUCTURAL,  // tags, braces, brackets, etc.
    TEXT,        // plain text in markup

    META,        // directives, pragmas, annotations
    INDENTATION, // YAML/Python-style indent/dedent
    INTERNAL,    // Start and end of stream
    ERROR
}
