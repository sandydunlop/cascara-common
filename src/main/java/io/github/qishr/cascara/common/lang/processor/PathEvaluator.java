package io.github.qishr.cascara.common.lang.processor;

import java.util.List;

import io.github.qishr.cascara.common.lang.ast.AstNode;
import io.github.qishr.cascara.common.lang.ast.PathExpression;

public interface PathEvaluator extends Processor {
    List<AstNode> evaluate(AstNode root, PathExpression<?> expr);
}
