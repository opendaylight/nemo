/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.intent.IntentResolutionException;
import org.opendaylight.nemo.intent.IntentResolver;
import org.opendaylight.nemo.intent.computation.VNMappingException;
import org.opendaylight.nemo.user.advancedquery.AdvancedQuery;
import org.opendaylight.nemo.user.tenantmanager.AAA;
import org.opendaylight.nemo.user.tenantmanager.RegisterUser;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.transactionmanager.TransactionBegin;
import org.opendaylight.nemo.user.transactionmanager.TransactionEnd;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.LanguageIntent;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOParse.ParseException;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOParse.SimpleCharStream;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOParse.NEMOparserTokenManager;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent.DeleteIntent;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateIntent;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.*;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.advanced.nemo.query.input.QueryCondition;
import java.util.concurrent.Future;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import static org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.CommonRpcResult.ResultCode.Error;
import static org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.CommonRpcResult.ResultCode.Ok;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.*;
import java.util.Map;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import com.google.common.base.Function;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOParse.NEMOparser;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import com.google.common.base.Optional;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.user.rev151010.UserRoles;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import static org.mockito.Mockito.*;
public class UserManagerTest{
	private DataBroker dataBroker;
    private LanguageIntent languageIntent;
    private TenantManage tenantManage;
	private IntentResolver intentResolver;
	UserManager usermanager1;
	@org.junit.Before
	public  void setUp()throws Exception{
	dataBroker=mock(DataBroker.class);
    intentResolver=mock(IntentResolver.class);
	tenantManage=mock(TenantManage.class);
	
    Class<NEMOparser> class1 = NEMOparser.class;
   Field field = class1.getDeclaredField("jj_initialized_once");
   field.setAccessible(true);
   field.set(usermanager1,false);
   
   Class<SimpleCharStream> class2=SimpleCharStream.class;
   Field field1=class2.getDeclaredField("inputStream");
   field1.setAccessible(true);
   field1.set(usermanager1,null);
   
   Class<NEMOparserTokenManager> class3=NEMOparserTokenManager.class;
   Field field2=class3.getDeclaredField("input_stream");
   field2.setAccessible(true);
   field2.set(usermanager1,null);
   usermanager1=new UserManager(dataBroker,intentResolver);   
	}

 	@org.junit.Test 
	public void advancedNemoQueryTest()throws Exception{
		
		AdvancedNemoQueryInput input =mock(AdvancedNemoQueryInput.class);
        Map<UserId, User> result=new HashMap<UserId, User>();
        Users users = mock(Users.class);
        User user = mock(User.class);
        UserId userId = mock(UserId.class);
		UserId userId1 = mock(UserId.class);
	    result.put(userId,user);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);
        Optional<Users> usersOpt = Optional.of(users);
        List<User> userList = new ArrayList<User>();
		
		
		userList.add(user);
		
