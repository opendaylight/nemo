/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.renderer.openflow.physicalnetwork;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.hosts.PhysicalHost;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.hosts.PhysicalHostKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLinkKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.nodes.PhysicalNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.nodes.PhysicalNodeKey;

import com.google.common.util.concurrent.CheckedFuture;
/**
 * Created by zhangmeng on 2015/11/30.
 */
public class DataBrokerAdapterTest extends TestCase {
    private DataBrokerAdapter dataBrokerAdapter;
    private DataBroker dataBroker;
    @Override
    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        dataBrokerAdapter = new DataBrokerAdapter(dataBroker);
    }

    @Test
    public void testGetPhysicalHostIdentifier() throws Exception {
        PhysicalHostKey physicalHostKey = mock(PhysicalHostKey.class);
        Class<DataBrokerAdapter> class1 = DataBrokerAdapter.class;
        Method method  = class1.getDeclaredMethod("getPhysicalHostIdentifier", new Class[]{PhysicalHostKey.class});

        method.setAccessible(true);
        Object object =
                method.invoke(dataBrokerAdapter, new Object[]{physicalHostKey = mock(PhysicalHostKey.class)});
        Assert.assertTrue(object != null);
    }

    @Test
    public void testGetPhysicalNodeIdentifier() throws Exception {
        PhysicalNodeKey physicalNodeKey = mock(PhysicalNodeKey.class);
        Class<DataBrokerAdapter> class1 = DataBrokerAdapter.class;
        Method method = class1.getDeclaredMethod("getPhysicalNodeIdentifier",new Class[]{PhysicalNodeKey.class});

        method.setAccessible(true);
        Object object = method.invoke(dataBrokerAdapter,physicalNodeKey);
        Assert.assertTrue(object != null);

    }

    @Test
    public void testGetPhysicalLinkIdentifier() throws Exception {
        PhysicalLinkKey physicalLinkKey  = mock(PhysicalLinkKey.class);
        Class<DataBrokerAdapter> class1 = DataBrokerAdapter.class;
        Method method = class1.getDeclaredMethod("getPhysicalLinkIdentifier",new Class[]{PhysicalLinkKey.class});

        method.setAccessible(true);
        Object object = method.invoke(dataBrokerAdapter,physicalLinkKey);
        Assert.assertTrue(object != null);
    }

    @Test
    public void testAddPhysicalHost() throws Exception {
        final PhysicalHost physicalHost = mock(PhysicalHost.class);
        WriteTransaction writeTransaction = mock(WriteTransaction.class);
        PhysicalHostKey physicalHostKey = mock(PhysicalHostKey.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);

        Class<DataBrokerAdapter> class1 = DataBrokerAdapter.class;
        Method method = class1.getDeclaredMethod("addPhysicalHost",new Class[]{PhysicalHost.class});
        method.setAccessible(true);

        when(dataBroker.newWriteOnlyTransaction()).thenReturn(writeTransaction);
        when(physicalHost.getKey()).thenReturn(physicalHostKey);
        when(writeTransaction.submit()).thenReturn(checkedFuture);

        method.invoke(dataBrokerAdapter, physicalHost);

        verify(dataBroker).newWriteOnlyTransaction();
        verify(physicalHost).getKey();
        verify(writeTransaction).submit();
    }
    @Test
    public void testAddPhysicalNode() throws Exception {
        final PhysicalNode physicalNode = mock(PhysicalNode.class);
        WriteTransaction writeTransaction = mock(WriteTransaction.class);
        PhysicalNodeKey physicalNodeKey = mock(PhysicalNodeKey.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);

        Class<DataBrokerAdapter> class1 = DataBrokerAdapter.class;
        Method method = class1.getDeclaredMethod("addPhysicalNode",new Class[]{PhysicalNode.class});
        method.setAccessible(true);

        when(dataBroker.newWriteOnlyTransaction()).thenReturn(writeTransaction);
        when(physicalNode.getKey()).thenReturn(physicalNodeKey);
        when(writeTransaction.submit()).thenReturn(checkedFuture);

//        boolean b = false;
        Assert.assertNotNull(method.invoke(dataBrokerAdapter, physicalNode));

        verify(dataBroker).newWriteOnlyTransaction();
        verify(physicalNode).getKey();
        verify(writeTransaction).submit();

    }
    @Test
    public void testRemovePhysicalNode() throws Exception {
        final PhysicalNodeKey nodeKey = mock(PhysicalNodeKey.class);
        WriteTransaction writeTransaction = mock(WriteTransaction.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);

        Class<DataBrokerAdapter> class1 = DataBrokerAdapter.class;
        Method method = class1.getDeclaredMethod("removePhysicalNode",new Class[]{PhysicalNodeKey.class});
        method.setAccessible(true);

        when(dataBroker.newWriteOnlyTransaction()).thenReturn(writeTransaction);
        when(writeTransaction.submit()).thenReturn(checkedFuture);

        Assert.assertNotNull(method.invoke(dataBrokerAdapter, nodeKey));

        verify(dataBroker).newWriteOnlyTransaction();
        verify(writeTransaction).submit();

    }
    @Test
    public void testAddPhysicalLink() throws Exception {
        final PhysicalLink physicalLink = mock(PhysicalLink.class);
        WriteTransaction writeTransaction = mock(WriteTransaction.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);
        PhysicalLinkKey physicalLinkKey = mock(PhysicalLinkKey.class);

        Class<DataBrokerAdapter> class1 = DataBrokerAdapter.class;
        Method method = class1.getDeclaredMethod("addPhysicalLink",new Class[]{PhysicalLink.class});
        method.setAccessible(true);

        when(dataBroker.newWriteOnlyTransaction()).thenReturn(writeTransaction);
        when(physicalLink.getKey()).thenReturn(physicalLinkKey);
        when(writeTransaction.submit()).thenReturn(checkedFuture);

        method.invoke(dataBrokerAdapter, physicalLink);

        verify(dataBroker).newWriteOnlyTransaction();
        verify(physicalLink).getKey();
        verify(writeTransaction).submit();
    }

    @Test
    public void testRemovePhysicalLink() throws Exception {
        final PhysicalLinkKey physicalLinkKey = mock(PhysicalLinkKey.class);
        WriteTransaction writeTransaction = mock(WriteTransaction.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);

        Class<DataBrokerAdapter> class1 = DataBrokerAdapter.class;
        Method method = class1.getDeclaredMethod("removePhysicalLink", new Class[]{PhysicalLinkKey.class});
        method.setAccessible(true);

        when(dataBroker.newWriteOnlyTransaction()).thenReturn(writeTransaction);
        when(writeTransaction.submit()).thenReturn(checkedFuture);

        method.invoke(dataBrokerAdapter, physicalLinkKey);

        verify(dataBroker).newWriteOnlyTransaction();
        verify(writeTransaction).submit();

    }

}