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

import com.torchmind.candle.api.NodeValueType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Provides an abstract implementation of {@link com.torchmind.candle.api.IVisitor}.
 *
 * @author Johannes Donath
 */
public abstract class AbstractVisitor extends ValidationVisitor {
        private List<Object> arrayContents = null;

        /**
         * Retrieves and converts array contents as a certain type.
         *
         * @param inputType  The input type.
         * @param outputType The output type.
         * @param function   The conversion method.
         * @param <I>        The input type.
         * @param <R>        The output type.
         * @return The array.
         *
         * @throws java.lang.IllegalStateException when one or more values within the array are invalid.
         */
        @Nonnull
        @SuppressWarnings ("unchecked")
        protected <I, R> R[] getArrayContent (@Nonnull Class<I> inputType, @Nonnull Class<R> outputType, @Nonnull Function<I, R> function) throws IllegalStateException {
                try {
                        // @formatter:off
                        return this.arrayContents.stream ()
                                                 .map ((i) -> {
                                                         if (i == null) { return null; }
                                                         return ((I) i);
                                                 })
                                                        .map (function)
                                                                .toArray ((l) -> ((R[]) Array.newInstance (outputType, l)));
                        // @formatter:on
                } finally {
                        this.arrayContents = null;
                }
        }

        /**
         * Retrieves array contents as a certain type.
         *
         * @param outputType The output type.
         * @param <R>        The output type.
         * @return The array.
         *
         * @throws java.lang.IllegalStateException when one or more values within the array are invalid.
         */
        @Nonnull
        protected <R> R[] getArrayContent (@Nonnull Class<R> outputType) throws IllegalStateException {
                return this.getArrayContent (outputType, outputType, (i) -> i);
        }

        /**
         * Retrieves the current array type.
         *
         * @return The array type.
         */
        @Nonnull
        protected NodeValueType getArrayType () {
                // @formatter:off
                return this.arrayContents.stream ()
                        .filter (i -> i != null)
                                .map (Object::getClass)
                                        .findAny ()
                                                .filter (i -> (
                                                        Boolean.class.isAssignableFrom (i) ||
                                                        EnumWrapper.class.isAssignableFrom (i) ||
                                                        Float.class.isAssignableFrom (i) ||
                                                        Integer.class.isAssignableFrom (i) ||
                                                        String.class.isAssignableFrom (i)
                                                ))
                                                        .map (i -> {
                                                                if (Boolean.class.isAssignableFrom (i))
                                                                        return NodeValueType.BOOLEAN;
                                                                if (EnumWrapper.class.isAssignableFrom (i))
                                                                        return NodeValueType.ENUM;
                                                                if (Float.class.isAssignableFrom (i))
                                                                        return NodeValueType.FLOAT;
                                                                if (Integer.class.isAssignableFrom (i))
                                                                        return NodeValueType.INTEGER;

                                                                return NodeValueType.STRING;
                                                        })
                                                                .orElse (NodeValueType.NULL);
                // @formatter:on
        }

        /**
         * Pushes a value to the end of the current array (if any).
         *
         * @param object The object.
         * @return {@code true} if an array is present and the value was added to it's contents.
         */
        protected boolean pushValue (@Nullable Object object) {
                if (this.arrayContents == null) { return false; }
                this.arrayContents.add (object);
                return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitArray () {
                super.visitArray ();

                this.arrayContents = new ArrayList<> ();
        }

        /**
         * Provides a wrapper for enum values.
         */
        public static class EnumWrapper {
                private String value;

                public EnumWrapper (String value) {
                        this.value = value;
                }

                /**
                 * Retrieves the wrapped value.
                 *
                 * @return The wrapped enum value.
                 */
                public String value () {
                        return this.value;
                }
        }
}
