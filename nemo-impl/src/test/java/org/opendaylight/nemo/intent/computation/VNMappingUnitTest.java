/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent.computation;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.CheckedFuture;
import org.junit.runner.RunWith;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.AAA;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalResourceEntityId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNetworkId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.AttributeDefinition.AttributeValueType;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualLinkId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.VirtualLinks;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.attribute.instance.AttributeValue;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.AttributeName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNodeAttributeDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalPortAttributeDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.attribute.definition.AttributeMatchPatterns;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.attribute.instance.attribute.value.RangeValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalNodes;
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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.VirtualNodeInstance.NodeType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.VirtualResourceInstance;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.UserVnPnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.user.vn.pn.mapping.VnPnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.user.vn.pn.mapping.VnPnMappingResultBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.VirtualNodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.*;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/12/21.
 */
public class VNMappingUnitTest extends TestCase {
    private Class<VNMappingUnit> class1;
    private Method method;
    private DataBroker dataBroker;
    private PNComputationUnit pnComputationUnit;
    private PNResourcesTracker pnResourcesTracker;
    private VNMappingUnit vnMappingUnit;

    @Before
    public void setUp() throws Exception {
        class1 = VNMappingUnit.class;
        dataBroker = mock(DataBroker.class);
        pnComputationUnit = mock(PNComputationUnit.class);
        pnResourcesTracker = mock(PNResourcesTracker.class);

        vnMappingUnit = new VNMappingUnit(dataBroker,pnComputationUnit,pnResourcesTracker);
    }

    @Test
    public void testVirtualNetworkMapping() throws Exception {
        VirtualNetwork virtualNetwork = mock(VirtualNetwork.class);
        UserVnPnMapping userVnPnMapping = mock(UserVnPnMapping.class);
        List<PhysicalPath> physicalPaths = new ArrayList<PhysicalPath>();

        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        PhysicalNodes physicalNodes = mock(PhysicalNodes.class);
        VirtualNodes virtualNodes_temp = mock(VirtualNodes.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);
        VirtualNode virtualNode = mock(VirtualNode.class);
        VirtualLinks virtualLinks_temp = mock(VirtualLinks.class);
        VirtualLink virtualLink = mock(VirtualLink.class);
        VirtualNetworkId virtualNetworkId = mock(VirtualNetworkId.class);
        VirtualNodeId virtualNodeId = mock(VirtualNodeId.class);
        VnPnMappingResult vnPnMappingResult_virtualLinkMapping = mock(VnPnMappingResult.class);
        UserId userId = mock(UserId.class);
        VirtualLinkId virtualLinkId = mock(VirtualLinkId.class);
        PhysicalResourceEntityId physicalResourceEntityId_virtualLinkMapping = mock(PhysicalResourceEntityId.class);
        Optional<PhysicalNodes> result = Optional.of(physicalNodes);
        List<PhysicalNode> physicalNodeList = new ArrayList<PhysicalNode>();
        List<VirtualNode> virtualNodes = new ArrayList<VirtualNode>();
        List<VirtualLink> virtualLinks = new ArrayList<VirtualLink>();
        List<VnPnMappingResult> vnPnMappingResults = new ArrayList<VnPnMappingResult>();
        List<VnPnMappingResult> vnPnMappingResults_virtualLinkMapping = new ArrayList<VnPnMappingResult>();

        virtualLinks.add(virtualLink);
        vnPnMappingResults_virtualLinkMapping.add(vnPnMappingResult_virtualLinkMapping);

        Assert.assertTrue(result.isPresent());
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(checkedFuture);
        when(checkedFuture.get()).thenReturn(result);
        when(physicalNodes.getPhysicalNode()).thenReturn(physicalNodeList);
        when(virtualNetwork.getUserId()).thenReturn(userId);
        when(userVnPnMapping.getVnPnMappingResult())
                .thenReturn(vnPnMappingResults)
                .thenReturn(vnPnMappingResults_virtualLinkMapping);
        when(virtualNetwork.getVirtualNodes()).thenReturn(virtualNodes_temp);
        when(virtualNodes_temp.getVirtualNode()).thenReturn(virtualNodes);
        //for circle 1
            //TODO
        //for circle 2
        when(virtualNetwork.getVirtualLinks()).thenReturn(virtualLinks_temp);
        when(virtualLinks_temp.getVirtualLink()).thenReturn(virtualLinks);
        when(virtualNetwork.getNetworkId()).thenReturn(virtualNetworkId);
        //get into method "virtualLinkMapping" args(virtualNetworkId,virtualLink,userVnPnMapping)
        when(virtualLink.getSrcNodeId()).thenReturn(virtualNodeId);
        when(virtualLink.getDestNodeId()).thenReturn(virtualNodeId);
        when(virtualNodeId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
        //powermock
//        PowerMockito.mockStatic(VNMappingUnitUtils.class);
//        PowerMockito.when(VNMappingUnitUtils.getVnPnMappingResult(vnPnMappingResults_virtualLinkMapping, new VirtualResourceEntityId(new String("00001111-0000-0000-0000-000011112222"))))
//                .thenReturn(vnPnMappingResult_virtualLinkMapping);
        when(vnPnMappingResult_virtualLinkMapping.getVirtualResourceEntityId()).thenReturn(new VirtualResourceEntityId(new String("00001111-0000-0000-0000-000011112222")));
        when(vnPnMappingResult_virtualLinkMapping.getPhysicalResourceEntityId()).thenReturn(physicalResourceEntityId_virtualLinkMapping);
        when(physicalResourceEntityId_virtualLinkMapping.getValue()).thenReturn(new String("test"));
        when(virtualLink.getBandwidth()).thenReturn(1L);
        //return to main
        doNothing().when(pnResourcesTracker).addPhysicalPath(any(UserId.class),any(PhysicalPath.class));
        when(virtualLink.getLinkId()).thenReturn(virtualLinkId);
        when(virtualLinkId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));

        vnMappingUnit.virtualNetworkMapping(virtualNetwork, userVnPnMapping, physicalPaths);
        verify(pnResourcesTracker).addPhysicalPath(any(UserId.class), any(PhysicalPath.class));
        verify(userVnPnMapping,times(2)).getVnPnMappingResult();
        verify(virtualLink,times(2)).getSrcNodeId();
        verify(virtualLink,times(2)).getDestNodeId();
        verify(virtualNodeId,times(2)).getValue();
    }

