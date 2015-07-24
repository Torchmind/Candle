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
package com.torchmind.candle;

import com.torchmind.candle.api.*;
import com.torchmind.candle.node.property.*;
import com.torchmind.candle.node.property.array.*;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

/**
 * Provides methods of serializing candle documents or nodes.
 * @author Johannes Donath
 */
public class CandleSerializer {
        private String indentation = "\t";
        private String newline = System.lineSeparator ();
        private boolean prettyPrint = true;

        /**
         * Generates an indentation of a certain level.
         * @param level The level.
         * @return The indentation.
         */
        @Nonnull
        protected String indent (@Nonnegative int level) {
                if (level == 0) return "";

                StringBuilder buffer = new StringBuilder ();

                for (int i = 1; i <= level; i++) {
                        buffer.append (this.indentation ());
                }

                return buffer.toString ();
        }

        /**
         * Retrieves the current indentation character.
         * @return The character.
         */
        @Nullable
        public String indentation () {
                return this.indentation;
        }

        /**
         * Sets the indentation character.
         * @param indentation The character or character sequence.
         * @return The serializer.
         */
        @Nonnull
        public CandleSerializer indentation (@Nullable String indentation) {
                this.indentation = indentation;
                return this;
        }

        /**
         * Retrieves the newline character sequence.
         * @return The sequence.
         */
        @Nonnull
        public String newline () {
                return this.newline;
        }

        /**
         * Defines the newline character sequence.
         * @param newline The character sequence.
         * @return The serializer.
         */
        @Nonnull
        public CandleSerializer newline (@Nonnull String newline) {
                this.newline = newline;
                return this;
        }

        /**
         * Checks whether pretty print is enabled.
         * When pretty print is disabled, all unneeded newlines are omitted.
         * @return True if pretty print is enabled
         */
        public boolean prettyPrint () {
                return this.prettyPrint;
        }

        /**
         * Enables/Disables pretty print.
         * When pretty print is disabled, all unneeded newlines are omitted.
         * @param prettyPrint True if enabled.
         * @return The serializer.
         */
        @Nonnull
        public CandleSerializer prettyPrint (boolean prettyPrint) {
                this.prettyPrint = prettyPrint;
                return this;
        }

        /**
         * Serializes an entire document into a file.
         * @param node The root document node.
         * @param file The file.
         * @return The serializer.
         * @throws java.io.IOException when writing fails.
         */
        @Nonnull
        public CandleSerializer serialize (@Nonnull IDocumentNode node, @Nonnull File file) throws IOException {
                try (FileOutputStream outputStream = new FileOutputStream (file)) {
                        return this.serialize (node, outputStream);
                }
        }

        /**
         * Serializes an entire document into a stream.
         * @param node The root document node.
         * @param outputStream The output stream.
         * @return The serializer.
         * @throws java.io.IOException when writing fails.
         */
        @Nonnull
        public CandleSerializer serialize (@Nonnull IDocumentNode node, @Nonnull OutputStream outputStream) throws IOException {
                try (OutputStreamWriter writer = new OutputStreamWriter (outputStream, StandardCharsets.UTF_8)) {
                        return this.serialize (node, writer);
                }
        }

        /**
         * Serializes an entire document into a writer.
         * @param node The root document node.
         * @param writer The writer.
         * @return The serializer.
         * @throws java.io.IOException when writing fails.
         */
        @Nonnull
        public CandleSerializer serialize (@Nonnull IDocumentNode node, @Nonnull Writer writer) throws IOException {
                writer.write (this.serialize (node));
                return this;
        }

        /**
         * Serializes an entire document into a string.
         * @param node The root document node.
         * @return The serializer.
         */
        @Nonnull
        public String serialize (@Nonnull IDocumentNode node) {
                StringBuilder buffer = new StringBuilder ();

                node.forEach (n -> {
                        buffer.append (this.serialize (n));
                        buffer.append (this.newline ());
                });

                return buffer.toString ();
        }

