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

#ip link del server1-eth0
ip netns delete server1
#ip link del vm1-eth0
ip netns delete vm1
#ip link del vm2-eth0
ip netns delete vm2
#ip link del server2-eth0
ip netns delete server2
#ip link del enter-eth0
ip netns delete enter
#ip link del inet-eth0
ip netns delete inet

echo "Removing temporary and log files......"

rm /tmp/sw*
#rm /tmp/*.log
rm /tmp/vconn-unix.*
rm /tmp/vlogs.*

echo "Shutting down network done."

exit 0