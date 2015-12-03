/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateResult;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.instancecheck.OperationInstanceCheck;
import org.opendaylight.nemo.user.vnspacemanager.syntaxcheck.OperationDefinitionCheck;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Operations;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.OperationBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.OperationKey;
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
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.instancecheck.ResultInstanceCheck;
import org.opendaylight.nemo.user.vnspacemanager.syntaxcheck.ResultDefinitionCheck;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Results;


/**
 * Created by ldzd11 on 2015/11/9.
 */
public class UpdateResultTest {

    private UpdateResult updateResult;

    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private ResultDefinitionCheck resultDefinitionCheck;
    private ResultInstanceCheck resultInstanceCheck;
    private UserId userId;
    private Results results;
    
    @org.junit.Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        resultDefinitionCheck = mock(ResultDefinitionCheck.class);
        resultInstanceCheck = mock(ResultInstanceCheck.class);
        userId = mock(UserId.class);
        results = mock(Results.class);
        
        updateResult = new UpdateResult(dataBroker, tenantManage);
        //updateFlow = mock(UpdateFlow.class);

    }

    @org.junit.Test
    public void testResultHandling() throws Exception {
    	
    	String errorDefinition = null;
    	when(resultDefinitionCheck.CheckDefinition(any(Results.class))).thenReturn(null);
    	Assert.assertEquals(updateResult.ResultHandling(userId,results),errorDefinition);
    	
    	errorDefinition = null;
    	String errorInstance = null;
    	when(resultInstanceCheck.checkResultInstance(any(UserId.class),any(Results.class))).thenReturn(errorInstance);
    	Assert.assertEquals(updateResult.ResultHandling(userId,results),errorInstance);
    	
    	errorInstance = null;
    	//userId = null;
    	//results = null;
    	//WriteTransaction t = mock(WriteTransaction.class);
    	//when(dataBroker.newWriteOnlyTransaction()).thenReturn(t);
    	
        Assert.assertNull(updateResult.ResultHandling(userId, results));

    }
}