/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.renderer.cli;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.PhysicalNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalLinks;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.PhysicalPaths;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.nodes.PhysicalNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.paths.PhysicalPath;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.network.physical.paths.PhysicalPathBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.IntentVnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.user.intent.vn.mapping.intent.vn.mapping.result.VirtualResource;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.UserVnPnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.vn.pn.mapping.results.user.vn.pn.mapping.VnPnMappingResult;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.ConnectionId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalLinkId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalNodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.engine.common.rev151010.PhysicalPortId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.object.rev151010.connection.instance.EndNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 *
 * @author Shixing Liu
 */
public class CliBuilder implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(CliBuilder.class);

    private final DataBroker dataProvider;

    private final TelnetUtils telnetUtils;

    /**
     * Constructor
     *
     * @param dataProvider
     */
    public CliBuilder(DataBroker dataProvider) {
        super();

        this.dataProvider = dataProvider;

        LOG.info("New TelnetUtils.");
        telnetUtils = new TelnetUtils();

        LOG.info("Initialized CliBuilder.");

		return;
    }

    /**
     *
     * @param physicalNodes
     */
    public void init(List<PhysicalNode> physicalNodes) {
        for ( PhysicalNode physicalNode : physicalNodes ) {
            // TODO:
        }

		return;
    }

    /**
     *
     * @param nodeId
     * @param userIntentVnMapping
     * @param userVnPnMapping
     * @return
     */
    private VnPnMappingResult getNodeVnPnMappingResult(NodeId nodeId,
                                                       UserIntentVnMapping userIntentVnMapping,
                                                       UserVnPnMapping userVnPnMapping)
    {
        VnPnMappingResult vnPnMappingResultforNode = null;
        List<IntentVnMappingResult> intentVnMappingResults = userIntentVnMapping.getIntentVnMappingResult();

        //System.out.println("intentVnMappingResults size = " + intentVnMappingResults.size());
        for(IntentVnMappingResult intentVnMappingResult: intentVnMappingResults){
            if(intentVnMappingResult.getIntentId().getValue().equals(nodeId.getValue())){

                List<VirtualResource> virtualResources = intentVnMappingResult.getVirtualResource();
                //System.out.println("virtualResources size()= "+ virtualResources.size());
                for(VirtualResource virtualResource: virtualResources){
                    if(VirtualResource.VirtualResourceType.Vport
                            == virtualResource.getVirtualResourceType()){
                        List<VnPnMappingResult> vnPnMappingResults = userVnPnMapping.getVnPnMappingResult();
                        for(VnPnMappingResult vnPnMappingResult: vnPnMappingResults){
                            if(vnPnMappingResult.getVirtualResourceEntityId().getValue()
                                    .equals(virtualResource.getVirtualResourceEntityId().getValue())){

                                System.out.println("equals(virtualResource.getVirtualResourceEntityId())");
                                vnPnMappingResultforNode = vnPnMappingResult;
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        return  vnPnMappingResultforNode;
    }

    /**
     *
     * @param connection
     * @param user
     * @return
     */
    private Boolean isConnectTwoExtGroup(Connection connection, User user){
        List<EndNode> endNodeList = connection.getEndNode();
        Boolean isTwoExtGroup = Boolean.TRUE;
        for(EndNode endnode:endNodeList){
            NodeId nodeId =  endnode.getNodeId();
            List<Node> nodeList = user.getObjects().getNode();
            for(Node node:nodeList){
                if(node.getNodeId().equals(nodeId)){
                    //System.out.println(node.getNodeType().getValue());
                    if(! node.getNodeType().getValue().equals("ext-group")){
                        isTwoExtGroup = Boolean.FALSE;
                        break;
                    }
                }
            }
            if( isTwoExtGroup == Boolean.FALSE){
                break;
            }
        }
        //System.out.println("isConnectTwoExtGroup = "+ isTwoExtGroup.booleanValue());
        return  isTwoExtGroup;
    }

    /**
     *
     * @param physicalLinkId
     * @param physicalNetwork
     * @return
     */
    private PhysicalNodeId getSrcNodeIdforLinkInPath(PhysicalLinkId physicalLinkId,
                                                     PhysicalNetwork physicalNetwork){

        PhysicalNodeId physicalNodeId = null;
        PhysicalLinks physicalLinksInNetwork = physicalNetwork.getPhysicalLinks();
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params
                .xml.ns.yang.generic.physical.network.rev151010
                .physical.network.physical.links.PhysicalLink>
                physicalLinksInNetworkList = physicalLinksInNetwork.getPhysicalLink();
        for(org.opendaylight.yang.gen.v1.urn.opendaylight.params
                .xml.ns.yang.generic.physical.network.rev151010
                .physical.network.physical.links
                .PhysicalLink physicalLink :physicalLinksInNetworkList) {

            if (physicalLink.getLinkId().getValue().equals(physicalLinkId.getValue())) {
                physicalNodeId = physicalLink.getSrcNodeId();
            }
        }
        return  physicalNodeId;
    }

    /**
     *
     * @param physicalLinkId
     * @param physicalNetwork
     * @return
     */
    private PhysicalPortId getSrcPortIdforLinkInPath(PhysicalLinkId physicalLinkId,
                                                     PhysicalNetwork physicalNetwork){

        PhysicalPortId physicalPortId = null;
        PhysicalLinks physicalLinksInNetwork = physicalNetwork.getPhysicalLinks();
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params
                .xml.ns.yang.generic.physical.network.rev151010
                .physical.network.physical.links.PhysicalLink>
                physicalLinksInNetworkList = physicalLinksInNetwork.getPhysicalLink();
        for(org.opendaylight.yang.gen.v1.urn.opendaylight.params
                .xml.ns.yang.generic.physical.network.rev151010
                .physical.network.physical.links
                .PhysicalLink physicalLink :physicalLinksInNetworkList) {

            if (physicalLink.getLinkId().getValue().equals(physicalLinkId.getValue())) {
                physicalPortId = physicalLink.getSrcPortId();
            }
        }
        return  physicalPortId;
    }

    /**
     *
     * @param physicalLinkId
     * @param physicalNetwork
     * @return
     */
    private PhysicalNodeId getDestNodeIdforLinkInPath(PhysicalLinkId physicalLinkId,
                                                     PhysicalNetwork physicalNetwork){

        PhysicalNodeId physicalNodeId = null;
        PhysicalLinks physicalLinksInNetwork = physicalNetwork.getPhysicalLinks();
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params
                .xml.ns.yang.generic.physical.network.rev151010
                .physical.network.physical.links.PhysicalLink>
                physicalLinksInNetworkList = physicalLinksInNetwork.getPhysicalLink();
        for(org.opendaylight.yang.gen.v1.urn.opendaylight.params
                .xml.ns.yang.generic.physical.network.rev151010
                .physical.network.physical.links
                .PhysicalLink physicalLink :physicalLinksInNetworkList) {

            if (physicalLink.getLinkId().getValue().equals(physicalLinkId.getValue())) {
                physicalNodeId = physicalLink.getDestNodeId();
            }
        }
        return  physicalNodeId;
    }

    /**
     *
     * @param physicalLinkId
     * @param physicalNetwork
     * @return
     */
    private PhysicalPortId getDestPortIdforLinkInPath(PhysicalLinkId physicalLinkId,
                                                      PhysicalNetwork physicalNetwork){

        PhysicalPortId physicalPortId = null;
        PhysicalLinks physicalLinksInNetwork = physicalNetwork.getPhysicalLinks();
        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params
                .xml.ns.yang.generic.physical.network.rev151010
                .physical.network.physical.links.PhysicalLink>
                physicalLinksInNetworkList = physicalLinksInNetwork.getPhysicalLink();
        for(org.opendaylight.yang.gen.v1.urn.opendaylight.params
                .xml.ns.yang.generic.physical.network.rev151010
                .physical.network.physical.links
                .PhysicalLink physicalLink :physicalLinksInNetworkList) {

            if (physicalLink.getLinkId().getValue().equals(physicalLinkId.getValue())) {
                physicalPortId = physicalLink.getDestPortId();
            }
        }
        return  physicalPortId;
    }

    /**
     *
     * @param onePhysicalNodeId
     * @param otherPhysicalNodeId
     * @param physicalNetwork
     * @return
     */
    private PhysicalPath getPhysicalPathforConnection(PhysicalNodeId onePhysicalNodeId,
                                                      PhysicalNodeId otherPhysicalNodeId,
                                                      PhysicalNetwork physicalNetwork){

        PhysicalPath physicalPathforConnection = null;
        PhysicalPaths physicalPaths = physicalNetwork.getPhysicalPaths();
        if(null == physicalPaths.getPhysicalPath()){
            LOG.info("PhysicalPath are null");
            return physicalPathforConnection;
        }

        PhysicalNodeId firstNodeIdforPath = null;
        PhysicalNodeId lastNodeIdforPath = null;
        List<PhysicalPath> physicalPathList = physicalPaths.getPhysicalPath();

        for(PhysicalPath physicalPath:physicalPathList) {

            PhysicalPath soredPhysicalPath = sortPhysicalLinksOfPhysicalPath(physicalPath);
            List< org.opendaylight.yang.gen.v1.urn.opendaylight.params
                    .xml.ns.yang.generic.physical.network.rev151010
                    .physical.path.instance.PhysicalLink> physicalLinks = soredPhysicalPath.getPhysicalLink();

            if(physicalLinks != null && physicalLinks.size() > 0){

                PhysicalLinkId firstPhysicalLinkId = physicalLinks.get(0).getLinkId();
                PhysicalLinkId lastPhysicalLinkId = physicalLinks.get(physicalLinks.size()-1).getLinkId();

                firstNodeIdforPath = getSrcNodeIdforLinkInPath(firstPhysicalLinkId, physicalNetwork);
                lastNodeIdforPath = getDestNodeIdforLinkInPath(lastPhysicalLinkId, physicalNetwork);
                if((firstNodeIdforPath != null)
                        && (lastNodeIdforPath != null)
                        && (firstNodeIdforPath.getValue().equals(onePhysicalNodeId.getValue()))
                        && (lastNodeIdforPath.getValue().equals(otherPhysicalNodeId.getValue()))){

                    physicalPathforConnection = soredPhysicalPath;
                    break;
                }
            }
        }

        return  physicalPathforConnection;
    }

    /**
     *
     * @param physicalPath
     * @return
     */
    private PhysicalPath sortPhysicalLinksOfPhysicalPath(PhysicalPath physicalPath) {
        if ( physicalPath.getPhysicalLink().isEmpty()
                || 1 == physicalPath.getPhysicalLink().size() ) {
            return physicalPath;
        }

        List<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink> sortedPhysicalLinks =
                new ArrayList<org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink>(physicalPath.getPhysicalLink().size());
        sortedPhysicalLinks.addAll(physicalPath.getPhysicalLink());

        for ( org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.physical.network.rev151010.physical.path.instance.PhysicalLink
                physicalLink : physicalPath.getPhysicalLink() ) {
            sortedPhysicalLinks.set(physicalLink.getOrder().intValue(), physicalLink);
        }
        PhysicalPath physicalPath1 = new PhysicalPathBuilder(physicalPath)
                .setPhysicalLink(sortedPhysicalLinks)
                .build();

        return physicalPath1;
    }

    /**
     *
     */
    private class ConfigElementForConnection {

        public PhysicalNodeId getPhysicalNodeId() {
            return physicalNodeId;
        }

        public void setPhysicalNodeId(PhysicalNodeId physicalNodeId) {
            this.physicalNodeId = physicalNodeId;
        }

        public PhysicalPortId getInnerPhysicalPortId() {
            return innerPhysicalPortId;
        }

        public void setInnerPhysicalPortId(PhysicalPortId innerPhysicalPortId) {
            this.innerPhysicalPortId = innerPhysicalPortId;
        }

        public PhysicalPortId getOuterPhysicalPortId() {
            return outerPhysicalPortId;
        }

        public void setOuterPhysicalPortId(PhysicalPortId outerPhysicalPortId) {
            this.outerPhysicalPortId = outerPhysicalPortId;
        }
        private PhysicalNodeId physicalNodeId;
        private PhysicalPortId innerPhysicalPortId;
        private PhysicalPortId outerPhysicalPortId;

    }

    /**
     *
     * @param physicalPath
     * @param physicalNetwork
     * @param onePhysicalPortId
     * @param otherPhysicalPortId
     * @return
     */
    private List<ConfigElementForConnection> getConfigElementListForConnection(
                                                                           PhysicalPath physicalPath,
                                                                           PhysicalNetwork physicalNetwork,
                                                                           PhysicalPortId onePhysicalPortId,
                                                                           PhysicalPortId otherPhysicalPortId
                                                                           ){

        List<ConfigElementForConnection> configElementListForConnection = new ArrayList<ConfigElementForConnection>();

        if((physicalPath == null)){
            return  configElementListForConnection;
        }
        List< org.opendaylight.yang.gen.v1.urn.opendaylight.params
                .xml.ns.yang.generic.physical.network.rev151010
                .physical.path.instance.PhysicalLink> physicalLinks = physicalPath.getPhysicalLink();

        Iterator<org.opendaylight.yang.gen.v1.urn.opendaylight.params
                .xml.ns.yang.generic.physical.network.rev151010
                .physical.path.instance.PhysicalLink> physicalLinkInPathIter = physicalLinks.iterator();

        int counter = -1;

        org.opendaylight.yang.gen.v1.urn.opendaylight.params
                .xml.ns.yang.generic.physical.network.rev151010
                .physical.path.instance.PhysicalLink previousPhysicalLink = null;

        Boolean crossWAN = Boolean.FALSE;

        while (physicalLinkInPathIter.hasNext()) {
            //first device
            counter = counter + 1;
            if(0 == counter){
                ConfigElementForConnection configElementForConnection = new ConfigElementForConnection();

                //save previous link
                previousPhysicalLink = physicalLinkInPathIter.next();

                PhysicalLinkId physicalLinkId = previousPhysicalLink.getLinkId();
                PhysicalNodeId physicalSrcNodeId = getSrcNodeIdforLinkInPath(physicalLinkId, physicalNetwork);
                PhysicalPortId physicalSrcPortId = getSrcPortIdforLinkInPath(physicalLinkId, physicalNetwork);
                configElementForConnection.setPhysicalNodeId(physicalSrcNodeId);
                configElementForConnection.setInnerPhysicalPortId(physicalSrcPortId);
                configElementForConnection.setOuterPhysicalPortId(onePhysicalPortId);
                configElementListForConnection.add(configElementForConnection);
            }
            else {

                //for middle devices
                ConfigElementForConnection configElementForConnection = new ConfigElementForConnection();
                org.opendaylight.yang.gen.v1.urn.opendaylight.params
                        .xml.ns.yang.generic.physical.network.rev151010
                        .physical.path.instance.PhysicalLink currentPhysicalLink = physicalLinkInPathIter.next();

                PhysicalLinkId currentPhysicalLinkId = currentPhysicalLink.getLinkId();
                PhysicalNodeId physicalSrcNodeId = getSrcNodeIdforLinkInPath(currentPhysicalLinkId, physicalNetwork);
                PhysicalPortId physicalSrcPortId = getSrcPortIdforLinkInPath(currentPhysicalLinkId, physicalNetwork);
                configElementForConnection.setPhysicalNodeId(physicalSrcNodeId);

                PhysicalLinkId previousPhysicalLinkId = previousPhysicalLink.getLinkId();
                PhysicalPortId physicalDestNodeId = getDestPortIdforLinkInPath(previousPhysicalLinkId, physicalNetwork);

                if(Boolean.FALSE == crossWAN) {
                    configElementForConnection.setInnerPhysicalPortId(physicalSrcPortId);
                    configElementForConnection.setOuterPhysicalPortId(physicalDestNodeId);
                    //if cross WAN
                    if(physicalSrcNodeId.getValue().contains(":PE")){

                        crossWAN = Boolean.TRUE;
                    }
                }else{
                    configElementForConnection.setInnerPhysicalPortId(physicalDestNodeId);
                    configElementForConnection.setOuterPhysicalPortId(physicalSrcPortId);

                }
                configElementListForConnection.add(configElementForConnection);
                //for last device
                if((counter + 1) == physicalLinks.size()){

                    ConfigElementForConnection lastconfigElementForConnection = new ConfigElementForConnection();
                    PhysicalNodeId lastphysicalDestNodeId = getDestNodeIdforLinkInPath(currentPhysicalLinkId, physicalNetwork);
                    PhysicalPortId lastphysicalDestPortId = getDestPortIdforLinkInPath(currentPhysicalLinkId, physicalNetwork);
                    lastconfigElementForConnection.setPhysicalNodeId(lastphysicalDestNodeId);
                    lastconfigElementForConnection.setInnerPhysicalPortId(lastphysicalDestPortId);
                    lastconfigElementForConnection.setOuterPhysicalPortId(otherPhysicalPortId);
                    configElementListForConnection.add(lastconfigElementForConnection);
                }
                previousPhysicalLink = currentPhysicalLink;// alternate
            }
        }

        return  configElementListForConnection;
    }

    /**
     *
     * @param configElementListForConnection
     * @return
     */
    private List<ConfigElementForConnection> getPeersOfPEs(
            List<ConfigElementForConnection> configElementListForConnection){

        List<ConfigElementForConnection> peersOfPEs = new ArrayList<ConfigElementForConnection>();
        for(ConfigElementForConnection c: configElementListForConnection){

            if(c.getPhysicalNodeId().getValue().contains(":PE")){
                peersOfPEs.add(c);
            }
        }
        return  peersOfPEs;
    }

    /**
     *
     * @param oneNode
     * @param anotherNode
     * @param peersList
     * @return
     */
    private Boolean isInPeersList(PhysicalNodeId oneNode,
                                  PhysicalNodeId anotherNode,
                                  List<List<ConfigElementForConnection>> peersList){

        for(List<ConfigElementForConnection> peers:peersList) {
            ConfigElementForConnection configElementOnePE = (ConfigElementForConnection) peers.get(0);
            ConfigElementForConnection configElementAnother = (ConfigElementForConnection) peers.get(1);

           if(configElementOnePE.getPhysicalNodeId().getValue().equals(oneNode.getValue())&&
              configElementAnother.getPhysicalNodeId().getValue().equals(anotherNode.getValue())){

               return  Boolean.TRUE;
           }
        }
        System.out.println("isInPeersList() = Boolean.FALSE");
        return  Boolean.FALSE;
    }

    /**
     *
     * @param peersList
     * @return
     */
    public Boolean isFullMeshTopology(List<List<ConfigElementForConnection>> peersList){

        List<PhysicalNodeId> beUsedPEs = new ArrayList<PhysicalNodeId>();

        for(List<ConfigElementForConnection> peers:peersList){
            ConfigElementForConnection configElementOnePE  = (ConfigElementForConnection)peers.get(0);
            ConfigElementForConnection configElementAnother  = (ConfigElementForConnection)peers.get(1);
            if(!beUsedPEs.contains(configElementOnePE.getPhysicalNodeId())){
                beUsedPEs.add(configElementOnePE.getPhysicalNodeId());
            }
            if(!beUsedPEs.contains(configElementAnother.getPhysicalNodeId())){
                beUsedPEs.add(configElementAnother.getPhysicalNodeId());
            }
        }

        for(int i =0; i<beUsedPEs.size(); i++){

            PhysicalNodeId oneNode = (PhysicalNodeId)beUsedPEs.get(i);
            for(int j= i+1; j<beUsedPEs.size(); j++){
                PhysicalNodeId anotherNode = (PhysicalNodeId)beUsedPEs.get(j);
                if(isInPeersList(oneNode, anotherNode, peersList)== Boolean.FALSE){

                    return  Boolean.FALSE;
                }
            }
        }

        System.out.println("isFullMeshTopology() =  Boolean.TRUE");
        return  Boolean.TRUE;
    }

    /**
     *
     * @param physicalNodeId
     * @param VPNPEs
     * @return
     */
    private Boolean hasThisPE(PhysicalNodeId physicalNodeId,
                              List<Map<PhysicalNodeId, PhysicalPortId>> VPNPEs){

        Boolean hasThisPE = Boolean.FALSE;
        for(Map<PhysicalNodeId, PhysicalPortId> pe: VPNPEs){

            if(pe.containsKey(physicalNodeId) == true){
                hasThisPE = Boolean.TRUE;
                break;
            }
        }
        return  hasThisPE;
    }

    /**
     *
     * @param paramList
     * @param template
     * @return
     */
    private String getClis(List<String> paramList, String template){

        for(int i=0;i<paramList.size();i++){

            String paramSymbol = "#"+(i+1)+"#";
            String paramValue = paramList.get(i);
            template = template.replace(paramSymbol, paramValue);
        }
        return  template;
    }

    /**
     *
     * @param peersList
     */
    private void buildFullMeshVPN(List<List<ConfigElementForConnection>> peersList){

        List<Map<PhysicalNodeId, PhysicalPortId>> VPNPEs = new ArrayList<Map<PhysicalNodeId, PhysicalPortId>>();
        for(List<ConfigElementForConnection> peers:peersList){

            ConfigElementForConnection configElementOnePE  = (ConfigElementForConnection)peers.get(0);
            ConfigElementForConnection configElementAnother  = (ConfigElementForConnection)peers.get(1);

            if(hasThisPE(configElementOnePE.getPhysicalNodeId(), VPNPEs)== Boolean.FALSE){
                Map<PhysicalNodeId, PhysicalPortId> accessPEInfo = new HashMap<PhysicalNodeId, PhysicalPortId>() ;
                accessPEInfo.put(configElementOnePE.getPhysicalNodeId(),
                        configElementOnePE.getOuterPhysicalPortId());
                VPNPEs.add(accessPEInfo);
            }
            if(hasThisPE(configElementAnother.getPhysicalNodeId(), VPNPEs)== Boolean.FALSE){
                Map<PhysicalNodeId, PhysicalPortId> accessPEInfo = new HashMap<PhysicalNodeId, PhysicalPortId>() ;
                accessPEInfo.put(configElementAnother.getPhysicalNodeId(),
                        configElementAnother.getOuterPhysicalPortId());
                VPNPEs.add(accessPEInfo);
            }
        }

        for(Map<PhysicalNodeId, PhysicalPortId> pe: VPNPEs) {
            Iterator iter = pe.entrySet().iterator();
            while (iter.hasNext()) {

                //build cli according to template
                String l3VPNTemplate = new String(
                                "interface  #1#\n" +
                                "ip address   #2#\n" +
                                "undo shutdown\n" +
                                "quit\n" +
                                "\n" +
                                "ospf  #3#\n" +
                                "area  #4#\n" +
                                "network   #5#\n" +
                                "quit\n" +
                                "quit\n" +
                                "\n" +
                                "ip vpn-instance  #6#\n" +
                                "route-distinguisher   #7#\n" +
                                "vpn-target  #8#\n" +
                                "quit\n" +
                                "quit\n" +
                                "\n" +
                                "interface  #1#\n" +
                                "ip binding vpn-instance  #6#\n" +
                                "quit\n" +
                                "\n" +
                                "bgp  #9#\n" +
                                "ipv4-family vpn-instance  #6#\n" +
                                "peer  #10#  as-number  #11#\n" +
                                "import-route direct\n" +
                                "quit\n" +
                                "quit\n" +
                                "interface  #1#\n" +
                                "ip address   #2#\n" +
                                "undo shutdown\n" +
                                "quit\n" +
                                "\n");


                String clearL3VPNTemplate = new String(
                        "interface #1#\n" +
                        "undo ip address #2#\n" +
                        "quit\n" +
                        "ospf #3# \n" +
                        "area #4# \n" +
                        "undo network #5#\n" +
                        "quit\n" +
                        "quit\n" +
                        "undo ip vpn-instance #6#\n");

                Map.Entry entry = (Map.Entry) iter.next();
                PhysicalNodeId key = (PhysicalNodeId)entry.getKey();
                PhysicalPortId val = (PhysicalPortId)entry.getValue();

                //for test
                //System.out.println("PE ="+key.getValue());
                //System.out.println("ACPort="+val.getValue());

                //find the deivce
                String deviceName = key.getValue();

                /*
                //(1)fill params from mapping result for template(2,5,10,11)
                String param_1 = getAccessPortName(val.getValue());

                //(2)fill params from ACInfo config file for template
                String param_2 = getCEPortIpAddressAndMask();
                String param_5 = getCEAndPEIpPrefix();
                String param_10 = getPEAccessPortIpAddress();
                String param_11 = getCEBGPNumber();

                //(3)fill params from deivce config(3,4,9)
                String param_3 = getOSPFNumberforPE();
                String param_4 = getAreaNumberforPE();
                String param_9 = getBGPNumberforPE();

                //(4)fill params from automatic generation for template
                String param_6 = generateVPNName();
                String param_7 = generateVPNRD();
                String param_8 = generateVPNTarget();
                */


                //(1)fill params from mapping result for template(2,5,10,11)
                String param_1 = "";
                //(2)fill params from ACInfo config file for template
                String param_2 = "";
                String param_5 = "";
                String param_10 = "";
                String param_11 = "";

                //(3)fill params from deivce config(3,4,9)
                String param_3 = "";
                String param_4 = "";
                String param_9 = "";

                //(4)fill params from automatic generation for template
                String param_6 = "";
                String param_7 = "";
                String param_8 = "";

                if(deviceName.contains("PE1")){

                    //(1)fill params from mapping result for template(2,5,10,11)
                    param_1 = "GigabitEthernet 2/0/6";

                    //(2)fill params from ACInfo config file for template
                    param_2 = "168.1.1.3 255.255.255.0";
                    param_5 = "168.1.1.0  0.0.0.255";
                    param_10 = "168.1.1.2";
                    param_11 = "100";

                    //(3)fill params from deivce config(3,4,9)
                    param_3 = "1";
                    param_4 = "0";
                    param_9 = "200";

                    //(4)fill params from automatic generation for template
                    param_6 = "vpn1";
                    param_7 = "100:1";
                    param_8 = "1:1";

                }else if(deviceName.contains("PE2")){

                    //(1)fill params from mapping result for template(2,5,10,11)
                    param_1 = "GigabitEthernet 3/0/3";

                    //(2)fill params from ACInfo config file for template
                    param_2 = "174.1.1.3 255.255.255.0";
                    param_5 = "174.1.1.0  0.0.0.255";
                    param_10 = "174.1.1.2";
                    param_11 = "100";

                    //(3)fill params from deivce config(3,4,9)
                    param_3 = "1";
                    param_4 = "0";
                    param_9 = "200";

                    //(4)fill params from automatic generation for template
                    param_6 = "vpn1";
                    param_7 = "100:1";
                    param_8 = "1:1";

                }else if(deviceName.contains("PE3")){

                    //(1)fill params from mapping result for template(2,5,10,11)
                    param_1 = "GigabitEthernet 1/0/0";

                    //(2)fill params from ACInfo config file for template
                    param_2 = "172.1.1.2 255.255.255.0";
                    param_5 = "172.1.1.0 0.0.0.255";
                    param_10 = "172.1.1.3";
                    param_11 = "100";

                    //(3)fill params from deivce config(3,4,9)
                    param_3 = "1";
                    param_4 = "0";
                    param_9 = "200";

                    //(4)fill params from automatic generation for template
                    param_6 = "vpn1";
                    param_7 = "100:1";
                    param_8 = "1:1";
                }

                List<String> paramListForL3VPNConfig = new ArrayList<String>();
                paramListForL3VPNConfig.add(param_1);
                paramListForL3VPNConfig.add(param_2);
                paramListForL3VPNConfig.add(param_3);
                paramListForL3VPNConfig.add(param_4);
                paramListForL3VPNConfig.add(param_5);
                paramListForL3VPNConfig.add(param_6);
                paramListForL3VPNConfig.add(param_7);
                paramListForL3VPNConfig.add(param_8);
                paramListForL3VPNConfig.add(param_9);
                paramListForL3VPNConfig.add(param_10);
                paramListForL3VPNConfig.add(param_11);
                String cliExecutionSequences = getClis(paramListForL3VPNConfig, l3VPNTemplate);

                List<String> clearPramListForL3VPNConfig = new ArrayList<String>();
                clearPramListForL3VPNConfig.add(param_1);
                clearPramListForL3VPNConfig.add(param_2);
                clearPramListForL3VPNConfig.add(param_3);
                clearPramListForL3VPNConfig.add(param_4);
                clearPramListForL3VPNConfig.add(param_5);
                clearPramListForL3VPNConfig.add(param_6);
                String clearOldConfigString = getClis(clearPramListForL3VPNConfig, clearL3VPNTemplate);

                //TODO: write data to DataStore
                //Current Test means to call telnet client to send down command lines
                System.out.println();
                System.out.println("telnetUtils.executionCliOnDevice for "+ deviceName);
                System.out.println();

                telnetUtils.configL3vpnOnDeviceByCli(deviceName, clearOldConfigString, cliExecutionSequences);

                //wait for execution to complete
                while (Boolean.TRUE){

                    if(telnetUtils.isConfigOver()== Boolean.TRUE){

                        break;
                    }else{
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            LOG.error("Exception:",e);
                        }
                    }
                }
            }
        }

    }

    /**
     *
     * @param user
     * @param virtualNetwork
     * @param userIntentVnMapping
     * @param userVnPnMapping
     * @param physicalNetwork
     */
    public void updateCliExecutionSequence(User user,
                                           VirtualNetwork virtualNetwork,
                                           UserIntentVnMapping userIntentVnMapping,
                                           UserVnPnMapping userVnPnMapping,
                                           PhysicalNetwork physicalNetwork) {

        LOG.info("CliBuilder is updating cli execution sequences.");
        System.out.println();
        System.out.println("CliBuilder is updating cli execution sequences.");
        System.out.println();

        List<List<ConfigElementForConnection>> peersList = new ArrayList<List<ConfigElementForConnection>>();

        //search connection
        if(user.getObjects() != null) {

            List<Connection> connectionList = user.getObjects().getConnection();

            System.out.println("connection size = "+connectionList.size());
            for(Connection connection: connectionList){

                //search connection for two ex-group
                List<EndNode> endNodeList = connection.getEndNode();
                if( isConnectTwoExtGroup(connection, user) == Boolean.TRUE ){

                    ConnectionId connectionId = connection.getConnectionId();
                    NodeId  oneNodeId = endNodeList.get(0).getNodeId();
                    //System.out.println("endNodeList.get(0).getNodeId()"+endNodeList.get(1).getNodeId().getValue());
                    NodeId  otherNodeId = endNodeList.get(1).getNodeId();
                    //System.out.println("endNodeList.get(1).getNodeId()"+endNodeList.get(1).getNodeId().getValue());
                    PhysicalPortId onePhysicalPortId = null;
                    PhysicalPortId otherPhysicalPortId = null;
                    PhysicalNodeId onePhysicalNodeId = null;
                    PhysicalNodeId otherPhysicalNodeId = null;
                    PhysicalPath physicalPathforConnection = null;

                    List<ConfigElementForConnection> configElementListForConnection = new ArrayList<ConfigElementForConnection>();
                    VnPnMappingResult vnPnMappingResultforOneNode =
                            getNodeVnPnMappingResult(oneNodeId, userIntentVnMapping, userVnPnMapping);

                    if(vnPnMappingResultforOneNode != null) {
                        onePhysicalPortId = new PhysicalPortId(
                                vnPnMappingResultforOneNode.getPhysicalResourceEntityId().getValue());
                        System.out.println(
                                vnPnMappingResultforOneNode.getPhysicalResourceEntityId().getValue());
                        onePhysicalNodeId = new PhysicalNodeId(
                                vnPnMappingResultforOneNode.getParentPhysicalResourceEntityId().getValue());
                        System.out.println(
                                vnPnMappingResultforOneNode.getParentPhysicalResourceEntityId().getValue());

                    }

                    VnPnMappingResult vnPnMappingResultforOtherNode =
                            getNodeVnPnMappingResult(otherNodeId, userIntentVnMapping, userVnPnMapping);

                    if(vnPnMappingResultforOtherNode != null) {
                        otherPhysicalPortId = new PhysicalPortId(
                                vnPnMappingResultforOtherNode.getPhysicalResourceEntityId().getValue());
                        System.out.println(
                                vnPnMappingResultforOtherNode.getPhysicalResourceEntityId().getValue());
                        otherPhysicalNodeId = new PhysicalNodeId(
                                vnPnMappingResultforOtherNode.getParentPhysicalResourceEntityId().getValue());
                        System.out.println(
                                vnPnMappingResultforOtherNode.getParentPhysicalResourceEntityId().getValue());

                    }

                    physicalPathforConnection = getPhysicalPathforConnection(
                                                 onePhysicalNodeId, otherPhysicalNodeId, physicalNetwork );

                    if(physicalPathforConnection != null){

                        configElementListForConnection = getConfigElementListForConnection(physicalPathforConnection,
                                                                                           physicalNetwork,
                                                                                           onePhysicalPortId,
                                                                                           otherPhysicalPortId);
                        /*//for test
                        for (ConfigElementForConnection c : configElementListForConnection) {
                            System.out.println();
                            System.out.println(c.getPhysicalNodeId().getValue());
                            System.out.println(c.getOuterPhysicalPortId().getValue());
                            System.out.println(c.getInnerPhysicalPortId().getValue());
                        }*/
                        List<ConfigElementForConnection> peersOfPEs = getPeersOfPEs(configElementListForConnection);
                        peersList.add(peersOfPEs);

                    }
                }

            }

            //if topo is full mesh
            if(isFullMeshTopology(peersList) == Boolean.TRUE){
                buildFullMeshVPN(peersList);
            }
        }
    }

    /**
     *
     * @throws Exception
     */
	@Override
    public void close() throws Exception {
        if (telnetUtils != null){
            telnetUtils.close();
        }

		return;
    }
}
