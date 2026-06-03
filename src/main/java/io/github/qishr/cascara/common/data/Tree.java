package io.github.qishr.cascara.common.data;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class Tree<T extends TreeData<T,V>,V> {
    private static final String NL = "\n";
    private static final int TAB_SIZE = 2;

    private TreeData<T,V> root;
    private boolean renderValues;

    /// Constructs an empty Tree.
    public Tree() {
        // Nothing to do here
    }

    public void setRenderValues(boolean v) {
        renderValues = v;
    }

    public void setRoot(TreeData<T,V> node) {
        root = node;
    }

    public void render(Writer writer) throws IOException {
        render(writer, root, 0);
    }

    public void render(Writer writer, TreeData<T,V> node, int indent) throws IOException {
        writer.write(" ".repeat(TAB_SIZE * indent));
        writer.write(node.getNodeName());
        writer.write(NL);

        if (renderValues && node.getPhysicalValue() != null) {
            renderValue(writer, node.getPhysicalValue(), indent + 1);
        }

        for (TreeData<T,V> child : node.getChildren()) {
            render(writer, child, indent + 1);
        }
    }

    /// Renders TreeData.getValue()
    private void renderValue(Writer writer, V value, int indent) throws IOException {
        if (value instanceof List list && !list.isEmpty()) {
            if (list.getFirst() instanceof TableData firstRow) {
                @SuppressWarnings("unchecked")
				List<TableData> rows = list;
                renderTable(writer, firstRow.getValuesMap().values().size(), rows, indent);
            }
        }
    }

    private void renderTable(Writer writer, int columns, List<TableData> rows, int indent) throws IOException {
        Table table = new Table();
        table.setShowHeaders(false);

        TableData firstRow = rows.getFirst();
        for (String columnName : firstRow.getValuesMap().keySet()) {
            table.addColumn(columnName);
        }

        // Add the data
        for (TableData row : rows) {
            table.addRow(row);
        }
        table.render(writer, TAB_SIZE * indent);
    }
}
