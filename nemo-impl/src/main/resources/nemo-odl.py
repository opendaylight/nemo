#!/usr/bin/python
#Filename:nemo-odl.py
import requests,json
import argparse, sys
from requests.auth import HTTPBasicAuth

USERNAME='admin'
PASSWORD='admin'

PREDEFINE_USERROLE="http://%s:8181/restconf/config/nemo-user:user-roles/"
PREDEFINE_NODETYPE="http://%s:8181/restconf/config/nemo-object:node-definitions/"
PREDEFINE_CONNECTIONTYPE="http://%s:8181/restconf/config/nemo-object:connection-definitions/"
PREDEFINE_FLOWMATCHTYPE="http://%s:8181/restconf/config/nemo-object:match-item-definitions/"
PREDEFINE_FLOWPROPERTY="http://%s:8181/restconf/config/nemo-object:flow-property-definitions/"
PREDEFINE_OPERATIONACTION="http://%s:8181/restconf/config/nemo-operation:action-definitions/"
PREDEFINE_OPERATIONCONDITION="http://%s:8181/restconf/config/nemo-operation:condition-parameter-definitions/"
PHYSICAL_PORT_ATTRIBUTE="http://%s:8181/restconf/config/generic-physical-network:physical-port-attribute-definitions/"
PHYSICAL_NODE_ATTRIBUTE="http://%s:8181/restconf/config/generic-physical-network:physical-node-attribute-definitions/"



def add_predefined_userrole(contHost):
	data={
		"user-roles": {
			"user-role": [
							{
								"role-name": "tenant",
								"role-description": "It's a non-administor user"
							},
							{
                                "role-name": "admin",
                              	"role-description": "It's a administor user"
                            }
						]
					}
		}
	put(PREDEFINE_USERROLE % contHost, data)

def add_predefined_nodetypes(contHost):
	data={
		"node-definitions": {
				"node-definition": [
						{
							"node-type": "host",
							"property-definition": [
									{
										"property-name": "name",
										"property-value-type": "string"
									},
									{
										"property-name": "location",
										"property-value-type": "string"
									},
									{
										#For example, one legal mac-address is 00:01:0a:90:78:02.
										"property-name": "mac-address",
										"property-value-type": "string"
									},
									{
										#For example, one legal ip-address is 10.0.2.0/24.
										"property-name": "ip-address",
										"property-value-type": "string"
									}
								]
						},
						{
							"node-type": "l2-group",
							"property-definition": [
									{
										#For example, one legal ip-address is 10.0.2.0/24.
										"property-name": "ip-prefix",
										"property-value-type": "string"
									},
									{
										#For example, one legal gateway-ip is 10.0.2.1.
										"property-name": "gateway-ip",
										"property-value-type": "string"
									},
									{
										"property-name": "location",
										"property-value-type": "string"
									},
						 			{
                                    	"property-name": "sub-nodes",
                                       	"property-value-type": "string"
                                	}
								]
						},
						{
							"node-type": "l3-group",
							"property-definition": [
									{
										#For example, one legal ip-address is 10.0.2.0/24.
										"property-name": "ip-prefix",
										"property-value-type": "string"
									}
								]
						},
						{
							"node-type": "ext-group",
							"property-definition": [
									{
										"property-name": "location",
										"property-value-type": "string",
										"is-required": "required"
									},
									{
										"property-name": "ac-info-network",
										"property-value-type": "string",
										"is-required": "required"
									},
									{
										"property-name": "ac-info-protocol",
										"property-value-type": "string",
										"is-required": "required"
									 },
									{
										"property-name": "ip-prefix",
										"property-value-type": "string"
									 }
								]
						},
						{
							"node-type": "chain-group"
						},
						{
							"node-type": "fw",
							"property-definition": [
									{
										"property-name": "location",
										"property-value-type": "string",
										"is-required": "required"
									},
									{
										"property-name": "operating-mode",
										"property-value-type": "string",
										"is-required": "required"
									}
								]
						},
						{
							"node-type": "lb",
							"property-definition": [
									{
										"property-name": "location",
										"property-value-type": "string",
										"is-required": "required"
									},
									{
										"property-name": "operating-mode",
										"property-value-type": "string",
										"is-required": "required"
									}
								]
						},
						{
							"node-type": "cache",
							"property-definition": [
									{
										"property-name": "location",
										"property-value-type": "string",
										"is-required": "required"
									},
									{
										"property-name": "operating-mode",
										"property-value-type": "string",
										"is-required": "required"
									}
								]
						}
					]
				}
		}
	put(PREDEFINE_NODETYPE % contHost, data)

