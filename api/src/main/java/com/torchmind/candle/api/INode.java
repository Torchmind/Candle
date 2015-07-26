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
 * Represents a node within the document tree.
 *
 * @author Johannes Donath
 */
public interface INode {

        /**
         * Visits a node.
         *
         * @param visitor The visitor.
         * @return The node.
         */
        @Nonnull
        INode accept (@Nonnull IVisitor visitor);

        /**
         * Retrieves the parent document node.
         *
         * @return The node.
         */
        @Nonnull
        IDocumentNode document ();

        /**
         * Ensures that a node consists of a certain type.
         *
         * @param type The type.
         * @return The node.
         *
         * @throws java.lang.IllegalStateException when the node type differs.
         */
        @Nonnull
        INode ensureType (@Nonnull NodeType type) throws IllegalStateException;

        /**
         * Retrieves the node type.
         *
         * @return The type.
         */
        @Nonnull
        NodeType type ();
}
