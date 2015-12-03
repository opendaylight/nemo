/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateFlow;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.instancecheck.FlowInstanceCheck;
import org.opendaylight.nemo.user.vnspacemanager.syntaxcheck.FlowDefinitionCheck;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.FlowBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.FlowKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ldzd11 on 2015/11/9.
 */
public class UpdateFlowTest {

    private UpdateFlow updateFlow;

    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private UserId userId;
    private Flow flow;
    
    @org.junit.Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        userId = mock(UserId.class);
        flow = mock(Flow.class);
        
        updateFlow = new UpdateFlow(dataBroker, tenantManage);
        //updateFlow = mock(UpdateFlow.class);

    }

    @org.junit.Test
    public void testFlowHandling() throws Exception {
    	
    	String errorDefinition = new String("The match item has not been defined.");
    	
    	CheckedFuture connectiondefinitionFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
//       when(dataBroker.newReadOnlyTransaction().read(any(LogicalDatastoreType.class),
//       any(InstanceIdentifier.class))).thenReturn(userRolesFuture);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(connectiondefinitionFuture); 
         
    	//when(flowDefinitionCheck.CheckDefinition(any(Flow.class))).thenReturn(errorDefinition);
    	Assert.assertEquals(updateFlow.FlowHandling(userId,flow),errorDefinition);
    	
    	//errorDefinition = null;
    	//String errorInstance = new String("Case 1");
    	//when(flowInstanceCheck.checkFlowInstance(any(UserId.class),any(Flow.class))).thenReturn(errorInstance);
    	//Assert.assertEquals(updateFlow.FlowHandling(userId,flow),errorInstance);
    	
    	//errorInstance = null;
    	//userId = null;
    	//flow = null;
    	//WriteTransaction t = mock(WriteTransaction.class);
    	//when(dataBroker.newWriteOnlyTransaction()).thenReturn(t);
    	
        //Assert.assertNull(updateFlow.FlowHandling(userId, flow));

    }
}