package io.github.qishr.cascara.common.service;

import io.github.qishr.cascara.common.util.CascaraRuntimeException;

public class CascaraServiceException extends CascaraRuntimeException {

    public CascaraServiceException(String message) {
        super(message);
    }

    public CascaraServiceException(String message, Throwable e) {
        super(message, e);
    }
}
