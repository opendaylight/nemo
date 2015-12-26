/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.vnspacemanager.languagestyle.updateintentlang;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.user.tenantmanager.TenantManage;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
import org.opendaylight.nemo.user.vnspacemanager.structurestyle.updateintent.UpdateOperation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.OperationBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.OperationKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.ParameterValuesBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.action.instance.parameter.values.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegmentBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegmentKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.condition.segment.ConditionParameterTargetValueBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.ActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.ActionKey;

import java.util.*;

/**
 * Created by z00293636 on 2015/11/6.
 */
public class UpdateOperationLang {
    private TenantManage tenantManage;
    private Operation operation;
    private UpdateOperation updateOperation;
    List<ConditionSegment> conditionSegmentList = new ArrayList<ConditionSegment>();
    List<Action> actionList = new ArrayList<Action>();

    public UpdateOperationLang(DataBroker dataBroker, TenantManage tenantManage){
        this.tenantManage = tenantManage;
        updateOperation = new UpdateOperation(dataBroker, tenantManage);
    }

    public String OperationHandling(UserId userId, String operationname, String target, String priority,
                                    LinkedHashMap<String,LinkedHashMap<String,String>> conditions,LinkedHashMap<String,LinkedHashMap<String,String>>actions){
        String errorInfo = null;
        errorInfo = createOperation(userId,operationname,target,priority,conditions,actions);
        if (errorInfo==null){
            errorInfo = updateOperation.OperationHandling(userId,this.operation);
        }
        return errorInfo;
    }

    private String createOperation(UserId userId, String operationname, String target, String priority,
                                   LinkedHashMap<String,LinkedHashMap<String,String>> conditions,LinkedHashMap<String,LinkedHashMap<String,String>>actions){
        String errorInfo = null;
        Boolean TargetExist = false;
        OperationBuilder operationBuilder = new OperationBuilder();

        Operation operation1 = null;
        if (tenantManage.getObjectId(userId,operationname)!=null){
            OperationId operationId = new OperationId(tenantManage.getObjectId(userId,operationname));
            if (tenantManage.getOperation(userId).containsKey(operationId)){
                operation1 = tenantManage.getOperation(userId).get(operationId);
            }
            if (tenantManage.getOperationDataStore(userId).containsKey(operationId)){
                operation1 = tenantManage.getOperationDataStore(userId).get(operationId);
            }
        }
        if (operation1!=null){
            operationBuilder.setKey(operation1.getKey())
                    .setOperationId(operation1.getOperationId());
        }
       else {
            OperationId operationId = new OperationId(UUID.randomUUID().toString());
            operationBuilder.setKey(new OperationKey(operationId));
            operationBuilder.setOperationId(operationId);
        }

        if (tenantManage.getObjectId(userId,target)!=null){
            operationBuilder.setTargetObject(new ObjectId(tenantManage.getObjectId(userId,target)));
        }
        else {
            return "The target " + target + " is not exist.";
        }
        operationBuilder.setOperationName(new OperationName(operationname));
        operationBuilder.setPriority(Long.parseLong(priority));

        if (!conditions.isEmpty() && errorInfo==null){
            errorInfo = createCondition(conditions);
            if (errorInfo==null) {
                operationBuilder.setConditionSegment(conditionSegmentList);
            }
        }

        if (!actions.isEmpty() && errorInfo ==null){
            errorInfo = createAction(userId,actions);
            if (errorInfo==null){
                operationBuilder.setAction(actionList);
            }
        }
        operation = operationBuilder.build();
        return  errorInfo;
    }

