//var phy_hosts; move to nemo_main.js
var phy_links,phy_nodes;
var host_count,node_count_phy,link_count;
var Node_phy_Id = 0;
var Edge_phy_Id = 0;
nodes_phy = new vis.DataSet();
edges_phy = new vis.DataSet();
var host_id = [];
var host_name = [];
var phy_host_ip = [];
var phy_host_mac = [];
var node_map_id = [];

jQuery(document).ready(function ($) {

	$("#query_topo").click(function(){
	       var topo_data = 0;
		$.ajax({
			url: "/restconf/operational/generic-physical-network:physical-network/", 
			type:"GET",
			dataType:"json",
			success: function(data){
				if(data != null)
				{
					topo_data = data;
					analy_topo(topo_data);
					create_physical_topo();
				}
				else
					alert("No Physical Data");
			},
			error:function(data){
			alert("error");
			}
		}); 
/* 		topo_data = pyh_data_test;
		analy_topo(topo_data);
		create_physical_topo(); */
	});
   $("#query_topo").click();
});
 
function analy_topo(topo_data)
{
	phy_hosts = [];
	phy_nodes = [];
	phy_links = [];
	host_count = 0;
	node_count_phy = 0;
	link_count = 0;
	if(topo_data["physical-network"]["physical-nodes"]!=null)
	{
		for(var i in topo_data["physical-network"]["physical-nodes"]["physical-node"])
		{
			var node_data_temp = [];
			var data_temp;
			var json_temp = topo_data["physical-network"]["physical-nodes"]["physical-node"][i];
/* 			for(var key in json_temp)
			{
				data_temp = json_temp[key];
			} */
			node_data_temp["node-id"] = json_temp["node-id"];
			node_data_temp["node-type"] = json_temp["node-type"];
			phy_nodes[node_count_phy] = node_data_temp;
			node_count_phy++;
		}

	}
	if(topo_data["physical-network"]["physical-hosts"]!=null)
	{
		for(var i in topo_data["physical-network"]["physical-hosts"]["physical-host"])
		{
			//var host_name_temp = 'host' + i;
				var host_data_temp = [];
			var data_temp;
			var json_temp = topo_data["physical-network"]["physical-hosts"]["physical-host"][i];
			/* for(var key in json_temp)
			{
				data_temp = json_temp[key];
			} */
			host_data_temp["node-id"] = json_temp["node-id"];
			host_data_temp["host-name"] = json_temp["host-name"];
			host_data_temp["host-id"] = json_temp["host-id"];
			phy_hosts[host_count] = host_data_temp;
			host_count++;
			host_id[json_temp["host-name"]] = json_temp["host-id"];
			host_name[json_temp["host-id"]] = json_temp["host-name"];
				phy_host_ip[json_temp["ip-addresses"]["ip-address"][0]] = json_temp["host-name"];
			phy_host_mac[json_temp["mac-address"]] = json_temp["host-name"];
		}
	}
	if(topo_data["physical-network"]["physical-links"]!=null)
	{
		for(var i in topo_data["physical-network"]["physical-links"]["physical-link"])
		{
			var link_data_temp = [];
			var data_temp;
			var json_temp = topo_data["physical-network"]["physical-links"]["physical-link"][i];
		/* 	for(var key in json_temp)
			{
				data_temp = json_temp[key];
			} */
			link_data_temp["link-id"] = json_temp["link-id"];
			link_data_temp["src-node-id"] = json_temp["src-node-id"];
			link_data_temp["dest-node-id"] = json_temp["dest-node-id"];
			phy_links[link_count] = link_data_temp;
			link_count++;
		}
	}
}

function create_physical_topo()
{
	nodes_phy.clear();
	edges_phy.clear();
	node_map_id = [];
	Node_phy_Id = 0;
	Edge_phy_Id = 0;
	console.log(phy_nodes);
	for(var i in phy_nodes)
	{
		nodes_phy.add({
            id: ++Node_phy_Id,
            label: phy_nodes[i]["node-type"]+phy_nodes[i]["node-id"].split(":")[1],
            group: phy_nodes[i]["node-type"],
        });
		node_map_id[phy_nodes[i]["node-id"]] = Node_phy_Id;
	}
	for(var i in phy_hosts)
	{
		nodes_phy.add({
            id: ++Node_phy_Id,
            label: phy_hosts[i]["host-name"],
            image: "src/app/nemo/images/host.png",
            shape: 'image',
			fontSize: 15
        });
		var to_full_id;
		for(var node_id_cursor in phy_nodes)
		{
			if(phy_nodes[node_id_cursor]["node-id"].indexOf(phy_hosts[i]["node-id"]) > -1)
			{
				to_full_id = phy_nodes[node_id_cursor]["node-id"];
				break;
			}
		}
		edges_phy.add({
			id:++Edge_phy_Id,
			from:Node_phy_Id,
			to:node_map_id[to_full_id]
		});
		node_map_id[phy_nodes[i]["host-id"]] = Node_phy_Id;
	}
	for(var i in phy_links)
	{
	    var from_full_id;
		for(var node_id_cursor in phy_nodes)
		{
			if(phy_nodes[node_id_cursor]["node-id"].indexOf(phy_links[i]["src-node-id"]) > -1)
			{
				from_full_id = phy_nodes[node_id_cursor]["node-id"];
				break;
			}
		}
		var to_full_id;
		for(var node_id_cursor in phy_nodes)
		{
			if(phy_nodes[node_id_cursor]["node-id"].indexOf(phy_links[i]["dest-node-id"]) > -1)
			{
				to_full_id = phy_nodes[node_id_cursor]["node-id"];
				break;
			}
		}
		edges_phy.add({
			id:++Edge_phy_Id,
			from:node_map_id[from_full_id],
			to:node_map_id[to_full_id]
		});
	}
	var data = {
		nodes: nodes_phy,
		edges: edges_phy
	};
	var options3 = {
		/* physics: {
		repulsion: {
			centralGravity: 0,
			springLength: 200,//弹簧长度
			springConstant: 0,//弹簧常数
			nodeDistance: 0,
			damping: 0 //阻尼，减幅，衰减
		}}, */
		smoothCurves: false,
		stabilize: true,
		nodes: {
          // default for all nodes
			shape: 'dot', 
			fontSize:15,
			radius:16,
			fixed:true
		},
		groups:{
			switch:{
				color: {
					border: 'black',
					background: '#B0E2FF',				
				}
            },
				
			router:{
				color: {
					border: 'black',
					background: '#7FFF00',
				}	

			}
		}
		
	};
	var container = document.getElementById('graph');
	graph = new vis.Graph(container, data, options3)
}



