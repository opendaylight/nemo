/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.nemo.intent;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.nemo.intent.action.ActionResolver;
import org.opendaylight.nemo.intent.computation.VNComputationUnit;
import org.opendaylight.nemo.intent.condition.ConditionDeterminer;
import org.opendaylight.nemo.intent.condition.ConditionManager;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.generic.virtual.network.rev151010.virtual.networks.VirtualNetwork;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.intent.mapping.result.rev151010.intent.vn.mapping.results.UserIntentVnMapping;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.UserId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Connection;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.condition.instance.ConditionSegment;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.operation.rev151010.operation.instance.Action;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Resolve the user's operation intent that might act on the node, connection
 * or flow. Perform the monitoring if the operation contains a condition which
 * determines when to execute the operation. Resolve the actions included in
 * the operation and decide how to implement them on the physical network.
 *
 * @author Zhigang Ji
 */
public class OperationResolver {
    private static final Logger LOG = LoggerFactory.getLogger(OperationResolver.class);

    private final DataBroker dataBroker;

    /**
     * The condition manager to resolve and manage the condition in the operation.
     */
    private ConditionManager conditionManager;

    /**
     * The action resolver to resolve actions in the operation.
     */
    private ActionResolver actionResolver;

    public OperationResolver(DataBroker dataBroker, ConditionManager conditionManager,
                             Map<UserId, VNComputationUnit> vnComputationUnits) {
        super();

        this.dataBroker = dataBroker;

        this.conditionManager = conditionManager;
        actionResolver = new ActionResolver(dataBroker, vnComputationUnits);

        LOG.debug("Initialized the renderer common operation resolver.");

        return;
    }

    /**
     * Resolve the user's operation intent and perform the monitoring for
     * the condition that it contains. Generate the intent mapping results
     * for the operation in the data store, which denote how to implement
     * it on the physical network.
     *
     * @param user The user for the operation.
     * @param operation The operation to be resolved.
     * @param virtualNetwork The virtual network for the user.
     * @param userIntentVnMapping The intent-vn mapping for the user.
     */
    protected void resolveOperation(User user, Operation operation, VirtualNetwork virtualNetwork,
                                    UserIntentVnMapping userIntentVnMapping)
            throws IntentResolutionException {
        DataObject dataObject = IntentResolverUtils
                .getObject(user.getObjects(), operation.getTargetObject());

        if ( null == dataObject ) {
            throw new IntentResolutionException("The target object of the operation " +
                    operation.getOperationId().getValue() + " does not exist.");
        }

        List<Operation> operations = user.getOperations().getOperation();
        List<Operation> sameTargetObjectOperations = IntentResolverUtils
                .getSameTargetObjectOperations(operations, operation);

        if ( !sameTargetObjectOperations.isEmpty() ) {
            List<Operation> greaterPriorityOperations = new LinkedList<Operation>();
            List<Operation> equalPriorityOperations = new LinkedList<Operation>();

            IntentResolverUtils.getGreaterAndEqualPriorityOperations(sameTargetObjectOperations,
                    operation, greaterPriorityOperations, equalPriorityOperations);

            if ( greaterPriorityOperations.isEmpty() ) {
                if ( !equalPriorityOperations.isEmpty() ) {
                    Operation conflictingOperation = IntentResolverUtils
                            .getConflictingOperation(equalPriorityOperations, operation);

                    if ( null != conflictingOperation ) {
                        throw new IntentResolutionException("The operation " +
                                operation.getOperationId().getValue() + " conflicts with the one " +
                                conflictingOperation.getOperationId().getValue() + ".");
                    }
                }
            } else {
                return;
            }
        }

        List<ConditionSegment> conditionSegments = operation.getConditionSegment();

        if ( !ConditionDeterminer.isConditionMet(conditionSegments) ) {
            conditionManager.handleOperation(user.getUserId(), operation);

            return;
        }

        List<Action> actions = operation.getAction();

        if ( null != actions && !actions.isEmpty() ) {
            if ( dataObject instanceof Node ) {
                actionResolver.resolveActions(user, operation, (Node)dataObject,
                        virtualNetwork, userIntentVnMapping);
            } else if ( dataObject instanceof Connection ) {
                actionResolver.resolveActions(user, operation, (Connection)dataObject,
                        virtualNetwork, userIntentVnMapping);
            } else {
                actionResolver.resolveActions(user, operation, (Flow)dataObject,
                        virtualNetwork, userIntentVnMapping);
            }
        }

        return;
    }

    /**
     * TODO
     *
     * @param user TODO
     * @param operations TODO
     * @param operationsApplyingToNode TODO
     * @param operationsApplyingToConnection TODO
     * @param operationsApplyingToFlow TODO
     * @throws IntentResolutionException
     */
    protected void classifyOperations(User user, List<Operation> operations,
                                      List<Operation> operationsApplyingToNode,
                                      List<Operation> operationsApplyingToConnection,
                                      List<Operation> operationsApplyingToFlow)
            throws IntentResolutionException {
        DataObject dataObject;

        for ( Operation operation : operations ) {
            dataObject = IntentResolverUtils
                    .getObject(user.getObjects(), operation.getTargetObject());

            if ( null == dataObject ) {
                throw new IntentResolutionException("The target object of the operation " +
                        operation.getOperationId().getValue() + " does not exist.");
            }

            if ( dataObject instanceof Node ) {
                operationsApplyingToNode.add(operation);
            } else if ( dataObject instanceof Connection ) {
                operationsApplyingToConnection.add(operation);
            } else {
                operationsApplyingToFlow.add(operation);
            }
        }

        return;
    }
}
