/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.renderer.openflow;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.IpAddress;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.IpPrefix;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Ipv4Prefix;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev100924.MacAddress;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.dec.mpls.ttl._case.DecMplsTtl;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.dec.mpls.ttl._case.DecMplsTtlBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.dec.nw.ttl._case.DecNwTtl;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.dec.nw.ttl._case.DecNwTtlBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.output.action._case.OutputAction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.output.action._case.OutputActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.pop.mpls.action._case.PopMplsAction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.pop.mpls.action._case.PopMplsActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.push.mpls.action._case.PushMplsAction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.push.mpls.action._case.PushMplsActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.set.field._case.SetField;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.set.field._case.SetFieldBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowCapableNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.meters.Meter;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.meters.MeterBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.meters.MeterKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.Table;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.TableKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.FlowBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.FlowKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.Instructions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.InstructionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.Match;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.apply.actions._case.ApplyActions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.apply.actions._case.ApplyActionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.go.to.table._case.GoToTable;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.go.to.table._case.GoToTableBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.write.metadata._case.WriteMetadata;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.write.metadata._case.WriteMetadataBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.Instruction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.node.NodeConnector;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.node.NodeConnectorKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.l2.types.rev130827.EtherType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.BandId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.MeterBandType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.MeterFlags;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.MeterId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.band.type.band.type.Drop;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.band.type.band.type.DropBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.meter.MeterBandHeadersBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.meter.meter.band.headers.MeterBandHeader;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.meter.meter.band.headers.MeterBandHeaderBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.meter.meter.band.headers.MeterBandHeaderKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.meter.types.rev130918.meter.meter.band.headers.meter.band.header.MeterBandTypesBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetDestinationBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetSourceBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetTypeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._3.match.Ipv4Match;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._3.match.Ipv4MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalLinks;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalPaths;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.nodes.PhysicalNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.paths.PhysicalPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.paths.PhysicalPathBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.instance.PhysicalPort;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.arps.VirtualArp;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.arps.VirtualArpKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.paths.VirtualPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.node.instance.VirtualPort;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResource;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.UserVnPnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.user.vn.pn.mapping.VnPnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.IntentId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.MatchItemName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.MatchItem;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.*;

public class FlowUtils implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(FlowUtils.class);

    private static final short IN_PORT_TABLE_ID = 0;
    private static final short MPLS_LABEL_TABLE_ID = 1;
    private static final short MAC_TABLE_ID = 2;
    private static final short IP_TABLE_ID = 3;
    private static final short ARP_TABLE_ID = 4;

    private static final int DEFAULT_FLOW_PRIORITY = 0;
    private static final String DEFAULT_METADATA_MASK = "ffffffffffffffff";

    private static final int ETH_TYPE_IP = 0x0800;
    private static final int ETH_TYPE_MPLS = 0x8847;
    private static final int ETH_TYPE_ARP = 0x0806;

    private final DataBroker dataBroker;

    private Map<PhysicalNodeId, MplsLabelGenerator> mplsGenerators;
    private Map<PhysicalNodeId, MeterIdGenerator> meterIdGenerators;
    private Map<PhysicalPathId, List<Integer>> mplsLabelsOfPhysicalPaths;
    private Map<PhysicalPathId, Long> meterIdsOfPhysicalPaths;
	private Map<UserId, Long> metadatas;
    private long currentMetadata = 0;

    private PhysicalNetworkHelper physicalNetworkHelper;
    private VirtualNetworkHelper virtualNetworkHelper;

    //liushixing
    private Map<UserId, List<InstanceIdentifier<Flow>>> flowIdsOfUsers;
    private Map<UserId, List<InstanceIdentifier<Meter>>> meterIdIdsOfUsers;
