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
import org.opendaylight.nemo.user.tenantmanager.AAA;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.structure.style.nemo.delete.input.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.structure.style.nemo.delete.input.Operations;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.OperationId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.StructureStyleNemoDeleteInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.structure.style.nemo.delete.input.Results;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import java.util.*;
import java.util.List;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserPassword;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserRoleName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
public class DeleteIntentTest{
	private DataBroker dataBroker;
    private TenantManage tenantManage;
    private DeleteNode deleteNode;
    private DeleteConnection deleteConnection;
    private DeleteFlow deleteFlow;
    private DeleteOperation deleteOperation;
    private DeleteResult deleteResult;
	private AAA aaa;
	private Objects objects;
	private StructureStyleNemoDeleteInput styleNemoDeleteInput;
	private DeleteIntent deleteintent;
	private Operations operations;
	private Results results;
	private UserId userid;
	private NodeId nodeid;
	private ConnectionId connectionid;
	private FlowId flowid;
	private List<NodeId> nodeidlist;
	private List<ConnectionId> connectionidlist;
	private List<FlowId> flowidlist;
	private List<OperationId> operationidlist;
	private OperationId operationid;
	private User user;
@org.junit.Before
public void setUp() throws Exception{
	dataBroker=mock(DataBroker.class);
	tenantManage=mock(TenantManage.class);
	deleteNode=mock(DeleteNode.class);
	deleteConnection=mock(DeleteConnection.class);
	//deleteFlow=mock(DeleteFlow.class);
	deleteOperation=mock(DeleteOperation.class);
	deleteResult=mock(DeleteResult.class);
	aaa=mock(AAA.class);
	objects=mock(Objects.class);
	styleNemoDeleteInput=mock(StructureStyleNemoDeleteInput.class);
	deleteintent=new DeleteIntent(dataBroker,tenantManage);
	results=mock(Results.class);
	operations=mock(Operations.class);
	nodeid=mock(NodeId.class);
	connectionid=mock(ConnectionId.class);
	flowid=mock(FlowId.class);
	nodeidlist=new ArrayList<NodeId>(1);
	nodeidlist.add(nodeid);
	connectionidlist=new ArrayList<ConnectionId>(1);
	connectionidlist.add(connectionid);
	flowidlist=new ArrayList<FlowId>(1);
	flowidlist.add(flowid);
	user=mock(User.class);
	operationid=mock(OperationId.class);
	operationidlist=new ArrayList<OperationId>(1);
	operationidlist.add(operationid);
}
@org.junit.Test
public void styleNemoDeleteOutputTest(){
	 CheckedFuture connectiondefinitionFuture = mock(CheckedFuture.class);
     ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
	 when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
	 UserId useridtest=mock(UserId.class);
	 UserName usernametest=mock(UserName.class);
	 UserPassword userpasswordtest=mock(UserPassword.class);
	 UserRoleName userrolenametest=mock(UserRoleName.class);
	 when(styleNemoDeleteInput.getUserId()).thenReturn(useridtest);
	 //branch 1
	 when(aaa.checkUser(styleNemoDeleteInput.getUserId())).thenReturn("The user is not exist.");
	 Assert.assertEquals(deleteintent.styleNemoDeleteOutput(aaa,styleNemoDeleteInput),"The user is not exist.");
	 //branch 2
	 when(aaa.checkUser(styleNemoDeleteInput.getUserId())).thenReturn(null);
	 when(styleNemoDeleteInput.getObjects()).thenReturn(null);
	  Assert.assertEquals(deleteintent.styleNemoDeleteOutput(aaa,styleNemoDeleteInput),null);
	 //branch 3
	 //if-
	  when(aaa.checkUser(styleNemoDeleteInput.getUserId())).thenReturn(null);
	  when(styleNemoDeleteInput.getObjects()).thenReturn(objects);
	  when(styleNemoDeleteInput.getResults()).thenReturn(null);
	  when(styleNemoDeleteInput.getOperations()).thenReturn(null);
	  //if--
	  when(styleNemoDeleteInput.getObjects().getNode()).thenReturn(null);
	  when(styleNemoDeleteInput.getObjects().getConnection()).thenReturn(null);
	  when(styleNemoDeleteInput.getObjects().getFlow()).thenReturn(null);
	  Assert.assertEquals(deleteintent.styleNemoDeleteOutput(aaa,styleNemoDeleteInput),null); 
	  //branch 4
	  //if-
	  when(aaa.checkUser(styleNemoDeleteInput.getUserId())).thenReturn(null);
	  when(styleNemoDeleteInput.getObjects()).thenReturn(objects);
	  when(styleNemoDeleteInput.getResults()).thenReturn(null);
	  when(styleNemoDeleteInput.getOperations()).thenReturn(null);
	  //if--
	  when(styleNemoDeleteInput.getObjects().getNode()).thenReturn(nodeidlist);
	  when(styleNemoDeleteInput.getObjects().getConnection()).thenReturn(null);
	  when(styleNemoDeleteInput.getObjects().getFlow()).thenReturn(null);
	  when(styleNemoDeleteInput.getUserId()).thenReturn(userid);
	  when(tenantManage.getUser()).thenReturn(user);
	  Assert.assertEquals(deleteintent.styleNemoDeleteOutput(aaa,styleNemoDeleteInput),"The node instance null is not exist.");
	  //branch 5
	  //if-
	  when(aaa.checkUser(styleNemoDeleteInput.getUserId())).thenReturn(null);
	  when(styleNemoDeleteInput.getObjects()).thenReturn(objects);
	  when(styleNemoDeleteInput.getResults()).thenReturn(null);
	  when(styleNemoDeleteInput.getOperations()).thenReturn(null);
	  //if--
	  when(styleNemoDeleteInput.getObjects().getNode()).thenReturn(null);
	  when(styleNemoDeleteInput.getObjects().getConnection()).thenReturn(connectionidlist);
	  when(styleNemoDeleteInput.getObjects().getFlow()).thenReturn(null);
	  Assert.assertEquals(deleteintent.styleNemoDeleteOutput(aaa,styleNemoDeleteInput),"The connection instance null is not exit.");
      //branch 6
     //if-
	  when(aaa.checkUser(styleNemoDeleteInput.getUserId())).thenReturn(null);
	  when(styleNemoDeleteInput.getObjects()).thenReturn(objects);
	  when(styleNemoDeleteInput.getResults()).thenReturn(null);
	  when(styleNemoDeleteInput.getOperations()).thenReturn(null);
	  //if--
	  when(styleNemoDeleteInput.getObjects().getNode()).thenReturn(null);
	  when(styleNemoDeleteInput.getObjects().getConnection()).thenReturn(null);
	  when(styleNemoDeleteInput.getObjects().getFlow()).thenReturn(flowidlist);
      Assert.assertEquals(deleteintent.styleNemoDeleteOutput(aaa,styleNemoDeleteInput),"The flow instance null is not exist.");	  
	 //branch 7
	 //if-
	  when(aaa.checkUser(styleNemoDeleteInput.getUserId())).thenReturn(null);
	  when(styleNemoDeleteInput.getObjects()).thenReturn(null);
	  when(styleNemoDeleteInput.getResults()).thenReturn(null);
	  when(styleNemoDeleteInput.getOperations()).thenReturn(operations);
	  //if--
      when(operations.getOperation()).thenReturn(null);
	  Assert.assertEquals(deleteintent.styleNemoDeleteOutput(aaa,styleNemoDeleteInput),null); 
	  //branch 8
	  //if-
	  when(styleNemoDeleteInput.getObjects()).thenReturn(null);
	  when(styleNemoDeleteInput.getResults()).thenReturn(null);
	  when(styleNemoDeleteInput.getOperations()).thenReturn(operations);
	  //if--
      when(operations.getOperation()).thenReturn(operationidlist);
	  Assert.assertEquals(deleteintent.styleNemoDeleteOutput(aaa,styleNemoDeleteInput),"The operation instance null is not exist.");
	 //branch 9
	 //if-
	 when(aaa.checkUser(styleNemoDeleteInput.getUserId())).thenReturn(null);
	  when(styleNemoDeleteInput.getObjects()).thenReturn(null);
	  when(styleNemoDeleteInput.getResults()).thenReturn(results);
	  when(styleNemoDeleteInput.getOperations()).thenReturn(null);
	  Assert.assertEquals(deleteintent.styleNemoDeleteOutput(aaa,styleNemoDeleteInput),null);   	 
}
}