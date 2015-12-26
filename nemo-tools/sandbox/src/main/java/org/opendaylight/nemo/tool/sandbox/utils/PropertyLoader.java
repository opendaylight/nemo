/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.tool.sandbox.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hj on 9/7/15.
 */
public class PropertyLoader {
    public static void loadProperties(String filePath, String noExistWarn) {
        try {
            InputStream propFileStream = PropertyLoader.class.getClass().getResourceAsStream(filePath);
            if (propFileStream != null)
                System.getProperties().load(propFileStream);
            else {
                System.out.println(noExistWarn);
            }
        } catch (IOException e) {
            System.out.println("Read properties file error:" + e.getMessage() + "!");
        }
    }

    public static List<String> readLines(String filePath, String noExistWarn) {
        List<String> lines = new ArrayList<String>();

        try {
            InputStream inputStream = PropertyLoader.class.getClass().getResourceAsStream(filePath);
            if (inputStream != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    lines.add(line);
                }
            }else {
                System.out.println(noExistWarn);
            }
        } catch (Exception e) {
            System.out.println("Read lines error:" + e.getMessage() + "!");
        }
        return lines;
    }
}
