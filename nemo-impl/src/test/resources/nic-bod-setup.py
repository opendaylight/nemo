#!/usr/bin/python

# This script sets up the physical network and NEMO Nodes for the NIC BoD flow.
# Use nemo-odl.py to set up the predefined NEMO definitions first.

import requests,json
import argparse, sys
from requests.auth import HTTPBasicAuth

USERNAME='admin'
PASSWORD='admin'

TRANSACTION_BEGIN="http://%s:8181/restconf/operations/nemo-intent:begin-transaction"
TRANSACTION_END="http://%s:8181/restconf/operations/nemo-intent:end-transaction"
REGISTER_USER="http://%s:8181/restconf/operations/nemo-intent:register-user"
STRUCTURE_UPDATE_USERS="http://%s:8181/restconf/operations/nemo-intent:structure-style-nemo-update"
PHYSICAL_NETWORK="http://%s:8181/restconf/config/generic-physical-network:physical-network"

def create_physical_network(contHost):
	data={
			 "physical-network": {
					 "physical-hosts": {
							 "physical-host": [
									 {
					 "host-id": "22282cca-9a13-4d0c-a67e-a933ebb0b0ae",
											 "node-id": "openflow:2",
											 "host-name": "video-server2",
											 "mac-address": "00:00:00:00:00:04",
											 "port-id": "openflow:2:2",
											 "ip-addresses": {
													 "ip-address": [
															 "192.168.12.4"
													 ]
					 }
				   },
				   {
					 "host-id": "7b796915-adf4-4356-b5ca-de005ac410c1",
						   "node-id": "openflow:1",
						   "host-name": "video-server1",
						   "mac-address": "00:00:00:00:00:01",
						   "port-id": "openflow:1:1",
						   "ip-addresses": {
								   "ip-address": [
										   "192.168.11.2"
								   ]
					 }
				   },
				   {
					 "host-id": "6c787caa-156a-49ed-8546-547bdccf283c",
						   "node-id": "openflow:2",
						   "host-name": "vm2",
						   "mac-address": "00:00:00:00:00:03",
						   "port-id": "openflow:2:1",
						   "ip-addresses": {
								   "ip-address": [
										   "192.168.12.3"
								   ]
					 }
				   },
				   {
					 "host-id": "1eaf9a67-a171-42a8-9282-71cf702f61dd",
						   "node-id": "openflow:1",
						   "host-name": "vm1",
						   "mac-address": "00:00:00:00:00:02",
						   "port-id": "openflow:1:2",
						   "ip-addresses": {
								   "ip-address": [
										   "192.168.12.2"
								   ]
					 }
				   }
				 ]
			   },
			   "physical-nodes": {
					   "physical-node": [
							   {
					 "node-id": "openflow:3",
									   "attribute": [
											   {
						 "attribute-name": "location",
													   "attribute-value": {
															   "string-value": "openflow:3"
													   }
					   }
					 ],
					 "node-type": "router",
									   "physical-port": [
											   {
						 "port-id": "openflow:3:1",
													   "attribute": [
															   {
							 "attribute-name": "location",
																	   "attribute-value": {
																			   "string-value": "openflow:3:1"
																	   }
						   }
						 ],
						 "port-type": "internal",
													   "mac-address": "BE:DB:79:24:0A:B1",
													   "bandwidth": 10240
											   },
					   {
						 "port-id": "openflow:3:2",
							   "attribute": [
									   {
							 "attribute-name": "location",
											   "attribute-value": {
													   "string-value": "openflow:3:2"
											   }
						   }
						 ],
						 "port-type": "internal",
							   "mac-address": "06:E7:27:6D:C2:A5",
							   "bandwidth": 10240
					   },
					   {
						 "port-id": "openflow:3:3",
							   "attribute": [
									   {
							 "attribute-name": "location",
											   "attribute-value": {
													   "string-value": "openflow:3:3"
											   }
						   }
						 ],
						 "port-type": "internal",
							   "mac-address": "9E:00:76:D7:0C:1D",
							   "bandwidth": 10240
					   },
					   {
						 "port-id": "openflow:3:4",
							   "attribute": [
									   {
							 "attribute-name": "location",
											   "attribute-value": {
													   "string-value": "openflow:3:4"
											   }
						   }
						 ],
						 "port-type": "external",
							   "mac-address": "00:00:00:00:03:04",
							   "bandwidth": 10240
					   }
					 ]
				   },
				   {
					 "node-id": "openflow:4",
						   "attribute": [
								   {
						 "attribute-name": "location",
										   "attribute-value": {
												   "string-value": "openflow:4"
										   }
					   }
					 ],
					 "node-type": "router",
						   "physical-port": [
								   {
						 "port-id": "openflow:4:2",
										   "attribute": [
												   {
							 "attribute-name": "location",
														   "attribute-value": {
																   "string-value": "openflow:4:2"
														   }
						   }
						 ],
						 "port-type": "external",
										   "mac-address": "00:00:00:00:04:02",
										   "bandwidth": 10240
								   },
					   {
						 "port-id": "openflow:4:1",
							   "attribute": [
									   {
							 "attribute-name": "location",
											   "attribute-value": {
													   "string-value": "openflow:4:1"
											   }
						   }
						 ],
						 "port-type": "internal",
							   "mac-address": "4E:93:BE:EF:99:35",
							   "bandwidth": 10240
					   }
					 ]
				   },
				   {
					 "node-id": "openflow:1",
						   "attribute": [
								   {
						 "attribute-name": "location",
										   "attribute-value": {
												   "string-value": "openflow:1"
										   }
					   }
					 ],
					 "node-type": "switch",
						   "physical-port": [
								   {
						 "port-id": "openflow:1:1",
										   "attribute": [
												   {
							 "attribute-name": "location",
														   "attribute-value": {
																   "string-value": "openflow:1:1"
														   }
						   }
						 ],
						 "port-type": "external",
										   "mac-address": "BA:8C:1F:60:2C:FC",
										   "bandwidth": 10240
								   },
					   {
						 "port-id": "openflow:1:2",
							   "attribute": [
									   {
							 "attribute-name": "location",
											   "attribute-value": {
													   "string-value": "openflow:1:2"
											   }
						   }
						 ],
						 "port-type": "external",
							   "mac-address": "82:ED:57:DE:28:F0",
							   "bandwidth": 10240
					   },
					   {
						 "port-id": "openflow:1:3",
							   "attribute": [
									   {
							 "attribute-name": "location",
											   "attribute-value": {
													   "string-value": "openflow:1:3"
											   }
						   }
						 ],
						 "port-type": "internal",
							   "mac-address": "62:D3:F1:80:06:F0",
							   "bandwidth": 10240
					   }
					 ]
				   },
				   {
					 "node-id": "openflow:2",
						   "attribute": [
								   {
						 "attribute-name": "location",
										   "attribute-value": {
												   "string-value": "openflow:2"
										   }
					   }
					 ],
					 "node-type": "switch",
						   "physical-port": [
								   {
						 "port-id": "openflow:2:3",
										   "attribute": [
												   {
							 "attribute-name": "location",
														   "attribute-value": {
																   "string-value": "openflow:2:3"
														   }
						   }
						 ],
						 "port-type": "internal",
										   "mac-address": "42:33:22:43:EF:02",
										   "bandwidth": 10240
								   },
					   {
						 "port-id": "openflow:2:2",
							   "attribute": [
									   {
							 "attribute-name": "location",
											   "attribute-value": {
													   "string-value": "openflow:2:2"
											   }
						   }
						 ],
						 "port-type": "external",
							   "mac-address": "32:7C:45:B9:ED:18",
							   "bandwidth": 10240
					   },
					   {
						 "port-id": "openflow:2:1",
							   "attribute": [
									   {
							 "attribute-name": "location",
											   "attribute-value": {
													   "string-value": "openflow:2:1"
											   }
						   }
						 ],
						 "port-type": "external",
							   "mac-address": "D6:86:A8:54:2B:32",
							   "bandwidth": 10240
					   }
					 ]
				   }
				 ]
			   },
			   "physical-links": {
					   "physical-link": [
							   {
					 "link-id": "openflow:4:1",
									   "loss-rate": 1,
									   "delay": 1,
									   "src-port-id": "openflow:4:1",
									   "dest-node-id": "openflow:3",
									   "metric": 1,
									   "dest-port-id": "openflow:3:3",
									   "bandwidth": 10240,
									   "src-node-id": "openflow:4"
							   },
				   {
					 "link-id": "openflow:2:3",
						   "loss-rate": 1,
						   "delay": 1,
						   "src-port-id": "openflow:2:3",
						   "dest-node-id": "openflow:3",
						   "metric": 1,
						   "dest-port-id": "openflow:3:2",
						   "bandwidth": 10240,
						   "src-node-id": "openflow:2"
				   },
				   {
					 "link-id": "openflow:3:1",
						   "loss-rate": 1,
						   "delay": 1,
						   "src-port-id": "openflow:3:1",
						   "dest-node-id": "openflow:1",
						   "metric": 1,
						   "dest-port-id": "openflow:1:3",
						   "bandwidth": 10240,
						   "src-node-id": "openflow:3"
				   },
				   {
					 "link-id": "openflow:1:3",
						   "loss-rate": 1,
						   "delay": 1,
						   "src-port-id": "openflow:1:3",
						   "dest-node-id": "openflow:3",
						   "metric": 1,
						   "dest-port-id": "openflow:3:1",
						   "bandwidth": 10240,
						   "src-node-id": "openflow:1"
				   },
				   {
					 "link-id": "openflow:3:3",
						   "loss-rate": 1,
						   "delay": 1,
						   "src-port-id": "openflow:3:3",
						   "dest-node-id": "openflow:4",
						   "metric": 1,
						   "dest-port-id": "openflow:4:1",
						   "bandwidth": 10240,
						   "src-node-id": "openflow:3"
				   },
				   {
					 "link-id": "openflow:3:2",
						   "loss-rate": 1,
						   "delay": 1,
						   "src-port-id": "openflow:3:2",
						   "dest-node-id": "openflow:2",
						   "metric": 1,
						   "dest-port-id": "openflow:2:3",
						   "bandwidth": 10240,
						   "src-node-id": "openflow:3"
				   }
				 ]
			   }
			 }
		}
	put(PHYSICAL_NETWORK % contHost, data)


