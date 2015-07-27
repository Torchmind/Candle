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

import com.torchmind.candle.api.IObjectNode;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;

/**
 * Provides a solution for generating human-readable Candle configuration files from {@link com.torchmind.candle.api.INode}
 * implementations.
 *
 * @author Johannes Donath
 */
public class CandleWriter extends ValidationVisitor {
        public StringBuilder builder = new StringBuilder ();
        private String indentation = "\t";
        private String newline = System.lineSeparator ();
        private int currentLevel = 0;
        private boolean inArray;

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
        public void visitArray () {
                super.visitArray ();


                // [
                this.builder.append ("[");
                this.line ();

                this.inArray = true;
                this.currentLevel++;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitArrayEnd () {
                super.visitArrayEnd ();

                this.inArray = false;
                this.currentLevel--;

                this.indent ();
                this.builder.append ("]");
                this.line ();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitBoolean (boolean value) {
                super.visitBoolean (value);

                if (this.inArray) { this.indent (); }
                this.builder.append (Boolean.toString (value));
                if (this.inArray) { this.builder.append (","); }
                this.line ();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitComment (@Nonnull String text) {
                super.visitComment (text);

                if (text.contains ("\n")) {
                        this.indent ();
                        this.builder.append ("/*" + text + "*/");
                        this.line ();

                        return;
                }

                this.indent ();
                this.builder.append ("//" + text);
                this.line ();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitDefault () {
                super.visitDefault ();

                this.builder.append ("default");
                this.line ();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitEnum (@Nonnull String value) {
                super.visitEnum (value);

                if (this.inArray) { this.indent (); }
                this.builder.append (value);
                if (this.inArray) { this.builder.append (","); }
                this.line ();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitFloat (float value) {
                super.visitFloat (value);

                if (this.inArray) { this.indent (); }
                this.builder.append (Float.toString (value));
                if (this.inArray) { this.builder.append (","); }
                this.line ();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitInteger (int value) {
                super.visitInteger (value);

                if (this.inArray) { this.indent (); }
                this.builder.append (Integer.toString (value));
                if (this.inArray) { this.builder.append (","); }
                this.line ();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitNull () {
                super.visitNull ();

                if (this.inArray) { this.indent (); }
                this.builder.append ("null");
                if (this.inArray) { this.builder.append (","); }
                this.line ();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitObject (@Nonnull String name) {
                super.visitObject (name);

                this.indent ();
                this.builder.append (name);
                this.builder.append (" {");
                this.line ();

                this.currentLevel++;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitObjectEnd () {
                super.visitObjectEnd ();

                this.currentLevel--;

                this.indent ();
                this.builder.append ("}");
                this.line ();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitProperty (@Nonnull String name) {
                super.visitProperty (name);

                this.indent ();
                this.builder.append (name);
                this.builder.append (" = ");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitString (@Nonnull String value) {
                super.visitString (value);

                if (this.inArray) { this.indent (); }
                this.builder.append ("\"");
                this.builder.append (this.escapeString (value));
                this.builder.append ("\"");
                if (this.inArray) { this.builder.append (","); }
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
                node.accept (this);
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
