/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.renderer.openflow;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

public class OpenflowRenderer implements AutoCloseable {
	
	private static final Logger LOG = LoggerFactory.getLogger(OpenflowRenderer.class);
	
	private DataBroker dataBroker;
	private final ResourceManager resourceManager;
	private final FlowTableManager flowTableMng;
	
	public OpenflowRenderer(DataBroker dataBroker) {
		super();
        this.dataBroker = dataBroker;
		System.out.println();
		System.out.println("Waiting for loading config file about 30s...");

		LOG.info("New ResourceManager.");
		resourceManager = new ResourceManager(dataBroker);

		LOG.info("New FlowTableManager.");
		flowTableMng = new FlowTableManager(dataBroker, resourceManager);

		LOG.info("Initialized openflow renderer.");
    }
	
	// *************
    // AutoCloseable
    // *************

    @Override
    public void close() throws Exception {
		if (flowTableMng != null) flowTableMng.close();
		if (resourceManager != null) resourceManager.close();
    }
}

