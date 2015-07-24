/*
 * Copyright 2015 Johannes Donath <johannesd@torchmind.com>
 * and other copyright owners as documented in the project's IP log.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.torchmind.candle.api;

import com.torchmind.candle.api.property.IPropertyNode;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Represents an object node.
 *
 * @author Johannes Donath
 */
public interface IObjectNode extends INamedNode, Iterable<INode> {

        /**
         * Appends a node to the tree.
         *
         * @param node The node.
         * @return This node.
         */
        @Nonnull
        IObjectNode append (@Nonnull INode node);

        /**
         * Retrieves a list of children within the tree.
         *
         * @return The list.
         */
        @Nonnull
        List<INode> children ();

        /**
         * Retrieves a list of children with a certain type within the tree.
         *
         * @param nodeType The node type.
         * @param <T>      The node type.
         * @return The list.
         */
        @Nonnull
        <T extends INode> List<T> children (@Nonnull Class<T> nodeType);

        /**
         * Clears the node tree.
         *
         * @return The node.
         */
        @Nonnull
        IObjectNode clear ();

        /**
         * Performs the given action for each child element of a certain type.
         *
         * @param nodeType The node type.
         * @param consumer The consumer (action).
         * @param <T>      The node type.
         * @return This node.
         */
        @Nonnull
        default <T extends INode> IObjectNode forEach (@Nonnull Class<T> nodeType, @Nonnull Consumer<? super T> consumer) {
                this.children (nodeType).forEach (consumer::accept);
                return this;
        }

