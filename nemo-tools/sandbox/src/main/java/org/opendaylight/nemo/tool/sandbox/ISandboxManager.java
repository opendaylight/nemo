/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.tool.sandbox;

import java.util.List;

/**
 * Created by hj on 12/14/15.
 */
public interface ISandboxManager {
    public boolean createNetwork(List<String> commands);

    public boolean createNetwork();

    public boolean destroyNetwork();

    public String getHosts();

    public String getSwitches();

    public String getLinks();

    public String getExternals();

    public String execute(String name, String command);
}
