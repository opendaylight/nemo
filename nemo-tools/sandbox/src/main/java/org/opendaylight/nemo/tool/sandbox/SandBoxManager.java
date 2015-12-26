/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.tool.sandbox;

import org.json.JSONObject;
import org.opendaylight.nemo.tool.sandbox.models.*;
import org.opendaylight.nemo.tool.sandbox.utils.PropertyLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hj on 12/9/15.
 */
public class SandBoxManager implements ISandboxManager {
    private static Logger log = LoggerFactory.getLogger(SandBoxManager.class);
    private static SandBoxManager sandBoxManager = new SandBoxManager();

    public static SandBoxManager getInstance() {
        return sandBoxManager;
    }

    private Network network = null;

    private SandBoxManager() {
        List<String> list = PropertyLoader.readLines("/Network", "Network file does not exist.");
        network = generateNetwork(list);
    }

    @Override
    public boolean createNetwork(List<String> list) {
        if (CmdExecutor.open()) {
            if (network != null && CmdExecutor.open()) {
                network.uninstall();
                CmdExecutor.close();
            }
            network = generateNetwork(list);
            return createNetwork();
        } else {
            System.out.println("Can not open ssh.");
        }
        return false;

    }

    @Override
    public boolean createNetwork() {
        if (network != null && CmdExecutor.open()) {
            network.install();
            network.echoConfig();
            CmdExecutor.close();
            System.out.println("Network have been installed.");
            return true;
        } else {
            System.out.println("Network is null or can not open ssh,Network: " + network);
        }
        return false;
    }

    @Override
    public String getHosts() {
        if (network != null && CmdExecutor.open()) {
            JSONObject jsonObject = network.hostJsonNode();
            CmdExecutor.close();
            if (jsonObject != null) {
                return jsonObject.toString();
            }
        } else {
            System.out.println("Network is null or can not open ssh,Network: " + network);
        }
        return null;
    }

    @Override
    public String getExternals() {
        if (network != null && CmdExecutor.open()) {
            JSONObject jsonObject = network.externalJsonNode();
            CmdExecutor.close();
            if (jsonObject != null) {
                return jsonObject.toString();
            }
        } else {
            System.out.println("Network is null or can not open ssh,Network: " + network);
        }
        return null;
    }

    @Override
    public String getSwitches() {
        if (network != null) {
            JSONObject jsonObject = network.nodeJsonNode();
            if (jsonObject != null) {
                return jsonObject.toString();
            }
        } else {
            System.out.println("Network is null.");
        }
        return null;
    }

    @Override
    public String getLinks() {
        if (network != null) {
            JSONObject jsonObject = network.innerLinkJsonNode();
            if (jsonObject != null) {
                return jsonObject.toString();
            }
        } else {
            System.out.println("Network is null.");
        }
        return null;
    }

    @Override
    public boolean destroyNetwork() {
        if (network != null && CmdExecutor.open()) {
            network.uninstall();
            network = null;
            CmdExecutor.close();
        }
        return true;
    }

    @Override
    public String execute(String name, String command) {
        return network.execute(name,command);
    }

    private Network generateNetwork(List<String> list) {
        Network network = new Network();
        Map<String/*node name*/, List<Connector>> connectorMap = new HashMap<String, List<Connector>>();
        List<String> nodes = new ArrayList<String>();
        for (String line : list) {
            if (line.startsWith("Link")) {
                Link link = handleLink(line);
                if (link != null) {
                    network.addLink(link);
                    saveConnector(link.getSrcConnector(), connectorMap);
                    saveConnector(link.getDstConnector(), connectorMap);
                }
            } else {
                nodes.add(line);
            }
        }
        for (String line : nodes) {
            NodeType nodeType = getNodeType(line);
            switch (nodeType) {
                case SWITCH:
                case ROUTER:
                    Switch aSwitch = handleSwitch(line, connectorMap);
                    if (aSwitch != null) {
                        network.addSwitch(aSwitch);
                    }
                    break;
                case VM:
                    VirtualMachine virtualMachine = handleVirtualMachine(line, connectorMap);
                    if (virtualMachine != null) {
                        network.addHost(virtualMachine);
                    }
                    break;
                case CACHE:
                    Cache cache = handleCache(line, connectorMap);
                    if (cache != null) {
                        network.addHost(cache);
                    }
                    break;
                case FIREWALL:
                    Firewall firewall = handleFirewall(line, connectorMap);
                    if (firewall != null) {
                        network.addHost(firewall);
                    }
                    break;
                default:
                    log.warn("Ignore line : {}.", line);
            }
        }
        return network;
    }