def register_user(contHost):
	data={
			"input":{
					"user-id":"14ce424a-3e50-4a2a-ad5c-b29845158c8b",
					"user-name":"user2",
					"user-password":"abc",
					"user-role":"tenant"
					}
		}
	post(REGISTER_USER % contHost, data)

def transaction_begin(contHost):
	data={
			"input":{
					"user-id":"14ce424a-3e50-4a2a-ad5c-b29845158c8b"
					}
		}
	post(TRANSACTION_BEGIN % contHost, data)

def transaction_end(contHost):
	data={
			"input":{
					"user-id":"14ce424a-3e50-4a2a-ad5c-b29845158c8b"
					}
		}
	post(TRANSACTION_END % contHost, data)

def add_server1_host(contHost):
	data={
			"input":{
				  "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
				  "objects":{
						"node":[
								{
									"node-name": "server1",
									"node-type": "host",
									"node-id":"7b796915-adf4-4356-b5ca-de005ac410c1"
								}
							]
						}
					}
		}
	post(STRUCTURE_UPDATE_USERS % contHost, data)

def add_server2_host(contHost):
	data={
			"input":{
				  "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
				  "objects":{
						"node":[
								{
									"node-name": "server2",
									"node-type": "host",
									"node-id":"22282cca-9a13-4d0c-a67e-a933ebb0b0ae"
								}
							]
						}
					}
		}
	post(STRUCTURE_UPDATE_USERS % contHost, data)

