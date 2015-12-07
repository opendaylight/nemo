/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.*;
import static org.mockito.Mockito.mock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.OperationId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Operations;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
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
import java.util.List;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.*;
public class DeleteOperationTest{
	private DataBroker dataBroker;
    private TenantManage tenantManage;
	private UserId userId;
	private User user;
    private	OperationId operationId;
	private DeleteOperation deleteoperation;
	private Operations operations;
	private Operation operation;
	private OperationId notoperationId;
	private List<Operation> operationList;
	private   WriteTransaction  writetransaction;
	@org.junit.Before
	public void setUp() throws Exception{
	dataBroker=mock(DataBroker.class);
	tenantManage=mock(TenantManage.class);
    userId=mock(UserId.class);
    operationId=mock(OperationId.class);
	user=mock(User.class);
	operation=mock(Operation.class);
	notoperationId=mock(OperationId.class);
    deleteoperation=new DeleteOperation(dataBroker,tenantManage);
    operations=mock(Operations.class);
    operationList=new ArrayList<Operation>(1);
	operationList.add(operation);
    writetransaction=mock(WriteTransaction.class);
	}
	@org.junit.Test
	public void DeleteOperationhandlingTest(){
	 CheckedFuture connectiondefinitionFuture = mock(CheckedFuture.class);
     ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
	 when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
		//branch 1
		when(tenantManage.getUser()).thenReturn(null);
		Assert.assertEquals(deleteoperation.DeleteOperationhandling(userId,operationId),"There are no user in the data store.");
		//branch 2
		when(tenantManage.getUser()).thenReturn(user);
		when(user.getOperations()).thenReturn(null);
		Assert.assertEquals(deleteoperation.DeleteOperationhandling(userId,operationId),null);
		//branch 3
		when(tenantManage.getUser()).thenReturn(user);
		when(user.getOperations()).thenReturn(operations);	
        when(operations.getOperation()).thenReturn(null);
        Assert.assertEquals(deleteoperation.DeleteOperationhandling(userId,operationId),"There are no operation instances in the data store.");		
	    //branch 4
		when(tenantManage.getUser()).thenReturn(user);
		when(user.getOperations()).thenReturn(operations);	
        when(operations.getOperation()).thenReturn(operationList);
		when(operation.getOperationId()).thenReturn(notoperationId);
		Assert.assertEquals(deleteoperation.DeleteOperationhandling(userId,operationId),"The operation instance " +operationId.getValue()+" is not exist. Could not be deleted.");
	    //branch 5
		when(tenantManage.getUser()).thenReturn(user);
		when(user.getOperations()).thenReturn(operations);	
        when(operations.getOperation()).thenReturn(operationList);
		when(operation.getOperationId()).thenReturn(operationId);
		when(dataBroker.newWriteOnlyTransaction()).thenReturn(writetransaction);
	    CheckedFuture<Void, TransactionCommitFailedException> f;
	    f=mock(CheckedFuture.class);
	    when(writetransaction.submit()).thenReturn(f);
		Assert.assertEquals(deleteoperation.DeleteOperationhandling(userId,operationId),null);
	}
}