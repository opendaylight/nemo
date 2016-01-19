/*
 * Copyright (c) 2016 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.tool.sandbox.northbound;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import org.codehaus.enunciate.jaxrs.ResponseCode;
import org.codehaus.enunciate.jaxrs.StatusCodes;
import org.codehaus.enunciate.jaxrs.TypeHint;
import org.junit.runner.RunWith;
import org.opendaylight.nemo.tool.sandbox.SandBoxManager;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2016/1/14.
 */

public class SandboxNorthboundTest extends TestCase {
    private SandboxNorthbound sandboxNorthbound;
    @Before
    public void setUp() throws Exception {
        sandboxNorthbound = new SandboxNorthbound();
    }

    @Test
    public void testCreate() throws Exception {
        ExecuteRequest executeRequest = new ExecuteRequest();
        Assert.assertTrue(sandboxNorthbound.create(null,"test") != null);
        Assert.assertTrue(sandboxNorthbound.create(null,executeRequest) != null);
    }

    @Test
    public void testDestroy() throws Exception {
        Assert.assertTrue(sandboxNorthbound.destroy(null) != null);
    }

    @Test
    public void testGetXml() throws Exception {
        Assert.assertTrue(sandboxNorthbound.getXml() != null);
    }

    @Test
    public void testGetHosts() throws Exception {
        Assert.assertTrue(sandboxNorthbound.getHosts() == null);
    }

    @Test
    public void testGetSwitches() throws Exception {
        Assert.assertTrue(sandboxNorthbound.getSwitches() != null);
    }

    @Test
    public void testGetLinks() throws Exception {
        Assert.assertTrue(sandboxNorthbound.getLinks() != null);
    }

    @Test
    public void testGetExternals() throws Exception {
        Assert.assertTrue(sandboxNorthbound.getExternals() == null);
    }
}