package io.github.qishr.cascara.common.lang.ast;

public interface CommentAstNode extends AstNode {
    String getString();      // The actual comment text without the '#'
    String getRawValue();    // The lexeme, including the '#'
    boolean isMultiLine(); // Useful for CSS or Java modules
}
