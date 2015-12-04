/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.languagestyle.updateintentlang;

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

import java.util.*;

/**
 * Created by z00293636 on 2015/10/31.
 */
public class UpdateNodeLang {
    private TenantManage tenantManage;
    private UpdateNode updateNode;
    private Node node;

    public UpdateNodeLang(DataBroker dataBroker, TenantManage tenantManage){
        this.tenantManage = tenantManage;
        updateNode = new UpdateNode(dataBroker,tenantManage);
    }

    public String NodeHandling(UserId userId,String nodename, String nodetype, List<String> subnodes, LinkedHashMap<String, LinkedHashMap<String,String>> propertyList){
        String errorInfo = null;
        if (nodetype.equals(NEMOConstants.host)&&!subnodes.isEmpty()){
            errorInfo = "the node "+nodename+ " is a " + NEMOConstants.host +", it could not contain other nodes.";
            return errorInfo;
        }
        if (!propertyList.isEmpty()){
            errorInfo = checkProperty(propertyList);
        }
        if (errorInfo==null){
            errorInfo = createNode(userId, nodename, nodetype, subnodes, propertyList);
            if (errorInfo == null){
                return updateNode.NodeHandling(userId,this.node);
            }
        }
        return errorInfo;
    }

    private String checkProperty(LinkedHashMap<String, LinkedHashMap<String,String>> propertyList){
        String errorInfo = null;
       for (String propertyName : propertyList.keySet()) {
           LinkedHashMap<String, String> propertyValues = propertyList.get(propertyName);
           Iterator<String> iterator = propertyValues.values().iterator();
           String valueType = iterator.next();

           while (iterator.hasNext()) {
               if (valueType.equals(NEMOConstants.range)) {
                   errorInfo = "The property should just has one range value.";
                   return errorInfo;
               }
               else if (valueType.equals(NEMOConstants.string)){
                   if (!iterator.next().equals(NEMOConstants.string)){
                       return "The property " + propertyValues.get(iterator.next()) + " should be string.";
                   }
               }
               else if (valueType.equals(NEMOConstants.integer)){
                   if (!iterator.next().equals(NEMOConstants.integer)){
                       return "The property " + propertyValues.get(iterator.next()) + " should be int.";
                   }
               }
           }
       }
        return errorInfo;
    }

