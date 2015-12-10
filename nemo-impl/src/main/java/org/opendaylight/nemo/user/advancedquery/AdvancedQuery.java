/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.advancedquery;

import org.opendaylight.nemo.user.tenantmanager.AAA;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.AdvancedNemoQueryInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.advanced.nemo.query.input.QueryCondition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Operations;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Results;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.MatchItem;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.IntValue;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by z00293636 on 2015/8/29.
 */
public class AdvancedQuery
{
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private QueryDefinitionCheck queryDefinitionCheck;
    private Objects objects;
    private Operations operations;
    private List<Results> resultsList;
    private static final Logger LOG = LoggerFactory.getLogger(QueryDefinitionCheck.class);

    public AdvancedQuery(DataBroker dataBroker, TenantManage tenantManage)
    {
        this.dataBroker = dataBroker;
        this.tenantManage = tenantManage;
        queryDefinitionCheck = new QueryDefinitionCheck(dataBroker);
        objects = null;
        operations = null;
        resultsList = null;
    }

    public String advancedQuery(AAA aaa, AdvancedNemoQueryInput input)
    {
        String errorInfo = null;
        List<QueryCondition> queryConditionList = null;

        errorInfo = aaa.checkUser(input.getUserId());

        if (errorInfo != null)
        {
            return errorInfo;
        }
        else
        {
            if (input.getQueryCondition() != null)
            {
                queryConditionList = input.getQueryCondition();
                for (QueryCondition queryCondition : queryConditionList)
                {
                    errorInfo = queryDefinitionCheck.CheckQueryDefinition(queryCondition);
                    if (errorInfo != null)
                    {
                        break;
                    }
                }
            }
        }
        return errorInfo;
    }

    public String getAdvancedQueryReuslt(AdvancedNemoQueryInput advancedNemoQueryInput)
    {
        UserId userId = advancedNemoQueryInput.getUserId();
        List<QueryCondition> queryConditionList = advancedNemoQueryInput.getQueryCondition();
        String queryResult = null;

        for (QueryCondition queryCondition : queryConditionList)
        {
            if (queryCondition.getQueryIntentType() != null)
            {
                if (queryCondition.getQueryIntentType().getIntValue() == 0)
                {
                    queryResult += nodeInstanceQuery(userId, queryCondition);
                }
                if (queryCondition.getQueryIntentType().getIntValue() == 1)
                {
                    queryResult += connectionInstanceQuery(userId,queryCondition);
                }
                if (queryCondition.getQueryIntentType().getIntValue() == 2)
                {
                    queryResult += flowInstanceQuery(userId,queryCondition);
                }
                if (queryCondition.getQueryIntentType().getIntValue() == 3)
                {
                    queryResult += operationsInstanceQuery(userId,queryCondition);
                }
            }
        }

        return queryResult;
    }

