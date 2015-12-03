/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;

import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateConnection;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.instancecheck.ConnectionInstanceCheck;
import org.opendaylight.nemo.user.vnspacemanager.syntaxcheck.ConnectionDefinitionCheck;


import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.spy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.instancecheck.ConnectionInstanceCheck;
import org.opendaylight.nemo.user.vnspacemanager.syntaxcheck.ConnectionDefinitionCheck;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.ConnectionBuilder;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.ConnectionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
/**
 * Created by ldzd11 on 2015/11/9.
 */

//@RunWith(PowerMockRunner.class)
//@PrepareForTest( { UpdateConnection.class })
public class UpdateConnectionTest {


    private UpdateConnection updateConnection;

    private UserId userId;
    private Connection connection;
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    //private ConnectionDefinitionCheck connectionDefinitionCheck;
    //private ConnectionInstanceCheck connectionInstanceCheck;



    @Before
    public void setUp() throws Exception {

        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        //connectionDefinitionCheck = mock(ConnectionDefinitionCheck.class);
        //connectionInstanceCheck = mock(ConnectionInstanceCheck.class);
        
        
        updateConnection = new UpdateConnection(dataBroker,tenantManage);

        userId = mock(UserId.class);
        connection = mock(Connection.class);
        //updateConnection = mock(UpdateConnection.class);
        
    }

    @Test
    public void testConnectionHandling() throws Exception {
    	
    	String errorDefinition = new String("This type of connection has not been defined.");
    	
    	CheckedFuture connectiondefinitionFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
//       when(dataBroker.newReadOnlyTransaction().read(any(LogicalDatastoreType.class),
//       any(InstanceIdentifier.class))).thenReturn(userRolesFuture);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(connectiondefinitionFuture); 
         
        //when(updateConnection.callPrivateMethod()).thenCallRealMethod();  
        //when(updateConnection.connectionDefinitionCheck.CheckConnectionDefinition.any(Connection.class)).thenReturn(errorDefinition);
    	//when(CheckConnectionDefinition(any(Connection.class))).thenReturn(errorDefinition);
    	//connectionDefinitionCheck.CheckConnectionDefinition(mock(Connection.class));
    	
    	Assert.assertEquals(updateConnection.ConnectionHandling(userId,connection),errorDefinition);
    	
    	//errorDefinition = null;
    	//String errorInstance = new String("Case 1");
    	//when(checkConnInstance(any(UserId.class),any(Connection.class))).thenReturn(errorInstance);
    	//Assert.assertEquals(updateConnection.ConnectionHandling(userId,connection),errorInstance);
    	
    	//errorInstance = null;
    	//userId = null;
    	//connection = null;
    	//WriteTransaction t = mock(WriteTransaction.class);
    	//when(dataBroker.newWriteOnlyTransaction()).thenReturn(t);
    	
        //Assert.assertNull(updateConnection.ConnectionHandling(userId, connection));

    }
}