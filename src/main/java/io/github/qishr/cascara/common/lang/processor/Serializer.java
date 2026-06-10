package io.github.qishr.cascara.common.lang.processor;

import io.github.qishr.cascara.common.lang.ast.AstNode;
import io.github.qishr.cascara.common.lang.exception.SerializerException;
import io.github.qishr.cascara.common.type.TypeDescriptor;

/// Handles the transformation between Java Objects (POJOs) and the AST or textual formats.
///
/// This interface provides a high-level API for data binding, allowing users to
/// move seamlessly between raw objects, structured ASTs, and the final string output.
///
/// @param <A> The specific subtype of AstNode used by this language implementation.
public interface Serializer<A extends AstNode> extends Processor {
    /// Converts a Java Object directly into its textual representation,
    // for example JSON or YAML.
    ///
    /// @param object The object to serialize.
    /// @return The formatted string (e.g., YAML or JSON).
    /// @throws SerializerException If serialization fails.
    String toText(Object object) throws SerializerException;

    /// Parses a string (e.g. JSON or YAML) directly into a Java Object
    // of the specified type.
    ///
    /// @param text  The source text to parse and deserialize.
    /// @param clazz The target type.
    /// @param <C>   The type of the resulting object.
    /// @return A populated instance of the requested class.
    /// @throws SerializerException If parsing or mapping fails.
    <C> C fromText(String text, Class<C> clazz) throws SerializerException;


    /// Transforms a Java Object into an AST representation.
    ///
    /// @param object The POJO or collection to transform.
    /// @return An AST representation of the provided object.
    /// @throws SerializerException If the object cannot be mapped to the AST.
    A toAst(Object object);

    /// Transforms an AST representation back into a specific Java type.
    ///
    /// @param astNode  The root AST node to interpret.
    /// @param clazz The target type to instantiate and populate.
    /// @param <C>   The type of the resulting object.
    /// @return A populated instance of the requested class.
    /// @throws SerializerException If the AST structure does not match the target type.
    <C> C fromAst(A astNode, Class<C> clazz);

    void addTypeDescriptor(TypeDescriptor typeDescriptor);
}
