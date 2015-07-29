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

import com.torchmind.candle.CandleParser;
import com.torchmind.candle.api.IVisitor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

/**
 * Provides test cases for {@link com.torchmind.candle.CandleParser}.
 * @author Johannes Donath
 */
@RunWith (MockitoJUnitRunner.class)
public class CandleParserTest {
        @Mock
        private IVisitor visitor;

        /**
         * Tests {@link com.torchmind.candle.CandleParser#parse(java.io.InputStream)}.
         */
        @Test
        public void testParse () throws IOException {
                CandleParser parser = new CandleParser (this.visitor);
                parser.parse (CandleParserTest.class.getResourceAsStream ("/test.cndl"));

                // @formatter:off
                Mockito.verify (this.visitor, Mockito.times (50))
                        .visitArray ();
                Mockito.verify (this.visitor, Mockito.times (50))
                        .visitArrayEnd ();

                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitString ("Test");
                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitString ("Test\"Test");
                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitString ("Test\\Test");
                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitString ("Test\bTest");
                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitString ("Test\fTest");
                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitString ("Test\nTest");
                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitString ("Test\rTest");
                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitString ("Test\tTest");
                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitString ("Test\u2764Test");
                Mockito.verify (this.visitor, Mockito.times (15))
                        .visitString ("Test 1");
                Mockito.verify (this.visitor, Mockito.times (10))
                        .visitString ("Test 2");
                Mockito.verify (this.visitor, Mockito.times (10))
                        .visitString ("Test 3");
                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitString ("Test 4");

                Mockito.verify (this.visitor, Mockito.times (10))
                        .visitEnum ("VALUE1");
                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitEnum ("VALUE2");
                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitEnum ("VALUE3");
                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitEnum ("VALUE4");

                Mockito.verify (this.visitor, Mockito.times (40))
                        .visitNull ();

                Mockito.verify (this.visitor, Mockito.times (15))
                        .visitBoolean (true);
                Mockito.verify (this.visitor, Mockito.times (15))
                        .visitBoolean (false);

                Mockito.verify (this.visitor)
                        .visitComment ("\n * I am a nice small multi-line comment (Seriously did you really think I'm going to copy the entire Apache 2.0\n * license header into the test? That would be a bit excessive wouldn't it?\n ");
                Mockito.verify (this.visitor)
                        .visitComment (" Test Comment");

                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitDefault ();

                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitFloat (1.23f);
                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitFloat (-1.23f);
                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitFloat (1.1f);
                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitFloat (1.2f);
                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitFloat (1.3f);
                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitFloat (1.4f);

                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitInteger (1234);
                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitInteger (-1234);
                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitInteger (0xFF);
                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitInteger (1);
                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitInteger (2);
                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitInteger (3);
                Mockito.verify (this.visitor, Mockito.times (5))
                        .visitInteger (4);

                Mockito.verify (this.visitor)
                        .visitObject ("object1");
                Mockito.verify (this.visitor, Mockito.times (2))
                        .visitObject ("child1");
                Mockito.verify (this.visitor)
                        .visitObject ("child2");
                Mockito.verify (this.visitor, Mockito.times (4))
                        .visitObjectEnd ();
                // @formatter:on
        }
}
