package io.github.qishr.cascara.common.lang.ast;

public interface ReferenceAstNode<T extends AstNode> extends AstNode {
    /// The name of the anchor being referenced (e.g. "default_settings")
    String getReferenceTarget();

    /// Returns the original node being pointed to.
    T resolve();
}