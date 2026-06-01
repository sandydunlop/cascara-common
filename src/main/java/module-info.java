module cascara.common {
    uses io.github.qishr.cascara.common.service.ServiceProvider;

    exports io.github.qishr.cascara.common.lang.annotation;
    exports io.github.qishr.cascara.common.diagnostic;
    exports io.github.qishr.cascara.common.lang;
    exports io.github.qishr.cascara.common.lang.ast;
    exports io.github.qishr.cascara.common.lang.exception;
    exports io.github.qishr.cascara.common.lang.processor;
    exports io.github.qishr.cascara.common.lang.semantic;
    exports io.github.qishr.cascara.common.lang.simple;
    exports io.github.qishr.cascara.common.lang.token;
    exports io.github.qishr.cascara.common.semver;
    exports io.github.qishr.cascara.common.service;
    exports io.github.qishr.cascara.common.spi;
    exports io.github.qishr.cascara.common.type;
    exports io.github.qishr.cascara.common.util;

    opens io.github.qishr.cascara.common.util;

    provides io.github.qishr.cascara.common.type.TypeDescriptor
        with io.github.qishr.cascara.common.type.LocalDateTimeDescriptor,
             io.github.qishr.cascara.common.type.UriTypeDescriptor,
             io.github.qishr.cascara.common.type.PathTypeDescriptor;

}
