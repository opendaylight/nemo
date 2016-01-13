var MappingResults = '';

//intent-vn MappingResults
function getMappingResults() {
	var MappingData = null;
	jQuery.ajax({
		url: "/restconf/config/intent-mapping-result:intent-vn-mapping-results/",
		type: "GET",
		dataType: "json",
		async: false,
		success: function(data) {
			if (data != null) {
				MappingData = data;
				MappingResults = data;
				console.log(MappingData);
			} else
				alert("No intent-vn Mapping Data");
		},
		error: function(data) {
			console.log("Get intent-vn Mapping Data Error!");
			// alert("Get intent-vn Mapping Data Error!");
		}
	});
	return MappingData;
}

function getMappingDatas() {
	var Datas = getMappingResults();
	if (!Datas) {
		Datas = VnMappingJson;
	}
	MappingResults = Datas;
	//return virtualDatas;
}

getMappingResults();
// getMappingDatas();
function getMappingResultById(user_id, Data) {
	if (!Data) Data = MappingResults;
	if (!Data) return;
	if (typeof(Data) == 'string') {
		var user_json = JSON.parse(Data);
	} else {
		var user_json = Data;
	}
	console.log(user_json);
	var user_info = user_json['intent-vn-mapping-results']['user-intent-vn-mapping'];
	for (var item in user_info) {
		if (user_id == user_info[item]['user-id']) {
			return user_info[item];
		}
	}
}

//vn-pn MappingResults
var PnMappingResults = '';

function getPnMappingResults() {
	var MappingData = null;
	jQuery.ajax({
		url: "/restconf/config/intent-mapping-result:vn-pn-mapping-results/",
		type: "GET",
		dataType: "json",
		async: false,
		success: function(data) {
			if (data != null) {
				MappingData = data;
				PnMappingResults = data;
				console.log(PnMappingResults);
			} else
				alert("No vn-pn Mapping Data");
		},
		error: function(data) {
			console.log("Get vn-pn Mapping Data Error!");
			// alert("Get vn-pn Mapping Data Error!");
		}
	});
	return MappingData;
}
getPnMappingResults();
function getPnMappingResultById(user_id, Data) {
	if (!Data) Data = PnMappingResults;
	if (!Data) return;
	if (typeof(Data) == 'string') {
		var user_json = JSON.parse(Data);
	} else {
		var user_json = Data;
	}
	console.log(user_json);
	var user_info = user_json['vn-pn-mapping-results']['user-vn-pn-mapping'];
	for (var item in user_info) {
		if (user_id == user_info[item]['user-id']) {
			return user_info[item];
		}
	}
}

