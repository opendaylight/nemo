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

import java.util.*;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOParse.NEMOparser;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.GetDefinitions;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.hosts.PhysicalHost;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalHostName;
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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ObjectId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.TemplateName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.definitions.TemplateDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserRoleName;
//import java.lang.reflect.Field;
import java.lang.reflect.Field;
import java.lang.reflect.Method;



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
    private Field field;
    private Class class1;
    private Method method;

    @org.junit.Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        updateNodeLangTest = new UpdateNodeLang(dataBroker,tenantManage);
        propertyList = new LinkedHashMap<String,LinkedHashMap<String,String>>();
        user = mock(User.class);
        object = mock(Objects.class);
        nodes = new ArrayList<Node>(){{add(mock(Node.class));}};
        userId = mock(UserId.class);
        nodename = new String("node1");
        class1 = UpdateNodeLang.class;

    }

    @org.junit.Test
    public void testNodeHandling() throws Exception {
        //if (nodetype.equals(NEMOConstants.host)&&!subnodes.isEmpty())
        nodetype = new String(NEMOConstants.host);
        subnodes = new ArrayList<String>(){{
            add("node1");
        }};
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Result1 :"+updateNodeLangTest.NodeHandling(userId,nodename,nodetype,subnodes,propertyList));

        //if (tenantManage.getTempalteDefinition(userId)!=null)
        //if (!subnodes.isEmpty())
        //if (nodeModel)
        //if (!subnodes.isEmpty())
        nodetype = new String("null");
        subnodes = new ArrayList<String>(){{
            add("node1");
        }};
        HashMap<TemplateName,TemplateDefinition>  TempalteDefinition = new HashMap<TemplateName,TemplateDefinition>();
        TemplateName templateName = new TemplateName("null");
        TemplateDefinition templateDefinition = mock(TemplateDefinition.class);
        TempalteDefinition.put(templateName,templateDefinition);
        when(tenantManage.getTempalteDefinition(userId)).thenReturn(TempalteDefinition);
        when(tenantManage.getTempalteDefinition(userId)).thenReturn(TempalteDefinition);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Result2 :"+updateNodeLangTest.NodeHandling(userId,nodename,nodetype,subnodes,propertyList));

        //if (tenantManage.getTempalteDefinition(userId)==null)
        //if (tenantManage.getDefinitionDataStore(userId)!=null)
        //if (!subnodes.isEmpty())
        //if (nodeModel)
        //if (!subnodes.isEmpty())
        nodetype = new String("null");
        subnodes = new ArrayList<String>(){{
            add("node1");
        }};
        when(tenantManage.getTempalteDefinition(userId)).thenReturn(null);
        when(tenantManage.getDefinitionDataStore(userId)).thenReturn(TempalteDefinition);
        when(tenantManage.getDefinitionDataStore(userId)).thenReturn(TempalteDefinition);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Result3 :"+updateNodeLangTest.NodeHandling(userId,nodename,nodetype,subnodes,propertyList));

        //if (tenantManage.getTempalteDefinition(userId)==null)
        //if (tenantManage.getDefinitionDataStore(userId)==null)
        // else if (!nodeModel)
        //if (!subnodes.isEmpty())
        //if (nodeModel)
        //if (!subnodes.isEmpty())
        nodetype = new String("null");
        subnodes = new ArrayList<String>(){{
            add("node1");
        }};
        HashMap<UserId, User> usersMap = new HashMap<UserId, User>();
        UserId userId2 = mock(UserId.class);
        User user2 = mock(User.class);
        usersMap.put(userId2,user2);
        when(tenantManage.getTempalteDefinition(userId)).thenReturn(null);
        when(tenantManage.getDefinitionDataStore(userId)).thenReturn(null);
        when(tenantManage.getUsers()).thenReturn(usersMap);
        when(user2.getUserRole()).thenReturn(new UserRoleName(NEMOConstants.admin));
        //when(user2.getUserRole()).thenReturn(1);
        UserId userId3 = mock(UserId.class);
        when(user2.getUserId()).thenReturn(userId3);
        when(tenantManage.getDefinitionDataStore(userId3)).thenReturn(TempalteDefinition);
        when(user2.getUserId()).thenReturn(userId3);
        when(tenantManage.getDefinitionDataStore(userId3)).thenReturn(TempalteDefinition);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Result4 :"+updateNodeLangTest.NodeHandling(userId,nodename,nodetype,subnodes,propertyList));

        //if (tenantManage.getTempalteDefinition(userId)==null)
        //if (tenantManage.getDefinitionDataStore(userId)==null)
        // else if (!nodeModel)
        //else
        //(!propertyList.isEmpty())
        //if (errorInfo==null)
        //test checkProperty
        //test createNode
        nodetype = new String("null");
        subnodes = new ArrayList<String>(){{
            add("node1");
        }};
        properties = new LinkedHashMap<String,String>(){{
            //put(new String("1"),NEMOConstants.range);
            //put(new String("group"),NEMOConstants.string);
            //put(new String("100"),NEMOConstants.integer);
            put(new String("100,200"),NEMOConstants.range);
            //put(new String("100"),NEMOConstants.integer);
        }};
        field = class1.getDeclaredField("updateNode");
        UpdateNode updateNode = mock(UpdateNode.class);
        field.setAccessible(true);
        field.set(updateNodeLangTest,updateNode);
        propertyList.put(new String("s1"),properties);
        when(tenantManage.getTempalteDefinition(userId)).thenReturn(null);
        when(tenantManage.getDefinitionDataStore(userId)).thenReturn(null);
        when(tenantManage.getUsers()).thenReturn(usersMap);
        when(user2.getUserRole()).thenReturn(new UserRoleName("null"));
        //test createNode()
        HashMap<NodeId,Node> nodeMap = new HashMap<NodeId,Node>();
        NodeId nodeId4 = new NodeId("11111111-1111-1111-1111-111111111111");
        Node node1 = mock(Node.class);
        nodeMap.put(nodeId4, node1);
        when(tenantManage.getObjectId(userId, nodename)).thenReturn("11111111-1111-1111-1111-111111111111");
        when(tenantManage.getObjectId(userId,nodename)).thenReturn("11111111-1111-1111-1111-111111111111");
        when(tenantManage.getNode(userId)).thenReturn(nodeMap);
        when(node1.getNodeId()).thenReturn(new NodeId("11111111-1111-1111-1111-111111111111"));
        //if (!subnodes.isEmpty())
        when(tenantManage.getObjectId(userId,"node1")).thenReturn("11111111-1111-1111-1111-111111111111");
        //if (errorInfo==null && !propertyList.isEmpty())
        field = class1.getDeclaredField("node");
        Node nodemock = mock(Node.class);
        field.setAccessible(true);
        field.set(updateNodeLangTest,nodemock);
        when(updateNode.NodeHandling(userId,nodemock)).thenReturn("string");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Result5 :"+updateNodeLangTest.NodeHandling(userId,nodename,nodetype,subnodes,propertyList));

        //if (tenantManage.getTempalteDefinition(userId)!=null)
        //if (!subnodes.isEmpty())
        //if (nodeModel)
        //if else
        nodetype = new String("null");
        subnodes = new ArrayList<String>();
        field = class1.getDeclaredField("updateTemplateInstanceLang");
        UpdateTemplateInstanceLang updateTemplateInstanceLang = mock(UpdateTemplateInstanceLang.class);
        field.setAccessible(true);
        field.set(updateNodeLangTest,updateTemplateInstanceLang);
        when(tenantManage.getTempalteDefinition(userId)).thenReturn(TempalteDefinition);
        when(tenantManage.getTempalteDefinition(userId)).thenReturn(TempalteDefinition);
        when(updateTemplateInstanceLang.templateInstanceLang(userId,nodename,nodetype,propertyList)).thenReturn("errorinfo");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Result6 :"+updateNodeLangTest.NodeHandling(userId,nodename,nodetype,subnodes,propertyList));


        //if (tenantManage.getTempalteDefinition(userId)==null)
        //if (tenantManage.getDefinitionDataStore(userId)==null)
        // else if (!nodeModel)
        //else
        //(!propertyList.isEmpty())
        //if (errorInfo==null)
        //test checkProperty()
        nodetype = new String("null");
        subnodes = new ArrayList<String>(){{
            add("node1");
        }};
        properties = new LinkedHashMap<String,String>(){{
            put(new String("100,200"),NEMOConstants.range);
            put(new String("100"),NEMOConstants.integer);
        }};
        propertyList.put(new String("s1"),properties);
        when(tenantManage.getTempalteDefinition(userId)).thenReturn(null);
        when(tenantManage.getDefinitionDataStore(userId)).thenReturn(null);
        when(tenantManage.getUsers()).thenReturn(usersMap);
        when(user2.getUserRole()).thenReturn(new UserRoleName("null"));

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Result7 :"+updateNodeLangTest.NodeHandling(userId,nodename,nodetype,subnodes,propertyList));
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
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Result8 "+method.invoke(updateNodeLangTest,propertyList));


        properties = new LinkedHashMap<String,String>(){{
            put(new String("group"),NEMOConstants.string);
            put(new String("100"),NEMOConstants.integer);
            put(new String("100,200"),NEMOConstants.range);
        }};
        propertyList.put(new String("s1"),properties);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Result9 "+method.invoke(updateNodeLangTest,propertyList));
    }

    @org.junit.Test
    public void testcreateNode() throws Exception {
        nodetype = new String(NEMOConstants.host);
        method = class1.getDeclaredMethod("createNode",new Class[]{
                UserId.class,
                String.class,
                String.class,
                List.class,
                LinkedHashMap.class,

        });
        subnodes = new ArrayList<String>(){{
            add("node1");
        }};
        properties = new LinkedHashMap<String,String>(){{
            put(new String("100,200"),NEMOConstants.range);
            put(new String("100,200"),NEMOConstants.range);
        }};
        propertyList.put(new String("s1"),properties);
        method.setAccessible(true);
        field = class1.getDeclaredField("getDefinitions");
        field.setAccessible(true);
        GetDefinitions getDefinitions = mock(GetDefinitions.class);
        field.set(updateNodeLangTest,getDefinitions);
        Map<PhysicalHostName, PhysicalHost> physicalHostMap = new HashMap<PhysicalHostName, PhysicalHost>();
        when(tenantManage.getObjectId(userId,nodename)).thenReturn(null);
        when(getDefinitions.getPhysicalHost()).thenReturn(physicalHostMap);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Result10 "+method.invoke(updateNodeLangTest,userId,nodename,nodetype,subnodes,propertyList));


        subnodes = new ArrayList<String>();
        properties = new LinkedHashMap<String,String>(){{
            put(new String("group"),NEMOConstants.string);
        }};
        propertyList.put(new String("s1"),properties);
        method.setAccessible(true);

        HashMap<NodeId,Node> nodeMap = new HashMap<NodeId,Node>();
        NodeId nodeId4 = new NodeId("11111111-1111-1111-1111-111111111111");
        Node node1 = mock(Node.class);
        nodeMap.put(nodeId4, node1);
        when(tenantManage.getObjectId(userId, nodename)).thenReturn("11111111-1111-1111-1111-111111111111");
        when(tenantManage.getObjectId(userId,nodename)).thenReturn("11111111-1111-1111-1111-111111111111");
        when(tenantManage.getNode(userId)).thenReturn(null);
        when(tenantManage.getNodeDataStore(userId)).thenReturn(nodeMap);
        when(node1.getNodeId()).thenReturn(new NodeId("11111111-1111-1111-1111-111111111111"));
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Result11 "+method.invoke(updateNodeLangTest,userId,nodename,nodetype,subnodes,propertyList));


        nodetype = "error";
        subnodes = new ArrayList<String>();
        properties = new LinkedHashMap<String,String>(){{
            put(new String("100"),NEMOConstants.integer);
        }};
        propertyList.put(new String("s1"),properties);
        method.setAccessible(true);

        when(tenantManage.getObjectId(userId, nodename)).thenReturn("11111111-1111-1111-1111-111111111111");
        when(tenantManage.getObjectId(userId,nodename)).thenReturn("11111111-1111-1111-1111-111111111111");
        when(tenantManage.getNode(userId)).thenReturn(null);
        when(tenantManage.getNodeDataStore(userId)).thenReturn(null);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Result12 "+method.invoke(updateNodeLangTest,userId,nodename,nodetype,subnodes,propertyList));
    }
}