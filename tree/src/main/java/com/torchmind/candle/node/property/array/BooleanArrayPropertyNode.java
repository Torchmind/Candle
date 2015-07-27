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
package com.torchmind.candle.node.property.array;

import com.torchmind.candle.api.IDocumentNode;
import com.torchmind.candle.api.ITreeVisitor;
import com.torchmind.candle.api.IVisitor;
import com.torchmind.candle.api.property.array.IBooleanArrayPropertyNode;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.Arrays;

/**
 * Represents a boolean array property within the tree.
 *
 * @author Johannes Donath
 */
public class BooleanArrayPropertyNode extends AbstractArrayPropertyNode implements IBooleanArrayPropertyNode {
        private boolean[] array;

        public BooleanArrayPropertyNode (@Nonnull IDocumentNode documentNode, @Nonnull String name, @Nonnull boolean[] array) {
                super (documentNode, name);

                this.array (array);
        }

        public BooleanArrayPropertyNode (@Nonnull IDocumentNode documentNode, @Nonnull String name, @Nonnull Boolean[] array) {
                super (documentNode, name);

                this.array (array);
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public BooleanArrayPropertyNode accept (@Nonnull IVisitor visitor) {
                super.accept (visitor);

                visitor.visitArray ();

                for (boolean current : this.array ()) {
                        visitor.visitBoolean (current);
                }

                visitor.visitArrayEnd ();
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public BooleanArrayPropertyNode accept (@Nonnull ITreeVisitor visitor) {
                super.accept (visitor);

                visitor.visitArrayPropertyNode (this.document (), this);
                visitor.visitArrayPropertyNodeEnd (this.document (), this);

                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public BooleanArrayPropertyNode array (@Nonnull boolean[] array) {
                this.array = array;
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public IBooleanArrayPropertyNode array (@Nonnull Boolean[] array) {
                boolean[] primitiveArray = new boolean[array.length];
                for (int i = 0; i < primitiveArray.length; i++) { primitiveArray[i] = array[i]; }
                return this.array (primitiveArray);
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public boolean[] array () {
                return this.array;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @Nonnegative
        public int length () {
                return this.array.length;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString () {
                return String.format ("BooleanArrayPropertyNode{%s,array=%s}", super.toString (), Arrays.toString (this.array ()));
        }
}
