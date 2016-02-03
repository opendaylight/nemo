/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.tool.sandbox.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opendaylight.nemo.tool.sandbox.CmdExecutor;
import org.opendaylight.nemo.tool.sandbox.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by hj on 12/9/15.
 */
public class Network {
    private static Logger log = LoggerFactory.getLogger(Network.class);
    private Map<String/*name*/, Host> hostMap;
    private Map<String/*name*/, Switch> switchMap;
    private Map<Connector/*External connector*/, Connector/*Connector on host*/> externalConnectorMap;
    private List<Link> internalLinks;
    private List<Link> links;

    public Network() {
        hostMap = new HashMap<String, Host>();
        switchMap = new HashMap<String, Switch>();
        links = new ArrayList<Link>();

        externalConnectorMap = new HashMap<Connector, Connector>();
        internalLinks = new ArrayList<Link>();
    }

    public void addHost(Host host) {
        hostMap.put(host.getName(), host);
    }

    public void addSwitch(Switch sw) {
        switchMap.put(sw.getName(), sw);
    }

    public void addLink(Link link) {
        links.add(link);
    }

    public String execute(String name, String command) {
        Host host = hostMap.get(name);
        if (host != null) {
            if (CmdExecutor.open()) {
                String result = CmdExecutor.sshExecute("ip netns exec " + name + " " + command);
                CmdExecutor.close();
                return result;
            } else {
                return "Can not open ssh right now,please try again.";
            }
        }
        return name + " " + "is not a firewall,can not execute " + command;
    }

    public void install() {
        traversal();

        for (Link link : links) {
            link.install();
        }

        for (Node node : switchMap.values()) {
            node.install();
        }

        for (Node node : hostMap.values()) {
            node.install();
        }
    }

    public void uninstall() {
        for (Link link : links) {
            link.uninstall();
        }

        pKill();

        for (Host host : hostMap.values()) {
            host.uninstall();
        }
    }

    private void pKill() {
        try {
            CmdExecutor.sshExecute("pkill -9 ofdatapath");
            CmdExecutor.sshExecute("pkill -9 ofprotocol");

            CmdExecutor.sshExecute("pkill -9 fail-ofdatapath");
            CmdExecutor.sshExecute("pkill -9 fail-ofprotocol");

            CmdExecutor.sshExecute("pkill -9 ext-ofdatapath");
            CmdExecutor.sshExecute("pkill -9 ext-ofprotocol");
        } catch (Exception e) {

        }
    }

    public void echoConfig() {
        CmdExecutor.sshExecute("mkdir -p " + Config.getConfigPath());

        String hosts = hostJsonNode().toString().replaceAll("\"", "\\\\\"").replaceAll("\\{", "\\\\{");
        String hostsPath = Config.getConfigPath() + "/" + Config.getConfigHostsFileName();
        CmdExecutor.sshExecute("echo " + hosts + " > " + hostsPath);
//        FileUtils.write(hostsPath, hosts);

        String externals = externalJsonNode().toString().replaceAll("\"", "\\\\\"").replaceAll("\\{", "\\\\{");
        String externalsPath = Config.getConfigPath() + "/" + Config.getConfigExternalsFileName();
        CmdExecutor.sshExecute("echo " + externals + " > " + externalsPath);
//        FileUtils.write(externalsPath, externals);

        String nodes = nodeJsonNode().toString().replaceAll("\"", "\\\\\"").replaceAll("\\{", "\\\\{");
        String nodesPath = Config.getConfigPath() + "/" + Config.getConfigNodesFileName();
        CmdExecutor.sshExecute("echo " + nodes + " > " + nodesPath);
//        FileUtils.write(nodesPath, nodes);

        String links = externalJsonNode().toString().replaceAll("\"", "\\\\\"").replaceAll("\\{", "\\\\{");
        String linksPath = Config.getConfigPath() + "/" + Config.getConfigLinksFileName();
        CmdExecutor.sshExecute("echo " + links + " > " + linksPath);
//        FileUtils.write(linksPath,links);
    }

    private void traversal() {
        externalConnectorMap.clear();
        internalLinks.clear();

        for (Link link : links) {
            String leftNode = link.getSrcConnector().getNodeName();
            String rightNode = link.getDstConnector().getNodeName();
            if (hostMap.containsKey(leftNode) && switchMap.containsKey(rightNode)) {
                externalConnectorMap.put(link.getDstConnector(), link.getSrcConnector());
            } else if (hostMap.containsKey(rightNode) && switchMap.containsKey(leftNode)) {
                externalConnectorMap.put(link.getSrcConnector(), link.getDstConnector());
            } else if (switchMap.containsKey(leftNode) && switchMap.containsKey(rightNode)) {
                internalLinks.add(link);
            } else {
                log.error("Illegal link: {}.", link);
            }
        }
    }

    public JSONObject nodeJsonNode() {
        JSONObject root = new JSONObject();
        JSONArray nodeArray = new JSONArray();
        int index = 0;
        try {
            for (Switch sw : switchMap.values()) {
                JSONObject nodeJson = buildNodeJson(sw);
                if (nodeJson != null) {
                    nodeArray.put(index, nodeJson);
                    index++;
                }
            }
            root.put("node", nodeArray);
            return root;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            log.error("Exception:",e);
        }
        return null;
    }

