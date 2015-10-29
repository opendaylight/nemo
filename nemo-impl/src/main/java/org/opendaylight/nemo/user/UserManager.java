/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user;

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
 * Created by z00293636 on 2015/9/7.
 */
public class UserManager implements NemoIntentService, AutoCloseable {

    private DataBroker dataBroker;
    private RpcProviderRegistry rpcProviderRegistry;

    private VNSpaceManagement vnSpaceManagement;
    private RegisterUser registerUser;
    private UpdateIntent updateIntent;
    private DeleteIntent deleteIntent;
    private LanguageIntent languageIntent;
    private TenantManage tenantManage;
    private AAA aaa;
    private TransactionBegin transactionBegin;
    private TransactionEnd transactionEnd;
    private AdvancedQuery advancedQuery;
    private IntentResolver intentResolver;

    Boolean transaction;
    Boolean informresolver;

    private RpcRegistration<NemoIntentService> rpcRegistration;

    public UserManager(DataBroker dataBroker, RpcProviderRegistry rpcProviderRegistry,
                       IntentResolver intentResolver)
    {
        this.dataBroker = dataBroker;
        this.rpcProviderRegistry = rpcProviderRegistry;

        this.intentResolver = intentResolver;
        vnSpaceManagement = new VNSpaceManagement(dataBroker);
        tenantManage = new TenantManage(dataBroker);
        aaa = new AAA(tenantManage);
        registerUser = new RegisterUser(tenantManage);
        updateIntent = new UpdateIntent(dataBroker,tenantManage);
        deleteIntent = new DeleteIntent(dataBroker, tenantManage);
        languageIntent = new LanguageIntent(dataBroker);
        advancedQuery = new AdvancedQuery(dataBroker, tenantManage);

        transactionBegin = new TransactionBegin();
        transactionEnd = new TransactionEnd();

        transaction = false;
        informresolver = false;

        rpcRegistration = rpcProviderRegistry.addRpcImplementation(NemoIntentService.class, this);
    }
    @Override
    public Future<RpcResult<AdvancedNemoQueryOutput>> advancedNemoQuery(AdvancedNemoQueryInput input) {
        RpcResult<AdvancedNemoQueryOutput> advancedNemoQueryOutputRpcResult = null;
        AdvancedNemoQueryOutputBuilder advancedNemoQueryOutputBuilder = new AdvancedNemoQueryOutputBuilder();

        String errorInfo = advancedQuery.advancedQuery(aaa, input);
        if (errorInfo != null)
        {
            advancedNemoQueryOutputBuilder.setResultCode(CommonRpcResult.ResultCode.Error).setMessage(errorInfo);
        }
        else
        {
            advancedNemoQueryOutputBuilder.setResultCode(CommonRpcResult.ResultCode.Ok).setMessage(advancedQuery.getAdvancedQueryReuslt(input));
        }
        advancedNemoQueryOutputRpcResult = RpcResultBuilder.<AdvancedNemoQueryOutput>success(advancedNemoQueryOutputBuilder.build()).build();

        return Futures.immediateFuture(advancedNemoQueryOutputRpcResult);
    }

    @Override
    public Future<RpcResult<BeginTransactionOutput>> beginTransaction(BeginTransactionInput input) {
        RpcResult<BeginTransactionOutput> beginTransactionOutputResult = null;
        BeginTransactionOutputBuilder beginTransactionOutputBuilder = new BeginTransactionOutputBuilder();

        if (transaction)
        {
            beginTransactionOutputBuilder.setResultCode(CommonRpcResult.ResultCode.Error).setMessage("The previous transaction has not been finished.");
        }
        else
        {
            String errorInfo = transactionBegin.transactionbegin(aaa,input);
            if (errorInfo != null)
            {
                beginTransactionOutputBuilder.setResultCode(CommonRpcResult.ResultCode.Error).setMessage(errorInfo);
            }
            else
            {
                transaction = true;
                beginTransactionOutputBuilder.setResultCode(CommonRpcResult.ResultCode.Ok).setMessage("Transaction Begin.");
            }
        }
        beginTransactionOutputResult = RpcResultBuilder.<BeginTransactionOutput>success(beginTransactionOutputBuilder.build()).build();
        return Futures.immediateFuture(beginTransactionOutputResult);
    }

