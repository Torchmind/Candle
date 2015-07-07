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
package com.torchmind.candle.node;

import com.torchmind.candle.api.ICommentNode;
import com.torchmind.candle.api.IDocumentNode;

import javax.annotation.Nonnull;

/**
 * Provides an implementation of {@link }
 * @author Johannes Donath
 */
public class CommentNode extends AbstractNode implements ICommentNode {
        private String content;
        private boolean multiline;

        public CommentNode (@Nonnull IDocumentNode documentNode, @Nonnull String content) {
                super (documentNode);

                this.text (content);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isMultiline () {
                return this.multiline;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public ICommentNode text (@Nonnull String text) {
                this.content = text;
                this.multiline = text.contains ("\n");
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public String text () {
                return this.content;
        }
}
