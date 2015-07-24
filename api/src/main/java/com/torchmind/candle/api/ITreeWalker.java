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

import com.torchmind.candle.api.property.IArrayPropertyNode;
import com.torchmind.candle.api.property.IPropertyNode;

import javax.annotation.Nonnull;

/**
 * Provides a base interface for tree walkers.
 * @author Johannes Donath
 */
public interface ITreeWalker {

        /**
         * Visits a document node.
         * @param node The node.
         */
        void visitDocumentNode (@Nonnull IDocumentNode node);

        /**
         * Visits a document node end.
         * @param document The document.
         */
        void visitDocumentNodeEnd (@Nonnull IDocumentNode document);

        /**
         * Visits an object node.
         * @param document The document.
         * @param node The node.
         */
        void visitObjectNode (@Nonnull IDocumentNode document, @Nonnull IObjectNode node);

        /**
         * Visits an object node end.
         * @param document The document.
         * @param node The node.
         */
        void visitObjectNodeEnd (@Nonnull IDocumentNode document, @Nonnull IObjectNode node);

        /**
         * Visits a comment node.
         * @param document The document.
         * @param node The node.
         */
        void visitCommentNode (@Nonnull IDocumentNode document, @Nonnull ICommentNode node);

        /**
         * Visits a property node.
         * <strong>Note:</strong> Instances of {@link com.torchmind.candle.api.property.IArrayPropertyNode} are handled by
         * {@link #visitPropertyNode(IDocumentNode, com.torchmind.candle.api.property.IArrayPropertyNode)}.
         * @param document The document.
         * @param node The node.
         */
        void visitPropertyNode (@Nonnull IDocumentNode document, @Nonnull IPropertyNode node);

        /**
         * Visits an array property node.
         * @param document The document.
         * @param node The node.
         */
        void visitPropertyNode (@Nonnull IDocumentNode document, @Nonnull IArrayPropertyNode node);
}
