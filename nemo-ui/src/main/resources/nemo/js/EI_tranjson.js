var restful_json=[];



var restful_count=[];
var restful_query_json=[];
var restful_query_count=[];
var restful_exec_flag = 0;
var intterval_id = 0;
//###############################################EXEC_Query##################################################//
	function query_status_exec()
	{
		//var query_str = '{"input":{"user-id":"'+user_id+'","user-name":"'+user_name+'","user-password":"'+user_password+'","user-role":"'+user_role+'"}}';
		//var query_json = jQuery.parseJSON(query_str);
		jQuery.ajax({ 
			    url: "/restconf/config/intent-processing-status:intent-processing-statuses/user-intent-status/"+user_id+"/transaction-status/", 
				type:"GET",
				//data:query_str,
				//contentType:"application/json; charset=UTF-8",
				dataType:"json",
				success: function(data){
					if(data["output"]["result-code"] == "ok")
					{
						restful_exec_flag = 0;
						jQuery("#show_status").hide();
						if(intterval_id != 0)
						{
                             exec_success();
							alert("Exec succeed!!!");
							//clearInterval(intterval_id);
						}
					}
				},
                                error:function(data){
					console.log(data);
					//alert("exec query failed");				
				}
		});
	}
//#####################################################################################################################//
jQuery(document).ready(function ($) {
	
});




//##################################### CREATE_JSON(Entity,Policy,Delete,Query) ######################################//
function create_json(SI_name)
{
	var restful_node=[];
	var restful_connection=[];
	var restful_flow=[];
	var restful_policy=[];
	var restful_delete=[];
	restful_query_count[SI_name] = 0;
	// var node_json_count = 0;
	// var connection_json_count = 0;
	// var flow_json_count = 0;
	// var node_json_count = 0;
	// var delete_json_count = 0;
	restful_json[SI_name]=[];
	var json_query_tmp = [];
	console.log(localStorage.length);

	for (var i = 0; i<localStorage.length;i++)
	{
		if(localStorage.key(i).indexOf(SI_name+'__') == -1)
			continue;
		else if(localStorage.key(i).indexOf(SI_name+'__node') > -1 || localStorage.key(i).indexOf('connection_') > -1 || localStorage.key(i).indexOf('flow_') > -1 )
		{
			var type_info = tran_tpjson_entity(localStorage.getItem(localStorage.key(i)));
			if(localStorage.key(i).indexOf(SI_name+'__node') > -1)
			{
				for (var j = 0; j < type_info.length; j++) {
					restful_node.push(type_info[j]);
				}
		    }
		    else if(localStorage.key(i).indexOf('connection_') > -1){
		   		restful_connection.push(type_info);
		    }
		    else if(localStorage.key(i).indexOf('flow_') > -1)
		    {
		    	restful_flow.push(type_info);
		    }
		}
		else if(localStorage.key(i).indexOf(SI_name+'__policy') > -1)
		{
			restful_policy.push(tran_tpjson_policy(localStorage.getItem(localStorage.key(i))));
		}
		else if(localStorage.key(i).indexOf(SI_name+'__delete') > -1)
		{
			var delete_id;
			if(localStorage.key(i).indexOf('node') > -1)
			{
				var delete_data = jQuery.parseJSON(localStorage.getItem(localStorage.key(i)));
				delete_id = delete_data["node_id"];
			}
			else if(localStorage.key(i).indexOf('connection') > -1)
			{
				var delete_data = jQuery.parseJSON(localStorage.getItem(localStorage.key(i)));
				delete_id = delete_data["connection_id"];
			}
			else if(localStorage.key(i).indexOf('flow') > -1)
			{
				var delete_data = jQuery.parseJSON(localStorage.getItem(localStorage.key(i)));
				delete_id = delete_data["flow_id"];
			}
			else if(localStorage.key(i).indexOf('policy') > -1)
			{
				var delete_data = jQuery.parseJSON(localStorage.getItem(localStorage.key(i)));
				delete_id = delete_data["policy_id"];
			}
			restful_delete.push(tran_tpjson_delete(delete_id));
		}
		else if(localStorage.key(i).indexOf(SI_name+'__query') > -1)
		{
			json_query_tmp[restful_query_count[SI_name]] = tran_tpjson_query(localStorage.getItem(localStorage.key(i)));
			restful_query_count[SI_name]++;
		}
	}
	for(var json_cursor in restful_delete)
		restful_json[SI_name].push(restful_delete[json_cursor]);
	for(var json_cursor in restful_node)
		restful_json[SI_name].push(restful_node[json_cursor]);
	for(var json_cursor in restful_connection)
		restful_json[SI_name].push(restful_connection[json_cursor]);
	for(var json_cursor in restful_flow)
		restful_json[SI_name].push(restful_flow[json_cursor]);
	for(var json_cursor in restful_policy)
		restful_json[SI_name].push(restful_policy[json_cursor]);
	restful_query_json[SI_name] = json_query_tmp;
	console.log(restful_json[SI_name]);
	console.log(restful_query_json[SI_name]);
}
//#####################################################################################################################//


