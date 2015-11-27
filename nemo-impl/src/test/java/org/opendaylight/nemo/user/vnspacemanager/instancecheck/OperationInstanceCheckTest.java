package org.opendaylight.nemo.user.vnspacemanager.instancecheck;

import org.opendaylight.nemo.user.tenantmanager.TenantManage;


import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Operations;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ObjectId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.OperationId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.OperationName;



import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas Liu on 2015/11/23.
 */
public class OperationInstanceCheckTest extends junit.framework.TestCase {
    private OperationInstanceCheck perationInstanceCheckTest;
    private TenantManage tenantManage;
    private UserId userId;
    private Node node;
    private User user;
    private List<Connection> connectionList;
    private List<Flow> flowList;
    private Objects object;
    //private Operation operation;
    private ObjectId objectId;
    private List<Node> nodeList;
    private List<Operation> operationList;
    private Operation operation;
    private NodeId nodeId,nodeId2;
    private ConnectionId connectionId,connectionId2;
    private FlowId flowId,flowId2;
    private Operations operations;
    private OperationId operationId;
    private OperationName operationName;

    @org.junit.Before
    public void setUp() throws Exception {
        tenantManage = mock(TenantManage.class);
        perationInstanceCheckTest = new OperationInstanceCheck(tenantManage);
        userId = mock(UserId.class);
        node = mock(Node.class);
        user = mock(User.class);
        object = mock(Objects.class);
        operation = mock(Operation.class);
        objectId = mock(ObjectId.class);
        nodeList = new ArrayList<Node>();
        nodeList.add(mock(Node.class));
        connectionList = new ArrayList<Connection>();
        connectionList.add(mock(Connection.class));
        flowList = new ArrayList<Flow>();
        flowList.add(mock(Flow.class));
        operationList = new ArrayList<Operation>();
        operationList.add(mock(Operation.class));
        nodeId = mock(NodeId.class);
        nodeId2 = mock(NodeId.class);
        connectionId = mock(ConnectionId.class);
        connectionId2 = mock(ConnectionId.class);
        flowId = mock(FlowId.class);
        flowId2 = mock(FlowId.class);
        operationId = mock(OperationId.class);
        operationName = mock(OperationName.class);


    }

    @org.junit.Test
    public void testCheckOperationInstance() throws Exception {

        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);
        when(operation.getTargetObject()).thenReturn(objectId);
        when(user.getObjects()).thenReturn(null);
        perationInstanceCheckTest.checkOperationInstance(userId,operation);
        verify(tenantManage).fetchVNSpace(userId);
        verify(tenantManage).getUser();
        verify(operation,times(2)).getTargetObject();
        verify(user,times(2)).getObjects();


        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);
        when(operation.getTargetObject()).thenReturn(objectId);
        when(user.getObjects()).thenReturn(object);
        when(user.getObjects()).thenReturn(object);

        when(object.getNode()).thenReturn(nodeList);
        when(object.getNode()).thenReturn(nodeList);
        when(nodeList.get(0).getNodeId()).thenReturn(nodeId);
        when(nodeId.getValue()).thenReturn(new String("1"));
        when(operation.getTargetObject()).thenReturn(nodeId2);
        when(nodeId2.getValue()).thenReturn(new String("1"));

        when(object.getConnection()).thenReturn(connectionList);
        when(object.getConnection()).thenReturn(connectionList);
        when(connectionList.get(0).getConnectionId()).thenReturn(connectionId);
        when(connectionId.getValue()).thenReturn(new String("1"));
        when(operation.getTargetObject()).thenReturn(connectionId2);
        when(connectionId2.getValue()).thenReturn(new String("1"));

        when(object.getFlow()).thenReturn(flowList);
        when(object.getFlow()).thenReturn(flowList);
        when(flowList.get(0).getFlowId()).thenReturn(flowId);
        when(flowId.getValue()).thenReturn(new String("1"));
        when(operation.getTargetObject()).thenReturn(flowId2);
        when(flowId2.getValue()).thenReturn(new String("1"));
        perationInstanceCheckTest.checkOperationInstance(userId,operation);
        verify(tenantManage,times(2)).fetchVNSpace(userId);
        verify(tenantManage,times(2)).getUser();
        verify(operation,times(7)).getTargetObject();
        verify(user,times(5)).getObjects();
        verify(object,times(2)).getNode();
        verify(nodeList.get(0)).getNodeId();
        verify(nodeId).getValue();
        verify(object,times(2)).getConnection();
        verify(connectionList.get(0)).getConnectionId();
        verify(connectionId).getValue();
        verify(object,times(2)).getFlow();
        verify(flowList.get(0)).getFlowId();
        verify(flowId).getValue();
        verify(flowId2,times(3)).getValue();

        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);
        when(operation.getTargetObject()).thenReturn(objectId);
        when(user.getObjects()).thenReturn(object);
        when(user.getObjects()).thenReturn(object);

        when(object.getNode()).thenReturn(nodeList);
        when(object.getNode()).thenReturn(nodeList);
        when(nodeList.get(0).getNodeId().getValue()).thenReturn(new String("1"));
        when(operation.getTargetObject().getValue()).thenReturn(new String("2"));

        when(object.getConnection()).thenReturn(connectionList);
        when(object.getConnection()).thenReturn(connectionList);
        when(connectionList.get(0).getConnectionId().getValue()).thenReturn(new String("1"));
        when(operation.getTargetObject().getValue()).thenReturn(new String("2"));

        when(object.getFlow()).thenReturn(flowList);
        when(object.getFlow()).thenReturn(flowList);
        when(flowList.get(0).getFlowId().getValue()).thenReturn(new String("1"));
        when(operation.getTargetObject().getValue()).thenReturn(new String("2"));

        perationInstanceCheckTest.checkOperationInstance(userId,operation);
        verify(tenantManage,times(3)).fetchVNSpace(userId);
        verify(tenantManage,times(3)).getUser();
        verify(operation,times(15)).getTargetObject();
        verify(user,times(8)).getObjects();
        verify(object,times(4)).getNode();
        verify(nodeList.get(0),times(3)).getNodeId();
        verify(nodeId,times(2)).getValue();
        verify(object,times(4)).getConnection();
        verify(connectionList.get(0),times(3)).getConnectionId();
        verify(connectionId,times(2)).getValue();
        verify(object,times(4)).getFlow();
        verify(flowList.get(0),times(3)).getFlowId();
        verify(flowId,times(2)).getValue();
        verify(flowId2,times(3)).getValue();

