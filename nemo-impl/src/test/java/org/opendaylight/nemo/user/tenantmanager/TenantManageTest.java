package org.opendaylight.nemo.user.tenantmanager;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.ListenableFuture;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
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
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zhangmeng on 2015/11/5.
 */
public class TenantManageTest extends TestCase {
    private DataBroker dataBroker;
    private List<UserRole> userRoleList;
    private List<User> usersList ;
    private User user;
    TenantManage tenantManage;
    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        user = mock(User.class);
        tenantManage = new TenantManage(dataBroker);

    }

    @Test
    public void testGetUserRoleList() throws Exception {

    }

    @Test
    public void testGetUsersList() throws Exception {

    }

    @Test
    public void testGetUser() throws Exception {

    }

    @Test
    public void testFetchUserRoles() throws Exception {

    }

    @Test
    public void testFetchUsers() throws Exception {
//        InstanceIdentifier<Users> usersInsId = InstanceIdentifier.builder(Users.class).build();
//        ListenableFuture<Optional<Users>> usersFuture = dataBroker.newReadOnlyTransaction().read(LogicalDatastoreType.CONFIGURATION, usersInsId);
//        Futures.addCallback(usersFuture, new FutureCallback<Optional<Users>>() {
//            @Override
//            public void onSuccess(Optional<Users> result)
//            {
//                setUserList(result.get().getUser());
//                return;
//            }
//
//            @Override
//            public void onFailure(Throwable t)
//            {
//                LOG.error("Can not read users information.", t);
//
//                return;
//            }
//        });
        //InstanceIdentifier<Users> usersInsId = mock(InstanceIdentifier.class)
        ReadOnlyTransaction transaction = mock(ReadOnlyTransaction.class);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(transaction);
        CheckedFuture usersFuture = mock(CheckedFuture.class);
        when(transaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(usersFuture);


    }

    @Test
    public void testFetchVNSpace() throws Exception {

    }

    @Test
    public void testAddUser() throws Exception {

    }
}