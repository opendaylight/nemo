/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.renderer.openflow.physicalnetwork;

import org.opendaylight.nemo.renderer.openflow.FlowUtils;
import org.opendaylight.openflowplugin.libraries.liblldp.EtherTypes;
import org.opendaylight.openflowplugin.libraries.liblldp.Ethernet;
import org.opendaylight.openflowplugin.libraries.liblldp.NetUtils;
import org.opendaylight.openflowplugin.libraries.liblldp.PacketException;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketReceived;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hj on 11/10/15.
 */
public class OFPacketInListener implements PacketProcessingListener {
    private static final Logger LOG = LoggerFactory.getLogger(OFPacketInListener.class);
    private final FlowUtils ofFlowUtils;
    public OFPacketInListener(FlowUtils ofFlowUtils) {
        this.ofFlowUtils = ofFlowUtils;
    }

    @Override
    public void onPacketReceived(PacketReceived packetReceived) {
        if (null == packetReceived) {
            return;
        }
        byte[] payload = packetReceived.getPayload();
        Ethernet ethernet = new Ethernet();
        try {
            ethernet.deserialize(payload, 0, NetUtils.NUM_BITS_IN_A_BYTE * payload.length);
        } catch (PacketException e) {
            LOG.warn("Failed to decode packet in message: {}", packetReceived);
        }
        if (EtherTypes.ARP.shortValue() == ethernet.getEtherType()) {
            NodeConnectorRef ingressNodeConnectorRef = packetReceived.getIngress();
//            String strNodeId = ingressNodeConnectorRef
//                    .getValue()
//                    .firstIdentifierOf(Node.class)
//                    .firstKeyOf(Node.class)
//                    .getId()
//                    .getValue();
//            String strPortId = ingressNodeConnectorRef
//                    .getValue()
//                    .firstIdentifierOf(NodeConnector.class)
//                    .firstKeyOf(NodeConnector.class)
//                    .getId()
//                    .getValue();
            LOG.debug("Receive one arp packet in message:\r\n {}.", packetReceived);
            ofFlowUtils.handleArp(packetReceived,ingressNodeConnectorRef);
        }
    }
}
