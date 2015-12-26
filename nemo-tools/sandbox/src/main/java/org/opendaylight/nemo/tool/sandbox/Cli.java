/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.tool.sandbox;

import asg.cliche.Command;
import asg.cliche.Shell;
import asg.cliche.ShellDependent;

/**
 * Created by hj on 12/9/15.
 */
public class Cli implements ShellDependent {
    private Shell shell;

    public Cli() {

    }

    @Override
    public void cliSetShell(Shell shell) {
        this.shell = shell;
    }

    @Command
    public void install() {
        SandBoxManager.getInstance().createNetwork();
    }

    @Command
    public void uninstall() {
        SandBoxManager.getInstance().destroyNetwork();
    }
}
