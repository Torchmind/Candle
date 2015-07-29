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
import com.torchmind.candle.api.IVisitor;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import javax.annotation.Nonnull;
import java.io.*;

/**
 * @author Johannes Donath
 */
public class CandleParser {
        private final Listener listener;

        public CandleParser (@Nonnull IVisitor visitor) {
                this.listener = new Listener (visitor);
        }

        /**
         * Parses a candle configuration file.
         * @param file The file.
         * @return The parser.
         * @throws java.io.IOException when reading the file fails.
         */
        @Nonnull
        public CandleParser parse (@Nonnull File file) throws IOException {
                try (FileReader reader = new FileReader (file)) {
                        return this.parse (reader);
                }
        }

        /**
         * Parses a candle configuration.
         * @param reader The reader.
         * @return The parser.
         * @throws java.io.IOException when reading fails.
         */
        @Nonnull
        public CandleParser parse (@Nonnull Reader reader) throws IOException {
                return this.parse (new ANTLRInputStream (reader));
        }

        /**
         * Parses a candle configuration.
         * @param inputStream The input stream.
         * @return The parser.
         * @throws java.io.IOException when reading fails.
         */
        @Nonnull
        public CandleParser parse (@Nonnull InputStream inputStream) throws IOException {
                return this.parse (new ANTLRInputStream (inputStream));
        }

        /**
         * Parses a candle configuration.
         * @param inputStream The ANTLR stream.
         * @return The parser.
         */
        @Nonnull
        protected CandleParser parse (@Nonnull ANTLRInputStream inputStream) {
                CandleLexer lexer = new CandleLexer (inputStream);
                lexer.addErrorListener (new LexerErrorListener ());

                CommonTokenStream tokenStream = new CommonTokenStream (lexer);
                com.torchmind.candle.antlr.CandleParser parser = new com.torchmind.candle.antlr.CandleParser (tokenStream);
                parser.setErrorHandler (new ParserErrorStrategy ());

                ParseTreeWalker walker = new ParseTreeWalker ();
                walker.walk (this.listener, parser.candle ());

                return this;
        }

        /**
         * Provides a {@link com.torchmind.candle.antlr.CandleParserListener} implementation for use with {@link com.torchmind.candle.CandleParser}.
         */
        private static class Listener extends CandleParserBaseListener {
                private final IVisitor visitor;

                public Listener (@Nonnull IVisitor visitor) {
                        this.visitor = visitor;
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void enterCommentMultiline (com.torchmind.candle.antlr.CandleParser.CommentMultilineContext ctx) {
                        this.visitor.visitComment (ctx.getText ().substring (2, (ctx.getText ().length () - 2)));
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void enterCommentSingleline (com.torchmind.candle.antlr.CandleParser.CommentSinglelineContext ctx) {
                        this.visitor.visitComment (ctx.getText ().substring (2));
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void enterObjectIdentifier (com.torchmind.candle.antlr.CandleParser.ObjectIdentifierContext ctx) {
                        this.visitor.visitObject (ctx.getText ());
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void exitObject (com.torchmind.candle.antlr.CandleParser.ObjectContext ctx) {
                        this.visitor.visitObjectEnd ();
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void enterPropertyIdentifier (com.torchmind.candle.antlr.CandleParser.PropertyIdentifierContext ctx) {
                        this.visitor.visitProperty (ctx.getText ());
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void enterPropertyValueBoolean (com.torchmind.candle.antlr.CandleParser.PropertyValueBooleanContext ctx) {
                        this.visitor.visitBoolean (Boolean.parseBoolean (ctx.getText ()));
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void enterPropertyValueDefault (com.torchmind.candle.antlr.CandleParser.PropertyValueDefaultContext ctx) {
                        this.visitor.visitDefault ();
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void enterPropertyValueEnum (com.torchmind.candle.antlr.CandleParser.PropertyValueEnumContext ctx) {
                        this.visitor.visitEnum (ctx.getText ());
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void enterPropertyValueFloat (com.torchmind.candle.antlr.CandleParser.PropertyValueFloatContext ctx) {
                        this.visitor.visitFloat (Float.parseFloat (ctx.getText ()));
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void enterPropertyValueInteger (com.torchmind.candle.antlr.CandleParser.PropertyValueIntegerContext ctx) {
                        this.visitor.visitInteger (Integer.decode (ctx.getText ()));
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void enterPropertyValueNull (com.torchmind.candle.antlr.CandleParser.PropertyValueNullContext ctx) {
                        this.visitor.visitNull ();
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void enterPropertyValueString (com.torchmind.candle.antlr.CandleParser.PropertyValueStringContext ctx) {
                        this.visitor.visitString (ctx.getText ().substring (1, (ctx.getText ().length () - 1)));
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void enterPropertyValueArray (com.torchmind.candle.antlr.CandleParser.PropertyValueArrayContext ctx) {
                        this.visitor.visitArray ();
                }

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void exitPropertyValueArray (com.torchmind.candle.antlr.CandleParser.PropertyValueArrayContext ctx) {
                        this.visitor.visitArrayEnd ();
                }
        }
}
