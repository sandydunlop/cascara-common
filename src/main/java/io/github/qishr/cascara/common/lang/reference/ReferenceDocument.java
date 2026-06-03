package io.github.qishr.cascara.common.lang.reference;

import java.net.URI;
import java.util.List;

import io.github.qishr.cascara.common.lang.StructuredDocument;
import io.github.qishr.cascara.common.lang.ast.*;

public final class ReferenceDocument extends ReferenceNode implements StructuredDocument {
    private URI originUri;
    private URI schemaUri;

    private final ReferenceMapNode root;

    public ReferenceDocument(ReferenceMapNode root) {
        this.root = root;
    }

    public ReferenceMapNode getRoot() {
        return root;
    }

    public URI getOriginUri() { return originUri; }
    public void setOriginUri(URI originUri) { this.schemaUri = originUri; }

    public URI getSchemaUri() { return schemaUri; }
    public void setSchemaUri(URI schemaUri) { this.schemaUri = schemaUri; }

    public AstNode get(String key) {
        return root.get(key);
    }

    public String getString(String key) {
        return root.getString(key);
    }

    public MapAstNode<?, ?> getMap(String key) {
        return root.getMap(key);
    }

    public ReferenceSequenceNode getSequence(String key) {
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
