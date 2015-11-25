package org.opendaylight.nemo.intent;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import com.google.common.base.Optional;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.ReadWriteTransaction;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.nemo.intent.IntentResolver;
import org.opendaylight.nemo.intent.computation.PNComputationUnit;
import org.opendaylight.nemo.intent.computation.VNComputationUnit;
import org.opendaylight.nemo.intent.computation.VNMappingUnit;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalLinkId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalPaths;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.paths.PhysicalPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.paths.PhysicalPathKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.VirtualNetworks;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetworkBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetworkKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.arps.VirtualArp;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.routes.VirtualRoute;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.IntentVnMappingResults;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.VnPnMappingResults;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMappingBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMappingKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.UserVnPnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.UserVnPnMappingBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.UserVnPnMappingKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.user.vn.pn.mapping.VnPnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalPathId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNetworkId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalLinks;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalNodes;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.util.concurrent.CheckedFuture;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/11/25.
 */
public class IntentResolverTest extends TestCase {
    private IntentResolver intentResolver;
    private DataBroker dataBroker;
    @Before
    public void setUp() throws Exception {
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        Optional<PhysicalNodes> result = mock(Optional.class);
        Optional<PhysicalLinks> result1 = mock(Optional.class);
        CheckedFuture future = mock(CheckedFuture.class);
        CheckedFuture future1 = mock(CheckedFuture.class);
        dataBroker = mock(DataBroker.class);

        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class),any(InstanceIdentifier.class)))
                .thenReturn(future).thenReturn(future1);
        when(future.get()).thenReturn(result);
        when(future1.get()).thenReturn(result1);
        when(result.isPresent()).thenReturn(false);
        when(result1.isPresent()).thenReturn(false);

        intentResolver = new IntentResolver(dataBroker);
        verify(dataBroker).newReadOnlyTransaction();
        verify(readOnlyTransaction,times(2)).read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class));
        verify(future).get();
        verify(future1).get();
        verify(result).isPresent();
        verify(result1).isPresent();

    }

    @Test
    public void testResolveIntent() throws Exception {
//        UserId userId = mock(UserId.class);
//
//        when(userId.getValue()).thenReturn(new String());
//        intentResolver.resolveIntent(userId);
    }

    @Test
    public void testClose() throws Exception {
        intentResolver.close();
        Assert.assertNotNull(intentResolver);
    }
}