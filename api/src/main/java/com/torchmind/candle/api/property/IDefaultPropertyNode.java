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

import com.torchmind.candle.api.ITreeVisitor;
import com.torchmind.candle.api.IVisitor;
import com.torchmind.candle.api.NodeValueType;

import javax.annotation.Nonnull;

/**
 * Represents a default property value.
 *
 * @author Johannes Donath
 */
public interface IDefaultPropertyNode extends IPropertyNode {

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        IDefaultPropertyNode accept (@Nonnull IVisitor visitor);

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        IDefaultPropertyNode accept (@Nonnull ITreeVisitor visitor);

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        default NodeValueType valueType () {
                return NodeValueType.DEFAULT;
        }
}
