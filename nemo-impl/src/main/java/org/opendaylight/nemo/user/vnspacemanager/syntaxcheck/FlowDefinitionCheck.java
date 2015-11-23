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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.MatchItemDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.MatchItem;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.definitions.MatchItemDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.MatchItemValue;
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
public class FlowDefinitionCheck {

    private DataBroker dataBroker;
    private List<MatchItemDefinition> matchItemDefinitionList;
    private static final Logger LOG = LoggerFactory.getLogger(FlowDefinitionCheck.class);

    public FlowDefinitionCheck(DataBroker dataBroker)
    {
        this.dataBroker = dataBroker;
        matchItemDefinitionList = null;
    }

    public String CheckDefinition(Flow flow)
    {
        String errorInfo = null;
        fetchMatchItemDefinitions();
        Boolean matchHasDefined = false;
        if (flow.getMatchItem() != null)
        {
            if (matchItemDefinitionList != null )
            {
                for (MatchItem matchItem : flow.getMatchItem())
                {
                    for (MatchItemDefinition matchItemDefinition : matchItemDefinitionList)
                    {
                        if (matchItem.getMatchItemName().equals(matchItemDefinition.getMatchItemName()))
                        {
                            matchHasDefined = true;
                            MatchItemValue matchItemValue = matchItem.getMatchItemValue();
                            MatchItemDefinition.MatchItemValueType matchItemValueType = matchItemDefinition.getMatchItemValueType();

                            if (matchItemValue != null && matchItemValueType != null)
                            {
                                if (matchItemValueType.getIntValue()==0 && !(matchItemValue.getIntValue()==null&&matchItemValue.getStringValue()!=null&&matchItemValue.getRangeValue()==null))
                                {
                                    errorInfo = "The match item value type for" +matchItem.getMatchItemName().toString()+"should be string.";
                                    break;
                                }

                                if (matchItemValueType.getIntValue()==1 && !(matchItemValue.getIntValue()!=null&&matchItemValue.getStringValue()==null&&matchItemValue.getRangeValue()==null))
                                {
                                    errorInfo = "The match item value type for" + matchItem.getMatchItemName().toString()+"should be integer.";
                                    break;
                                }

                                if (matchItemValueType.getIntValue()==2 && !(matchItemValue.getIntValue()==null&&matchItemValue.getStringValue()==null&&matchItemValue.getRangeValue()!=null))
                                {
                                    errorInfo = "The match item value type for" + matchItem.getMatchItemName().toString()+"should be range.";
                                    break;
                                }
                            }
                        }
                    }
                }
        }
            if (!matchHasDefined)
            {
                errorInfo = "The match item has not been defined.";
            }
    }
        return errorInfo;
    }

    private void fetchMatchItemDefinitions()
    {
        InstanceIdentifier<MatchItemDefinitions> matchitemdefinitionId = InstanceIdentifier.builder(MatchItemDefinitions.class).build();
        ListenableFuture<Optional<MatchItemDefinitions>> matchitemdefinitionFuture = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.CONFIGURATION, matchitemdefinitionId);
        Futures.addCallback(matchitemdefinitionFuture, new FutureCallback<Optional<MatchItemDefinitions>>() {
            @Override
            public void onSuccess(Optional<MatchItemDefinitions> result) {
               setMatchItemDefinitionList( result.get().getMatchItemDefinition());
                return;
            }

            @Override
            public void onFailure(Throwable t) {
                LOG.error("Can not read match item definition information.", t);

                return;
            }
        });
        return ;
    }

    private void setMatchItemDefinitionList(List<MatchItemDefinition> matchItemDefinitionList)
    {
        this.matchItemDefinitionList = matchItemDefinitionList;
    }
}
