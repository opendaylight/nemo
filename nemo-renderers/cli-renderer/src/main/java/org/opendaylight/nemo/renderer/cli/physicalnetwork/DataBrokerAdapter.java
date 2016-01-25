/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.renderer.cli.physicalnetwork;

import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalHosts;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalLinks;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalNodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.hosts.PhysicalHost;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.hosts.PhysicalHostKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLinkKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.nodes.PhysicalNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.nodes.PhysicalNodeKey;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* Created by hj on 11/6/15.
*/
public class DataBrokerAdapter {
    private static final Logger log = LoggerFactory.getLogger(DataBrokerAdapter.class);
    final private DataBroker dataBroker;
    private static final String DEFAULT_TOPOLOGY_ID = "flow:1";


    public DataBrokerAdapter(DataBroker dataBroker) {
        this.dataBroker = dataBroker;
    }



    private InstanceIdentifier<PhysicalHost> getPhysicalHostIdentifier(PhysicalHostKey physicalHostKey) {
        return InstanceIdentifier
                .builder(PhysicalNetwork.class)
                .child(PhysicalHosts.class)
                .child(PhysicalHost.class, physicalHostKey)
                .build();
    }
    private InstanceIdentifier<PhysicalNode> getPhysicalNodeIdentifier(PhysicalNodeKey physicalNodeKey) {
        return InstanceIdentifier
                .builder(PhysicalNetwork.class)
                .child(PhysicalNodes.class)
                .child(PhysicalNode.class, physicalNodeKey)
                .build();
    }
    private InstanceIdentifier<PhysicalLink> getPhysicalLinkIdentifier(PhysicalLinkKey physicalLinkKey) {
        return InstanceIdentifier
                .builder(PhysicalNetwork.class)
                .child(PhysicalLinks.class)
                .child(PhysicalLink.class, physicalLinkKey)
                .build();
    }

    protected void addPhysicalHost(final PhysicalHost physicalHost) {
        try {
            WriteTransaction transaction = dataBroker.newWriteOnlyTransaction();
            final InstanceIdentifier<PhysicalHost> instanceIdentifier = getPhysicalHostIdentifier(physicalHost.getKey());

            transaction.put(LogicalDatastoreType.OPERATIONAL, instanceIdentifier, physicalHost, true);

            CheckedFuture<Void, TransactionCommitFailedException> future = transaction.submit();
            Futures.addCallback(future, new FutureCallback<Void>() {
                @Override
                public void onFailure(Throwable t) {
                    log.warn("Could not write PhysicalHost: {}  {}", physicalHost, t);
                }

                @Override
                public void onSuccess(Void result) {
                    //System.out.println("write transaction onSuccess.");
                    log.debug("Write PhysicalHost transaction onSuccess: {}.", physicalHost);
                }
            });

        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Exception:",e);
        }

    }
    protected boolean addPhysicalNode(final PhysicalNode physicalNode){
        final boolean[] addResult = {false};
        try {
            WriteTransaction t = dataBroker.newWriteOnlyTransaction();
            InstanceIdentifier<PhysicalNode> identifier = getPhysicalNodeIdentifier(physicalNode.getKey());
            t.put(LogicalDatastoreType.OPERATIONAL, identifier, physicalNode, true);

            CheckedFuture<Void, TransactionCommitFailedException> future = t.submit();
            Futures.addCallback(future, new FutureCallback<Void>() {
                @Override
                public void onFailure(Throwable t) {
                    log.warn("Could not write PhysicalNode {} : {}",physicalNode, t);
                }

                @Override
                public void onSuccess(Void result) {
                    addResult[0] = true;
                    log.debug("write transaction onSuccess.");
                }
            });

        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Exception:",e);
        }
        return addResult[0];
    }
    protected boolean removePhysicalNode(final PhysicalNodeKey nodeKey){
        final boolean[] removeResult = {false};
        try {
            WriteTransaction t = dataBroker.newWriteOnlyTransaction();
            InstanceIdentifier<PhysicalNode> identifier = getPhysicalNodeIdentifier(nodeKey);
            t.delete(LogicalDatastoreType.OPERATIONAL, identifier);

            CheckedFuture<Void, TransactionCommitFailedException> future = t.submit();
            Futures.addCallback(future, new FutureCallback<Void>() {
                @Override
                public void onFailure(Throwable t) {
                    log.error("Could not delete PhysicalNode {} : {}",nodeKey, t);
                }

                @Override
                public void onSuccess(Void result) {
                    removeResult[0]=true;
                    log.debug("Delete {} transaction onSuccess.",nodeKey);
                }
            });

        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Exception:",e);
        }
        return removeResult[0];
    }

    protected void addPhysicalLink(final PhysicalLink physicalLink){
        try {
            WriteTransaction transaction = dataBroker.newWriteOnlyTransaction();
            final InstanceIdentifier<PhysicalLink> linkIdentifier = getPhysicalLinkIdentifier(physicalLink.getKey());
            transaction.put(LogicalDatastoreType.OPERATIONAL, linkIdentifier, physicalLink, true);

            CheckedFuture<Void, TransactionCommitFailedException> f = transaction.submit();
            Futures.addCallback(f, new FutureCallback<Void>() {
                @Override
                public void onFailure(Throwable t) {
                    log.warn("Could not put PhysicalLink {} : {}",physicalLink.getKey(), t);
                }

                @Override
                public void onSuccess(Void result) {
                    //System.out.println("write transaction onSuccess.");
                    log.debug("Put transaction onSuccess:{}.",physicalLink.getKey());
                }
            });

        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Exception:",e);
        }
    }
    protected void removePhysicalLink(final PhysicalLinkKey physicalLinkKey){
        try {
            WriteTransaction transaction = dataBroker.newWriteOnlyTransaction();
            final InstanceIdentifier<PhysicalLink> linkIdentifier = getPhysicalLinkIdentifier(physicalLinkKey);
            transaction.delete(LogicalDatastoreType.OPERATIONAL, linkIdentifier);

            CheckedFuture<Void, TransactionCommitFailedException> f = transaction.submit();
            Futures.addCallback(f, new FutureCallback<Void>() {
                @Override
                public void onFailure(Throwable t) {
                    log.warn("Could not remove PhysicalLink {} : {}",physicalLinkKey, t);
                }

                @Override
                public void onSuccess(Void result) {
                    //System.out.println("write transaction onSuccess.");
                    log.debug("Remove transaction onSuccess:{}.", physicalLinkKey);
                }
            });

        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Exception:",e);
        }
    }

}
