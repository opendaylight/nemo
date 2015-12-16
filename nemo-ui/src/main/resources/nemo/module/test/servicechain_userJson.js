var keyWords=['Create','Import','Host','Node','Connection','Flow','Operation','Type','Endnodes','Property','Contain','Match','Target','Action'];
var userinfo ={
  "users": {
    "user": [
      {
        "user-id": "af4fc2be-e3f4-4388-a8ef-3aabae872f2b",
        "user-role": "tenant",
        "user-password": "abcd",
        "user-name": "user1",
        "objects": {
          "node": [
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
                        "value": "192.168.11.0/24",
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
              "node-name": "headquarter"
            },
            {
              "node-id": "41ee9aad-5f61-469d-99a9-e691d2a1de05",
              "node-type": "chain-group",
              "node-name": "chain1",
              "sub-node": [
                {
                  "node-id": "c2cd9de6-ab25-4d3f-bff2-c4d785a41995",
                  "order": 0
                },
                {
                  "node-id": "a5a96dc7-51dd-44a5-802b-7e67a309fb36",
                  "order": 1
                }
              ]
            },
            {
              "node-id": "c2cd9de6-ab25-4d3f-bff2-c4d785a41995",
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
              "node-name": "fw1"
            },
            {
              "node-id": "b46cfa7f-93a3-43f4-ac20-09307c75feca",
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
                        "value": "192.168.12.0/24",
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
              "node-name": "branch"
            },
            {
              "node-id": "a5a96dc7-51dd-44a5-802b-7e67a309fb36",
              "property": [
                {
                  "property-name": "location",
                  "property-values": {
                    "string-value": [
                      {
                        "value": "openflow:2:2",
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
              "node-type": "cache",
              "node-name": "cache2"
            }
          ],
          "connection": [
            {
              "connection-id": "fed2b570-2e80-4914-a5af-040594b651b9",
              "connection-type": "p2p",
              "end-node": [
                {
                  "node-id": "41ee9aad-5f61-469d-99a9-e691d2a1de05",
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
              "connection-id": "7175bac3-b785-2278-90ed-613480e354e8",
              "connection-type": "p2p",
              "end-node": [
                {
                  "node-id": "94a6fb90-b425-4ffd-9515-c0684aa4c37f",
                  "order": 0
                },
                {
                  "node-id": "b46cfa7f-93a3-43f4-ac20-09307c75feca",
                  "order": 0
                }
              ],
              "connection-name": "c1"
            },
            {
              "connection-id": "9397bac3-d9a7-449a-b20f-8356a2f3760a",
              "connection-type": "p2p",
              "end-node": [
                {
                  "node-id": "94a6fb90-b425-4ffd-9515-c0684aa4c37f",
                  "order": 0
                },
                {
                  "node-id": "41ee9aad-5f61-469d-99a9-e691d2a1de05",
                  "order": 0
                }
              ],
              "connection-name": "c2"
            }
          ],
          "flow": [
            {
              "flow-id": "cf48eeee-882e-435a-adf4-ea22ba88331f",
              "match-item": [
                {
                  "match-item-name": "dst-ip",
                  "match-item-value": {
                    "string-value": "192.168.11.0/24"
                  }
                },
                {
                  "match-item-name": "src-ip",
                  "match-item-value": {
                    "string-value": "192.168.12.0/24"
                  }
                }
              ],
              "flow-name": "f1"
            }
          ]
        },
        "operations": {
          "operation": [
            {
              "operation-id": "619ee3bb-1e40-480f-b0fa-a331820a5518",
              "priority": 0,
              "action": [
                {
                  "action-name": "go-through",
                  "order": 0,
                  "parameter-values": {
                    "string-value": [
                      {
                        "value": "41ee9aad-5f61-469d-99a9-e691d2a1de05",
                        "order": 0
                      }
                    ]
                  }
                }
              ],
              "target-object": "cf48eeee-882e-435a-adf4-ea22ba88331f",
              "operation-name": "o1"
            }
          ]
        }
      }
    ]
  }
}
//console.log(userinfo);
var physicalJson= {
  "physical-network": {
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
              "mac-address": "D2:69:26:45:3C:B4",
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
              "mac-address": "82:F5:6A:E7:C2:51",
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
              "mac-address": "7E:BD:57:AB:AD:12",
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
              "mac-address": "00:0C:29:98:7E:1D",
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
              "mac-address": "00:0C:29:98:7E:27",
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
              "mac-address": "16:4E:17:55:69:EF",
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
          "node-type": "router",
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
              "mac-address": "00:0C:29:98:7E:09",
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
              "mac-address": "9E:A7:FD:52:0E:4F",
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
              "mac-address": "6E:93:61:CF:19:D0",
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
          "node-type": "router",
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
              "mac-address": "F2:23:F9:75:DA:F8",
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
              "mac-address": "6E:7E:E6:D8:CA:56",
              "bandwidth": 10240
            }
          ]
        }
      ]
    },
    "physical-links": {
      "physical-link": [
        {
          "link-id": "openflow:4:2",
          "loss-rate": 1,
          "delay": 1,
          "dest-port-id": "openflow:2:2",
          "src-port-id": "openflow:4:2",
          "dest-node-id": "openflow:2",
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
        },
        {
          "link-id": "openflow:2:2",
          "loss-rate": 1,
          "delay": 1,
          "dest-port-id": "openflow:4:2",
          "src-port-id": "openflow:2:2",
          "dest-node-id": "openflow:4",
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
          "path-id": "d26f3d08-dcd8-4bdb-b74e-d09cc5ab4090",
          "delay": 0,
          "physical-link": [
            {
              "link-id": "openflow:3:3",
              "order": 1
            },
            {
              "link-id": "openflow:2:3",
              "order": 0
            }
          ],
          "metric": 2,
          "bandwidth": 0
        },
        {
          "path-id": "0eec470f-ec46-4390-8c84-4c27ca013255",
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
          "path-id": "6065bd5d-5058-41ec-a901-92e184755b52",
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
          "path-id": "c8da2902-31d9-49f5-b7a8-ade64c497bea",
          "delay": 0,
          "physical-link": [
            {
              "link-id": "openflow:3:2",
              "order": 1
            },
            {
              "link-id": "openflow:4:1",
              "order": 0
            }
          ],
          "metric": 2,
          "bandwidth": 0
        },
        {
          "path-id": "ae567148-9747-4aa7-9fef-b08d282e4d17",
          "delay": 0,
          "physical-link": [
            {
              "link-id": "openflow:4:1",
              "order": 0
            }
          ],
          "metric": 1,
          "bandwidth": 0
        },
        {
          "path-id": "ae1b6b7a-2725-4f26-8b75-7c21a086a1c4",
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
          "path-id": "6592bf47-5953-442e-90d3-e2fde9221fd5",
          "delay": 0,
          "physical-link": [
            {
              "link-id": "openflow:3:3",
              "order": 0
            }
          ],
          "metric": 1,
          "bandwidth": 0
        },
        {
          "path-id": "8b9dbbc9-a895-4adc-9cca-29217c1fa1ff",
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
        "network-id": "af4fc2be-e3f4-4388-a8ef-3aabae872f2b",
        "virtual-nodes": {
          "virtual-node": [
            {
              "node-id": "43429643-c423-4d46-817d-092ac20d3b79",
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
                  "port-id": "1f0bf4a0-6857-47ec-b2af-2104ef1d0c79",
                  "external-ip-prefixes": {
                    "external-ip-prefix": [
                      "192.168.11.0/24"
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
                },
                {
                  "port-id": "41a4e6b1-019e-456c-bd99-8fc5f60c3a29",
                  "port-type": "internal",
                  "bandwidth": 0
                },
                {
                  "port-id": "6c494b58-fabf-48f7-8907-43850cb4607a",
                  "port-type": "internal"
                }
              ]
            },
            {
              "node-id": "ef802514-a373-4f24-a162-0553ffe13f09",
              "node-type": "vrouter",
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
                  "port-id": "a086ba37-a6b7-490a-a3cb-27a31378e096",
                  "port-type": "internal"
                },
                {
                  "port-id": "6208bb8d-1489-4aec-bf21-8702d8f69951",
                  "port-type": "internal"
                },
                {
                  "port-id": "32ddfaa1-1dd8-434a-8479-362d592521d1",
                  "external-ip-prefixes": {},
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
                }
              ]
            },
            {
              "node-id": "7f41bc57-f4ee-42cc-8fac-f25bda374be9",
              "node-type": "vrouter",
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
                  "port-id": "6285cebd-5d4a-4a1d-b732-20470240547a",
                  "port-type": "internal"
                },
                {
                  "port-id": "e8c938ca-f1d5-450d-a6ca-3cd81119c0ec",
                  "port-type": "internal"
                },
                {
                  "port-id": "e427cc5e-93c9-4f5d-adf6-a2dc2b50e57c",
                  "external-ip-prefixes": {},
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
            },
            {
              "node-id": "02f661f4-4097-4d2d-8986-5c17e74429ad",
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
                  "port-id": "5d0faf77-435c-4c19-a129-db6481aa40a5",
                  "port-type": "internal"
                },
                {
                  "port-id": "82712a15-b38f-459c-ae1e-be1e34566a2a",
                  "external-ip-prefixes": {
                    "external-ip-prefix": [
                      "192.168.12.0/24"
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
                },
                {
                  "port-id": "aca59fd4-8958-49e1-a290-a39529a8e699",
                  "port-type": "internal",
                  "bandwidth": 0
                }
              ]
            }
          ]
        },
        "virtual-arps": {},
        "virtual-links": {
          "virtual-link": [
            {
              "link-id": "7d28e3a4-32bc-4393-b4ac-b5cb3e88bddb",
              "src-node-id": "43429643-c423-4d46-817d-092ac20d3b79",
              "delay": 0,
              "dest-port-id": "a086ba37-a6b7-490a-a3cb-27a31378e096",
              "metric": 2,
              "bandwidth": 0,
              "dest-node-id": "ef802514-a373-4f24-a162-0553ffe13f09",
              "src-port-id": "6c494b58-fabf-48f7-8907-43850cb4607a"
            },
            {
              "link-id": "cdf567ba-f523-4ac1-a240-4db08bdc4091",
              "src-node-id": "ef802514-a373-4f24-a162-0553ffe13f09",
              "delay": 0,
              "dest-port-id": "6c494b58-fabf-48f7-8907-43850cb4607a",
              "metric": 2,
              "bandwidth": 0,
              "dest-node-id": "43429643-c423-4d46-817d-092ac20d3b79",
              "src-port-id": "a086ba37-a6b7-490a-a3cb-27a31378e096"
            },
            {
              "link-id": "c7f45b8c-a4eb-4b8c-9ecd-2330d9b61d36",
              "src-node-id": "7f41bc57-f4ee-42cc-8fac-f25bda374be9",
              "delay": 0,
              "dest-port-id": "6208bb8d-1489-4aec-bf21-8702d8f69951",
              "metric": 2,
              "bandwidth": 0,
              "dest-node-id": "ef802514-a373-4f24-a162-0553ffe13f09",
              "src-port-id": "e8c938ca-f1d5-450d-a6ca-3cd81119c0ec"
            },
            {
              "link-id": "9957beb9-e9a6-47fb-8c94-9be6d56bd427",
              "src-node-id": "ef802514-a373-4f24-a162-0553ffe13f09",
              "delay": 0,
              "dest-port-id": "e8c938ca-f1d5-450d-a6ca-3cd81119c0ec",
              "metric": 2,
              "bandwidth": 0,
              "dest-node-id": "7f41bc57-f4ee-42cc-8fac-f25bda374be9",
              "src-port-id": "6208bb8d-1489-4aec-bf21-8702d8f69951"
            },
            {
              "link-id": "20cd9c28-483e-4703-8adb-43326799c9a2",
              "src-node-id": "7f41bc57-f4ee-42cc-8fac-f25bda374be9",
              "delay": 0,
              "dest-port-id": "5d0faf77-435c-4c19-a129-db6481aa40a5",
              "metric": 1,
              "bandwidth": 0,
              "dest-node-id": "02f661f4-4097-4d2d-8986-5c17e74429ad",
              "src-port-id": "6285cebd-5d4a-4a1d-b732-20470240547a"
            },
            {
              "link-id": "82937d51-52b6-4334-8fe4-eac541e97684",
              "src-node-id": "02f661f4-4097-4d2d-8986-5c17e74429ad",
              "delay": 0,
              "dest-port-id": "6285cebd-5d4a-4a1d-b732-20470240547a",
              "metric": 1,
              "bandwidth": 0,
              "dest-node-id": "7f41bc57-f4ee-42cc-8fac-f25bda374be9",
              "src-port-id": "5d0faf77-435c-4c19-a129-db6481aa40a5"
            },
            {
              "link-id": "39ded017-fed2-41f8-b709-ae102ebd015d",
              "src-node-id": "02f661f4-4097-4d2d-8986-5c17e74429ad",
              "delay": 0,
              "dest-port-id": "41a4e6b1-019e-456c-bd99-8fc5f60c3a29",
              "metric": 1,
              "bandwidth": 0,
              "dest-node-id": "43429643-c423-4d46-817d-092ac20d3b79",
              "src-port-id": "aca59fd4-8958-49e1-a290-a39529a8e699"
            },
            {
              "link-id": "3b9cdd19-0c5e-4a99-ad48-a323e949a9a3",
              "src-node-id": "43429643-c423-4d46-817d-092ac20d3b79",
              "delay": 0,
              "dest-port-id": "aca59fd4-8958-49e1-a290-a39529a8e699",
              "metric": 1,
              "bandwidth": 0,
              "dest-node-id": "02f661f4-4097-4d2d-8986-5c17e74429ad",
              "src-port-id": "41a4e6b1-019e-456c-bd99-8fc5f60c3a29"
            }
          ]
        },
        "user-id": "af4fc2be-e3f4-4388-a8ef-3aabae872f2b",
        "virtual-routes": {},
        "virtual-paths": {
          "virtual-path": [
            {
              "path-id": "9e29b1b5-1627-488d-a5b4-8fe221235493",
              "virtual-link": [
                {
                  "link-id": "82937d51-52b6-4334-8fe4-eac541e97684",
                  "order": 0
                }
              ],
              "bandwidth": 0
            },
            {
              "path-id": "ff6042a3-bdfa-4f0e-8b17-336b3e2f3ecb",
              "virtual-link": [
                {
                  "link-id": "cdf567ba-f523-4ac1-a240-4db08bdc4091",
                  "order": 0
                }
              ],
              "bandwidth": 0
            },
            {
              "path-id": "a12799b8-523d-4ab2-8fbf-f356c5ad52eb",
              "virtual-link": [
                {
                  "link-id": "c7f45b8c-a4eb-4b8c-9ecd-2330d9b61d36",
                  "order": 0
                }
              ],
              "bandwidth": 0
            }
          ]
        }
      }
    ]
  }
}
var MappingJson={
  "intent-vn-mapping-results": {
    "user-intent-vn-mapping": [
      {
        "user-id": "af4fc2be-e3f4-4388-a8ef-3aabae872f2b",
        "intent-vn-mapping-result": [
          {
            "intent-id": "94a6fb90-b425-4ffd-9515-c0684aa4c37f",
            "intent-type": "node",
            "virtual-resource": [
              {
                "virtual-resource-id": "fa9a6915-b6d9-4248-87c4-3237bddd4489",
                "parent-virtual-resource-entity-id": "43429643-c423-4d46-817d-092ac20d3b79",
                "virtual-resource-type": "vport",
                "order": 0,
                "virtual-resource-entity-id": "1f0bf4a0-6857-47ec-b2af-2104ef1d0c79"
              }
            ]
          },
          {
            "intent-id": "b46cfa7f-93a3-43f4-ac20-09307c75feca",
            "intent-type": "node",
            "virtual-resource": [
              {
                "virtual-resource-id": "460f3011-6f1b-46d8-9fe7-0d5576ff592d",
                "parent-virtual-resource-entity-id": "02f661f4-4097-4d2d-8986-5c17e74429ad",
                "virtual-resource-type": "vport",
                "order": 0,
                "virtual-resource-entity-id": "82712a15-b38f-459c-ae1e-be1e34566a2a"
              }
            ]
          },
          {
            "intent-id": "7175bac3-b785-2278-90ed-613480e354e8",
            "intent-type": "connection",
            "virtual-resource": [
              {
                "virtual-resource-id": "0395de51-d926-4f1f-ba25-5977f1490d2e",
                "virtual-resource-type": "vlink",
                "order": 0,
                "virtual-resource-entity-id": "39ded017-fed2-41f8-b709-ae102ebd015d"
              },
              {
                "virtual-resource-id": "b303592e-f379-470f-af6f-c28937969bc3",
                "virtual-resource-type": "vlink",
                "order": 0,
                "virtual-resource-entity-id": "3b9cdd19-0c5e-4a99-ad48-a323e949a9a3"
              }
            ]
          },
          {
            "intent-id": "619ee3bb-1e40-480f-b0fa-a331820a5518",
            "intent-type": "operation",
            "virtual-resource": [
              {
                "virtual-resource-id": "eb289733-18c8-4601-8544-25e9f56045d0",
                "parent-virtual-resource-entity-id": "ef802514-a373-4f24-a162-0553ffe13f09",
                "virtual-resource-type": "vport",
                "order": 3,
                "virtual-resource-entity-id": "32ddfaa1-1dd8-434a-8479-362d592521d1"
              },
              {
                "virtual-resource-id": "11ee03c9-3bf6-421b-b783-2524fca560fd",
                "virtual-resource-type": "vpath",
                "order": 2,
                "virtual-resource-entity-id": "a12799b8-523d-4ab2-8fbf-f356c5ad52eb"
              },
              {
                "virtual-resource-id": "2c9b5484-bc1f-410e-9089-ac2ed0e74842",
                "virtual-resource-type": "vpath",
                "order": 4,
                "virtual-resource-entity-id": "ff6042a3-bdfa-4f0e-8b17-336b3e2f3ecb"
              },
              {
                "virtual-resource-id": "31d34ef8-08a3-45b2-af7c-31c2fff126e2",
                "parent-virtual-resource-entity-id": "7f41bc57-f4ee-42cc-8fac-f25bda374be9",
                "virtual-resource-type": "vport",
                "order": 1,
                "virtual-resource-entity-id": "e427cc5e-93c9-4f5d-adf6-a2dc2b50e57c"
              },
              {
                "virtual-resource-id": "80cfeef9-eb82-4f72-a1b0-852964d0f236",
                "virtual-resource-type": "vpath",
                "order": 0,
                "virtual-resource-entity-id": "9e29b1b5-1627-488d-a5b4-8fe221235493"
              }
            ]
          },
          {
            "intent-id": "c2cd9de6-ab25-4d3f-bff2-c4d785a41995",
            "intent-type": "node",
            "virtual-resource": [
              {
                "virtual-resource-id": "31d34ef8-08a3-45b2-af7c-31c2fff126e2",
                "parent-virtual-resource-entity-id": "7f41bc57-f4ee-42cc-8fac-f25bda374be9",
                "virtual-resource-type": "vport",
                "order": 0,
                "virtual-resource-entity-id": "e427cc5e-93c9-4f5d-adf6-a2dc2b50e57c"
              }
            ]
          },
          {
            "intent-id": "41ee9aad-5f61-469d-99a9-e691d2a1de05",
            "intent-type": "node",
            "virtual-resource": [
              {
                "virtual-resource-id": "eb289733-18c8-4601-8544-25e9f56045d0",
                "parent-virtual-resource-entity-id": "ef802514-a373-4f24-a162-0553ffe13f09",
                "virtual-resource-type": "vport",
                "order": 2,
                "virtual-resource-entity-id": "32ddfaa1-1dd8-434a-8479-362d592521d1"
              },
              {
                "virtual-resource-id": "11ee03c9-3bf6-421b-b783-2524fca560fd",
                "virtual-resource-type": "vpath",
                "order": 1,
                "virtual-resource-entity-id": "a12799b8-523d-4ab2-8fbf-f356c5ad52eb"
              },
              {
                "virtual-resource-id": "31d34ef8-08a3-45b2-af7c-31c2fff126e2",
                "parent-virtual-resource-entity-id": "7f41bc57-f4ee-42cc-8fac-f25bda374be9",
                "virtual-resource-type": "vport",
                "order": 0,
                "virtual-resource-entity-id": "e427cc5e-93c9-4f5d-adf6-a2dc2b50e57c"
              }
            ]
          },
          {
            "intent-id": "a5a96dc7-51dd-44a5-802b-7e67a309fb36",
            "intent-type": "node",
            "virtual-resource": [
              {
                "virtual-resource-id": "eb289733-18c8-4601-8544-25e9f56045d0",
                "parent-virtual-resource-entity-id": "ef802514-a373-4f24-a162-0553ffe13f09",
                "virtual-resource-type": "vport",
                "order": 0,
                "virtual-resource-entity-id": "32ddfaa1-1dd8-434a-8479-362d592521d1"
              }
            ]
          }
        ],
        "virtual-network-id": "af4fc2be-e3f4-4388-a8ef-3aabae872f2b"
      }
    ]
  }
}