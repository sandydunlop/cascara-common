package io.github.qishr.cascara.common.lang.processor;

import io.github.qishr.cascara.common.lang.exception.ParserException;
import io.github.qishr.cascara.common.lang.streaming.Event;

import java.lang.AutoCloseable;

public interface PullParser extends Processor, AutoCloseable {
    /// Advances to the next event in the stream and returns it.
    /// Returns null (or an END_DOCUMENT event) when the stream is exhausted.
    Event nextEvent() throws ParserException;

    /// Checks if the parser can continue advancing.
    boolean hasNext() throws ParserException;
}