    @Test
    public void testVirtualNetworkMapping1() throws Exception {
        VirtualNodeId virtualNodeId = mock(VirtualNodeId.class);
        VirtualNetworkId virtualNetworkId = mock(VirtualNetworkId.class);
        VnPnMappingResult vnPnMappingResult_virtualLinkMapping = mock(VnPnMappingResult.class);
        VirtualNetwork virtualNetwork = mock(VirtualNetwork.class);
        UserVnPnMapping userVnPnMapping = mock(UserVnPnMapping.class);
        VirtualLinks virtualLinks_temp = mock(VirtualLinks.class);
        VirtualLink virtualLink = mock(VirtualLink.class);
        VirtualLinkId virtualLinkId = mock(VirtualLinkId.class);
        PhysicalResourceEntityId physicalResourceEntityId_virtualLinkMapping = mock(PhysicalResourceEntityId.class);
        List<VirtualLink> virtualLinks = new ArrayList<VirtualLink>();
        List<VirtualLink> unmappedVirtualLinks = new ArrayList<VirtualLink>();
        List<PhysicalPath> physicalPaths = new ArrayList<PhysicalPath>();
        List<VnPnMappingResult> vnPnMappingResults = new ArrayList<VnPnMappingResult>();
        List<VnPnMappingResult> vnPnMappingResults_virtualLinkMapping = new ArrayList<VnPnMappingResult>();

        unmappedVirtualLinks.add(virtualLink);
        virtualLinks.add(virtualLink);
        vnPnMappingResults_virtualLinkMapping.add(vnPnMappingResult_virtualLinkMapping);

        when(virtualNetwork.getVirtualLinks()).thenReturn(virtualLinks_temp);
        when(virtualLinks_temp.getVirtualLink()).thenReturn(virtualLinks);
        when(userVnPnMapping.getVnPnMappingResult())
                .thenReturn(vnPnMappingResults)
                .thenReturn(vnPnMappingResults_virtualLinkMapping);;
        when(virtualNetwork.getNetworkId()).thenReturn(virtualNetworkId);
        //get into method "virtualLinkMapping" args(virtualNetworkId,virtualLink,userVnPnMapping)
        when(virtualLink.getSrcNodeId()).thenReturn(virtualNodeId);
        when(virtualLink.getDestNodeId()).thenReturn(virtualNodeId);
        when(virtualNodeId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
        //powermock
//        PowerMockito.mockStatic(VNMappingUnitUtils.class);
//        PowerMockito.when(VNMappingUnitUtils.getVnPnMappingResult(vnPnMappingResults_virtualLinkMapping, new VirtualResourceEntityId(new String("00001111-0000-0000-0000-000011112222"))))
//                .thenReturn(vnPnMappingResult_virtualLinkMapping);
        when(vnPnMappingResult_virtualLinkMapping.getVirtualResourceEntityId()).thenReturn(new VirtualResourceEntityId(new String("00001111-0000-0000-0000-000011112222")));
        when(vnPnMappingResult_virtualLinkMapping.getPhysicalResourceEntityId()).thenReturn(physicalResourceEntityId_virtualLinkMapping);
        when(physicalResourceEntityId_virtualLinkMapping.getValue()).thenReturn(new String("test"));
        when(virtualLink.getBandwidth()).thenReturn(1L);
        //return to main
        doNothing().when(pnResourcesTracker).addPhysicalPath(any(UserId.class), any(PhysicalPath.class));
        when(virtualLink.getLinkId()).thenReturn(virtualLinkId);
        when(virtualLinkId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));

        vnMappingUnit.virtualNetworkMapping(virtualNetwork, unmappedVirtualLinks, userVnPnMapping, physicalPaths);
        verify(vnPnMappingResult_virtualLinkMapping,times(2)).getVirtualResourceEntityId();
        verify(pnResourcesTracker).addPhysicalPath(any(UserId.class), any(PhysicalPath.class));
        verify(userVnPnMapping,times(2)).getVnPnMappingResult();
        verify(virtualLink,times(2)).getSrcNodeId();
        verify(virtualLink,times(2)).getDestNodeId();
        verify(virtualNodeId,times(2)).getValue();
    }

