#!/bin/bash

########## sw1 ##########
ip link add name sw1-eth2 type veth peer name fw2-eth0
ip link add name sw1-eth3 type veth peer name sw3-eth1

########## sw2 ##########
ip link add name sw2-eth1 type veth peer name cache1-eth0
ip link add name sw2-eth3 type veth peer name sw3-eth2

########## sw3 ##########
#ip link add name sw3-eth1 type veth peer name sw1-eth3
#ip link add name sw3-eth2 type veth peer name sw2-eth3
ip link add name sw3-eth3 type veth peer name sw4-eth1

########## sw4 ##########
#ip link add name sw4-eth1 type veth peer name sw3-eth3

########## fw2 ##########
ip netns add fw2
ip link set fw2-eth0 netns fw2

########## cache1 ##########
ip netns add cache1
ip link set cache1-eth0 netns cache1

########## sw1 ##########
ofdatapath -i eth0,sw1-eth2,sw1-eth3 punix:/tmp/sw1 -d 000000000001 1> /tmp/sw1-ofd.log 2> /tmp/sw1-ofd.log &
ofprotocol unix:/tmp/sw1 tcp:127.0.0.1:6633 --fail=closed --listen=ptcp:6661 1> /tmp/sw1-ofp.log 2> /tmp/sw1-ofp.log &

########## sw2 ##########
ofdatapath -i sw2-eth1,eth1,sw2-eth3 punix:/tmp/sw2 -d 000000000002 1> /tmp/sw2-ofd.log 2> /tmp/sw2-ofd.log &
ofprotocol unix:/tmp/sw2 tcp:127.0.0.1:6633 --fail=closed --listen=ptcp:6662 1> /tmp/sw2-ofp.log 2> /tmp/sw2-ofp.log &

########## sw3 ##########
ofdatapath -i sw3-eth1,sw3-eth2,sw3-eth3,eth2 punix:/tmp/sw3 -d 000000000003 1> /tmp/sw3-ofd.log 2> /tmp/sw3-ofd.log &
ofprotocol unix:/tmp/sw3 tcp:127.0.0.1:6633 --fail=closed --listen=ptcp:6663 1> /tmp/sw3-ofp.log 2> /tmp/sw3-ofp.log &

########## sw4 ##########
ofdatapath -i sw4-eth1,eth3 punix:/tmp/sw4 -d 000000000004 1> /tmp/sw4-ofd.log 2> /tmp/sw4-ofd.log &
ofprotocol unix:/tmp/sw4 tcp:127.0.0.1:6633 --fail=closed --listen=ptcp:6664 1> /tmp/sw4-ofp.log 2> /tmp/sw4-ofp.log &

sleep 5

########## lldp ##########
dpctl unix:/tmp/sw1 flow-mod cmd=add,table=0,idle=0,hard=0,prio=65535 eth_type=0x88cc apply:output=ctrl:0xff
dpctl unix:/tmp/sw2 flow-mod cmd=add,table=0,idle=0,hard=0,prio=65535 eth_type=0x88cc apply:output=ctrl:0xff
dpctl unix:/tmp/sw3 flow-mod cmd=add,table=0,idle=0,hard=0,prio=65535 eth_type=0x88cc apply:output=ctrl:0xff
dpctl unix:/tmp/sw4 flow-mod cmd=add,table=0,idle=0,hard=0,prio=65535 eth_type=0x88cc apply:output=ctrl:0xff

exit 0



ip netns exec fw2 bash
ifconfig fw2-eth0 down
ifconfig fw2-eth0 hw ether 00:00:00:00:00:01
ifconfig fw2-eth0 up
ifconfig fw2-eth0
exit

ip netns exec cache1 bash
ifconfig cache1-eth0 down
ifconfig cache1-eth0 hw ether 00:00:00:00:00:02
ifconfig cache1-eth0 up
ifconfig cache1-eth0
exit



echo 1 > /proc/sys/net/ipv4/ip_forward
cat /proc/sys/net/ipv4/ip_forward

iptables -A FORWARD -i eth0 -j DROP
iptables -vxnL FORWARD

iptables -D FORWARD -i eth0 -j DROP
iptables -vxnL FORWARD