    private JSONObject buildNodeJson(Switch sw) {
        JSONObject nodeJson = new JSONObject();
        try {
            String nodeId = "openflow:" + sw.getDataPathId();
            String type = sw instanceof Router ? "router" : "switch";
            JSONArray attributes = defaultNodeAttributes(nodeId);
            JSONArray ports = new JSONArray();
            int index = 0;
            List<Connector> connectors = sw.getConnectors();
            for (Connector connector : connectors) {
                JSONObject portJson = new JSONObject();
                String portId = nodeId + ":" + connector.getOrder();
                String portType = externalConnectorMap.containsKey(connector) ? "external" : "internal";
                JSONArray portAttributes = defaultPortAttributes(portId);
                portJson.put("port-id", portId);
                portJson.put("port-type", portType);
                portJson.put("attribute", portAttributes);
                ports.put(index, portJson);
                index++;
            }
            nodeJson.put("node-id", nodeId);
            nodeJson.put("node-type", type);
            nodeJson.put("attribute", attributes);
            nodeJson.put("port", ports);
            return nodeJson;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Exception:",e);
        }
        return null;
    }

    private JSONArray defaultNodeAttributes(String nodeId) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "location");
        jsonObject.put("value", nodeId);
        jsonArray.put(0, jsonObject);
        return jsonArray;
    }

    private JSONArray defaultPortAttributes(String portId) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "location");
        jsonObject.put("value", portId);
        jsonArray.put(0, jsonObject);
        return jsonArray;
    }

    public JSONObject externalJsonNode() {
        JSONObject root = new JSONObject();
        JSONArray externalMacs = new JSONArray();
        int index = 0;
        try {
            for (Connector exCon : externalConnectorMap.keySet()) {
                Connector hostCon = externalConnectorMap.get(exCon);
                String hostMac = CmdExecutor.queryInterfaceMac(hostCon.getConnectorName(), hostCon.getNodeName());
                Switch sw = switchMap.get(exCon.getNodeName());
                if (hostMac != null && sw != null) {
                    JSONObject exNode = new JSONObject();
                    String nodeId = "openflow:" + sw.getDataPathId();
                    String portId = nodeId + ":" + exCon.getOrder();

                    exNode.put("node-id", nodeId);
                    exNode.put("port-id", portId);
                    exNode.put("mac-address", hostMac);
                    externalMacs.put(index, exNode);
                    index++;
                }
            }
            root.put("external-network-mac", externalMacs);
            return root;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            log.error("Exception:",e);
        }
        return null;
    }

    public JSONObject hostJsonNode() {
        JSONObject root = new JSONObject();
        JSONArray hostArray = new JSONArray();
        int index = 0;
        try {
            for (Connector exCon : externalConnectorMap.keySet()) {
                Connector hostCon = externalConnectorMap.get(exCon);
                String exConMac = CmdExecutor.queryInterfaceMac(exCon.getConnectorName());
                Switch sw = switchMap.get(exCon.getNodeName());

                JSONObject hostJson = new JSONObject();
                String hostName = hostCon.getNodeName();
                Host host = hostMap.get(hostName);
                if ( exConMac != null && sw != null && host != null) {
                    JSONArray ipv4Array = ipAddress(hostCon);
                    String nodeId = "openflow:" + sw.getDataPathId();
                    String connectorId = nodeId + ":" + exCon.getOrder();
                    hostJson.put("name", hostName);
                    hostJson.put("id", host.getUuid());
                    hostJson.put("type", host.getNodeType());
                    hostJson.put("ip-addresses", ipv4Array);
                    hostJson.put("mac-address", exConMac);
                    hostJson.put("node-id", nodeId);
                    hostJson.put("connector-id", connectorId);
                    hostArray.put(index, hostJson);
                    index++;
                } else {
                    log.error("Can not put host [{}] to configuration file,exMac:{}  sw:{} id:{}.", hostName, sw);
                }
            }
            root.put("host", hostArray);
            return root;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            log.error("Exception:",e);
        }
        return null;
    }

    private JSONArray ipAddress(Connector connector) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        Host host = hostMap.get(connector.getNodeName());
        if (host != null) {
            String ipv4 = host.getIpv4(connector.getOrder());
            if (ipv4 != null) {
                ipv4 = ipv4 + "/";
                ipv4 = ipv4.substring(0, ipv4.indexOf("/"));
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ip-address", ipv4);
                jsonArray.put(0, jsonObject);
                return jsonArray;
            }
        }
        return null;
    }

    public JSONObject innerLinkJsonNode() {
        JSONObject root = new JSONObject();
        JSONArray linkArray = new JSONArray();
        int index = 0;
        try {
            for (Link link : internalLinks) {
                JSONObject srcLinkNode = buildLinkNode(link.getSrcConnector());
                if (srcLinkNode != null) {
                    linkArray.put(index, srcLinkNode);
                    index++;
                }
                JSONObject dstLinkNode = buildLinkNode(link.getDstConnector());
                if (srcLinkNode != null) {
                    linkArray.put(index, dstLinkNode);
                    index++;
                }
            }
            root.put("link", linkArray);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            log.error("Exception:",e);
            return null;
        }
        return root;
    }

    private JSONObject buildLinkNode(Connector connector) {
        Switch sw = switchMap.get(connector.getNodeName());
        if (sw != null) {
            JSONObject linkNode = new JSONObject();
            String nodeId = "openflow:" + sw.getDataPathId();
            String linkId = nodeId + ":" + connector.getOrder();
            try {
                linkNode.put("link-id", linkId);
                linkNode.put("metric", "1");
                linkNode.put("delay", "");
                linkNode.put("loss-rate", "");
                return linkNode;
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                log.error("Exception:",e);
            }
        }
        return null;
    }
}
