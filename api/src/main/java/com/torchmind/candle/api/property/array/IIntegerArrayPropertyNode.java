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
package com.torchmind.candle.api.property.array;

import com.torchmind.candle.api.IVisitor;
import com.torchmind.candle.api.NodeValueType;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * @author Johannes Donath
 */
public interface IIntegerArrayPropertyNode extends IArrayPropertyNode {

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        IIntegerArrayPropertyNode accept (@Nonnull IVisitor visitor);

        /**
         * Sets an array value.
         *
         * @param array The array.
         * @return The node.
         */
        @Nonnull
        IIntegerArrayPropertyNode array (@Nonnull int[] array);

        /**
         * Sets an array value.
         *
         * @param array The array.
         * @return The node.
         */
        @Nonnull
        IIntegerArrayPropertyNode array (@Nonnull Integer[] array);

        /**
         * Retrieves an array value.
         *
         * @return The array.
         */
        @Nonnull
        int[] array ();

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        default NodeValueType itemType () {
                return NodeValueType.INTEGER;
        }

        /**
         * Retrieves an unsigned array value.
         *
         * @return The array.
         *
         * @throws java.lang.IllegalStateException when one or more values are negative.
         */
        @Nonnull
        @Nonnegative
        int[] unsignedArray () throws IllegalStateException;
}
