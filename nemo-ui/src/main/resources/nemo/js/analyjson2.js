var instance_name;
var id_match_name = [];
var flow_match_name = [];
var storage_node_data = [];
function analy_instance(instdata)
{
	instance_name = user_name + "_instance";
	
	
	
	
}

/* function find_node_name(node_id)
{
	var SI_name = instance_name;
	for (var find_cursor = 0; find_cursor<localStorage.length;find_cursor++)
	{
		if(localStorage.key(find_cursor).indexOf(SI_name+'__') == -1)
			continue;
		else if(localStorage.key(find_cursor).indexOf('node_') > -1 )
		{
			var find_json_temp = jQuery.parseJSON(localStorage.getItem(localStorage.key(find_cursor)));
			for(var key in find_json_temp)
			{
				if(find_json_temp[key]["node_id"] == node_id)
					return find_json_temp[key]["node_name"];
			}
		}	
	}
	alert("no node id name match!!!");
} */



function analy_node(node_json)
{
	var node_local_json_tmp;
	for(var i in node_json["objects"]["node"])
	{
		var json_tmp = node_json["objects"]["node"][i];
		id_match_name[json_tmp["node-id"]] = json_tmp["node-name"];
		storage_node_data[json_tmp["node-id"]] = json_tmp;
		node_local_json_tmp = '{'+instance_name+'__'+'node_'+json_tmp["node-name"]+':{';
		node_local_json_tmp += '"Entity_type":"node","node_id":"'+json_tmp["node-id"]+'","node_name":"'+json_tmp["node-name"]+'","node_type":"'+json_tmp["node-type"]+'","property":{';
		for(var k in json_tmp["property"])
		{
			var json_tmp2 = json_tmp["property"][k];
			if(json_tmp2["property-name"] =='location')
				node_local_json_tmp += '"'+json_tmp2["property-name"]+'":"'+json_tmp2["property-values"]["string-value"][0]["value"]+'",';
			else(json_tmp2["property-name"] =='capacity')
				node_local_json_tmp += '"'+json_tmp2["property-name"]+'":"'+json_tmp2["property-values"]["int-value"][0]["value"]+'",';
		}
		node_local_json_tmp = node_local_json_tmp.substring(0,node_local_json_tmp.length-1);
		node_local_json_tmp += '}}}';
	}
    return 	node_local_json_tmp;
}

function analy_sub_node()
{
	var SI_name = instance_name;
	for (var find_cursor = 0; find_cursor<localStorage.length;find_cursor++)
	{
		if(localStorage.key(find_cursor).indexOf(SI_name+'__') == -1)
			continue;
		else if(localStorage.key(find_cursor).indexOf('node_') > -1 )
		{
			var find_str_temp = localStorage.getItem(localStorage.key(find_cursor));
			var find_json_temp = jQuery.parseJSON(localStorage.getItem(localStorage.key(find_cursor)));
			for(var key in find_json_temp)
			{
				if(find_json_temp[key]["node_type"].indexOf("group") > -1)
				{
					var node_json = storage_node_data[find_json_temp[key]["node_id"]];
					find_str_temp = find_str_temp.substring(0,find_str_temp.length-2);
					find_str_temp += ',"sub-node":{';
					var host_count = 0;
					var group_count = 0;
					for(var sub_cursor in node_json["sub-node"])
					{
						
						if(id_match_name[node_json["sub-node"][sub_cursor]["node-id"]] != null)
						{
							if(group_count == 0)
							{
								find_str_temp += '"sub-node_group": [';
							}
							find_str_temp += '"'+id_match_name[node_json["sub-node"][sub_cursor]["node-id"]]+'"';
							group_count++;
						}
					}
					if(group_count>0)
						find_str_temp += '],'
					for(var sub_cursor in node_json["sub-node"])
					{
						if(host_name[node_json["sub-node"][sub_cursor]["node-id"]] != null)
						{
							if(host_count == 0)
							{
								find_str_temp += '"sub-node_host": [';
							}
							find_str_temp += '"'+host_name[node_json["sub-node"][sub_cursor]["node-id"]]+'"';
							host_count++;
						}
					}
					find_str_temp += '}';
					if(host_count>0)
						find_str_temp += ']'
					if(group_count > 0 && host_count = 0)
						find_str_temp = find_str_temp.substring(0,find_str_temp.length-1);
					if(group_count > 0 || host_count > 0)
						find_str_temp += '}}}';
					else 
						find_str_temp = find_str_temp.substring(0,find_str_temp.length-13);
					find_str_temp += '}}';
				}
			}
			localStorage[localStorage.key(find_cursor))] = find_str_temp;
		}	
	}
	
}


