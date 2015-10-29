/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.NodeInstance;

/**
 * Created by z00293636 on 2015/9/6.
 */
public class VNSpaceManagement {
    private DataBroker dataBroker;

    public VNSpaceManagement(DataBroker dataBroker)
    {
        this.dataBroker = dataBroker;
    }

    public NodeInstance getNodeInstance(UserId userId, NodeId nodeId) {
        // TODO

        return null;
    }

}
