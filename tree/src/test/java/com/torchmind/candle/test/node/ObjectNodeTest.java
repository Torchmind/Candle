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
import com.torchmind.candle.api.ITreeVisitor;
import com.torchmind.candle.api.IVisitor;
import com.torchmind.candle.api.property.IPropertyNode;
import com.torchmind.candle.api.property.array.IArrayPropertyNode;
import com.torchmind.candle.api.property.array.IBooleanArrayPropertyNode;
import com.torchmind.candle.node.CommentNode;
import com.torchmind.candle.node.ObjectNode;
import com.torchmind.candle.node.property.*;
import com.torchmind.candle.node.property.array.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Iterator;

/**
 * Provides test cases for {@link com.torchmind.candle.node.ObjectNode}.
 *
 * @author Johannes Donath
 */
@RunWith (MockitoJUnitRunner.class)
public class ObjectNodeTest {
        @Mock
        private IVisitor visitor;
        @Mock
        private ITreeVisitor treeVisitor;

        /**
         * Tests {@link com.torchmind.candle.node.ObjectNode#get(String)} and associated type specific methods.
         */
        @Test
        public void testGet () {
                Candle candle = new Candle ();

                boolean[] testValue1 = new boolean[] { true, false, true, false };
                String[] testValue2 = new String[] { "VALUE1", "VALUE2", "VALUE3", "VALUE4" };
                TestEnum[] testValue2Enum = new TestEnum[] { TestEnum.VALUE1, TestEnum.VALUE2, TestEnum.VALUE3, TestEnum.VALUE4 };
                float[] testValue3 = new float[] { 0.01f, 0.02f, 0.03f, 0.04f };
                int[] testValue4 = new int[] { 1, 2, 3, 4 };
                String[] testValue6 = new String[] { "Test 1", "Test 2", "Test 3", "Test 4" };
                boolean testValue7 = true;
                String testValue9 = "VALUE1";
                TestEnum testValue9Enum = TestEnum.VALUE1;
                float testValue10 = 21.5f;
                int testValue11 = 42;
                String testValue13 = "Test 1";

                BooleanArrayPropertyNode node1 = new BooleanArrayPropertyNode (candle, "property1", testValue1);
                EnumArrayPropertyNode node2 = new EnumArrayPropertyNode (candle, "property2", testValue2Enum);
                FloatArrayPropertyNode node3 = new FloatArrayPropertyNode (candle, "property3", testValue3);
                IntegerArrayPropertyNode node4 = new IntegerArrayPropertyNode (candle, "property4", testValue4);
                NullArrayPropertyNode node5 = new NullArrayPropertyNode (candle, "property5");
                StringArrayPropertyNode node6 = new StringArrayPropertyNode (candle, "property6", testValue6);
                BooleanPropertyNode node7 = new BooleanPropertyNode (candle, "property7", testValue7);
                DefaultPropertyNode node8 = new DefaultPropertyNode (candle, "property8");
                EnumPropertyNode node9 = new EnumPropertyNode (candle, "property9", testValue9Enum);
                FloatPropertyNode node10 = new FloatPropertyNode (candle, "property10", testValue10);
                IntegerPropertyNode node11 = new IntegerPropertyNode (candle, "property11", testValue11);
                NullPropertyNode node12 = new NullPropertyNode (candle, "property12");
                StringPropertyNode node13 = new StringPropertyNode (candle, "property13", testValue13);

                candle.append (node1);
                candle.append (node2);
                candle.append (node3);
                candle.append (node4);
                candle.append (node5);
                candle.append (node6);
                candle.append (node7);
                candle.append (node8);
                candle.append (node9);
                candle.append (node10);
                candle.append (node11);
                candle.append (node12);
                candle.append (node13);

                Assert.assertEquals (node1, candle.get ("property1"));
                Assert.assertEquals (node1, candle.get ("property1", BooleanArrayPropertyNode.class));
                Assert.assertArrayEquals (testValue1, candle.getBooleanArray ("property1"));

                Assert.assertEquals (node2, candle.get ("property2"));
                Assert.assertEquals (node2, candle.get ("property2", EnumArrayPropertyNode.class));
                Assert.assertArrayEquals (testValue2, candle.getEnumArray ("property2"));
                Assert.assertArrayEquals (testValue2Enum, candle.getEnumArray ("property2", TestEnum.class));

                Assert.assertEquals (node3, candle.get ("property3"));
                Assert.assertEquals (node3, candle.get ("property3", FloatArrayPropertyNode.class));
                Assert.assertArrayEquals (testValue3, candle.getFloatArray ("property3"), 0.001f);
                Assert.assertArrayEquals (testValue3, candle.getUnsignedFloatArray ("property3"), 0.001f);

                Assert.assertEquals (node4, candle.get ("property4"));
                Assert.assertEquals (node4, candle.get ("property4", IntegerArrayPropertyNode.class));
                Assert.assertArrayEquals (testValue4, candle.getIntegerArray ("property4"));
                Assert.assertArrayEquals (testValue4, candle.getUnsignedIntegerArray ("property4"));

                Assert.assertEquals (node5, candle.get ("property5"));
                Assert.assertEquals (node5, candle.get ("property5", NullArrayPropertyNode.class));

                Assert.assertEquals (node6, candle.get ("property6"));
                Assert.assertEquals (node6, candle.get ("property6", StringArrayPropertyNode.class));
                Assert.assertArrayEquals (testValue6, candle.getStringArray ("property6"));

                Assert.assertEquals (node7, candle.get ("property7"));
                Assert.assertEquals (node7, candle.get ("property7", BooleanPropertyNode.class));
                Assert.assertEquals (testValue7, candle.getBoolean ("property7"));

                Assert.assertEquals (node8, candle.get ("property8"));
                Assert.assertEquals (node8, candle.get ("property8", DefaultPropertyNode.class));

                Assert.assertEquals (node9, candle.get ("property9"));
                Assert.assertEquals (node9, candle.get ("property9", EnumPropertyNode.class));
                Assert.assertEquals (testValue9, candle.getEnum ("property9"));
                Assert.assertEquals (testValue9Enum, candle.getEnum ("property9", TestEnum.class));

                Assert.assertEquals (node10, candle.get ("property10"));
                Assert.assertEquals (node10, candle.get ("property10", FloatPropertyNode.class));
                Assert.assertEquals (testValue10, candle.getFloat ("property10"), 0.001f);
                Assert.assertEquals (testValue10, candle.getUnsignedFloat ("property10"), 0.001f);

                Assert.assertEquals (node11, candle.get ("property11"));
                Assert.assertEquals (node11, candle.get ("property11", IntegerPropertyNode.class));
                Assert.assertEquals (testValue11, candle.getInteger ("property11"));
                Assert.assertEquals (testValue11, candle.getUnsignedInteger ("property11"));

                Assert.assertEquals (node12, candle.get ("property12"));
                Assert.assertEquals (node12, candle.get ("property12", NullPropertyNode.class));

                Assert.assertEquals (node13, candle.get ("property13"));
                Assert.assertEquals (node13, candle.get ("property13", StringPropertyNode.class));
                Assert.assertEquals (testValue13, candle.getString ("property13"));
        }

