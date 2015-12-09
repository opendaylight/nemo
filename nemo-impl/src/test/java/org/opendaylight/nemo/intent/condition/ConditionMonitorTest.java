/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.intent.condition;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.nemo.intent.IntentResolver;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConditionParameterName;

import static org.junit.Assert.*;
import org.opendaylight.nemo.intent.IntentResolver;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment.ConditionParameterMatchPattern;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment.PrecursorRelationOperator;

import java.util.List;
import java.util.TimerTask;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/12/9.
 */
public class ConditionMonitorTest extends TestCase {
    private IntentResolver intentResolver;
    private UserId userId;
    private List<ConditionSegment> conditionSegments;
    private ConditionMonitor conditionMonitor;
    @Before
    public void setUp() throws Exception {
        intentResolver = mock(IntentResolver.class);
        userId = mock(UserId.class);
        conditionSegments = new ArrayList<ConditionSegment>();

        conditionMonitor = new ConditionMonitor(intentResolver,userId,conditionSegments);
    }

    @Test
    public void testRun() throws Exception {
        Class<ConditionMonitor> class1 = ConditionMonitor.class;
        Field field = class1.getDeclaredField("conditionSegments");
        field.setAccessible(true);
        ConditionSegment conditionSegment = mock(ConditionSegment.class);
        ConditionParameterName conditionParameterName = mock(ConditionParameterName.class);
        //init
        conditionMonitor.run();
        System.out.println(field.get(conditionMonitor));

        //change data
        conditionSegments.add(conditionSegment);
        field.set(conditionMonitor, conditionSegments);

        // create conditions
        when(conditionSegment.getPrecursorRelationOperator()).thenReturn(ConditionSegment.PrecursorRelationOperator.Not);
        when(conditionSegment.getConditionParameterName()).thenReturn(conditionParameterName);
        when(conditionParameterName.getValue()).thenReturn(new String("test"));//do not get into method "isTimeConditionMet" and return false
        // test changed
        conditionMonitor.run();
        System.out.println(field.get(conditionMonitor));

    }
}