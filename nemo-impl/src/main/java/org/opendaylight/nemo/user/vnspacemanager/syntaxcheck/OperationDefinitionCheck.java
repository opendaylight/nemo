/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.syntaxcheck;

import java.util.List;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.ActionDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.ConditionParameterDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.definitions.ActionDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.ParameterValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.condition.segment.ConditionParameterTargetValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.parameter.definitions.ConditionParameterDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.parameter.definitions.condition.parameter.definition.ParameterMatchPatterns;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Created by z00293636 on 2015/9/2.
 */
public class OperationDefinitionCheck {

    private DataBroker dataBroker;
    private List<ActionDefinition> actionDefinitionList;
    private List<ConditionParameterDefinition> conditionParameterDefinitionList;
    private static final Logger LOG = LoggerFactory.getLogger(OperationDefinitionCheck.class);
    public OperationDefinitionCheck(DataBroker dataBroker)
    {
        this.dataBroker = dataBroker;
        actionDefinitionList = null;
        conditionParameterDefinitionList = null;
    }

    public String CheckDefinition(Operation operation)
    {
        fetchActionDefinitions();
        fetchConditionParaDefinitions();
        String errorInfo = null;

        if (operation.getAction() != null )
        {
           errorInfo = checkAction(operation);
        }
        if (errorInfo == null && operation.getConditionSegment() != null)
        {
            errorInfo = checkCondition(operation);
        }
        return errorInfo;
    }

    private String checkAction(Operation operation)
    {
        String errorInfo = null;
        Boolean actionHasDefine = false;

        if ( actionDefinitionList == null)
        {
            errorInfo = "There are no actions has been defined in the data store.";
        }
        else {
            for (Action action : operation.getAction()) {
                for (ActionDefinition actionDefinition : actionDefinitionList) {
                    if (actionDefinition.getActionName().equals(action.getActionName())) {
                        actionHasDefine = true;

                        ParameterValues parameterValues = action.getParameterValues();
                        ActionDefinition.ParameterValueType parameterValueType = actionDefinition.getParameterValueType();

                        if (parameterValues != null && parameterValueType != null)
                        {
                            if (parameterValueType.getIntValue() == 0 && !(parameterValues.getIntValue() == null && parameterValues.getStringValue() != null && parameterValues.getRangeValue() == null)) {
                                errorInfo = "The value type of" + action.getActionName().toString() + "should be string.";
                                break;
                            }
                            if (parameterValueType.getIntValue() == 1 && !(parameterValues.getIntValue() != null && parameterValues.getStringValue() == null && parameterValues.getRangeValue() == null)) {
                                errorInfo = "The value type of" + action.getActionName().toString() + "should be integer.";
                                break;
                            }
                            if (parameterValueType.getIntValue() == 2 && !(parameterValues.getIntValue() == null && parameterValues.getStringValue() == null && parameterValues.getRangeValue() != null)) {
                                errorInfo = "The value type of" + action.getActionName().toString() + "should be range.";
                                break;
                            }
                        }
                    }

                }
            }
            if (!actionHasDefine) {
                errorInfo = "The action type has not been defined.";
            }
        }
        return errorInfo;
    }

