/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.advancedquery;
import org.opendaylight.nemo.user.tenantmanager.AAA;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.AdvancedNemoQueryInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.advanced.nemo.query.input.QueryCondition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Operations;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Results;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.MatchItem;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.flow.instance.Property;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.property.instance.property.values.IntValue;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserPassword;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserRoleName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.QueryConditionName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.query.condition.instance.QueryConditionTargetValue;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.QueryConditionInstance.QueryIntentType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.QueryConditionInstance.QueryConditionMatchPattern;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.QueryConditionInstance;

import java.util.List;
import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.*;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import static org.mockito.Mockito.*;
/*
2015/11/28
build by Gao Jie
*/
public class AdvancedQueryTest{
		private DataBroker dataBroker;
        private TenantManage tenantManage;
		private AdvancedQuery advancedquery;
        private AAA aaa;
		private UserId userid;
		private UserName username;
		private UserPassword userpassword;
		private UserRoleName userrolename;
        private AdvancedNemoQueryInput input;
		private QueryCondition querycondition;
        private List<QueryCondition> queryconditionlist;
        private AdvancedNemoQueryInput advancedQueryinput;		
@org.junit.Before
public void setUp() throws Exception{
	dataBroker=mock(DataBroker.class);
	tenantManage=mock(TenantManage.class);
	advancedquery=new AdvancedQuery(dataBroker,tenantManage);
	aaa=mock(AAA.class);
    input=mock(AdvancedNemoQueryInput.class);
	userid=mock(UserId.class);
	username=mock(UserName.class);
	userpassword=mock(UserPassword.class);
	userrolename=mock(UserRoleName.class);
	querycondition=mock(QueryCondition.class);
	queryconditionlist=new ArrayList<QueryCondition>(3);
	queryconditionlist.add(querycondition);
    advancedQueryinput=mock(AdvancedNemoQueryInput.class);	
} 
@org.junit.Test
public void advancedQueryTest() throws Exception{
	//branch 1
	when(input.getUserId()).thenReturn(userid);
	when(aaa.checkUser(input.getUserId())).thenReturn("The password is not right.");
    Assert.assertEquals(advancedquery.advancedQuery(aaa,input),"The password is not right.");
	//branch 2
	when(input.getUserId()).thenReturn(userid);
	when(aaa.checkUser(input.getUserId())).thenReturn(null);
    //if-
    when(input.getQueryCondition()).thenReturn(null);
	 Assert.assertEquals(advancedquery.advancedQuery(aaa,input),null);
	 //branch3
	when(input.getUserId()).thenReturn(userid);
	when(aaa.checkUser(input.getUserId())).thenReturn(null);
	//if-
	List<QueryCondition> queryconditionlist=new ArrayList<QueryCondition>(3);
	queryconditionlist.add(querycondition);
	when(input.getQueryCondition()).thenReturn(queryconditionlist);
    //if--
	when(querycondition.getQueryConditionName()).thenReturn(null);
	 //Assert.assertEquals(advancedquery.advancedQuery(aaa,input),"The condition has not been defined.");
	}
@org.junit.Test
public void getAdvancedQueryReusltTest(){
	//branch 1
	when(input.getUserId()).thenReturn(null);
	when(input.getQueryCondition()).thenReturn(queryconditionlist);
	when(querycondition.getQueryIntentType()).thenReturn(null);
	Assert.assertEquals(advancedquery.getAdvancedQueryReuslt(input),null);
	//branch 2 
	when(input.getUserId()).thenReturn(null);
	when(input.getQueryCondition()).thenReturn(queryconditionlist);
	when(querycondition.getQueryIntentType()).thenReturn(null);
	Assert.assertEquals(advancedquery.getAdvancedQueryReuslt(input),null);
}
}