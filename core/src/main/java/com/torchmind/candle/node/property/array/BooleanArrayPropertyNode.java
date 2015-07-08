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
import com.torchmind.candle.node.property.BooleanPropertyNode;

import javax.annotation.Nonnull;

/**
 * Represents a boolean array property within the tree.
 * @author Johannes Donath
 */
public class BooleanArrayPropertyNode extends AbstractArrayPropertyNode {
        private boolean[] array;

        public BooleanArrayPropertyNode (@Nonnull IDocumentNode documentNode, @Nonnull String name, @Nonnull boolean[] array) {
                super (documentNode, name);

                this.array (array);
        }

        public BooleanArrayPropertyNode (@Nonnull IDocumentNode documentNode, @Nonnull String name, @Nonnull Boolean[] array) {
                super (documentNode, name);

                boolean[] primitiveArray = new boolean[array.length];
                for (int i = 0; i < primitiveArray.length; i++) primitiveArray[i] = array[i];
                this.array (primitiveArray);
        }

        /**
         * Retrieves the boolean array.
         * @return The array.
         */
        @Nonnull
        public boolean[] array () {
                return this.array;
        }

        /**
         * Sets the boolean array.
         * @param array The array.
         * @return The node.
         */
        @Nonnull
        public BooleanArrayPropertyNode array (@Nonnull boolean[] array) {
                this.array = array;
                return this;
        }

        @Nonnull
        @Override
        public NodeValueType itemType () {
                return NodeValueType.BOOLEAN;
        }

        @Override
        public int length () {
                return this.array.length;
        }
}
