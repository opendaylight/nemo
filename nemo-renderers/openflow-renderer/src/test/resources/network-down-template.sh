#!/bin/bash

pkill -9 ofdatapath
pkill -9  ofprotocol

pkill -9 fail-ofdatapath
pkill -9 fail-ofprotocol

pkill -9 ext-ofdatapath
pkill -9 ext-ofprotocol

ip link del sw1-eth1
ip link del sw1-eth2
ip link del sw1-eth3
ip link del sw2-eth1
ip link del sw2-eth2
ip link del sw2-eth3
ip link del sw3-eth1
ip link del sw3-eth2
ip link del sw3-eth3
ip link del sw3-eth4
ip link del sw4-eth1
ip link del sw4-eth2

ip link del fw-eth0
ip netns delete fw
ip link del inet-eth0
ip netns delete inet
ip link del itor-eth0
ip netns delete itor
ip link del server-eth0
ip netns delete server
ip link del vm1-eth0
ip netns delete vm1
ip link del vm2-eth0
ip netns delete vm2

rm /tmp/sw*
rm /tmp/*.log
rm /tmp/vconn-unix.*
rm /tmp/vlogs.*

exit 0