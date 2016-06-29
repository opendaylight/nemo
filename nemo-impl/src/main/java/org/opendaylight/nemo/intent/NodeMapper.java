/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent;

import com.google.common.base.Optional;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev130715.IpAddress;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev130715.IpPrefix;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev130715.Ipv4Prefix;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev130715.MacAddress;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.attribute.instance.AttributeValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.attribute.instance.AttributeValueBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalHosts;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.hosts.PhysicalHost;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.arps.VirtualArp;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.arps.VirtualArpBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLinkBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNodeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPathBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.port.instance.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.port.instance.PhysicalResourceRequirement;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.port.instance.PhysicalResourceRequirementBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResultBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResource;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResourceBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.IntentId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.PropertyName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.SubNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Resolve the user's node intent. Create the virtual node according to
 * the intent if it's needed, and then map the created virtual node into
 * the physical network. Finally, store these intent mapping results
 * into the data store.
 *
 * @author Zhigang Ji
 */
public class NodeMapper {
    private static final Logger LOG = LoggerFactory.getLogger(NodeMapper.class);

    private final DataBroker dataBroker;

    public NodeMapper(DataBroker dataBroker) {
        super();

        this.dataBroker = dataBroker;

        LOG.debug("Initialized the renderer common node mapper.");

        return;
    }

    /**
     * Resolve the user's node intent, perform the necessary intent
     * mapping, and store the intent mapping results into the data store.
     *
     * @param user The user for the node.
     * @param node The node to be resolved.
     * @param virtualNetwork The virtual network for the user.
     * @param userIntentVnMapping The intent-vn mapping for the user.
     */
    protected void resolveNode(User user, Node node, VirtualNetwork virtualNetwork,
                               UserIntentVnMapping userIntentVnMapping)
            throws IntentResolutionException {
        NodeType nodeType = node.getNodeType();

        if ( nodeType.equals(new NodeType("host")) ) {
            resolveHost(user, node, virtualNetwork, userIntentVnMapping);

            return;
        }

        if ( nodeType.equals(new NodeType(("l2-group"))) ) {
            resolveLayer2Group(user, node, virtualNetwork, userIntentVnMapping);

            return;
        }

        if ( nodeType.equals(new NodeType("l3-group")) ) {
            resolveLayer3Group(user, node, virtualNetwork, userIntentVnMapping);

            return;
        }

        if ( nodeType.equals(new NodeType("ext-group")) ) {
            if ( IntentResolverUtils.checkExternalLayer3Group(node) ) {
                resolveExternalLayer3Group(user, node, virtualNetwork, userIntentVnMapping);
            }

            return;
        }

        if ( nodeType.equals(new NodeType("chain-group")) ) {
            resolveServiceChainGroup(user, node, virtualNetwork, userIntentVnMapping);

            return;
        }

        if ( nodeType.equals(new NodeType("fw"))
                || nodeType.equals(new NodeType("lb"))
                || nodeType.equals(new NodeType("cache")) ) {
            resolveServiceFunction(user, node, virtualNetwork, userIntentVnMapping);

            return;
        }

        throw new IntentResolutionException("Unknown node type.");

//        return;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param node TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     */
    protected void resolveHost(User user, Node node, VirtualNetwork virtualNetwork,
                               UserIntentVnMapping userIntentVnMapping)
            throws IntentResolutionException {
        ReadOnlyTransaction readOnlyTransaction = dataBroker.newReadOnlyTransaction();

        InstanceIdentifier<PhysicalHosts> physicalHostsIid = InstanceIdentifier
                .builder(PhysicalNetwork.class)
                .child(PhysicalHosts.class)
                .build();
        Optional<PhysicalHosts> result;

        try {
            result = readOnlyTransaction.read(LogicalDatastoreType.OPERATIONAL, physicalHostsIid).get();
        } catch ( InterruptedException exception ) {
            throw new IntentResolutionException("Can not read the physical hosts.");
        } catch ( ExecutionException exception ) {
            throw new IntentResolutionException("Can not read the physical hosts.");
        }

        if ( !result.isPresent() ) {
            throw new IntentResolutionException("The physical hosts do not exist.");
        }

        PhysicalHosts physicalHosts = result.get();
        List<PhysicalHost> physicalHostList = physicalHosts.getPhysicalHost();
        PhysicalHost physicalHost = IntentResolverUtils.getPhysicalHost(physicalHostList, node);

        if ( null == physicalHost ) {
            throw new IntentResolutionException("The physical host corresponding to the node " +
                    node.getNodeId().getValue() + " does not exist.");
        }

        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement> physicalResourceRequirements =
                new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement>(1);
        AttributeValue attributeValue = new AttributeValueBuilder()
                .setStringValue(physicalHost.getNodeId().getValue())
                .build();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement physicalResourceRequirement =
                new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirementBuilder()
                        .setAttributeName(new AttributeName("location"))
                        .setAttributeValue(attributeValue)
                        .setAttributeMatchPattern(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.Equal)
                        .build();
        physicalResourceRequirements.add(physicalResourceRequirement);

        VirtualNode virtualNode = new VirtualNodeBuilder()
                .setNodeId(new VirtualNodeId(UUID.randomUUID().toString()))
                .setNodeType(VirtualNode.NodeType.Vswitch)
                .setVirtualPort(new LinkedList<VirtualPort>())
                .setPhysicalResourceRequirement(physicalResourceRequirements)
                .build();

        virtualNetwork.getVirtualNodes().getVirtualNode().add(virtualNode);

        List<MacAddress> macAddresses = new ArrayList<MacAddress>(1);
        macAddresses.add(physicalHost.getMacAddress());
        ExternalMacAddresses externalMacAddresses = new ExternalMacAddressesBuilder()
                .setExternalMacAddress(macAddresses)
                .build();

        List<PhysicalResourceRequirement> physicalResourceRequirements1 =
                new ArrayList<PhysicalResourceRequirement>(1);
        AttributeValue attributeValue1 = new AttributeValueBuilder()
                .setStringValue(physicalHost.getPortId().getValue())
                .build();
        PhysicalResourceRequirement physicalResourceRequirement1 = new PhysicalResourceRequirementBuilder()
                .setAttributeName(new AttributeName("location"))
                .setAttributeValue(attributeValue1)
                .setAttributeMatchPattern(PhysicalResourceRequirement.AttributeMatchPattern.Equal)
                .build();
        physicalResourceRequirements1.add(physicalResourceRequirement1);

        VirtualPort virtualPort = new VirtualPortBuilder()
                .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                .setPortType(VirtualPort.PortType.External)
                .setExternalMacAddresses(externalMacAddresses)
                .setPhysicalResourceRequirement(physicalResourceRequirements1)
                .build();

        virtualNode.getVirtualPort().add(virtualPort);

        List<VirtualArp> virtualArps = virtualNetwork.getVirtualArps().getVirtualArp();
        VirtualArp virtualArp;

        for ( IpAddress ipAddress : physicalHost.getIpAddresses().getIpAddress() ) {
            virtualArp = new VirtualArpBuilder()
                    .setIpAddress(ipAddress)
                    .setMacAddress(physicalHost.getMacAddress())
                    .setNodeId(virtualNode.getNodeId())
                    .setPortId(virtualPort.getPortId())
                    .build();
            virtualArps.add(virtualArp);
        }

        List<VirtualResource> virtualResources = new ArrayList<VirtualResource>(1);
        VirtualResource virtualResource = new VirtualResourceBuilder()
                .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                .setVirtualResourceType(VirtualResource.VirtualResourceType.Vport)
                .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualPort.getPortId().getValue()))
                .setParentVirtualResourceEntityId(new VirtualResourceEntityId(virtualNode.getNodeId().getValue()))
                .setOrder(0L)
                .build();
        virtualResources.add(virtualResource);

