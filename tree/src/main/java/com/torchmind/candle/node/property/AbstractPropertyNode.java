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
import com.torchmind.candle.api.IPropertyNode;
import com.torchmind.candle.api.NodeValueType;
import com.torchmind.candle.node.AbstractNamedNode;

import javax.annotation.Nonnull;

/**
 * Provides an abstract implementation of {@link com.torchmind.candle.api.IPropertyNode}.
 *
 * @author Johannes Donath
 */
public abstract class AbstractPropertyNode extends AbstractNamedNode implements IPropertyNode {

        public AbstractPropertyNode (@Nonnull IDocumentNode documentNode, @Nonnull String name) {
                super (documentNode, name);
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public IPropertyNode ensureValueType (@Nonnull NodeValueType valueType) throws IllegalStateException {
                if (this.valueType () != valueType) {
                        throw new IllegalStateException ("Expected value node representing type " + valueType + " but found " + this.valueType ());
                }
                return this;
        }
}
