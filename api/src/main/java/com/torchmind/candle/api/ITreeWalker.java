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

import com.torchmind.candle.api.property.*;
import com.torchmind.candle.api.property.array.*;

import javax.annotation.Nonnull;

/**
 * Provides a base interface for tree walkers.
 *
 * @author Johannes Donath
 */
public interface ITreeWalker {

        /**
         * Visits an array property node.
         *
         * @param document The document.
         * @param node     The node.
         */
        void visitArrayPropertyNode (@Nonnull IDocumentNode document, @Nonnull IArrayPropertyNode node);

        /**
         * Visits a property node of type {@link com.torchmind.candle.api.property.array.IBooleanArrayPropertyNode}.
         *
         * @param document The document.
         * @param node     The node.
         */
        void visitArrayPropertyNode (@Nonnull IDocumentNode document, @Nonnull IBooleanArrayPropertyNode node);

        /**
         * Visits a property node of type {@link com.torchmind.candle.api.property.array.IEnumArrayPropertyNode}.
         *
         * @param document The document.
         * @param node     The node.
         */
        void visitArrayPropertyNode (@Nonnull IDocumentNode document, @Nonnull IEnumArrayPropertyNode node);

        /**
         * Visits a property node of type {@link com.torchmind.candle.api.property.array.IFloatArrayPropertyNode}.
         *
         * @param document The document.
         * @param node     The node.
         */
        void visitArrayPropertyNode (@Nonnull IDocumentNode document, @Nonnull IFloatArrayPropertyNode node);

        /**
         * Visits a property node of type {@link com.torchmind.candle.api.property.array.IIntegerArrayPropertyNode}.
         *
         * @param document The document.
         * @param node     The node.
         */
        void visitArrayPropertyNode (@Nonnull IDocumentNode document, @Nonnull IIntegerArrayPropertyNode node);

        /**
         * Visits a property node of type {@link com.torchmind.candle.api.property.array.INullArrayPropertyNode}.
         *
         * @param document The document.
         * @param node     The node.
         */
        void visitArrayPropertyNode (@Nonnull IDocumentNode document, @Nonnull INullArrayPropertyNode node);

        /**
         * Visits a property node of type {@link com.torchmind.candle.api.property.array.IStringArrayPropertyNode}.
         *
         * @param document The document.
         * @param node     The node.
         */
        void visitArrayPropertyNode (@Nonnull IDocumentNode document, @Nonnull IStringArrayPropertyNode node);

        /**
         * Visits an array property node end.
         *
         * @param document The document.
         * @param node     The node.
         */
        void visitArrayPropertyNodeEnd (@Nonnull IDocumentNode document, @Nonnull IArrayPropertyNode node);

        /**
         * Visits a comment node.
         *
         * @param document The document.
         * @param node     The node.
         */
        void visitCommentNode (@Nonnull IDocumentNode document, @Nonnull ICommentNode node);

        /**
         * Visits a document node.
         *
         * @param node The node.
         */
        void visitDocumentNode (@Nonnull IDocumentNode node);

        /**
         * Visits a document node end.
         *
         * @param document The document.
         */
        void visitDocumentNodeEnd (@Nonnull IDocumentNode document);

        /**
         * Visits an object node.
         *
         * @param document The document.
         * @param node     The node.
         */
        void visitObjectNode (@Nonnull IDocumentNode document, @Nonnull IObjectNode node);

        /**
         * Visits an object node end.
         *
         * @param document The document.
         * @param node     The node.
         */
        void visitObjectNodeEnd (@Nonnull IDocumentNode document, @Nonnull IObjectNode node);

        /**
         * Visits a property node.
         * <strong>Note:</strong> Instances of {@link com.torchmind.candle.api.property.array.IArrayPropertyNode} are handled by
         * {@link #visitArrayPropertyNode(IDocumentNode, com.torchmind.candle.api.property.array.IArrayPropertyNode)}.
         *
         * @param document The document.
         * @param node     The node.
         */
        void visitPropertyNode (@Nonnull IDocumentNode document, @Nonnull IPropertyNode node);

        /**
         * Visits a property node of type {@link com.torchmind.candle.api.property.IBooleanPropertyNode}.
         *
         * @param document The document.
         * @param node     The node.
         */
        void visitPropertyNode (@Nonnull IDocumentNode document, @Nonnull IBooleanPropertyNode node);

        /**
         * Visits a property node of type {@link com.torchmind.candle.api.property.IDefaultPropertyNode}.
         *
         * @param document The document.
         * @param node     The node.
         */
        void visitPropertyNode (@Nonnull IDocumentNode document, @Nonnull IDefaultPropertyNode node);

        /**
         * Visits a property node of type {@link com.torchmind.candle.api.property.IEnumPropertyNode}.
         *
         * @param document The document.
         * @param node     The node.
         */
        void visitPropertyNode (@Nonnull IDocumentNode document, @Nonnull IEnumPropertyNode node);

        /**
         * Visits a property node of type {@link com.torchmind.candle.api.property.IFloatPropertyNode}.
         *
         * @param document The document.
         * @param node     The node.
         */
        void visitPropertyNode (@Nonnull IDocumentNode document, @Nonnull IFloatPropertyNode node);

        /**
         * Visits a property node of type {@link com.torchmind.candle.api.property.IIntegerPropertyNode}.
         *
         * @param document The document.
         * @param node     The node.
         */
        void visitPropertyNode (@Nonnull IDocumentNode document, @Nonnull IIntegerPropertyNode node);

        /**
         * Visits a property node of type {@link com.torchmind.candle.api.property.INullPropertyNode}.
         *
         * @param document The document.
         * @param node     The node.
         */
        void visitPropertyNode (@Nonnull IDocumentNode document, @Nonnull INullPropertyNode node);

        /**
         * Visits a property node of type {@link com.torchmind.candle.api.property.IStringPropertyNode}.
         *
         * @param document The document.
         * @param node     The node.
         */
        void visitPropertyNode (@Nonnull IDocumentNode document, @Nonnull IStringPropertyNode node);

        /**
         * Visits a property node end.
         *
         * @param document The document.
         * @param node     The node.
         */
        void visitPropertyNodeEnd (@Nonnull IDocumentNode document, @Nonnull IPropertyNode node);
}
