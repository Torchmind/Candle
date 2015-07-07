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

/**
 * Provides a list of valid node types.
 * @author Johannes Donath
 */
public enum NodeType {
        /**
         * Represents a Single-Line or Multi-Line comment.
         * Nodes that identify themselves as comment nodes implement {@link com.torchmind.candle.api.ICommentNode}.
         */
        COMMENT,

        /**
         * Represents an inclusion.
         * <strong>Note:</strong> There are no definitions for this node type yet as this type has not been fully documented
         * in the Candle specification yet.
         */
        INCLUDE,

        /**
         * Represents a document.
         * Nodes that identify themselves as document nodes implement {@link com.torchmind.candle.api.IDocumentNode}.
         */
        DOCUMENT,

        /**
         * Represents an object.
         * Nodes that identify themselves as object nodes implement {@link com.torchmind.candle.api.IObjectNode}.
         */
        OBJECT,

        /**
         * Represents a property.
         */
        PROPERTY
}
