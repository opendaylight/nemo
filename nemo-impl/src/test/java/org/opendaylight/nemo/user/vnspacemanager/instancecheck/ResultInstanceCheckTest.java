package org.opendaylight.nemo.user.vnspacemanager.instancecheck;

import org.opendaylight.nemo.user.tenantmanager.TenantManage;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Results;

import javax.xml.transform.Result;

/**
 * Created by Thomas Liu on 2015/11/26.
 */
public class ResultInstanceCheckTest extends junit.framework.TestCase {

    private ResultInstanceCheck  resultInstanceCheckTest;
    private TenantManage tenantManage;
    private Results results;
    private UserId userId;
    @org.junit.Before
    public void setUp() throws Exception {
        tenantManage = mock(TenantManage.class);
        resultInstanceCheckTest = new ResultInstanceCheck(tenantManage);
        results = mock(Results.class);
        userId = mock(UserId.class);
    }

    @org.junit.Test
    public void testCheckResultInstance() throws Exception {
        resultInstanceCheckTest.checkResultInstance(userId,results);

    }
}