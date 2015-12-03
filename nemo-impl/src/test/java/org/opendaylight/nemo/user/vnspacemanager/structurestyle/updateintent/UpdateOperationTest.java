/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateOperation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.instancecheck.OperationInstanceCheck;
import org.opendaylight.nemo.user.vnspacemanager.syntaxcheck.OperationDefinitionCheck;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalHostId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalNodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Operations;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.OperationBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.OperationKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.hosts.PhysicalHost;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResource;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.MatchItem;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.SubNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;

import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;


/**
 * Created by ldzd11 on 2015/11/9.
 */
public class UpdateOperationTest {

    private UpdateOperation updateOperation;

    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private UserId userId;
    private Operation operation;
    
    @org.junit.Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        userId = mock(UserId.class);
        operation = mock(Operation.class);
        
        updateOperation = new UpdateOperation(dataBroker, tenantManage);
        //updateFlow = mock(UpdateFlow.class);

    }
    
    @org.junit.Test
    public void testOperationHandling() throws Exception {
    	
    	//String errorDefinition = new String("Case 0");
    	
    	CheckedFuture operationdefinitionFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
//       when(dataBroker.newReadOnlyTransaction().read(any(LogicalDatastoreType.class),
//       any(InstanceIdentifier.class))).thenReturn(userRolesFuture);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(operationdefinitionFuture); 
         
    	//when(operationDefinitioCheck.CheckDefinition(any(Operation.class))).thenReturn(errorDefinition);
    	Assert.assertEquals(updateOperation.OperationHandling(userId,operation),"There are no actions has been defined in the data store.");
    	
    	
    	when(operation.getAction()).thenReturn(null);
    	when(operation.getConditionSegment()).thenReturn(null);//errorDefinition==null
    	
    	ObjectId objectId = mock(ObjectId.class);
    	when(operation.getTargetObject()).thenReturn(objectId);
    	//Assert.assertNull(updateOperation.OperationHandling(userId, operation));
    	
    	Assert.assertEquals(updateOperation.OperationHandling(userId,operation),"There are no objects in data store.");
    	
    	when(operation.getTargetObject()).thenReturn(null);
    	
    	WriteTransaction writeTransaction = mock(WriteTransaction.class);
    	when(dataBroker.newWriteOnlyTransaction()).thenReturn(writeTransaction);
    	Assert.assertNull(updateOperation.OperationHandling(userId, operation));
    	//errorDefinition = null;
    	//String errorInstance = new String("Case 1");
    	//when(operationInstanceCheck.checkOperationInstance(any(UserId.class),any(Operation.class))).thenReturn(errorInstance);
    	//Assert.assertEquals(updateOperation.OperationHandling(userId,operation),errorInstance);
    	
    	//errorInstance = null;
    	//userId = null;
    	//operation = null;
    	//WriteTransaction t = mock(WriteTransaction.class);
    	//when(dataBroker.newWriteOnlyTransaction()).thenReturn(t);
    	
        //Assert.assertNull(updateOperation.OperationHandling(userId, operation));

    }
}