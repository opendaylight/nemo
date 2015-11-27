package org.opendaylight.nemo.user.vnspacemanager.instancecheck;


import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.EndNode;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.ListenableFuture;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.RegisterUserInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.user.rev151010.UserRoles;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.user.rev151010.user.roles.UserRole;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.PropertyName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.NodeDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.definitions.NodeDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.node.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.definitions.PropertyDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.PropertyValues;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Thomas Liu on 2015/11/20.
 */
public class ConnectionInstanceCheckTest extends junit.framework.TestCase {
    private ConnectionInstanceCheck connectionInstanceCheckTest;
    private TenantManage tenantManage;
    private DataBroker dataBroker;
    private UserId userId,userId2;
    private Connection connection;
    private User user;
    private List<Node> nodes;
    private List<EndNode> endnodes;;
    private Objects object;
    private List<Connection> connlist;
    private ConnectionId connectionId,connectionId2;
    private ConnectionType connectionType,connectionType2;
    private ConnectionName connectionName,connectionName2;
    private NodeId nodeId,nodeId2;
    @org.junit.Before
    public void setUp() throws Exception {
        //dataBroker = mock(DataBroker.class);
        //tenantManage = new TenantManage(dataBroker);
        tenantManage = mock(TenantManage.class);
        connectionInstanceCheckTest = new ConnectionInstanceCheck(tenantManage);
        userId = mock(UserId.class);
        userId2 = mock(UserId.class);
        connection = mock(Connection.class);
        user = mock(User.class);
        nodes= new ArrayList<Node>();
        nodes.add(mock(Node.class));
        endnodes= new ArrayList<EndNode>();
        endnodes.add(mock(EndNode.class));
        object = mock(Objects.class);
        connlist= new ArrayList<Connection>();
        connlist.add(mock(Connection.class));
        connectionId = mock(ConnectionId.class);
        connectionId2 = mock(ConnectionId.class);
        connectionType = mock(ConnectionType.class);
        connectionType2 = mock(ConnectionType.class);
        connectionName = mock(ConnectionName.class);
        connectionName2 = mock(ConnectionName.class);
        nodeId = mock(NodeId.class);
        nodeId2 = mock(NodeId.class);
    }


    @org.junit.Test
    public void testCheckConnInstance() throws Exception {
        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);
        when(user.getObjects()).thenReturn(object);
        when(user.getObjects().getConnection()).thenReturn(connlist);
        when(tenantManage.getUser().getObjects().getConnection()).thenReturn(connlist);
        when(connlist.get(0).getConnectionId()).thenReturn(connectionId);
        when(connection.getConnectionId()).thenReturn(connectionId2);
        //when(connectionId.equals(any(Class.class))).thenReturn(true);
        when(connlist.get(0).getConnectionType()).thenReturn(connectionType);
        when(connection.getConnectionType()).thenReturn(connectionType2);
        //when(connectionId.equals(any(Class.class))).thenReturn(false);
        when(connlist.get(0).getConnectionName()).thenReturn(connectionName);
        when(connection.getConnectionName()).thenReturn(connectionName2);
        //when(connectionId.equals(any(Class.class))).thenReturn(false);
        when(user.getObjects().getNode()).thenReturn(null);
        connectionInstanceCheckTest.checkConnInstance(userId,connection);
        verify(tenantManage).fetchVNSpace(userId);
        verify(tenantManage,times(3)).getUser();
        verify(user,times(7)).getObjects();
        verify(user.getObjects(),times(2)).getConnection();
        verify(connlist.get(0)).getConnectionId();
        verify(connection).getConnectionId();





        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);
        when(user.getObjects()).thenReturn(object);
        when(user.getObjects().getConnection()).thenReturn(null);
        when(user.getObjects().getNode()).thenReturn(nodes);
        when(connection.getEndNode()).thenReturn(endnodes);
        when(user.getObjects().getNode()).thenReturn(nodes);
        when(nodes.get(0).getNodeId()).thenReturn(nodeId);
        when(endnodes.get(0).getNodeId()).thenReturn(nodeId2);
        //when(nodeId.equals(endnodes.get(0).getNodeId())).thenReturn(true);
        connectionInstanceCheckTest.checkConnInstance(userId,connection);
        verify(tenantManage,times(2)).fetchVNSpace(userId);
        verify(tenantManage,times(4)).getUser();
        verify(user,times(15)).getObjects();
        //verify(user,times(15)).getObjects().getConnection();
        //verify(user,times(15)).getObjects().getNode();
        verify(connection).getEndNode();
        //verify(user).getObjects().getNode();
        verify(nodes.get(0)).getNodeId();
        //verify(nodeId).equals(endnodes.get(0).getNodeId());



        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);
        when(user.getObjects()).thenReturn(null);
        when(connection.getEndNode()).thenReturn(endnodes);
        connectionInstanceCheckTest.checkConnInstance(userId,connection);
        verify(tenantManage,times(3)).fetchVNSpace(userId);
        verify(tenantManage,times(5)).getUser();
        verify(user,times(16)).getObjects();
        verify(connection,times(2)).getEndNode();

        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);
        when(user.getObjects()).thenReturn(null);
        when(connection.getEndNode()).thenReturn(null);
        connectionInstanceCheckTest.checkConnInstance(userId,connection);
        verify(tenantManage,times(4)).fetchVNSpace(userId);
        verify(tenantManage,times(6)).getUser();
        verify(user,times(17)).getObjects();
        verify(connection,times(3)).getEndNode();



        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(null);
        when(connection.getEndNode()).thenReturn(endnodes);
        connectionInstanceCheckTest.checkConnInstance(userId,connection);
        verify(tenantManage,times(5)).fetchVNSpace(userId);
        verify(tenantManage,times(7)).getUser();
        verify(connection,times(4)).getEndNode();


        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(null);
        when(connection.getEndNode()).thenReturn(null);
        connectionInstanceCheckTest.checkConnInstance(userId,connection);
        verify(tenantManage,times(6)).fetchVNSpace(userId);
        verify(tenantManage,times(8)).getUser();
        verify(connection,times(5)).getEndNode();
    }
}