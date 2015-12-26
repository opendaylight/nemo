/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.tool.sandbox.utils;

import java.math.BigInteger;

public class HexString {
    public static String toHexString(long val, int padTo) {
        char arr[] = Long.toHexString(val).toCharArray();
        String ret = "";
        // prepend the right number of leading zeros
        int i = 0;
        for (; i < (padTo * 2 - arr.length); i++) {
            ret += "0";
//            if ((i % 2) != 0)
//                ret += ":";
        }
        for (int j = 0; j < arr.length; j++) {
            ret += arr[j];
//            if ((((i + j) % 2) != 0) && (j < (arr.length - 1)))
//                ret += ":";
        }
        return ret;
    }

    public static String toHexString(long val) {
        return toHexString(val, 6);
    }

    /**
     * Convert a string of hex values into a string of bytes
     *
     * @param values "0f:ca:fe:de:ad:be:ef"
     * @return [15, 5 ,2, 5, 17]
     * @throws NumberFormatException If the string can not be parsed
     */
    public static byte[] fromHexString(String values) throws NumberFormatException {
        String[] octets = values.split(":");
        byte[] ret = new byte[octets.length];

        for (int i = 0; i < octets.length; i++) {
            if (octets[i].length() > 2)
                throw new NumberFormatException("Invalid octet length");
            ret[i] = Integer.valueOf(octets[i], 16).byteValue();
        }
        return ret;
    }

    public static long toLong(String values) throws NumberFormatException {
        // Long.parseLong() can't handle HexStrings with MSB set. Sigh. 
        BigInteger bi = new BigInteger(values.replaceAll(":", ""), 16);
        if (bi.bitLength() > 64)
            throw new NumberFormatException("Input string too big to fit in long: " + values);
        return bi.longValue();
    }
}