        /**
         * Serializes an object node into a file.
         * @param node The object node.
         * @param file The output file.
         * @return The serializer.
         * @throws java.io.IOException when writing fails.
         */
        @Nonnull
        public CandleSerializer serialize (@Nonnull IObjectNode node, @Nonnull File file) throws IOException {
                try (FileOutputStream outputStream = new FileOutputStream (file)) {
                        return this.serialize (node, outputStream);
                }
        }

        /**
         * Serializes an object node into a stream.
         * @param node The object node.
         * @param outputStream The output stream.
         * @return The serializer.
         * @throws java.io.IOException when writing fails.
         */
        @Nonnull
        public CandleSerializer serialize (@Nonnull IObjectNode node, @Nonnull OutputStream outputStream) throws IOException {
                try (OutputStreamWriter writer = new OutputStreamWriter (outputStream, StandardCharsets.UTF_8)) {
                        return this.serialize (node, writer);
                }
        }

        /**
         * Serializes an object node into a writer.
         * @param node The object node.
         * @param writer The writer.
         * @return The serializer.
         * @throws java.io.IOException when writing fails.
         */
        @Nonnull
        public CandleSerializer serialize (@Nonnull IObjectNode node, @Nonnull Writer writer) throws IOException {
                writer.write (this.serialize (node));
                return this;
        }

        /**
         * Serializes an object node.
         * @param node The node.
         * @return The serialized node.
         */
        @Nonnull
        public String serialize (@Nonnull IObjectNode node) {
                return this.serialize (node, 0);
        }

        /**
         * Serializes an object node.
         * @param node The node.
         * @param level The lvel.
         * @return The serialized node.
         */
        @Nonnull
        protected String serialize (@Nonnull IObjectNode node, @Nonnegative int level) {
                StringBuilder buffer = new StringBuilder ();

                buffer.append (this.indent (level));
                buffer.append (node.name ());
                if (this.prettyPrint) buffer.append (" ");
                buffer.append ("{");
                if (this.prettyPrint) buffer.append (this.newline ());

                node.forEach (n -> {
                        buffer.append (this.serialize (n, (level + 1)));
                        if (this.prettyPrint () || (n instanceof ICommentNode && !((ICommentNode) n).isMultiline ())) buffer.append (this.newline ());
                });

                buffer.append (this.indent (level));
                buffer.append ("}");
                return buffer.toString ();
        }

        /**
         * Serializes a single node into a file.
         * @param node The node.
         * @param file The output file.
         * @return The serializer.
         * @throws java.lang.IllegalArgumentException when no method for serialization is known for a custom node.
         * @throws java.io.IOException when writing fails.
         */
        @Nonnull
        public CandleSerializer serialize (@Nonnull INode node, @Nonnull File file) throws IllegalArgumentException, IOException {
                try (FileOutputStream outputStream = new FileOutputStream (file)) {
                        return this.serialize (node, outputStream);
                }
        }

        /**
         * Serializes a single node into a stream.
         * @param node The node.
         * @param outputStream The output stream.
         * @return The serializer.
         * @throws java.lang.IllegalArgumentException when no method for serialization is known for a custom node.
         * @throws java.io.IOException when writing fails.
         */
        @Nonnull
        public CandleSerializer serialize (@Nonnull INode node, @Nonnull OutputStream outputStream) throws IllegalArgumentException, IOException {
                try (OutputStreamWriter writer = new OutputStreamWriter (outputStream)) {
                        return this.serialize (node, writer);
                }
        }

        /**
         * Serializes a single node into a writer.
         * @param node The node.
         * @param writer The writer.
         * @return The serializer.
         * @throws java.lang.IllegalArgumentException when no method for serialization is known for a custom node.
         * @throws java.io.IOException when writing fails.
         */
        @Nonnull
        public CandleSerializer serialize (@Nonnull INode node, @Nonnull Writer writer) throws IllegalArgumentException, IOException {
                writer.write (this.serialize (node));
                return this;
        }

