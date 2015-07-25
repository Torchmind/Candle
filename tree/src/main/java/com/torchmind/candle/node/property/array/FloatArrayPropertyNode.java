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
import com.torchmind.candle.api.property.array.IFloatArrayPropertyNode;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.Arrays;

/**
 * Represents a float array within the tree.
 *
 * @author Johannes Donath
 */
public class FloatArrayPropertyNode extends AbstractArrayPropertyNode implements IFloatArrayPropertyNode {
        private float[] array;

        public FloatArrayPropertyNode (@Nonnull IDocumentNode documentNode, @Nonnull String name, @Nonnull float[] array) {
                super (documentNode, name);

                this.array (array);
        }

        public FloatArrayPropertyNode (@Nonnull IDocumentNode documentNode, @Nonnull String name, @Nonnull Float[] array) {
                super (documentNode, name);

                this.array (array);
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public float[] array () {
                return this.array;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public FloatArrayPropertyNode array (@Nonnull float[] array) {
                this.array = array;
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public IFloatArrayPropertyNode array (@Nonnull Float[] array) {
                float[] primitiveArray = new float[array.length];
                for (int i = 0; i < primitiveArray.length; i++) { primitiveArray[i] = array[i]; }
                return this.array (primitiveArray);
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        @Nonnegative
        public float[] unsignedArray () {
                for (float current : this.array) {
                        if (current < 0) {
                                throw new IllegalStateException ("Expected an unsigned value but got " + current);
                        }
                }

                return this.array;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @Nonnegative
        public int length () {
                return this.array.length;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString () {
                return String.format ("FloatArrayPropertyNode{%s,array=%s}", super.toString (), Arrays.toString (this.array ()));
        }
}
