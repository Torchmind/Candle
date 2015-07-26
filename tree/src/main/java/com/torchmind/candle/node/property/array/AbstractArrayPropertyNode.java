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
import com.torchmind.candle.api.IVisitor;
import com.torchmind.candle.api.NodeValueType;
import com.torchmind.candle.api.property.array.IArrayPropertyNode;
import com.torchmind.candle.node.property.AbstractPropertyNode;

import javax.annotation.Nonnull;

/**
 * Provides an abstract implementation of {@link com.torchmind.candle.api.property.array.IArrayPropertyNode}.
 *
 * @author Johannes Donath
 */
public abstract class AbstractArrayPropertyNode extends AbstractPropertyNode implements IArrayPropertyNode {

        public AbstractArrayPropertyNode (@Nonnull IDocumentNode documentNode, @Nonnull String name) {
                super (documentNode, name);
        }

        @Nonnull
        @Override
        public AbstractArrayPropertyNode accept (@Nonnull IVisitor visitor) {
                super.accept (visitor);
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public AbstractArrayPropertyNode ensureItemType (@Nonnull NodeValueType valueType) throws IllegalStateException {
                if (this.itemType () != valueType) {
                        throw new IllegalStateException ("Expected value node representing items of type " + valueType + " but found " + this.itemType ());
                }
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString () {
                return String.format ("%s,length=%d", super.toString (), this.length ());
        }
}