//#################################################### CREATE_Entity_Json##############################################//
function tran_tpjson_entity(entity_str)
{
	var entity_json = jQuery.parseJSON(entity_str);
	var restful_json_temp;
	for(var key in entity_json)
	{
		if(entity_json[key]["Entity_Type"] == "node")
		{
			restful_json_temp=tran_tojson_node(entity_json[key]);
		}
		else if(entity_json[key]["Entity_Type"] == "connection")
		{
			restful_json_temp=tran_tojson_connection(entity_json[key]);
		}
		else if(entity_json[key]["Entity_Type"] == "flow")
		{
			restful_json_temp=tran_tojson_flow(entity_json[key]);
		}
		
	}
    return restful_json_temp;
}
//#####################################################################################################################//

//#################################################### CREATE_Policy_Json##############################################//
function tran_tpjson_policy(policy_str)
{
	var policy_data;
	var policy_json = jQuery.parseJSON(policy_str);
	var restful_json_temp;
	for(var key_ in policy_json)
	{
		policy_data = policy_json[key_];
	}
	
	var condition_count = 0;
	restful_json_temp = '{"input":{"user-id":"'+user_id+'","user-name":"'+user_name+'","user-password":"'+user_password+'","user-role":"'+user_role+'",';
    //test
	//restful_json_temp += '"operations":{"operation":[{"operation-id":"'+1+'","operation-name":"'+2+'","priority":"'+3+'","target-object":"'+5+'","condition":[';
	//alert(policy_data["apply_entity_id"].substring(5,policy_data["apply_entity_id"].length));
	restful_json_temp += '"operations":{"operation":[{"operation-id":"'+policy_data["policy_id"]+'","operation-name":"'+policy_data["policy_name"]+'","target-object":"'+find_flow_id(policy_data["apply_entity_id"].substring(5,policy_data["apply_entity_id"].length))+'","priority":"'+policy_data["policy_priority"]+'","condition":[';
	 //console.log(policy_data["condition"][0]);
	 for(var key_2 in policy_data["condition"])
	 {
		var condition_array = policy_data["condition"][key_2].split(' ');
		//console.log(condition_array);
		restful_json_temp += '{"condition-segement-id":"'+condition_array[5].substring(1,condition_array[5].length-1)+'","condition-parameter-name": "'+condition_array[0]+'","condition-parameter-match-pattern":"'+condition_array[1]+'","condtion-parameter-target-value":{"int-value":"'+condition_array[2]+'"},"condition-relation-operator":"'+condition_array[3]+'","order":"'+condition_array[4]+'"},';
		condition_count++;	 
	 }
	 if(condition_count>0)
	 {
	 	restful_json_temp = restful_json_temp.substring(0,(restful_json_temp.length-1));
	 	restful_json_temp+='],';
	 }
		
	else
		restful_json_temp = restful_json_temp.substring(0,(restful_json_temp.length-13));

	//test
    //restful_json_temp += '{"condition-segement-id":"'+1+'","condition-parameter-name": "'+2+'","condition-parameter-match-pattern":"'+3+'","condtion-parameter-target-value":{"int-value":"'+4+'"},"condition-relation-operator":"'+5+'","order":"'+6+'"}';

	restful_json_temp += '"action":[{"action-name":"'+policy_data["action"]+'","parameter-values":{"string-value":[{"value":"'+find_node_id(policy_data["data"])+'","order":"'+0+'"}]},"order":"0"}]';
	restful_json_temp += '}]}}}';
	console.log(restful_json_temp);
	return restful_json_temp;
}
//#####################################################################################################################//

