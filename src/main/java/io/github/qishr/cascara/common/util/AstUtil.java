package io.github.qishr.cascara.common.util;

import io.github.qishr.cascara.common.lang.StructuredDocument;
import io.github.qishr.cascara.common.lang.ast.AstNode;
import io.github.qishr.cascara.common.lang.ast.MapAstNode;
import io.github.qishr.cascara.common.lang.ast.MapEntryAstNode;
import io.github.qishr.cascara.common.lang.ast.ScalarAstNode;
import io.github.qishr.cascara.common.lang.ast.SequenceAstNode;

public class AstUtil {
    public static void printAst(AstNode ast, int depth) {
        if (ast instanceof StructuredDocument astDoc) {
            System.out.println("  ".repeat(depth) + "StructuredDocument:");
            printAst(astDoc.getRoot(), depth + 1);
        } else if (ast instanceof MapAstNode astMap) {
            System.out.println("  ".repeat(depth) + "MapAstNode:");
            for (Object entry : astMap.getEntries()) {
                if (entry instanceof AstNode astNode) {
                    printAst(astNode, depth + 1);
                }
            }
        } else if (ast instanceof SequenceAstNode astSeq) {
            System.out.println("  ".repeat(depth) + "SequenceAstNode:");
            for (Object element : astSeq.getElements()) {
                System.out.println("  ".repeat(depth) + "  Element:");
                if (element instanceof AstNode astNode) {
                    printAst(astNode, depth + 2);
                }
            }
        } else if (ast instanceof ScalarAstNode astScalar) {
            System.out.println("  ".repeat(depth) + "ScalarAstNode: " + astScalar.asString());
        } else if (ast instanceof MapEntryAstNode astMapEntry) {
            System.out.println("  ".repeat(depth) + "MapEntryAstNode:");
            System.out.println("  ".repeat(depth) + "  Key:");
            printAst(astMapEntry.getKey(), depth + 2);
            System.out.println("  ".repeat(depth) + "  Value:");
            printAst(astMapEntry.getValue(), depth + 2);
        } else {
            System.err.println("Unknown AST node");
        }
    }
}
