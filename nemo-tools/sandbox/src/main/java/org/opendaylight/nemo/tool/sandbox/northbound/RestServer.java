/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.tool.sandbox.northbound;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class RestServer {
    // Base URI the Grizzly HTTP server will listen on
    private static HttpServer server;

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined inN this application.
     *
     *@param socket todo 
     */
    public static void start(String socket) {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.example package
        final ResourceConfig rc = new ResourceConfig().packages("org.opendaylight.nemo.tool.sandbox.northbound");

        // create and start a new instance of grizzly http serverssss
        // exposing the Jersey application at BASE_URI
        server = GrizzlyHttpServerFactory.createHttpServer(URI.create(socket), rc);
    }

    public static void stop() {
        if (server != null) {
            server.shutdown();
            server = null;
        }
    }
}
