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
package com.torchmind.candle.node;

import com.torchmind.candle.api.*;
import com.torchmind.candle.api.property.*;
import com.torchmind.candle.api.property.array.*;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Johannes Donath
 */
public class ObjectNode extends AbstractNamedNode implements IObjectNode {
        private final LinkedList<INode> children = new LinkedList<> ();

        protected ObjectNode () {
                super ();
        }

        public ObjectNode (@Nonnull IDocumentNode documentNode, @Nonnull String name) {
                super (documentNode, name);
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public IObjectNode accept (@Nonnull ITreeVisitor visitor) {
                this.stream ().forEach (n -> {
                        if (n instanceof IObjectNode) {
                                visitor.visitObjectNode (this.document (), ((IObjectNode) n));
                                ((IObjectNode) n).accept (visitor);
                                visitor.visitObjectNodeEnd (this.document (), ((IObjectNode) n));

                                return;
                        }

                        if (n instanceof ICommentNode) {
                                visitor.visitCommentNode (this.document (), ((ICommentNode) n));
                                return;
                        }

                        if (n instanceof IArrayPropertyNode) {
                                visitor.visitArrayPropertyNode (this.document (), ((IArrayPropertyNode) n));

                                if (n instanceof IBooleanArrayPropertyNode) {
                                        visitor.visitArrayPropertyNode (this.document (), ((IBooleanArrayPropertyNode) n));
                                } else if (n instanceof IEnumArrayPropertyNode) {
                                        visitor.visitArrayPropertyNode (this.document (), ((IEnumArrayPropertyNode) n));
                                } else if (n instanceof IFloatArrayPropertyNode) {
                                        visitor.visitArrayPropertyNode (this.document (), ((IFloatArrayPropertyNode) n));
                                } else if (n instanceof IIntegerArrayPropertyNode) {
                                        visitor.visitArrayPropertyNode (this.document (), ((IIntegerArrayPropertyNode) n));
                                } else if (n instanceof INullArrayPropertyNode) {
                                        visitor.visitArrayPropertyNode (this.document (), ((INullArrayPropertyNode) n));
                                } else if (n instanceof IStringArrayPropertyNode) {
                                        visitor.visitArrayPropertyNode (this.document (), ((IStringArrayPropertyNode) n));
                                } else {
                                        throw new UnsupportedOperationException ("Cannot walk unknown node of type " + n.getClass ().getCanonicalName ());
                                }

                                visitor.visitArrayPropertyNodeEnd (this.document (), ((IArrayPropertyNode) n));
                                return;
                        }

                        if (n instanceof IPropertyNode) {
                                visitor.visitPropertyNode (this.document (), ((IPropertyNode) n));

                                if (n instanceof IBooleanPropertyNode) {
                                        visitor.visitPropertyNode (this.document (), ((IBooleanPropertyNode) n));
                                } else if (n instanceof IDefaultPropertyNode) {
                                        visitor.visitPropertyNode (this.document (), ((IDefaultPropertyNode) n));
                                } else if (n instanceof IEnumPropertyNode) {
                                        visitor.visitPropertyNode (this.document (), ((IEnumPropertyNode) n));
                                } else if (n instanceof IFloatPropertyNode) {
                                        visitor.visitPropertyNode (this.document (), ((IFloatPropertyNode) n));
                                } else if (n instanceof IIntegerPropertyNode) {
                                        visitor.visitPropertyNode (this.document (), ((IIntegerPropertyNode) n));
                                } else if (n instanceof INullPropertyNode) {
                                        visitor.visitPropertyNode (this.document (), ((INullPropertyNode) n));
                                } else if (n instanceof IStringPropertyNode) {
                                        visitor.visitPropertyNode (this.document (), ((IStringPropertyNode) n));
                                } else {
                                        throw new UnsupportedOperationException ("Cannot walk unknown node of type " + n.getClass ().getCanonicalName ());
                                }

                                visitor.visitPropertyNodeEnd (this.document (), ((IPropertyNode) n));
                                return;
                        }

                        throw new UnsupportedOperationException ("Cannot walk unknown node of type " + n.getClass ().getCanonicalName ());
                });

                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public IObjectNode accept (@Nonnull IVisitor visitor) {
                this.stream ().forEach (n -> {
                        if (n instanceof IObjectNode) {
                                visitor.visitObject (((IObjectNode) n).name ());
                                ((IObjectNode) n).accept (visitor);
                                visitor.visitObjectEnd ();

                                return;
                        }

                        n.accept (visitor);
                });

                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public IObjectNode append (@Nonnull INode node) {
                if (node instanceof INamedNode && this.isPresent (((INamedNode) node).name ())) {
                        this.replace (((INamedNode) node).name (), node);
                        return this;
                }

                this.children.add (node);
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public List<INode> children () {
                return Collections.unmodifiableList (this.children);
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public <T extends INode> List<T> children (@Nonnull Class<T> nodeType) {
                return this.stream (nodeType).collect (Collectors.toList ());
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public IObjectNode clear () {
                this.children.clear ();
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public INode get (@Nonnull String name) throws NoSuchElementException {
                final String closestNode = (name.indexOf ('.') == -1 ? name : name.substring (0, name.indexOf ('.')));

                // @formatter:off
                INode node = this.stream ()
                        .filter (n -> {
                                if (!(n instanceof INamedNode)) return false;
                                return closestNode.equalsIgnoreCase (((INamedNode) n).name ());
                        })
                                 .findFirst ()
                                        .orElseThrow (() -> new NoSuchElementException ("Could not locate element with name \"" + name + "\"" + (!closestNode.equals (name) ? " (failed to locate closest node \"" + closestNode + "\")" : "")));
                // @formatter:on
                if (name.indexOf ('.') == -1) { return node; }
                if (!(node instanceof IObjectNode)) {
                        throw new NoSuchElementException ("Node with name \"" + closestNode + "\" is not a container node");
                }
                return ((IObjectNode) node).get (name.substring ((closestNode.length () + 1)));
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        @SuppressWarnings ("unchecked")
        public <T extends INode> T get (@Nonnull String name, @Nonnull Class<T> nodeType) throws IllegalStateException, NoSuchElementException {
                INode node = this.get (name);
                if (!nodeType.isAssignableFrom (node.getClass ())) {
                        throw new IllegalStateException ("Expected node of type " + nodeType.getCanonicalName () + " but got " + node.getClass ().getName ());
                }
                return ((T) node);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean getBoolean (@Nonnull String name) throws IllegalStateException, NoSuchElementException {
                return this.getPropertyValue (name, IBooleanPropertyNode.class, IBooleanPropertyNode::value, () -> false);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean getBoolean (@Nonnull String name, boolean defaultValue) throws IllegalStateException {
                if (this.isDefault (name)) { return defaultValue; }
                return this.getBoolean (name);
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public boolean[] getBooleanArray (@Nonnull String name) throws IllegalStateException, NoSuchElementException {
                return this.getPropertyValue (name, IBooleanArrayPropertyNode.class, IBooleanArrayPropertyNode::array);
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public boolean[] getBooleanArray (@Nonnull String name, @Nullable boolean[] defaultValue) throws IllegalStateException, NoSuchElementException {
                if (this.isDefault (name)) { return defaultValue; }
                return this.getBooleanArray (name);
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public String getEnum (@Nonnull String name) throws IllegalStateException, NoSuchElementException {
                return this.getPropertyValue (name, IEnumPropertyNode.class, IEnumPropertyNode::value);
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public String getEnum (@Nonnull String name, @Nullable String defaultValue) throws IllegalStateException, NoSuchElementException {
                if (this.isDefault (name)) { return defaultValue; }
                return this.getEnum (name);
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public <T extends Enum> T getEnum (@Nonnull String name, @Nonnull Class<T> enumType) throws IllegalStateException, NoSuchElementException {
                return this.getPropertyValue (name, IEnumPropertyNode.class, (n) -> n.value (enumType));
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        @SuppressWarnings ("unchecked")
        // Indeed very unchecked. However Java does not allow us to extend Enums so this is perfectly fine ...
        public <T extends Enum> T getEnum (@Nonnull String name, @Nonnull T defaultValue) throws IllegalStateException, NoSuchElementException {
                if (this.isDefault (name)) { return defaultValue; }
                return this.getEnum (name, ((Class<T>) defaultValue.getClass ()));
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public <T extends Enum> T getEnum (@Nonnull String name, @Nullable T defaultValue, @Nonnull Class<T> enumType) throws IllegalStateException, NoSuchElementException {
                if (this.isDefault (name)) { return defaultValue; }
                return this.getEnum (name, enumType);
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public String[] getEnumArray (@Nonnull String name) throws IllegalStateException, NoSuchElementException {
                return this.getPropertyValue (name, IEnumArrayPropertyNode.class, IEnumArrayPropertyNode::array);
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public String[] getEnumArray (@Nonnull String name, @Nullable String[] defaultValue) throws IllegalStateException, NoSuchElementException {
                if (this.isDefault (name)) { return defaultValue; }
                return this.getEnumArray (name);
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public <T extends Enum> T[] getEnumArray (@Nonnull String name, @Nullable Class<T> enumType) throws IllegalStateException, NoSuchElementException {
                return this.getPropertyValue (name, IEnumArrayPropertyNode.class, (n) -> n.array (enumType));
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public <T extends Enum> T[] getEnumArray (@Nonnull String name, @Nullable T[] defaultValue, @Nonnull Class<T> enumType) throws IllegalStateException, NoSuchElementException {
                if (this.isDefault (name)) { return defaultValue; }
                return this.getEnumArray (name, enumType);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public float getFloat (@Nonnull String name) throws IllegalStateException, NoSuchElementException {
                return this.getPropertyValue (name, IFloatPropertyNode.class, IFloatPropertyNode::value, () -> 0.0f);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public float getFloat (@Nonnull String name, float defaultValue) throws IllegalStateException, NoSuchElementException {
                if (this.isDefault (name)) { return defaultValue; }
                return this.getFloat (name);
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public float[] getFloatArray (@Nonnull String name) throws IllegalStateException, NoSuchElementException {
                return this.getPropertyValue (name, IFloatArrayPropertyNode.class, IFloatArrayPropertyNode::array);
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public float[] getFloatArray (@Nonnegative String name, @Nullable float[] defaultValue) throws IllegalStateException, NoSuchElementException {
                if (this.isDefault (name)) { return defaultValue; }
                return this.getFloatArray (name);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getInteger (@Nonnull String name) throws IllegalStateException, NoSuchElementException {
                return this.getPropertyValue (name, IIntegerPropertyNode.class, IIntegerPropertyNode::value, () -> 0);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getInteger (@Nonnull String name, int defaultValue) throws IllegalStateException, NoSuchElementException {
                if (this.isDefault (name)) { return defaultValue; }
                return this.getInteger (name);
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public int[] getIntegerArray (@Nonnull String name) throws IllegalStateException, NoSuchElementException {
                return this.getPropertyValue (name, IIntegerArrayPropertyNode.class, IIntegerArrayPropertyNode::array);
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public int[] getIntegerArray (@Nonnull String name, @Nullable int[] defaultValue) throws IllegalStateException, NoSuchElementException {
                if (this.isDefault (name)) { return defaultValue; }
                return this.getIntegerArray (name);
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public IPropertyNode getProperty (@Nonnull String name) throws IllegalStateException, NoSuchElementException {
                return this.get (name, IPropertyNode.class);
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public <T extends IPropertyNode, R> R getPropertyValue (@Nonnull String name, @Nonnull Class<T> nodeType, @Nonnull Function<T, R> ifPresent) {
                return this.getPropertyValue (name, nodeType, ifPresent, () -> null);
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public <T extends IPropertyNode, R> R getPropertyValue (@Nonnull String name, @Nonnull Class<T> nodeType, @Nonnull Function<T, R> ifPresent, @Nonnull Supplier<R> ifNull) {
                if (this.isNull (name) || this.isDefault (name)) { return ifNull.get (); }
                return ifPresent.apply (this.get (name, nodeType));
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public String getString (@Nonnull String name) throws IllegalStateException, NoSuchElementException {
                return this.getPropertyValue (name, IStringPropertyNode.class, IStringPropertyNode::value);
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public String getString (@Nonnegative String name, @Nullable String defaultValue) throws IllegalStateException, NoSuchElementException {
                if (this.isDefault (name)) { return defaultValue; }
                return this.getString (name);
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public String[] getStringArray (@Nonnull String name) throws IllegalStateException, NoSuchElementException {
                return this.getPropertyValue (name, IStringArrayPropertyNode.class, IStringArrayPropertyNode::array);
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public String[] getStringArray (@Nonnull String name, @Nullable String[] defaultValue) throws IllegalStateException, NoSuchElementException {
                if (this.isDefault (name)) { return defaultValue; }
                return this.getStringArray (name);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public float getUnsignedFloat (@Nonnull String name) throws IllegalStateException, NoSuchElementException {
                return this.getPropertyValue (name, IFloatPropertyNode.class, IFloatPropertyNode::unsignedValue, () -> 0.0f);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public float getUnsignedFloat (@Nonnull String name, @Nonnegative float defaultValue) throws IllegalStateException, NoSuchElementException {
                if (this.isDefault (name)) { return defaultValue; }
                return this.getUnsignedFloat (name);
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public float[] getUnsignedFloatArray (@Nonnull String name) throws IllegalStateException, NoSuchElementException {
                return this.getPropertyValue (name, IFloatArrayPropertyNode.class, IFloatArrayPropertyNode::unsignedArray);
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public float[] getUnsignedFloatArray (@Nonnull String name, @Nullable @Nonnegative float[] defaultValue) throws IllegalStateException, NoSuchElementException {
                if (this.isDefault (name)) { return defaultValue; }
                return this.getUnsignedFloatArray (name);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getUnsignedInteger (@Nonnull String name) throws IllegalStateException, NoSuchElementException {
                return this.getPropertyValue (name, IIntegerPropertyNode.class, IIntegerPropertyNode::unsignedValue, () -> 0);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getUnsignedInteger (@Nonnegative String name, @Nonnegative int defaultValue) throws IllegalStateException, NoSuchElementException {
                if (this.isDefault (name)) { return defaultValue; }
                return this.getUnsignedInteger (name);
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public int[] getUnsignedIntegerArray (@Nonnull String name) throws IllegalStateException, NoSuchElementException {
                return this.getPropertyValue (name, IIntegerArrayPropertyNode.class, IIntegerArrayPropertyNode::unsignedArray);
        }

        /**
         * {@inheritDoc}
         */
        @Nullable
        @Override
        public int[] getUnsignedIntegerArray (@Nonnull String name, @Nullable @Nonnegative int[] defaultValue) throws IllegalStateException, NoSuchElementException {
                if (this.isDefault (name)) { return defaultValue; }
                return this.getUnsignedIntegerArray (name);
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public IObjectNode insertAfter (@Nonnull String after, @Nonnull INode node) throws NoSuchElementException {
                int index = after.indexOf ('.');
                if (index != -1) {
                        IObjectNode closestNode = this.get (after.substring (0, index), IObjectNode.class);
                        closestNode.insertAfter (after.substring ((index + 1)), node);
                        return this;
                }

                return this.insertAfter (this.get (after), node);
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public IObjectNode insertAfter (@Nonnull INode after, @Nonnull INode node) throws NoSuchElementException {
                int index = (this.children.indexOf (after) + 1);
                if (index == 0) {
                        throw new NoSuchElementException ("Cannot locate element to insert after within tree");
                }
                this.children.add (index, node);
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public IObjectNode insertBefore (@Nonnull String before, @Nonnull INode node) throws NoSuchElementException {
                int index = before.indexOf ('.');
                if (index != -1) {
                        IObjectNode closestNode = this.get (before.substring (0, index), IObjectNode.class);
                        closestNode.insertBefore (before.substring ((index + 1)), node);
                        return this;
                }

                return this.insertBefore (this.get (before), node);
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public IObjectNode insertBefore (@Nonnull INode before, @Nonnull INode node) throws NoSuchElementException {
                int index = this.children.indexOf (before);
                if (index == -1) {
                        throw new NoSuchElementException ("Cannot locate element to insert before within tree");
                }
                this.children.add (index, node);
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isDefault (@Nonnull String name) throws IllegalStateException, NoSuchElementException {
                INode node = this.get (name);
                node.ensureType (NodeType.PROPERTY);

                return ((IPropertyNode) node).valueType () == NodeValueType.DEFAULT;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isNull (@Nonnull String name) throws IllegalStateException, NoSuchElementException {
                INode node = this.get (name);
                node.ensureType (NodeType.PROPERTY);

                return ((IPropertyNode) node).valueType () == NodeValueType.NULL;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isPresent (@Nonnull String name) {
                try {
                        int index = name.indexOf ('.');
                        if (index != -1) {
                                IObjectNode closestNode = this.get (name.substring (0, index), IObjectNode.class);
                                return closestNode.isPresent (name.substring ((index + 1)));
                        }

                        // @formatter:off
                        return this.stream ()
                                .parallel ()
                                        .filter (n -> {
                                                if (!(n instanceof INamedNode)) return false;
                                                return name.equalsIgnoreCase (((INamedNode) n).name ());
                                        })
                                                .findAny ()
                                                        .isPresent ();
                        // @formatter:on
                } catch (IllegalStateException | NoSuchElementException ex) {
                        return false;
                }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isPresent (@Nonnull String name, @Nonnull Class<? extends INode> nodeType) {
                try {
                        int index = name.indexOf ('.');
                        if (index != -1) {
                                IObjectNode closestNode = this.get (name.substring (0, index), IObjectNode.class);
                                return closestNode.isPresent (name.substring ((index + 1)), nodeType);
                        }

                        // @formatter:off
                        return this.stream (nodeType)
                                   .parallel ()
                                           .filter (n -> {
                                                   if (!(n instanceof INamedNode)) return false;
                                                   return name.equalsIgnoreCase (((INamedNode) n).name ());
                                           })
                                                   .findAny ()
                                                        .isPresent ();
                        // @formatter:on
                } catch (IllegalStateException | NoSuchElementException ex) {
                        return false;
                }
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public <T extends INode> Iterator<T> iterator (@Nonnull Class<T> nodeType) {
                return this.stream (nodeType).iterator ();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<INode> iterator () {
                return this.children ().iterator ();
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public IObjectNode remove (@Nonnull String name) throws NoSuchElementException {
                int index = name.indexOf ('.');
                if (index != -1) {
                        IObjectNode node = this.get (name.substring (0, index), IObjectNode.class);
                        node.remove (name.substring ((index + 1)));
                        return this;
                }

                return this.remove (this.get (name));
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public IObjectNode remove (@Nonnull INode node) throws NoSuchElementException {
                if (!this.children.remove (node)) {
                        throw new NoSuchElementException ("Cannot locate element to remove within tree");
                }
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public IObjectNode replace (@Nonnull String name, @Nonnull INode replacement) throws NoSuchElementException {
                int index = name.indexOf ('.');
                if (index != -1) {
                        IObjectNode closestNode = this.get (name.substring (0, index), IObjectNode.class);
                        closestNode.replace (name.substring ((index + 1)), replacement);
                        return this;
                }

                return this.replace (this.get (name), replacement);
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public IObjectNode replace (@Nonnull INode node, @Nonnull INode replacement) throws NoSuchElementException {
                int index = this.children.indexOf (node);
                if (index == -1) { throw new NoSuchElementException ("Cannot locate element to replace within tree"); }

                this.children.remove (node);
                this.children.add (index, replacement);
                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size () {
                return this.children.size ();
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public Stream<INode> stream () {
                return this.children.stream ();
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        @SuppressWarnings ("unchecked")
        public <T extends INode> Stream<T> stream (@Nonnull Class<T> nodeType) {
                return ((Stream<T>) this.stream ().parallel ()
                                        .filter (n -> nodeType.isAssignableFrom (n.getClass ()))
                                        .sequential ());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString () {
                return String.format ("ObjectNode{%s,children=[%s]}", super.toString (), this.children ());
        }
}
