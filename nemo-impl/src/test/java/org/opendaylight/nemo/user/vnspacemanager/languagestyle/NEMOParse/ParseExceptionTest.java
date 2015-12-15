/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOParse;

import  org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOParse.ParseException;
import static org.mockito.Mockito.mock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ldzd11 on 2015/12/7.
 */
public class ParseExceptionTest {

    private ParseException parseException;
    private ParseException parseException2;
    private ParseException parseException3;
    private String str;
    private String[] tokenImageVal;
    private String[] tokenImage;
    private int[][] expectedTokenSequencesVal;
    private int[][] expectedTokenSequences;
    private Token currentTokenVal;
    private Token currentToken;
    @org.junit.Before
    public void setUp() throws Exception {
        tokenImageVal = new String[2];
        tokenImage = new String[2];

        tokenImageVal[0] = "tokenImageVal1";
        tokenImageVal[1] = "tokenImageVal2";

        expectedTokenSequencesVal = new int[1][1];
        expectedTokenSequences = new int[1][1];
        expectedTokenSequencesVal[0][0] = 1;

        currentTokenVal = new Token();
        currentToken = new Token();
        currentTokenVal.next = currentToken;

        parseException = new ParseException();
        parseException2 = new ParseException("test");
        parseException3 = new ParseException(currentTokenVal,expectedTokenSequencesVal,tokenImageVal);

    }

    @org.junit.Test
    public void testAdd_escapes() throws Exception {



        str = new String("\0");
        Assert.assertEquals(parseException.add_escapes(str),"");
        Assert.assertEquals(parseException2.add_escapes(str),"");
        Assert.assertEquals(parseException3.add_escapes(str),"");


        str = new String("\b");
        Assert.assertEquals(parseException.add_escapes(str),"\\b");
        Assert.assertEquals(parseException2.add_escapes(str),"\\b");
        Assert.assertEquals(parseException3.add_escapes(str),"\\b");
        str = new String("\t");

        Assert.assertEquals(parseException.add_escapes(str),"\\t");
        str = new String("\n");
        Assert.assertEquals(parseException.add_escapes(str),"\\n");
        str = new String("\f");
        Assert.assertEquals(parseException.add_escapes(str),"\\f");
        str = new String("\r");
        Assert.assertEquals(parseException.add_escapes(str),"\\r");
        str = new String("\"");
        Assert.assertEquals(parseException.add_escapes(str),"\\\"");
        str = new String("\'");
        Assert.assertEquals(parseException.add_escapes(str),"\\\'");
        str = new String("\\");
        Assert.assertEquals(parseException.add_escapes(str),"\\\\");
        str = new String("0\b\t\n\f\r\"\'\\");
        Assert.assertEquals(parseException.add_escapes(str),"0\\b\\t\\n\\f\\r\\\"\\\'\\\\");
        Assert.assertEquals(parseException2.add_escapes(str),"0\\b\\t\\n\\f\\r\\\"\\'\\\\");
        Assert.assertEquals(parseException3.add_escapes(str),"0\\b\\t\\n\\f\\r\\\"\\'\\\\");
        str = String.format("%c",0x15);
        Assert.assertEquals(parseException.add_escapes(str),"\\u0015");
        Assert.assertEquals(parseException2.add_escapes(str),"\\u0015");
        Assert.assertEquals(parseException3.add_escapes(str),"\\u0015");
        str = String.format("%c",'a');
        Assert.assertEquals(parseException.add_escapes(str),"a");
        Assert.assertEquals(parseException2.add_escapes(str),"a");
        Assert.assertEquals(parseException3.add_escapes(str),"a");

        Assert.assertNotNull(parseException);
Assert.assertNotNull(parseException2);

Assert.assertNotNull(parseException3);

    }
}