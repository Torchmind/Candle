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

/**
 * Provides an exception for parser error cases.
 * @author Johannes Donath
 */
public class CandleParserException extends CandleException {

        protected CandleParserException (String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
                super (message, cause, enableSuppression, writableStackTrace);
        }

        public CandleParserException () {
                super ();
        }

        public CandleParserException (String message) {
                super (message);
        }

        public CandleParserException (String message, Throwable cause) {
                super (message, cause);
        }

        public CandleParserException (Throwable cause) {
                super (cause);
        }
}
