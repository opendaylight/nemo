/*
 * Copyright (c) 2015 Huawei, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.nemo.user.tenantmanager;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.nemo.user.vnspacemanager.languagestyle.NEMOConstants;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.common.rev151010.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.RegisterUserInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.Users;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Objects;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.Operations;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.TemplateDefinitions;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.TemplateInstances;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.objects.*;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.Operation;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.operations.OperationKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.definitions.TemplateDefinition;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.definitions.TemplateDefinitionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.instances.TemplateInstance;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.user.intent.template.instances.TemplateInstanceKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.User;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.intent.rev151010.users.UserKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.user.rev151010.UserRoles;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.nemo.user.rev151010.user.roles.UserRole;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by z00293636 on 2015/8/29.
 */

/* maintain tenant information, including how many tenants are active, their information */
public class TenantManage {
    private static final Logger LOG = LoggerFactory.getLogger(TenantManage.class);
    private DataBroker dataBroker;
    private User user;
    private Map<UserId, Map<NodeId, Node>> userNodeMap = new HashMap<UserId, Map<NodeId, Node>>();
    private Map<UserId, Map<ConnectionId, Connection>> userConnectionMap = new HashMap<UserId, Map<ConnectionId, Connection>>();
    private Map<UserId, Map<FlowId, Flow>> userFlowMap = new HashMap<UserId, Map<FlowId, Flow>>();
    private Map<UserId, Map<OperationId, Operation>> userOperationMap = new HashMap<UserId, Map<OperationId, Operation>>();
    private Map<UserId, Map<TemplateName, TemplateDefinition>> userTemplateDefinitionMap = new HashMap<UserId, Map<TemplateName, TemplateDefinition>>();
    private Map<UserId, Map<TemplateInstanceId, TemplateInstance>> userTemplateInstanceMap = new HashMap<UserId, Map<TemplateInstanceId, TemplateInstance>>();
    private Map<UserId, Map<String, String>> userDeleteIntent = new HashMap<UserId, Map<String, String>>();
    private Map<UserId, Map<String, String>> userNameIdMap = new HashMap<UserId, Map<String, String>>();

    public TenantManage(DataBroker dataBroker){
        this.dataBroker = dataBroker;
    }

    private void setUser(User user)
    {
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }

    /**
     *
     * @return null if an error was encountered, or an empty map if there was no
     *         error but no data was retrieved.
     */
    public Map<UserRoleName, UserRole> getUserRoles() {

        InstanceIdentifier<UserRoles> userRolesInsId = InstanceIdentifier.builder(UserRoles.class).build();
        ListenableFuture<Optional<UserRoles>> userRolesFuture = this.dataBroker.newReadOnlyTransaction().read(
                LogicalDatastoreType.CONFIGURATION, userRolesInsId);

        final Optional<UserRoles> userRolesOpt;
        try {
            // TODO: consider time out here?
            userRolesOpt = userRolesFuture.get();
        } catch (InterruptedException e) {
            LOG.error("Cannot read role information.", e);
            return null;
        } catch (ExecutionException e) {
            LOG.error("Cannot read role information.", e);
            return null;
        }

        // TODO: change to Java 8 lambda expressions
        return userRolesOpt.transform(new Function<UserRoles, Map<UserRoleName, UserRole>>() {
            @Override
            public Map<UserRoleName, UserRole> apply(UserRoles input) {
                return Maps.uniqueIndex(input.getUserRole(), new Function<UserRole, UserRoleName>() {
                    @Override
                    public UserRoleName apply(UserRole role) {
                        return role.getRoleName();
                    }
                });
            }
        }).or(new HashMap<UserRoleName, UserRole>());
    }

    /**
    *
    * @return null if an error was encountered, or an empty map if there was no
    *         error but no data was retrieved.
    */
    public Map<UserId, User> getUsers() {
        InstanceIdentifier<Users> usersInsId = InstanceIdentifier.builder(Users.class).build();
        ListenableFuture<Optional<Users>> usersFuture = dataBroker.newReadOnlyTransaction().read(
                LogicalDatastoreType.CONFIGURATION, usersInsId);

        final Optional<Users> usersOpt;
        try {
            // TODO: consider time out here?
            usersOpt = usersFuture.get();
        } catch (InterruptedException e) {
            LOG.error("Cannot read user information.", e);
            return null;
        } catch (ExecutionException e) {
            LOG.error("Cannot read user information.", e);
            return null;
        }

        // TODO: change to Java 8 lambda expressions
        return usersOpt.transform(new Function<Users, Map<UserId, User>>() {
            @Override
            public Map<UserId, User> apply(Users input) {
                return Maps.uniqueIndex(input.getUser(), new Function<User, UserId>() {
                    @Override
                    public UserId apply(User user) {
                        return user.getUserId();
                    }
                });
            }
        }).or(new HashMap<UserId, User>());
    }

