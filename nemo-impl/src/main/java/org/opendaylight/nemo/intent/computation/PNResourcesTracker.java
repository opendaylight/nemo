/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent.computation;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.DataChangeListener;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataBroker.DataChangeScope;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataChangeEvent;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.nemo.intent.IntentResolver;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalLinks;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalNodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.links.PhysicalLink;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.nodes.PhysicalNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.paths.PhysicalPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.node.instance.PhysicalPort;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yangtools.concepts.ListenerRegistration;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * The virtual network mapping unit implements the following functions:
 * (1) Automatically perform the virtual network mapping when the user's
 * virtual network changed, which is subscribed from the data store.
 * (2) Automatically perform the remapping for the virtual networks that
 * are influenced by the changes of the underlying physical network
 * which are also subscribed from the data store.
 *
 * @author Zhigang Ji
 */
public class PNResourcesTracker {
    private static final Logger log = LoggerFactory.getLogger(PNResourcesTracker.class);

    private final ConcurrentHashMap<UserId, CopyOnWriteArraySet<String>/*physical resources*/> physicalResourceMap;

    private final DataBroker dataBroker;
    private final IntentResolver intentResolver;

    /**
     * The registration for the physical node change listener.
     */
    private ListenerRegistration<DataChangeListener> physicalNodeChangeListenerReg;

    /**
     * The registration for the physical link change listener.
     */
    private ListenerRegistration<DataChangeListener> physicalLinkChangeListenerReg;

    public PNResourcesTracker(DataBroker dataBroker, IntentResolver intentResolver) {
        super();

        this.dataBroker = dataBroker;
        this.intentResolver = intentResolver;
        physicalResourceMap = new ConcurrentHashMap<UserId, CopyOnWriteArraySet<String>>();

        InstanceIdentifier<PhysicalNode> nodeInstanceIdentifier = InstanceIdentifier
                .builder(PhysicalNetwork.class)
                .child(PhysicalNodes.class)
                .child(PhysicalNode.class)
                .build();
        InstanceIdentifier<PhysicalLink> linkInstanceIdentifier = InstanceIdentifier
                .builder(PhysicalNetwork.class)
                .child(PhysicalLinks.class)
                .child(PhysicalLink.class)
                .build();

        physicalNodeChangeListenerReg = dataBroker.registerDataChangeListener(LogicalDatastoreType.OPERATIONAL,
                nodeInstanceIdentifier, new PhysicalNodeChangeListener(), DataChangeScope.BASE);
        physicalLinkChangeListenerReg = dataBroker.registerDataChangeListener(LogicalDatastoreType.OPERATIONAL,
                linkInstanceIdentifier, new PhysicalLinkChangeListener(), DataChangeScope.BASE);

        return;
    }
    public void close(){
        if(physicalLinkChangeListenerReg!=null){
            physicalLinkChangeListenerReg.close();
        }
        if(physicalNodeChangeListenerReg!=null){
            physicalNodeChangeListenerReg.close();
        }
        physicalResourceMap.clear();
    }
    protected synchronized void resetResource(UserId userId) {
        physicalResourceMap.remove(userId);
    }

    protected void addPhysicalNode(UserId userId, PhysicalNode physicalNode) {
        addPhysicalResource(userId, physicalNode.getKey().toString());
    }

    protected void addPhysicalPort(UserId userId, PhysicalPort physicalPort) {
        addPhysicalResource(userId, physicalPort.getKey().toString());
    }

    protected void addPhysicalPath(UserId userId, PhysicalPath physicalPath) {
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink>
                physicalLinkList = physicalPath.getPhysicalLink();
        for (org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink physicalLink :
                physicalLinkList) {
            addPhysicalResource(userId, physicalLink.getKey().toString());
        }
    }

    private synchronized void addPhysicalResource(UserId userId, String resourceId) {
        if (!physicalResourceMap.containsKey(userId)) {
            physicalResourceMap.put(userId, new CopyOnWriteArraySet<String>());
        }
        physicalResourceMap.get(userId).add(resourceId);
    }

    private synchronized void physicalResourceDown(String resourceId) {
        Map<UserId, CopyOnWriteArraySet<String>> tmpMap = new HashMap<UserId, CopyOnWriteArraySet<String>>(physicalResourceMap);
        for (UserId userId : tmpMap.keySet()) {
            Set<String> physicalResources = tmpMap.get(userId);
            if (physicalResources.contains(resourceId)) {
                physicalResourceMap.remove(userId);
                try {
                    log.info("Physical network changed and affected the virtual network of " +
                            "user {}, start remapping.", userId.getValue());

                    intentResolver.resolveIntent(userId);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    log.error("Exception:",e);
                }
            }
        }
    }

