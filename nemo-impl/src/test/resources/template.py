#!/usr/bin/python
#Filename:servicechain.py
import requests,json
import argparse, sys
from requests.auth import HTTPBasicAuth

USERNAME='admin'
PASSWORD='admin'

TRANSACTION_BEGIN="http://%s:8181/restconf/operations/nemo-intent:begin-transaction"
TRANSACTION_END="http://%s:8181/restconf/operations/nemo-intent:end-transaction"
REGISTER_USER="http://%s:8181/restconf/operations/nemo-intent:register-user"
LANGUAGE_INTENT="http://%s:8181/restconf/operations/nemo-intent:language-style-nemo-request"	


def register_admin(contHost):
	data={
			"input":{
					"user-id":"af4fc2be-e3f4-4388-a8ef-3aabae872f2b",
					"user-name":"admin",
					"user-password":"abcd",
					"user-role":"admin"
					}
		}
	post(REGISTER_USER % contHost, data)

def transaction_begin_admin(contHost):
	data={
			"input":{
					"user-id":"af4fc2be-e3f4-4388-a8ef-3aabae872f2b"				
					}
		}
	post(TRANSACTION_BEGIN % contHost, data)
	
def transaction_end_admin(contHost):
	data={
			"input":{
					"user-id":"af4fc2be-e3f4-4388-a8ef-3aabae872f2b"				
					}
		}
	post(TRANSACTION_END % contHost, data)
	
def register_template_definition(contHost):
	data={
			"input":{
			  "user-id": "af4fc2be-e3f4-4388-a8ef-3aabae872f2b",
			  "nemo-statement": 
				"CREATE NodeModel dmz Property string: location-fw, string: location-n2, string: ipprefix, string: gatewayip, string: srcip,string: subnodes-n2; Node fw1 Type fw Property location: location-fw, operating-mode: layer3; Node n1 Type chain-group Contain fw1; Node n2 Type l2-group Property location: location-n2, ip-prefix: ipprefix, gateway-ip: gatewayip, sub-nodes: subnodes-n2; Connection c1 Type p2p Endnodes n1,n2; Flow f1 Match dst-ip: ipprefix, src-ip: srcip, dst-port: 80; Operation o1  Target f1 Priority 0 Action deny;"
			}
		}
	post(LANGUAGE_INTENT % contHost, data)
	
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

def add_vm2(contHost):
	data={		
			"input":{
					"user-id":"14ce424a-3e50-4a2a-ad5c-b29845158c8b",
					"nemo-statement": "CREATE Node vm2 Type host;"
					}	
	}
	post(LANGUAGE_INTENT % contHost,data)

def add_server1(contHost):
	data={		
			"input":{
					"user-id":"14ce424a-3e50-4a2a-ad5c-b29845158c8b",
					"nemo-statement": "CREATE Node server1 Type host;"
					}	
		}
	post(LANGUAGE_INTENT % contHost,data)

def add_dmz_node(contHost):
	data={
			"input":{				 
				  "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
				  "nemo-statement":
					"CREATE Node dmz1 Type dmz Property location-fw: openflow:1:1, location-n2: openflow:3,ipprefix: 192.168.12.0/24, gatewayip: 192.168.12.1, srcip: 172.168.1.0/24, subnodes-n2:[vm2,server1];"
				}
		}
	post(LANGUAGE_INTENT % contHost,data)

def add_internet_node(contHost):
	data={
			"input":{				 
				  "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
				  "nemo-statement":
					"CREATE Node internet Type ext-group Property location: openflow:3:4, ac-info-network: layer3, ac-info-protocol: static, ip-prefix: 172.168.1.0/24;"
				}
		}
	post(LANGUAGE_INTENT % contHost,data)

def add_interior_node(contHost):
	data={
			"input":{				 
				  "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
				  "nemo-statement":
					"CREATE Node interior Type ext-group Property location: openflow:4:2, ac-info-network: layer3, ac-info-protocol: static, ip-prefix: 192.168.13.0/24;"
				}
	}
	post(LANGUAGE_INTENT % contHost,data)
	
def add_internet_dmz_connection(contHost):
	data={
			"input":{				 
				  "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
				  "nemo-statement":
					"CREATE Connection c1 Type p2p Endnodes internet,dmz1.n1;"
				}
	}
	post(LANGUAGE_INTENT % contHost,data)
	
def add_dmz_interior_connection(contHost):
	data={
			"input":{				 
				  "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
				  "nemo-statement":
					"CREATE Connection c2 Type p2p Endnodes dmz1.n2,interior;"
				}
	}
	post(LANGUAGE_INTENT % contHost,data)

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
	
	register_admin(args.controller)
	transaction_begin_admin(args.controller)
	register_template_definition(args.controller)
	transaction_end_admin(args.controller)
	
	register_user(args.controller)
	transaction_begin(args.controller)
	add_vm2(args.controller)
	add_server1(args.controller)
	add_internet_node(args.controller)
	add_dmz_node(args.controller)
	add_interior_node(args.controller)
	
	add_internet_dmz_connection(args.controller)
	add_dmz_interior_connection(args.controller)
	transaction_end(args.controller)