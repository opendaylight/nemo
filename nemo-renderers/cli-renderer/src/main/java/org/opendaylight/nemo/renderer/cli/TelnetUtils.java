/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.renderer.cli;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 *
 * @author Shixing Liu
 */
public class TelnetUtils implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(TelnetUtils.class);

    private static Boolean ClearOver = Boolean.TRUE;
    private static String instanceOfClearCliTemplate = null;
    private static String instanceOfNewCliTemplate = null;
    private static String currentDeviceName = null;
    private static Boolean ConfigOver = Boolean.FALSE;

    /**
     *
     */
    public TelnetUtils(){
        LOG.info("TelnetUtils constructor");
    }

    /**
     *
     * @throws Exception
     */
    public void close() throws Exception {
        // TODO

		return;
    }

    /**
     *
     * @return
     */
    public static Boolean isClearOver() {
        return ClearOver;
    }

    /**
     *
     * @param clearOver
     */
    public static void setClearOver(Boolean clearOver) {
        ClearOver = clearOver;
    }

    /**
     *
     * @return
     */
    public static String getInstanceOfClearCliTemplate() {
        return instanceOfClearCliTemplate;
    }

    /**
     *
     * @param instanceOfClearCliTemplate
     */
    public static void setInstanceOfClearCliTemplate(String instanceOfClearCliTemplate) {
        TelnetUtils.instanceOfClearCliTemplate = instanceOfClearCliTemplate;
    }

    /**
     *
     * @return
     */
    public static String getInstanceOfNewCliTemplate() {
        return instanceOfNewCliTemplate;
    }

    /**
     *
     * @param instanceOfNewCliTemplate
     */
    public static void setInstanceOfNewCliTemplate(String instanceOfNewCliTemplate) {
        TelnetUtils.instanceOfNewCliTemplate = instanceOfNewCliTemplate;
    }

    /**
     *
     * @return
     */
    public static String getCurrentDeviceName() {
        return currentDeviceName;
    }

    /**
     *
     * @param currentDeviceName
     */
    public static void setCurrentDeviceName(String currentDeviceName) {
        TelnetUtils.currentDeviceName = currentDeviceName;
    }

    /**
     *
     * @return
     */
    public static Boolean isConfigOver() {
        return ConfigOver.booleanValue();
    }

    /**
     *
     * @param configOver
     */
    public static void setConfigOver(Boolean configOver) {
        ConfigOver = configOver;
    }

    /**
     *
     * @param simpleChannelUpstreamHandler
     */
    private void startConnectAndConfig(SimpleChannelUpstreamHandler simpleChannelUpstreamHandler){
        ChannelFactory factory = new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool());
        ClientBootstrap bootstrap = new ClientBootstrap(factory);
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() {
                ChannelPipeline p = Channels.pipeline();
                p.addLast("encode", new StringEncoder());
                p.addLast("decode", new StringDecoder());
                p.addLast("handler", new ClientHandlerForClearOldConfig());
                return p;
            }
        });
        bootstrap.setOption("tcpNoDelay" , true);
        bootstrap.setOption("keepAlive", true);
        bootstrap.setOption("connectTimeoutMillis", 5000);

        String ipAddress = null;
        String deviceName = getCurrentDeviceName();
        if(deviceName.contains("PE1")) {
            ipAddress = "191.4.1.114";
        }else if(deviceName.contains("PE2")){
            ipAddress = "191.4.1.77";
        }else if(deviceName.contains("PE3")){
            ipAddress = "191.4.1.103";
        }
        System.out.println("Try to clear old configuration for device "+ deviceName+"\n");
        bootstrap.connect(new InetSocketAddress(ipAddress, 23));

    }

    /**
     *
     */
    private void clearOldConfig(){

        ChannelFactory factory = new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool());
        ClientBootstrap bootstrap = new ClientBootstrap(factory);
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() {
                ChannelPipeline p = Channels.pipeline();
                p.addLast("encode", new StringEncoder());
                p.addLast("decode", new StringDecoder());
                p.addLast("handler", new ClientHandlerForClearOldConfig());
                return p;
            }
        });
        bootstrap.setOption("tcpNoDelay" , true);
        bootstrap.setOption("keepAlive", true);
        bootstrap.setOption("connectTimeoutMillis", 5000);

        String ipAddress = null;
        String deviceName = getCurrentDeviceName();
        if(deviceName.contains("PE1")) {
            ipAddress = "191.4.1.114";
        }else if(deviceName.contains("PE2")){
            ipAddress = "191.4.1.77";
        }else if(deviceName.contains("PE3")){
            ipAddress = "191.4.1.103";
        }
        System.out.println("Try to clear old configuration for device "+ deviceName+"\n");
        bootstrap.connect(new InetSocketAddress(ipAddress, 23));
    }

    /**
     *
     */
    private static class ClientHandlerForClearOldConfig extends SimpleChannelUpstreamHandler {
        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
        }

        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)throws Exception {
            String str = (String)(e.getMessage());
            System.out.println(e.getMessage());
            if(str.contains("Username")){
                e.getChannel().write("lfk\n");
            }
            else if(str.contains("Password")){
                e.getChannel().write("123!@#\n");
            }
            else if(str.contains("The current login time is")){
                //System.out.println("------------b1----------");
                setClearOver(Boolean.FALSE);
                e.getChannel().write("system-view\n");
                e.getChannel().write("undo ip vpn-instance vpn1\n");

            }else if(str.contains("Error: The VPN instance does not exist.")){
                //System.out.println("------------b2----------");
                setClearOver(Boolean.TRUE);
                e.getChannel().close();
                System.out.println("Clear is completed for device " + getCurrentDeviceName());
                //start send down new config cli
                sendNewConfig();
            }else if(str.contains("Error: The VPN instance is in stale state.")){
                //System.out.println("------------b3----------");
                e.getChannel().write("undo ip vpn-instance vpn1\n");
            }
            else if((isClearOver() == Boolean.FALSE)
                    &&(!str.contains("Error: The VPN instance does not exist."))
                    &&(!str.contains("Error: The VPN instance is in stale state."))){
                //System.out.println("------------b4----------");
                e.getChannel().write("undo ip vpn-instance vpn1\n");
            }
            super.messageReceived(ctx, e);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
            // TODO Auto-generated catch block
            LOG.error("Exception:",e.getCause());
           // e.getCause().printStackTrace();
            e.getChannel().close();
        }
    }

    /**
     *
     */
    private static void sendNewConfig(){
        ChannelFactory factory = new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool());
        ClientBootstrap bootstrap = new ClientBootstrap(factory);
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() {
                ChannelPipeline p = Channels.pipeline();
                p.addLast("encode",new StringEncoder());
                p.addLast("decode",new StringDecoder());
                p.addLast("handler",new ClientHandlerForSendNewConfig());
                return p;
            }
        });
        bootstrap.setOption("connectTimeoutMillis", 6000);
        bootstrap.setOption("tcpNoDelay" , true);
        bootstrap.setOption("keepAlive", true);

        String ipAddress = null;
        String deviceName = getCurrentDeviceName();
        if(deviceName.contains("PE1")) {
            ipAddress = "191.4.1.114";
        }else if(deviceName.contains("PE2")){
            ipAddress = "191.4.1.77";
        }else if(deviceName.contains("PE3")){
            ipAddress = "191.4.1.103";
        }
        System.out.println("Start to configure device " + deviceName + " with CLI.");
        bootstrap.connect(new InetSocketAddress(ipAddress, 23));
    }

    /**
     *
     * @param deviceName
     * @param clearOldConfigString
     * @param cliExecutionSequences
     */
    public void configL3vpnOnDeviceByCli(String deviceName,
                                     String clearOldConfigString,
                                     String cliExecutionSequences){

        setConfigOver(Boolean.FALSE);
        setInstanceOfClearCliTemplate(clearOldConfigString);
        setInstanceOfNewCliTemplate(cliExecutionSequences);
        setCurrentDeviceName(deviceName);
        clearOldConfig();
    }

    /**
     *
     */
    private static class ClientHandlerForSendNewConfig extends SimpleChannelUpstreamHandler {
        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
        }

        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
            String str = (String)(e.getMessage());
            System.out.println(e.getMessage());
            if(str.contains("Username")){
                e.getChannel().write("lfk\n");
            }
            else if(str.contains("Password")){
                e.getChannel().write("123!@#\n");
            }
            if (str.contains("The current login time is")) {
                System.out.println("ClientHandlerForConfig ");
                e.getChannel().write("system-view\n");
                e.getChannel().write(getInstanceOfClearCliTemplate());
                e.getChannel().write(getInstanceOfNewCliTemplate());
                setConfigOver(Boolean.TRUE);
                System.out.println("Configuration is completed for device "+ getCurrentDeviceName());

            }
            super.messageReceived(ctx, e);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
          //  e.getCause().printStackTrace();
            // TODO Auto-generated catch block
            LOG.error("Exception:",e.getCause());
            e.getChannel().close();
        }
    }
}
