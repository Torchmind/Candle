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
import com.torchmind.candle.api.IObjectNode;
import com.torchmind.candle.api.error.CandleException;
import com.torchmind.candle.api.error.CandleLexerException;
import com.torchmind.candle.api.error.CandleParserException;
import com.torchmind.candle.node.property.array.NullArrayPropertyNode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

/**
 * Tests {@link com.torchmind.candle.Candle} and associated classes.
 *
 * @author Johannes Donath
 */
@RunWith (MockitoJUnitRunner.class)
public class CandleTest {

        /**
         * Tests {@link com.torchmind.candle.Candle#read(org.antlr.v4.runtime.ANTLRInputStream)}.
         */
        @Test
        public void testLoad () throws CandleException, IOException {
                Candle candle = new Candle ();
                candle.read (CandleTest.class.getResourceAsStream ("/test.cndl"));

                Assert.assertEquals ("Test", candle.getString ("property1"));
                Assert.assertEquals ("Test\"Test", candle.getString ("property2"));
                Assert.assertEquals ("Test\\Test", candle.getString ("property3"));
                Assert.assertEquals ("Test\bTest", candle.getString ("property4"));
                Assert.assertEquals ("Test\fTest", candle.getString ("property5"));
                Assert.assertEquals ("Test\nTest", candle.getString ("property6"));
                Assert.assertEquals ("Test\rTest", candle.getString ("property7"));
                Assert.assertEquals ("Test\tTest", candle.getString ("property8"));
                Assert.assertEquals ("Test\u2764Test", candle.getString ("property9"));

                Assert.assertEquals (1234, candle.getInteger ("property10"));
                Assert.assertEquals (-1234, candle.getInteger ("property11"));
                Assert.assertEquals (0xFF, candle.getInteger ("property12"));

                Assert.assertEquals (1.23f, candle.getFloat ("property13"), 0.001);
                Assert.assertEquals (-1.23f, candle.getFloat ("property14"), 0.001);

                Assert.assertEquals (true, candle.getBoolean ("property15"));
                Assert.assertEquals (false, candle.getBoolean ("property16"));

                Assert.assertEquals ("VALUE1", candle.getEnum ("property17"));

                Assert.assertTrue (candle.isNull ("property18"));

                Assert.assertArrayEquals (new String[] { "Test 1", "Test 2", "Test 3" }, candle.getStringArray ("property19"));
                Assert.assertArrayEquals (new int[] { 1, 2, 3, 4 }, candle.getIntegerArray ("property20"));
                Assert.assertArrayEquals (new int[] { -1, -2, -3, -4 }, candle.getIntegerArray ("property21"));
                Assert.assertArrayEquals (new int[] { 0xA, 0xB, 0xC, 0xD }, candle.getIntegerArray ("property22"));
                Assert.assertArrayEquals (new float[] { 1.1f, 1.2f, 1.3f, 1.4f }, candle.getFloatArray ("property23"), 0.001f);
                Assert.assertArrayEquals (new boolean[] { true, false, true, false }, candle.getBooleanArray ("property24"));
                Assert.assertArrayEquals (new String[] { "VALUE1", "VALUE2", "VALUE3", "VALUE4" }, candle.getEnumArray ("property25"));
                Assert.assertEquals (NullArrayPropertyNode.class, candle.get ("property26").getClass ());
                Assert.assertArrayEquals (new String[] { null, null, "Test 1", null }, candle.getStringArray ("property27"));
                Assert.assertArrayEquals (new String[] { "Test 1", "Test 2", "Test 3", "Test 4" }, candle.getStringArray ("property28"));

                {
                        IObjectNode objectNode = candle.get ("object1", IObjectNode.class);
                        Assert.assertNotNull (objectNode);

                        Assert.assertEquals ("Test", objectNode.getString ("property1"));
                        Assert.assertEquals ("Test\"Test", objectNode.getString ("property2"));
                        Assert.assertEquals ("Test\\Test", objectNode.getString ("property3"));
                        Assert.assertEquals ("Test\bTest", objectNode.getString ("property4"));
                        Assert.assertEquals ("Test\fTest", objectNode.getString ("property5"));
                        Assert.assertEquals ("Test\nTest", objectNode.getString ("property6"));
                        Assert.assertEquals ("Test\rTest", objectNode.getString ("property7"));
                        Assert.assertEquals ("Test\tTest", objectNode.getString ("property8"));
                        Assert.assertEquals ("Test\u2764Test", objectNode.getString ("property9"));

                        Assert.assertEquals (1234, objectNode.getInteger ("property10"));
                        Assert.assertEquals (-1234, objectNode.getInteger ("property11"));
                        Assert.assertEquals (0xFF, objectNode.getInteger ("property12"));

                        Assert.assertEquals (1.23f, objectNode.getFloat ("property13"), 0.001);
                        Assert.assertEquals (-1.23f, objectNode.getFloat ("property14"), 0.001);

                        Assert.assertEquals (true, objectNode.getBoolean ("property15"));
                        Assert.assertEquals (false, objectNode.getBoolean ("property16"));

                        Assert.assertEquals ("VALUE1", objectNode.getEnum ("property17"));

                        Assert.assertTrue (objectNode.isNull ("property18"));

                        Assert.assertArrayEquals (new String[] { "Test 1", "Test 2", "Test 3" }, objectNode.getStringArray ("property19"));
                        Assert.assertArrayEquals (new int[] { 1, 2, 3, 4 }, objectNode.getIntegerArray ("property20"));
                        Assert.assertArrayEquals (new int[] { -1, -2, -3, -4 }, objectNode.getIntegerArray ("property21"));
                        Assert.assertArrayEquals (new int[] { 0xA, 0xB, 0xC, 0xD }, objectNode.getIntegerArray ("property22"));
                        Assert.assertArrayEquals (new float[] { 1.1f, 1.2f, 1.3f, 1.4f }, objectNode.getFloatArray ("property23"), 0.001f);
                        Assert.assertArrayEquals (new boolean[] { true, false, true, false }, objectNode.getBooleanArray ("property24"));
                        Assert.assertArrayEquals (new String[] { "VALUE1", "VALUE2", "VALUE3", "VALUE4" }, objectNode.getEnumArray ("property25"));
                        Assert.assertEquals (NullArrayPropertyNode.class, objectNode.get ("property26").getClass ());
                        Assert.assertArrayEquals (new String[] { null, null, "Test 1", null }, objectNode.getStringArray ("property27"));
                        Assert.assertArrayEquals (new String[] { "Test 1", "Test 2", "Test 3", "Test 4" }, objectNode.getStringArray ("property28"));

                        {
                                Assert.assertEquals ("Test", objectNode.getString ("child1.property1"));
                                Assert.assertEquals ("Test\"Test", objectNode.getString ("child1.property2"));
                                Assert.assertEquals ("Test\\Test", objectNode.getString ("child1.property3"));
                                Assert.assertEquals ("Test\bTest", objectNode.getString ("child1.property4"));
                                Assert.assertEquals ("Test\fTest", objectNode.getString ("child1.property5"));
                                Assert.assertEquals ("Test\nTest", objectNode.getString ("child1.property6"));
                                Assert.assertEquals ("Test\rTest", objectNode.getString ("child1.property7"));
                                Assert.assertEquals ("Test\tTest", objectNode.getString ("child1.property8"));
                                Assert.assertEquals ("Test\u2764Test", objectNode.getString ("child1.property9"));

                                Assert.assertEquals (1234, objectNode.getInteger ("child1.property10"));
                                Assert.assertEquals (-1234, objectNode.getInteger ("child1.property11"));
                                Assert.assertEquals (0xFF, objectNode.getInteger ("child1.property12"));

                                Assert.assertEquals (1.23f, objectNode.getFloat ("child1.property13"), 0.001);
                                Assert.assertEquals (-1.23f, objectNode.getFloat ("child1.property14"), 0.001);

                                Assert.assertEquals (true, objectNode.getBoolean ("child1.property15"));
                                Assert.assertEquals (false, objectNode.getBoolean ("child1.property16"));

                                Assert.assertEquals ("VALUE1", objectNode.getEnum ("child1.property17"));

                                Assert.assertTrue (objectNode.isNull ("child1.property18"));

                                Assert.assertArrayEquals (new String[] { "Test 1", "Test 2", "Test 3" }, objectNode.getStringArray ("child1.property19"));
                                Assert.assertArrayEquals (new int[] { 1, 2, 3, 4 }, objectNode.getIntegerArray ("child1.property20"));
                                Assert.assertArrayEquals (new int[] { -1, -2, -3, -4 }, objectNode.getIntegerArray ("child1.property21"));
                                Assert.assertArrayEquals (new int[] { 0xA, 0xB, 0xC, 0xD }, objectNode.getIntegerArray ("child1.property22"));
                                Assert.assertArrayEquals (new float[] { 1.1f, 1.2f, 1.3f, 1.4f }, objectNode.getFloatArray ("child1.property23"), 0.001f);
                                Assert.assertArrayEquals (new boolean[] { true, false, true, false }, objectNode.getBooleanArray ("child1.property24"));
                                Assert.assertArrayEquals (new String[] { "VALUE1", "VALUE2", "VALUE3", "VALUE4" }, objectNode.getEnumArray ("child1.property25"));
                                Assert.assertEquals (NullArrayPropertyNode.class, objectNode.get ("child1.property26").getClass ());
                                Assert.assertArrayEquals (new String[] { null, null, "Test 1", null }, objectNode.getStringArray ("child1.property27"));
                                Assert.assertArrayEquals (new String[] { "Test 1", "Test 2", "Test 3", "Test 4" }, objectNode.getStringArray ("child1.property28"));
                        }

                        {
                                Assert.assertEquals ("Test", objectNode.getString ("child1.child1.property1"));
                                Assert.assertEquals ("Test\"Test", objectNode.getString ("child1.child1.property2"));
                                Assert.assertEquals ("Test\\Test", objectNode.getString ("child1.child1.property3"));
                                Assert.assertEquals ("Test\bTest", objectNode.getString ("child1.child1.property4"));
                                Assert.assertEquals ("Test\fTest", objectNode.getString ("child1.child1.property5"));
                                Assert.assertEquals ("Test\nTest", objectNode.getString ("child1.child1.property6"));
                                Assert.assertEquals ("Test\rTest", objectNode.getString ("child1.child1.property7"));
                                Assert.assertEquals ("Test\tTest", objectNode.getString ("child1.child1.property8"));
                                Assert.assertEquals ("Test\u2764Test", objectNode.getString ("child1.child1.property9"));

                                Assert.assertEquals (1234, objectNode.getInteger ("child1.child1.property10"));
                                Assert.assertEquals (-1234, objectNode.getInteger ("child1.child1.property11"));
                                Assert.assertEquals (0xFF, objectNode.getInteger ("child1.child1.property12"));

                                Assert.assertEquals (1.23f, objectNode.getFloat ("child1.child1.property13"), 0.001);
                                Assert.assertEquals (-1.23f, objectNode.getFloat ("child1.child1.property14"), 0.001);

                                Assert.assertEquals (true, objectNode.getBoolean ("child1.child1.property15"));
                                Assert.assertEquals (false, objectNode.getBoolean ("child1.child1.property16"));

                                Assert.assertEquals ("VALUE1", objectNode.getEnum ("child1.child1.property17"));

                                Assert.assertTrue (objectNode.isNull ("child1.child1.property18"));

                                Assert.assertArrayEquals (new String[] { "Test 1", "Test 2", "Test 3" }, objectNode.getStringArray ("child1.child1.property19"));
                                Assert.assertArrayEquals (new int[] { 1, 2, 3, 4 }, objectNode.getIntegerArray ("child1.child1.property20"));
                                Assert.assertArrayEquals (new int[] { -1, -2, -3, -4 }, objectNode.getIntegerArray ("child1.child1.property21"));
                                Assert.assertArrayEquals (new int[] { 0xA, 0xB, 0xC, 0xD }, objectNode.getIntegerArray ("child1.child1.property22"));
                                Assert.assertArrayEquals (new float[] { 1.1f, 1.2f, 1.3f, 1.4f }, objectNode.getFloatArray ("child1.child1.property23"), 0.001f);
                                Assert.assertArrayEquals (new boolean[] { true, false, true, false }, objectNode.getBooleanArray ("child1.child1.property24"));
                                Assert.assertArrayEquals (new String[] { "VALUE1", "VALUE2", "VALUE3", "VALUE4" }, objectNode.getEnumArray ("child1.child1.property25"));
                                Assert.assertEquals (NullArrayPropertyNode.class, objectNode.get ("child1.child1.property26").getClass ());
                                Assert.assertArrayEquals (new String[] { null, null, "Test 1", null }, objectNode.getStringArray ("child1.child1.property27"));
                                Assert.assertArrayEquals (new String[] { "Test 1", "Test 2", "Test 3", "Test 4" }, objectNode.getStringArray ("child1.child1.property28"));
                        }
                }

                {
                        Assert.assertNotNull (candle.get ("object1.child2", IObjectNode.class));

                        Assert.assertEquals ("Test", candle.getString ("object1.child2.property1"));
                        Assert.assertEquals ("Test\"Test", candle.getString ("object1.child2.property2"));
                        Assert.assertEquals ("Test\\Test", candle.getString ("object1.child2.property3"));
                        Assert.assertEquals ("Test\bTest", candle.getString ("object1.child2.property4"));
                        Assert.assertEquals ("Test\fTest", candle.getString ("object1.child2.property5"));
                        Assert.assertEquals ("Test\nTest", candle.getString ("object1.child2.property6"));
                        Assert.assertEquals ("Test\rTest", candle.getString ("object1.child2.property7"));
                        Assert.assertEquals ("Test\tTest", candle.getString ("object1.child2.property8"));
                        Assert.assertEquals ("Test\u2764Test", candle.getString ("object1.child2.property9"));

                        Assert.assertEquals (1234, candle.getInteger ("object1.child2.property10"));
                        Assert.assertEquals (-1234, candle.getInteger ("object1.child2.property11"));
                        Assert.assertEquals (0xFF, candle.getInteger ("object1.child2.property12"));

                        Assert.assertEquals (1.23f, candle.getFloat ("object1.child2.property13"), 0.001);
                        Assert.assertEquals (-1.23f, candle.getFloat ("object1.child2.property14"), 0.001);

                        Assert.assertEquals (true, candle.getBoolean ("object1.child2.property15"));
                        Assert.assertEquals (false, candle.getBoolean ("object1.child2.property16"));

                        Assert.assertEquals ("VALUE1", candle.getEnum ("object1.child2.property17"));

                        Assert.assertTrue (candle.isNull ("object1.child2.property18"));

                        Assert.assertArrayEquals (new String[] { "Test 1", "Test 2", "Test 3" }, candle.getStringArray ("object1.child2.property19"));
                        Assert.assertArrayEquals (new int[] { 1, 2, 3, 4 }, candle.getIntegerArray ("object1.child2.property20"));
                        Assert.assertArrayEquals (new int[] { -1, -2, -3, -4 }, candle.getIntegerArray ("object1.child2.property21"));
                        Assert.assertArrayEquals (new int[] { 0xA, 0xB, 0xC, 0xD }, candle.getIntegerArray ("object1.child2.property22"));
                        Assert.assertArrayEquals (new float[] { 1.1f, 1.2f, 1.3f, 1.4f }, candle.getFloatArray ("object1.child2.property23"), 0.001f);
                        Assert.assertArrayEquals (new boolean[] { true, false, true, false }, candle.getBooleanArray ("object1.child2.property24"));
                        Assert.assertArrayEquals (new String[] { "VALUE1", "VALUE2", "VALUE3", "VALUE4" }, candle.getEnumArray ("object1.child2.property25"));
                        Assert.assertEquals (NullArrayPropertyNode.class, candle.get ("object1.child2.property26").getClass ());
                        Assert.assertArrayEquals (new String[] { null, null, "Test 1", null }, candle.getStringArray ("object1.child2.property27"));
                        Assert.assertArrayEquals (new String[] { "Test 1", "Test 2", "Test 3", "Test 4" }, candle.getStringArray ("object1.child2.property28"));
                }
        }

        /**
         * Tests error handling of {@link com.torchmind.candle.Candle#read(org.antlr.v4.runtime.ANTLRInputStream)}.
         */
        @Test (expected = CandleLexerException.class)
        public void testLexerError () throws IOException, CandleException {
                Candle candle = new Candle ();
                candle.read (CandleTest.class.getResourceAsStream ("/testLexerError.cndl"));
        }

        /**
         * Tests error handling of {@link com.torchmind.candle.Candle#read(org.antlr.v4.runtime.ANTLRInputStream)}.
         */
        @Test (expected = CandleParserException.class)
        public void testParserError () throws IOException, CandleException {
                Candle candle = new Candle ();
                candle.read (CandleTest.class.getResourceAsStream ("/testParserError.cndl"));
        }
}