    private String createCondition(LinkedHashMap<String,LinkedHashMap<String,String>> conditions){
        String errorInfo = null;

        for (String conditionname : conditions.keySet()){
            String[] condition = conditionname.split(NEMOConstants.comma);
            ConditionSegmentBuilder conditionSegmentBuilder = new ConditionSegmentBuilder();
            ConditionSegmentId conditionSegmentId = new ConditionSegmentId(UUID.randomUUID().toString());
            conditionSegmentBuilder.setKey(new ConditionSegmentKey(conditionSegmentId));
            conditionSegmentBuilder.setConditionSegmentId(conditionSegmentId);

            String preRelationOperator = condition[0];
            if (preRelationOperator == null){
                conditionSegmentBuilder.setPrecursorRelationOperator(ConditionSegment.PrecursorRelationOperator.None);
            }
            else if (preRelationOperator.equals(NEMOConstants.not)){
                conditionSegmentBuilder.setPrecursorRelationOperator(ConditionSegment.PrecursorRelationOperator.Not);
            }
            else if (preRelationOperator.equals(NEMOConstants.and)){
                conditionSegmentBuilder.setPrecursorRelationOperator(ConditionSegment.PrecursorRelationOperator.And);
            }
            else if (preRelationOperator.equals(NEMOConstants.or)){
                conditionSegmentBuilder.setPrecursorRelationOperator(ConditionSegment.PrecursorRelationOperator.Or);
            }

            String condtionName = condition[1];
            conditionSegmentBuilder.setConditionParameterName(new ConditionParameterName(condtionName));

            String matchPattern = condition[2];
            if (matchPattern.equals(NEMOConstants.equal)){
                conditionSegmentBuilder.setConditionParameterMatchPattern(ConditionSegment.ConditionParameterMatchPattern.Equal);
            }
            else if (matchPattern.equals(NEMOConstants.not_equal)){
                conditionSegmentBuilder.setConditionParameterMatchPattern(ConditionSegment.ConditionParameterMatchPattern.NotEqual);
            }
            else if (matchPattern.equals(NEMOConstants.greater_than)){
                conditionSegmentBuilder.setConditionParameterMatchPattern(ConditionSegment.ConditionParameterMatchPattern.GreaterThan);
            }
            else if (matchPattern.equals(NEMOConstants.less_than)){
                conditionSegmentBuilder.setConditionParameterMatchPattern(ConditionSegment.ConditionParameterMatchPattern.LessThan);
            }
            else if (matchPattern.equals(NEMOConstants.not_less_than)){
                conditionSegmentBuilder.setConditionParameterMatchPattern(ConditionSegment.ConditionParameterMatchPattern.NotLessThan);
            }
            else if (matchPattern.equals(NEMOConstants.not_greater_than)){
                conditionSegmentBuilder.setConditionParameterMatchPattern(ConditionSegment.ConditionParameterMatchPattern.NotGreaterThan);
            }
            else if (matchPattern.equals(NEMOConstants.between)){
                conditionSegmentBuilder.setConditionParameterMatchPattern(ConditionSegment.ConditionParameterMatchPattern.Between);
            }

            LinkedHashMap<String, String> targetValue = conditions.get(conditionname);
            ConditionParameterTargetValueBuilder conditionParameterTargetValueBuilder = new ConditionParameterTargetValueBuilder();
            Iterator<String> iterator = targetValue.keySet().iterator();
            String value = iterator.next();

            if (targetValue.get(value).equals(NEMOConstants.string)){
                conditionParameterTargetValueBuilder.setStringValue(value);
            }
            else if (targetValue.get(value).equals(NEMOConstants.integer)){
                conditionParameterTargetValueBuilder.setIntValue(Long.parseLong(value));
            }
            else if (targetValue.get(value).equals(NEMOConstants.range)){
                RangeValueBuilder rangeValueBuilder = new RangeValueBuilder();
                String[] values = value.split(NEMOConstants.comma);
                if (Long.parseLong(values[0])>Long.parseLong(values[1])){
                    rangeValueBuilder.setMax(Long.valueOf(values[0]));
                    rangeValueBuilder.setMin(Long.parseLong(values[1]));
                }
                else{
                    rangeValueBuilder.setMin(Long.parseLong(values[0]));
                    rangeValueBuilder.setMax(Long.parseLong(values[1]));
                }
            }

            conditionSegmentBuilder.setConditionParameterTargetValue(conditionParameterTargetValueBuilder.build());
            conditionSegmentList.add(conditionSegmentBuilder.build());
        }
        return errorInfo;
    }