    @Test
    public void testClose() throws Exception {
        vnMappingUnit.close();
        Assert.assertTrue(vnMappingUnit != null);
    }

    @Test
    public void testCheckAttributeRangeValueSatisfied() throws Exception {
        method = class1.getDeclaredMethod("checkAttributeRangeValueSatisfied",new Class[]{Long.class,
                RangeValue.class,
                org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.class});
        method.setAccessible(true);

        Long attributeValue = 1L;
        RangeValue requiredAttributeValue = mock(RangeValue.class);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern
                attributeMatchPattern = org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.Between;

        when(requiredAttributeValue.getMin()).thenReturn(0L);
        when(requiredAttributeValue.getMax()).thenReturn(2L);

        Assert.assertTrue((boolean) method.invoke(vnMappingUnit, attributeValue, requiredAttributeValue, attributeMatchPattern));
    }
    @Test
    public void testCheckAttributeRangeValueSatisfied1() throws Exception {
        method = class1.getDeclaredMethod("checkAttributeRangeValueSatisfied",new Class[]{Long.class,RangeValue.class,PhysicalResourceRequirement.AttributeMatchPattern.class});
        method.setAccessible(true);

        Long attributeValue = 1L;
        RangeValue requiredAttributeValue = mock(RangeValue.class);
        PhysicalResourceRequirement.AttributeMatchPattern attributeMatchPattern = PhysicalResourceRequirement.AttributeMatchPattern.Between;

        when(requiredAttributeValue.getMin()).thenReturn(0L);
        when(requiredAttributeValue.getMax()).thenReturn(2L);

        Assert.assertTrue((boolean)method.invoke(vnMappingUnit,attributeValue,requiredAttributeValue,attributeMatchPattern));
    }

    @Test
    public void testCheckAttributeIntegerValueSatisfied() throws Exception {
        method = class1.getDeclaredMethod("checkAttributeIntegerValueSatisfied",new Class[]{Long.class,
                Long.class,
                org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.class
                });
        method.setAccessible(true);

        Long attributeValue = 2L;
        Long requiredAttributeValue = 1L;
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern
                attributeMatchPattern = org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.LessThan;
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern
                attributeMatchPattern1 = org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.NotLessThan;
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern
                attributeMatchPattern2 = org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.Equal;
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern
                attributeMatchPattern3 = org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.NotEqual;
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern
                attributeMatchPattern4 = org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.GreaterThan;
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern
                attributeMatchPattern5 = org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.NotGreaterThan;

        Assert.assertTrue((boolean)method.invoke(vnMappingUnit,attributeValue,requiredAttributeValue,attributeMatchPattern) == false);
        Assert.assertTrue((boolean)method.invoke(vnMappingUnit,attributeValue,requiredAttributeValue,attributeMatchPattern1) == true);
        Assert.assertTrue((boolean)method.invoke(vnMappingUnit,attributeValue,requiredAttributeValue,attributeMatchPattern2) == false);
        Assert.assertTrue((boolean) method.invoke(vnMappingUnit, attributeValue, requiredAttributeValue, attributeMatchPattern3) == true);
        Assert.assertTrue((boolean) method.invoke(vnMappingUnit, attributeValue, requiredAttributeValue, attributeMatchPattern4) == true);
        Assert.assertTrue((boolean)method.invoke(vnMappingUnit,attributeValue,requiredAttributeValue,attributeMatchPattern5) == false);
    }

