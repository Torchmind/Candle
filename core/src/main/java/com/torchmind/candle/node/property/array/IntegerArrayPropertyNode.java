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
package com.torchmind.candle.node.property.array;

import com.torchmind.candle.api.IDocumentNode;
import com.torchmind.candle.api.NodeValueType;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.Arrays;

/**
 * Represents integer array properties within the tree.
 *
 * @author Johannes Donath
 */
public class IntegerArrayPropertyNode extends AbstractArrayPropertyNode {
        private int[] array;

        public IntegerArrayPropertyNode (@Nonnull IDocumentNode documentNode, @Nonnull String name, @Nonnull int[] array) {
                super (documentNode, name);

                this.array (array);
        }

        public IntegerArrayPropertyNode (@Nonnull IDocumentNode documentNode, @Nonnull String name, @Nonnull Integer[] array) {
                super (documentNode, name);

                int[] primitiveArray = new int[array.length];
                for (int i = 0; i < primitiveArray.length; i++) { primitiveArray[i] = array[i]; }
                this.array (primitiveArray);
        }

        /**
         * Retrieves the integer array.
         *
         * @return The array.
         */
        @Nonnull
        public int[] array () {
                return this.array;
        }

        /**
         * Sets the integer array.
         *
         * @param array The array.
         * @return The node.
         */
        @Nonnull
        public IntegerArrayPropertyNode array (@Nonnull int[] array) {
                this.array = array;
                return this;
        }

        /**
         * Retrieves the unsigned integer array.
         *
         * @return The array.
         */
        @Nonnull
        @Nonnegative
        public int[] arrayUnsigned () {
                for (int current : this.array) {
                        if (current < 0) {
                                throw new IllegalStateException ("Expected an unsigned value but got " + current);
                        }
                }

                return this.array;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public NodeValueType itemType () {
                return NodeValueType.INTEGER;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int length () {
                return this.array.length;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString () {
                return String.format ("IntegerArrayPropertyNode{%s,array=%s}", super.toString (), Arrays.toString (this.array ()));
        }
}
