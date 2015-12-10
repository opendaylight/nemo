/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.renderer.cli;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.renderer.cli.physicalnetwork.PhysicalResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NEMO renderer that uses CLI to implement an overlay network
 * using traditional network devices.
 *
 * @author Zhigang Ji
 */
public class CliRenderer implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(CliRenderer.class);

    private final DataBroker dataBroker;

    private final PhysicalResourceLoader physicalResourceLoader;
    private final CliTrigger cliTrigger;

    /**
     *
     * @param dataBroker
     */
	public CliRenderer(DataBroker dataBroker) {
		super();

        this.dataBroker = dataBroker;

        LOG.debug("new PhysicalResourceLoader.");
        physicalResourceLoader = new PhysicalResourceLoader(dataBroker);

        LOG.debug("new CliTrigger.");
        cliTrigger = new CliTrigger(dataBroker);

		LOG.info("Initialized cli renderer.");

        return;
    }

    /**
     *
     * @throws Exception
     */
	@Override
    public void close() throws Exception {
        if (physicalResourceLoader != null){
            physicalResourceLoader.close();
        }

        if (cliTrigger != null){
            cliTrigger.close();
        }

		return;
    }
}
