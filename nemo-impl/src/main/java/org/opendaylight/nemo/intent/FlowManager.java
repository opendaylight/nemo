/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Maintain the information of the user's flows. For example, query
 * the values of the properties of the flow, such as the path of the
 * flow, and store them into the data store.
 *
 * @author Zhigang Ji
 */
public class FlowManager {
    private static final Logger LOG = LoggerFactory.getLogger(FlowManager.class);

    private final DataBroker dataBroker;

    public FlowManager(DataBroker dataBroker) {
        super();

        this.dataBroker = dataBroker;

        LOG.debug("Initialized the renderer common flow manager.");

        return;
    }

    /**
     * Query the values of the properties of the flow, which are read-only
     * for the user, and store them into the data store.
     *
     * @param userId The user id for the flow.
     * @param flow The flow to be resolved.
     */
    protected void resolveFlow(UserId userId, Flow flow) throws IntentResolutionException {
        // TODO

        return;
    }
}
