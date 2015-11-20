/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.languagestyle.deleteintentlang;

import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.deleteintent.DeleteNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;

import java.util.List;

/**
 * Created by z00293636 on 2015/11/6.
 */
public class DeleteNodeLang {
    private DataBroker dataBroker;
    private TenantManage tenantManage;
    private DeleteNode deleteNode;

    public DeleteNodeLang(DataBroker dataBroker, TenantManage tenantManage)
    {
        this.dataBroker = dataBroker;
        this.tenantManage = tenantManage;
        deleteNode = new DeleteNode(dataBroker,tenantManage);
    }

    public String DeleteNodeHandling(UserId userId, String nodename)
    {
        String errorInfo = null;
        tenantManage.fetchVNSpace(userId);
        User user =tenantManage.getUser();
        if (user.getObjects()!=null)
        {
            if (!user.getObjects().getNode().isEmpty())
            {
                List<Node> nodeList = user.getObjects().getNode();
                Boolean nodeExist = false;
                for (Node node1 : nodeList)
                {
                    if (node1.getNodeName().getValue().equals(nodename))
                    {
                        nodeExist = true;
                        errorInfo = deleteNode.DeleNodeHandling(userId,node1.getNodeId());
                    }
                }
                if (!nodeExist)
                {
                    errorInfo = "The node"+nodename + "is not exist in this user vn space.";
                }
            }
            else
            {
                errorInfo = "The node"+nodename + "is not exist in this user vn space.";
            }
        }
        else
        {
            errorInfo = "The node"+nodename + "is not exist in this user vn space.";
        }

        return errorInfo;
    }

}