        /**
         * Retrieves a node within the tree.
         *
         * @param name The node name.
         * @return The node.
         *
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nonnull
        INode get (@Nonnull String name) throws NoSuchElementException;

        /**
         * Retrieves a node of a certain type within the tree.
         *
         * @param name     The name of the node.
         * @param nodeType The node type.
         * @param <T>      The node type.
         * @return The node.
         *
         * @throws java.lang.IllegalStateException  when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nonnull
        <T extends INode> T get (@Nonnull String name, @Nonnull Class<T> nodeType) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves a boolean value from within the tree.
         *
         * @param name The node name.
         * @return The boolean value.
         *
         * @throws java.lang.IllegalStateException  when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        boolean getBoolean (@Nonnull String name) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves a boolean value from within the tree.
         *
         * @param name         The node name.
         * @param defaultValue The default value.
         * @return The boolean value.
         *
         * @throws java.lang.IllegalStateException when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        boolean getBoolean (@Nonnull String name, boolean defaultValue) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves a boolean array from within the tree.
         *
         * @param name The node name.
         * @return The boolean array.
         *
         * @throws java.lang.IllegalStateException  when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nullable
        boolean[] getBooleanArray (@Nonnull String name) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves a boolean array from within the tree.
         *
         * @param name         The node name.
         * @param defaultValue The default array.
         * @return The boolean array.
         *
         * @throws java.lang.IllegalStateException when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nullable
        boolean[] getBooleanArray (@Nonnull String name, @Nullable boolean[] defaultValue) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves an enum value from within the tree.
         *
         * @param name The node name.
         * @return The raw enum value.
         *
         * @throws java.lang.IllegalStateException  when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nullable
        String getEnum (@Nonnull String name) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves an enum value from within the tree.
         *
         * @param name         The node name.
         * @param defaultValue The raw default value.
         * @return The raw enum value.
         *
         * @throws java.lang.IllegalStateException when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nullable
        String getEnum (@Nonnull String name, @Nullable String defaultValue) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves an enum value from within the tree.
         *
         * @param name     The node name.
         * @param enumType The enum type.
         * @param <T>      The enum type.
         * @return The enum value.
         *
         * @throws java.lang.IllegalStateException  when the node type differs from the expected type or the value was not found within the enum type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nullable
        <T extends Enum> T getEnum (@Nonnull String name, @Nonnull Class<T> enumType) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves an enum value from within the tree.
         *
         * @param name         The node name.
         * @param defaultValue The default value.
         * @param <T>          The enum type.
         * @return The enum value.
         *
         * @throws java.lang.IllegalStateException when the node type differs from the expected type or the value was not found within the enum type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nullable
        <T extends Enum> T getEnum (@Nonnull String name, @Nonnull T defaultValue) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves an enum value from within the tree.
         *
         * @param name         The node name.
         * @param defaultValue The default value.
         * @param enumType     The enum type.
         * @param <T>          The enum type.
         * @return The enum value.
         *
         * @throws java.lang.IllegalStateException when the node type differs from the expected type or the value was not found within the enum type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nullable
        <T extends Enum> T getEnum (@Nonnull String name, @Nullable T defaultValue, @Nonnull Class<T> enumType) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves an enum array from within the tree.
         *
         * @param name The node name.
         * @return The raw enum array.
         *
         * @throws java.lang.IllegalStateException  when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nullable
        String[] getEnumArray (@Nonnull String name) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves an enum array from within the tree.
         *
         * @param name         The node name.
         * @param defaultValue The raw default array.
         * @return The raw enum array.
         *
         * @throws java.lang.IllegalStateException  when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nullable
        String[] getEnumArray (@Nonnull String name, @Nullable String[] defaultValue) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves an enum array from within the tree.
         *
         * @param name     The node name.
         * @param enumType The enum type.
         * @param <T>      The enum type.
         * @return The enum array.
         *
         * @throws java.lang.IllegalStateException  when the node type differs from the expected type or one or more values were not found within the enum type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nullable
        <T extends Enum> T[] getEnumArray (@Nonnull String name, @Nullable Class<T> enumType) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves an enum array from within the tree.
         *
         * @param name         The node name.
         * @param defaultValue The default array.
         * @param enumType     The enum type.
         * @param <T>          The enum type.
         * @return The enum array.
         *
         * @throws java.lang.IllegalStateException when the node type differs from the expected type or one or more values were not found within the enum type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nullable
        <T extends Enum> T[] getEnumArray (@Nonnull String name, @Nullable T[] defaultValue, @Nonnull Class<T> enumType) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves a float value from within the tree.
         *
         * @param name The node name.
         * @return The float value.
         *
         * @throws java.lang.IllegalStateException  when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        float getFloat (@Nonnull String name) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves a float value from within the tree.
         *
         * @param name         The node name.
         * @param defaultValue The default value.
         * @return The float value.
         *
         * @throws java.lang.IllegalStateException when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        float getFloat (@Nonnull String name, float defaultValue) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves a float array from within the tree.
         *
         * @param name The node name.
         * @return The float array.
         *
         * @throws java.lang.IllegalStateException  when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nullable
        float[] getFloatArray (@Nonnull String name) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves a float array from within the tree.
         *
         * @param name         The node name.
         * @param defaultValue The default array.
         * @return The float array.
         *
         * @throws java.lang.IllegalStateException when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nullable
        float[] getFloatArray (@Nonnegative String name, @Nullable float[] defaultValue) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves an integer value from within the tree.
         *
         * @param name The node name.
         * @return The integer value.
         *
         * @throws java.lang.IllegalStateException  when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        int getInteger (@Nonnull String name) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves an integer value from within the tree.
         *
         * @param name         The node name.
         * @param defaultValue The default value.
         * @return The integer value.
         *
         * @throws java.lang.IllegalStateException when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        int getInteger (@Nonnull String name, int defaultValue) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves an integer array from within the tree.
         *
         * @param name The node name.
         * @return The integer array.
         *
         * @throws java.lang.IllegalStateException  when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nullable
        int[] getIntegerArray (@Nonnull String name) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves an integer array from within the tree.
         *
         * @param name         The node name.
         * @param defaultValue The default array.
         * @return The integer array.
         *
         * @throws java.lang.IllegalStateException when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nullable
        int[] getIntegerArray (@Nonnull String name, @Nullable int[] defaultValue) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves a property node.
         *
         * @param name The name of the node.
         * @return The node.
         *
         * @throws java.lang.IllegalStateException  when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nonnull
        IPropertyNode getProperty (@Nonnull String name) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves a property value.
         *
         * @param name      The node name.
         * @param nodeType  The node type.
         * @param ifPresent The function to execute if the node value is present.
         * @param <T>       The node type.
         * @param <R>       The return type.
         * @return The produced value.
         */
        @Nullable
        <T extends IPropertyNode, R> R getPropertyValue (@Nonnull String name, @Nonnull Class<T> nodeType, @Nonnull Function<T, R> ifPresent);