    private String createNode(UserId userId, String nodename, String nodetype, List<String> subnodes, LinkedHashMap<String, LinkedHashMap<String,String>> propertyList){
        String errorInfo = null;
        tenantManage.fetchVNSpace(userId);
        User user =tenantManage.getUser();
        List<Node> nodeList = new ArrayList<Node>();
        if (user.getObjects()!=null)
        {
            if (user.getObjects().getNode()!=null)
            {
                nodeList = user.getObjects().getNode();
            }
        }
        NodeBuilder nodeBuilder = new NodeBuilder();
        Boolean NodeExist = false;
        if (!nodeList.isEmpty())
        {
            for (Node node1:nodeList)
            {
                if (node1.getNodeName().getValue().equals(nodename))
                {
                    NodeExist = true;
                    nodeBuilder.setKey(node1.getKey());
                    nodeBuilder.setNodeId(node1.getNodeId());
                }
            }
        }
        if (!NodeExist)
        {
            NodeId nodeId = new NodeId(UUID.randomUUID().toString());
            nodeBuilder.setKey(new NodeKey(nodeId));
            nodeBuilder.setNodeId(nodeId);
        }

        nodeBuilder.setNodeName(new NodeName(nodename));
        nodeBuilder.setNodeType(new NodeType(nodetype));

        List<SubNode> subNodeList = new ArrayList<SubNode>();
        Boolean subnodeexist = false;
        if (errorInfo==null && !subnodes.isEmpty())
        {
            for (String subnodename : subnodes)
            {
                int order = 0;
                if (!nodeList.isEmpty())
                {
                    for (Node node : nodeList)
                    {
                        if (node.getNodeName().getValue().equals(subnodename))
                        {
                            subnodeexist = true;
                            SubNodeBuilder subNodeBuilder = new SubNodeBuilder();
                            subNodeBuilder.setKey(new SubNodeKey(node.getNodeId()));
                            subNodeBuilder.setNodeId(node.getNodeId());
                            subNodeBuilder.setOrder((long)order);
                            subNodeList.add(subNodeBuilder.build());
                        }
                    }
                    if (!subnodeexist)
                    {
                        errorInfo = "The subnode is not exist.";
                        return errorInfo;
                    }
                }
                else
                {
                    errorInfo = "The subnode is not exist.";
                    return errorInfo;
                }
                order ++;
            }
            nodeBuilder.setSubNode(subNodeList);
        }

        if (errorInfo==null && !propertyList.isEmpty())
        {
            PropertyBuilder propertyBuilder = new PropertyBuilder();
            List<Property> nodeproperty = new ArrayList<Property>();
            for (String propertyname : propertyList.keySet())
            {
                propertyBuilder.setKey(new PropertyKey(new PropertyName(propertyname)));
                propertyBuilder.setPropertyName(new PropertyName(propertyname));

                PropertyValuesBuilder propertyValuesBuilder = new PropertyValuesBuilder();
                LinkedHashMap<String, String> valuetype = propertyList.get(propertyname);
                List<IntValue> intValueList = new ArrayList<IntValue>();
                List<StringValue> stringValueList = new ArrayList<StringValue>();
                RangeValue RangeValue = null;

                int order = 0;
                for (String value : valuetype.keySet())
                {
//                    if ((valuetype.get(value).equals(NEMOConstants.string))||valuetype.get(value).equals(NEMOConstants.ipv4addr)||valuetype.get(value).equals(NEMOConstants.ipv4pref)
//                        ||valuetype.get(value).equals(NEMOConstants.ethaddr))
                    if (valuetype.get(value).equals(NEMOConstants.string))
                    {
                        StringValueBuilder stringValueBuilder = new StringValueBuilder();
                        stringValueBuilder.setKey(new StringValueKey((long)order,value));
                        stringValueBuilder.setValue(value);
                        stringValueBuilder.setOrder((long)order);
                        stringValueList.add(stringValueBuilder.build());
                    }
                    if (valuetype.get(value).equals(NEMOConstants.integer))
                    {
                        IntValueBuilder intValueBuilder = new IntValueBuilder();
                        intValueBuilder.setKey(new IntValueKey((long)order,Long.parseLong(value)));
                        intValueBuilder.setValue(Long.parseLong(value));
                        intValueBuilder.setOrder((long)order);
                        intValueList.add(intValueBuilder.build());
                    }
                    if (valuetype.get(value).equals(NEMOConstants.range))
                    {
                        String[] rangevalue = new String[2];
                        rangevalue = value.split(",");
                        RangeValueBuilder rangeValueBuilder = new RangeValueBuilder();
                        if (Long.parseLong(rangevalue[0])<Long.parseLong(rangevalue[1]))
                        {
                            rangeValueBuilder.setMin(Long.parseLong(rangevalue[0]));
                            rangeValueBuilder.setMax(Long.parseLong(rangevalue[1]));
                        }
                       else
                        {
                            rangeValueBuilder.setMin(Long.parseLong(rangevalue[1]));
                            rangeValueBuilder.setMax(Long.parseLong(rangevalue[0]));
                        }
                        RangeValue = rangeValueBuilder.build();
                        propertyValuesBuilder.setRangeValue(RangeValue);
                    }
                    order++;
                }
                if (intValueList.isEmpty())
                {
                    intValueList = null;
                    propertyValuesBuilder.setIntValue(intValueList);
                }
                if (stringValueList.isEmpty())
                {
                    stringValueList = null;
                    propertyValuesBuilder.setStringValue(stringValueList);
                }

                propertyValuesBuilder.setIntValue(intValueList).setStringValue(stringValueList).setRangeValue(RangeValue);
                propertyBuilder.setPropertyValues(propertyValuesBuilder.build());
                nodeproperty.add(propertyBuilder.build());
            }
            nodeBuilder.setProperty(nodeproperty);
        }
        this.node = nodeBuilder.build();
        return errorInfo;
    }
}
