/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.intent.condition;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.nemo.intent.IntentResolver;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.OperationId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/12/9.
 */
public class ConditionHandlerImplTest extends TestCase {
    private ConditionHandlerImpl conditionHandler;
    private IntentResolver intentResolver;
    private UserId userId;
    @Before
    public void setUp() throws Exception {
        userId = mock(UserId.class);
        intentResolver = mock(IntentResolver.class);

        conditionHandler = new ConditionHandlerImpl(intentResolver,userId);
    }

    @Test
    public void testHandleCondition() throws Exception {
        Operation operation = mock(Operation.class);
        OperationId operationId = mock(OperationId.class);
         List<ConditionSegment> conditionSegments = new ArrayList<ConditionSegment>();

        when(operation.getOperationId()).thenReturn(operationId);
        when(operationId.getValue()).thenReturn(new String("test"));
        when(operation.getConditionSegment()).thenReturn(conditionSegments);

        conditionHandler.handleCondition(operation);
    }

    @Test
    public void testClearCondition() throws Exception {
        conditionHandler.clearCondition();
    }

    @Test
    public void testToString() throws Exception {
        String test = conditionHandler.toString();
        Assert.assertTrue(! test.equals(null));
    }
}