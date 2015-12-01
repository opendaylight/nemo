/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.renderer.openflow;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import com.google.common.base.Optional;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.DataChangeListener;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataBroker.DataChangeScope;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataChangeEvent;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.nemo.renderer.openflow.FlowTableManager;
import org.opendaylight.nemo.renderer.openflow.FlowUtils;
import org.opendaylight.nemo.renderer.openflow.physicalnetwork.PhyConfigLoader;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalNodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.nodes.PhysicalNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.VirtualNetworks;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetworkKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.IntentVnMappingResults;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.VnPnMappingResults;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMappingKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.UserVnPnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNetworkId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.yangtools.concepts.ListenerRegistration;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.util.concurrent.CheckedFuture;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import com.google.common.base.Optional;
import java.util.Set;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/12/1.
 */
public class FlowTableManagerTest extends TestCase {
    private FlowTableManager flowTableManager;
    private DataBroker dataProvider;
    private PacketProcessingService packetProcessingService;
    private PhyConfigLoader phyConfigLoader;
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