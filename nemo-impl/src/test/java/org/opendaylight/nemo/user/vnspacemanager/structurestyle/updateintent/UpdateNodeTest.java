/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateNode;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.instancecheck.NodeInstanceCheck;
import org.opendaylight.nemo.user.vnspacemanager.syntaxcheck.NodeDefinitionCheck;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.NodeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.NodeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;

/**
 * Created by ldzd11 on 2015/11/9.
 */
public class UpdateNodeTest {

    private UpdateNode updateNode;

    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private UserId userId;
    private Node node;
    
    @org.junit.Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        userId = mock(UserId.class);
        node = mock(Node.class);
        
        updateNode = new UpdateNode(dataBroker, tenantManage);
        //updateFlow = mock(UpdateFlow.class);

    }

    @org.junit.Test
    public void testNodeHandling() throws Exception {
    	
    	String errorDefinition = new String("This type of Node has not been defined.");
    	
    	CheckedFuture connectiondefinitionFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
//       when(dataBroker.newReadOnlyTransaction().read(any(LogicalDatastoreType.class),
//       any(InstanceIdentifier.class))).thenReturn(userRolesFuture);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(connectiondefinitionFuture); 
         
    	//when(nodeCheck.CheckNodeDefinition(any(Node.class))).thenReturn(errorDefinition);
    	Assert.assertEquals(updateNode.NodeHandling(userId,node),errorDefinition);
    	
    	
    	
    	
    	//errorDefinition = null;
    	//String errorInstance = new String("Case 1");
    	//when(nodeInstanceCheck.checkNodeInstance(any(UserId.class),any(Node.class))).thenReturn(errorInstance);
    	//Assert.assertEquals(updateNode.NodeHandling(userId,node),errorInstance);
    	
    	//errorInstance = null;
    	//userId = null;
    	//node = null;
    	//WriteTransaction t = mock(WriteTransaction.class);
    	//when(dataBroker.newWriteOnlyTransaction()).thenReturn(t);
    	
       // Assert.assertNull(updateNode.NodeHandling(userId, node));

    }
}