    private String nodeInstanceQuery(UserId userId, QueryCondition queryCondition)
    {
        String queryResult = null;

        fetchObjectsInstance(userId);

        if (objects != null)
        {
            if (objects.getNode() != null)
            {
                List<Node> nodeList = objects.getNode();

                for (Node node : nodeList)
                {
                    if (node.getProperty() != null)
                    {
                        for(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property property : node.getProperty())
                        {
                            if (property.getPropertyName().equals(queryCondition.getQueryConditionName()))
                            {
                                int operator = queryCondition.getQueryConditionMatchPattern().getIntValue();
                                List<IntValue> values =property.getPropertyValues().getIntValue();
                                if (operator == 0 && (property.getPropertyValues().getIntValue() != null))
                                {
                                    for(IntValue value:values)
                                    {
                                        if (value.getValue()<queryCondition.getQueryConditionTargetValue().getIntValue())
                                        {
                                            queryResult += node.getNodeId().toString()+node.getNodeName().toString()+node.getNodeType().toString()+node.getSubNode().toString()+node.getProperty().toString();
                                        }
                                    }
                                }
                                if (operator == 1 && (property.getPropertyValues().getIntValue() != null))
                                {
                                    for(IntValue value:values)
                                    {
                                        if (value.getValue()<=queryCondition.getQueryConditionTargetValue().getIntValue())
                                        {
                                            queryResult += node.getNodeId().toString()+node.getNodeName().toString()+node.getNodeType().toString()+node.getSubNode().toString()+node.getProperty().toString();
                                        }
                                    }
                                }
                                if (operator == 2 && (property.getPropertyValues().getIntValue() != null))
                                {
                                    for(IntValue value:values)
                                    {
                                        if (value.getValue()==queryCondition.getQueryConditionTargetValue().getIntValue())
                                        {
                                            queryResult += node.getNodeId().toString()+node.getNodeName().toString()+node.getNodeType().toString()+node.getSubNode().toString()+node.getProperty().toString();
                                        }
                                    }
                                }
                                if (operator == 3 && (property.getPropertyValues().getIntValue() != null))
                                {
                                    for(IntValue value:values)
                                    {
                                        if (value.getValue()!=queryCondition.getQueryConditionTargetValue().getIntValue())
                                        {
                                            queryResult += node.getNodeId().toString()+node.getNodeName().toString()+node.getNodeType().toString()+node.getSubNode().toString()+node.getProperty().toString();
                                        }
                                    }
                                }
                                if (operator == 4 && (property.getPropertyValues().getIntValue() != null))
                                {
                                    for(IntValue value:values)
                                    {
                                        if (value.getValue()>queryCondition.getQueryConditionTargetValue().getIntValue())
                                        {
                                            queryResult += node.getNodeId().toString()+node.getNodeName().toString()+node.getNodeType().toString()+node.getSubNode().toString()+node.getProperty().toString();
                                        }
                                    }
                                }
                                if (operator == 5 && (property.getPropertyValues().getIntValue() != null))
                                {
                                    for(IntValue value:values)
                                    {
                                        if (value.getValue()>=queryCondition.getQueryConditionTargetValue().getIntValue())
                                        {
                                            queryResult += node.getNodeId().toString()+node.getNodeName().toString()+node.getNodeType().toString()+node.getSubNode().toString()+node.getProperty().toString();
                                        }
                                    }
                                }
                                if (operator == 6 && (property.getPropertyValues().getIntValue() != null))
                                {
                                    //between todo
                                }
                            }
                        }
                    }

            }
        }

        }

        return queryResult;
    }

    private String connectionInstanceQuery(UserId userId, QueryCondition queryCondition)
    {
        String queryResult = null;

        fetchObjectsInstance(userId);
        if (objects != null)
        {
            if (objects.getConnection() != null)
            {
                List<Connection> connectionList = objects.getConnection();

                for (Connection connection : connectionList)
                {
                    if (connection.getProperty() != null)
                    {
                        for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property property : connection.getProperty())
                        {
                            if (property.getPropertyName().equals(queryCondition.getQueryConditionName()))
                            {
                                int operator = queryCondition.getQueryConditionMatchPattern().getIntValue();
                                List<IntValue> values =property.getPropertyValues().getIntValue();
                                if (operator == 0 && (property.getPropertyValues().getIntValue() != null))
                                {
                                    for(IntValue value:values)
                                    {
                                        if (value.getValue()<queryCondition.getQueryConditionTargetValue().getIntValue())
                                        {
                                            queryResult += connection.getConnectionId().toString()+ connection.getConnectionName().toString()+connection.getConnectionType().toString()+connection.getEndNode().toString()+connection.getProperty().toString();
                                        }
                                    }
                                }
                                if (operator == 1 && (property.getPropertyValues().getIntValue() != null))
                                {
                                    for(IntValue value:values)
                                    {
                                        if (value.getValue()<=queryCondition.getQueryConditionTargetValue().getIntValue())
                                        {
                                            queryResult += connection.getConnectionId().toString()+ connection.getConnectionName().toString()+connection.getConnectionType().toString()+connection.getEndNode().toString()+connection.getProperty().toString();
                                        }
                                    }
                                }
                                if (operator == 2 && (property.getPropertyValues().getIntValue() != null))
                                {
                                    for(IntValue value:values)
                                    {
                                        if (value.getValue()==queryCondition.getQueryConditionTargetValue().getIntValue())
                                        {
                                            queryResult += connection.getConnectionId().toString()+ connection.getConnectionName().toString()+connection.getConnectionType().toString()+connection.getEndNode().toString()+connection.getProperty().toString();
                                        }
                                    }
                                }
                                if (operator == 3 && (property.getPropertyValues().getIntValue() != null))
                                {
                                    for(IntValue value:values)
                                    {
                                        if (value.getValue()!=queryCondition.getQueryConditionTargetValue().getIntValue())
                                        {
                                            queryResult += connection.getConnectionId().toString()+ connection.getConnectionName().toString()+connection.getConnectionType().toString()+connection.getEndNode().toString()+connection.getProperty().toString();
                                        }
                                    }
                                }
                                if (operator == 4 && (property.getPropertyValues().getIntValue() != null))
                                {
                                    for(IntValue value:values)
                                    {
                                        if (value.getValue()>queryCondition.getQueryConditionTargetValue().getIntValue())
                                        {
                                            queryResult += connection.getConnectionId().toString()+ connection.getConnectionName().toString()+connection.getConnectionType().toString()+connection.getEndNode().toString()+connection.getProperty().toString();
                                        }
                                    }
                                }
                                if (operator == 5 && (property.getPropertyValues().getIntValue() != null))
                                {
                                    for(IntValue value:values)
                                    {
                                        if (value.getValue()>=queryCondition.getQueryConditionTargetValue().getIntValue())
                                        {
                                            queryResult += connection.getConnectionId().toString()+ connection.getConnectionName().toString()+connection.getConnectionType().toString()+connection.getEndNode().toString()+connection.getProperty().toString();
                                        }
                                    }
                                }
                                if (operator == 6 && (property.getPropertyValues().getIntValue() != null))
                                {
                                    //between todo
                                }
                            }
                        }
                    }

                }
            }
        }

