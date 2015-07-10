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

import com.torchmind.candle.Candle;
import com.torchmind.candle.CandleSerializer;
import com.torchmind.candle.api.error.CandleException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.*;

/**
 * Provides test cases for {@link com.torchmind.candle.CandleSerializer}.
 * @author Johannes Donath
 */
@RunWith (MockitoJUnitRunner.class)
public class CandleSerializerTest {

        /**
         * Tests the standard serialization format written by {@link com.torchmind.candle.CandleSerializer#serialize(com.torchmind.candle.api.IDocumentNode, java.io.Writer)}.
         */
        @Test
        public void testStandardWrite () throws CandleException, IOException {
                // build expected value
                // Note: This is only needed due to the fact that Candle files utilize system specific line separators
                BufferedReader reader = new BufferedReader (new InputStreamReader (CandleSerializerTest.class.getResourceAsStream ("/testSerialized.cndl")));
                StringBuilder expected = new StringBuilder ();

                reader.lines ().forEach (s -> {
                        expected.append (s);
                        expected.append ("\n");
                });

                Candle candle = Candle.readFile (CandleSerializerTest.class.getResourceAsStream ("/testSerialized.cndl"));
                CandleSerializer serializer = new CandleSerializer ().newline ("\n");

                StringWriter writer = new StringWriter ();
                serializer.serialize (candle, new File ("testSerialized.cndl"));
                serializer.serialize (candle, writer);

                Assert.assertEquals (expected.toString (), writer.toString ());
        }
}
