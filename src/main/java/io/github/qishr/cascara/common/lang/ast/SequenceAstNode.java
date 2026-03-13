package io.github.qishr.cascara.common.lang.ast;

import java.util.List;

public interface SequenceAstNode<T extends AstNode> extends AstNode {
    int size();
    void add(T node);
    void remove(int index);
    void clear();
    T get(int index);
    List<T> getElements();
    Iterable<T> items();
}