    /**
     * A listener to ch ange events related to physical nodes being
     * added, removed or updated.
     *
     * @author Zhigang Ji
     */
    private class PhysicalNodeChangeListener implements DataChangeListener {
        @Override
        public void onDataChanged(AsyncDataChangeEvent<InstanceIdentifier<?>, DataObject> change) {
            if (null == change) {
                return;
            }

            Map<InstanceIdentifier<?>, DataObject> originalData = change.getOriginalData();
            Map<InstanceIdentifier<?>, DataObject> updatedData = change.getUpdatedData();

            if (null != updatedData && !updatedData.isEmpty()) {
                for (InstanceIdentifier<?> identifier : updatedData.keySet()) {
                    DataObject originData = originalData.get(identifier);
                    DataObject currentData = updatedData.get(identifier);
                    if (originData != null && originData instanceof PhysicalNode) {
                        handleNodeUpdate((PhysicalNode) originData, (PhysicalNode) currentData);
                    }
                }
            }

            Set<InstanceIdentifier<?>> removedPaths = change.getRemovedPaths();

            if (null != removedPaths && !removedPaths.isEmpty()) {
                DataObject dataObject;
                for (InstanceIdentifier<?> instanceId : removedPaths) {
                    dataObject = originalData.get(instanceId);
                    if (dataObject != null && dataObject instanceof PhysicalNode) {
                        String resourceId = ((PhysicalNode) dataObject).getKey().toString();
                        log.debug("Physical node {} removed.", resourceId);
                        physicalResourceDown(resourceId);
                    }

                }
            }
        }

        private void handleNodeUpdate(PhysicalNode originNode, PhysicalNode currentNode) {
            log.debug("Handle node {} update.", originNode.getNodeId().getValue());
            List<PhysicalPort> originPorts = originNode.getPhysicalPort();
            List<PhysicalPort> currentPorts = currentNode.getPhysicalPort() == null ? new ArrayList<PhysicalPort>() : currentNode.getPhysicalPort();
            if (originPorts != null)
                for (PhysicalPort physicalPort : originPorts) {
                    if (!currentPorts.contains(physicalPort)) {
                        log.debug("Physical port {} removed.", physicalPort.getPortId().getValue());
                        physicalResourceDown(physicalPort.getKey().toString());
                    }
                }
        }
    }

    /**
     * A listener to change events related to physical links being
     * added, removed or updated.
     *
     * @author Zhigang Ji
     */
    private class PhysicalLinkChangeListener implements DataChangeListener {
        @Override
        public void onDataChanged(AsyncDataChangeEvent<InstanceIdentifier<?>, DataObject> change) {
            if (null == change) {
                return;
            }

            Map<InstanceIdentifier<?>, DataObject> originalData = change.getOriginalData();
            Map<InstanceIdentifier<?>, DataObject> updatedData = change.getUpdatedData();

            if (null != updatedData && !updatedData.isEmpty()) {
                for (InstanceIdentifier<?> identifier : updatedData.keySet()) {
                    DataObject originData = originalData.get(identifier);
                    DataObject currentDate = updatedData.get(identifier);
                    if (originData != null && originData instanceof PhysicalLink) {
                        handleLinkUpdate((PhysicalLink) originalData, (PhysicalLink) currentDate);
                    }
                }
            }

            Set<InstanceIdentifier<?>> removedPaths = change.getRemovedPaths();

            if (null != removedPaths && !removedPaths.isEmpty()) {
                DataObject dataObject;

                for (InstanceIdentifier<?> instanceId : removedPaths) {
                    dataObject = originalData.get(instanceId);
                    if (dataObject != null && dataObject instanceof PhysicalLink) {
                        String resourceId = ((PhysicalLink) dataObject).getKey().toString();
                        log.debug("Physical link {} removed.", resourceId);
                        physicalResourceDown(resourceId);
                    }
                }
            }
        }

        private void handleLinkUpdate(PhysicalLink originLink, PhysicalLink currentLink) {
            log.debug("Handle physical link {} update.", originLink.getLinkId().getValue());
            //TODO
        }
    }


}
