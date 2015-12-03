/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.syntaxcheck;

import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Results;
import static org.junit.Assert.*;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Created by wjf on 2015/11/25.
 */
public class ResultDefinitionCheckTest {
    private ResultDefinitionCheck resultDefinitionCheck;
    private Results results;
    @org.junit.Before
    public void setUp() throws Exception {
        results = mock(Results.class);
        resultDefinitionCheck = new ResultDefinitionCheck();
    }

    @org.junit.Test
    public void testCheckDefinition() throws Exception {
        Assert.assertNull(resultDefinitionCheck.CheckDefinition(results));
    }
}