var createMappingTables = {
		//mainData(virtualData)
		createVirtualNodeTable: function(id, Data) {
			if (!Data) return;
			console.log(Data);
			var virtualnodes = [];
			var Mynode = Data['virtual-nodes']['virtual-node'];
			for (var i in Mynode) {
				if (true)
					virtualnodes[Mynode[i]['node-id']] = Mynode[i]['physical-resource-requirement'][0]['attribute-value']['string-value'];
			}
			console.log(virtualnodes);
			// jQuery("#" + id).find('tr:gt(0)').empty();
			var count=0;
			for(var x in virtual_nodes){count++;}
			if(count==0) return;
			for (var i in virtualnodes) {
				var $tr = '<tr><td>' + virtual_nodes[i][1] + '</td><td>' + virtualnodes[i] + '</td></tr>'
				jQuery("#" + id).append($tr);
			}
			var node_count = jQuery("#"+id).find('tr').length;
 			console.log("node_count:",node_count);
 			if(!node_count) return
 			if(node_count<10){
 				jQuery("div.tableBodyContainer:eq(4)").height(node_count*22+5);
 			}
 			else{
 				jQuery("div.tableBodyContainer:eq(4)").height(200);
 			}
		},

		PathId: [], //virtual-link-id--->physical-path-id

		//virtualData---->virtul-link,src-node,dest-node
		//virtualData.links<--->vn-pn-Mapping.virtual-resource-entity-id[link]-----vn-pn-Mapping.virtual-physical-entity-id[path]<--->physicalData.physical-path---->Physical Nodes passed by Path
		//id tableId,Data MainData(virtualData)
		createVirtualLinkTable: function(id, Data, vn_pnData, physicalData) {
			if (!Data) return;
			console.log(Data);
			var virtuallinks = []; //virtual-link-id----->[src-node-id,dest-node-id]
			var Mylink = Data['virtual-links']['virtual-link'];
			for (var i in Mylink) {
				virtuallinks[Mylink[i]['link-id']] = [Mylink[i]['src-node-id'], Mylink[i]['dest-node-id']];
			}

			console.log(virtuallinks);
			PathId = []; //virtual-link-id--->physical-path-id
			var paths = createMappingTables.getPaths(virtuallinks, vn_pnData, physicalData, PathId); //physical-path-id----->[Physical Nodes passed by Path](count=0,1,2,3...)
			console.log(PathId);
			console.log(paths);
			var count=0;
			for(var x in virtual_nodes){count++;}
			if(count==0) return;
			for (var i in virtuallinks) {
				var $tr = '<tr><td>' + virtual_nodes[virtuallinks[i][0]][1] + '-' + virtual_nodes[virtuallinks[i][1]][1] + '</td><td>' + virtual_nodes[virtuallinks[i][0]][1] + '</td><td>' + virtual_nodes[virtuallinks[i][1]][1] + '</td><td title='+PathId[i]+'>' + PathId[i].substring(0,14)+'...' + '</td>'
				var pathsNode = ''
				for (var nodesPath in paths[PathId[i]]) {
					pathsNode += paths[PathId[i]][nodesPath] + '-';
				}
				pathsNode = pathsNode.substring(0, pathsNode.length - 1);
				$tr += '<td>' + pathsNode + '</td>';
				$tr += '</tr>'
				jQuery("#" + id).append($tr);
			}
		},


		getPaths: function(linkArray, vn_pnData, physicalData, PathId) {
			// console.log(linkArray);
			// console.log(vn_pnData);
			// console.log(physicalData);
			if (!linkArray || !vn_pnData || !physicalData) return;
			//PathId=[];
			for (var link in linkArray) {
				for (var i in vn_pnData['vn-pn-mapping-result']) {
					if (link == vn_pnData['vn-pn-mapping-result'][i]['virtual-resource-entity-id'] && vn_pnData['vn-pn-mapping-result'][i]['physical-resource-type'] == 'path') {
						PathId[link] = vn_pnData['vn-pn-mapping-result'][i]['physical-resource-entity-id'];
					}
				}
			}
			console.log(PathId);
			var Paths = [];
			var sourcePaths = physicalData['physical-network']['physical-paths'];
			if(!sourcePaths) return Paths;

			sourcePaths = physicalData['physical-network']['physical-paths']['physical-path'];
			// console.log(sourcePaths);
			for (var path in PathId) {
				for (var sp in sourcePaths) {
					if (PathId[path] == sourcePaths[sp]['path-id']) {
						var physical_links = sourcePaths[sp]['physical-link'];

						if (!physical_links) {
							Paths[PathId[path]] = [''];
							continue;
						} else {
							if (physical_links.length == 1) {
								Paths[PathId[path]] = createMappingTables.getNodesByLink(physical_links[0]['link-id'], physicalData);
							} else {
								physical_links = physical_links.sort(function(a, b) {
									return a.order - b.order
								});
								var passedByPath = [];
								Paths[PathId[path]] = [];
								for (var l in physical_links) {
									passedByPath.push(physical_links[l]['link-id']);
									var this_paths = createMappingTables.getNodesByLink(physical_links[l]['link-id'], physicalData);
									if (l == 0) {
										Paths[PathId[path]].push(this_paths[0]);
										Paths[PathId[path]].push(this_paths[1]);
									} else {
										Paths[PathId[path]].push(this_paths[1]);
									}
								}

								//Paths[PathId[path]] = passedByPath;
							}
						}
					}
				}
			}
			console.log(Paths);
			return Paths;
		},
		getNodesByLink: function(link_id, physicalData) {
			if (!physicalData) return;
			var sourceLinks = physicalData['physical-network']['physical-links']['physical-link'];
			for (var link in sourceLinks) {
				if (link_id == sourceLinks[link]['link-id']) {
					var pathnodes = [];
					pathnodes.push(sourceLinks[link]['src-node-id']);
					pathnodes.push(sourceLinks[link]['dest-node-id']);
					return pathnodes;
				}
			}
			return null;
		}
	}