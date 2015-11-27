package org.opendaylight.nemo.intent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.nemo.intent.IntentResolverUtils;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualResourceEntityId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.VirtualResourceInstance.VirtualResourceType;
import static org.junit.Assert.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.hosts.PhysicalHost;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.links.VirtualLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.virtual.network.virtual.nodes.VirtualNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResource;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalHostId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalNodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.MatchItem;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.SubNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.StringValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.match.item.instance.MatchItemValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static org.mockito.Mockito.*;

/**
 * Created by zhangmeng on 2015/11/26.
 */
public class IntentResolverUtilsTest extends IntentResolverUtils {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testCheckExternalLayer3Group() throws Exception {
        Node node = mock(Node.class);
        List<Property> properties = new LinkedList<Property>();
        Property property = mock(Property.class);
        properties.add(property);

        when(node.getProperty()).thenReturn(null);//avoid testing method "getNodeProperty"
        IntentResolverUtils.checkExternalLayer3Group(node);
        verify(node).getProperty();

        //test method "getNodeProperty". and method "CheckExternalLayer3Group"`s another branch
//        when(node.getProperty()).thenReturn(properties);
//        when(property.getPropertyName()).thenReturn(new PropertyName("ac-info-network"));

        /* anther branch can`t be tested. The reason is that I cannot known the method property.getPropertyValues() returns what object.
           So I cant`t mock the needed object
        */
    }

    @Test
    public void testGetPhysicalHost() throws Exception {
        List<PhysicalHost> physicalHosts = new LinkedList<PhysicalHost>();
        Node node =  mock(Node.class);
        NodeId nodeId = mock(NodeId.class);

        when(node.getNodeId()).thenReturn(nodeId);
        when(nodeId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));

        // test private method "getPhysicalHost"
        PhysicalHost physicalHost = mock(PhysicalHost.class);
        physicalHosts.add(physicalHost);
        when(physicalHost.getHostId())
                .thenReturn(new PhysicalHostId("00001111-0000-0000-0000-000011112222"))
                .thenReturn(new PhysicalHostId("00001111-0000-0000-0000-000011112221"));