        /**
         * Serializes a single node.
         * @param node The node.
         * @return The serializer.
         * @throws java.lang.IllegalArgumentException when no method for serialization is known for a custom node.
         */
        @Nonnull
        public String serialize (@Nonnull INode node) throws IllegalArgumentException {
                return this.serialize (node, 0);
        }

        /**
         * Serializes a single node on a certain level.
         * @param node The node.
         * @param level The level.
         * @return The serializer.
         * @throws java.lang.IllegalArgumentException when no method for serialization is known for a custom node.
         */
        @Nonnull
        protected String serialize (@Nonnull INode node, @Nonnegative int level) throws IllegalArgumentException {
                try {
                        Method method = null;

                        try {
                                method = this.getClass ().getDeclaredMethod ("serialize", node.getClass (), int.class);
                        } catch (NoSuchMethodException ex) {
                                for (Class<?> type : node.getClass ().getInterfaces ()) {
                                        if (INode.class.equals (type) || INamedNode.class.equals (type) || IPropertyNode.class.equals (type)) continue;
                                        if (!INode.class.isAssignableFrom (type)) continue;

                                        try {
                                                method = this.getClass ().getDeclaredMethod ("serialize", type, int.class);
                                        } catch (NoSuchMethodException ignore) { }
                                }

                                if (method == null) throw ex;
                        }

                        method.setAccessible (true);
                        return ((String) method.invoke (this, node, level));
                } catch (NoSuchMethodException ex) {
                        throw new IllegalArgumentException ("Unsupported node type: " + node.getClass (), ex);
                } catch (IllegalAccessException | InvocationTargetException ex) {
                        throw new IllegalArgumentException ("Serialization failed: " + node.getClass (), ex);
                }
        }

        /**
         * Serializes a comment node on a certain level.
         * @param node The node.
         * @param level The level.
         * @return The serialized comment.
         */
        @Nonnull
        protected String serialize (@Nonnull ICommentNode node, @Nonnegative int level) {
                StringBuilder builder = new StringBuilder ();
                builder.append (this.indent (level));

                if (node.isMultiline ()) {
                        builder.append ("/*");
                        builder.append (node.text ());
                        builder.append ("*/");

                        return builder.toString ();
                }

                builder.append ("//");
                builder.append (node.text ());
                return builder.toString ();
        }

        /**
         * Serializes a named node prefix.
         * @param node The node.
         * @param level The level.
         * @return The serialized node prefix.
         */
        @Nonnull
        protected String serialize (@Nonnull INamedNode node, @Nonnegative int level) {
                return this.indent (level) + node.name ();
        }

        /**
         * Serializes a property node prefix.
         * @param node The node.
         * @param level The level.
         * @return The serialized node prefix.
         */
        @Nonnull
        protected String serialize (@Nonnull IPropertyNode node, @Nonnegative int level) {
                StringBuilder buffer = new StringBuilder ();

                buffer.append (this.serialize (((INamedNode) node), level));
                if (this.prettyPrint ())
                        buffer.append (" = ");
                else
                        buffer.append ("=");

                return buffer.toString ();
        }

        /**
         * Serializes a boolean node on a certain level.
         * @param node The node.
         * @param level The level.
         * @return The serialized node.
         */
        @Nonnull
        protected String serialize (@Nonnull BooleanPropertyNode node, @Nonnegative int level) {
                StringBuilder buffer = new StringBuilder ();

                buffer.append (this.serialize (((IPropertyNode) node), level));
                buffer.append (Boolean.toString (node.value ()));

                return buffer.toString ();
        }

        /**
         * Serializes a default node on a certain level.
         * @param node The node.
         * @param level The level.
         * @return The serialized node.
         */
        @Nonnull
        protected String serialize (@Nonnull DefaultPropertyNode node, @Nonnegative int level) {
                StringBuilder buffer = new StringBuilder ();

                buffer.append (this.serialize (((IPropertyNode) node), level));
                buffer.append ("default");

                return buffer.toString ();
        }

