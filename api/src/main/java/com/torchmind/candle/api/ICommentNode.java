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

import javax.annotation.Nonnull;

/**
 * Represents a single-line or multi-line comment node.
 *
 * @author Johannes Donath
 */
public interface ICommentNode extends INode {

        /**
         * Checks whether the node represents a multiline comment.
         *
         * @return The comment.
         */
        boolean isMultiline ();

        /**
         * Sets the comment contents.
         *
         * @param text The text.
         * @return The node.
         */
        @Nonnull
        ICommentNode text (@Nonnull String text);

        /**
         * Retrieves the comment contents.
         *
         * @return The text.
         */
        @Nonnull
        String text ();

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        default NodeType type () {
                return NodeType.COMMENT;
        }
}
