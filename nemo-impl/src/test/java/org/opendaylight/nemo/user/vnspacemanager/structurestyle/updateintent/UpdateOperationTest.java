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
//import org.opendaylight.nemo.user.vnspacemanager.instancecheck.OperationInstanceCheck;
//import org.opendaylight.nemo.user.vnspacemanager.syntaxcheck.OperationDefinitionCheck;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * Created by Zhongwei Zhao on 2015/12/23.
 */
public class UpdateOperationTest {

    private UpdateOperation updateOperation;

    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private UserId userId;
    private User user;
    private Operation operation;
    private Objects objects;
    
    @org.junit.Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        userId = mock(UserId.class);
        user = mock(User.class);
        operation = mock(Operation.class);
        
        updateOperation = new UpdateOperation(dataBroker, tenantManage);
        objects = mock(Objects.class);

    }
    
    @org.junit.Test
    public void testOperationHandling() throws Exception {
    	
    	CheckedFuture operationdefinitionFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);

        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(operationdefinitionFuture); 
        //The action type has not been defined.
    	Assert.assertEquals(updateOperation.OperationHandling(userId,operation),"The action type has not been defined.");
    	
    	
    	when(operation.getAction()).thenReturn(null);
    	when(operation.getConditionSegment()).thenReturn(null);
    	
    	ObjectId objectId = mock(ObjectId.class);
    	when(operation.getTargetObject()).thenReturn(objectId);
    	//The target object is not exist.
    	Assert.assertEquals(updateOperation.OperationHandling(userId,operation),"The target null is not exist.");
    	
    	
    	when(operation.getTargetObject()).thenReturn(null);
    	//The target should not be empty.
    	Assert.assertEquals(updateOperation.OperationHandling(userId,operation),"The target should not be empty.");
    	
    	
    	when(operation.getTargetObject()).thenReturn(objectId);
    	when(tenantManage.getUser()).thenReturn(user);
    	when(user.getObjects()).thenReturn(objects);
    	
    	Node node = mock(Node.class);
    	List<Node> nodeList = new ArrayList<Node>(1);
    	nodeList.add(node);
    	when(objects.getNode()).thenReturn(nodeList);
    	
    	NodeId nodeId = mock(NodeId.class);
    	when(node.getNodeId()).thenReturn(nodeId);
    	when(nodeId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
    	when(objectId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
    	
    	WriteTransaction writeTransaction = mock(WriteTransaction.class);
    	when(dataBroker.newWriteOnlyTransaction()).thenReturn(writeTransaction);

    	OperationId operationId = mock(OperationId.class);
    	when(operation.getOperationId()).thenReturn(operationId);
    	CheckedFuture<Void, TransactionCommitFailedException> f;
        f=mock(CheckedFuture.class);
        when(writeTransaction.submit()).thenReturn(f);
    	//null
    	Assert.assertEquals(updateOperation.OperationHandling(userId,operation),"The target 00001111-0000-0000-0000-000011112222 is not exist.");
    	
    }
}