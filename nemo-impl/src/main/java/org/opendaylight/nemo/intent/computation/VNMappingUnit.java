/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent.computation;

import com.google.common.base.Optional;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.VirtualResourceInstance;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.UserVnPnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.user.vn.pn.mapping.VnPnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.user.vn.pn.mapping.VnPnMappingResultBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.*;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * The virtual network mapping unit implements the following functions:
 * (1) Automatically perform the virtual network mapping when the user's
 *     virtual network changed, which is subscribed from the data store.
 * (2) Automatically perform the remapping for the virtual networks that
 *     are influenced by the changes of the underlying physical network
 *     which are also subscribed from the data store.
 *
 * @author Zhigang Ji
 */
public class VNMappingUnit implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(VNMappingUnit.class);

    private final DataBroker dataBroker;

    /**
     * The physical network computation unit.
     */
    private PNComputationUnit pnComputationUnit;

    /**
     * The physical network resource tracker.
     */
    private PNResourcesTracker pnResourcesTracker;

    public VNMappingUnit(DataBroker dataBroker,
                         PNComputationUnit pnComputationUnit,
                         PNResourcesTracker pnResourcesTracker) {
        super();

        this.dataBroker = dataBroker;
        this.pnComputationUnit = pnComputationUnit;
        this.pnResourcesTracker = pnResourcesTracker;

        LOG.debug("Initialized the virtual network mapping unit.");

        return;
    }

    /**
     * TODO
     *
     * @param virtualNetwork TODO
     * @param userVnPnMapping TODO
     * @param physicalPaths TODO
     */
    public void virtualNetworkMapping(VirtualNetwork virtualNetwork, UserVnPnMapping userVnPnMapping,
                                      List<PhysicalPath> physicalPaths)
            throws VNMappingException {
        ReadOnlyTransaction readOnlyTransaction = dataBroker.newReadOnlyTransaction();

        InstanceIdentifier<PhysicalNodes> physicalNodesIid = InstanceIdentifier
                .builder(PhysicalNetwork.class)
                .child(PhysicalNodes.class)
                .build();
        Optional<PhysicalNodes> result;

        try {
            result = readOnlyTransaction.read(LogicalDatastoreType.OPERATIONAL, physicalNodesIid).get();
        } catch ( InterruptedException exception ) {
            throw new VNMappingException("Can not read the physical nodes.");
        } catch ( ExecutionException exception ) {
            throw new VNMappingException("Can not read the physical nodes.");
        }

        if ( !result.isPresent() ) {
            throw new VNMappingException("Failed virtual network mapping caused by " +
                    "absent underlying network topology.");
        }

        PhysicalNodes physicalNodes = result.get();
        List<PhysicalNode> physicalNodeList = physicalNodes.getPhysicalNode();

        UserId userId = virtualNetwork.getUserId();
        List<VnPnMappingResult> vnPnMappingResults = userVnPnMapping.getVnPnMappingResult();
        List<VirtualNode> virtualNodes = virtualNetwork.getVirtualNodes().getVirtualNode();
        List<VirtualPort> virtualPorts;
        PhysicalNode physicalNode;
        PhysicalPort physicalPort;
        VnPnMappingResult vnPnMappingResult;

        for ( VirtualNode virtualNode : virtualNodes ) {
            physicalNode = virtualNodeMapping(virtualNetwork.getNetworkId(), virtualNode, physicalNodeList);

            if ( null == physicalNode ) {
                // If mapping failed, reset the user physical resources.
                pnResourcesTracker.resetResource(userId);

                throw new VNMappingException("Failed mapping for the virtual node " +
                        virtualNode.getNodeId().getValue() + " in the virtual network " +
                        virtualNetwork.getNetworkId().getValue());
            }

            // Keep physical resource.
            pnResourcesTracker.addPhysicalNode(userId, physicalNode);

            virtualPorts = virtualNode.getVirtualPort();

            for ( VirtualPort virtualPort : virtualPorts ) {
                if ( VirtualPort.PortType.External == virtualPort.getPortType() ) {
                    physicalPort = virtualPortMapping(virtualNetwork.getNetworkId(),
                            virtualNode.getNodeId(), virtualPort, physicalNode);

                    if ( null == physicalPort ) {
                        // If mapping failed, reset the user physical resources.
                        pnResourcesTracker.resetResource(userId);

                        throw new VNMappingException("Failed mapping for the virtual port " +
                                virtualPort.getPortId().getValue() + " of the virtual node " +
                                virtualNode.getNodeId().getValue() + " in the virtual network " +
                                virtualNetwork.getNetworkId().getValue());
                    }

                    // Keep physical resource.
                    pnResourcesTracker.addPhysicalPort(userId, physicalPort);

                    vnPnMappingResult = new VnPnMappingResultBuilder()
                            .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                            .setVirtualResourceType(VirtualResourceInstance.VirtualResourceType.Vport)
                            .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualPort.getPortId().getValue()))
                            .setParentVirtualResourceEntityId(new VirtualResourceEntityId(virtualNode.getNodeId().getValue()))
                            .setPhysicalResourceId(new PhysicalResourceId(UUID.randomUUID().toString()))
                            .setPhysicalResourceType(PhysicalResourceInstance.PhysicalResourceType.Port)
                            .setPhysicalResourceEntityId(new PhysicalResourceEntityId(physicalPort.getPortId().getValue()))
                            .setParentPhysicalResourceEntityId(new PhysicalResourceEntityId(physicalNode.getNodeId().getValue()))
                            .build();

                    vnPnMappingResults.add(vnPnMappingResult);
                }
            }

            vnPnMappingResult = new VnPnMappingResultBuilder()
                    .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                    .setVirtualResourceType(VirtualResourceInstance.VirtualResourceType.Vnode)
                    .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualNode.getNodeId().getValue()))
                    .setPhysicalResourceId(new PhysicalResourceId(UUID.randomUUID().toString()))
                    .setPhysicalResourceType(PhysicalResourceInstance.PhysicalResourceType.Node)
                    .setPhysicalResourceEntityId(new PhysicalResourceEntityId(physicalNode.getNodeId().getValue()))
                    .build();

            vnPnMappingResults.add(vnPnMappingResult);
        }

        List<VirtualLink> virtualLinks = virtualNetwork.getVirtualLinks().getVirtualLink();
        List<VirtualLink> newVirtualLinks = new ArrayList<VirtualLink>(virtualLinks.size());
        PhysicalPath physicalPath;
        VirtualLink newVirtualLink;

        for ( VirtualLink virtualLink : virtualLinks ) {
            physicalPath = virtualLinkMapping(virtualNetwork.getNetworkId(), virtualLink, userVnPnMapping);

            if ( null == physicalPath ) {
                // If mapping failed, reset the user physical resources.
                pnResourcesTracker.resetResource(userId);

                throw new VNMappingException("Failed mapping for the virtual link " +
                        virtualLink.getLinkId().getValue() + " in the virtual network " +
                        virtualNetwork.getNetworkId().getValue());
            }

            // Keep physical resource.
            pnResourcesTracker.addPhysicalPath(userId, physicalPath);

            physicalPaths.add(physicalPath);

            newVirtualLink = new VirtualLinkBuilder(virtualLink)
                    .setMetric(physicalPath.getMetric())
                    .setDelay(physicalPath.getDelay())
                    .build();

            newVirtualLinks.add(newVirtualLink);

            vnPnMappingResult = new VnPnMappingResultBuilder()
                    .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                    .setVirtualResourceType(VirtualResourceInstance.VirtualResourceType.Vlink)
                    .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualLink.getLinkId().getValue()))
                    .setPhysicalResourceId(new PhysicalResourceId(UUID.randomUUID().toString()))
                    .setPhysicalResourceType(PhysicalResourceInstance.PhysicalResourceType.Path)
                    .setPhysicalResourceEntityId(new PhysicalResourceEntityId(physicalPath.getPathId().getValue()))
                    .build();

            vnPnMappingResults.add(vnPnMappingResult);
        }

        virtualLinks.clear();
        virtualLinks.addAll(newVirtualLinks);

        return;
    }

    /**
     * TODO
     *
     * @param virtualNetwork TODO
     * @param unmappedVirtualLinks TODO
     * @param userVnPnMapping TODO
     * @param physicalPaths TODO
     * @throws VNMappingException
     */
    public void virtualNetworkMapping(VirtualNetwork virtualNetwork, List<VirtualLink> unmappedVirtualLinks,
                                      UserVnPnMapping userVnPnMapping, List<PhysicalPath> physicalPaths)
            throws VNMappingException {
        UserId userId = virtualNetwork.getUserId();
        List<VirtualLink> virtualLinks = virtualNetwork.getVirtualLinks().getVirtualLink();
        List<VnPnMappingResult> vnPnMappingResults = userVnPnMapping.getVnPnMappingResult();
        PhysicalPath physicalPath;
        VirtualLink newVirtualLink;
        VnPnMappingResult vnPnMappingResult;
        int i = virtualLinks.size() - unmappedVirtualLinks.size();

        for ( VirtualLink virtualLink : unmappedVirtualLinks ) {
            physicalPath = virtualLinkMapping(virtualNetwork.getNetworkId(), virtualLink, userVnPnMapping);

            if ( null == physicalPath ) {
                // If mapping failed, reset the user physical resources.
                pnResourcesTracker.resetResource(userId);

                throw new VNMappingException("Failed mapping for the virtual link " +
                        virtualLink.getLinkId().getValue() + " in the virtual network " +
                        virtualNetwork.getNetworkId().getValue());
            }

            // Keep physical resource.
            pnResourcesTracker.addPhysicalPath(userId, physicalPath);

            physicalPaths.add(physicalPath);

            newVirtualLink = new VirtualLinkBuilder(virtualLink)
                    .setMetric(physicalPath.getMetric())
                    .setDelay(physicalPath.getDelay())
                    .build();

            virtualLinks.set(i++, newVirtualLink);

            vnPnMappingResult = new VnPnMappingResultBuilder()
                    .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                    .setVirtualResourceType(VirtualResourceInstance.VirtualResourceType.Vlink)
                    .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualLink.getLinkId().getValue()))
                    .setPhysicalResourceId(new PhysicalResourceId(UUID.randomUUID().toString()))
                    .setPhysicalResourceType(PhysicalResourceInstance.PhysicalResourceType.Path)
                    .setPhysicalResourceEntityId(new PhysicalResourceEntityId(physicalPath.getPathId().getValue()))
                    .build();

            vnPnMappingResults.add(vnPnMappingResult);
        }

        return;
    }

    @Override
    public void close() throws Exception {
        return;
    }

    /**
     * TODO
     *
     * @param virtualNetworkId TODO
     * @param virtualNodeId TODO
     * @param virtualPort TODO
     * @param physicalNode TODO
     * @return TODO
     */
    private PhysicalPort virtualPortMapping(VirtualNetworkId virtualNetworkId, VirtualNodeId virtualNodeId,
                                            VirtualPort virtualPort, PhysicalNode physicalNode)
            throws VNMappingException {
        if ( VirtualPort.PortType.Internal == virtualPort.getPortType() ) {
            return null;
        }

        List<PhysicalPort> physicalPorts = physicalNode.getPhysicalPort();
        List<PhysicalResourceRequirement> physicalResourceRequirements = virtualPort.getPhysicalResourceRequirement();

        for ( PhysicalPort physicalPort : physicalPorts ) {
            if ( PhysicalPort.PortType.External == physicalPort.getPortType()
                    && checkPhysicalPortSatisfied(physicalPort, physicalResourceRequirements) ) {
                return physicalPort;
            }
        }

        return null;
    }

    /**
     * TODO
     *
     * @param virtualNetworkId TODO
     * @param virtualNode TODO
     * @param physicalNodes TODO
     * @return TODO
     */
    private PhysicalNode virtualNodeMapping(VirtualNetworkId virtualNetworkId, VirtualNode virtualNode,
                                            List<PhysicalNode> physicalNodes)
            throws VNMappingException {
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement>
                physicalResourceRequirements = virtualNode.getPhysicalResourceRequirement();
        List<PhysicalNode> candidates = new LinkedList<PhysicalNode>();

        switch ( virtualNode.getNodeType() ) {
            case Vswitch:
                for ( PhysicalNode physicalNode : physicalNodes ) {
                    if ( PhysicalNode.NodeType.Switch == physicalNode.getNodeType() ) {
                        if ( checkPhysicalNodeSatisfied(physicalNode, physicalResourceRequirements) ) {
                            candidates.add(physicalNode);
                        }
                    }
                }
                break;

            case Vrouter:
                for ( PhysicalNode physicalNode : physicalNodes ) {
                    if ( PhysicalNode.NodeType.Router == physicalNode.getNodeType() ) {
                        if ( checkPhysicalNodeSatisfied(physicalNode, physicalResourceRequirements) ) {
                            candidates.add(physicalNode);
                        }
                    }
                }
                break;

            case Vfirewall:
                for ( PhysicalNode physicalNode : physicalNodes ) {
                    if ( PhysicalNode.NodeType.Firewall == physicalNode.getNodeType() ) {
                        if ( checkPhysicalNodeSatisfied(physicalNode, physicalResourceRequirements) ) {
                            candidates.add(physicalNode);
                        }
                    }
                }
                break;

            case Vloadbalancer:
                for ( PhysicalNode physicalNode : physicalNodes ) {
                    if ( PhysicalNode.NodeType.Loadbalancer == physicalNode.getNodeType() ) {
                        if ( checkPhysicalNodeSatisfied(physicalNode, physicalResourceRequirements) ) {
                            candidates.add(physicalNode);
                        }
                    }
                }
                break;

            default:
                throw new VNMappingException("Unsupported virtual node type " +
                        virtualNode.getNodeType() + ".");
//                break;
        }

        if ( candidates.isEmpty() ) {
            return null;
        }

        return candidates.get(0);
    }

    /**
     * TODO
     *
     * @param virtualNetworkId TODO
     * @param virtualLink TODO
     * @param userVnPnMapping TODO
     * @return TODO
     */
    private PhysicalPath virtualLinkMapping(VirtualNetworkId virtualNetworkId, VirtualLink virtualLink,
                                            UserVnPnMapping userVnPnMapping)
            throws VNMappingException {
        List<VnPnMappingResult> vnPnMappingResults = userVnPnMapping.getVnPnMappingResult();
        VnPnMappingResult vnPnMappingResult = VNMappingUnitUtils.getVnPnMappingResult(vnPnMappingResults,
                new VirtualResourceEntityId(virtualLink.getSrcNodeId().getValue()));

        if ( null == vnPnMappingResult ) {
            throw new VNMappingException("Can not get the vn-pn mapping result for " +
                    "the virtual node " + virtualLink.getSrcNodeId().getValue());
        }

        PhysicalNodeId source = new PhysicalNodeId(vnPnMappingResult.getPhysicalResourceEntityId().getValue());

        vnPnMappingResult = VNMappingUnitUtils.getVnPnMappingResult(vnPnMappingResults,
                new VirtualResourceEntityId(virtualLink.getDestNodeId().getValue()));

        if ( null == vnPnMappingResult ) {
            throw new VNMappingException("Can not get the vn-pn mapping result for " +
                    "the virtual node " + virtualLink.getDestNodeId().getValue());
        }

        PhysicalNodeId target = new PhysicalNodeId(vnPnMappingResult.getPhysicalResourceEntityId().getValue());

        if ( source.equals(target) ) {
            PhysicalPath physicalPath = new PhysicalPathBuilder()
                    .setPathId(new PhysicalPathId(UUID.randomUUID().toString()))
                    .setPhysicalLink(new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink>(0))
                    .setMetric(0L)
                    .setBandwidth(virtualLink.getBandwidth())
                    .setDelay(0L)
                    .build();

            return physicalPath;
        }

        PhysicalPath physicalPath;

        if ( 0 == virtualLink.getBandwidth() ) {
            physicalPath = pnComputationUnit.computePath(source, target);
        } else {
            physicalPath = pnComputationUnit.computePath(source, target, virtualLink.getBandwidth());
        }

        return physicalPath;
    }

    /**
     * TODO
     *
     * @param physicalPort TODO
     * @param physicalResourceRequirements TODO
     * @return TODO
     */
    private boolean checkPhysicalPortSatisfied(PhysicalPort physicalPort,
                                               List<PhysicalResourceRequirement> physicalResourceRequirements)
            throws VNMappingException {
        List<Attribute> attributes = physicalPort.getAttribute();
        Attribute attribute;

        for ( PhysicalResourceRequirement physicalResourceRequirement : physicalResourceRequirements ) {
            attribute = getPhysicalPortAttribute(attributes, physicalResourceRequirement.getAttributeName());

            if ( null == attribute ) {
                return false;
            }

            if ( !checkPhysicalPortAttributeSatisfied(attribute, physicalResourceRequirement) ) {
                return false;
            }
        }

        return true;
    }

    /**
     * TODO
     *
     * @param physicalNode TODO
     * @param physicalResourceRequirements TODO
     * @return TODO
     */
    private boolean checkPhysicalNodeSatisfied(PhysicalNode physicalNode,
                                               List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement>
                                                       physicalResourceRequirements)
            throws VNMappingException {
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.instance.Attribute>
                attributes = physicalNode.getAttribute();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.instance.Attribute attribute;

        for ( org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement
                physicalResourceRequirement : physicalResourceRequirements ) {
            attribute = getPhysicalNodeAttribute(attributes, physicalResourceRequirement.getAttributeName());

            if ( null == attribute ) {
                return false;
            }

            if ( !checkPhysicalNodeAttributeSatisfied(attribute, physicalResourceRequirement) ) {
                return false;
            }
        }

        return true;
    }

    /**
     * TODO
     *
     * @param attributes TODO
     * @param attributeName TODO
     * @return TODO
     */
    private Attribute getPhysicalPortAttribute(List<Attribute> attributes, AttributeName attributeName) {
        for ( Attribute attribute : attributes ) {
            if ( attribute.getAttributeName().equals(attributeName) ) {
                return attribute;
            }
        }

        return null;
    }

    /**
     * TODO
     *
     * @param attributes TODO
     * @param attributeName TODO
     * @return TODO
     */
    private org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.instance.Attribute getPhysicalNodeAttribute(
            List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.instance.Attribute> attributes,
            AttributeName attributeName) {
        for ( org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.instance.Attribute
                attribute : attributes ) {
            if ( attribute.getAttributeName().equals(attributeName) ) {
                return attribute;
            }
        }

        return null;
    }

    /**
     * TODO
     *
     * @param attribute TODO
     * @param physicalResourceRequirement TODO
     * @return TODO
     */
    private boolean checkPhysicalPortAttributeSatisfied(Attribute attribute,
                                                        PhysicalResourceRequirement physicalResourceRequirement)
            throws VNMappingException {
        ReadOnlyTransaction readOnlyTransaction = dataBroker.newReadOnlyTransaction();

        InstanceIdentifier<PhysicalPortAttributeDefinition> physicalPortAttributeDefinitionIid = InstanceIdentifier
                .builder(PhysicalPortAttributeDefinitions.class)
                .child(PhysicalPortAttributeDefinition.class,
                        new PhysicalPortAttributeDefinitionKey(attribute.getAttributeName()))
                .build();
        Optional<PhysicalPortAttributeDefinition> result;

        try {
            result = readOnlyTransaction.read(LogicalDatastoreType.CONFIGURATION,
                    physicalPortAttributeDefinitionIid).get();
        } catch ( InterruptedException exception ) {
            throw new VNMappingException("Can not read the physical port attribute definition " +
                    "with attribute name " + attribute.getAttributeName().getValue() + ".");
        } catch ( ExecutionException exception ) {
            throw new VNMappingException("Can not read the physical port attribute definition " +
                    "with attribute name " + attribute.getAttributeName().getValue() + ".");
        }

        if ( !result.isPresent() ) {
            throw new VNMappingException("The physical port attribute definition with attribute name " +
                    attribute.getAttributeName().getValue() + " does not exist.");
        }

        PhysicalPortAttributeDefinition physicalPortAttributeDefinition = result.get();
        List<AttributeMatchPatterns.AttributeMatchPattern> attributeMatchPatterns
                = physicalPortAttributeDefinition.getAttributeMatchPatterns().getAttributeMatchPattern();
        PhysicalResourceRequirement.AttributeMatchPattern attributeMatchPattern
                = physicalResourceRequirement.getAttributeMatchPattern();

        if ( !checkAttributeMatchPatternSpecified(attributeMatchPatterns, attributeMatchPattern) ) {
            throw new VNMappingException("The attribute match pattern " + attributeMatchPattern +
                    " is not specified in the physical port attribute definition " +
                    "with attribute name " + attribute.getAttributeName().getValue() + ".");
        }

        switch ( physicalPortAttributeDefinition.getAttributeValueType() ) {
            case String:
                return checkAttributeStringValueSatisfied(attribute.getAttributeValue().getStringValue(),
                        physicalResourceRequirement.getAttributeValue().getStringValue(), attributeMatchPattern);

            case Int:
                return checkAttributeIntegerValueSatisfied(attribute.getAttributeValue().getIntValue(),
                        physicalResourceRequirement.getAttributeValue().getIntValue(), attributeMatchPattern);

            case Range:
                return checkAttributeRangeValueSatisfied(attribute.getAttributeValue().getIntValue(),
                        physicalResourceRequirement.getAttributeValue().getRangeValue(), attributeMatchPattern);

            default:
                throw new VNMappingException("Unsupported physical port attribute value type " +
                        physicalPortAttributeDefinition.getAttributeValueType() + ".");
//                break;
        }

//        return false;
    }

    /**
     * TODO
     *
     * @param attribute TODO
     * @param physicalResourceRequirement TODO
     * @return TODO
     */
    private boolean checkPhysicalNodeAttributeSatisfied(
            org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.instance.Attribute attribute,
            org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement physicalResourceRequirement)
            throws VNMappingException {
        ReadOnlyTransaction readOnlyTransaction = dataBroker.newReadOnlyTransaction();

        InstanceIdentifier<PhysicalNodeAttributeDefinition> physicalNodeAttributeDefinitionIid = InstanceIdentifier
                .builder(PhysicalNodeAttributeDefinitions.class)
                .child(PhysicalNodeAttributeDefinition.class,
                        new PhysicalNodeAttributeDefinitionKey(attribute.getAttributeName()))
                .build();
        Optional<PhysicalNodeAttributeDefinition> result;

        try {
            result = readOnlyTransaction.read(LogicalDatastoreType.CONFIGURATION,
                    physicalNodeAttributeDefinitionIid).get();
        } catch ( InterruptedException exception ) {
            throw new VNMappingException("Can not read the physical node attribute definition " +
                    "with attribute name " + attribute.getAttributeName().getValue() + ".");
        } catch ( ExecutionException exception ) {
            throw new VNMappingException("Can not read the physical node attribute definition " +
                    "with attribute name " + attribute.getAttributeName().getValue() + ".");
        }

        if ( !result.isPresent() ) {
            throw new VNMappingException("The physical node attribute definition with attribute name " +
                    attribute.getAttributeName().getValue() + " does not exist.");
        }

        PhysicalNodeAttributeDefinition physicalNodeAttributeDefinition = result.get();
        List<AttributeMatchPatterns.AttributeMatchPattern> attributeMatchPatterns
                = physicalNodeAttributeDefinition.getAttributeMatchPatterns().getAttributeMatchPattern();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern
                attributeMatchPattern = physicalResourceRequirement.getAttributeMatchPattern();

        if ( !checkAttributeMatchPatternSpecified(attributeMatchPatterns, attributeMatchPattern) ) {
            throw new VNMappingException("The attribute match pattern " + attributeMatchPattern +
                    " is not specified in the physical node attribute definition " +
                    "with attribute name " + attribute.getAttributeName().getValue() + ".");
        }

        switch ( physicalNodeAttributeDefinition.getAttributeValueType() ) {
            case String:
                return checkAttributeStringValueSatisfied(attribute.getAttributeValue().getStringValue(),
                        physicalResourceRequirement.getAttributeValue().getStringValue(), attributeMatchPattern);

            case Int:
                return checkAttributeIntegerValueSatisfied(attribute.getAttributeValue().getIntValue(),
                        physicalResourceRequirement.getAttributeValue().getIntValue(), attributeMatchPattern);

            case Range:
                return checkAttributeRangeValueSatisfied(attribute.getAttributeValue().getIntValue(),
                        physicalResourceRequirement.getAttributeValue().getRangeValue(), attributeMatchPattern);

            default:
                throw new VNMappingException("Unsupported physical node attribute value type " +
                        physicalNodeAttributeDefinition.getAttributeValueType() + ".");
//                break;
        }

//        return false;
    }

    /**
     * TODO
     *
     * @param attributeMatchPatterns TODO
     * @param attributeMatchPattern TODO
     * @return TODO
     */
    private boolean checkAttributeMatchPatternSpecified(List<AttributeMatchPatterns.AttributeMatchPattern> attributeMatchPatterns,
                                                        PhysicalResourceRequirement.AttributeMatchPattern attributeMatchPattern) {
        for ( AttributeMatchPatterns.AttributeMatchPattern attributeMatchPattern1 : attributeMatchPatterns ) {
            if ( attributeMatchPattern1.name().equals(attributeMatchPattern.name()) ) {
                return true;
            }
        }

        return false;
    }

    /**
     * TODO
     *
     * @param attributeMatchPatterns TODO
     * @param attributeMatchPattern TODO
     * @return TODO
     */
    private boolean checkAttributeMatchPatternSpecified(List<AttributeMatchPatterns.AttributeMatchPattern> attributeMatchPatterns,
                                                        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern
                                                                attributeMatchPattern) {
        for ( AttributeMatchPatterns.AttributeMatchPattern attributeMatchPattern1 : attributeMatchPatterns ) {
            if ( attributeMatchPattern1.name().equals(attributeMatchPattern.name()) ) {
                return true;
            }
        }

        return false;
    }

    /**
     * TODO
     *
     * @param attributeValue TODO
     * @param requiredAttributeValue TODO
     * @param attributeMatchPattern TODO
     * @return TODO
     */
    private boolean checkAttributeStringValueSatisfied(String attributeValue, String requiredAttributeValue,
                                                       PhysicalResourceRequirement.AttributeMatchPattern attributeMatchPattern)
            throws VNMappingException {
        int result = attributeValue.compareTo(requiredAttributeValue);

        switch ( attributeMatchPattern ) {
            case LessThan:
                return 0 > result;

            case NotLessThan:
                return 0 <= result;

            case Equal:
                return 0 == result;

            case NotEqual:
                return 0 != result;

            case GreaterThan:
                return 0 < result;

            case NotGreaterThan:
                return 0 >= result;

            default:
                throw new VNMappingException("Unsupported attribute match pattern " +
                        attributeMatchPattern + " for the attribute string value.");
//                break;
        }

//        return false;
    }

    /**
     * TODO
     *
     * @param attributeValue TODO
     * @param requiredAttributeValue TODO
     * @param attributeMatchPattern TODO
     * @return TODO
     */
    private boolean checkAttributeStringValueSatisfied(String attributeValue, String requiredAttributeValue,
                                                       org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern
                                                               attributeMatchPattern)
            throws VNMappingException {
        int result = attributeValue.compareTo(requiredAttributeValue);

        switch ( attributeMatchPattern ) {
            case LessThan:
                return 0 > result;

            case NotLessThan:
                return 0 <= result;

            case Equal:
                return 0 == result;

            case NotEqual:
                return 0 != result;

            case GreaterThan:
                return 0 < result;

            case NotGreaterThan:
                return 0 >= result;

            default:
                throw new VNMappingException("Unsupported attribute match pattern " +
                        attributeMatchPattern + " for the attribute string value.");
//                break;
        }

//        return false;
    }

    /**
     * TODO
     *
     * @param attributeValue TODO
     * @param requiredAttributeValue TODO
     * @param attributeMatchPattern TODO
     * @return TODO
     */
    private boolean checkAttributeIntegerValueSatisfied(Long attributeValue, Long requiredAttributeValue,
                                                        PhysicalResourceRequirement.AttributeMatchPattern attributeMatchPattern)
            throws VNMappingException {
        int result = attributeValue.compareTo(requiredAttributeValue);

        switch ( attributeMatchPattern ) {
            case LessThan:
                return 0 > result;

            case NotLessThan:
                return 0 <= result;

            case Equal:
                return 0 == result;

            case NotEqual:
                return 0 != result;

            case GreaterThan:
                return 0 < result;

            case NotGreaterThan:
                return 0 >= result;

            default:
                throw new VNMappingException("Unsupported attribute match pattern " +
                        attributeMatchPattern + " for the attribute integer value.");
//                break;
        }

//        return false;
    }

    /**
     * TODO
     *
     * @param attributeValue TODO
     * @param requiredAttributeValue TODO
     * @param attributeMatchPattern TODO
     * @return TODO
     */
    private boolean checkAttributeIntegerValueSatisfied(Long attributeValue, Long requiredAttributeValue,
                                                        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern
                                                                attributeMatchPattern)
            throws VNMappingException {
        int result = attributeValue.compareTo(requiredAttributeValue);

        switch ( attributeMatchPattern ) {
            case LessThan:
                return 0 > result;

            case NotLessThan:
                return 0 <= result;

            case Equal:
                return 0 == result;

            case NotEqual:
                return 0 != result;

            case GreaterThan:
                return 0 < result;

            case NotGreaterThan:
                return 0 >= result;

            default:
                throw new VNMappingException("Unsupported attribute match pattern " +
                        attributeMatchPattern + " for the attribute integer value.");
//                break;
        }

//        return false;
    }

    /**
     * TODO
     *
     * @param attributeValue TODO
     * @param requiredAttributeValue TODO
     * @param attributeMatchPattern TODO
     * @return TODO
     */
    private boolean checkAttributeRangeValueSatisfied(Long attributeValue, RangeValue requiredAttributeValue,
                                                      PhysicalResourceRequirement.AttributeMatchPattern attributeMatchPattern)
            throws VNMappingException {
        switch ( attributeMatchPattern ) {
            case Between:
                return attributeValue > requiredAttributeValue.getMin()
                        && attributeValue < requiredAttributeValue.getMax();

            default:
                throw new VNMappingException("Unsupported attribute match pattern " +
                        attributeMatchPattern + " for the attribute range value.");
//                break;
        }

//        return false;
    }

    /**
     * TODO
     *
     * @param attributeValue TODO
     * @param requiredAttributeValue TODO
     * @param attributeMatchPattern TODO
     * @return TODO
     */
    private boolean checkAttributeRangeValueSatisfied(Long attributeValue, RangeValue requiredAttributeValue,
                                                      org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern
                                                              attributeMatchPattern)
            throws VNMappingException {
        switch ( attributeMatchPattern ) {
            case Between:
                return attributeValue > requiredAttributeValue.getMin()
                        && attributeValue < requiredAttributeValue.getMax();

            default:
                throw new VNMappingException("Unsupported attribute match pattern " +
                        attributeMatchPattern + " for the attribute range value.");
//                break;
        }

//        return false;
    }

