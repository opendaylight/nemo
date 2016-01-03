nodes_vir = new vis.DataSet();
edges_vir = new vis.DataSet();

var VirtualDatas='';

 function getVirtualInfos(){
 	 var virData=null;
		jQuery.ajax({
			url: "/restconf/config/generic-virtual-network:virtual-networks/", 
			type:"GET",
			dataType:"json",
			async: false,
			success: function(data){
				if(data != null)
				{
					virData = data;
					VirtualDatas=data;
					console.log(VirtualDatas);
				}
				else
					alert("No Virtual Data");
			},
			error:function(data){
			// alert("Get Virtual Data Error!");
			console.log("Get Virtual Data Error!");
			}
		});
	 return virData;
 }
  getVirtualInfos();
 function getVirtualDatas(){
 	var Datas=getVirtualInfos();
 	if(!Datas){
 	Datas=virtualJson;
 	}
 	VirtualDatas=Datas;
 	// console.log(VirtualDatas);
 	//return virtualDatas;
 }
// getVirtualDatas();
function getVirtualInfoById(user_id,Data){
	if(!Data) Data=VirtualDatas;
	if(!Data) return;
	if(typeof(Data)=='string'){
    var user_json = JSON.parse(Data);
    }
    else
    {
    var user_json = Data;
    }

    var user_info = user_json['virtual-networks']['virtual-network'];
 	for(var item in user_info){
 		if(user_id==user_info[item]['user-id']){
 			return user_info[item];
 		}
 	}
}

var virtual_nodes=[];//virtual_node_id----->[vid_node_id,virtual_node_name]
function create_virtual_topo(topo_data,intentinfo,mappinginfo)
{
	nodes_vir.clear();
	edges_vir.clear();

    console.log(topo_data);
	var vitrual_network=topo_data;
	if(vitrual_network!=null)
	{	
		var vitrual_nodes_js=vitrual_network["virtual-nodes"]["virtual-node"];
		for(var i in vitrual_nodes_js)
		{
			virtual_nodes[vitrual_nodes_js[i]["node-id"]]=[(+i+1),vitrual_nodes_js[i]["node-type"]+(+i+1)];		
			nodes_vir.add({
            id: +i+1,
            label: vitrual_nodes_js[i]["node-type"]+(+i+1),
            group: vitrual_nodes_js[i]["node-type"],
            radius:20
        });

		}
       console.log(virtual_nodes);
       var vitrual_links=vitrual_network["virtual-links"]["virtual-link"];
       for(var j in vitrual_links){
       	   edges_vir.add({
            id: +j+1,
            from: virtual_nodes[vitrual_links[j]["src-node-id"]][0],
            to: virtual_nodes[vitrual_links[j]["dest-node-id"]][0],
            length:150,
            width:2,
            color:{color:'ff4e00',highlight:'ff4e00',hover:'ff4e00'}
        });
       }
	}

drawOthers(null,intentinfo,mappinginfo);
console.log(virtual_nodes);
	var data= {
		nodes: nodes_vir,
		edges: edges_vir
	};
	var options = {
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
			fontSize:15,
			shape: 'dot',            
			radius:23,
			fixed:true
		},
		groups:{
			vswitch:{				
				color: {
					border: 'black',
					background: '#B0E2FF',				
				}
            },				
			vrouter:{
				color: {
					border: 'black',
					background: '#7FFF00',
				}	
			},
			fw:{
				//shape:'box',
				color: {
					border: 'black',
					background: '#EEEE00',
				}	
			},
			lb:{
				//shape:'box',
				color: {
					border: 'black',
					background: '#C63300',
				}	
			},
			cache:{
				//shape:'box',
				color: {
					border: 'black',
					background: '#FF00FF',
				}	
			}
		}
		
	};

    console.log(nodes_vir.get().length);
	var container = document.getElementById('vir_graph');
	var graph = new vis.Graph(container, data, options);
}

function drawOthers(virtualData,intentData,mappingData){
	console.log();
	var user_nodes=intentData['objects']['node'];
	var intentNodes=[];//intent_node_id(physical host or node id)------>[intent_node_type,intent_node_name]
		for (var i in user_nodes) {
			intentNodes[user_nodes[i]['node-id']]=[user_nodes[i]['node-type'],user_nodes[i]['node-name']];
		};
	var mapping_nodes=mappingData['intent-vn-mapping-result'];
	var mappingNodes=[];//intent-id(physical host or node id)------>virtual_node_id
		for (var i in mapping_nodes) {
			if(mapping_nodes[i]['intent-type']=='node')
			{
				if(mapping_nodes[i]['virtual-resource'][0]['parent-virtual-resource-entity-id'])
				mappingNodes[mapping_nodes[i]['intent-id']]=mapping_nodes[i]['virtual-resource'][0]['parent-virtual-resource-entity-id'];
			}
		};
	console.log(intentNodes);
	console.log(mappingNodes);
	var nodelen= nodes_vir.get().length;
	for(var item in intentNodes){
		virtual_nodes[item]=[parseInt(nodelen+1),'external-node'];
		if(intentNodes[item][0]=='host'){
			nodes_vir.add({
            id: ++nodelen,
            label: ''+intentNodes[item][1],
            image: "src/app/nemo/images/host.png",
            shape: 'image',
			fontSize: 15
        });
		}
		else if(intentNodes[item][0]=='fw'){
			nodes_vir.add({
            id: ++nodelen,
            label: ''+intentNodes[item][1],
            group: 'fw',
            image: "src/app/nemo/images/fw.png",
            shape: 'image',
			fontSize: 15
        });
		}
		else if(intentNodes[item][0]=='lb'){
			nodes_vir.add({
            id: ++nodelen,
            label: ''+intentNodes[item][1],
            group: 'lb',
            image: "src/app/nemo/images/lb.png",
            shape: 'image',
			fontSize: 15
        });
		}
		else if(intentNodes[item][0]=='cache'){
			nodes_vir.add({
            id: ++nodelen,
            label: ''+intentNodes[item][1],
            group: 'cache',
            image: "src/app/nemo/images/cache.png",
            shape: 'image',
			fontSize: 15
        });
		}
		else if(intentNodes[item][0]=='ext-group'){
			nodes_vir.add({
            id: ++nodelen,
            label: ''+intentNodes[item][1],
            image: "src/app/nemo/images/ext-group.png",
            shape: 'image',
			fontSize: 15
        });
		}
		else
			{
				delete virtual_nodes[item];
			}
	}
	console.log(virtual_nodes);
	for(var item in mappingNodes){
		// console.log(virtual_nodes[item]);
		if(!virtual_nodes[item]) continue;
		var edgeslen=edges_vir.get().length;
		 edges_vir.add({
            id: ++edgeslen,
            from: virtual_nodes[item][0],
            to: virtual_nodes[mappingNodes[item]][0],
            length:150
        });
	}
}

