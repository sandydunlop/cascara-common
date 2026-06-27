package io.github.qishr.cascara.common.lang.streaming;

@FunctionalInterface
public interface StreamHandler {
    void onEvent(Event event);
}