//#################################################### CREATE_Delete_Json##############################################//
function tran_tpjson_delete(delete_id)
{
	var restful_json_temp;
	
	restful_json_temp = '{"input":{"user-id":"'+user_id+'","user-name":"'+user_name+'","user-password":"'+user_password+'","user-role":"'+user_role+'",';
restful_json_temp += '"objects":{node":["'+delete_id+'"]';
	restful_json_temp += '}}}';
	return restful_json_temp;
}
//#####################################################################################################################//


//#################################################### CREATE_Query_Json##############################################//
function tran_tpjson_query(query_str)
{
	var query_data = jQuery.parseJSON(query_str);
	var restful_json_temp;
	
	restful_json_temp = '{"input":{"user-id":"'+user_id+'","user-name":"'+user_name+'","user-password":"'+user_password+'","user-role":"'+user_role+'",';
	restful_json_temp += '"query-condition":[{"query-condition-id":"'+1+'","query-condition-name":"'+2+'","query-intent-type":"'+3+'","query-condition-match-pattern":"'+4+'","query-condition-match-pattern":{"int-value":"'+5+'"}}]';
	restful_json_temp += '}}';
	return restful_json_temp;
}
//#####################################################################################################################//


//######################################### CREATE_Entity_Json(node,connection,flow)###################################//
function find_node_id(node_name)
{
	var SI_name = jQuery(".NE_up #sel_1").children('option:selected').val();
	for (var find_cursor = 0; find_cursor<localStorage.length;find_cursor++)
	{
		if(localStorage.key(find_cursor).indexOf(SI_name+'__') == -1)
			continue;
		else if(localStorage.key(find_cursor).indexOf('node_') > -1 )
		{
			var find_json_temp = jQuery.parseJSON(localStorage.getItem(localStorage.key(find_cursor)));
			for(var key in find_json_temp)
			{
				if(find_json_temp[key]["node_name"] == node_name)
					return find_json_temp[key]["node_id"];
			}
		}	
	}
	alert("no node id name match!!!");
}
function find_flow_id(flow_name)
{
	var SI_name = jQuery(".NE_up #sel_1").children('option:selected').val();
	for (var find_cursor = 0; find_cursor<localStorage.length;find_cursor++)
	{
		if(localStorage.key(find_cursor).indexOf(SI_name+'__') == -1)
			continue;
		else if(localStorage.key(find_cursor).indexOf('__flow_') > -1 )
		{
			var find_json_temp = jQuery.parseJSON(localStorage.getItem(localStorage.key(find_cursor)));
			for(var key in find_json_temp)
			{
				if(find_json_temp[key]["flow_name"] == flow_name)
					return find_json_temp[key]["flow_id"];
			}
		}	
	}
	alert("no flow id name match!!!");
}

