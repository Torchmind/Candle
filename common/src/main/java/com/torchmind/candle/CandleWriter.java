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

import com.torchmind.candle.api.ICommentNode;
import com.torchmind.candle.api.IDocumentNode;
import com.torchmind.candle.api.IObjectNode;
import com.torchmind.candle.api.ITreeVisitor;
import com.torchmind.candle.api.property.*;
import com.torchmind.candle.api.property.array.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;

/**
 * Provides a solution for generating human-readable Candle configuration files from {@link com.torchmind.candle.api.INode}
 * implementations.
 *
 * @author Johannes Donath
 */
public class CandleWriter implements ITreeVisitor {
        public StringBuilder builder = new StringBuilder ();
        private String indentation = "\t";
        private String newline = System.lineSeparator ();
        private int currentLevel = 0;

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

                        if (((int) element) < 0x20 || ((int) element) > 0x7E) {
                                builder.append (String.format ("\\u%4X", ((int) element)));
                        } else { builder.append (element); }
                }

                return builder.toString ();
        }

        /**
         * Indents following text according to the current level.
         */
        protected void indent () {
                if (this.currentLevel == 0) { return; }
                if (this.indentation () == null) { return; }

                for (int i = 0; i < this.currentLevel; i++) {
                        this.builder.append (this.indentation ());
                }
        }

        /**
         * Retrieves the selected indentation character(s).
         *
         * @return The character(s).
         */
        @Nullable
        public String indentation () {
                return this.indentation;
        }

        /**
         * Sets the indentation character(s).
         *
         * @param indentation The character(s).
         * @return The serializer.
         */
        @Nonnull
        public CandleWriter indentation (@Nullable String indentation) {
                this.indentation = indentation;
                return this;
        }

        /**
         * Appends a new line.
         */
        protected void line () {
                this.builder.append (this.newline ());
        }

        /**
         * Retrieves the newline character(s).
         *
         * @return The character(s).
         */
        @Nonnull
        public String newline () {
                return this.newline;
        }

        /**
         * Sets the newline character(s).
         *
         * @param newline The character(s).
         * @return The serializer.
         */
        @Nonnull
        public CandleWriter newline (@Nonnull String newline) {
                this.newline = newline;
                return this;
        }

        /**
         * Resets the writer state.
         *
         * @return The writer.
         */
        @Nonnull
        public CandleWriter reset () {
                this.builder.setLength (0);
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visit (@Nonnull IObjectNode node) {
                node.accept (this);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitArrayPropertyNode (@Nonnull IDocumentNode document, @Nonnull IArrayPropertyNode node) {
                this.visitPropertyNode (document, ((IPropertyNode) node));

                // [
                this.builder.append ("[");
                this.line ();

                this.currentLevel++;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitArrayPropertyNode (@Nonnull IDocumentNode document, @Nonnull IBooleanArrayPropertyNode node) {
                for (boolean current : node.array ()) {
                        this.indent ();

                        // <true|false>,
                        this.builder.append (Boolean.toString (current));
                        this.builder.append (",");

                        this.line ();
                }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitArrayPropertyNode (@Nonnull IDocumentNode document, @Nonnull IEnumArrayPropertyNode node) {
                for (String current : node.array ()) {
                        this.indent ();

                        // ENUM_VALUE,
                        if (current == null) { this.builder.append ("null"); } else { this.builder.append (current); }
                        this.builder.append (",");

                        this.line ();
                }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitArrayPropertyNode (@Nonnull IDocumentNode document, @Nonnull IFloatArrayPropertyNode node) {
                for (float current : node.array ()) {
                        this.indent ();

                        // 0.0f,
                        this.builder.append (Float.toString (current));
                        this.builder.append (",");

                        this.line ();
                }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitArrayPropertyNode (@Nonnull IDocumentNode document, @Nonnull IIntegerArrayPropertyNode node) {
                for (int current : node.array ()) {
                        this.indent ();

                        // 0,
                        this.builder.append (Integer.toString (current));
                        this.builder.append (",");

                        this.line ();
                }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitArrayPropertyNode (@Nonnull IDocumentNode document, @Nonnull INullArrayPropertyNode node) {
                // NO-OP as [] is sufficient for a null array.
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitArrayPropertyNode (@Nonnull IDocumentNode document, @Nonnull IStringArrayPropertyNode node) {
                for (String current : node.array ()) {
                        this.indent ();

                        // "<string>",
                        if (current == null) { this.builder.append ("null"); } else {
                                this.builder.append ("\"");
                                this.builder.append (this.escapeString (current));
                                this.builder.append ("\"");
                        }
                        this.builder.append (",");

                        this.line ();
                }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitArrayPropertyNodeEnd (@Nonnull IDocumentNode document, @Nonnull IArrayPropertyNode node) {
                this.currentLevel--;

                // ]
                this.indent ();
                this.builder.append ("]");
                this.line ();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitCommentNode (@Nonnull IDocumentNode document, @Nonnull ICommentNode node) {
                this.indent ();

                if (node.isMultiline ()) {
                        // /* <text> */
                        this.builder.append ("/*");
                        this.builder.append (node.text ());
                        this.builder.append ("*/");
                } else {
                        // // <text>
                        this.builder.append ("//");
                        this.builder.append (node.text ());
                }

                this.line ();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitDocumentNode (@Nonnull IDocumentNode node) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitDocumentNodeEnd (@Nonnull IDocumentNode document) {
                this.currentLevel = 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitObjectNode (@Nonnull IDocumentNode document, @Nonnull IObjectNode node) {
                this.indent ();

                // <name> {
                this.builder.append (node.name ());
                this.builder.append (" {");
                this.line ();

                this.currentLevel++;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitObjectNodeEnd (@Nonnull IDocumentNode document, @Nonnull IObjectNode node) {
                this.currentLevel--;
                this.indent ();

                // }
                this.builder.append ("}");
                this.line ();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitPropertyNode (@Nonnull IDocumentNode document, @Nonnull IPropertyNode node) {
                this.indent ();

                // property =
                this.builder.append (node.name ());
                this.builder.append (" = ");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitPropertyNode (@Nonnull IDocumentNode document, @Nonnull IBooleanPropertyNode node) {
                this.builder.append (Boolean.toString (node.value ()));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitPropertyNode (@Nonnull IDocumentNode document, @Nonnull IDefaultPropertyNode node) {
                this.builder.append ("default");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitPropertyNode (@Nonnull IDocumentNode document, @Nonnull IEnumPropertyNode node) {
                this.builder.append (node.value ());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitPropertyNode (@Nonnull IDocumentNode document, @Nonnull IFloatPropertyNode node) {
                this.builder.append (Float.toString (node.value ()));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitPropertyNode (@Nonnull IDocumentNode document, @Nonnull IIntegerPropertyNode node) {
                this.builder.append (Integer.toString (node.value ()));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitPropertyNode (@Nonnull IDocumentNode document, @Nonnull INullPropertyNode node) {
                this.builder.append ("null");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitPropertyNode (@Nonnull IDocumentNode document, @Nonnull IStringPropertyNode node) {
                this.builder.append ("\"");
                this.builder.append (this.escapeString (node.value ()));
                this.builder.append ("\"");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitPropertyNodeEnd (@Nonnull IDocumentNode document, @Nonnull IPropertyNode node) {
                this.line ();
        }

        /**
         * Writes an object tree.
         *
         * @param file The output file.
         * @param node The root node.
         * @return The writer.
         *
         * @throws java.io.IOException when writing fails.
         */
        @Nonnull
        public CandleWriter write (@Nonnull File file, @Nonnull IObjectNode node) throws IOException {
                try (FileOutputStream outputStream = new FileOutputStream (file)) {
                        return this.write (outputStream, node);
                }
        }

        /**
         * Writes an object tree.
         *
         * @param outputStream The output stream.
         * @param node         The root node.
         * @return The writer.
         *
         * @throws java.io.IOException when writing fails.
         */
        @Nonnull
        public CandleWriter write (@Nonnull OutputStream outputStream, @Nonnull IObjectNode node) throws IOException {
                try (OutputStreamWriter writer = new OutputStreamWriter (outputStream)) {
                        return this.write (writer, node);
                }
        }

        /**
         * Writes an object tree.
         *
         * @param writer The writer.
         * @param node   The root node.
         * @return The writer.
         *
         * @throws java.io.IOException when writing fails.
         */
        @Nonnull
        public CandleWriter write (@Nonnull Writer writer, @Nonnull IObjectNode node) throws IOException {
                this.visit (node);
                return this.write (writer);
        }

        /**
         * Writes the stored node tree and resets.
         *
         * @param writer The writer.
         * @return The writer.
         *
         * @throws java.io.IOException when writing fails.
         */
        @Nonnull
        public CandleWriter write (@Nonnull Writer writer) throws IOException {
                writer.write (this.toString ());
                return this.reset ();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString () {
                return this.builder.toString ();
        }
}
