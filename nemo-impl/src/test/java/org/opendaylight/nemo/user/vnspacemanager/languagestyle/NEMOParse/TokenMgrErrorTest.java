/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOParse;

import  org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOParse.TokenMgrError;

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
 * Created by ldzd11 on 2015/12/8.
 */
public class TokenMgrErrorTest {
    private TokenMgrError tokenMgrError;
    private TokenMgrError tokenMgrError2;
    private TokenMgrError tokenMgrError3;
    private  String str;
    private String message;
    private int reason;
    private  boolean EOFSeen;
    private  boolean EOFSeen2;
    private  int lexState;
    private int errorLine;
    private int errorColumn;
    private String errorAfter;
    private char curChar;

    @org.junit.Before
    public void setUp() throws Exception {
        reason = 1;
        message = new String("test");
        tokenMgrError = new TokenMgrError();
        tokenMgrError2 = new TokenMgrError(message,reason);


        EOFSeen = false;
        EOFSeen2 =true;
        lexState = 1;
        errorLine = 1;
        errorColumn =1;
        errorAfter = new String("testAfter");
        curChar = '1';

        tokenMgrError3 = new TokenMgrError(EOFSeen,lexState,errorLine,errorColumn,errorAfter,curChar,reason);

    }

    @org.junit.Test
    public void testAddEscapes() throws Exception {
        str = new String("\0");
        Assert.assertEquals(tokenMgrError.addEscapes(str),"");

        str = new String("\b");
        Assert.assertEquals(tokenMgrError.addEscapes(str),"\\b");

        str = new String("\t");
        Assert.assertEquals(tokenMgrError.addEscapes(str),"\\t");

        str = new String("\n");
        Assert.assertEquals(tokenMgrError.addEscapes(str),"\\n");

        str = new String("\f");
        Assert.assertEquals(tokenMgrError.addEscapes(str),"\\f");

        str = new String("\r");
        Assert.assertEquals(tokenMgrError.addEscapes(str),"\\r");

        str = new String("\"");
        Assert.assertEquals(tokenMgrError.addEscapes(str),"\\\"");

        str = new String("\'");
        Assert.assertEquals(tokenMgrError.addEscapes(str),"\\\'");

        str = new String("\\");
        Assert.assertEquals(tokenMgrError.addEscapes(str),"\\\\");

        str = new String("\0\b\t\n\f\r\"\'\\");
        Assert.assertEquals(tokenMgrError.addEscapes(str),"\\b\\t\\n\\f\\r\\\"\\\'\\\\");

        str = String.format("%c",0x15);
        Assert.assertEquals(tokenMgrError.addEscapes(str),"\\u0015");

        str = String.format("%c",'a');
        Assert.assertEquals(tokenMgrError.addEscapes(str),"a");

    }

    @org.junit.Test
    public void testLexicalError() throws Exception {

        Assert.assertEquals(tokenMgrError.LexicalError(EOFSeen,lexState,errorLine,errorColumn,errorAfter,curChar),
                "Lexical error at line " +
                        "1" + ", column " +
                        "1" + ".  Encountered: " +
                        "\"" + "1" + "\"" + " (" + "49"+ "), " +
                        "after : \"" + "testAfter" + "\""
        );

        Assert.assertEquals(tokenMgrError.LexicalError(EOFSeen2,lexState,errorLine,errorColumn,errorAfter,curChar),
                "Lexical error at line " +
                        "1" + ", column " +
                        "1" + ".  Encountered: " +
                        "<EOF> "+
                        "after : \"" + "testAfter" + "\""
        );


    }

    @org.junit.Test
    public void testGetMessage() throws Exception {
        Assert.assertEquals(tokenMgrError.getMessage(),null);
        Assert.assertEquals(tokenMgrError2.getMessage(),"test");


        Assert.assertEquals(tokenMgrError3.getMessage(),"Lexical error at line " +
                "1" + ", column " +
                "1" + ".  Encountered: " +
                "\"" + "1" + "\"" + " (" + "49"+ "), " +
                "after : \"" + "testAfter" + "\""
        );
Assert.assertNotNull(tokenMgrError2);



    }
}