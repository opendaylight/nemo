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
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateConnection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.ConnectionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.ConnectionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValuesBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.*;

import java.util.*;

/**
 * Created by z00293636 on 2015/11/5.
 */
public class UpdateConnectionLang {
    private TenantManage tenantManage;
    private Connection connection;
    private UpdateConnection updateConnection;

    public UpdateConnectionLang(DataBroker dataBroker, TenantManage tenantManage){
        this.tenantManage = tenantManage;
        updateConnection = new UpdateConnection(dataBroker,tenantManage);
    }

    public String ConnectionHandling(UserId userId,String connectionname, String connectiontype, List<String> endnodes, LinkedHashMap<String, LinkedHashMap<String,String>> propertyList){
        String errorInfo = null;
        if (!propertyList.isEmpty()){
            errorInfo = checkProperty(propertyList);
        }
        if (errorInfo==null){
            errorInfo = createConnection(userId,connectionname,connectiontype,endnodes,propertyList);
            if (errorInfo == null){
                return updateConnection.ConnectionHandling(userId,this.connection);
            }
        }
        return errorInfo;
    }

    private String checkProperty(LinkedHashMap<String, LinkedHashMap<String,String>> propertyList){
        String errorInfo = null;
        for (String propertyName : propertyList.keySet()){
            LinkedHashMap<String, String> propertyValues = propertyList.get(propertyName);
            Iterator<String> iterator = propertyValues.values().iterator();
            String valueType = iterator.next();

            while (iterator.hasNext()){
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

    private String createConnection(UserId userId, String connectionname, String connectiontype, List<String> endnodes, LinkedHashMap<String, LinkedHashMap<String,String>> propertyList){
        String errorInfo = null;
        ConnectionBuilder connectionBuilder = new ConnectionBuilder();
        Connection connection1 = null;
        if (tenantManage.getObjectId(userId,connectionname)!=null){
            ConnectionId connectionId = new ConnectionId(tenantManage.getObjectId(userId,connectionname));
            if (tenantManage.getConnection(userId).containsKey(connectionId)){
                connection1 = tenantManage.getConnection(userId).get(connectionId);
            }
            else if (tenantManage.getConnectionDataStore(userId).containsKey(connectionId)){
                connection1 = tenantManage.getConnectionDataStore(userId).get(connectionId);
            }
        }

        if (connection1==null){
            ConnectionId connectionId = new ConnectionId(UUID.randomUUID().toString());
            connectionBuilder.setKey(new ConnectionKey(connectionId))
                             .setConnectionId(connectionId);
        }
        else {
            connectionBuilder.setKey(connection1.getKey())
                             .setConnectionId(connection1.getConnectionId());
        }

        connectionBuilder.setConnectionName(new ConnectionName(connectionname))
                         .setConnectionType(new ConnectionType(connectiontype));

        if (!endnodes.isEmpty()){
            List<EndNode> endNodeList = new ArrayList<EndNode>();
            for (String nodeName : endnodes){
                if (tenantManage.getObjectId(userId,nodeName)!=null){
                    Long order = 0L;
                    NodeId nodeId = new NodeId(tenantManage.getObjectId(userId,nodeName));
                    EndNodeBuilder endNodeBuilder = new EndNodeBuilder();
                    endNodeBuilder.setKey(new EndNodeKey(nodeId))
                                  .setNodeId(nodeId)
                                  .setOrder(order);
                    order++;
                    endNodeList.add(endNodeBuilder.build());
                }
                else {
                    return "The end node " + nodeName + " is not exist.";
                }
            }
            connectionBuilder.setEndNode(endNodeList);
        }

        if (!propertyList.isEmpty() && errorInfo==null){
            PropertyBuilder propertyBuilder = new PropertyBuilder();
            List<Property> connectionproperty = new ArrayList<Property>();
            for (String propertyname : propertyList.keySet()){
                propertyBuilder.setKey(new PropertyKey(new PropertyName(propertyname)));
                propertyBuilder.setPropertyName(new PropertyName(propertyname));

                PropertyValuesBuilder propertyValuesBuilder = new PropertyValuesBuilder();
                LinkedHashMap<String, String> valuetype = propertyList.get(propertyname);
                List<IntValue> intValueList = new ArrayList<IntValue>();
                List<StringValue> stringValueList = new ArrayList<StringValue>();
                RangeValue Rangevalue = null;
                int order = 0;
                for (String value : valuetype.keySet()){
                    if (valuetype.get(value).equals(NEMOConstants.string)){
                        StringValueBuilder stringValueBuilder = new StringValueBuilder();
                        stringValueBuilder.setKey(new StringValueKey((long)order,value));
                        stringValueBuilder.setValue(value);
                        stringValueBuilder.setOrder((long)order);
                        stringValueList.add(stringValueBuilder.build());
                    }
                    if (valuetype.get(value).equals(NEMOConstants.integer)){
                        IntValueBuilder intValueBuilder = new IntValueBuilder();
                        intValueBuilder.setKey(new IntValueKey((long)order,Long.parseLong(value)));
                        intValueBuilder.setValue(Long.parseLong(value));
                        intValueBuilder.setOrder((long)order);
                        intValueList.add(intValueBuilder.build());
                    }
                    if (valuetype.get(value).equals(NEMOConstants.range)){
                        String[] rangevalue = new String[2];
                        rangevalue = value.split(",");
                        RangeValueBuilder rangeValueBuilder = new RangeValueBuilder();
                        if (Long.parseLong(rangevalue[0])<Long.parseLong(rangevalue[1])){
                            rangeValueBuilder.setMin(Long.parseLong(rangevalue[0]));
                            rangeValueBuilder.setMax(Long.parseLong(rangevalue[1]));
                        }
                        else{
                            rangeValueBuilder.setMin(Long.parseLong(rangevalue[1]));
                            rangeValueBuilder.setMax(Long.parseLong(rangevalue[0]));
                        }
                        Rangevalue = rangeValueBuilder.build();
                        propertyValuesBuilder.setRangeValue(Rangevalue);
                    }
                    order++;
                }
                if (intValueList.isEmpty()){
                    intValueList = null;
                    propertyValuesBuilder.setIntValue(intValueList);
                }
                if (stringValueList.isEmpty()){
                    stringValueList = null;
                    propertyValuesBuilder.setStringValue(stringValueList);
                }
                propertyValuesBuilder.setIntValue(intValueList).setStringValue(stringValueList).setRangeValue(Rangevalue);
                propertyBuilder.setPropertyValues(propertyValuesBuilder.build());
                connectionproperty.add(propertyBuilder.build());
            }
            connectionBuilder.setProperty(connectionproperty);
        }
        this.connection = connectionBuilder.build();
        return errorInfo;
    }
}
