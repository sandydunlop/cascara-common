package io.github.qishr.cascara.common.lang.processor;

import io.github.qishr.cascara.common.lang.QuoteStyle;
import io.github.qishr.cascara.common.lang.ast.AstNode;
import io.github.qishr.cascara.common.lang.ast.MapAstNode;
import io.github.qishr.cascara.common.lang.ast.MapEntryAstNode;
import io.github.qishr.cascara.common.lang.ast.ScalarAstNode;
import io.github.qishr.cascara.common.lang.ast.SequenceAstNode;
import io.github.qishr.cascara.common.lang.type.Primitive;

public interface AstFactory<
    N extends AstNode,
    S extends ScalarAstNode<N>,
    L extends SequenceAstNode<N>,
    M extends MapAstNode<N,E>,
    E extends MapEntryAstNode<N>
> {
    S createScalarNode(Object primitiveValue);
    S createScalarNode(Object primitiveValue, QuoteStyle quoteStyle);
    S createScalarNode(Primitive primitive);
    N createScalarKeyNode(Object key);
    L createSequenceNode();
    M createMapNode();
}