    @Test
    public void testCheckAttributeIntegerValueSatisfied1() throws Exception {
        method = class1.getDeclaredMethod("checkAttributeIntegerValueSatisfied",new Class[]{Long.class,Long.class,PhysicalResourceRequirement.AttributeMatchPattern.class});
        method.setAccessible(true);

        Long attributeValue = 2L;
        Long requiredAttributeValue = 1L;
        PhysicalResourceRequirement.AttributeMatchPattern attributeMatchPattern = PhysicalResourceRequirement.AttributeMatchPattern.LessThan;
        PhysicalResourceRequirement.AttributeMatchPattern attributeMatchPattern1 = PhysicalResourceRequirement.AttributeMatchPattern.NotLessThan;
        PhysicalResourceRequirement.AttributeMatchPattern attributeMatchPattern2 = PhysicalResourceRequirement.AttributeMatchPattern.Equal;
        PhysicalResourceRequirement.AttributeMatchPattern attributeMatchPattern3 = PhysicalResourceRequirement.AttributeMatchPattern.NotEqual;
        PhysicalResourceRequirement.AttributeMatchPattern attributeMatchPattern4 = PhysicalResourceRequirement.AttributeMatchPattern.GreaterThan;
        PhysicalResourceRequirement.AttributeMatchPattern attributeMatchPattern5 = PhysicalResourceRequirement.AttributeMatchPattern.NotGreaterThan;

        Assert.assertTrue(!(boolean)method.invoke(vnMappingUnit,attributeValue,requiredAttributeValue,attributeMatchPattern));
        Assert.assertTrue((boolean)method.invoke(vnMappingUnit,attributeValue,requiredAttributeValue,attributeMatchPattern1));
        Assert.assertTrue(!(boolean)method.invoke(vnMappingUnit,attributeValue,requiredAttributeValue,attributeMatchPattern2));
        Assert.assertTrue((boolean) method.invoke(vnMappingUnit,attributeValue, requiredAttributeValue, attributeMatchPattern3));
        Assert.assertTrue((boolean) method.invoke(vnMappingUnit,attributeValue, requiredAttributeValue, attributeMatchPattern4));
        Assert.assertTrue(!(boolean)method.invoke(vnMappingUnit,attributeValue,requiredAttributeValue,attributeMatchPattern5));
    }

    @Test
    public void testCheckAttributeStringValueSatisfied() throws Exception {
        method = class1.getDeclaredMethod("checkAttributeStringValueSatisfied",new Class[]{
                String.class,
                String.class,
                org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.class
        });
        method.setAccessible(true);

        String attributeValue = new String("test");
        String requiredAttributeValue = new String("test");
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern
                attributeMatchPattern = org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.LessThan;
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern
                attributeMatchPattern1 = org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.NotLessThan;
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern
                attributeMatchPattern2 = org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.Equal;
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern
                attributeMatchPattern3 = org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.NotEqual;
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern
                attributeMatchPattern4 = org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.GreaterThan;
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern
                attributeMatchPattern5 = org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.NotGreaterThan;

        Assert.assertTrue(!(boolean)method.invoke(vnMappingUnit,attributeValue,requiredAttributeValue,attributeMatchPattern));
        Assert.assertTrue((boolean)method.invoke(vnMappingUnit,attributeValue,requiredAttributeValue,attributeMatchPattern1));
        Assert.assertTrue((boolean)method.invoke(vnMappingUnit,attributeValue,requiredAttributeValue,attributeMatchPattern2));
        Assert.assertTrue(!(boolean) method.invoke(vnMappingUnit,attributeValue, requiredAttributeValue, attributeMatchPattern3));
        Assert.assertTrue(!(boolean) method.invoke(vnMappingUnit,attributeValue, requiredAttributeValue, attributeMatchPattern4));
        Assert.assertTrue((boolean)method.invoke(vnMappingUnit,attributeValue,requiredAttributeValue,attributeMatchPattern5));
    }

    @Test
    public void testCheckAttributeStringValueSatisfied1() throws Exception {
        method = class1.getDeclaredMethod("checkAttributeStringValueSatisfied",new Class[]{
                String.class,
                String.class,
                PhysicalResourceRequirement.AttributeMatchPattern.class
        });
        method.setAccessible(true);

        String attributeValue = new String("test");
        String requiredAttributeValue = new String("test");
        PhysicalResourceRequirement.AttributeMatchPattern attributeMatchPattern = PhysicalResourceRequirement.AttributeMatchPattern.LessThan;
        PhysicalResourceRequirement.AttributeMatchPattern attributeMatchPattern1 = PhysicalResourceRequirement.AttributeMatchPattern.NotLessThan;
        PhysicalResourceRequirement.AttributeMatchPattern attributeMatchPattern2 = PhysicalResourceRequirement.AttributeMatchPattern.Equal;
        PhysicalResourceRequirement.AttributeMatchPattern attributeMatchPattern3 = PhysicalResourceRequirement.AttributeMatchPattern.NotEqual;
        PhysicalResourceRequirement.AttributeMatchPattern attributeMatchPattern4 = PhysicalResourceRequirement.AttributeMatchPattern.GreaterThan;
        PhysicalResourceRequirement.AttributeMatchPattern attributeMatchPattern5 = PhysicalResourceRequirement.AttributeMatchPattern.NotGreaterThan;

        Assert.assertTrue(!(boolean)method.invoke(vnMappingUnit,attributeValue,requiredAttributeValue,attributeMatchPattern));
        Assert.assertTrue((boolean)method.invoke(vnMappingUnit,attributeValue,requiredAttributeValue,attributeMatchPattern1));
        Assert.assertTrue((boolean)method.invoke(vnMappingUnit,attributeValue,requiredAttributeValue,attributeMatchPattern2));
        Assert.assertTrue(!(boolean) method.invoke(vnMappingUnit,attributeValue, requiredAttributeValue, attributeMatchPattern3));
        Assert.assertTrue(!(boolean) method.invoke(vnMappingUnit,attributeValue, requiredAttributeValue, attributeMatchPattern4));
        Assert.assertTrue((boolean)method.invoke(vnMappingUnit,attributeValue,requiredAttributeValue,attributeMatchPattern5));
    }

