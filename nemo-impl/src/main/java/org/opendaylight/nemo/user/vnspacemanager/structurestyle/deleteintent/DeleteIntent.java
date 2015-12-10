/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.AAA;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.OperationId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.StructureStyleNemoDeleteInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.structure.style.nemo.delete.input.Results;

import java.util.List;

/**
 * Created by z00293636 on 2015/9/2.
 */
public class DeleteIntent {

    private TenantManage tenantManage;
    private DeleteNode deleteNode;
    private DeleteConnection deleteConnection;
    private DeleteFlow deleteFlow;
    private DeleteOperation deleteOperation;
    private DeleteResult deleteResult;

    public DeleteIntent(DataBroker dataBroker, TenantManage tenantManage){
        this.tenantManage = tenantManage;
        deleteNode = new DeleteNode(dataBroker, tenantManage);
        deleteConnection = new DeleteConnection(dataBroker,tenantManage);
        deleteFlow = new DeleteFlow(dataBroker,tenantManage);
        deleteOperation = new DeleteOperation(dataBroker,tenantManage);
        deleteResult = new DeleteResult();
    }

    public String styleNemoDeleteOutput(AAA aaa,StructureStyleNemoDeleteInput styleNemoDeleteInput){
        String errorInfo = null;

        errorInfo = aaa.checkUser(styleNemoDeleteInput.getUserId());
        if (errorInfo != null){
            return errorInfo;
        }
        else{
            if (styleNemoDeleteInput.getObjects() != null){
                if (styleNemoDeleteInput.getObjects().getNode() != null && errorInfo == null){
                    List<NodeId> nodeIdList= styleNemoDeleteInput.getObjects().getNode();
                    for (NodeId nodeId : nodeIdList){
                        errorInfo = deleteNode.DeleNodeHandling(styleNemoDeleteInput.getUserId(), nodeId);
                        if (errorInfo != null) {
                            break;
                        }
                    }
                    return errorInfo;
                }
                if (styleNemoDeleteInput.getObjects().getConnection() != null && errorInfo == null){
                    List<ConnectionId> connectionIdList = styleNemoDeleteInput.getObjects().getConnection();
                    for (ConnectionId connectionId : connectionIdList){
                        errorInfo = deleteConnection.DeleteConnectionHandling(styleNemoDeleteInput.getUserId(),connectionId);
                        if (errorInfo != null){
                            break;
                        }
                    }
                    return errorInfo;
                }
                if (styleNemoDeleteInput.getObjects().getFlow() != null && errorInfo == null){
                    List<FlowId> flowIdList = styleNemoDeleteInput.getObjects().getFlow();
                    for (FlowId flowId : flowIdList ){
                        errorInfo = deleteFlow.DeleteFlowHandling(styleNemoDeleteInput.getUserId(),flowId);
                        if (errorInfo != null){
                            break;
                        }
                    }
                    return errorInfo;
                }
            }
        if (styleNemoDeleteInput.getOperations() != null){
            if (styleNemoDeleteInput.getOperations().getOperation() != null && errorInfo == null){
                List<OperationId> operationIdList = styleNemoDeleteInput.getOperations().getOperation();
                for (OperationId operationId : operationIdList){
                    errorInfo = deleteOperation.DeleteOperationhandling(styleNemoDeleteInput.getUserId(),operationId);
                    if (errorInfo != null){
                        break;
                    }
                }
                return errorInfo;
            }
        }
        if (styleNemoDeleteInput.getResults() != null){
            Results results = styleNemoDeleteInput.getResults();
            errorInfo = deleteResult.DeleteResultHandling(styleNemoDeleteInput.getUserId(),results);
            if (errorInfo != null){
                return errorInfo;
            }
           }
        }
        return null;
    }
}
