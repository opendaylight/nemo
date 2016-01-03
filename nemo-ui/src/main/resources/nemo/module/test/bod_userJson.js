var userinfo ={
  "users": {
    "user": [
      {
        "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
        "user-role": "tenant",
        "user-password": "abc",
        "user-name": "user2",
        "objects": {
          "node": [
            {
              "node-id": "22282cca-9a13-4d0c-a67e-a933ebb0b0ae",
              "node-name": "server2",
              "node-type": "host"
            },
            {
              "node-id": "1eaf9a67-a171-42a8-9282-71cf702f61dd",
              "node-name": "vm1",
              "node-type": "host"
            },
            {
              "node-id": "175425f7-c9c9-474a-962c-70cb6c180d4d",
              "property": [
                {
                  "property-name": "location",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "openflow:3",
                        "order": 0
                      }
                    ]
                  }
                },
                {
                  "property-name": "ip-prefix",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "192.168.12.0/24",
                        "order": 0
                      }
                    ]
                  }
                },
                {
                  "property-name": "gateway-ip",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "192.168.12.1",
                        "order": 0
                      }
                    ]
                  }
                }
              ],
              "node-type": "l2-group",
              "node-name": "interior",
              "sub-node": [
                {
                  "node-id": "6c787caa-156a-49ed-8546-547bdccf283c",
                  "order": 0
                },
                {
                  "node-id": "22282cca-9a13-4d0c-a67e-a933ebb0b0ae",
                  "order": 0
                },
                {
                  "node-id": "1eaf9a67-a171-42a8-9282-71cf702f61dd",
                  "order": 0
                }
              ]
            },
            {
              "node-id": "6c787caa-156a-49ed-8546-547bdccf283c",
              "node-name": "vm2",
              "node-type": "host"
            },
            {
              "node-id": "94a6fb90-b425-4ffd-9515-c0684aa4c37f",
              "property": [
                {
                  "property-name": "ac-info-network",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "layer3",
                        "order": 0
                      }
                    ]
                  }
                },
                {
                  "property-name": "ip-prefix",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "192.168.13.0/24",
                        "order": 0
                      }
                    ]
                  }
                },
                {
                  "property-name": "ac-info-protocol",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "static",
                        "order": 0
                      }
                    ]
                  }
                },
                {
                  "property-name": "location",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "openflow:4:2",
                        "order": 0
                      }
                    ]
                  }
                }
              ],
              "node-type": "ext-group",
              "node-name": "enterprise"
            },
            {
              "node-id": "b46cfa7f-93a3-43f4-ac20-09307c75feca",
              "property": [
                {
                  "property-name": "location",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "openflow:3",
                        "order": 0
                      }
                    ]
                  }
                },
                {
                  "property-name": "ip-prefix",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "192.168.11.0/24",
                        "order": 0
                      }
                    ]
                  }
                },
                {
                  "property-name": "gateway-ip",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "192.168.11.1",
                        "order": 0
                      }
                    ]
                  }
                }
              ],
              "node-type": "l2-group",
              "node-name": "dmz",
              "sub-node": [
                {
                  "node-id": "7b796915-adf4-4356-b5ca-de005ac410c1",
                  "order": 0
                }
              ]
            },
            {
              "node-id": "7b796915-adf4-4356-b5ca-de005ac410c1",
              "node-name": "server1",
              "node-type": "host"
            },
            {
              "node-id": "d463232f-363f-491c-a6f5-097ed0a794d3",
              "property": [
                {
                  "property-name": "ac-info-network",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "layer3",
                        "order": 0
                      }
                    ]
                  }
                },
                {
                  "property-name": "ip-prefix",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "172.168.1.0/24",
                        "order": 0
                      }
                    ]
                  }
                },
                {
                  "property-name": "ac-info-protocol",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "static",
                        "order": 0
                      }
                    ]
                  }
                },
                {
                  "property-name": "location",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "openflow:3:4",
                        "order": 0
                      }
                    ]
                  }
                }
              ],
              "node-type": "ext-group",
              "node-name": "internet"
            }
          ],
          "connection": [
            {
              "connection-id": "30da6667-608e-4d2f-bb50-79e5cabcc523",
              "property": [
                {
                  "property-name": "bandwidth",
                  "property-values": {
                    "int-value": [
                      {
                        "value": 128,
                        "order": 0
                      }
                    ]
                  }
                }
              ],
              "connection-type": "p2p",
              "end-node": [
                {
                  "node-id": "94a6fb90-b425-4ffd-9515-c0684aa4c37f",
                  "order": 0
                },
                {
                  "node-id": "175425f7-c9c9-474a-962c-70cb6c180d4d",
                  "order": 0
                }
              ],
              "connection-name": "c1"
            },
            {
              "connection-id": "e0d56fee-7235-4748-a2a1-eb5e3733d866",
              "connection-type": "p2p",
              "end-node": [
                {
                  "node-id": "d463232f-363f-491c-a6f5-097ed0a794d3",
                  "order": 0
                },
                {
                  "node-id": "b46cfa7f-93a3-43f4-ac20-09307c75feca",
                  "order": 0
                }
              ],
              "connection-name": "c3"
            },
            {
              "connection-id": "b49e3960-c08d-4fff-b9fc-08b65ebcde2c",
              "connection-type": "p2p",
              "end-node": [
                {
                  "node-id": "175425f7-c9c9-474a-962c-70cb6c180d4d",
                  "order": 0
                },
                {
                  "node-id": "b46cfa7f-93a3-43f4-ac20-09307c75feca",
                  "order": 0
                }
              ],
              "connection-name": "c2"
            }
          ]
        }
      }
    ]
  }
}
//console.log(userinfo);
userinfo2={
  "users": {
    "user": [
      {
        "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
        "user-role": "tenant",
        "user-password": "abc",
        "user-name": "user2",
        "objects": {
          "node": [
            {
              "node-id": "22282cca-9a13-4d0c-a67e-a933ebb0b0ae",
              "node-name": "server1",
              "node-type": "host"
            },
            {
              "node-id": "9d6f5343-f38e-4101-ab60-309e6322ace3",
              "property": [
                {
                  "property-name": "location",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "openflow:1:1",
                        "order": 0
                      }
                    ]
                  }
                },
                {
                  "property-name": "operating-mode",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "layer3",
                        "order": 0
                      }
                    ]
                  }
                }
              ],
              "node-type": "fw",
              "node-name": "dmz1.fw1"
            },
            {
              "node-id": "ae83ca1d-43ef-4d02-a48b-6c250bb084bd",
              "property": [
                {
                  "property-name": "ip-prefix",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "192.168.13.0/24",
                        "order": 0
                      }
                    ]
                  }
                },
                {
                  "property-name": "ac-info-network",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "layer3",
                        "order": 0
                      }
                    ]
                  }
                },
                {
                  "property-name": "ac-info-protocol",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "static",
                        "order": 0
                      }
                    ]
                  }
                },
                {
                  "property-name": "location",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "openflow:4:2",
                        "order": 0
                      }
                    ]
                  }
                }
              ],
              "node-type": "ext-group",
              "node-name": "interior"
            },
            {
              "node-id": "7dc2233e-f8a2-416c-902a-95cff42b398a",
              "property": [
                {
                  "property-name": "location",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "openflow:3",
                        "order": 0
                      }
                    ]
                  }
                },
                {
                  "property-name": "ip-prefix",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "192.168.12.0/24",
                        "order": 0
                      }
                    ]
                  }
                },
                {
                  "property-name": "gateway-ip",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "192.168.12.1",
                        "order": 0
                      }
                    ]
                  }
                }
              ],
              "node-type": "l2-group",
              "node-name": "dmz1.n2",
              "sub-node": [
                {
                  "node-id": "6c787caa-156a-49ed-8546-547bdccf283c",
                  "order": 0
                },
                {
                  "node-id": "22282cca-9a13-4d0c-a67e-a933ebb0b0ae",
                  "order": 1
                }
              ]
            },
            {
              "node-id": "c3afb4f5-ffb4-4413-bad9-020babc67ed8",
              "property": [
                {
                  "property-name": "ip-prefix",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "172.168.1.0/24",
                        "order": 0
                      }
                    ]
                  }
                },
                {
                  "property-name": "ac-info-network",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "layer3",
                        "order": 0
                      }
                    ]
                  }
                },
                {
                  "property-name": "ac-info-protocol",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "static",
                        "order": 0
                      }
                    ]
                  }
                },
                {
                  "property-name": "location",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "openflow:3:4",
                        "order": 0
                      }
                    ]
                  }
                }
              ],
              "node-type": "ext-group",
              "node-name": "internet"
            },
            {
              "node-id": "6c787caa-156a-49ed-8546-547bdccf283c",
              "node-name": "vm2",
              "node-type": "host"
            },
            {
              "node-id": "0dc22c0a-8ff0-448f-afc3-86fa22976242",
              "node-type": "chain-group",
              "node-name": "dmz1.n1",
              "sub-node": [
                {
                  "node-id": "9d6f5343-f38e-4101-ab60-309e6322ace3",
                  "order": 0
                }
              ]
            }
          ],
          "connection": [
            {
              "connection-id": "94709024-6675-4a9a-b098-07e14d18633f",
              "end-node": [
                {
                  "node-id": "c3afb4f5-ffb4-4413-bad9-020babc67ed8",
                  "order": 0
                },
                {
                  "node-id": "0dc22c0a-8ff0-448f-afc3-86fa22976242",
                  "order": 0
                }
              ],
              "connection-type": "p2p",
              "connection-name": "c1"
            },
            {
              "connection-id": "a88b037d-0aa0-4a50-8076-225449c8f036",
              "end-node": [
                {
                  "node-id": "0dc22c0a-8ff0-448f-afc3-86fa22976242",
                  "order": 0
                },
                {
                  "node-id": "7dc2233e-f8a2-416c-902a-95cff42b398a",
                  "order": 1
                }
              ],
              "connection-type": "p2p",
              "connection-name": "dmz1.c1"
            },
            {
              "connection-id": "06c60ef9-f16d-4368-9697-aa6b1cf1340a",
              "end-node": [
                {
                  "node-id": "ae83ca1d-43ef-4d02-a48b-6c250bb084bd",
                  "order": 0
                },
                {
                  "node-id": "7dc2233e-f8a2-416c-902a-95cff42b398a",
                  "order": 0
                }
              ],
              "connection-type": "p2p",
              "connection-name": "c2"
            }
          ],
          "flow": [
            {
              "flow-id": "613648ce-1059-4d06-b25a-0d54193c6586",
              "match-item": [
                {
                  "match-item-name": "dst-port",
                  "match-item-value": {
                    "int-value": 80
                  }
                },
                {
                  "match-item-name": "dst-ip",
                  "match-item-value": {
                    "string-value": "192.168.12.0/24"
                  }
                },
                {
                  "match-item-name": "src-ip",
                  "match-item-value": {
                    "string-value": "172.168.1.0/24"
                  }
                }
              ],
              "flow-name": "dmz1.f1"
            }
          ]
        },
        "operations": {
          "operation": [
            {
              "operation-id": "315b1a21-f5b3-4298-868f-6b08729d2622",
              "priority": 0,
              "action": [
                {
                  "action-name": "deny",
                  "order": 0
                }
              ],
              "target-object": "613648ce-1059-4d06-b25a-0d54193c6586",
              "operation-name": "dmz1.o1"
            }
          ]
        },
        "template-instances": {
          "template-instance": [
            {
              "template-instance-id": "e630091d-f336-4a3a-a699-fe3a4fe7c6e2",
              "template-parameter": [
                {
                  "parameter-name": "subnodes-n2",
                  "parameter-values": {
                    "string-value": [
                      {
                        "value": "vm2",
                        "order": 0
                      },
                      {
                        "value": "server1",
                        "order": 1
                      }
                    ]
                  }
                },
                {
                  "parameter-name": "location-fw",
                  "parameter-values": {
                    "string-value": [
                      {
                        "value": "openflow:1:1",
                        "order": 0
                      }
                    ]
                  }
                },
                {
                  "parameter-name": "ipprefix",
                  "parameter-values": {
                    "string-value": [
                      {
                        "value": "192.168.12.0/24",
                        "order": 0
                      }
                    ]
                  }
                },
                {
                  "parameter-name": "location-n2",
                  "parameter-values": {
                    "string-value": [
                      {
                        "value": "openflow:3",
                        "order": 0
                      }
                    ]
                  }
                },
                {
                  "parameter-name": "gatewayip",
                  "parameter-values": {
                    "string-value": [
                      {
                        "value": "192.168.12.1",
                        "order": 0
                      }
                    ]
                  }
                },
                {
                  "parameter-name": "srcip",
                  "parameter-values": {
                    "string-value": [
                      {
                        "value": "172.168.1.0/24",
                        "order": 0
                      }
                    ]
                  }
                }
              ],
              "template-name": "dmz",
              "template-instance-name": "dmz1"
            }
          ]
        }
      },
      {
        "user-id": "af4fc2be-e3f4-4388-a8ef-3aabae872f2b",
        "user-role": "admin",
        "user-password": "abcd",
        "user-name": "admin",
        "template-definitions": {
          "template-definition": [
            {
              "template-name": "dmz",
              "abstract-intents": {
                "abstract-objects": {
                  "abstract-connection": [
                    {
                      "connection-id": "86f56efc-db49-4d7c-853c-6e7db169f56c",
                      "end-node": [
                        {
                          "node-id": "fc096b0b-95cb-4f12-af28-b700a1228191",
                          "order": 0
                        },
                        {
                          "node-id": "9cfda02c-ba8b-41c2-9b36-b7144390fcc3",
                          "order": 1
                        }
                      ],
                      "connection-type": "p2p",
                      "connection-name": "c1"
                    }
                  ],
                  "abstract-node": [
                    {
                      "node-id": "f011ae1d-bb3e-42e0-9fa1-f6fa3641f02d",
                      "property": [
                        {
                          "property-name": "location",
                          "property-values": {
                            "string-value": [
                              {
                                "value": "location-fw",
                                "order": 0
                              }
                            ]
                          }
                        },
                        {
                          "property-name": "operating-mode",
                          "property-values": {
                            "string-value": [
                              {
                                "value": "layer3",
                                "order": 0
                              }
                            ]
                          }
                        }
                      ],
                      "node-type": "fw",
                      "node-name": "fw1"
                    },
                    {
                      "node-id": "9cfda02c-ba8b-41c2-9b36-b7144390fcc3",
                      "property": [
                        {
                          "property-name": "ip-prefix",
                          "property-values": {
                            "string-value": [
                              {
                                "value": "ipprefix",
                                "order": 0
                              }
                            ]
                          }
                        },
                        {
                          "property-name": "gateway-ip",
                          "property-values": {
                            "string-value": [
                              {
                                "value": "gatewayip",
                                "order": 0
                              }
                            ]
                          }
                        },
                        {
                          "property-name": "location",
                          "property-values": {
                            "string-value": [
                              {
                                "value": "location-n2",
                                "order": 0
                              }
                            ]
                          }
                        },
                        {
                          "property-name": "sub-nodes",
                          "property-values": {
                            "string-value": [
                              {
                                "value": "subnodes-n2",
                                "order": 0
                              }
                            ]
                          }
                        }
                      ],
                      "node-type": "l2-group",
                      "node-name": "n2"
                    },
                    {
                      "node-id": "fc096b0b-95cb-4f12-af28-b700a1228191",
                      "node-type": "chain-group",
                      "node-name": "n1",
                      "sub-node": [
                        {
                          "node-id": "f011ae1d-bb3e-42e0-9fa1-f6fa3641f02d",
                          "order": 0
                        }
                      ]
                    }
                  ],
                  "abstract-flow": [
                    {
                      "flow-id": "815c0bee-b04e-4c56-9bb8-b743d72ba66f",
                      "match-item": [
                        {
                          "match-item-name": "dst-port",
                          "match-item-value": {
                            "int-value": 80
                          }
                        },
                        {
                          "match-item-name": "dst-ip",
                          "match-item-value": {
                            "string-value": "ipprefix"
                          }
                        },
                        {
                          "match-item-name": "src-ip",
                          "match-item-value": {
                            "string-value": "srcip"
                          }
                        }
                      ],
                      "flow-name": "f1"
                    }
                  ]
                },
                "abstract-operations": {
                  "abstract-operation": [
                    {
                      "operation-id": "769c52f5-1c75-4c21-b270-15fa259de39b",
                      "priority": 0,
                      "action": [
                        {
                          "action-name": "deny",
                          "order": 0
                        }
                      ],
                      "target-object": "815c0bee-b04e-4c56-9bb8-b743d72ba66f",
                      "operation-name": "o1"
                    }
                  ]
                }
              },
              "template-parameter": [
                {
                  "parameter-name": "subnodes-n2",
                  "parameter-value-type": "string"
                },
                {
                  "parameter-name": "location-fw",
                  "parameter-value-type": "string"
                },
                {
                  "parameter-name": "ipprefix",
                  "parameter-value-type": "string"
                },
                {
                  "parameter-name": "location-n2",
                  "parameter-value-type": "string"
                },
                {
                  "parameter-name": "gatewayip",
                  "parameter-value-type": "string"
                },
                {
                  "parameter-name": "srcip",
                  "parameter-value-type": "string"
                }
              ]
            }
          ]
        }
      }
    ]
  }
  }
