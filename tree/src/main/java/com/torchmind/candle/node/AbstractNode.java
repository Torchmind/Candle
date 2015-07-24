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
import com.torchmind.candle.api.INode;
import com.torchmind.candle.api.NodeType;

import javax.annotation.Nonnull;

/**
 * Provides an abstract utility implementation of {@link com.torchmind.candle.api.INode}.
 *
 * @author Johannes Donath
 */
public abstract class AbstractNode implements INode {
        private IDocumentNode documentNode;

        protected AbstractNode () { }

        public AbstractNode (@Nonnull IDocumentNode documentNode) {
                super ();

                this.documentNode = documentNode;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public IDocumentNode document () {
                return this.documentNode;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public AbstractNode ensureType (@Nonnull NodeType type) throws IllegalStateException {
                if (this.type () != type) {
                        throw new IllegalStateException ("Expected node of type " + type + " but found " + this.type ());
                }
                return this;
        }
}