        /**
         * Retrieves a property value.
         *
         * @param name      The node name.
         * @param nodeType  The node type.
         * @param ifPresent The function to execute if the node value is present.
         * @param ifNull    The function to execute if the node value is null.
         * @param <T>       The node type.
         * @param <R>       The return type.
         * @return The produced value.
         */
        @Nonnull
        <T extends IPropertyNode, R> R getPropertyValue (@Nonnull String name, @Nonnull Class<T> nodeType, @Nonnull Function<T, R> ifPresent, @Nonnull Supplier<R> ifNull);

        /**
         * Retrieves a string value from within the tree.
         *
         * @param name The node name.
         * @return The string value.
         *
         * @throws java.lang.IllegalStateException  when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nullable
        String getString (@Nonnull String name) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves a string value from within the tree.
         *
         * @param name         The node name.
         * @param defaultValue The default value.
         * @return The string value.
         *
         * @throws java.lang.IllegalStateException when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nullable
        String getString (@Nonnegative String name, @Nullable String defaultValue) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves a string array from within the tree.
         *
         * @param name The node name.
         * @return The string array.
         *
         * @throws java.lang.IllegalStateException  when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nullable
        String[] getStringArray (@Nonnull String name) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves a string array from within the tree.
         *
         * @param name         The node name.
         * @param defaultValue The default array.
         * @return The string array.
         *
         * @throws java.lang.IllegalStateException when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nullable
        String[] getStringArray (@Nonnull String name, @Nullable String[] defaultValue) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves an unsigned float value from within the tree.
         *
         * @param name The node name.
         * @return The unsigned float value.
         *
         * @throws java.lang.IllegalStateException  when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nonnegative
        float getUnsignedFloat (@Nonnull String name) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves an unsigned float value from within the tree.
         *
         * @param name         The node name.
         * @param defaultValue The default value.
         * @return The unsigned float value.
         *
         * @throws java.lang.IllegalStateException when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nonnegative
        float getUnsignedFloat (@Nonnull String name, @Nonnegative float defaultValue) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves an unsigned float array from within the tree.
         *
         * @param name The node name.
         * @return The unsigned float array.
         *
         * @throws java.lang.IllegalStateException  when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nullable
        @Nonnegative
        float[] getUnsignedFloatArray (@Nonnull String name) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves an unsigned float array from within the tree.
         *
         * @param name         The node name.
         * @param defaultValue The default array.
         * @return The unsigned float array.
         *
         * @throws java.lang.IllegalStateException when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nullable
        @Nonnegative
        float[] getUnsignedFloatArray (@Nonnull String name, @Nullable @Nonnegative float[] defaultValue) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves an unsigned integer value from within the tree.
         *
         * @param name The node name.
         * @return The unsigned integer value.
         *
         * @throws java.lang.IllegalStateException  when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nonnegative
        int getUnsignedInteger (@Nonnull String name) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves an unsigned integer value from within the tree.
         *
         * @param name         The node name.
         * @param defaultValue The default value.
         * @return The unsigned integer value.
         *
         * @throws java.lang.IllegalStateException when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nonnegative
        int getUnsignedInteger (@Nonnegative String name, @Nonnegative int defaultValue) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves an unsigned integer array from within the tree.
         *
         * @param name The node name.
         * @return The unsigned integer array.
         *
         * @throws java.lang.IllegalStateException  when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nullable
        @Nonnegative
        int[] getUnsignedIntegerArray (@Nonnull String name) throws IllegalStateException, NoSuchElementException;

        /**
         * Retrieves an unsigned integer array from within the tree.
         *
         * @param name         The node name.
         * @param defaultValue The default array.
         * @return The unsigned integer array.
         *
         * @throws java.lang.IllegalStateException when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nullable
        @Nonnegative
        int[] getUnsignedIntegerArray (@Nonnull String name, @Nullable @Nonnegative int[] defaultValue) throws IllegalStateException, NoSuchElementException;

        /**
         * Inserts node {@code node} after node {@code after}.
         *
         * @param after The name of the node to insert behind of.
         * @param node  The node to insert.
         * @return This node.
         *
         * @throws java.util.NoSuchElementException when the node to insert in behind of is not present within the tree.
         */
        @Nonnull
        IObjectNode insertAfter (@Nonnull String after, @Nonnull INode node) throws NoSuchElementException;

