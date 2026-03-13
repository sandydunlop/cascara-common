package io.github.qishr.cascara.common.lang.processor;

import java.net.URI;

import io.github.qishr.cascara.common.lang.StructuredDocument;
import io.github.qishr.cascara.common.lang.exception.ParserException;

public interface Parser<T extends StructuredDocument> extends Processor {
    T parse(String text) throws ParserException;
    T parse(String text, URI uri) throws ParserException;
}
