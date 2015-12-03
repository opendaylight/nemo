#!/usr/bin/python
#Filename:servicechain.py
import requests,json
import argparse, sys
from requests.auth import HTTPBasicAuth

USERNAME='admin'
PASSWORD='admin'

TRANSACTION_BEGIN="http://%s:8181/restconf/operations/nemo-intent:begin-transaction/"
TRANSACTION_END="http://%s:8181/restconf/operations/nemo-intent:end-transaction/"
REGISTER_USER="http://%s:8181/restconf/operations/nemo-intent:register-user/"
STRUCTURE_UPDATE_USERS="http://%s:8181/restconf/operations/nemo-intent:structure-style-nemo-update/"

def register_user(contHost):
	data={
			"input":{
					"user-id":"af4fc2be-e3f4-4388-a8ef-3aabae872f2b",
					"user-name":"user1",
					"user-password":"abcd",
					"user-role":"tenant"
					}
		}
	post(REGISTER_USER % contHost, data)

def transaction_begin(contHost):
	data={
			"input":{
					"user-id":"af4fc2be-e3f4-4388-a8ef-3aabae872f2b"
					}
		}
	post(TRANSACTION_BEGIN % contHost, data)

def transaction_end(contHost):
	data={
			"input":{
					"user-id":"af4fc2be-e3f4-4388-a8ef-3aabae872f2b"
					}
		}
	post(TRANSACTION_END % contHost, data)


def add_headquarter_node(contHost):
	data={
		  "input":{
			  "user-id": "af4fc2be-e3f4-4388-a8ef-3aabae872f2b",
			  "objects":{
			  "node":[
					  {
							"node-name": "headquarter",
							"node-type": "ext-group",
							"property": [
										  {
											"property-name": "location",
											"property-values": {
													  "string-value": [
															{
															  "order": "0",
															  "value": "openflow:4:2"
															}
														]
													}
										  },
										  {
											"property-name": "ac-info-network",
											"property-values": {
													  "string-value": [
															{
															  "order": "0",
															  "value": "layer3"
															}
														]
													}
										  },
										  {
											"property-name": "ac-info-protocol",
											"property-values": {
														"string-value": [
															{
																"order": "0",
																"value": "static"
															}
														]
													}
										  },
										  {
											"property-name": "ip-prefix",
											"property-values": {
													  "string-value": [
															{
															  "order": "0",
															  "value": "192.168.11.0/24"
															}
														]
													}
										  }
										],
							"node-id": "94a6fb90-b425-4ffd-9515-c0684aa4c37f"
						  }
					]
			  }
		  }
		}
	post(STRUCTURE_UPDATE_USERS % contHost, data)

def add_site_node(contHost):
	data={
			  "input":{
				  "user-id": "af4fc2be-e3f4-4388-a8ef-3aabae872f2b",
				  "objects":{
				  "node":[
						  {
								"node-name": "branch",
								"node-type": "ext-group",
								"property": [
										  {
											"property-name": "location",
											"property-values": {
													  "string-value": [
															{
															  "order": "0",
															  "value": "openflow:3:4"
															}
														]
													}
										  },
										  {
											"property-name": "ac-info-network",
											"property-values": {
													  "string-value": [
															{
															  "order": "0",
															  "value": "layer3"
															}
														]
													}
										  },
										  {
											"property-name": "ac-info-protocol",
											"property-values": {
														"string-value": [
															{
																"order": "0",
																"value": "static"
															}
														]
													}
										  },
										  {
											"property-name": "ip-prefix",
											"property-values": {
													  "string-value": [
															{
															  "order": "0",
															  "value": "192.168.12.0/24"
															}
														]
													}
										  }
										],
								"node-id": "b46cfa7f-93a3-43f4-ac20-09307c75feca"
							  }
						]
				  }
			  }
		}
	post(STRUCTURE_UPDATE_USERS % contHost, data)

