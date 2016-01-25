/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.renderer.openflow;

import com.google.common.base.Optional;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.DataChangeListener;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataBroker.DataChangeScope;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataChangeEvent;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.nemo.renderer.openflow.physicalnetwork.PhyConfigLoader;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalNodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.nodes.PhysicalNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.VirtualNetworks;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetworkKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.IntentVnMappingResults;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.VnPnMappingResults;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMappingKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.UserVnPnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.VirtualNetworkId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.yangtools.concepts.ListenerRegistration;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class FlowTableManager implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(FlowTableManager.class);

    private final DataBroker dataProvider;
    private final PacketProcessingService packetProcessingService;

    private ListenerRegistration<DataChangeListener> userVnPnMappingChangeListenerReg;

    private FlowUtils flowUtils = null;

    public FlowTableManager(DataBroker dataProvider,
                            PacketProcessingService packetProcessingService,
                            PhyConfigLoader phyConfigLoader) {
        super();

        this.dataProvider = dataProvider;
        this.packetProcessingService = packetProcessingService;

        LOG.debug("Initialized FlowTableManager.");

        flowUtils = new FlowUtils(dataProvider, packetProcessingService, phyConfigLoader);

        // Register listener;
        registerUserVnPnMappingListener();
    }

    protected FlowUtils getFlowUtils() {
        return flowUtils;
    }

    private void registerUserVnPnMappingListener() {

        //build userVnPnMappingIid
        InstanceIdentifier<UserVnPnMapping> userVnPnMappingIid = InstanceIdentifier
                .builder(VnPnMappingResults.class)
                .child(UserVnPnMapping.class)
                .build();
        //register
        userVnPnMappingChangeListenerReg = dataProvider.registerDataChangeListener(
                LogicalDatastoreType.CONFIGURATION, userVnPnMappingIid,
                new UserVnPnMappingChangeListener(), DataChangeScope.BASE);
    }


    private User getUser(UserId userId) {

        ReadOnlyTransaction readOnlyTransaction = dataProvider.newReadOnlyTransaction();
        InstanceIdentifier<User> userIid = InstanceIdentifier.builder(Users.class)
                .child(User.class, new UserKey(userId))
                .build();
        Optional<User> result = null;

        try {
            result = readOnlyTransaction.read(LogicalDatastoreType.CONFIGURATION, userIid).get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            LOG.error("Exception:",e);
        }
        if (result.isPresent()){
            LOG.debug("getUser  OK");
            return (result.get());

        }else{
            LOG.debug("getUser  ERROR");
            return  null;
        }
    }


    private  VirtualNetwork getVirtualNetwork(UserId userId) {

        VirtualNetworkId virtualNetworkId = new VirtualNetworkId(userId.getValue());
        VirtualNetworkKey virtualNetworkKey = new VirtualNetworkKey(virtualNetworkId);

        ReadOnlyTransaction readOnlyTransaction = dataProvider.newReadOnlyTransaction();
        InstanceIdentifier<VirtualNetwork> virtualNetworkIid = InstanceIdentifier
                .builder(VirtualNetworks.class)
                .child(VirtualNetwork.class, virtualNetworkKey)
                .build();
        Optional<VirtualNetwork> result = null;

        try {
            result = readOnlyTransaction.read(LogicalDatastoreType.CONFIGURATION, virtualNetworkIid).get();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            LOG.error("Exception:",e);
        }
        if (result.isPresent()) {
            LOG.debug("getVirtualNetwork  OK");
            return (result.get());

        }else{
            LOG.debug("getVirtualNetwork  ERROR");
            return  null;
        }
    }

    private  UserIntentVnMapping getUserIntentVnMapping(UserId userId) {

        ReadOnlyTransaction readOnlyTransaction = dataProvider.newReadOnlyTransaction();
        InstanceIdentifier<UserIntentVnMapping> userIntentVnMappingIid = InstanceIdentifier
                .builder(IntentVnMappingResults.class)
                .child(UserIntentVnMapping.class, new UserIntentVnMappingKey(userId))
                .build();
        Optional<UserIntentVnMapping> result = null;
        try {
            result = readOnlyTransaction.read(LogicalDatastoreType.CONFIGURATION, userIntentVnMappingIid).get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            LOG.error("Exception:",e);
        }
        if (result.isPresent()) {
            LOG.debug("getUserIntentVnMapping  OK");
            return (result.get());

        }else{
            LOG.debug("getUserIntentVnMapping  ERROR");
            return  null;
        }
    }

    private PhysicalNetwork getPhysicalNetwork() {

        ReadOnlyTransaction readOnlyTransaction = dataProvider.newReadOnlyTransaction();
        InstanceIdentifier<PhysicalNetwork> physicalNetworkIid = InstanceIdentifier
                .builder(PhysicalNetwork.class)
                .build();
        Optional<PhysicalNetwork> result = null;
        try {
            result = readOnlyTransaction.read(LogicalDatastoreType.OPERATIONAL, physicalNetworkIid).get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            LOG.error("Exception:",e);
        }
        if (result.isPresent()) {
            LOG.debug("getPhysicalNetwork  OK");
            return (result.get());

        }else{
            LOG.debug("getPhysicalNetwork  ERROR");
            return  null;
        }
    }


    //A listener implementation
    private class UserVnPnMappingChangeListener implements DataChangeListener {

        @Override
        public void onDataChanged(AsyncDataChangeEvent<InstanceIdentifier<?>, DataObject> change) {
            if ( null == change ) {
                return;
            }

            Map<InstanceIdentifier<?>, DataObject> createdData = change.getCreatedData();
            if ( null != createdData && !createdData.isEmpty() ) {
                for ( DataObject dataObject : createdData.values() ) {
                    if ( dataObject instanceof UserVnPnMapping ) {
                        LOG.debug("Ready to update flow table.");
                        UserVnPnMapping userVnPnMapping = (UserVnPnMapping)dataObject;
                        UserId userId = userVnPnMapping.getUserId();

                        User user = getUser(userId);
                        VirtualNetwork virtualNetwork = getVirtualNetwork(userId);
                        UserIntentVnMapping userIntentVnMapping = getUserIntentVnMapping(userId);
                        PhysicalNetwork physicalNetwork = getPhysicalNetwork();
                        if(physicalNetwork == null)
                        {
                            LOG.debug("Physical Network data are not present.");
                            return;
                        }
                        PhysicalNodes physicalNodes= physicalNetwork.getPhysicalNodes();
                        List<PhysicalNode> physicalNodeList = physicalNodes.getPhysicalNode();
                        flowUtils.init(physicalNodeList);

                        flowUtils.updateFlowTable(user, virtualNetwork, userIntentVnMapping, userVnPnMapping, physicalNetwork);
                        LOG.debug("Already call flowUtils.updateFlowTable().");
                    }
                }
            }

            Map<InstanceIdentifier<?>, DataObject> updatedData = change.getUpdatedData();
            if ( null != updatedData && !updatedData.isEmpty() ) {
                for ( DataObject dataObject : updatedData.values() ) {
                    if ( dataObject instanceof UserVnPnMapping ) {
                        UserVnPnMapping userVnPnMapping = (UserVnPnMapping)dataObject;
                        UserId userId = userVnPnMapping.getUserId();

                        flowUtils.deleteFlowEntries(userId);

                        User user = getUser(userId);
                        VirtualNetwork virtualNetwork = getVirtualNetwork(userId);
                        UserIntentVnMapping userIntentVnMapping = getUserIntentVnMapping(userId);
                        PhysicalNetwork physicalNetwork = getPhysicalNetwork();
                        if(physicalNetwork == null)
                        {
                            LOG.debug("Physical Network data are not present.");
                            return;
                        }

                        flowUtils.updateFlowTable(user, virtualNetwork, userIntentVnMapping, userVnPnMapping, physicalNetwork);
                    }
                }
            }

            Map<InstanceIdentifier<?>, DataObject> originalData = change.getOriginalData();
            Set<InstanceIdentifier<?>> removedPaths = change.getRemovedPaths();
            if ( null != removedPaths && !removedPaths.isEmpty() ) {
                DataObject dataObject;

                for ( InstanceIdentifier<?> instanceId : removedPaths ) {
                    dataObject = originalData.get(instanceId);
                    if ( null != dataObject && dataObject instanceof UserVnPnMapping ) {
                        UserVnPnMapping userVnPnMapping = (UserVnPnMapping)dataObject;
                        flowUtils.deleteFlowEntries(userVnPnMapping.getUserId());
                    }
                }
            }
            return;
        }
    }

    @Override
    public void close() throws Exception {

        if ( null != userVnPnMappingChangeListenerReg ) {
            userVnPnMappingChangeListenerReg.close();
        }
        if ( null != flowUtils ) {
            flowUtils.close();
        }
    }
}