//    private List<PhysicalPortId> assignedPortForInPort;
    private ResourceManager resourceManager;
	
    private Map<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId, MacAddress> gatewayMacAddress;

    public FlowUtils(DataBroker dataBroker, ResourceManager resourceManager) {
        super();

        this.dataBroker = dataBroker;
        this.resourceManager = resourceManager;

        mplsGenerators = new HashMap<PhysicalNodeId, MplsLabelGenerator>();
        meterIdGenerators = new HashMap<PhysicalNodeId, MeterIdGenerator>();
        mplsLabelsOfPhysicalPaths = new HashMap<PhysicalPathId, List<Integer>>();
        meterIdsOfPhysicalPaths = new HashMap<PhysicalPathId, Long>();
        metadatas = new HashMap<UserId, Long>();

        //liushixing
        flowIdsOfUsers = new HashMap<UserId, List<InstanceIdentifier<Flow>>>();
        meterIdIdsOfUsers = new HashMap<UserId, List<InstanceIdentifier<Meter>>>();
//        assignedPortForInPort = new ArrayList<PhysicalPortId>();

        gatewayMacAddress = new HashMap<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId, MacAddress>();
        // Gateway of the DMZ group.
        gatewayMacAddress.put(new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId("b46cfa7f-93a3-43f4-ac20-09307c75feca"),
                new MacAddress("00:00:0a:0b:0c:01"));
        // Gateway of the interior group.
        gatewayMacAddress.put(new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId("175425f7-c9c9-474a-962c-70cb6c180d4d"),
                new MacAddress("00:00:0a:0b:0c:02"));
    }

	public void init(List<PhysicalNode> physicalNodes) {
		for ( PhysicalNode physicalNode : physicalNodes ) {
			mplsGenerators.put(physicalNode.getNodeId(), new MplsLabelGenerator());
			meterIdGenerators.put(physicalNode.getNodeId(), new MeterIdGenerator());
		}
	}

    /**
     * TODO
     *
     * @author Shixing Liu
     * @param userId TODO
     * @param nodeId TODO
     * @param tableId TODO
     * @param flowId TODO
     */
    private InstanceIdentifier<Flow> generateFlowInsId(UserId userId,
                                                       NodeId nodeId,
                                                       Short tableId,
                                                       FlowId flowId){


        InstanceIdentifier<Flow> flowInsId = createFlowPath(nodeId, tableId, flowId);

        if(flowIdsOfUsers.containsKey(userId) == false){
            List<InstanceIdentifier<Flow>> flowInsIds = new ArrayList<InstanceIdentifier<Flow>>();
            flowInsIds.add(flowInsId);
            flowIdsOfUsers.put(userId, flowInsIds);
        }else{
            List<InstanceIdentifier<Flow>> flowInsIds = flowIdsOfUsers.get(userId);
            flowInsIds.add(flowInsId);
        }
        LOG.info("nemo:generateFlowInsId");
        return  flowInsId;
    }

    /**
     * TODO
     *
     * @author Shixing Liu
     * @param userId TODO
     * @param nodeKey TODO
     * @param meterKey TODO
     */
    private InstanceIdentifier<Meter> generateMeterInsId(UserId userId,
                                                         NodeKey nodeKey,
                                                         MeterKey meterKey){
        InstanceIdentifier<Meter> meterInsId = InstanceIdentifier.create(Nodes.class)
                .child(Node.class,nodeKey)
                .augmentation(FlowCapableNode.class).child(Meter.class, meterKey);

        if(meterIdIdsOfUsers.containsKey(userId) == false){
            List<InstanceIdentifier<Meter>> meterInsIds = new ArrayList<InstanceIdentifier<Meter>>();
            meterInsIds.add(meterInsId);
            meterIdIdsOfUsers.put(userId, meterInsIds);
        }
        else{
            List<InstanceIdentifier<Meter>> meterInsIds = meterIdIdsOfUsers.get(userId);
            meterInsIds.add(meterInsId);
        }
        LOG.info("nemo:getMeterInsId");
        return  meterInsId;
    }

    public void updateFlowTable(User user,
                                VirtualNetwork virtualNetwork,
                                UserIntentVnMapping userIntentVnMapping,
                                UserVnPnMapping userVnPnMapping,
                                PhysicalNetwork physicalNetwork) {
		//If new user, generate metadata;
        if ( !metadatas.containsKey(user.getUserId()) ) {
            metadatas.put(user.getUserId(), ++currentMetadata);
        }

        physicalNetworkHelper = new PhysicalNetworkHelper(physicalNetwork);
        virtualNetworkHelper = new VirtualNetworkHelper(virtualNetwork);

		updateInPortTable(user, virtualNetwork, userIntentVnMapping, userVnPnMapping, physicalNetwork);
        updateMeterTable(user, virtualNetwork, userIntentVnMapping, userVnPnMapping, physicalNetwork);
		updateMplsTable(user, virtualNetwork, userIntentVnMapping, userVnPnMapping, physicalNetwork);
		updateIpTable(user, virtualNetwork, userIntentVnMapping, userVnPnMapping, physicalNetwork);
		updateArpTable(user, virtualNetwork, userIntentVnMapping, userVnPnMapping, physicalNetwork);
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param userId TODO
     */
    public void deleteFlowEntries(UserId userId) {
        deleteFlowTableEntries(userId);
        deleteMeterTableEntries(userId);

        flowIdsOfUsers.put(userId, new LinkedList<InstanceIdentifier<Flow>>());
        meterIdIdsOfUsers.put(userId, new LinkedList<InstanceIdentifier<Meter>>());

        return;
    }

    @Override
    public void close() throws Exception {
        // TODO
    }

    /**
     * TODO
     *
     * @author Shixing Liu
     * @param physicalDestNodeId TODO
     * @param physicalDestPortId TODO
     */
    private void configInternalInPortFlowTable(UserId userId,
                                               PhysicalNodeId physicalDestNodeId,
                                               PhysicalPortId physicalDestPortId) {
        String nodeID = physicalDestNodeId.getValue();
        String portID = physicalDestPortId.getValue();

        WriteTransaction writeTransaction = dataBroker.newWriteOnlyTransaction();
        List<Instruction> instructionList = new LinkedList<Instruction>();
        Match match = new MatchBuilder().setInPort(new NodeConnectorId(portID)).build();

        GoToTable gotoTable = new GoToTableBuilder().setTableId(MPLS_LABEL_TABLE_ID).build();
        GoToTableCase gotoTableCase = new GoToTableCaseBuilder().setGoToTable(gotoTable).build();
        Instruction instructionGoto = new InstructionBuilder().setOrder(0).setInstruction(gotoTableCase).build();
        instructionList.add(instructionGoto);

        Instructions instructions = new InstructionsBuilder().setInstruction(instructionList).build();

        FlowId flowId = new FlowId(UUID.randomUUID().toString());
        FlowBuilder flowBuilder = baseFlowBuilder().setId(flowId).setTableId(IN_PORT_TABLE_ID).setPriority(DEFAULT_FLOW_PRIORITY);
        Flow flow = flowBuilder.setMatch(match).setInstructions(instructions).build();
        NodeId nodeId = new NodeId(nodeID);

        InstanceIdentifier<Flow> flowInsId = generateFlowInsId(userId, nodeId, flow.getTableId(), flow.getId());

        writeTransaction.put(LogicalDatastoreType.CONFIGURATION, flowInsId, flow, true);
        writeTransaction.submit();

        LOG.info("nemo:configInternalInPortFlowTable");
        return;
    }

    /**
     * TODO
     *
     * @author Shixing Liu
     * @param physicalDestNodeId TODO
     * @param physicalDestPortId TODO
     * @param destNodeType TODO
     */
    private void configExternalInPortFlowTable(UserId userId,
                                               PhysicalNodeId physicalDestNodeId,
                                               PhysicalPortId physicalDestPortId,
                                               VirtualNode.NodeType destNodeType) {
        String nodeID = physicalDestNodeId.getValue();
        String portID = physicalDestPortId.getValue();

        WriteTransaction writeTransaction = dataBroker.newWriteOnlyTransaction();
        List<Instruction> instructionList = new LinkedList<Instruction>();
        Match match = new MatchBuilder().setInPort(new NodeConnectorId(portID)).build();

        WriteMetadata writeMetadata = new WriteMetadataBuilder().setMetadata(BigInteger.valueOf(metadatas.get(userId))).setMetadataMask(new BigInteger(DEFAULT_METADATA_MASK, 16)).build();
        WriteMetadataCase writeMetadataCase = new WriteMetadataCaseBuilder().setWriteMetadata(writeMetadata).build();
        Instruction instructionMeta = new InstructionBuilder().setOrder(instructionList.size()).setInstruction(writeMetadataCase).build();
        instructionList.add(instructionMeta);

        GoToTable gotoTable = new GoToTableBuilder().setTableId((destNodeType==VirtualNode.NodeType.Vswitch)? MAC_TABLE_ID : IP_TABLE_ID).build();
        GoToTableCase gotoTableCase = new GoToTableCaseBuilder().setGoToTable(gotoTable).build();
        Instruction instructionGoto = new InstructionBuilder().setOrder(instructionList.size()).setInstruction(gotoTableCase).build();
        instructionList.add(instructionGoto);

        Instructions instructions = new InstructionsBuilder().setInstruction(instructionList).build();

        FlowId flowId = new FlowId(UUID.randomUUID().toString());

        FlowBuilder flowBuilder = baseFlowBuilder().setId(flowId).setTableId(IN_PORT_TABLE_ID).setPriority(DEFAULT_FLOW_PRIORITY);
        Flow flow = flowBuilder.setMatch(match).setInstructions(instructions).build();

        NodeId nodeId = new NodeId(nodeID);

        InstanceIdentifier<Flow> flowInsId = generateFlowInsId(userId, nodeId, flow.getTableId(), flow.getId());

        writeTransaction.put(LogicalDatastoreType.CONFIGURATION, flowInsId, flow, true);
        writeTransaction.submit();

        LOG.info("nemo:configExternalInPortFlowTable");

        return;
    }

    /**
     * TODO
     *
     * @author Shixing Liu
     * @param user TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     * @param userVnPnMapping TODO
     * @param physicalNetwork TODO
     */
    private void updateInPortTable(User user,
                                   VirtualNetwork virtualNetwork,
                                   UserIntentVnMapping userIntentVnMapping,
                                   UserVnPnMapping userVnPnMapping,
                                   PhysicalNetwork physicalNetwork) {
        List<VnPnMappingResult> vnPnMappingResults = userVnPnMapping.getVnPnMappingResult();

        for(VnPnMappingResult vnPnMappingResult:vnPnMappingResults){
            LOG.info("nemo:inport  for(VnPnMappingResult vnPnMappingResult:vnPnMappingResults)");
            if(VirtualResource.VirtualResourceType.Vport == vnPnMappingResult.getVirtualResourceType()) {
                VirtualPortId virtualPortid = new VirtualPortId(vnPnMappingResult.getVirtualResourceEntityId().getValue());
                VirtualNodeId virtualNodeId = new VirtualNodeId(vnPnMappingResult.getParentVirtualResourceEntityId().getValue());

                VirtualPort.PortType virtualPortType = VirtualPort.PortType.Internal;
                VirtualNode.NodeType virtualNodeType = VirtualNode.NodeType.Vswitch;

                List<VirtualNode> virtualNodes = virtualNetwork.getVirtualNodes().getVirtualNode();
                for (VirtualNode virtualNode : virtualNodes) {
                    if (virtualNode.getNodeId().equals(virtualNodeId)){
                        virtualNodeType = virtualNode.getNodeType();
                        for (VirtualPort virtualPort : virtualNode.getVirtualPort()) {
                            if (virtualPort.getPortId().equals(virtualPortid)){
                                virtualPortType = virtualPort.getPortType();
                                break;
                            }
                        }
                        break;
                    }
                }

                PhysicalNodeId physicalDestNodeId =
                        new PhysicalNodeId(vnPnMappingResult.getParentPhysicalResourceEntityId().getValue());

                PhysicalPortId physicalDestPortId =
                        new PhysicalPortId(vnPnMappingResult.getPhysicalResourceEntityId().getValue());

                if (virtualPortType == VirtualPort.PortType.External) {
                    configExternalInPortFlowTable(userVnPnMapping.getUserId(), physicalDestNodeId, physicalDestPortId, virtualNodeType);
                } else {
                    configInternalInPortFlowTable(userVnPnMapping.getUserId(), physicalDestNodeId, physicalDestPortId);
                }
            }
        }

        for(PhysicalNode physicalNode: physicalNetwork.getPhysicalNodes().getPhysicalNode()){
            PhysicalNodeId physicalDestNodeId = physicalNode.getNodeId();

            for (PhysicalPort physicalPort: physicalNode.getPhysicalPort()){
                PhysicalPortId physicalDestPortId = physicalPort.getPortId();
                PhysicalPort.PortType physicalPortType = physicalPort.getPortType();

                if(physicalPortType == PhysicalPort.PortType.Internal){
                    configInternalInPortFlowTable(userVnPnMapping.getUserId(), physicalDestNodeId, physicalDestPortId);
                }
            }
        }
	}

    /**
     * TODO
     *
     * @author Shixing Liu
     * @param user TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     * @param userVnPnMapping TODO
     * @param physicalNetwork TODO
     */
    private void updateMeterTable(User user,
                                  VirtualNetwork virtualNetwork,
                                  UserIntentVnMapping userIntentVnMapping,
                                  UserVnPnMapping userVnPnMapping,
                                  PhysicalNetwork physicalNetwork) {

        LOG.info("nemo:meter updateMeterTable()");
        PhysicalPaths physicalPaths = physicalNetwork.getPhysicalPaths();
        if(null == physicalPaths.getPhysicalPath()){
            LOG.info("PhysicalPath are null");
            return;
        }

        List<PhysicalPath> physicalPathList = physicalPaths.getPhysicalPath();

        PhysicalLinks physicalLinks = physicalNetwork.getPhysicalLinks();
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLink>
                physicalLinksList = physicalLinks.getPhysicalLink();

        for(PhysicalPath physicalPath:physicalPathList) {
            if(physicalPath.getBandwidth() > 0 ) {
                LOG.info("nemo: meter physicalPath.getBandwidth() = {}", physicalPath.getBandwidth());
                if(meterIdsOfPhysicalPaths.containsKey(physicalPath.getPathId())== false){
                    LOG.info("nemo:meter meterIdsOfPhysicalPaths.containsKey(physicalPath.getPathId())== false");
                    org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink
                            physicalLinkInPath = physicalPath.getPhysicalLink().get(0);

                    LOG.info("nemo:meter physicalLinkInPath"+physicalLinkInPath.getLinkId().getValue());
                    for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLink physicalLink : physicalLinksList) {
                        LOG.info("nemo:meter physicalLink "+physicalLink.getLinkId().getValue());
                        if (physicalLinkInPath.getLinkId().getValue().equals(physicalLink.getLinkId().getValue())) {

                            LOG.info("nemo:meter find plink for ppath.");
                            PhysicalNodeId physicalSrcNodeId = physicalLink.getSrcNodeId();
                            PhysicalPortId physicalSrcPortId = physicalLink.getSrcPortId();

                            LOG.info("nemo:meter meterIdGenerators.size() = "+ meterIdGenerators.size());
                            LOG.info("nemo:meter physicalSrcNodeId =" + physicalSrcNodeId.getValue());

                            LOG.info("nemo:meter Assign meter id");
                            Long meterId = (long)0;
                            if(meterIdGenerators.containsKey(physicalSrcNodeId) == false){
                                LOG.info("meterIdGenerators.containsKey(physicalSrcNodeId) == false");
                                MeterIdGenerator meterIdGenerator = new MeterIdGenerator();
                                meterIdGenerators.put(physicalSrcNodeId, meterIdGenerator);
                                meterId = meterIdGenerators.get(physicalSrcNodeId).generateMeterId();
                                meterIdsOfPhysicalPaths.put(physicalPath.getPathId(),meterId);
                            }
                            else{
                                LOG.info("meterIdGenerators.containsKey(physicalSrcNodeId) == true");
                                meterId = meterIdGenerators.get(physicalSrcNodeId).generateMeterId();
                                meterIdsOfPhysicalPaths.put(physicalPath.getPathId(),meterId);
                            }

                            //Generate meter flow entries
                            LOG.info("nemo:meter Generate meter flow entries");
                            NodeKey nodeKey = new NodeKey(new NodeId(physicalSrcNodeId.getValue()));
                            MeterKey meterKey = new MeterKey(new MeterId(meterId));

                            InstanceIdentifier<Meter> meterInsId = generateMeterInsId(userVnPnMapping.getUserId(), nodeKey, meterKey);

                            /*
                            MeterBandHeaderBuilder meterBandHeaderBuilder = new MeterBandHeaderBuilder();
                            MeterBandTypesBuilder meterBandTypesB = new MeterBandTypesBuilder();
                            MeterBandType bandFlag = new MeterBandType(true, false, false);
                            meterBandTypesB.setFlags(bandFlag);// _ofpmbtDrop
                            DropBuilder drop = new DropBuilder();
                            drop.setDropBurstSize(physicalPath.getBandwidth() / 2);
                            drop.setDropRate(physicalPath.getBandwidth());
                            Drop drp = drop.build();
                            meterBandHeaderBuilder.setBandType(drp);
                            meterBandHeaderBuilder.setMeterBandTypes(meterBandTypesB.build());
                            MeterBandHeader meterBH = meterBandHeaderBuilder.build();
                            MeterBuilder meterBuilder = new MeterBuilder();
                            meterBuilder.setMeterBandHeaders(meterBandHeadersBuilder.setMeterBandHeader(meterBandHeaders).build());
                            */

                            MeterBuilder meterBuilder = new MeterBuilder();
                            MeterBandHeaderBuilder meterBandHeaderBuilder = new MeterBandHeaderBuilder();
                            MeterBandHeadersBuilder meterBandHeadersBuilder = new MeterBandHeadersBuilder();
                            MeterBandTypesBuilder meterBandTypesB = new MeterBandTypesBuilder();

                            meterBandHeaderBuilder.setKey(new MeterBandHeaderKey(new BandId(physicalPath.getBandwidth())));
                            meterBandHeaderBuilder.setBandBurstSize((long)0);
                            meterBandHeaderBuilder.setBandRate(physicalPath.getBandwidth());

                            MeterBandType bandFlag = new MeterBandType(true, false, false);
                            meterBandTypesB.setFlags(bandFlag);// _ofpmbtDrop
                            DropBuilder drop = new DropBuilder();
                            drop.setDropBurstSize((long)0);
                            drop.setDropRate(physicalPath.getBandwidth());
                            Drop drp = drop.build();
                            meterBandHeaderBuilder.setBandType(drp);
                            meterBandHeaderBuilder.setMeterBandTypes(meterBandTypesB.build());

                            MeterBandHeader meterBH = meterBandHeaderBuilder.build();
                            List<MeterBandHeader> meterBandHeaders = new ArrayList<MeterBandHeader>();
                            meterBandHeaders.add(0, meterBH);

                            meterBuilder.setKey(new MeterKey(new MeterId(meterId)));
                            meterBuilder.setBarrier(false);

                            meterBuilder.setFlags(new MeterFlags(false, true, false, true));

                            meterBuilder.setContainerName("container." + physicalPath.getPathId());
                            meterBuilder.setMeterName("meter." + physicalPath.getPathId());
                            meterBandHeadersBuilder.setMeterBandHeader(meterBandHeaders);
                            meterBuilder.setMeterBandHeaders(meterBandHeadersBuilder.build());

                            Meter meter = meterBuilder.build();

                            WriteTransaction writeTransaction = dataBroker.newWriteOnlyTransaction();
                            writeTransaction.put(LogicalDatastoreType.CONFIGURATION, meterInsId, meter);
                            writeTransaction.submit();
                            LOG.info("nemo:meter writeTransaction.submit();");
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * TODO
     *
     * @author Shixing Liu
     * @param physicalPath TODO
     * @param physicalLinksList TODO
     */
    public void assignMPLSLabelForPPath(PhysicalPath physicalPath,
                  List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLink> physicalLinksList){

        LOG.info("nemo: for(1)");
        List<Integer> mplsLabels = new ArrayList<Integer>();
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink>
                physicalLinksInPath = physicalPath.getPhysicalLink();

        for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink physicalLinkinPath : physicalLinksInPath) {
            LOG.info("nemo: for(2)");
            for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLink physicalLink : physicalLinksList) {
                LOG.info("nemo: for(3)");
                if (physicalLinkinPath.getLinkId().getValue().equals(physicalLink.getLinkId().getValue())) {
                    LOG.info("nemo: physicalLinkinPath.getLinkId() == physicalLink.getLinkId()");
                    PhysicalNodeId physicalDestNodeId = physicalLink.getDestNodeId();
                    if(mplsGenerators.containsKey(physicalDestNodeId) == true){

                        mplsLabels.add(mplsGenerators.get(physicalDestNodeId).generateMplsLabel());

                    }
                    else{

                        MplsLabelGenerator mplsLabelGenerator = new MplsLabelGenerator();
                        mplsGenerators.put(physicalDestNodeId, mplsLabelGenerator);
                        mplsLabels.add(mplsGenerators.get(physicalDestNodeId).generateMplsLabel());
                    }
                }
            }
        }
        mplsLabelsOfPhysicalPaths.put(physicalPath.getPathId(), mplsLabels);
    }

    /**
     * TODO
     *
     * @author Shixing Liu
     * @param user TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     * @param userVnPnMapping TODO
     * @param physicalNetwork TODO
     */
    private void updateMplsTable(User user,
                                 VirtualNetwork virtualNetwork,
                                 UserIntentVnMapping userIntentVnMapping,
                                 UserVnPnMapping userVnPnMapping,
                                 PhysicalNetwork physicalNetwork) {

        LOG.info("nemo:mpls: updateMplsTable()");
        PhysicalPaths physicalPaths = physicalNetwork.getPhysicalPaths();

        if(null == physicalPaths.getPhysicalPath()){
            LOG.info("PhysicalPaths are null");
            return;
        }

        List<PhysicalPath> physicalPathList = physicalPaths.getPhysicalPath();

        PhysicalLinks physicalLinks = physicalNetwork.getPhysicalLinks();
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLink>
                physicalLinksList = physicalLinks.getPhysicalLink();

        Iterator<Integer> mplsLabelIter = null;

        List<VnPnMappingResult> vnPnMappingResults = userVnPnMapping.getVnPnMappingResult();

        for(VnPnMappingResult vnPnMappingResult:vnPnMappingResults) {
            if (VirtualResource.VirtualResourceType.Vlink == vnPnMappingResult.getVirtualResourceType()) {
                PhysicalPathId physicalPathId = new PhysicalPathId(vnPnMappingResult.getPhysicalResourceEntityId().getValue());
                PhysicalPath physicalPath = physicalNetworkHelper.getPhysicalPath(physicalPathId);

                VirtualLinkId virtualLinkId = new VirtualLinkId(vnPnMappingResult.getVirtualResourceEntityId().getValue());
                VirtualLink virtualLink = virtualNetworkHelper.getVirtualLink(virtualLinkId);
                VirtualNodeId virtualNodeId = virtualLink.getDestNodeId();
                VirtualNode.NodeType nodetype = VirtualNode.NodeType.Vswitch;
                List<VirtualNode> virtualNodes = virtualNetwork.getVirtualNodes().getVirtualNode();
                for (VirtualNode virtualNode : virtualNodes) {
                    if (virtualNode.getNodeId().equals(virtualNodeId)) {
                        nodetype = virtualNode.getNodeType();
                        break;
                    }
                }
                
                //Assign MPLS Label and record MPLS Label
                assignMPLSLabelForPPath(physicalPath,physicalLinksList);

                //Create Flow Entries for MPLS Flow Table
                int counter = 0;
                int inMPLSLabel = Integer.MAX_VALUE;
                int outMPLSLabel = Integer.MAX_VALUE;

                List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink>
                        physicalLinksInPath = physicalPath.getPhysicalLink();
                for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink physicalLinkinPath : physicalLinksInPath) {
                    for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLink physicalLink : physicalLinksList) {
                        if (physicalLinkinPath.getLinkId().getValue().equals(physicalLink.getLinkId().getValue())) {
                            PhysicalNodeId physicalDestNodeId = physicalLink.getDestNodeId();
                            PhysicalNodeId physicalSrcNodeId = physicalLink.getSrcNodeId();
                            PhysicalPortId physicalSrcPortId = physicalLink.getSrcPortId();

                            if (0 == counter++) {
                                LOG.info("nemo:mpls:0 == counter");
                                mplsLabelIter = mplsLabelsOfPhysicalPaths.get(physicalPath.getPathId()).iterator();
                                inMPLSLabel = mplsLabelIter.next();
                            } else {
                                LOG.info("nemo:mpls:counter="+counter);
                                WriteTransaction writeTransaction = dataBroker.newWriteOnlyTransaction();
                                List<Instruction> instructionList = new LinkedList<Instruction>();
                                List<Action> actionList = new LinkedList<Action>();
                                outMPLSLabel = mplsLabelIter.next();

                                EthernetMatchBuilder ethernetMatchBuilder = new EthernetMatchBuilder().setEthernetType(new EthernetTypeBuilder().setType(new EtherType((long) ETH_TYPE_MPLS)).build());
                                EthernetMatch ethernetMatch = ethernetMatchBuilder.build();

                                ProtocolMatchFieldsBuilder protocolMatchFieldsBuilder = new ProtocolMatchFieldsBuilder().setMplsLabel((long) inMPLSLabel);
                                ProtocolMatchFields protocolMatchFields = protocolMatchFieldsBuilder.build();

                                Match match = new MatchBuilder().setEthernetMatch(ethernetMatch).setProtocolMatchFields(protocolMatchFields).build();

                                SetField setField = new SetFieldBuilder().setProtocolMatchFields(new ProtocolMatchFieldsBuilder().setMplsLabel((long) outMPLSLabel).build()).build();
                                SetFieldCase setFieldCase = new SetFieldCaseBuilder().setSetField(setField).build();
                                Action actionSetField = new ActionBuilder().setOrder(0).setAction(setFieldCase).build();
                                actionList.add(actionSetField);

                                DecMplsTtl decMplsTtl = new DecMplsTtlBuilder().build();
                                DecMplsTtlCase decMplsTtlCase = new DecMplsTtlCaseBuilder().setDecMplsTtl(decMplsTtl).build();
                                Action actionDecMPLS = new ActionBuilder().setOrder(1).setAction(decMplsTtlCase).build();
                                actionList.add(actionDecMPLS);

                                OutputAction outputAction = new OutputActionBuilder().setOutputNodeConnector(new NodeConnectorId(physicalSrcPortId.getValue())).build();
                                OutputActionCase outputActionCase = new OutputActionCaseBuilder().setOutputAction(outputAction).build();
                                Action actionOutput = new ActionBuilder().setOrder(2).setAction(outputActionCase).build();
                                actionList.add(actionOutput);

                                ApplyActions applyActions = new ApplyActionsBuilder().setAction(actionList).build();
                                ApplyActionsCase applyActionsCase = new ApplyActionsCaseBuilder().setApplyActions(applyActions).build();
                                Instruction instructionApply = new InstructionBuilder().setOrder(0).setInstruction(applyActionsCase).build();
                                instructionList.add(instructionApply);

                                Instructions instructions = new InstructionsBuilder().setInstruction(instructionList).build();

                                FlowId flowId = new FlowId(UUID.randomUUID().toString());
                                FlowBuilder flowBuilder = baseFlowBuilder().setId(flowId).setTableId(MPLS_LABEL_TABLE_ID).setPriority(DEFAULT_FLOW_PRIORITY);
                                Flow flow = flowBuilder.setMatch(match).setInstructions(instructions).build();

                                LOG.info("nemo:mpls"+physicalSrcNodeId.getValue());

                                NodeId nodeId = new NodeId(physicalSrcNodeId.getValue());

                                InstanceIdentifier<Flow> flowInsId = generateFlowInsId(user.getUserId(), nodeId, flow.getTableId(), flow.getId());

                                writeTransaction.put(LogicalDatastoreType.CONFIGURATION, flowInsId, flow, true);
                                writeTransaction.submit();

                                inMPLSLabel = outMPLSLabel;
                            }
                            //The last hop
                            if (physicalPath.getPhysicalLink().size() == counter) {
                                LOG.info("nemo:mpls: last hop");
                                WriteTransaction writeTransaction = dataBroker.newWriteOnlyTransaction();
                                List<Instruction> instructionList = new LinkedList<Instruction>();
                                List<Action> actionList = new LinkedList<Action>();

                                EthernetMatchBuilder ethernetMatchBuilder = new EthernetMatchBuilder().setEthernetType(new EthernetTypeBuilder().setType(new EtherType((long) ETH_TYPE_MPLS)).build());
                                EthernetMatch ethernetMatch = ethernetMatchBuilder.build();

                                ProtocolMatchFieldsBuilder protocolMatchFieldsBuilder = new ProtocolMatchFieldsBuilder().setMplsLabel((long) inMPLSLabel);
                                ProtocolMatchFields protocolMatchFields = protocolMatchFieldsBuilder.build();

                                Match match = new MatchBuilder().setEthernetMatch(ethernetMatch).setProtocolMatchFields(protocolMatchFields).build();

                                PopMplsAction popMplsAction = new PopMplsActionBuilder().setEthernetType(ETH_TYPE_IP).build();
                                PopMplsActionCase popMplsActionCase = new PopMplsActionCaseBuilder().setPopMplsAction(popMplsAction).build();
                                Action actionPopMPLS = new ActionBuilder().setOrder(0).setAction(popMplsActionCase).build();
                                actionList.add(actionPopMPLS);

                                ApplyActions applyActions = new ApplyActionsBuilder().setAction(actionList).build();
                                ApplyActionsCase applyActionsCase = new ApplyActionsCaseBuilder().setApplyActions(applyActions).build();
                                Instruction instructionApply = new InstructionBuilder().setOrder(0).setInstruction(applyActionsCase).build();
                                instructionList.add(instructionApply);

                                WriteMetadata writeMetadata = new WriteMetadataBuilder().setMetadata(BigInteger.valueOf(metadatas.get(user.getUserId()))).setMetadataMask(new BigInteger(DEFAULT_METADATA_MASK, 16)).build();
                                WriteMetadataCase writeMetadataCase = new WriteMetadataCaseBuilder().setWriteMetadata(writeMetadata).build();
                                Instruction instructionMeta = new InstructionBuilder().setOrder(1).setInstruction(writeMetadataCase).build();
                                instructionList.add(instructionMeta);

                                GoToTable gotoTable = new GoToTableBuilder().setTableId((nodetype == VirtualNode.NodeType.Vswitch)? MAC_TABLE_ID:IP_TABLE_ID).build();
                                GoToTableCase gotoTableCase = new GoToTableCaseBuilder().setGoToTable(gotoTable).build();
                                Instruction instructionGoto = new InstructionBuilder().setOrder(2).setInstruction(gotoTableCase).build();
                                instructionList.add(instructionGoto);

                                Instructions instructions = new InstructionsBuilder().setInstruction(instructionList).build();

                                FlowId flowId = new FlowId(UUID.randomUUID().toString());
                                FlowBuilder flowBuilder = baseFlowBuilder().setId(flowId).setTableId(MPLS_LABEL_TABLE_ID).setPriority(DEFAULT_FLOW_PRIORITY);
                                Flow flow = flowBuilder.setMatch(match).setInstructions(instructions).build();

                                LOG.info("nemo:mpls:"+physicalDestNodeId.getValue());
                                NodeId nodeId = new NodeId(physicalDestNodeId.getValue());
                                InstanceIdentifier<Flow> flowInsId = generateFlowInsId(user.getUserId(), nodeId, flow.getTableId(), flow.getId());

                                writeTransaction.put(LogicalDatastoreType.CONFIGURATION, flowInsId, flow, true);
                                writeTransaction.submit();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param user TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     * @param userVnPnMapping TODO
     * @param physicalNetwork TODO
     */
    private void updateIpTable(User user,
                               VirtualNetwork virtualNetwork,
                               UserIntentVnMapping userIntentVnMapping,
                               UserVnPnMapping userVnPnMapping,
                               PhysicalNetwork physicalNetwork) {
        List<VnPnMappingResult> vnPnMappingResults = userVnPnMapping.getVnPnMappingResult();
        Map<VirtualNodeId, VirtualNode> virtualRouters = virtualNetworkHelper.getVirtualRouters();
        Map<VirtualNodeId, Map.Entry<VirtualPort, VirtualLink>> connectedVirtualSwitches;
        Map<VirtualNodeId, Map.Entry<VirtualPort, VirtualLink>> connectedVirtualSwitches1;
        Map<VirtualNodeId, Map.Entry<VirtualPort, VirtualLink>> connectedVirtualRouters;
        Map<VirtualNodeId, Map.Entry<VirtualPort, VirtualLink>> connectedVirtualRouters1;
        VirtualPort layer2ExternalVirtualPort;
        VirtualPort layer2ExternalVirtualPort1;
        VirtualPort layer2ExternalVirtualPort2;
        VirtualPort layer3ExternalVirtualPort;
        VirtualPort layer3ExternalVirtualPort1;
        VnPnMappingResult vnPnMappingResult;
        PhysicalNodeId physicalNodeId;
        PhysicalNodeId physicalNodeId1;
        VirtualLink virtualLink;
        VirtualLink virtualLink1;
        PhysicalPathId physicalPathId;
        PhysicalPathId physicalPathId1;
        PhysicalPath physicalPath;
        PhysicalPath physicalPath1;
        PhysicalPortId physicalPortId;
        PhysicalPort physicalPort;
        List<IpPrefix> remoteIpPrefixes;
        List<IpPrefix> ipPrefixes;
        List<MacAddress> macAddresses;
        VirtualPort virtualPort;
        VirtualArp virtualArp;
        IpPrefix ipPrefix;
        MacAddress gatewayMacAddress;

        for ( VirtualNodeId virtualNodeId : virtualRouters.keySet() ) {
            vnPnMappingResult = getVnPnMappingResult(vnPnMappingResults,
                    new VirtualResourceEntityId(virtualNodeId.getValue()));
            physicalNodeId = new PhysicalNodeId(vnPnMappingResult.getPhysicalResourceEntityId().getValue());

            connectedVirtualRouters = virtualNetworkHelper.getConnectedVirtualRouters(virtualNodeId);

            if ( null != connectedVirtualRouters ) {
                for ( Map.Entry<VirtualNodeId, Map.Entry<VirtualPort, VirtualLink>> entry
                        : connectedVirtualRouters.entrySet() ) {
                    virtualLink = entry.getValue().getValue();
                    vnPnMappingResult = getVnPnMappingResult(vnPnMappingResults,
                            new VirtualResourceEntityId(virtualLink.getLinkId().getValue()));
                    physicalPathId = new PhysicalPathId(
                            vnPnMappingResult.getPhysicalResourceEntityId().getValue());
                    physicalPath = physicalNetworkHelper.getPhysicalPath(physicalPathId);
                    remoteIpPrefixes = new LinkedList<IpPrefix>();

                    connectedVirtualSwitches1 = virtualNetworkHelper
                            .getConnectedVirtualSwitches(entry.getKey());

                    if ( null != connectedVirtualSwitches1 ) {
                        virtualPort = connectedVirtualSwitches1.entrySet().iterator().next().getValue().getKey();
                        ipPrefix = virtualPort.getExternalIpPrefixes().getExternalIpPrefix().get(0);
                        remoteIpPrefixes.add(ipPrefix);
                    }

                    layer3ExternalVirtualPort1 = virtualNetworkHelper
                            .getLayer3ExternalVirtualPort(entry.getKey());

                    if ( null != layer3ExternalVirtualPort1 ) {
                        remoteIpPrefixes.addAll(
                                layer3ExternalVirtualPort1.getExternalIpPrefixes().getExternalIpPrefix());
                    }

                    layer2ExternalVirtualPort1 = virtualNetworkHelper
                            .getLayer2ExternalVirtualPort(entry.getKey());

                    if ( null != layer2ExternalVirtualPort1 ) {
                        remoteIpPrefixes.addAll(
                                layer2ExternalVirtualPort1.getExternalIpPrefixes().getExternalIpPrefix());
                    }

                    if ( !physicalPath.getPhysicalLink().isEmpty() ) {
                        for ( IpPrefix ipPrefix1 : remoteIpPrefixes ) {
                            configIpTableEntry(user.getUserId(), ipPrefix1, physicalPath, false);
                        }
                    }
                }
            }

            connectedVirtualSwitches = virtualNetworkHelper.getConnectedVirtualSwitches(virtualNodeId);

            if ( null != connectedVirtualSwitches ) {
                virtualPort = connectedVirtualSwitches.values().iterator().next().getKey();
                ipPrefix = virtualPort.getExternalIpPrefixes().getExternalIpPrefix().get(0);
                gatewayMacAddress = getGateWayMacAddress(userIntentVnMapping, virtualNodeId);

                configIpTableEntry(user.getUserId(), ipPrefix, gatewayMacAddress, physicalNodeId);

                for ( Map.Entry<VirtualNodeId, Map.Entry<VirtualPort, VirtualLink>> entry
                        : connectedVirtualSwitches.entrySet() ) {
                    virtualLink = entry.getValue().getValue();
                    vnPnMappingResult = getVnPnMappingResult(vnPnMappingResults,
                            new VirtualResourceEntityId(virtualLink.getLinkId().getValue()));
                    physicalPathId = new PhysicalPathId(
                            vnPnMappingResult.getPhysicalResourceEntityId().getValue());
                    physicalPath = physicalNetworkHelper.getPhysicalPath(physicalPathId);

                    connectedVirtualRouters1 = virtualNetworkHelper
                            .getConnectedVirtualRouters(entry.getKey());

                    if ( null != connectedVirtualRouters1 ) {
                        virtualLink1 = connectedVirtualRouters1.values().iterator().next().getValue();
                        vnPnMappingResult = getVnPnMappingResult(vnPnMappingResults,
                                new VirtualResourceEntityId(virtualLink1.getLinkId().getValue()));
                        physicalPathId1 = new PhysicalPathId(
                                vnPnMappingResult.getPhysicalResourceEntityId().getValue());
                        physicalPath1 = physicalNetworkHelper.getPhysicalPath(physicalPathId1);

                        configMacTableEntry(user.getUserId(), gatewayMacAddress, physicalPath1);
                    }

                    connectedVirtualSwitches1 = virtualNetworkHelper
                            .getConnectedVirtualSwitches(entry.getKey());

                    if ( null != connectedVirtualSwitches1 ) {
                        for ( Map.Entry<VirtualNodeId, Map.Entry<VirtualPort, VirtualLink>> entry1
                                : connectedVirtualSwitches1.entrySet() ) {
                            virtualLink1 = entry1.getValue().getValue();
                            vnPnMappingResult = getVnPnMappingResult(vnPnMappingResults,
                                    new VirtualResourceEntityId(virtualLink1.getLinkId().getValue()));
                            physicalPathId1 = new PhysicalPathId(
                                    vnPnMappingResult.getPhysicalResourceEntityId().getValue());
                            physicalPath1 = physicalNetworkHelper.getPhysicalPath(physicalPathId1);

                            if ( !physicalPath1.getPhysicalLink().isEmpty() ) {
                                layer2ExternalVirtualPort2 = virtualNetworkHelper
                                        .getLayer2ExternalVirtualPort(entry1.getKey());

                                if ( null != layer2ExternalVirtualPort2 ) {
                                    macAddresses = layer2ExternalVirtualPort2
                                            .getExternalMacAddresses().getExternalMacAddress();

                                    for ( MacAddress macAddress : macAddresses ) {
                                        configMacTableEntry(user.getUserId(), macAddress, physicalPath1);
                                    }
                                }
                            }
                        }
                    }

                    layer2ExternalVirtualPort1 = virtualNetworkHelper
                            .getLayer2ExternalVirtualPort(entry.getKey());

                    if ( null != layer2ExternalVirtualPort1 ) {
                        vnPnMappingResult = getVnPnMappingResult(vnPnMappingResults,
                                new VirtualResourceEntityId(layer2ExternalVirtualPort1.getPortId().getValue()));
                        physicalNodeId1 = new PhysicalNodeId(
                                vnPnMappingResult.getParentPhysicalResourceEntityId().getValue());
                        physicalPortId = new PhysicalPortId(
                                vnPnMappingResult.getPhysicalResourceEntityId().getValue());
                        physicalPort = physicalNetworkHelper.getPhysicalPort(physicalNodeId1, physicalPortId);

                        macAddresses = layer2ExternalVirtualPort1
                                .getExternalMacAddresses().getExternalMacAddress();

                        for ( MacAddress macAddress : macAddresses ) {
                            virtualArp = virtualNetworkHelper.getVirtualArp(macAddress);

                            configArpTableEntry(user.getUserId(), virtualArp, physicalPath);
                            configMacTableEntry(user.getUserId(), macAddress, physicalNodeId1, physicalPort);
                        }
                    }
                }
            }

            layer3ExternalVirtualPort = virtualNetworkHelper.getLayer3ExternalVirtualPort(virtualNodeId);

            if ( null != layer3ExternalVirtualPort ) {
                vnPnMappingResult = getVnPnMappingResult(vnPnMappingResults,
                        new VirtualResourceEntityId(layer3ExternalVirtualPort.getPortId().getValue()));
                physicalPortId = new PhysicalPortId(vnPnMappingResult.getPhysicalResourceEntityId().getValue());
                physicalPort = physicalNetworkHelper.getPhysicalPort(physicalNodeId, physicalPortId);

                ipPrefixes = layer3ExternalVirtualPort.getExternalIpPrefixes().getExternalIpPrefix();

                for ( IpPrefix ipPrefix1 : ipPrefixes ) {
                    configIpTableEntry(user.getUserId(), ipPrefix1, physicalNodeId, physicalPort, false);
                }
            }

            layer2ExternalVirtualPort = virtualNetworkHelper.getLayer2ExternalVirtualPort(virtualNodeId);

            if ( null != layer2ExternalVirtualPort ) {
                // TODO: config ip and arp tables.
            }
        }

        updateIpTableForOperations(user, virtualNetwork, userIntentVnMapping, userVnPnMapping, physicalNetwork);

        // for testing - jizhigang
        // log format: vnode(nodetype) --> vnode(nodetype): vlink; ppath; plinks; mplslabels; meterid;
        Set<VirtualLinkId> printedVirtualLinks = new HashSet<VirtualLinkId>();
        VirtualNode srcVirtualNode;
        VirtualNode dstVirtualNode;
        PhysicalPath physicalPath2;

        for ( VirtualLink virtualLink2 : virtualNetwork.getVirtualLinks().getVirtualLink() ) {
            if ( !printedVirtualLinks.contains(virtualLink2.getLinkId()) ) {
                srcVirtualNode = virtualNetworkHelper.virtualNodeMap.get(virtualLink2.getSrcNodeId());
                dstVirtualNode = virtualNetworkHelper.virtualNodeMap.get(virtualLink2.getDestNodeId());
                vnPnMappingResult = getVnPnMappingResult(vnPnMappingResults, new VirtualResourceEntityId(virtualLink2.getLinkId().getValue()));
                physicalPath2 = physicalNetworkHelper.getPhysicalPath(new PhysicalPathId(vnPnMappingResult.getPhysicalResourceEntityId().getValue()));

                System.out.println(srcVirtualNode.getNodeId().getValue() + "(" + srcVirtualNode.getNodeType() + ") --> " +
                        dstVirtualNode.getNodeId().getValue() + "(" + dstVirtualNode.getNodeType() + "): " + virtualLink2.getLinkId().getValue() + "; " +
                        physicalPath2.getPathId().getValue() + "; " + physicalPath2.getPhysicalLink() + "; " +
                        mplsLabelsOfPhysicalPaths.get(physicalPath2.getPathId()) + "; " +
                        meterIdsOfPhysicalPaths.get(physicalPath2.getPathId()) + ";");

                for ( VirtualLink virtualLink3 : virtualNetwork.getVirtualLinks().getVirtualLink() ) {
                    if ( virtualLink3.getSrcNodeId().equals(dstVirtualNode.getNodeId())
                            && virtualLink3.getDestNodeId().equals(srcVirtualNode.getNodeId()) ) {
                        vnPnMappingResult = getVnPnMappingResult(vnPnMappingResults, new VirtualResourceEntityId(virtualLink3.getLinkId().getValue()));
                        physicalPath2 = physicalNetworkHelper.getPhysicalPath(new PhysicalPathId(vnPnMappingResult.getPhysicalResourceEntityId().getValue()));

                        System.out.println(dstVirtualNode.getNodeId().getValue() + "(" + dstVirtualNode.getNodeType() + ") --> " +
                                srcVirtualNode.getNodeId().getValue() + "(" + srcVirtualNode.getNodeType() + "): " + virtualLink3.getLinkId().getValue() + "; " +
                                physicalPath2.getPathId().getValue() + "; " + physicalPath2.getPhysicalLink() + "; " +
                                mplsLabelsOfPhysicalPaths.get(physicalPath2.getPathId()) + "; " +
                                meterIdsOfPhysicalPaths.get(physicalPath2.getPathId()) + ";");

                        printedVirtualLinks.add(virtualLink3.getLinkId());
                    }
                }

                printedVirtualLinks.add(virtualLink2.getLinkId());
            }
        }
        // for testing - jizhigang

        return;
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param user TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     * @param userVnPnMapping TODO
     * @param physicalNetwork TODO
     */
    private void updateArpTable(User user,
                                VirtualNetwork virtualNetwork,
                                UserIntentVnMapping userIntentVnMapping,
                                UserVnPnMapping userVnPnMapping,
                                PhysicalNetwork physicalNetwork) {
        // TODO
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param userId TODO
     */
    private void deleteFlowTableEntries(UserId userId) {
        WriteTransaction writeTransaction = dataBroker.newWriteOnlyTransaction();

        for ( InstanceIdentifier<Flow> flowIid : flowIdsOfUsers.get(userId) ) {
            writeTransaction.delete(LogicalDatastoreType.CONFIGURATION, flowIid);
        }

        writeTransaction.submit();

        return;
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param userId TODO
     */
    private void deleteMeterTableEntries(UserId userId) {
        WriteTransaction writeTransaction = dataBroker.newWriteOnlyTransaction();

        for ( InstanceIdentifier<Meter> meterIid : meterIdIdsOfUsers.get(userId) ) {
            writeTransaction.delete(LogicalDatastoreType.CONFIGURATION, meterIid);
        }

        writeTransaction.submit();

        return;
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param user TODO
     * @param virtualNetwork TODO
     * @param userIntentVnMapping TODO
     * @param userVnPnMapping TODO
     * @param physicalNetwork TODO
     */
    private void updateIpTableForOperations(User user,
                                            VirtualNetwork virtualNetwork,
                                            UserIntentVnMapping userIntentVnMapping,
                                            UserVnPnMapping userVnPnMapping,
                                            PhysicalNetwork physicalNetwork) {
        if ( null == user.getOperations() ) {
            return;
        }

        List<Operation> operations = user.getOperations().getOperation();

        if ( null == operations || operations.isEmpty() ) {
            return;
        }

        Operation operation = operations.get(0);

        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow> nemoFlows =
                user.getObjects().getFlow();
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowId nemoFlowId =
                new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowId(operation.getTargetObject().getValue());
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow nemoFlow =
                getFlow(nemoFlows, nemoFlowId);

        long priority = 1 + operation.getPriority();

        List<IntentVnMappingResult> intentVnMappingResults = userIntentVnMapping.getIntentVnMappingResult();
        List<VnPnMappingResult> vnPnMappingResults = userVnPnMapping.getVnPnMappingResult();
        IntentId intentId = new IntentId(operation.getOperationId().getValue());
        IntentVnMappingResult intentVnMappingResult = getIntentVnMappingResult(intentVnMappingResults, intentId);
        List<VirtualResource> virtualResources = sortVirtualResources(intentVnMappingResult.getVirtualResource());
        Iterator<VirtualResource> iterator = virtualResources.iterator();
        VirtualResource virtualResource;
        VirtualResource virtualResource1 = null;
        VirtualPathId virtualPathId;
        VirtualPath virtualPath;
        VirtualLinkId virtualLinkId;
        VirtualLink virtualLink;
        VirtualNodeId virtualNodeId;
        VnPnMappingResult vnPnMappingResult;

        Map<VirtualNodeId, Map.Entry<VirtualPort, VirtualLink>> connectedVirtualSwitches;
        VirtualPort layer2ExternalVirtualPort;
        VirtualPort layer3ExternalVirtualPort;

        PhysicalPathId physicalPathId;
        PhysicalPathId physicalPathId1;
        PhysicalPath physicalPath;
        PhysicalPath physicalPath1;
        PhysicalNodeId physicalNodeId;
        PhysicalNodeId physicalNodeId1;
        PhysicalPortId physicalPortId;
        PhysicalPortId physicalPortId1;
        PhysicalLink physicalLink;
        PhysicalPort physicalPort;
        PhysicalPort physicalPort1;

        while ( iterator.hasNext() ) {
            virtualResource = iterator.next();

            if ( null == virtualResource1 ) {
                if ( VirtualResource.VirtualResourceType.Vpath
                        == virtualResource.getVirtualResourceType() ) {
                    virtualPathId = new VirtualPathId(virtualResource.getVirtualResourceEntityId().getValue());
                    virtualPath = virtualNetworkHelper.getVirtualPath(virtualPathId);
                    virtualLinkId = virtualPath.getVirtualLink().get(0).getLinkId();
                    vnPnMappingResult = getVnPnMappingResult(vnPnMappingResults,
                            new VirtualResourceEntityId(virtualLinkId.getValue()));
                    physicalPathId = new PhysicalPathId(vnPnMappingResult.getPhysicalResourceEntityId().getValue());
                    physicalPath = physicalNetworkHelper.getPhysicalPath(physicalPathId);

                    if ( physicalPath.getPhysicalLink().isEmpty() ) {
                        continue;
                    } else {
                        virtualLink = virtualNetworkHelper.getVirtualLink(virtualLinkId);
                        connectedVirtualSwitches = virtualNetworkHelper
                                .getConnectedVirtualSwitches(virtualLink.getSrcNodeId());

                        if ( null != connectedVirtualSwitches ) {
                            for ( Map.Entry<VirtualPort, VirtualLink> entry : connectedVirtualSwitches.values() ) {
                                vnPnMappingResult = getVnPnMappingResult(vnPnMappingResults,
                                        new VirtualResourceEntityId(entry.getValue().getLinkId().getValue()));
                                physicalPathId1 = new PhysicalPathId(
                                        vnPnMappingResult.getPhysicalResourceEntityId().getValue());
                                physicalPath1 = physicalNetworkHelper.getPhysicalPath(physicalPathId1);
                                physicalLink = physicalNetworkHelper
                                        .getFirstPhysicalLinkOfPhysicalPath(physicalPath1);
                                physicalPort = physicalNetworkHelper
                                        .getPhysicalPort(physicalLink.getSrcNodeId(), physicalLink.getSrcPortId());

                                configIpTableEntryForOperation(user.getUserId(), nemoFlow,
                                        physicalPort, physicalPath, (short)priority, true);
                            }
                        }

                        layer3ExternalVirtualPort = virtualNetworkHelper
                                .getLayer3ExternalVirtualPort(virtualLink.getSrcNodeId());

                        if ( null != layer3ExternalVirtualPort ) {
                            vnPnMappingResult = getVnPnMappingResult(vnPnMappingResults,
                                    new VirtualResourceEntityId(layer3ExternalVirtualPort.getPortId().getValue()));
                            physicalNodeId = new PhysicalNodeId(
                                    vnPnMappingResult.getParentPhysicalResourceEntityId().getValue());
                            physicalPortId = new PhysicalPortId(
                                    vnPnMappingResult.getPhysicalResourceEntityId().getValue());
                            physicalPort = physicalNetworkHelper.getPhysicalPort(physicalNodeId, physicalPortId);

                            configIpTableEntryForOperation(user.getUserId(), nemoFlow,
                                    physicalPort, physicalPath, (short)priority, true);
                        }

                        layer2ExternalVirtualPort = virtualNetworkHelper
                                .getLayer2ExternalVirtualPort(virtualLink.getSrcNodeId());

                        if ( null != layer2ExternalVirtualPort ) {
                            vnPnMappingResult = getVnPnMappingResult(vnPnMappingResults,
                                    new VirtualResourceEntityId(layer2ExternalVirtualPort.getPortId().getValue()));
                            physicalNodeId = new PhysicalNodeId(
                                    vnPnMappingResult.getParentPhysicalResourceEntityId().getValue());
                            physicalPortId = new PhysicalPortId(
                                    vnPnMappingResult.getPhysicalResourceEntityId().getValue());
                            physicalPort = physicalNetworkHelper.getPhysicalPort(physicalNodeId, physicalPortId);

                            configIpTableEntryForOperation(user.getUserId(), nemoFlow,
                                    physicalPort, physicalPath, (short)priority, true);
                        }
                    }
                } else if ( VirtualResource.VirtualResourceType.Vport
                        == virtualResource.getVirtualResourceType() ) {
                    virtualNodeId = new VirtualNodeId(virtualResource.getParentVirtualResourceEntityId().getValue());
                    vnPnMappingResult = getVnPnMappingResult(vnPnMappingResults,
                            new VirtualResourceEntityId(virtualResource.getVirtualResourceEntityId().getValue()));
                    physicalNodeId = new PhysicalNodeId(
                            vnPnMappingResult.getParentPhysicalResourceEntityId().getValue());
                    physicalPortId = new PhysicalPortId(vnPnMappingResult.getPhysicalResourceEntityId().getValue());
                    physicalPort = physicalNetworkHelper.getPhysicalPort(physicalNodeId, physicalPortId);

                    connectedVirtualSwitches = virtualNetworkHelper.getConnectedVirtualSwitches(virtualNodeId);

                    if ( null != connectedVirtualSwitches ) {
                        for ( Map.Entry<VirtualPort, VirtualLink> entry : connectedVirtualSwitches.values() ) {
                            vnPnMappingResult = getVnPnMappingResult(vnPnMappingResults,
                                    new VirtualResourceEntityId(entry.getValue().getLinkId().getValue()));
                            physicalPathId = new PhysicalPathId(
                                    vnPnMappingResult.getPhysicalResourceEntityId().getValue());
                            physicalPath = physicalNetworkHelper.getPhysicalPath(physicalPathId);
                            physicalLink = physicalNetworkHelper
                                    .getFirstPhysicalLinkOfPhysicalPath(physicalPath);
                            physicalPort1 = physicalNetworkHelper
                                    .getPhysicalPort(physicalLink.getSrcNodeId(), physicalLink.getSrcPortId());

                            configIpTableEntryForOperation(user.getUserId(), nemoFlow,
                                    physicalNodeId, physicalPort1, physicalPort, (short)priority, true);
                        }
                    }

                    layer3ExternalVirtualPort = virtualNetworkHelper.getLayer3ExternalVirtualPort(virtualNodeId);

                    if ( null != layer3ExternalVirtualPort ) {
                        vnPnMappingResult = getVnPnMappingResult(vnPnMappingResults,
                                new VirtualResourceEntityId(layer3ExternalVirtualPort.getPortId().getValue()));
                        physicalNodeId1 = new PhysicalNodeId(
                                vnPnMappingResult.getParentPhysicalResourceEntityId().getValue());
                        physicalPortId1 = new PhysicalPortId(
                                vnPnMappingResult.getPhysicalResourceEntityId().getValue());
                        physicalPort1 = physicalNetworkHelper.getPhysicalPort(physicalNodeId1, physicalPortId1);

                        configIpTableEntryForOperation(user.getUserId(), nemoFlow,
                                physicalNodeId1, physicalPort1, physicalPort, (short)priority, true);
                    }

                    layer2ExternalVirtualPort = virtualNetworkHelper.getLayer2ExternalVirtualPort(virtualNodeId);

                    if ( null != layer2ExternalVirtualPort ) {
                        vnPnMappingResult = getVnPnMappingResult(vnPnMappingResults,
                                new VirtualResourceEntityId(layer2ExternalVirtualPort.getPortId().getValue()));
                        physicalNodeId1 = new PhysicalNodeId(
                                vnPnMappingResult.getParentPhysicalResourceEntityId().getValue());
                        physicalPortId1 = new PhysicalPortId(
                                vnPnMappingResult.getPhysicalResourceEntityId().getValue());
                        physicalPort1 = physicalNetworkHelper.getPhysicalPort(physicalNodeId1, physicalPortId1);

                        configIpTableEntryForOperation(user.getUserId(), nemoFlow,
                                physicalNodeId1, physicalPort1, physicalPort, (short)priority, true);
                    }
                }
            } else {
                if ( VirtualResource.VirtualResourceType.Vport == virtualResource1.getVirtualResourceType()
                        && VirtualResource.VirtualResourceType.Vpath == virtualResource.getVirtualResourceType() ) {
                    virtualPathId = new VirtualPathId(virtualResource.getVirtualResourceEntityId().getValue());
                    virtualPath = virtualNetworkHelper.getVirtualPath(virtualPathId);
                    virtualLinkId = virtualPath.getVirtualLink().get(0).getLinkId();
                    vnPnMappingResult = getVnPnMappingResult(vnPnMappingResults,
                            new VirtualResourceEntityId(virtualLinkId.getValue()));
                    physicalPathId = new PhysicalPathId(vnPnMappingResult.getPhysicalResourceEntityId().getValue());
                    physicalPath = physicalNetworkHelper.getPhysicalPath(physicalPathId);

                    if ( physicalPath.getPhysicalLink().isEmpty() ) {
                        continue;
                    } else {
                        vnPnMappingResult = getVnPnMappingResult(vnPnMappingResults,
                                new VirtualResourceEntityId(virtualResource1.getVirtualResourceEntityId().getValue()));
                        physicalNodeId = new PhysicalNodeId(
                                vnPnMappingResult.getParentPhysicalResourceEntityId().getValue());
                        physicalPortId = new PhysicalPortId(
                                vnPnMappingResult.getPhysicalResourceEntityId().getValue());
                        physicalPort = physicalNetworkHelper.getPhysicalPort(physicalNodeId, physicalPortId);

                        configIpTableEntryForOperation(user.getUserId(), nemoFlow,
                                physicalPort, physicalPath, (short)priority, true);
                    }
                } else if ( VirtualResource.VirtualResourceType.Vpath == virtualResource1.getVirtualResourceType()
                        && VirtualResource.VirtualResourceType.Vport == virtualResource.getVirtualResourceType() ) {
                    virtualPathId = new VirtualPathId(virtualResource1.getVirtualResourceEntityId().getValue());
                    virtualPath = virtualNetworkHelper.getVirtualPath(virtualPathId);
                    virtualLinkId = virtualPath.getVirtualLink().get(0).getLinkId();
                    vnPnMappingResult = getVnPnMappingResult(vnPnMappingResults,
                            new VirtualResourceEntityId(virtualLinkId.getValue()));
                    physicalPathId = new PhysicalPathId(vnPnMappingResult.getPhysicalResourceEntityId().getValue());
                    physicalPath = physicalNetworkHelper.getPhysicalPath(physicalPathId);
                    physicalLink = physicalNetworkHelper.getLastPhysicalLinkOfPhysicalPath(physicalPath);
                    physicalPort1 = physicalNetworkHelper
                            .getPhysicalPort(physicalLink.getDestNodeId(), physicalLink.getDestPortId());

                    vnPnMappingResult = getVnPnMappingResult(vnPnMappingResults,
                            new VirtualResourceEntityId(virtualResource.getVirtualResourceEntityId().getValue()));
                    physicalNodeId = new PhysicalNodeId(
                            vnPnMappingResult.getParentPhysicalResourceEntityId().getValue());
                    physicalPortId = new PhysicalPortId(
                            vnPnMappingResult.getPhysicalResourceEntityId().getValue());
                    physicalPort = physicalNetworkHelper.getPhysicalPort(physicalNodeId, physicalPortId);

                    configIpTableEntryForOperation(user.getUserId(), nemoFlow,
                            physicalNodeId, physicalPort1, physicalPort, (short)priority, true);
                } else if ( VirtualResource.VirtualResourceType.Vport == virtualResource1.getVirtualResourceType()
                        && VirtualResource.VirtualResourceType.Vport == virtualResource.getVirtualResourceType() ) {
                    vnPnMappingResult = getVnPnMappingResult(vnPnMappingResults,
                            new VirtualResourceEntityId(virtualResource1.getVirtualResourceEntityId().getValue()));
                    physicalNodeId1 = new PhysicalNodeId(
                            vnPnMappingResult.getParentPhysicalResourceEntityId().getValue());
                    physicalPortId1 = new PhysicalPortId(
                            vnPnMappingResult.getPhysicalResourceEntityId().getValue());
                    physicalPort1 = physicalNetworkHelper.getPhysicalPort(physicalNodeId1, physicalPortId1);

                    vnPnMappingResult = getVnPnMappingResult(vnPnMappingResults,
                            new VirtualResourceEntityId(virtualResource.getVirtualResourceEntityId().getValue()));
                    physicalNodeId = new PhysicalNodeId(
                            vnPnMappingResult.getParentPhysicalResourceEntityId().getValue());
                    physicalPortId = new PhysicalPortId(
                            vnPnMappingResult.getPhysicalResourceEntityId().getValue());
                    physicalPort = physicalNetworkHelper.getPhysicalPort(physicalNodeId, physicalPortId);

                    configIpTableEntryForOperation(user.getUserId(), nemoFlow,
                            physicalNodeId1, physicalPort1, physicalPort, (short)priority, true);
                }
            }

            virtualResource1 = virtualResource;
        }

        return;
    }

    /**
     * TODO
     *
     * @author Zhigang Ji, Shixing Liu
     * @param physicalNodeId TODO
     * @param physicalPortId TODO
     * @return TODO
     */
    private MacAddress getMacAddressOfConnectedExternalDevice(PhysicalNodeId physicalNodeId,
                                                              PhysicalPortId physicalPortId) {
        com.google.common.collect.Table<PhysicalNodeId, PhysicalPortId, MacAddress> externalNetworkMacTable =
                this.resourceManager.getExternalNetworkMacTable();

        MacAddress macAddress = externalNetworkMacTable.get(physicalNodeId, physicalPortId);
        if(macAddress == null){
            LOG.info("nemo: cannot find external network device mac address");
        }
        LOG.info("nemo: external network device mac address");

        return macAddress;
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param userIntentVnMapping TODO
     * @param virtualNodeId TODO
     * @return TODO
     */
    private MacAddress getGateWayMacAddress(UserIntentVnMapping userIntentVnMapping,
                                            VirtualNodeId virtualNodeId) {
        List<IntentVnMappingResult> intentVnMappingResults = userIntentVnMapping.getIntentVnMappingResult();
        VirtualResource virtualResource;

        for ( IntentVnMappingResult intentVnMappingResult : intentVnMappingResults ) {
            virtualResource = intentVnMappingResult.getVirtualResource().get(0);

            if ( virtualResource.getVirtualResourceEntityId().getValue().equals(virtualNodeId.getValue()) ) {
                org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId nodeId =
                        new org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId(intentVnMappingResult.getIntentId().getValue());

                return gatewayMacAddress.get(nodeId);
            }
        }

        return null;
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param flows TODO
     * @param flowId TODO
     * @return TODO
     */
    private org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow getFlow(
            List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow> flows,
            org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowId flowId) {
        for ( org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow
                flow : flows ) {
            if ( flow.getFlowId().equals(flowId) ) {
                return flow;
            }
        }

        return null;
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param matchItems TODO
     * @param matchItemName TODO
     * @return TODO
     */
    private MatchItem getMatchItem(List<MatchItem> matchItems, MatchItemName matchItemName) {
        for ( MatchItem matchItem : matchItems ) {
            if ( matchItem.getMatchItemName().equals(matchItemName) ) {
                return matchItem;
            }
        }

        return null;
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param intentVnMappingResults TODO
     * @param intentId TODO
     * @return TODO
     */
    private IntentVnMappingResult getIntentVnMappingResult(
            List<IntentVnMappingResult> intentVnMappingResults, IntentId intentId) {
        for ( IntentVnMappingResult intentVnMappingResult : intentVnMappingResults ) {
            if ( intentVnMappingResult.getIntentId().equals(intentId) ) {
                return intentVnMappingResult;
            }
        }

        return null;
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param vnPnMappingResults TODO
     * @param virtualResourceEntityId TODO
     * @return TODO
     */
    private VnPnMappingResult getVnPnMappingResult(List<VnPnMappingResult> vnPnMappingResults,
                                                   VirtualResourceEntityId virtualResourceEntityId) {
        for ( VnPnMappingResult vnPnMappingResult : vnPnMappingResults ) {
            if ( vnPnMappingResult.getVirtualResourceEntityId().equals(virtualResourceEntityId) ) {
                return vnPnMappingResult;
            }
        }

        return null;
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param virtualResources TODO
     * @return TODO
     */
    private List<VirtualResource> sortVirtualResources(List<VirtualResource> virtualResources) {
        if ( null == virtualResources || 2 > virtualResources.size() ) {
            return virtualResources;
        }

        List<VirtualResource> sortedVirtualResources =
                new ArrayList<VirtualResource>(virtualResources.size());
        sortedVirtualResources.addAll(virtualResources);

        for ( VirtualResource virtualResource : virtualResources ) {
            sortedVirtualResources.set(virtualResource.getOrder().intValue(), virtualResource);
        }

        return sortedVirtualResources;
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param userId TODO
     * @param macAddress TODO
     * @param physicalNodeId TODO
     * @param physicalPort TODO
     */
    private void configMacTableEntry(UserId userId, MacAddress macAddress,
                                     PhysicalNodeId physicalNodeId, PhysicalPort physicalPort) {
        WriteTransaction writeTransaction = dataBroker.newWriteOnlyTransaction();
        List<Instruction> instructionList = new LinkedList<Instruction>();
        List<Action> actionList = new LinkedList<Action>();

        EthernetMatchBuilder ethernetMatchBuilder = new EthernetMatchBuilder().setEthernetDestination(new EthernetDestinationBuilder().setAddress(macAddress).build());
        EthernetMatch ethernetMatch = ethernetMatchBuilder.build();

        MetadataBuilder metadataBuilder = new MetadataBuilder().setMetadata(BigInteger.valueOf(metadatas.get(userId)));
        Metadata metadata = metadataBuilder.build();

        Match match = new MatchBuilder().setEthernetMatch(ethernetMatch).setMetadata(metadata).build();

        OutputAction outputAction = new OutputActionBuilder().setOutputNodeConnector(createNodeConnectorId(physicalPort.getPortId())).build();
        OutputActionCase outputActionCase = new OutputActionCaseBuilder().setOutputAction(outputAction).build();
        Action actionOutput = new ActionBuilder().setOrder(actionList.size()).setAction(outputActionCase).build();
        actionList.add(actionOutput);

        ApplyActions applyActions = new ApplyActionsBuilder().setAction(actionList).build();
        ApplyActionsCase applyActionsCase = new ApplyActionsCaseBuilder().setApplyActions(applyActions).build();
        Instruction instructionApply = new InstructionBuilder().setOrder(instructionList.size()).setInstruction(applyActionsCase).build();
        instructionList.add(instructionApply);

        Instructions instructions = new InstructionsBuilder().setInstruction(instructionList).build();

        FlowId flowId = new FlowId(UUID.randomUUID().toString());
        FlowBuilder flowBuilder = baseFlowBuilder().setId(flowId).setTableId(MAC_TABLE_ID).setPriority(DEFAULT_FLOW_PRIORITY);
        Flow flow = flowBuilder.setMatch(match).setInstructions(instructions).build();

        NodeId nodeId = createNodeId(physicalNodeId);
        InstanceIdentifier<Flow> flowInsId = generateFlowInsId(userId, nodeId, flow.getTableId(), flow.getId());

        writeTransaction.put(LogicalDatastoreType.CONFIGURATION, flowInsId, flow, true);
        writeTransaction.submit();

        return;
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param userId TODO
     * @param macAddress TODO
     * @param physicalPath TODO
     */
    private void configMacTableEntry(UserId userId, MacAddress macAddress,
                                     PhysicalPath physicalPath) {
        WriteTransaction writeTransaction = dataBroker.newWriteOnlyTransaction();
        List<Instruction> instructionList = new LinkedList<Instruction>();
        List<Action> actionList = new LinkedList<Action>();

        PhysicalLink physicalLink = physicalNetworkHelper.getFirstPhysicalLinkOfPhysicalPath(physicalPath);

        EthernetMatchBuilder ethernetMatchBuilder = new EthernetMatchBuilder().setEthernetDestination(new EthernetDestinationBuilder().setAddress(macAddress).build());
        EthernetMatch ethernetMatch = ethernetMatchBuilder.build();

        MetadataBuilder metadataBuilder = new MetadataBuilder().setMetadata(BigInteger.valueOf(metadatas.get(userId)));
        Metadata metadata = metadataBuilder.build();

        Match match = new MatchBuilder().setEthernetMatch(ethernetMatch).setMetadata(metadata).build();

        PushMplsAction pushMplsAction = new PushMplsActionBuilder().setEthernetType(ETH_TYPE_MPLS).build();
        PushMplsActionCase pushMplsActionCase = new PushMplsActionCaseBuilder().setPushMplsAction(pushMplsAction).build();
        Action actionPushMPLS = new ActionBuilder().setOrder(actionList.size()).setAction(pushMplsActionCase).build();
        actionList.add(actionPushMPLS);

        SetField setField = new SetFieldBuilder().setProtocolMatchFields(new ProtocolMatchFieldsBuilder().setMplsLabel((long)mplsLabelsOfPhysicalPaths.get(physicalPath.getPathId()).get(0)).build()).build();
        SetFieldCase setFieldCase = new SetFieldCaseBuilder().setSetField(setField).build();
        Action actionSetField = new ActionBuilder().setOrder(actionList.size()).setAction(setFieldCase).build();
        actionList.add(actionSetField);

        OutputAction outputAction = new OutputActionBuilder().setOutputNodeConnector(createNodeConnectorId(physicalLink.getSrcPortId())).build();
        OutputActionCase outputActionCase = new OutputActionCaseBuilder().setOutputAction(outputAction).build();
        Action actionOutput = new ActionBuilder().setOrder(actionList.size()).setAction(outputActionCase).build();
        actionList.add(actionOutput);

        ApplyActions applyActions = new ApplyActionsBuilder().setAction(actionList).build();
        ApplyActionsCase applyActionsCase = new ApplyActionsCaseBuilder().setApplyActions(applyActions).build();
        Instruction instructionApply = new InstructionBuilder().setOrder(instructionList.size()).setInstruction(applyActionsCase).build();
        instructionList.add(instructionApply);

        Instructions instructions = new InstructionsBuilder().setInstruction(instructionList).build();

        FlowId flowId = new FlowId(UUID.randomUUID().toString());
        FlowBuilder flowBuilder = baseFlowBuilder().setId(flowId).setTableId(MAC_TABLE_ID).setPriority(DEFAULT_FLOW_PRIORITY);
        Flow flow = flowBuilder.setMatch(match).setInstructions(instructions).build();

        NodeId nodeId = createNodeId(physicalLink.getSrcNodeId());
        InstanceIdentifier<Flow> flowInsId = generateFlowInsId(userId, nodeId, flow.getTableId(), flow.getId());

        writeTransaction.put(LogicalDatastoreType.CONFIGURATION, flowInsId, flow, true);
        writeTransaction.submit();

        return;
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param userId TODO
     * @param ipPrefix TODO
     * @param physicalNodeId TODO
     * @param physicalPort TODO
     * @param goToArpTable TODO
     */
    private void configIpTableEntry(UserId userId, IpPrefix ipPrefix, PhysicalNodeId physicalNodeId,
                                    PhysicalPort physicalPort, boolean goToArpTable) {
        WriteTransaction writeTransaction = dataBroker.newWriteOnlyTransaction();
        List<Instruction> instructionList = new LinkedList<Instruction>();
        List<Action> actionList = new LinkedList<Action>();

        EthernetMatchBuilder ethernetMatchBuilder = new EthernetMatchBuilder().setEthernetType(new EthernetTypeBuilder().setType(new EtherType((long)ETH_TYPE_IP)).build());
        EthernetMatch ethernetMatch = ethernetMatchBuilder.build();

        MetadataBuilder metadataBuilder = new MetadataBuilder().setMetadata(BigInteger.valueOf(metadatas.get(userId)));
        Metadata metadata = metadataBuilder.build();

        Ipv4MatchBuilder ipv4MatchBuilder = new Ipv4MatchBuilder().setIpv4Destination(ipPrefix.getIpv4Prefix());
        Ipv4Match ipv4Match = ipv4MatchBuilder.build();

        Match match = new MatchBuilder().setEthernetMatch(ethernetMatch).setMetadata(metadata).setLayer3Match(ipv4Match).build();

        DecNwTtl decNwTtl = new DecNwTtlBuilder().build();
        DecNwTtlCase decNwTtlCase = new DecNwTtlCaseBuilder().setDecNwTtl(decNwTtl).build();
        Action actionDecNW = new ActionBuilder().setOrder(actionList.size()).setAction(decNwTtlCase).build();
        actionList.add(actionDecNW);

        ethernetMatchBuilder = new EthernetMatchBuilder().setEthernetSource(new EthernetSourceBuilder().setAddress(physicalPort.getMacAddress()).build());
        ethernetMatch = ethernetMatchBuilder.build();

        SetField setField = new SetFieldBuilder().setEthernetMatch(ethernetMatch).build();
        SetFieldCase setFieldCase = new SetFieldCaseBuilder().setSetField(setField).build();
        Action actionSetField = new ActionBuilder().setOrder(actionList.size()).setAction(setFieldCase).build();
        actionList.add(actionSetField);

        if ( goToArpTable )
        {
            ApplyActions applyActions = new ApplyActionsBuilder().setAction(actionList).build();
            ApplyActionsCase applyActionsCase = new ApplyActionsCaseBuilder().setApplyActions(applyActions).build();
            Instruction instructionApply = new InstructionBuilder().setOrder(instructionList.size()).setInstruction(applyActionsCase).build();
            instructionList.add(instructionApply);

            WriteMetadata writeMetadata = new WriteMetadataBuilder().setMetadata(BigInteger.valueOf(metadatas.get(userId))).setMetadataMask(new BigInteger(DEFAULT_METADATA_MASK, 16)).build();
            WriteMetadataCase writeMetadataCase = new WriteMetadataCaseBuilder().setWriteMetadata(writeMetadata).build();
            Instruction instructionMeta = new InstructionBuilder().setOrder(instructionList.size()).setInstruction(writeMetadataCase).build();
            instructionList.add(instructionMeta);

            GoToTable gotoTable = new GoToTableBuilder().setTableId(ARP_TABLE_ID).build();
            GoToTableCase gotoTableCase = new GoToTableCaseBuilder().setGoToTable(gotoTable).build();
            Instruction instructionGoto = new InstructionBuilder().setOrder(instructionList.size()).setInstruction(gotoTableCase).build();
            instructionList.add(instructionGoto);
        }
        else
        {
            ethernetMatchBuilder = new EthernetMatchBuilder().setEthernetDestination(new EthernetDestinationBuilder().setAddress(getMacAddressOfConnectedExternalDevice(physicalNodeId, physicalPort.getPortId())).build());
            ethernetMatch = ethernetMatchBuilder.build();

            setField = new SetFieldBuilder().setEthernetMatch(ethernetMatch).build();
            setFieldCase = new SetFieldCaseBuilder().setSetField(setField).build();
            actionSetField = new ActionBuilder().setOrder(actionList.size()).setAction(setFieldCase).build();
            actionList.add(actionSetField);

            OutputAction outputAction = new OutputActionBuilder().setOutputNodeConnector(createNodeConnectorId(physicalPort.getPortId())).build();
            OutputActionCase outputActionCase = new OutputActionCaseBuilder().setOutputAction(outputAction).build();
            Action actionOutput = new ActionBuilder().setOrder(actionList.size()).setAction(outputActionCase).build();
            actionList.add(actionOutput);

            ApplyActions applyActions = new ApplyActionsBuilder().setAction(actionList).build();
            ApplyActionsCase applyActionsCase = new ApplyActionsCaseBuilder().setApplyActions(applyActions).build();
            Instruction instructionApply = new InstructionBuilder().setOrder(instructionList.size()).setInstruction(applyActionsCase).build();
            instructionList.add(instructionApply);
        }

        Instructions instructions = new InstructionsBuilder().setInstruction(instructionList).build();

        FlowId flowId = new FlowId(UUID.randomUUID().toString());
        FlowBuilder flowBuilder = baseFlowBuilder().setId(flowId).setTableId(IP_TABLE_ID).setPriority(DEFAULT_FLOW_PRIORITY);
        Flow flow = flowBuilder.setMatch(match).setInstructions(instructions).build();

        NodeId nodeId = createNodeId(physicalNodeId);
        InstanceIdentifier<Flow> flowInsId = generateFlowInsId(userId, nodeId, flow.getTableId(), flow.getId());

        writeTransaction.put(LogicalDatastoreType.CONFIGURATION, flowInsId, flow, true);
        writeTransaction.submit();

        return;
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param userId TODO
     * @param ipPrefix TODO
     * @param physicalPath TODO
     * @param goToArpTable TODO
     */
    private void configIpTableEntry(UserId userId, IpPrefix ipPrefix,
                                    PhysicalPath physicalPath, boolean goToArpTable) {
        WriteTransaction writeTransaction = dataBroker.newWriteOnlyTransaction();
        List<Instruction> instructionList = new LinkedList<Instruction>();
        List<Action> actionList = new LinkedList<Action>();

        PhysicalLink physicalLink = physicalNetworkHelper.getFirstPhysicalLinkOfPhysicalPath(physicalPath);

        EthernetMatchBuilder ethernetMatchBuilder = new EthernetMatchBuilder().setEthernetType(new EthernetTypeBuilder().setType(new EtherType((long)ETH_TYPE_IP)).build());
        EthernetMatch ethernetMatch = ethernetMatchBuilder.build();

        MetadataBuilder metadataBuilder = new MetadataBuilder().setMetadata(BigInteger.valueOf(metadatas.get(userId)));
        Metadata metadata = metadataBuilder.build();

        Ipv4MatchBuilder ipv4MatchBuilder = new Ipv4MatchBuilder().setIpv4Destination(ipPrefix.getIpv4Prefix());
        Ipv4Match ipv4Match = ipv4MatchBuilder.build();

        Match match = new MatchBuilder().setEthernetMatch(ethernetMatch).setMetadata(metadata).setLayer3Match(ipv4Match).build();

        if ( 0 < physicalPath.getBandwidth() ) {
            org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.meter._case.Meter
               meter = new org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.meter._case.MeterBuilder()
                    .setMeterId(new MeterId(meterIdsOfPhysicalPaths.get(physicalPath.getPathId()))).build();
            MeterCase meterCase = new MeterCaseBuilder().setMeter(meter).build();
            Instruction instructionMeter = new InstructionBuilder().setOrder(instructionList.size()).setInstruction(meterCase).build();
            instructionList.add(instructionMeter);
        }

        DecNwTtl decNwTtl = new DecNwTtlBuilder().build();
        DecNwTtlCase decNwTtlCase = new DecNwTtlCaseBuilder().setDecNwTtl(decNwTtl).build();
        Action actionDecNW = new ActionBuilder().setOrder(actionList.size()).setAction(decNwTtlCase).build();
        actionList.add(actionDecNW);

        if ( goToArpTable )
        {
            PhysicalPort physicalPort = physicalNetworkHelper.getPhysicalPort(physicalLink.getSrcNodeId(), physicalLink.getSrcPortId());

            ethernetMatchBuilder = new EthernetMatchBuilder().setEthernetSource(new EthernetSourceBuilder().setAddress(physicalPort.getMacAddress()).build());
            ethernetMatch = ethernetMatchBuilder.build();

            SetField setField = new SetFieldBuilder().setEthernetMatch(ethernetMatch).build();
            SetFieldCase setFieldCase = new SetFieldCaseBuilder().setSetField(setField).build();
            Action actionSetField = new ActionBuilder().setOrder(actionList.size()).setAction(setFieldCase).build();
            actionList.add(actionSetField);

            ApplyActions applyActions = new ApplyActionsBuilder().setAction(actionList).build();
            ApplyActionsCase applyActionsCase = new ApplyActionsCaseBuilder().setApplyActions(applyActions).build();
            Instruction instructionApply = new InstructionBuilder().setOrder(instructionList.size()).setInstruction(applyActionsCase).build();
            instructionList.add(instructionApply);

            WriteMetadata writeMetadata = new WriteMetadataBuilder().setMetadata(BigInteger.valueOf(metadatas.get(userId))).setMetadataMask(new BigInteger(DEFAULT_METADATA_MASK, 16)).build();
            WriteMetadataCase writeMetadataCase = new WriteMetadataCaseBuilder().setWriteMetadata(writeMetadata).build();
            Instruction instructionMeta = new InstructionBuilder().setOrder(instructionList.size()).setInstruction(writeMetadataCase).build();
            instructionList.add(instructionMeta);

            GoToTable gotoTable = new GoToTableBuilder().setTableId(ARP_TABLE_ID).build();
            GoToTableCase gotoTableCase = new GoToTableCaseBuilder().setGoToTable(gotoTable).build();
            Instruction instructionGoto = new InstructionBuilder().setOrder(instructionList.size()).setInstruction(gotoTableCase).build();
            instructionList.add(instructionGoto);
        }
        else
        {
            PushMplsAction pushMplsAction = new PushMplsActionBuilder().setEthernetType(ETH_TYPE_MPLS).build();
            PushMplsActionCase pushMplsActionCase = new PushMplsActionCaseBuilder().setPushMplsAction(pushMplsAction).build();
            Action actionPushMPLS = new ActionBuilder().setOrder(actionList.size()).setAction(pushMplsActionCase).build();
            actionList.add(actionPushMPLS);

            SetField setField = new SetFieldBuilder().setProtocolMatchFields(new ProtocolMatchFieldsBuilder().setMplsLabel((long)mplsLabelsOfPhysicalPaths.get(physicalPath.getPathId()).get(0)).build()).build();
            SetFieldCase setFieldCase = new SetFieldCaseBuilder().setSetField(setField).build();
            Action actionSetField = new ActionBuilder().setOrder(actionList.size()).setAction(setFieldCase).build();
            actionList.add(actionSetField);

            OutputAction outputAction = new OutputActionBuilder().setOutputNodeConnector(createNodeConnectorId(physicalLink.getSrcPortId())).build();
            OutputActionCase outputActionCase = new OutputActionCaseBuilder().setOutputAction(outputAction).build();
            Action actionOutput = new ActionBuilder().setOrder(actionList.size()).setAction(outputActionCase).build();
            actionList.add(actionOutput);

            ApplyActions applyActions = new ApplyActionsBuilder().setAction(actionList).build();
            ApplyActionsCase applyActionsCase = new ApplyActionsCaseBuilder().setApplyActions(applyActions).build();
            Instruction instructionApply = new InstructionBuilder().setOrder(instructionList.size()).setInstruction(applyActionsCase).build();
            instructionList.add(instructionApply);
        }

        Instructions instructions = new InstructionsBuilder().setInstruction(instructionList).build();

        FlowId flowId = new FlowId(UUID.randomUUID().toString());
        FlowBuilder flowBuilder = baseFlowBuilder().setId(flowId).setTableId(IP_TABLE_ID).setPriority(DEFAULT_FLOW_PRIORITY);
        Flow flow = flowBuilder.setMatch(match).setInstructions(instructions).build();

        NodeId nodeId = createNodeId(physicalLink.getSrcNodeId());
        InstanceIdentifier<Flow> flowInsId = generateFlowInsId(userId, nodeId, flow.getTableId(), flow.getId());

        writeTransaction.put(LogicalDatastoreType.CONFIGURATION, flowInsId, flow, true);
        writeTransaction.submit();

        return;
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param userId TODO
     * @param ipPrefix TODO
     * @param macAddress TODO
     * @param physicalNodeId TODO
     */
    private void configIpTableEntry(UserId userId, IpPrefix ipPrefix,
                                    MacAddress macAddress, PhysicalNodeId physicalNodeId) {
        WriteTransaction writeTransaction = dataBroker.newWriteOnlyTransaction();
        List<Instruction> instructionList = new LinkedList<Instruction>();
        List<Action> actionList = new LinkedList<Action>();

        EthernetMatchBuilder ethernetMatchBuilder = new EthernetMatchBuilder().setEthernetType(new EthernetTypeBuilder().setType(new EtherType((long)ETH_TYPE_IP)).build());
        EthernetMatch ethernetMatch = ethernetMatchBuilder.build();

        MetadataBuilder metadataBuilder = new MetadataBuilder().setMetadata(BigInteger.valueOf(metadatas.get(userId)));
        Metadata metadata = metadataBuilder.build();

        Ipv4MatchBuilder ipv4MatchBuilder = new Ipv4MatchBuilder().setIpv4Destination(ipPrefix.getIpv4Prefix());
        Ipv4Match ipv4Match = ipv4MatchBuilder.build();

        Match match = new MatchBuilder().setEthernetMatch(ethernetMatch).setMetadata(metadata).setLayer3Match(ipv4Match).build();

        DecNwTtl decNwTtl = new DecNwTtlBuilder().build();
        DecNwTtlCase decNwTtlCase = new DecNwTtlCaseBuilder().setDecNwTtl(decNwTtl).build();
        Action actionDecNW = new ActionBuilder().setOrder(actionList.size()).setAction(decNwTtlCase).build();
        actionList.add(actionDecNW);

        ethernetMatchBuilder = new EthernetMatchBuilder().setEthernetSource(new EthernetSourceBuilder().setAddress(macAddress).build());
        ethernetMatch = ethernetMatchBuilder.build();

        SetField setField = new SetFieldBuilder().setEthernetMatch(ethernetMatch).build();
        SetFieldCase setFieldCase = new SetFieldCaseBuilder().setSetField(setField).build();
        Action actionSetField = new ActionBuilder().setOrder(actionList.size()).setAction(setFieldCase).build();
        actionList.add(actionSetField);

        ApplyActions applyActions = new ApplyActionsBuilder().setAction(actionList).build();
        ApplyActionsCase applyActionsCase = new ApplyActionsCaseBuilder().setApplyActions(applyActions).build();
        Instruction instructionApply = new InstructionBuilder().setOrder(instructionList.size()).setInstruction(applyActionsCase).build();
        instructionList.add(instructionApply);

        WriteMetadata writeMetadata = new WriteMetadataBuilder().setMetadata(BigInteger.valueOf(metadatas.get(userId))).setMetadataMask(new BigInteger(DEFAULT_METADATA_MASK, 16)).build();
        WriteMetadataCase writeMetadataCase = new WriteMetadataCaseBuilder().setWriteMetadata(writeMetadata).build();
        Instruction instructionMeta = new InstructionBuilder().setOrder(instructionList.size()).setInstruction(writeMetadataCase).build();
        instructionList.add(instructionMeta);

        GoToTable gotoTable = new GoToTableBuilder().setTableId(ARP_TABLE_ID).build();
        GoToTableCase gotoTableCase = new GoToTableCaseBuilder().setGoToTable(gotoTable).build();
        Instruction instructionGoto = new InstructionBuilder().setOrder(instructionList.size()).setInstruction(gotoTableCase).build();
        instructionList.add(instructionGoto);

        Instructions instructions = new InstructionsBuilder().setInstruction(instructionList).build();

        FlowId flowId = new FlowId(UUID.randomUUID().toString());
        FlowBuilder flowBuilder = baseFlowBuilder().setId(flowId).setTableId(IP_TABLE_ID).setPriority(DEFAULT_FLOW_PRIORITY);
        Flow flow = flowBuilder.setMatch(match).setInstructions(instructions).build();

        NodeId nodeId = createNodeId(physicalNodeId);
        InstanceIdentifier<Flow> flowInsId = generateFlowInsId(userId, nodeId, flow.getTableId(), flow.getId());

        writeTransaction.put(LogicalDatastoreType.CONFIGURATION, flowInsId, flow, true);
        writeTransaction.submit();

        return;
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param userId TODO
     * @param virtualArp TODO
     * @param physicalPath TODO
     */
    private void configArpTableEntry(UserId userId, VirtualArp virtualArp, PhysicalPath physicalPath) {
        WriteTransaction writeTransaction = dataBroker.newWriteOnlyTransaction();
        List<Instruction> instructionList = new LinkedList<Instruction>();
        List<Action> actionList = new LinkedList<Action>();

        PhysicalLink physicalLink = physicalNetworkHelper.getFirstPhysicalLinkOfPhysicalPath(physicalPath);

        EthernetMatchBuilder ethernetMatchBuilder = new EthernetMatchBuilder().setEthernetType(new EthernetTypeBuilder().setType(new EtherType((long)ETH_TYPE_IP)).build());
        EthernetMatch ethernetMatch = ethernetMatchBuilder.build();

        Ipv4MatchBuilder ipv4MatchBuilder = new Ipv4MatchBuilder().setIpv4Destination(convertIpAddressToIpPrefix(virtualArp.getIpAddress()).getIpv4Prefix());
        Ipv4Match ipv4Match = ipv4MatchBuilder.build();

        MetadataBuilder metadataBuilder = new MetadataBuilder().setMetadata(BigInteger.valueOf(metadatas.get(userId)));
        Metadata metadata = metadataBuilder.build();

        Match match = new MatchBuilder().setEthernetMatch(ethernetMatch).setLayer3Match(ipv4Match).setMetadata(metadata).build();

        PushMplsAction pushMplsAction = new PushMplsActionBuilder().setEthernetType(ETH_TYPE_MPLS).build();
        PushMplsActionCase pushMplsActionCase = new PushMplsActionCaseBuilder().setPushMplsAction(pushMplsAction).build();
        Action actionPushMPLS = new ActionBuilder().setOrder(actionList.size()).setAction(pushMplsActionCase).build();
        actionList.add(actionPushMPLS);

        SetField setField = new SetFieldBuilder().setProtocolMatchFields(new ProtocolMatchFieldsBuilder().setMplsLabel((long)mplsLabelsOfPhysicalPaths.get(physicalPath.getPathId()).get(0)).build()).build();
        SetFieldCase setFieldCase = new SetFieldCaseBuilder().setSetField(setField).build();
        Action actionSetField = new ActionBuilder().setOrder(actionList.size()).setAction(setFieldCase).build();
        actionList.add(actionSetField);

        ethernetMatchBuilder = new EthernetMatchBuilder().setEthernetDestination(new EthernetDestinationBuilder().setAddress(virtualArp.getMacAddress()).build());
        ethernetMatch = ethernetMatchBuilder.build();

        setField = new SetFieldBuilder().setEthernetMatch(ethernetMatch).build();
        setFieldCase = new SetFieldCaseBuilder().setSetField(setField).build();
        actionSetField = new ActionBuilder().setOrder(actionList.size()).setAction(setFieldCase).build();
        actionList.add(actionSetField);

        OutputAction outputAction = new OutputActionBuilder().setOutputNodeConnector(createNodeConnectorId(physicalLink.getSrcPortId())).build();
        OutputActionCase outputActionCase = new OutputActionCaseBuilder().setOutputAction(outputAction).build();
        Action actionOutput = new ActionBuilder().setOrder(actionList.size()).setAction(outputActionCase).build();
        actionList.add(actionOutput);

        ApplyActions applyActions = new ApplyActionsBuilder().setAction(actionList).build();
        ApplyActionsCase applyActionsCase = new ApplyActionsCaseBuilder().setApplyActions(applyActions).build();
        Instruction instructionApply = new InstructionBuilder().setOrder(instructionList.size()).setInstruction(applyActionsCase).build();
        instructionList.add(instructionApply);

        Instructions instructions = new InstructionsBuilder().setInstruction(instructionList).build();

        FlowId flowId = new FlowId(UUID.randomUUID().toString());
        FlowBuilder flowBuilder = baseFlowBuilder().setId(flowId).setTableId(ARP_TABLE_ID).setPriority(DEFAULT_FLOW_PRIORITY);
        Flow flow = flowBuilder.setMatch(match).setInstructions(instructions).build();

        NodeId nodeId = createNodeId(physicalLink.getSrcNodeId());
        InstanceIdentifier<Flow> flowInsId = generateFlowInsId(userId, nodeId, flow.getTableId(), flow.getId());

        writeTransaction.put(LogicalDatastoreType.CONFIGURATION, flowInsId, flow, true);
        writeTransaction.submit();

        return;
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param userId TODO
     * @param nemoFlow TODO
     * @param physicalNodeId TODO
     * @param inPhysicalPort TODO
     * @param outPhysicalPort TODO
     * @param policyPriority TODO
     * @param layer3Forwarding TODO
     */
    private void configIpTableEntryForOperation(UserId userId,
                                                org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow nemoFlow,
                                                PhysicalNodeId physicalNodeId,
                                                PhysicalPort inPhysicalPort,
                                                PhysicalPort outPhysicalPort,
                                                short policyPriority,
                                                boolean layer3Forwarding) {
        WriteTransaction writeTransaction = dataBroker.newWriteOnlyTransaction();
        List<Instruction> instructionList = new LinkedList<Instruction>();
        List<Action> actionList = new LinkedList<Action>();

        Match match = createMatch(userId, nemoFlow, inPhysicalPort);

        if ( layer3Forwarding )
        {
            DecNwTtl decNwTtl = new DecNwTtlBuilder().build();
            DecNwTtlCase decNwTtlCase = new DecNwTtlCaseBuilder().setDecNwTtl(decNwTtl).build();
            Action actionDecNW = new ActionBuilder().setOrder(actionList.size()).setAction(decNwTtlCase).build();
            actionList.add(actionDecNW);

            EthernetMatchBuilder ethernetMatchBuilder = new EthernetMatchBuilder().setEthernetSource(new EthernetSourceBuilder().setAddress(outPhysicalPort.getMacAddress()).build());
            EthernetMatch ethernetMatch = ethernetMatchBuilder.build();

            SetField setField = new SetFieldBuilder().setEthernetMatch(ethernetMatch).build();
            SetFieldCase setFieldCase = new SetFieldCaseBuilder().setSetField(setField).build();
            Action actionSetField = new ActionBuilder().setOrder(actionList.size()).setAction(setFieldCase).build();
            actionList.add(actionSetField);
        }

        EthernetMatchBuilder ethernetMatchBuilder = new EthernetMatchBuilder().setEthernetDestination(new EthernetDestinationBuilder().setAddress(getMacAddressOfConnectedExternalDevice(physicalNodeId, outPhysicalPort.getPortId())).build());
        EthernetMatch ethernetMatch = ethernetMatchBuilder.build();

        SetField setField = new SetFieldBuilder().setEthernetMatch(ethernetMatch).build();
        SetFieldCase setFieldCase = new SetFieldCaseBuilder().setSetField(setField).build();
        Action actionSetField = new ActionBuilder().setOrder(actionList.size()).setAction(setFieldCase).build();
        actionList.add(actionSetField);

        OutputAction outputAction = new OutputActionBuilder().setOutputNodeConnector(createNodeConnectorId(outPhysicalPort.getPortId())).build();
        OutputActionCase outputActionCase = new OutputActionCaseBuilder().setOutputAction(outputAction).build();
        Action actionOutput = new ActionBuilder().setOrder(actionList.size()).setAction(outputActionCase).build();
        actionList.add(actionOutput);

        ApplyActions applyActions = new ApplyActionsBuilder().setAction(actionList).build();
        ApplyActionsCase applyActionsCase = new ApplyActionsCaseBuilder().setApplyActions(applyActions).build();
        Instruction instructionApply = new InstructionBuilder().setOrder(instructionList.size()).setInstruction(applyActionsCase).build();
        instructionList.add(instructionApply);

        Instructions instructions = new InstructionsBuilder().setInstruction(instructionList).build();

        FlowId flowId = new FlowId(UUID.randomUUID().toString());
        FlowBuilder flowBuilder = baseFlowBuilder().setId(flowId).setTableId(layer3Forwarding ? IP_TABLE_ID : MAC_TABLE_ID);
        Flow flow = flowBuilder.setPriority(DEFAULT_FLOW_PRIORITY + policyPriority).setMatch(match).setInstructions(instructions).build();

        NodeId nodeId = createNodeId(physicalNodeId);
        InstanceIdentifier<Flow> flowInsId = generateFlowInsId(userId, nodeId, flow.getTableId(), flow.getId());

        writeTransaction.put(LogicalDatastoreType.CONFIGURATION, flowInsId, flow, true);
        writeTransaction.submit();

        return;
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param userId TODO
     * @param nemoFlow TODO
     * @param inPhysicalPort TODO
     * @param outPhysicalPath TODO
     * @param policyPriority TODO
     * @param layer3Forwarding TODO
     */
    private void configIpTableEntryForOperation(UserId userId,
                                                org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow nemoFlow,
                                                PhysicalPort inPhysicalPort,
                                                PhysicalPath outPhysicalPath,
                                                short policyPriority,
                                                boolean layer3Forwarding) {
        WriteTransaction writeTransaction = dataBroker.newWriteOnlyTransaction();
        PhysicalLink physicalLink = physicalNetworkHelper.getFirstPhysicalLinkOfPhysicalPath(outPhysicalPath);
        List<Instruction> instructionList = new LinkedList<Instruction>();
        List<Action> actionList = new LinkedList<Action>();

        Match match = createMatch(userId, nemoFlow, inPhysicalPort);

        if ( layer3Forwarding )
        {
            DecNwTtl decNwTtl = new DecNwTtlBuilder().build();
            DecNwTtlCase decNwTtlCase = new DecNwTtlCaseBuilder().setDecNwTtl(decNwTtl).build();
            Action actionDecNW = new ActionBuilder().setOrder(actionList.size()).setAction(decNwTtlCase).build();
            actionList.add(actionDecNW);
        }

        PushMplsAction pushMplsAction = new PushMplsActionBuilder().setEthernetType(ETH_TYPE_MPLS).build();
        PushMplsActionCase pushMplsActionCase = new PushMplsActionCaseBuilder().setPushMplsAction(pushMplsAction).build();
        Action actionPushMPLS = new ActionBuilder().setOrder(actionList.size()).setAction(pushMplsActionCase).build();
        actionList.add(actionPushMPLS);

        SetField setField = new SetFieldBuilder().setProtocolMatchFields(new ProtocolMatchFieldsBuilder().setMplsLabel((long)mplsLabelsOfPhysicalPaths.get(outPhysicalPath.getPathId()).get(0)).build()).build();
        SetFieldCase setFieldCase = new SetFieldCaseBuilder().setSetField(setField).build();
        Action actionSetField = new ActionBuilder().setOrder(actionList.size()).setAction(setFieldCase).build();
        actionList.add(actionSetField);

        OutputAction outputAction = new OutputActionBuilder().setOutputNodeConnector(createNodeConnectorId(physicalLink.getSrcPortId())).build();
        OutputActionCase outputActionCase = new OutputActionCaseBuilder().setOutputAction(outputAction).build();
        Action actionOutput = new ActionBuilder().setOrder(actionList.size()).setAction(outputActionCase).build();
        actionList.add(actionOutput);

        ApplyActions applyActions = new ApplyActionsBuilder().setAction(actionList).build();
        ApplyActionsCase applyActionsCase = new ApplyActionsCaseBuilder().setApplyActions(applyActions).build();
        Instruction instructionApply = new InstructionBuilder().setOrder(instructionList.size()).setInstruction(applyActionsCase).build();
        instructionList.add(instructionApply);

        Instructions instructions = new InstructionsBuilder().setInstruction(instructionList).build();

        FlowId flowId = new FlowId(UUID.randomUUID().toString());
        FlowBuilder flowBuilder = baseFlowBuilder().setId(flowId).setTableId(layer3Forwarding ? IP_TABLE_ID : MAC_TABLE_ID);
        Flow flow = flowBuilder.setPriority(DEFAULT_FLOW_PRIORITY + policyPriority).setMatch(match).setInstructions(instructions).build();

        NodeId nodeId = createNodeId(physicalLink.getSrcNodeId());
        InstanceIdentifier<Flow> flowInsId = generateFlowInsId(userId, nodeId, flow.getTableId(), flow.getId());

        writeTransaction.put(LogicalDatastoreType.CONFIGURATION, flowInsId, flow, true);
        writeTransaction.submit();

        return;
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param userId TODO
     * @param nemoFlow TODO
     * @param physicalPort TODO
     * @return TODO
     */
    private Match createMatch(UserId userId,
                              org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow nemoFlow,
                              PhysicalPort physicalPort) {
        List<MatchItem> matchItems = nemoFlow.getMatchItem();
        MatchItem srcIpMatchItem = getMatchItem(matchItems, new MatchItemName("src-ip"));
        MatchItem dstIpMatchItem = getMatchItem(matchItems, new MatchItemName("dst-ip"));

        EthernetMatchBuilder ethernetMatchBuilder = new EthernetMatchBuilder();
        Ipv4MatchBuilder ipv4MatchBuilder = new Ipv4MatchBuilder();
        MetadataBuilder metadataBuilder = null;
        boolean containEthernetMatch = false;
        boolean containIpv4Match = false;

        if ( null != userId ) {
            metadataBuilder = new MetadataBuilder().setMetadata(BigInteger.valueOf(metadatas.get(userId)));
        }

        if ( null != srcIpMatchItem || null != dstIpMatchItem ) {
            ethernetMatchBuilder = ethernetMatchBuilder.setEthernetType(new EthernetTypeBuilder().setType(new EtherType((long)ETH_TYPE_IP)).build());
            containEthernetMatch = true;
        }

        if ( null != srcIpMatchItem ) {
            String matchItemValue = srcIpMatchItem.getMatchItemValue().getStringValue();
            ipv4MatchBuilder = ipv4MatchBuilder.setIpv4Source(new Ipv4Prefix(matchItemValue));
            containIpv4Match = true;
        }

        if ( null != dstIpMatchItem ) {
            String matchItemValue = dstIpMatchItem.getMatchItemValue().getStringValue();
            ipv4MatchBuilder = ipv4MatchBuilder.setIpv4Destination(new Ipv4Prefix(matchItemValue));
            containIpv4Match = true;
        }

        MatchBuilder matchBuilder = new MatchBuilder();

        if ( null != physicalPort ) {
            matchBuilder = matchBuilder.setInPort(createNodeConnectorId(physicalPort.getPortId()));
        }

        if ( containEthernetMatch ) {
            matchBuilder = matchBuilder.setEthernetMatch(ethernetMatchBuilder.build());
        }

        if ( null != userId ) {
            matchBuilder = matchBuilder.setMetadata(metadataBuilder.build());
        }

        if ( containIpv4Match ) {
            matchBuilder = matchBuilder.setLayer3Match(ipv4MatchBuilder.build());
        }

        return matchBuilder.build();
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @return TODO
     */
    private FlowBuilder baseFlowBuilder() {
        return new FlowBuilder().setBarrier(false).setHardTimeout(0).setIdleTimeout(0);
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param physicalPortId TODO
     * @return TODO
     */
    private NodeConnectorId createNodeConnectorId(PhysicalPortId physicalPortId) {
        return new NodeConnectorId(physicalPortId.getValue());
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param nodeId TODO
     * @param nodeConnectorId TODO
     * @return TODO
     */
    private InstanceIdentifier<NodeConnector> createNodeConnectorPath(NodeId nodeId, NodeConnectorId nodeConnectorId) {
        return createNodePath(nodeId).child(NodeConnector.class, new NodeConnectorKey(nodeConnectorId));
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param physicalNodeId TODO
     * @param physicalPortId TODO
     * @return TODO
     */
    private NodeConnectorRef createNodeConnectorRef(PhysicalNodeId physicalNodeId, PhysicalPortId physicalPortId) {
        return new NodeConnectorRef(createNodeConnectorPath(createNodeId(physicalNodeId), createNodeConnectorId(physicalPortId)));
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param physicalNodeId TODO
     * @return TODO
     */
    private NodeId createNodeId(PhysicalNodeId physicalNodeId) {
        return new NodeId(physicalNodeId.getValue());
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param nodeId TODO
     * @return TODO
     */
    private InstanceIdentifier<Node> createNodePath(NodeId nodeId) {
        return InstanceIdentifier.builder(Nodes.class).child(Node.class, new NodeKey(nodeId)).build();
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param physicalNodeId TODO
     * @return TODO
     */
    private NodeRef createNodeRef(PhysicalNodeId physicalNodeId) {
        return new NodeRef(createNodePath(createNodeId(physicalNodeId)));
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param nodeId TODO
     * @param tableId TODO
     * @return TODO
     */
    private InstanceIdentifier<Table> createTablePath(NodeId nodeId, Short tableId) {
        return createNodePath(nodeId).builder().augmentation(FlowCapableNode.class).child(Table.class, new TableKey(tableId)).build();
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param nodeId TODO
     * @param tableId TODO
     * @param flowId TODO
     * @return TODO
     */
    private InstanceIdentifier<Flow> createFlowPath(NodeId nodeId, Short tableId, FlowId flowId) {
        return createTablePath(nodeId, tableId).child(Flow.class, new FlowKey(flowId));
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     * @param ipAddress TODO
     * @return TODO
     */
    private IpPrefix convertIpAddressToIpPrefix(IpAddress ipAddress) {
        return new IpPrefix(new Ipv4Prefix(ipAddress.getIpv4Address().getValue() + "/32"));
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     */
    private class MplsLabelGenerator {
        private int i = 0;

        protected int generateMplsLabel() {
            return ++i;
        }
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     */
    private class MeterIdGenerator {
        private long i = 0;

        protected long generateMeterId() {
            return ++i;
        }
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     */
    private class PhysicalNetworkHelper {
        private Map<PhysicalNodeId, PhysicalNode> physicalNodeMap;
        private Map<PhysicalLinkId, PhysicalLink> physicalLinkMap;
        private Map<PhysicalPathId, PhysicalPath> physicalPathMap;
        private Map<PhysicalNodeId, Map<PhysicalPortId, PhysicalPort>> physicalPortMap;

        public PhysicalNetworkHelper(PhysicalNetwork physicalNetwork) {
            physicalNodeMap = new HashMap<PhysicalNodeId, PhysicalNode>();
            physicalLinkMap = new HashMap<PhysicalLinkId, PhysicalLink>();
            physicalPathMap = new HashMap<PhysicalPathId, PhysicalPath>();
            physicalPortMap = new HashMap<PhysicalNodeId, Map<PhysicalPortId, PhysicalPort>>();

            List<PhysicalNode> physicalNodes = physicalNetwork.getPhysicalNodes().getPhysicalNode();
            Map<PhysicalPortId, PhysicalPort> physicalPorts;

            for ( PhysicalNode physicalNode : physicalNodes ) {
                physicalNodeMap.put(physicalNode.getNodeId(), physicalNode);

                physicalPorts = new HashMap<PhysicalPortId, PhysicalPort>();
                physicalPortMap.put(physicalNode.getNodeId(), physicalPorts);

                for ( PhysicalPort physicalPort : physicalNode.getPhysicalPort() ) {
                    physicalPorts.put(physicalPort.getPortId(), physicalPort);
                }
            }

            List<PhysicalLink> physicalLinks = physicalNetwork.getPhysicalLinks().getPhysicalLink();

            for ( PhysicalLink physicalLink : physicalLinks ) {
                physicalLinkMap.put(physicalLink.getLinkId(), physicalLink);
            }

            if ( null != physicalNetwork.getPhysicalPaths() ) {
                List<PhysicalPath> physicalPaths = physicalNetwork.getPhysicalPaths().getPhysicalPath();

                if ( null != physicalPaths ) {
                    for ( PhysicalPath physicalPath : physicalPaths ) {
//                        physicalPathMap.put(physicalPath.getPathId(), physicalPath);
                        physicalPathMap.put(physicalPath.getPathId(),
                                sortPhysicalLinksOfPhysicalPath(physicalPath));
                    }
                }
            }

            return;
        }

        protected PhysicalLink getPhysicalLink(PhysicalLinkId physicalLinkId) {
            return physicalLinkMap.get(physicalLinkId);
        }

        protected PhysicalPath getPhysicalPath(PhysicalPathId physicalPathId) {
            return physicalPathMap.get(physicalPathId);
        }

        protected PhysicalPort getPhysicalPort(PhysicalNodeId physicalNodeId, PhysicalPortId physicalPortId) {
            return physicalPortMap.get(physicalNodeId).get(physicalPortId);
        }

        protected PhysicalLink getFirstPhysicalLinkOfPhysicalPath(PhysicalPath physicalPath) {
//            for ( org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink
//                    physicalLink : physicalPath.getPhysicalLink() ) {
//                if ( 0 == physicalLink.getOrder() ) {
//                    return physicalLinkMap.get(physicalLink.getLinkId());
//                }
//            }
//
//            return null;

            if ( physicalPath.getPhysicalLink().isEmpty() ) {
                return null;
            }

            PhysicalLinkId physicalLinkId = physicalPath.getPhysicalLink().get(0).getLinkId();

            return physicalLinkMap.get(physicalLinkId);
        }

        protected PhysicalLink getLastPhysicalLinkOfPhysicalPath(PhysicalPath physicalPath) {
//            long order = physicalPath.getPhysicalLink().size() - 1;
//
//            for ( org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink
//                    physicalLink : physicalPath.getPhysicalLink() ) {
//                if ( physicalLink.getOrder() == order ) {
//                    return physicalLinkMap.get(physicalLink.getLinkId());
//                }
//            }
//
//            return null;

            if ( physicalPath.getPhysicalLink().isEmpty() ) {
                return null;
            }

            PhysicalLinkId physicalLinkId = physicalPath.getPhysicalLink()
                    .get(physicalPath.getPhysicalLink().size() - 1).getLinkId();

            return physicalLinkMap.get(physicalLinkId);
        }

        private PhysicalPath sortPhysicalLinksOfPhysicalPath(PhysicalPath physicalPath) {
            if ( physicalPath.getPhysicalLink().isEmpty()
                    || 1 == physicalPath.getPhysicalLink().size() ) {
                return physicalPath;
            }

            List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink> sortedPhysicalLinks =
                    new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink>(physicalPath.getPhysicalLink().size());
            sortedPhysicalLinks.addAll(physicalPath.getPhysicalLink());

            for ( org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink
                    physicalLink : physicalPath.getPhysicalLink() ) {
                sortedPhysicalLinks.set(physicalLink.getOrder().intValue(), physicalLink);
            }

            PhysicalPath physicalPath1 = new PhysicalPathBuilder(physicalPath)
                    .setPhysicalLink(sortedPhysicalLinks)
                    .build();

            return physicalPath1;
        }
    }

    /**
     * TODO
     *
     * @author Zhigang Ji
     */
    private class VirtualNetworkHelper {
        private Map<VirtualNodeId, VirtualNode> virtualNodeMap;
        private Map<VirtualLinkId, VirtualLink> virtualLinkMap;
        private Map<VirtualPathId, VirtualPath> virtualPathMap;
        private Map<VirtualNodeId, VirtualNode> virtualRouterMap;
        private Map<VirtualNodeId, Map<VirtualPortId, VirtualPort>> virtualPortMap;
        private Map<VirtualNodeId, VirtualPort> layer2ExternalVirtualPortMap;
        private Map<VirtualNodeId, VirtualPort> layer3ExternalVirtualPortMap;
        private Map<VirtualNodeId, Map<VirtualNodeId, Map.Entry<VirtualPort, VirtualLink>>> virtualSwitchConnectedInternalVirtualPortMap;
        private Map<VirtualNodeId, Map<VirtualNodeId, Map.Entry<VirtualPort, VirtualLink>>> virtualRouterConnectedInternalVirtualPortMap;
        private Map<VirtualArpKey, VirtualArp> virtualArpMap;
        private Map<MacAddress, VirtualArp> macAddressKeyVirtualArpMap;

        public VirtualNetworkHelper(VirtualNetwork virtualNetwork) {
            virtualNodeMap = new HashMap<VirtualNodeId, VirtualNode>();
            virtualLinkMap = new HashMap<VirtualLinkId, VirtualLink>();
            virtualPathMap = new HashMap<VirtualPathId, VirtualPath>();
            virtualRouterMap = new HashMap<VirtualNodeId, VirtualNode>();
            virtualPortMap = new HashMap<VirtualNodeId, Map<VirtualPortId, VirtualPort>>();
            layer2ExternalVirtualPortMap = new HashMap<VirtualNodeId, VirtualPort>();
            layer3ExternalVirtualPortMap = new HashMap<VirtualNodeId, VirtualPort>();
            virtualSwitchConnectedInternalVirtualPortMap =
                    new HashMap<VirtualNodeId, Map<VirtualNodeId, Map.Entry<VirtualPort, VirtualLink>>>();
            virtualRouterConnectedInternalVirtualPortMap =
                    new HashMap<VirtualNodeId, Map<VirtualNodeId, Map.Entry<VirtualPort, VirtualLink>>>();
            virtualArpMap = new HashMap<VirtualArpKey, VirtualArp>();
            macAddressKeyVirtualArpMap = new HashMap<MacAddress, VirtualArp>();

            List<VirtualNode> virtualNodes = virtualNetwork.getVirtualNodes().getVirtualNode();
            Map<VirtualPortId, VirtualPort> virtualPorts;

            for ( VirtualNode virtualNode : virtualNodes ) {
                virtualNodeMap.put(virtualNode.getNodeId(), virtualNode);

                if ( VirtualNode.NodeType.Vrouter == virtualNode.getNodeType() ) {
                    virtualRouterMap.put(virtualNode.getNodeId(), virtualNode);
                }

                virtualPorts = new HashMap<VirtualPortId, VirtualPort>();
                virtualPortMap.put(virtualNode.getNodeId(), virtualPorts);

                for ( VirtualPort virtualPort : virtualNode.getVirtualPort() ) {
                    virtualPorts.put(virtualPort.getPortId(), virtualPort);

                    if ( VirtualPort.PortType.External == virtualPort.getPortType() ) {
                        if ( null == virtualPort.getExternalMacAddresses() ) {
                            layer3ExternalVirtualPortMap.put(virtualNode.getNodeId(), virtualPort);
                        } else {
                            layer2ExternalVirtualPortMap.put(virtualNode.getNodeId(), virtualPort);
                        }
                    }
                }
            }

            List<VirtualLink> virtualLinks = virtualNetwork.getVirtualLinks().getVirtualLink();
            Map<VirtualNodeId, Map.Entry<VirtualPort, VirtualLink>> virtualSwitchConnectedInternalVirtualPorts;
            Map<VirtualNodeId, Map.Entry<VirtualPort, VirtualLink>> virtualRouterConnectedInternalVirtualPorts;
            VirtualNode virtualNode;
            VirtualPort virtualPort;

            for ( VirtualLink virtualLink : virtualLinks ) {
                virtualLinkMap.put(virtualLink.getLinkId(), virtualLink);

                virtualNode = virtualNodeMap.get(virtualLink.getDestNodeId());
                virtualPort = virtualPortMap.get(virtualLink.getSrcNodeId()).get(virtualLink.getSrcPortId());

                if ( VirtualNode.NodeType.Vswitch == virtualNode.getNodeType() ) {
                    virtualSwitchConnectedInternalVirtualPorts =
                            virtualSwitchConnectedInternalVirtualPortMap.get(virtualLink.getSrcNodeId());

                    if ( null == virtualSwitchConnectedInternalVirtualPorts ) {
                        virtualSwitchConnectedInternalVirtualPorts =
                                new HashMap<VirtualNodeId, Map.Entry<VirtualPort, VirtualLink>>();
                        virtualSwitchConnectedInternalVirtualPortMap.put(
                                virtualLink.getSrcNodeId(), virtualSwitchConnectedInternalVirtualPorts);
                    }

                    virtualSwitchConnectedInternalVirtualPorts.put(virtualLink.getDestNodeId(),
                            new AbstractMap.SimpleEntry<VirtualPort, VirtualLink>(virtualPort, virtualLink));
                } else if ( VirtualNode.NodeType.Vrouter == virtualNode.getNodeType() ) {
                    virtualRouterConnectedInternalVirtualPorts =
                            virtualRouterConnectedInternalVirtualPortMap.get(virtualLink.getSrcNodeId());

                    if ( null == virtualRouterConnectedInternalVirtualPorts ) {
                        virtualRouterConnectedInternalVirtualPorts =
                                new HashMap<VirtualNodeId, Map.Entry<VirtualPort, VirtualLink>>();
                        virtualRouterConnectedInternalVirtualPortMap.put(
                                virtualLink.getSrcNodeId(), virtualRouterConnectedInternalVirtualPorts);
                    }

                    virtualRouterConnectedInternalVirtualPorts.put(virtualLink.getDestNodeId(),
                            new AbstractMap.SimpleEntry<VirtualPort, VirtualLink>(virtualPort, virtualLink));
                }
            }

            List<VirtualPath> virtualPaths = virtualNetwork.getVirtualPaths().getVirtualPath();

            for ( VirtualPath virtualPath : virtualPaths ) {
                virtualPathMap.put(virtualPath.getPathId(), virtualPath);
            }

            List<VirtualArp> virtualArps = virtualNetwork.getVirtualArps().getVirtualArp();

            for ( VirtualArp virtualArp : virtualArps ) {
                virtualArpMap.put(virtualArp.getKey(), virtualArp);
                macAddressKeyVirtualArpMap.put(virtualArp.getMacAddress(), virtualArp);
            }

            return;
        }

        protected VirtualLink getVirtualLink(VirtualLinkId virtualLinkId) {
            return virtualLinkMap.get(virtualLinkId);
        }

        protected VirtualPath getVirtualPath(VirtualPathId virtualPathId) {
            return virtualPathMap.get(virtualPathId);
        }

        protected Map<VirtualNodeId, VirtualNode> getVirtualRouters() {
            return virtualRouterMap;
        }

        protected VirtualPort getLayer2ExternalVirtualPort(VirtualNodeId virtualNodeId) {
            return layer2ExternalVirtualPortMap.get(virtualNodeId);
        }

        protected VirtualPort getLayer3ExternalVirtualPort(VirtualNodeId virtualNodeId) {
            return layer3ExternalVirtualPortMap.get(virtualNodeId);
        }

        protected Map<VirtualNodeId, Map.Entry<VirtualPort, VirtualLink>> getConnectedVirtualSwitches(
                VirtualNodeId virtualNodeId) {
            return virtualSwitchConnectedInternalVirtualPortMap.get(virtualNodeId);
        }

        protected Map<VirtualNodeId, Map.Entry<VirtualPort, VirtualLink>> getConnectedVirtualRouters(
                VirtualNodeId virtualNodeId) {
            return virtualRouterConnectedInternalVirtualPortMap.get(virtualNodeId);
        }

        protected VirtualArp getVirtualArp(MacAddress macAddress) {
            return macAddressKeyVirtualArpMap.get(macAddress);
        }
    }
}
