/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package user.vnspacemanager.languagestyle.deleteintentlang;

import org.junit.Assert;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent.DeleteFlow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowId;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.deleteintentlang.DeleteFlowLang;
import  org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/12/9.
 */
public class DeleteFlowLangTest extends TestCase {
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private DeleteFlowLang deleteFlowLang;
    private DeleteFlow deleteFlow;
    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        deleteFlow = mock(DeleteFlow.class);
        deleteFlowLang = new DeleteFlowLang(dataBroker,tenantManage);
    }

    @Test
    public void testDeleteFlowHandling() throws Exception {
        String result;
        String flowname = new String("test");
        UserId userId = mock(UserId.class);
        User user = mock(User.class);
        Objects objects = mock(Objects.class);
        Flow flow = mock(Flow.class);
        FlowId flowId = mock(FlowId.class);
        FlowName flowName = mock(FlowName.class);
        List<Flow> flowList = new ArrayList<Flow>();

        doNothing().when(tenantManage).fetchVNSpace(any(UserId.class));
        when(tenantManage.getUser()).thenReturn(user);
        when(user.getObjects())
                .thenReturn(null)//if (user.getObjects()!=null)
                .thenReturn(objects);//get into if
        result = deleteFlowLang.DeleteFlowHandling(userId,flowname);
        Assert.assertTrue(result.equals("The flow " + flowname + " is not exist in this user vn space."));
        when(objects.getFlow()).thenReturn(flowList);
        result = deleteFlowLang.DeleteFlowHandling(userId,flowname);
        Assert.assertTrue(result.equals("The flow " + flowname + " is not exist in this user vn space."));
        Assert.assertTrue(flowList.isEmpty());
        //list not null
        flowList.add(flow);
        when(flow.getFlowName()).thenReturn(flowName);
        when(flowName.getValue())
                .thenReturn("1")
                .thenReturn(flowname);
        result = deleteFlowLang.DeleteFlowHandling(userId,flowname);
        Assert.assertTrue(result.equals("The flow " + flowname + " is not exist in this user vn space."));
        //test last
        when(flow.getFlowId()).thenReturn(flowId);
        Class<DeleteFlowLang> class1 = DeleteFlowLang.class;
        Field field = class1.getDeclaredField("deleteFlow");
        field.setAccessible(true);
        field.set(deleteFlowLang, deleteFlow);
        System.out.println(field.get(deleteFlowLang));
        when(deleteFlow.DeleteFlowHandling(any(UserId.class),any(FlowId.class))).thenReturn(new String("test"));
        result = deleteFlowLang.DeleteFlowHandling(userId,flowname);
        Assert.assertTrue(result.equals("test"));
        verify(flow).getFlowId();
    }
}