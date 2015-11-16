/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import com.google.common.util.concurrent.Futures;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.RpcRegistration;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.nemo.intent.IntentResolutionException;
import org.opendaylight.nemo.intent.IntentResolver;
import org.opendaylight.nemo.intent.computation.VNMappingException;
import org.opendaylight.nemo.user.advancedquery.AdvancedQuery;
import org.opendaylight.nemo.user.tenantmanager.AAA;
import org.opendaylight.nemo.user.tenantmanager.RegisterUser;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.transactionmanager.TransactionBegin;
import org.opendaylight.nemo.user.transactionmanager.TransactionEnd;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.LanguageIntent;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent.DeleteIntent;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateIntent;
import org.opendaylight.nemo.user.vnspacemanager.VNSpaceManagement;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.*;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;

import java.util.concurrent.Future;

/**
 * Created by Thomas Liu on 2015/11/12.
 */
public class UserManagerTest extends TestCase {
    private UserManager userManagerTest;
    private AdvancedNemoQueryInput advancedNemoQueryInput;
    private BeginTransactionInput beginTransactionInput;
    private EndTransactionInput endTransactionInput;
    private LanguageStyleNemoRequestInput languageStyleNemoRequestInput;
    private  RegisterUserInput registerUserInput;
    private  StructureStyleNemoDeleteInput structureStyleNemoDeleteInput;
    private  StructureStyleNemoUpdateInput structureStyleNemoUpdateInput;


    @org.junit.Before
    public void setUp() throws Exception {
        userManagerTest = mock(UserManager.class);
        advancedNemoQueryInput = mock(AdvancedNemoQueryInput.class);
        beginTransactionInput = mock(BeginTransactionInput.class);
        endTransactionInput = mock(EndTransactionInput.class);
        languageStyleNemoRequestInput = mock(LanguageStyleNemoRequestInput.class);
        registerUserInput = mock(RegisterUserInput.class);
        structureStyleNemoDeleteInput = mock(StructureStyleNemoDeleteInput.class);
        structureStyleNemoUpdateInput = mock(StructureStyleNemoUpdateInput.class);
    }

    @org.junit.Test
    public void testAdvancedNemoQuery() throws Exception {
        userManagerTest.advancedNemoQuery(advancedNemoQueryInput);
        Assert.assertNotNull(userManagerTest);
        verify(userManagerTest).advancedNemoQuery(advancedNemoQueryInput);
    }

    @org.junit.Test
    public void testBeginTransaction() throws Exception {
        userManagerTest.beginTransaction(beginTransactionInput);
        Assert.assertNotNull(userManagerTest);
        verify(userManagerTest).beginTransaction(beginTransactionInput);

    }

    @org.junit.Test
    public void testEndTransaction() throws Exception {
        userManagerTest.endTransaction(endTransactionInput);
        Assert.assertNotNull(userManagerTest);
        verify(userManagerTest).endTransaction(endTransactionInput);
    }

    @org.junit.Test
    public void testLanguageStyleNemoRequest() throws Exception {
        userManagerTest.languageStyleNemoRequest(languageStyleNemoRequestInput);
        Assert.assertNotNull(userManagerTest);
        verify(userManagerTest).languageStyleNemoRequest(languageStyleNemoRequestInput);
    }

    @org.junit.Test
    public void testRegisterUser() throws Exception {
        userManagerTest.registerUser(registerUserInput);
        Assert.assertNotNull(userManagerTest);
        verify(userManagerTest).registerUser(registerUserInput);
    }

    @org.junit.Test
    public void testStructureStyleNemoDelete() throws Exception {
        userManagerTest.structureStyleNemoDelete(structureStyleNemoDeleteInput);
        Assert.assertNotNull(userManagerTest);
        verify(userManagerTest).structureStyleNemoDelete(structureStyleNemoDeleteInput);
    }

    @org.junit.Test
    public void structureStyleNemoUpdate() throws Exception {
        userManagerTest.structureStyleNemoUpdate(structureStyleNemoUpdateInput);
        Assert.assertNotNull(userManagerTest);
        verify(userManagerTest).structureStyleNemoUpdate(structureStyleNemoUpdateInput);
    }

    @org.junit.Test
    public void testClose() throws Exception {
        userManagerTest.close();
        Assert.assertNotNull(userManagerTest);
        verify(userManagerTest).close();
    }
}