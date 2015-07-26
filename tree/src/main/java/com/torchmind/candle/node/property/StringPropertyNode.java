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
import com.torchmind.candle.api.IVisitor;
import com.torchmind.candle.api.property.IStringPropertyNode;

import javax.annotation.Nonnull;

/**
 * Represents a string value within the tree.
 *
 * @author Johannes Donath
 */
public class StringPropertyNode extends AbstractPropertyNode implements IStringPropertyNode {
        private String value;

        public StringPropertyNode (@Nonnull IDocumentNode documentNode, @Nonnull String name, @Nonnull String value) {
                super (documentNode, name);

                this.value (value);
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public StringPropertyNode accept (@Nonnull IVisitor visitor) {
                super.accept (visitor);

                visitor.visitString (this.value ());
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public StringPropertyNode value (@Nonnull String value) {
                this.value = value;
                return this;
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
        @Override
        public String toString () {
                return String.format ("StringPropertyNode{%s,value=\"%s\"}", super.toString (), this.value ());
        }
}
