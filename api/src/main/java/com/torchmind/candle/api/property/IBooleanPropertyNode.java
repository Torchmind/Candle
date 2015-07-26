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
package com.torchmind.candle.api.property;

import com.torchmind.candle.api.IVisitor;
import com.torchmind.candle.api.NodeValueType;

import javax.annotation.Nonnull;

/**
 * Represents a {@link java.lang.Boolean} property value.
 *
 * @author Johannes Donath
 */
public interface IBooleanPropertyNode extends IPropertyNode {

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        IBooleanPropertyNode accept (@Nonnull IVisitor visitor);

        /**
         * Sets the property value.
         *
         * @param value The value.
         * @return The node.
         */
        @Nonnull
        IBooleanPropertyNode value (boolean value);

        /**
         * Retrieves the property value.
         *
         * @return The value.
         */
        boolean value ();

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        default NodeValueType valueType () {
                return NodeValueType.BOOLEAN;
        }
}
