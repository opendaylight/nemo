/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.renderer.cli;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
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

    public CliRenderer(DataBroker dataBroker) {
        super();

        this.dataBroker = dataBroker;

        LOG.info("Initialized the NEMO CLI renderer.");

        return;
    }

    @Override
    public void close() throws Exception {
        // TODO
    }
}
