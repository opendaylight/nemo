/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.renderer.openflow;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.nemo.renderer.openflow.physicalnetwork.PhyConfigLoader;
import org.opendaylight.nemo.renderer.openflow.physicalnetwork.PhysicalNetworkAdapter;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenflowRenderer implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(OpenflowRenderer.class);

    private final DataBroker dataBroker;
    private final NotificationProviderService notificationProviderService;
    private final PacketProcessingService packetProcessingService;

    private final PhyConfigLoader phyConfigLoader;
    private final FlowTableManager flowTableMng;
    private PhysicalNetworkAdapter physicalNetworkAdapter;

    public OpenflowRenderer(DataBroker dataBroker,
                            NotificationProviderService notificationProviderService,
                            PacketProcessingService packetProcessingService) {
        super();

        this.dataBroker = dataBroker;
        this.notificationProviderService = notificationProviderService;
        this.packetProcessingService = packetProcessingService;

        phyConfigLoader = new PhyConfigLoader(dataBroker);
        LOG.debug("New FlowTableManager.");
        flowTableMng = new FlowTableManager(dataBroker
                , packetProcessingService
                , phyConfigLoader);
        physicalNetworkAdapter = new PhysicalNetworkAdapter(dataBroker
                , notificationProviderService
                , phyConfigLoader
                , flowTableMng.getFlowUtils());

        LOG.info("Initialized the NEMO OpenFlow renderer.");
    }

    // *************
    // AutoCloseable
    // *************

    @Override
    public void close() throws Exception {
        if (flowTableMng != null) flowTableMng.close();
        if (physicalNetworkAdapter != null) physicalNetworkAdapter.close();
    }
}