    @Test
    public void testCheckAttributeMatchPatternSpecified() throws Exception {
        method = class1.getDeclaredMethod("checkAttributeMatchPatternSpecified",new Class[]{
                List.class,
                org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.class
        });
        method.setAccessible(true);

        AttributeMatchPatterns.AttributeMatchPattern matchPattern = AttributeMatchPatterns.AttributeMatchPattern.LessThan;
        List<AttributeMatchPatterns.AttributeMatchPattern> attributeMatchPatterns = new ArrayList<AttributeMatchPatterns.AttributeMatchPattern>();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern
                attributeMatchPattern = org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.LessThan;
        attributeMatchPatterns.add(matchPattern);

        Assert.assertTrue((boolean)method.invoke(vnMappingUnit,attributeMatchPatterns,attributeMatchPattern));

    }

    @Test
    public void testCheckAttributeMatchPatternSpecified1() throws Exception {
        method = class1.getDeclaredMethod("checkAttributeMatchPatternSpecified",new Class[]{
                List.class,
                PhysicalResourceRequirement.AttributeMatchPattern.class
        });
        method.setAccessible(true);

        AttributeMatchPatterns.AttributeMatchPattern matchPattern = AttributeMatchPatterns.AttributeMatchPattern.LessThan;
        List<AttributeMatchPatterns.AttributeMatchPattern> attributeMatchPatterns = new ArrayList<AttributeMatchPatterns.AttributeMatchPattern>();
        PhysicalResourceRequirement.AttributeMatchPattern attributeMatchPattern = PhysicalResourceRequirement.AttributeMatchPattern.LessThan;
        attributeMatchPatterns.add(matchPattern);

        Assert.assertTrue((boolean) method.invoke(vnMappingUnit, attributeMatchPatterns, attributeMatchPattern));

    }

