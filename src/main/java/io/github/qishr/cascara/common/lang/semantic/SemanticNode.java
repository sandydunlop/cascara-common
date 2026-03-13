package io.github.qishr.cascara.common.lang.semantic;

import java.nio.file.Path;
import java.util.List;

public interface SemanticNode {
    String getId();
    String getDisplayName();
    Path getSourcePath();
    int getLine();
    int getColumn();
    List<? extends SemanticNode> getChildren();
}
