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
package com.torchmind.candle.api;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * Represents a property assignment node that uses array values.
 * @author Johannes Donath
 */
public interface IArrayPropertyNode extends IPropertyNode {

        /**
         * Ensures the array consists of a certain type of element.
         * @param valueType The expected value type.
         * @return The node.
         * @throws java.lang.IllegalStateException when the expected value type differs.
         */
        @Nonnull
        IArrayPropertyNode ensureItemType (@Nonnull NodeValueType valueType) throws IllegalStateException;

        /**
         * Retrieves the array item type.
         * @return The type.
         */
        @Nonnull
        NodeValueType itemType ();

        /**
         * Retrieves the array length.
         * @return The length.
         */
        @Nonnegative
        int length ();

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        default NodeValueType valueType () {
                return NodeValueType.ARRAY;
        }
}
