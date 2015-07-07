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

/**
 * Provides a list of valid value types.
 *
 * @author Johannes Donath
 */
public enum NodeValueType {
        /**
         * Represents an array of a certain type of element.
         */
        ARRAY,

        /**
         * Represents a binary value (0 = false, 1 = true).
         */
        BOOLEAN,

        /**
         * Represents a value within a list of possible values.
         */
        ENUM,

        /**
         * Represents a floating point number.
         */
        FLOAT,

        /**
         * Represents an integer.
         */
        INTEGER,

        /**
         * Represents a null (empty) value.
         */
        NULL,

        /**
         * Represents a character sequence.
         */
        STRING
}
