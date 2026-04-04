package io.github.qishr.cascara.common.lang.token;

public interface TokenType {
    /// A stable, language‑specific identifier (e.g. "JAVA_IF", "YAML_KEY", "XML_TAG_NAME").
    String getId();

    /// The language‑agnostic category used by editors and highlighters.
    TokenCategory getCategory();
}