var creataVirtualTables={
createVirtualNodeTable:function (id,Data){
	// if(!Data) Data=VirtualDatas;
	if(!Data) return;
	var virtualnodes=[];
	// virtualnodes=virtual_nodes;
	virtualnodes=[];
	var virtualNodes=Data['virtual-nodes']['virtual-node'];
	console.log(virtualnodes);
	for(var item in virtualNodes){
		//virtualnodes[virtualNodes[item]['node-id']]=[virtual_nodes[virtualNodes[item]['node-id']]]
		var virTableInfo={};
		virTableInfo.virtual_node_id=virtualNodes[item]['node-id'];
		virTableInfo.virtual_node_name=virtual_nodes[virtualNodes[item]['node-id']][1];
		virTableInfo.internal_port_number='';
		virTableInfo.external_port_number='';
		var virtualPort=virtualNodes[item]['virtual-port'];
		if(!virtualPort){virtualnodes.push(virTableInfo);continue;}
		var interPort=exterPort=0;
		for(var i in virtualPort){
			if(virtualPort[i]['port-type']=='internal') interPort++;
			if(virtualPort[i]['port-type']=='external') exterPort++;
		}
		virTableInfo.internal_port_number=interPort;
		virTableInfo.external_port_number=exterPort;
		virtualnodes.push(virTableInfo);

	}
		console.log(virtualnodes);
 	// var Mynode=Data['virtual-network']['virtual-nodes']['virtual-node'];
 	// for(var i in Mynode){
 	// 	if(true)
 	// 	virtualnodes[Mynode[i]['node-id']]=Mynode[i]['node-type'];
 	// }
 	// console.log(virtualnodes);
 	jQuery("#"+id).find('tr:gt(0)').empty();
 	for(var item in virtualnodes){		
 		// if(virtualnodes[item][1]!='external-node'){}

 	    var $tr='<tr><td title='+virtualnodes[item].virtual_node_id+'>'+virtualnodes[item].virtual_node_id+'</td><td>'+virtualnodes[item].virtual_node_name+'</td>'
 		$tr+='<td>'+'virtual '+virtualnodes[item].virtual_node_name.substring(1,virtualnodes[item].virtual_node_name.length-1)+'</td>'
 		$tr+='<td>'+virtualnodes[item].internal_port_number+'</td><td>'+virtualnodes[item].external_port_number+'</td>'
 		$tr+='</tr>'
 		jQuery("#"+id).append($tr);
 	}
 	var node_count = jQuery("#"+id).find('tr').length;
 	console.log("node_count:",node_count);
 	if(!node_count) return
 	if(node_count<10){
 		jQuery("div.tableBodyContainer:eq(2)").height(node_count*22+5);
 	}
 	else{
 		jQuery("div.tableBodyContainer:eq(2)").height(200);
 	}
},

createVirtualLinkTable:function (id,Data){
	// if(!Data) Data=VirtualDatas;
	if(!Data) return;
	var virtuallinks=[];//virtual_link_id---->[src-node-id,dest-node-id,metric,bandwidth,delay]

 	var mylink=Data['virtual-links']['virtual-link'];
 	for(var i in mylink){
 		if(!mylink[i]['metric'])
 		virtuallinks[mylink[i]['link-id']]=[mylink[i]['src-node-id'],mylink[i]['dest-node-id'],'',mylink[i]['bandwidth'],mylink[i]['delay']];
 	    else
 		virtuallinks[mylink[i]['link-id']]=[mylink[i]['src-node-id'],mylink[i]['dest-node-id'],mylink[i]['metric'],mylink[i]['bandwidth'],mylink[i]['delay']];

 	}
 	console.log(virtuallinks);
 	jQuery("#"+id).find('tr:gt(0)').empty();
 	for(var item in virtuallinks){
 		var $tr='<tr><td title='+item+'>'+item+'</td><td>'+virtual_nodes[virtuallinks[item][0]][1]+'-'+virtual_nodes[virtuallinks[item][1]][1]+'</td>'
 		 $tr+='<td>'+virtual_nodes[virtuallinks[item][0]][1]+'<td>'+virtual_nodes[virtuallinks[item][1]][1]+'</td>';
 		 $tr+='<td>'+virtuallinks[item][3]+'kbps'+'</td><td>'+virtuallinks[item][4]+'ms'+'</td>';
 		 $tr+='</tr>'
 		jQuery("#"+id).append($tr);
 	}
}
}



