/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.renderer.openflow.utils;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.opendaylight.controller.liblldp.BitBufferHelper;
import org.opendaylight.controller.liblldp.Packet;
import org.opendaylight.nemo.renderer.openflow.utils.ARP;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/11/24.
 */
public class ARPTest extends TestCase {
    private ARP arp;
    private ARP arp1;
    private ARP arp2;

    @Before
    public void setUp() throws Exception {
        arp = new ARP();
        arp1 = new ARP(true);
        arp2 = new ARP(false);
    }


    @Test
    public void testHardwareType() throws Exception {
        short hardwaretype = 0;

        // set
        arp1.setHardwareType(hardwaretype);
        arp2.setHardwareType(hardwaretype);
        arp.setHardwareType(hardwaretype);

        //test get
        Assert.assertEquals(0, arp.getHardwareType());
        Assert.assertEquals(0,arp1.getHardwareType());
        Assert.assertEquals(0,arp2.getHardwareType());

        hardwaretype = 1;

        // set
        arp1.setHardwareType(hardwaretype);
        arp2.setHardwareType(hardwaretype);
        arp.setHardwareType(hardwaretype);

        //test get
        Assert.assertEquals(1, arp.getHardwareType());
        Assert.assertEquals(1,arp1.getHardwareType());
        Assert.assertEquals(1,arp2.getHardwareType());
    }

    @Test
    public void testProtocolType() throws Exception {
        short protocoltype = 0;

        //set
        arp.setProtocolType(protocoltype);
        arp1.setProtocolType(protocoltype);
        arp2.setProtocolType(protocoltype);

        //test get
        Assert.assertEquals(0,arp.getProtocolType());
        Assert.assertEquals(0,arp1.getProtocolType());
        Assert.assertEquals(0,arp2.getProtocolType());

         protocoltype = 1;

        //set
        arp.setProtocolType(protocoltype);
        arp1.setProtocolType(protocoltype);
        arp2.setProtocolType(protocoltype);

        //test get
        Assert.assertEquals(1,arp.getProtocolType());
        Assert.assertEquals(1,arp1.getProtocolType());
        Assert.assertEquals(1,arp2.getProtocolType());
    }

    @Test
    public void testHardwareAddressLength() throws Exception {
        byte hardwareaddresslength = 0;

        //set
        arp.setHardwareAddressLength(hardwareaddresslength);
        arp1.setHardwareAddressLength(hardwareaddresslength);
        arp2.setHardwareAddressLength(hardwareaddresslength);

        //test get
        Assert.assertEquals(0,arp.getHardwareAddressLength());
        Assert.assertEquals(0,arp1.getHardwareAddressLength());
        Assert.assertEquals(0,arp2.getHardwareAddressLength());
         hardwareaddresslength = 1;

        //set
        arp.setHardwareAddressLength(hardwareaddresslength);
        arp1.setHardwareAddressLength(hardwareaddresslength);
        arp2.setHardwareAddressLength(hardwareaddresslength);

        //test get
        Assert.assertEquals(1,arp.getHardwareAddressLength());
        Assert.assertEquals(1,arp1.getHardwareAddressLength());
        Assert.assertEquals(1,arp2.getHardwareAddressLength());
    }

    @Test
    public void testProtocolAddressLength() throws Exception {
        byte protocoladdresslength = 0;

        //set
        arp.setProtocolAddressLength(protocoladdresslength);
        arp1.setProtocolAddressLength(protocoladdresslength);
        arp2.setProtocolAddressLength(protocoladdresslength);

        //test get
        Assert.assertEquals(0,arp.getProtocolAddressLength());
        Assert.assertEquals(0,arp1.getProtocolAddressLength());
        Assert.assertEquals(0,arp2.getProtocolAddressLength());
         protocoladdresslength = 1;

        //set
        arp.setProtocolAddressLength(protocoladdresslength);
        arp1.setProtocolAddressLength(protocoladdresslength);
        arp2.setProtocolAddressLength(protocoladdresslength);

        //test get
        Assert.assertEquals(1,arp.getProtocolAddressLength());
        Assert.assertEquals(1,arp1.getProtocolAddressLength());
        Assert.assertEquals(1,arp2.getProtocolAddressLength());
    }

