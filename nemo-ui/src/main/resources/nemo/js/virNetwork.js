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
			alert("Get Virtual Data Error!");
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

var virtual_nodes=[];
function create_virtual_topo(topo_data,intentinfo,mappinginfo)
{
	nodes_vir.clear();
	edges_vir.clear();

    console.log(topo_data);
	var vitrual_network=topo_data;
	if(vitrual_network!=null)
	{	
		var vitrual_nodes=vitrual_network["virtual-nodes"]["virtual-node"];
		for(var i in vitrual_nodes)
		{
			virtual_nodes[vitrual_nodes[i]["node-id"]]=(+i+1);		
			nodes_vir.add({
            id: +i+1,
            label: vitrual_nodes[i]["node-type"],
            group: vitrual_nodes[i]["node-type"],
            radius:20
        });

		}
       console.log(virtual_nodes);
       var vitrual_links=vitrual_network["virtual-links"]["virtual-link"];
       for(var j in vitrual_links){
       	   edges_vir.add({
            id: +j+1,
            from: virtual_nodes[vitrual_links[j]["src-node-id"]],
            to: virtual_nodes[vitrual_links[j]["dest-node-id"]],
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
	var intentNodes=[];
		for (var i in user_nodes) {
			intentNodes[user_nodes[i]['node-id']]=[user_nodes[i]['node-type'],user_nodes[i]['node-name']];
		};
	var mapping_nodes=mappingData['intent-vn-mapping-result'];
	var mappingNodes=[];
		for (var i in mapping_nodes) {
			if(mapping_nodes[i]['intent-type']=='node')
			{
				mappingNodes[mapping_nodes[i]['intent-id']]=mapping_nodes[i]['virtual-resource'][0]['parent-virtual-resource-entity-id'];
			}
		};
	console.log(intentNodes);
	console.log(mappingNodes);
	var nodelen= nodes_vir.get().length;
	for(var item in intentNodes){
		virtual_nodes[item]=nodelen+1;
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
	for(var item in mappingNodes){
		var edgeslen=edges_vir.get().length;
		 edges_vir.add({
            id: ++edgeslen,
            from: virtual_nodes[item],
            to: virtual_nodes[mappingNodes[item]],
            length:150
        });
	}
}




