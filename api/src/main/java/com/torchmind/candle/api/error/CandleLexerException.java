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
package com.torchmind.candle.api.error;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Provides an exception for lexer error cases.
 *
 * @author Johannes Donath
 */
public class CandleLexerException extends CandleException {
        public static final String FORMAT = "In line %d:%d: %s";

        public CandleLexerException (@Nullable Object offendingSymbol, @Nonnegative int line, @Nonnegative int offset, @Nonnull String message, @Nullable Throwable cause) {
                super (String.format (FORMAT, line, offset, message));
                // TODO: See whether offendingSymbol can be integrated into the error message in a sane way when set ...
        }
}