    private String createAction(UserId userId,LinkedHashMap<String,LinkedHashMap<String,String>>actions){
        String errorInfo = null;
        int order = 0;
        for (String actionname : actions.keySet()){
            ActionBuilder actionBuilder = new ActionBuilder();
            actionBuilder.setKey(new ActionKey(new ActionName(actionname)));
            actionBuilder.setActionName(new ActionName(actionname));
            actionBuilder.setOrder((long) order);

            if (actions.get(actionname)!=null){
                ParameterValuesBuilder parameterValuesBuilder = new ParameterValuesBuilder();
                LinkedHashMap<String, String> actionvalues = actions.get(actionname);
                List<IntValue> intValueList = new ArrayList<IntValue>();
                List<StringValue> stringValueList = new ArrayList<StringValue>();
                RangeValue rangeValue = null;

                long actionorder = 0L;
                for (String actionvalue : actionvalues.keySet())
                {
                    if (actionvalues.get(actionvalue).equals(NEMOConstants.string))
                    {
                        if (intValueList.isEmpty()&&rangeValue==null) {
                            Boolean ParaExist = false;
                            StringValueBuilder stringValueBuilder = new StringValueBuilder();
                            if (tenantManage.getObjectId(userId,actionvalue)!=null){
                                stringValueBuilder.setKey(new StringValueKey(actionorder,tenantManage.getObjectId(userId,actionvalue)))
                                                  .setOrder(actionorder)
                                                  .setValue(tenantManage.getObjectId(userId,actionvalue));
                                actionorder++;
                            }
                                stringValueList.add(stringValueBuilder.build());
                            }

                    }
                    if (actionvalues.get(actionvalue).equals(NEMOConstants.integer)) {
                        if (stringValueList.isEmpty()&&rangeValue==null){
                            IntValueBuilder intValueBuilder = new IntValueBuilder();
                            intValueBuilder.setKey(new IntValueKey((long)actionorder,Long.parseLong(actionvalue)));
                            intValueBuilder.setOrder(actionorder);
                            intValueBuilder.setValue(Long.parseLong(actionvalue));
                            intValueList.add(intValueBuilder.build());
                            actionorder ++;
                        }
                        else{
                            errorInfo = "The parameter"+actionvalue +" in action should be int";
                            return errorInfo;
                        }
                    }
                    if (actionvalues.get(actionvalue).equals(NEMOConstants.range)){
                        if (intValueList.isEmpty()&&stringValueList.isEmpty())
                        {
                            String[] rangevalue = new String[2];
                            rangevalue = actionvalue.split(NEMOConstants.comma);
                            RangeValueBuilder rangeValueBuilder = new RangeValueBuilder();
                            if (Long.parseLong(rangevalue[0])<Long.parseLong(rangevalue[1])){
                                rangeValueBuilder.setMin(Long.parseLong(rangevalue[0]));
                                rangeValueBuilder.setMax(Long.parseLong(rangevalue[1]));
                            }
                            else{
                                rangeValueBuilder.setMin(Long.parseLong(rangevalue[1]));
                                rangeValueBuilder.setMax(Long.parseLong(rangevalue[0]));
                            }
                            rangeValue = rangeValueBuilder.build();
                            actionorder ++;
                        }
                        else{
                            errorInfo = "The parameter"+actionvalue +" in action should be range";
                            return errorInfo;
                        }
                    }
                }
                if (intValueList.isEmpty()) {
                    intValueList = null;
                    parameterValuesBuilder.setIntValue(intValueList);
                }
                if (stringValueList.isEmpty()){
                    stringValueList = null;
                    parameterValuesBuilder.setStringValue(stringValueList);
                }
                parameterValuesBuilder.setIntValue(intValueList).setStringValue(stringValueList).setRangeValue(rangeValue);
                actionBuilder.setParameterValues(parameterValuesBuilder.build());
            }
            actionList.add(actionBuilder.build());
        }
        return errorInfo;
    }
}
