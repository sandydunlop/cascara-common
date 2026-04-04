package io.github.qishr.cascara.common.lang.processor;

import java.net.URI;
import java.util.List;
import java.util.Set;

import io.github.qishr.cascara.common.lang.token.Token;
import io.github.qishr.cascara.common.lang.token.TokenType;

public interface Tokenizer<T extends Token> extends Processor {
    List<T> tokenize(String text);
    List<T> tokenize(String text, URI uri);
    default Set<? extends TokenType> getTokenTypes() { return Set.of(); }
}
