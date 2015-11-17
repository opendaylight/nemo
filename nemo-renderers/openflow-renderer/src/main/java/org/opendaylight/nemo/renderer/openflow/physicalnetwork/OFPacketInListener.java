/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.renderer.openflow.physicalnetwork;

import org.opendaylight.controller.liblldp.EtherTypes;
import org.opendaylight.controller.liblldp.Ethernet;
import org.opendaylight.controller.liblldp.NetUtils;
import org.opendaylight.controller.liblldp.PacketException;
import org.opendaylight.nemo.renderer.openflow.FlowUtils;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketReceived;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hj on 11/10/15.
 */
public class OFPacketInListener implements PacketProcessingListener {
    private static final Logger log = LoggerFactory.getLogger(OFPacketInListener.class);
    private FlowUtils ofFlowUtils;
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
            ethernet.deserialize(payload, 0, NetUtils.NumBitsInAByte * payload.length);
        } catch (PacketException e) {
            log.warn("Failed to decode packet in message: {}", packetReceived);
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
            log.debug("Receive one arp packet in message:\r\n {}.", packetReceived);
            ofFlowUtils.handleArp(packetReceived,ingressNodeConnectorRef);
        }
    }
}
