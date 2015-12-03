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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;

import java.util.Arrays;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hj on 11/19/15.
 */
public class ConditionHandlerImpl implements IConditionHandler {
    private static int INTERVAL = 400;
    private ConcurrentHashMap<String, Timer> timers;
    private IntentResolver intentResolver;
    private UserId userId;
    private byte[] mutex = new byte[1];

    public ConditionHandlerImpl(IntentResolver intentResolver,UserId userId) {
        this.intentResolver = intentResolver;
        this.userId =  userId;
        this.timers = new ConcurrentHashMap<String, Timer>();
    }

    @Override
    public void handleCondition(Operation operation) {
        synchronized (mutex) {
            String name = operation.getOperationId().getValue();
            Timer timer = new Timer("name");
            ConditionMonitor conditionMonitor = new ConditionMonitor(intentResolver,userId,operation.getConditionSegment());
            timer.schedule(conditionMonitor, 1, INTERVAL);
            timers.put(name, timer);
        }
    }

    @Override
    public void clearCondition() {
        synchronized (mutex) {
            for (Timer timer : timers.values()) {
                timer.cancel();
            }
            timers.clear();
        }
    }

    @Override
    public String toString() {
        return "ConditionHandlerImpl{" +
                "timers=" + timers +
                ", intentResolver=" + intentResolver +
                ", userId=" + userId +
                ", mutex=" + Arrays.toString(mutex) +
                '}';
    }
}
