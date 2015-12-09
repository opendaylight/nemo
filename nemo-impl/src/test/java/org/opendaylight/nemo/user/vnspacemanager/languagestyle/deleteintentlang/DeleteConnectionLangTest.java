/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package user.vnspacemanager.languagestyle.deleteintentlang;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.deleteintentlang.DeleteConnectionLang;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent.DeleteConnection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionName;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

/**
 * Created by zhangmeng on 2015/12/9.
 */
public class DeleteConnectionLangTest extends TestCase {
    private DeleteConnectionLang deleteConnectionLang;
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private DeleteConnection deleteConnection;
    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        deleteConnection = mock(DeleteConnection.class);
        
        deleteConnectionLang = new DeleteConnectionLang(dataBroker,tenantManage);
    }

    @Test
    public void testDeleteConnectionHandling() throws Exception {
        String result;
        String connectionname = new String("test");
        Objects objects = mock(Objects.class);
        UserId userId = mock(UserId.class);
        User user = mock(User.class);
        Connection connection = mock(Connection.class);
        ConnectionId connectionId = mock(ConnectionId.class);
        ConnectionName connectionName = mock(ConnectionName.class);
        List<Connection> connections = new ArrayList<Connection>();

        doNothing().when(tenantManage).fetchVNSpace(any(UserId.class));
        when(tenantManage.getUser()).thenReturn(user);
        when(user.getObjects())
                .thenReturn(null)//if (user.getObjects()!=null)
                .thenReturn(objects);//get into if

        result = deleteConnectionLang.DeleteConnectionHandling(userId,connectionname);
        Assert.assertTrue(result.equals("The connection " + connectionname + " is not exist in this user vn space."));
        verify(user).getObjects();
        //test null
        when(objects.getConnection()).thenReturn(connections);
        Assert.assertTrue(connections.isEmpty());
        result = deleteConnectionLang.DeleteConnectionHandling(userId,connectionname);
        Assert.assertTrue(result.equals("The connection " + connectionname + " is not exist in this user vn space."));
        //test not null
        connections.add(connection);
        when(connection.getConnectionName()).thenReturn(connectionName);
        when(connectionName.getValue())
                .thenReturn("")
                .thenReturn(connectionname);
        Assert.assertTrue(!connections.isEmpty());
        result = deleteConnectionLang.DeleteConnectionHandling(userId,connectionname);
        Assert.assertTrue(result.equals("The connection " + connectionname + " is not exist in this user vn space."));
        //test last
        when(connection.getConnectionId()).thenReturn(connectionId);
        Class<DeleteConnectionLang> class1 = DeleteConnectionLang.class;
        Field field = class1.getDeclaredField("deleteConnection");
        field.setAccessible(true);
        field.set(deleteConnectionLang, deleteConnection);
        System.out.println(field.get(deleteConnectionLang));
        when(deleteConnection.DeleteConnectionHandling(any(UserId.class),any(ConnectionId.class))).thenReturn(new String("test"));
        result = deleteConnectionLang.DeleteConnectionHandling(userId,connectionname);
        Assert.assertTrue(result.equals("test"));
        verify(connection).getConnectionId();
    }
}