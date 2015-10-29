/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.instancecheck.NodeInstanceCheck;
import org.opendaylight.nemo.user.vnspacemanager.syntaxcheck.NodeDefinitionCheck;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.NodeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.NodeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by z00293636 on 2015/8/31.
 */
public class UpdateNode {

    private DataBroker dataBroker;
    private NodeDefinitionCheck nodeCheck;
    private NodeInstanceCheck nodeInstanceCheck;
    private static final Logger LOG = LoggerFactory.getLogger(UpdateNode.class);

    public UpdateNode(DataBroker dataBroker,TenantManage tenantManage)
    {
        this.dataBroker = dataBroker;
        nodeCheck = new NodeDefinitionCheck(dataBroker);
        nodeInstanceCheck = new NodeInstanceCheck(tenantManage);
    }

    public String NodeHandling(UserId userId,Node node)
    {
        String errorDefinition = nodeCheck.CheckNodeDefinition(node);
        String errorInstance = nodeInstanceCheck.checkNodeInstance(userId,node);

        if (errorDefinition != null)
        {
            return errorDefinition;
        }
        if (errorInstance != null)
        {
            return errorInstance;
        }
        else
        {
            WriteTransaction t = dataBroker.newWriteOnlyTransaction();
            if (userId != null && node.getNodeId() !=null)
            {
//                NodeBuilder nodeBuilder = new NodeBuilder();
//                nodeBuilder.setNodeId(node.getNodeId());
//                nodeBuilder.setNodeId(node.getNodeId());
//                nodeBuilder.setNodeName(node.getNodeName());
//                nodeBuilder.setSubNode(node.getSubNode());
//                nodeBuilder.setProperty(node.getProperty());
//                Node node1 = nodeBuilder.build();

                Node node1 = new NodeBuilder(node).build();
                NodeKey nodeKey = new NodeKey(node.getKey());

                UserKey userKey = new UserKey(userId);

                InstanceIdentifier<Node> nodeid = InstanceIdentifier.builder(Users.class).child(User.class, userKey).child(Objects.class).child(Node.class,nodeKey).build();
                t.put(LogicalDatastoreType.CONFIGURATION, nodeid, node1,true);
                CheckedFuture<Void, TransactionCommitFailedException> f = t.submit();
                Futures.addCallback(f, new FutureCallback<Void>() {
                    @Override
                    public void onFailure(Throwable t) {
                        LOG.error("Could not write endpoint base container", t);
                    }

                    @Override
                    public void onSuccess(Void result) {
                    }
                });
            }
        }
        return null;
    }

}