    private Switch handleSwitch(String line, Map<String, List<Connector>> connectorMap) {
        line = line.trim();
        String args[] = line.split(",");
        if (args.length == 3) {
            Switch sw = null;
            if (line.startsWith("Router"))
                sw = new Router(args[1].trim(), Long.parseLong(args[2]));
            else
                sw = new Switch(args[1].trim(), Long.parseLong(args[2]));
            List<Connector> connectors = connectorMap.get(sw.getName());
            if (connectors != null) {
                for (Connector connector : connectors) {
                    sw.addConnectors(connector);
                }
            }
            return sw;
        }
        return null;
    }

    private VirtualMachine handleVirtualMachine(String line, Map<String, List<Connector>> connectorMap) {
        line = line.trim();
        String args[] = line.split(",");
        if (args.length >= 4) {
            VirtualMachine virtualMachine = new VirtualMachine(args[1].trim(),args[2].trim());
            parserHost(args, connectorMap, virtualMachine);
            return virtualMachine;
        }
        return null;
    }

    private Cache handleCache(String line, Map<String, List<Connector>> connectorMap) {
        line = line.trim();
        String args[] = line.split(",");
        if (args.length >= 4) {
            Cache cache = new Cache(args[1].trim(),args[2].trim());
            parserHost(args, connectorMap, cache);
            return cache;
        }
        return null;
    }

    private Firewall handleFirewall(String line, Map<String, List<Connector>> connectorMap) {
        line = line.trim();
        String args[] = line.split(",");
        if (args.length >= 4) {
            Firewall firewall = new Firewall(args[1].trim(),args[2].trim());
            parserHost(args, connectorMap, firewall);
            return firewall;
        }
        return null;
    }

    private void parserHost(String[] args, Map<String, List<Connector>> connectorMap, Host host) {
        List<Connector> connectors = connectorMap.get(host.getName());
        if (connectors != null) {
            for (Connector connector : connectors) {
                host.addConnectors(connector);
            }
        }
        for (int i = 3; i < args.length; i++) {
            String[] array = args[i].trim().split(":");
            if (array.length == 2) {
                int order = Integer.parseInt(array[0]);
                host.putConnectorIpv4(order, array[1]);
            }
        }

    }

    private void saveConnector(Connector connector, Map<String, List<Connector>> connectorMap) {
        String nodeName = connector.getNodeName();
        if (!connectorMap.containsKey(nodeName)) {
            connectorMap.put(nodeName, new ArrayList<Connector>());
        }
        connectorMap.get(nodeName).add(connector);
    }

    private Link handleLink(String link) {
        link = link.trim();
        String args[] = link.split(",");
        if (args.length == 5) {
            Connector lConnector = new Connector(args[1].trim(), Integer.parseInt(args[2].trim()));
            Connector rConnector = new Connector(args[3].trim(), Integer.parseInt(args[4].trim()));
            return new Link(lConnector, rConnector);
        }
        return null;
    }

    private NodeType getNodeType(String line) {
        if (line.startsWith("Switch"))
            return NodeType.SWITCH;
        if (line.startsWith("Router")) {
            return NodeType.ROUTER;
        }
        if (line.startsWith("Cache")) {
            return NodeType.CACHE;
        }
        if (line.startsWith("Firewall")) {
            return NodeType.FIREWALL;
        }
        if (line.startsWith("Vm")) {
            return NodeType.VM;
        }
        return NodeType.UNKNOWN;
    }
}
