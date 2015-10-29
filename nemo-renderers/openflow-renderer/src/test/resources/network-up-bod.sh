#!/bin/bash

########## sw1 ##########
ip link add name sw1-eth1 type veth peer name server1-eth0
ip link add name sw1-eth2 type veth peer name vm1-eth0
ip link add name sw1-eth3 type veth peer name sw3-eth1

########## sw2 ##########
ip link add name sw2-eth1 type veth peer name vm2-eth0
ip link add name sw2-eth3 type veth peer name sw3-eth2

########## sw3 ##########
#ip link add name sw3-eth1 type veth peer name sw1-eth3
#ip link add name sw3-eth2 type veth peer name sw2-eth3
ip link add name sw3-eth3 type veth peer name sw4-eth1

########## sw4 ##########
#ip link add name sw4-eth1 type veth peer name sw3-eth3

########## server1 ##########
ip netns add server1
ip link set server1-eth0 netns server1

########## vm1 ##########
ip netns add vm1
ip link set vm1-eth0 netns vm1

########## vm2 ##########
ip netns add vm2
ip link set vm2-eth0 netns vm2

########## sw1 ##########
ofdatapath -i sw1-eth1,sw1-eth2,sw1-eth3 punix:/tmp/sw1 -d 000000000001 1> /tmp/sw1-ofd.log 2> /tmp/sw1-ofd.log &
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
dpctl unix:/tmp/sw1 flow-mod cmd=add,table=0 eth_type=0x88cc apply:output=ctrl:0xff
dpctl unix:/tmp/sw2 flow-mod cmd=add,table=0 eth_type=0x88cc apply:output=ctrl:0xff
dpctl unix:/tmp/sw3 flow-mod cmd=add,table=0 eth_type=0x88cc apply:output=ctrl:0xff
dpctl unix:/tmp/sw4 flow-mod cmd=add,table=0 eth_type=0x88cc apply:output=ctrl:0xff

exit 0



ip netns exec server1 bash
ifconfig server1-eth0 down
ifconfig server1-eth0 hw ether 00:00:00:00:00:03
ifconfig server1-eth0 192.168.11.2/24
ifconfig server1-eth0 up
ifconfig server1-eth0
route add default gw 192.168.11.1
route -n
arp -s 192.168.11.1 00:00:0a:0b:0c:01
exit

ip netns exec vm1 bash
ifconfig vm1-eth0 down
ifconfig vm1-eth0 hw ether 00:00:00:00:00:01
ifconfig vm1-eth0 192.168.12.2/24
ifconfig vm1-eth0 up
ifconfig vm1-eth0
route add default gw 192.168.12.1
route -n
arp -s 192.168.12.1 00:00:0a:0b:0c:02
arp -s 192.168.12.3 00:00:00:00:00:02
arp -s 192.168.12.4 08:00:27:a0:a9:b3
exit

ip netns exec vm2 bash
ifconfig vm2-eth0 down
ifconfig vm2-eth0 hw ether 00:00:00:00:00:02
ifconfig vm2-eth0 192.168.12.3/24
ifconfig vm2-eth0 up
ifconfig vm2-eth0
route add default gw 192.168.12.1
route -n
arp -s 192.168.12.1 00:00:0a:0b:0c:02
arp -s 192.168.12.2 00:00:00:00:00:01
arp -s 192.168.12.4 08:00:27:a0:a9:b3
exit



ip netns exec server1 bash
ping -c 3 192.168.12.4
ping -c 3 192.168.13.2
ping -c 3 172.168.1.2
exit

ip netns exec vm1 bash
ping -c 3 192.168.12.3
ping -c 3 192.168.12.4
ping -c 3 192.168.11.2
ping -c 3 192.168.13.2
ping -c 3 172.168.1.2
exit

ip netns exec vm2 bash
ping -c 3 192.168.12.2
ping -c 3 192.168.12.4
ping -c 3 192.168.11.2
ping -c 3 192.168.13.2
ping -c 3 172.168.1.2
exit
