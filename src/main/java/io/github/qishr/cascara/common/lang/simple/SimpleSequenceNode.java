package io.github.qishr.cascara.common.lang.simple;

import io.github.qishr.cascara.common.lang.ast.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SimpleSequenceNode extends SimpleNode implements SequenceAstNode<SimpleNode> {

    private final List<SimpleNode> elements = new ArrayList<>();

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public void add(SimpleNode node) {
        elements.add(node);
    }

    @Override
    public void remove(int index) {
        elements.remove(index);
    }

    @Override
    public void clear() {
        elements.clear();
    }

    @Override
    public SimpleNode get(int index) {
        return elements.get(index);
    }

    @Override
    public List<SimpleNode> getElements() {
        return elements;
    }

    @Override
    public Iterable<SimpleNode> items() {
        return elements;
    }

    @Override
    public List<? extends AstNode> getChildren() {
        return elements;
    }

    @Override
    public List<CommentAstNode> getComments() {
        return Collections.emptyList();
    }
}
