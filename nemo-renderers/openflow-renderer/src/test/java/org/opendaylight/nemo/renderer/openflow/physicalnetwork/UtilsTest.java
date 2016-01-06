/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.renderer.openflow.physicalnetwork;
import org.junit.Assert;
import org.junit.Test;
import org.opendaylight.nemo.renderer.openflow.physicalnetwork.Utils;

import static org.junit.Assert.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNodeInstance;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalPortInstance;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/11/30.
 */
public class UtilsTest extends Utils {

    @Test
    public void testGetNodeType() throws Exception {
        String strType = "switch";
        Assert.assertTrue(Utils.getNodeType(strType) == PhysicalNodeInstance.NodeType.Switch);
        strType = "router";
        Assert.assertTrue(Utils.getNodeType(strType) == PhysicalNodeInstance.NodeType.Router);
        strType = "firewall";
        Assert.assertTrue(Utils.getNodeType(strType) == PhysicalNodeInstance.NodeType.Firewall);
        strType = "loadbalancer";
        Assert.assertTrue(Utils.getNodeType(strType) == PhysicalNodeInstance.NodeType.Loadbalancer);
        strType = "test";
        Assert.assertTrue(Utils.getNodeType(strType) == null);
    }

    @Test
    public void testGetPortType() throws Exception {
        String strType = "external";
        Assert.assertTrue(Utils.getPortType(strType) == PhysicalPortInstance.PortType.External);
        strType = "test";
        Assert.assertTrue(Utils.getPortType(strType) == PhysicalPortInstance.PortType.Internal);
    }

    @Test
    public void testReadFile() throws Exception {
        String Path = new String(".");
        Assert.assertNotNull(Utils.readFile(Path));
        Path = "./test";
        Assert.assertNotNull(Utils.readFile(Path));
        Assert.assertTrue(Utils.readFile(Path) == "");
    }
}