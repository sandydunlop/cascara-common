package io.github.qishr.cascara.common.type;

import io.github.qishr.cascara.common.lang.ast.MapAstNode;

public interface ScalarDescriptor extends TypeDescriptor {

    Object toType(String string);
    String toText(Object object);

    void toSchema(MapAstNode<?,?> node);

    public String getType();
    public String getFormat();
    public String getContentEncoding();
}
