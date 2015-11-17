/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.renderer.openflow.physicalnetwork;

import org.opendaylight.controller.md.sal.binding.api.DataChangeListener;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataChangeEvent;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * Created by hj on 11/5/15.
 */
public class OFNodeListener implements DataChangeListener {
    private static final Logger log = LoggerFactory.getLogger(OFNodeListener.class);
    final private PhysicalNetworkAdapter pnConverter;

    protected OFNodeListener(PhysicalNetworkAdapter pnConverter) {
        this.pnConverter = pnConverter;
    }

    @Override
    public void onDataChanged(AsyncDataChangeEvent<InstanceIdentifier<?>, DataObject> change) {
        if (null == change) {
            return;
        }
        Node tmpOfNode = null;

        Map<InstanceIdentifier<?>, DataObject> createdData = change.getCreatedData();
        if (null != createdData && !createdData.isEmpty()) {
            for (DataObject data : createdData.values()) {
                if (!(data instanceof Node)) {
                    log.warn("PFNodeListener accept an [{}] created event.", data);
                    break;
                }
                tmpOfNode = (Node)data;
                log.debug("OF node added:{}",tmpOfNode.getKey());
                pnConverter.ofNodeAdded(tmpOfNode);
            }
        }

        Map<InstanceIdentifier<?>, DataObject> updateData = change.getUpdatedData();
        if (null != updateData && !updateData.isEmpty()) {
            for (DataObject data : updateData.values()) {
                if (!(data instanceof Node)) {
                    log.warn("PFNodeListener accept an [{}] update event.", data);
                    break;
                }
                tmpOfNode = (Node)data;
                log.debug("OF node updated:{}",tmpOfNode.getKey());
                pnConverter.ofNodeAdded(tmpOfNode);
            }
        }

        Map<InstanceIdentifier<?>, DataObject> originalData = change.getOriginalData();
        Set<InstanceIdentifier<?>> removedPaths = change.getRemovedPaths();

        if ( null != removedPaths && !removedPaths.isEmpty() ) {
            DataObject dataObject ;
            for ( InstanceIdentifier<?> instanceId : removedPaths ) {
                dataObject = originalData.get(instanceId);

                if ( null != dataObject && dataObject instanceof Node) {
                    log.debug("OF node removed:{}",((Node)dataObject).getKey());
                    pnConverter.ofNodeRemoved((Node)dataObject);
                }
            }
        }

    }
}
