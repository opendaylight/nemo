/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.ConnectionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.ConnectionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.ConnectionDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.definitions.ConnectionDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.EndNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.definitions.PropertyDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by z00293636 on 2015/8/31.
 */
public class UpdateConnection {

    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private final SettableFuture<List<ConnectionDefinition>> connectionDefinitionListFuture = SettableFuture.create();
    private static final Logger LOG = LoggerFactory.getLogger(UpdateConnection.class);

    public UpdateConnection(DataBroker dataBroker, TenantManage tenantManage){
        this.dataBroker = dataBroker;
        this.tenantManage = tenantManage;
    }

    public String ConnectionHandling(UserId userId, Connection connection){
        String errorInfo = null;
        errorInfo =checkDefinition(connection);
        if (errorInfo != null){
            return errorInfo;
        }
        else {
            errorInfo = checkInstance(userId,connection);
            if (errorInfo != null){
                return errorInfo;
            }
            else {
                WriteTransaction t = dataBroker.newWriteOnlyTransaction();
                if (userId != null && connection != null){
                    Connection connection1 = new ConnectionBuilder(connection).build();
                    ConnectionKey connectionKey = new ConnectionKey(connection.getConnectionId());

                    UserKey userKey = new UserKey(userId);

                    InstanceIdentifier<Connection> connectionid = InstanceIdentifier.builder(Users.class).child(User.class, userKey).child(Objects.class).child(Connection.class,connectionKey).build();
                    t.put(LogicalDatastoreType.CONFIGURATION, connectionid, connection1,true);
                    CheckedFuture<Void, TransactionCommitFailedException> f = t.submit();
                    Futures.addCallback(f, new FutureCallback<Void>() {
                        @Override
                        public void onFailure(Throwable t) {
                            LOG.error("Could not write endpoint base container", t);
                        }

                        @Override
                        public void onSuccess(Void result) {
                        }
                    });
                }
            }
        }
        return errorInfo;
    }

    private String checkInstance(UserId userId, Connection connection){
        String errorInfo = null;
        tenantManage.fetchVNSpace(userId);
        User user = tenantManage.getUser();

        if (user!=null){
            if (user.getObjects()!=null){
                if (user.getObjects().getConnection()!=null){
                    List<Connection> connectionList = tenantManage.getUser().getObjects().getConnection();

                    for (Connection connection1 : connectionList){
                        if (connection1.getConnectionId().equals(connection.getConnectionId())){
                            if (!connection1.getConnectionType().equals(connection.getConnectionType())){
                                errorInfo = "The connection type should not be changed.";
                                break;
                            }
                            if (!connection1.getConnectionName().equals(connection.getConnectionName())){
                                errorInfo = "The End node should not be changed.";
                                break;
                            }

                        }
                    }
                }
                if (connection.getEndNode()!=null){
                    if (user.getObjects().getNode()!=null){
                        List<EndNode> endnodeList = connection.getEndNode();
                        List<Node> nodeList = user.getObjects().getNode();
                        Boolean nodeExist = false;

                        for (EndNode endNode : endnodeList){
                            for (Node node : nodeList){
                                if (endNode.getNodeId().equals(node.getNodeId())){
                                    nodeExist = true;
                                }
                            }
                            if (!nodeExist){
                                errorInfo = "The endnode is not exist in user vn space.";
                            }
                        }
                    }
                    else
                    {
                        errorInfo = "The endnodes are not exist in the user vn space.";
                    }
                }
            }
            else {
                if (connection.getEndNode() != null){
                    errorInfo = "There are no nodes in user vn space.";
                }
            }
        }
        else {
            if (connection.getEndNode() != null){
                errorInfo = "There are no nodes in user vn space.";
            }
        }
        return errorInfo;
    }

    private String checkDefinition(Connection connection){
        String errorInfo = null;
        fetchConnectionDefinitionList();
        Map<ConnectionType, ConnectionDefinition> connectionDefinitionMap = getConnectionDefinition();
        if (connectionDefinitionMap.isEmpty()){
            return "This type of connection has not been defined.";
        }
        else {
            if (connectionDefinitionMap.containsKey(connection.getConnectionType())){
                List<Property> connectionProperties = connection.getProperty();
                List<PropertyDefinition> propertyDefinitions = connectionDefinitionMap.get(connection.getConnectionType()).getPropertyDefinition();
                if (connectionProperties != null && propertyDefinitions ==null)
                {
                    errorInfo = "There are no properties for this type of connection.";
                }
                else if (connectionProperties != null && propertyDefinitions != null)
                {
                    errorInfo = CheckProperty(connection.getProperty(), propertyDefinitions);
                }
            }
            else {
                return "This type of connection has not been defined.";
            }
        }
        return errorInfo;
    }