function tran_tojson_node(node_data)
{
	var node_json;
	console.log(node_data);
	var host_subnode=[];//sub node 

	node_json = '{"input":{"user-id":"'+user_id+'","user-name":"'+user_name+'","user-password":"'+user_password+'","user-role":"'+user_role+'",';
	node_json += '"objects":{"node":[{"node-id":"'+node_data["node_id"]+'","node-name":"'+node_data["node_name"]+'","node-type":"'+node_data["node_type"]+'"';
	
	if(node_data["sub-node"] != null && node_data["sub-node"]["sub-node"].length > 0)
	{
		node_json += ',"sub-node":[';
		var sub_count = 0;
		for(var value in node_data["sub-node"]["sub-node"])
		{
			if(host_id[node_data["sub-node"]["sub-node"][value].split(":")[1]] != null)
			{
				host_subnode.push(node_data["sub-node"]["sub-node"][value].split(":")[1]);
			}
			var id_temp;
			//node_json += '{"node-id":"'+host_id[node_data["sub-node"]["sub-node_host"][value]]+'"},';
			if(host_id[node_data["sub-node"]["sub-node"][value].split(":")[1]] == null && find_node_id(node_data["sub-node"]["sub-node"][value].split(":")[1]) ==null)
				continue;
			else
			{
				if (host_id[node_data["sub-node"]["sub-node"][value].split(":")[1]] != null)
					id_temp = host_id[node_data["sub-node"]["sub-node"][value].split(":")[1]];
				else
					id_temp = find_node_id(node_data["sub-node"]["sub-node"][value].split(":")[1]);
			}
			node_json += '{"node-id":"'+id_temp+'",';
			if(node_data["node_type"]=="chain-group")
			{
			 node_json+='"order":"'+sub_count+'"},';
		     }
		     else
		     {
		     	 node_json+='"order":"0"},';
		     }
			sub_count++;
		}
		if(sub_count>0)
			node_json = node_json.substring(0,node_json.length-1);
		node_json += ']';
	}
	node_json += ',"property":[';
	var property_count = 0;
	for(var property_cursor in node_data["property"])
	{	
		if(node_data["property"][property_cursor] != '')
		{
			node_json += '{"property-name":"'+property_cursor+'","property-values":{"string-value":[{"value":"'+node_data["property"][property_cursor]+'","order":"0"}]}},'
			property_count++;
		}
		
	}
	if(property_count > 0)
	{
		node_json = node_json.substring(0,node_json.length-1);
		node_json += ']';
	}
	else
		node_json = node_json.substring(0,node_json.length-13);

	node_json += '}]}}}';
	console.log(node_json);
    
    var host_json_str=[];
	for(i in host_subnode){
	var host_json = '{"input":{"user-id":"'+user_id+'","user-name":"'+user_name+'","user-password":"'+user_password+'","user-role":"'+user_role+'",';
	 host_json += '"objects":{"node":[{"node-id":"'+host_id[host_subnode[i]]+'","node-name":"'+host_subnode[i]+'","node-type":"host"}]}';
	 host_json +="}}";
	 host_json_str.push(host_json)
	}
	host_json_str.push(node_json);

	return host_json_str;
}

