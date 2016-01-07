#!/usr/bin/python

# This script deletes and recreates the NIC BoD intents.
# Use nic-bod-setup.py to set up the physical network and NEMO nodes first

import requests,json
import argparse, sys
from requests.auth import HTTPBasicAuth

USERNAME='admin'
PASSWORD='admin'

NIC_INTENTS="http://%s:8181/restconf/config/intent:intents"
NIC_INTENT="http://%s:8181/restconf/config/intent:intents/intent/14ce424a-3e50-4a2a-ad5c-b29845158c8b"


def delete_nic_intents(contHost):
        delete(NIC_INTENTS % contHost)

def create_nic_intent(contHost):
        data = {
                "intent": {
                        "id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
                        "actions": [
                                {
         "order": 1,
                                        "allow": {}
                                }
     ],
     "subjects": [
             {
         "order": 1 ,
                     "end-point-group": { "name": "dmz" }
             }, {
          "order": 2 ,
                     "end-point-group": { "name": "interior" }
             }
     ],
     "constraints": [
             {
          "order": 1,
                     "bandwidth-constraint": { "bandwidth": "10G" }
             }
     ],
     "conditions": [
             {
          "order": 1,
                     "daily": { "start-time": "08:00:00Z", "duration": "10h" }
             }
     ]
}
                }
        put(NIC_INTENT % contHost, data)


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

        delete_nic_intents(args.controller)
        create_nic_intent(args.controller)
