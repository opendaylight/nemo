/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.advancedquery;

import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.QueryConditionDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.advanced.nemo.query.input.QueryCondition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.query.condition.definitions.QueryConditionDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.query.condition.definitions.query.condition.definition.QueryConditionMatchPatterns;
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
 * Created by z00293636 on 2015/9/16.
 */
public class QueryDefinitionCheck {
    private DataBroker dataBroker;
    private List<QueryConditionDefinition> queryConditionDefinitions;
    private static final Logger LOG = LoggerFactory.getLogger(QueryDefinitionCheck.class);

    public QueryDefinitionCheck(DataBroker dataBroker)
    {
        this.dataBroker = dataBroker;
        queryConditionDefinitions = null;
    }

    public String CheckQueryDefinition(QueryCondition queryCondition)
    {
        fetchQueryConditionDefinitionList();
        Boolean conditionexist = false;
        String errorInfo = null;
        if (queryConditionDefinitions != null)
        {
            for (QueryConditionDefinition queryConditionDefinition : queryConditionDefinitions)
            {
                if (queryConditionDefinition.getQueryConditionName().equals(queryCondition.getQueryConditionName()))
                {
                    conditionexist = true;

                    if (queryCondition.getQueryIntentType() != null)
                    {
                        if (queryConditionDefinition.getQueryIntentType() != null)
                        {
                            if (queryCondition.getQueryIntentType().getIntValue() != queryConditionDefinition.getQueryIntentType().getIntValue())
                            {
                                errorInfo = "The query target is not consistent with the definition.";
                                break;
                            }
                        }
                        else
                        {
                            errorInfo = "There are no query intent type defined in this query condition.";
                            break;
                        }
                    }

                    if (queryCondition.getQueryConditionTargetValue() != null)
                    {
                        if (queryConditionDefinition.getQueryConditionValueType() != null)
                        {
                            QueryConditionDefinition.QueryConditionValueType queryConditionValueType = queryConditionDefinition.getQueryConditionValueType();
                            if (queryConditionValueType.getIntValue() == 0 && !(queryCondition.getQueryConditionTargetValue().getIntValue() == null && queryCondition.getQueryConditionTargetValue().getStringValue() != null && queryCondition.getQueryConditionTargetValue().getRangeValue() == null))
                            {
                                errorInfo =  "The property value type should be string";
                                break;
                            }
                            else if (queryConditionValueType.getIntValue() == 1 && !(queryCondition.getQueryConditionTargetValue().getIntValue() != null && queryCondition.getQueryConditionTargetValue().getStringValue() == null && queryCondition.getQueryConditionTargetValue().getRangeValue() == null))
                            {
                                errorInfo =  "The property value type should be integer";
                                break;
                            }
                            else if (queryConditionValueType.getIntValue() == 2 && !(queryCondition.getQueryConditionTargetValue().getIntValue() == null && queryCondition.getQueryConditionTargetValue().getStringValue() == null && queryCondition.getQueryConditionTargetValue().getRangeValue() != null))
                            {
                                errorInfo =  "The property value type should be range";
                                break;
                            }
                        }
                        else
                        {
                            errorInfo = "There are no query condition value type defined in query condition.";
                            break;
                        }
                    }

                    if (queryCondition.getQueryConditionMatchPattern() != null)
                    {
                        if (queryConditionDefinition.getQueryConditionMatchPatterns() != null)
                        {
                            if (queryConditionDefinition.getQueryConditionMatchPatterns().getQueryConditionMatchPattern() != null)
                            {
                                List<QueryConditionMatchPatterns.QueryConditionMatchPattern> queryConditionMatchPatternList= queryConditionDefinition.getQueryConditionMatchPatterns().getQueryConditionMatchPattern();
                               if (!queryConditionMatchPatternList.contains(queryCondition.getQueryConditionMatchPattern()))
                                {
                                    errorInfo = "The query condition match type pattern is not included in the definitions.";
                                    break;
                                }
                            }
                            else
                            {
                                errorInfo = "There are no query condition list defined in query condition.";
                            }
                        }
                        else
                        {
                            errorInfo = "There are no query condition match patterns defined in query condition.";
                        }
                    }
                }
            }
        }

        if (!conditionexist)
        {
            errorInfo = "The condition has not been defined.";
        }
        return errorInfo;
    }

    private void fetchQueryConditionDefinitionList()
    {
        InstanceIdentifier<QueryConditionDefinitions> queryCondiDefInsIdentifier = InstanceIdentifier.builder(QueryConditionDefinitions.class).build();
        ListenableFuture<Optional<QueryConditionDefinitions>> querydefinitionFuture = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.CONFIGURATION, queryCondiDefInsIdentifier);
        Futures.addCallback(querydefinitionFuture, new FutureCallback<Optional<QueryConditionDefinitions>>() {
            @Override
            public void onSuccess(Optional<QueryConditionDefinitions> result)
            {
                setQueryConditionDefinitions(result.get().getQueryConditionDefinition());
                return;
            }

            @Override
            public void onFailure(Throwable t)
            {
                LOG.error("Can not read query definition information.", t);

                return;
            }
        });
        return ;
    }

    private void setQueryConditionDefinitions(List<QueryConditionDefinition> queryConditionDefinitions)
    {
        this.queryConditionDefinitions = queryConditionDefinitions;
    }
 }
