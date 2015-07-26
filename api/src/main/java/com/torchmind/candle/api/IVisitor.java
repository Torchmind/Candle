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

import javax.annotation.Nonnull;

/**
 * Provides a visitor for Candle parsers and components.
 *
 * @author Johannes Donath
 */
public interface IVisitor {

        /**
         * Visits an array entry.
         */
        void visitArray ();

        /**
         * Visits an array end.
         */
        void visitArrayEnd ();

        /**
         * Visits a boolean value.
         *
         * @param value The value.
         */
        void visitBoolean (boolean value);

        /**
         * Visits a comment.
         *
         * @param text The text.
         */
        void visitComment (@Nonnull String text);

        /**
         * Visits a default value.
         */
        void visitDefault ();

        /**
         * Visits an enum value.
         *
         * @param value The value.
         */
        void visitEnum (@Nonnull String value);

        /**
         * Visits a float value.
         *
         * @param value The value.
         */
        void visitFloat (float value);

        /**
         * Visits an integer value.
         *
         * @param value The value.
         */
        void visitInteger (int value);

        /**
         * Visits a null value.
         */
        void visitNull ();

        /**
         * Visits an object entry.
         *
         * @param name The object name.
         */
        void visitObject (@Nonnull String name);

        /**
         * Visits an object end.
         */
        void visitObjectEnd ();

        /**
         * Visits a property.
         *
         * @param name The name.
         */
        void visitProperty (@Nonnull String name);

        /**
         * Visits a string value.
         *
         * @param value The string.
         */
        void visitString (@Nonnull String value);
}
