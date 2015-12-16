var keyWords=['Create','Import','Host','Node','Connection','Flow','Operation','Type','Endnodes','Property','Contain','Match','Target','Action'];
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
      },
      {
        "user-id": "64af5353-9b5d-46e2-6bda-072c3a94a591",
        "user-role": "tenant",
        "user-password": "q",
        "user-name": "q"
      }
    ]
  }
}
//console.log(userinfo);
var physicalJson= {
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
              "mac-address": "22:37:1D:6B:94:E5",
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
              "mac-address": "46:8E:D7:44:14:CC",
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
              "mac-address": "02:34:9F:53:DD:05",
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
              "mac-address": "66:EC:EA:63:EC:AD",
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
              "mac-address": "C2:0D:AE:D8:3C:56",
              "bandwidth": 10240
            }
          ]
        },
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
              "mac-address": "4A:EB:B6:F8:E1:9C",
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
              "mac-address": "8A:0F:06:EA:40:07",
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
              "mac-address": "EA:76:A0:D3:8D:F8",
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
              "mac-address": "9A:E4:DE:B1:B2:11",
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
          "dest-port-id": "openflow:2:2",
          "src-port-id": "openflow:3:4",
          "dest-node-id": "openflow:2",
          "bandwidth": 10240,
          "src-node-id": "openflow:3"
        }
      ]
    },
    "physical-paths": {
      "physical-path": [
        {
          "path-id": "41068e63-c61d-414f-93b3-4109db13b39a",
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
          "path-id": "8c71bff9-7b13-4842-9109-1cda48865160",
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
          "path-id": "456ad088-8610-4154-bf14-6e1ad236edaa",
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
          "path-id": "5d9fd420-e20c-4ddd-97c7-0fe85d06ea17",
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
          "path-id": "d86e2725-b848-4ce8-904a-967f9e083577",
          "delay": 0,
          "metric": 0,
          "bandwidth": 0
        },
        {
          "path-id": "397a6c45-b87f-4e67-865a-7ca9f4b12975",
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
          "path-id": "e98cd5ca-361a-4b8d-a6a6-7088a8a8514d",
          "delay": 0,
          "metric": 0,
          "bandwidth": 0
        },
        {
          "path-id": "aefbb3a4-d71c-4bb3-8298-a4ec9123e973",
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
          "path-id": "b8dc0a4d-319a-49fd-92a0-ad8b70c58cb4",
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
          "path-id": "9559b3ce-356c-4989-b87f-023ddc39f15b",
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
          "path-id": "dffd6c72-39d6-43b5-95c0-139b7c06cb81",
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
          "path-id": "dea9decf-7713-44ad-9276-0c49624f8c20",
          "delay": 0,
          "metric": 0,
          "bandwidth": 0
        },
        {
          "path-id": "36768acc-2cdd-4e64-8db5-27b753a36e81",
          "delay": 0,
          "metric": 0,
          "bandwidth": 0
        },
        {
          "path-id": "264b59f1-3316-484a-bf5e-0d1412668a13",
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
          "path-id": "299eb64e-dbb4-4cc6-b1d7-bc694034052b",
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
          "path-id": "18ccd1d0-4d60-47f1-a5da-92dbf2cc5a24",
          "delay": 0,
          "metric": 0,
          "bandwidth": 0
        },
        {
          "path-id": "d181589e-d067-4b71-9991-ba26248e0cd4",
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
          "path-id": "fc0c1384-8c1e-4a44-815f-b2c9c2749bfc",
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
          "path-id": "eece4538-22ba-4876-bb0e-28b363f4ee0f",
          "delay": 0,
          "metric": 0,
          "bandwidth": 0
        },
        {
          "path-id": "1338e6bb-82b4-4ca8-973a-df77b5fff3ea",
          "delay": 0,
          "physical-link": [
            {
              "link-id": "openflow:3:1",
              "order": 0
            }
          ],
          "metric": 1,
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
              "node-id": "6e77a772-602a-4e23-9275-522dd8b2fccb",
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
                  "port-id": "2d56888f-d697-4758-9de1-53540476afac",
                  "port-type": "internal"
                },
                {
                  "port-id": "4a0307d7-0ca1-4e66-83d9-d6051c116bd9",
                  "port-type": "internal"
                },
                {
                  "port-id": "b553b789-7e7b-4577-ad7d-d40cdb54b525",
                  "port-type": "internal"
                },
                {
                  "port-id": "56d54ad9-1d55-455d-828c-add02f9cfbd3",
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
                }
              ]
            },
            {
              "node-id": "74766098-9f67-4be0-8b6d-b6011110198c",
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
                  "port-id": "19e72797-a78c-418c-8f99-1e2d4293f3e5",
                  "port-type": "internal",
                  "bandwidth": 0
                },
                {
                  "port-id": "9680299d-dc93-4ba0-aff4-20c43962b249",
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
              "node-id": "ba9e5273-2c47-43c1-a959-5779488ce1f8",
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
                  "port-id": "25ba99fd-8846-490b-bbe7-883f71914851",
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
                },
                {
                  "port-id": "90b1cc61-d631-4c9c-90bc-0aed911679ff",
                  "port-type": "internal",
                  "bandwidth": 128
                }
              ]
            },
            {
              "node-id": "d7b911e0-f7b4-4fb5-b9dc-af87738485bb",
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
                  "port-id": "7a558ce5-cbfd-4c4e-ac4b-223cb553ea42",
                  "external-ip-prefixes": {
                    "external-ip-prefix": [
                      "192.168.12.0/24"
                    ]
                  },
                  "port-type": "internal"
                },
                {
                  "port-id": "8981025b-2990-4d77-b041-0db8af1f8325",
                  "external-ip-prefixes": {
                    "external-ip-prefix": [
                      "192.168.12.0/24"
                    ]
                  },
                  "port-type": "internal"
                },
                {
                  "port-id": "ea178dbc-4ea7-4855-b1f0-130caae61ac5",
                  "port-type": "internal",
                  "bandwidth": 128
                },
                {
                  "port-id": "e246cc12-1c0a-467f-8440-b7bb43539b18",
                  "port-type": "internal",
                  "bandwidth": 0
                },
                {
                  "port-id": "03e82eac-9906-4c52-8960-0c6c34b6605a",
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
              "node-id": "7826e5d1-2a8c-4bca-be38-ada2fb54dd47",
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
                  "port-id": "eb30411b-1fc3-47c6-9238-ad437e362416",
                  "external-ip-prefixes": {
                    "external-ip-prefix": [
                      "192.168.11.0/24"
                    ]
                  },
                  "port-type": "internal"
                },
                {
                  "port-id": "831ff095-5fed-4125-8249-3b1238e05a9e",
                  "port-type": "internal",
                  "bandwidth": 0
                },
                {
                  "port-id": "56d04cbc-2557-4514-ae69-35d3b4fb6dc3",
                  "port-type": "internal",
                  "bandwidth": 0
                }
              ]
            },
            {
              "node-id": "3450efe7-dd9c-46c9-a10d-a57b609f2885",
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
                  "port-id": "56e7046b-3f13-4f73-92a4-e166cc076f5a",
                  "port-type": "internal"
                },
                {
                  "port-id": "bc7f6f1d-3921-44a8-b7e0-12c03bfdf9b3",
                  "port-type": "internal"
                },
                {
                  "port-id": "e5a44c4c-6efb-4852-bf0a-2639d9cb739c",
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
                },
                {
                  "port-id": "b295c6ad-eb02-4d43-bb48-a5f72db8b63c",
                  "port-type": "internal"
                }
              ]
            },
            {
              "node-id": "8c79c039-41c0-4bd9-a7af-704ed437cd49",
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
                  "port-id": "577517d2-f936-4227-9b3a-ecb462a57d15",
                  "port-type": "internal"
                },
                {
                  "port-id": "92931833-02db-4e6d-8cf7-8fa5d43c34a5",
                  "port-type": "internal"
                },
                {
                  "port-id": "948651a1-a523-43dc-b593-b0f44c817826",
                  "port-type": "internal"
                },
                {
                  "port-id": "3020a073-aec7-4e45-888b-98a7c06a0cb4",
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
              "node-id": "3c641151-82a9-45f1-a40b-6d3fa6ddccd1",
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
                  "port-id": "b76cd89e-6d7f-48d5-aada-0aab94fa8d3f",
                  "port-type": "internal"
                },
                {
                  "port-id": "b57dc73e-e107-4913-9de2-d1a13b06b8b0",
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
              "node-id": "6e77a772-602a-4e23-9275-522dd8b2fccb",
              "mac-address": "08:00:27:a0:a9:b3",
              "port-id": "56d54ad9-1d55-455d-828c-add02f9cfbd3"
            },
            {
              "ip-address": "192.168.11.2",
              "node-id": "3c641151-82a9-45f1-a40b-6d3fa6ddccd1",
              "mac-address": "00:00:00:00:00:03",
              "port-id": "b57dc73e-e107-4913-9de2-d1a13b06b8b0"
            },
            {
              "ip-address": "192.168.12.2",
              "node-id": "8c79c039-41c0-4bd9-a7af-704ed437cd49",
              "mac-address": "00:00:00:00:00:01",
              "port-id": "3020a073-aec7-4e45-888b-98a7c06a0cb4"
            },
            {
              "ip-address": "192.168.12.3",
              "node-id": "3450efe7-dd9c-46c9-a10d-a57b609f2885",
              "mac-address": "00:00:00:00:00:02",
              "port-id": "e5a44c4c-6efb-4852-bf0a-2639d9cb739c"
            }
          ]
        },
        "virtual-links": {
          "virtual-link": [
            {
              "link-id": "d5a26599-8433-4e32-ae2f-8bf81c15a007",
              "src-node-id": "8c79c039-41c0-4bd9-a7af-704ed437cd49",
              "delay": 0,
              "dest-port-id": "03e82eac-9906-4c52-8960-0c6c34b6605a",
              "metric": 1,
              "bandwidth": 0,
              "dest-node-id": "d7b911e0-f7b4-4fb5-b9dc-af87738485bb",
              "src-port-id": "92931833-02db-4e6d-8cf7-8fa5d43c34a5"
            },
            {
              "link-id": "93605e9e-cfbe-4d87-80ea-7bcd3f2c1b72",
              "src-node-id": "8c79c039-41c0-4bd9-a7af-704ed437cd49",
              "delay": 0,
              "dest-port-id": "2d56888f-d697-4758-9de1-53540476afac",
              "metric": 2,
              "bandwidth": 0,
              "dest-node-id": "6e77a772-602a-4e23-9275-522dd8b2fccb",
              "src-port-id": "948651a1-a523-43dc-b593-b0f44c817826"
            },
            {
              "link-id": "3c2cdead-f362-495a-8e97-6f14c97ff077",
              "src-node-id": "d7b911e0-f7b4-4fb5-b9dc-af87738485bb",
              "delay": 0,
              "dest-port-id": "b553b789-7e7b-4577-ad7d-d40cdb54b525",
              "metric": 1,
              "bandwidth": 0,
              "dest-node-id": "6e77a772-602a-4e23-9275-522dd8b2fccb",
              "src-port-id": "7a558ce5-cbfd-4c4e-ac4b-223cb553ea42"
            },
            {
              "link-id": "09692509-6681-4b41-915b-ce12ae33187f",
              "src-node-id": "8c79c039-41c0-4bd9-a7af-704ed437cd49",
              "delay": 0,
              "dest-port-id": "b295c6ad-eb02-4d43-bb48-a5f72db8b63c",
              "metric": 2,
              "bandwidth": 0,
              "dest-node-id": "3450efe7-dd9c-46c9-a10d-a57b609f2885",
              "src-port-id": "577517d2-f936-4227-9b3a-ecb462a57d15"
            },
            {
              "link-id": "54e28d07-370d-4023-a80d-9897819bfda6",
              "src-node-id": "3450efe7-dd9c-46c9-a10d-a57b609f2885",
              "delay": 0,
              "dest-port-id": "577517d2-f936-4227-9b3a-ecb462a57d15",
              "metric": 2,
              "bandwidth": 0,
              "dest-node-id": "8c79c039-41c0-4bd9-a7af-704ed437cd49",
              "src-port-id": "b295c6ad-eb02-4d43-bb48-a5f72db8b63c"
            },
            {
              "link-id": "b920b33f-10f1-4b9a-858b-76ef2c2565fb",
              "src-node-id": "6e77a772-602a-4e23-9275-522dd8b2fccb",
              "delay": 0,
              "dest-port-id": "948651a1-a523-43dc-b593-b0f44c817826",
              "metric": 2,
              "bandwidth": 0,
              "dest-node-id": "8c79c039-41c0-4bd9-a7af-704ed437cd49",
              "src-port-id": "2d56888f-d697-4758-9de1-53540476afac"
            },
            {
              "link-id": "8e495e86-e415-4f5b-acd6-1e45472e07f7",
              "src-node-id": "3c641151-82a9-45f1-a40b-6d3fa6ddccd1",
              "delay": 0,
              "dest-port-id": "eb30411b-1fc3-47c6-9238-ad437e362416",
              "metric": 1,
              "bandwidth": 0,
              "dest-node-id": "7826e5d1-2a8c-4bca-be38-ada2fb54dd47",
              "src-port-id": "b76cd89e-6d7f-48d5-aada-0aab94fa8d3f"
            },
            {
              "link-id": "07510b23-7253-4ead-94f0-a536f1db346f",
              "src-node-id": "7826e5d1-2a8c-4bca-be38-ada2fb54dd47",
              "delay": 0,
              "dest-port-id": "b76cd89e-6d7f-48d5-aada-0aab94fa8d3f",
              "metric": 1,
              "bandwidth": 0,
              "dest-node-id": "3c641151-82a9-45f1-a40b-6d3fa6ddccd1",
              "src-port-id": "eb30411b-1fc3-47c6-9238-ad437e362416"
            },
            {
              "link-id": "2c7793da-a1ad-4651-9367-db754c19684e",
              "src-node-id": "d7b911e0-f7b4-4fb5-b9dc-af87738485bb",
              "delay": 0,
              "dest-port-id": "92931833-02db-4e6d-8cf7-8fa5d43c34a5",
              "metric": 1,
              "bandwidth": 0,
              "dest-node-id": "8c79c039-41c0-4bd9-a7af-704ed437cd49",
              "src-port-id": "03e82eac-9906-4c52-8960-0c6c34b6605a"
            },
            {
              "link-id": "2579caf3-2a2d-49bb-b5c3-226aab60c3e5",
              "src-node-id": "3450efe7-dd9c-46c9-a10d-a57b609f2885",
              "delay": 0,
              "dest-port-id": "4a0307d7-0ca1-4e66-83d9-d6051c116bd9",
              "metric": 0,
              "bandwidth": 0,
              "dest-node-id": "6e77a772-602a-4e23-9275-522dd8b2fccb",
              "src-port-id": "bc7f6f1d-3921-44a8-b7e0-12c03bfdf9b3"
            },
            {
              "link-id": "b44bdf96-f78b-40b0-8b06-0f9cc5c46b24",
              "src-node-id": "6e77a772-602a-4e23-9275-522dd8b2fccb",
              "delay": 0,
              "dest-port-id": "bc7f6f1d-3921-44a8-b7e0-12c03bfdf9b3",
              "metric": 0,
              "bandwidth": 0,
              "dest-node-id": "3450efe7-dd9c-46c9-a10d-a57b609f2885",
              "src-port-id": "4a0307d7-0ca1-4e66-83d9-d6051c116bd9"
            },
            {
              "link-id": "b4abce22-18a3-4c3c-8c1a-4560b18b2f04",
              "src-node-id": "6e77a772-602a-4e23-9275-522dd8b2fccb",
              "delay": 0,
              "dest-port-id": "7a558ce5-cbfd-4c4e-ac4b-223cb553ea42",
              "metric": 1,
              "bandwidth": 0,
              "dest-node-id": "d7b911e0-f7b4-4fb5-b9dc-af87738485bb",
              "src-port-id": "b553b789-7e7b-4577-ad7d-d40cdb54b525"
            },
            {
              "link-id": "85c374ab-4135-4d5a-b3f7-80b780d12ddb",
              "src-node-id": "7826e5d1-2a8c-4bca-be38-ada2fb54dd47",
              "delay": 0,
              "dest-port-id": "e246cc12-1c0a-467f-8440-b7bb43539b18",
              "metric": 0,
              "bandwidth": 0,
              "dest-node-id": "d7b911e0-f7b4-4fb5-b9dc-af87738485bb",
              "src-port-id": "831ff095-5fed-4125-8249-3b1238e05a9e"
            },
            {
              "link-id": "bf4ccacb-cb99-46ce-8365-b2e1545cfe45",
              "src-node-id": "d7b911e0-f7b4-4fb5-b9dc-af87738485bb",
              "delay": 0,
              "dest-port-id": "831ff095-5fed-4125-8249-3b1238e05a9e",
              "metric": 0,
              "bandwidth": 0,
              "dest-node-id": "7826e5d1-2a8c-4bca-be38-ada2fb54dd47",
              "src-port-id": "e246cc12-1c0a-467f-8440-b7bb43539b18"
            },
            {
              "link-id": "decd48aa-5e1b-4775-899b-8e25e8d006f3",
              "src-node-id": "3450efe7-dd9c-46c9-a10d-a57b609f2885",
              "delay": 0,
              "dest-port-id": "8981025b-2990-4d77-b041-0db8af1f8325",
              "metric": 1,
              "bandwidth": 0,
              "dest-node-id": "d7b911e0-f7b4-4fb5-b9dc-af87738485bb",
              "src-port-id": "56e7046b-3f13-4f73-92a4-e166cc076f5a"
            },
            {
              "link-id": "136907df-d099-45ab-b55e-5ff32d4d1579",
              "src-node-id": "74766098-9f67-4be0-8b6d-b6011110198c",
              "delay": 0,
              "dest-port-id": "56d04cbc-2557-4514-ae69-35d3b4fb6dc3",
              "metric": 0,
              "bandwidth": 0,
              "dest-node-id": "7826e5d1-2a8c-4bca-be38-ada2fb54dd47",
              "src-port-id": "19e72797-a78c-418c-8f99-1e2d4293f3e5"
            },
            {
              "link-id": "c7aa534e-fcd5-4164-a636-a236d95ba624",
              "src-node-id": "d7b911e0-f7b4-4fb5-b9dc-af87738485bb",
              "delay": 0,
              "dest-port-id": "90b1cc61-d631-4c9c-90bc-0aed911679ff",
              "metric": 1,
              "bandwidth": 128,
              "dest-node-id": "ba9e5273-2c47-43c1-a959-5779488ce1f8",
              "src-port-id": "ea178dbc-4ea7-4855-b1f0-130caae61ac5"
            },
            {
              "link-id": "f14c1592-d107-48c1-a5ca-835ad347e38b",
              "src-node-id": "d7b911e0-f7b4-4fb5-b9dc-af87738485bb",
              "delay": 0,
              "dest-port-id": "56e7046b-3f13-4f73-92a4-e166cc076f5a",
              "metric": 1,
              "bandwidth": 0,
              "dest-node-id": "3450efe7-dd9c-46c9-a10d-a57b609f2885",
              "src-port-id": "8981025b-2990-4d77-b041-0db8af1f8325"
            },
            {
              "link-id": "94d01a13-0f9d-42ce-bc2e-308f16789dc7",
              "src-node-id": "7826e5d1-2a8c-4bca-be38-ada2fb54dd47",
              "delay": 0,
              "dest-port-id": "19e72797-a78c-418c-8f99-1e2d4293f3e5",
              "metric": 0,
              "bandwidth": 0,
              "dest-node-id": "74766098-9f67-4be0-8b6d-b6011110198c",
              "src-port-id": "56d04cbc-2557-4514-ae69-35d3b4fb6dc3"
            },
            {
              "link-id": "65b81cf0-5006-4aa4-80ca-f9cd01ada1b6",
              "src-node-id": "ba9e5273-2c47-43c1-a959-5779488ce1f8",
              "delay": 0,
              "dest-port-id": "ea178dbc-4ea7-4855-b1f0-130caae61ac5",
              "metric": 1,
              "bandwidth": 128,
              "dest-node-id": "d7b911e0-f7b4-4fb5-b9dc-af87738485bb",
              "src-port-id": "90b1cc61-d631-4c9c-90bc-0aed911679ff"
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
var MappingJson={
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
                "virtual-resource-id": "29bd638b-2187-4c73-9298-2c887d331a7d",
                "parent-virtual-resource-entity-id": "ba9e5273-2c47-43c1-a959-5779488ce1f8",
                "virtual-resource-type": "vport",
                "order": 0,
                "virtual-resource-entity-id": "25ba99fd-8846-490b-bbe7-883f71914851"
              }
            ]
          },
          {
            "intent-id": "6c787caa-156a-49ed-8546-547bdccf283c",
            "intent-type": "node",
            "virtual-resource": [
              {
                "virtual-resource-id": "ecd7a4eb-4f68-416f-88d0-af96f914ee3d",
                "parent-virtual-resource-entity-id": "3450efe7-dd9c-46c9-a10d-a57b609f2885",
                "virtual-resource-type": "vport",
                "order": 0,
                "virtual-resource-entity-id": "e5a44c4c-6efb-4852-bf0a-2639d9cb739c"
              }
            ]
          },
          {
            "intent-id": "e0d56fee-7235-4748-a2a1-eb5e3733d866",
            "intent-type": "connection",
            "virtual-resource": [
              {
                "virtual-resource-id": "466dac24-f6ea-44b4-8011-2de59bc5cd97",
                "virtual-resource-type": "vlink",
                "order": 0,
                "virtual-resource-entity-id": "94d01a13-0f9d-42ce-bc2e-308f16789dc7"
              },
              {
                "virtual-resource-id": "135ba490-19d9-4cb0-a211-49c6571c5064",
                "virtual-resource-type": "vlink",
                "order": 0,
                "virtual-resource-entity-id": "136907df-d099-45ab-b55e-5ff32d4d1579"
              }
            ]
          },
          {
            "intent-id": "b46cfa7f-93a3-43f4-ac20-09307c75feca",
            "intent-type": "node",
            "virtual-resource": [
              {
                "virtual-resource-id": "d56007fd-01bc-4f07-a770-9a22a4ebb11f",
                "virtual-resource-type": "vnode",
                "order": 0,
                "virtual-resource-entity-id": "7826e5d1-2a8c-4bca-be38-ada2fb54dd47"
              }
            ]
          },
          {
            "intent-id": "d463232f-363f-491c-a6f5-097ed0a794d3",
            "intent-type": "node",
            "virtual-resource": [
              {
                "virtual-resource-id": "05d68f48-1f68-4c3b-b19e-312a9a2b0471",
                "parent-virtual-resource-entity-id": "74766098-9f67-4be0-8b6d-b6011110198c",
                "virtual-resource-type": "vport",
                "order": 0,
                "virtual-resource-entity-id": "9680299d-dc93-4ba0-aff4-20c43962b249"
              }
            ]
          },
          {
            "intent-id": "b49e3960-c08d-4fff-b9fc-08b65ebcde2c",
            "intent-type": "connection",
            "virtual-resource": [
              {
                "virtual-resource-id": "9101d69a-312a-43b4-a9e1-a68c1d7acea4",
                "virtual-resource-type": "vlink",
                "order": 0,
                "virtual-resource-entity-id": "bf4ccacb-cb99-46ce-8365-b2e1545cfe45"
              },
              {
                "virtual-resource-id": "d6ed9071-e257-4a52-a64b-678bb1156221",
                "virtual-resource-type": "vlink",
                "order": 0,
                "virtual-resource-entity-id": "85c374ab-4135-4d5a-b3f7-80b780d12ddb"
              }
            ]
          },
          {
            "intent-id": "30da6667-608e-4d2f-bb50-79e5cabcc523",
            "intent-type": "connection",
            "virtual-resource": [
              {
                "virtual-resource-id": "3c37017e-5495-4d2e-8a91-dfd7afe3d63d",
                "virtual-resource-type": "vlink",
                "order": 0,
                "virtual-resource-entity-id": "65b81cf0-5006-4aa4-80ca-f9cd01ada1b6"
              },
              {
                "virtual-resource-id": "0eddd640-073f-4762-abd0-ca9af26fd380",
                "virtual-resource-type": "vlink",
                "order": 0,
                "virtual-resource-entity-id": "c7aa534e-fcd5-4164-a636-a236d95ba624"
              }
            ]
          },
          {
            "intent-id": "1eaf9a67-a171-42a8-9282-71cf702f61dd",
            "intent-type": "node",
            "virtual-resource": [
              {
                "virtual-resource-id": "fe7c8040-3e8e-44e9-882f-17a0557af7ad",
                "parent-virtual-resource-entity-id": "8c79c039-41c0-4bd9-a7af-704ed437cd49",
                "virtual-resource-type": "vport",
                "order": 0,
                "virtual-resource-entity-id": "3020a073-aec7-4e45-888b-98a7c06a0cb4"
              }
            ]
          },
          {
            "intent-id": "175425f7-c9c9-474a-962c-70cb6c180d4d",
            "intent-type": "node",
            "virtual-resource": [
              {
                "virtual-resource-id": "7fff5626-dd2a-42e8-a0f2-efe374470178",
                "virtual-resource-type": "vnode",
                "order": 0,
                "virtual-resource-entity-id": "d7b911e0-f7b4-4fb5-b9dc-af87738485bb"
              }
            ]
          },
          {
            "intent-id": "7b796915-adf4-4356-b5ca-de005ac410c1",
            "intent-type": "node",
            "virtual-resource": [
              {
                "virtual-resource-id": "a892362e-f2c2-4cf6-bfb2-5b5b1ca3014d",
                "parent-virtual-resource-entity-id": "3c641151-82a9-45f1-a40b-6d3fa6ddccd1",
                "virtual-resource-type": "vport",
                "order": 0,
                "virtual-resource-entity-id": "b57dc73e-e107-4913-9de2-d1a13b06b8b0"
              }
            ]
          },
          {
            "intent-id": "22282cca-9a13-4d0c-a67e-a933ebb0b0ae",
            "intent-type": "node",
            "virtual-resource": [
              {
                "virtual-resource-id": "d43bd8d0-3564-46a6-a2b1-441bdd947543",
                "parent-virtual-resource-entity-id": "6e77a772-602a-4e23-9275-522dd8b2fccb",
                "virtual-resource-type": "vport",
                "order": 0,
                "virtual-resource-entity-id": "56d54ad9-1d55-455d-828c-add02f9cfbd3"
              }
            ]
          }
        ],
        "virtual-network-id": "14ce424a-3e50-4a2a-ad5c-b29845158c8b"
      }
    ]
  }
}