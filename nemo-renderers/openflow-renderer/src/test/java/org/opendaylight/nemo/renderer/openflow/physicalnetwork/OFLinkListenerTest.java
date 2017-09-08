/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.renderer.openflow.physicalnetwork;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.controller.md.sal.binding.api.DataObjectModification;
import org.opendaylight.controller.md.sal.binding.api.DataTreeModification;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.topology.Link;
/**
 * Created by zhangmeng on 2015/11/19.
 */
public class OFLinkListenerTest {
    private final PhysicalNetworkAdapter mockAdapter = mock(PhysicalNetworkAdapter.class);
    private OFLinkListener listener;

    @Before
    public void setUp() throws Exception {
        listener = new OFLinkListener(mockAdapter);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnDataTreeChanged() throws Exception {
        DataTreeModification<Link> mockDataTreeModification = mock(DataTreeModification.class);
        DataObjectModification<Link> mockModification = mock(DataObjectModification.class);
        doReturn(mockModification).when(mockDataTreeModification).getRootNode();

        Link mockLink = mock(Link.class);
        doReturn(mockLink).when(mockModification).getDataAfter();
        doReturn(DataObjectModification.ModificationType.WRITE).when(mockModification).getModificationType();

        listener.onDataTreeChanged(Collections.singletonList(mockDataTreeModification));

        verify(mockAdapter).ofLinkAdded(mockLink);
    }
}
