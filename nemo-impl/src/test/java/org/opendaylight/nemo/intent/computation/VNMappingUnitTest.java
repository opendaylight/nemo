package org.test.computation;

        import junit.framework.TestCase;
        import org.junit.Assert;
        import org.junit.Before;
        import org.junit.Test;
        import org.opendaylight.nemo.intent.computation.PNComputationUnit;
        import org.opendaylight.nemo.intent.computation.VNComputationUnit;
        import org.opendaylight.nemo.intent.computation.VNMappingUnit;
        import com.google.common.base.Optional;
        import org.opendaylight.controller.md.sal.binding.api.DataBroker;
        import org.opendaylight.controller.md.sal.binding.api.DataChangeListener;
        import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
        import org.opendaylight.controller.md.sal.common.api.data.AsyncDataBroker.DataChangeScope;
        import org.opendaylight.controller.md.sal.common.api.data.AsyncDataChangeEvent;
        import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNetwork;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNodeAttributeDefinitions;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalPortAttributeDefinitions;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.attribute.definition.AttributeMatchPatterns;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.attribute.instance.attribute.value.RangeValue;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalLinks;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalNodes;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLink;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.nodes.PhysicalNode;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.paths.PhysicalPath;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.paths.PhysicalPathBuilder;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.attribute.definitions.PhysicalNodeAttributeDefinition;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.attribute.definitions.PhysicalNodeAttributeDefinitionKey;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.instance.PhysicalPort;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.port.attribute.definitions.PhysicalPortAttributeDefinition;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.port.attribute.definitions.PhysicalPortAttributeDefinitionKey;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.port.instance.Attribute;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLinkBuilder;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNode;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.VirtualPort;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.port.instance.PhysicalResourceRequirement;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.PhysicalResourceInstance;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.VirtualResourceInstance;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.UserVnPnMapping;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.user.vn.pn.mapping.VnPnMappingResult;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.user.vn.pn.mapping.VnPnMappingResultBuilder;
        import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.*;
        import org.opendaylight.yangtools.concepts.ListenerRegistration;
        import org.opendaylight.yangtools.yang.binding.DataObject;
        import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import java.util.Map;
        import static org.mockito.Mockito.*;
        import static org.junit.Assert.*;
        import java.util.*;
/**
 * Created by zhangmeng on 2015/11/9.
 */
public class VNMappingUnitTest extends TestCase {
    private VNMappingUnit vnMappingUnit;
    private DataBroker dataBroker;
    private PNComputationUnit pnComputationUnit;
    private Map<PhysicalNodeId, Map<PhysicalPortId, ListenerRegistration<DataChangeListener>>> physicalPortChangeListenerRegs;
    private ListenerRegistration<DataChangeListener> physicalNodeChangeListenerReg;
    private ListenerRegistration<DataChangeListener> physicalLinkChangeListenerReg;

    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        pnComputationUnit = mock(PNComputationUnit.class);
        vnMappingUnit =  mock(VNMappingUnit.class);

    }

    @Test
    public void testVirtualNetworkMapping() throws Exception {
        VirtualNetwork virtualNetwork = mock(VirtualNetwork.class);
        UserVnPnMapping userVnPnMapping = mock(UserVnPnMapping.class);
        List<PhysicalPath> physicalPaths = new ArrayList<PhysicalPath>();
        vnMappingUnit.virtualNetworkMapping(virtualNetwork,userVnPnMapping,physicalPaths);
        Assert.assertNotNull(vnMappingUnit);
        verify(vnMappingUnit).virtualNetworkMapping(any(VirtualNetwork.class),any(UserVnPnMapping.class),any(List.class));
    }

    @Test
    public void testClose() throws Exception {
        Assert.assertNotNull(vnMappingUnit);
        vnMappingUnit.close();
        verify(vnMappingUnit).close();
    }
}