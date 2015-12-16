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


        //errorInfo == null
        properties = new LinkedHashMap<String,String>(){{
            put(new String("1"),NEMOConstants.range);
            put(new String("group"),NEMOConstants.string);
            //put(new String("100"),NEMOConstants.integer);
            put(new String("100,200"),NEMOConstants.range);
        }};
        propertyList.put(new String("p1"),properties);
        //createConnection()
        //userId,connectionname,connectiontype,endnodes,propertyList
        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);
        when(user.getObjects()).thenReturn(object);
        when(object.getNode()).thenReturn(nodeList);
        when(object.getNode()).thenReturn(nodeList);
        when(object.getConnection()).thenReturn(connList);
        when(object.getConnection()).thenReturn(connList);

        //connection
        //connection exists
        when(connList.get(0).getConnectionName()).thenReturn(connectionName);
        when(connectionName.getValue()).thenReturn(new String("conn1"));
        when(connList.get(0).getConnectionId()).thenReturn(connectionId);

        //endnodes
        when(nodeList.get(0).getNodeName()).thenReturn(nodeName);
        when(nodeName.getValue()).thenReturn(new String("node1"));
        when(nodeList.get(0).getNodeId()).thenReturn(nodeId);
        when(nodeList.get(0).getNodeId()).thenReturn(nodeId);

        //propertylist
        updateConnectionLangTest.ConnectionHandling(userId, connectionname, connectiontype, endnodes, propertyList);

        properties = new LinkedHashMap<String,String>(){{
            //put(new String("1"),NEMOConstants.range);
            //put(new String("group"),NEMOConstants.string);
            //put(new String("100"),NEMOConstants.integer);
            put(new String("100,200"),NEMOConstants.range);
        }};
        propertyList.put(new String("p1"),properties);
        //createConnection()
        //userId,connectionname,connectiontype,endnodes,propertyList
        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);
        when(user.getObjects()).thenReturn(object);
        when(object.getNode()).thenReturn(nodeList);
        when(object.getNode()).thenReturn(nodeList);
        when(object.getConnection()).thenReturn(connList);
        when(object.getConnection()).thenReturn(connList);

        //connection
        //connection exists
        when(connList.get(0).getConnectionName()).thenReturn(connectionName);
        when(connectionName.getValue()).thenReturn(new String("conn1"));
        when(connList.get(0).getConnectionId()).thenReturn(connectionId);

        //endnodes
        when(nodeList.get(0).getNodeName()).thenReturn(nodeName);
        when(nodeName.getValue()).thenReturn(new String("node1"));
        when(nodeList.get(0).getNodeId()).thenReturn(nodeId);
        when(nodeList.get(0).getNodeId()).thenReturn(nodeId);

        //propertylist
        updateConnectionLangTest.ConnectionHandling(userId, connectionname, connectiontype, endnodes, propertyList);

        /*connList = new ArrayList<Connection>();
        endnodes = new ArrayList<String>();
        properties = new LinkedHashMap<String,String>(){{
            //put(new String("1"),NEMOConstants.range);
            //put(new String("group"),NEMOConstants.string);
            //put(new String("100"),NEMOConstants.integer);
            put(new String("100,200"),NEMOConstants.range);
        }};
        propertyList.put(new String("p1"),properties);
        //createConnection()
        //userId,connectionname,connectiontype,endnodes,propertyList
        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);
        when(user.getObjects()).thenReturn(object);
        when(object.getNode()).thenReturn(nodeList);
        when(object.getNode()).thenReturn(nodeList);
        when(object.getConnection()).thenReturn(connList);
        when(object.getConnection()).thenReturn(connList);


        updateConnectionLangTest.ConnectionHandling(userId, connectionname, connectiontype, endnodes, propertyList);*/


        properties = new LinkedHashMap<String,String>(){{
            //put(new String("1"),NEMOConstants.range);
            //put(new String("group"),NEMOConstants.string);
            //put(new String("100"),NEMOConstants.integer);
            //put(new String("100,200"),NEMOConstants.range);
        }};
        propertyList.put(new String("p1"),properties);
        //createConnection()
        //userId,connectionname,connectiontype,endnodes,propertyList

        //updateConnectionLangTest.ConnectionHandling(userId, connectionname, connectiontype, endnodes, propertyList);



    }
}