        /**
         * Serializes an enum node on a certain level.
         * @param node The node.
         * @param level The level.
         * @return The serialized node.
         */
        @Nonnull
        protected String serialize (@Nonnull EnumPropertyNode node, @Nonnegative int level) {
                StringBuilder buffer = new StringBuilder ();

                buffer.append (this.serialize (((IPropertyNode) node), level));
                buffer.append (node.value ());

                return buffer.toString ();
        }

        /**
         * Serializes a float node on a certain level.
         * @param node The node.
         * @param level The level.
         * @return The serialized node.
         */
        @Nonnull
        protected String serialize (@Nonnull FloatPropertyNode node, @Nonnegative int level) {
                StringBuilder buffer = new StringBuilder ();

                buffer.append (this.serialize (((IPropertyNode) node), level));
                buffer.append (Float.toString (node.value ())); // TODO: Support scientific notation properly

                return buffer.toString ();
        }

        /**
         * Serializes an integer node on a certain level.
         * @param node The node.
         * @param level The level.
         * @return The serialized node.
         */
        @Nonnull
        protected String serialize (@Nonnull IntegerPropertyNode node, @Nonnegative int level) {
                StringBuilder buffer = new StringBuilder ();

                buffer.append (this.serialize (((IPropertyNode) node), level));
                buffer.append (Integer.toString (node.value ())); // TODO: Support hex notation properly

                return buffer.toString ();
        }

        /**
         * Serializes a null node on a certain level.
         * @param node The node.
         * @param level The level.
         * @return The serialized node.
         */
        @Nonnull
        protected String serialize (@Nonnull NullPropertyNode node, @Nonnegative int level) {
                StringBuilder buffer = new StringBuilder ();

                buffer.append (this.serialize (((IPropertyNode) node), level));
                buffer.append ("null");

                return buffer.toString ();
        }

        /**
         * Serializes a string node on a certain level.
         * @param node The node.
         * @param level The level.
         * @return The serialized node.
         */
        @Nonnull
        protected String serialize (@Nonnull StringPropertyNode node, @Nonnegative int level) {
                StringBuilder buffer = new StringBuilder ();

                buffer.append (this.serialize (((IPropertyNode) node), level));
                buffer.append ("\"" + this.escapeString (node.value ()) + "\"");

                return buffer.toString ();
        }

        /**
         * Serializes a boolean array node on a certain level.
         * @param node The node.
         * @param level The level.
         * @return The serialized node.
         */
        @Nonnull
        protected String serialize (@Nonnull BooleanArrayPropertyNode node, @Nonnegative int level) {
                StringBuilder buffer = new StringBuilder ();

                buffer.append (this.serialize (((IPropertyNode) node), level));
                buffer.append ("[");
                if (this.prettyPrint ()) buffer.append (this.newline ());

                for (boolean value : node.array ()) {
                        if (this.prettyPrint ()) buffer.append (this.indent ((level + 1)));
                        buffer.append (Boolean.toString (value));
                        buffer.append (",");
                        if (this.prettyPrint ()) buffer.append (this.newline ());
                }

                if (this.prettyPrint ()) buffer.append (this.indent (level));
                buffer.append ("]");

                return buffer.toString ();
        }

        /**
         * Serializes an enum array node on a certain level.
         * @param node The node.
         * @param level The level.
         * @return The serialized node.
         */
        @Nonnull
        protected String serialize (@Nonnull EnumArrayPropertyNode node, @Nonnegative int level) {
                StringBuilder buffer = new StringBuilder ();

                buffer.append (this.serialize (((IPropertyNode) node), level));
                buffer.append ("[");
                if (this.prettyPrint ()) buffer.append (this.newline ());

                for (String value : node.array ()) {
                        if (this.prettyPrint ()) buffer.append (this.indent ((level + 1)));

                        if (value != null)
                                buffer.append (value);
                        else
                                buffer.append ("null");

                        buffer.append (",");

                        if (this.prettyPrint ()) buffer.append (this.newline ());
                }

                if (this.prettyPrint ()) buffer.append (this.indent (level));
                buffer.append ("]");

                return buffer.toString ();
        }

