/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
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
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOParse.ParseException;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent.DeleteIntent;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateIntent;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.*;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;

import java.util.concurrent.Future;

import static org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.CommonRpcResult.ResultCode.Error;
import static org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.CommonRpcResult.ResultCode.Ok;

/**
 * Created by z00293636 on 2015/9/7.
 */
public class UserManager implements NemoIntentService {

    private final DataBroker dataBroker;

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

    public UserManager(DataBroker dataBroker0, IntentResolver intentResolver0)
    {
        this.dataBroker = dataBroker0;

        this.intentResolver = intentResolver0;
        tenantManage = new TenantManage(dataBroker);
        aaa = new AAA(tenantManage);
        registerUser = new RegisterUser(tenantManage);
        updateIntent = new UpdateIntent(dataBroker,tenantManage);
        deleteIntent = new DeleteIntent(dataBroker, tenantManage);
        languageIntent = new LanguageIntent(dataBroker,tenantManage);
        advancedQuery = new AdvancedQuery(dataBroker, tenantManage);
        transactionBegin = new TransactionBegin();
        transactionEnd = new TransactionEnd();

        transaction = false;
        informresolver = false;
    }
    @Override
    public Future<RpcResult<AdvancedNemoQueryOutput>> advancedNemoQuery(AdvancedNemoQueryInput input) {

        final AdvancedNemoQueryOutputBuilder outputBuilder = new AdvancedNemoQueryOutputBuilder();
        String errorInfo = advancedQuery.advancedQuery(aaa, input);

        if (errorInfo != null){
            outputBuilder.setResultCode(Error).setMessage(errorInfo);
        }
        else{
            outputBuilder.setResultCode(Ok).setMessage(advancedQuery.getAdvancedQueryReuslt(input));
        }

        return  RpcResultBuilder.success(outputBuilder).buildFuture();
    }

    @Override
    public Future<RpcResult<BeginTransactionOutput>> beginTransaction(BeginTransactionInput input) {

        final BeginTransactionOutputBuilder outputBuilder = new BeginTransactionOutputBuilder();
        if (transaction){
            outputBuilder.setResultCode(Error).setMessage("The previous transaction has not been finished.");
        }
        else{
            String errorInfo = transactionBegin.transactionbegin(aaa,input);
            if (errorInfo != null){
                outputBuilder.setResultCode(Error).setMessage(errorInfo);
            }
            else{
                transaction = true;
                outputBuilder.setResultCode(Ok).setMessage("Transaction Begin.");
            }
        }
        return RpcResultBuilder.success(outputBuilder).buildFuture();
    }

    @Override
    public Future<RpcResult<EndTransactionOutput>> endTransaction(EndTransactionInput input) {

        final EndTransactionOutputBuilder outputBuilder = new EndTransactionOutputBuilder();

        if (!transaction){
            outputBuilder.setResultCode(Error).setMessage("The transaction has not started.");
        }
        else{
            String errorInfo = transactionEnd.transactionend(aaa,input);
            if (errorInfo != null){
                outputBuilder.setResultCode(Error).setMessage(errorInfo);
            }
            else{
                transaction = false;
                tenantManage.transactionHandling(input.getUserId());
                if (informresolver){
                    informresolver = false;
                    try{
                        intentResolver.resolveIntent(input.getUserId());
                        outputBuilder.setResultCode(Ok).setMessage("The transaction ends.");
                    }
                    catch (IntentResolutionException e){
                        e.printStackTrace();
                        outputBuilder.setResultCode(Error).setMessage(e.getMessage());
                    }
                    catch (VNMappingException e){
                        e.printStackTrace();
                        outputBuilder.setResultCode(Error).setMessage(e.getMessage());
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    outputBuilder.setResultCode(Ok).setMessage("The transaction ends.");
                }
            }
        }
        return RpcResultBuilder.success(outputBuilder).buildFuture();
    }

    @Override
    public Future<RpcResult<LanguageStyleNemoRequestOutput>> languageStyleNemoRequest(LanguageStyleNemoRequestInput input) {

        final LanguageStyleNemoRequestOutputBuilder outputBuilder = new LanguageStyleNemoRequestOutputBuilder();
        try {
            String errorInfo = languageIntent.LanIntentHandler(aaa,input);
            if (errorInfo != null){
                if (errorInfo.contains(NEMOConstants.Results)){
                    outputBuilder.setResultCode(Ok).setMessage(errorInfo);
                }
                else{
                    outputBuilder.setResultCode(Error).setMessage(errorInfo);
                }
            }
            else{
                outputBuilder.setResultCode(Ok).setMessage("The intent has been stored in this transaction.");
                informresolver = true;
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
            outputBuilder.setResultCode(Error).setMessage(e.getMessage());
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            outputBuilder.setResultCode(Error).setMessage(e.getMessage());
        }
        return RpcResultBuilder.success(outputBuilder).buildFuture();
    }

    @Override
    public Future<RpcResult<RegisterUserOutput>> registerUser(RegisterUserInput input) {

        final RegisterUserOutputBuilder outputBuilder = new RegisterUserOutputBuilder();
        String errorInfo = registerUser.registerUser(input);

        if (errorInfo != null){
            outputBuilder.setResultCode(Error).setMessage(errorInfo);
        }
        else{
            outputBuilder.setResultCode(Ok).setMessage("Register user successfully.");
        }
        return RpcResultBuilder.success(outputBuilder).buildFuture();
    }

    @Override
    public Future<RpcResult<StructureStyleNemoDeleteOutput>> structureStyleNemoDelete(StructureStyleNemoDeleteInput input) {

        final StructureStyleNemoDeleteOutputBuilder outputBuilder = new StructureStyleNemoDeleteOutputBuilder();
        String errorInfo = deleteIntent.styleNemoDeleteOutput(aaa,input);

        if (errorInfo != null){
            outputBuilder.setResultCode(Error).setMessage(errorInfo);
        }
        else{
            outputBuilder.setResultCode(Ok).setMessage("The intent has been handled by user manager successfully.");
            informresolver = true;
        }
        return RpcResultBuilder.success(outputBuilder).buildFuture();
    }

    @Override
    public Future<RpcResult<StructureStyleNemoUpdateOutput>> structureStyleNemoUpdate(StructureStyleNemoUpdateInput input) {

        final StructureStyleNemoUpdateOutputBuilder outputBuilder = new StructureStyleNemoUpdateOutputBuilder();
        try {
            String erroInfo = updateIntent.updateIntent(aaa,input);
            if (erroInfo != null){
                outputBuilder.setResultCode(Error).setMessage(erroInfo);
            }
            else{
                outputBuilder.setResultCode(Ok).setMessage("The intent has been handled by user manager successfully.");
                informresolver = true;
            }
        }
        catch (NumberFormatException e){
            e.printStackTrace();
            outputBuilder.setResultCode(Error).setMessage(e.getMessage());
        }
        return RpcResultBuilder.success(outputBuilder).buildFuture();
    }
}
