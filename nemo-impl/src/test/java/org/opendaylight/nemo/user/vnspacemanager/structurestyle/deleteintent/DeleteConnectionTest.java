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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.ConnectionKey;
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
import java.util.*;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import static org.mockito.Mockito.*;
public class  DeleteConnectionTest{
	    private DataBroker dataBroker;
        private TenantManage tenantManage;
        private DeleteConnection deleteconnection;
        private UserId userId;
        private ConnectionId connectionID;
		private User user;
		private Objects objects;
		private Connection connection;
		private List<Connection> connectionList;
		private ConnectionId NotconnectionID;
		private  ConnectionId connectionID2;
		private   WriteTransaction  writetransaction;
@org.junit.Before
public void setUp() throws Exception {
	    dataBroker=mock(DataBroker.class);
		tenantManage=mock(TenantManage.class);
        deleteconnection=mock(DeleteConnection.class);
        userId=mock(UserId.class);
        connectionID=mock(ConnectionId.class);
		NotconnectionID=mock(ConnectionId.class);
		connectionID2=connectionID;
		user=mock(User.class);
		objects=mock(Objects.class);
		connection=mock(Connection.class);
		connectionList=new ArrayList<Connection>(3);
		connectionList.add(connection);
		deleteconnection=new DeleteConnection(dataBroker,tenantManage);
		writetransaction=mock(WriteTransaction.class);
        }
@org.junit.Test
public void DeleteConnectionHandlingTest() throws Exception{
	//no data test
	   CheckedFuture connectiondefinitionFuture = mock(CheckedFuture.class);
       ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
	   when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
	  
	   when(tenantManage.getUser()).thenReturn(null);
	   Assert.assertEquals(deleteconnection.DeleteConnectionHandling(userId,connectionID),"There are no connection instances in data store.");
	   //data exists and other branches
	   // branch 1
	   when (tenantManage.getUser()).thenReturn(user);
	   when(user.getObjects()).thenReturn(null);
	   Assert.assertEquals(deleteconnection.DeleteConnectionHandling(userId,connectionID),null);
	   //branch 2
	   when(tenantManage.getUser()).thenReturn(user);
	   when(user.getObjects()).thenReturn(objects);
	   when(user.getObjects().getConnection()).thenReturn(null);
	   Assert.assertEquals(deleteconnection.DeleteConnectionHandling(userId,connectionID),"There are no connection instances in data store.");
       //branch 3
	   when(tenantManage.getUser()).thenReturn(user);
	   when(user.getObjects()).thenReturn(objects);
	   when(objects.getConnection()).thenReturn(connectionList);
	   when(connection.getConnectionId()).thenReturn(NotconnectionID);
	   Assert.assertEquals(deleteconnection.DeleteConnectionHandling(userId,connectionID),"The connection instance " +connectionID.getValue()+" is not exit. Could not be deleted.");
       //branch 4
	   when(tenantManage.getUser()).thenReturn(user);
	   when(user.getObjects()).thenReturn(objects);
	   when(objects.getConnection()).thenReturn(connectionList);
	   when(connection.getConnectionId()).thenReturn(connectionID);
	   when(dataBroker.newWriteOnlyTransaction()).thenReturn(writetransaction);
	   CheckedFuture<Void, TransactionCommitFailedException> f;
	   f=mock(CheckedFuture.class);
	   when(writetransaction.submit()).thenReturn(f);
	   Assert.assertEquals(deleteconnection.DeleteConnectionHandling(userId,connectionID),null);
       //-------
		Assert.assertNotNull(deleteconnection);
		Assert.assertNotNull(tenantManage);
        //Assert.assertNull(deleteconnection.DeleteConnectionHandling(userId,connectionID));
        }
 
        }