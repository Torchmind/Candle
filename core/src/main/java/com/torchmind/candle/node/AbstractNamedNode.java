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
package com.torchmind.candle.node;

import com.torchmind.candle.api.IDocumentNode;
import com.torchmind.candle.api.INamedNode;
import com.torchmind.candle.api.NodeType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Provides an abstract implementation of {@link com.torchmind.candle.api.INamedNode}.
 * @author Johannes Donath
 */
public abstract class AbstractNamedNode extends AbstractNode implements INamedNode {
        private String name;

        public AbstractNamedNode (@Nonnull IDocumentNode documentNode, @Nonnull String name) {
                super (documentNode);

                this.name (name);
        }

        protected AbstractNamedNode () {
                super ();
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public String name () {
                return this.name;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public INamedNode name (@Nonnull String name) {
                this.name = name;
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString () {
                return String.format ("name=\"%s\"", this.name ());
        }
}
