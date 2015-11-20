/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.languagestyle.updateintentlang;

import org.opendaylight.nemo.user.tenantmanager.TenantManage;
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
import org.opendaylight.controller.md.sal.binding.api.DataBroker;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by z00293636 on 2015/11/5.
 */
public class UpdateConnectionLang {
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private Connection connection;
    private UpdateConnection updateConnection;

    public UpdateConnectionLang(DataBroker dataBroker, TenantManage tenantManage)
    {
        this.dataBroker = dataBroker;
        this.tenantManage = tenantManage;
        updateConnection = new UpdateConnection(dataBroker,tenantManage);
    }
    public String ConnectionHandling(UserId userId,String connectionname, String connectiontype, List<String> endnodes, LinkedHashMap<String, LinkedHashMap<String,String>> propertyList)
    {
        String errorInfo = null;
        if (!propertyList.isEmpty())
        {
            errorInfo = checkProperty(propertyList);
        }
        if (errorInfo==null)
        {
            errorInfo = createConnection(userId,connectionname,connectiontype,endnodes,propertyList);
        }
        if (errorInfo==null)
        {
            errorInfo = updateConnection.ConnectionHandling(userId,this.connection);
        }
        return errorInfo;
    }
    private String checkProperty(LinkedHashMap<String, LinkedHashMap<String,String>> propertyList)
    {
        String errorInfo = null;

        for (String propertyname : propertyList.keySet())
        {
            List<String> valuetype = new ArrayList<String>();
            Boolean typeRight = true;
            LinkedHashMap<String,String> values = propertyList.get(propertyname);
            for (String value : values.keySet())
            {
                valuetype.add(values.get(value));
            }
            for (String type : valuetype)
            {
                if (!type.equals(valuetype.get(0)))
                {
                    typeRight = false;
                }
                if (valuetype.get(0).equals("range")&&valuetype.size()>1)
                {
                    errorInfo = "One property can just have one range value.";
                    return errorInfo;
                }
            }
            if (!typeRight)
            {
                errorInfo = "The value type is not consistent.";
            }
        }

        return errorInfo;
    }

    private String createConnection(UserId userId, String connectionname, String connectiontype, List<String> endnodes, LinkedHashMap<String, LinkedHashMap<String,String>> propertyList)
    {
        String errorInfo = null;
        tenantManage.fetchVNSpace(userId);
        User user = tenantManage.getUser();
        List<Connection> connectionList = new ArrayList<Connection>();
        List<Node> nodeList = new ArrayList<Node>();
        Boolean ConnExist = false;

        if (user.getObjects()!=null)
        {
            if (user.getObjects().getNode()!=null)
            {
                nodeList = user.getObjects().getNode();
            }
            if (user.getObjects().getConnection()!=null)
            {
                connectionList = user.getObjects().getConnection();
            }
        }
        ConnectionBuilder connectionBuilder = new ConnectionBuilder();
        if (!connectionList.isEmpty())
        {
            for (Connection connection1:connectionList)
            {
                if (connection1.getConnectionName().getValue().equals(connectionname))
                {
                    ConnExist = true;
                    connectionBuilder.setKey(connection1.getKey());
                    connectionBuilder.setConnectionId(connection1.getConnectionId());
                }
            }
        }
        if (!ConnExist)
        {
            ConnectionId connectionId = new ConnectionId(UUID.randomUUID().toString());
            connectionBuilder.setKey(new ConnectionKey(connectionId));
            connectionBuilder.setConnectionId(connectionId);
        }

        connectionBuilder.setConnectionName(new ConnectionName(connectionname));
        connectionBuilder.setConnectionType(new ConnectionType(connectiontype));

        if (!endnodes.isEmpty() && errorInfo==null)
        {
            List<EndNode> endNodeList = new ArrayList<EndNode>();

            if (nodeList.isEmpty())
            {
                errorInfo = "The EndNode is not exist in the user vn space.";
                return errorInfo;
            }
            else
            {
                int order = 0;
                for (String endnode : endnodes)
                {
                    Boolean endnodeexist = false;
                    for (Node node : nodeList) {
                        if (node.getNodeName().getValue().equals(endnode))
                        {
                            endnodeexist = true;
                            EndNodeBuilder endNodeBuilder = new EndNodeBuilder();
                            endNodeBuilder.setKey(new EndNodeKey(node.getNodeId()));
                            endNodeBuilder.setNodeId(node.getNodeId());
                            endNodeBuilder.setOrder((long) order);
                            endNodeList.add(endNodeBuilder.build());
                        }
                    }
                    if (!endnodeexist) {
                        errorInfo = "The EndNode is not exist in the user vn space.";
                        return errorInfo;
                    }
                    order ++;
                }
            }
            connectionBuilder.setEndNode(endNodeList);
        }

        if (!propertyList.isEmpty() && errorInfo==null)
        {
            PropertyBuilder propertyBuilder = new PropertyBuilder();
            List<Property> connectionproperty = new ArrayList<Property>();
            for (String propertyname : propertyList.keySet())
            {
                propertyBuilder.setKey(new PropertyKey(new PropertyName(propertyname)));
                propertyBuilder.setPropertyName(new PropertyName(propertyname));

                PropertyValuesBuilder propertyValuesBuilder = new PropertyValuesBuilder();
                LinkedHashMap<String, String> valuetype = propertyList.get(propertyname);
                List<IntValue> intValueList = new ArrayList<IntValue>();
                List<StringValue> stringValueList = new ArrayList<StringValue>();
                RangeValue Rangevalue = null;
                int order = 0;
                for (String value : valuetype.keySet())
                {
                    if (valuetype.get(value).equals("string"))
                    {
                        StringValueBuilder stringValueBuilder = new StringValueBuilder();
                        stringValueBuilder.setKey(new StringValueKey((long)order,value));
                        stringValueBuilder.setValue(value);
                        stringValueBuilder.setOrder((long)order);
                        stringValueList.add(stringValueBuilder.build());
                    }
                    if (valuetype.get(value).equals("int"))
                    {
                        IntValueBuilder intValueBuilder = new IntValueBuilder();
                        intValueBuilder.setKey(new IntValueKey((long)order,Long.parseLong(value)));
                        intValueBuilder.setValue(Long.parseLong(value));
                        intValueBuilder.setOrder((long)order);
                        intValueList.add(intValueBuilder.build());
                    }
                    if (valuetype.get(value).equals("range"))
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
                        Rangevalue = rangeValueBuilder.build();
                        propertyValuesBuilder.setRangeValue(Rangevalue);
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
                propertyValuesBuilder.setIntValue(intValueList).setStringValue(stringValueList).setRangeValue(Rangevalue);
                propertyBuilder.setPropertyValues(propertyValuesBuilder.build());
                connectionproperty.add(propertyBuilder.build());
            }
            connectionBuilder.setProperty(connectionproperty);
        }
        setConnection(connectionBuilder.build());
        return errorInfo;
    }

    private void setConnection(Connection connection)
    {
        this.connection = connection;
    }
}
