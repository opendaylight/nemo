/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.languagestyle;

import org.opendaylight.nemo.user.vnspacemanager.languagestyle.LanguageIntent;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.AAA;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOParse.NEMOparser;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOParse.NEMOparserTokenManager;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOParse.ParseException;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOParse.SimpleCharStream;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.LanguageStyleNemoRequestInput;

import java.io.StringReader;
import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by ldzd11 on 2015/12/14.
 */
public class LanguageIntentTest {

    private LanguageIntent languageIntent;
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private NEMOparser nemOparser;
    private AAA aaa;
    private UserId userId;
    // private  NemoStatement nemoStatement;
    private LanguageStyleNemoRequestInput languageStyleNemoRequestInput;
    private String errorInfo;

    @org.junit.Before
    public void setUp() throws Exception {
        Class<NEMOparser> class1 = NEMOparser.class;
        Class<SimpleCharStream> class2 = SimpleCharStream.class;
        Class<NEMOparserTokenManager> class3 = NEMOparserTokenManager.class;
        Field field = class1.getDeclaredField("jj_initialized_once");
        field.setAccessible(true);
        Field field1 = class2.getDeclaredField("inputStream");
        field1.setAccessible(true);
        Field field2 = class3.getDeclaredField("input_stream");
        field2.setAccessible(true);

        field.set(class1, false);
        Assert.assertTrue(!(Boolean) field.get(class1));
        field1.set(class2, null);
        Assert.assertTrue(field1.get(class2) == null);
        field2.set(class3,null);
        Assert.assertTrue(field2.get(class3) == null);

        userId = mock(UserId.class);
        // nemoStatement = mock(NemoStatement.class);
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        languageStyleNemoRequestInput = mock(LanguageStyleNemoRequestInput.class);
        aaa = mock(AAA.class);
        // nemOparser = new NEMOparser(new StringReader(""));
        errorInfo = new String();
        languageIntent = new LanguageIntent(dataBroker,tenantManage);

    }

    @org.junit.Test
    public void testLanIntentHandler() throws Exception {
        when(languageStyleNemoRequestInput.getUserId()).thenReturn(userId);
        when(languageStyleNemoRequestInput.getNemoStatement()).thenReturn(new String("test"));
        when(aaa.checkUser(languageStyleNemoRequestInput.getUserId())).thenReturn(null);
        try{
            Assert.assertEquals(languageIntent.LanIntentHandler(aaa, languageStyleNemoRequestInput),nemOparser.parseNEMO(languageStyleNemoRequestInput.getUserId(),languageStyleNemoRequestInput.getNemoStatement(),dataBroker,tenantManage));
        }
        catch(ParseException e){
        }

        when(aaa.checkUser(languageStyleNemoRequestInput.getUserId())).thenReturn(errorInfo);
        try{
            Assert.assertEquals(languageIntent.LanIntentHandler(aaa, languageStyleNemoRequestInput),errorInfo);
        }
        catch(ParseException e){
        }
    }
}