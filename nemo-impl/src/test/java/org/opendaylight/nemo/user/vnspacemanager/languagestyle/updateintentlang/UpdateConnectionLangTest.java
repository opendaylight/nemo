/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.languagestyle.updateintentlang;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateConnection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.ConnectionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.ConnectionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValuesBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionId;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;


import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashMap;

import  java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Thomas Liu on 2015/12/14.
 */
public class UpdateConnectionLangTest extends TestCase {
    private UpdateConnectionLang updateConnectionLangTest;
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private UserId userId;
    private String connectionname;
    private String connectiontype;
    private List<String> endnodes;
    private LinkedHashMap<String, LinkedHashMap<String,String>> propertyList;
    private LinkedHashMap<String,String> properties;
    private Field field;
    private Class class1;
    private Method method;

    //createConnection
    private User user;
    private Objects object;
    private List<Node> nodeList;
    private List<Connection> connList;
    private ConnectionName connectionName;
    private ConnectionId connectionId;
    private NodeName nodeName;
    private NodeId nodeId;

    @org.junit.Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        updateConnectionLangTest = new UpdateConnectionLang(dataBroker,tenantManage);
        userId = mock(UserId.class);
        propertyList = new LinkedHashMap<String, LinkedHashMap<String,String>>();
        connectionname = new String("conn1");
        connectiontype = new String("p2p");
        endnodes = new ArrayList<String>(){{add(new String("123"));}};
        class1 = UpdateConnectionLang.class;
        //createConnection
        user = mock(User.class);
        object = mock(Objects.class);
        nodeList = new ArrayList<Node>(){{add(mock(Node.class));}};
        connList = new ArrayList<Connection>(){{add(mock(Connection.class));}};
        connectionName = mock(ConnectionName.class);
        connectionId = mock(ConnectionId.class);
        nodeName = mock(NodeName.class);
        nodeId = mock(NodeId.class);



    }

    @org.junit.Test
    public void testConnectionHandling() throws Exception {
        field = class1.getDeclaredField("updateConnection");
        UpdateConnection updateConnection = mock(UpdateConnection.class);
        field.setAccessible(true);
        field.set(updateConnectionLangTest,updateConnection);
        field = class1.getDeclaredField("connection");
        Connection connection = mock(Connection.class);
        field.setAccessible(true);
        field.set(updateConnectionLangTest,connection);
        //errorInfo == null
        endnodes = new ArrayList<String>(){{add(new String("123"));}};
        connectiontype = new String("p2p");
        properties = new LinkedHashMap<String,String>(){{
            //put(new String("1"),NEMOConstants.range);
            //put(new String("group"),NEMOConstants.string);
            //put(new String("100"),NEMOConstants.integer);
            put(new String("100,200"),NEMOConstants.range);
        }};
        propertyList.put(new String("p1"),properties);
        //checkProperties()
        //createConnection()
        //userId,connectionname,connectiontype,endnodes,propertyList
        when(tenantManage.getObjectId(userId,connectionname)).thenReturn("11111111-1111-1111-1111-111111111111");
        HashMap<ConnectionId,Connection> connectionMap = new HashMap<ConnectionId,Connection>();
        ConnectionId connectionId = new ConnectionId("11111111-1111-1111-1111-111111111111");
        Connection connection2 = mock(Connection.class);
        connectionMap.put(connectionId,connection2);
        when(tenantManage.getConnection(userId)).thenReturn(connectionMap);
        when(tenantManage.getConnection(userId)).thenReturn(connectionMap);
        //if (!endnodes.isEmpty())
        when(tenantManage.getObjectId(userId,"123")).thenReturn("11111111-1111-1111-1111-111111111111");
        when(tenantManage.getObjectId(userId,"123")).thenReturn("11111111-1111-1111-1111-111111111111");
        when(updateConnection.ConnectionHandling(userId,connection)).thenReturn("ConnectionHandling");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@ Result1 :"+updateConnectionLangTest.ConnectionHandling(userId, connectionname, connectiontype, endnodes, propertyList));


        //
        properties = new LinkedHashMap<String,String>(){{
            put(new String("100,200"),NEMOConstants.range);
            put(new String("100"),NEMOConstants.integer);
        }};
        propertyList.put(new String("p1"),properties);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@ Result2 :"+updateConnectionLangTest.ConnectionHandling(userId, connectionname, connectiontype, endnodes, propertyList));
    }

    @org.junit.Test
    public void testcheckProperty() throws Exception {
        method = class1.getDeclaredMethod("checkProperty",new Class[]{
                LinkedHashMap.class,
        });
        properties = new LinkedHashMap<String,String>(){{
            put(new String("100"),NEMOConstants.integer);
            put(new String("group"),NEMOConstants.string);
            put(new String("100,200"),NEMOConstants.range);
        }};
        propertyList.put(new String("s1"),properties);
        method.setAccessible(true);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@ Result3 :"+method.invoke(updateConnectionLangTest,propertyList));

        properties = new LinkedHashMap<String,String>(){{
            put(new String("group"),NEMOConstants.string);
            put(new String("100"),NEMOConstants.integer);
            put(new String("100,200"),NEMOConstants.range);
        }};
        propertyList.put(new String("s1"),properties);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@ Result4 :"+method.invoke(updateConnectionLangTest,propertyList));
    }

    @org.junit.Test
    public void testcreateConnection() throws Exception {

        method = class1.getDeclaredMethod("createConnection",new Class[]{
                UserId.class,
                String.class,
                String.class,
                List.class,
                LinkedHashMap.class,
        });
        method.setAccessible(true);


        properties = new LinkedHashMap<String,String>(){{
            put(new String("100"),NEMOConstants.string);
        }};
        propertyList.put(new String("p1"),properties);
        endnodes = new ArrayList<String>();
        when(tenantManage.getObjectId(userId,connectionname)).thenReturn(null);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@ Result5 :"+method.invoke(updateConnectionLangTest,userId,connectionname,connectiontype,endnodes,propertyList));


        properties = new LinkedHashMap<String,String>(){{
            put(new String("200,100"),NEMOConstants.range);
        }};
        propertyList.put(new String("p1"),properties);
        endnodes = new ArrayList<String>();
        when(tenantManage.getObjectId(userId,connectionname)).thenReturn(null);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@ Result6 :"+method.invoke(updateConnectionLangTest,userId,connectionname,connectiontype,endnodes,propertyList));

        properties = new LinkedHashMap<String,String>(){{
            put(new String("1"),NEMOConstants.integer);
        }};
        propertyList.put(new String("p1"),properties);
        endnodes = new ArrayList<String>();
        when(tenantManage.getObjectId(userId,connectionname)).thenReturn(null);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@ Result7 :"+method.invoke(updateConnectionLangTest,userId,connectionname,connectiontype,endnodes,propertyList));

        properties = new LinkedHashMap<String,String>(){{
            put(new String("100"),NEMOConstants.integer);

        }};
        propertyList.put(new String("p1"),properties);
        endnodes = new ArrayList<String>();
        HashMap<ConnectionId,Connection> connectionMap = new HashMap<ConnectionId,Connection>();
        ConnectionId connectionId = new ConnectionId("11111111-1111-1111-1111-111121111111");
        Connection connection2 = mock(Connection.class);
        connectionMap.put(connectionId,connection2);
        when(tenantManage.getObjectId(userId,connectionname)).thenReturn("11111111-1111-1111-1111-111111111111");
        when(tenantManage.getConnection(userId)).thenReturn(connectionMap);
        connectionMap.clear();
        connectionMap.put(new ConnectionId("11111111-1111-1111-1111-111111111111"),connection2);
        when(tenantManage.getConnectionDataStore(userId)).thenReturn(connectionMap);
        when(tenantManage.getConnectionDataStore(userId)).thenReturn(connectionMap);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@ Result8 :"+method.invoke(updateConnectionLangTest,userId,connectionname,connectiontype,endnodes,propertyList));
    }


}