def add_vm1_host(contHost):
	data={
			"input":{
				  "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
				  "objects":{
						"node":[
								{
									"node-name": "vm1",
									"node-type": "host",
									"node-id":"1eaf9a67-a171-42a8-9282-71cf702f61dd"
								}
							]
						}
					}
		}
	post(STRUCTURE_UPDATE_USERS % contHost, data)

def add_vm2_host(contHost):
	data={
			"input":{
				  "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
				  "objects":{
						"node":[
								{
									"node-name": "vm2",
									"node-type": "host",
									"node-id":"6c787caa-156a-49ed-8546-547bdccf283c"
								}
							]
						}
					}
		}
	post(STRUCTURE_UPDATE_USERS % contHost, data)

def add_enterpise_node(contHost):
	data={
		  "input":{
			  "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
			  "objects":{
			  "node":[
					  {
							"node-name": "enterprise",
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
															  "value": "192.168.13.0/24"
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

def add_internet_node(contHost):
	data={
		  "input":{
			  "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
			  "objects":{
			  "node":[
					  {
							"node-name": "internet",
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
															  #wait for external network ip
															  "value": "172.168.1.0/24"
															}
														]
													}
										  }
										],
							"node-id": "d463232f-363f-491c-a6f5-097ed0a794d3"
						  }
					]
			  }
		  }
		}
	post(STRUCTURE_UPDATE_USERS % contHost, data)