        /**
         * Tests {@link com.torchmind.candle.node.ObjectNode#append(com.torchmind.candle.api.INode)}, {@link com.torchmind.candle.node.ObjectNode#insertBefore(com.torchmind.candle.api.INode, com.torchmind.candle.api.INode)} and {@link com.torchmind.candle.node.ObjectNode#insertAfter(com.torchmind.candle.api.INode, com.torchmind.candle.api.INode)}.
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

        /**
         * Tests {@link com.torchmind.candle.node.ObjectNode#insertBefore(com.torchmind.candle.api.INode, com.torchmind.candle.api.INode)} and {@link com.torchmind.candle.node.ObjectNode#insertAfter(com.torchmind.candle.api.INode, com.torchmind.candle.api.INode)}.
         */
        @Test
        public void testInsertNamed () {
                Candle candle = new Candle ();

                IntegerPropertyNode node1 = new IntegerPropertyNode (candle, "property1", 42);
                IntegerPropertyNode node2 = new IntegerPropertyNode (candle, "property2", 42);
                IntegerPropertyNode node3 = new IntegerPropertyNode (candle, "property3", 42);

                candle.append (node1);

                candle.insertBefore ("property1", node2);
                Iterator<INode> it = candle.iterator ();
                Assert.assertEquals (node2, it.next ());
                Assert.assertEquals (node1, it.next ());

                candle.insertAfter ("property2", node3);
                it = candle.iterator ();
                Assert.assertEquals (node2, it.next ());
                Assert.assertEquals (node3, it.next ());
                Assert.assertEquals (node1, it.next ());
        }