        IntentVnMappingResult intentVnMappingResult = new IntentVnMappingResultBuilder()
                .setIntentId(new IntentId(node.getNodeId().getValue()))
                .setIntentType(IntentVnMappingResult.IntentType.Node)
                .setVirtualResource(virtualResources)
                .build();

        userIntentVnMapping.getIntentVnMappingResult().add(intentVnMappingResult);

        return;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param node TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     */
    protected void resolveLayer2Group(User user, Node node, VirtualNetwork virtualNetwork,
                                      UserIntentVnMapping userIntentVnMapping)
            throws IntentResolutionException {
        List<Node> nodes = user.getObjects().getNode();
        List<SubNode> subNodes = node.getSubNode();
        Node node1;
        NodeType nodeType = new NodeType("ext-group");
        List<VirtualNode> virtualSwitches = new ArrayList<VirtualNode>(subNodes.size());
        List<IntentVnMappingResult> intentVnMappingResults = userIntentVnMapping.getIntentVnMappingResult();
        List<VirtualNode> virtualNodes = virtualNetwork.getVirtualNodes().getVirtualNode();
        IntentVnMappingResult intentVnMappingResult;
        VirtualNode virtualNode;

        if ( null != subNodes ) {
            for ( SubNode subNode : subNodes ) {
                node1 = IntentResolverUtils.getNode(nodes, subNode.getNodeId());

                if ( null == node1 ) {
                    throw new IntentResolutionException("The sub-node " + subNode.getNodeId().getValue() +
                            " of the node " + node.getNodeId().getValue() + " does not exist.");
                }

                if ( node1.getNodeType().equals(nodeType) ) {
                    if ( IntentResolverUtils.checkExternalLayer3Group(node1) ) {
                        throw new IntentResolutionException("The sub-node " + subNode.getNodeId().getValue() +
                                " of the node " + node.getNodeId().getValue() + " can't be layer3 group.");
                    }

                    resolveExternalLayer2Group(user, node1, virtualNetwork, userIntentVnMapping, false);
                }

                intentVnMappingResult = IntentResolverUtils.getIntentVnMappingResult(intentVnMappingResults,
                        new IntentId(node1.getNodeId().getValue()));

                if ( null == intentVnMappingResult ) {
                    throw new IntentResolutionException("Can not get the intent-vn mapping result for " +
                            "the node " + node1.getNodeId().getValue() + ".");
                }

                virtualNode = IntentResolverUtils.getVirtualNode(virtualNodes,
                        new VirtualNodeId(intentVnMappingResult.getVirtualResource().get(0)
                                .getParentVirtualResourceEntityId().getValue()));

                if ( null == virtualNode ) {
                    throw new IntentResolutionException("Can not get the virtual node created for " +
                            "the node " + node1.getNodeId().getValue() + ".");
                }

                virtualSwitches.add(virtualNode);
            }
        }

        Property locationProperty = IntentResolverUtils
                .getNodeProperty(node.getProperty(), new PropertyName("location"));
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement> physicalResourceRequirements;

        if ( null == locationProperty ) {
            physicalResourceRequirements =
                    new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement>(0);
        } else {
            physicalResourceRequirements =
                    new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement>(1);
            AttributeValue attributeValue = new AttributeValueBuilder()
                    .setStringValue(locationProperty.getPropertyValues().getStringValue().get(0).getValue())
                    .build();
            org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement physicalResourceRequirement =
                    new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirementBuilder()
                            .setAttributeName(new AttributeName("location"))
                            .setAttributeValue(attributeValue)
                            .setAttributeMatchPattern(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.Equal)
                            .build();
            physicalResourceRequirements.add(physicalResourceRequirement);
        }

        VirtualNode virtualRouter = new VirtualNodeBuilder()
                .setNodeId(new VirtualNodeId(UUID.randomUUID().toString()))
                .setNodeType(VirtualNode.NodeType.Vrouter)
                .setVirtualPort(new LinkedList<VirtualPort>())
                .setPhysicalResourceRequirement(physicalResourceRequirements)
                .build();

        virtualNodes.add(virtualRouter);

        ExternalIpPrefixes externalIpPrefixes;
        Property ipPrefixProperty = IntentResolverUtils
                .getNodeProperty(node.getProperty(), new PropertyName("ip-prefix"));

        if ( null == ipPrefixProperty ) {
            externalIpPrefixes = new ExternalIpPrefixesBuilder()
                    .setExternalIpPrefix(new ArrayList<IpPrefix>(0))
                    .build();
        } else {
            List<IpPrefix> ipPrefixes = new ArrayList<IpPrefix>(1);
            IpPrefix ipPrefix = new IpPrefix(
                    new Ipv4Prefix(ipPrefixProperty.getPropertyValues().getStringValue().get(0).getValue()));
            ipPrefixes.add(ipPrefix);

            externalIpPrefixes = new ExternalIpPrefixesBuilder()
                    .setExternalIpPrefix(ipPrefixes)
                    .build();
        }

        List<VirtualLink> virtualLinks = virtualNetwork.getVirtualLinks().getVirtualLink();
        Set<VirtualNodeId> handledVirtualSwitches = new HashSet<VirtualNodeId>();
        VirtualPort virtualPort;
        VirtualPort virtualPort1;
        VirtualLink virtualLink;

        for ( VirtualNode virtualSwitch : virtualSwitches ) {
            virtualPort = new VirtualPortBuilder()
                    .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                    .setPortType(VirtualPort.PortType.Internal)
                    .setExternalIpPrefixes(externalIpPrefixes)
                    .build();
            virtualRouter.getVirtualPort().add(virtualPort);

            virtualPort1 = new VirtualPortBuilder()
                    .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                    .setPortType(VirtualPort.PortType.Internal)
                    .build();
            virtualSwitch.getVirtualPort().add(virtualPort1);

            virtualLink = new VirtualLinkBuilder()
                    .setLinkId(new VirtualLinkId(UUID.randomUUID().toString()))
                    .setSrcNodeId(virtualRouter.getNodeId())
                    .setSrcPortId(virtualPort.getPortId())
                    .setDestNodeId(virtualSwitch.getNodeId())
                    .setDestPortId(virtualPort1.getPortId())
                    .setBandwidth(0L)
                    .build();
            virtualLinks.add(virtualLink);

            virtualLink = new VirtualLinkBuilder()
                    .setLinkId(new VirtualLinkId(UUID.randomUUID().toString()))
                    .setSrcNodeId(virtualSwitch.getNodeId())
                    .setSrcPortId(virtualPort1.getPortId())
                    .setDestNodeId(virtualRouter.getNodeId())
                    .setDestPortId(virtualPort.getPortId())
                    .setBandwidth(0L)
                    .build();
            virtualLinks.add(virtualLink);

            for ( VirtualNode virtualSwitch1 : virtualSwitches ) {
                if ( virtualSwitch1.getNodeId().equals(virtualSwitch.getNodeId()) ) {
                    continue;
                }

                if ( handledVirtualSwitches.contains(virtualSwitch1.getNodeId()) ) {
                    continue;
                }

                virtualPort = new VirtualPortBuilder()
                        .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                        .setPortType(VirtualPort.PortType.Internal)
                        .build();
                virtualSwitch.getVirtualPort().add(virtualPort);

                virtualPort1 = new VirtualPortBuilder()
                        .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                        .setPortType(VirtualPort.PortType.Internal)
                        .build();
                virtualSwitch1.getVirtualPort().add(virtualPort1);

                virtualLink = new VirtualLinkBuilder()
                        .setLinkId(new VirtualLinkId(UUID.randomUUID().toString()))
                        .setSrcNodeId(virtualSwitch.getNodeId())
                        .setSrcPortId(virtualPort.getPortId())
                        .setDestNodeId(virtualSwitch1.getNodeId())
                        .setDestPortId(virtualPort1.getPortId())
                        .setBandwidth(0L)
                        .build();
                virtualLinks.add(virtualLink);

                virtualLink = new VirtualLinkBuilder()
                        .setLinkId(new VirtualLinkId(UUID.randomUUID().toString()))
                        .setSrcNodeId(virtualSwitch1.getNodeId())
                        .setSrcPortId(virtualPort1.getPortId())
                        .setDestNodeId(virtualSwitch.getNodeId())
                        .setDestPortId(virtualPort.getPortId())
                        .setBandwidth(0L)
                        .build();
                virtualLinks.add(virtualLink);
            }

            handledVirtualSwitches.add(virtualSwitch.getNodeId());
        }

        List<VirtualResource> virtualResources = new ArrayList<VirtualResource>(1);
        VirtualResource virtualResource = new VirtualResourceBuilder()
                .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                .setVirtualResourceType(VirtualResource.VirtualResourceType.Vnode)
                .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualRouter.getNodeId().getValue()))
                .setOrder(0L)
                .build();
        virtualResources.add(virtualResource);

        intentVnMappingResult = new IntentVnMappingResultBuilder()
                .setIntentId(new IntentId(node.getNodeId().getValue()))
                .setIntentType(IntentVnMappingResult.IntentType.Node)
                .setVirtualResource(virtualResources)
                .build();

        intentVnMappingResults.add(intentVnMappingResult);

        return;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param node TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     */
    protected void resolveLayer3Group(User user, Node node, VirtualNetwork virtualNetwork,
                                      UserIntentVnMapping userIntentVnMapping)
            throws IntentResolutionException {
        // TODO: 1、l3-group的sub-node可能是：l2-group、ext-group(l2)、ext-group(l3)。
        // TODO:    将各个sub-node对应的gateway/access vrouter用vlinks连接（vrouter的vport）。
        // TODO:    特别注意：需为ext-group(l2)创建gateway vrouter。
        // TODO: 2、在data store中，创建vrouter、vports、vlinks。
        // TODO: 3、将intent - vn mapping result写入data store。
        // TODO: 4、将新vrouter、vlinks更新入user对应的算法实例。
        // TODO:    通过算法实例，重新计算全网路由。
        // TODO: 5、在data store中，新增 or 更新vroute。

        return;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param node TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     * @param generatingVirtualRouter TODO
     */
    protected void resolveExternalLayer2Group(User user, Node node, VirtualNetwork virtualNetwork,
                                              UserIntentVnMapping userIntentVnMapping,
                                              boolean generatingVirtualRouter)
            throws IntentResolutionException {
        Property property = IntentResolverUtils
                .getNodeProperty(node.getProperty(), new PropertyName("location"));

        if ( null == property ) {
            throw new IntentResolutionException("Can not get the location property of the node " +
                    node.getNodeId().getValue() + ".");
        }

        PhysicalNodeId physicalNodeId = IntentResolverUtils
                .generatePhysicalNodeIdFromNodeLocationProperty(property);

        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement> physicalResourceRequirements =
                new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement>(1);
        AttributeValue attributeValue = new AttributeValueBuilder()
                .setStringValue(physicalNodeId.getValue())
                .build();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement physicalResourceRequirement =
                new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirementBuilder()
                        .setAttributeName(new AttributeName("location"))
                        .setAttributeValue(attributeValue)
                        .setAttributeMatchPattern(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.Equal)
                        .build();
        physicalResourceRequirements.add(physicalResourceRequirement);

        VirtualNode virtualNode = new VirtualNodeBuilder()
                .setNodeId(new VirtualNodeId(UUID.randomUUID().toString()))
                .setNodeType(generatingVirtualRouter ? VirtualNode.NodeType.Vrouter : VirtualNode.NodeType.Vswitch)
                .setVirtualPort(new LinkedList<VirtualPort>())
                .setPhysicalResourceRequirement(physicalResourceRequirements)
                .build();

        virtualNetwork.getVirtualNodes().getVirtualNode().add(virtualNode);

        ExternalMacAddresses externalMacAddresses = new ExternalMacAddressesBuilder()
                .setExternalMacAddress(new ArrayList<MacAddress>(0))
                .build();

        ExternalIpPrefixes externalIpPrefixes = null;

        if ( generatingVirtualRouter ) {
            Property property1 = IntentResolverUtils
                    .getNodeProperty(node.getProperty(), new PropertyName("ip-prefix"));

            if ( null != property1 ) {
                List<IpPrefix> ipPrefixes = new ArrayList<IpPrefix>(1);
                IpPrefix ipPrefix = new IpPrefix(
                        new Ipv4Prefix(property1.getPropertyValues().getStringValue().get(0).getValue()));
                ipPrefixes.add(ipPrefix);

                externalIpPrefixes = new ExternalIpPrefixesBuilder()
                        .setExternalIpPrefix(ipPrefixes)
                        .build();
            } else {
                externalIpPrefixes = new ExternalIpPrefixesBuilder()
                        .setExternalIpPrefix(new ArrayList<IpPrefix>(0))
                        .build();
            }
        }

        List<PhysicalResourceRequirement> physicalResourceRequirements1 =
                new ArrayList<PhysicalResourceRequirement>(1);
        AttributeValue attributeValue1 = new AttributeValueBuilder()
                .setStringValue(property.getPropertyValues().getStringValue().get(0).getValue())
                .build();
        PhysicalResourceRequirement physicalResourceRequirement1 = new PhysicalResourceRequirementBuilder()
                .setAttributeName(new AttributeName("location"))
                .setAttributeValue(attributeValue1)
                .setAttributeMatchPattern(PhysicalResourceRequirement.AttributeMatchPattern.Equal)
                .build();
        physicalResourceRequirements1.add(physicalResourceRequirement1);

        VirtualPort virtualPort = new VirtualPortBuilder()
                .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                .setPortType(VirtualPort.PortType.External)
                .setExternalMacAddresses(externalMacAddresses)
                .setExternalIpPrefixes(externalIpPrefixes)
                .setPhysicalResourceRequirement(physicalResourceRequirements1)
                .build();

        virtualNode.getVirtualPort().add(virtualPort);

        List<VirtualResource> virtualResources = new ArrayList<VirtualResource>(1);
        VirtualResource virtualResource = new VirtualResourceBuilder()
                .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                .setVirtualResourceType(VirtualResource.VirtualResourceType.Vport)
                .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualPort.getPortId().getValue()))
                .setParentVirtualResourceEntityId(new VirtualResourceEntityId(virtualNode.getNodeId().getValue()))
                .setOrder(0L)
                .build();
        virtualResources.add(virtualResource);

        IntentVnMappingResult intentVnMappingResult = new IntentVnMappingResultBuilder()
                .setIntentId(new IntentId(node.getNodeId().getValue()))
                .setIntentType(IntentVnMappingResult.IntentType.Node)
                .setVirtualResource(virtualResources)
                .build();

        userIntentVnMapping.getIntentVnMappingResult().add(intentVnMappingResult);

        return;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param node TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     */
    protected void resolveExternalLayer3Group(User user, Node node, VirtualNetwork virtualNetwork,
                                              UserIntentVnMapping userIntentVnMapping)
            throws IntentResolutionException {
        Property property = IntentResolverUtils
                .getNodeProperty(node.getProperty(), new PropertyName("location"));

        if ( null == property ) {
            throw new IntentResolutionException("Can not get the location property of the node " +
                    node.getNodeId().getValue() + ".");
        }

        PhysicalNodeId physicalNodeId = IntentResolverUtils
                .generatePhysicalNodeIdFromNodeLocationProperty(property);

        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement> physicalResourceRequirements =
                new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement>(1);
        AttributeValue attributeValue = new AttributeValueBuilder()
                .setStringValue(physicalNodeId.getValue())
                .build();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement physicalResourceRequirement =
                new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirementBuilder()
                        .setAttributeName(new AttributeName("location"))
                        .setAttributeValue(attributeValue)
                        .setAttributeMatchPattern(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.Equal)
                        .build();
        physicalResourceRequirements.add(physicalResourceRequirement);

        VirtualNode virtualNode = new VirtualNodeBuilder()
                .setNodeId(new VirtualNodeId(UUID.randomUUID().toString()))
                .setNodeType(VirtualNode.NodeType.Vrouter)
                .setVirtualPort(new LinkedList<VirtualPort>())
                .setPhysicalResourceRequirement(physicalResourceRequirements)
                .build();

        virtualNetwork.getVirtualNodes().getVirtualNode().add(virtualNode);

        ExternalIpPrefixes externalIpPrefixes;
        Property property1 = IntentResolverUtils
                .getNodeProperty(node.getProperty(), new PropertyName("ip-prefix"));

        if ( null != property1 ) {
            List<IpPrefix> ipPrefixes =
                    new ArrayList<IpPrefix>(property1.getPropertyValues().getStringValue().size());
            IpPrefix ipPrefix;

            for ( StringValue stringValue : property1.getPropertyValues().getStringValue() ) {
                ipPrefix = new IpPrefix(new Ipv4Prefix(stringValue.getValue()));
                ipPrefixes.add(ipPrefix);
            }

            externalIpPrefixes = new ExternalIpPrefixesBuilder()
                    .setExternalIpPrefix(ipPrefixes)
                    .build();
        } else {
            externalIpPrefixes = new ExternalIpPrefixesBuilder()
                    .setExternalIpPrefix(new ArrayList<IpPrefix>(0))
                    .build();
        }

        List<PhysicalResourceRequirement> physicalResourceRequirements1 =
                new ArrayList<PhysicalResourceRequirement>(1);
        AttributeValue attributeValue1 = new AttributeValueBuilder()
                .setStringValue(property.getPropertyValues().getStringValue().get(0).getValue())
                .build();
        PhysicalResourceRequirement physicalResourceRequirement1 = new PhysicalResourceRequirementBuilder()
                .setAttributeName(new AttributeName("location"))
                .setAttributeValue(attributeValue1)
                .setAttributeMatchPattern(PhysicalResourceRequirement.AttributeMatchPattern.Equal)
                .build();
        physicalResourceRequirements1.add(physicalResourceRequirement1);

        VirtualPort virtualPort = new VirtualPortBuilder()
                .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                .setPortType(VirtualPort.PortType.External)
                .setExternalIpPrefixes(externalIpPrefixes)
                .setPhysicalResourceRequirement(physicalResourceRequirements1)
                .build();

        virtualNode.getVirtualPort().add(virtualPort);

        List<VirtualResource> virtualResources = new ArrayList<VirtualResource>(1);
        VirtualResource virtualResource = new VirtualResourceBuilder()
                .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                .setVirtualResourceType(VirtualResource.VirtualResourceType.Vport)
                .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualPort.getPortId().getValue()))
                .setParentVirtualResourceEntityId(new VirtualResourceEntityId(virtualNode.getNodeId().getValue()))
                .setOrder(0L)
                .build();
        virtualResources.add(virtualResource);

        IntentVnMappingResult intentVnMappingResult = new IntentVnMappingResultBuilder()
                .setIntentId(new IntentId(node.getNodeId().getValue()))
                .setIntentType(IntentVnMappingResult.IntentType.Node)
                .setVirtualResource(virtualResources)
                .build();

        userIntentVnMapping.getIntentVnMappingResult().add(intentVnMappingResult);

        return;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param node TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     */
    protected void resolveServiceChainGroup(User user, Node node, VirtualNetwork virtualNetwork,
                                            UserIntentVnMapping userIntentVnMapping)
            throws IntentResolutionException {
        List<SubNode> subNodes = node.getSubNode();

        if ( !subNodes.isEmpty() ) {
            List<IntentVnMappingResult> intentVnMappingResults = userIntentVnMapping.getIntentVnMappingResult();
            List<VirtualResource> virtualResources = new LinkedList<VirtualResource>();

            if ( 1 == subNodes.size() ) {
                SubNode subNode = subNodes.get(0);
                IntentVnMappingResult intentVnMappingResult = IntentResolverUtils
                        .getIntentVnMappingResult(intentVnMappingResults, new IntentId(subNode.getNodeId().getValue()));

                if ( null == intentVnMappingResult ) {
                    throw new IntentResolutionException("Can not get the intent-vn mapping result for " +
                            "the node " + subNode.getNodeId().getValue() + ".");
                }

                VirtualResource virtualResource = intentVnMappingResult.getVirtualResource().get(0);
                virtualResource = new VirtualResourceBuilder(virtualResource)
                        .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                        .setOrder(0L)
                        .build();
                virtualResources.add(virtualResource);
            } else {
                subNodes = IntentResolverUtils.sortSubNodes(subNodes);
                List<Node> nodes = user.getObjects().getNode();
                Iterator<SubNode> iterator = subNodes.iterator();
                SubNode subNode1 = iterator.next();
                SubNode subNode2;
                IntentVnMappingResult intentVnMappingResult;
                VirtualResource virtualResource;

                if ( IntentResolverUtils.checkAllLayer2OperatingMode(subNodes, nodes) ) {
                    // TODO
                } else if ( IntentResolverUtils.checkAllLayer3OperatingMode(subNodes, nodes) ) {
                    List<VirtualNode> virtualNodes = virtualNetwork.getVirtualNodes().getVirtualNode();
                    List<VirtualLink> virtualLinks = virtualNetwork.getVirtualLinks().getVirtualLink();
                    List<VirtualPath> virtualPaths = virtualNetwork.getVirtualPaths().getVirtualPath();
                    VirtualNodeId virtualNodeId1;
                    VirtualNodeId virtualNodeId2;
                    VirtualNode virtualNode1 = null;
                    VirtualNode virtualNode2 = null;
                    VirtualPort virtualPort1;
                    VirtualPort virtualPort2;
                    VirtualLink virtualLink;
                    List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLink> virtualLinks1;
                    org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLink virtualLink1;
                    VirtualPath virtualPath;

                    intentVnMappingResult = IntentResolverUtils.getIntentVnMappingResult(intentVnMappingResults,
                            new IntentId(subNode1.getNodeId().getValue()));

                    if ( null == intentVnMappingResult ) {
                        throw new IntentResolutionException("Can not get the intent-vn mapping result for " +
                                "the node " + subNode1.getNodeId().getValue() + ".");
                    }

                    virtualResource = intentVnMappingResult.getVirtualResource().get(0);
                    virtualResource = new VirtualResourceBuilder(virtualResource)
                            .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                            .setOrder(0L)
                            .build();
                    virtualResources.add(virtualResource);

                    virtualNodeId1 = new VirtualNodeId(intentVnMappingResult.getVirtualResource().get(0)
                            .getParentVirtualResourceEntityId().getValue());

                    while ( iterator.hasNext() ) {
                        subNode2 = iterator.next();
                        intentVnMappingResult = IntentResolverUtils.getIntentVnMappingResult(intentVnMappingResults,
                                new IntentId(subNode2.getNodeId().getValue()));

                        if ( null == intentVnMappingResult ) {
                            throw new IntentResolutionException("Can not get the intent-vn mapping result " +
                                    "for the node " + subNode2.getNodeId().getValue() + ".");
                        }

                        virtualNodeId2 = new VirtualNodeId(intentVnMappingResult.getVirtualResource().get(0)
                                .getParentVirtualResourceEntityId().getValue());
                        virtualLink = IntentResolverUtils.getVirtualLink(virtualLinks, virtualNodeId1, virtualNodeId2);

                        if ( null == virtualLink ) {
                            if ( null == virtualNode1 ) {
                                virtualNode1 = IntentResolverUtils.getVirtualNode(virtualNodes, virtualNodeId1);

                                if ( null == virtualNode1 ) {
                                    throw new IntentResolutionException("Can not get the virtual node " +
                                            "created for the node " + subNode1.getNodeId().getValue() + ".");
                                }
                            }

                            virtualNode2 = IntentResolverUtils.getVirtualNode(virtualNodes, virtualNodeId2);

                            if ( null == virtualNode2 ) {
                                throw new IntentResolutionException("Can not get the virtual node created " +
                                        "for the node " + subNode2.getNodeId().getValue() + ".");
                            }

                            virtualPort1 = new VirtualPortBuilder()
                                    .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                                    .setPortType(VirtualPort.PortType.Internal)
                                    .build();
                            virtualNode1.getVirtualPort().add(virtualPort1);

                            virtualPort2 = new VirtualPortBuilder()
                                    .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                                    .setPortType(VirtualPort.PortType.Internal)
                                    .build();
                            virtualNode2.getVirtualPort().add(virtualPort2);

                            virtualLink = new VirtualLinkBuilder()
                                    .setLinkId(new VirtualLinkId(UUID.randomUUID().toString()))
                                    .setSrcNodeId(virtualNodeId2)
                                    .setSrcPortId(virtualPort2.getPortId())
                                    .setDestNodeId(virtualNodeId1)
                                    .setDestPortId(virtualPort1.getPortId())
                                    .setBandwidth(0L)
                                    .build();
                            virtualLinks.add(virtualLink);

                            virtualLink = new VirtualLinkBuilder()
                                    .setLinkId(new VirtualLinkId(UUID.randomUUID().toString()))
                                    .setSrcNodeId(virtualNodeId1)
                                    .setSrcPortId(virtualPort1.getPortId())
                                    .setDestNodeId(virtualNodeId2)
                                    .setDestPortId(virtualPort2.getPortId())
                                    .setBandwidth(0L)
                                    .build();
                            virtualLinks.add(virtualLink);
                        }

                        virtualLinks1 = new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLink>(1);
                        virtualLink1 = new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.path.instance.VirtualLinkBuilder()
                                .setLinkId(virtualLink.getLinkId())
                                .setOrder(0L)
                                .build();
                        virtualLinks1.add(virtualLink1);

                        virtualPath = new VirtualPathBuilder()
                                .setPathId(new VirtualPathId(UUID.randomUUID().toString()))
                                .setVirtualLink(virtualLinks1)
                                .setBandwidth(0L)
                                .build();
                        virtualPaths.add(virtualPath);

                        virtualResource = new VirtualResourceBuilder()
                                .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                                .setVirtualResourceType(VirtualResource.VirtualResourceType.Vpath)
                                .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualPath.getPathId().getValue()))
                                .setOrder((long) virtualResources.size())
                                .build();
                        virtualResources.add(virtualResource);

                        virtualResource = intentVnMappingResult.getVirtualResource().get(0);
                        virtualResource = new VirtualResourceBuilder(virtualResource)
                                .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                                .setOrder((long) virtualResources.size())
                                .build();
                        virtualResources.add(virtualResource);

                        subNode1 = subNode2;
                        virtualNodeId1 = virtualNodeId2;
                        virtualNode1 = virtualNode2;
                        virtualNode2 = null;
                    }
                } else {
                    // TODO
                }
            }

            IntentVnMappingResult intentVnMappingResult = new IntentVnMappingResultBuilder()
                    .setIntentId(new IntentId(node.getNodeId().getValue()))
                    .setIntentType(IntentVnMappingResult.IntentType.Node)
                    .setVirtualResource(virtualResources)
                    .build();

            intentVnMappingResults.add(intentVnMappingResult);
        }

        return;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param node TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     */
    protected void resolveServiceFunction(User user, Node node, VirtualNetwork virtualNetwork,
                                          UserIntentVnMapping userIntentVnMapping)
            throws IntentResolutionException {
        Property locationProperty = IntentResolverUtils
                .getNodeProperty(node.getProperty(), new PropertyName("location"));

        if ( null == locationProperty ) {
            throw new IntentResolutionException("Can not get the location property of the node " +
                    node.getNodeId().getValue() + ".");
        }

        Property operatingModeProperty = IntentResolverUtils
                .getNodeProperty(node.getProperty(), new PropertyName("operating-mode"));

        if ( null == operatingModeProperty ) {
            throw new IntentResolutionException("Can not get the operating mode property of the node " +
                    node.getNodeId().getValue() + ".");
        }

        PhysicalNodeId physicalNodeId = IntentResolverUtils
                .generatePhysicalNodeIdFromNodeLocationProperty(locationProperty);

        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement> physicalResourceRequirements =
                new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement>(1);
        AttributeValue attributeValue = new AttributeValueBuilder()
                .setStringValue(physicalNodeId.getValue())
                .build();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement physicalResourceRequirement =
                new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirementBuilder()
                        .setAttributeName(new AttributeName("location"))
                        .setAttributeValue(attributeValue)
                        .setAttributeMatchPattern(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.PhysicalResourceRequirement.AttributeMatchPattern.Equal)
                        .build();
        physicalResourceRequirements.add(physicalResourceRequirement);

        String operatingModePropertyValue =
                operatingModeProperty.getPropertyValues().getStringValue().get(0).getValue();

        VirtualNode virtualNode = new VirtualNodeBuilder()
                .setNodeId(new VirtualNodeId(UUID.randomUUID().toString()))
                .setNodeType(operatingModePropertyValue.equals("layer3") ?
                        VirtualNode.NodeType.Vrouter : VirtualNode.NodeType.Vswitch)
                .setVirtualPort(new LinkedList<VirtualPort>())
                .setPhysicalResourceRequirement(physicalResourceRequirements)
                .build();

        virtualNetwork.getVirtualNodes().getVirtualNode().add(virtualNode);

        ExternalMacAddresses externalMacAddresses = null;
        ExternalIpPrefixes externalIpPrefixes = null;

        if ( operatingModePropertyValue.equals("layer2") ) {
            externalMacAddresses = new ExternalMacAddressesBuilder()
                    .setExternalMacAddress(new ArrayList<MacAddress>(0))
                    .build();
        } else {
            externalIpPrefixes = new ExternalIpPrefixesBuilder()
                    .setExternalIpPrefix(new ArrayList<IpPrefix>(0))
                    .build();
        }

        List<PhysicalResourceRequirement> physicalResourceRequirements1 =
                new ArrayList<PhysicalResourceRequirement>(1);
        AttributeValue attributeValue1 = new AttributeValueBuilder()
                .setStringValue(locationProperty.getPropertyValues().getStringValue().get(0).getValue())
                .build();
        PhysicalResourceRequirement physicalResourceRequirement1 = new PhysicalResourceRequirementBuilder()
                .setAttributeName(new AttributeName("location"))
                .setAttributeValue(attributeValue1)
                .setAttributeMatchPattern(PhysicalResourceRequirement.AttributeMatchPattern.Equal)
                .build();
        physicalResourceRequirements1.add(physicalResourceRequirement1);

        VirtualPort virtualPort = new VirtualPortBuilder()
                .setPortId(new VirtualPortId(UUID.randomUUID().toString()))
                .setPortType(VirtualPort.PortType.External)
                .setExternalMacAddresses(externalMacAddresses)
                .setExternalIpPrefixes(externalIpPrefixes)
                .setPhysicalResourceRequirement(physicalResourceRequirements1)
                .build();

        virtualNode.getVirtualPort().add(virtualPort);

        if ( operatingModePropertyValue.equals("layer2") ) {
            // TODO: Connect to all virtual switches in the same layer2 group.
        }

        List<VirtualResource> virtualResources = new ArrayList<VirtualResource>(1);
        VirtualResource virtualResource = new VirtualResourceBuilder()
                .setVirtualResourceId(new VirtualResourceId(UUID.randomUUID().toString()))
                .setVirtualResourceType(VirtualResource.VirtualResourceType.Vport)
                .setVirtualResourceEntityId(new VirtualResourceEntityId(virtualPort.getPortId().getValue()))
                .setParentVirtualResourceEntityId(new VirtualResourceEntityId(virtualNode.getNodeId().getValue()))
                .setOrder(0L)
                .build();
        virtualResources.add(virtualResource);

        IntentVnMappingResult intentVnMappingResult = new IntentVnMappingResultBuilder()
                .setIntentId(new IntentId(node.getNodeId().getValue()))
                .setIntentType(IntentVnMappingResult.IntentType.Node)
                .setVirtualResource(virtualResources)
                .build();

        userIntentVnMapping.getIntentVnMappingResult().add(intentVnMappingResult);

        return;
    }
}