def add_predefined_connectiontypes(contHost):
	data={
			"connection-definitions": {
				"connection-definition":[
						{
							"connection-type": "p2p",
							"property-definition": [
									{
										#the unit of bandwidth is mbps.
										"property-name": "bandwidth",
										"property-value-type": "int"
									}
								]
						},
						{
							"connection-type": "p2mp",
							"property-definition": [
									{
										#the unit of bandwidth is mbps.
										"property-name": "bandwidth",
										"property-value-type": "int"
									}
								]
						},
						{
							"connection-type": "mesh",
							"property-definition": [
									{
										#the unit of bandwidth is mbps.
										"property-name": "bandwidth",
										"property-value-type": "int"
									}
								]
						},
						{
							"connection-type": "chain",
							"property-definition": [
									{
										#the unit of bandwidth is mbps.
										"property-name": "bandwidth",
										"property-value-type": "int"
									}
								]
						}
					]
				}
		}
	put(PREDEFINE_CONNECTIONTYPE % contHost, data)

def add_flow_matchtypes(contHost):
	data={
			"match-item-definitions": {
				"match-item-definition": [
						{
							#For example, one legal eth-type is arp.
							"match-item-name": "eth-type",
							"match-item-value-type": "string"
						},
						{
							#For example, one legal src-mac is 00:00:0a:b7:01:90;
							"match-item-name": "src-mac",
							"match-item-value-type": "string"
						},
						{
							#For example, one legal src-mac is 00:00:0a:b7:01:90;
							"match-item-name": "dst-mac",
							"match-item-value-type": "string"
						},
						{
							#For example, one legal proto is http;
							"match-item-name": "proto",
							"match-item-value-type": "string"
						},
						{
							#For example, one legal src-ip is 10.0.2.1;
							"match-item-name": "src-ip",
							"match-item-value-type": "string"
						},
						{
							#For example, one legal dst-ip is 10.0.1.2;
							"match-item-name": "dst-ip",
							"match-item-value-type": "string"
						},
						{
							#For example, one legal src-port is 80;
							"match-item-name": "src-port",
							"match-item-value-type": "int"
						},
						{
							#For example, one legal dst-port is 22;
							"match-item-name": "dst-port",
							"match-item-value-type": "int"
						}
					]
				}
		}
	put(PREDEFINE_FLOWMATCHTYPE % contHost, data)

def add_flow_properties(contHost):
	data={
			"flow-property-definitions": {
					"property-definition": [
									{
										"property-name": "path",
										"property-value-type": "string"
									}
								]
					}
		}
	put(PREDEFINE_FLOWPROPERTY % contHost, data)

def add_operation_actions(contHost):
	data={
			"action-definitions": {
				"action-definition": [
						{
							"action-name": "deny"
						},
								{
							"action-name": "allow"
						},
						{
							"action-name": "go-through",
							"parameter-value-type": "string"
						},
						{
							"action-name": "qos-bandwidth",
							"parameter-value-type": "int"
						}
					]
				}
		}
	put(PREDEFINE_OPERATIONACTION % contHost, data)

def add_operation_conditions(contHost):
	data={
			"condition-parameter-definitions": {
				"condition-parameter-definition": [
						{
							"parameter-name": "time",
							"parameter-value-type": "string",
							"parameter-match-patterns": {
							 "parameter-match-pattern": ["less-than" ,"not-less-than","equal","not-equal",
											"greater-than","not-greater-than","between","periodical"]
							 }								}
					]
				}
		}
	put(PREDEFINE_OPERATIONCONDITION % contHost, data)

def add_port_attributes(conHost):
	data={
			"physical-port-attribute-definitions":{
					"physical-port-attribute-definition":[
							{
								"attribute-name":"location",
								"attribute-value-type":"string",
								"attribute-match-patterns":{
									"attribute-match-pattern":["equal"]
								}
							}
						]
			}
		}
	put(PHYSICAL_PORT_ATTRIBUTE % conHost, data)

def add_node_attributes(conHost):
	data={
			"physical-node-attribute-definitions":{
				"physical-node-attribute-definition":[
					{
						"attribute-name":"location",
						"attribute-value-type":"string",
						"attribute-match-patterns":{
							"attribute-match-pattern":["equal"]
						}
					},
					{
						"attribute-name":"capacity",
						"attribute-value-type":"int",
						"attribute-match-patterns":{
							"attribute-match-pattern":["equal"]
						}
					}
				]
			}
		}
	put(PHYSICAL_NODE_ATTRIBUTE % conHost, data)



def put(url, data):
    headers = {'Content-type': 'application/yang.data+json',
               'Accept': 'application/yang.data+json'}
    print "PUT %s" % url
    print json.dumps(data, indent=4, sort_keys=True)
    r = requests.put(url, data=json.dumps(data), headers=headers, auth=HTTPBasicAuth(USERNAME, PASSWORD))
    print r.text
    r.raise_for_status()

if __name__ == '__main__':

	parser = argparse.ArgumentParser()
	parser.add_argument('--controller', default='127.0.0.1', help='controller IP')
	args=parser.parse_args()

	print args.controller

	add_predefined_userrole(args.controller)
	add_predefined_nodetypes(args.controller)
	add_predefined_connectiontypes(args.controller)
	add_flow_matchtypes(args.controller)
	add_flow_properties(args.controller)
	add_operation_actions(args.controller)
	add_operation_conditions(args.controller)
	add_port_attributes(args.controller)
	add_node_attributes(args.controller)