        /**
         * Tests {@link com.torchmind.candle.node.ObjectNode#insertBefore(com.torchmind.candle.api.INode, com.torchmind.candle.api.INode)} and {@link com.torchmind.candle.node.ObjectNode#insertAfter(com.torchmind.candle.api.INode, com.torchmind.candle.api.INode)}.
         */
        @Test
        public void testInsertPath () {
                Candle candle = new Candle ();

                ObjectNode parentNode1 = new ObjectNode (candle, "object1");

                IntegerPropertyNode node1 = new IntegerPropertyNode (candle, "property1", 42);
                IntegerPropertyNode node2 = new IntegerPropertyNode (candle, "property2", 42);
                IntegerPropertyNode node3 = new IntegerPropertyNode (candle, "property3", 42);

                parentNode1.append (node1);
                candle.append (parentNode1);

                candle.insertBefore ("object1.property1", node2);
                Iterator<INode> it = parentNode1.iterator ();
                Assert.assertEquals (node2, it.next ());
                Assert.assertEquals (node1, it.next ());

                candle.insertAfter ("object1.property2", node3);
                it = parentNode1.iterator ();
                Assert.assertEquals (node2, it.next ());
                Assert.assertEquals (node3, it.next ());
                Assert.assertEquals (node1, it.next ());
        }

