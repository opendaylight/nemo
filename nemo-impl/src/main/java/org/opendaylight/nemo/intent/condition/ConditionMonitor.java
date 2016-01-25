/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent.condition;

import org.opendaylight.nemo.intent.IntentResolver;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by hj on 11/19/15.
 * TimerTask for one condition.
 */
public class ConditionMonitor extends TimerTask {
    private static Logger log = LoggerFactory.getLogger(ConditionMonitor.class);
    private List<ConditionSegment> conditionSegments;
    //Handle operation when condition state changed
    private IntentResolver intentResolver;
    private boolean preState;
    private UserId userId;

    public ConditionMonitor(IntentResolver intentResolver, UserId userId, List<ConditionSegment> conditionSegments) {
        this.conditionSegments = conditionSegments;
        this.intentResolver = intentResolver;
        this.userId = userId;
        preState = ConditionDeterminer.isConditionMet(conditionSegments);
    }

    @Override
    public void run() {
        boolean currentState = ConditionDeterminer.isConditionMet(conditionSegments);
        if (currentState != preState) {
            try {
                intentResolver.resolveIntent(userId);
            } catch (Exception e) {
                //TODO Auto-generated catch block
                log.error("Exception:",e);
            }
            preState = currentState;
        }
    }
}
