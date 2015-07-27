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
import com.torchmind.candle.api.ITreeVisitor;
import com.torchmind.candle.api.IVisitor;
import com.torchmind.candle.api.NodeValueType;
import com.torchmind.candle.api.property.INullPropertyNode;

import javax.annotation.Nonnull;

/**
 * Represents a null value within the tree.
 *
 * @author Johannes Donath
 */
public class NullPropertyNode extends AbstractPropertyNode implements INullPropertyNode {

        public NullPropertyNode (@Nonnull IDocumentNode documentNode, @Nonnull String name) {
                super (documentNode, name);
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public NullPropertyNode accept (@Nonnull IVisitor visitor) {
                super.accept (visitor);

                visitor.visitNull ();
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public NullPropertyNode accept (@Nonnull ITreeVisitor visitor) {
                super.accept (visitor);

                visitor.visitPropertyNode (this.document (), this);
                visitor.visitPropertyNodeEnd (this.document (), this);

                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public NullPropertyNode ensureValueType (@Nonnull NodeValueType valueType) throws IllegalStateException {
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString () {
                return String.format ("NullPropertyNode{%s}", super.toString ());
        }
}
