/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOParse;

import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOParse.Token;

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
public class TokenTest {

    private Token token;
    private Token token2;
    private Token token3;

    private static final long serialVersionUID = 1L;
    private int kind;
    private  int beginLine;
    private int beginColumn;
    private int endLine;
    private int endColumn;
    private int ofKind;
    private String image;
    private Token next;
    private Token specialToken;


    @org.junit.Before
    public void setUp() throws Exception {
        token = new Token();
        kind = 1;
        image = new String("test");
        token2 = new Token(kind);
        token3 = new Token(kind,image);
        ofKind = 1;



    }

    @org.junit.Test
    public void testGetValue() throws Exception {
        Assert.assertEquals(token.getValue(),null);
    }

    @org.junit.Test
    public void testToString() throws Exception {
        Assert.assertEquals(token3.toString(),"test");
        Assert.assertEquals(token2.toString(),null);

    }

    @org.junit.Test
    public void testNewToken() throws Exception {
	
       Assert.assertTrue(token.newToken(ofKind,image) instanceof Token);
        Assert.assertNotNull(token);

	}
    @org.junit.Test
    public void testNewToken1() throws Exception {
	Assert.assertTrue(token.newToken(ofKind) instanceof Token);
        Assert.assertNotNull(token);

    }
}