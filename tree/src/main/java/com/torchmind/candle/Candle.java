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

import com.torchmind.candle.antlr.*;
import com.torchmind.candle.api.IDocumentNode;
import com.torchmind.candle.api.ITreeVisitor;
import com.torchmind.candle.api.error.CandleException;
import com.torchmind.candle.node.ObjectNode;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javax.annotation.Nonnull;
import java.io.*;

/**
 * Provides a root document for the Candle Configuration File Format.
 *
 * @author Johannes Donath
 */
public class Candle extends ObjectNode implements IDocumentNode {

        public Candle () {
                super ();
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
                return this.read (file.getPath ());
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
                return this.read (new ANTLRFileStream (fileName));
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
                return this.read (new ANTLRInputStream (inputStream));
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
                        CandleLexer lexer = new CandleLexer (inputStream);
                        lexer.addErrorListener (new LexerErrorListener ());

                        CommonTokenStream tokenStream = new CommonTokenStream (lexer);
                        CandleParser parser = new CandleParser (tokenStream);
                        parser.setErrorHandler (new ParserErrorStrategy ());

                        ParseTreeWalker walker = new ParseTreeWalker ();
                        CandleListener listener = new CandleListener (this);

                        this.clear ();
                        walker.walk (listener, parser.candle ());
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
}
