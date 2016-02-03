var IntentInfos='';

function getIntentInfos(){
	  var userData=null;
       jQuery.ajax({
			url: "/restconf/config/nemo-intent:users/",
			type: "GET",
			async: false,
			dataType: "json",
			success: function(data) {
				console.log(data);
				IntentInfos=data;
				userData=data;
			},
			error: function(data) {
				console.log(data);
				console.log('Get IntentInfo Error!');
				// alert('Get IntentInfo Error!');
			}
		});
       return userData;
     
	}

 function getIntentDatas(){
 	var Datas=getIntentInfos();
 	if(!Datas){
 	Datas=userinfo;
 	}
 	IntentInfos=Datas;
 	//return virtualDatas;
 }

getIntentInfos();
// getIntentDatas();
 function getIntentInfoById(user_id,Data){
 		//console.log(1);
 	if(!Data) Data=IntentInfos;
 	if(!Data) return;
 	if(typeof(Data)=='string'){
    var user_json = JSON.parse(Data);
    }
    else
    {
    var user_json = Data;
    }

     console.log(user_json);
    var user_info = user_json['users']['user'];
 	for(var item in user_info){
 		if(user_id==user_info[item]['user-id']){
 			return user_info[item];
 		}
 	}
 }

 function getIntentInfoByName(user_name,Data){
     console.log(Data);
 	if(typeof(Data)=='string'){
    var user_json = JSON.parse(Data);
    }
    else
    {
    var user_json = Data;
    }
    // console.log(user_json);
    var user_info = user_json['users']['user'];
 	for(var item in user_info){
 		if(user_name==user_info[item]['user-name']){
 			return user_info[item];
 		}
 	}
 }



	function lead_policy(src_group, dest_group, flow_count_temp, color, id, chain_name, src_name, dest_name) {
		var cir_r = parseInt(jQuery("#" + src_group + " circle:eq(0)").attr("r"));

		//get circle cx cy
		src_cx = parseInt(jQuery("#" + src_group + " circle:eq(0)").attr("cx"));
		src_cy = parseInt(jQuery("#" + src_group + " circle:eq(0)").attr("cy"));
		dest_cx = parseInt(jQuery("#" + dest_group + " circle:eq(0)").attr("cx"));
		dest_cy = parseInt(jQuery("#" + dest_group + " circle:eq(0)").attr("cy"));
		console.log(src_cx + "  " + src_cy + "  " + dest_cx + "   " + dest_cy);

		//calculate deg
		var tri_h = dest_cx - src_cx;
		var tri_l = src_cy - dest_cy;
		var tri_s = Math.sqrt(tri_l * tri_l + tri_h * tri_h);
		var deg = Math.asin(tri_l / tri_s);
		console.log(tri_h + "  " + tri_l + "  " + tri_s);
		//alert(deg);
		console.log(deg);


		//calculate offset
		var offset = ((20 + (15 * parseInt(flow_count_temp))) / 180) * Math.PI;
		console.log("temp: " + flow_count_temp + " offset:  " + offset);

		//calculate path possition
		var path_src_x, path_src_y, path_dest_x, path_dest_y, mid_x, mid_y, b_x, b_y;
		if (tri_h >= 0) {
			mid_x = src_cx + tri_h / 2;
			mid_y = src_cy - tri_l / 2;
			path_src_x = src_cx + cir_r * (Math.cos(offset + deg));
			path_src_y = src_cy - cir_r * (Math.sin(offset + deg));
			path_dest_x = dest_cx - (cir_r + 14) * (Math.cos(offset - deg));
			path_dest_y = dest_cy - (cir_r + 14) * (Math.sin(offset - deg));
			if (tri_l >= 0) {
				b_x = mid_x - (80 + 30 * parseInt(flow_count_temp)) * Math.abs(Math.sin(deg));;
				b_y = mid_y - (80 + 30 * parseInt(flow_count_temp)) * Math.abs(Math.cos(deg));
			} else {
				b_x = mid_x + (80 + 30 * parseInt(flow_count_temp)) * Math.abs(Math.sin(deg));
				b_y = mid_y - (80 + 30 * parseInt(flow_count_temp)) * Math.abs(Math.cos(deg));
			}

		} else {
			mid_x = src_cx + tri_h / 2;
			mid_y = src_cy - tri_l / 2;
			path_src_x = src_cx - cir_r * (Math.cos(offset - deg));
			path_src_y = src_cy + cir_r * (Math.sin(offset - deg));
			path_dest_x = dest_cx + (cir_r + 14) * (Math.cos(-offset - deg));
			path_dest_y = dest_cy - (cir_r + 14) * (Math.sin(-offset - deg));
			if (tri_l >= 0) {
				b_x = mid_x - (80 + 30 * parseInt(flow_count_temp)) * Math.abs(Math.sin(deg));
				b_y = mid_y + (80 + 30 * parseInt(flow_count_temp)) * Math.abs(Math.cos(deg));
			} else {
				b_x = mid_x + (80 + 30 * parseInt(flow_count_temp)) * Math.abs(Math.sin(deg));
				b_y = mid_y + (80 + 30 * parseInt(flow_count_temp)) * Math.abs(Math.cos(deg));
			}

		}
		//path title
		var path_title = document.createElementNS('http://www.w3.org/2000/svg', 'path');



		//create path
		var path = document.createElementNS('http://www.w3.org/2000/svg', 'path');
		var path_main = jQuery(path).attr({
			id: id,
			node_start: src_name,
			node_end: dest_name,
			type: "flow",
			sx: path_src_x,
			sy: path_src_y,
			mx: b_x,
			my: b_y,
			ex: path_dest_x,
			ey: path_dest_y,
			count: flow_count_temp,
			via: chain_name,
			d: 'M' + path_src_x + " " + path_src_y + ' Q' + b_x + ' ' + b_y + ' ' + path_dest_x + ' ' + path_dest_y,
			'stroke': color,
			'stroke-width': 3,
			fill: "none",
			'stroke-dasharray': "6,6",
			'marker-end': "url(#idArrow2)",
			'marker-mid': "url(#idtext2)"
		});
		jQuery('#service_svg2').prepend(path_main);

	}

	function get_path_color() {
		while (1) {
			var num1 = Math.floor(Math.random() * 256);
			if (num1 > 220)
				continue;
			return num1.toString();
		}
	}

	function flow_get_end_name(src_ip, dest_ip) {
		// console.log(src_ip);
		// console.log(dest_ip);
		var host_name = ['', ''];
		if (typeof(phy_host_ip[src_ip]) != "undefined")
			host_name[0] = phy_host_ip[src_ip];
		else {
			for (var find_ip_cursor = 0; find_ip_cursor < jQuery("#service_svg2 g").length; find_ip_cursor++) {
				if (jQuery("#service_svg2 g:eq(" + find_ip_cursor + ")").attr("ip-prefix") == src_ip) {
					host_name[0] = jQuery("#service_svg2 g:eq(" + find_ip_cursor + ")").attr("id");
				}

			}
		}
		if (typeof(phy_host_ip[dest_ip]) != "undefined")
			host_name[1] = phy_host_ip[dest_ip];
		else {
			for (var find_ip_cursor = 0; find_ip_cursor < jQuery("#service_svg2 g").length; find_ip_cursor++) {
				if (jQuery("#service_svg2 g:eq(" + find_ip_cursor + ")").attr("ip-prefix") == dest_ip) {
					host_name[1] = jQuery("#service_svg2 g:eq(" + find_ip_cursor + ")").attr("id");
				}

			}
		}
		console.log(host_name[0] + " " + host_name[1]);
		return host_name;


	}

	function flow_get_group(src_ip, dest_ip) {
		var src_host_name = flow_get_end_name(src_ip, dest_ip)[0];
		var dest_host_name = flow_get_end_name(src_ip, dest_ip)[1];
		console.log(src_host_name);
		console.log(dest_host_name);
		var group_node = ['', ''];
		for (var i = 0; i < jQuery("#service_svg2 g").length; i++) {
			host_list = jQuery("#service_svg2 g:eq(" + i + ")").attr("sub").split(",");
			console.log(host_list);
			if (host_list[0] == '') {
				continue;
			}
			for (var j = 0; j < host_list.length; j++) {
				if (host_list[j] == src_host_name) {
					group_node[0] = jQuery("#service_svg2 g:eq(" + i + ")").attr("id");
				}
				if (host_list[j] == dest_host_name) {
					group_node[1] = jQuery("#service_svg2 g:eq(" + i + ")").attr("id");
				}
			}
			if (group_node[0] != '' && group_node[1] != '')
				break;
		}
		console.log("node: " + group_node[0] + "  " + group_node[1]);
		return group_node;
	}

	function draw_flow_data(src_ip, dest_ip, flow_name,src_node,dest_node) {
		if(!src_ip && !dest_ip){
		var src_group = intentNodesArray[src_node];
		var dest_group = intentNodesArray[dest_node];
		}
		else{
	    var src_group = flow_get_group(src_ip, dest_ip)[0];
		var dest_group = flow_get_group(src_ip, dest_ip)[1];
		}

		
		console.log(src_group);
		console.log(dest_group);
		//get flow count
		var flow_count_temp = 0;
		while (1) {
			var end_flag = 0;
			for (var find_count = 0; find_count < jQuery("#service_svg2 path").length; find_count++) {
				if (jQuery("#service_svg2 path:eq(" + find_count + ")").attr("type") != "flow")
					continue;
				if (jQuery("#service_svg2 path:eq(" + find_count + ")").attr("node_start") == src_group && jQuery("#service_svg2 path:eq(" + find_count + ")").attr("node_end") == dest_group) {
					if (flow_count_temp == jQuery("#service_svg2 path:eq(" + find_count + ")").attr("count")) {
						end_flag = 1;
						flow_count_temp++;
						break;
					}
				}
			}
			if (end_flag != 1)
				break;
		}



		//get Radio
		var cir_r = parseInt(jQuery("[id='" + src_group + "'] circle:eq(0)").attr("r"));


		//get circle cx cy
		src_cx = parseInt(jQuery("[id='" + src_group + "'] circle:eq(0)").attr("cx"));
		src_cy = parseInt(jQuery("[id='" + src_group + "'] circle:eq(0)").attr("cy"));
		dest_cx = parseInt(jQuery("[id='" + dest_group + "'] circle:eq(0)").attr("cx"));
		dest_cy = parseInt(jQuery("[id='" + dest_group + "'] circle:eq(0)").attr("cy"));
		console.log(src_cx + "  " + src_cy + "  " + dest_cx + "   " + dest_cy);

		//calculate deg
		var tri_h = dest_cx - src_cx;
		var tri_l = src_cy - dest_cy;
		var tri_s = Math.sqrt(tri_l * tri_l + tri_h * tri_h);
		var deg = Math.asin(tri_l / tri_s);
		console.log(tri_h + "  " + tri_l + "  " + tri_s);
		//alert(deg);
		console.log(deg);


		//calculate offset
		var offset = ((20 + (15 * flow_count_temp)) / 180) * Math.PI;
		console.log("offset:  " + offset);

		//calculate path possition
		var path_src_x, path_src_y, path_dest_x, path_dest_y, mid_x, mid_y, b_x, b_y;
		if (tri_h >= 0) {
			mid_x = src_cx + tri_h / 2;
			mid_y = src_cy - tri_l / 2;
			path_src_x = src_cx + cir_r * (Math.cos(offset + deg));
			path_src_y = src_cy - cir_r * (Math.sin(offset + deg));
			path_dest_x = dest_cx - (cir_r + 14) * (Math.cos(offset - deg));
			path_dest_y = dest_cy - (cir_r + 14) * (Math.sin(offset - deg));
			if (tri_l >= 0) {
				b_x = mid_x - (80 + 30 * flow_count_temp) * Math.abs(Math.sin(deg));;
				b_y = mid_y - (80 + 30 * flow_count_temp) * Math.abs(Math.cos(deg));
			} else {
				b_x = mid_x + (80 + 30 * flow_count_temp) * Math.abs(Math.sin(deg));
				b_y = mid_y - (80 + 30 * flow_count_temp) * Math.abs(Math.cos(deg));
			}

		} else {
			mid_x = src_cx + tri_h / 2;
			mid_y = src_cy - tri_l / 2;
			path_src_x = src_cx - cir_r * (Math.cos(offset - deg));
			path_src_y = src_cy + cir_r * (Math.sin(offset - deg));
			path_dest_x = dest_cx + (cir_r + 14) * (Math.cos(-offset - deg));
			path_dest_y = dest_cy - (cir_r + 14) * (Math.sin(-offset - deg));
			if (tri_l >= 0) {
				b_x = mid_x - (80 + 30 * flow_count_temp) * Math.abs(Math.sin(deg));
				b_y = mid_y + (80 + 30 * flow_count_temp) * Math.abs(Math.cos(deg));
			} else {
				b_x = mid_x + (80 + 30 * flow_count_temp) * Math.abs(Math.sin(deg));
				b_y = mid_y + (80 + 30 * flow_count_temp) * Math.abs(Math.cos(deg));
			}

		}
		//path title
		var path_title = document.createElementNS('http://www.w3.org/2000/svg', 'path');



		//create path
		var path = document.createElementNS('http://www.w3.org/2000/svg', 'path');
		var path_main = jQuery(path).attr({
			id: flow_name,
			node_start: src_group,
			node_end: dest_group,
			type: "flow",
			sx: path_src_x,
			sy: path_src_y,
			mx: b_x,
			my: b_y,
			ex: path_dest_x,
			ey: path_dest_y,
			via: "none",
			count: flow_count_temp,
			d: 'M' + path_src_x + " " + path_src_y + ' Q' + b_x + ' ' + b_y + ' ' + path_dest_x + ' ' + path_dest_y,
			'stroke': "rgb(" + get_path_color() + "," + get_path_color() + "," + get_path_color() + ")",
			'stroke-width': 3,
			fill: "none",
			'stroke-dasharray': "6,6",
			'marker-end': "url(#idArrow2)",
			'marker-mid': "url(#idtext2)"
		});
		if (ne_flag == 0)
			jQuery(path).attr("stroke", "rgb(" + get_path_color() + "," + get_path_color() + "," + get_path_color() + ")");
		else if (ne_flag == 1)
			jQuery(path).attr("stroke", old_color);
		jQuery('#service_svg2').prepend(path_main);


	}

	function draw_connection_data(conn_name, node_name_1, node_name_2, bandwidth) {
		// console.log(conn_name);
		// console.log(node_name_1);
		// console.log(node_name_2);
		// console.log(bandwidth);
		if (conn_name != null && typeof(conn_name) != "undefined")
			jQuery("#" + conn_name).remove();
		try {
			var node_cx_1 = jQuery("[id='" + node_name_1 + "_group']").attr("cx");
			var node_cy_1 = jQuery("[id='" + node_name_1 + "_group']").attr("cy");
			var node_cx_2 = jQuery("[id='" + node_name_2 + "_group']").attr("cx");
			var node_cy_2 = jQuery("[id='" + node_name_2 + "_group']").attr("cy");
			// console.log(node_cx_1);
			// console.log(node_cy_1);
			// console.log(node_cx_2);
			// console.log(node_cy_2);
			//var g = document.createElementNS('http://www.w3.org/2000/svg', 'g');
			var path = document.createElementNS('http://www.w3.org/2000/svg', 'path');
			var path_main = jQuery(path).attr({
				id: conn_name,
				node_start: node_name_1,
				node_end: node_name_2,
				type: "connection",
				d: 'M' + node_cx_1 + " " + node_cy_1 + ' L' + node_cx_2 + ' ' + node_cy_2,
				'stroke': "black",
				'stroke-width': parseInt(bandwidth),
				fill: "black"
			});
			jQuery('#service_svg2').prepend(path_main);
		} catch (err) {
			alert(err);
		}
	}

	function redraw_node_possition_data() {
		node_count = jQuery("#service_svg2 g").length;
		if (node_count == 1) {
			var x = 550;
			var y = 450;
			dis_x = x - jQuery("#service_svg2 g:eq(0) circle:eq(0)").attr("cx");
			dis_y = y - jQuery("#service_svg2 g:eq(0) circle:eq(0)").attr("cy");
			jQuery("#service_svg2 g:eq(0) circle:eq(0)").attr("cx", x);
			jQuery("#service_svg2 g:eq(0) circle:eq(0)").attr("cy", y);
			for (var j = 0; j < jQuery("#service_svg2 g:eq(0) rect").length; j++) {
				var last_x = parseInt(jQuery("#service_svg2 g:eq(0) rect:eq(" + j + ")").attr("x"));
				var last_y = parseInt(jQuery("#service_svg2 g:eq(0) rect:eq(" + j + ")").attr("y"));
				jQuery("#service_svg2 g:eq(0) rect:eq(" + j + ")").attr("x", last_x + dis_x);
				jQuery("#service_svg2 g:eq(0) rect:eq(" + j + ")").attr("y", last_y + dis_y);
			}
			for (var j = 0; j < jQuery("#service_svg2 g:eq(0) text").length; j++) {
				jQuery("#service_svg2 g:eq(0) text:eq(" + j + ")").attr("x", parseInt(jQuery("#service_svg2 g:eq(0) text:eq(" + j + ")").attr("x")) + dis_x);
				jQuery("#service_svg2 g:eq(0) text:eq(" + j + ")").attr("y", parseInt(jQuery("#service_svg2 g:eq(0) text:eq(" + j + ")").attr("y")) + dis_y);
			}

			return;

		}
		var res = calculatePos(node_count);
		var r = 300;
		for (var i = 0; i < node_count; i++) {
			var deg = res[i];
			var x = 550 + Math.cos(deg) * r;
			var y = 450 - Math.sin(deg) * r;
			if(node_count%2==1)
				var y = 500 - Math.sin(deg) * r;
			dis_x = x - parseInt(jQuery("#service_svg2 g:eq(" + i + ") circle:eq(0)").attr("cx"));
			dis_y = y - parseInt(jQuery("#service_svg2 g:eq(" + i + ") circle:eq(0)").attr("cy"));
			jQuery("#service_svg2 g:eq(" + i + ") circle:eq(0)").attr("cx", x);
			jQuery("#service_svg2 g:eq(" + i + ") circle:eq(0)").attr("cy", y);
			for (var j = 0; j < jQuery("#service_svg2 g:eq(" + i + ") rect").length; j++) {
				jQuery("#service_svg2 g:eq(" + i + ") rect:eq(" + j + ")").attr("x", parseInt(jQuery("#service_svg2 g:eq(" + i + ") rect:eq(" + j + ")").attr("x")) + dis_x);
				jQuery("#service_svg2 g:eq(" + i + ") rect:eq(" + j + ")").attr("y", parseInt(jQuery("#service_svg2 g:eq(" + i + ") rect:eq(" + j + ")").attr("y")) + dis_y);
			}
			for (var j = 0; j < jQuery("#service_svg2 g:eq(" + i + ") text").length; j++) {
				jQuery("#service_svg2 g:eq(" + i + ") text:eq(" + j + ")").attr("x", parseInt(jQuery("#service_svg2 g:eq(" + i + ") text:eq(" + j + ")").attr("x")) + dis_x);
				jQuery("#service_svg2 g:eq(" + i + ") text:eq(" + j + ")").attr("y", parseInt(jQuery("#service_svg2 g:eq(" + i + ") text:eq(" + j + ")").attr("y")) + dis_y);
			}

		}

	}

	function draw_group_data(node_name, node_number, node_list, node_type, ip) {
		// alert("type:"+node_type);
		var circle_x = 550;
		var circle_y = 450;
		if (node_number == 0) {
			var g = document.createElementNS('http://www.w3.org/2000/svg', 'g');
			var circle_main = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
			var inCircle1_main = jQuery(circle_main).attr({
				id: node_name + "_group",
				cx: circle_x,
				cy: circle_y,
				r: 90,
				'stroke': "black",
				'stroke-width': 3,
				fill: "white"
			});
			var text1 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text1_main = jQuery(text1).attr({
				id: node_name + "_title1",
				x: circle_x,
				y: circle_y - 8,
				fill: "black",
				'text-anchor': 'middle',
				"stroke": "black",
				"stroke-width": 0.8

			});
			jQuery(text1).text(node_name);
			var text2 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text2_main = jQuery(text2).attr({
				id: node_name + "_title2",
				x: circle_x,
				y: circle_y + 0 - 100,
				fill: "                       ",
				'text-anchor': 'middle'
			});
			jQuery(text2).text(node_type);
			var g_main = jQuery(g).append(inCircle1_main);
			jQuery(g).append(text1_main);
			jQuery(g).append(text2_main);
			jQuery(g).attr("id", node_name);
			jQuery(g).attr("type", node_type);
			if (node_type != "ext-group")
				jQuery(g).attr("sub", "");
			else {
				jQuery(g).attr("sub", node_name);
				jQuery(g).attr("ip-prefix", ip);
				//ext_ip[jQuery("#ext-group_ip-prefix").val().trim()] = jQuery("#node_name").val().trim();

			}
			if (node_type.indexOf("chain") > -1)
				jQuery(g).attr("flow", 0);
			console.log(g_main);
			jQuery('#service_svg2').append(g_main);
			console.log(jQuery('#service_svg2').html())
		} else if (node_number == 1) {
			var g = document.createElementNS('http://www.w3.org/2000/svg', 'g');
			var circle_main = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
			var inCircle1_main = jQuery(circle_main).attr({
				id: node_name + "_group",
				cx: circle_x,
				cy: circle_y,
				r: 90,
				'stroke': "black",
				'stroke-width': 3,
				fill: "white"
			});
			var host1 = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
			var host1_main = jQuery(host1).attr({
				id: node_list[0],
				x: circle_x - 57,
				y: circle_y - 25,
				width: 114,
				height: 50,
				'stroke': "black",
				'stroke-width': 1,
				fill: "white"
			});
			var text1 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text1_main = jQuery(text1).attr({
				id: node_name + "_title1",
				x: circle_x,
				y: circle_y - 48,
				fill: "black",
				'text-anchor': 'middle',
				"stroke": "black",
				"stroke-width": 0.8
			});
			jQuery(text1).text(node_name);
			var text2 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text2_main = jQuery(text2).attr({
				id: node_name + "_title2",
				x: circle_x,
				y: (circle_y - 0 - 100),
				fill: "black",
				'text-anchor': 'middle'
			});
			jQuery(text2).text(node_type);
			var text3 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text3_main = jQuery(text3).attr({
				id: node_list[0] + '_text',
				x: circle_x,
				y: circle_y + 5,
				fill: "black",
				'text-anchor': 'middle'
			});
			jQuery(text3).text(node_list[0]);
			var g_main = jQuery(g).append(inCircle1_main);
			jQuery(g).append(host1_main);
			jQuery(g).append(text1_main);
			jQuery(g).append(text2_main);
			jQuery(g).append(text3_main);
			jQuery(g).attr("id", node_name);
			jQuery(g).attr("type", node_type);
			jQuery(g).attr("sub", node_name,node_list[0]);
			if (node_type.indexOf("chain") > -1)
				jQuery(g).attr("flow", 0);
			if(ip) jQuery(g).attr("ip-prefix", ip);
			jQuery('#service_svg2').append(g_main);
		} else if (node_number == 2) {
			var g = document.createElementNS('http://www.w3.org/2000/svg', 'g');
			var circle_main = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
			var inCircle1_main = jQuery(circle_main).attr({
				id: node_name + "_group",
				cx: circle_x,
				cy: circle_y,
				r: 90,
				'stroke': "black",
				'stroke-width': 3,
				fill: "white"
			});
			var host1 = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
			var host1_main = jQuery(host1).attr({
				id: node_list[0],
				x: circle_x - 55,
				y: circle_y - 25,
				width: 110,
				height: 34,
				'stroke': "black",
				'stroke-width': 1,
				fill: "white"
			});
			var host2 = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
			var host2_main = jQuery(host2).attr({
				id: node_list[1],
				x: circle_x - 55,
				y: circle_y + 18,
				width: 110,
				height: 34,
				'stroke': "black",
				'stroke-width': 1,
				fill: "white"
			});
			var text1 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text1_main = jQuery(text1).attr({
				id: node_name + "_title1",
				x: circle_x,
				y: circle_y - 48,
				fill: "black",
				'text-anchor': 'middle',
				"stroke": "black",
				"stroke-width": 0.8
			});
			jQuery(text1).text(node_name);
			var text2 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text2_main = jQuery(text2).attr({
				id: node_name + "_title2",
				x: circle_x,
				y: circle_y - 0 - 100,
				fill: "black",
				'text-anchor': 'middle'
			});
			jQuery(text2).text(node_type);
			var text3 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text3_main = jQuery(text3).attr({
				id: node_list[0] + "_text",
				x: circle_x,
				y: circle_y - 5,
				fill: "black",
				'text-anchor': 'middle'
			});
			jQuery(text3).text(node_list[0]);
			var text4 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text4_main = jQuery(text4).attr({
				id: node_list[1] + "_text",
				x: circle_x,
				y: circle_y + 40,
				fill: "black",
				'text-anchor': 'middle'
			});
			jQuery(text4).text(node_list[1]);
			var g_main = jQuery(g).append(inCircle1_main);
			jQuery(g).append(host1_main);
			jQuery(g).append(host2_main);
			jQuery(g).append(text1_main);
			jQuery(g).append(text2_main);
			jQuery(g).append(text3_main);
			jQuery(g).append(text4_main);
			jQuery(g).attr("id", node_name);
			jQuery(g).attr("type", node_type);
			jQuery(g).attr("sub", node_name,node_list[0] + "," + node_list[1]);
			if (node_type.indexOf("chain") > -1)
				jQuery(g).attr("flow", 0);
		    if(ip) jQuery(g).attr("ip-prefix", ip);
			jQuery('#service_svg2').append(g);

		} else if (node_number == 3) {
			var g = document.createElementNS('http://www.w3.org/2000/svg', 'g');
			var circle_main = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
			var inCircle1_main = jQuery(circle_main).attr({
				id: node_name + "_group",
				cx: circle_x,
				cy: circle_y,
				r: 90,
				'stroke': "black",
				'stroke-width': 3,
				fill: "white"
			});
			var host1 = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
			var host1_main = jQuery(host1).attr({
				id: node_list[0],
				x: circle_x - 55,
				y: circle_y - 31,
				width: 110,
				height: 25,
				'stroke': "black",
				'stroke-width': 1,
				fill: "white"
			});
			var host2 = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
			var host2_main = jQuery(host2).attr({
				id: node_list[1],
				x: circle_x - 55,
				y: circle_y - 3,
				width: 110,
				height: 25,
				'stroke': "black",
				'stroke-width': 1,
				fill: "white"
			});
			var host3 = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
			var host3_main = jQuery(host3).attr({
				id: node_list[2],
				x: circle_x - 55,
				y: circle_y + 27,
				width: 110,
				height: 25,
				'stroke': "black",
				'stroke-width': 1,
				fill: "white"
			});
			var text1 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text1_main = jQuery(text1).attr({
				id: node_name + "_title1",
				x: circle_x,
				y: circle_y - 51,
				fill: "black",
				'text-anchor': 'middle',
				"stroke": "black",
				"stroke-width": 0.8
			});
			jQuery(text1).text(node_name);
			var text2 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text2_main = jQuery(text2).attr({
				id: node_name + "_title2",
				x: circle_x,
				y: circle_y - 0 - 100,
				fill: "black",
				'text-anchor': 'middle'
			});
			jQuery(text2).text(node_type);
			var text3 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text3_main = jQuery(text3).attr({
				id: node_list[0] + '_text',
				x: circle_x,
				y: circle_y - 13,
				fill: "black",
				'text-anchor': 'middle'
			});
			jQuery(text3).text(node_list[0]);
			var text4 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text4_main = jQuery(text4).attr({
				id: node_list[1] + '_text',
				x: circle_x,
				y: circle_y + 15,
				fill: "black",
				'text-anchor': 'middle'
			});
			jQuery(text4).text(node_list[1]);
			var text5 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text5_main = jQuery(text5).attr({
				id: node_list[2] + '_text',
				x: circle_x,
				y: circle_y + 45,
				fill: "black",
				'text-anchor': 'middle'
			});
			jQuery(text5).text(node_list[2]);
			var g_main = jQuery(g).append(inCircle1_main);
			jQuery(g).append(host1_main);
			jQuery(g).append(host2_main);
			jQuery(g).append(host3_main);
			jQuery(g).append(text1_main);
			jQuery(g).append(text2_main);
			jQuery(g).append(text3_main);
			jQuery(g).append(text4_main);
			jQuery(g).append(text5_main);
			jQuery(g).attr("id", node_name);
			jQuery(g).attr("type", node_type);
			jQuery(g).attr("sub", node_name,node_list[0] + "," + node_list[1] + "," + node_list[2]);
			if (node_type.indexOf("chain") > -1)
				jQuery(g).attr("flow", 0);
			jQuery('#service_svg2').append(g);
		}
		else if(node_number >=4){
			var g = document.createElementNS('http://www.w3.org/2000/svg', 'g');
			var circle_main = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
			var inCircle1_main = jQuery(circle_main).attr({
				id: node_name + "_group",
				cx: circle_x,
				cy: circle_y,
				r: 90,
				'stroke': "black",
				'stroke-width': 3,
				fill: "white"
			});
			var host1 = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
			var host1_main = jQuery(host1).attr({
				id: node_list[0],
				x: circle_x - 55,
				y: circle_y - 31,
				width: 110,
				height: 25,
				'stroke': "black",
				'stroke-width': 1,
				fill: "white"
			});
			var host2 = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
			var host2_main = jQuery(host2).attr({
				id: node_list[1],
				x: circle_x - 55,
				y: circle_y - 3,
				width: 110,
				height: 25,
				'stroke': "black",
				'stroke-width': 1,
				fill: "white"
			});
			var host3 = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
			var host3_main = jQuery(host3).attr({
				id: node_list[2],
				x: circle_x - 55,
				y: circle_y + 27,
				width: 110,
				height: 25,
				'stroke': "black",
				'stroke-width': 1,
				fill: "white"
			});
			var text1 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text1_main = jQuery(text1).attr({
				id: node_name + "_title1",
				x: circle_x,
				y: circle_y - 51,
				fill: "black",
				'text-anchor': 'middle',
				"stroke": "black",
				"stroke-width": 0.8
			});
			jQuery(text1).text(node_name);
			var text2 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text2_main = jQuery(text2).attr({
				id: node_name + "_title2",
				x: circle_x,
				y: circle_y - 0 - 100,
				fill: "black",
				'text-anchor': 'middle'
			});
			jQuery(text2).text(node_type);
			var text3 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text3_main = jQuery(text3).attr({
				id: node_list[0] + '_text',
				x: circle_x,
				y: circle_y - 13,
				fill: "black",
				'text-anchor': 'middle'
			});
			jQuery(text3).text(node_list[0]);
			var text4 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text4_main = jQuery(text4).attr({
				id: node_list[1] + '_text',
				x: circle_x,
				y: circle_y + 15,
				fill: "black",
				'text-anchor': 'middle'
			});
			jQuery(text4).text(node_list[1]);
			var text5 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text5_main = jQuery(text5).attr({
				id: node_list[2] + '_text',
				x: circle_x,
				y: circle_y + 45,
				fill: "black",
				'text-anchor': 'middle'
			});
			jQuery(text5).text(node_list[2]);
			var text6 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text6_main = jQuery(text6).attr({
				id: node_list[2] + '_text',
				x: circle_x,
				y: circle_y + 43+25,
				fill: "black",
				'text-anchor': 'middle'
			});
			jQuery(text6).text("........");
			var g_main = jQuery(g).append(inCircle1_main);
			jQuery(g).append(host1_main);
			jQuery(g).append(host2_main);
			jQuery(g).append(host3_main);
			jQuery(g).append(text1_main);
			jQuery(g).append(text2_main);
			jQuery(g).append(text3_main);
			jQuery(g).append(text4_main);
			jQuery(g).append(text5_main);
			jQuery(g).append(text6_main);
			jQuery(g).attr("id", node_name);
			jQuery(g).attr("type", node_type);
			jQuery(g).attr("sub", node_name,node_list[0] + "," + node_list[1] + "," + node_list[2]);
			if (node_type.indexOf("chain") > -1)
				jQuery(g).attr("flow", 0);
			jQuery('#service_svg2').append(g);
		}
	}

    var intentNodesArray=[];//node id--->node name
	function getUserNodes(data){
	var nodes=data["node"];
    for(var item in nodes){
    	intentNodesArray[nodes[item]["node-id"]]=nodes[item]["node-name"];
     }
	}
	function analyjson_topo(user_data) {
	    //jQuery("#service_svg2").show();
		//jQuery("#service_svg").hide();
		//jQuery("#graph").hide();
		//jQuery("#service_svg").empty();
		// var username = jQuery("#getUserName").val();
		//alert(username);
		var html_init = '<defs><marker id="idArrow2" viewBox="0 0 20 20" refX="0" refY="10" markerUnits="strokeWidth" markerWidth="6" markerHeight="15" orient="auto"><path d="M 0 0 L 20 10 L0 20 z"fill="purple"stroke="black"/> </marker>\
                    <marker id="idtext2" viewBox="0 0 120 50" refX="0" refY="0" markerUnits="strokeWidth" markerWidth="20" markerHeight="20"  orient="auto"><text style="font-family:sans-serif; font-size:14pt;"  x="20" y="20">text</text></marker></defs>';
		jQuery("#service_svg2").html(html_init);
		//var topo_data;
		//  jQuery.ajax({
		// 	url: "/restconf/config/nemo-intent:users/",
		// 	type: "GET",
		// 	async: false,
		// 	dataType: "json",
		// 	success: function(data) {
		// 		console.log(data);
		// 		topo_data = data;
		// 	},
		// 	error: function(data) {
		// 		console.log(data);
		// 	}
		// });

		//var name_data_list = topo_data["users"]["user"];

		//var user_json_data, user_data;
		// for (var i in name_data_list) {
		// 	if (name_data_list[i]["user-name"] == username) {
		// 		user_data = name_data_list[i];
		// 		user_json_data = name_data_list[i]["objects"];
		// 		break;
		// 	}
		// }

		var user_json_data = user_data['objects'];
		if (user_json_data == null)
			return;
		getUserNodes(user_json_data);
		console.log("intentNodesArray",intentNodesArray);
		//host
		var host_list = [];
		for (var host_cursor in user_json_data["node"]) {
			if (user_json_data["node"][host_cursor]["node-type"] != "host")
				continue;
			host_list[user_json_data["node"][host_cursor]["node-id"]] = user_json_data["node"][host_cursor]["node-name"];

		}

		//vas
		var vas_list = [];
		for (var vas_cursor in user_json_data["node"]) {
			// if (user_json_data["node"][vas_cursor]["node-type"] != "cache" && user_json_data["node"][vas_cursor]["node-type"] != "fw" && user_json_data["node"][vas_cursor]["node-type"] != "lb" )
				// continue;

			vas_list[user_json_data["node"][vas_cursor]["node-id"]] = user_json_data["node"][vas_cursor]["node-name"];

		}
        

		//group
		var node_list = [];

		//l2/l3-group
		for (var lgroup_cursor in user_json_data["node"]) {
			if (user_json_data["node"][lgroup_cursor]["node-type"] != "l2-group" && user_json_data["node"][lgroup_cursor]["node-type"] != "l3-group")
				continue;
			var sub_count = 0;
			var sub_list = [];
			for (var sub_cursor in user_json_data["node"][lgroup_cursor]["sub-node"]) {
				sub_list[sub_count] = host_list[user_json_data["node"][lgroup_cursor]["sub-node"][sub_cursor]["node-id"]];
				sub_count++;
			}
			var ip='';
			var myProperty=user_json_data["node"][lgroup_cursor]['property'];
			if(myProperty){
			for(var item in myProperty){
				if(myProperty[item]['property-name']=='ip-prefix'){
					ip=myProperty[item]['property-values']['string-value'][0]['value'];
				}
			}
	        }
			draw_group_data(user_json_data["node"][lgroup_cursor]["node-name"], sub_list.length, sub_list, user_json_data["node"][lgroup_cursor]["node-type"], ip);
			redraw_node_possition_data();
			node_list[user_json_data["node"][lgroup_cursor]["node-id"]] = user_json_data["node"][lgroup_cursor]["node-name"];
		}
		//chain_group
		for (var cgroup_cursor in user_json_data["node"]) {

			if (user_json_data["node"][cgroup_cursor]["node-type"] != "chain-group")
				continue;
			//alert("chain");
			var sub_count = 0;
			var sub_list = [];
			for (var sub_cursor in user_json_data["node"][cgroup_cursor]["sub-node"]) {
				//alert(user_json_data["node"][cgroup_cursor]["sub-node"][sub_cursor]["node-id"]);
				sub_list[sub_count] = vas_list[user_json_data["node"][cgroup_cursor]["sub-node"][sub_cursor]["node-id"]];
				sub_count++;
			}
			//alert();
			draw_group_data(user_json_data["node"][cgroup_cursor]["node-name"], sub_list.length, sub_list, user_json_data["node"][cgroup_cursor]["node-type"], "");
			redraw_node_possition_data();
			node_list[user_json_data["node"][cgroup_cursor]["node-id"]] = user_json_data["node"][cgroup_cursor]["node-name"];
		}
		//ext-group
		for (var egroup_cursor in user_json_data["node"]) {
			if (user_json_data["node"][egroup_cursor]["node-type"] != "ext-group")
				continue;
			// alert("ext");
			var ip;
			for (var ip_cursor in user_json_data["node"][egroup_cursor]["property"]) {
				if (user_json_data["node"][egroup_cursor]["property"][ip_cursor]["property-name"] == "ip-prefix") {
					ip = user_json_data["node"][egroup_cursor]["property"][ip_cursor]["property-values"]["string-value"][0]["value"];
				}
			}
			draw_group_data(user_json_data["node"][egroup_cursor]["node-name"], 0, '', user_json_data["node"][egroup_cursor]["node-type"], ip);
			redraw_node_possition_data();
			node_list[user_json_data["node"][egroup_cursor]["node-id"]] = user_json_data["node"][egroup_cursor]["node-name"];
		}
          // console.log(node_list);
		//connection
		for (var conn_cursor in user_json_data["connection"]) {
			var start_name = node_list[user_json_data["connection"][conn_cursor]["end-node"][0]["node-id"]];
			var end_name = node_list[user_json_data["connection"][conn_cursor]["end-node"][1]["node-id"]];
			var bandwidth;
			if (user_json_data["connection"][conn_cursor]["property"] != null)
				bandwidth = user_json_data["connection"][conn_cursor]["property"][0]["property-values"]["int-value"][0]["value"];
			else
				bandwidth = 100;
			draw_connection_data(user_json_data["connection"][conn_cursor]["connection-name"], start_name, end_name, bandwidth / 100);
		}

		//flow
		if (user_json_data["flow"] != null) {
			var flow_list = [];
			for (var flow_cursor in user_json_data["flow"]) {
				var src_ip, dst_ip;
				for (var match_cursor in user_json_data["flow"][flow_cursor]["match-item"]) {
					if (user_json_data["flow"][flow_cursor]["match-item"][match_cursor]["match-item-name"] == "dst-ip") {
						dst_ip = user_json_data["flow"][flow_cursor]["match-item"][match_cursor]["match-item-value"]["string-value"];
					}
					if (user_json_data["flow"][flow_cursor]["match-item"][match_cursor]["match-item-name"] == "src-ip") {
						src_ip = user_json_data["flow"][flow_cursor]["match-item"][match_cursor]["match-item-value"]["string-value"];
					}
				}
				if(!user_json_data["flow"][flow_cursor]["property"]){
				draw_flow_data(src_ip, dst_ip, user_json_data["flow"][flow_cursor]["flow-name"]);
			    }
			    else{
			    	var src_node,dest_node;
			    	var property=user_json_data["flow"][flow_cursor]["property"];
			    	for(var item in property){
			    	if(property[item]["property-name"]=="src-node")
			    		src_node=property[item]["property-values"]["string-value"][0]["value"];
			    	if(property[item]["property-name"]=="dst-node")
			    		dest_node=property[item]["property-values"]["string-value"][0]["value"];
			       }
			       draw_flow_data(null, null, user_json_data["flow"][flow_cursor]["flow-name"],src_node,dest_node);
			    }
				flow_list[user_json_data["flow"][flow_cursor]["flow-id"]] = user_json_data["flow"][flow_cursor]["flow-name"]
			}

		}
		//operation
		if (user_data["operations"] != null) {
			for (var operation_cursor in user_data["operations"]["operation"]) {
				var myTarget=user_data["operations"]["operation"][operation_cursor]["action"][0]['action-name'];
				if(myTarget!='go-through') continue;
				var flow_name = flow_list[user_data["operations"]["operation"][operation_cursor]["target-object"]];
				var chain_name = node_list[user_data["operations"]["operation"][operation_cursor]["action"][0]["parameter-values"]["string-value"][0]["value"]];
				var node_start = jQuery("[id='" + flow_name+"']").attr("node_start");
				var node_end = jQuery("[id='" + flow_name+"']").attr("node_end");
				var c1_flag = 0;
				var c2_flag = 0;
				for (var i = 0; i < jQuery("#service_svg2 path").length; i++) {
					if (jQuery("#service_svg2 path:eq(" + i + ")").attr("type") == "connection") {
						var node_name_old_1 = jQuery("#service_svg2 path:eq(" + i + ")").attr("node_start");
						var node_name_old_2 = jQuery("#service_svg2 path:eq(" + i + ")").attr("node_end");
						console.log("old:" + node_name_old_1 + "  " + node_name_old_2);
						if (((node_start == node_name_old_1) && (chain_name == node_name_old_2)) || ((node_start == node_name_old_2) && (chain_name == node_name_old_1))) {
							c1_flag = 1;
						}
					}
				}
				for (var i = 0; i < jQuery("#service_svg2 path").length; i++) {
					if (jQuery("#service_svg2 path:eq(" + i + ")").attr("type") == "connection") {
						var node_name_old_1 = jQuery("#service_svg2 path:eq(" + i + ")").attr("node_start");
						var node_name_old_2 = jQuery("#service_svg2 path:eq(" + i + ")").attr("node_end");
						console.log("old:" + node_name_old_1 + "  " + node_name_old_2);
						if (((node_end == node_name_old_1) && (chain_name == node_name_old_2)) || ((node_end == node_name_old_2) && (chain_name == node_name_old_1))) {
							c2_flag = 1;
						}
					}
				}
				if (c1_flag == 0 && c2_flag == 0) {
					alert("No connection!");
					return;
				}
				lead_policy(node_start, chain_name, parseInt(jQuery("#" + flow_name).attr("count")), jQuery("#" + flow_name).attr("stroke"), flow_name + "_1", chain_name, node_start, node_end)
				lead_policy(chain_name, node_end, parseInt(jQuery("#" + flow_name).attr("count")), jQuery("#" + flow_name).attr("stroke"), flow_name + "_2", chain_name, node_start, node_end)
				jQuery("#" + flow_name).remove();
			}

		}
	}

	//parse nemo language
	function parseNemoLan(userinfo) {
		console.log(userinfo);
		var userJsonString = JSON.stringify(userinfo);
		var userJson = JSON.parse(userJsonString);
		// console.log(userJson);
		var user_data = userinfo;
		//set host nodes connection and flow array  array[id]-->name
		var host = [];
		var nodes = [];
		var connection = [];
		var flow = [];
		for (var i in user_data) {
			// console.log(i);
			if (i == 'objects') {
				for (var j in user_data['objects']) {
					// console.log(j);
					if (j == 'node') {
						for (var k in user_data[i][j]) {
							nodes[user_data[i][j][k]["node-id"]] = user_data[i][j][k]["node-name"];
						}
					} else if (j == 'connection') {
						for (var k in user_data[i][j]) {
							connection[user_data[i][j][k]["connection-id"]] = user_data[i][j][k]["connection-name"];
						}
					} else if (j == 'flow') {
						for (var k in user_data[i][j]) {
							flow[user_data[i][j][k]["flow-id"]] = user_data[i][j][k]["flow-name"];
						}
					}
				}
			} else if (i == 'operations') {

			}
		}
		console.log(nodes);
		console.log(connection);
		console.log(flow);
		//parse and show nemo language（node connection flow operation）
		for (var i in user_data["objects"]) {
			if (i == "node") {
				var nodeNemo = [];
				var node = user_data["objects"]['node'];
				for(var j in node){
					if(node[j]["node-type"]=="fw"||node[j]["node-type"]=="firewall"){
 						node[j].order=1;
					}
					else if(node[j]["node-type"]=="cache"){
 						node[j].order=1;
					}
					else if(node[j]["node-type"]=="lb"||node[j]["node-type"]=="loadblance"){
 						node[j].order=1;
					}
					else if(node[j]["node-type"]=="host"||(node[j]["node-type"].indexOf("host")>=0)||node[j]["node-type"]=="dpi"){
						node[j].order=2;
					}
					else if(node[j]["node-type"]=="ext-group"){
						node[j].order=4;
					}
					else{
						node[j].order=3;
					}
				}
				console.log(node);
				node.sort(function(a,b){return a.order-b.order;});
				for (var j in node) {
					var nemo_str='';
					if(node[j]['node-type']=='host'||node[j]['node-type'].indexOf('host')>=0){
						 nemo_str += "IMPORT Node  " + node[j]['node-name'] + " Type " + node[j]['node-type'];
					}else{
						 nemo_str += "CREATE Node  " + node[j]['node-name'] + " Type " + node[j]['node-type'];
					}					
					var sub_node = node[j]['sub-node'];
					if (sub_node && sub_node.length > 0) {
						nemo_str += ' Contain ';
						for (var k in sub_node) {
							nemo_str += nodes[sub_node[k]['node-id']] + ',';
						}
						nemo_str = nemo_str.substring(0, nemo_str.length - 1);
					}
					var property = node[j]['property'];
					if (property && property.length > 0) {
						nemo_str += ' Property ';
						for (var k in property) {
							nemo_str += property[k]['property-name'] + ':"' + property[k]['property-values']['string-value'][0]['value'] + '",';
						}
						nemo_str = nemo_str.substring(0, nemo_str.length - 1);
					}
					nodeNemo.push(nemo_str);
				}
				for (var item in nodeNemo) {
					// alert(jQuery("#nemo_str_show").html);
					jQuery("#nemo_str_show").append('<p>'+nodeNemo[item]+';&nbsp;&nbsp;'+'</p>');
				}
				console.log(nodeNemo);
			} 

			else if (i == "connection") {
				var connectionNemo = [];
				var conn = user_data["objects"]['connection'];
				for (var j in conn) {
					var nemo_str = "CREATE Connection " + conn[j]['connection-name'] + ' Type ' + conn[j]['connection-type'];
					nemo_str += " Endnodes " + nodes[conn[j]['end-node'][0]['node-id']] + ',' + nodes[conn[j]['end-node'][1]['node-id']];
				    var property = conn[j]['property'];
				    if(property&&property.length>0){
				    	nemo_str+=" Property";
				    for(var k in property){
				    	nemo_str+=' '+property[k]['property-name']+':"';
				    	var property_value=property[k]['property-values'];
				    	console.log(property_value);
				    	for(var v in property_value){//int-value or string-value
				    		nemo_str+=property_value[v][0]['value'];
				    	}
				    	nemo_str+='",';
				    }
				    	nemo_str = nemo_str.substring(0,nemo_str.length-1);
				}

					connectionNemo.push(nemo_str);
				}
				for (var item in connectionNemo) {
					// alert(jQuery("#nemo_str_show").html);
					jQuery("#nemo_str_show").append('<p>'+connectionNemo[item]+';&nbsp;&nbsp;'+'</p>');
				}
				console.log(connectionNemo);
			} 

			else if (i = 'flow') {
				var flowNemo = [];
				var fl = user_data["objects"]['flow'];
				for (var j in fl) {
					var nemo_str = 'CREATE Flow ' + fl[j]['flow-name'];
					var match = fl[j]["match-item"];
					if (match && match.length > 0) {
						nemo_str += " Match ";
						for (var k in match) {
							if(match[k]["match-item-value"]['string-value'])
							nemo_str += match[k]['match-item-name'] + ':"' + match[k]["match-item-value"]['string-value'] + '",';
						    else if(match[k]["match-item-value"]['int-value'])
							nemo_str += match[k]['match-item-name'] + ':' + match[k]["match-item-value"]['int-value'] + ',';
						}
						nemo_str = nemo_str.substring(0, nemo_str.length - 1);
					}
					var property=fl[j]["property"];
						if(property && property.length > 0){
							nemo_str += " Property ";
							for (var k in property) {
							if(property[k]["property-name"])
							nemo_str += property[k]['property-name'] + ':"' + intentNodesArray[property[k]["property-values"]['string-value'][0]["value"]] + '",';
						}
						nemo_str = nemo_str.substring(0, nemo_str.length - 1);
						}
					flowNemo.push(nemo_str);
				}

				for (var item in flowNemo) {
					// alert(jQuery("#nemo_str_show").html);
					jQuery("#nemo_str_show").append('<p>'+flowNemo[item]+';&nbsp;&nbsp;'+'</p>');
				}
				console.log(flowNemo);
			}
		}


		for (var i in user_data["operations"]) {
			var opNemo = [];
			var operation = user_data["operations"]['operation'];
			for (var j in operation) {
				var nemo_str = 'CREATE Operation ' + operation[j]["operation-name"];
				// console.log(flow[operation[j]['target-object']]);
				if(flow[operation[j]['target-object']])
					nemo_str += ' Target ' + flow[operation[j]['target-object']];
				else
					nemo_str += ' Target ' + connection[operation[j]['target-object']];
				var condition = operation[j]['condition-segment'];
				if (condition && condition.length > 0) {
					nemo_str += " Condition ";
					condition = condition.sort(function(a,b){return a.order-b.order});
					console.log(condition);
					for (var k in condition) {
						nemo_str+=relChars[condition[k]["precursor-relation-operator"]];
						nemo_str+="(";
						nemo_str+=condition[k]["condition-parameter-name"];
						nemo_str+=opChars[condition[k]["condition-parameter-match-pattern"]];
						nemo_str+=condition[k]["condition-parameter-target-value"]["string-value"];
						nemo_str+=")";
					   
					}
				}
				var action = operation[j]['action'];
				if (action && action.length > 0) {
					nemo_str += " Action "
					for (var k in action) {
						if(action[k]['action-name']=='go-through')
					    nemo_str += action[k]['action-name'] + ":" + nodes[action[k]['parameter-values']["string-value"][0]['value']] + ',';
					   if(action[k]['action-name']=='deny')
					   	nemo_str+= action[k]['action-name']+',';
					   if(action[k]['action-name']=='qos-bandwidth'){
					   	nemo_str+= action[k]['action-name']+':'+action[k]["parameter-values"]["int-value"][0]["value"]+'kbps,';
					   }
					}
					nemo_str = nemo_str.substring(0, nemo_str.length - 1);
				}
				
				opNemo.push(nemo_str);
			}
				for (var item in opNemo) {
					// alert(jQuery("#nemo_str_show").html);
					jQuery("#nemo_str_show").append('<p>'+opNemo[item]+';&nbsp;&nbsp;'+'</p>');
				}
			console.log(opNemo);
		}
	}