/*
        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);
        when(operation.getTargetObject()).thenReturn(null);
        when(user.getObjects()).thenReturn(object);
        when(user.getOperations()).thenReturn(operations);
        when(user.getOperations().getOperation()).thenReturn(operationList);
        when(tenantManage.getUser().getOperations().getOperation()).thenReturn(operationList);
        when(operationList.get(0).getOperationId()).thenReturn(operationId);
        when(operation.getOperationId()).thenReturn(operationId);
        when(operationList.get(0).getOperationName()).thenReturn(operationName);
        when(operation.getOperationName()).thenReturn(operationName);
        perationInstanceCheckTest.checkOperationInstance(userId,operation);*/





        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(null);
        when(operation.getTargetObject()).thenReturn(objectId);
        perationInstanceCheckTest.checkOperationInstance(userId,operation);
        verify(tenantManage,times(4)).fetchVNSpace(userId);
        verify(tenantManage,times(4)).getUser();
        verify(operation,times(16)).getTargetObject();

        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(null);
        when(operation.getTargetObject()).thenReturn(null);
        perationInstanceCheckTest.checkOperationInstance(userId,operation);
        verify(tenantManage,times(5)).fetchVNSpace(userId);
        verify(tenantManage,times(5)).getUser();
        verify(operation,times(17)).getTargetObject();


        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(null);
        when(operation.getTargetObject()).thenReturn(null);
        perationInstanceCheckTest.checkOperationInstance(userId,operation);
        verify(tenantManage,times(6)).fetchVNSpace(userId);
        verify(tenantManage,times(6)).getUser();
        verify(operation,times(18)).getTargetObject();



        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);
        when(operation.getTargetObject()).thenReturn(null);
        when(user.getObjects()).thenReturn(object);
        when(user.getOperations()).thenReturn(null);
        perationInstanceCheckTest.checkOperationInstance(userId,operation);
        verify(tenantManage,times(7)).fetchVNSpace(userId);
        verify(tenantManage,times(7)).getUser();
        verify(operation,times(20)).getTargetObject();
        verify(user,times(8)).getObjects();
        verify(user,times(2)).getOperations();




        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);
        when(operation.getTargetObject()).thenReturn(objectId);
        when(user.getObjects()).thenReturn(object);
        when(user.getObjects()).thenReturn(object);

        when(object.getNode()).thenReturn(null);
        when(object.getConnection()).thenReturn(null);
        when(object.getFlow()).thenReturn(null);

        perationInstanceCheckTest.checkOperationInstance(userId,operation);
        verify(tenantManage,times(8)).fetchVNSpace(userId);
        verify(tenantManage,times(8)).getUser();
        verify(operation,times(22)).getTargetObject();
        verify(user,times(11)).getObjects();
        verify(object,times(5)).getNode();
        verify(object,times(5)).getConnection();
        verify(object,times(5)).getFlow();



    }
}