    @Test
    public void testOpCode() throws Exception {
        short opcode = 0;

        //set
        arp.setOpCode(opcode);
        arp1.setOpCode(opcode);
        arp2.setOpCode(opcode);

        //test get
        Assert.assertEquals(0, arp.getOpCode());
        Assert.assertEquals(0, arp1.getOpCode());
        Assert.assertEquals(0,arp2.getOpCode());

        //set
        opcode = 1;
        arp.setOpCode(opcode);
        arp1.setOpCode(opcode);
        arp2.setOpCode(opcode);

        //test get
        Assert.assertEquals(1, arp.getOpCode());
        Assert.assertEquals(1, arp1.getOpCode());
        Assert.assertEquals(1,arp2.getOpCode());
    }

    @Test
    public void testSenderHardwareAddress() throws Exception {
        byte []senderhardwareaddrss = {0};

        //set
        arp.setSenderHardwareAddress(senderhardwareaddrss);
        arp1.setSenderHardwareAddress(senderhardwareaddrss);
        arp2.setSenderHardwareAddress(senderhardwareaddrss);

        //test get
        Assert.assertEquals(senderhardwareaddrss, arp.getSenderHardwareAddress());
        Assert.assertEquals(senderhardwareaddrss,arp1.getSenderHardwareAddress());
        Assert.assertEquals(senderhardwareaddrss,arp2.getSenderHardwareAddress());
    }

    @Test
    public void testTargetHardwareAddress() throws Exception {
        byte []targethardwareaddress = {0};

        //set
        arp.setTargetHardwareAddress(targethardwareaddress);
        arp1.setTargetHardwareAddress(targethardwareaddress);
        arp2.setTargetHardwareAddress(targethardwareaddress);

        //test get
        Assert.assertEquals(targethardwareaddress,arp.getTargetHardwareAddress());
        Assert.assertEquals(targethardwareaddress,arp1.getTargetHardwareAddress());
        Assert.assertEquals(targethardwareaddress,arp2.getTargetHardwareAddress());


    }

    @Test
    public void testTargetProtocolAddress() throws Exception {
        byte []targetpotocoladdress = {0};

        //set
        arp.setTargetProtocolAddress(targetpotocoladdress);
        arp1.setTargetProtocolAddress(targetpotocoladdress);
        arp2.setTargetProtocolAddress(targetpotocoladdress);

        //test get
        Assert.assertEquals(targetpotocoladdress,arp.getTargetProtocolAddress());
        Assert.assertEquals(targetpotocoladdress,arp1.getTargetProtocolAddress());
        Assert.assertEquals(targetpotocoladdress,arp2.getTargetProtocolAddress());

    }

    @Test
    public void testSenderProtocolAddress() throws Exception {
        byte []senderprotocoladdress = {0};

        //set
        arp.setSenderProtocolAddress(senderprotocoladdress);
        arp1.setSenderProtocolAddress(senderprotocoladdress);
        arp2.setSenderProtocolAddress(senderprotocoladdress);

        //test get
        Assert.assertEquals(senderprotocoladdress,arp.getSenderProtocolAddress());
        Assert.assertEquals(senderprotocoladdress,arp1.getSenderProtocolAddress());
        Assert.assertEquals(senderprotocoladdress,arp2.getSenderProtocolAddress());

    }

    @Test
    public void testHashCode() throws Exception {
        arp.hashCode();
        arp1.hashCode();
        arp2.hashCode();
        Assert.assertNotEquals(0, arp.hashCode());
        Assert.assertNotEquals(0,arp1.hashCode());
        Assert.assertNotEquals(0,arp2.hashCode());

    }

    @Test
    public void testEquals() throws Exception {
        Assert.assertTrue(arp.equals(arp));
        Assert.assertTrue(arp.equals(arp1));
        Assert.assertTrue(arp1.equals(arp2));
    }
}