var physicalJson2={
  "physical-network": {
    "physical-hosts": {
      "physical-host": [
        {
          "host-id": "22282cca-9a13-4d0c-a67e-a933ebb0b0ae",
          "node-id": "openflow:2",
          "host-name": "video-server2",
          "mac-address": "08:00:27:a0:a9:b3",
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
          "mac-address": "00:00:00:00:00:03",
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
          "mac-address": "00:00:00:00:00:02",
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
          "mac-address": "00:00:00:00:00:01",
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
          "node-type": "router"
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
          "node-type": "router"
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
              "mac-address": "56:21:6B:B3:3E:1A",
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
              "mac-address": "FE:2C:6E:85:F0:E1",
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
              "mac-address": "B2:78:02:9F:FA:2C",
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
              "mac-address": "52:B8:9B:40:CF:43",
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
              "mac-address": "00:0C:29:98:7E:13",
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
              "mac-address": "52:A7:95:B0:0F:FE",
              "bandwidth": 10240
            }
          ]
        }
      ]
    },
    "physical-links": {
      "physical-link": [
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
          "link-id": "openflow:1:3",
          "loss-rate": 1,
          "delay": 1,
          "src-port-id": "openflow:1:3",
          "dest-node-id": "openflow:3",
          "metric": 1,
          "dest-port-id": "openflow:3:1",
          "bandwidth": 10240,
          "src-node-id": "openflow:1"
        }
      ]
    }
  }
}
var physicalJson={
  "physical-network": {
    "physical-hosts": {
      "physical-host": [
        {
          "host-id": "22282cca-9a13-4d0c-a67e-a933ebb0b0ae",
          "node-id": "openflow:2",
          "host-name": "video-server2",
          "mac-address": "08:00:27:a0:a9:b3",
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
          "mac-address": "00:00:00:00:00:03",
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
          "mac-address": "00:00:00:00:00:02",
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
          "mac-address": "00:00:00:00:00:01",
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
          "node-type": "router"
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
          "node-type": "router"
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
          "node-type": "switch"
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
          "node-type": "switch"
        }
      ]
    },
    "physical-links": {
      "physical-link": [
        {
          "link-id": "openflow:4:2",
          "loss-rate": 1,
          "delay": 1,
          "dest-port-id": "openflow:3:4",
          "src-port-id": "openflow:4:2",
          "dest-node-id": "openflow:3",
          "bandwidth": 10240,
          "src-node-id": "openflow:4"
        },
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
        },
        {
          "link-id": "openflow:2:2",
          "loss-rate": 1,
          "delay": 1,
          "dest-port-id": "openflow:3:4",
          "src-port-id": "openflow:2:2",
          "dest-node-id": "openflow:3",
          "bandwidth": 10240,
          "src-node-id": "openflow:2"
        },
        {
          "link-id": "openflow:3:4",
          "loss-rate": 1,
          "delay": 1,
          "dest-port-id": "openflow:4:2",
          "src-port-id": "openflow:3:4",
          "dest-node-id": "openflow:4",
          "bandwidth": 10240,
          "src-node-id": "openflow:3"
        }
      ]
    },
    "physical-paths": {
      "physical-path": [
        {
          "path-id": "847ce4e6-d631-4d90-b2f3-dcfd56dbce3a",
          "delay": 0,
          "metric": 0,
          "bandwidth": 0
        },
        {
          "path-id": "4ce046d2-0c20-43c7-b7f8-fa441e07d443",
          "delay": 0,
          "metric": 0,
          "bandwidth": 0
        },
        {
          "path-id": "6657f46b-edd2-47fe-b3fc-7b5aa16c7b97",
          "delay": 0,
          "physical-link": [
            {
              "link-id": "openflow:4:1",
              "order": 0
            }
          ],
          "metric": 1,
          "bandwidth": 128
        },
        {
          "path-id": "7b875b56-ce40-4fcd-ac76-b361d0311d8f",
          "delay": 0,
          "physical-link": [
            {
              "link-id": "openflow:3:2",
              "order": 1
            },
            {
              "link-id": "openflow:1:3",
              "order": 0
            }
          ],
          "metric": 2,
          "bandwidth": 0
        },
        {
          "path-id": "cb9b13ad-0172-43f5-9678-69859698071d",
          "delay": 0,
          "physical-link": [
            {
              "link-id": "openflow:1:3",
              "order": 0
            }
          ],
          "metric": 1,
          "bandwidth": 0
        },
        {
          "path-id": "23f80b16-76d1-424c-9a71-aacd60a62bcd",
          "delay": 0,
          "metric": 0,
          "bandwidth": 0
        },
        {
          "path-id": "3b9d1480-df24-4b0b-ba58-8c92605fe917",
          "delay": 0,
          "metric": 0,
          "bandwidth": 0
        },
        {
          "path-id": "f84b9fda-b3f6-4631-a829-5089274093b0",
          "delay": 0,
          "physical-link": [
            {
              "link-id": "openflow:2:3",
              "order": 0
            }
          ],
          "metric": 1,
          "bandwidth": 0
        },
        {
          "path-id": "bdd1e0cb-72aa-4f04-a2db-a07a15c62d61",
          "delay": 0,
          "physical-link": [
            {
              "link-id": "openflow:3:2",
              "order": 0
            }
          ],
          "metric": 1,
          "bandwidth": 0
        },
        {
          "path-id": "eaa935ac-c511-4541-80cd-4a1640ae2246",
          "delay": 0,
          "physical-link": [
            {
              "link-id": "openflow:3:2",
              "order": 1
            },
            {
              "link-id": "openflow:1:3",
              "order": 0
            }
          ],
          "metric": 2,
          "bandwidth": 0
        },
        {
          "path-id": "cb3de13d-cd6f-4f32-9596-09ddbb6d1c44",
          "delay": 0,
          "metric": 0,
          "bandwidth": 0
        },
        {
          "path-id": "4fdc5a09-ceca-461a-b3d3-1c3a9506dc74",
          "delay": 0,
          "physical-link": [
            {
              "link-id": "openflow:3:3",
              "order": 0
            }
          ],
          "metric": 1,
          "bandwidth": 128
        },
        {
          "path-id": "c231b5a3-7752-4f78-b8eb-b023ad4bea2f",
          "delay": 0,
          "physical-link": [
            {
              "link-id": "openflow:3:2",
              "order": 0
            }
          ],
          "metric": 1,
          "bandwidth": 0
        },
        {
          "path-id": "aab18b50-3bf6-4fde-aac1-968ffe22f518",
          "delay": 0,
          "physical-link": [
            {
              "link-id": "openflow:3:1",
              "order": 0
            }
          ],
          "metric": 1,
          "bandwidth": 0
        },
        {
          "path-id": "ba0726d2-c72f-4b3e-b972-72da39430537",
          "delay": 0,
          "physical-link": [
            {
              "link-id": "openflow:2:3",
              "order": 0
            },
            {
              "link-id": "openflow:3:1",
              "order": 1
            }
          ],
          "metric": 2,
          "bandwidth": 0
        },
        {
          "path-id": "e0f22266-e0c2-4467-b143-800a7b212a98",
          "delay": 0,
          "metric": 0,
          "bandwidth": 0
        },
        {
          "path-id": "765e80be-6232-498d-b095-3b2c59448535",
          "delay": 0,
          "physical-link": [
            {
              "link-id": "openflow:2:3",
              "order": 0
            }
          ],
          "metric": 1,
          "bandwidth": 0
        },
        {
          "path-id": "a710b9b8-5abf-4c59-9afd-88ccf8d935b0",
          "delay": 0,
          "physical-link": [
            {
              "link-id": "openflow:3:1",
              "order": 0
            }
          ],
          "metric": 1,
          "bandwidth": 0
        },
        {
          "path-id": "61c4597d-02a5-4609-8756-955024691a17",
          "delay": 0,
          "physical-link": [
            {
              "link-id": "openflow:1:3",
              "order": 0
            }
          ],
          "metric": 1,
          "bandwidth": 0
        },
        {
          "path-id": "5fc169b2-1209-4663-92c3-031100923f6f",
          "delay": 0,
          "physical-link": [
            {
              "link-id": "openflow:2:3",
              "order": 0
            },
            {
              "link-id": "openflow:3:1",
              "order": 1
            }
          ],
          "metric": 2,
          "bandwidth": 0
        }
      ]
    }
  }
}
var virtualJson={
  "virtual-networks": {
    "virtual-network": [
      {
        "network-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
        "virtual-nodes": {
          "virtual-node": [
            {
              "node-id": "d52e6bb0-93aa-4bf8-88cf-f24b11651a88",
              "node-type": "vrouter",
              "physical-resource-requirement": [
                {
                  "attribute-name": "location",
                  "attribute-value": {
                    "string-value": "openflow:3"
                  },
                  "attribute-match-pattern": "equal"
                }
              ],
              "virtual-port": [
                {
                  "port-id": "ee08bce2-0d1b-45f7-947c-73fa82175ee8",
                  "port-type": "internal",
                  "bandwidth": 128
                },
                {
                  "port-id": "5f965841-2e92-4317-a550-4be57d706896",
                  "external-ip-prefixes": {
                    "external-ip-prefix": [
                      "192.168.12.0/24"
                    ]
                  },
                  "port-type": "internal"
                },
                {
                  "port-id": "81e7f41a-be2c-4986-ac1d-11fbd4062be4",
                  "port-type": "internal",
                  "bandwidth": 0
                },
                {
                  "port-id": "c3ea674e-3dfb-492b-98d4-94b5f3b826e5",
                  "external-ip-prefixes": {
                    "external-ip-prefix": [
                      "192.168.12.0/24"
                    ]
                  },
                  "port-type": "internal"
                },
                {
                  "port-id": "c04b0c95-b19b-4d29-962d-bb88ee271703",
                  "external-ip-prefixes": {
                    "external-ip-prefix": [
                      "192.168.12.0/24"
                    ]
                  },
                  "port-type": "internal"
                }
              ]
            },
            {
              "node-id": "b0cec72b-376f-47f4-b7ae-44c0eb10d491",
              "node-type": "vswitch",
              "physical-resource-requirement": [
                {
                  "attribute-name": "location",
                  "attribute-value": {
                    "string-value": "openflow:2"
                  },
                  "attribute-match-pattern": "equal"
                }
              ],
              "virtual-port": [
                {
                  "port-id": "fe8a0153-2a95-4473-8b62-16a6542c9212",
                  "external-mac-addresses": {
                    "external-mac-address": [
                      "08:00:27:a0:a9:b3"
                    ]
                  },
                  "port-type": "external",
                  "physical-resource-requirement": [
                    {
                      "attribute-name": "location",
                      "attribute-value": {
                        "string-value": "openflow:2:2"
                      },
                      "attribute-match-pattern": "equal"
                    }
                  ]
                },
                {
                  "port-id": "f7782068-8bf2-4ffb-a432-447a5cb708c4",
                  "port-type": "internal"
                },
                {
                  "port-id": "7cb2dda8-3cd2-4d84-996a-1d61aa0358a5",
                  "port-type": "internal"
                },
                {
                  "port-id": "f6171160-ef25-45e9-8251-7a44468a9a68",
                  "port-type": "internal"
                }
              ]
            },
            {
              "node-id": "2698e79a-4a9e-4b9a-ae17-a5698a999bc9",
              "node-type": "vswitch",
              "physical-resource-requirement": [
                {
                  "attribute-name": "location",
                  "attribute-value": {
                    "string-value": "openflow:1"
                  },
                  "attribute-match-pattern": "equal"
                }
              ],
              "virtual-port": [
                {
                  "port-id": "34bca859-1b88-4d5a-b564-7d9080063609",
                  "port-type": "internal"
                },
                {
                  "port-id": "707d56c8-4aee-4466-9383-503ecedd31c3",
                  "port-type": "internal"
                },
                {
                  "port-id": "32ea562d-5ba2-4c84-8560-9f0b4f8dc3ae",
                  "port-type": "internal"
                },
                {
                  "port-id": "c0284004-7b36-4bda-ab98-3f33a67655db",
                  "external-mac-addresses": {
                    "external-mac-address": [
                      "00:00:00:00:00:01"
                    ]
                  },
                  "port-type": "external",
                  "physical-resource-requirement": [
                    {
                      "attribute-name": "location",
                      "attribute-value": {
                        "string-value": "openflow:1:2"
                      },
                      "attribute-match-pattern": "equal"
                    }
                  ]
                }
              ]
            },
            {
              "node-id": "868cf78a-54b7-495d-9ae7-a8fa3f2c7883",
              "node-type": "vrouter",
              "physical-resource-requirement": [
                {
                  "attribute-name": "location",
                  "attribute-value": {
                    "string-value": "openflow:3"
                  },
                  "attribute-match-pattern": "equal"
                }
              ],
              "virtual-port": [
                {
                  "port-id": "9fb8376a-f286-489a-bde4-d5adde1c28c8",
                  "port-type": "internal",
                  "bandwidth": 0
                },
                {
                  "port-id": "b743e2d7-bec1-4e88-b2f8-0c3438322e03",
                  "port-type": "internal",
                  "bandwidth": 0
                },
                {
                  "port-id": "57c62f69-536a-4628-8d03-a3bf230ac607",
                  "external-ip-prefixes": {
                    "external-ip-prefix": [
                      "192.168.11.0/24"
                    ]
                  },
                  "port-type": "internal"
                }
              ]
            },
            {
              "node-id": "e280be82-6354-4cd3-b8ca-7a41e7acc448",
              "node-type": "vrouter",
              "physical-resource-requirement": [
                {
                  "attribute-name": "location",
                  "attribute-value": {
                    "string-value": "openflow:4"
                  },
                  "attribute-match-pattern": "equal"
                }
              ],
              "virtual-port": [
                {
                  "port-id": "01a637dd-55c9-4d96-8729-91538346001f",
                  "port-type": "internal",
                  "bandwidth": 128
                },
                {
                  "port-id": "1b10a84d-247c-40b7-b4e6-6e3e0b140cda",
                  "external-ip-prefixes": {
                    "external-ip-prefix": [
                      "192.168.13.0/24"
                    ]
                  },
                  "port-type": "external",
                  "physical-resource-requirement": [
                    {
                      "attribute-name": "location",
                      "attribute-value": {
                        "string-value": "openflow:4:2"
                      },
                      "attribute-match-pattern": "equal"
                    }
                  ]
                }
              ]
            },
            {
              "node-id": "80213cce-4e1b-4b32-8d71-e2d68fedc50d",
              "node-type": "vrouter",
              "physical-resource-requirement": [
                {
                  "attribute-name": "location",
                  "attribute-value": {
                    "string-value": "openflow:3"
                  },
                  "attribute-match-pattern": "equal"
                }
              ],
              "virtual-port": [
                {
                  "port-id": "3ac1b718-b541-4df4-8e7b-a56407673cbf",
                  "port-type": "internal",
                  "bandwidth": 0
                },
                {
                  "port-id": "89f43bd8-e6f7-4aea-9fb2-936f6255bf4b",
                  "external-ip-prefixes": {
                    "external-ip-prefix": [
                      "172.168.1.0/24"
                    ]
                  },
                  "port-type": "external",
                  "physical-resource-requirement": [
                    {
                      "attribute-name": "location",
                      "attribute-value": {
                        "string-value": "openflow:3:4"
                      },
                      "attribute-match-pattern": "equal"
                    }
                  ]
                }
              ]
            },
            {
              "node-id": "bfc9341e-52d1-4e00-a992-110140be11b4",
              "node-type": "vswitch",
              "physical-resource-requirement": [
                {
                  "attribute-name": "location",
                  "attribute-value": {
                    "string-value": "openflow:2"
                  },
                  "attribute-match-pattern": "equal"
                }
              ],
              "virtual-port": [
                {
                  "port-id": "57c413cd-8b84-4eb9-80d1-b67b3b187687",
                  "port-type": "internal"
                },
                {
                  "port-id": "3ccecbc5-6871-448e-bfe7-7da0d8d617da",
                  "port-type": "internal"
                },
                {
                  "port-id": "7ac662fe-1684-445e-9253-9b55a3cbd03c",
                  "port-type": "internal"
                },
                {
                  "port-id": "78baf6e4-5df3-45b2-be20-107844d71d87",
                  "external-mac-addresses": {
                    "external-mac-address": [
                      "00:00:00:00:00:02"
                    ]
                  },
                  "port-type": "external",
                  "physical-resource-requirement": [
                    {
                      "attribute-name": "location",
                      "attribute-value": {
                        "string-value": "openflow:2:1"
                      },
                      "attribute-match-pattern": "equal"
                    }
                  ]
                }
              ]
            },
            {
              "node-id": "520bdfdc-ecd4-4a3b-ab64-7bdd5f178add",
              "node-type": "vswitch",
              "physical-resource-requirement": [
                {
                  "attribute-name": "location",
                  "attribute-value": {
                    "string-value": "openflow:1"
                  },
                  "attribute-match-pattern": "equal"
                }
              ],
              "virtual-port": [
                {
                  "port-id": "58efb0ef-eb99-4afe-bfaa-ee1d6f98e235",
                  "port-type": "internal"
                },
                {
                  "port-id": "2ec42067-0694-497f-8fd6-142d5e1e2820",
                  "external-mac-addresses": {
                    "external-mac-address": [
                      "00:00:00:00:00:03"
                    ]
                  },
                  "port-type": "external",
                  "physical-resource-requirement": [
                    {
                      "attribute-name": "location",
                      "attribute-value": {
                        "string-value": "openflow:1:1"
                      },
                      "attribute-match-pattern": "equal"
                    }
                  ]
                }
              ]
            }
          ]
        },
        "virtual-arps": {
          "virtual-arp": [
            {
              "ip-address": "192.168.12.4",
              "node-id": "b0cec72b-376f-47f4-b7ae-44c0eb10d491",
              "mac-address": "08:00:27:a0:a9:b3",
              "port-id": "fe8a0153-2a95-4473-8b62-16a6542c9212"
            },
            {
              "ip-address": "192.168.11.2",
              "node-id": "520bdfdc-ecd4-4a3b-ab64-7bdd5f178add",
              "mac-address": "00:00:00:00:00:03",
              "port-id": "2ec42067-0694-497f-8fd6-142d5e1e2820"
            },
            {
              "ip-address": "192.168.12.2",
              "node-id": "2698e79a-4a9e-4b9a-ae17-a5698a999bc9",
              "mac-address": "00:00:00:00:00:01",
              "port-id": "c0284004-7b36-4bda-ab98-3f33a67655db"
            },
            {
              "ip-address": "192.168.12.3",
              "node-id": "bfc9341e-52d1-4e00-a992-110140be11b4",
              "mac-address": "00:00:00:00:00:02",
              "port-id": "78baf6e4-5df3-45b2-be20-107844d71d87"
            }
          ]
        },
        "virtual-links": {
          "virtual-link": [
            {
              "link-id": "b19b3f5c-0fb3-4dca-98b5-5136b406e988",
              "src-node-id": "868cf78a-54b7-495d-9ae7-a8fa3f2c7883",
              "delay": 0,
              "dest-port-id": "3ac1b718-b541-4df4-8e7b-a56407673cbf",
              "metric": 0,
              "bandwidth": 0,
              "dest-node-id": "80213cce-4e1b-4b32-8d71-e2d68fedc50d",
              "src-port-id": "b743e2d7-bec1-4e88-b2f8-0c3438322e03"
            },
            {
              "link-id": "6632b4c7-a794-4e14-a2c7-872e81277d94",
              "src-node-id": "520bdfdc-ecd4-4a3b-ab64-7bdd5f178add",
              "delay": 0,
              "dest-port-id": "57c62f69-536a-4628-8d03-a3bf230ac607",
              "metric": 1,
              "bandwidth": 0,
              "dest-node-id": "868cf78a-54b7-495d-9ae7-a8fa3f2c7883",
              "src-port-id": "58efb0ef-eb99-4afe-bfaa-ee1d6f98e235"
            },
            {
              "link-id": "8b2218cf-fbbb-4e14-85ca-694ceac1edfb",
              "src-node-id": "2698e79a-4a9e-4b9a-ae17-a5698a999bc9",
              "delay": 0,
              "dest-port-id": "f7782068-8bf2-4ffb-a432-447a5cb708c4",
              "metric": 2,
              "bandwidth": 0,
              "dest-node-id": "b0cec72b-376f-47f4-b7ae-44c0eb10d491",
              "src-port-id": "707d56c8-4aee-4466-9383-503ecedd31c3"
            },
            {
              "link-id": "4b591338-e82f-4b8b-a7c4-afb241549455",
              "src-node-id": "b0cec72b-376f-47f4-b7ae-44c0eb10d491",
              "delay": 0,
              "dest-port-id": "7ac662fe-1684-445e-9253-9b55a3cbd03c",
              "metric": 0,
              "bandwidth": 0,
              "dest-node-id": "bfc9341e-52d1-4e00-a992-110140be11b4",
              "src-port-id": "f6171160-ef25-45e9-8251-7a44468a9a68"
            },
            {
              "link-id": "8cb78a59-146d-48f6-90e0-6fda9165a901",
              "src-node-id": "868cf78a-54b7-495d-9ae7-a8fa3f2c7883",
              "delay": 0,
              "dest-port-id": "81e7f41a-be2c-4986-ac1d-11fbd4062be4",
              "metric": 0,
              "bandwidth": 0,
              "dest-node-id": "d52e6bb0-93aa-4bf8-88cf-f24b11651a88",
              "src-port-id": "9fb8376a-f286-489a-bde4-d5adde1c28c8"
            },
            {
              "link-id": "110d2527-b282-4126-932f-8c2b9d728f82",
              "src-node-id": "d52e6bb0-93aa-4bf8-88cf-f24b11651a88",
              "delay": 0,
              "dest-port-id": "34bca859-1b88-4d5a-b564-7d9080063609",
              "metric": 1,
              "bandwidth": 0,
              "dest-node-id": "2698e79a-4a9e-4b9a-ae17-a5698a999bc9",
              "src-port-id": "c3ea674e-3dfb-492b-98d4-94b5f3b826e5"
            },
            {
              "link-id": "c00198e9-8b1c-4f12-84f2-5ddc5bbee995",
              "src-node-id": "80213cce-4e1b-4b32-8d71-e2d68fedc50d",
              "delay": 0,
              "dest-port-id": "b743e2d7-bec1-4e88-b2f8-0c3438322e03",
              "metric": 0,
              "bandwidth": 0,
              "dest-node-id": "868cf78a-54b7-495d-9ae7-a8fa3f2c7883",
              "src-port-id": "3ac1b718-b541-4df4-8e7b-a56407673cbf"
            },
            {
              "link-id": "98d7b523-9532-4974-825e-c91aa402fe28",
              "src-node-id": "b0cec72b-376f-47f4-b7ae-44c0eb10d491",
              "delay": 0,
              "dest-port-id": "5f965841-2e92-4317-a550-4be57d706896",
              "metric": 1,
              "bandwidth": 0,
              "dest-node-id": "d52e6bb0-93aa-4bf8-88cf-f24b11651a88",
              "src-port-id": "7cb2dda8-3cd2-4d84-996a-1d61aa0358a5"
            },
            {
              "link-id": "ad1e1487-59a5-4fc1-8680-7a1e4d1aaf1a",
              "src-node-id": "d52e6bb0-93aa-4bf8-88cf-f24b11651a88",
              "delay": 0,
              "dest-port-id": "3ccecbc5-6871-448e-bfe7-7da0d8d617da",
              "metric": 1,
              "bandwidth": 0,
              "dest-node-id": "bfc9341e-52d1-4e00-a992-110140be11b4",
              "src-port-id": "c04b0c95-b19b-4d29-962d-bb88ee271703"
            },
            {
              "link-id": "3909140b-cd9d-4dce-a82f-a8b16d33f20b",
              "src-node-id": "bfc9341e-52d1-4e00-a992-110140be11b4",
              "delay": 0,
              "dest-port-id": "32ea562d-5ba2-4c84-8560-9f0b4f8dc3ae",
              "metric": 2,
              "bandwidth": 0,
              "dest-node-id": "2698e79a-4a9e-4b9a-ae17-a5698a999bc9",
              "src-port-id": "57c413cd-8b84-4eb9-80d1-b67b3b187687"
            },
            {
              "link-id": "f24926ee-586f-4434-a56c-b0e77c2b6031",
              "src-node-id": "bfc9341e-52d1-4e00-a992-110140be11b4",
              "delay": 0,
              "dest-port-id": "c04b0c95-b19b-4d29-962d-bb88ee271703",
              "metric": 1,
              "bandwidth": 0,
              "dest-node-id": "d52e6bb0-93aa-4bf8-88cf-f24b11651a88",
              "src-port-id": "3ccecbc5-6871-448e-bfe7-7da0d8d617da"
            },
            {
              "link-id": "866bf38c-8a6b-4c73-8d7b-74b02797171d",
              "src-node-id": "e280be82-6354-4cd3-b8ca-7a41e7acc448",
              "delay": 0,
              "dest-port-id": "ee08bce2-0d1b-45f7-947c-73fa82175ee8",
              "metric": 1,
              "bandwidth": 128,
              "dest-node-id": "d52e6bb0-93aa-4bf8-88cf-f24b11651a88",
              "src-port-id": "01a637dd-55c9-4d96-8729-91538346001f"
            },
            {
              "link-id": "16e4dfd8-74e4-44ba-8a7d-0ddba8e00b52",
              "src-node-id": "868cf78a-54b7-495d-9ae7-a8fa3f2c7883",
              "delay": 0,
              "dest-port-id": "58efb0ef-eb99-4afe-bfaa-ee1d6f98e235",
              "metric": 1,
              "bandwidth": 0,
              "dest-node-id": "520bdfdc-ecd4-4a3b-ab64-7bdd5f178add",
              "src-port-id": "57c62f69-536a-4628-8d03-a3bf230ac607"
            },
            {
              "link-id": "8295f474-0788-4431-ab0d-124b0c3d0b5f",
              "src-node-id": "bfc9341e-52d1-4e00-a992-110140be11b4",
              "delay": 0,
              "dest-port-id": "f6171160-ef25-45e9-8251-7a44468a9a68",
              "metric": 0,
              "bandwidth": 0,
              "dest-node-id": "b0cec72b-376f-47f4-b7ae-44c0eb10d491",
              "src-port-id": "7ac662fe-1684-445e-9253-9b55a3cbd03c"
            },
            {
              "link-id": "63d55804-3204-4119-8187-7bd794061986",
              "src-node-id": "2698e79a-4a9e-4b9a-ae17-a5698a999bc9",
              "delay": 0,
              "dest-port-id": "57c413cd-8b84-4eb9-80d1-b67b3b187687",
              "metric": 2,
              "bandwidth": 0,
              "dest-node-id": "bfc9341e-52d1-4e00-a992-110140be11b4",
              "src-port-id": "32ea562d-5ba2-4c84-8560-9f0b4f8dc3ae"
            },
            {
              "link-id": "92e5487b-ef90-4769-9ca8-10f60ab43c83",
              "src-node-id": "d52e6bb0-93aa-4bf8-88cf-f24b11651a88",
              "delay": 0,
              "dest-port-id": "01a637dd-55c9-4d96-8729-91538346001f",
              "metric": 1,
              "bandwidth": 128,
              "dest-node-id": "e280be82-6354-4cd3-b8ca-7a41e7acc448",
              "src-port-id": "ee08bce2-0d1b-45f7-947c-73fa82175ee8"
            },
            {
              "link-id": "fad38c1a-153b-4814-bf12-8d40887808b3",
              "src-node-id": "d52e6bb0-93aa-4bf8-88cf-f24b11651a88",
              "delay": 0,
              "dest-port-id": "7cb2dda8-3cd2-4d84-996a-1d61aa0358a5",
              "metric": 1,
              "bandwidth": 0,
              "dest-node-id": "b0cec72b-376f-47f4-b7ae-44c0eb10d491",
              "src-port-id": "5f965841-2e92-4317-a550-4be57d706896"
            },
            {
              "link-id": "5467514b-9598-4767-8c76-f8e9d23a69b3",
              "src-node-id": "d52e6bb0-93aa-4bf8-88cf-f24b11651a88",
              "delay": 0,
              "dest-port-id": "9fb8376a-f286-489a-bde4-d5adde1c28c8",
              "metric": 0,
              "bandwidth": 0,
              "dest-node-id": "868cf78a-54b7-495d-9ae7-a8fa3f2c7883",
              "src-port-id": "81e7f41a-be2c-4986-ac1d-11fbd4062be4"
            },
            {
              "link-id": "316d4aa1-88ff-4b4b-bff2-fa8089f04e83",
              "src-node-id": "b0cec72b-376f-47f4-b7ae-44c0eb10d491",
              "delay": 0,
              "dest-port-id": "707d56c8-4aee-4466-9383-503ecedd31c3",
              "metric": 2,
              "bandwidth": 0,
              "dest-node-id": "2698e79a-4a9e-4b9a-ae17-a5698a999bc9",
              "src-port-id": "f7782068-8bf2-4ffb-a432-447a5cb708c4"
            },
            {
              "link-id": "ae7131f1-7207-4693-a149-e497738b1081",
              "src-node-id": "2698e79a-4a9e-4b9a-ae17-a5698a999bc9",
              "delay": 0,
              "dest-port-id": "c3ea674e-3dfb-492b-98d4-94b5f3b826e5",
              "metric": 1,
              "bandwidth": 0,
              "dest-node-id": "d52e6bb0-93aa-4bf8-88cf-f24b11651a88",
              "src-port-id": "34bca859-1b88-4d5a-b564-7d9080063609"
            }
          ]
        },
        "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
        "virtual-routes": {},
        "virtual-paths": {}
      }
    ]
  }
}
var VnMappingJson={
  "intent-vn-mapping-results": {
    "user-intent-vn-mapping": [
      {
        "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
        "intent-vn-mapping-result": [
          {
            "intent-id": "94a6fb90-b425-4ffd-9515-c0684aa4c37f",
            "intent-type": "node",
            "virtual-resource": [
              {
                "virtual-resource-id": "af948b19-9636-461f-8a46-4d7d340d17e5",
                "parent-virtual-resource-entity-id": "e280be82-6354-4cd3-b8ca-7a41e7acc448",
                "virtual-resource-type": "vport",
                "order": 0,
                "virtual-resource-entity-id": "1b10a84d-247c-40b7-b4e6-6e3e0b140cda"
              }
            ]
          },
          {
            "intent-id": "6c787caa-156a-49ed-8546-547bdccf283c",
            "intent-type": "node",
            "virtual-resource": [
              {
                "virtual-resource-id": "88d1b2f5-f3f6-4060-ab94-f99ff2db1ca1",
                "parent-virtual-resource-entity-id": "bfc9341e-52d1-4e00-a992-110140be11b4",
                "virtual-resource-type": "vport",
                "order": 0,
                "virtual-resource-entity-id": "78baf6e4-5df3-45b2-be20-107844d71d87"
              }
            ]
          },
          {
            "intent-id": "e0d56fee-7235-4748-a2a1-eb5e3733d866",
            "intent-type": "connection",
            "virtual-resource": [
              {
                "virtual-resource-id": "a4cdf6b5-adcf-4533-b7b0-aed75fa32bbb",
                "virtual-resource-type": "vlink",
                "order": 0,
                "virtual-resource-entity-id": "c00198e9-8b1c-4f12-84f2-5ddc5bbee995"
              },
              {
                "virtual-resource-id": "f4025170-a20b-4a7f-aad1-1f1d4c4c4b4c",
                "virtual-resource-type": "vlink",
                "order": 0,
                "virtual-resource-entity-id": "b19b3f5c-0fb3-4dca-98b5-5136b406e988"
              }
            ]
          },
          {
            "intent-id": "b46cfa7f-93a3-43f4-ac20-09307c75feca",
            "intent-type": "node",
            "virtual-resource": [
              {
                "virtual-resource-id": "293c7f66-8a22-4a20-8a91-0fe672431436",
                "virtual-resource-type": "vnode",
                "order": 0,
                "virtual-resource-entity-id": "868cf78a-54b7-495d-9ae7-a8fa3f2c7883"
              }
            ]
          },
          {
            "intent-id": "d463232f-363f-491c-a6f5-097ed0a794d3",
            "intent-type": "node",
            "virtual-resource": [
              {
                "virtual-resource-id": "a267bedc-fb63-4c5b-9862-87794345695c",
                "parent-virtual-resource-entity-id": "80213cce-4e1b-4b32-8d71-e2d68fedc50d",
                "virtual-resource-type": "vport",
                "order": 0,
                "virtual-resource-entity-id": "89f43bd8-e6f7-4aea-9fb2-936f6255bf4b"
              }
            ]
          },
          {
            "intent-id": "b49e3960-c08d-4fff-b9fc-08b65ebcde2c",
            "intent-type": "connection",
            "virtual-resource": [
              {
                "virtual-resource-id": "6bd8c604-dbb7-473b-85eb-4e6df01fa17b",
                "virtual-resource-type": "vlink",
                "order": 0,
                "virtual-resource-entity-id": "8cb78a59-146d-48f6-90e0-6fda9165a901"
              },
              {
                "virtual-resource-id": "39b09f4c-eaeb-4a87-a1ee-bf209926328b",
                "virtual-resource-type": "vlink",
                "order": 0,
                "virtual-resource-entity-id": "5467514b-9598-4767-8c76-f8e9d23a69b3"
              }
            ]
          },
          {
            "intent-id": "30da6667-608e-4d2f-bb50-79e5cabcc523",
            "intent-type": "connection",
            "virtual-resource": [
              {
                "virtual-resource-id": "a7997c59-6880-479d-a50a-7a00ee21ed01",
                "virtual-resource-type": "vlink",
                "order": 0,
                "virtual-resource-entity-id": "866bf38c-8a6b-4c73-8d7b-74b02797171d"
              },
              {
                "virtual-resource-id": "be484e6c-7f80-4d70-b244-d394065e98e1",
                "virtual-resource-type": "vlink",
                "order": 0,
                "virtual-resource-entity-id": "92e5487b-ef90-4769-9ca8-10f60ab43c83"
              }
            ]
          },
          {
            "intent-id": "1eaf9a67-a171-42a8-9282-71cf702f61dd",
            "intent-type": "node",
            "virtual-resource": [
              {
                "virtual-resource-id": "5dc78465-e253-430d-bffc-c4c62ea8159a",
                "parent-virtual-resource-entity-id": "2698e79a-4a9e-4b9a-ae17-a5698a999bc9",
                "virtual-resource-type": "vport",
                "order": 0,
                "virtual-resource-entity-id": "c0284004-7b36-4bda-ab98-3f33a67655db"
              }
            ]
          },
          {
            "intent-id": "175425f7-c9c9-474a-962c-70cb6c180d4d",
            "intent-type": "node",
            "virtual-resource": [
              {
                "virtual-resource-id": "52eace82-2f94-4d1d-b135-1a2fb928b741",
                "virtual-resource-type": "vnode",
                "order": 0,
                "virtual-resource-entity-id": "d52e6bb0-93aa-4bf8-88cf-f24b11651a88"
              }
            ]
          },
          {
            "intent-id": "7b796915-adf4-4356-b5ca-de005ac410c1",
            "intent-type": "node",
            "virtual-resource": [
              {
                "virtual-resource-id": "acfe47d3-274a-4ce5-bcd9-3a8e947ef666",
                "parent-virtual-resource-entity-id": "520bdfdc-ecd4-4a3b-ab64-7bdd5f178add",
                "virtual-resource-type": "vport",
                "order": 0,
                "virtual-resource-entity-id": "2ec42067-0694-497f-8fd6-142d5e1e2820"
              }
            ]
          },
          {
            "intent-id": "22282cca-9a13-4d0c-a67e-a933ebb0b0ae",
            "intent-type": "node",
            "virtual-resource": [
              {
                "virtual-resource-id": "dcba1206-0961-4571-89a5-a96bcdce2e50",
                "parent-virtual-resource-entity-id": "b0cec72b-376f-47f4-b7ae-44c0eb10d491",
                "virtual-resource-type": "vport",
                "order": 0,
                "virtual-resource-entity-id": "fe8a0153-2a95-4473-8b62-16a6542c9212"
              }
            ]
          }
        ],
        "virtual-network-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b"
      }
    ]
  }
}
var PnMappingJson={
  "vn-pn-mapping-results": {
    "user-vn-pn-mapping": [
      {
        "virtual-network-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b",
        "vn-pn-mapping-result": [
          {
            "virtual-resource-entity-id": "78baf6e4-5df3-45b2-be20-107844d71d87",
            "parent-physical-resource-entity-id": "openflow:2",
            "parent-virtual-resource-entity-id": "bfc9341e-52d1-4e00-a992-110140be11b4",
            "virtual-resource-id": "ad67bbcf-030a-44cb-8694-a25007ce05e3",
            "physical-resource-id": "e8135c7c-a1e0-41c0-843b-9c2d4bd87761",
            "physical-resource-entity-id": "openflow:2:1",
            "physical-resource-type": "port",
            "virtual-resource-type": "vport"
          },
          {
            "virtual-resource-entity-id": "fad38c1a-153b-4814-bf12-8d40887808b3",
            "virtual-resource-id": "01b5783e-db6e-4103-83c9-2445f5a72752",
            "physical-resource-id": "f856a1c9-9c1d-4e9f-873e-1a4543b195f0",
            "physical-resource-entity-id": "c231b5a3-7752-4f78-b8eb-b023ad4bea2f",
            "physical-resource-type": "path",
            "virtual-resource-type": "vlink"
          },
          {
            "virtual-resource-entity-id": "fe8a0153-2a95-4473-8b62-16a6542c9212",
            "parent-physical-resource-entity-id": "openflow:2",
            "parent-virtual-resource-entity-id": "b0cec72b-376f-47f4-b7ae-44c0eb10d491",
            "virtual-resource-id": "e8121faa-0109-4cec-89ef-54a3bfcd78ff",
            "physical-resource-id": "b1682914-fb2b-4bbb-b5a0-a9bd43fbfa3e",
            "physical-resource-entity-id": "openflow:2:2",
            "physical-resource-type": "port",
            "virtual-resource-type": "vport"
          },
          {
            "virtual-resource-entity-id": "80213cce-4e1b-4b32-8d71-e2d68fedc50d",
            "virtual-resource-id": "fa7d0f51-3a65-46a6-a428-a1d6fc927c1f",
            "physical-resource-id": "f992d18f-1d8a-43fb-9b4f-e2c7d63210e8",
            "physical-resource-entity-id": "openflow:3",
            "physical-resource-type": "node",
            "virtual-resource-type": "vnode"
          },
          {
            "virtual-resource-entity-id": "5467514b-9598-4767-8c76-f8e9d23a69b3",
            "virtual-resource-id": "f702bebc-725a-41c2-89e4-06bcde838282",
            "physical-resource-id": "a1836436-17ff-495f-be9a-39e44bb77e26",
            "physical-resource-entity-id": "3b9d1480-df24-4b0b-ba58-8c92605fe917",
            "physical-resource-type": "path",
            "virtual-resource-type": "vlink"
          },
          {
            "virtual-resource-entity-id": "6632b4c7-a794-4e14-a2c7-872e81277d94",
            "virtual-resource-id": "9b3fe3f6-2959-460c-a354-7d3ccc571554",
            "physical-resource-id": "2d70ca54-9b42-47dc-8632-0c69c1e72e01",
            "physical-resource-entity-id": "cb9b13ad-0172-43f5-9678-69859698071d",
            "physical-resource-type": "path",
            "virtual-resource-type": "vlink"
          },
          {
            "virtual-resource-entity-id": "3909140b-cd9d-4dce-a82f-a8b16d33f20b",
            "virtual-resource-id": "6b6250dd-2f79-424a-b6d0-4a80b8059e20",
            "physical-resource-id": "f43d01be-ea31-40d1-9263-f019f78ccf12",
            "physical-resource-entity-id": "5fc169b2-1209-4663-92c3-031100923f6f",
            "physical-resource-type": "path",
            "virtual-resource-type": "vlink"
          },
          {
            "virtual-resource-entity-id": "520bdfdc-ecd4-4a3b-ab64-7bdd5f178add",
            "virtual-resource-id": "6b91164b-81fd-4781-9042-d09ee6eac4da",
            "physical-resource-id": "39f2ba50-661b-484a-91b2-53a845bbe0f2",
            "physical-resource-entity-id": "openflow:1",
            "physical-resource-type": "node",
            "virtual-resource-type": "vnode"
          },
          {
            "virtual-resource-entity-id": "1b10a84d-247c-40b7-b4e6-6e3e0b140cda",
            "parent-physical-resource-entity-id": "openflow:4",
            "parent-virtual-resource-entity-id": "e280be82-6354-4cd3-b8ca-7a41e7acc448",
            "virtual-resource-id": "0b34cbe8-20ab-49f6-b717-b9e878670581",
            "physical-resource-id": "dded9100-be4c-4fb9-9e70-656bf3845309",
            "physical-resource-entity-id": "openflow:4:2",
            "physical-resource-type": "port",
            "virtual-resource-type": "vport"
          },
          {
            "virtual-resource-entity-id": "bfc9341e-52d1-4e00-a992-110140be11b4",
            "virtual-resource-id": "98787bce-8bad-480b-a786-47932246ec4f",
            "physical-resource-id": "f0c5e55f-2256-4e01-b8b7-6fea603c90ba",
            "physical-resource-entity-id": "openflow:2",
            "physical-resource-type": "node",
            "virtual-resource-type": "vnode"
          },
          {
            "virtual-resource-entity-id": "b0cec72b-376f-47f4-b7ae-44c0eb10d491",
            "virtual-resource-id": "4f7de648-9a9a-4e4a-ac41-01e6b2ff146a",
            "physical-resource-id": "e50ff1b1-e386-45d8-8ff3-70edb1cd4dbf",
            "physical-resource-entity-id": "openflow:2",
            "physical-resource-type": "node",
            "virtual-resource-type": "vnode"
          },
          {
            "virtual-resource-entity-id": "316d4aa1-88ff-4b4b-bff2-fa8089f04e83",
            "virtual-resource-id": "072ec5ca-4e7c-47ab-934b-709de978b85c",
            "physical-resource-id": "3deee815-b701-4a21-b65d-d1d55ff755ef",
            "physical-resource-entity-id": "ba0726d2-c72f-4b3e-b972-72da39430537",
            "physical-resource-type": "path",
            "virtual-resource-type": "vlink"
          },
          {
            "virtual-resource-entity-id": "866bf38c-8a6b-4c73-8d7b-74b02797171d",
            "virtual-resource-id": "a368c2b2-8bbb-4927-8966-bb4e02e8258e",
            "physical-resource-id": "5a9223b1-3a6a-4dbb-9f34-d3b1449f9dae",
            "physical-resource-entity-id": "6657f46b-edd2-47fe-b3fc-7b5aa16c7b97",
            "physical-resource-type": "path",
            "virtual-resource-type": "vlink"
          },
          {
            "virtual-resource-entity-id": "f24926ee-586f-4434-a56c-b0e77c2b6031",
            "virtual-resource-id": "72f002b8-3dab-4c04-ad66-646453f62337",
            "physical-resource-id": "23686c36-42b7-4ece-a626-c497f8817a93",
            "physical-resource-entity-id": "f84b9fda-b3f6-4631-a829-5089274093b0",
            "physical-resource-type": "path",
            "virtual-resource-type": "vlink"
          },
          {
            "virtual-resource-entity-id": "110d2527-b282-4126-932f-8c2b9d728f82",
            "virtual-resource-id": "145fea68-5f44-4732-91da-91f0db169e49",
            "physical-resource-id": "f62b400e-c849-4c18-8a2f-3b2a841ff7e4",
            "physical-resource-entity-id": "aab18b50-3bf6-4fde-aac1-968ffe22f518",
            "physical-resource-type": "path",
            "virtual-resource-type": "vlink"
          },
          {
            "virtual-resource-entity-id": "c0284004-7b36-4bda-ab98-3f33a67655db",
            "parent-physical-resource-entity-id": "openflow:1",
            "parent-virtual-resource-entity-id": "2698e79a-4a9e-4b9a-ae17-a5698a999bc9",
            "virtual-resource-id": "b8ab2d49-ff0c-4891-87a6-1980ad83c7c3",
            "physical-resource-id": "211d94d3-c4c7-40b7-9c2e-90ff5a4d976a",
            "physical-resource-entity-id": "openflow:1:2",
            "physical-resource-type": "port",
            "virtual-resource-type": "vport"
          },
          {
            "virtual-resource-entity-id": "b19b3f5c-0fb3-4dca-98b5-5136b406e988",
            "virtual-resource-id": "b5dc7980-2332-47d4-80d2-0737f28639d2",
            "physical-resource-id": "8bca1f44-3f1f-4271-8079-dd854c9702d2",
            "physical-resource-entity-id": "847ce4e6-d631-4d90-b2f3-dcfd56dbce3a",
            "physical-resource-type": "path",
            "virtual-resource-type": "vlink"
          },
          {
            "virtual-resource-entity-id": "8295f474-0788-4431-ab0d-124b0c3d0b5f",
            "virtual-resource-id": "04a08cc1-af4a-4fbb-90f4-b964b9d07abd",
            "physical-resource-id": "fa3d22e1-7957-4e7f-86d5-4cb12f035620",
            "physical-resource-entity-id": "e0f22266-e0c2-4467-b143-800a7b212a98",
            "physical-resource-type": "path",
            "virtual-resource-type": "vlink"
          },
          {
            "virtual-resource-entity-id": "d52e6bb0-93aa-4bf8-88cf-f24b11651a88",
            "virtual-resource-id": "0c9f60d7-a7e1-423c-88bf-28fa793ecb00",
            "physical-resource-id": "d175b146-c4fc-4069-82b9-853131e8023f",
            "physical-resource-entity-id": "openflow:3",
            "physical-resource-type": "node",
            "virtual-resource-type": "vnode"
          },
          {
            "virtual-resource-entity-id": "8b2218cf-fbbb-4e14-85ca-694ceac1edfb",
            "virtual-resource-id": "c6078cf6-9ed4-4046-b994-4dc4e0b88dd5",
            "physical-resource-id": "86d6d843-7f35-49e1-a344-0c602017ac5d",
            "physical-resource-entity-id": "eaa935ac-c511-4541-80cd-4a1640ae2246",
            "physical-resource-type": "path",
            "virtual-resource-type": "vlink"
          },
          {
            "virtual-resource-entity-id": "ae7131f1-7207-4693-a149-e497738b1081",
            "virtual-resource-id": "b34e2039-44f1-458e-a731-6061bbfc76bb",
            "physical-resource-id": "25dad0dc-0b3e-4dca-83b9-ca1118ae9ff1",
            "physical-resource-entity-id": "61c4597d-02a5-4609-8756-955024691a17",
            "physical-resource-type": "path",
            "virtual-resource-type": "vlink"
          },
          {
            "virtual-resource-entity-id": "98d7b523-9532-4974-825e-c91aa402fe28",
            "virtual-resource-id": "9d3cc16c-ce20-484a-8d2f-b083ed077ef7",
            "physical-resource-id": "56b4a39c-c1c3-4eba-9b80-1e37948e6461",
            "physical-resource-entity-id": "765e80be-6232-498d-b095-3b2c59448535",
            "physical-resource-type": "path",
            "virtual-resource-type": "vlink"
          },
          {
            "virtual-resource-entity-id": "868cf78a-54b7-495d-9ae7-a8fa3f2c7883",
            "virtual-resource-id": "33393811-e461-4d55-aeda-837cdafad7c5",
            "physical-resource-id": "e001fe0a-e035-4106-8dda-883445e15352",
            "physical-resource-entity-id": "openflow:3",
            "physical-resource-type": "node",
            "virtual-resource-type": "vnode"
          },
          {
            "virtual-resource-entity-id": "92e5487b-ef90-4769-9ca8-10f60ab43c83",
            "virtual-resource-id": "0ce90b95-dbc5-40fe-aa62-0609b9dee100",
            "physical-resource-id": "c0a078ad-c7dc-4aad-80a1-4d7fad5f028e",
            "physical-resource-entity-id": "4fdc5a09-ceca-461a-b3d3-1c3a9506dc74",
            "physical-resource-type": "path",
            "virtual-resource-type": "vlink"
          },
          {
            "virtual-resource-entity-id": "4b591338-e82f-4b8b-a7c4-afb241549455",
            "virtual-resource-id": "f3f73508-a241-4a76-af48-7bd706cfd4ea",
            "physical-resource-id": "7041697c-fe57-4baa-8548-2412d41a830b",
            "physical-resource-entity-id": "23f80b16-76d1-424c-9a71-aacd60a62bcd",
            "physical-resource-type": "path",
            "virtual-resource-type": "vlink"
          },
          {
            "virtual-resource-entity-id": "16e4dfd8-74e4-44ba-8a7d-0ddba8e00b52",
            "virtual-resource-id": "e668751f-13b0-4816-80b1-643bd08130db",
            "physical-resource-id": "1a328854-19ca-4331-b1cd-f8b915c9553f",
            "physical-resource-entity-id": "a710b9b8-5abf-4c59-9afd-88ccf8d935b0",
            "physical-resource-type": "path",
            "virtual-resource-type": "vlink"
          },
          {
            "virtual-resource-entity-id": "e280be82-6354-4cd3-b8ca-7a41e7acc448",
            "virtual-resource-id": "9bd4be7b-98ea-4f08-ac8c-d81880fe7727",
            "physical-resource-id": "f03f81e3-87ff-40bd-99ce-7ade5df4bae5",
            "physical-resource-entity-id": "openflow:4",
            "physical-resource-type": "node",
            "virtual-resource-type": "vnode"
          },
          {
            "virtual-resource-entity-id": "63d55804-3204-4119-8187-7bd794061986",
            "virtual-resource-id": "479e807e-138e-45ba-a02b-76e75ee5482c",
            "physical-resource-id": "6010e2df-e031-4f50-8f56-f152347ddda5",
            "physical-resource-entity-id": "7b875b56-ce40-4fcd-ac76-b361d0311d8f",
            "physical-resource-type": "path",
            "virtual-resource-type": "vlink"
          },
          {
            "virtual-resource-entity-id": "c00198e9-8b1c-4f12-84f2-5ddc5bbee995",
            "virtual-resource-id": "14e197fa-0c67-427c-bfb9-da053aa4bf62",
            "physical-resource-id": "ba0cda05-e69b-4f43-bfcd-6ea46dc95775",
            "physical-resource-entity-id": "4ce046d2-0c20-43c7-b7f8-fa441e07d443",
            "physical-resource-type": "path",
            "virtual-resource-type": "vlink"
          },
          {
            "virtual-resource-entity-id": "2ec42067-0694-497f-8fd6-142d5e1e2820",
            "parent-physical-resource-entity-id": "openflow:1",
            "parent-virtual-resource-entity-id": "520bdfdc-ecd4-4a3b-ab64-7bdd5f178add",
            "virtual-resource-id": "87ead73f-ac56-473b-9de2-5e2755a781c2",
            "physical-resource-id": "2fc96459-bd96-4010-ab12-4bb81b6639ed",
            "physical-resource-entity-id": "openflow:1:1",
            "physical-resource-type": "port",
            "virtual-resource-type": "vport"
          },
          {
            "virtual-resource-entity-id": "89f43bd8-e6f7-4aea-9fb2-936f6255bf4b",
            "parent-physical-resource-entity-id": "openflow:3",
            "parent-virtual-resource-entity-id": "80213cce-4e1b-4b32-8d71-e2d68fedc50d",
            "virtual-resource-id": "52101662-9a12-4e67-b35e-3389e0e19faf",
            "physical-resource-id": "e4e39b0b-3e94-49e2-9056-af969c866a2a",
            "physical-resource-entity-id": "openflow:3:4",
            "physical-resource-type": "port",
            "virtual-resource-type": "vport"
          },
          {
            "virtual-resource-entity-id": "ad1e1487-59a5-4fc1-8680-7a1e4d1aaf1a",
            "virtual-resource-id": "514d8aa7-df8a-4fbe-83a7-596663b67f6d",
            "physical-resource-id": "7b88b0b2-af4b-4de4-892c-52ec34103ed7",
            "physical-resource-entity-id": "bdd1e0cb-72aa-4f04-a2db-a07a15c62d61",
            "physical-resource-type": "path",
            "virtual-resource-type": "vlink"
          },
          {
            "virtual-resource-entity-id": "8cb78a59-146d-48f6-90e0-6fda9165a901",
            "virtual-resource-id": "62e24268-8d6b-4832-baa3-0756ccd16140",
            "physical-resource-id": "e6d0bdcb-6b5a-449c-97cd-aceeb58ce72f",
            "physical-resource-entity-id": "cb3de13d-cd6f-4f32-9596-09ddbb6d1c44",
            "physical-resource-type": "path",
            "virtual-resource-type": "vlink"
          },
          {
            "virtual-resource-entity-id": "2698e79a-4a9e-4b9a-ae17-a5698a999bc9",
            "virtual-resource-id": "48cf54ae-11e7-46bd-a1e0-2747853d2e1f",
            "physical-resource-id": "5af260e5-7b06-4552-94d1-34643d4200df",
            "physical-resource-entity-id": "openflow:1",
            "physical-resource-type": "node",
            "virtual-resource-type": "vnode"
          }
        ],
        "user-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b"
      }
    ]
  }
}