function analy_connection(connection_json)
{
	var connection_local_json_tmp;
	for(var i in node_json["objects"]["connection"])
	{
		var json_tmp = node_json["objects"]["connection"][i];
		connection_local_json_tmp = '{'+instance_name+'__'+'connection_'+json_tmp["connection-name"]+':{';
		connection_local_json_tmp += '"Entity_type":"connection","connection_id":"'+json_tmp["connection-id"]+'","connection_name":"'+json_tmp["connection-name"]+'","connection_ype":"'+json_tmp["connection-type"]+'"';
		if(json_tmp["connection-type"] == 'p2p')
		{
			connection_local_json_tmp +=',"End-nodes":{';
			connection_local_json_tmp += '"one_node_name":"'+id_match_name[json_tmp["connection-id"]["sub-node"][0]["node-id"]]+'",';
			connection_local_json_tmp += '"one_node_name":"'+id_match_name[json_tmp["connection-id"]["sub-node"][1]["node-id"]]+'"}';
		}
		else if(json_tmp["connection-type"] == 'p2mp')
		{
			connection_local_json_tmp +=',"End-nodes":{';
			connection_local_json_tmp += '"one_node_name":"'+id_match_name[json_tmp["connection-id"]["sub-node"][0]["node-id"]]+'",';
			connection_local_json_tmp += '"other_node_name":[';
			for(var p2mp_cursor =1;p2mp_cursor<.length;p2mp_cursor++)
				connection_local_json_tmp += '"'+id_match_name[json_tmp["connection-id"]["sub-node"][p2mp_cursor]["node-id"]]+'",';
			connection_local_json_tmp = connection_local_json_tmp.substring(0,connection_local_json_tmp.length-1);
			connection_local_json_tmp += ']}';
		}
		connection_local_json_tmp += ',"property":{"'+json_tmp["property"][0]["property-name"]+'":"'+json_tmp["property"][0]["property-values"]["int-value"][0]["value"]+'"}';
		connection_local_json_tmp += '}}';
	}
	return 	connection_local_json_tmp;
}

function analy_flow(flow_json)
{
	var flow_local_json_tmp;
	for(var i in flow_json["objects"]["flow"])
	{
		var json_tmp = node_json["objects"]["flow"][i];
		flow_match_name[json_tmp["flow-id"]] = json_tmp["flow-name"];
		flow_local_json_tmp = '{'+instance_name+'__'+'flow_'+json_tmp["flow-name"]+':{';
		flow_local_json_tmp += '"Entity_type":"flow","flow_id":"'+json_tmp["flow-id"]+'","flow_name":"'+json_tmp["flow-name"]+'","match-items":{';
		for(var flow_cursor in json_tmp["match-item"])
		{
			flow_local_json_tmp += '"'+json_tmp["match-item"][flow_cursor]["match-item-name"]+'":"'+json_tmp["match-item"][flow_cursor]["match-item-values"]+'",';
		}
		flow_local_json_tmp = flow_local_json_tmp.substring(0,flow_local_json_tmp.length-1);
		flow_local_json_tmp += '}}}';
	}
	return 	flow_local_json_tmp;
}

function analy_policy(policy_json)
{
	var policy_local_json_tmp;
	for(var i in flow_json["objects"]["operation"])
	{
		policy_local_json_tmp = '{'+instance_name+'__'+'policy_'+json_tmp["policy-name"]+':{';
		policy_local_json_tmp += '"policy_id":"'+json_tmp["operation-id"]+'","policy_name":"'+json_tmp["operation-name"]+'","apply_entity_id":"'+'flow:'+flow_match_name[json_tmp["target-object"]]+'","condition:["';
		for(var condition_cursor in json_tmp["condition"])
		{
			policy_local_json_tmp += '"'+json_tmp["condition"][condition_cursor]["condition-parameter-name"]+' '+json_tmp["condition"][condition_cursor]["condition-parameter-match-pattern"]+' '+json_tmp["condition"][condition_cursor]["condtion-parameter-target-value"]["int-value"]+' '+ json_tmp["condition"][condition_cursor]["condition-relation-operator"]+' '+json_tmp["condition"][condition_cursor]["order"]+' '+json_tmp["condition"][condition_cursor]["condition-parameter-id"]+'";';
		}
		policy_local_json_tmp = policy_local_json_tmp.substring(0,policy_local_json_tmp.length-1);
		policy_local_json_tmp += '],';
		
	}	
	return 	policy_local_json_tmp;
}