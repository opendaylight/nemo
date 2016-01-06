/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.renderer.openflow.physicalnetwork;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.nemo.renderer.openflow.physicalnetwork.OFNodeListener;

import static org.junit.Assert.*;
import org.opendaylight.controller.md.sal.binding.api.DataChangeListener;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataChangeEvent;
import org.opendaylight.nemo.renderer.openflow.physicalnetwork.PhysicalNetworkAdapter;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/11/24.
 */
public class OFNodeListenerTest extends OFNodeListener {
    public OFNodeListenerTest(){
        super(mock(PhysicalNetworkAdapter.class));
    }
    private AsyncDataChangeEvent <InstanceIdentifier<?>, DataObject>asyncDataChangeEvent;
    @Before
    public void setUp() throws Exception {
        asyncDataChangeEvent = mock(AsyncDataChangeEvent.class);
    }

    @Test
    public void testOnDataChanged() throws Exception {
        this.onDataChanged(null);
        this.onDataChanged(asyncDataChangeEvent);
        verify(asyncDataChangeEvent).getCreatedData();
        verify(asyncDataChangeEvent).getUpdatedData();
        verify(asyncDataChangeEvent).getOriginalData();
        verify(asyncDataChangeEvent).getRemovedPaths();
        Assert.assertNotNull(this);
    }
}