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
package com.torchmind.candle.test;

import com.torchmind.candle.ValidationVisitor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Provides test cases for {@link com.torchmind.candle.ValidationVisitor}.
 *
 * @author Johannes Donath
 */
@RunWith (MockitoJUnitRunner.class)
public class ValidationVisitorTest {
        private ValidationVisitor visitor;

        @Before
        public void setup () {
                this.visitor = new ValidationVisitor ();
        }

        @Test (expected = IllegalStateException.class)
        public void testArrayArray () {
                this.visitor.visitProperty ("property");
                this.visitor.visitArray ();
                this.visitor.visitArray ();
        }

        @Test (expected = IllegalStateException.class)
        public void testDefaultArray () {
                this.visitor.visitProperty ("property");
                this.visitor.visitArray ();
                this.visitor.visitDefault ();
        }

        @Test (expected = IllegalStateException.class)
        public void testEarlyArray () {
                this.visitor.visitArray ();
        }

        @Test (expected = IllegalStateException.class)
        public void testEarlyArrayEnd () {
                this.visitor.visitProperty ("property");
                this.visitor.visitArrayEnd ();
        }

        @Test (expected = IllegalStateException.class)
        public void testEarlyBoolean () {
                this.visitor.visitBoolean (true);
        }

        @Test (expected = IllegalStateException.class)
        public void testEarlyDefault () {
                this.visitor.visitDefault ();
        }

        @Test (expected = IllegalStateException.class)
        public void testEarlyEnum () {
                this.visitor.visitEnum ("TEST1");
        }

        @Test (expected = IllegalStateException.class)
        public void testEarlyFloat () {
                this.visitor.visitFloat (42.0f);
        }

        @Test (expected = IllegalStateException.class)
        public void testEarlyInteger () {
                this.visitor.visitInteger (42);
        }

        @Test (expected = IllegalStateException.class)
        public void testEarlyNull () {
                this.visitor.visitNull ();
        }

        @Test (expected = IllegalStateException.class)
        public void testEarlyObjectEnd () {
                this.visitor.visitObjectEnd ();
        }

        @Test (expected = IllegalStateException.class)
        public void testLateComment () {
                this.visitor.visitProperty ("property");
                this.visitor.visitComment ("Comment");
        }

        @Test (expected = IllegalStateException.class)
        public void testLateObject () {
                this.visitor.visitProperty ("property");
                this.visitor.visitObject ("object");
        }

        @Test (expected = IllegalStateException.class)
        public void testMixedBooleanArray () {
                this.visitor.visitProperty ("property");
                this.visitor.visitArray ();
                this.visitor.visitBoolean (true);
                this.visitor.visitInteger (42);
        }

        @Test (expected = IllegalStateException.class)
        public void testMixedEnumArray () {
                this.visitor.visitProperty ("property");
                this.visitor.visitArray ();
                this.visitor.visitEnum ("TEST1");
                this.visitor.visitInteger (42);
        }

        @Test (expected = IllegalStateException.class)
        public void testMixedFloatArray () {
                this.visitor.visitProperty ("property");
                this.visitor.visitArray ();
                this.visitor.visitFloat (42.0f);
                this.visitor.visitInteger (42);
        }

        @Test (expected = IllegalStateException.class)
        public void testMixedIntegerArray () {
                this.visitor.visitProperty ("property");
                this.visitor.visitArray ();
                this.visitor.visitInteger (42);
                this.visitor.visitFloat (42.0f);
        }

        @Test (expected = IllegalStateException.class)
        public void testNullBooleanArray () {
                this.visitor.visitProperty ("property");
                this.visitor.visitArray ();
                this.visitor.visitBoolean (true);
                this.visitor.visitNull ();
        }

        @Test
        public void testNullEnumArray () {
                this.visitor.visitProperty ("property");
                this.visitor.visitArray ();
                this.visitor.visitEnum ("TEST1");
                this.visitor.visitNull ();
        }

        @Test (expected = IllegalStateException.class)
        public void testNullFloatArray () {
                this.visitor.visitProperty ("property");
                this.visitor.visitArray ();
                this.visitor.visitFloat (42.0f);
                this.visitor.visitNull ();
        }

        @Test (expected = IllegalStateException.class)
        public void testNullIntegerArray () {
                this.visitor.visitProperty ("property");
                this.visitor.visitArray ();
                this.visitor.visitInteger (42);
                this.visitor.visitNull ();
        }

