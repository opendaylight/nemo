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
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.opendaylight.nemo.tool.sandbox.CmdExecutor;
import org.opendaylight.nemo.tool.sandbox.models.Connector;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by Thomas Liu on 2016/1/14.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({CmdExecutor.class})
public class LinkTest extends TestCase {

    private Connector srcInterface;
    private Connector dstInterface;
    private Link likeTest;

    @Before
    public void setUp() throws Exception {
        srcInterface = new Connector("src",1);
        dstInterface = new Connector("dst",2);
        likeTest = new Link(srcInterface,dstInterface);

    }

    @Test
    public void testGetSrcConnector() throws Exception {
        Assert.assertNotNull(likeTest.getSrcConnector());
    }

    @Test
    public void testGetDstConnector() throws Exception {
        Assert.assertNotNull(likeTest.getDstConnector());
    }

    @Test
    public void testInstall() throws Exception {
        PowerMockito.mockStatic(CmdExecutor.class);
        PowerMockito.when(CmdExecutor.sshExecute(any(String.class))).thenReturn("null");
        likeTest.install();
    }

    @Test
    public void testUninstall() throws Exception {
        PowerMockito.mockStatic(CmdExecutor.class);
        PowerMockito.when(CmdExecutor.sshExecute(any(String.class))).thenReturn("null");
        likeTest.uninstall();
    }

    @Test
    public void testEquals() throws Exception {
        Assert.assertNotNull(likeTest.equals(this));
    }

    @Test
    public void testHashCode() throws Exception {
        Assert.assertNotNull(likeTest.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertNotNull(likeTest.toString());
    }
}