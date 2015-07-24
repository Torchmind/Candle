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
package com.torchmind.candle.node.property;

import com.torchmind.candle.api.IDocumentNode;
import com.torchmind.candle.api.NodeValueType;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * Represents a float value within the tree.
 *
 * @author Johannes Donath
 */
public class FloatPropertyNode extends AbstractPropertyNode {
        private float value;

        public FloatPropertyNode (@Nonnull IDocumentNode documentNode, @Nonnull String name, float value) {
                super (documentNode, name);

                this.value (value);
        }

        /**
         * Retrieves the float value.
         *
         * @return The value.
         */
        public float value () {
                return this.value;
        }

        /**
         * Sets the float value.
         *
         * @param value The value.
         * @return The node.
         */
        @Nonnull
        public FloatPropertyNode value (float value) {
                this.value = value;
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public NodeValueType valueType () {
                return NodeValueType.FLOAT;
        }

        /**
         * Retrieves an unsigned float value.
         *
         * @return The value.
         *
         * @throws java.lang.IllegalStateException when a negative value was supplied.
         */
        @Nonnegative
        public float valueUnsigned () throws IllegalStateException {
                if (this.value () < 0) {
                        throw new IllegalStateException ("Expected an unsigned value but got " + this.value ());
                }
                return this.value ();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString () {
                return String.format ("FloatPropertyNode{%s,value=%f}", super.toString (), this.value ());
        }
}
