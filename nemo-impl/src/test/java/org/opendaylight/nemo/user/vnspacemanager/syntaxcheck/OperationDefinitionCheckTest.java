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

import com.google.common.base.Optional;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.ListenableFuture;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.opendaylight.yangtools.yang.common.RpcError.ErrorType;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import com.google.common.base.Function;
import com.google.common.util.concurrent.CheckedFuture;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ParameterName;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import static org.mockito.Mockito.*;
@RunWith(PowerMockRunner.class) 
@PrepareForTest(ConnectionDefinitionCheck.class) 
public class OperationDefinitionCheckTest{
	private OperationDefinitionCheck operationDefinitionCheck;
	private DataBroker dataBroker;
	private ActionDefinition actionDefinition;
	private ConditionParameterDefinition conditionParameterDefinition;
	private List<ActionDefinition> actionDefinitionList;
    private List<ConditionParameterDefinition> conditionParameterDefinitionList;
	private Operation operation;
    //private static final Logger LOG = LoggerFactory.getLogger(NodeDefinitionCheck.class);
	
	@org.junit.Before
    public void setUp() throws Exception {
        actionDefinitionList = new LinkedList<ActionDefinition>();
		conditionParameterDefinitionList = new LinkedList<ConditionParameterDefinition>();
       // LOG = mock(Logger.class);
		operation = mock(Operation.class);
        dataBroker = mock(DataBroker.class);
        operationDefinitionCheck = new OperationDefinitionCheck(dataBroker);
    }
	@org.junit.Test
    public void testcheckConnectionDefinition() throws Exception {
        CheckedFuture actiondefinitionFuture = mock(CheckedFuture.class);
		CheckedFuture conditionparadefinitionFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(actiondefinitionFuture);
		when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(conditionparadefinitionFuture);
		//branch null
		Assert.assertNotNull(operation.getAction());
		Assert.assertNotNull(operation.getConditionSegment());
        Assert.assertEquals(operationDefinitionCheck.CheckDefinition(operation),"There are no actions has been defined in the data store.");
		when(operation.getAction()).thenReturn(null);
		Assert.assertEquals(operationDefinitionCheck.CheckDefinition(operation),"This condition has not been defined in data store.");
		//

        verify(dataBroker,times(4)).newReadOnlyTransaction();
        verify(readOnlyTransaction,times(4)).read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class));
		
    }
}