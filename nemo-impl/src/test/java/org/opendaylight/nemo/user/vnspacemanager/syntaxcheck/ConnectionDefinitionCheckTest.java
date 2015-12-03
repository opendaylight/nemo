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
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.PropertyName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.ConnectionDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.definitions.ConnectionDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.definitions.PropertyDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.definitions.PropertyDefinition.PropertyValueType;
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

import java.util.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import java.lang.reflect.Method; 
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ConnectionDefinitionCheck.class)
public class ConnectionDefinitionCheckTest {
    private ConnectionDefinitionCheck connectionDefinitionCheck;
    private DataBroker dataBroker;
	private ConnectionDefinition connectionDefinition;
    private List<ConnectionDefinition> connectionDefinitionList;
    private Connection connection;
	private List<Property> connectionProperty;
	private List<PropertyDefinition> propertyDefinitionList;
	private Property property;
	private PropertyDefinition propertyDefinition;
		
    // private static final Logger LOG;
    @org.junit.Before
    public void setUp() throws Exception {
		ConnectionDefinition connectionDefinition = mock(ConnectionDefinition.class);
		connectionDefinitionList=new ArrayList<ConnectionDefinition>(3);
		property = mock(Property.class);
		propertyDefinition = mock(PropertyDefinition.class);
		connectionProperty = new ArrayList<Property>(3);
		propertyDefinitionList = new ArrayList<PropertyDefinition>(3);
		connectionDefinitionList.add(connectionDefinition);
        connection = mock(Connection.class);
        // LOG = mock(Logger.class);
		dataBroker = mock(DataBroker.class);
        connectionDefinitionCheck =new ConnectionDefinitionCheck(dataBroker);
    }

    @org.junit.Test
    public void testcheckConnectionDefinition() throws Exception {
        CheckedFuture connectiondefinitionFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(connectiondefinitionFuture);
		
		//branch  connectionDefinitionList null
		Assert.assertEquals(connectionDefinitionCheck.CheckConnectionDefinition(connection),"This type of connection has not been defined.");
		
		//use reflact to test private CheckProperty
		Method testCheckProperty = connectionDefinitionCheck.getClass().getDeclaredMethod("CheckProperty",List.class,List.class);  
		testCheckProperty.setAccessible(true);
		//branch1 connectionProperty null,propertyDefinitionList null
		Assert.assertNull(testCheckProperty.invoke(connectionDefinitionCheck,connectionProperty,propertyDefinitionList));
		//branch2 property.getPropertyName() not equals(propertyDefinition.getPropertyName())
		connectionProperty.add(property);
		propertyDefinitionList.add(propertyDefinition);	
		PropertyName propertyName1 = mock(PropertyName.class);
		PropertyName propertyName2 = mock(PropertyName.class);
		when(property.getPropertyName()).thenReturn(propertyName1);
		when(propertyDefinition.getPropertyName()).thenReturn(propertyName2);
		Assert.assertEquals(testCheckProperty.invoke(connectionDefinitionCheck,connectionProperty,propertyDefinitionList),"This type of property" + property.getPropertyName().toString() + " has not been defined.");
		//branch3  property.getPropertyName()equals(propertyDefinition.getPropertyName()),but can not mock PropertyValueType
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
		verifyPrivate(connectionDefinitionCheck).invoke("fetchConnectionDefinitionList");
		
    }
}