        /**
         * Serializes a float array node on a certain level.
         * @param node The node.
         * @param level The level.
         * @return The serialized node.
         */
        @Nonnull
        protected String serialize (@Nonnull FloatArrayPropertyNode node, @Nonnegative int level) {
                StringBuilder buffer = new StringBuilder ();

                buffer.append (this.serialize (((IPropertyNode) node), level));
                buffer.append ("[");
                if (this.prettyPrint ()) buffer.append (this.newline ());

                for (float value : node.array ()) {
                        if (this.prettyPrint ()) buffer.append (this.indent ((level + 1)));
                        buffer.append (Float.toString (value));
                        buffer.append (",");
                        if (this.prettyPrint ()) buffer.append (this.newline ());
                }

                if (this.prettyPrint ()) buffer.append (this.indent (level));
                buffer.append ("]");

                return buffer.toString ();
        }

        /**
         * Serializes an integer array node on a certain level.
         * @param node The node.
         * @param level The level.
         * @return The serialized node.
         */
        @Nonnull
        protected String serialize (@Nonnull IntegerArrayPropertyNode node, @Nonnegative int level) {
                StringBuilder buffer = new StringBuilder ();

                buffer.append (this.serialize (((IPropertyNode) node), level));
                buffer.append ("[");
                if (this.prettyPrint ()) buffer.append (this.newline ());

                for (int value : node.array ()) {
                        if (this.prettyPrint ()) buffer.append (this.indent ((level + 1)));
                        buffer.append (Integer.toString (value));
                        buffer.append (",");
                        if (this.prettyPrint ()) buffer.append (this.newline ());
                }

                if (this.prettyPrint ()) buffer.append (this.indent (level));
                buffer.append ("]");

                return buffer.toString ();
        }

        /**
         * Serializes a null array node on a certain level.
         * @param node The node.
         * @param level The level.
         * @return The serialized node.
         */
        @Nonnull
        protected String serialize (@Nonnull NullArrayPropertyNode node, @Nonnegative int level) {
                StringBuilder buffer = new StringBuilder ();

                buffer.append (this.serialize (((IPropertyNode) node), level));
                buffer.append ("[");
                if (this.prettyPrint ()) buffer.append (" ");
                buffer.append ("]");

                return buffer.toString ();
        }

        /**
         * Serializes a string array node on a certain level.
         * @param node The node.
         * @param level The level.
         * @return The serialized node.
         */
        @Nonnull
        protected String serialize (@Nonnegative StringArrayPropertyNode node, @Nonnegative int level) {
                StringBuilder buffer = new StringBuilder ();

                buffer.append (this.serialize (((IPropertyNode) node), level));
                buffer.append ("[");
                if (this.prettyPrint ()) buffer.append (this.newline ());

                for (String value : node.array ()) {
                        if (this.prettyPrint ()) buffer.append (this.indent ((level + 1)));

                        if (value != null)
                                buffer.append ("\"" + this.escapeString (value) + "\"");
                        else
                                buffer.append ("null");

                        buffer.append (",");

                        if (this.prettyPrint ()) buffer.append (this.newline ());
                }

                if (this.prettyPrint ()) buffer.append (this.indent (level));
                buffer.append ("]");

                return buffer.toString ();
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        protected String escapeString (@Nonnull String text) {
                text = text.replace ("\\", "\\\\");
                text = text.replace ("\"", "\\\"");
                text = text.replace ("\b", "\\b");
                text = text.replace ("\f", "\\f");
                text = text.replace ("\n", "\\n");
                text = text.replace ("\r", "\\r");
                text = text.replace ("\t", "\\t");

                StringBuilder builder = new StringBuilder ();

                for (int i = 0; i < text.length (); i++) {
                        char element = text.charAt (i);

                        if (((int) element) < 0x20 || ((int) element) > 0x7E)
                                builder.append (String.format ("\\u%4X", ((int) element)));
                        else
                                builder.append (element);
                }

                return builder.toString ();
        }
}
