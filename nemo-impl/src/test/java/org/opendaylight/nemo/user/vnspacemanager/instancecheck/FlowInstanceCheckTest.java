package org.opendaylight.nemo.user.vnspacemanager.instancecheck;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas Liu on 2015/11/20.
 */
public class FlowInstanceCheckTest extends junit.framework.TestCase {
    private TenantManage tenantManage;
    private FlowInstanceCheck flowInstanceCheckTest;
    private UserId userId;
    private Flow flow;
    private Objects object;
    private User user;
    private List<Flow> flowList;
    private FlowId flowid;
    private FlowName flowname;

    @org.junit.Before
    public void setUp() throws Exception {
        tenantManage = mock(TenantManage.class);
        flowInstanceCheckTest = new FlowInstanceCheck(tenantManage);
        userId = mock(UserId.class);
        flow = mock(Flow.class);
        object = mock(Objects.class);
        user = mock(User.class);
        flowList = new ArrayList<Flow>();
        flowList.add(mock(Flow.class));
        flowid = mock(FlowId.class);
        flowname = mock(FlowName.class);

    }

    @org.junit.Test
    public void testCheckFlowInstance() throws Exception {
        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);
        when(user.getObjects()).thenReturn(object);
        when(user.getObjects().getFlow()).thenReturn(flowList);
        when(tenantManage.getUser().getObjects().getFlow()).thenReturn(flowList);
        when(flowList.get(0).getFlowId()).thenReturn(flowid);
        when(flow.getFlowId()).thenReturn(flowid);
        when(flowList.get(0).getFlowName()).thenReturn(flowname);
        when(flow.getFlowName()).thenReturn(flowname);
        flowInstanceCheckTest.checkFlowInstance(userId,flow);
        verify(tenantManage).fetchVNSpace(userId);
        verify(tenantManage,times(3)).getUser();
        verify(user,times(5)).getObjects();
        verify(user.getObjects(),times(2)).getFlow();
        //verify(tenantManage,times(3)).getUser().getObjects().getFlow();
        verify(flowList.get(0)).getFlowId();
        verify(flow).getFlowId();
        verify(flowList.get(0)).getFlowName();
        verify(flow).getFlowName();



        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(null);
        flowInstanceCheckTest.checkFlowInstance(userId,flow);
        verify(tenantManage,times(2)).fetchVNSpace(userId);
        verify(tenantManage,times(4)).getUser();


        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);
        when(user.getObjects()).thenReturn(null);
        flowInstanceCheckTest.checkFlowInstance(userId,flow);
        verify(tenantManage,times(3)).fetchVNSpace(userId);
        verify(tenantManage,times(5)).getUser();
        verify(user,times(7)).getObjects();



        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);
        when(user.getObjects()).thenReturn(object);
        when(user.getObjects().getFlow()).thenReturn(null);
        flowInstanceCheckTest.checkFlowInstance(userId,flow);
        verify(tenantManage,times(4)).fetchVNSpace(userId);
        verify(tenantManage,times(6)).getUser();
        verify(user,times(10)).getObjects();
        verify(user.getObjects(),times(3)).getFlow();








    }
}