        /**
         * Inserts node {@code node} after node {@code after}.
         *
         * @param after The node to insert behind of.
         * @param node  The node to insert.
         * @return This node.
         *
         * @throws java.util.NoSuchElementException when the node to insert in behind of is not present within the tree.
         */
        @Nonnull
        IObjectNode insertAfter (@Nonnull INode after, @Nonnull INode node) throws NoSuchElementException;

        /**
         * Inserts node {@code node} before node {@code before}.
         *
         * @param before The name of the node to insert in front of.
         * @param node   The node to insert.
         * @return This node.
         *
         * @throws java.util.NoSuchElementException when the node to insert in front of is not present within the tree.
         */
        @Nonnull
        IObjectNode insertBefore (@Nonnull String before, @Nonnull INode node) throws NoSuchElementException;

        /**
         * Inserts node {@code node} before node {@code before}.
         *
         * @param before The node to insert in front of.
         * @param node   The node to insert.
         * @return This node.
         *
         * @throws java.util.NoSuchElementException when the node to insert in front of is not present within the tree.
         */
        @Nonnull
        IObjectNode insertBefore (@Nonnull INode before, @Nonnull INode node) throws NoSuchElementException;

        /**
         * Checks whether a value is set to default.
         *
         * @param name The node name.
         * @return True if default.
         *
         * @throws java.lang.IllegalStateException  when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the element is not present within the tree.
         */
        boolean isDefault (@Nonnull String name) throws IllegalStateException, NoSuchElementException;

        /**
         * Checks whether the node represents the root document node.
         *
         * @return The node.
         */
        default boolean isDocumentRoot () {
                return false;
        }

        /**
         * Checks whether a value is null.
         *
         * @param name The node name.
         * @return True if null.
         *
         * @throws java.lang.IllegalStateException  when the node type differs from the expected type.
         * @throws java.util.NoSuchElementException when the element is not present within the tree.
         */
        boolean isNull (@Nonnull String name) throws IllegalStateException, NoSuchElementException;

        /**
         * Checks whether a node is present within the tree.
         *
         * @param name The node name.
         * @return True if present.
         */
        boolean isPresent (@Nonnull String name);

        /**
         * Checks whether a node of a certain type is present within the tree.
         *
         * @param name     The node name.
         * @param nodeType The node type.
         * @return True if present.
         */
        boolean isPresent (@Nonnull String name, @Nonnull Class<? extends INode> nodeType);

        /**
         * Retrieves an iterator for a certain node type within the tree.
         *
         * @param nodeType The node type.
         * @param <T>      The node type.
         * @return The iterator.
         */
        @Nonnull
        <T extends INode> Iterator<T> iterator (@Nonnull Class<T> nodeType);

        /**
         * Removes a child from the tree.
         *
         * @param name The node name.
         * @return This node.
         *
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nonnull
        IObjectNode remove (@Nonnull String name) throws NoSuchElementException;

        /**
         * Removes a child from the tree.
         *
         * @param node The node.
         * @return This node.
         *
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nonnull
        IObjectNode remove (@Nonnull INode node) throws NoSuchElementException;

        /**
         * Replaces a node within the tree.
         *
         * @param name        The node name.
         * @param replacement The replacement node.
         * @return This node.
         *
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nonnull
        IObjectNode replace (@Nonnull String name, @Nonnull INode replacement) throws NoSuchElementException;

        /**
         * Replaces a node within the tree.
         *
         * @param node        The node name.
         * @param replacement The replacement node.
         * @return This node.
         *
         * @throws java.util.NoSuchElementException when the node is not present within the tree.
         */
        @Nonnull
        IObjectNode replace (@Nonnull INode node, @Nonnull INode replacement) throws NoSuchElementException;

        /**
         * Retrieves the tree size.
         *
         * @return The size.
         */
        @Nonnegative
        int size ();

        /**
         * Retrieves a stream of children within the tree.
         *
         * @return The stream.
         */
        @Nonnull
        Stream<INode> stream ();

        /**
         * Retrieves stream of children with a certain type within the tree.
         *
         * @param nodeType The node type.
         * @param <T>      The node type.
         * @return The stream.
         */
        @Nonnull
        <T extends INode> Stream<T> stream (@Nonnull Class<T> nodeType);

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        default NodeType type () {
                return NodeType.OBJECT;
        }

        /**
         * Walks the node tree.
         * @param walker The walker.
         * @return The node.
         */
        @Nonnull
        IObjectNode walk (@Nonnull ITreeWalker walker);
}
