package io.github.qishr.cascara.common.lang.ast;

import java.util.List;

/// Represents the structural pairing of a key and a value in a Map.
/// @param K The type of the key (usually String/ScalarAstNode)
/// @param V The type of the value node
public interface MapEntryAstNode<T extends AstNode> extends AstNode {
    T getKey();
    T getValue();
    void setValue(T value);

    @Override
    default List<T> getChildren() {
        return List.of(getValue(), getValue());
    }
}

