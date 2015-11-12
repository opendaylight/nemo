package org.opendaylight.nemo.user.vnspacemanager.instancecheck;

import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Results;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Created by wangjunfei on 2015/11/10.
 */
public class ResultInstanceCheckTest {
    private TenantManage tenantManage;
    private UserId userId;
    private Results results;
    private ResultInstanceCheck resultInstanceCheck;
    @org.junit.Before
    public void setUp() throws Exception {
        tenantManage = mock(TenantManage.class);
        userId = mock(UserId.class);
        results = mock(Results.class);
        resultInstanceCheck = mock(ResultInstanceCheck.class);
    }

    @org.junit.Test
    public void testCheckResultInstance() throws Exception {
        Assert.assertNull(resultInstanceCheck.checkResultInstance(userId,results));
    }
}