/*
 * Copyright (c) 2016 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.tool.sandbox.models;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.nemo.tool.sandbox.models.Connector;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import static org.junit.Assert.*;

/**
 * Created by Thomas Liu on 2016/1/14.
 */
public class ConnectorTest extends TestCase {
    private int order;
    private String nodeName;
    private Connector connectorTest;

    @Before
    public void setUp() throws Exception {
        order = 10;
        nodeName = "connector";
        connectorTest = new Connector(nodeName,order);

    }

    @Test
    public void testGetConnectorName() throws Exception {
        Assert.assertNotNull(connectorTest.getConnectorName());
    }

    @Test
    public void testGetOrder() throws Exception {
        Assert.assertNotNull(connectorTest.getOrder());
    }

    @Test
    public void testGetNodeName() throws Exception {
        Assert.assertNotNull(connectorTest.getNodeName());
    }

    @Test
    public void testEquals() throws Exception {
        Assert.assertNotNull(connectorTest.equals(this));

    }

    @Test
    public void testHashCode() throws Exception {
        Assert.assertNotNull(connectorTest.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertNotNull(connectorTest.toString());
    }

    @Test
    public void testCompareTo() throws Exception {
        Assert.assertNotNull(connectorTest.compareTo(new Connector("connector2",5)));
    }
}