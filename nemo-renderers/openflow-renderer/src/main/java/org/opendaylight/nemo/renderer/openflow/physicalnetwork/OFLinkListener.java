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
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.topology.Link;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hj on 11/5/15.
 */
public class OFLinkListener implements DataTreeChangeListener<Link> {
    private static final Logger log = LoggerFactory.getLogger(OFLinkListener.class);
    final private PhysicalNetworkAdapter pnConverter;

    protected OFLinkListener(PhysicalNetworkAdapter pnConverter) {
        this.pnConverter = pnConverter;
    }

    @Override
    public void onDataTreeChanged(Collection<DataTreeModification<Link>> changes) {
        for (DataTreeModification<Link> change: changes) {
            DataObjectModification<Link> rootNode = change.getRootNode();
            switch (rootNode.getModificationType()) {
                case WRITE:
                case SUBTREE_MODIFIED:
                    final Link updatedLink = rootNode.getDataAfter();
                    log.debug("OF link updated:{}", updatedLink.getKey());
                    pnConverter.ofLinkAdded(updatedLink);
                    break;
                case DELETE:
                    final Link deletedLink = rootNode.getDataBefore();
                    log.debug("OF link removed:{}", deletedLink.getKey());
                    pnConverter.ofLinkRemoved(deletedLink);
                    break;
                default:
                    break;
            }
        }
    }
}
