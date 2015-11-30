package org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.*;
import static org.mockito.Mockito.mock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.FlowKey;
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
import java.util.*;
import java.util.List;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
public class DeleteFlowTest{
	private DataBroker dataBroker;
    private TenantManage tenantManage;
	private DeleteFlow deleteflow;
	private UserId userId;
    private	FlowId flowId;
	private Objects objects;
	private User  user;
	private List<Flow> flowlist;
	private Flow flow;
	private FlowId notflowId;
	private   WriteTransaction  writetransaction;
	@org.junit.Before
	public void setUp() throws Exception {
		dataBroker=mock(DataBroker.class);
		tenantManage=mock(TenantManage.class);
		deleteflow=new DeleteFlow(dataBroker,tenantManage);
		userId=mock(UserId.class);
		flowId=mock(FlowId.class);
		objects=mock(Objects.class);
		user=mock(User.class);
		flow=mock(Flow.class);
		notflowId=mock(FlowId.class);
		flowlist=new ArrayList<Flow>(1);
		flowlist.add(flow);
		writetransaction=mock(WriteTransaction.class);
	}
	@org.junit.Test
	public void  DeleteFlowHandlingTest() throws Exception{
	 CheckedFuture connectiondefinitionFuture = mock(CheckedFuture.class);
     ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
	 when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);

     //no data test
	 when(tenantManage.getUser()).thenReturn(null);
	 Assert.assertEquals(deleteflow.DeleteFlowHandling(userId,flowId),"There are no user in the data store.");
	 //data exists and other branches
	 //branch 1
	when(tenantManage.getUser()).thenReturn(user);
	when(user.getObjects()).thenReturn(null);
	Assert.assertEquals(deleteflow.DeleteFlowHandling(userId,flowId),null);
	//branch 2
	when(tenantManage.getUser()).thenReturn(user);
	when(user.getObjects()).thenReturn(objects);
	when(user.getObjects().getFlow()).thenReturn(null);
	Assert.assertEquals(deleteflow.DeleteFlowHandling(userId,flowId),"There are no flow instances in the data store.");
	//branch 3
	when(tenantManage.getUser()).thenReturn(user);
	when(user.getObjects()).thenReturn(objects);
	when(user.getObjects().getFlow()).thenReturn(flowlist);
	when(flow.getFlowId()).thenReturn(notflowId);
	Assert.assertEquals(deleteflow.DeleteFlowHandling(userId,flowId),"The flow instance" +flowId.toString()+"is not exist. Could not be deleted.");
	//branch 4
	when(tenantManage.getUser()).thenReturn(user);
	when(user.getObjects()).thenReturn(objects);
	when(user.getObjects().getFlow()).thenReturn(flowlist);
	when(flow.getFlowId()).thenReturn(flowId);
	when(dataBroker.newWriteOnlyTransaction()).thenReturn(writetransaction);
	CheckedFuture<Void, TransactionCommitFailedException> f;
	f=mock(CheckedFuture.class);
	when(writetransaction.submit()).thenReturn(f);
	Assert.assertEquals(deleteflow.DeleteFlowHandling(userId,flowId),null);
	}
}
