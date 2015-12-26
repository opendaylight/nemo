/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.AAA;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.StructureStyleNemoUpdateInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Results;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.definitions.TemplateDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.instances.TemplateInstance;

import java.util.List;

/**
 * Created by z00293636 on 2015/8/31.
 */
public class UpdateIntent {
    private UpdateNode updateNode;
    private UpdateConnection updateConnection;
    private UpdateFlow updateFlow;
    private UpdateOperation updateOperation;
    private UpdateResult updateResult;
    private UpdateTemplateDefinition updateTemplateDefinition;
    private UpdateTemplateInstance updateTemplateInstance;

    public UpdateIntent(DataBroker dataBroker, TenantManage tenantManage){
        updateNode = new UpdateNode(dataBroker,tenantManage);
        updateConnection = new UpdateConnection(dataBroker,tenantManage);
        updateFlow = new UpdateFlow(dataBroker,tenantManage);
        updateOperation = new UpdateOperation(dataBroker,tenantManage);
        updateResult = new UpdateResult(dataBroker,tenantManage);
        updateTemplateDefinition = new UpdateTemplateDefinition(dataBroker,tenantManage);
        updateTemplateInstance = new UpdateTemplateInstance(dataBroker,tenantManage);
    }

    public String updateIntent(AAA aaa,StructureStyleNemoUpdateInput structureStyleNemoUpdateInput){
        String erroInfo = null;

        erroInfo = aaa.checkUser(structureStyleNemoUpdateInput.getUserId());

        if (erroInfo != null){
            return erroInfo;
        }
        else{
            if(structureStyleNemoUpdateInput.getObjects() != null){
                if (structureStyleNemoUpdateInput.getObjects().getNode() != null && erroInfo == null){
                    List<Node> nodeList= structureStyleNemoUpdateInput.getObjects().getNode();
                    for (Node node : nodeList ){
                        erroInfo = updateNode.NodeHandling(structureStyleNemoUpdateInput.getUserId(), node);
                        if (erroInfo != null){
                            break;
                        }
                    }
                }
                if (structureStyleNemoUpdateInput.getObjects().getConnection() != null && erroInfo == null){
                    List<Connection> connectionList = structureStyleNemoUpdateInput.getObjects().getConnection();
                    for (Connection connection : connectionList){
                        erroInfo = updateConnection.ConnectionHandling(structureStyleNemoUpdateInput.getUserId(), connection);
                        if (erroInfo != null){
                            break;
                        }
                    }
                }

                if (structureStyleNemoUpdateInput.getObjects().getFlow() != null && erroInfo == null){
                    List<Flow> flowList = structureStyleNemoUpdateInput.getObjects().getFlow();
                    for (Flow flow : flowList){
                        erroInfo = updateFlow.FlowHandling(structureStyleNemoUpdateInput.getUserId(),flow);
                        if (erroInfo != null) {
                            break;
                        }
                    }
                }
            }

        if (structureStyleNemoUpdateInput.getOperations() != null){
                if (structureStyleNemoUpdateInput.getOperations().getOperation() != null && erroInfo == null){
                    List<Operation> operationList =structureStyleNemoUpdateInput.getOperations().getOperation();
                    for (Operation operation : operationList){
                        erroInfo = updateOperation.OperationHandling(structureStyleNemoUpdateInput.getUserId(), operation);
                        if (erroInfo !=null){
                            break;
                        }
                    }
                }
            }

        if (structureStyleNemoUpdateInput.getResults() != null){
                if ( structureStyleNemoUpdateInput.getResults() != null && erroInfo == null){
                    Results results = structureStyleNemoUpdateInput.getResults();
                    erroInfo = updateResult.ResultHandling(structureStyleNemoUpdateInput.getUserId(), results);
                    if (erroInfo != null){
                        //todo
                    }
                }
            }
        }

        if (structureStyleNemoUpdateInput.getTemplateDefinitions()!=null){
            if (structureStyleNemoUpdateInput.getTemplateDefinitions().getTemplateDefinition()!=null && erroInfo == null){
                List<TemplateDefinition> templateDefinitionList = structureStyleNemoUpdateInput.getTemplateDefinitions().getTemplateDefinition();
                for (TemplateDefinition templateDefinition : templateDefinitionList ){
                    erroInfo = updateTemplateDefinition.checkTemplateDefinition(structureStyleNemoUpdateInput.getUserId(),templateDefinition);
                    if (erroInfo!=null){
                        break;
                    }
                }
            }
        }

        if (structureStyleNemoUpdateInput.getTemplateInstances()!=null){
            if (structureStyleNemoUpdateInput.getTemplateInstances().getTemplateInstance()!=null && erroInfo ==null){
                List<TemplateInstance> templateInstanceList = structureStyleNemoUpdateInput.getTemplateInstances().getTemplateInstance();
                for (TemplateInstance templateInstance : templateInstanceList){
                    erroInfo = updateTemplateInstance.checkTemplateInstance(structureStyleNemoUpdateInput.getUserId(),templateInstance);
                    if (erroInfo!=null){
                        break;
                    }
                }
            }
        }
     return erroInfo;
    }
}
