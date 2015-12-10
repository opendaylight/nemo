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

import static org.junit.Assert.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment.ConditionParameterMatchPattern;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment.PrecursorRelationOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.condition.segment.ConditionParameterTargetValue;
import static org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment.PrecursorRelationOperator.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConditionParameterName;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/12/9.
 */
public class ConditionDeterminerTest extends TestCase {
    private ConditionDeterminer conditionDeterminer;
    @Before
    public void setUp() throws Exception {
        conditionDeterminer = new ConditionDeterminer();
    }

    @Test
    public void testIsConditionMet1() throws Exception {
        boolean result;
        List<ConditionSegment> conditionSegments = new ArrayList<ConditionSegment>();
        ConditionSegment conditionSegment = mock(ConditionSegment.class);
        ConditionSegment conditionSegment1 = mock(ConditionSegment.class);
        ConditionParameterTargetValue conditionParameterTargetValue = mock(ConditionParameterTargetValue.class);
        ConditionParameterTargetValue conditionParameterTargetValue1 = mock(ConditionParameterTargetValue.class);
        ConditionParameterName conditionParameterName = mock(ConditionParameterName.class);
        ConditionParameterName conditionParameterName1 = mock(ConditionParameterName.class);

        // branch length = 0
        Assert.assertTrue(conditionSegments.size() == 0);
        result = ConditionDeterminer.isConditionMet(conditionSegments);
        Assert.assertEquals(true, result);
        // branch not 0
        conditionSegments.add(conditionSegment);
        conditionSegments.add(conditionSegment1);
        when(conditionSegment.getPrecursorRelationOperator()).thenReturn(Not);
        when(conditionSegment1.getPrecursorRelationOperator()).thenReturn(Or);
        when(conditionSegment.getConditionParameterName()).thenReturn(conditionParameterName);
        when(conditionSegment1.getConditionParameterName()).thenReturn(conditionParameterName1);
        when(conditionParameterName.getValue())
                .thenReturn(new String("test"))//do not get into method "isTimeConditionMet" and return false
                .thenReturn(new String("time"));//get into method "isTimeConditionMet"
        when(conditionParameterName1.getValue())
                .thenReturn(new String("test"))//do not get into method "isTimeConditionMet" and return false
                .thenReturn(new String("time"));//get into method "isTimeConditionMet"

        result = ConditionDeterminer.isConditionMet(conditionSegments);
        Assert.assertTrue(result == false);
        Assert.assertTrue(conditionSegments.get(0) == conditionSegment && conditionSegments.get(1) == conditionSegment1);
        verify(conditionSegment, times(2)).getPrecursorRelationOperator();
        verify(conditionSegment1, times(2)).getPrecursorRelationOperator();

        // test time
        when(conditionSegment.getConditionParameterMatchPattern())
                .thenReturn(ConditionSegment.ConditionParameterMatchPattern.GreaterThan
                )//case GreaterThan  return false
                .thenReturn(ConditionSegment.ConditionParameterMatchPattern.NotGreaterThan
                )//case NotGreaterThan return false
                .thenReturn(ConditionSegment.ConditionParameterMatchPattern.LessThan
                );//case LessThan: return false
        when(conditionSegment.getConditionParameterTargetValue()).thenReturn(conditionParameterTargetValue);
        when(conditionParameterTargetValue.getStringValue())
                .thenReturn(new String(""))
                .thenReturn(new String(""))
                .thenReturn(new String(""));
        when(conditionSegment1.getConditionParameterMatchPattern())
                .thenReturn(ConditionSegment.ConditionParameterMatchPattern.NotLessThan
                )//case NotLessThan  return can be not true
                .thenReturn(ConditionSegment.ConditionParameterMatchPattern.Between
                )//case Between return can be not true
                .thenReturn(ConditionSegment.ConditionParameterMatchPattern.GreaterThan
                );//case GreaterThan  return false
        when(conditionSegment1.getConditionParameterTargetValue()).thenReturn(conditionParameterTargetValue1);
        when(conditionParameterTargetValue1.getStringValue())
                .thenReturn(new String("2011-11-11 11:11:11"))
                .thenReturn(new String("1991-01-31 11:11:11"))
                .thenReturn(new String(""));

        result = ConditionDeterminer.isConditionMet(conditionSegments);
        Assert.assertTrue(result == true);
        result = ConditionDeterminer.isConditionMet(conditionSegments);
        Assert.assertTrue(result == false);
        result = ConditionDeterminer.isConditionMet(conditionSegments);
        Assert.assertTrue(result == false);
        verify(conditionSegment, times(5)).getPrecursorRelationOperator();
        verify(conditionSegment1, times(5)).getPrecursorRelationOperator();
    }

    @Test
    public void testMain() throws Exception {
        ConditionDeterminer.main(new String[]{"1","2"});
    }

    @Test
    public void testSortSegments() throws Exception {
        final List<ConditionSegment> conditionSegments;
    }
}