    @Override
    public Future<RpcResult<EndTransactionOutput>> endTransaction(EndTransactionInput input) {
        RpcResult<EndTransactionOutput> endTransactionOutputResult = null;
        EndTransactionOutputBuilder endTransactionOutputBuilder = new EndTransactionOutputBuilder();

        if (!transaction)
        {
            endTransactionOutputBuilder.setResultCode(CommonRpcResult.ResultCode.Error).setMessage("The transaction has not started.");
        }
        else
        {
            String errorInfo = transactionEnd.transactionend(aaa,input);
            if (errorInfo != null)
            {
                endTransactionOutputBuilder.setResultCode(CommonRpcResult.ResultCode.Error).setMessage(errorInfo);
            }
            else
            {
                transaction = false;
                if (informresolver)
                {
                    informresolver = false;
                    try
                    {
                        intentResolver.resolveIntent(input.getUserId());
						endTransactionOutputBuilder.setResultCode(CommonRpcResult.ResultCode.Ok).setMessage("The transaction ends.");
                    }
                    catch (IntentResolutionException | VNMappingException e)
                    {
                        e.printStackTrace();
                        endTransactionOutputBuilder.setResultCode(CommonRpcResult.ResultCode.Error).setMessage(e.getMessage());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    endTransactionOutputBuilder.setResultCode(CommonRpcResult.ResultCode.Ok).setMessage("The transaction ends.");
                }

            }
        }
        endTransactionOutputResult = RpcResultBuilder.<EndTransactionOutput>success(endTransactionOutputBuilder.build()).build();
        return Futures.immediateFuture(endTransactionOutputResult);
    }

    @Override
    public Future<RpcResult<LanguageStyleNemoRequestOutput>> languageStyleNemoRequest(LanguageStyleNemoRequestInput input) {
        RpcResult<LanguageStyleNemoRequestOutput> styleNemoRequestOutputRpcResult = null;
        LanguageStyleNemoRequestOutputBuilder languageStyleNemoRequestOutputBuilder = new LanguageStyleNemoRequestOutputBuilder();

        String errorInfo = languageIntent.LanIntentHandler(aaa,input);

        if (errorInfo != null)
        {
            languageStyleNemoRequestOutputBuilder.setResultCode(CommonRpcResult.ResultCode.Error).setMessage(errorInfo);
        }
        else
        {
            languageStyleNemoRequestOutputBuilder.setResultCode(CommonRpcResult.ResultCode.Ok).setMessage("The intent has been stored in this transaction.");
            informresolver = true;
        }
        styleNemoRequestOutputRpcResult = RpcResultBuilder.<LanguageStyleNemoRequestOutput>success(languageStyleNemoRequestOutputBuilder.build()).build();

        return Futures.immediateFuture(styleNemoRequestOutputRpcResult);
    }

    @Override
    public Future<RpcResult<RegisterUserOutput>> registerUser(RegisterUserInput input) {
        RpcResult<RegisterUserOutput> registerUserOutputRpcResult = null;
        RegisterUserOutputBuilder registerUserOutputBuilder = new RegisterUserOutputBuilder();
        String errorInfo = registerUser.registerUser(input);

        if (errorInfo != null)
        {
            registerUserOutputBuilder.setResultCode(CommonRpcResult.ResultCode.Error).setMessage(errorInfo);
        }
        else
        {
            registerUserOutputBuilder.setResultCode(CommonRpcResult.ResultCode.Ok).setMessage("Register user successfully.");
        }

        registerUserOutputRpcResult = RpcResultBuilder.<RegisterUserOutput>success(registerUserOutputBuilder.build()).build();

        return Futures.immediateFuture(registerUserOutputRpcResult);
    }

    @Override
    public Future<RpcResult<StructureStyleNemoDeleteOutput>> structureStyleNemoDelete(StructureStyleNemoDeleteInput input) {
        RpcResult<StructureStyleNemoDeleteOutput> styleNemoDeleteOutputRpcResult = null;
        StructureStyleNemoDeleteOutputBuilder styleNemoDeleteOutputBuilder = new StructureStyleNemoDeleteOutputBuilder();

        String errorInfo = deleteIntent.styleNemoDeleteOutput(aaa,input);

        if (errorInfo != null)
        {
            styleNemoDeleteOutputBuilder.setResultCode(CommonRpcResult.ResultCode.Error).setMessage(errorInfo);
        }
        else
        {
            styleNemoDeleteOutputBuilder.setResultCode(CommonRpcResult.ResultCode.Ok).setMessage("The intent has been handled by user manager successfully.");
            informresolver = true;
        }


        styleNemoDeleteOutputRpcResult = RpcResultBuilder.<StructureStyleNemoDeleteOutput>success(styleNemoDeleteOutputBuilder.build()).build();

        return Futures.immediateFuture(styleNemoDeleteOutputRpcResult);
    }

    @Override
    public Future<RpcResult<StructureStyleNemoUpdateOutput>> structureStyleNemoUpdate(StructureStyleNemoUpdateInput input) {
        RpcResult<StructureStyleNemoUpdateOutput> styleNemoUpdateOutputRpcResult = null;
        StructureStyleNemoUpdateOutputBuilder styleNemoUpdateOutputBuilder = new StructureStyleNemoUpdateOutputBuilder();

        String erroInfo = updateIntent.updateIntent(aaa,input);

        if (erroInfo != null)
        {
            styleNemoUpdateOutputBuilder.setResultCode(CommonRpcResult.ResultCode.Error).setMessage(erroInfo);
        }
        else
        {
            styleNemoUpdateOutputBuilder.setResultCode(CommonRpcResult.ResultCode.Ok).setMessage("The intent has been handled by user manager successfully.");
            informresolver = true;
        }

        styleNemoUpdateOutputRpcResult = RpcResultBuilder.<StructureStyleNemoUpdateOutput>success(styleNemoUpdateOutputBuilder.build()).build();

        return Futures.immediateFuture(styleNemoUpdateOutputRpcResult);
    }

    @Override
    public void close() throws Exception {
        if ( null != rpcRegistration ) {
            rpcRegistration.close();
        }
    }
}
