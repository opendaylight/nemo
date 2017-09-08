/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.renderer.openflow;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.CheckedFuture;
import java.lang.reflect.Method;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.nemo.renderer.openflow.physicalnetwork.PhyConfigLoader;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
/**
 * Created by zhangmeng on 2015/12/1.
 */
public class FlowTableManagerTest extends TestCase {
    private FlowTableManager flowTableManager;
    private DataBroker dataProvider;
    private PacketProcessingService packetProcessingService;
    private PhyConfigLoader phyConfigLoader;
    @Override
    @Before
    public void setUp() throws Exception {
        dataProvider = mock(DataBroker.class);
        packetProcessingService = mock(PacketProcessingService.class);
        phyConfigLoader = mock(PhyConfigLoader.class);

        flowTableManager = new FlowTableManager(dataProvider,packetProcessingService,phyConfigLoader);
    }
    @Test
    public void testGetFlowUtils() throws Exception {
        FlowUtils flowUtils;

        Class<FlowTableManager> class1 = FlowTableManager.class;
        Method method = class1.getDeclaredMethod("getFlowUtils");
        method.setAccessible(true);

        flowUtils = (FlowUtils) method.invoke(flowTableManager);
        Assert.assertTrue(flowUtils != null);
    }

    @Test
    public void testClose() throws Exception {
        flowTableManager.close();
        Assert.assertTrue(flowTableManager != null);
    }

    @Test
    public void testGetUser()throws  Exception{
        UserId userId = mock(UserId.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);
        Optional<User> result = mock(Optional.class);
        User user = mock(User.class);
        User user1 ;

        Class<FlowTableManager> class1 = FlowTableManager.class;
        Method method = class1.getDeclaredMethod("getUser",new Class[]{UserId.class});
        method.setAccessible(true);

        when(dataProvider.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(checkedFuture);
        when(checkedFuture.get()).thenReturn(result);
        when(result.isPresent())
                .thenReturn(false)
                .thenReturn(true);
        when(result.get()).thenReturn(user);

        //branch 1
        user1 = (User)method.invoke(flowTableManager,userId);
        Assert.assertTrue(user1 == null);

        //branch2
        user1 = (User)method.invoke(flowTableManager,userId);
        Assert.assertTrue(user1 != null);

    }

    @Test
    public void testGetVirtualNetwork()throws  Exception{
        UserId userId = mock(UserId.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);
        Optional<VirtualNetwork> result = mock(Optional.class);
        VirtualNetwork virtualNetwork = mock(VirtualNetwork.class);
        VirtualNetwork virtualNetwork1 ;

        Class<FlowTableManager> class1 = FlowTableManager.class;
        Method method = class1.getDeclaredMethod("getVirtualNetwork", new Class[]{UserId.class});
        method.setAccessible(true);

        when(userId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
        when(dataProvider.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(checkedFuture);
        when(checkedFuture.get()).thenReturn(result);
        when(result.isPresent())
                .thenReturn(false)
                .thenReturn(true);
        when(result.get()).thenReturn(virtualNetwork);

        //branch 1
        virtualNetwork1 = (VirtualNetwork) method.invoke(flowTableManager,userId);
        Assert.assertTrue(virtualNetwork1 == null);

        //branch 2
        virtualNetwork1 = (VirtualNetwork) method.invoke(flowTableManager,userId);
        Assert.assertTrue(virtualNetwork1 != null);
    }

    @Test
    public void testGetUserIntentVnMapping()throws  Exception{
        UserId userId = mock(UserId.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);
        Optional<UserIntentVnMapping> result = mock(Optional.class);
        UserIntentVnMapping vnMapping;
//        InstanceIdentifier instanceIdentifier

        Class<FlowTableManager> class1 = FlowTableManager.class;
        Method method = class1.getDeclaredMethod("getUserIntentVnMapping",new Class[]{UserId.class});
        method.setAccessible(true);

        when(dataProvider.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(checkedFuture);
        when(checkedFuture.get()).thenReturn(result);
        when(result.isPresent())
                .thenReturn(false)
                .thenReturn(true);
        when(result.get()).thenReturn(mock(UserIntentVnMapping.class));

        //branch1
        vnMapping = (UserIntentVnMapping) method.invoke(flowTableManager,userId);
        Assert.assertTrue(vnMapping == null);

        //branch2
        vnMapping = (UserIntentVnMapping) method.invoke(flowTableManager,userId);
        Assert.assertTrue(vnMapping != null);
    }
}
