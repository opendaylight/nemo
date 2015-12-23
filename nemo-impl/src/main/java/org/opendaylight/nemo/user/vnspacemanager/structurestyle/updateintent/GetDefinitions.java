/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ActionName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.MatchItemName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.ConnectionDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.MatchItemDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.NodeDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.definitions.ConnectionDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.definitions.MatchItemDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.definitions.NodeDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.ActionDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.ConditionParameterDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.definitions.ActionDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.parameter.definitions.ConditionParameterDefinition;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Created by z00293636 on 2015/12/3.
 */
public class GetDefinitions {
    private DataBroker dataBroker;
    private List<NodeDefinition> nodeDefinitionList = null;
    private List<ConnectionDefinition> connectionDefinitionsList = null;
    private List<MatchItemDefinition> matchItemDefinitionList = null;
    private List<ConditionParameterDefinition> conditionParameterDefinitionList = null;
    private List<ActionDefinition> actionDefinitionList = null;
    private static final Logger LOG = LoggerFactory.getLogger(GetDefinitions.class);

    public GetDefinitions(DataBroker dataBroker){
        this.dataBroker = dataBroker;
    }

    public Map<NodeType, NodeDefinition> getNodeDefinition(){
        fetchNodeDefinitions();
        Map<NodeType, NodeDefinition> map = new HashMap<NodeType, NodeDefinition>();
        if (nodeDefinitionList!=null){
            for (NodeDefinition nodeDefinition : nodeDefinitionList){
                map.put(nodeDefinition.getNodeType(),nodeDefinition);
            }
        }
        return map;
    }

    public Map<MatchItemName, MatchItemDefinition> getMatchItemDefinition(){
        fetchMatchItemDefinitions();
        Map<MatchItemName, MatchItemDefinition> map = new HashMap<MatchItemName, MatchItemDefinition>();
        if (matchItemDefinitionList != null){
            for (MatchItemDefinition matchItemDefinition : matchItemDefinitionList){
                map.put(matchItemDefinition.getMatchItemName(),matchItemDefinition);
            }
        }
        return map;
    }

    public Map<ConnectionType, ConnectionDefinition> getConnectionDefinition(){
        fetchConnectionDefinitionList();
        Map<ConnectionType, ConnectionDefinition> map = new HashMap<ConnectionType, ConnectionDefinition>();
        if (connectionDefinitionsList != null){
            for (ConnectionDefinition connectionDefinition : connectionDefinitionsList){
                map.put(connectionDefinition.getConnectionType(),connectionDefinition);
            }
        }
        return map;
    }

    public Map<ActionName, ActionDefinition> getActionDefinition(){
        fetchActionDefinitions();
        Map<ActionName,ActionDefinition> map = new HashMap<ActionName, ActionDefinition>();
        if (actionDefinitionList!=null){
            for (ActionDefinition actionDefinition : actionDefinitionList){
                map.put(actionDefinition.getActionName(),actionDefinition);
            }
        }
        return map;
    }

    public Map<String, ConditionParameterDefinition> getConditionParameterDefinition(){
        fetchConditionParaDefinitions();
        Map<String, ConditionParameterDefinition> map = new HashMap<>();
        if (conditionParameterDefinitionList!=null){
            for (ConditionParameterDefinition conditionParameterDefinition : conditionParameterDefinitionList){
                map.put(conditionParameterDefinition.getParameterName().getValue(), conditionParameterDefinition);
            }
        }
        return map;
    }

    private void setNodeDefinitionsList(List<NodeDefinition> nodeDefinitiones){
        this.nodeDefinitionList = nodeDefinitiones;
    }

    private void setMatchItemDefintionList(List<MatchItemDefinition> matchItemDefinitions){
        this.matchItemDefinitionList = matchItemDefinitions;
    }

    private void setConnectionDefinitionsList(List<ConnectionDefinition> connectionDefinitions){
        this.connectionDefinitionsList = connectionDefinitions;
    }

    private void setConditionParameterDefinitionList(List<ConditionParameterDefinition> conditionParameterDefinitions){
        this.conditionParameterDefinitionList = conditionParameterDefinitions;
    }

