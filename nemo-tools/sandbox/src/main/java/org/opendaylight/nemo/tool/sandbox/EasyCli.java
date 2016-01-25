/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.tool.sandbox;

import asg.cliche.Shell;
import asg.cliche.ShellFactory;

import java.io.IOException;

/**
 * Created by hj on 12/9/15.
 */
public class EasyCli {
    private static Logger log = LoggerFactory.getLogger(EasyCli.class);
    private Shell shell;

    public EasyCli(String prompt,String hint,Object handler){
        shell = ShellFactory.createConsoleShell(prompt, hint, handler);
    }

    public void add(Object handler){
        shell.addMainHandler(handler,"");
    }

    public void run(){
        try {
            shell.commandLoop();
        } catch (IOException e) {
           log.error(e);
        }
    }
}