        /**
         * Tests {@link com.torchmind.candle.node.ObjectNode#accept(com.torchmind.candle.api.IVisitor)}.
         */
        @Test
        public void testVisitor () {
                Candle candle = new Candle ();
                ObjectNode node = new ObjectNode (candle, "object1");

                BooleanArrayPropertyNode propertyNode1 = new BooleanArrayPropertyNode (candle, "property1", new boolean[] { true, false, true, false });
                EnumArrayPropertyNode propertyNode2 = new EnumArrayPropertyNode (candle, "property2", new String[] { "TEST1", "TEST2", "TEST3", "TEST4" });
                FloatArrayPropertyNode propertyNode3 = new FloatArrayPropertyNode (candle, "property3", new float[] { 1.1f, 1.2f, 1.3f, 1.4f });
                IntegerArrayPropertyNode propertyNode4 = new IntegerArrayPropertyNode (candle, "property4", new int[] { 1, 2, 3, 4 });
                NullArrayPropertyNode propertyNode5 = new NullArrayPropertyNode (candle, "property5");
                StringArrayPropertyNode propertyNode6 = new StringArrayPropertyNode (candle, "property6", new String[] { "Test 1", "Test 2", "Test 3", "Test 4" });
                BooleanPropertyNode propertyNode7 = new BooleanPropertyNode (candle, "property7", true);
                DefaultPropertyNode propertyNode8 = new DefaultPropertyNode (candle, "property8");
                EnumPropertyNode propertyNode9 = new EnumPropertyNode (candle, "property9", "TEST1");
                FloatPropertyNode propertyNode10 = new FloatPropertyNode (candle, "property10", 1.23f);
                IntegerPropertyNode propertyNode11 = new IntegerPropertyNode (candle, "property11", 42);
                NullPropertyNode propertyNode12 = new NullPropertyNode (candle, "property12");
                StringPropertyNode propertyNode13 = new StringPropertyNode (candle, "property13", "Test 1");

                node.append (propertyNode1);
                node.append (propertyNode2);
                node.append (propertyNode3);
                node.append (propertyNode4);
                node.append (propertyNode5);
                node.append (propertyNode6);
                node.append (propertyNode7);
                node.append (propertyNode8);
                node.append (propertyNode9);
                node.append (propertyNode10);
                node.append (propertyNode11);
                node.append (propertyNode12);
                node.append (propertyNode13);

                node.accept (this.visitor);

                // @formatter:off
                {
                        Mockito.verify (this.visitor, Mockito.times (6))
                                .visitArray ();
                        Mockito.verify (this.visitor, Mockito.times (6))
                                .visitArrayEnd ();
                }

                {
                        Mockito.verify (this.visitor, Mockito.times (3))
                                .visitBoolean (true);
                        Mockito.verify (this.visitor, Mockito.times (2))
                                .visitBoolean (false);
                }

                {
                        Mockito.verify (this.visitor, Mockito.times (2))
                                .visitEnum ("TEST1");
                        Mockito.verify (this.visitor)
                                .visitEnum ("TEST2");
                        Mockito.verify (this.visitor)
                                .visitEnum ("TEST3");
                        Mockito.verify (this.visitor)
                                .visitEnum ("TEST4");
                }

                {
                        Mockito.verify (this.visitor)
                                .visitProperty ("property1");
                        Mockito.verify (this.visitor)
                                .visitProperty ("property2");
                        Mockito.verify (this.visitor)
                                .visitProperty ("property3");
                        Mockito.verify (this.visitor)
                                .visitProperty ("property4");
                        Mockito.verify (this.visitor)
                                .visitProperty ("property5");
                        Mockito.verify (this.visitor)
                                .visitProperty ("property6");
                        Mockito.verify (this.visitor)
                                .visitProperty ("property7");
                        Mockito.verify (this.visitor)
                                .visitProperty ("property8");
                        Mockito.verify (this.visitor)
                                .visitProperty ("property9");
                        Mockito.verify (this.visitor)
                                .visitProperty ("property10");
                        Mockito.verify (this.visitor)
                                .visitProperty ("property11");
                        Mockito.verify (this.visitor)
                                .visitProperty ("property12");
                        Mockito.verify (this.visitor)
                                .visitProperty ("property13");
                }

                {
                        Mockito.verify (this.visitor)
                                .visitFloat (1.1f);
                        Mockito.verify (this.visitor)
                                .visitFloat (1.2f);
                        Mockito.verify (this.visitor)
                                .visitFloat (1.3f);
                        Mockito.verify (this.visitor)
                                .visitFloat (1.4f);
                        Mockito.verify (this.visitor)
                                .visitFloat (1.23f);
                }

                {
                        Mockito.verify (this.visitor)
                                .visitInteger (1);
                        Mockito.verify (this.visitor)
                                .visitInteger (2);
                        Mockito.verify (this.visitor)
                                .visitInteger (3);
                        Mockito.verify (this.visitor)
                                .visitInteger (4);
                }

                {
                        Mockito.verify (this.visitor, Mockito.times (2))
                                .visitString ("Test 1");
                        Mockito.verify (this.visitor)
                                .visitString ("Test 2");
                        Mockito.verify (this.visitor)
                                .visitString ("Test 3");
                        Mockito.verify (this.visitor)
                                .visitString ("Test 4");
                }

                {
                        Mockito.verify (this.visitor)
                                .visitDefault ();
                }

                {
                        Mockito.verify (this.visitor)
                                .visitInteger (42);
                }

                {
                        Mockito.verify (this.visitor)
                                .visitNull ();
                }
                // @formatter:on
        }

