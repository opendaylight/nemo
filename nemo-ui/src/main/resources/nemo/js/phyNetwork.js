//var phy_hosts; move to nemo_main.js
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

var physicalData=null;
jQuery(document).ready(function ($) {
getPhysicalDatas();
if(!physicalData) return;
//analy_topo(physicalData);
//create_physical_topo();
});
 //physicalJson
 function getPhysicalDatas(){
 	var PhyData=null;
 	jQuery.ajax({
			url: "/restconf/operational/generic-physical-network:physical-network/", 
			type:"GET",
			dataType:"json",
			async:false,
			success: function(data){
				console.log(data);
				if(data != null)
				{
					PhyData=data;
					physicalData=data;
					//analy_topo(physicalData);
                    //create_physical_topo();
				}
				else
					alert("No Physical Data");
			},
			error:function(data){
			 console.log("Get Physical Data Error!");
			 // alert("Get Physical Data Error!");
			}
		}); 
 	return PhyData;
 }

 function getPhysicalData(){
 physicalData=getPhysicalDatas();
 console.log(physicalData)
 if(!physicalData) physicalData=physicalJson;
 if(!physicalData) return;
 }
 // getPhysicalData();

function createPhysicalTopo(Data){
	nodes_phy.clear();
	edges_phy.clear();
	if(!Data) Data=physicalData;
	if(!Data) return;
	var physicalHost=Data['physical-network']['physical-hosts'];
	var physicalNode=Data['physical-network']['physical-nodes']['physical-node'];
	var physicalLink=Data['physical-network']['physical-links'];
	var myHost=[];//host_id----->[vis_node_id,connected node_id,host_name]
	var myHostPort=[];//host_id----->host_port_id
	var myNode=[];//node_id----->[vis_node_id,node_type]
	var externalNode=[];//external_port_id----->[vis_node_id,node_id]
	var myLink=[];//null
	if(physicalHost){
		physicalHost=physicalHost['physical-host'];		
		for(var i in physicalHost){
			myHost[physicalHost[i]['host-id']]=[+i+1,physicalHost[i]['node-id'],physicalHost[i]['host-name']];
			myHostPort[physicalHost[i]['host-id']]=physicalHost[i]['port-id'];
			nodes_phy.add({
				id: +i+1,
                label: physicalHost[i]["host-name"],
                image: "src/app/nemo/images/host.png",
                shape: 'image',
			    fontSize: 15
			});
		}
	}
	console.log(myHost);
	if(physicalNode){
		var nodeslen=nodes_phy.get().length;
		for(var i in physicalNode){
			myNode[physicalNode[i]['node-id']]=[nodeslen+1,physicalNode[i]['node-type'],''];
			nodes_phy.add({
				id: ++nodeslen,
                label: physicalNode[i]["node-type"]+physicalNode[i]["node-id"].split(":")[1],
                group: physicalNode[i]["node-type"],
			});
			var physicalPort=physicalNode[i]['physical-port'];
			if(physicalPort){
				for(var item in physicalPort){
					if(physicalPort[item]['port-type']=='external')
					{
					var flag=true;
					for(var hostPort in myHostPort){
						if(physicalPort[item]['port-id']==myHostPort[hostPort])
							flag=false;
					}
					if(flag)	
					externalNode[physicalPort[item]['port-id']]=[physicalNode[i]['node-id']];
				    }
				}
			}
		}
	}
	console.log(myNode);
	console.log(externalNode);

	var externalnodecount=0;
    for(var x in externalNode){externalnodecount++;}
    if(externalnodecount>0){
    	var nodeslen=nodes_phy.get().length;
    	for(var i in externalNode){
    		externalNode[i]=[nodeslen+1,externalNode[i][0]];
    		nodes_phy.add({
				id: ++nodeslen,
                label: 'ext-network',
                image: "src/app/nemo/images/ext-group.png",
                shape: 'image',
			});
    	}
    	console.log(externalNode);
    	var edgeslen=edges_phy.get().length;
    	for(var i in externalNode){
    		edges_phy.add({
				id: ++edgeslen,
                from:externalNode[i][0],
			    to:myNode[externalNode[i][1]][0],
			    length:150,
			    width: 1
			});
    	}
    }
	if(physicalLink){
		physicalLink=physicalLink['physical-link'];
		var edgeslen=edges_phy.get().length;
		for(var i in physicalLink){
			// myLink[]
			edges_phy.add({
		     id:++edgeslen,
			from:myNode[physicalLink[i]['src-node-id']][0],
			to:myNode[physicalLink[i]['dest-node-id']][0],
			length:150,
			width: 2
			});
		}
	}

    var hostcount=0;
	for(var x in physicalHost){hostcount++;}
	if(hostcount>0){
		var edgeslen=edges_phy.get().length;
		console.log(edgeslen);
		for(var i in myHost){
			// myLink[]
			edges_phy.add({
		    id:++edgeslen,
			from:myHost[i][0],
			to:myNode[myHost[i][1]][0],
			length:150,
			width: 1
			});
		}
	}

var data = {
		nodes: nodes_phy,
		edges: edges_phy
	};

var options_phy = {
		/* physics: {
		repulsion: {
			centralGravity: 0,
			springLength: 200,//弹簧长度
			springConstant: 0,//弹簧常数
			nodeDistance: 0,
			damping: 0 //阻尼，减幅，衰减
		}}, */
		smoothCurves: false,
		stabilize: false,
		nodes: {
          // default for all nodes
			shape: 'dot', 
			fontSize:16,
			radius:23,
			fixed:true
		},
		edges:{
			// length:200,
			width: 1
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
	console.log(jQuery('#phy_graph').width());
	jQuery('#phy_graph').width(800).height(500);
	console.log(jQuery('#phy_graph').width());
	var container = document.getElementById('phy_graph');
	if(!container) return;
	var graph = new vis.Graph(container, data, options_phy);
}

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
			if(json_temp['ip-addresses'])
			phy_host_ip[json_temp["ip-addresses"]["ip-address"][0]] = json_temp["host-name"];
		    if(json_temp['mac-address'])
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
			to:node_map_id[to_full_id],
			length:150
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
			to:node_map_id[to_full_id],
			length:150,
			width: 2
		});
	}
	// console.log(1);
	var data = {
		nodes: nodes_phy,
		edges: edges_phy
	};
	var options_phy = {
		/* physics: {
		repulsion: {
			centralGravity: 0,
			springLength: 200,//弹簧长度
			springConstant: 0,//弹簧常数
			nodeDistance: 0,
			damping: 0 //阻尼，减幅，衰减
		}}, */
		smoothCurves: false,
		stabilize: false,
		nodes: {
          // default for all nodes
			shape: 'dot', 
			fontSize:16,
			radius:23,
			fixed:true
		},
		edges:{
			// length:200,
			width: 1
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
	console.log(jQuery('#phy_graph').width());
	jQuery('#phy_graph').width(800).height(500);
	console.log(jQuery('#phy_graph').width());
	var container = document.getElementById('phy_graph');
	if(!container) return;
	var graph = new vis.Graph(container, data, options_phy)
}

var creataPhysicalTables={
createPhyicalNodeTable:function (id,Data){
	if(!Data) Data=physicalData;
	if(!Data) return;
	var physicalnodes=[];//physical_node_id---->[physical_node_type,[internal physical port number,external physical port number]]
 	var Mynode=Data['physical-network']['physical-nodes']['physical-node'];
 	for(var i in Mynode){
 		physicalnodes[Mynode[i]['node-id']]=[Mynode[i]['node-type']];
 	    var physicalPorts=Mynode[i]['physical-port'];
 	    if(!physicalPorts) {physicalnodes[Mynode[i]['node-id']].push(['','']);continue;}
 	    var interPort=exterPort=0;
 	    for(var port in physicalPorts){
 	    	if(physicalPorts[port]['port-type']=='internal') interPort++;
 	    	if(physicalPorts[port]['port-type']=='external') exterPort++;	    	
 	    }
 	    physicalnodes[Mynode[i]['node-id']].push([interPort,exterPort]);
 	}
 	console.log(physicalnodes);
 	jQuery("#"+id).find('tr').remove();
 	for(var item in physicalnodes){
 		var $tr='<tr><td>'+item+'</td><td>'+physicalnodes[item][0]+'</td>'
 		$tr+='<td>'+physicalnodes[item][1][0]+'</td><td>'+physicalnodes[item][1][1]+'</td>'
 		$tr+='</tr>'
 		jQuery("#"+id).append($tr);
 	}
 	var node_count = jQuery("#"+id).find('tr').length;
 	console.log("node_count:",node_count);
 	if(!node_count) return;
 	if(node_count<10){
 		jQuery("div.tableBodyContainer:first").height(node_count*22+5);
 	}
 	else{
 		jQuery("div.tableBodyContainer:first").height(200);
 	}
},
createPhyicalLinkTable:function (id,Data){
	if(!Data) Data=physicalData;
	if(!Data) return;
	var physaicallinks=[];
	if(!Data['physical-network']['physical-links']) return;
 	var mylink=Data['physical-network']['physical-links']['physical-link'];
 	for(var i in mylink){
 		if(!mylink[i]['metric'])
 		physaicallinks[mylink[i]['link-id']]=[mylink[i]['src-node-id'],mylink[i]['dest-node-id'],'',mylink[i]['bandwidth'],mylink[i]['delay']];
 	    else
 		physaicallinks[mylink[i]['link-id']]=[mylink[i]['src-node-id'],mylink[i]['dest-node-id'],mylink[i]['metric'],mylink[i]['bandwidth'],mylink[i]['delay']];

 	}
 	console.log(physaicallinks);
 	jQuery("#"+id).find('tr').remove();
 	for(var item in physaicallinks){
 		var $tr='<tr><td>'+item+'</td><td>'+physaicallinks[item][0]+'</td>'
 		 $tr+='<td>'+physaicallinks[item][1]+'</td><td>'+physaicallinks[item][2]+'</td>';
 		 $tr+='<td>'+physaicallinks[item][3]+'kbps'+'</td><td>'+physaicallinks[item][4]+'ms'+'</td>';
 		 $tr+='</tr>'
 		jQuery("#"+id).append($tr);
 	}
}
}