//    /**
//     * A listener to change events related to virtual ports being
//     * added, removed or updated.
//     *
//     * @author Zhigang Ji
//     */
//    private class VirtualPortChangeListener implements DataChangeListener {
//        /**
//         * The virtual network that the virtual port belongs to.
//         */
//        private VirtualNetworkId virtualNetworkId;
//
//        /**
//         * The virtual node that the virtual port belongs to.
//         */
//        private VirtualNodeId virtualNodeId;
//
//        public VirtualPortChangeListener(VirtualNetworkId virtualNetworkId, VirtualNodeId virtualNodeId) {
//            super();
//
//            this.virtualNetworkId = virtualNetworkId;
//            this.virtualNodeId = virtualNodeId;
//
//            return;
//        }
//
//        @Override
//        public void onDataChanged(AsyncDataChangeEvent<InstanceIdentifier<?>, DataObject> change) {
//            if ( null == change ) {
//                return;
//            }
//
//            Map<InstanceIdentifier<?>, DataObject> createdData = change.getCreatedData();
//
//            if ( null != createdData && !createdData.isEmpty() ) {
//                for ( DataObject dataObject : createdData.values() ) {
//                    if ( dataObject instanceof VirtualPort ) {
//                        // TODO: 1、执行端口映射。
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * A listener to change events related to virtual nodes being
//     * added, removed or updated.
//     *
//     * @author Zhigang Ji
//     */
//    private class VirtualNodeChangeListener implements DataChangeListener {
//        /**
//         * The virtual network that the virtual node belongs to.
//         */
//        private VirtualNetworkId virtualNetworkId;
//
//        public VirtualNodeChangeListener(VirtualNetworkId virtualNetworkId) {
//            super();
//
//            this.virtualNetworkId = virtualNetworkId;
//
//            return;
//        }
//
//        @Override
//        public void onDataChanged(AsyncDataChangeEvent<InstanceIdentifier<?>, DataObject> change) {
//            if ( null == change ) {
//                return;
//            }
//
//            Map<InstanceIdentifier<?>, DataObject> createdData = change.getCreatedData();
//
//            if ( null != createdData && !createdData.isEmpty() ) {
//                for ( DataObject dataObject : createdData.values() ) {
//                    if ( dataObject instanceof VirtualNode ) {
//                        // TODO: 1、执行节点映射。
//                        // TODO: 2、启动vports监听(external端口)，还需监听vnode中vport的增加。
//                        // TODO: 3、读取已有vports，并执行端口映射(external端口)。
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * A listener to change events related to virtual links being
//     * added, removed or updated.
//     *
//     * @author Zhigang Ji
//     */
//    private class VirtualLinkChangeListener implements DataChangeListener {
//        /**
//         * The virtual network that the virtual link belongs to.
//         */
//        private VirtualNetworkId virtualNetworkId;
//
//        public VirtualLinkChangeListener(VirtualNetworkId virtualNetworkId) {
//            super();
//
//            this.virtualNetworkId = virtualNetworkId;
//
//            return;
//        }
//
//        @Override
//        public void onDataChanged(AsyncDataChangeEvent<InstanceIdentifier<?>, DataObject> change) {
//            if ( null == change ) {
//                return;
//            }
//
//            Map<InstanceIdentifier<?>, DataObject> createdData = change.getCreatedData();
//
//            if ( null != createdData && !createdData.isEmpty() ) {
//                for ( DataObject dataObject : createdData.values() ) {
//                    if ( dataObject instanceof VirtualLink ) {
//                        // TODO: 1、执行链路映射(两端vport映射结果可不写)。
//                    }
//                }
//            }
//        }
//    }
}
