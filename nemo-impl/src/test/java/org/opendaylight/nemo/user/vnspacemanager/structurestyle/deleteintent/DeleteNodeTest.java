package org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.*;
import static org.mockito.Mockito.mock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.NodeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.*;
public class DeleteNodeTest{
	private DataBroker dataBroker;
    private TenantManage tenantManage;
	private UserId  userid;
	private NodeId  nodeid;
	private DeleteNode deletenode;
	private User user;
	private Objects objects;
	private  Node node;
	private NodeId notnodeid;
	private List<Node> nodeList;
	private   WriteTransaction  writetransaction;
	@org.junit.Before
	public void setUp() throws Exception{
		dataBroker=mock(DataBroker.class);
		tenantManage=mock(TenantManage.class);
        userid=mock(UserId.class);	
		nodeid=mock(NodeId.class);
		deletenode=new DeleteNode(dataBroker,tenantManage);
		user=mock(User.class);
		objects=mock(Objects.class);
		node=mock(Node.class);
		notnodeid=mock(NodeId.class);
		nodeList=new ArrayList<Node>(1);
		nodeList.add(node);
		writetransaction=mock(WriteTransaction.class);
	}
	@org.junit.Test
    public void DeleNodeHandlingTest(){
	 CheckedFuture connectiondefinitionFuture = mock(CheckedFuture.class);
     ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
	 when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
    //branch 1
    when(tenantManage.getUser()).thenReturn(null);
    Assert.assertEquals(deletenode.DeleNodeHandling(userid,nodeid),"There are no user in data store.");	
	//branch 2
	when(tenantManage.getUser()).thenReturn(user);
	when(user.getObjects()).thenReturn(null);
	Assert.assertEquals(deletenode.DeleNodeHandling(userid,nodeid),null);
   //branch 3
	when(tenantManage.getUser()).thenReturn(user);
	when(user.getObjects()).thenReturn(objects);
	when(objects.getNode()).thenReturn(null);
	Assert.assertEquals(deletenode.DeleNodeHandling(userid,nodeid),"There are no nodes instances in data store.");
	//branch 4
	when(tenantManage.getUser()).thenReturn(user);
	when(user.getObjects()).thenReturn(objects);
	when(objects.getNode()).thenReturn(nodeList);
	when(node.getNodeId()).thenReturn(notnodeid);
	Assert.assertEquals(deletenode.DeleNodeHandling(userid,nodeid),"The node instance" +nodeid.toString()+"is not exist.Could not be deleted");
	//branch 5
	when(tenantManage.getUser()).thenReturn(user);
	when(user.getObjects()).thenReturn(objects);
	when(objects.getNode()).thenReturn(nodeList);
	when(node.getNodeId()).thenReturn(nodeid);
	when(dataBroker.newWriteOnlyTransaction()).thenReturn(writetransaction);
	CheckedFuture<Void, TransactionCommitFailedException> f;
	f=mock(CheckedFuture.class);
	when(writetransaction.submit()).thenReturn(f);
	Assert.assertEquals(deletenode.DeleNodeHandling(userid,nodeid),null);
	}
}
