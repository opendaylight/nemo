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

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.lang.reflect.Method; 
import java.util.*;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOParse.NEMOparser;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.NodeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.NodeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValuesBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;



/**
 * Created by Thomas Liu on 2015/12/15.
 */
public class UpdateNodeLangTest extends TestCase {

    private UpdateNodeLang updateNodeLangTest;
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private UserId userId;
    private String nodename;
    private String nodetype;
    private List<String> subnodes;
    private LinkedHashMap<String, LinkedHashMap<String,String>> propertyList;
    private LinkedHashMap<String,String> properties;
    private User user;
    private Objects object;
    private List<Node> nodes;

    @org.junit.Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        updateNodeLangTest = new UpdateNodeLang(dataBroker,tenantManage);

        propertyList = new LinkedHashMap<String,LinkedHashMap<String,String>>();
        properties = new LinkedHashMap<String,String>(){{
            //put(new String("1"),NEMOConstants.range);
            put(new String("group"),NEMOConstants.string);
            put(new String("100"),NEMOConstants.integer);
            put(new String("100,200"),NEMOConstants.range);
        }};
        propertyList.put(new String("1"),properties);

        user = mock(User.class);
        object = mock(Objects.class);
        nodes = new ArrayList<Node>(){{add(mock(Node.class));}};

        nodename = new String("node1");

    }

    @org.junit.Test
    public void testNodeHandling() throws Exception {

        nodetype = new String("host");
        subnodes = new ArrayList<String>();
        updateNodeLangTest.NodeHandling(userId,nodename,nodetype,subnodes,propertyList);

        nodetype = new String("host");
        subnodes = new ArrayList<String>(){{
            add("node1");
        }};

        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);

        when(user.getObjects()).thenReturn(object);
        when(user.getObjects()).thenReturn(object);
        when(object.getNode()).thenReturn(nodes);
        when(user.getObjects()).thenReturn(object);
        when(object.getNode()).thenReturn(nodes);

        when(nodes.get(0).getNodeName()).thenReturn(new NodeName("node1"));
        when(nodes.get(0).getNodeId()).thenReturn(new NodeId("11111111-1111-1111-1111-111111111111"));

        when(nodes.get(0).getNodeName()).thenReturn(new NodeName("node1"));
        when(nodes.get(0).getNodeId()).thenReturn(new NodeId("11111111-1111-1111-1111-111111111111"));
        when(nodes.get(0).getNodeId()).thenReturn(new NodeId("11111111-1111-1111-1111-111111111111"));

        updateNodeLangTest.NodeHandling(userId, nodename, nodetype, subnodes, propertyList);


    }
	@org.junit.Test
	public void createNodeTest() throws Exception{
	//branch 1 
	UserId userId=mock(UserId.class);
	NodeId nodeid=new NodeId("11111111-1111-1111-1111-111111111111");
	NodeKey nodekey=mock(NodeKey.class);
	String nodename="nodename"; 
	String nodetype=NEMOConstants.host; 
	ObjectId objectid=mock(ObjectId.class);
	List<String> subnodes=null; 
	Node node =mock(Node.class);
	Map<NodeId, Node> nodes=new HashMap<NodeId, Node>(2);
	nodes.put(nodeid,node);
	LinkedHashMap<String, LinkedHashMap<String,String>> propertyList1=null;
    when(tenantManage.getObjectId(userId,nodename)).thenReturn("11111111-1111-1111-1111-111111111111");	
	when(tenantManage.getNode(any(UserId.class))).thenReturn(nodes);
	when(nodes.get(any(NodeId.class))).thenReturn(node);
	when(tenantManage.getNodeDataStore(userId)).thenReturn(nodes);
	when(nodes.get(any(NodeId.class))).thenReturn(node);
	when(node.getKey()).thenReturn(nodekey);
	when(node.getNodeId()).thenReturn(nodeid);
	Class<?>[] args=new Class<?>[5];
	args[0]=userId.getClass();
	args[1]=nodename.getClass();
	args[2]=nodetype.getClass();
	args[3]=subnodes.getClass();
	args[4]=propertyList1.getClass();
	Object[] args1 = new Object[5]; 
	args1[0]=userId;
	args1[1]=nodename;
	args1[2]=nodetype;
	args1[3]=subnodes;
	args1[4]=propertyList1;
	Method updatenodeintentlang= updateNodeLangTest.getClass().getDeclaredMethod("createNode",args);  
	updatenodeintentlang.setAccessible(true);
	Assert.assertNull(updatenodeintentlang.invoke(updateNodeLangTest,args1));
    //branch 2
	when(tenantManage.getObjectId(userId,nodename)).thenReturn("11111111-1111-1111-1111-111111111111");	
	when(tenantManage.getNode(any(UserId.class))).thenReturn(nodes);
	when(nodes.get(any(NodeId.class))).thenReturn(node);
	when(tenantManage.getNodeDataStore(userId)).thenReturn(nodes);
	when(nodes.get(any(NodeId.class))).thenReturn(node);
	when(node.getKey()).thenReturn(nodekey);
	when(node.getNodeId()).thenReturn(nodeid);
	subnodes=new ArrayList<String>(3);
	String subnodeName="subnodename";
	subnodes.add(subnodeName);
	args1[3]=subnodes;
	when(tenantManage.getObjectId(userId,subnodeName)).thenReturn(null);
	Assert.assertEquals(updatenodeintentlang.invoke(updateNodeLangTest,args1),"The subnode " +subnodeName+ " is not exist.");
	//branch 3
	when(tenantManage.getObjectId(userId,nodename)).thenReturn("11111111-1111-1111-1111-111111111111");	
	when(tenantManage.getNode(any(UserId.class))).thenReturn(nodes);
	when(nodes.get(any(NodeId.class))).thenReturn(node);
	when(tenantManage.getNodeDataStore(userId)).thenReturn(nodes);
	when(nodes.get(any(NodeId.class))).thenReturn(node);
	when(node.getKey()).thenReturn(nodekey);
	when(node.getNodeId()).thenReturn(nodeid);
	
	when(tenantManage.getObjectId(userId,subnodeName)).thenReturn("11111111-1111-1111-1111-111111111111");
	Assert.assertNull(updatenodeintentlang.invoke(updateNodeLangTest,args1));
	//branch 4
	LinkedHashMap<String,String> properties1;
	propertyList1 = new LinkedHashMap<String,LinkedHashMap<String,String>>();
    properties1 = new LinkedHashMap<String,String>(){{
        //put(new String("1"),NEMOConstants.range);
        put(new String("group"),NEMOConstants.string);
        put(new String("100"),NEMOConstants.integer);
        put(new String("100,200"),NEMOConstants.range);
    }};
    propertyList1.put(new String("1"),properties1);
	args1[4]=propertyList1;
	when(tenantManage.getObjectId(userId,nodename)).thenReturn("11111111-1111-1111-1111-111111111111");	
	when(tenantManage.getNode(any(UserId.class))).thenReturn(nodes);
	when(nodes.get(any(NodeId.class))).thenReturn(node);
	when(tenantManage.getNodeDataStore(userId)).thenReturn(nodes);
	when(nodes.get(any(NodeId.class))).thenReturn(node);
	when(node.getKey()).thenReturn(nodekey);
	when(node.getNodeId()).thenReturn(nodeid);
	
	when(tenantManage.getObjectId(userId,subnodeName)).thenReturn("11111111-1111-1111-1111-111111111111");
	Assert.assertNull(updatenodeintentlang.invoke(updateNodeLangTest,args1));
	
	}
}