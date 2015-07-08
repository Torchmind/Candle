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

import javax.annotation.Nonnull;
import java.util.Arrays;

/**
 * Represents a string array property within the tree.
 * @author Johannes Donath
 */
public class StringArrayPropertyNode extends AbstractArrayPropertyNode {
        private String[] array;

        public StringArrayPropertyNode (@Nonnull IDocumentNode documentNode, @Nonnull String name, @Nonnull String[] array) {
                super (documentNode, name);

                this.array (array);
        }

        /**
         * Retrieves the string array.
         * @return The array.
         */
        public String[] array () {
                return this.array;
        }

        /**
         * Sets the string array.
         * @param array The array.
         * @return The node.
         */
        public StringArrayPropertyNode array (String[] array) {
                this.array = array;
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public NodeValueType itemType () {
                return NodeValueType.STRING;
        }

        @Override
        public int length () {
                return this.array.length;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString () {
                return String.format ("StringArrayPropertyNode{%s,array=[%s]}", super.toString (), Arrays.toString (this.array ()));
        }
}
