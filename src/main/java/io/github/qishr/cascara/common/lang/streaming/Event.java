package io.github.qishr.cascara.common.lang.streaming;

public interface Event {
    EventType getType();
    String getContent();
    long getLineNumber();
    long getColumnNumber();
}