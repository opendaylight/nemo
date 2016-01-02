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
import java.util.Map;
import java.util.HashMap;
import java.util.*;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import static org.mockito.Mockito.*;
public class  DeleteConnectionTest{
	    private DataBroker dataBroker;
        private TenantManage tenantManage;
        private DeleteConnection deleteconnection;
        private UserId userId;
        private ConnectionId connectionId;
		private User user;
		private Objects objects;
		private Connection connection;
		private List<Connection> connectionList;
		private ConnectionId NotconnectionId;
		private ConnectionId connectionId2;
		private WriteTransaction  writetransaction;
		
		private Map<ConnectionId, Connection> connectionMap;
		private Map<ConnectionId, Connection> connectionDataStore;
		
		
@org.junit.Before
public void setUp() throws Exception {
	    dataBroker=mock(DataBroker.class);
		tenantManage=mock(TenantManage.class);
        deleteconnection=mock(DeleteConnection.class);
        userId=mock(UserId.class);
        connectionId=mock(ConnectionId.class);
		NotconnectionId=mock(ConnectionId.class);
		connectionId2=connectionId;
		user=mock(User.class);
		objects=mock(Objects.class);
		connection=mock(Connection.class);
		connectionList=new ArrayList<Connection>(3);
		connectionList.add(connection);
		deleteconnection=new DeleteConnection(dataBroker,tenantManage);
		writetransaction=mock(WriteTransaction.class);
		
		connectionMap = new HashMap<ConnectionId, Connection>();
		connectionMap.put(connectionId,connection);
		connectionDataStore = new HashMap<ConnectionId, Connection>();
		connectionDataStore.put(connectionId,connection);
		
        }
@org.junit.Test
public void DeleteConnectionHandlingTest() throws Exception{
	
	   CheckedFuture connectiondefinitionFuture = mock(CheckedFuture.class);
       ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
	   when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
	   
	   
	   when(tenantManage.getConnection(userId)).thenReturn(connectionMap);
	   Assert.assertEquals(deleteconnection.DeleteConnectionHandling(userId,connectionId),null);
	   
	   when(tenantManage.getConnection(userId)).thenReturn(null);
	   when(tenantManage.getConnectionDataStore(userId)).thenReturn(connectionDataStore);
	   Assert.assertEquals(deleteconnection.DeleteConnectionHandling(userId,connectionId),null);
	   
	   when(tenantManage.getConnectionDataStore(userId)).thenReturn(null);
	   Assert.assertEquals(deleteconnection.DeleteConnectionHandling(userId,connectionId),"The connection instance null is not exit.");
	   
}
}