/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.advancedquery;

import java.util.List;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.QueryConditionDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.advanced.nemo.query.input.QueryCondition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.query.condition.definitions.QueryConditionDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.query.condition.definitions.query.condition.definition.QueryConditionMatchPatterns;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.query.condition.definitions.QueryConditionDefinition.QueryConditionValueType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.query.condition.instance.QueryConditionTargetValue;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.QueryConditionName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Optional;
import com.google.common.base.Function;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;
import static org.mockito.Mockito.mock;
import java.lang.reflect.Method; 
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.*;
import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
import static org.mockito.Mockito.*;
/*
 *Created by Gao Jie on 2015/12/02
*/
public class QueryDefinitionCheckTest{
	private DataBroker dataBroker;
	private QueryDefinitionCheck querydefinitioncheck;
	private  QueryCondition queryCondition;
	private QueryConditionDefinition queryconditionDefinition;
    private List<QueryConditionDefinition> queryconditionDefinitionlist;
	private QueryConditionName queryconditionname;
	private QueryConditionName notqueryconditionname;
	
/* 	private List<Property> connectionProperty;
	private List<PropertyDefinition> propertyDefinitionList;
	private Property property;
	private PropertyDefinition propertyDefinition; */
	@org.junit.Before
	public void setUp() throws Exception{
		dataBroker=mock(DataBroker.class);
		querydefinitioncheck=new QueryDefinitionCheck(dataBroker);
		queryCondition=mock(QueryCondition.class);
		queryconditionDefinition=mock(QueryConditionDefinition.class);
		queryconditionDefinitionlist=null;
		queryconditionname=mock(QueryConditionName.class);
		notqueryconditionname=mock(QueryConditionName.class);
/* 		property = mock(Property.class);
		propertyDefinition = mock(PropertyDefinition.class);
		connectionProperty = new ArrayList<Property>(3);
		propertyDefinitionList = new ArrayList<PropertyDefinition>(3);
		connectionDefinitionList.add(connectionDefinition); */
	}
	@org.junit.Test
	public void CheckQueryDefinitionTest() throws Exception{
		CheckedFuture connectiondefinitionFuture = mock(CheckedFuture.class);
        ReadOnlyTransaction readOnlyTransaction = mock(ReadOnlyTransaction.class);
        when(dataBroker.newReadOnlyTransaction()).thenReturn(readOnlyTransaction);
        when(readOnlyTransaction.read(any(LogicalDatastoreType.class), any(InstanceIdentifier.class))).thenReturn(connectiondefinitionFuture);
		//branch 1 queryConditionDefinitions = null
		Assert.assertEquals(querydefinitioncheck.CheckQueryDefinition(queryCondition),"The condition has not been defined.");
		//branch 2 queryConditionDefinitions != null
		queryconditionDefinitionlist=new ArrayList<QueryConditionDefinition>(3);
		queryconditionDefinitionlist.add(queryconditionDefinition);
		when(queryCondition.getQueryConditionName()).thenReturn(queryconditionname);
		when(queryCondition.getQueryConditionName()).thenReturn(notqueryconditionname);
		Assert.assertEquals(querydefinitioncheck.CheckQueryDefinition(queryCondition),"The condition has not been defined.");
	    //branch 3
		Method setqueryconditiondefinitions = querydefinitioncheck.getClass().getDeclaredMethod("setQueryConditionDefinitions",List.class);  
		setqueryconditiondefinitions.setAccessible(true);
		Assert.assertNull(setqueryconditiondefinitions.invoke(querydefinitioncheck,queryconditionDefinitionlist));
		when(queryCondition.getQueryConditionName()).thenReturn(queryconditionname);
		when(queryconditionDefinition.getQueryConditionName()).thenReturn(queryconditionname);
		when(queryCondition.getQueryIntentType()).thenReturn(null);
		when(queryCondition.getQueryConditionTargetValue()).thenReturn(null);
		when(queryCondition.getQueryConditionMatchPattern()).thenReturn(null);
		Assert.assertEquals(querydefinitioncheck.CheckQueryDefinition(queryCondition),null);
		//branch  4
		when(queryCondition.getQueryConditionName()).thenReturn(queryconditionname);
		when(queryconditionDefinition.getQueryConditionName()).thenReturn(queryconditionname);
		when(queryCondition.getQueryIntentType()).thenReturn(null);
		QueryConditionTargetValue queryconditiontargetvalue=mock(QueryConditionTargetValue.class);
		when(queryCondition.getQueryConditionTargetValue()).thenReturn(queryconditiontargetvalue);
		when(queryconditionDefinition.getQueryConditionMatchPatterns()).thenReturn(null);
		Assert.assertEquals(querydefinitioncheck.CheckQueryDefinition(queryCondition),"There are no query condition value type defined in query condition.");
		//branch 5
		/* when(queryCondition.getQueryConditionName()).thenReturn(queryconditionname);
		when(queryconditionDefinition.getQueryConditionName()).thenReturn(queryconditionname);
		when(queryCondition.getQueryIntentType()).thenReturn(null);
		when(queryCondition.getQueryConditionTargetValue()).thenReturn(null);
		QueryConditionMatchPatterns queryconditionmatchpattenrs=mock(QueryConditionMatchPatterns.class);
		when(queryCondition.getQueryConditionMatchPatterns()).thenReturn(queryconditionmatchpattenrs); */
		
	}
}