        /**
         * Tests {@link com.torchmind.candle.node.ObjectNode#accept(com.torchmind.candle.api.ITreeVisitor)}.
         */
        @Test
        public void testTreeVisitor () {
                Candle candle = new Candle ();
                ObjectNode node = new ObjectNode (candle, "object1");

                BooleanArrayPropertyNode propertyNode1 = new BooleanArrayPropertyNode (candle, "property1", new boolean[] { true, false, true, false });
                EnumArrayPropertyNode propertyNode2 = new EnumArrayPropertyNode (candle, "property2", new String[] { "TEST1", "TEST2", "TEST3", "TEST4" });
                FloatArrayPropertyNode propertyNode3 = new FloatArrayPropertyNode (candle, "property3", new float[] { 1.1f, 1.2f, 1.3f, 1.4f });
                IntegerArrayPropertyNode propertyNode4 = new IntegerArrayPropertyNode (candle, "property4", new int[] { 1, 2, 3, 4 });
                NullArrayPropertyNode propertyNode5 = new NullArrayPropertyNode (candle, "property5");
                StringArrayPropertyNode propertyNode6 = new StringArrayPropertyNode (candle, "property6", new String[] { "Test 1", "Test 2", "Test 3", "Test 4" });
                BooleanPropertyNode propertyNode7 = new BooleanPropertyNode (candle, "property7", true);
                DefaultPropertyNode propertyNode8 = new DefaultPropertyNode (candle, "property8");
                EnumPropertyNode propertyNode9 = new EnumPropertyNode (candle, "property9", "TEST1");
                FloatPropertyNode propertyNode10 = new FloatPropertyNode (candle, "property10", 1.23f);
                IntegerPropertyNode propertyNode11 = new IntegerPropertyNode (candle, "property11", 42);
                NullPropertyNode propertyNode12 = new NullPropertyNode (candle, "property12");
                StringPropertyNode propertyNode13 = new StringPropertyNode (candle, "property13", "Test 1");

                node.append (propertyNode1);
                node.append (propertyNode2);
                node.append (propertyNode3);
                node.append (propertyNode4);
                node.append (propertyNode5);
                node.append (propertyNode6);
                node.append (propertyNode7);
                node.append (propertyNode8);
                node.append (propertyNode9);
                node.append (propertyNode10);
                node.append (propertyNode11);
                node.append (propertyNode12);
                node.append (propertyNode13);

                node.accept (this.treeVisitor);

                // @formatter:off
                {
                        Mockito.verify (this.treeVisitor)
                                .visitArrayPropertyNode (candle, ((IArrayPropertyNode) propertyNode1));
                        Mockito.verify (this.treeVisitor)
                                .visitArrayPropertyNode (candle, propertyNode1);
                        Mockito.verify (this.treeVisitor)
                                .visitArrayPropertyNodeEnd (candle, propertyNode1);
                }

                {
                        Mockito.verify (this.treeVisitor)
                                .visitArrayPropertyNode (candle, ((IArrayPropertyNode) propertyNode2));
                        Mockito.verify (this.treeVisitor)
                                .visitArrayPropertyNode (candle, propertyNode2);
                        Mockito.verify (this.treeVisitor)
                                .visitArrayPropertyNodeEnd (candle, propertyNode2);
                }

                {
                        Mockito.verify (this.treeVisitor)
                                .visitArrayPropertyNode (candle, ((IArrayPropertyNode) propertyNode3));
                        Mockito.verify (this.treeVisitor)
                                .visitArrayPropertyNode (candle, propertyNode3);
                        Mockito.verify (this.treeVisitor)
                                .visitArrayPropertyNodeEnd (candle, propertyNode3);
                }

                {
                        Mockito.verify (this.treeVisitor)
                                .visitArrayPropertyNode (candle, ((IArrayPropertyNode) propertyNode4));
                        Mockito.verify (this.treeVisitor)
                                .visitArrayPropertyNode (candle, propertyNode4);
                        Mockito.verify (this.treeVisitor)
                                .visitArrayPropertyNodeEnd (candle, propertyNode4);
                }

                {
                        Mockito.verify (this.treeVisitor)
                                .visitArrayPropertyNode (candle, ((IArrayPropertyNode) propertyNode5));
                        Mockito.verify (this.treeVisitor)
                                .visitArrayPropertyNode (candle, propertyNode5);
                        Mockito.verify (this.treeVisitor)
                                .visitArrayPropertyNodeEnd (candle, propertyNode5);
                }

                {
                        Mockito.verify (this.treeVisitor)
                                .visitArrayPropertyNode (candle, ((IArrayPropertyNode) propertyNode6));
                        Mockito.verify (this.treeVisitor)
                                .visitArrayPropertyNode (candle, propertyNode6);
                        Mockito.verify (this.treeVisitor)
                                .visitArrayPropertyNodeEnd (candle, propertyNode6);
                }

                // Non-Array properties
                {
                        Mockito.verify (this.treeVisitor)
                                .visitPropertyNode (candle, ((IPropertyNode) propertyNode7));
                        Mockito.verify (this.treeVisitor)
                                .visitPropertyNode (candle, propertyNode7);
                        Mockito.verify (this.treeVisitor)
                                .visitPropertyNodeEnd (candle, propertyNode7);
                }

                {
                        Mockito.verify (this.treeVisitor)
                                .visitPropertyNode (candle, ((IPropertyNode) propertyNode8));
                        Mockito.verify (this.treeVisitor)
                                .visitPropertyNode (candle, propertyNode8);
                        Mockito.verify (this.treeVisitor)
                                .visitPropertyNodeEnd (candle, propertyNode8);
                }

                {
                        Mockito.verify (this.treeVisitor)
                                .visitPropertyNode (candle, ((IPropertyNode) propertyNode9));
                        Mockito.verify (this.treeVisitor)
                                .visitPropertyNode (candle, propertyNode9);
                        Mockito.verify (this.treeVisitor)
                                .visitPropertyNodeEnd (candle, propertyNode9);
                }

                {
                        Mockito.verify (this.treeVisitor)
                                .visitPropertyNode (candle, ((IPropertyNode) propertyNode10));
                        Mockito.verify (this.treeVisitor)
                                .visitPropertyNode (candle, propertyNode10);
                        Mockito.verify (this.treeVisitor)
                                .visitPropertyNodeEnd (candle, propertyNode10);
                }

                {
                        Mockito.verify (this.treeVisitor)
                                .visitPropertyNode (candle, ((IPropertyNode) propertyNode11));
                        Mockito.verify (this.treeVisitor)
                                .visitPropertyNode (candle, propertyNode11);
                        Mockito.verify (this.treeVisitor)
                                .visitPropertyNodeEnd (candle, propertyNode11);
                }

                {
                        Mockito.verify (this.treeVisitor)
                                .visitPropertyNode (candle, ((IPropertyNode) propertyNode12));
                        Mockito.verify (this.treeVisitor)
                                .visitPropertyNode (candle, propertyNode12);
                        Mockito.verify (this.treeVisitor)
                                .visitPropertyNodeEnd (candle, propertyNode12);
                }

                {
                        Mockito.verify (this.treeVisitor)
                                .visitPropertyNode (candle, ((IPropertyNode) propertyNode13));
                        Mockito.verify (this.treeVisitor)
                                .visitPropertyNode (candle, propertyNode13);
                        Mockito.verify (this.treeVisitor)
                                .visitPropertyNodeEnd (candle, propertyNode13);
                }
                // @formatter:on
        }

        enum TestEnum {
                VALUE1,
                VALUE2,
                VALUE3,
                VALUE4
        }
}
