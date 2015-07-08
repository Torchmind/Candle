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

/**
 * Represents an empty array property within the tree.
 * @author Johannes Donath
 */
public class NullArrayPropertyNode extends AbstractArrayPropertyNode {

        public NullArrayPropertyNode (@Nonnull IDocumentNode documentNode, @Nonnull String name) {
                super (documentNode, name);
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public NullArrayPropertyNode ensureItemType (@Nonnull NodeValueType valueType) throws IllegalStateException {
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public NodeValueType itemType () {
                return NodeValueType.NULL;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int length () {
                return 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString () {
                return String.format ("NullArrayPropertyNode{%s}", super.toString ());
        }
}
