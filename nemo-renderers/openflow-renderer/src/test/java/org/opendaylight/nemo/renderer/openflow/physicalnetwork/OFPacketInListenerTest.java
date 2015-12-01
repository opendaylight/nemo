package org.opendaylight.nemo.renderer.openflow.physicalnetwork;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.controller.liblldp.EtherTypes;
import org.opendaylight.controller.liblldp.Ethernet;
import org.opendaylight.controller.liblldp.NetUtils;
import org.opendaylight.controller.liblldp.PacketException;
import org.opendaylight.nemo.renderer.openflow.FlowUtils;
import org.opendaylight.nemo.renderer.openflow.physicalnetwork.OFPacketInListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketReceived;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/11/24.
 */
public class OFPacketInListenerTest extends TestCase {
    private OFPacketInListener ofPacketInListener;
    private FlowUtils flowUtils;
    @Before
    public void setUp() throws Exception {
        flowUtils = mock(FlowUtils.class);
        ofPacketInListener = new OFPacketInListener(flowUtils);

    }

    @Test//to be resolve
    public void testOnPacketReceived() throws Exception {
//        byte bt[] = new byte[0];
//        PacketReceived packetReceived = mock(PacketReceived.class);
//        when(packetReceived.getPayload()).thenReturn(bt);
//        when(packetReceived.getIngress()).thenReturn(mock(NodeConnectorRef.class));

        ofPacketInListener.onPacketReceived(null);
        Assert.assertNotNull(ofPacketInListener);

    }
}