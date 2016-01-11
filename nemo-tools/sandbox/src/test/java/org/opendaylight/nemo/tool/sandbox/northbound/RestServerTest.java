/*
 * Copyright (c) 2016 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.tool.sandbox.northbound;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.Reader;
import java.net.URI;
/**
 * Created by zhangmeng on 2016/1/14.
 */
public class RestServerTest extends TestCase {
    private RestServer restServer;
    @Before
    public void setUp() throws Exception {
        restServer = new RestServer();
    }

    @Test
    public void testStart() throws Exception {
//        RestServer.start("s1");
    }

    @Test
    public void testStop() throws Exception {
        RestServer.stop();
    }
}