		when(input.getUserId()).thenReturn(userId1);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(checkedFuture);
        when(checkedFuture.get()).thenReturn(usersOpt);
        when(users.getUser()).thenReturn(userList);
        when(user.getUserId()).thenReturn(userId);
		AdvancedNemoQueryOutputBuilder outputBuilder = new AdvancedNemoQueryOutputBuilder();
		outputBuilder.setResultCode(Error).setMessage("The user is not exist.");
        Assert.assertNotNull(usermanager1.advancedNemoQuery(input)); 
	} 
		
 		
	@Test 
	public void beginTransactionTest()throws Exception{
        IntentResolver intentResolver1=mock(IntentResolver.class);
        BeginTransactionInput input=mock(BeginTransactionInput.class);
	    BeginTransactionOutputBuilder outputBuilder = new BeginTransactionOutputBuilder();
	     Map<UserId, User> result;
        Users users = mock(Users.class);
        User user = mock(User.class);
        UserId userId = mock(UserId.class);
		UserId userId1 = mock(UserId.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);
        Optional<Users> usersOpt = Optional.of(users);
        List<User> userList = new ArrayList<User>();
		
		
		userList.add(user);
	
	    when(input.getUserId()).thenReturn(userId1);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(checkedFuture);
        when(checkedFuture.get()).thenReturn(usersOpt);
        when(users.getUser()).thenReturn(userList);
        when(user.getUserId()).thenReturn(userId);
    	Assert.assertNotNull(usermanager1.beginTransaction(input)); 
	}
	@Test
   public void 	endTransactionTest() throws Exception{
	   usermanager1.transaction=false;
	   EndTransactionInput input=mock(EndTransactionInput.class);
	   Assert.assertNotNull(usermanager1.endTransaction(input)); 
	   
	   
	   usermanager1.transaction=true;
	     Map<UserId, User> result;
        Users users = mock(Users.class);
        User user = mock(User.class);
        UserId userId = mock(UserId.class);
		UserId userId1 = mock(UserId.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);
        Optional<Users> usersOpt = Optional.of(users);
        List<User> userList = new ArrayList<User>();
		
		
		userList.add(user);
	
	    when(input.getUserId()).thenReturn(userId1);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(checkedFuture);
        when(checkedFuture.get()).thenReturn(usersOpt);
        when(users.getUser()).thenReturn(userList);
        when(user.getUserId()).thenReturn(userId);
	   usermanager1.informresolver=false;
	   Assert.assertNotNull(usermanager1.endTransaction(input));   
   }
   @Test
   public void languageStyleNemoRequestTest() throws Exception{
	LanguageStyleNemoRequestInput input=mock(LanguageStyleNemoRequestInput.class);
     Map<UserId, User> result;
        Users users = mock(Users.class);
        User user = mock(User.class);
        UserId userId = mock(UserId.class);
		UserId userId1 = mock(UserId.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);
        Optional<Users> usersOpt = Optional.of(users);
        List<User> userList = new ArrayList<User>();
		
		
		userList.add(user);
	
	    when(input.getUserId()).thenReturn(userId1);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(checkedFuture);
        when(checkedFuture.get()).thenReturn(usersOpt);
        when(users.getUser()).thenReturn(userList);
        when(user.getUserId()).thenReturn(userId);
		Assert.assertNotNull(usermanager1.languageStyleNemoRequest(input));  
   }
   @Test 
   public void registerUserTest() throws Exception{
	 RegisterUserInput input=mock(RegisterUserInput.class); 
	   Map<UserId, User> result=new HashMap<UserId, User>();
        Users users = mock(Users.class);
		UserRoles userRoles = mock(UserRoles.class);
        User user = mock(User.class);
        UserId userId = mock(UserId.class);
		
	    result.put(userId,user);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);
        Optional<Users> usersOpt = Optional.of(users);
        List<User> userList = new ArrayList<User>();
		Optional<UserRoles> userrolesOpt = Optional.of(userRoles);
		
		userList.add(user);
		
		when(input.getUserId()).thenReturn(userId).thenReturn(userId);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(checkedFuture).thenReturn(checkedFuture);
        when(checkedFuture.get()).thenReturn(userrolesOpt).thenReturn(usersOpt);
        when(users.getUser()).thenReturn(userList);
        when(user.getUserId()).thenReturn(userId);
		Assert.assertNotNull(usermanager1.registerUser(input)); 
		}
	@Test
	public void structureStyleNemoDeleteTest() throws Exception{
	StructureStyleNemoDeleteInput input=mock(StructureStyleNemoDeleteInput.class);
     Map<UserId, User> result=new HashMap<UserId, User>();
        Users users = mock(Users.class);
        User user = mock(User.class);
        UserId userId = mock(UserId.class);
		UserId userId1 = mock(UserId.class);
	    result.put(userId,user);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);
        Optional<Users> usersOpt = Optional.of(users);
        List<User> userList = new ArrayList<User>();
		
		
		userList.add(user);
		
		when(input.getUserId()).thenReturn(userId1);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(checkedFuture);
        when(checkedFuture.get()).thenReturn(usersOpt);
        when(users.getUser()).thenReturn(userList);
        when(user.getUserId()).thenReturn(userId);
		AdvancedNemoQueryOutputBuilder outputBuilder = new AdvancedNemoQueryOutputBuilder();
		outputBuilder.setResultCode(Error).setMessage("The user is not exist.");
        Assert.assertNotNull(usermanager1.structureStyleNemoDelete(input));	
	}
	public void structureStyleNemoUpdateTest() throws Exception{
	StructureStyleNemoUpdateInput input=mock(StructureStyleNemoUpdateInput.class);
	     Map<UserId, User> result=new HashMap<UserId, User>();
        Users users = mock(Users.class);
        User user = mock(User.class);
        UserId userId = mock(UserId.class);
		UserId userId1 = mock(UserId.class);
	    result.put(userId,user);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        CheckedFuture checkedFuture = mock(CheckedFuture.class);
        Optional<Users> usersOpt = Optional.of(users);
        List<User> userList = new ArrayList<User>();
		
		
		userList.add(user);
		
		when(input.getUserId()).thenReturn(userId1);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(checkedFuture);
        when(checkedFuture.get()).thenReturn(usersOpt);
        when(users.getUser()).thenReturn(userList);
        when(user.getUserId()).thenReturn(userId);
		AdvancedNemoQueryOutputBuilder outputBuilder = new AdvancedNemoQueryOutputBuilder();
		outputBuilder.setResultCode(Error).setMessage("The user is not exist.");
        Assert.assertNotNull(usermanager1.structureStyleNemoUpdate(input));
	}
}

