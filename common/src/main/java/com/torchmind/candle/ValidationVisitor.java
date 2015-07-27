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
package com.torchmind.candle;

import com.torchmind.candle.api.IVisitor;
import com.torchmind.candle.api.NodeValueType;

import javax.annotation.Nonnull;

/**
 * Validates low-level Candle format trees.
 *
 * @author Johannes Donath
 */
public class ValidationVisitor implements IVisitor {
        private int currentLevel = 0;
        private NodeValueType arrayType = null;
        private boolean expectingValue = false;

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitArray () {
                if (!this.expectingValue) {
                        throw new IllegalStateException ("Expected object, property or comment but got array");
                }
                if (this.arrayType != null) { throw new IllegalStateException ("Expected value but got array"); }

                this.arrayType = NodeValueType.NULL;
                this.currentLevel++;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitArrayEnd () {
                if (this.arrayType == null) {
                        throw new IllegalStateException ("Expected " + (this.expectingValue ? "value" : "object, property or comment") + " but got array end");
                }

                this.arrayType = null;
                this.expectingValue = false;
                this.currentLevel--;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitBoolean (boolean value) {
                this.visitValue (NodeValueType.BOOLEAN);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitComment (@Nonnull String text) {
                if (this.expectingValue) {
                        throw new IllegalStateException ("Expected value " + (this.arrayType != null ? " or array end" : "") + " but got comment");
                }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitDefault () {
                if (this.arrayType != null) { throw new IllegalStateException ("Expected value but got default"); }
                this.visitValue (NodeValueType.DEFAULT);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitEnum (@Nonnull String value) {
                this.visitValue (NodeValueType.ENUM);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitFloat (float value) {
                this.visitValue (NodeValueType.FLOAT);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitInteger (int value) {
                this.visitValue (NodeValueType.INTEGER);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitNull () {
                this.visitValue (NodeValueType.NULL);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitObject (@Nonnull String name) {
                if (this.expectingValue) {
                        throw new IllegalStateException ("Expected value " + (this.arrayType != null ? " or array end" : "") + " but got comment");
                }
                this.currentLevel++;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitObjectEnd () {
                if (this.currentLevel == 0) {
                        throw new IllegalStateException ("Expected property, object or comment but got object end");
                }
                this.currentLevel--;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitProperty (@Nonnull String name) {
                if (this.expectingValue) {
                        throw new IllegalStateException ("Expected value " + (this.arrayType != null ? " or array end" : "") + " but got property");
                }
                this.expectingValue = true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void visitString (@Nonnull String value) {
                this.visitValue (NodeValueType.STRING);
        }

        /**
         * Visits a value type.
         */
        private void visitValue (@Nonnull NodeValueType valueType) {
                if (!this.expectingValue) {
                        throw new IllegalStateException ("Expected object, property or comment but got value");
                }

                if (this.arrayType != null && this.arrayType != NodeValueType.NULL) {
                        if (valueType == NodeValueType.NULL && (this.arrayType != NodeValueType.ENUM && this.arrayType != NodeValueType.STRING)) {
                                throw new IllegalStateException ("Expected value of type " + this.arrayType + " but got " + valueType);
                        }
                        if (valueType != NodeValueType.NULL && this.arrayType != valueType) {
                                throw new IllegalStateException ("Expected value of type " + this.arrayType + " but got " + valueType);
                        }
                }

                if (this.arrayType == NodeValueType.NULL && valueType != NodeValueType.NULL) {
                        this.arrayType = valueType;
                }

                if (this.arrayType != null) { return; }
                this.expectingValue = false;
        }
}