function tran_tojson_connection(connection_data)
{
	var connection_json;
	
	connection_json = '{"input":{"user-id":"'+user_id+'","user-name":"'+user_name+'","user-password":"'+user_password+'","user-role":"'+user_role+'",';
	connection_json += '"objects":{"connection":[{"connection-id":"'+connection_data["connection_id"]+'","connection-name":"'+connection_data["connection_name"]+'","connection-type":"'+connection_data["connection_type"]+'",';
	

	if(connection_data["connection_type"] == 'p2p')
	{
	var para1 = connection_data["End-nodes"]["one_node_name"].split(":");
	var c1="";
	  if(para1.length>1)
		{
			c1=para1[1];
		 }
		 else{
            c1=para1[0];
		 }
     
     var para2 = connection_data["End-nodes"]["other_node_name"].split(":");
	  var c2="";
	   if(para1.length>1)
		{
			c2=para2[1];
		 }
		 else{
            c2=para2[0];
		 }
		connection_json += '"end-node":[ {"node-id":"'+find_node_id(c1)+'","order":"0"},{"node-id":"'+find_node_id(c2)+'","order":"0"}';
	}
	else if(connection_data["connection_type"] == 'p2mp') 
	{
		var para1 = connection_data["End-nodes"]["one_node_name"].split(":");
	    var c1="";
	    if(para1.length>1)
		  {
			c1=para1[1];
		   }
		  else{
            c1=para1[0];
		   }
     
         var para2 =connection_data["End-nodes"]["other_node_name"][p2mp_cursor].split(":");
	     var c2="";
	    if(para1.length>1)
		{
			c2=para2[1];
		 }
		 else{
            c2=para2[0];
		  }

		connection_json += '"end-node":[{"node-id":"'+find_node_id(c1)+'","order":"0"},';
		for(var p2mp_cursor in connection_data["End-nodes"]["other_node_name"])
		{
			connection_json += '{"node-id":"'+find_node_id(c2)+'","order":"0"},';
		}
		connection_json = connection_json.substring(0,connection_json.length-1);
	}
	else if(connection_data["connection_type"] == 'mesh')
	{
		var para1 = connection_data["node"][mesh_cursor].split(":");
	    var c1="";
	    if(para1.length>1)
		  {
			c1=para1[1];
		   }
		  else{
            c1=para1[0];
		   }
		connection_json += '"end-node":[';
		for(var mesh_cursor in connection_data["node"])
		{
			connection_json += '{"node-id":"'+find_node_id(c1)+'","order":"0"},';
		}
		connection_json = connection_json.substring(0,connection_json.length-1);
	}	
	else if(connection_data["connection_type"] == 'chain')
	{
		connection_json += '"end-node":[';
		for(var chain_cursor in connection_data["node"])
		{
			connection_json += '{"node-id":"'+find_node_id(connection_data["node"][chain_cursor].split(":")[1])+'","order":"0"},';
		}
		connection_json = connection_json.substring(0,connection_json.length-1);
	}		

	connection_json += ']';

	
	if(connection_data["property"]["bandwidth"]!="")
	{
	connection_json +=',"property":[';
    }
	for(var key in connection_data["property"])
	{

		if(connection_data["property"][key] != '')
			connection_json += '{"property-name":"'+key+'","property-values":{"int-value":[{"value":"'+connection_data["property"][key]+'","order":"0"}]}},';
		
	}
	connection_json = connection_json.substring(0,connection_json.length-1);
	connection_json += ']}]}}}';
	console.log(connection_json);
	return connection_json;
}

function tran_tojson_flow(flow_data)
{
	var flow_json;
	
	flow_json = '{"input":{"user-id":"'+user_id+'","user-name":"'+user_name+'","user-password":"'+user_password+'","user-role":"'+user_role+'",';
	flow_json += '"objects":{"flow":[{"flow-id":"'+flow_data["flow_id"]+'","flow-name":"'+flow_data["flow_name"]+'","match-item":[';
	for(var key in flow_data["match_items"])
	{
		if(flow_data["match_items"][key] != '')
			flow_json += '{"match-item-name":"'+key+'","match-item-value":{"string-value":"'+flow_data["match_items"][key]+'"}},';
	}
	flow_json = flow_json.substring(0,flow_json.length-1);
	flow_json += ']}]}}}';
	console.log(flow_json);
	return flow_json;
}
//#####################################################################################################################//



/* function test()
{
	console.log(tran_tojson_user());
	tran_tojson_delete('c0ae82a9-8fa0-9ab2-4bff-8c2151160201');
	tran_tpjson_entity('{"mms__node_inet1":{"Entity_Type":"node","node_id":"c0ae82a9-8fa0-9ab2-4bff-8c2151160201","node_name":"inet1","node_type":"internet","property":{"location":"WAN_port1","ipprefix":"asdas"}}}');
    tran_tpjson_entity('{"mms__connection_conn1":{"Entity_Type":"connection","connection_id":"eb307116-dc28-a84c-5539-62cb0cac9516","connection_name":"conn1","connection_type":"p2p","End-nodes":{"one_node_name":"inet","other_node_name":"inet1"},"property":{"bandwidth":"21","latency":"","Jitter":""}}}');
	tran_tpjson_entity('{"mms__flow_f3":{"Entity_Type":"flow","flow_id":"41cf1ba9-2ab9-f91a-30ec-d1f0e29553c7","flow_name":"f3","match_items":{"src_node":"g1","dest_node":"inet","src_ip":"","dest_ip":"","src_port":"","dest_port":"","protocol":"","vlanid":"30","bidirect":"true"}}}');
} */
