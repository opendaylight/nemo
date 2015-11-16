/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.test.computation;
        import com.google.common.base.Optional;
        import org.opendaylight.controller.md.sal.binding.api.DataBroker;
        import org.opendaylight.controller.md.sal.binding.api.DataChangeListener;
        import org.opendaylight.controller.md.sal.binding.api.ReadWriteTransaction;
        import org.opendaylight.controller.md.sal.common.api.data.AsyncDataBroker.DataChangeScope;
        import org.opendaylight.controller.md.sal.common.api.data.AsyncDataChangeEvent;
        import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
        import org.opendaylight.nemo.intent.algorithm.Edge;
        import org.opendaylight.nemo.intent.algorithm.RoutingAlgorithm;
        import org.opendaylight.nemo.intent.algorithm.Vertex;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.VirtualNetworks;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetworkKey;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.VirtualLinks;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.VirtualNodes;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.VirtualPaths;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.VirtualRoutes;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNode;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPath;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPathBuilder;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPathKey;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.routes.VirtualRoute;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.routes.VirtualRouteBuilder;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.routes.VirtualRouteKey;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLinkBuilder;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualLinkId;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNetworkId;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNodeId;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualPathId;
        import org.opendaylight.yangtools.concepts.ListenerRegistration;
        import org.opendaylight.yangtools.yang.binding.DataObject;
        import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import static org.mockito.Mockito.*;
        import junit.framework.TestCase;
        import org.junit.Assert;
        import org.junit.Before;
        import org.junit.Test;
        import org.opendaylight.nemo.intent.computation.VNComputationUnit;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualLinkId;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNetworkId;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNodeId;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualPathId;
        import static org.junit.Assert.*;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
/**
 * Created by zhangmeng on 2015/11/9.
 */
public class VNComputationUnitTest extends TestCase {

    private VNComputationUnit vnComputationUnit;
    private DataBroker dataBroker;
    private UserId userId;
    private VirtualNodeId source,target;
    private VirtualNodeId source1,target1;
    private long bandwidth;
    @Before
    public void setUp() throws Exception {

        source = mock(VirtualNodeId.class);
        target = mock(VirtualNodeId.class);
        source1 = mock(VirtualNodeId.class);
        target1 = mock(VirtualNodeId.class);
        vnComputationUnit = mock(VNComputationUnit.class);
    }

    @Test
    public void testComputePath() throws Exception {
        vnComputationUnit.computePath(source, target);
        verify(vnComputationUnit).computePath(source, target);
        Assert.assertNotEquals(mock(VirtualPath.class), vnComputationUnit.computePath(source, target));
        Assert.assertNotNull(vnComputationUnit);
    }

    @Test
    public void testComputePath1() throws Exception {
        vnComputationUnit.computePath(source1, target1, bandwidth);
        verify(vnComputationUnit).computePath(source1, target1, bandwidth);
        Assert.assertNotEquals(mock(VirtualPath.class), vnComputationUnit.computePath(source1, target1, bandwidth));
        Assert.assertNotNull(vnComputationUnit);
    }

    @Test
    public void testClose() throws Exception {
        vnComputationUnit.close();
        Assert.assertNotNull(vnComputationUnit);
        verify(vnComputationUnit).close();
    }
}