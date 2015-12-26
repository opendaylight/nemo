#!/bin/bash

########## sw1 ##########
ip link add name sw1-eth1 type veth peer name fw-eth0
ip link add name sw1-eth2 type veth peer name vm1-eth0
ip link add name sw1-eth3 type veth peer name sw3-eth1

########## sw2 ##########
ip link add name sw2-eth1 type veth peer name vm2-eth0
ip link add name sw2-eth2 type veth peer name server-eth0
ip link add name sw2-eth3 type veth peer name sw3-eth2

########## sw3 ##########
#ip link add name sw3-eth1 type veth peer name sw1-eth3
#ip link add name sw3-eth2 type veth peer name sw2-eth3
ip link add name sw3-eth3 type veth peer name sw4-eth1
ip link add name sw3-eth4 type veth peer name inet-eth0

########## sw4 ##########
#ip link add name sw4-eth1 type veth peer name sw3-eth3
ip link add name sw4-eth2 type veth peer name itor-eth0

########## internet ##########
ip netns add inet
ip link set inet-eth0 netns inet

########## fw ##########
ip netns add fw
ip link set fw-eth0 netns fw

########## vm1##########
ip netns add vm1
ip link set vm1-eth0 netns vm1

########## vm2 ##########
ip netns add vm2
ip link set vm2-eth0 netns vm2

########## server ##########
ip netns add server
ip link set server-eth0 netns server

########## interior ##########
ip netns add itor
ip link set itor-eth0 netns itor

########## sw1 ##########
ofdatapath -i sw1-eth1,sw1-eth2,sw1-eth3 punix:/tmp/sw1 -d 000000000001 1> /tmp/sw1-ofd.log 2> /tmp/sw1-ofd.log &
ofprotocol unix:/tmp/sw1 tcp:127.0.0.1:6633 --fail=closed --listen=ptcp:6661 1> /tmp/sw1-ofp.log 2> /tmp/sw1-ofp.log &

########## sw2 ##########
ofdatapath -i sw2-eth1,sw2-eth2,sw2-eth3 punix:/tmp/sw2 -d 000000000002 1> /tmp/sw2-ofd.log 2> /tmp/sw2-ofd.log &
ofprotocol unix:/tmp/sw2 tcp:127.0.0.1:6633 --fail=closed --listen=ptcp:6662 1> /tmp/sw2-ofp.log 2> /tmp/sw2-ofp.log &

########## sw3 ##########
ofdatapath -i sw3-eth1,sw3-eth2,sw3-eth3,sw3-eth4 punix:/tmp/sw3 -d 000000000003 1> /tmp/sw3-ofd.log 2> /tmp/sw3-ofd.log &
ofprotocol unix:/tmp/sw3 tcp:127.0.0.1:6633 --fail=closed --listen=ptcp:6663 1> /tmp/sw3-ofp.log 2> /tmp/sw3-ofp.log &

########## sw4 ##########
ofdatapath -i sw4-eth1,sw4-eth2 punix:/tmp/sw4 -d 000000000004 1> /tmp/sw4-ofd.log 2> /tmp/sw4-ofd.log &
ofprotocol unix:/tmp/sw4 tcp:127.0.0.1:6633 --fail=closed --listen=ptcp:6664 1> /tmp/sw4-ofp.log 2> /tmp/sw4-ofp.log &

echo "Configuring OpenFlow soft switches......"

ifconfig sw1-eth1 down
ifconfig sw1-eth1 hw ether 00:00:00:00:01:01
ifconfig sw1-eth1 up

ifconfig sw3-eth4 down
ifconfig sw3-eth4 hw ether 00:00:00:00:03:04
ifconfig sw3-eth4 up

ifconfig sw4-eth2 down
ifconfig sw4-eth2 hw ether 00:00:00:00:04:02
ifconfig sw4-eth2 up

echo "Configuring hosts......"

ip netns exec fw ifconfig fw-eth0 down
ip netns exec fw ifconfig fw-eth0 hw ether 00:00:00:00:00:01
ip netns exec fw ifconfig fw-eth0 192.168.11.2/24
ip netns exec fw ifconfig fw-eth0 up
ip netns exec fw ifconfig fw-eth0
ip netns exec fw route add default gw 192.168.11.1
ip netns exec fw route -n
ip netns exec fw arp -s 192.168.11.1 00:00:00:00:01:01
ip netns exec fw echo 1 > /proc/sys/net/ipv4/ip_forward
ip netns exec fw cat /proc/sys/net/ipv4/ip_forward

ip netns exec inet ifconfig inet-eth0 down
ip netns exec inet ifconfig inet-eth0 hw ether 00:00:00:00:00:02
ip netns exec inet ifconfig inet-eth0 172.168.1.2/24
ip netns exec inet ifconfig inet-eth0 up
ip netns exec inet ifconfig inet-eth0
ip netns exec inet route add default gw 172.168.1.1
ip netns exec inet route -n
ip netns exec inet arp -s 172.168.1.1 00:00:00:00:03:04

ip netns exec itor ifconfig itor-eth0 down
ip netns exec itor ifconfig itor-eth0 hw ether 00:00:00:00:00:03
ip netns exec itor ifconfig itor-eth0 192.168.13.2/24
ip netns exec itor ifconfig itor-eth0 up
ip netns exec itor ifconfig itor-eth0
ip netns exec itor route add default gw 192.168.13.1
ip netns exec itor route -n
ip netns exec itor arp -s 192.168.13.1 00:00:00:00:04:02

ip netns exec server ifconfig server-eth0 down
ip netns exec server ifconfig server-eth0 hw ether 00:00:00:00:00:04
ip netns exec server ifconfig server-eth0 192.168.12.2/24
ip netns exec server ifconfig server-eth0 up
ip netns exec server ifconfig server-eth0
ip netns exec server route add default gw 192.168.12.1
ip netns exec server route -n

echo "Configuring flow entries for topology discovery......"

sleep 5

########## lldp ##########
dpctl unix:/tmp/sw1 flow-mod cmd=add,table=0,idle=0,hard=0,prio=65535 eth_type=0x88cc apply:output=ctrl:0xff
dpctl unix:/tmp/sw2 flow-mod cmd=add,table=0,idle=0,hard=0,prio=65535 eth_type=0x88cc apply:output=ctrl:0xff
dpctl unix:/tmp/sw3 flow-mod cmd=add,table=0,idle=0,hard=0,prio=65535 eth_type=0x88cc apply:output=ctrl:0xff
dpctl unix:/tmp/sw4 flow-mod cmd=add,table=0,idle=0,hard=0,prio=65535 eth_type=0x88cc apply:output=ctrl:0xff

echo "Creating network done."

exit 0



ip netns exec fw iptables -A FORWARD -i fw-eth0 -j DROP
ip netns exec fw iptables -vxnL FORWARD

ip netns exec fw iptables -D FORWARD -i fw-eth0 -j DROP
ip netns exec fw iptables -vxnL FORWARD
