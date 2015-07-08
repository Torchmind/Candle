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
package com.torchmind.candle.antlr;

import com.torchmind.candle.Candle;
import com.torchmind.candle.api.error.CandleParserException;
import com.torchmind.candle.node.CommentNode;
import com.torchmind.candle.node.ObjectNode;
import com.torchmind.candle.node.property.*;
import com.torchmind.candle.node.property.array.*;

import javax.annotation.Nonnull;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Supplier;

/**
 * @author Johannes Donath
 */
public class CandleListener extends CandleParserBaseListener {
        private final Candle candle;


        private String lastIdentifier;
        private final Stack<ObjectNode> objectNodeStack = new Stack<> ();
        private List<Object> arrayContent = null;

        public CandleListener (@Nonnull Candle candle) {
                this.candle = candle;
                this.objectNodeStack.push (candle);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void enterCommentMultiline (CandleParser.CommentMultilineContext ctx) {
                this.objectNodeStack.peek ().append (new CommentNode (this.candle, ctx.getText ().substring (2, (ctx.getText ().length () - 2))));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void enterCommentSingleline (CandleParser.CommentSinglelineContext ctx) {
                this.objectNodeStack.peek ().append (new CommentNode (this.candle, ctx.getText ().substring (2)));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void exitObject (CandleParser.ObjectContext ctx) {
                this.objectNodeStack.pop ();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void enterObjectIdentifier (CandleParser.ObjectIdentifierContext ctx) {
                ObjectNode node = new ObjectNode (this.candle, ctx.getText ());

                this.objectNodeStack.peek ().append (node);
                this.objectNodeStack.push (node);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void enterPropertyIdentifier (CandleParser.PropertyIdentifierContext ctx) {
                this.lastIdentifier = ctx.getText ();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void enterPropertyValueBoolean (CandleParser.PropertyValueBooleanContext ctx) {
                boolean value = Boolean.parseBoolean (ctx.getText ());

                if (this.arrayContent != null)
                        this.arrayContent.add (value);
                else {
                        this.objectNodeStack.peek ().append (new BooleanPropertyNode (this.candle, this.lastIdentifier, value));
                        this.lastIdentifier = null;
                }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void enterPropertyValueEnum (CandleParser.PropertyValueEnumContext ctx) {
                String value = ctx.getText ();

                if (this.arrayContent != null)
                        this.arrayContent.add (new EnumWrapper (value));
                else {
                        this.objectNodeStack.peek ().append (new EnumPropertyNode (this.candle, this.lastIdentifier, value));
                        this.lastIdentifier = null;
                }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void enterPropertyValueFloat (CandleParser.PropertyValueFloatContext ctx) {
                float value = Float.parseFloat (ctx.getText ());

                if (this.arrayContent != null)
                        this.arrayContent.add (value);
                else {
                        this.objectNodeStack.peek ().append (new FloatPropertyNode (this.candle, this.lastIdentifier, value));
                        this.lastIdentifier = null;
                }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void enterPropertyValueInteger (CandleParser.PropertyValueIntegerContext ctx) {
                int value = Integer.decode (ctx.getText ());

                if (this.arrayContent != null)
                        this.arrayContent.add (value);
                else {
                        this.objectNodeStack.peek ().append (new IntegerPropertyNode (this.candle, this.lastIdentifier, value));
                        this.lastIdentifier = null;
                }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void enterPropertyValueNull (CandleParser.PropertyValueNullContext ctx) {
                if (this.arrayContent != null)
                        this.arrayContent.add (null);
                else {
                        this.objectNodeStack.peek ().append (new NullPropertyNode (this.candle, this.lastIdentifier));
                        this.lastIdentifier = null;
                }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void enterPropertyValueString (CandleParser.PropertyValueStringContext ctx) {
                String value = ctx.getText ().substring (1);
                value = value.substring (0, value.length () - 1);

                if (this.arrayContent != null)
                        this.arrayContent.add (value);
                else {
                        this.objectNodeStack.peek ().append (new StringPropertyNode (this.candle, this.lastIdentifier, value));
                        this.lastIdentifier = null;
                }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void enterPropertyValueArray (CandleParser.PropertyValueArrayContext ctx) {
                this.arrayContent = new ArrayList<> ();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void exitPropertyValueArray (CandleParser.PropertyValueArrayContext ctx) {
                List<Object> arrayContent = this.arrayContent;
                this.arrayContent = null;

                Object firstSaneValue = null;
                Iterator<Object> arrayContentIterator = arrayContent.iterator ();
                while (arrayContentIterator.hasNext () && firstSaneValue == null) {
                        firstSaneValue = arrayContentIterator.next ();
                }

                if (firstSaneValue == null)
                        this.objectNodeStack.peek ().append (new NullArrayPropertyNode (this.candle, this.lastIdentifier));
                else if (firstSaneValue.getClass ().equals (Boolean.class))
                        this.objectNodeStack.peek ().append (new BooleanArrayPropertyNode (this.candle, this.lastIdentifier, this.getArrayContents (arrayContent, Boolean.class, () -> false)));
                else if (firstSaneValue.getClass ().equals (EnumWrapper.class)) {
                        String[] values = Arrays.stream (this.getArrayContents (arrayContent, EnumWrapper.class)).map ((e) -> {
                                if (e == null) return null;
                                return e.name;
                        }).toArray (String[]::new);

                        this.objectNodeStack.peek ().append (new EnumArrayPropertyNode (this.candle, this.lastIdentifier, values));
                } else if (firstSaneValue.getClass ().equals (Float.class))
                        this.objectNodeStack.peek ().append (new FloatArrayPropertyNode (this.candle, this.lastIdentifier, this.getArrayContents (arrayContent, Float.class, () -> 0.0f)));
                else if (firstSaneValue.getClass ().equals (Integer.class))
                        this.objectNodeStack.peek ().append (new IntegerArrayPropertyNode (this.candle, this.lastIdentifier, this.getArrayContents (arrayContent, Integer.class, () -> 0)));
                else if (firstSaneValue.getClass ().equals (String.class))
                        this.objectNodeStack.peek ().append (new StringArrayPropertyNode (this.candle, this.lastIdentifier, this.getArrayContents (arrayContent, String.class)));
                else
                        throw new RuntimeException (new CandleParserException ("Cannot handle array element of type " + firstSaneValue.getClass ().getCanonicalName ()));
        }

        /**
         * Retrieves the array contents.
         *
         * @param objects The array list.
         * @param type    The expected array type.
         * @param <R>     The expected array type.
         * @return The array.
         */
        @Nonnull
        protected <R> R[] getArrayContents (@Nonnull List<Object> objects, @Nonnull Class<R> type) {
                return this.getArrayContents (objects, type, () -> null);
        }

        /**
         * Retrieves the array contents.
         *
         * @param objects The array list.
         * @param type    The expected array type.
         * @param ifNull  The supplier for null values.
         * @param <R>     The expected array type.
         * @return The array.
         */
        @Nonnull
        @SuppressWarnings ("unchecked")
        protected <R> R[] getArrayContents (@Nonnull List<Object> objects, @Nonnull Class<R> type, @Nonnull Supplier<R> ifNull) {
                // @formatter:off
                return objects.stream ()
                              .map ((o) -> {
                                      if (o != null && !type.isAssignableFrom (o.getClass ())) throw new IllegalStateException ("Could not decode array contents: Expected element of type " + type.getCanonicalName () + " but got " + o.getClass ().getCanonicalName ());
                                      return (o == null ? ifNull.get () : o);
                              })
                              .toArray ((i) -> ((R[]) Array.newInstance (type, i)));
                // @formatter:on
        }

        /**
         * Represents enum values within array stacks.
         */
        private static class EnumWrapper {
                public String name;

                public EnumWrapper (@Nonnull String name) {
                        this.name = name;
                }
        }
}
