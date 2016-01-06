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
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.ListenableFuture;
import static org.junit.Assert.*;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.nemo.renderer.openflow.OpenflowRenderer;
import org.opendaylight.nemo.renderer.openflow.physicalnetwork.PhyConfigLoader;
import org.opendaylight.nemo.renderer.openflow.physicalnetwork.PhysicalNetworkAdapter;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/11/24.
 */
public class OpenflowRendererTest extends TestCase {
    private OpenflowRenderer openflowRenderer;
    private DataBroker dataBroker;
    private NotificationProviderService notificationProviderService;
    private PacketProcessingService packetProcessingService;

    @Before
    public void setUp() throws Exception {
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class); 
                
        dataBroker = mock(DataBroker.class);
        notificationProviderService = mock(NotificationProviderService.class);
        packetProcessingService = mock(PacketProcessingService.class);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class),any(InstanceIdentifier.class))).thenReturn(checkedFuture);

        openflowRenderer = new OpenflowRenderer(dataBroker,notificationProviderService,packetProcessingService);
    }

    @Test
    public void testClose() throws Exception {
        openflowRenderer.close();
        Assert.assertNotNull(openflowRenderer);
    }
}