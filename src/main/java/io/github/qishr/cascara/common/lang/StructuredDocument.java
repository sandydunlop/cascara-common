package io.github.qishr.cascara.common.lang;

import java.net.URI;
import java.util.Optional;

import io.github.qishr.cascara.common.lang.ast.AstNode;

/// Represents a top-level language resource that contains structured data.
public interface StructuredDocument extends AstNode {
    /// Returns the root data node (usually a Map or Sequence).
    AstNode getRoot();

    /// Returns the URI of the schema that defines this document's structure,
    /// typically extracted from a header comment or metadata field.
    Optional<URI> getSchemaUri();
}