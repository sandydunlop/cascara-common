package io.github.qishr.cascara.common.lang.token;

public interface Token {
    public TokenType getType();
    public String getLexeme();
    public Object getValue();
    public int getOffset();
    public int getStartLine();
    public int getStartColumn();
    public String toString();
}
