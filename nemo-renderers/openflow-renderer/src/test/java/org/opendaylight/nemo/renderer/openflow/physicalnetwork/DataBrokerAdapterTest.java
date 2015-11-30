package org.opendaylight.nemo.renderer.openflow.physicalnetwork;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.nemo.renderer.openflow.physicalnetwork.DataBrokerAdapter;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalHosts;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalLinks;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalNodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.hosts.PhysicalHost;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.hosts.PhysicalHostKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLinkKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.nodes.PhysicalNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.nodes.PhysicalNodeKey;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/11/30.
 */
public class DataBrokerAdapterTest extends TestCase {
    private DataBrokerAdapter dataBrokerAdapter;
    private DataBroker dataBroker;
    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        dataBrokerAdapter = new DataBrokerAdapter(dataBroker);
    }

    @Test
    public void testAddPhysicalHost() throws Exception {
        PhysicalHostKey physicalHostKey = mock(PhysicalHostKey.class);
        Class<DataBrokerAdapter> class1 = DataBrokerAdapter.class;
        Method method  = class1.getDeclaredMethod("getPhysicalHostIdentifier", new Class[]{PhysicalHostKey.class});

        method.setAccessible(true);
        Object result = method.invoke(dataBrokerAdapter,new Object[]{physicalHostKey = mock(PhysicalHostKey.class)});
    }

    @Test
    public void testAddPhysicalNode() throws Exception {

    }

    @Test
    public void testRemovePhysicalNode() throws Exception {

    }

    @Test
    public void testAddPhysicalLink() throws Exception {

    }

    @Test
    public void testRemovePhysicalLink() throws Exception {

    }
}