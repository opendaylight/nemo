/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.test.user.vnspacemanager.languagestyle;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.AAA;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.LanguageIntent;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.LanguageStyleNemoRequestInput;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/11/12.
 */
public class LanguageIntentTest extends TestCase {
    private DataBroker dataBroker;
    private LanguageIntent languageIntent;

    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        languageIntent = new LanguageIntent(dataBroker);

    }

    @Test
    public void testLanIntentHandler() throws Exception {
        AAA aaa = mock(AAA.class);
        LanguageStyleNemoRequestInput languageStyleNemoRequestInput = mock(LanguageStyleNemoRequestInput.class);
        Assert.assertEquals(null,languageIntent.LanIntentHandler(aaa,languageStyleNemoRequestInput));
    }
}