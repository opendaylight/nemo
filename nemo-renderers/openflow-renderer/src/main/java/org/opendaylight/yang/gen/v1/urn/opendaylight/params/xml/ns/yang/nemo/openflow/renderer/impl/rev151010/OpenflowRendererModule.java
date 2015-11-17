/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.openflow.renderer.impl.rev151010;

import org.opendaylight.nemo.renderer.openflow.OpenflowRenderer;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenflowRendererModule extends org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.openflow.renderer.impl.rev151010.AbstractOpenflowRendererModule {

    private static final Logger LOG = LoggerFactory.getLogger(OpenflowRenderer.class);

    public OpenflowRendererModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public OpenflowRendererModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.openflow.renderer.impl.rev151010.OpenflowRendererModule oldModule, java.lang.AutoCloseable oldInstance) {
        super(identifier, dependencyResolver, oldModule, oldInstance);
    }

    @Override
    public void customValidation() {
        // add custom validation form module attributes here.
    }

    @Override
    public java.lang.AutoCloseable createInstance() {
        LOG.debug("Openflow Renderer createInstance()");
        final OpenflowRenderer renderer = new OpenflowRenderer(
                getDataBrokerDependency(), getNotificationServiceDependency(),
                getRpcRegistryDependency().getRpcService(PacketProcessingService.class));

        final class CloseResources implements AutoCloseable {
            @Override
            public void close() throws Exception {
                if (renderer != null) {
                    renderer.close();
                }
                LOG.debug("Openflow Renderer (instance {}) tear down.", this);
            }
        }

        return new CloseResources();
    }

}