def add_firewall_node(contHost):
	data={
			  "input":{
				  "user-id": "af4fc2be-e3f4-4388-a8ef-3aabae872f2b",
				  "objects":{
				  "node":[
						  {
								"node-name": "fw1",
								"node-type": "fw",
								"property": [
											  {
												"property-name": "location",
												"property-values": {
														  "string-value": [
																{
																  "order": "0",
																  "value": "openflow:1:1"
																}
															]
														}
											  },
											  {
												"property-name": "operating-mode",
												"property-values": {
															"string-value": [
																{
																	"order": "0",
																	"value": "layer3"
																}
															]
														}
											  }
											],
								"node-id": "c2cd9de6-ab25-4d3f-bff2-c4d785a41995"
							  }
						]
				  }
			  }
		}
	post(STRUCTURE_UPDATE_USERS % contHost, data)

def add_cache_node(contHost):
	data={
			  "input":{
				  "user-id": "af4fc2be-e3f4-4388-a8ef-3aabae872f2b",
				  "objects":{
				  "node":[
						  {
								"node-name": "cache2",
								"node-type": "cache",
								"property": [
											  {
												"property-name": "location",
												"property-values": {
														  "string-value": [
																{
																  "order": "0",
																  "value": "openflow:2:2"
																}
															]
														}
											  },
											  {
												"property-name": "operating-mode",
												"property-values": {
															"string-value": [
																{
																	"order": "0",
																	"value": "layer3"
																}
															]
														}
											  }
											],
								"node-id": "a5a96dc7-51dd-44a5-802b-7e67a309fb36"
							  }
						]
				  }
			  }
		}
	post(STRUCTURE_UPDATE_USERS % contHost, data)

def add_chain_node(contHost):
	data={
		  "input":{
			  "user-id": "af4fc2be-e3f4-4388-a8ef-3aabae872f2b",
			  "objects":{
			  "node":[
					  {     "sub-node": [
								{
									"order": "0",
									"node-id": "c2cd9de6-ab25-4d3f-bff2-c4d785a41995"
								},
								{
									"order": "1",
									"node-id": "a5a96dc7-51dd-44a5-802b-7e67a309fb36"
								}
							],
							"node-name": "chain1",
							"node-type": "chain-group",
							"node-id": "41ee9aad-5f61-469d-99a9-e691d2a1de05"
						  }
					]
			  }
		  }
		}
	post(STRUCTURE_UPDATE_USERS % contHost, data)

def add_head_site_connection(contHost):
	data={
		 "input": {
			  "user-id": "af4fc2be-e3f4-4388-a8ef-3aabae872f2b",
			  "objects":{
				  "connection": [
						  {
							"connection-name": "c1",
							"connection-id": "7175bac3-b785-2278-90ed-613480e354e8",
							"end-node": [
							  {
								"order": "0",
								"node-id": "94a6fb90-b425-4ffd-9515-c0684aa4c37f"
							  },
							  {
								"order": "0",
								"node-id": "b46cfa7f-93a3-43f4-ac20-09307c75feca"
							  }
							],
							"connection-type": "p2p"
						}
					]
				}
		  }
	}
	post(STRUCTURE_UPDATE_USERS % contHost, data)

def add_head_chain_connection(contHost):
	data={
		 "input": {
			  "user-id": "af4fc2be-e3f4-4388-a8ef-3aabae872f2b",
			  "objects":{
				  "connection": [
						  {
							"connection-name": "c2",
							"connection-id": "9397bac3-d9a7-449a-b20f-8356a2f3760a",
							"end-node": [
							  {
								"order": "0",
								"node-id": "94a6fb90-b425-4ffd-9515-c0684aa4c37f"
							  },
							  {
								"order": "0",
								"node-id": "41ee9aad-5f61-469d-99a9-e691d2a1de05"
							  }
							],
							"connection-type": "p2p"
						}
					]
				}
		  }
	}
	post(STRUCTURE_UPDATE_USERS % contHost, data)

def add_site_chain_connection(contHost):
	data={
		 "input": {
			  "user-id": "af4fc2be-e3f4-4388-a8ef-3aabae872f2b",
			  "objects":{
				  "connection": [
						  {
							"connection-name": "c3",
							"connection-id": "fed2b570-2e80-4914-a5af-040594b651b9",
							"end-node": [
							  {
								"order": "0",
								"node-id": "41ee9aad-5f61-469d-99a9-e691d2a1de05"
							  },
							  {
								"order": "0",
								"node-id": "b46cfa7f-93a3-43f4-ac20-09307c75feca"
							  }
							],
							"connection-type": "p2p"
						}
					]
				}
		  }
	}
	post(STRUCTURE_UPDATE_USERS % contHost, data)

