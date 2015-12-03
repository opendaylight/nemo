/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.syntaxcheck;

import org.junit.runner.RunWith;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.spy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.PropertyName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.NodeDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.definitions.NodeDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.definitions.PropertyDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.definitions.PropertyDefinition;

import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.opendaylight.yangtools.yang.common.RpcError.ErrorType;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import com.google.common.base.Optional;
import com.google.common.base.Function;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.ListIterator;

import java.lang.reflect.Method; 
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class) 
@PrepareForTest(NodeDefinitionCheck.class) 
public class NodeDefinitionCheckTest{
	private NodeDefinitionCheck nodeDefinitionCheck;
	private DataBroker dataBroker;
    private List<NodeDefinition> nodeDefinitionList;
	private Node node;
	private NodeDefinition nodeDefinition;
	private List<Property> nodeProperties;
	private List<PropertyDefinition> nodePropertyDefinitions;
	private Property property;
	private PropertyDefinition propertyDefinition;
    //private static final Logger LOG = LoggerFactory.getLogger(NodeDefinitionCheck.class);
	 @org.junit.Before
    public void setUp() throws Exception {
		nodeDefinition = mock(NodeDefinition.class);
		nodeDefinitionList = new ArrayList<NodeDefinition>(3);
        // LOG = mock(Logger.class);
	    node = mock(Node.class);
        dataBroker = mock(DataBroker.class);
        nodeDefinitionCheck = new NodeDefinitionCheck(dataBroker);
		property = mock(Property.class);
		propertyDefinition = mock(PropertyDefinition.class);
		nodeProperties = new ArrayList<Property>(3);
		nodePropertyDefinitions = new ArrayList<PropertyDefinition>(3);
    }
	 @org.junit.Test
    public void testcheckConnectionDefinition() throws Exception {
        CheckedFuture nodedefinitionFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(nodedefinitionFuture);
        //branch nodeDefinitionList null
		Assert.assertEquals(nodeDefinitionCheck.CheckNodeDefinition(node),"This type of Node has not been defined.");
		
		//use reflact test private checkProperty
		Method testcheckProperty = nodeDefinitionCheck.getClass().getDeclaredMethod("checkProperty",List.class,List.class);  
		testcheckProperty.setAccessible(true);
		//branch1 nodeProperties null, nodePropertyDefinition null
		Assert.assertNull(testcheckProperty.invoke(nodeDefinitionCheck,nodeProperties,nodePropertyDefinitions));
		//branch2 
		nodeProperties.add(property);
		nodePropertyDefinitions.add(propertyDefinition);
		PropertyName propertyName1 = mock(PropertyName.class);
		PropertyName propertyName2 = mock(PropertyName.class);
		when(property.getPropertyName()).thenReturn(propertyName1);
		when(propertyDefinition.getPropertyName()).thenReturn(propertyName2);
		Assert.assertEquals(testcheckProperty.invoke(nodeDefinitionCheck,nodeProperties,nodePropertyDefinitions),"The property"+property.getPropertyName().toString()+"has not been defined.");
		//branch3
		when(property.getPropertyName()).thenReturn(propertyName1);
		PropertyName propertyName3 = propertyName1;
		when(propertyDefinition.getPropertyName()).thenReturn(propertyName3);
		PropertyValues propertyValues = mock(PropertyValues.class);
		when(property.getPropertyValues()).thenReturn(propertyValues);
		Assert.assertNotNull(property.getPropertyValues());
		Assert.assertNull(propertyDefinition.getPropertyValueType());
		//dataBroker test
        verify(dataBroker).newReadOnlyTransaction();
        verify(readOnlyTransaction).read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class));
		verifyPrivate(nodeDefinitionCheck).invoke("fetchNodeDefinitions");
    }
}