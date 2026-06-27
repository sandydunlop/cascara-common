package io.github.qishr.cascara.common.lang.streaming;

public interface Event {
    EventType getType();
    String getText();
    long getLineNumber();
    long getColumnNumber();
}