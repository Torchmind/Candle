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
import com.torchmind.candle.api.error.CandleException;
import com.torchmind.candle.api.property.array.IArrayPropertyNode;
import com.torchmind.candle.node.CommentNode;
import com.torchmind.candle.node.ObjectNode;
import com.torchmind.candle.node.property.*;
import com.torchmind.candle.node.property.array.*;
import org.antlr.v4.runtime.ANTLRInputStream;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.Stack;

/**
 * Provides a root document for the Candle Configuration File Format.
 *
 * @author Johannes Donath
 */
public class Candle extends ObjectNode implements IDocumentNode {
        private IVisitor visitor = new Visitor (this);

        public Candle () {
                super ();
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public Candle accept (@Nonnull IVisitor visitor) {
                super.accept (visitor);
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public IDocumentNode accept (@Nonnull ITreeVisitor walker) {
                walker.visitDocumentNode (this);
                super.accept (walker);
                walker.visitDocumentNodeEnd (this);

                return this;
        }

        /**
         * Processes a {@link java.io.File}.
         *
         * @param file The file.
         * @return The document.
         *
         * @throws com.torchmind.candle.api.error.CandleException when lexing or parsing fails.
         * @throws java.io.IOException                            when reading the document fails.
         */
        @Nonnull
        public Candle read (@Nonnull File file) throws CandleException, IOException {
                try {
                        this.clear ();

                        CandleParser parser = new CandleParser (this.visitor);
                        parser.parse (file);

                        return this;
                } catch (RuntimeException ex) {
                        if (ex.getCause () instanceof CandleException) { throw ((CandleException) ex.getCause ()); }
                        throw ex;
                }
        }

        /**
         * Processes a file.
         *
         * @param fileName The file name/path.
         * @return The document.
         *
         * @throws com.torchmind.candle.api.error.CandleException when lexing or parsing fails.
         * @throws java.io.IOException                            when reading the document fails.
         */
        @Nonnull
        public Candle read (@Nonnull String fileName) throws CandleException, IOException {
                return this.read (new File (fileName));
        }

        /**
         * Processes an {@link java.io.InputStream}.
         *
         * @param inputStream The input stream.
         * @return The document.
         *
         * @throws com.torchmind.candle.api.error.CandleException when lexing or parsing fails.
         * @throws java.io.IOException                            when reading the document fails.
         */
        @Nonnull
        public Candle read (@Nonnull InputStream inputStream) throws CandleException, IOException {
                try {
                        this.clear ();

                        CandleParser parser = new CandleParser (this.visitor);
                        parser.parse (inputStream);

                        return this;
                } catch (RuntimeException ex) {
                        if (ex.getCause () instanceof CandleException) { throw ((CandleException) ex.getCause ()); }
                        throw ex;
                }
        }

        /**
         * Processes an {@link org.antlr.v4.runtime.ANTLRInputStream}.
         *
         * @param inputStream The input stream.
         * @return The document.
         *
         * @throws com.torchmind.candle.api.error.CandleException when lexing or parsing fails.
         */
        @Nonnull
        protected Candle read (@Nonnull ANTLRInputStream inputStream) throws CandleException {
                try {
                        this.clear ();

                        CandleParser parser = new CandleParser (this.visitor);
                        parser.parse (inputStream);

                        return this;
                } catch (RuntimeException ex) {
                        if (ex.getCause () instanceof CandleException) { throw ((CandleException) ex.getCause ()); }
                        throw ex;
                }
        }

        /**
         * Reads a Candle document.
         *
         * @param file The file.
         * @return The document.
         *
         * @throws com.torchmind.candle.api.error.CandleException when lexing or parsing fails.
         * @throws java.io.IOException                            when reading the document fails.
         */
        @Nonnull
        public static Candle readFile (@Nonnull File file) throws CandleException, IOException {
                return (new Candle ()).read (file);
        }

        /**
         * Reads a Candle document.
         *
         * @param inputStream The input stream.
         * @return The document.
         *
         * @throws com.torchmind.candle.api.error.CandleException when lexing or parsing fails.
         * @throws java.io.IOException                            when reading the document fails.
         */
        @Nonnull
        public static Candle readFile (@Nonnull InputStream inputStream) throws CandleException, IOException {
                return (new Candle ()).read (inputStream);
        }

        /**
         * Retrieves the document visitor.
         *
         * @return The visitor.
         */
        @Nonnull
        public IVisitor visitor () {
                return this.visitor;
        }

        /**
         * Writes the document into a stream.
         *
         * @param outputStream The output stream.
         * @return The document.
         *
         * @throws java.io.IOException when writing fails.
         */
        @Nonnull
        public Candle write (@Nonnull OutputStream outputStream) throws IOException {
                CandleWriter writer = new CandleWriter ();
                writer.write (outputStream, this);
                return this;
        }

        /**
         * Writes the document into a file.
         *
         * @param file The file.
         * @return The document.
         *
         * @throws java.io.IOException when writing fails.
         */
        @Nonnull
        public Candle write (@Nonnull File file) throws IOException {
                try (FileOutputStream outputStream = new FileOutputStream (file)) {
                        return this.write (outputStream);
                }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString () {
                return String.format ("Candle{children=[%s]}", this.children ());
        }

        /**
         * Provides a low-level visitor to the document.
         */
        private static class Visitor extends AbstractVisitor {
                private final Candle document;

                private Stack<IObjectNode> objectStack = new Stack<> ();
                private String identifier = null;

                private Visitor (@Nonnull Candle document) {
                        this.document = document;

                        this.objectStack.push (document);
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void visitArrayEnd () {
                        super.visitArrayEnd ();

                        NodeValueType arrayType = this.getArrayType ();
                        IArrayPropertyNode propertyNode;

                        switch (arrayType) {
                                case BOOLEAN:
                                        propertyNode = new BooleanArrayPropertyNode (this.document, this.identifier, this.getArrayContent (Boolean.class));
                                        break;
                                case ENUM:
                                        propertyNode = new EnumArrayPropertyNode (this.document, this.identifier, this.getArrayContent (EnumWrapper.class, String.class, EnumWrapper::value));
                                        break;
                                case FLOAT:
                                        propertyNode = new FloatArrayPropertyNode (this.document, this.identifier, this.getArrayContent (Float.class));
                                        break;
                                case INTEGER:
                                        propertyNode = new IntegerArrayPropertyNode (this.document, this.identifier, this.getArrayContent (Integer.class));
                                        break;
                                case NULL:
                                        propertyNode = new NullArrayPropertyNode (this.document, this.identifier);
                                        break;
                                case STRING:
                                        propertyNode = new StringArrayPropertyNode (this.document, this.identifier, this.getArrayContent (String.class));
                                        break;
                                default:
                                        throw new UnsupportedOperationException ("Unsupported array type: " + arrayType);
                        }

                        this.objectStack.peek ().append (propertyNode);
                        this.identifier = null;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void visitBoolean (boolean value) {
                        super.visitBoolean (value);
                        if (this.pushValue (value)) { return; }

                        this.objectStack.peek ().append (new BooleanPropertyNode (this.document, this.identifier, value));
                        this.identifier = null;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void visitComment (@Nonnull String text) {
                        super.visitComment (text);

                        this.objectStack.peek ().append (new CommentNode (this.document, text));
                        this.identifier = null;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void visitDefault () {
                        super.visitDefault ();

                        this.objectStack.peek ().append (new DefaultPropertyNode (this.document, this.identifier));
                        this.identifier = null;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void visitEnum (@Nonnull String value) {
                        super.visitEnum (value);
                        if (this.pushValue (new EnumWrapper (value))) { return; }

                        this.objectStack.peek ().append (new EnumPropertyNode (this.document, this.identifier, value));
                        this.identifier = null;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void visitFloat (float value) {
                        super.visitFloat (value);
                        if (this.pushValue (value)) { return; }

                        this.objectStack.peek ().append (new FloatPropertyNode (this.document, this.identifier, value));
                        this.identifier = null;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void visitInteger (int value) {
                        super.visitInteger (value);
                        if (this.pushValue (value)) { return; }

                        this.objectStack.peek ().append (new IntegerPropertyNode (this.document, this.identifier, value));
                        this.identifier = null;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void visitNull () {
                        super.visitNull ();
                        if (this.pushValue (null)) { return; }

                        this.objectStack.peek ().append (new NullPropertyNode (this.document, this.identifier));
                        this.identifier = null;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void visitObject (@Nonnull String name) {
                        super.visitObject (name);

                        ObjectNode node = new ObjectNode (this.document, name);

                        this.objectStack.peek ().append (node);
                        this.objectStack.push (node);
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void visitObjectEnd () {
                        super.visitObjectEnd ();

                        this.objectStack.pop ();
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void visitProperty (@Nonnull String name) {
                        super.visitProperty (name);

                        this.identifier = name;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void visitString (@Nonnull String value) {
                        super.visitString (value);
                        if (this.pushValue (value)) { return; }

                        this.objectStack.peek ().append (new StringPropertyNode (this.document, this.identifier, value));
                        this.identifier = null;
                }
        }
}