    public void fetchVNSpace(UserId userId){
        final Map<UserId, User> users = getUsers();
        setUser((users != null) ? users.get(userId) : null);
    }

    public void addUser(RegisterUserInput registerUserInput){
        WriteTransaction t = dataBroker.newWriteOnlyTransaction();
        if (registerUserInput.getUserId() != null)
        {
            User user = new UserBuilder(registerUserInput).build();
            UserKey userKey = new UserKey(registerUserInput.getUserId());

            InstanceIdentifier<User> userid = InstanceIdentifier.builder(Users.class).child(User.class, userKey).build();

            t.put(LogicalDatastoreType.CONFIGURATION, userid, user,true);
            CheckedFuture<Void, TransactionCommitFailedException> f = t.submit();
            Futures.addCallback(f, new FutureCallback<Void>() {
                @Override
                public void onFailure(Throwable t) {
                    LOG.error("Could not write endpoint base container", t);
                }

                @Override
                public void onSuccess(Void result) {

                }
            });
            try {
                f.get();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * create a space to store all information in one transaction
     */
    public void transactionHandling(UserId userId){
        writeDataStore(userId);
        deleteDataStore(userId);

        userNodeMap.clear();
        userConnectionMap.clear();
        userFlowMap.clear();
        userOperationMap.clear();
        userTemplateDefinitionMap.clear();
        userTemplateInstanceMap.clear();
        userDeleteIntent.clear();
    }

    /**
     * store and get all user node in one transaction into map
     * @param userId
     */
    public void setNode(UserId userId, NodeId nodeId, Node node){
        if (userNodeMap.containsKey(userId)){
            Map<NodeId, Node> nodeMap = userNodeMap.get(userId);
            nodeMap.put(nodeId, node);
        }
        else {
            Map<NodeId, Node> nodeMap = new HashMap<NodeId, Node>();
            nodeMap.put(nodeId,node);
            userNodeMap.put(userId,nodeMap);
        }
        setUserNameIdMap(userId, node.getNodeName().getValue(),nodeId.getValue());
    }

    public Map<NodeId, Node> getNode(UserId userId){
        if (!userNodeMap.isEmpty()){
            return userNodeMap.get(userId);
        }
        else {
            return null;
        }
    }

    public void setConnection(UserId userId, ConnectionId connectionId, Connection connection){
        if (userConnectionMap.containsKey(userId)){
            Map<ConnectionId, Connection> connectionMap = userConnectionMap.get(userId);
            connectionMap.put(connectionId,connection);
        }
        else {
            Map<ConnectionId, Connection> connectionMap = new HashMap<ConnectionId, Connection>();
            connectionMap.put(connectionId,connection);
            userConnectionMap.put(userId,connectionMap);
        }
        setUserNameIdMap(userId, connection.getConnectionName().getValue(),connectionId.getValue());
    }

    public Map<ConnectionId, Connection> getConnection(UserId userId){
        if (!userConnectionMap.isEmpty()){
            return userConnectionMap.get(userId);
        }
        else {
            return null;
        }
    }

    public void setFlow(UserId userId, FlowId flowId, Flow flow){
        if (userFlowMap.containsKey(userId)){
            Map<FlowId,Flow> flowMap = userFlowMap.get(userId);
            flowMap.put(flowId,flow);
        }
        else {
            Map<FlowId,Flow> flowMap = new HashMap<FlowId, Flow>();
            flowMap.put(flowId,flow);
            userFlowMap.put(userId,flowMap);
        }
        setUserNameIdMap(userId,flow.getFlowName().getValue(),flowId.getValue());
    }

    public Map<FlowId,Flow> getFlow(UserId userId){
        if (!userFlowMap.isEmpty()){
            return userFlowMap.get(userId);
        }
        else {
            return null;
        }
    }

    public void setOperation(UserId userId, OperationId operationId, Operation operation){
        if (userOperationMap.containsKey(userId)){
            Map<OperationId, Operation> operationMap = userOperationMap.get(userId);
            operationMap.put(operationId,operation);
        }
        else {
            Map<OperationId, Operation> operationMap = new HashMap<OperationId, Operation>();
            operationMap.put(operationId,operation);
            userOperationMap.put(userId,operationMap);
        }
        setUserNameIdMap(userId,operation.getOperationName().getValue(),operationId.getValue());
    }

    public Map<OperationId, Operation> getOperation(UserId userId){
        if (!userOperationMap.isEmpty()){
            return userOperationMap.get(userId);
        }
        else {
            return null;
        }
    }

    public void setTemplateDefinition(UserId userId,TemplateName templateName, TemplateDefinition definition){
        if (userTemplateDefinitionMap.containsKey(userId)){
            Map<TemplateName, TemplateDefinition> definitionMap = userTemplateDefinitionMap.get(userId);
            definitionMap.put(templateName,definition);
        }
        else {
            Map<TemplateName, TemplateDefinition> definitionMap = new HashMap<TemplateName, TemplateDefinition>();
            definitionMap.put(templateName,definition);
            userTemplateDefinitionMap.put(userId,definitionMap);
        }
    }

    public Map<TemplateName, TemplateDefinition> getTempalteDefinition(UserId userId){
        if (!userTemplateDefinitionMap.isEmpty()){
            return userTemplateDefinitionMap.get(userId);
        }
        else {
            return null;
        }
    }

    public void setUserTemplateInstance(UserId userId, TemplateInstanceId instanceId, TemplateInstance instance){
        if (userTemplateInstanceMap.containsKey(userId)){
            Map<TemplateInstanceId, TemplateInstance> instanceMap = userTemplateInstanceMap.get(userId);
            instanceMap.put(instanceId,instance);
        }
        else {
            Map<TemplateInstanceId, TemplateInstance> instanceMap = new HashMap<TemplateInstanceId, TemplateInstance>();
            instanceMap.put(instanceId,instance);
            userTemplateInstanceMap.put(userId,instanceMap);
        }
    }

    public Map<TemplateInstanceId, TemplateInstance> getTemplateInstance(UserId userId){
        if (!userTemplateInstanceMap.isEmpty()){
            return userTemplateInstanceMap.get(userId);
        }
        else {
            return null;
        }
    }

    /**
     * store all intent need to be deleted in the data store
     * @param userId
     * @param type
     * @param objectId
     */
    public void setUserDeleteIntent(UserId userId, String type, String objectId){
        if (userDeleteIntent.containsKey(userId)){
            Map<String, String> deleteObject = userDeleteIntent.get(userId);
            deleteObject.put(objectId,type);
        }
        else {
            Map<String, String> deleteObject = new HashMap<String, String>();
            deleteObject.put(objectId,type);
            userDeleteIntent.put(userId, deleteObject);
        }
    }

    /**
     * store all name-id map
     * @param userId
     * @param name
     * @param objectId
     */
    public void setUserNameIdMap(UserId userId, String name, String objectId){
        if (userNameIdMap.containsKey(userId)){
            Map<String, String> map = userNameIdMap.get(userId);
            map.put(name,objectId);
        }
        else {
            Map<String, String> nameIdMap = new HashMap<String, String>();
            nameIdMap.put(name, objectId);
            userNameIdMap.put(userId, nameIdMap);
        }
    }

    public Map<String, String> getUserNameIdMap(UserId userId){
        if (!userNameIdMap.isEmpty()){
            return userNameIdMap.get(userId);
        }
        else {
            return null;
        }
    }

    public String getObjectId(UserId userId, String name){
        if (userNameIdMap.containsKey(userId)){
            if (userNameIdMap.get(userId).containsKey(name)){
                return userNameIdMap.get(userId).get(name);
            }
        }
        return null;
    }

    public String getName(UserId userId, String objectId){
        if (userNameIdMap.containsKey(userId)){
            for (String name : userNameIdMap.get(userId).keySet()){
                if (objectId.equals(userNameIdMap.get(userId).get(name))){
                    return name;
                }
            }
        }
        return null;
    }

    /**
     * get all information stored in data store
     */

    public Map<NodeId, Node> getNodeDataStore(UserId userId){
        Map<NodeId, Node> nodeMap = new HashMap<NodeId, Node>();
        fetchVNSpace(userId);
        User user1 = getUser();
        if (user1.getObjects()!=null){
            if (user1.getObjects().getNode()!=null){
                for (Node node : user1.getObjects().getNode()){
                    nodeMap.put(node.getNodeId(),node);
                }
            }
        }
        return nodeMap.isEmpty()?null:nodeMap;
    }

    public Map<ConnectionId, Connection> getConnectionDataStore(UserId userId){
        Map<ConnectionId, Connection> connectionMap = new HashMap<ConnectionId, Connection>();
        fetchVNSpace(userId);
        User user1 = getUser();
        if (user1.getObjects()!=null){
            if (user1.getObjects().getConnection()!=null){
                for (Connection connection : user1.getObjects().getConnection()){
                    connectionMap.put(connection.getConnectionId(),connection);
                }
            }
        }
        return connectionMap.isEmpty()?null:connectionMap;
    }

    public Map<FlowId, Flow> getFlowDataStore(UserId userId){
        Map<FlowId, Flow> flowMap = new HashMap<FlowId, Flow>();
        fetchVNSpace(userId);
        User user1 = getUser();
        if (user1.getObjects()!=null){
            if (user1.getObjects().getFlow()!=null){
                for (Flow flow : user1.getObjects().getFlow()){
                    flowMap.put(flow.getFlowId(),flow);
                }
            }
        }
        return flowMap.isEmpty()?null:flowMap;
    }

    public Map<OperationId, Operation> getOperationDataStore(UserId userId){
        Map<OperationId, Operation> operationMap = new HashMap<OperationId, Operation>();
        fetchVNSpace(userId);
        User user1 = getUser();
        if (user1.getOperations()!=null){
            if (user1.getOperations().getOperation()!=null){
                for (Operation operation : user1.getOperations().getOperation()){
                    operationMap.put(operation.getOperationId(),operation);
                }
            }
        }
        return operationMap.isEmpty()?null:operationMap;
    }

    public Map<TemplateName, TemplateDefinition> getDefinitionDataStore(UserId userId){
        Map<TemplateName, TemplateDefinition> definitionMap = new HashMap<TemplateName, TemplateDefinition>();
        fetchVNSpace(userId);
        User user1 = getUser();
        if (user1.getTemplateDefinitions()!=null){
            if (user1.getTemplateDefinitions().getTemplateDefinition()!=null){
                for (TemplateDefinition definition : user1.getTemplateDefinitions().getTemplateDefinition()){
                    definitionMap.put(definition.getTemplateName(),definition);
                }
            }
        }
        return definitionMap.isEmpty()?null:definitionMap;
    }

    public Map<TemplateInstanceId, TemplateInstance> getInstanceDataStore(UserId userId){
        Map<TemplateInstanceId, TemplateInstance> instanceMap = new HashMap<TemplateInstanceId, TemplateInstance>();
        fetchVNSpace(userId);
        User user1 = getUser();
        if (user1.getTemplateInstances()!=null){
            if (user1.getTemplateInstances().getTemplateInstance()!=null){
                for (TemplateInstance instance : user1.getTemplateInstances().getTemplateInstance()){
                    instanceMap.put(instance.getTemplateInstanceId(),instance);
                }
            }
        }
        return instanceMap.isEmpty()?null:instanceMap;
    }

    /**
     * write all information in one transaction into data store
     */
    private void writeDataStore(UserId userId){

        UserKey userKey = new UserKey(userId);
        if (getNode(userId)!=null){
            for (NodeId nodeId : getNode(userId).keySet()){
                NodeKey nodeKey = new NodeKey(nodeId);
                WriteTransaction t = dataBroker.newWriteOnlyTransaction();
                InstanceIdentifier<Node> nodeid = InstanceIdentifier.builder(Users.class).child(User.class, userKey).child(Objects.class).child(Node.class,nodeKey).build();
                t.put(LogicalDatastoreType.CONFIGURATION, nodeid, getNode(userId).get(nodeId),true);
                CheckedFuture<Void, TransactionCommitFailedException> f = t.submit();
                Futures.addCallback(f, new FutureCallback<Void>() {
                    @Override
                    public void onFailure(Throwable t) {
                        LOG.error("Could not write node instance", t);
                    }
                    @Override
                    public void onSuccess(Void result) {
                    }
                });
                try {
                    f.get();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
        if (getConnection(userId)!=null){
           for (ConnectionId connectionId : getConnection(userId).keySet()){
                ConnectionKey connectionKey = new ConnectionKey(connectionId);
               WriteTransaction t = dataBroker.newWriteOnlyTransaction();
                InstanceIdentifier<Connection> connectionid = InstanceIdentifier.builder(Users.class).child(User.class, userKey).child(Objects.class).child(Connection.class,connectionKey).build();
                t.put(LogicalDatastoreType.CONFIGURATION, connectionid, getConnection(userId).get(connectionId),true);
                CheckedFuture<Void, TransactionCommitFailedException> f = t.submit();
                Futures.addCallback(f, new FutureCallback<Void>() {
                    @Override
                    public void onFailure(Throwable t) {
                        LOG.error("Could not write connection instance", t);
                    }
                    @Override
                    public void onSuccess(Void result) {
                    }
                });
               try {
                   f.get();
               } catch (InterruptedException e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
               } catch (ExecutionException e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
               }
           }
        }
        if (getFlow(userId)!=null){
           for (FlowId flowId : getFlow(userId).keySet()){
                FlowKey flowKey = new FlowKey(flowId);
               WriteTransaction t = dataBroker.newWriteOnlyTransaction();
                InstanceIdentifier<Flow> flowid = InstanceIdentifier.builder(Users.class).child(User.class, userKey).child(Objects.class).child(Flow.class,flowKey).build();
                t.put(LogicalDatastoreType.CONFIGURATION, flowid, getFlow(userId).get(flowId),true);
                CheckedFuture<Void, TransactionCommitFailedException> f = t.submit();
                Futures.addCallback(f, new FutureCallback<Void>() {
                    @Override
                    public void onFailure(Throwable t) {
                        LOG.error("Could not write endpoint base container", t);
                    }
                    @Override
                    public void onSuccess(Void result) {
                    }
                });
               try {
                   f.get();
               } catch (InterruptedException e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
               } catch (ExecutionException e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
               }
           }
        }
        if (getOperation(userId)!=null){
            for (OperationId operationId : getOperation(userId).keySet()){
                OperationKey operationKey = new OperationKey(operationId);
                WriteTransaction t = dataBroker.newWriteOnlyTransaction();
                InstanceIdentifier<Operation> operationid = InstanceIdentifier.builder(Users.class).child(User.class, userKey).child(Operations.class).child(Operation.class,operationKey).build();
                t.put(LogicalDatastoreType.CONFIGURATION, operationid, getOperation(userId).get(operationId),true);
                CheckedFuture<Void, TransactionCommitFailedException> f = t.submit();
                Futures.addCallback(f, new FutureCallback<Void>() {
                    @Override
                    public void onFailure(Throwable t) {
                        LOG.error("Could not write endpoint base container", t);
                    }

                    @Override
                    public void onSuccess(Void result) {

                    }
                });
                try {
                    f.get();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        if (getTempalteDefinition(userId)!=null){
            for (TemplateName templateName : getTempalteDefinition(userId).keySet()){
                TemplateDefinitionKey key = new TemplateDefinitionKey(templateName);
                WriteTransaction t = dataBroker.newWriteOnlyTransaction();
                InstanceIdentifier<TemplateDefinition> templateDefinitionId = InstanceIdentifier.builder(Users.class).child(User.class, userKey)
                        .child(TemplateDefinitions.class).child(TemplateDefinition.class, key)
                        .build();
                t.put(LogicalDatastoreType.CONFIGURATION, templateDefinitionId, getTempalteDefinition(userId).get(templateName),true);
                CheckedFuture<Void, TransactionCommitFailedException> f = t.submit();
                Futures.addCallback(f, new FutureCallback<Void>() {
                    @Override
                    public void onFailure(Throwable t) {
                        LOG.error("Could not write endpoint base container", t);
                    }
                    @Override
                    public void onSuccess(Void result) {
                    }
                });
                try {
                    f.get();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        if (getTemplateInstance(userId)!=null){
          for (TemplateInstanceId instanceId : getTemplateInstance(userId).keySet()){
                TemplateInstanceKey key = new TemplateInstanceKey(instanceId);
                WriteTransaction t = dataBroker.newWriteOnlyTransaction();
                InstanceIdentifier<TemplateInstance> templateDefinitionId = InstanceIdentifier.builder(Users.class).child(User.class, userKey)
                        .child(TemplateInstances.class).child(TemplateInstance.class, key)
                        .build();
                t.put(LogicalDatastoreType.CONFIGURATION, templateDefinitionId, getTemplateInstance(userId).get(instanceId),true);
                CheckedFuture<Void, TransactionCommitFailedException> f = t.submit();
                Futures.addCallback(f, new FutureCallback<Void>() {
                    @Override
                    public void onFailure(Throwable t) {
                        LOG.error("Could not write endpoint base container", t);
                    }
                    @Override
                    public void onSuccess(Void result) {
                    }
                });
              try {
                  f.get();
              } catch (InterruptedException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
              } catch (ExecutionException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
              }
          }
        }
    }

    /**
     * delete intent in data store
     * @param userId
     */
    private void deleteDataStore(UserId userId){
        if (userDeleteIntent.get(userId)!=null){
            Map<String, String> deleteObject = userDeleteIntent.get(userId);

            for (String objectId : deleteObject.keySet()){
                if (deleteObject.get(objectId).equals(NEMOConstants.node)){
                    UserKey userKey = new UserKey(userId);
                    NodeKey nodeKey = new NodeKey(new NodeId(objectId));
                    WriteTransaction t = dataBroker.newWriteOnlyTransaction();
                    InstanceIdentifier<Node> nodeid = InstanceIdentifier.builder(Users.class).child(User.class, userKey).child(Objects.class).child(Node.class,nodeKey).build();
                    t.delete(LogicalDatastoreType.CONFIGURATION, nodeid);
                    CheckedFuture<Void, TransactionCommitFailedException> f = t.submit();
                    Futures.addCallback(f, new FutureCallback<Void>() {
                        @Override
                        public void onFailure(Throwable t) {
                            LOG.error("Could not delete node instance", t);
                        }
                        @Override
                        public void onSuccess(Void result) {
                        }
                    });
                }
                if (deleteObject.get(objectId).equals(NEMOConstants.connection)){
                    UserKey userKey = new UserKey(userId);
                    ConnectionKey connectionKey = new ConnectionKey(new ConnectionId(objectId));
                    WriteTransaction t = dataBroker.newWriteOnlyTransaction();
                    InstanceIdentifier<Connection> connectionid = InstanceIdentifier.builder(Users.class).child(User.class, userKey).child(Objects.class).child(Connection.class,connectionKey).build();
                    t.delete(LogicalDatastoreType.CONFIGURATION, connectionid);
                    CheckedFuture<Void, TransactionCommitFailedException> f = t.submit();
                    Futures.addCallback(f, new FutureCallback<Void>() {
                        @Override
                        public void onFailure(Throwable t) {
                            LOG.error("Could not delete connection instance", t);
                        }
                        @Override
                        public void onSuccess(Void result) {
                        }
                    });
                }
                if (deleteObject.get(objectId).equals(NEMOConstants.flow)){
                    UserKey userKey = new UserKey(userId);
                    FlowKey flowKey = new FlowKey(new FlowId(objectId));
                    WriteTransaction t = dataBroker.newWriteOnlyTransaction();
                    InstanceIdentifier<Flow> flowid = InstanceIdentifier.builder(Users.class).child(User.class, userKey).child(Objects.class).child(Flow.class,flowKey).build();
                    t.delete(LogicalDatastoreType.CONFIGURATION, flowid);
                    CheckedFuture<Void, TransactionCommitFailedException> f = t.submit();
                    Futures.addCallback(f, new FutureCallback<Void>() {
                        @Override
                        public void onFailure(Throwable t) {
                            LOG.error("Could not delete flow instance", t);
                        }
                        @Override
                        public void onSuccess(Void result) {
                        }
                    });
                }
                if (deleteObject.get(objectId).equals(NEMOConstants.operation)){
                    UserKey userKey = new UserKey(userId);
                    OperationKey operationKey = new OperationKey(new OperationId(objectId));
                    WriteTransaction t = dataBroker.newWriteOnlyTransaction();
                    InstanceIdentifier<Operation> operationid = InstanceIdentifier.builder(Users.class).child(User.class, userKey).child(Operations.class).child(Operation.class,operationKey).build();
                    t.delete(LogicalDatastoreType.CONFIGURATION, operationid);
                    CheckedFuture<Void, TransactionCommitFailedException> f = t.submit();
                    Futures.addCallback(f, new FutureCallback<Void>() {
                        @Override
                        public void onFailure(Throwable t) {
                            LOG.error("Could not delete operation instance", t);
                        }
                        @Override
                        public void onSuccess(Void result) {
                        }
                    });
                }
            }
        }
    }
}
