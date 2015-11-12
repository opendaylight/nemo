package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;
import  org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateResult;

import static org.mockito.Mockito.mock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.instancecheck.ResultInstanceCheck;
import org.opendaylight.nemo.user.vnspacemanager.syntaxcheck.ResultDefinitionCheck;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Results;
/**
 * Created by ldzd11 on 2015/11/9.
 */
public class UpdateResultTest {


    private UpdateResult updateResult;

    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private ResultDefinitionCheck resultDefinitionCheck;
    private ResultInstanceCheck resultInstanceCheck;
    private UserId userId;
    private Results results;

    @org.junit.Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        resultDefinitionCheck = mock(ResultDefinitionCheck.class);
        resultInstanceCheck = mock(ResultInstanceCheck.class);
        userId = mock(UserId.class);
        results = mock(Results.class);

        updateResult = mock(UpdateResult.class);


    }

    @org.junit.Test
    public void testResultHandling() throws Exception {
        Assert.assertNull(updateResult.ResultHandling(userId, results));

    }
}