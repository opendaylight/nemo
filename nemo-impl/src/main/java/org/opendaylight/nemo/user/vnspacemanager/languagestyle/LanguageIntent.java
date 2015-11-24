/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.languagestyle;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.AAA;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.LanguageStyleNemoRequestInput;

/**
 * Created by z00293636 on 2015/8/31.
 */
public class LanguageIntent {

    private DataBroker dataBroker;

    public LanguageIntent(DataBroker dataBroker)
    {
       this.dataBroker = dataBroker;
    }

    public String LanIntentHandler(AAA aaa, LanguageStyleNemoRequestInput languageStyleNemoRequestInput){

        String errorInfo = null;

        errorInfo = aaa.checkUser(languageStyleNemoRequestInput);
        if (errorInfo !=null)
        {
           return errorInfo;
        }
        else
        {
            //TODO language parse
        }

       return null;
    }
}
