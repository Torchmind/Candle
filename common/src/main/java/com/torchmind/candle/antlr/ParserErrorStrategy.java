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
package com.torchmind.candle.antlr;

import com.torchmind.candle.api.error.CandleParserException;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.ParseCancellationException;

/**
 * Wraps parser errors for user convenience.
 *
 * @author Johannes Donath
 */
public class ParserErrorStrategy extends BailErrorStrategy {

        /**
         * {@inheritDoc}
         */
        @Override
        public void recover (Parser recognizer, RecognitionException e) {
                try {
                        super.recover (recognizer, e);
                } catch (ParseCancellationException ex) {
                        throw new RuntimeException (new CandleParserException (ex.getMessage (), ex));
                }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Token recoverInline (Parser recognizer) throws RecognitionException {
                try {
                        return super.recoverInline (recognizer);
                } catch (ParseCancellationException ex) {
                        throw new RuntimeException (new CandleParserException (ex.getMessage (), ex));
                }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void reportError (Parser recognizer, RecognitionException e) {
        }
}