    private String checkCondition(Operation operation)
    {
        String errorInfo = null;
        Boolean conditionHasDefined = false;

        if (conditionParameterDefinitionList != null)
        {
            if (operation.getConditionSegment() != null)
            {
                for (ConditionSegment conditionSegment :operation.getConditionSegment())
                {
                    for (ConditionParameterDefinition conditionParameterDefinition : conditionParameterDefinitionList)
                    {
                        if (conditionParameterDefinition.getParameterName().getValue().equals(conditionSegment.getConditionParameterName().getValue()))
                        {
                            conditionHasDefined = true;
                            if (conditionSegment.getConditionParameterMatchPattern() != null)
                            {
                                if (conditionParameterDefinition.getParameterMatchPatterns() != null)
                                {
                                    List<ParameterMatchPatterns.ParameterMatchPattern> conditionParameterMatchPatterns = conditionParameterDefinition.getParameterMatchPatterns().getParameterMatchPattern();
                                    if (conditionParameterMatchPatterns != null)
                                    {
                                        Boolean matchpatternexist = false;
                                        for (ParameterMatchPatterns.ParameterMatchPattern parameterMatchPattern : conditionParameterMatchPatterns)
                                        {
                                            if (parameterMatchPattern.getIntValue() == conditionSegment.getConditionParameterMatchPattern().getIntValue())
                                            {
                                                matchpatternexist = true;
                                                break;
                                            }
                                        }
                                        if (!matchpatternexist)
                                        {
                                            errorInfo = "The match pattern has not defined in the condition.";
                                        }
                                    }
                                    else
                                    {
                                        errorInfo = "There are no match pattern in match pattrn list.";
                                        break;
                                    }
                                }
                                else
                                {
                                    errorInfo = "No match patterns have been defined in data store.";
                                    break;
                                }
                            }
                            if (conditionSegment.getConditionParameterTargetValue() != null)
                            {
                                if (conditionParameterDefinition.getParameterValueType() != null)
                                {
                                    ConditionParameterTargetValue conditionParameterTargetValue = conditionSegment.getConditionParameterTargetValue();
                                    ConditionParameterDefinition.ParameterValueType  parameterValueType = conditionParameterDefinition.getParameterValueType();

                                    if (parameterValueType.getIntValue() == 0 && !(conditionParameterTargetValue.getIntValue() == null && conditionParameterTargetValue.getStringValue() != null && conditionParameterTargetValue.getRangeValue() == null)) {
                                        errorInfo = "The value type of" + conditionSegment.getConditionParameterName().toString() + "should be string.";
                                        break;
                                    }
                                    if (parameterValueType.getIntValue() == 1 && !(conditionParameterTargetValue.getIntValue() != null && conditionParameterTargetValue.getStringValue() == null && conditionParameterTargetValue.getRangeValue() == null)) {
                                        errorInfo = "The value type of" + conditionSegment.getConditionParameterName().toString() + "should be integer.";
                                        break;
                                    }
                                    if (parameterValueType.getIntValue() == 2 && !(conditionParameterTargetValue.getIntValue() == null && conditionParameterTargetValue.getStringValue() == null && conditionParameterTargetValue.getRangeValue() != null)) {
                                        errorInfo = "The value type of" + conditionSegment.getConditionParameterName().toString() + "should be range.";
                                        break;
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }

        if (!conditionHasDefined)
        {
            errorInfo = "This condition has not been defined in data store.";
        }
        return errorInfo;
    }

    private void fetchActionDefinitions()
    {
        InstanceIdentifier<ActionDefinitions> actiondefinitionId = InstanceIdentifier.builder(ActionDefinitions.class).build();
        ListenableFuture<Optional<ActionDefinitions>> actiondefinitionFuture = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.CONFIGURATION, actiondefinitionId);
        Futures.addCallback(actiondefinitionFuture, new FutureCallback<Optional<ActionDefinitions>>() {
            @Override
            public void onSuccess(Optional<ActionDefinitions> result) {
                setActionDefinitionList( result.get().getActionDefinition());
                return;
            }

            @Override
            public void onFailure(Throwable t) {
                LOG.error("Can not read action definition information.", t);

                return;
            }
        });
        return ;
    }

    private void fetchConditionParaDefinitions()
    {
        InstanceIdentifier<ConditionParameterDefinitions> conditionparadefinitionId = InstanceIdentifier.builder(ConditionParameterDefinitions.class).build();
        ListenableFuture<Optional<ConditionParameterDefinitions>> conditionparadefinitionFuture = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.CONFIGURATION, conditionparadefinitionId);
        Futures.addCallback(conditionparadefinitionFuture, new FutureCallback<Optional<ConditionParameterDefinitions>>() {
            @Override
            public void onSuccess(Optional<ConditionParameterDefinitions> result) {
                setConditionParameterDefinitionList( result.get().getConditionParameterDefinition());
                return;
            }

            @Override
            public void onFailure(Throwable t) {
                LOG.error("Can not read condition parameter definition information.", t);
                return;
            }
        });
        return ;
    }

    private void setActionDefinitionList(List<ActionDefinition> actionDefinitionList)
    {
        this.actionDefinitionList = actionDefinitionList;
    }

    private void setConditionParameterDefinitionList(List<ConditionParameterDefinition> conditionParameterDefinitionList)
    {
        this.conditionParameterDefinitionList = conditionParameterDefinitionList;
    }
}
