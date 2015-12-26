#!/bin/bash

echo "Creating links......"

########## sw1 ##########
ip link add name sw1-eth1 type veth peer name fw1-eth0
ip link add name sw1-eth2 type veth peer name fw2-eth0
ip link add name sw1-eth3 type veth peer name sw3-eth1

########## sw2 ##########
ip link add name sw2-eth1 type veth peer name cache1-eth0
ip link add name sw2-eth2 type veth peer name cache2-eth0
ip link add name sw2-eth3 type veth peer name sw3-eth2

########## sw3 ##########
#ip link add name sw3-eth1 type veth peer name sw1-eth3
#ip link add name sw3-eth2 type veth peer name sw2-eth3
ip link add name sw3-eth3 type veth peer name sw4-eth1
ip link add name sw3-eth4 type veth peer name branch-eth0

########## sw4 ##########
#ip link add name sw4-eth1 type veth peer name sw3-eth3
ip link add name sw4-eth2 type veth peer name head-eth0

echo "Creating hosts......"

########## fw1 ##########
ip netns add fw1
ip link set fw1-eth0 netns fw1

########## fw2 ##########
ip netns add fw2
ip link set fw2-eth0 netns fw2

########## cache1 ##########
ip netns add cache1
ip link set cache1-eth0 netns cache1

########## cache2 ##########
ip netns add cache2
ip link set cache2-eth0 netns cache2

########## headquarter ##########
ip netns add head
ip link set head-eth0 netns head

########## branch ##########
ip netns add branch
ip link set branch-eth0 netns branch

echo "Starting OpenFlow soft switches......"

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

ifconfig sw2-eth2 down
ifconfig sw2-eth2 hw ether 00:00:00:00:02:02
ifconfig sw2-eth2 up

ifconfig sw3-eth4 down
ifconfig sw3-eth4 hw ether 00:00:00:00:03:04
ifconfig sw3-eth4 up

ifconfig sw4-eth2 down
ifconfig sw4-eth2 hw ether 00:00:00:00:04:02
ifconfig sw4-eth2 up

echo "Configuring hosts......"

ip netns exec fw1 ifconfig fw1-eth0 down
ip netns exec fw1 ifconfig fw1-eth0 hw ether 00:00:00:00:00:01
ip netns exec fw1 ifconfig fw1-eth0 192.168.13.2/24
ip netns exec fw1 ifconfig fw1-eth0 up
ip netns exec fw1 route add default gw 192.168.13.1
ip netns exec fw1 arp -s 192.168.13.1 00:00:00:00:01:01
ip netns exec fw1 echo 1 > /proc/sys/net/ipv4/ip_forward

ip netns exec cache2 ifconfig cache2-eth0 down
ip netns exec cache2 ifconfig cache2-eth0 hw ether 00:00:00:00:00:02
ip netns exec cache2 ifconfig cache2-eth0 192.168.14.2/24
ip netns exec cache2 ifconfig cache2-eth0 up
ip netns exec cache2 route add default gw 192.168.14.1
ip netns exec cache2 arp -s 192.168.14.1 00:00:00:00:02:02
ip netns exec cache2 echo 1 > /proc/sys/net/ipv4/ip_forward

ip netns exec head ifconfig head-eth0 down
ip netns exec head ifconfig head-eth0 hw ether 00:00:00:00:00:03
ip netns exec head ifconfig head-eth0 192.168.11.2/24
ip netns exec head ifconfig head-eth0 up
ip netns exec head route add default gw 192.168.11.1
ip netns exec head arp -s 192.168.11.1 00:00:00:00:04:02

ip netns exec branch ifconfig branch-eth0 down
ip netns exec branch ifconfig branch-eth0 hw ether 00:00:00:00:00:04
ip netns exec branch ifconfig branch-eth0 192.168.12.2/24
ip netns exec branch ifconfig branch-eth0 up
ip netns exec branch route add default gw 192.168.12.1
ip netns exec branch arp -s 192.168.12.1 00:00:00:00:03:04

echo "Configuring flow entries for topology discovery......"

sleep 5

########## lldp ##########
dpctl unix:/tmp/sw1 flow-mod cmd=add,table=0,idle=0,hard=0,prio=65535 eth_type=0x88cc apply:output=ctrl:0xff
dpctl unix:/tmp/sw2 flow-mod cmd=add,table=0,idle=0,hard=0,prio=65535 eth_type=0x88cc apply:output=ctrl:0xff
dpctl unix:/tmp/sw3 flow-mod cmd=add,table=0,idle=0,hard=0,prio=65535 eth_type=0x88cc apply:output=ctrl:0xff
dpctl unix:/tmp/sw4 flow-mod cmd=add,table=0,idle=0,hard=0,prio=65535 eth_type=0x88cc apply:output=ctrl:0xff

echo "Creating network done."

exit 0



ip netns exec fw1 iptables -A FORWARD -i fw1-eth0 -j DROP
ip netns exec fw1 iptables -vxnL FORWARD

ip netns exec fw1 iptables -D FORWARD -i fw1-eth0 -j DROP
ip netns exec fw1 iptables -vxnL FORWARD
