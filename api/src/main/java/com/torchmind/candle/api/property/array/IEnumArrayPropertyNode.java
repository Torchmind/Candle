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

import com.torchmind.candle.api.NodeValueType;

import javax.annotation.Nonnull;

/**
 * @author Johannes Donath
 */
public interface IEnumArrayPropertyNode extends IArrayPropertyNode {

        /**
         * Retrieves a raw array value.
         *
         * @return The array.
         */
        @Nonnull
        String[] array ();

        /**
         * Sets a raw array value.
         *
         * @param array The array.
         * @return The node.
         */
        @Nonnull
        IEnumArrayPropertyNode array (@Nonnull String[] array);

        /**
         * Retrieves an array value.
         *
         * @param enumType The enum type.
         * @param <E>      The enum type.
         * @return The array.
         *
         * @throws java.lang.IllegalStateException when one or more elements are not present within the enum.
         */
        @Nonnull
        <E extends Enum> E[] array (@Nonnull Class<E> enumType) throws IllegalStateException;

        /**
         * Sets an array value.
         *
         * @param array The array.
         * @param <E>   The enum type.
         * @return The node.
         */
        @Nonnull
        <E extends Enum> IEnumArrayPropertyNode array (@Nonnull E[] array);

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        default NodeValueType itemType () {
                return NodeValueType.ENUM;
        }
}
