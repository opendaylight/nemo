#!/usr/bin/python
#Filename:vpn.py
import requests,json
import argparse, sys
from requests.auth import HTTPBasicAuth

USERNAME='admin'
PASSWORD='admin'

TRANSACTION_BEGIN="http://%s:8181/restconf/operations/nemo-intent:begin-transaction"
TRANSACTION_END="http://%s:8181/restconf/operations/nemo-intent:end-transaction"
REGISTER_USER="http://%s:8181/restconf/operations/nemo-intent:register-user"
STRUCTURE_UPDATE_USERS="http://%s:8181/restconf/operations/nemo-intent:structure-style-nemo-update"

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
					"user-id":"14ce424a-3e50-4a2a-ad5c-b29845158c8b",
					"user-name":"user2",
					"user-password":"abc",
					"user-role":"tenant"
					}
		}
	post(TRANSACTION_BEGIN % contHost, data)

	
def transaction_end(contHost):
	data={
			"input":{
					"user-id":"14ce424a-3e50-4a2a-ad5c-b29845158c8b",
					"user-name":"user2",
					"user-password":"abc",
					"user-role":"tenant"
					}
		}
	post(TRANSACTION_END % contHost, data)

	
def add_branch1_node(contHost):
	data={
		  "input":{
			  "user-name": "user2",
			  "user-role": "tenant",
			  "user-password": "abc",
			  "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
			  "objects":{
			  "node":[
					  {
							"node-name": "bank-branch1",
							"node-type": "ext-group",
							"property": [
										  {
											"property-name": "location",
											"property-values": {
													  "string-value": [
															{
															  "order": "0",
															  "value": "HW:NE40E:CE1:GE1/0/0"
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
															  "value": "160.1.1.0/24"
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
	

def add_branch2_node(contHost):
	data={
		  "input":{
			  "user-name": "user2",
			  "user-role": "tenant",
			  "user-password": "abc",
			  "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
			  "objects":{
			  "node":[
					  {
							"node-name": "bank-branch2",
							"node-type": "ext-group",
							"property": [
										  {
											"property-name": "location",
											"property-values": {
													  "string-value": [
															{
															  "order": "0",
															  "value": "HW:NE40E:CE2:GE3/0/2"
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
															  "value": "169.1.1.0/24"
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
	
def add_DC_node(contHost):
	data={
		  "input":{
			  "user-name": "user2",
			  "user-role": "tenant",
			  "user-password": "abc",
			  "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
			  "objects":{
			  "node":[
					  {
							"node-name": "bank-DC",
							"node-type": "ext-group",
							"property": [
										  {
											"property-name": "location",
											"property-values": {
													  "string-value": [
															{
															  "order": "0",
															  "value": "HW:NE40E:CE3:GE1/0/0"
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
															  "value": "164.1.1.0/24"
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
	
	
	
def add_branch1_branch2_connection(contHost):
	data={
		 "input": {
			  "user-name": "user2",
			  "user-role": "tenant",
			  "user-password": "abc",
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
								"node-id": "d463232f-363f-491c-a6f5-097ed0a794d3"
							  }
							],
							"connection-type": "p2p",
						}
					]
				}
		  }
	}
	post(STRUCTURE_UPDATE_USERS % contHost, data)

def add_branch1_DC_connection(contHost):
	data={
		 "input": {
			  "user-name": "user2",
			  "user-role": "tenant",
			  "user-password": "abc",
			  "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
			  "objects":{
				  "connection": [
						  {
							"connection-name": "c2",
							"connection-id": "b49e3960-c08d-4fff-b9fc-08b65ebcde2c",
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
							"connection-type": "p2p",
						}
					]
				}
		  }
	}
	post(STRUCTURE_UPDATE_USERS % contHost, data)
	

def add_branch2_DC_connection(contHost):
	data={
		 "input": {
			  "user-name": "user2",
			  "user-role": "tenant",
			  "user-password": "abc",
			  "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
			  "objects":{
				  "connection": [
						  {
							"connection-name": "c3",
							"connection-id": "e0d56fee-7235-4748-a2a1-eb5e3733d866",
							"end-node": [
							  {
								"order": "0",
								"node-id": "d463232f-363f-491c-a6f5-097ed0a794d3"
							  },
							  {
								"order": "0",
								"node-id": "b46cfa7f-93a3-43f4-ac20-09307c75feca"
							  }
							],
							"connection-type": "p2p",
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

	transaction_begin(args.controller)
	
	add_branch1_node(args.controller)
	add_branch2_node(args.controller)
	add_DC_node(args.controller)
	
	add_branch1_branch2_connection(args.controller)
	add_branch1_DC_connection(args.controller)
	add_branch2_DC_connection(args.controller)
	
	transaction_end(args.controller)