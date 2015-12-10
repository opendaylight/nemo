/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package user.vnspacemanager.languagestyle.deleteintentlang;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.deleteintentlang.DeleteOperationLang;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent.DeleteOperation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Operations;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.OperationName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.OperationId;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/12/10.
 */
public class DeleteOperationLangTest extends TestCase {
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private DeleteOperationLang deleteOperationLang;
    private DeleteOperation deleteOperation;

    @Before
    public void setUp() throws Exception {
        dataBroker = mock(DataBroker.class);
        tenantManage = mock(TenantManage.class);
        deleteOperation = mock(DeleteOperation.class);

        deleteOperationLang = new DeleteOperationLang(dataBroker,tenantManage);
    }

    @Test
    public void testDeleteOperationHandling() throws Exception {
        UserId userId = mock(UserId.class);
        User user = mock(User.class);
        OperationName operationName = mock(OperationName.class);
        Operation operation = mock(Operation.class);
        Operations operations = mock(Operations.class);
        OperationId operationId = mock(OperationId.class);
        List<Operation> operationList = new ArrayList<Operation>();
        String operationname = new String("test");
        String result;

        doNothing().when(tenantManage).fetchVNSpace(userId);
        when(tenantManage.getUser()).thenReturn(user);
        when(user.getOperations())
                .thenReturn(null)
                .thenReturn(operations);

        result = deleteOperationLang.DeleteOperationHandling(userId,operationname);
        Assert.assertTrue(result.equals("The operation " + operationname + " is not exist in this user vn space."));

        when(operations.getOperation()).thenReturn(operationList);
        result = deleteOperationLang.DeleteOperationHandling(userId,operationname);
        Assert.assertTrue(operationList.isEmpty());
        Assert.assertTrue(result.equals("The operation " + operationname + " is not exist in this user vn space."));

        operationList.add(operation);
        when(operation.getOperationName()).thenReturn(operationName);
        when(operationName.getValue())
                .thenReturn("")
                .thenReturn(operationname);
        result = deleteOperationLang.DeleteOperationHandling(userId,operationname);
        Assert.assertTrue(result.equals("The operation " + operationname + " is not exist in this user vn space."));

        Class<DeleteOperationLang> class1 = DeleteOperationLang.class;
        Field field = class1.getDeclaredField("deleteOperation");
        field.setAccessible(true);
        field.set(deleteOperationLang, deleteOperation);
        System.out.println(field.get(deleteOperationLang));
        when(operation.getOperationId()).thenReturn(operationId);
        when(deleteOperation.DeleteOperationhandling(any(UserId.class), any(OperationId.class))).thenReturn(operationname);
        result = deleteOperationLang.DeleteOperationHandling(userId,operationname);
        Assert.assertTrue(result.equals(operationname));
    }
}