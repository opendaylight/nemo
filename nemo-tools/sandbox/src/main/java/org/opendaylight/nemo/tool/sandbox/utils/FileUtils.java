/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.tool.sandbox.utils;

import java.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by hj on 12/11/15.
 */
public class FileUtils {
    private static Logger log = LoggerFactory.getLogger(FileUtils.class);
    public static void write(String path, String content) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                if (!f.createNewFile()) {
                    System.out.println("Can not create new file " + path);
                }
            }
            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(content);
            output.close();
        } catch (Exception e) {
            log.error("Exception:",e);
        }
    }
}
