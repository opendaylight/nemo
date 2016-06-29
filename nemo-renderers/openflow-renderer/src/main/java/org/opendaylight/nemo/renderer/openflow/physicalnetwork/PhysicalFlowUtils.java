/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.renderer.openflow.physicalnetwork;


import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev130715.Uri;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.OutputActionCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.OutputActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.output.action._case.OutputAction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.output.action._case.OutputActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowCapableNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.Table;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.TableKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.FlowBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.FlowKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.OutputPortValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.Instructions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.InstructionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.Match;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.ApplyActionsCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.ApplyActionsCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.apply.actions._case.ApplyActions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.apply.actions._case.ApplyActionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.Instruction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.Nodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.l2.types.rev130827.EtherType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetTypeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.EthernetMatch;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.EthernetMatchBuilder;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class PhysicalFlowUtils {
    private static final Logger LOG = LoggerFactory.getLogger(PhysicalFlowUtils.class);
    private static final short CONFIG_TABLE_ID = 0;
    private static final int CONFIG_FLOW_PRIORITY = 0;
    private static final int ETH_TYPE_ARP = 0x0806;
    private static final int ETH_TYPE_LLDP = 0x88cc;
    private final DataBroker dataBroker;

    public PhysicalFlowUtils(DataBroker dataBroker) {
        this.dataBroker = dataBroker;
    }
    /**
     * Config the lldp flow entry while find one of switch.
     * @param strNodeId string id of the target node.
     */
    public void configLLDPEntry(String strNodeId) {

        WriteTransaction writeTransaction = dataBroker.newWriteOnlyTransaction();

        EthernetType ethernetTypeLLDP = new EthernetTypeBuilder().setType(new EtherType((long) ETH_TYPE_LLDP)).build();
        EthernetMatchBuilder ethernetMatchBuilder = new EthernetMatchBuilder().setEthernetType(ethernetTypeLLDP);
        EthernetMatch ethernetMatch = ethernetMatchBuilder.build();

        Match match = new MatchBuilder().setEthernetMatch(ethernetMatch).build();

        List<Instruction> instructionList = new LinkedList<Instruction>();
        List<Action> actionList = new LinkedList<Action>();
        Uri uri = controllerConnectorId();
        OutputAction outputAction = new OutputActionBuilder().setOutputNodeConnector(uri).build();
        OutputActionCase outputActionCase = new OutputActionCaseBuilder().setOutputAction(outputAction).build();
        Action actionOutput = new ActionBuilder().setOrder(actionList.size()).setAction(outputActionCase).build();
        actionList.add(actionOutput);

        ApplyActions applyActions = new ApplyActionsBuilder().setAction(actionList).build();
        ApplyActionsCase applyActionsCase = new ApplyActionsCaseBuilder().setApplyActions(applyActions).build();
        Instruction instructionApply = new InstructionBuilder().setOrder(instructionList.size()).setInstruction(applyActionsCase).build();
        instructionList.add(instructionApply);

        Instructions instructions = new InstructionsBuilder().setInstruction(instructionList).build();

        FlowId flowId = new FlowId(UUID.randomUUID().toString());
        FlowBuilder flowBuilder = baseFlowBuilder().setId(flowId).setTableId(CONFIG_TABLE_ID).setPriority(CONFIG_FLOW_PRIORITY);
        Flow flow = flowBuilder.setMatch(match).setInstructions(instructions).build();

        NodeId nodeId = new NodeId(strNodeId);
        InstanceIdentifier<Flow> flowInsId = createFlowPath(nodeId, flow.getTableId(), flow.getId());

        writeTransaction.put(LogicalDatastoreType.CONFIGURATION, flowInsId, flow, true);
        writeTransaction.submit();

        return;
    }
    /**
     * Config the arp flow entry while find one of switch.
     * @param strNodeId string id of the target node.
     */
    public void configArpPEntry(String strNodeId) {

        WriteTransaction writeTransaction = dataBroker.newWriteOnlyTransaction();

        EthernetType ethernetTypeLLDP = new EthernetTypeBuilder().setType(new EtherType((long) ETH_TYPE_ARP)).build();
        EthernetMatchBuilder ethernetMatchBuilder = new EthernetMatchBuilder().setEthernetType(ethernetTypeLLDP);
        EthernetMatch ethernetMatch = ethernetMatchBuilder.build();

        Match match = new MatchBuilder().setEthernetMatch(ethernetMatch).build();

        List<Instruction> instructionList = new LinkedList<Instruction>();
        List<Action> actionList = new LinkedList<Action>();
        Uri uri = controllerConnectorId();
        OutputAction outputAction = new OutputActionBuilder().setOutputNodeConnector(uri).build();
        OutputActionCase outputActionCase = new OutputActionCaseBuilder().setOutputAction(outputAction).build();
        Action actionOutput = new ActionBuilder().setOrder(actionList.size()).setAction(outputActionCase).build();
        actionList.add(actionOutput);

        ApplyActions applyActions = new ApplyActionsBuilder().setAction(actionList).build();
        ApplyActionsCase applyActionsCase = new ApplyActionsCaseBuilder().setApplyActions(applyActions).build();
        Instruction instructionApply = new InstructionBuilder().setOrder(instructionList.size()).setInstruction(applyActionsCase).build();
        instructionList.add(instructionApply);

        Instructions instructions = new InstructionsBuilder().setInstruction(instructionList).build();

        FlowId flowId = new FlowId(UUID.randomUUID().toString());
        FlowBuilder flowBuilder = baseFlowBuilder().setId(flowId).setTableId(CONFIG_TABLE_ID).setPriority(CONFIG_FLOW_PRIORITY);
        Flow flow = flowBuilder.setMatch(match).setInstructions(instructions).build();

        NodeId nodeId = new NodeId(strNodeId);
        InstanceIdentifier<Flow> flowInsId = createFlowPath(nodeId, flow.getTableId(), flow.getId());

        writeTransaction.put(LogicalDatastoreType.CONFIGURATION, flowInsId, flow, true);
        writeTransaction.submit();

        return;
    }
    private Uri controllerConnectorId(){
        return new Uri(OutputPortValues.CONTROLLER.toString());
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
     * @return TODO
     */
    private InstanceIdentifier<Node> createNodePath(NodeId nodeId) {
        return InstanceIdentifier.builder(Nodes.class).child(Node.class, new NodeKey(nodeId)).build();
    }
}
