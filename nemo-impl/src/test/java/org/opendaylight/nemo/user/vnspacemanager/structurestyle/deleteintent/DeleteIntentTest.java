package org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.*;
import static org.mockito.Mockito.mock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.AAA;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.OperationId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.StructureStyleNemoDeleteInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.structure.style.nemo.delete.input.Results;

import java.util.List;
public class DeleteIntentTest{
	private DataBroker dataBroker;
    private TenantManage tenantManage;
    private DeleteNode deleteNode;
    private DeleteConnection deleteConnection;
    private DeleteFlow deleteFlow;
    private DeleteOperation deleteOperation;
    private DeleteResult deleteResult;
	private AAA aaa;
	private StructureStyleNemoDeleteInput styleNemoDeleteInput;
	private DeleteIntent deleteintent;
@org.junit.Before
public void setUp() throws Exception{
	dataBroker=mock(DataBroker.class);
	tenantManage=mock(TenantManage.class);
	deleteNode=mock(DeleteNode.class);
	deleteConnection=mock(DeleteConnection.class);
	deleteFlow=mock(DeleteFlow.class);
	deleteOperation=mock(DeleteOperation.class);
	deleteResult=mock(DeleteResult.class);
	aaa=mock(AAA.class);
	styleNemoDeleteInput=mock(StructureStyleNemoDeleteInput.class);
	deleteintent=mock(DeleteIntent.class);
}
@org.junit.Test
public void styleNemoDeleteOutputTest(){
	Assert.assertNull(deleteintent.styleNemoDeleteOutput(aaa,styleNemoDeleteInput));
}
}