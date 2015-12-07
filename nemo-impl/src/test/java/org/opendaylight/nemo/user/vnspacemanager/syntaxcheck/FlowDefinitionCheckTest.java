/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.syntaxcheck;

import org.junit.runner.RunWith;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.spy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.FlowPropertyDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.MatchItemDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.MatchItem;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.definitions.MatchItemDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.MatchItemValue;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.opendaylight.yangtools.yang.common.RpcError.ErrorType;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import com.google.common.base.Optional;
import com.google.common.base.Function;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class) 
@PrepareForTest(ConnectionDefinitionCheck.class) 
public class FlowDefinitionCheckTest{
	private FlowDefinitionCheck flowDefinitionCheck;
    private DataBroker dataBroker;
    private List<MatchItemDefinition> matchItemDefinitionList; 
	private Flow flow;
   // private static final Logger LOG;
    @org.junit.Before
    public void setUp() throws Exception {
        matchItemDefinitionList = new LinkedList<MatchItemDefinition>();
       // LOG = mock(Logger.class);

        dataBroker = mock(DataBroker.class);
		flow = mock(Flow.class);
        flowDefinitionCheck = new FlowDefinitionCheck(dataBroker);
    }
	 @org.junit.Test
    public void testcheckConnectionDefinition() throws Exception {
        CheckedFuture matchitemdefinitionFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(matchitemdefinitionFuture);
		Assert.assertNotNull(flow.getMatchItem());
		Assert.assertEquals(flowDefinitionCheck.CheckDefinition(flow),"The match item has not been defined.");
		//dataBroker test 
        verify(dataBroker).newReadOnlyTransaction();
        verify(readOnlyTransaction).read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class));
		verifyPrivate(flowDefinitionCheck).invoke("fetchMatchItemDefinitions");
		
    }

}