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
import com.torchmind.candle.api.property.array.IEnumArrayPropertyNode;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Represents an enum array property within the tree.
 *
 * @author Johannes Donath
 */
public class EnumArrayPropertyNode extends AbstractArrayPropertyNode implements IEnumArrayPropertyNode {
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
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public String[] array () {
                return this.array;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        @SuppressWarnings ("unchecked")
        public <T extends Enum> T[] array (@Nonnull Class<T> enumType) {
                try {
                        T[] array = ((T[]) Array.newInstance (enumType, this.length ()));

                        for (int i = 0; i < this.length (); i++) {
                                String currentElement = this.array ()[i];
                                array[i] = (currentElement != null ? ((T) Enum.valueOf (enumType, currentElement)) : null);
                        }

                        return array;
                } catch (IllegalArgumentException ex) {
                        throw new IllegalStateException ("Enum contains one or more values not listed by type " + enumType.getCanonicalName () + ": " + ex.getMessage (), ex);
                }
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public EnumArrayPropertyNode array (@Nonnull String[] array) {
                this.array = array;
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public <T extends Enum> EnumArrayPropertyNode array (@Nonnull T[] array) {
                String[] convertedArray = new String[array.length];
                for (int i = 0; i < array.length; i++) {
                        convertedArray[i] = (array[i] != null ? array[i].name () : null);
                }
                return this.array (convertedArray);
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
                return String.format ("EnumArrayPropertyNode{%s,array=%s}", super.toString (), Arrays.toString (this.array ()));
        }
}
