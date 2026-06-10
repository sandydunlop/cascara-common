package io.github.qishr.cascara.common.type;

import java.time.Instant;

import io.github.qishr.cascara.common.lang.ast.MapAstNode;

public class InstantTypeDescriptor extends AbstractScalarDescriptor {

    public static final String FORMAT = "date-time";
    public static final String SCHEMA_TYPE = "string";

    public InstantTypeDescriptor() {
        super(Instant.class, SCHEMA_TYPE, FORMAT);
    }

	@Override
	public void toSchema(MapAstNode<?,?> node) {
        node.put(SCHEMA_TYPE, SCHEMA_TYPE);
        node.put(SCHEMA_FORMAT, FORMAT);
	}

    @Override
    public Object toType(String text) {
        return null;
    }

    @Override
    public String toText(Object object) {
        if (object.getClass() == Instant.class) {
            return null;
        }
        return null;
    }
}
