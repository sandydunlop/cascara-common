package io.github.qishr.cascara.common.lang.simple;

import io.github.qishr.cascara.common.lang.ast.*;
import java.util.Collections;
import java.util.List;

public final class SimpleMapEntryNode extends SimpleNode implements MapEntryAstNode<SimpleNode> {

    private final SimpleNode key;
    private SimpleNode value;

    public SimpleMapEntryNode(SimpleNode key, SimpleNode value) {
        this.key = key;
        this.value = value;
    }

    public SimpleMapEntryNode(String key, SimpleNode value) {
        this.key = new SimpleScalarNode(key);
        this.value = value;
    }

    public SimpleMapEntryNode(String key, String value) {
        this.key = new SimpleScalarNode(key);
        this.value = new SimpleScalarNode(value);
    }

    @Override
    public SimpleNode getKey() {
        return key;
    }

    @Override
    public SimpleNode getValue() {
        return value;
    }

    @Override
    public void setValue(SimpleNode value) {
        this.value = value;
    }

    @Override
    public List<SimpleNode> getChildren() {
        return List.of(key, value);
    }

    @Override
    public List<CommentAstNode> getComments() {
        return Collections.emptyList();
    }
}