        @Test
        public void testSuccess () {
                this.visitor.visitProperty ("property1");
                this.visitor.visitArray ();
                this.visitor.visitBoolean (true);
                this.visitor.visitBoolean (false);
                this.visitor.visitBoolean (true);
                this.visitor.visitArrayEnd ();

                this.visitor.visitProperty ("property1");
                this.visitor.visitArray ();
                this.visitor.visitEnum ("TEST1");
                this.visitor.visitEnum ("TEST2");
                this.visitor.visitEnum ("TEST3");
                this.visitor.visitArrayEnd ();

                this.visitor.visitProperty ("property1");
                this.visitor.visitArray ();
                this.visitor.visitFloat (1.1f);
                this.visitor.visitFloat (1.2f);
                this.visitor.visitFloat (1.3f);
                this.visitor.visitArrayEnd ();

                this.visitor.visitProperty ("property1");
                this.visitor.visitArray ();
                this.visitor.visitInteger (21);
                this.visitor.visitInteger (42);
                this.visitor.visitInteger (84);
                this.visitor.visitArrayEnd ();

                this.visitor.visitProperty ("property1");
                this.visitor.visitArray ();
                this.visitor.visitNull ();
                this.visitor.visitNull ();
                this.visitor.visitNull ();
                this.visitor.visitArrayEnd ();

                this.visitor.visitProperty ("property1");
                this.visitor.visitArray ();
                this.visitor.visitArrayEnd ();

                this.visitor.visitProperty ("property1");
                this.visitor.visitArray ();
                this.visitor.visitString ("Test 1");
                this.visitor.visitString ("Test 2");
                this.visitor.visitString ("Test 3");
                this.visitor.visitArrayEnd ();

                this.visitor.visitProperty ("property1");
                this.visitor.visitBoolean (true);

                this.visitor.visitProperty ("property1");
                this.visitor.visitDefault ();

                this.visitor.visitProperty ("property1");
                this.visitor.visitEnum ("TEST1");

                this.visitor.visitProperty ("property1");
                this.visitor.visitFloat (1.23f);

                this.visitor.visitProperty ("property1");
                this.visitor.visitInteger (42);

                this.visitor.visitProperty ("property1");
                this.visitor.visitNull ();

                this.visitor.visitProperty ("property1");
                this.visitor.visitString ("property1");

                this.visitor.visitObject ("object1");
                this.visitor.visitObjectEnd ();

                this.visitor.visitObject ("object1");
                {
                        this.visitor.visitProperty ("property1");
                        this.visitor.visitArray ();
                        this.visitor.visitBoolean (true);
                        this.visitor.visitBoolean (false);
                        this.visitor.visitBoolean (true);
                        this.visitor.visitArrayEnd ();

                        this.visitor.visitProperty ("property1");
                        this.visitor.visitArray ();
                        this.visitor.visitEnum ("TEST1");
                        this.visitor.visitEnum ("TEST2");
                        this.visitor.visitEnum ("TEST3");
                        this.visitor.visitArrayEnd ();

                        this.visitor.visitProperty ("property1");
                        this.visitor.visitArray ();
                        this.visitor.visitFloat (1.1f);
                        this.visitor.visitFloat (1.2f);
                        this.visitor.visitFloat (1.3f);
                        this.visitor.visitArrayEnd ();

                        this.visitor.visitProperty ("property1");
                        this.visitor.visitArray ();
                        this.visitor.visitInteger (21);
                        this.visitor.visitInteger (42);
                        this.visitor.visitInteger (84);
                        this.visitor.visitArrayEnd ();

                        this.visitor.visitProperty ("property1");
                        this.visitor.visitArray ();
                        this.visitor.visitNull ();
                        this.visitor.visitNull ();
                        this.visitor.visitNull ();
                        this.visitor.visitArrayEnd ();

                        this.visitor.visitProperty ("property1");
                        this.visitor.visitArray ();
                        this.visitor.visitArrayEnd ();

                        this.visitor.visitProperty ("property1");
                        this.visitor.visitArray ();
                        this.visitor.visitString ("Test 1");
                        this.visitor.visitString ("Test 2");
                        this.visitor.visitString ("Test 3");
                        this.visitor.visitArrayEnd ();

                        this.visitor.visitProperty ("property1");
                        this.visitor.visitBoolean (true);

                        this.visitor.visitProperty ("property1");
                        this.visitor.visitDefault ();

                        this.visitor.visitProperty ("property1");
                        this.visitor.visitEnum ("TEST1");

                        this.visitor.visitProperty ("property1");
                        this.visitor.visitFloat (1.23f);

                        this.visitor.visitProperty ("property1");
                        this.visitor.visitInteger (42);

                        this.visitor.visitProperty ("property1");
                        this.visitor.visitNull ();

                        this.visitor.visitProperty ("property1");
                        this.visitor.visitString ("property1");

                        this.visitor.visitObject ("object1");
                        this.visitor.visitObjectEnd ();
                }
                this.visitor.visitObjectEnd ();
        }
}
