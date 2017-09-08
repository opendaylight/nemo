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
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;

/**
 * Created by zhangmeng on 2015/11/24.
 */
public class OFNodeListenerTest {
    private final PhysicalNetworkAdapter mockAdapter = mock(PhysicalNetworkAdapter.class);
    private OFNodeListener listener;

    @Before
    public void setUp() throws Exception {
        listener = new OFNodeListener(mockAdapter);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testOnDataTreeChanged() throws Exception {
        DataTreeModification<Node> mockDataTreeModification = mock(DataTreeModification.class);
        DataObjectModification<Node> mockModification = mock(DataObjectModification.class);
        doReturn(mockModification).when(mockDataTreeModification).getRootNode();

        Node mockNode = mock(Node.class);
        doReturn(mockNode).when(mockModification).getDataAfter();
        doReturn(DataObjectModification.ModificationType.WRITE).when(mockModification).getModificationType();

        listener.onDataTreeChanged(Collections.singletonList(mockDataTreeModification));

        verify(mockAdapter).ofNodeAdded(mockNode);
    }
}
