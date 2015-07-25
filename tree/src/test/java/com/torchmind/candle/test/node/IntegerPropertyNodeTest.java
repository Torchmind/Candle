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
package com.torchmind.candle.test.node;

import com.torchmind.candle.Candle;
import com.torchmind.candle.node.property.IntegerPropertyNode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Provides test cases for {@link com.torchmind.candle.node.property.IntegerPropertyNode}.
 *
 * @author Johannes Donath
 */
@RunWith (MockitoJUnitRunner.class)
public class IntegerPropertyNodeTest {
        @Mock
        private Candle candle;

        /**
         * Tests {@link com.torchmind.candle.node.property.IntegerPropertyNode#unsignedValue()}.
         */
        @Test (expected = IllegalStateException.class)
        public void testFailureUnsigned () {
                IntegerPropertyNode node = new IntegerPropertyNode (this.candle, "testProperty1", -42);
                node.unsignedValue ();
        }

        /**
         * Tests {@link com.torchmind.candle.node.property.IntegerPropertyNode#unsignedValue()}.
         */
        @Test
        public void testSuccessfulUnsigned () {
                IntegerPropertyNode node = new IntegerPropertyNode (this.candle, "testProperty1", 42);

                Assert.assertEquals (42.0f, node.unsignedValue (), 0.01f);
        }
}