    private String CheckProperty(List<Property> connectionProperty, List<PropertyDefinition> propertyDefinitionList ){
        String errorInfo = null;
        for (Property property : connectionProperty)
        {
            Boolean properyHasDefine = false;
            if (errorInfo != null)
            {
                break;
            }
            else
            {
                for (PropertyDefinition propertyDefinition : propertyDefinitionList)
                {
                    if (property.getPropertyName().equals(propertyDefinition.getPropertyName()))
                    {
                        properyHasDefine = true;
                        PropertyValues propertyValues = property.getPropertyValues();
                        PropertyDefinition.PropertyValueType propertyValueType = propertyDefinition.getPropertyValueType();

                        if (propertyValues != null && propertyValueType != null)
                        {
                            if (propertyDefinition.getIsReadOnly()!=null
                                    && PropertyDefinition.IsReadOnly.ReadOnly == propertyDefinition.getIsReadOnly())
                            {
                                if (propertyDefinition.getIsRequired().getIntValue() == 1)
                                {
                                    errorInfo = "The property value type of " + property.getPropertyName().toString() + " is read only.";
                                    break;
                                }
                            }
                            else
                            {
                                if (propertyValueType.getIntValue() == 0 && !(propertyValues.getIntValue() == null && propertyValues.getStringValue() != null && propertyValues.getRangeValue() == null)) {
                                    errorInfo = "The property value type of " + property.getPropertyName().toString() + " should be string.";
                                    break;
                                }
                                if (propertyValueType.getIntValue() == 1 && !(propertyValues.getIntValue() != null && propertyValues.getStringValue() == null && propertyValues.getRangeValue() == null)) {
                                    errorInfo = "The property value type of" + property.getPropertyName().toString() + " should be integer.";
                                    break;
                                }
                                if (propertyValueType.getIntValue() == 2 && !(propertyValues.getIntValue() == null && propertyValues.getStringValue() == null && propertyValues.getRangeValue() != null)) {
                                    errorInfo = "The property value type of" + property.getPropertyName().toString() + " should be range.";
                                    break;
                                }
                            }
                        }
                    }
                }
                if (!properyHasDefine) {
                    errorInfo = "This type of property" + property.getPropertyName().toString() + " has not been defined.";
                }
            }
        }

        if (errorInfo == null)
        {
            Boolean requiredProperty = false;
            for (PropertyDefinition propertyDefinition : propertyDefinitionList)
            {
                if (propertyDefinition.getIsRequired()!=null)
                {
                    if (propertyDefinition.getIsRequired().getIntValue() == 0)
                    {
                        for (Property property : connectionProperty)
                        {
                            if (property.getPropertyName().equals(propertyDefinition.getPropertyName()))
                            {
                                requiredProperty = true;
                            }
                        }
                        if (!requiredProperty)
                        {
                            errorInfo = "The required property" + propertyDefinition.getPropertyName().toString() + "is not included in the intent.";
                        }
                    }
                }
            }
        }
        return errorInfo;
    }

    private void fetchConnectionDefinitionList(){
        InstanceIdentifier<ConnectionDefinitions> connectiondefinitionId = InstanceIdentifier.builder(ConnectionDefinitions.class).build();
        ListenableFuture<Optional<ConnectionDefinitions>> connectiondefinitionFuture = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.CONFIGURATION, connectiondefinitionId);
        Futures.addCallback(connectiondefinitionFuture, new FutureCallback<Optional<ConnectionDefinitions>>() {
            @Override
            public void onSuccess(Optional<ConnectionDefinitions> result) {
                setConnectionDefinitionListFuture(result.get().getConnectionDefinition());

            }

            @Override
            public void onFailure(Throwable t) {
                LOG.error("Can not read connection definition information.", t);
            }
        });
        return;
    }

    private void setConnectionDefinitionListFuture(List<ConnectionDefinition> connectionDefinitionList){
        this.connectionDefinitionListFuture.set(connectionDefinitionList);
    }

    private List<ConnectionDefinition> getConnectionDefinitionList(){
        try{
            return connectionDefinitionListFuture.get(1, TimeUnit.SECONDS);
        }catch (InterruptedException | ExecutionException | TimeoutException e) {
            LOG.error("Cannot read role information.", e);
            return null;
        }
    }

    private Map<ConnectionType, ConnectionDefinition> getConnectionDefinition(){
        List<ConnectionDefinition> connectionDefinitionList = getConnectionDefinitionList();
        Map<ConnectionType, ConnectionDefinition> connectionDefinitionMap = new HashMap<ConnectionType, ConnectionDefinition>();
        if (connectionDefinitionList!=null){
            for (ConnectionDefinition connectionDefinition : connectionDefinitionList){
                connectionDefinitionMap.put(connectionDefinition.getConnectionType(),connectionDefinition);
            }
        }
        return connectionDefinitionMap;
    }
}
