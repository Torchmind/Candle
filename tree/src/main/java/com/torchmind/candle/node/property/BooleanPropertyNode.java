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
import com.torchmind.candle.api.property.IBooleanPropertyNode;

import javax.annotation.Nonnull;

/**
 * Represents a boolean within the node tree.
 *
 * @author Johannes Donath
 */
public class BooleanPropertyNode extends AbstractPropertyNode implements IBooleanPropertyNode {
        private boolean value;

        public BooleanPropertyNode (@Nonnull IDocumentNode documentNode, @Nonnull String name, boolean value) {
                super (documentNode, name);

                this.value (value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean value () {
                return this.value;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public BooleanPropertyNode value (boolean value) {
                this.value = value;
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString () {
                return String.format ("BooleanPropertyNode{%s,value=%b}", super.toString (), this.value ());
        }
}