def add_flow(contHost):
	data={
		 "input": {
			  "user-id": "af4fc2be-e3f4-4388-a8ef-3aabae872f2b",
			  "objects":{
				  "flow": [
						  {
							"match-item": [
								{
									"match-item-value": {
										"string-value": "192.168.12.0/24"
									},
									"match-item-name": "src-ip"
								},
								{
									"match-item-value": {
										"string-value": "192.168.11.0/24"
									},
									"match-item-name": "dst-ip"
								},
							],
							"flow-id": "cf48eeee-882e-435a-adf4-ea22ba88331f",
							"flow-name": "f1"
						}
					]
				}
		  }
	}
	post(STRUCTURE_UPDATE_USERS % contHost, data)

def add_operation(contHost):
	data={
		 "input": {
			  "user-id": "af4fc2be-e3f4-4388-a8ef-3aabae872f2b",
			  "operations":{
				  "operation": [
						  {
							"operation-name": "o1",
							"target-object": "cf48eeee-882e-435a-adf4-ea22ba88331f",
							"priority":"0",
							"operation-id": "619ee3bb-1e40-480f-b0fa-a331820a5518",
							"action":[
										{
											"action-name":"go-through",
											"parameter-values":{
												"string-value": [
													{"value": "41ee9aad-5f61-469d-99a9-e691d2a1de05",
													 "order":"0"}
												]
											},
											"order":"0"
										}
									]
						}
					]
				}
		  }
	}
	post(STRUCTURE_UPDATE_USERS % contHost, data)


def post(url, data):
    headers = {'Content-type': 'application/yang.data+json',
               'Accept': 'application/yang.data+json'}
    print "POST %s" % url
    print json.dumps(data, indent=4, sort_keys=True)
    r = requests.post(url, data=json.dumps(data), headers=headers, auth=HTTPBasicAuth(USERNAME, PASSWORD))
    print r.text
    r.raise_for_status()


if __name__ == '__main__':

	parser = argparse.ArgumentParser()
	parser.add_argument('--controller', default='127.0.0.1', help='controller IP')
	args=parser.parse_args()

	# CREATE User;
	register_user(args.controller)

	# CREATE Node headquarter Type ext-group Property location:openflow:4:2, ip-prefix:192.168.11.0/24;
	transaction_begin(args.controller)
	add_headquarter_node(args.controller)
	#transaction_end(args.controller)

	# CREATE Node headquarter Type ext-group Property location:openflow:3:4,ip-prefix:192.168.12.0/24;
	#transaction_begin(args.controller)
	add_site_node(args.controller)
	#transaction_end(args.controller)

	# IMPORT Node fw1 Type fw Property location:openflow:1:1;
	#transaction_begin(args.controller)
	add_firewall_node(args.controller)
	#transaction_end(args.controller)

	# IMPORT Node Cache2 Type cache Property location:openflow:2:2;
	#transaction_begin(args.controller)
	add_cache_node(args.controller)
	#transaction_end(args.controller)

	# CREATE Node chain1 Type chain-group Contain fw1,cache2;
	#transaction_begin(args.controller)
	add_chain_node(args.controller)
	#transaction_end(args.controller)

	# CREATE Connection c1 Type p2p Endnodes headquarter,site;
	#transaction_begin(args.controller)
	add_head_site_connection(args.controller)
	#transaction_end(args.controller)

	# CREATE Connection c2 Type p2p Endnodes headquarter,chain1;
	#transaction_begin(args.controller)
	add_head_chain_connection(args.controller)
	#transaction_end(args.controller)

	# CREATE Connection c3 Type p2p Endnodes site,chain1;
	#transaction_begin(args.controller)
	add_site_chain_connection(args.controller)
	#transaction_end(args.controller)

	# CREATE Flow f1 Match src-ip:192.168.12.0/24, dst-ip:192.168.11.0/24;
	#transaction_begin(args.controller)
	add_flow(args.controller)
	#transaction_end(args.controller)

	# CREATE Operation o1 Priority 0 Target f1 Action go-through: chain1;
	#transaction_begin(args.controller)
	add_operation(args.controller)
	transaction_end(args.controller)