    @Test
    public void testCheckPhysicalNodeAttributeSatisfied() throws Exception{
        method = class1.getDeclaredMethod("checkPhysicalNodeAttributeSatisfied",new Class[]{
                org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.instance.Attribute.class,
                org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.class
        });
        method.setAccessible(true);

        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.instance.Attribute
                attribute = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.instance.Attribute.class);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement
                physicalResourceRequirement = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.class);
        AttributeMatchPatterns attributeMatchPatterns_temp = mock(AttributeMatchPatterns.class);
        AttributeName attributeName = mock(AttributeName.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        PhysicalNodeAttributeDefinition physicalNodeAttributeDefinition = mock(PhysicalNodeAttributeDefinition.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);
        AttributeValue attributeValue = mock(AttributeValue.class);
        AttributeMatchPatterns.AttributeMatchPattern attributeMatchPattern_in_list = AttributeMatchPatterns.AttributeMatchPattern.LessThan;
        Optional<PhysicalNodeAttributeDefinition> result = Optional.of(physicalNodeAttributeDefinition);
        List<AttributeMatchPatterns.AttributeMatchPattern> attributeMatchPatterns = new ArrayList<AttributeMatchPatterns.AttributeMatchPattern>();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern
                attributeMatchPattern = org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.LessThan;

        attributeMatchPatterns.add(attributeMatchPattern_in_list);

        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(checkedFuture);
        when(checkedFuture.get()).thenReturn(result);
        when(physicalNodeAttributeDefinition.getAttributeMatchPatterns()).thenReturn(attributeMatchPatterns_temp);//need to test
        when(attributeMatchPatterns_temp.getAttributeMatchPattern()).thenReturn(attributeMatchPatterns);
        when(physicalResourceRequirement.getAttributeMatchPattern()).thenReturn(attributeMatchPattern);
        when(attribute.getAttributeName()).thenReturn(attributeName);//need to test

        //get into methond"checkAttributeMatchPatternSpecified" args(attributeMatchPatterns,attributeMatchPattern) both less then
        /*  returned true */

        when(physicalNodeAttributeDefinition.getAttributeValueType()).thenReturn(AttributeValueType.Int);
        when(attribute.getAttributeValue()).thenReturn(attributeValue);
        when(attributeValue.getIntValue())
                .thenReturn(1L)
                .thenReturn(2L);
        when(physicalResourceRequirement.getAttributeValue()).thenReturn(attributeValue);

        Assert.assertTrue((boolean) method.invoke(vnMappingUnit,attribute,physicalResourceRequirement));
    }

    @Test
    public void testCheckPhysicalNodeAttributeSatisfied1() throws Exception{
        method = class1.getDeclaredMethod("checkPhysicalPortAttributeSatisfied",new Class[]{
                Attribute.class,
                PhysicalResourceRequirement.class
        });
        method.setAccessible(true);

        Attribute attribute = mock(Attribute.class);
        AttributeMatchPatterns attributeMatchPatterns_temp = mock(AttributeMatchPatterns.class);
        PhysicalResourceRequirement physicalResourceRequirement = mock(PhysicalResourceRequirement.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        AttributeValue attributeValue = mock(AttributeValue.class);
        AttributeName attributeName = mock(AttributeName.class);
        AttributeMatchPatterns.AttributeMatchPattern attributeMatchPattern = AttributeMatchPatterns.AttributeMatchPattern.LessThan;
        PhysicalResourceRequirement.AttributeMatchPattern attributeMatchPattern1 = PhysicalResourceRequirement.AttributeMatchPattern.LessThan;
        CheckedFuture checkedFuture = mock(CheckedFuture.class);
        PhysicalPortAttributeDefinition physicalPortAttributeDefinition = mock(PhysicalPortAttributeDefinition.class);
        Optional<PhysicalPortAttributeDefinition> result = Optional.of(physicalPortAttributeDefinition);
        List<AttributeMatchPatterns.AttributeMatchPattern> attributeMatchPatterns = new ArrayList<AttributeMatchPatterns.AttributeMatchPattern>();

        attributeMatchPatterns.add(attributeMatchPattern);

        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(checkedFuture);
        when(checkedFuture.get()).thenReturn(result);
        when(physicalPortAttributeDefinition.getAttributeMatchPatterns()).thenReturn(attributeMatchPatterns_temp);
        when(attributeMatchPatterns_temp.getAttributeMatchPattern()).thenReturn(attributeMatchPatterns);
        when(physicalResourceRequirement.getAttributeMatchPattern()).thenReturn(attributeMatchPattern1);

        //get into methond"checkAttributeMatchPatternSpecified" args(attributeMatchPatterns,attributeMatchPattern) both less then
        /*  returned true */

        when(attribute.getAttributeName()).thenReturn(attributeName);
        when(physicalPortAttributeDefinition.getAttributeValueType()).thenReturn(AttributeValueType.Int);
        when(attribute.getAttributeValue()).thenReturn(attributeValue);
        when(attributeValue.getIntValue())
                .thenReturn(1L)
                .thenReturn(2L);
        when(physicalResourceRequirement.getAttributeValue()).thenReturn(attributeValue);

        Assert.assertTrue((boolean)method.invoke(vnMappingUnit,attribute,physicalResourceRequirement));
    }

    @Test
    public void testVirtualNodeMapping() throws Exception{
        method = class1.getDeclaredMethod("virtualNodeMapping",new Class[]{
                VirtualNetworkId.class,
                VirtualNode.class,
                List.class
        });
        method.setAccessible(true);

        VirtualNetworkId virtualNetworkId = mock(VirtualNetworkId.class);
        VirtualNode virtualNode = mock(VirtualNode.class);
        PhysicalNode.NodeType NodeType_Switch = PhysicalNode.NodeType.Switch;
        PhysicalNode.NodeType NodeType_Router = PhysicalNode.NodeType.Router;
        PhysicalNode.NodeType NodeType_Firewall = PhysicalNode.NodeType.Firewall;
        PhysicalNode.NodeType NodeType_Loadbalancer = PhysicalNode.NodeType.Loadbalancer;
        NodeType nodeType_Vswitch = NodeType.Vswitch;
        NodeType nodeType_Vrouter = NodeType.Vrouter;
        NodeType nodeType_Vfirewall = NodeType.Vrouter;
        NodeType nodeType_Vloadbalancer = NodeType.Vrouter;

//        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNodeInstance.NodeType
//                nodeType_
        PhysicalNode physicalNode = mock(PhysicalNode.class);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement
                physicalResourceRequirement = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.class);
        List<PhysicalNode> physicalNodes = new ArrayList<PhysicalNode>();
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement>
                physicalResourceRequirements = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement>();

        physicalResourceRequirements.add(physicalResourceRequirement);

        when(virtualNode.getPhysicalResourceRequirement()).thenReturn(physicalResourceRequirements);
        when(virtualNode.getNodeType())
                .thenReturn(nodeType_Vswitch)
                .thenReturn(nodeType_Vswitch)
                .thenReturn(nodeType_Vswitch)
                .thenReturn(nodeType_Vrouter)
                .thenReturn(nodeType_Vrouter)
                .thenReturn(nodeType_Vfirewall)
                .thenReturn(nodeType_Vfirewall)
                .thenReturn(nodeType_Vloadbalancer)
                .thenReturn(nodeType_Vloadbalancer);

        Assert.assertTrue(method.invoke(vnMappingUnit, virtualNetworkId, virtualNode, physicalNodes) == null);

        physicalNodes.add(physicalNode);

        //test nodeType_Vswitch
        when(physicalNode.getNodeType())
                .thenReturn(null)
                .thenReturn(NodeType_Switch)
                .thenReturn(null)
                .thenReturn(NodeType_Router)
                .thenReturn(null)
                .thenReturn(NodeType_Firewall)
                .thenReturn(null)
                .thenReturn(NodeType_Loadbalancer);
        Assert.assertTrue(method.invoke(vnMappingUnit, virtualNetworkId, virtualNode, physicalNodes) == null);
        Assert.assertTrue(method.invoke(vnMappingUnit, virtualNetworkId, virtualNode, physicalNodes) == null);
        //test nodeType_Vrouter
        Assert.assertTrue(method.invoke(vnMappingUnit, virtualNetworkId, virtualNode, physicalNodes) == null);
        Assert.assertTrue(method.invoke(vnMappingUnit, virtualNetworkId, virtualNode, physicalNodes) == null);
        Assert.assertTrue(method.invoke(vnMappingUnit, virtualNetworkId, virtualNode, physicalNodes) == null);
        Assert.assertTrue(method.invoke(vnMappingUnit, virtualNetworkId, virtualNode, physicalNodes) == null);
        Assert.assertTrue(method.invoke(vnMappingUnit, virtualNetworkId, virtualNode, physicalNodes) == null);
        Assert.assertTrue(method.invoke(vnMappingUnit, virtualNetworkId, virtualNode, physicalNodes) == null);
    }

    @Test
    public void testVirtualPortMapping() throws Exception{
        method = class1.getDeclaredMethod("virtualPortMapping", new Class[]{
                VirtualNetworkId.class,
                VirtualNodeId.class,
                VirtualPort.class,
                PhysicalNode.class
        });
        method.setAccessible(true);

        VirtualNetworkId virtualNetworkId = mock(VirtualNetworkId.class);
        VirtualNodeId virtualNodeId = mock(VirtualNodeId.class);
        VirtualPort virtualPort = mock(VirtualPort.class);
        PhysicalNode physicalNode = mock(PhysicalNode.class);
        VirtualPort.PortType portType = VirtualPort.PortType.Internal;
        PhysicalPort physicalPort = mock(PhysicalPort.class);
        List<PhysicalPort> physicalPorts = new ArrayList<PhysicalPort>();
        List<PhysicalResourceRequirement> physicalResourceRequirements = new ArrayList<PhysicalResourceRequirement>();

        physicalPorts.add(physicalPort);

        when(virtualPort.getPortType())
                .thenReturn(null)
                .thenReturn(portType);

        Assert.assertTrue(method.invoke(vnMappingUnit, virtualNetworkId, virtualNodeId, virtualPort, physicalNode) == null);

        //test not null
        when(physicalNode.getPhysicalPort()).thenReturn(physicalPorts);
        when(virtualPort.getPhysicalResourceRequirement()).thenReturn(physicalResourceRequirements);
        when(physicalPort.getPortType())
                .thenReturn(null)
                .thenReturn(PhysicalPort.PortType.External);

        Assert.assertTrue(method.invoke(vnMappingUnit, virtualNetworkId, virtualNodeId, virtualPort, physicalNode) == null);
        Assert.assertTrue(method.invoke(vnMappingUnit,virtualNetworkId,virtualNodeId,virtualPort,physicalNode) == null);

    }

    @Test
    public void testVirtualLinkMapping() throws Exception{
        method = class1.getDeclaredMethod("virtualLinkMapping",new Class[]{
                VirtualNetworkId.class,
                VirtualLink.class,
                UserVnPnMapping.class
        });
        method.setAccessible(true);

        VirtualNetworkId virtualNetworkId = mock(VirtualNetworkId.class);
        VirtualLink virtualLink = mock(VirtualLink.class);
        UserVnPnMapping userVnPnMapping = mock(UserVnPnMapping.class);
        VirtualNodeId virtualNodeId = mock(VirtualNodeId.class);
        VnPnMappingResult vnPnMappingResult = mock(VnPnMappingResult.class);
        PhysicalResourceEntityId physicalResourceEntityId = mock(PhysicalResourceEntityId.class);
        List<VnPnMappingResult> vnPnMappingResults = new ArrayList<VnPnMappingResult>();

        vnPnMappingResults.add(vnPnMappingResult);

        when(userVnPnMapping.getVnPnMappingResult()).thenReturn(vnPnMappingResults);
        when(virtualLink.getSrcNodeId()).thenReturn(virtualNodeId);
        when(virtualLink.getDestNodeId()).thenReturn(virtualNodeId);
        when(virtualNodeId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
        //powermock
//        PowerMockito.mockStatic(VNMappingUnitUtils.class);
//        PowerMockito.when(VNMappingUnitUtils.getVnPnMappingResult(vnPnMappingResults, new VirtualResourceEntityId(new String("00001111-0000-0000-0000-000011112222"))))
//                .thenReturn(vnPnMappingResult);
        // replace powermock
        //get into method "getVnPnMappingResult" args(vnPnMappingResults,new VirtualResourceEntityId(new String("00001111-0000-0000-0000-000011112222")))
        when(vnPnMappingResult.getVirtualResourceEntityId()).thenReturn(new VirtualResourceEntityId(new String("00001111-0000-0000-0000-000011112222")));
        // end
        when(vnPnMappingResult.getPhysicalResourceEntityId()).thenReturn(physicalResourceEntityId);
        when(physicalResourceEntityId.getValue())
                .thenReturn(new String("test"))//source
                .thenReturn(new String("test"))//destination
                .thenReturn(new String("src"))//source2
                .thenReturn(new String("dst"))//destination2
                .thenReturn(new String("src"))//source3
                .thenReturn(new String("dst"));//destination3
        when(virtualLink.getBandwidth())
                .thenReturn(1L)//first trap
                .thenReturn(0L)//second trap
                .thenReturn(1L);//third trap

        Assert.assertTrue((PhysicalPath) method.invoke(vnMappingUnit, virtualNetworkId, virtualLink, userVnPnMapping) != null);
        verify(virtualLink).getBandwidth();

        //test src != dst
        when(pnComputationUnit.computePath(any(PhysicalNodeId.class), any(PhysicalNodeId.class))).thenReturn(null);
        when(pnComputationUnit.computePath(any(PhysicalNodeId.class), any(PhysicalNodeId.class), any(long.class))).thenReturn(null);

        Assert.assertTrue((PhysicalPath) method.invoke(vnMappingUnit, virtualNetworkId, virtualLink, userVnPnMapping) == null);
        Assert.assertTrue((PhysicalPath) method.invoke(vnMappingUnit, virtualNetworkId, virtualLink, userVnPnMapping) == null);
        verify(virtualLink,times(4)).getBandwidth();
    }

    @Test
    public void testCheckPhysicalPortSatisfied() throws Exception{
        method = class1.getDeclaredMethod("checkPhysicalNodeSatisfied",new Class[]{
                PhysicalNode.class,
                List.class,
        });
        method.setAccessible(true);

        PhysicalNode physicalNode = mock(PhysicalNode.class);
        AttributeName attributeName = mock(AttributeName.class);
        AttributeName attributeName1 = mock(AttributeName.class);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement
                physicalResourceRequirement = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.class);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.instance.Attribute
                attribute = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.instance.Attribute.class);
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement>
                physicalResourceRequirements = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement>();
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.instance.Attribute>
                attributes = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.instance.Attribute>();

        attributes.add(attribute);
        physicalResourceRequirements.add(physicalResourceRequirement);
        Assert.assertTrue(attributeName != attributeName1);

        when(physicalNode.getAttribute()).thenReturn(attributes);
        when(physicalResourceRequirement.getAttributeName()).thenReturn(attributeName);
        //get into method "getPhysicalNodeAttribute _long" args(attributes,attributeName)
        when(attribute.getAttributeName())
                .thenReturn(attributeName1) //return null
                .thenReturn(attributeName); //return attribute
        //return to main
        Assert.assertTrue((boolean) method.invoke(vnMappingUnit, physicalNode, physicalResourceRequirements) == false);
        //get in to method "checkPhysicalNodeAttributeSatisfied" args(attribute,physicalResourceRequirement)
        // TODO
    }

    @Test
    public void testCheckPhysicalPortSatisfied1() throws Exception{
        method  = class1.getDeclaredMethod("checkPhysicalPortSatisfied",new Class[]{
                PhysicalPort.class,
                List.class
        });
        method.setAccessible(true);

        PhysicalPort physicalPort = mock(PhysicalPort.class);
        Attribute attribute = mock(Attribute.class);
        AttributeName attributeName = mock(AttributeName.class);
        AttributeName attributeName1 = mock(AttributeName.class);
        PhysicalResourceRequirement physicalResourceRequirement = mock(PhysicalResourceRequirement.class);
        List<Attribute> attributes = new ArrayList<Attribute>();
        List<PhysicalResourceRequirement> physicalResourceRequirements = new ArrayList<PhysicalResourceRequirement>();

        attributes.add(attribute);
        physicalResourceRequirements.add(physicalResourceRequirement);

        when(physicalPort.getAttribute()).thenReturn(attributes);
        when(physicalResourceRequirement.getAttributeName()).thenReturn(attributeName);
        //get into method "getPhysicalNodeAttribute" args(attributes,attributeName)
        when(attribute.getAttributeName()).thenReturn(attributeName1);
        //return to main
        Assert.assertTrue((boolean) method.invoke(vnMappingUnit, physicalPort, physicalResourceRequirements) == false);
        //get in to method "checkPhysicalNodeAttributeSatisfied" args(attribute,physicalResourceRequirement)
        // TODO
    }
}