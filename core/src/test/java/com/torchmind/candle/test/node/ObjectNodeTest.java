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
import com.torchmind.candle.api.INode;
import com.torchmind.candle.node.CommentNode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Iterator;

/**
 * Provides test cases for {@link com.torchmind.candle.node.ObjectNode}.
 * @author Johannes Donath
 */
@RunWith (MockitoJUnitRunner.class)
public class ObjectNodeTest {

        /**
         * Tests {@link com.torchmind.candle.Candle#append(com.torchmind.candle.api.INode)}, {@link com.torchmind.candle.Candle#insertBefore(com.torchmind.candle.api.INode, com.torchmind.candle.api.INode)} and {@link com.torchmind.candle.Candle#insertAfter(com.torchmind.candle.api.INode, com.torchmind.candle.api.INode)}.
         */
        @Test
        public void testInsert () {
                Candle candle = new Candle ();

                CommentNode node1 = new CommentNode (candle, "Test 1");
                CommentNode node2 = new CommentNode (candle, "Test 2");
                CommentNode node3 = new CommentNode (candle, "Test 3");

                candle.append (node1);
                Assert.assertEquals (node1, candle.iterator ().next ());

                candle.insertBefore (node1, node2);
                Iterator<INode> it = candle.iterator ();
                Assert.assertEquals (node2, it.next ());
                Assert.assertEquals (node1, it.next ());

                candle.insertAfter (node2, node3);
                it = candle.iterator ();
                Assert.assertEquals (node2, it.next ());
                Assert.assertEquals (node3, it.next ());
                Assert.assertEquals (node1, it.next ());
        }
}
