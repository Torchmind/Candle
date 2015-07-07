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
import java.lang.reflect.Array;

/**
 * Represents an enum array property within the tree.
 * @author Johannes Donath
 */
public class EnumArrayPropertyNode extends AbstractArrayPropertyNode {
        private String[] array;

        public EnumArrayPropertyNode (@Nonnull IDocumentNode documentNode, @Nonnull String name, @Nonnull String[] array) {
                super (documentNode, name);

                this.array (array);
        }

        public <T extends Enum> EnumArrayPropertyNode (@Nonnull IDocumentNode documentNode, @Nonnull String name, @Nonnull T[] array) {
                super (documentNode, name);

                this.array (array);
        }

        /**
         * Retrieves the raw enum array.
         * @return The array.
         */
        @Nonnull
        public String[] array () {
                return this.array;
        }

        /**
         * Retrieves the enum array.
         * @param enumType The enum type.
         * @param <T> The enum type.
         * @return The array.
         */
        @SuppressWarnings ("unchecked")
        <T extends Enum> T[] array (@Nonnull Class<T> enumType) {
                try {
                        T[] array = ((T[]) Array.newInstance (enumType, this.length ()));
                        for (int i = 0; i < this.length (); i++) array[i] = (this.array ()[i] != null ? Enum.valueOf (enumType, this.array ()[i]) : null);
                        return array;
                } catch (IllegalArgumentException ex) {
                        throw new IllegalStateException ("Enum contains one or more values not listed by type " + enumType.getCanonicalName () + ": " + ex.getMessage (), ex);
                }
        }

        /**
         * Sets the raw enum array.
         * @param array The array.
         * @return The node.
         */
        @Nonnull
        public EnumArrayPropertyNode array (@Nonnull String[] array) {
                this.array = array;
                return this;
        }

        /**
         * Sets the enum array.
         * @param array The array.
         * @param <T> The enum type.
         * @return The node.
         */
        @Nonnull
        public <T extends Enum> EnumArrayPropertyNode array (@Nonnull T[] array) {
                String[] convertedArray = new String[array.length];
                for (int i = 0; i < array.length; i++) convertedArray[i] = (array[i] != null ? array[i].name () : null);
                return this.array (convertedArray);
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public NodeValueType itemType () {
                return NodeValueType.STRING;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int length () {
                return this.array.length;
        }
}