    private void setActionDefinitionList(List<ActionDefinition> actionDefinitions){
        this.actionDefinitionList = actionDefinitions;
    }

    private void fetchNodeDefinitions(){
        InstanceIdentifier<NodeDefinitions> nodedefinitionId = InstanceIdentifier.builder(NodeDefinitions.class).build();
        ListenableFuture<Optional<NodeDefinitions>> nodedefinitionFuture = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.CONFIGURATION, nodedefinitionId);
        Futures.addCallback(nodedefinitionFuture, new FutureCallback<Optional<NodeDefinitions>>() {
            @Override
            public void onSuccess(Optional<NodeDefinitions> result){
                setNodeDefinitionsList(result.get().getNodeDefinition());
            }
            @Override
            public void onFailure(Throwable t){
                LOG.error("Can not read node definitions information.", t);
            }
        });
        return ;
    }

    private void fetchConnectionDefinitionList(){
        InstanceIdentifier<ConnectionDefinitions> connectiondefinitionId = InstanceIdentifier.builder(ConnectionDefinitions.class).build();
        ListenableFuture<Optional<ConnectionDefinitions>> connectiondefinitionFuture = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.CONFIGURATION, connectiondefinitionId);
        Futures.addCallback(connectiondefinitionFuture, new FutureCallback<Optional<ConnectionDefinitions>>() {
            @Override
            public void onSuccess(Optional<ConnectionDefinitions> result) {
                setConnectionDefinitionsList(result.get().getConnectionDefinition());
            }

            @Override
            public void onFailure(Throwable t) {
                LOG.error("Can not read connection definition information.", t);
            }
        });
        return;
    }

    private void fetchMatchItemDefinitions(){
        InstanceIdentifier<MatchItemDefinitions> matchitemdefinitionId = InstanceIdentifier.builder(MatchItemDefinitions.class).build();
        ListenableFuture<Optional<MatchItemDefinitions>> matchitemdefinitionFuture = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.CONFIGURATION, matchitemdefinitionId);
        Futures.addCallback(matchitemdefinitionFuture, new FutureCallback<Optional<MatchItemDefinitions>>() {
            @Override
            public void onSuccess(Optional<MatchItemDefinitions> result) {
                setMatchItemDefintionList(result.get().getMatchItemDefinition());
            }
            @Override
            public void onFailure(Throwable t) {
                LOG.error("Can not read match item definition information.", t);
            }
        });
        return ;
    }

    private void fetchActionDefinitions(){
        InstanceIdentifier<ActionDefinitions> actiondefinitionId = InstanceIdentifier.builder(ActionDefinitions.class).build();
        ListenableFuture<Optional<ActionDefinitions>> actiondefinitionFuture = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.CONFIGURATION, actiondefinitionId);
        Futures.addCallback(actiondefinitionFuture, new FutureCallback<Optional<ActionDefinitions>>() {
            @Override
            public void onSuccess(Optional<ActionDefinitions> result) {
                setActionDefinitionList(result.get().getActionDefinition());
            }

            @Override
            public void onFailure(Throwable t) {
                LOG.error("Can not read action definition information.", t);
            }
        });
        return ;
    }

    private void fetchConditionParaDefinitions(){
        InstanceIdentifier<ConditionParameterDefinitions> conditionparadefinitionId = InstanceIdentifier.builder(ConditionParameterDefinitions.class).build();
        ListenableFuture<Optional<ConditionParameterDefinitions>> conditionparadefinitionFuture = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.CONFIGURATION, conditionparadefinitionId);
        Futures.addCallback(conditionparadefinitionFuture, new FutureCallback<Optional<ConditionParameterDefinitions>>() {
            @Override
            public void onSuccess(Optional<ConditionParameterDefinitions> result) {
                setConditionParameterDefinitionList(result.get().getConditionParameterDefinition());
            }

            @Override
            public void onFailure(Throwable t) {
                LOG.error("Can not read condition parameter definition information.", t);
            }
        });

        try {
            conditionparadefinitionFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ;
    }
}