        IntentResolverUtils.getPhysicalHost(physicalHosts, node);
        IntentResolverUtils.getPhysicalHost(physicalHosts,node);
        verify(node,times(2)).getNodeId();
        verify(nodeId,times(2)).getValue();
        verify(physicalHost,times(2)).getHostId();
    }

    @Test
    public void testGetNodeProperty() throws Exception {
        List<Property> properties = new LinkedList<Property>();
        PropertyName propertyName = new PropertyName("test");
        Property property = mock(Property.class);
        properties.add(property);

        when(property.getPropertyName()).thenReturn(propertyName);
        IntentResolverUtils.getNodeProperty(properties, propertyName);
        verify(property).getPropertyName();

        IntentResolverUtils.getNodeProperty(null, propertyName);
    }

    @Test
    public void testGeneratePhysicalNodeIdFromNodeLocationProperty() throws Exception {
        //method can`t be tested because of "String propertyValue = property.getPropertyValues().getStringValue().get(0).getValue();"

    }

    @Test
    public void testGetIntentVnMappingResult() throws Exception {
        List<IntentVnMappingResult> intentVnMappingResults =  new LinkedList<IntentVnMappingResult>();
        IntentId intentId = mock(IntentId.class);
        IntentVnMappingResult intentVnMappingResult = mock(IntentVnMappingResult.class);
        intentVnMappingResults.add(intentVnMappingResult);

        when(intentVnMappingResult.getIntentId())
                .thenReturn(intentId)
                .thenReturn(new IntentId("00001111-0000-0000-0000-000011112222"));

        IntentResolverUtils.getIntentVnMappingResult(intentVnMappingResults, intentId);
        IntentResolverUtils.getIntentVnMappingResult(intentVnMappingResults, intentId);
        verify(intentVnMappingResult,times(2)).getIntentId();
    }

    @Test
    public void testGetVirtualNode() throws Exception {
        List<VirtualNode> virtualNodes =  new LinkedList<VirtualNode>();
        VirtualNodeId virtualNodeId = mock(VirtualNodeId.class);
        VirtualNode virtualNode = mock(VirtualNode.class);
        virtualNodes.add(virtualNode);

        when(virtualNode.getNodeId())
                .thenReturn(virtualNodeId)
                .thenReturn(new VirtualNodeId("00001111-0000-0000-0000-000011112222"));

        IntentResolverUtils.getVirtualNode(virtualNodes, virtualNodeId);
        IntentResolverUtils.getVirtualNode(virtualNodes, virtualNodeId);
        verify(virtualNode,times(2)).getNodeId();
    }

    @Test
    public void testGetNode() throws Exception {
        List<Node> nodes = new LinkedList<Node>();
        NodeId nodeId = mock(NodeId.class);
        Node node = mock(Node.class);
        nodes.add(node);

        when(node.getNodeId())
                .thenReturn(nodeId)
                .thenReturn(new NodeId("00001111-0000-0000-0000-000011112222"));

        IntentResolverUtils.getNode(nodes, nodeId);
        IntentResolverUtils.getNode(nodes, nodeId);
        verify(node,times(2)).getNodeId();
    }

    @Test
    public void testGetConnectionProperty() throws Exception {
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property>
                properties = new LinkedList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property>();
        PropertyName propertyName = mock(PropertyName.class);
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property
                property = mock(org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.Property.class);
        properties.add(property);

        when(property.getPropertyName()).thenReturn(propertyName);

        IntentResolverUtils.getConnectionProperty(properties, propertyName);
        verify(property).getPropertyName();

        IntentResolverUtils.getConnectionProperty(null,propertyName);
    }

    @Test
    public void testSortSubNodes() throws Exception {
        List<SubNode> subNodes = new LinkedList<SubNode>();
        SubNode subNode = mock(SubNode.class);
        SubNode subNode1 = mock(SubNode.class);
        long first = 0;
        long second = 1;

        //test empty
        Assert.assertTrue(subNodes.size() == 0);
        IntentResolverUtils.sortSubNodes(subNodes);
        //test size = 1;
        subNodes.add(subNode);
        Assert.assertTrue(subNodes.size() == 1);
        IntentResolverUtils.sortSubNodes(subNodes);
        //test size > 1
        subNodes.add(subNode1);
        when(subNode.getOrder()).thenReturn(first);
        when(subNode1.getOrder()).thenReturn(second);

    }

    @Test
    public void testCheckAllLayer2OperatingMode() throws Exception {
        List<SubNode> subNodes = new LinkedList<SubNode>();
        List<Node> nodes = new LinkedList<Node>();
        List<Property> properties = new LinkedList<Property>();
        //List<StringValue> stringValues = new LinkedList<StringValue>();
        List<StringValue> stringValues = mock(List.class);
        Property property = mock(Property.class);
        NodeId nodeId = mock(NodeId.class);
        Node node = mock(Node.class);
        SubNode subNode = mock(SubNode.class);
        PropertyName propertyName = mock(PropertyName.class);
        PropertyValues propertyValues = mock(PropertyValues.class);
        StringValue stringValue = mock(StringValue.class);

        //test empty
        Assert.assertTrue(subNodes.size() == 0);
        Assert.assertFalse(IntentResolverUtils.checkAllLayer2OperatingMode(subNodes, nodes));

        //test not empty
        subNodes.add(subNode);
        nodes.add(node);
        properties.add(property);
        //stringValues.add(stringValue);
        when(subNode.getNodeId()).thenReturn(nodeId);
        when(node.getNodeId())
                .thenReturn(new NodeId("00001111-0000-0000-0000-000011112222"))
                .thenReturn(nodeId)
                .thenReturn(nodeId)
                .thenReturn(nodeId);
        Assert.assertFalse(IntentResolverUtils.checkAllLayer2OperatingMode(subNodes, nodes));
        when(node.getProperty()).thenReturn(properties);
        when(property.getPropertyName())
                .thenReturn(propertyName)
                .thenReturn(new PropertyName("operating-mode"))
                .thenReturn(new PropertyName("operating-mode"));
        Assert.assertFalse(IntentResolverUtils.checkAllLayer2OperatingMode(subNodes, nodes));
        when(property.getPropertyValues()).thenReturn(propertyValues);
        when(propertyValues.getStringValue()).thenReturn(stringValues);
        when(stringValues.get(anyInt())).thenReturn(stringValue);
        when(stringValue.getValue())
                .thenReturn(new String("layer2"))
                .thenReturn(new String("test"));

        Assert.assertTrue(IntentResolverUtils.checkAllLayer2OperatingMode(subNodes, nodes));
        Assert.assertFalse(IntentResolverUtils.checkAllLayer2OperatingMode(subNodes, nodes));

        verify(subNode,times(4)).getNodeId();
        verify(node,times(4)).getNodeId();
        verify(node,times(3)).getProperty();
        verify(property,times(3)).getPropertyName();
        verify(property, times(2)).getPropertyValues();
        verify(propertyValues,times(2)).getStringValue();
        verify(stringValues,times(2)).get(anyInt());
        verify(stringValue,times(2)).getValue();

    }

    @Test
    public void testCheckAllLayer3OperatingMode() throws Exception {
        List<SubNode> subNodes = new LinkedList<SubNode>();
        List<Node> nodes = new LinkedList<Node>();
        List<Property> properties = new LinkedList<Property>();
        //List<StringValue> stringValues = new LinkedList<StringValue>();
        List<StringValue> stringValues = mock(List.class);
        Property property = mock(Property.class);
        NodeId nodeId = mock(NodeId.class);
        Node node = mock(Node.class);
        SubNode subNode = mock(SubNode.class);
        PropertyName propertyName = mock(PropertyName.class);
        PropertyValues propertyValues = mock(PropertyValues.class);
        StringValue stringValue = mock(StringValue.class);

        //test empty
        Assert.assertTrue(subNodes.size() == 0);
        Assert.assertFalse(IntentResolverUtils.checkAllLayer3OperatingMode(subNodes, nodes));

        //test not empty
        subNodes.add(subNode);
        nodes.add(node);
        properties.add(property);
        //stringValues.add(stringValue);
        when(subNode.getNodeId()).thenReturn(nodeId);
        when(node.getNodeId())
                .thenReturn(new NodeId("00001111-0000-0000-0000-000011112222"))
                .thenReturn(nodeId)
                .thenReturn(nodeId)
                .thenReturn(nodeId);
        Assert.assertFalse(IntentResolverUtils.checkAllLayer3OperatingMode(subNodes, nodes));
        when(node.getProperty()).thenReturn(properties);
        when(property.getPropertyName())
                .thenReturn(propertyName)
                .thenReturn(new PropertyName("operating-mode"))
                .thenReturn(new PropertyName("operating-mode"));
        Assert.assertFalse(IntentResolverUtils.checkAllLayer3OperatingMode(subNodes, nodes));
        when(property.getPropertyValues()).thenReturn(propertyValues);
        when(propertyValues.getStringValue()).thenReturn(stringValues);
        when(stringValues.get(anyInt())).thenReturn(stringValue);
        when(stringValue.getValue())
                .thenReturn(new String("layer3"))
                .thenReturn(new String("test"));

        Assert.assertTrue(IntentResolverUtils.checkAllLayer3OperatingMode(subNodes, nodes));
        Assert.assertFalse(IntentResolverUtils.checkAllLayer3OperatingMode(subNodes, nodes));

        verify(subNode,times(4)).getNodeId();
        verify(node,times(4)).getNodeId();
        verify(node,times(3)).getProperty();
        verify(property,times(3)).getPropertyName();
        verify(property, times(2)).getPropertyValues();
        verify(propertyValues,times(2)).getStringValue();
        verify(stringValues,times(2)).get(anyInt());
        verify(stringValue,times(2)).getValue();
    }

    @Test
    public void testGetVirtualLink() throws Exception {
        List<VirtualLink> virtualLinks = new LinkedList<VirtualLink>();
        VirtualNodeId srcVirtualNodeId = mock(VirtualNodeId.class);
        VirtualNodeId destVirtualNodeId = mock(VirtualNodeId.class);
        VirtualLink virtualLink = mock(VirtualLink.class);
        VirtualLink virtualLink_test ;
        virtualLinks.add(virtualLink);
        //test not null and null
        when(virtualLink.getSrcNodeId())
                .thenReturn(srcVirtualNodeId)
                .thenReturn(new VirtualNodeId("00001111-0000-0000-0000-000011112222"));
        when(virtualLink.getDestNodeId())
                .thenReturn(destVirtualNodeId)
                .thenReturn(new VirtualNodeId("00001111-0000-0000-0000-000011112222"));
        virtualLink_test = IntentResolverUtils.getVirtualLink(virtualLinks, srcVirtualNodeId, destVirtualNodeId);
        Assert.assertTrue(virtualLink_test != null);
        virtualLink_test = IntentResolverUtils.getVirtualLink(virtualLinks,srcVirtualNodeId,destVirtualNodeId);
        Assert.assertTrue(virtualLink_test == null);
        verify(virtualLink,times(2)).getSrcNodeId();
        verify(virtualLink).getDestNodeId();

    }

    @Test
    public void testGetObject() throws Exception {
        DataObject dataObject ;
        Objects objects = mock(Objects.class);
        ObjectId objectId = mock(ObjectId.class);
        Node node = mock(Node.class);
        Connection connection = mock(Connection.class);
        Flow flow = mock(Flow.class);
        List<Node> nodes = new LinkedList<Node>();
        List<Connection> connections = new LinkedList<Connection>();
        List<Flow> flows = new LinkedList<Flow>();

        when(objects.getNode()).thenReturn(nodes);
        when(objects.getConnection()).thenReturn(connections);
        when(objects.getFlow()).thenReturn(flows);
        when(objectId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
        // when nodes and connections and flows are all empty
        dataObject = IntentResolverUtils.getObject(objects, objectId);
        Assert.assertTrue(dataObject == null);
        //test  flow
        flows.add(flow);
        when(flow.getFlowId())
                .thenReturn(new FlowId(new String("00001111-0000-0000-0000-000011112222")))
                .thenReturn(mock(FlowId.class));
        //return true
        dataObject = IntentResolverUtils.getObject(objects, objectId);
        Assert.assertTrue(dataObject != null);
        //return null
        dataObject = IntentResolverUtils.getObject(objects, objectId);
        Assert.assertTrue(dataObject == null);

        //test connection
        connections.add(connection);
        when(connection.getConnectionId())
                .thenReturn(new ConnectionId(new String("00001111-0000-0000-0000-000011112222")))
                .thenReturn(mock(ConnectionId.class));
        //return true
        dataObject = IntentResolverUtils.getObject(objects, objectId);
        Assert.assertTrue(dataObject != null);
        //return null
        dataObject = IntentResolverUtils.getObject(objects, objectId);
        Assert.assertTrue(dataObject == null);

        //test node
        nodes.add(node);
        when(node.getNodeId())
                .thenReturn(new NodeId(new String("00001111-0000-0000-0000-000011112222")))
                .thenReturn(mock(NodeId.class));
        //return true
        dataObject = IntentResolverUtils.getObject(objects, objectId);
        Assert.assertTrue(dataObject != null);
        //return null
        dataObject = IntentResolverUtils.getObject(objects, objectId);
        Assert.assertTrue(dataObject == null);

    }

    @Test
    public void testGetSameTargetObjectOperations() throws Exception {
        List<Operation> operations = new LinkedList<Operation>();
        Operation operation = mock(Operation.class);

        List<Operation> list ;
        list = IntentResolverUtils.getSameTargetObjectOperations(operations,operation);
        Assert.assertTrue(list.size() == 0);
    }

    @Test
    public void testGetGreaterAndEqualPriorityOperations() throws Exception {
        List<Operation> operations = new LinkedList<Operation>();
        Operation operation = mock(Operation.class);
        List<Operation> greaterPriorityOperations = new LinkedList<Operation>();
        List<Operation> equalPriorityOperations = new LinkedList<Operation>();

        IntentResolverUtils.getGreaterAndEqualPriorityOperations(operations,operation,greaterPriorityOperations,equalPriorityOperations);
    }

    @Test
    public void testGetConflictingOperation() throws Exception {
        List<Operation> operations = new LinkedList<Operation>();
        Operation operation = mock(Operation.class);

        Operation operation1 = IntentResolverUtils.getConflictingOperation(operations,operation);
        Assert.assertTrue(operation1 == null);
    }

    @Test
    public void testGetConflictingOperations() throws Exception {
        List<Operation> operations = new LinkedList<Operation>();
        Operation operation = mock(Operation.class);

        List<Operation> operations1 = IntentResolverUtils.getConflictingOperations(operations, operation);
        Assert.assertTrue(operations1 == null);
    }

    @Test
    public void testGetAction() throws Exception {
        List<Action> actions = new LinkedList<Action>();
        ActionName actionName = mock(ActionName.class);
        Action action = mock(Action.class);
        Action action_test ;

        actions.add(action);
        when(action.getActionName())
                .thenReturn(actionName)
                .thenReturn(new ActionName("a"));
        action_test = IntentResolverUtils.getAction(actions,actionName);
        Assert.assertTrue(action_test != null);
        action_test = IntentResolverUtils.getAction(actions,actionName);
        Assert.assertTrue(action_test == null);
        verify(action,times(2)).getActionName();
    }

    @Test
    public void testGetSourceVirtualRouterOfFlow() throws Exception {
        VirtualNode virtualNode_test;
        VirtualNode virtualNode = mock(VirtualNode.class);
        Flow flow = mock(Flow.class);
        MatchItemName matchItemName = mock(MatchItemName.class);
        MatchItem matchItem = mock(MatchItem.class);
        MatchItemValue matchItemValue = mock(MatchItemValue.class);
        Node node = mock(Node.class);
        IntentVnMappingResult intentVnMappingResult = mock(IntentVnMappingResult.class);
        List<VirtualNode> virtualNodes = new ArrayList<VirtualNode>();
        List<MatchItem> matchItems = new ArrayList<MatchItem>();
        List<Node> nodes = new ArrayList<Node>();
        List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>();

        virtualNodes.add(virtualNode);
        nodes.add(node);
        intentVnMappingResults.add(intentVnMappingResult);
        //test private method "getMatchItem"
        when(flow.getMatchItem())
                .thenReturn(null) //test null
                .thenReturn(matchItems) //not null since this time
                .thenReturn(matchItems);

        virtualNode_test = IntentResolverUtils.getSourceVirtualRouterOfFlow(virtualNodes,flow,nodes,intentVnMappingResults);
        Assert.assertTrue(virtualNode_test == null);

        matchItems.add(matchItem);
        when(matchItem.getMatchItemName())
                .thenReturn(matchItemName)//not same as matchItemName , the value to be return is null
                .thenReturn(new MatchItemName("src-ip"));//same as matchItemName, value to be return is not null

        when(matchItem.getMatchItemValue()).thenReturn(matchItemValue);
        when(matchItemValue.getStringValue()).thenReturn(new String("test"));

        virtualNode_test = IntentResolverUtils.getSourceVirtualRouterOfFlow(virtualNodes,flow,nodes,intentVnMappingResults);
        Assert.assertTrue(virtualNode_test == null);

        ///////////////////////////////////////////////////////
        //test private method "getVirtualRouterWithIpPrefix"
        //test null
        nodes.clear();
        virtualNode_test = IntentResolverUtils.getSourceVirtualRouterOfFlow(virtualNodes,flow,nodes,intentVnMappingResults);
        Assert.assertTrue(virtualNode_test == null);

        //test not null
        NodeType layer2GroupNodeType = new NodeType("l2-group");
        NodeType externalGroupNodeType = new NodeType("ext-group");
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.VirtualNodeInstance.NodeType VrouterNodeType =
                VirtualNode.NodeType.Vrouter;
        NodeId nodeId = mock(NodeId.class);
        IntentId intentId = new IntentId(new String("00001111-0000-0000-0000-000011112222"));
        List<Property> properties = new ArrayList<Property>();
        List<StringValue> propertyValues_list = new ArrayList<StringValue>();
        List<VirtualResource> virtualResources = new ArrayList<VirtualResource>();
        StringValue stringValue = mock(StringValue.class);
        Property property = mock(Property.class);
        PropertyName propertyName = new PropertyName("ip-prefix");
        PropertyValues propertyValues = mock(PropertyValues.class);
        VirtualResource virtualResource = mock(VirtualResource.class);
        VirtualResourceType virtualResourceType_Vport = VirtualResource.VirtualResourceType.Vport;
        VirtualResourceType virtualResourceType_Vnode = VirtualResource.VirtualResourceType.Vnode;
        VirtualResourceType virtualResourceType_Other = null;
        VirtualResourceEntityId virtualResourceEntityId = mock(VirtualResourceEntityId.class);

        virtualResources.add(virtualResource);
        nodes.add(node);
        properties.add(property);
        propertyValues_list.add(stringValue);

        when(node.getNodeType())
                .thenReturn(mock(NodeType.class))
                .thenReturn(layer2GroupNodeType)
                .thenReturn(externalGroupNodeType);
        virtualNode_test = IntentResolverUtils.getSourceVirtualRouterOfFlow(virtualNodes, flow, nodes,intentVnMappingResults);
        Assert.assertTrue(virtualNode_test == null);


        when(node.getProperty()).thenReturn(properties);
        when(property.getPropertyName())
                .thenReturn(mock(PropertyName.class))
                .thenReturn(propertyName);
        virtualNode_test = IntentResolverUtils.getSourceVirtualRouterOfFlow(virtualNodes,flow, nodes, intentVnMappingResults);
        Assert.assertTrue(virtualNode_test == null);

        when(property.getPropertyValues()).thenReturn(propertyValues);
        when(propertyValues.getStringValue()).thenReturn(propertyValues_list);
        when(stringValue.getValue())
                .thenReturn(new String("test1"))
                .thenReturn(new String("test"));
        virtualNode_test = IntentResolverUtils.getSourceVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults);
        Assert.assertTrue(virtualNode_test == null);
        verify(stringValue).getValue();

        when(node.getNodeId()).thenReturn(nodeId);
        when(nodeId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
        when(intentVnMappingResult.getIntentId())
                .thenReturn(mock(IntentId.class))
                .thenReturn(intentId);

        virtualNode_test = IntentResolverUtils.getSourceVirtualRouterOfFlow(virtualNodes,flow,nodes,intentVnMappingResults);
        Assert.assertTrue(virtualNode_test == null);

        when(intentVnMappingResult.getVirtualResource()).thenReturn(virtualResources);
        Assert.assertTrue(virtualResources.get(0) == virtualResource);
        when(virtualResource.getVirtualResourceType())
                .thenReturn(virtualResourceType_Other)
                .thenReturn(virtualResourceType_Vport)
                .thenReturn(virtualResourceType_Vnode);
        when(virtualResource.getParentVirtualResourceEntityId()).thenReturn(virtualResourceEntityId);
        when(virtualResource.getVirtualResourceEntityId()).thenReturn(virtualResourceEntityId);
        when(virtualResourceEntityId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));

        virtualNode_test = IntentResolverUtils.getSourceVirtualRouterOfFlow(virtualNodes,flow,nodes,intentVnMappingResults);
        Assert.assertTrue(virtualNode_test == null);

        when(virtualNode.getNodeId())
                .thenReturn(mock(VirtualNodeId.class))
                .thenReturn(new VirtualNodeId(new String("00001111-0000-0000-0000-000011112222")));

        virtualNode_test = IntentResolverUtils.getSourceVirtualRouterOfFlow(virtualNodes,flow,nodes,intentVnMappingResults);
        Assert.assertTrue(virtualNode_test == null);
        verify(virtualNode).getNodeId();

        when(virtualNode.getNodeType())
                .thenReturn(null)
                .thenReturn(VrouterNodeType);

        virtualNode_test = IntentResolverUtils.getSourceVirtualRouterOfFlow(virtualNodes,flow,nodes,intentVnMappingResults);
        Assert.assertTrue(virtualNode_test == null);
        verify(virtualNode).getNodeType();

        virtualNode_test = IntentResolverUtils.getSourceVirtualRouterOfFlow(virtualNodes,flow,nodes,intentVnMappingResults);
        Assert.assertTrue(virtualNode_test == virtualNode);
        verify(virtualNode,times(2)).getNodeType();

    }

    @Test
    public void testGetDestinationVirtualRouterOfFlow() throws Exception {
        VirtualNode virtualNode_test;
        VirtualNode virtualNode = mock(VirtualNode.class);
        Flow flow = mock(Flow.class);
        MatchItemName matchItemName = mock(MatchItemName.class);
        MatchItem matchItem = mock(MatchItem.class);
        MatchItemValue matchItemValue = mock(MatchItemValue.class);
        Node node = mock(Node.class);
        IntentVnMappingResult intentVnMappingResult = mock(IntentVnMappingResult.class);
        List<VirtualNode> virtualNodes = new ArrayList<VirtualNode>();
        List<MatchItem> matchItems = new ArrayList<MatchItem>();
        List<Node> nodes = new ArrayList<Node>();
        List<IntentVnMappingResult> intentVnMappingResults = new ArrayList<IntentVnMappingResult>();

        virtualNodes.add(virtualNode);
        nodes.add(node);
        intentVnMappingResults.add(intentVnMappingResult);
        //test private method "getMatchItem"
        when(flow.getMatchItem())
                .thenReturn(null) //test null
                .thenReturn(matchItems) //not null since this time
                .thenReturn(matchItems);

        virtualNode_test = IntentResolverUtils.getDestinationVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults);
        Assert.assertTrue(virtualNode_test == null);

        matchItems.add(matchItem);
        when(matchItem.getMatchItemName())
                .thenReturn(matchItemName)//not same as matchItemName , the value to be return is null
                .thenReturn(new MatchItemName("dst-ip"));//same as matchItemName, value to be return is not null

        when(matchItem.getMatchItemValue()).thenReturn(matchItemValue);
        when(matchItemValue.getStringValue()).thenReturn(new String("test"));

        virtualNode_test = IntentResolverUtils.getDestinationVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults);
        Assert.assertTrue(virtualNode_test == null);

        ///////////////////////////////////////////////////////
        //test private method "getVirtualRouterWithIpPrefix"
        //test null
        nodes.clear();
        virtualNode_test = IntentResolverUtils.getDestinationVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults);
        Assert.assertTrue(virtualNode_test == null);

        //test not null
        NodeType layer2GroupNodeType = new NodeType("l2-group");
        NodeType externalGroupNodeType = new NodeType("ext-group");
        org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.VirtualNodeInstance.NodeType VrouterNodeType =
                VirtualNode.NodeType.Vrouter;
        NodeId nodeId = mock(NodeId.class);
        IntentId intentId = new IntentId(new String("00001111-0000-0000-0000-000011112222"));
        List<Property> properties = new ArrayList<Property>();
        List<StringValue> propertyValues_list = new ArrayList<StringValue>();
        List<VirtualResource> virtualResources = new ArrayList<VirtualResource>();
        StringValue stringValue = mock(StringValue.class);
        Property property = mock(Property.class);
        PropertyName propertyName = new PropertyName("ip-prefix");
        PropertyValues propertyValues = mock(PropertyValues.class);
        VirtualResource virtualResource = mock(VirtualResource.class);
        VirtualResourceType virtualResourceType_Vport = VirtualResource.VirtualResourceType.Vport;
        VirtualResourceType virtualResourceType_Vnode = VirtualResource.VirtualResourceType.Vnode;
        VirtualResourceType virtualResourceType_Other = null;
        VirtualResourceEntityId virtualResourceEntityId = mock(VirtualResourceEntityId.class);

        virtualResources.add(virtualResource);
        nodes.add(node);
        properties.add(property);
        propertyValues_list.add(stringValue);

        when(node.getNodeType())
                .thenReturn(mock(NodeType.class))
                .thenReturn(layer2GroupNodeType)
                .thenReturn(externalGroupNodeType);
        virtualNode_test = IntentResolverUtils.getDestinationVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults);
        Assert.assertTrue(virtualNode_test == null);


        when(node.getProperty()).thenReturn(properties);
        when(property.getPropertyName())
                .thenReturn(mock(PropertyName.class))
                .thenReturn(propertyName);
        virtualNode_test = IntentResolverUtils.getDestinationVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults);
        Assert.assertTrue(virtualNode_test == null);

        when(property.getPropertyValues()).thenReturn(propertyValues);
        when(propertyValues.getStringValue()).thenReturn(propertyValues_list);
        when(stringValue.getValue())
                .thenReturn(new String("test1"))
                .thenReturn(new String("test"));
        virtualNode_test = IntentResolverUtils.getDestinationVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults);
        Assert.assertTrue(virtualNode_test == null);
        verify(stringValue).getValue();

        when(node.getNodeId()).thenReturn(nodeId);
        when(nodeId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));
        when(intentVnMappingResult.getIntentId())
                .thenReturn(mock(IntentId.class))
                .thenReturn(intentId);

        virtualNode_test = IntentResolverUtils.getDestinationVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults);
        Assert.assertTrue(virtualNode_test == null);

        when(intentVnMappingResult.getVirtualResource()).thenReturn(virtualResources);
        Assert.assertTrue(virtualResources.get(0) == virtualResource);
        when(virtualResource.getVirtualResourceType())
                .thenReturn(virtualResourceType_Other)
                .thenReturn(virtualResourceType_Vport)
                .thenReturn(virtualResourceType_Vnode);
        when(virtualResource.getParentVirtualResourceEntityId()).thenReturn(virtualResourceEntityId);
        when(virtualResource.getVirtualResourceEntityId()).thenReturn(virtualResourceEntityId);
        when(virtualResourceEntityId.getValue()).thenReturn(new String("00001111-0000-0000-0000-000011112222"));

        virtualNode_test = IntentResolverUtils.getDestinationVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults);
        Assert.assertTrue(virtualNode_test == null);

        when(virtualNode.getNodeId())
                .thenReturn(mock(VirtualNodeId.class))
                .thenReturn(new VirtualNodeId(new String("00001111-0000-0000-0000-000011112222")));

        virtualNode_test = IntentResolverUtils.getDestinationVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults);
        Assert.assertTrue(virtualNode_test == null);
        verify(virtualNode).getNodeId();

        when(virtualNode.getNodeType())
                .thenReturn(null)
                .thenReturn(VrouterNodeType);

        virtualNode_test = IntentResolverUtils.getDestinationVirtualRouterOfFlow(virtualNodes, flow, nodes, intentVnMappingResults);
        Assert.assertTrue(virtualNode_test == null);
        verify(virtualNode).getNodeType();

        virtualNode_test = IntentResolverUtils.getDestinationVirtualRouterOfFlow(virtualNodes,flow,nodes,intentVnMappingResults);
        Assert.assertTrue(virtualNode_test == virtualNode);
        verify(virtualNode,times(2)).getNodeType();
    }

    @Test
    public void testSortVirtualResources() throws Exception {
        List<VirtualResource> virtualResources = new ArrayList<VirtualResource>();
        VirtualResource virtualResource = mock(VirtualResource.class);
        VirtualResource virtualResource1 = mock(VirtualResource.class);
        List<VirtualResource> result ;
        long first = 0;
        long second = 1;

        //test empty
        Assert.assertTrue(virtualResources.size() == 0);
        result = IntentResolverUtils.sortVirtualResources(virtualResources);
        Assert.assertTrue(result.size() == 0);

        //test 1
        virtualResources.add(virtualResource);
        Assert.assertTrue(virtualResources.size() == 1);
        result = IntentResolverUtils.sortVirtualResources(virtualResources);
        Assert.assertNotNull(result);

        //test >1
        virtualResources.add(virtualResource1);
        Assert.assertTrue(virtualResources.size() == 2);
        when(virtualResource.getOrder()).thenReturn(first);
        when(virtualResource1.getOrder()).thenReturn(second);
        result = IntentResolverUtils.sortVirtualResources(virtualResources);
        Assert.assertTrue(result.size() == 2);
        Assert.assertTrue(result.get(0) == virtualResource);

    }
}