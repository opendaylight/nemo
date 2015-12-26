/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.EndNode;

import java.util.List;

/**
 * Created by z00293636 on 2015/9/2.
 */
public class DeleteNode {
    private TenantManage tenantManage;

    public DeleteNode(DataBroker dataBroker, TenantManage tenantManage){
        this.tenantManage = tenantManage;
    }

    public String DeleNodeHandling(UserId userId,NodeId nodeId){
        Boolean nodeExist = false;

        if (tenantManage.getNode(userId)!=null){
            if (tenantManage.getNode(userId).containsKey(nodeId)){
                nodeExist = true;
                tenantManage.getNode(userId).remove(nodeId);
                tenantManage.getUserNameIdMap(userId).remove(tenantManage.getName(userId,nodeId.getValue()));
            }
        }
        if (tenantManage.getNodeDataStore(userId)!=null){
            if (tenantManage.getNodeDataStore(userId).containsKey(nodeId)){
                nodeExist = true;
                tenantManage.setUserDeleteIntent(userId, NEMOConstants.node,nodeId.getValue());
                tenantManage.getUserNameIdMap(userId).remove(tenantManage.getName(userId,nodeId.getValue()));
            }
        }
        if (!nodeExist){
            return "The node instance " +nodeId.getValue()+" is not exist.";
        }
        else {
            if (tenantManage.getConnection(userId)!=null){
                for (Connection connection : tenantManage.getConnection(userId).values()){
                    List<EndNode> endNodeList = connection.getEndNode();
                    for (EndNode endNode :endNodeList){
                        if (endNode.getNodeId().equals(nodeId)){
                            tenantManage.getConnection(userId).remove(connection.getConnectionId());
                            tenantManage.getUserNameIdMap(userId).remove(tenantManage.getName(userId,connection.getConnectionId().getValue()));
                            break;
                        }
                    }
                }
            }
            if (tenantManage.getConnectionDataStore(userId)!=null){
                for (Connection connection : tenantManage.getConnectionDataStore(userId).values()){
                    List<EndNode> endNodeList = connection.getEndNode();
                    for (EndNode endNode :endNodeList){
                        if (endNode.getNodeId().equals(nodeId)){
                            tenantManage.setUserDeleteIntent(userId,NEMOConstants.connection,connection.getConnectionId().getValue());
                            tenantManage.getUserNameIdMap(userId).remove(tenantManage.getName(userId,connection.getConnectionId().getValue()));
                            break;
                        }
                    }
                }
            }
            if (tenantManage.getOperation(userId)!=null){
                for (Operation operation : tenantManage.getOperation(userId).values()){
                    if (operation.getTargetObject().getValue().equals(nodeId.getValue())){
                        tenantManage.getOperation(userId).remove(operation.getOperationId());
                        tenantManage.getUserNameIdMap(userId).remove(tenantManage.getName(userId,operation.getOperationId().getValue()));
                    }
                }
            }
            if (tenantManage.getOperationDataStore(userId)!=null){
                for (Operation operation : tenantManage.getOperationDataStore(userId).values()){
                    if (operation.getTargetObject().getValue().equals(nodeId.getValue())){
                        tenantManage.setUserDeleteIntent(userId,NEMOConstants.operation,operation.getOperationId().getValue());
                        tenantManage.getUserNameIdMap(userId).remove(tenantManage.getName(userId,operation.getOperationId().getValue()));
                    }
                }
            }
        }
        return null;
    }
}
