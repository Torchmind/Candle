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
 * Represents an integer value within the tree.
 * @author Johannes Donath
 */
public class IntegerPropertyNode extends AbstractPropertyNode {
        private int value;

        public IntegerPropertyNode (@Nonnull IDocumentNode documentNode, @Nonnull String name, int value) {
                super (documentNode, name);

                this.value (value);
        }

        /**
         * {@inheritDoc}
         */
        public int value () {
                return this.value;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnegative
        public int valueUnsigned () {
                if (this.value () < 0) throw new IllegalStateException ("Expected an unsigned value but got " + this.value ());
                return this.value ();
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        public IntegerPropertyNode value (int value) {
                this.value = value;
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public NodeValueType valueType () {
                return NodeValueType.INTEGER;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString () {
                return String.format ("IntegerPropertyNode{%s,value=%d}", super.toString (), this.value ());
        }
}