        return queryResult;
    }

    private String flowInstanceQuery(UserId userId, QueryCondition queryCondition)
    {
        String queryResult = null;

        fetchObjectsInstance(userId);
        if (objects != null)
        {
            if (objects.getFlow() != null)
            {
                List<Flow> flowList = objects.getFlow();

                for (Flow flow: flowList)
                {
                    if (flow.getProperty() != null)
                    {
                        for (Property property : flow.getProperty())
                        {
                            if (property.getPropertyName().equals(queryCondition.getQueryConditionName()))
                            {
                                int operator = queryCondition.getQueryConditionMatchPattern().getIntValue();
                                List<IntValue> values = property.getPropertyValues().getIntValue();
                                if (operator == 0 && (property.getPropertyValues().getIntValue() != null))
                                {
                                    for(IntValue value:values)
                                    {
                                        if (value.getValue()<queryCondition.getQueryConditionTargetValue().getIntValue())
                                        {
                                            queryResult += flow.getFlowId().toString() + flow.getFlowName().toString() + flow.getMatchItem().toString() + flow.getProperty().toString();
                                        }
                                    }
                                }
                                if (operator == 1 && (property.getPropertyValues().getIntValue() != null))
                                {
                                    for(IntValue value:values)
                                    {
                                        if (value.getValue()<=queryCondition.getQueryConditionTargetValue().getIntValue())
                                        {
                                            queryResult += flow.getFlowId().toString() + flow.getFlowName().toString() + flow.getMatchItem().toString() + flow.getProperty().toString();
                                        }
                                    }
                                }
                                if (operator == 2 && (property.getPropertyValues().getIntValue() != null))
                                {
                                    for(IntValue value:values)
                                    {
                                        if (value.getValue()==queryCondition.getQueryConditionTargetValue().getIntValue())
                                        {
                                            queryResult += flow.getFlowId().toString() + flow.getFlowName().toString() + flow.getMatchItem().toString() + flow.getProperty().toString();
                                        }
                                    }
                                }
                                if (operator == 3 && (property.getPropertyValues().getIntValue() != null))
                                {
                                    for(IntValue value:values)
                                    {
                                        if (value.getValue()!=queryCondition.getQueryConditionTargetValue().getIntValue())
                                        {
                                            queryResult += flow.getFlowId().toString() + flow.getFlowName().toString() + flow.getMatchItem().toString() + flow.getProperty().toString();
                                        }
                                    }
                                }
                                if (operator == 4 && (property.getPropertyValues().getIntValue() != null))
                                {
                                    for(IntValue value:values)
                                    {
                                        if (value.getValue()>queryCondition.getQueryConditionTargetValue().getIntValue())
                                        {
                                            queryResult += flow.getFlowId().toString() + flow.getFlowName().toString() + flow.getMatchItem().toString() + flow.getProperty().toString();
                                        }
                                    }
                                }
                                if (operator == 5 && (property.getPropertyValues().getIntValue() != null))
                                {
                                    for(IntValue value:values)
                                    {
                                        if (value.getValue()>=queryCondition.getQueryConditionTargetValue().getIntValue())
                                        {
                                            queryResult += flow.getFlowId().toString() + flow.getFlowName().toString() + flow.getMatchItem().toString() + flow.getProperty().toString();
                                        }
                                    }
                                }
                                if (operator == 6 && (property.getPropertyValues().getIntValue() != null))
                                {
                                    //between todo
                                }
                            }
                        }
                    }
                    if (flow.getMatchItem() != null)
                    {
                        for(MatchItem matchItem: flow.getMatchItem())
                        {
                            if (matchItem.getMatchItemName().equals(queryCondition.getQueryConditionName()))
                            {
                                int operator = queryCondition.getQueryConditionMatchPattern().getIntValue();
                                if (operator == 0 && matchItem.getMatchItemValue().getIntValue()<queryCondition.getQueryConditionTargetValue().getIntValue())
                                {
                                    queryResult += flow.getFlowId().toString() + flow.getFlowName().toString() + flow.getMatchItem().toString() + flow.getProperty().toString();
                                }
                                if (operator == 1 && matchItem.getMatchItemValue().getIntValue()<=queryCondition.getQueryConditionTargetValue().getIntValue())
                                {
                                    queryResult += flow.getFlowId().toString() + flow.getFlowName().toString() + flow.getMatchItem().toString() + flow.getProperty().toString();
                                }
                                if (operator == 2 && matchItem.getMatchItemValue().getIntValue()==queryCondition.getQueryConditionTargetValue().getIntValue())
                                {
                                    queryResult += flow.getFlowId().toString() + flow.getFlowName().toString() + flow.getMatchItem().toString() + flow.getProperty().toString();
                                }
                                if (operator == 3 && matchItem.getMatchItemValue().getIntValue()!=queryCondition.getQueryConditionTargetValue().getIntValue())
                                {
                                    queryResult += flow.getFlowId().toString() + flow.getFlowName().toString() + flow.getMatchItem().toString() + flow.getProperty().toString();
                                }
                                if (operator == 4 && matchItem.getMatchItemValue().getIntValue()>queryCondition.getQueryConditionTargetValue().getIntValue())
                                {
                                    queryResult += flow.getFlowId().toString() + flow.getFlowName().toString() + flow.getMatchItem().toString() + flow.getProperty().toString();
                                }
                                if (operator == 5 && matchItem.getMatchItemValue().getIntValue()>=queryCondition.getQueryConditionTargetValue().getIntValue())
                                {
                                    queryResult += flow.getFlowId().toString() + flow.getFlowName().toString() + flow.getMatchItem().toString() + flow.getProperty().toString();
                                }
                                if (operator == 6 )
                                {
                                    //todo
                                }
                            }
                        }
                    }
                }
            }
        }

        return queryResult;
    }

    private String operationsInstanceQuery(UserId userId, QueryCondition queryCondition)
    {
        String queryResult = null;
        fetchOperationsInstance(userId);
        List<Operation> operationList = operations.getOperation();

        //todo

      return queryResult;
    }


    private void fetchObjectsInstance(UserId userId)
    {
        UserKey userKey = new UserKey(userId);
        InstanceIdentifier<Objects> objectsId = InstanceIdentifier.builder(Users.class).child(User.class, userKey).child(Objects.class).build();
        ListenableFuture<Optional<Objects>> objectsFuture = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.CONFIGURATION, objectsId);
        Futures.addCallback(objectsFuture, new FutureCallback<Optional<Objects>>() {
            @Override
            public void onSuccess(Optional<Objects> result)
            {
                setObjects(result.get());
                return;
            }

            @Override
            public void onFailure(Throwable t)
            {
                LOG.error("Can not read objects instances.", t);

                return;
            }
        });
    }

    private void fetchOperationsInstance(UserId userId)
    {
        UserKey userKey = new UserKey(userId);
        InstanceIdentifier<Operations> operationsId = InstanceIdentifier.builder(Users.class).child(User.class, userKey).child(Operations.class).build();
        ListenableFuture<Optional<Operations>> operationsFuture = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.CONFIGURATION, operationsId);
        Futures.addCallback(operationsFuture, new FutureCallback<Optional<Operations>>() {
            @Override
            public void onSuccess(Optional<Operations> result)
            {
                setOperations(result.get());
                return;
            }

            @Override
            public void onFailure(Throwable t)
            {
                LOG.error("Can not read operations instances.", t);

                return;
            }
        });
    }

    private void setObjects(Objects objects)
    {
        this.objects = objects;
    }
    private void setOperations(Operations operations)
    {
        this.operations = operations;
    }

}
