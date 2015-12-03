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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by j00212933 on 2015/11/23.
 */
public class ConditionManager implements IConditionManager {
    /**
     * TODO
     */
    private Map<UserId, IConditionHandler> conditionHandlers = null;
    private IntentResolver intentResolver;

    public ConditionManager(IntentResolver intentResolver) {
        this.conditionHandlers = new ConcurrentHashMap<UserId, IConditionHandler>();
        this.intentResolver = intentResolver;
    }

    public void handleOperation(UserId userId, Operation operation) {
        if (!conditionHandlers.containsKey(userId)) {
            conditionHandlers.put(userId, new ConditionHandlerImpl(intentResolver, userId));
        }
        conditionHandlers.get(userId).handleCondition(operation);
    }

    public void clear(UserId userId) {
        IConditionHandler conditionHandler = conditionHandlers.remove(userId);
        if (conditionHandler != null) {
            conditionHandler.clearCondition();
        }
    }

}
