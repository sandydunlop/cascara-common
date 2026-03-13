package io.github.qishr.cascara.common.lang.simple;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import io.github.qishr.cascara.common.lang.StructuredDocument;
import io.github.qishr.cascara.common.lang.ast.*;

public final class SimpleDocument extends SimpleNode implements StructuredDocument {

    private final SimpleMapNode root;

    public SimpleDocument(SimpleMapNode root) {
        this.root = root;
    }

    public SimpleMapNode getRoot() {
        return root;
    }

    public Optional<URI> getSchemaUri() {
        return Optional.empty();
    }

    public AstNode get(String key) {
        return root.get(key);
    }

    public String getString(String key) {
        return root.getString(key);
    }

    public MapAstNode<?, ?> getMap(String key) {
        return root.getMap(key);
    }

    public SimpleSequenceNode getSequence(String key) {
        return root.getSequence(key);
    }

    @Override
    public List<? extends AstNode> getChildren() {
        return List.of(root);
    }

    @Override
    public List<CommentAstNode> getComments() {
        return List.of();
    }
}
