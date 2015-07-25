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
import com.torchmind.candle.api.property.IEnumPropertyNode;

import javax.annotation.Nonnull;

/**
 * Represents an enum value within the tree.
 *
 * @author Johannes Donath
 */
public class EnumPropertyNode extends AbstractPropertyNode implements IEnumPropertyNode {
        private String value;

        public EnumPropertyNode (@Nonnull IDocumentNode documentNode, @Nonnull String name, @Nonnull String value) {
                super (documentNode, name);

                this.value (value);
        }

        public EnumPropertyNode (@Nonnull IDocumentNode documentNode, @Nonnull String name, @Nonnull Enum value) {
                super (documentNode, name);

                this.value (value);
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public String value () {
                return this.value;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public EnumPropertyNode value (@Nonnull String value) {
                this.value = value;
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public <E extends Enum> E value (@Nonnull Class<E> enumType) throws IllegalStateException {
                try {
                        // Looks unneeded, should be unneeded, isn't unneeded ...
                        return ((E) Enum.valueOf (enumType, this.value ()));
                } catch (IllegalArgumentException ex) {
                        throw new IllegalStateException ("Enum of type " + enumType.getCanonicalName () + " does not contain possible value \"" + this.value () + "\"", ex);
                }
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public EnumPropertyNode value (@Nonnull Enum value) {
                this.value (value.name ());
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString () {
                return String.format ("EnumPropertyNode{%s,value=%s}", super.toString (), this.value ());
        }
}
