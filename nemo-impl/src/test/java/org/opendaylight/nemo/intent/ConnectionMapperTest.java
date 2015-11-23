/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.intent;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
/**
 * Created by zhangmeng on 2015/11/9.
 */
public class ConnectionMapperTest extends ConnectionMapper{
    public ConnectionMapperTest(){
        super(mock(DataBroker.class),mock(NodeMapper.class));
    }
    public User user;
    public Connection connection;
    public VirtualNetwork virtualNetwork;
    public UserIntentVnMapping userIntentVnMapping;
    String flag;
    @Before
    public void setUp() throws Exception {
        user = mock(User.class);
	connection = mock(Connection.class);
        virtualNetwork = mock(VirtualNetwork.class);
        userIntentVnMapping = mock(UserIntentVnMapping.class);
        flag = new String();
    }

    @Test
    public void testResolveConnection() throws Exception {
        ConnectionType connectionType = connection.getConnectionType();
        Assert.assertNotEquals(mock(ConnectionType.class),connectionType);
        //this.resolveConnection(user,connection,virtualNetwork,userIntentVnMapping);
        Assert.assertNotNull(this);
    }
}