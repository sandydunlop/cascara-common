package io.github.qishr.cascara.common.semver;

///
public class SemVerException extends RuntimeException {
    public SemVerException(String msg) {
        super(msg);
    }

    public SemVerException(String msg, Throwable t) {
        super(msg, t);
    }
}
