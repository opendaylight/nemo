jQuery(document).ready(function ($) {
	var policy_set=[];
	var order_html=[];
	var order_doc=[];
$("#submit").click(function(){
	
	 // var service_name = $(".NE_up #sel_1").children('option:selected').val();	
	 // if(service_name == "news_hotline" || service_name == "s1")
	 // {
		//  var entity = service_name + "__" + "policy_"+document.getElementById("policy_name_list").options[0].text;
		//  var policy_json_str=localStorage.getItem(entity);
		//  var policy_json = jQuery.parseJSON(policy_json_str);
		//  var flow_id = policy_json[entity]["apply_entity_id"];
		//  var json_str_temp = localStorage.getItem(entity.substr(0,4)+flow_id.replace(":","_"));
		//  var json_temp = jQuery.parseJSON(json_str_temp);
		//  var vlanid = json_temp[entity.substr(0,4)+flow_id.replace(":","_")]["match_items"]["vlanid"];
		//  vlandata1 = vlanid.toString();
	 // }

	/* var entity_set = new Array();
	var json_data = jQuery.parseJSON(json_str);

	var service_name = $(".NE_up #sel_1").children('option:selected').val();	
	for(var i = 0;i<document.getElementById("policy_name_list").length;i++)
	{
		var entity = service_name + "__" + "policy_"+document.getElementById("policy_name_list").options[i].text;
		entity_set[i]=entity;
		policy_set[i]=localStorage.getItem(entity);

	}
	for(var i = 0;i<document.getElementById("policy_name_list").length;i++)
	{
		var json_str_temp,json_temp;
		var policy_json = jQuery.parseJSON(policy_set[i]);
		var flow_id = policy_json[entity_set[i]]["apply_entity_id"];
		json_str_temp = localStorage.getItem(entity_set[i].substr(0,4)+flow_id.replace(":","_"));
		json_temp = jQuery.parseJSON(json_str_temp);
		//alert(entity_set[i].substr(0,4)+flow_id.replace(":","_"));
		var src_id = json_temp[entity_set[i].substr(0,4)+flow_id.replace(":","_")]["match_items"]["src_node"];
		var dest_id = json_temp[entity_set[i].substr(0,4)+flow_id.replace(":","_")]["match_items"]["dest_node"];
		json_str_temp = localStorage.getItem(entity_set[i].substr(0,4)+"node_"+src_id.replace(":","_"));
		json_temp = jQuery.parseJSON(json_str_temp);
		var src_type = json_temp[entity_set[i].substr(0,4)+"node_"+src_id.replace(":","_")]["node_type"];
		var src_location = json_temp[entity_set[i].substr(0,4)+"node_"+src_id.replace(":","_")]["property"]["location"];
		json_str_temp = localStorage.getItem(entity_set[i].substr(0,4)+"node_"+dest_id.replace(":","_"));
		json_temp = jQuery.parseJSON(json_str_temp);
		var dest_type = json_temp[entity_set[i].substr(0,4)+"node_"+dest_id.replace(":","_")]["node_type"];
		var dest_location = json_temp[entity_set[i].substr(0,4)+"node_"+dest_id.replace(":","_")]["property"]["location"];
        for(var s2 =0;s2<json_data["physical-network"]["nodes"]["node"].length;s2++)
		{
			for(var s3=0;s3<json_data["physical-network"]["nodes"]["node"][s2]["interfaces"]["interface"].length;s3++)
			{				
				if(json_data["physical-network"]["nodes"]["node"][s2]["interfaces"]["interface"][s3]["location"] == src_location)
				{
					var node_name = json_data["physical-network"]["nodes"]["node"][s2]["node-id"];
					order_doc[node_name] = "";
					for(var s4=0;s4<json_data["physical-network"]["nodes"]["node"][s2]["interfaces"]["interface"].length;s4++)
					{
						var interface_temp = json_data["physical-network"]["nodes"]["node"][s2]["interfaces"]["interface"][s4];
						order_doc[node_name] += "interface "+interface_temp["interface-type"]+interface_temp["interface-number"]+"<br>";
						if(src_type == "group")
						order_doc[node_name] += "undo portswitch<br>";
						order_doc[node_name] += "description"+interface_temp["description"]+"<br>";
						order_doc[node_name] += "ip address"+interface_temp["ip-address"]+" "+interface_temp["mask"]+"<br><br>";
					}					
				}                					
			}	
		}
		for(var s2 =0;s2<json_data["physical-network"]["nodes"]["node"].length;s2++)
		{
			for(var s3=0;s3<json_data["physical-network"]["nodes"]["node"][s2]["interfaces"]["interface"].length;s3++)
			{				
				if(json_data["physical-network"]["nodes"]["node"][s2]["interfaces"]["interface"][s3]["location"] == dest_location)
				{
					var node_name = json_data["physical-network"]["nodes"]["node"][s2]["node-id"];
					order_doc[node_name] = "";
					for(var s4=0;s4<json_data["physical-network"]["nodes"]["node"][s2]["interfaces"]["interface"].length;s4++)
					{
						var interface_temp = json_data["physical-network"]["nodes"]["node"][s2]["interfaces"]["interface"][s4];
						order_doc[node_name] += "interface "+interface_temp["interface-type"]+interface_temp["interface-number"]+"<br>";
						if(dest_type == "group")
						order_doc[node_name] += "undo portswitch<br>";
						order_doc[node_name] += "description"+interface_temp["description"]+"<br>";
						order_doc[node_name] += "ip address"+interface_temp["ip-address"]+" "+interface_temp["mask"]+"<br><br>";
					}					
				}                					
			}	
		}
		if(policy_json[entity_set[i]]["action"] == "SFC")
		{
			var chain_list = policy_json[entity_set[i]]["chain_node_list"];
            if(chain_list !=null && chain_list.length == 1)
			{
				var firewall_id = json_data["physical-network"]["nodes"]["firewall-resource"]["firewall-id"];
				for(var s2 =0;s2<json_data["physical-network"]["nodes"]["node"].length;s2++)
				{
					if(json_data["physical-network"]["nodes"]["node"][s2]["node-id"] == firewall_id)
					{
						var node_name = json_data["physical-network"]["nodes"]["node"][s2]["node-id"];
					    order_doc[node_name] = "";
					    for(var s4=0;s4<json_data["physical-network"]["nodes"]["node"][s2]["interfaces"]["interface"].length;s4++)
					    {
						    var interface_temp = json_data["physical-network"]["nodes"]["node"][s2]["interfaces"]["interface"][s4];
						    order_doc[node_name] += "interface "+interface_temp["interface-type"]+interface_temp["interface-number"]+"<br>";
						    order_doc[node_name] += "description"+interface_temp["description"]+"<br>"
						    order_doc[node_name] += "ip address"+interface_temp["ip-address"]+" "+interface_temp["mask"]+"<br><br>";
					    }				
					}				
				}			
			}
			else if(chain_list !=null && chain_list.length > 1)
			{
				var json_str_temp,json_temp;
				for(var z=0;z<chain_list.length;z++)
				{
					json_str_temp = localStorage.getItem(entity_set[i].substr(0,4)+"node_"+chain_list[z]);
		            json_temp = jQuery.parseJSON(json_str_temp);
					if(json_temp[entity_set[i].substr(0,4)+"node_"+chain_list[z]]["node_type"] == "loadbalance")
					{
						var loadbalance_id = json_data["physical-network"]["nodes"]["LB-resource"]["LB-id"];
				        for(var s2 =0;s2<json_data["physical-network"]["nodes"]["node"].length;s2++)
				        {
					         if(json_data["physical-network"]["nodes"]["node"][s2]["node-id"] == loadbalance_id)
					         {
						        var node_name = json_data["physical-network"]["nodes"]["node"][s2]["node-id"];
					            order_doc[node_name] = "";
					            for(var s4=0;s4<json_data["physical-network"]["nodes"]["node"][s2]["interfaces"]["interface"].length;s4++)
					            {
						             var interface_temp = json_data["physical-network"]["nodes"]["node"][s2]["interfaces"]["interface"][s4];
						             order_doc[node_name] += "interface "+interface_temp["interface-type"]+interface_temp["interface-number"]+"<br>";
						             order_doc[node_name] += "description"+interface_temp["description"]+"<br>"
						             order_doc[node_name] += "ip address"+interface_temp["ip-address"]+" "+interface_temp["mask"]+"<br><br>";
					             }				
					         }				
				        }		
					}
	
				}				
			}
		}
	}  */
	//alert("Successfully Submitted!");
	
});
  /* $("#display_info").click(function(){
	  var json_data = jQuery.parseJSON(json_str);
	  for(var y=0;y<json_data["physical-network"]["nodes"]["node"].length;y++)
	{
		var node_id = json_data["physical-network"]["nodes"]["node"][y]["node-id"];
		if(order_doc[node_id] != null)
		{
			
			document.getElementById("order").innerHTML += node_id + "  orders: <br>";
			document.getElementById("order").innerHTML += order_doc[node_id]+"<br><br>";
		}
	}
	  	 policy_set=[];
	     order_html=[];
	     order_doc=[];
  }); */
  $("#display_info").click(function(){
	   var service_name = $(".NE_up #sel_1").children('option:selected').val();	
	 if(service_name == "news_hotline" || service_name == "s1")
	 {
		 var entity = service_name + "__" + "policy_"+document.getElementById("policy_name_list").options[0].text;
		 var policy_json_str=localStorage.getItem(entity);
		 var policy_json = jQuery.parseJSON(policy_json_str);
		 var flow_id = policy_json[entity]["apply_entity_id"];
		 var json_str_temp = localStorage.getItem(entity.substr(0,4)+flow_id.replace(":","_"));
		 var json_temp = jQuery.parseJSON(json_str_temp);
		 var vlanid = json_temp[entity.substr(0,4)+flow_id.replace(":","_")]["match_items"]["vlanid"];
		 vlandata1 = vlanid.toString();
		 document.getElementById("order").innerHTML = "Command Line Configuration<br><br>\
      Device: E11-CE128-5<br>\
    Command lines:<br>\
	interface GE1/0/46<br>\
	undo portswitch<br>\
	ip address 101.4.1.1 255.255.255.0<br><br>\
	interface 10GE2/0/6<br>\
	undo portswitch<br>\
	ip address 101.1.1.1 255.255.255.0<br><br>\
	interface GE1/0/7<br>\
	undo portswitch<br>\
	ip address 101.10.1.1 255.255.255.0<br><br>\
	interface GE1/0/6<br>\
	undo portswitch<br>\
	ip address 101.9.1.1 255.255.255.0<br><br>\
	interface GE1/0/5<br>\
	undo portswitch<br>\
	ip address 101.8.1.1 255.255.255.0<br><br>\
	interface GE1/0/4<br>\
	undo portswitch<br>\
	ip address 101.7.1.1 255.255.255.0<br><br>\
	interface GE1/0/3<br>\
	undo portswitch<br>\
	ip address 101.6.1.1 255.255.255.0<br><br>\
	interface GE1/0/2<br>\
	undo portswitch<br>\
	ip address 101.5.1.1 255.255.255.0<br><br>\
	interface GE1/0/1<br>\
	undo portswitch <br>\
	ip address 192.168.50.10 255.255.255.0<br><br>\
	ospf 100 router-id 101.1.1.2<br>\
	description NEMO-VAS<br>\
	area 0.0.0.0<br>\
	network 101.1.1.0 0.0.0.255<br>\
	network 101.4.1.0 0.0.0.255<br><br>\
	ip route-static 101.10.1.0 255.255.255.0 101.8.1.2<br><br>\
	vlan batch "+vlandata1+"\
	interface Vlanif "+vlandata1+"<br>\
	interface GE1/0/2<br>\
	port default vlan "+vlandata1+"<br><br><br>\
    Device: E-LB1<br>\
    Command lines:<br><br>\
	interface GE2/0/6<br>\
	ip address 101.6.1.2 255.255.255.0<br><br>\
	interface GE2/0/7<br>\
	ip address 101.5.1.2 255.255.255.0<br><br>\
	ip address 192.168.50.1 255.255.255.0<br>\
	ip route-static 0.0.0.0 0.0.0.0 GigabitEthernet2/0/6 101.6.1.1<br><br>\
     Device: E10-Firewall<br> Command lines:<br><br>\
	interface GE2/0/6<br>\
	ip address 101.8.1.2 255.255.255.0<br><br>interface GE2/0/7<br>ip address 101.7.1.2 255.255.255.0<br><br>\
	firewall zone trust<br>set priority 85<br>\
	detect ftp<br>\
	detect rtsp<br>\
	detect pptp<br>\
	add interface GigabitEthernet2/0/7<br><br>\
	firewall zone untrust<br>\
	set priority 5<br>\
	detect ftp<br>\
	detect rtsp<br>\
	detect pptp<br>add interface GigabitEthernet2/0/6<br><br>\
    firewall packet-filter default permit interzone trust untrust direction inbound<br>\
	firewall packet-filter default permit interzone trust untrust direction outbound<br><br>\
	nat address-group 100 101.10.1.0 101.10.1.100<br>\
	nat-policy interzone trust untrust outbound<br>\
	policy 0<br>\
	action source-nat<br>\
	policy source 192.168.50.0 0.0.0.255 <br>address-group 100<br><br>\
	ip route-static 101.10.1.0 255.255.255.0 NULL0<br>ip route-static 0.0.0.0 0.0.0.0 GigabitEthernet2/0/6 101.8.1.1<br><br>\
	Device: E9-NE40E-5<br>Command lines:<br><br>\
	interface GigabitEthernet2/0/15<br>\
	undo shutdown<br>\
	ip address 101.2.1.1 255.255.255.0<br><br>\
	interface GigabitEthernet2/0/19<br>\
	undo shutdown<br>\
	ip address 101.1.1.1 255.255.255.0<br><br>\
	interface GigabitEthernet2/0/1<br>\
	undo shutdown<br>\
	ip address 10.1.1.2 255.255.255.0<br><br>\
	ospf 100 router-id 101.1.1.1<br>\
	description NEMO-VAS<br>\
	area 0.0.0.0<br>\
	network 101.1.1.0 0.0.0.255<br>network 101.2.1.0 0.0.0.255<br><br>\
	";
	 }
	 else if(service_name == "s2" || service_name == "mms")
	 {
		 var vlandata = [];
		 for(var i =0 ;i<3;i++)
		 {
			  var entity = service_name + "__" + "policy_"+document.getElementById("policy_name_list").options[i].text;
		      var policy_json_str=localStorage.getItem(entity);
		      var policy_json = jQuery.parseJSON(policy_json_str);
		      var flow_id = policy_json[entity]["apply_entity_id"];
		      var json_str_temp = localStorage.getItem(entity.substr(0,4)+flow_id.replace(":","_"));
		      var json_temp = jQuery.parseJSON(json_str_temp);
		      var vlanid = json_temp[entity.substr(0,4)+flow_id.replace(":","_")]["match_items"]["vlanid"];
			  var dest = json_temp[entity.substr(0,4)+flow_id.replace(":","_")]["match_items"]["dest_node"];
			  json_str_temp = localStorage.getItem(entity.substr(0,4)+"node_"+dest);
			  json_temp = jQuery.parseJSON(json_str_temp);		  
		      if(json_temp[entity.substr(0,4)+"node_"+dest]["property"]["location"] == "access_port2") 
				  vlandata["access_port2"] = vlanid;
			  else if(json_temp[entity.substr(0,4)+"node_"+dest]["property"]["location"] == "access_port3")
			      vlandata["access_port3"] = vlanid;
			  else
				  vlandata["loadbalance"] = vlanid;
		 }
		 document.getElementById("order").innerHTML ="Command Line Configuration<br><br>Command lines:<br>\
	interface GE1/0/46<br>\
	undo portswitch<br>\
	ip address 101.4.1.1 255.255.255.0<br><br>\
	interface 10GE2/0/6<br>\
	undo portswitch<br>\
	ip address 101.1.1.1 255.255.255.0<br><br>\
	interface GE1/0/7<br>\
	undo portswitch<br>\
	ip address 101.10.1.1 255.255.255.0<br><br>\
	interface GE1/0/6<br>\
	undo portswitch<br>\
	ip address 101.9.1.1 255.255.255.0<br><br>\
	interface GE1/0/5<br>\
	undo portswitch<br>\
	ip address 101.8.1.1 255.255.255.0<br><br>\
	interface GE1/0/4<br>\
	undo portswitch<br>\
	ip address 101.7.1.1 255.255.255.0<br><br>\
	interface GE1/0/3<br>\
	undo portswitch<br>\
	ip address 101.6.1.1 255.255.255.0<br><br>\
	interface GE1/0/2<br>\
	undo portswitch<br>\
	ip address 101.5.1.1 255.255.255.0<br><br>\
	interface GE1/0/1<br>\
	undo portswitch <br>\
	ip address 192.168.50.10 255.255.255.0<br><br>\
	ospf 100 router-id 101.1.1.2<br>\
	description NEMO-VAS<br>\
	area 0.0.0.0<br>\
	network 101.1.1.0 0.0.0.255<br>\
	network 101.4.1.0 0.0.0.255<br><br>\
	vlan batch "+vlandata["access_port2"]+" "+vlandata["access_port3"]+" "+vlandata["loadbalance"]+"<br><br>\
	interface Vlanif "+vlandata["access_port2"]+"<br>\
	interface Vlanif "+vlandata["access_port3"]+"<br>\
	interface Vlanif "+vlandata["loadbalance"]+"<br><br>\
	interface GE1/0/2<br>\
	port trunk allow-pass vlan "+vlandata["loadbalance"]+"<br><br>\
	interface GE1/0/4<br>\
	port trunk allow-pass vlan "+vlandata["access_port2"]+" "+vlandata["access_port3"]+" "+vlandata["loadbalance"]+"<br><br>\
	interface GE1/0/6<br>\
	port trunk allow-pass vlan "+vlandata["access_port2"]+"<br><br>\
	interface GE1/0/7<br>\
	port trunk allow-pass vlan "+vlandata["access_port3"]+"<br><br>\
	ip ip-prefix prefix-a index 10 permit 101.9.1.0 24<br>\
	route-policy policy1 permit node 10<br>\
	apply ip-address next-hop 10.9.1.2<br><br>\
	ip ip-prefix prefix-a index 11 permit 101.10.1.0 24<br>\
	route-policy policy1 permit node 11<br>\
	apply ip-address next-hop 10.10.1.2<br><br>\
Device: E-LB1<br>\
Command lines:<br><br>\
	interface GE2/0/6<br>\
	ip address 101.6.1.2 255.255.255.0<br><br>\
	interface GE2/0/7<br>\
	ip address 101.5.1.2 255.255.255.0<br><br>\
	ip address 192.168.50.1 255.255.255.0<br>\
	ip route-static 0.0.0.0 0.0.0.0 GigabitEthernet2/0/6 101.6.1.1<br><br><br>\
Device: E10-Firewall<br>\
Command lines:<br><br>\
	interface GE2/0/6<br>\
	ip address 101.8.1.2 255.255.255.0<br><br>\
	interface GE2/0/7<br>\
	ip address 101.7.1.2 255.255.255.0<br><br>\
	firewall zone trust<br>\
	set priority 85<br>\
	detect ftp<br>\
	detect rtsp<br>\
	detect pptp<br>\
	add interface GigabitEthernet2/0/7<br><br>\
	firewall zone untrust<br>\
	set priority 5<br>\
	detect ftp<br>\
	detect rtsp<br>\
	detect pptp<br>\
	add interface GigabitEthernet2/0/6<br><br>\
	firewall packet-filter default permit interzone trust untrust direction inbound<br>\
	firewall packet-filter default permit interzone trust untrust direction outbound<br><br>\
	nat address-group 100 101.10.1.0 101.10.1.100<br>\
	nat-policy interzone trust untrust outbound<br>\
	policy 0<br>\
	action source-nat<br>\
	policy source 192.168.50.0 0.0.0.255 <br>\
	address-group 100<br><br>\
	ip route-static 101.10.1.0 255.255.255.0 NULL0<br>\
	ip route-static 0.0.0.0 0.0.0.0 GigabitEthernet2/0/6 101.8.1.1<br><br><br>\
Device: E9-NE40E-5<br>\
Command lines:<br><br>\
	interface GigabitEthernet2/0/15<br>\
	undo shutdown<br>\
	ip address 101.2.1.1 255.255.255.0<br><br>\
	interface GigabitEthernet2/0/19<br>\
	undo shutdown<br>\
	ip address 101.1.1.1 255.255.255.0<br><br>\
	interface GigabitEthernet2/0/1<br>\
	undo shutdown<br>\
	ip address 10.1.1.2 255.255.255.0<br><br>\
	ospf 100 router-id 101.1.1.1<br>\
	description NEMO-VAS<br>\
	area 0.0.0.0<br>\
	network 101.1.1.0 0.0.0.255<br>\
	network 101.2.1.0 0.0.0.255<br>"
;
		
		 
		 
		 
		 
	 }
	  
	  
	  
	  });
});