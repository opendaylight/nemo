/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.renderer.openflow.physicalnetwork;

import java.util.Collection;
import org.opendaylight.controller.md.sal.binding.api.DataObjectModification;
import org.opendaylight.controller.md.sal.binding.api.DataTreeChangeListener;
import org.opendaylight.controller.md.sal.binding.api.DataTreeModification;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hj on 11/5/15.
 */
public class OFNodeListener implements DataTreeChangeListener<Node> {
    private static final Logger log = LoggerFactory.getLogger(OFNodeListener.class);
    final private PhysicalNetworkAdapter pnConverter;

    protected OFNodeListener(PhysicalNetworkAdapter pnConverter) {
        this.pnConverter = pnConverter;
    }

    @Override
    public void onDataTreeChanged(Collection<DataTreeModification<Node>> changes) {
        for (DataTreeModification<Node> change: changes) {
            DataObjectModification<Node> rootNode = change.getRootNode();
            switch (rootNode.getModificationType()) {
                case WRITE:
                case SUBTREE_MODIFIED:
                    final Node updatedNode = rootNode.getDataAfter();
                    log.debug("OF node updated:{}", updatedNode.getKey());
                    pnConverter.ofNodeAdded(updatedNode);
                    break;
                case DELETE:
                    final Node deletedNode = rootNode.getDataBefore();
                    log.debug("OF node removed:{}", deletedNode.getKey());
                    pnConverter.ofNodeRemoved(deletedNode);
                    break;
                default:
                    break;
            }
        }
    }
}
