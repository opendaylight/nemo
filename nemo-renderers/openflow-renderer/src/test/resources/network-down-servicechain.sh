#!/bin/bash

echo "Shutting down OpenFlow soft switches......"

pkill -9 ofdatapath
pkill -9  ofprotocol

pkill -9 fail-ofdatapath
pkill -9 fail-ofprotocol

pkill -9 ext-ofdatapath
pkill -9 ext-ofprotocol

echo "Removing links......"

ip link del sw1-eth1
ip link del sw1-eth2
ip link del sw1-eth3
ip link del sw2-eth1
ip link del sw2-eth2
ip link del sw2-eth3
#ip link del sw3-eth1
#ip link del sw3-eth2
ip link del sw3-eth3
ip link del sw3-eth4
#ip link del sw4-eth1
ip link del sw4-eth2

echo "Removing hosts......"

#ip link del fw1-eth0
ip netns delete fw1
#ip link del fw2-eth0
ip netns delete fw2
#ip link del cache1-eth0
ip netns delete cache1
#ip link del cache2-eth0
ip netns delete cache2
#ip link del head-eth0
ip netns delete head
#ip link del branch-eth0
ip netns delete branch

echo "Removing temporary and log files......"

rm /tmp/sw*
#rm /tmp/*.log
rm /tmp/vconn-unix.*
rm /tmp/vlogs.*

echo "Shutting down network done."

exit 0