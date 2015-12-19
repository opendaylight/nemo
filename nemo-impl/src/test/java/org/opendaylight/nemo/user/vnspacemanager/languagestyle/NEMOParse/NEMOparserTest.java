/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
/* Generated By:JavaCC: Do not edit this line. NEMOparser.java */
package user.vnspacemanager.languagestyle.NEMOParse;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOParse.NEMOparser;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOParse.NEMOparserTokenManager;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOParse.SimpleCharStream;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.updateintentlang.UpdateNodeLang;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.updateintentlang.UpdateConnectionLang;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.updateintentlang.UpdateFlowLang;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.updateintentlang.UpdateOperationLang;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.deleteintentlang.DeleteNodeLang;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.deleteintentlang.DeleteConnectionLang;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.deleteintentlang.DeleteFlowLang;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.deleteintentlang.DeleteOperationLang;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.Query;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.StringReader;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
/**
 * Created by zhangmeng on 2015/12/16.
 */
public class NEMOparserTest extends TestCase {
    private InputStream inputStream;
    private Reader reader;
    private Reader reader_empty;
    private NEMOparserTokenManager nemOparserTokenManager;
    private NEMOparser nemOparser;
    private Class<NEMOparser> class1;
    private Class<NEMOparserTokenManager> nemOparserTokenManagerClass;
    private Class<SimpleCharStream> simpleCharStreamClass;
    private Field field_jj_initialized_once;
    private Field field_token_source;
    private Field field_jj_input_stream;
    private Method method_ReInit_SimpleCharStreamClass;
    private Method method_ReInit_NEMOparserTokenManager;

    @Before
    public void setUp() throws Exception {
        class1 = NEMOparser.class;
        nemOparserTokenManagerClass = NEMOparserTokenManager.class;
        simpleCharStreamClass = SimpleCharStream.class;
        field_jj_initialized_once = class1.getDeclaredField("jj_initialized_once");//Nemoparser
        field_token_source = class1.getDeclaredField("token_source");//NEMOparserTokenManager
        field_jj_input_stream = class1.getDeclaredField("jj_input_stream");//SimpleCharStream

        field_token_source.setAccessible(true);
        field_jj_input_stream.setAccessible(true);
        field_jj_initialized_once.setAccessible(true);


        method_ReInit_SimpleCharStreamClass = simpleCharStreamClass.getDeclaredMethod("ReInit", new Class[]{Reader.class, int.class, int.class, int.class});
        method_ReInit_SimpleCharStreamClass.setAccessible(true);
        method_ReInit_NEMOparserTokenManager = nemOparserTokenManagerClass.getDeclaredMethod("ReInit", new Class[]{SimpleCharStream.class});
        method_ReInit_NEMOparserTokenManager.setAccessible(true);

        inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
        reader = new Reader() {
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                return 0;
            }

            @Override
            public void close() throws IOException {

            }
        };
        reader_empty = null;
        nemOparserTokenManager = mock(NEMOparserTokenManager.class);
    }

    @Test
    public void testInit() throws Exception{

        Field field = simpleCharStreamClass.getDeclaredField("inputStream");
        field.setAccessible(true);
        field.set(simpleCharStreamClass,null);
        nemOparser = new NEMOparser(inputStream,null);
        Assert.assertTrue(field_jj_input_stream.get(class1) instanceof SimpleCharStream);
        Assert.assertTrue(field_token_source.get(class1) instanceof NEMOparserTokenManager);
        method_ReInit_SimpleCharStreamClass.invoke(field_jj_input_stream.get(class1), null, 1, 1, 1);
        NEMOparserTokenManager.ReInit(null);
        field_jj_initialized_once.set(class1, false);
        Assert.assertTrue((Boolean) field_jj_initialized_once.get(class1) == false);

        nemOparser = new NEMOparser(inputStream);
        method_ReInit_SimpleCharStreamClass.invoke(field_jj_input_stream.get(class1), null, 1, 1, 1);
        NEMOparserTokenManager.ReInit(null);
        field_jj_initialized_once.set(class1, false);

        nemOparser = new NEMOparser(nemOparserTokenManager);
        method_ReInit_SimpleCharStreamClass.invoke(field_jj_input_stream.get(class1), null, 1, 1, 1);
        NEMOparserTokenManager.ReInit(null);
        field_jj_initialized_once.set(class1, false);

        nemOparser = new NEMOparser(reader);
        method_ReInit_SimpleCharStreamClass.invoke(field_jj_input_stream.get(class1), null, 1, 1, 1);
        NEMOparserTokenManager.ReInit(null);
        field_jj_initialized_once.set(class1, false);

        nemOparser.ReInit(nemOparserTokenManager);
        NEMOparser.ReInit(inputStream);
        NEMOparser.ReInit(inputStream, null);
        NEMOparser.ReInit(reader_empty);
        method_ReInit_SimpleCharStreamClass.invoke(field_jj_input_stream.get(class1), null, 1, 1, 1);
        NEMOparserTokenManager.ReInit(null);
        field_jj_initialized_once.set(class1, false);
    }

//    @Test
//    public void testParseNEMO() throws Exception {
//        UserId userId = mock(UserId.class);
//        DataBroker dataBroker = mock(DataBroker.class);
//        TenantManage tenantManage = mock(TenantManage.class);
//
//        nemOparser = new NEMOparser(inputStream);
////        nemOparser.parseNEMO(userId,strNEMO,dataBroker,tenantManage);
//
//
//        method_ReInit_SimpleCharStreamClass.invoke(field_jj_input_stream.get(class1), null, 1, 1, 1);
//        NEMOparserTokenManager.ReInit(null);
//        field_jj_initialized_once.set(class1, false);
//    }

    @Test
    public void testNEMO() throws Exception {
//        UserId userId = mock(UserId.class);
//        DataBroker dataBroker = mock(DataBroker.class);
//        TenantManage tenantManage = mock(TenantManage.class);
//
//        nemOparser = new NEMOparser(inputStream);
//        Field field_jj_ntk = class1.getDeclaredField("jj_ntk");
//        field_jj_ntk.setAccessible(true);
//        field_jj_ntk.set(class1, 25);
//
//        System.out.println("jj_ntk:" + field_jj_ntk.get(class1));
//
//        NEMOparser.NEMO(userId,dataBroker,tenantManage);
//        method_ReInit_SimpleCharStreamClass.invoke(field_jj_input_stream.get(class1), null, 1, 1, 1);
//        NEMOparserTokenManager.ReInit(null);
//        field_jj_initialized_once.set(class1, false);
    }

    @Test
    public void testUpdateIntent() throws Exception {

    }

    @Test
    public void testDeleteIntent() throws Exception {

    }

    @Test
    public void testUpdateNode() throws Exception {

    }

    @Test
    public void testDeleteNode() throws Exception {

    }

    @Test
    public void testUpdateConnection() throws Exception {

    }

    @Test
    public void testDeleteConnection() throws Exception {

    }

    @Test
    public void testUpdateFlow() throws Exception {

    }

    @Test
    public void testDeleteFlow() throws Exception {

    }

    @Test
    public void testUpdateOperation() throws Exception {

    }

    @Test
    public void testDeleteOperation() throws Exception {

    }

    @Test
    public void testQuery() throws Exception {

    }

    @Test
    public void testGetNextToken() throws Exception {

    }

    @Test
    public void testGetToken() throws Exception {

    }

    @Test
    public void testGenerateParseException() throws Exception {

    }

    @Test
    public void testEnable_tracing() throws Exception {

    }

    @Test
    public void testDisable_tracing() throws Exception {

    }
}