def add_dmz_node(contHost):
	data={
		  "input":{
			  "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
			  "objects":{
			  "node":[
					  {
							"node-name": "dmz",
							"node-type": "l2-group",
							"sub-node": [
										  {
											"node-id":"7b796915-adf4-4356-b5ca-de005ac410c1",
											"order":"0"
										  }
								],
							"property": [
										  {
											"property-name": "location",
											"property-values": {
													  "string-value": [
															{
															  "order": "0",
															  "value": "openflow:3"
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
										  },
										  {
											"property-name": "gateway-ip",
											"property-values": {
													  "string-value": [
															{
															  "order": "0",
															  "value": "192.168.11.1"
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

def add_interior_node(contHost):
	data={
		  "input":{
			  "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
			  "objects":{
			  "node":[
					  {
							"node-name": "interior",
							"node-type": "l2-group",
							"sub-node": [
										  {
											"node-id":"22282cca-9a13-4d0c-a67e-a933ebb0b0ae",
											"order":"0"
										  },
										  {
											"node-id":"1eaf9a67-a171-42a8-9282-71cf702f61dd",
											"order":"0"
										  },
										  {
											"node-id":"6c787caa-156a-49ed-8546-547bdccf283c",
											"order":"0"
										  }
								],
							"property": [
										  {
											"property-name": "location",
											"property-values": {
													  "string-value": [
															{
															  "order": "0",
															  "value": "openflow:3"
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
										  },
										  {
											"property-name": "gateway-ip",
											"property-values": {
													  "string-value": [
															{
															  "order": "0",
															  "value": "192.168.12.1"
															}
														]
													}
										  }
										],
							"node-id": "175425f7-c9c9-474a-962c-70cb6c180d4d"
						  }
					]
			  }
		  }
		}
	post(STRUCTURE_UPDATE_USERS % contHost, data)

def add_enterprise_interior_connection(contHost):
	data={
		 "input": {
			  "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
			  "objects":{
				  "connection": [
						  {
							"connection-name": "c1",
							"connection-id": "30da6667-608e-4d2f-bb50-79e5cabcc523",
							"end-node": [
							  {
								"order": "0",
								"node-id": "94a6fb90-b425-4ffd-9515-c0684aa4c37f"
							  },
							  {
								"order": "0",
								"node-id": "175425f7-c9c9-474a-962c-70cb6c180d4d"
							  }
							],
							"connection-type": "p2p",
							"property": [
								{
									"property-name": "bandwidth",
									"property-values": {
													  "int-value": [
															{
															  "order": "0",
															  "value": "128"
															}
														]
													}
								}
							]
						}
					]
				}
		  }
	}
	post(STRUCTURE_UPDATE_USERS % contHost, data)

def add_interior_dmz_connection(contHost):
	data={
		 "input": {
			  "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
			  "objects":{
				  "connection": [
						  {
							"connection-name": "c2",
							"connection-id": "b49e3960-c08d-4fff-b9fc-08b65ebcde2c",
							"end-node": [
							  {
								"order": "0",
								"node-id": "175425f7-c9c9-474a-962c-70cb6c180d4d"
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

def add_dmz_internet_connection(contHost):
	data={
		 "input": {
			  "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
			  "objects":{
				  "connection": [
						  {
							"connection-name": "c3",
							"connection-id": "e0d56fee-7235-4748-a2a1-eb5e3733d866",
							"end-node": [
							  {
								"order": "0",
								"node-id": "b46cfa7f-93a3-43f4-ac20-09307c75feca"
							  },
							  {
								"order": "0",
								"node-id": "d463232f-363f-491c-a6f5-097ed0a794d3"
							  }
							],
							"connection-type": "p2p"
						}
					]
				}
		  }
	}
	post(STRUCTURE_UPDATE_USERS % contHost, data)

def update_enterprise_interior_connection(contHost):
	data={
		 "input": {
			  "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
			  "objects":{
				  "connection": [
						  {
							"connection-name": "c1",
							"connection-id": "30da6667-608e-4d2f-bb50-79e5cabcc523",
							"end-node": [
							  {
								"order": "0",
								"node-id": "94a6fb90-b425-4ffd-9515-c0684aa4c37f"
							  },
							  {
								"order": "0",
								"node-id": "175425f7-c9c9-474a-962c-70cb6c180d4d"
							  }
							],
							"connection-type": "p2p",
							"property": [
								{
									"property-name": "bandwidth",
									"property-values": {
													  "int-value": [
															{
															  "order": "0",
															  "value": "512"
															}
														]
													}
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

def put(url, data):
    headers = {'Content-type': 'application/yang.data+json',
               'Accept': 'application/yang.data+json'}
    print "PUT %s" % url
    print json.dumps(data, indent=4, sort_keys=True)
    r = requests.put(url, data=json.dumps(data), headers=headers, auth=HTTPBasicAuth(USERNAME, PASSWORD))
    print r.text
    r.raise_for_status()

def delete(url):
    headers = {'Content-type': 'application/yang.data+json',
               'Accept': 'application/yang.data+json'}
    print "DELETE %s" % url
    r = requests.delete(url, headers=headers, auth=HTTPBasicAuth(USERNAME, PASSWORD))
    print r.text
    r.raise_for_status()


if __name__ == '__main__':

	parser = argparse.ArgumentParser()
	parser.add_argument('--controller', default='127.0.0.1', help='controller IP')
	args=parser.parse_args()


        create_physical_network(args.controller)

	# CREATE User;
	register_user(args.controller)

	transaction_begin(args.controller)

	# IMPORT Node server1 Type host;
	add_server1_host(args.controller)

	# IMPORT Node server2 Type host;
	add_server2_host(args.controller)

	# IMPORT Node vm1 Type host;
	add_vm1_host(args.controller)

	# IMPORT Node vm2 Type host;
	add_vm2_host(args.controller)

	# IMPORT Node enterprise Type ext-group Property location:openflow:4:2, ip-prefix:192.18.13.0/24;
	add_enterpise_node(args.controller)

	# CREATE Node interior Type l2-group Contain server1,vm1,vm2;
	add_interior_node(args.controller)

	# CREATE Node dmz Type l2-group Contain server2;
	add_dmz_node(args.controller)

	# IMPORT Node internet Type ext-group Property location:openflow:3:4, ip-prefix:172.168.1.0/24;
	add_internet_node(args.controller)

	transaction_end(args.controller)

