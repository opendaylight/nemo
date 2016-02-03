/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.tool.sandbox;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.opendaylight.nemo.tool.sandbox.utils.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hj on 12/9/15.
 */
public class CmdExecutor {
    private static Logger log = LoggerFactory.getLogger(CmdExecutor.class);
    private static Connection sshConnection;
    private static String strHostName = Config.getHostName();
    private static String strUserName = Config.getHostUser();
    private static String strPassword = Config.getHostPassword();

    public static boolean open() {
        try {
            sshConnection = new Connection(strHostName);
            sshConnection.connect();
            if (false == sshConnection.authenticateWithPassword(strUserName, strPassword)) {
                throw new IOException("Authentication failed!");
            } else {
                return true;
            }
        } catch (IOException objException) {
            log.error("Exceptions:",objException);
            if (null != sshConnection) {
                sshConnection.close();
                sshConnection = null;
            }
        }
        return false;
    }

    public static void close() {
        if (null != sshConnection) {
            sshConnection.close();
            sshConnection = null;
        }
    }

    public static String sshExecute(String command) {
        Session session = null;
        try {
            session = sshConnection.openSession();
            if (!"root".equals(Config.getHostName())) {
                command = "sudo " + command;
            }
            session.execCommand(command);
            InputStream stdout = new StreamGobbler(session.getStdout());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stdout));
            String result = "";
            while (true) {
                String line = bufferedReader.readLine();
                if (line != null)
                    result += "\r\n" + line;
                else
                    break;
            }
            session.close();
            return result;

        } catch (IOException objException) {
            log.error("Exceptions:",objException);
        } finally {
            if (null != session) {
                session.close();
            }
        }
        return null;
    }

    public static String queryInterfaceMac(String name) {
        String result = sshExecute("ifconfig " + name);
        return getMacFromResult(result);
    }

    public static String queryInterfaceMac(String name, String namespce) {
        String result = sshExecute("ip netns exec " + namespce + " ifconfig " + name);
        return getMacFromResult(result);
    }

    private static String getMacFromResult(String result) {
        String args[] = result.split("\r\n");
        for (int i = 0; i < args.length; i++) {
            String arg = args[i].trim();
            if (arg.contains("HWaddr")) {
                String s[] = arg.split(" ");
                return s[s.length - 1];
            }
        }
        return null;
    }
}
