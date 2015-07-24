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

import javax.annotation.Nonnull;

/**
 * Represents an enum value within the tree.
 *
 * @author Johannes Donath
 */
public class EnumPropertyNode extends AbstractPropertyNode {
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
         * Retrieves the enum value.
         *
         * @return The value.
         */
        @Nonnull
        public String value () {
                return this.value;
        }

        /**
         * Sets the enum value.
         *
         * @param value The value.
         * @return The node.
         */
        @Nonnull
        public EnumPropertyNode value (String value) {
                this.value = value;
                return this;
        }

        /**
         * Retrieves the enum value.
         *
         * @param enumType The enum type.
         * @param <T>      The enum type.
         * @return The value.
         *
         * @throws java.lang.IllegalStateException when the enum does not contain the node value.
         */
        @Nonnull
        public <T extends Enum> T value (@Nonnull Class<T> enumType) throws IllegalStateException {
                try {
                        // Looks unneeded, should be unneeded, isn't unneeded ...
                        return ((T) Enum.valueOf (enumType, this.value ()));
                } catch (IllegalArgumentException ex) {
                        throw new IllegalStateException ("Enum of type " + enumType.getCanonicalName () + " does not contain possible value \"" + this.value () + "\"", ex);
                }
        }

        /**
         * Sets an enum value.
         *
         * @param value The enum value.
         * @return The node.
         */
        @Nonnull
        public EnumPropertyNode value (@Nonnull Enum value) {
                this.value (value.name ());
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public NodeValueType valueType () {
                return NodeValueType.ENUM;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString () {
                return String.format ("EnumPropertyNode{%s,value=%s}", super.toString (), this.value ());
        }
}
