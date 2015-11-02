var lgroup_aviable_node = ["host:h1", "host:h2", "host:h3"];
function update_host(){  
    if(phy_hosts.length>0)
 { 
        lgroup_aviable_node=[];
    for(var i=0;i<phy_hosts.length;i++)
    {
        lgroup_aviable_node.push("host:"+phy_hosts[i]["host-name"]);
    }
  }
}

jQuery(document).ready(function ($) {
    //localStorage.removeItem("nemo_str");
    //localStorage.clear();
    //getnemo_str();
    //init service_name
    //gloable variable
	
	
function lead_policy(src_group,dest_group,flow_count_temp,color,id,chain_name,src_name,dest_name)
		{
			var cir_r = parseInt($("#"+src_group+" circle:eq(0)").attr("r"));
			
			//get circle cx cy
			src_cx = parseInt($("#"+src_group+" circle:eq(0)").attr("cx"));
			src_cy = parseInt($("#"+src_group+" circle:eq(0)").attr("cy"));
			dest_cx = parseInt($("#"+dest_group+" circle:eq(0)").attr("cx"));
			dest_cy = parseInt($("#"+dest_group+" circle:eq(0)").attr("cy"));
			console.log(src_cx+"  "+src_cy+"  "+dest_cx+"   "+dest_cy);
			
			//calculate deg
			var tri_h = dest_cx-src_cx;
			var tri_l = src_cy-dest_cy;
			var tri_s = Math.sqrt(tri_l*tri_l + tri_h*tri_h);
			var deg = Math.asin(tri_l/tri_s);
			console.log(tri_h+"  "+tri_l+"  "+tri_s);
			//alert(deg);
			console.log(deg);
			
			
			//calculate offset
			var offset = ((20+(15*parseInt(flow_count_temp)))/180)*Math.PI;
			console.log("temp: "+flow_count_temp+" offset:  "+offset);
			
			//calculate path possition
			var path_src_x,path_src_y,path_dest_x,path_dest_y,mid_x,mid_y,b_x,b_y;
			if(tri_h >= 0)
			{
				mid_x = src_cx + tri_h/2;
				mid_y = src_cy - tri_l/2;
				path_src_x = src_cx + cir_r*(Math.cos(offset+deg));
				path_src_y = src_cy - cir_r*(Math.sin(offset+deg));
				path_dest_x = dest_cx - (cir_r+14)*(Math.cos(offset-deg));
				path_dest_y = dest_cy - (cir_r+14)*(Math.sin(offset-deg));
				if(tri_l >= 0)
				{			
					b_x = mid_x - (80+30*parseInt(flow_count_temp))*Math.abs(Math.sin(deg));;
					b_y = mid_y - (80+30*parseInt(flow_count_temp))*Math.abs(Math.cos(deg));
				}
				else
				{
					b_x = mid_x + (80+30*parseInt(flow_count_temp))*Math.abs(Math.sin(deg));
					b_y = mid_y - (80+30*parseInt(flow_count_temp))*Math.abs(Math.cos(deg));
				}
				
			}
			else
			{
				mid_x = src_cx + tri_h/2;
				mid_y = src_cy - tri_l/2;
				path_src_x = src_cx - cir_r*(Math.cos(offset-deg));
				path_src_y = src_cy + cir_r*(Math.sin(offset-deg));
				path_dest_x = dest_cx + (cir_r+14)*(Math.cos(-offset-deg));
				path_dest_y = dest_cy - (cir_r+14)*(Math.sin(-offset-deg));
				if(tri_l >= 0)
				{			
					b_x = mid_x - (80+30*parseInt(flow_count_temp))*Math.abs(Math.sin(deg));
					b_y = mid_y + (80+30*parseInt(flow_count_temp))*Math.abs(Math.cos(deg));
				}
				else
				{
					b_x = mid_x + (80+30*parseInt(flow_count_temp))*Math.abs(Math.sin(deg));
					b_y = mid_y + (80+30*parseInt(flow_count_temp))*Math.abs(Math.cos(deg));
				}
				
			}
			//path title
			var path_title = document.createElementNS('http://www.w3.org/2000/svg', 'path');
			
			
			
			//create path
			var path = document.createElementNS('http://www.w3.org/2000/svg', 'path');
			var path_main = $(path).attr({
				id: id,
				node_start:src_name,
				node_end:dest_name,
				type:"flow",
				sx:path_src_x,
				sy:path_src_y,
				mx:b_x,
				my:b_y,
				ex:path_dest_x,
				ey:path_dest_y,
				count:flow_count_temp,
				via:chain_name,
				d: 'M'+path_src_x + " " + path_src_y+' Q'+b_x+' '+b_y+' '+path_dest_x+' '+path_dest_y,
				'stroke':color,
				'stroke-width':3,
				fill:"none",
				'stroke-dasharray':"6,6" ,
				'marker-end':"url(#idArrow)",
				'marker-mid':"url(#idtext)"		
			});
				$('#service_svg').prepend(path_main);		

	}	
	function flow_get_end_name()
	{
		var src_ip = $("#src_ip").val().trim();
		var dest_ip = $("#dst_ip").val().trim();
		var host_name = ['',''];
		if(typeof(phy_host_ip[src_ip]) != "undefined")
			host_name[0] = phy_host_ip[src_ip];
		else
		{
			for(var find_ip_cursor = 0;find_ip_cursor<$("#service_svg g").length;find_ip_cursor++)
			{
				if($("#service_svg g:eq("+find_ip_cursor+")").attr("ip-prefix") == src_ip)
				{
					host_name[0] = $("#service_svg g:eq("+find_ip_cursor+")").attr("id");
				}	
				
			}
		}
		if(typeof(phy_host_ip[dest_ip]) != "undefined")
			host_name[1] = phy_host_ip[dest_ip];
		else
		{
			for(var find_ip_cursor = 0;find_ip_cursor<$("#service_svg g").length;find_ip_cursor++)
			{
				if($("#service_svg g:eq("+find_ip_cursor+")").attr("ip-prefix") == dest_ip)
				{
					host_name[1] = $("#service_svg g:eq("+find_ip_cursor+")").attr("id");
				}	
				
			}
		}		
		console.log(host_name[0]+" "+host_name[1]);
		return host_name;
		
		
	}
	function flow_get_group()
	{
		var src_host_name = flow_get_end_name()[0];
		var dest_host_name = flow_get_end_name()[1];
		var group_node = ['','']; 		
		for(var i = 0;i<$("#service_svg g").length;i++)
		{	
			host_list = $("#service_svg g:eq("+i+")").attr("sub").split(",");
			console.log(host_list);
			if(host_list[0] == '')
			{
				continue;
			}					
			for(var j =0;j<host_list.length;j++)
			{
				if(host_list[j] == src_host_name)
				{
					group_node[0] = $("#service_svg g:eq("+i+")").attr("id");
				}
				if(host_list[j] == dest_host_name)
				{
					group_node[1] = $("#service_svg g:eq("+i+")").attr("id");
				}	
			}
			if(group_node[0] != '' && group_node[1] != '')
				break;
		}
		console.log("node: "+group_node[0]+"  "+group_node[1]);
		return group_node;
	}
	function get_connection_node()
	{
		var p2p_con_name1 = $("#p2p_node_group").find("tr").eq(1).find("td").eq(1).find("span").eq(0).text().split(":");
		var p2p_re1 ;
		if(p2p_con_name1.length==1)
			p2p_re1=p2p_con_name1[0];
		else
			p2p_re1=p2p_con_name1[1];
		var p2p_con_name2 = $("#p2p_node_group").find("tr").eq(3).find("td").eq(1).find("span").eq(0).text().split(":");
		var p2p_re2 ;
		if(p2p_con_name2.length==1)
			p2p_re2=p2p_con_name2[0];
		else
			p2p_re2=p2p_con_name2[1];				
	    var node_name_1 = p2p_re1;
		var node_name_2 = p2p_re2;	
		var end_node = [];
		end_node[0] = node_name_1;
		end_node[1] = node_name_2;
		return end_node;
		
	}
	function get_path_color(){
		while(1)
		{
		   var num1 = Math.floor(Math.random()*256);
		   if(num1>220 )
			   continue;
		   return num1.toString();
		}
	}
	
	function redraw_flow_possition()
	{
		for(var i=0; i<$("#service_svg path").length;i++)
		{
			if($("#service_svg path:eq("+i+")").attr("type") != "flow")
				continue;
			
			if($("#service_svg path:eq("+i+")").attr("via").split(",")[0] == "none")
			{
				
				var src_group = $("#service_svg path:eq("+i+")").attr("node_start");
				var dest_group = $("#service_svg path:eq("+i+")").attr("node_end");
				var flow_count = $("#service_svg path:eq("+i+")").attr("count");
				var flow_id = $("#service_svg path:eq("+i+")").attr("id");
				var flow_color = $("#service_svg path:eq("+i+")").attr("stroke");
				$("#service_svg path:eq("+i+")").remove();
				lead_policy(src_group,dest_group,flow_count,flow_color,flow_id,"none",src_group,dest_group);		
			}
			else
			{
				if($("#service_svg path:eq("+i+")").attr("id").split("_")[1] == "1")
				{
					var src_group = $("#service_svg path:eq("+i+")").attr("node_start");
					var via_group = $("#service_svg path:eq("+i+")").attr("via").split(",")[0];
					var dest_group = $("#service_svg path:eq("+i+")").attr("node_end");
					var flow_count = $("#service_svg path:eq("+i+")").attr("count");
					var flow_id = $("#service_svg path:eq("+i+")").attr("id");
					var flow_color = $("#service_svg path:eq("+i+")").attr("stroke");
					$("#service_svg path:eq("+i+")").remove();
					lead_policy(src_group,via_group,flow_count,flow_color,flow_id,via_group,src_group,dest_group);
				}
				else if($("#service_svg path:eq("+i+")").attr("id").split("_")[1] == "2")
				{
					var src_group = $("#service_svg path:eq("+i+")").attr("node_start");
					var via_group = $("#service_svg path:eq("+i+")").attr("via").split(",")[0];
					var dest_group = $("#service_svg path:eq("+i+")").attr("node_end");
					var flow_count = $("#service_svg path:eq("+i+")").attr("count");
					var flow_id = $("#service_svg path:eq("+i+")").attr("id");
					var flow_color = $("#service_svg path:eq("+i+")").attr("stroke");
					$("#service_svg path:eq("+i+")").remove();
					lead_policy(via_group,dest_group,flow_count,flow_color,flow_id,via_group,src_group,dest_group);
				}
			}
		
		
		}
		
	}
	function redraw_connection_possition()
	{
		for(var i=0; i<$("#service_svg path").length;i++)
		{
			if($("#service_svg path:eq("+i+")").attr("type") != "connection")
				continue;
			var node_name_1 = $("#service_svg path:eq("+i+")").attr("node_start");
			var node_name_2 = $("#service_svg path:eq("+i+")").attr("node_end");
			var node_cx_1 = $("#"+node_name_1+"_group").attr("cx");;
			var node_cy_1 = $("#"+node_name_1+"_group").attr("cy");
			var node_cx_2 = $("#"+node_name_2+"_group").attr("cx");
			var node_cy_2 = $("#"+node_name_2+"_group").attr("cy");
			var conn_id = $("#service_svg path:eq("+i+")").attr("id")
			var path = document.createElementNS('http://www.w3.org/2000/svg', 'path');
			var path_main = $(path).attr({
				id: conn_id,
				node_start:node_name_1,
				node_end:node_name_2,
				type:"connection",
				d: 'M'+node_cx_1 + " " + node_cy_1+' L'+node_cx_2+' '+node_cy_2,
				'stroke':"black",
				'stroke-width':3,
				fill:"black"
			});
			$("#service_svg path:eq("+i+")").remove();
			$('#service_svg').prepend(path_main);					
		}		
	}
	function redraw_node_possition()
	{
		node_count = $("#service_svg g").length;
		if(node_count == 1)
		{
			var x = 550;
            var y = 450;
			dis_x = x-$("#service_svg g:eq(0) circle:eq(0)").attr("cx");
			dis_y = y-$("#service_svg g:eq(0) circle:eq(0)").attr("cy");
			$("#service_svg g:eq(0) circle:eq(0)").attr("cx",x);
			$("#service_svg g:eq(0) circle:eq(0)").attr("cy",y);
			for(var j =0;j<$("#service_svg g:eq(0) rect").length;j++)
			{
				var last_x = parseInt($("#service_svg g:eq(0) rect:eq("+j+")").attr("x"));
				var last_y = parseInt($("#service_svg g:eq(0) rect:eq("+j+")").attr("y"));
				$("#service_svg g:eq(0) rect:eq("+j+")").attr("x",last_x+dis_x);
				$("#service_svg g:eq(0) rect:eq("+j+")").attr("y",last_y+dis_y);
			}
			for(var j =0;j<$("#service_svg g:eq(0) text").length;j++)
			{
				$("#service_svg g:eq(0) text:eq("+j+")").attr("x",parseInt($("#service_svg g:eq(0) text:eq("+j+")").attr("x"))+dis_x);
				$("#service_svg g:eq(0) text:eq("+j+")").attr("y",parseInt($("#service_svg g:eq(0) text:eq("+j+")").attr("y"))+dis_y);			
			}
			
			 return;
			
		}
		var res = calculatePos(node_count);
        var r = 300;
		for(var i =0;i<node_count;i++)
		{
			var deg = res[i];
            var x = 550 + Math.cos(deg) * r;
            var y = 450 - Math.sin(deg) * r;
			dis_x = x-parseInt($("#service_svg g:eq("+i+") circle:eq(0)").attr("cx"));
			dis_y = y-parseInt($("#service_svg g:eq("+i+") circle:eq(0)").attr("cy"));
			$("#service_svg g:eq("+i+") circle:eq(0)").attr("cx",x);
			$("#service_svg g:eq("+i+") circle:eq(0)").attr("cy",y);
			for(var j =0;j<$("#service_svg g:eq("+i+") rect").length;j++)
			{
				$("#service_svg g:eq("+i+") rect:eq("+j+")").attr("x",parseInt($("#service_svg g:eq("+i+") rect:eq("+j+")").attr("x"))+dis_x);
				$("#service_svg g:eq("+i+") rect:eq("+j+")").attr("y",parseInt($("#service_svg g:eq("+i+") rect:eq("+j+")").attr("y"))+dis_y);
			}
			for(var j =0;j<$("#service_svg g:eq("+i+") text").length;j++)
			{
				$("#service_svg g:eq("+i+") text:eq("+j+")").attr("x",parseInt($("#service_svg g:eq("+i+") text:eq("+j+")").attr("x"))+dis_x);
				$("#service_svg g:eq("+i+") text:eq("+j+")").attr("y",parseInt($("#service_svg g:eq("+i+") text:eq("+j+")").attr("y"))+dis_y);			
			}
			
		}

	}
	function redraw_specific_node_possition(group_name,x,y)
	{
		dis_x = x-$("#"+group_name+" circle:eq(0)").attr("cx");
		dis_y = y-$("#"+group_name+" circle:eq(0)").attr("cy");
		$("#"+group_name+" circle:eq(0)").attr("cx",x);
		$("#"+group_name+" circle:eq(0)").attr("cy",y);
		for(var j =0;j<$("#"+group_name+" rect").length;j++)
		{
			var last_x = parseInt($("#"+group_name+" rect:eq("+j+")").attr("x"));
			var last_y = parseInt($("#"+group_name+" rect:eq("+j+")").attr("y"));
			$("#"+group_name+" rect:eq("+j+")").attr("x",last_x+dis_x);
			$("#"+group_name+" rect:eq("+j+")").attr("y",last_y+dis_y);
		}
		for(var j =0;j<$("#"+group_name+" text").length;j++)
		{
			$("#"+group_name+" text:eq("+j+")").attr("x",parseInt($("#"+group_name+" text:eq("+j+")").attr("x"))+dis_x);
			$("#"+group_name+" text:eq("+j+")").attr("y",parseInt($("#"+group_name+" text:eq("+j+")").attr("y"))+dis_y);			
		}
		return;
	}
	function draw_group(node_number,table_name)
	{
		var circle_x = 550;
		var circle_y = 450;
		if(node_number == 0)
		{
			var g = document.createElementNS('http://www.w3.org/2000/svg', 'g');
			var circle_main = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
			var inCircle1_main = $(circle_main).attr({
				id: $("#node_name").val().trim() + "_group",
				cx: circle_x,
				cy: circle_y,
				r:90,
				'stroke':"black",
				'stroke-width':3,
				fill:"white"
			});
			var text1 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text1_main = $(text1).attr({
				id: $("#node_name").val().trim() + "_title1",
				x: circle_x,
				y: circle_y-8,
				fill:"black",
				'text-anchor': 'middle',
				"stroke":"black",
				"stroke-width":0.8
				
			});
			$(text1).text($("#sel_show").children('option:selected').val());
			var text2 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text2_main = $(text2).attr({
				id: $("#node_name").val().trim() + "_title2",
				x: circle_x,
				y: circle_y+10,
				fill:"                       ",
				'text-anchor': 'middle'
			});
			$(text2).text($("#node_name").val().trim());
			var g_main = $(g).append(inCircle1_main);
			$(g).append(text1_main);
			$(g).append(text2_main);
			$(g).attr("id",$("#node_name").val().trim());
			$(g).attr("type",$("#sel_show").children('option:selected').val());
			if(table_name != "ext")
				$(g).attr("sub","");
			else
			{
				$(g).attr("sub",$("#node_name").val().trim());
				$(g).attr("ip-prefix",$("#ext-group_ip-prefix").val().trim());
				//ext_ip[$("#ext-group_ip-prefix").val().trim()] = $("#node_name").val().trim();
		
			}
			if(table_name.indexOf("chain") > -1)
				$(g).attr("flow",0);
			$('#service_svg').append(g_main);	
		}
		else if(node_number == 1)
		{
			var g = document.createElementNS('http://www.w3.org/2000/svg', 'g');
			var circle_main = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
			var inCircle1_main = $(circle_main).attr({
				id: $("#node_name").val().trim() + "_group",
				cx: circle_x,
				cy: circle_y,
				r:90,
				'stroke':"black",
				'stroke-width':3,
				fill:"white"
			});
			var host1 = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
			var host1_main = $(host1).attr({
				id: $("#node_name").val().trim() + "_title1",
				x: circle_x-57,
				y: circle_y-25,
				width:114,
				height:50,
				'stroke':"black",
				'stroke-width':1,
				fill:"white"
			});
			var text1 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text1_main = $(text1).attr({
				id: $("#node_name").val().trim() + "_title2",
				x: circle_x,
				y: circle_y-48,
				fill:"black",
				'text-anchor': 'middle',
				"stroke":"black",
				"stroke-width":0.8
			});
			$(text1).text($("#sel_show").children('option:selected').val());
			var text2 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text2_main = $(text2).attr({
				id: $("#"+table_name+" tr:eq(0) td:eq(0)").text(),
				x: circle_x,
				y: circle_y-30,
				fill:"black",
				'text-anchor': 'middle'
			});
			$(text2).text($("#node_name").val().trim());
			var text3 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text3_main = $(text3).attr({
				id: $("#"+table_name+" tr:eq(0) td:eq(0)").text().split(":")[1] + "_text",
				x: circle_x,
				y: circle_y,
				fill:"black",
				'text-anchor': 'middle'
			});
			$(text3).text($("#"+table_name+" tr:eq(0) td:eq(0)").text().split(":")[1]);
			var g_main = $(g).append(inCircle1_main);
			$(g).append(host1_main);
			$(g).append(text1_main);
			$(g).append(text2_main);
			$(g).append(text3_main);
			$(g).attr("id",$("#node_name").val().trim());
			$(g).attr("type",$("#sel_show").children('option:selected').val());
			$(g).attr("sub",$("#"+table_name+" tr:eq(0) td:eq(0)").text().split(":")[1]);
			if(table_name.indexOf("chain") > -1)
				$(g).attr("flow",0);
			$('#service_svg').append(g_main);				
		}
		else if(node_number == 2)
		{
			var g = document.createElementNS('http://www.w3.org/2000/svg', 'g');
			var circle_main = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
			var inCircle1_main = $(circle_main).attr({
				id: $("#node_name").val().trim() + "_group",
				cx: circle_x,
				cy: circle_y,
				r:90,
				'stroke':"black",
				'stroke-width':3,
				fill:"white"
			});
			var host1 = document.createElementNS('http://www.w3.org/2000/svg', 'rect');								
			var host1_main = $(host1).attr({
				id:$("#"+table_name+" tr:eq(0) td:eq(0)").text(),
				x: circle_x-55,
				y: circle_y-25,
				width:110,
				height:34,
				'stroke':"black",
				'stroke-width':1,
				fill:"white"
			});
			var host2 = document.createElementNS('http://www.w3.org/2000/svg', 'rect');								
			var host2_main = $(host2).attr({
				id: $("#"+table_name+" tr:eq(1) td:eq(0)").text(),
				x: circle_x-55,
				y: circle_y+18,
				width:110,
				height:34,
				'stroke':"black",
				'stroke-width':1,
				fill:"white"
			});
			var text1 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text1_main = $(text1).attr({
				id: $("#node_name").val().trim() + "_title1",
				x: circle_x,
				y: circle_y-48,
				fill:"black",
				'text-anchor': 'middle',
				"stroke":"black",
				"stroke-width":0.8
			});
			$(text1).text($("#sel_show").children('option:selected').val());
			var text2 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text2_main = $(text2).attr({
				id:  $("#node_name").val().trim() + "_title2",
				x: circle_x,
				y: circle_y-31,
				fill:"black",
				'text-anchor': 'middle'
			});
			$(text2).text($("#node_name").val().trim());
			var text3 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text3_main = $(text3).attr({
				id: $("#"+table_name+" tr:eq(0) td:eq(0)").text().split(":")[1] + "_text",
				x: circle_x,
				y: circle_y-5,
				fill:"black",
				'text-anchor': 'middle'
			});
			$(text3).text($("#"+table_name+" tr:eq(0) td:eq(0)").text().split(":")[1]);
			var text4 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text4_main = $(text4).attr({
				id: $("#"+table_name+" tr:eq(1) td:eq(0)").text().split(":")[1] + "_text",
				x: circle_x,
				y: circle_y+40,
				fill:"black",
				'text-anchor': 'middle'
			});
			$(text4).text($("#"+table_name+" tr:eq(1) td:eq(0)").text().split(":")[1]);
			var g_main = $(g).append(inCircle1_main);
			$(g).append(host1_main);
			$(g).append(host2_main);
			$(g).append(text1_main);
			$(g).append(text2_main);
			$(g).append(text3_main);
			$(g).append(text4_main);
			$(g).attr("id",$("#node_name").val().trim());
			$(g).attr("type",$("#sel_show").children('option:selected').val());
			$(g).attr("sub",$("#"+table_name+" tr:eq(0) td:eq(0)").text().split(":")[1]+","+$("#"+table_name+" tr:eq(1) td:eq(0)").text().split(":")[1]);
			if(table_name.indexOf("chain") > -1)
				$(g).attr("flow",0);
			$('#service_svg').append(g);
			
		}
		else if(node_number == 3)
		{
			var g = document.createElementNS('http://www.w3.org/2000/svg', 'g');
			var g = document.createElementNS('http://www.w3.org/2000/svg', 'g');
			var circle_main = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
			var inCircle1_main = $(circle_main).attr({
				id: $("#node_name").val().trim() + "_group",
				cx: circle_x,
				cy: circle_y,
				r:90,
				'stroke':"black",
				'stroke-width':3,
				fill:"white"
			});
			var host1 = document.createElementNS('http://www.w3.org/2000/svg', 'rect');								
			var host1_main = $(host1).attr({
				id: $("#"+table_name+" tr:eq(0) td:eq(0)").text(),
				x: circle_x-55,
				y: circle_y-31,
				width:110,
				height:25,
				'stroke':"black",
				'stroke-width':1,
				fill:"white"
			});
			var host2 = document.createElementNS('http://www.w3.org/2000/svg', 'rect');								
			var host2_main = $(host2).attr({
				id: $("#"+table_name+" tr:eq(1) td:eq(0)").text(),
				x: circle_x-55,
				y: circle_y-3,
				width:110,
				height:25,
				'stroke':"black",
				'stroke-width':1,
				fill:"white"
			});
			var host3 = document.createElementNS('http://www.w3.org/2000/svg', 'rect');								
			var host3_main = $(host3).attr({
				id: $("#"+table_name+" tr:eq(2) td:eq(0)").text(),
				x: circle_x-55,
				y: circle_y+27,
				width:110,
				height:25,
				'stroke':"black",
				'stroke-width':1,
				fill:"white"
			});
			var text1 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text1_main = $(text1).attr({
				id: $("#node_name").val().trim() + "_title1",
				x: circle_x,
				y: circle_y-51,
				fill:"black",
				'text-anchor': 'middle',
				"stroke":"black",
				"stroke-width":0.8
			});
			$(text1).text($("#sel_show").children('option:selected').val());
			var text2 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text2_main = $(text2).attr({
				id: $("#node_name").val().trim() + "_title2",
				x: circle_x,
				y: circle_y-36,
				fill:"black",
				'text-anchor': 'middle'
			});
			$(text2).text($("#node_name").val().trim());
			var text3 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text3_main = $(text3).attr({
				id: $("#"+table_name+" tr:eq(0) td:eq(0)").text().split(":")[1] + '_text',
				x: circle_x,
				y: circle_y-13,
				fill:"black",
				'text-anchor': 'middle'
			});
			$(text3).text($("#"+table_name+" tr:eq(0) td:eq(0)").text().split(":")[1]);
			var text4 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text4_main = $(text4).attr({
				id: $("#"+table_name+" tr:eq(1) td:eq(0)").text().split(":")[1] + '_text',
				x: circle_x,
				y: circle_y+13,
				fill:"black",
				'text-anchor': 'middle'
			});
			$(text4).text($("#"+table_name+" tr:eq(1) td:eq(0)").text().split(":")[1]);
			var text5 = document.createElementNS('http://www.w3.org/2000/svg', 'text');
			var text5_main = $(text5).attr({
				id: $("#"+table_name+" tr:eq(2) td:eq(0)").text().split(":")[1] + '_text',
				x: circle_x,
				y: circle_y+43,
				fill:"black",
				'text-anchor': 'middle'
			});
			$(text5).text($("#"+table_name+" tr:eq(2) td:eq(0)").text().split(":")[1]);
			var g_main = $(g).append(inCircle1_main);
			$(g).append(host1_main);
			$(g).append(host2_main);
			$(g).append(host3_main);
			$(g).append(text1_main);
			$(g).append(text2_main);
			$(g).append(text3_main);
			$(g).append(text4_main);
			$(g).append(text5_main);
			$(g).attr("id",$("#node_name").val().trim());
			$(g).attr("type",$("#sel_show").children('option:selected').val());
			$(g).attr("sub",$("#"+table_name+" tr:eq(0) td:eq(0)").text().split(":")[1]+","+$("#"+table_name+" tr:eq(1) td:eq(0)").text().split(":")[1]+","+$("#"+table_name+" tr:eq(2) td:eq(0)").text().split(":")[1]);
			if(table_name.indexOf("chain") > -1)
				$(g).attr("flow",0);
			$('#service_svg').append(g);	
		}		
	}
    var sevice_name_glb = "";
    var entity_name_glb = "";

    var group_node = new Array();

    var chain_aviable_node = [];//fw lb cache
    
    var conn_aviable_node = [];//l2-group l3-group ext-group ,useless
    
    var chain_node=[];//all chain group only, useless
    //group_node = get_nodes_array();
    //console.log("group_node:" + group_node);
    var src_ip = "", dest_ip = "", src_port = "", dest_port = "", protocol = "", vlanid = "";
    //function
    function getSevice_names() {
        $(".NE_up #sel_1").find("option").remove();
        var get_service_names = localStorage.getItem("service_names");
        console.log(get_service_names);
        if (!get_service_names || typeof (get_service_names) == "undefined")
            return;
        if (get_service_names != null || get_service_names != "") {
            svc_name = get_service_names.split(",");
            for (var k = 0; k < svc_name.length; k++) {
                if (svc_name[k] != "")
                    $(".NE_up #sel_1").append("<Option value='" + svc_name[k] + "'>" + svc_name[k] + "</Option>");
            }
        }
        $("#sel_1").val(service_select).attr("disabled", true);
    }
    function setnemo_str() {
        var service_name = "";
        service_name = $("#sel_1").children("option:selected").val();
        if (!service_name || typeof (service_name) == "undefined") return;
        str = "";
        //$("#nemo_str_show p").each(function () {
        //    str += $(this).html() + "|";
        //});
        //str = str.substring(0, str.length - 1);
        str = $("#nemo_str_show").html();
        //console.log("set_nemo:"+str);
        localStorage.setItem(service_name + "_nemo_str", str);
    }
    function getnemo_str() {
        $("#nemo_str_show").empty();
        var service_name = "";
        service_name = $("#sel_1").children("option:selected").val();
        if (!service_name || typeof (service_name) == "undefined")
            return;
        var str = localStorage.getItem(service_name + "_nemo_str");
        //console.log("nemo_str:"+str);
        //if (!str || typeof (str) == "undefined")
        //    return;
        //str = str.split("|");

        //for (s = 0; s < str.length; s++) {
        //    $("#nemo_str_show").append("<p class='key'>" + str[s] + "</p>");
        //}
        //console.log("get_nemo:" + str);
        $("#nemo_str_show").append(str);
    }

function get_aviable_node(type){
    var service_name = "";
    service_name = $("#sel_1").children("option:selected").val();
    if (!service_name || typeof (service_name) == "undefined") return;
    if(type=="lgroup"){
    var lgroup_str=localStorage.getItem(service_name+"_lgroup_aviable_node");
    if(!lgroup_str) return;
    var $json_str=JSON.parse(lgroup_str);
    lgroup_aviable_node = $json_str.lgroup_aviable_node;
    console.log($json_str.lgroup_aviable_node);
    }
    else if(type=="chain"){
    var chain_str=localStorage.getItem(service_name+"_chain_aviable_node");
    if(!chain_str) return;
    var $json_str=JSON.parse(chain_str);
    chain_aviable_node = $json_str.chain_aviable_node;
    console.log($json_str.chain_aviable_node);

    }
}
function set_aviable_node(type){
    var service_name = "";
    service_name = $("#sel_1").children("option:selected").val();
    if (!service_name || typeof (service_name) == "undefined") return;
    if(type=="lgroup"){
    var lgroup_str=localStorage.getItem(service_name+"_lgroup_aviable_node");
    if(!lgroup_str) return;
    var $json_str=JSON.parse(lgroup_str);
    $json_str.lgroup_aviable_node=lgroup_aviable_node;
    localStorage.setItem(service_name+"_lgroup_aviable_node",JSON.stringify($json_str));
    console.log($json_str.lgroup_aviable_node);
    }
    else if(type=="chain"){
    var chain_str=localStorage.getItem(service_name+"_chain_aviable_node");
    if(!chain_str) return;
    var $json_str=JSON.parse(chain_str);
    $json_str.chain_aviable_node=chain_aviable_node;
    localStorage.setItem(service_name+"_lgroup_aviable_node",JSON.stringify($json_str));
    console.log($json_str.chain_aviable_node);

    }
}

    function setentity_instance_list() {

        var sevice_name = "";
        sevice_name = $("#sel_1").children('option:selected').val().trim();
        if (sevice_name == "") return;
        current_entity_instance_list = "";
        $(".NE_up #sel_2 option").each(function () {
            current_entity_instance_list += $(this).val().trim() + ",";
        });
        //console.log("cur_entity_instance_list:" + current_entity_instance_list);
        current_entity_instance_list = current_entity_instance_list.substring(0, (current_entity_instance_list.length - 1));
        console.log("setentity_instance_list now_entity_instance_list: " + current_entity_instance_list);
        localStorage.setItem(sevice_name + "_entity_instance_list", current_entity_instance_list);
    }

    function setentity_instance_list_() {
        var sevice_name = "";
        sevice_name = $("#sel_1").children('option:selected').val().trim();
        if (sevice_name == "") return;
        current_entity_instance_list = "";

        localStorage.setItem(sevice_name + "_entity_instance_list_", $(".NE_up #sel_2").html());
        console.log("setentity_instance_list_: " + $(".NE_up #sel_2").html());

    }
    function getentity_instance_list() {
        var sevice_name = "";
        sevice_name = $("#sel_1").children('option:selected').val();
        if (sevice_name == "")
            return;
        var entity_instance_list = localStorage.getItem(sevice_name + "_entity_instance_list");
        console.log(entity_instance_list);
        if (!entity_instance_list && typeof (entity_instance_list) != "undefined" && entity_instance_list != 0)
            return;
        $("#sel_2").empty();
        if (entity_instance_list != null || entity_instance_list != "") {
            svc_name = entity_instance_list.split(",");
            for (var k = 0; k < svc_name.length; k++) {
                if (svc_name[k] != "")
                    $(".NE_up #sel_2").append("<Option value='" + svc_name[k] + "'>" + svc_name[k] + "</Option>");
            }
        }
    }
    function getentity_instance_list_() {
        var sevice_name = "";
        sevice_name = $("#sel_1").children('option:selected').val();
        if (sevice_name == "") return;
        var entity_instance_list = localStorage.getItem(sevice_name + "_entity_instance_list_");
        console.log(entity_instance_list);
        if (!entity_instance_list || typeof (entity_instance_list) == "undefined") return;
        $("#sel_2").empty().append(entity_instance_list);
    }
    //connection node
    function query_node_show() {
        var service_name = "";
        service_name = $("#sel_1").children("option:selected").val();
        if (!service_name || typeof (service_name) == "undefined") return;
        if ($("#sel_2 option").length == 0) return;
        $("#p2p_select,#p2mp_select,#mesh_select").empty();
        $("#sel_2 option").each(function () {
            if ($(this).val().trim().indexOf("node") >= 0) {
                var j = service_name + "__" + $(this).val().replace(":", "_");
                var entity = localStorage.getItem(j)
                if (!entity || typeof (entity) == "undefined") return true;
                var json = jQuery.parseJSON(entity);
                if (json[j]["node_type"] == "fw" || json[j]["node_type"] == "lb" || json[j]["node_type"] == "cache" ) return true;
                var str = $(this).val().split(":");
                $("#p2p_select,#p2mp_select,#mesh_select").append("<Option value='" + str[1] + "'>" + str[1]+ "</Option>");
            }
        });
    }
    //get nodes include firewall and loadbalance
    function get_nodes(id_) {
        var service_name = "";
        service_name = $("#sel_1").children("option:selected").val();
        if (!service_name || typeof (service_name) == "undefined") return;
        if ($("#sel_2 option").length == 0) return;
        $("#" + id_).empty();
        $("#sel_2 option").each(function () {
            if ($(this).val().trim().indexOf("node") >= 0) {
                var j = service_name + "__" + $(this).val().replace(":", "_");
                var entity = localStorage.getItem(j)
                if (!entity || typeof (entity) == "undefined") return true;
                var json = jQuery.parseJSON(entity);
                if (json[j]["node_type"] == "firewall" || json[j]["node_type"] == "loadbalance")
                    return true;
                var str = $(this).val().split(":");
                $("#" + id_).append("<option value='" + str[1] + "'>" + str[1] + "</option>");
            }
        });
    }
    function set_available_node()
    {
        $("#p2p_select,#p2mp_select,#mesh_select").empty();
        for (var i = 0; i <conn_aviable_node.length; i++) {
            $("#p2p_select,#p2mp_select,#mesh_select").append("<option value=" + conn_aviable_node[i] + ">" + conn_aviable_node[i]+ "</option>");
        }
    }
    function get_nodes_array() {
        var nodes = new Array();
        var service_name = localStorage.getItem("service_names");
        //service_name = $("#sel_1").children("option:selected").val();
        //console.log(service_name);
        if (!service_name || typeof (service_name) == "undefined") return nodes;
        var str = localStorage.getItem(service_name + "_entity_instance_list");
        //console.log("entity_list:" + str);
        if (!str || typeof (str) == "undefined") return nodes;
        str = str.split(",");

        for (var i = 0; i < str.length; i++) {
            var j = service_name + "__" + str[i].replace(":", "_");
            var json_str = localStorage.getItem(j);
            //console.log(json_str);
            var json = jQuery.parseJSON(json_str);
            if (json[j]["node_type"] == "firewall" || json[j]["node_type"] == "loadbalance")
                continue;
            nodes.push(json[j]["node_name"]);
        }
        //console.log("nodes:"+nodes);
        return nodes;
    }

    //create table function
    function CreateTable(rowCount, arr) {
        var table = $("<table id='tab' border=\"0\">");
        table.appendTo($("#p2mp_node-name2"));
        for (var i = 0; i < rowCount; i++) {
            var tr = $("<tr></tr>");
            tr.appendTo(table);
            var td = $("<td> <input type='checkbox'> </td><td>" + arr[i] + "</td>");
            td.appendTo(tr);
        }
        $("#p2mp_node-name2").append("</table>");
    }
    function check_policy(entity_name) {
        var sevice_name = "";
        sevice_name = $("#sel_1").children('option:selected').val();
        if (sevice_name == "")
            return;
        //entity = entity.split(":");
        var str = sevice_name + "_policy_list";

        console.log(str);
        var policy_list = localStorage.getItem(str);
        var last_policy = policy_list;
        console.log(policy_list);
        if (policy_list && typeof (policy_list) != "undefined") {
            policy_list = policy_list.split(",");
            console.log(policy_list);
            for (var i = 0; i < policy_list.length; i++) {
                json_key = sevice_name + "__policy_" + policy_list[i];
                //console.log(json_key);
                var policy = localStorage.getItem(json_key);
                //console.log(policy);
                if (!policy || typeof (policy) == "undefined")
                    return true;
                json = JSON.parse(policy);
                //console.log(entity_name);
                if (json[json_key]["apply_entity_id"] == entity_name) {
                    //console.log("true");
                    localStorage.removeItem(json_key);
                    console.log(policy_list);
                    temp = last_policy.split(",");
                    var ss = "";
                    for (var i = 0; i < temp.length; i++) {
                        if (temp[i] != json[json_key]["policy_name"]) {
                            ss += temp[i] + ",";
                        }
                    }
                    ss = ss.substring(0, ss.length - 1);
                    last_policy = ss;
                    console.log("last_policy:" + last_policy);
                    //console.log(policy_list);
                    $("#nemo_str_show p").each(function () {
                        {
                            console.log("policy_id:" + json[json_key]["policy_id"]);
                            console.log($(this).html());
                            if ($(this).html().indexOf(json[json_key]["policy_id"]) >= 0)
                                $(this).remove();
                        }
                    });
                    setnemo_str();
                }

            }
        }
        localStorage.setItem(sevice_name + "_policy_list", last_policy);
    }

    //network_entity_init（service name,Entity Instance list）
    $("#net_ent_init").click(function () {
        getSevice_names();
        $(".NE_up #sel_2").find("option").remove();
        getentity_instance_list_();
        getnemo_str();
        $(".NE_close_show").click();
        update_host();
		//svg recover
        var sevice_name = $("#sel_1").val();
        if(!sevice_name) return;
        $("#service_svg").show();       
        $("#service_svg2").hide();  
        //document.getElementById("service_svg2").style.display="none";     
		var svg_rec = localStorage.getItem(sevice_name+"_svg");
        if(svg_rec)
		$("#service_svg").html(svg_rec);
		
    });
    //servicr_name change event
    $(".NE_up #sel_1").change(function () {
        $(this).children('option:selected').val();
        $(".NE_up #sel_2").find("option").remove();
        getnemo_str();
        getentity_instance_list_();
    });
    //node type change enevt
    $(".NE_middle #sel_show").change(function () {
        var id_ = $(this).children('option:selected').val() + "_type";
        $(".node_type_group").hide();
        $("#" + id_).show();
        $(".node_type_group_group").each(function () {
            $(this).find("select").eq(0).children().remove();
            //$(this).find("select").eq(2).children().remove();
        });
        for (var i = 0; i < lgroup_aviable_node.length; i++) {
            $("#l2_addhost,#l3_addhost").append("<option value='" + lgroup_aviable_node[i] + "'>" + lgroup_aviable_node[i] + "</option>");
        }
        for (var i = 0; i < chain_aviable_node.length; i++) {
            $("#chain_addhost").append("<option value='" + chain_aviable_node[i] + "'>" + chain_aviable_node[i] + "</option>");
        }
    });
    //connection type chang event
    $(".NE_conn_show #con_type").change(function () {
        var id_ = $(this).children('option:selected').val() + "_type";
        $(".conn_type_group").hide();
        $("#" + id_).show();
        //set_available_node();
        query_node_show();

    });
    //add entity type(node connection flow)
    $("#add_entity").click(function () {
        //check service name
        if ($("#sel_1 option").length == 0) return;
        //content init
        $(".NE_close_show").click();
        //id init
        var class_ = "NE_" + $("#entity_type").children('option:selected').val() + "_show";
        $(".id_").each(function () { $(this).val(guid()).attr("disabled", "true") });
        $(".NE_close_item").hide();
        $("." + class_).show();
        //node host init
        get_aviable_node("lgroup");
        get_aviable_node("chain");
        //connection init
        query_node_show();       
        $("#p2p_node-name2 option:first").remove();
        $("#con_type").get(0).selectedIndex = 0;
        $("#con_type").change();
        //validate init
        $(".alert_").attr("src", "src/app/nemo/images/alert.png");
        $("#mac_icon").attr("src", "");
        $("#src_ip_icon").attr("src", "");
        $("#dest_ip_icon").attr("src", "");
        $("#src_port_icon").attr("src", "");
        $("#dest_port_icon").attr("src", "");
        $("#protocol").attr("src", "");
        $("#vlanid").attr("src", "");

        // $("#host_mac_address").blur();
        // $("#src_ip").blur();
        // $("#dest_ip").blur();
        // $("#src_port").blur();
        // $("#dest_port").blur();
        // $("#vlanid").blur();
        // $("#protocol").blur();
         $("#node_name").blur();
        $("#connection_name").blur();
        // $("#bandwidth").blur();
         $("#flow_name").blur();
        // $("#host_ip_address").blur();
        // $("#host_location").blur();
        // $("#firewall_location").blur();
        // //$("#forwarding_location").blur();
        // $("#internet_location").blur();

        //node init
        $("#sel_show").get(0).selectedIndex = 0;
        $("#sel_show").change();
        $("#l2_select_table,#l3_select_table").empty();
        //connection init
        $("#p2p_node_group table,#p2mp_node_group table,#mesh_node_group table,#chain_node_group table").empty();
        $tableContent = $.parseHTML(' <tr> <td colspan="2"><span  class="second_title">One End Point:</span></td> </tr>'+
                                     '<tr> <td width="60px"></td><td></td> </tr>'+ 
                                     '<tr> <td colspan="2"><span class="second_title">Other End Point:</span></td> </tr>'+
                                     '<tr><td></td><td></td></tr>');

        $("#p2p_node_group table,#p2mp_node_group table").append($tableContent);
        $tableContent_ = $.parseHTML(' <tr><td><span  class="second_title">End Point:</span></td><td></td> </tr>' +
                           ' <tr><td></td><td></td></tr>');
        $("#mesh_node_group table,#chain_node_group table").append($tableContent_);
        //chain-group empty
        $("#chain_delgroup #chain_select_table").empty();
        //goback change type and name
        $("#entity_type").attr("disabled", false);
        $("#node_name").attr("disabled", false);
        $("#connection_name").attr("disabled", false);
         $("#con_type").attr("disabled", false);
        $("#flow_name").attr("disabled", false);
        $("#p2p_node-name1").attr("disabled", false);
        $("#p2p_node-name2").attr("disabled", false);
        $("#p2mp_node-name1").attr("disabled", false);
        $("#p2mp_node-name2 table input").attr("disabled", false);
        //flow init
        //get_nodes("src_node");
        //$("#src_node").change();
        //entity list show empty
        if (flag == false)
            $("#sel_2").prepend("<Option value='empty'></Option>").get(0).selectedIndex = 0;
        //edit false
        flag = false;
        ne_flag = 0;
        $(".NE_middle #sel_show").change();
       // $(".node_type_group_group").find("select").children().remove();
    });

    $("#l2_ok").click(function () {
        var $l2_add_node = $("#l2_addhost option:selected");
        if (!$l2_add_node.html()) return;
        $l2_select_node = $("<tr><td align='center'>" + $l2_add_node.html() + "</td><td><input type='button' class='btn_ del_sub_node' value='delete'/></td></tr>");
        $("#l2_select_table").append($l2_select_node);
        $l2_add_node.remove();
        //addBtnEvent("del_sub_node");
    });
    $("#l3_ok").click(function () {
        var $l3_add_node = $("#l3_addhost option:selected");
        if (!$l3_add_node.html()) return;
        $l3_select_node = $("<tr><td align='center'>" + $l3_add_node.html() + "</td><td><input type='button' class='btn_ del_sub_node' value='delete'/></td></tr>");
        $("#l3_select_table").append($l3_select_node);
        $l3_add_node.remove();
        //addBtnEvent("del_sub_node");
    });
    var onBtnClick = function () {
        var node_name = $(this).parent().prev().html();
        $(this).parents("div.delgroup").parent().parent().prev().children().children("select").prepend("<option value=" + node_name + ">" + node_name + "</option>");
        $(this).parent().parent().remove();
    }
    $(".select_table").on('click', " .del_sub_node",onBtnClick);
    
    $("#chain_ok").click(function () {
        var $chain_add_node = $("#chain_addhost option:selected");
        if (!$chain_add_node.html()) return;
        $chain_select_node = $("<tr><td align='center'>" + $chain_add_node.html() + "</td><td><input type='button' class='btn_ del_sub_node' value='delete'/></td></tr>");
        $("#chain_select_table").append($chain_select_node);
        $chain_add_node.remove();
        //addBtnEvent("del_sub_node");
    });
    // var chain_onBtnClick = function () {
    //     var node_name = $(this).parent().prev().html();
    //     $(this).parents("div.delgroup").parent().parent().prev().children().children("select").prepend("<option value=" + node_name + ">" + node_name + "</option>");
    //     $(this).parent().parent().remove();
    // }
    $("#p2p_select_ok").click(function () {
        var $p2p_add_node = $("#p2p_select option:selected");
        if (!$p2p_add_node.html()) return;
       
        if ($("#p2p_node_group").find("tr").eq(1).find("td").eq(1).children().length==0) {
            $("#p2p_node_group").find("tr").eq(1).find("td").eq(1).html("<span>" + $p2p_add_node.html() + "</span>" + "<input type='button' class='btn_ del_node' value='Delete'/>");
            $p2p_add_node.remove();
        }
    });
    $("#p2p_select_ok2").click(function () {
        var $p2p_add_node = $("#p2p_select option:selected");
        if (!$p2p_add_node.html()) return;
        if ($("#p2p_node_group").find("tr").eq(3).find("td").eq(1).children().length == 0) {
            $("#p2p_node_group").find("tr").eq(3).find("td").eq(1).html("<span>" + $p2p_add_node.html() + "</span>" + "<input type='button' class='btn_ del_node' value='Delete'/>");
            //$("#p2p_node_group").append("<tr><td></td><td></td></tr>");
            $p2p_add_node.remove();
        }
    });
    var onBtnClick_p2p = function () {
        var node_name = $(this).prev().html();
        if ($("#p2p_node_group tr").index($(this)) == 1)
        {
            $(this).prev().remove(); $(this).remove();
        }
        else {
            $(this).prev().remove(); $(this).remove();
        }
        $("#p2p_select").prepend("<option value=" + node_name + ">" + node_name + "</option>");
        $("#p2p_select").get(0).selectedIndex = 0;
        
    }
    $("#p2p_node_group").on('click',".del_node", onBtnClick_p2p);


    $("#p2mp_select_ok").click(function () {
        var $p2mp_add_node = $("#p2mp_select option:selected");
        if (!$p2mp_add_node.html()) return;

        if ($("#p2mp_node_group").find("tr").eq(1).find("td").eq(1).children().length == 0) {
            $("#p2mp_node_group").find("tr").eq(1).find("td").eq(1).html("<span>" + $p2mp_add_node.html() + "</span>" + "<input type='button' class='btn_ del_p2mp_node' value='Delete'/>");
            //console.log($("#p2mp_node_group").find("tr").eq(1).find("td").html());
             $p2mp_add_node.remove();
        }
    });
    $("#p2mp_select_ok2").click(function () {
        var $p2mp_add_node = $("#p2mp_select option:selected");
        if (!$p2mp_add_node.html()) return;
   
            $("#p2mp_node_group").find("tr:last").find("td").eq(1).html("<span>" + $p2mp_add_node.html() + "</span>" + "<input type='button' class='btn_ del_p2mp_node' value='Delete'/>");
            $tr = $("<tr><td></td><td></td></tr>");
            $("#p2mp_node_group table").append($tr);
            $p2mp_add_node.remove();
    });
    var onBtnClick_p2mp = function () {
        var node_name = $(this).prev().html();
        //console.log($("#p2mp_node_group tr").index($(this).parent().parent()));
        if ($("#p2mp_node_group tr").index($(this).parent().parent()) == 1) {
            $(this).prev().remove(); $(this).remove();
        }
        else {
            $(this).parent().parent().remove();
        }
        $("#p2mp_select").prepend("<option value=" + node_name + ">" + node_name + "</option>");
        $("#p2mp_select").get(0).selectedIndex = 0;

    }
    $("#p2mp_node_group").on('click', ".del_p2mp_node",onBtnClick_p2mp);

    $("#mesh_select_ok").click(function () {
        var $add_node = $("#mesh_select option:selected");
        if (!$add_node.html()) return;

        $("#mesh_node_group").find("tr:last").find("td").eq(1).html("<span>" + $add_node.html() + "</span>" + "<input type='button' class='btn_ del_mesh_node' value='Delete'/>");
        $tr = $("<tr><td></td><td></td></tr>");
        $("#mesh_node_group table").append($tr);
        $add_node.remove();
    });
    var onBtnClick_mesh = function () {
        var node_name = $(this).prev().html();
        //console.log($("#p2mp_node_group tr").index($(this).parent().parent()));
        if ($("#mesh_node_group tr").index($(this).parent().parent()) == 1) {
            $(this).prev().remove(); $(this).remove();
        }
        else {
            $(this).parent().parent().remove();
        }
        $("#mesh_select").prepend("<option value=" + node_name + ">" + node_name + "</option>");
        $("#mesh_select").get(0).selectedIndex = 0;

    }
    $("#mesh_node_group").on('click', ".del_mesh_node",onBtnClick_mesh);

    $("#chain_select_ok").click(function () {
        var $add_node = $("#chain_select option:selected");
        if (!$add_node.html()) return;

        $("#chain_node_group").find("tr:last").find("td").eq(1).html("<span>" + $add_node.html() + "</span>" + "<input type='button' class='btn_ del_chain_node' value='Delete'/>");
        $tr = $("<tr><td></td><td></td></tr>");
        $("#chain_node_group table").append($tr);
        $add_node.remove();
    });
    var onBtnClick_chain = function () {
        var node_name = $(this).prev().html();
        //console.log($("#p2mp_node_group tr").index($(this).parent().parent()));
        if ($("#chain_node_group tr").index($(this).parent().parent()) == 1) {
            $(this).parent().parent().remove();
        }
        else {
            $(this).parent().parent().remove();
        }
        $("#chain_select").prepend("<option value=" + node_name + ">" + node_name + "</option>");
        $("#chain_select").get(0).selectedIndex = 0;
    }
    //$(".del_chain_node").live('click', onBtnClick_chain);

    //host group add and delete button,is useless now!
    /*
    $(".add_node").click(function () {
        var index = $(".select_table").index($(this).parents("table"));
        var $host_ = $(".select_table").eq(index).find("tr").eq(1).find("td").children().eq(0).children("option:selected");
        $(".select_table").eq(index).find("select").eq(1).append($host_);
        var lc = lgroup_aviable_node.indexOf($host_.val());
        lgroup_aviable_node.splice(lc, 1);
    });
    $(".del_node").click(function () {
        var index = $(".select_table").index($(this).parents("table"));
        var $host_ = $(".select_table").eq(index).find("select").eq(1).children("option:selected");
        $(".select_table").eq(index).find("select").eq(0).append($host_);
        lgroup_aviable_node.push($host_.val());
    });
    $(".add_group").click(function () {
        var index = $(".select_table").index($(this).parents("table"));
        var $group_ = $(".select_table").eq(index).find("select").eq(2).children("option:selected");
        //console.log($group_.val());
        $(".select_table").eq(index).find("select").eq(3).append($group_);
        var lc = group_node.indexOf($group_.val());
        group_node.splice(lc, 1);
    });
    $(".del_group").click(function () {
        var index = $(".select_table").index($(this).parents("table"));
        var $group_ = $(".select_table").eq(index).find("select").eq(3).children("option:selected");
        console.log($group_.val());
        $(".select_table").eq(index).find("select").eq(2).append($group_);
        group_node.push($group_.val());
    }); */

    //close entity type
    $(".NE_close_show").click(function () {
        $(".NE_close_item").hide();
        $(".NE_close_item input[type=text]").val("");
        //able change node type
        $("#sel_show").attr("disabled", false);
        //able change connection type 
        $("#con_type").attr("disabled", false);
        //remove empty entity 

        $("#sel_2 option").each(function () {
            if ($(this).val() == "empty")
                $(this).remove();
        });
        //$("#sel_2").get(0).selectedIndex = 0;
    });
    //delete_event
    $("#NE_Delete").click(function () {
        if ($("#sel_2 option:selected").val() == 'empty') return;
        //trigger entity_type close event
        $(".NE_close_show").click();

        $("#entity_type").attr("disabled", false);
        var sevice_name = $(".NE_up #sel_1").children('option:selected').val();
        var entity_name = $(".NE_up #sel_2").children('option:selected').val();
        var position;
        if (sevice_name == "" || entity_name == "") return;
        //alert(entity_name);
        if (!entity_name || typeof (entity_name) == "undefined") return;
        //////////////////////////////////////////zm/////////////////////////////////////////////////////////////
        //delete node
      if (entity_name.indexOf('node:') >= 0) {
            var str = new Array();
            var flag_group = 0;
            str = entity_name.split(':');

			var node_name_delete = entity_name.split(':')[1];
            // flag_group = 1 found in the list  flag_group = 0 not found in the list
            try {

				for(var chain_cursor = 0;chain_cursor<$("#service_svg g").length;chain_cursor++)
				{
					if($("#service_svg g:eq("+chain_cursor+")").attr("type")!="chain-group")
						continue;
					if($("#service_svg g:eq("+chain_cursor+")").attr("sub").indexOf(node_name_delete)>-1)
					{
						alert("Please delete the chain-group:"+$("#service_svg g:eq("+chain_cursor+")").attr("id")+" firstly!");
						return;
					}						
				}					
				for(var conn_cursor = 0;conn_cursor<$("#service_svg path").length;conn_cursor++)
				{
					console.log($("#service_svg path:eq("+conn_cursor+")").attr("id"));
					if($("#service_svg path:eq("+conn_cursor+")").attr("type")=="connection")
					{
						if($("#service_svg path:eq("+conn_cursor+")").attr("node_start") == node_name_delete ||$("#service_svg path:eq("+conn_cursor+")").attr("node_end") == node_name_delete)
						{
							alert("Please delete the connection "+$("#service_svg path:eq("+conn_cursor+")").attr("id")+" firstly!");
							return;
						}
						
					}
				}
				$("#"+node_name_delete).remove();
				redraw_node_possition();
				redraw_connection_possition();
				redraw_flow_possition();
            }
            catch (err) {
                alert(err);
            }
        }
            //delete connection
        else if (entity_name.indexOf('connection:') >= 0) {
            //alert('delete connection');
            try 
			{
				var conn_name_delete =  entity_name.split(':')[1];
				var src_group = $("#"+conn_name_delete).attr("node_start");
				var dest_group = $("#"+conn_name_delete).attr("node_end");
				for(var i=0; i<$("#service_svg path").length;i++)
				{
					if($("#service_svg path:eq("+i+")").attr("type") == "flow")
					{
						var flow_start = $("#service_svg path:eq("+i+")").attr("node_start");
						var flow_end = $("#service_svg path:eq("+i+")").attr("node_end");
						var flow_via = $("#service_svg path:eq("+i+")").attr("via").split(",")[0];
						if(((src_group == flow_start)&&(dest_group == flow_end))||((src_group == flow_end)&&(dest_group == flow_start)))
						{
							alert("Please delete the flow "+$("#service_svg path:eq("+i+")").attr("id").split("_")[0]+" firstly!");
							return;
						}
						if(((src_group == flow_start)&&(dest_group == flow_via))||((src_group == flow_via)&&(dest_group == flow_start)))
						{
							alert("Please delete the flow "+$("#service_svg path:eq("+i+")").attr("id").split("_")[0]+" firstly!");
							return;
						}
						if(((src_group == flow_via)&&(dest_group == flow_end))||((src_group == flow_end)&&(dest_group == flow_via)))
						{
							alert("Please delete the flow "+$("#service_svg path:eq("+i+")").attr("id").split("_")[0]+" firstly!");
							return;
						}
					}	
				}	
				$("#"+conn_name_delete).remove();
            }

            catch (err) {
                alert(err);
            }
        }
		
		else if (entity_name.indexOf('flow:') >= 0) {
			var flow_name_delete =  entity_name.split(':')[1];
			for(var i=0;i<$("#service_svg path").length;i++)
			{
				if($("#service_svg path:eq("+i+")").attr("type") != "flow")
					continue;
				console.log(i);
				console.log("#service_svg path:eq("+i+")");
				console.log($("#service_svg path:eq("+i+")"));
				console.log($("#service_svg path:eq("+i+")").attr("id"));
				if($("#service_svg path:eq("+i+")").attr("id").indexOf(flow_name_delete)>-1)
				{
					if($("#service_svg path:eq("+i+")").attr("via").split(",")[0]!="none")
					{
						alert("please delete the operation firstly!");
						return;
					}
				}	
			}
			$("#"+flow_name_delete).remove();
			
		}
		var svg_str = $("#service_svg").html();
		localStorage.setItem(sevice_name+"_svg",svg_str);
        //////////////////////////////////////////zm/////////////////////////////////////////////////////////////

        var $currentEntity = $("#sel_2 option:selected").val();
        var entity = entity_name.split(":");
        var cur_ent_info_key = sevice_name + "__" + entity_name.replace(':', '_');

        //check the entity that the policy applied to
        $("#nemo_str_show p").each(function () {
            check_policy($(".NE_up #sel_2 option:selected").val());
        });

        //if delete the node , the sub node be free , the node must be removed from the group ,useless
        var node_name=$("#sel_2").val();
        var j = sevice_name + "__" + $("#sel_2").val().replace(':', '_');
        var json_str = localStorage.getItem(j);
        var json = jQuery.parseJSON(json_str);

        if (json[j]["Entity_Type"] == "node") {
            if (json[j]["node_type"] == "l2-group" || json[j]["node_type"] == "l3-group")
            {
                 var lc = lgroup_aviable_node.indexOf(node_name.replace("node","l2-group"));
                 if(lc>=0)
                 lgroup_aviable_node.splice(lc, 1); 

                 var lc2 = lgroup_aviable_node.indexOf(node_name.replace("node","l3-group"));
                 if(lc2>=0)
                 lgroup_aviable_node.splice(lc2, 1);   

                 console.log(lgroup_aviable_node);
                for (i in json[j]["sub-node"]["sub-node"]) {
                    lgroup_aviable_node.push(json[j]["sub-node"]["sub-node"][i]);
                    $("#nemo_str_show p").each(function () {
                      if($(this).html().indexOf("IMPORT") >=0 && $(this).html().indexOf(json[j]["sub-node"]["sub-node"][i].split(":")[1]) >0)
                      {
                        $(this).remove();
                      }
                     });
                } 

            }
            if (json[j]["node_type"] == "fw" || json[j]["node_type"] == "lb" || json[j]["node_type"] == "cache" )
            {
                   var lc = chain_aviable_node.indexOf(node_name.replace("node","fw"));
                   if(lc>=0)
                   chain_aviable_node.splice(lc, 1);    
                   var lc2 = chain_aviable_node.indexOf(node_name.replace("node","lb"));
                   if(lc2>=0)
                   chain_aviable_node.splice(lc2, 1);    
                  var lc3 = chain_aviable_node.indexOf(node_name.replace("node","cache"));
                   if(lc3>=0)
                   chain_aviable_node.splice(lc3, 1);    
            } 
            if(json[j]["node_type"] == "chain-group"){
            	for(var subIndex in json[j]["sub-node"]["sub-node"]){
            		chain_aviable_node.push(json[j]["sub-node"]["sub-node"][subIndex]);
            	}
            }
        var lgroup = {};
        lgroup.lgroup_aviable_node=lgroup_aviable_node;
        localStorage.setItem(sevice_name+"_lgroup_aviable_node",JSON.stringify(lgroup));
         console.log(localStorage.getItem(sevice_name+"_lgroup_aviable_node"));
        var chain_node = {};
        chain_node.chain_aviable_node=chain_aviable_node;
        localStorage.setItem(sevice_name+"_chain_aviable_node",JSON.stringify(chain_node));
                    
        }

        //console.log(lgroup_aviable_node);
        //if delete node the connection delete also. caution edit and delete!      
        if (entity[0] == "node") {
            $(".NE_up #sel_2 option").each(function () {
                var ent_name = $(this).val();
                //console.log(ent_name);
                if (ent_name == "" || typeof (ent_name) == "undefined") return;
                var type = $(this).val().split(":");
                //console.log("type:"+type);
                if (type[0] == "node")
                    return true;
                var j = sevice_name + "__" + $(this).val().replace(':', '_');
                         
                var json_str = localStorage.getItem(j);
                var json = jQuery.parseJSON(json_str);
                if (json[j]["Entity_Type"] != "connection") return true;
                var del = false;
                console.log("connection json:" + json);
                if (json[j]["connection_type"] == "p2p") {
                    if (json[j]["End-nodes"]["one_node_name"] == entity[1] || json[j]["End-nodes"]["other_node_name"] == entity[1]) {
                        del = true;
                    }
                }
                if (json[j]["connection_type"] == "p2mp") {
                    if (json[j]["End-nodes"]["one_node_name"] == entity[1]) {
                        del = true;
                    }
                    for (d in json[j]["End-nodes"]["other_node_name"]) {
                        if (json[j]["End-nodes"]["other_node_name"][d] == entity[1]) {
                            if (json[j]["End-nodes"]["other_node_name"].length == 1) {
                                del = true;
                            }
                            else {
                                json[j]["End-nodes"]["other_node_name"].splice(d, 1);
                                console.log(json[j]["End-nodes"]["other_node_name"]);
                                console.log(json[j]);
                                localStorage.removeItem(sevice_name + "__" + $(this).val().replace(':', '_'));

                                localStorage.setItem(sevice_name + "__" + $(this).val().replace(':', '_'), '{"' + sevice_name + '__' + $(this).val().replace(":", "_") + '":' + JSON.stringify(json[j]) + "}");
                                console.log("current_json:" + JSON.stringify(json[j]));
                            }
                        }
                    }

                }
                if (del == true) {
                    var cur_connection_id = json[j]["connection_id"];
  
                    var en = $(this).val();
                    $("#nemo_str_show p").each(function () {
                        console.log($(this).html());
                        vali_str = en.split(":");

                        if ($(this).html().indexOf("Connection") >= 0 && $(this).html().indexOf(vali_str[1]) >= 0) {
                            if ($(this).hasClass("grey_background")) {
                            	var nemoSet = $(this).html().split(" ");
                            	var deleSet = $currentEntity.split(":");
                                //$("#nemo_str_show").append("<p><span class='key'>DELETE </span>" +deleSet[0]+" "+deleSet[1]+ "</p>");
                            }
                            else {
                                $(this).remove();
                            }
                             return false;
                        }
                    });

                    $obj=$(this);

                    if ($(this).hasClass("grey_background")) {
                        str = localStorage.getItem(sevice_name + "__" + $(this).val().replace(':', '_'));;
                        localStorage.setItem(evice_name + "__delete_" + $(this).val().replace(':', '_'), str);
                        console.log();
                    }

                    //$obj.remove();

                    //localStorage.removeItem(sevice_name + "__" + $(this).val().replace(':', '_'));
                }
            });
        }

        var $select_entity = $(".NE_up #sel_2 option:selected");
        $(".NE_up #sel_2 option:selected").remove();
        $(".NE_up #sel_2").get(0).selectedIndex = 0;
        var $op_ob;
        $("#nemo_str_show p").each(function () {
            // console.log($(this).html());
            vali_str = entity_name.split(":");
            //console.log(entity_name);
            var entity_type_ = "";
            if (vali_str[0] == "node")
                entity_type_ = "Node";
            else if (vali_str[0] == "connection")
                entity_type_ = "Connection";
            else
                entity_type_ = "Flow";
            console.log(entity_type_);
            console.log(vali_str[1]);
            if ($(this).html().indexOf(entity_type_) > 0 && $(this).html().indexOf(vali_str[1]) >= 0) {
                {
                    $op_ob = $(this);
                    //$(this).remove();
                    return false;
                }
            }
        });

        if ($select_entity.hasClass("grey_background")) {
        	//var nemoSet = $op_ob.html().split(" ");
            //$("#nemo_str_show").append("<p><span class='key'>Delete </span>" + nemoSet[2] + "</p>");
            var deleSet=$select_entity.val().split(":");
            console.log(deleSet);
            if(deleSet[0]=="node")
            $("#nemo_str_show").append("<p><span class='key'>DELETE Node </span>" + deleSet[1]+ "</p>");
            if(deleSet[0]=="connection")
            $("#nemo_str_show").append("<p><span class='key'>DELETE Connection </span>" + deleSet[1]+ "</p>");
            if(deleSet[0]=="flow")
            $("#nemo_str_show").append("<p><span class='key'>DELETE Flow </span>" + deleSet[1]+ "</p>");
            str = localStorage.getItem(cur_ent_info_key);
            localStorage.setItem(sevice_name + "__delete_" + entity_name.replace(':', '_'), str);
            console.log(sevice_name + "__delete_" + entity_name.replace(':', '_')+":"+str);
        }
        else {
            $op_ob.remove();
        }
        localStorage.removeItem(cur_ent_info_key);
        //update nemo string
        setnemo_str();
        //update instance list
        setentity_instance_list();
        setentity_instance_list_();
    });
    //query nodes ,show in select
    $("#p2p_node-name1").change(function () {
        get_nodes("p2p_node-name2");
        $("#p2p_node-name2 option[value=" + $('#p2p_node-name1').children('option:selected').val() + "]").remove();

    });

    //edit_event
    var flag = false;//edit flag
    $("#NE_Edit").click(function () {
        //validate service and entity name 
        var sevice_name = "";
        var entity_name = "";
        sevice_name = $(".NE_up #sel_1").children('option:selected').val();
        entity_name = $(".NE_up #sel_2").children('option:selected').text();
        if (sevice_name == "" || entity_name == "") return;
        if (!entity_name || typeof (entity_name) == "undefined") return;

        //get json string
        var entity = sevice_name + "__" + entity_name.replace(':', '_');
        var json_str = localStorage.getItem(entity);
        if (!json_str || typeof (json_str) == "undefined") return;
        console.log(json_str);
        //set Entity type
        var val = $("#sel_2").children("option:selected").val();
        if (!val || typeof (val) == "undefined" ) return;
        //trigger close event
        $(".NE_close_show").click();//clear content
        var s = val.split(":");
        $("#entity_type option[value=" + s[0] + "]").attr("selected", true);
        flag = true;//add event use this flag
        //trigger add entity  event
        $("#add_entity").click();
        flag = true;//save event use this flag
        //can't change entity type and node name
        $("#entity_type").attr("disabled", true);
        $("#node_name").attr("disabled", true);
        $("#sel_show").attr("disabled", true);
        $("#connection_name").attr("disabled", true);
        $("#con_type").attr("disabled", true);
        $("#flow_name").attr("disabled", true);
        $("#p2p_node-name1").attr("disabled", true);
        $("#p2p_node-name2").attr("disabled", true);
        $("#p2mp_node-name1").attr("disabled", true);
        $("#p2mp_node-name2 table input").attr("disabled", true);
        //var json = eval('(' + json_str + ')');
        var json = jQuery.parseJSON(json_str);
        if (json[entity]["Entity_Type"] == "node") {
            $("#node_id").val(json[entity]["node_id"]);
            $("#node_name").val(json[entity]["node_name"]);
            if (json[entity]["node_type"] == "fw") {
                $("#sel_show").get(0).selectedIndex = 4;
                $("#fw_location").val(json[entity]["property"]["location"]);
                $("#fw_operating-mode").val(json[entity]["property"]["operating-mode"]);
            }
            else if (json[entity]["node_type"] == "lb") {
                $("#sel_show").get(0).selectedIndex = 5;
                $("#lb_location").val(json[entity]["property"]["location"]);
                $("#lb_operating-mode").val(json[entity]["property"]["operating-mode"]);
            }
            else if (json[entity]["node_type"] == "cache") {
                $("#sel_show").get(0).selectedIndex = 6;
                $("#cache_location").val(json[entity]["property"]["location"]);
                $("#cache_operating-mode").val(json[entity]["property"]["operating-mode"]);
            }
            else if (json[entity]["node_type"] == "l2-group") {
                $("#sel_show").get(0).selectedIndex = 0;
                /*
                for (var i in json[entity]["sub-node"]["sub-node_host"]) {
                    var host = json[entity]["sub-node"]["sub-node_host"][i];
                    //console.log(host);
                    $("#l2_delhost").append("<option value=" + host + ">" + host + "</option>");
                    var lc = lgroup_aviable_node.indexOf(host);
                    lgroup_aviable_node.splice(lc, 1);
                }
                for (var i in json[entity]["sub-node"]["sub-node_group"]) {
                    var group = json[entity]["sub-node"]["sub-node_group"][i];
                    //console.log(group);
                    $("#l2_delgroup").append("<option value=" + group + ">" + group + "</option>");
                    var lc = group_node.indexOf(group);
                    group_node.splice(lc, 1);
                }
                */
                for (var i in json[entity]["sub-node"]["sub-node"]) {
                    $l2_select_node = $("<tr><td align='center'>" + json[entity]["sub-node"]["sub-node"][i] + "</td><td><input type='button' class='btn_ del_sub_node' value='delete'/></td></tr>");
                    $("#l2_select_table").append($l2_select_node);
                }
                $("#l2-group_ip-prefix").val(json[entity]["property"]["ip-prefix"]);
                $("#l2-group_gateway-ip").val(json[entity]["property"]["gateway-ip"]);
                $("#l2-group_location").val(json[entity]["property"]["location"]);
            }
            else if (json[entity]["node_type"] == "l3-group") {
                $("#sel_show").get(0).selectedIndex = 1;
                /*
                for (var i in json[entity]["sub-node"]["sub-node_host"]) {
                    var host = json[entity]["sub-node"]["sub-node_host"][i];
                    //console.log(host);
                    $("#l3_delhost").append("<option value=" + host + ">" + host + "</option>");
                    var lc = lgroup_aviable_node.indexOf(host);
                    lgroup_aviable_node.splice(lc, 1);
                }
                for (var i in json[entity]["sub-node"]["sub-node_group"]) {
                    var group = json[entity]["sub-node"]["sub-node_group"][i];
                    //console.log(group);
                    $("#l3_delgroup").append("<option value=" + group + ">" + group + "</option>");
                    var lc = group_node.indexOf(group);
                    group_node.splice(lc, 1);
                }
                */
                for (var i in json[entity]["sub-node"]["sub-node"]) {
                    $l3_select_node = $("<tr><td align='center'>" + json[entity]["sub-node"]["sub-node"][i] + "</td><td><input type='button' class='btn_ del_sub_node' value='delete'/></td></tr>");
                    $("#l3_select_table").append($l3_select_node);
                }
                $("#l3-group_ip-prefix").val(json[entity]["property"]["ip-prefix"]);
                //$("#l3-group_capacity").val(json[entity]["property"]["capacity"]);
            }
            else if (json[entity]["node_type"] == "ext-group") {
                $("#sel_show").get(0).selectedIndex = 2;
                
                $("#ext-group_location").val(json[entity]["property"]["location"]);
                $("#ext-group_ac-info-network").val(json[entity]["property"]["ac-info-network"]);
                $("#ext-group_ac-info-protocol").val(json[entity]["property"]["ac-info-protocol"]);
                $("#ext-group_ip-prefix").val(json[entity]["property"]["ip-prefix"]);
            }
              else if (json[entity]["node_type"] == "chain-group") {
                $("#sel_show").get(0).selectedIndex = 3;
                $("#chain_select_table").empty();
                for (var i in json[entity]["sub-node"]["sub-node"]) {
                    $chain_select_node = $("<tr><td align='center'>" + json[entity]["sub-node"]["sub-node"][i] + "</td><td><input type='button' class='btn_ del_sub_node' value='Delete'/></td></tr>");
                    $("#chain_select_table").append($chain_select_node);
                }
                //$("#chain-group_ip-prefix").val(json[entity]["property"]["ip-prefix"]);
                //$("#l3-group_capacity").val(json[entity]["property"]["capacity"]);
            }

        }
        else if (json[entity]["Entity_Type"] == "connection") {
            $("#connection_id").val(json[entity]["connection_id"]);
            $("#connection_name").val(json[entity]["connection_name"]);
            if (json[entity]["connection_type"] == "p2p") {
                $("#con_type").get(0).selectedIndex = 0;
                //$("#p2p_node-name1 option[value=" + json[entity]["End-nodes"]["one_node_name"] + "]").attr("selected", true);
               // $("#p2p_node-name2 option[value=" + json[entity]["End-nodes"]["other_node_name"] + "]").attr("selected", true);
                $("#p2p_node_group table tr:eq(1) td:eq(1)").append("<span>"+json[entity]["End-nodes"]["one_node_name"]+"</span>");
                $("#p2p_node_group table tr:eq(3) td:eq(1)").append("<span>" + json[entity]["End-nodes"]["other_node_name"] + "</span>");
            }
            else if (json[entity]["connection_type"] == "p2mp") {
                $("#con_type").get(0).selectedIndex = 1;
                // $("#p2mp_node-name1 option[value=" + json[entity]["End-nodes"]["one_node_name"] + "]").attr("selected", true);

                //$("#p2mp_node-name2 table tr").children("input").attr("selected", false);
                $("#con_type").change();
                // for (var d in json[entity]["End-nodes"]["other_node_name"]) {
                //     $("#p2mp_node-name2 table").find("tr").each(function () {
                //         //alert($(this).html());
                //         if ($(this).children('td').eq(1).text() == json[entity]["End-nodes"]["other_node_name"][d]) {
                //             $(this).children('td').eq(0).children('input').attr("checked", true);
                //         }
                //     });
                // }
                $("#p2mp_node_group table tr:eq(1) td:eq(1)").append("<span>"+json[entity]["End-nodes"]["one_node_name"]+"</span>");
                $("#p2mp_node_group table tr:last").remove();
                for (var i in json[entity]["End-nodes"]["other_node_name"]) {
                    $tr=$("<tr><td></td><td><span>" + json[entity]["End-nodes"]["other_node_name"][i] + "</span></td></tr>");
                    $("#p2mp_node_group table").append($tr);
                }

            }
            else if (json[entity]["connection_type"] == "mesh") {
                $("#con_type").get(0).selectedIndex = 2;
                $("#con_type").change();
                $("#mesh_node_group table tr:last").remove();
                for (var i in json[entity]["node"]) {
                    $tr = $("<tr><td></td><td>" + json[entity]["node"][i] + "</td></tr>");
                    $("#mesh_node_group table").append($tr);
                }
            }
            else {
                $("#con_type").get(0).selectedIndex = 3;
                $("#con_type").change();
                $("#chain_node_group table tr:last").remove();
                for (var i in json[entity]["node"]) {
                    $tr = $("<tr><td></td><td>" + json[entity]["node"][i] + "</td></tr>");
                    $("#chain_node_group table").append($tr);
                }
            }
            $("#bandwidth").val(json[entity]["property"]["bandwidth"]);
            $("#latency").val(json[entity]["property"]["latency"]);
            $("#Jitter").val(json[entity]["property"]["Jitter"]);

        }
        else {
            $("#flow_id").val(json[entity]["flow_id"]);
            $("#flow_name").val(json[entity]["flow_name"]);
            /*src_ip = json[entity]["match_items"]["src_ip"];
            dest_ip = json[entity]["match_items"]["dest_ip"];
            src_port = json[entity]["match_items"]["src_port"];
            dest_port = json[entity]["match_items"]["dest_port"];
            protocol = json[entity]["match_items"]["protocol"];
            vlanid = json[entity]["match_items"]["vlanid"];
            bidirect = json[entity]["match_items"]["bidirect"];*/
            $("#eth_type").val(json[entity]["match_items"]["eth-type"]);
            $("#src_mac").val(json[entity]["match_items"]["src-mac"]);
            $("#dst_mac").val(json[entity]["match_items"]["dst-mac"]);
            $("#protocol").val(json[entity]["match_items"]["protocol"]);
            $("#src_ip").val(json[entity]["match_items"]["src-ip"]);
            $("#dst_ip").val(json[entity]["match_items"]["dst-ip"]);
            $("#src_port").val(json[entity]["match_items"]["src-port"]);
            $("#dst_port").val(json[entity]["match_items"]["dst-port"]);
        }

        //trigger change event
        $("#sel_show").change();
        //validate trigger
        $(".alert_").attr("src", "src/app/nemo/images/alert.png");
        $("#mac_icon").attr("src", "");
        $("#host_mac_address").blur();
        $("#node_name").blur();
        $("#flow_name").blur();
        $("#host_ip_address").blur();
        $("#host_location").blur();
        $("#firewall_location").blur();
        //$("#forwarding_location").blur();
        $("#internet_location").blur();

        $("#loadBlance_location").blur();
        $("#group_location").blur();

        //connection property
        $("#connection_name").blur();
        //bandwidth
        //$("#bandwidth").blur();

        //flow property
        // $("#src_ip").blur();
        // $("#dest_ip").blur();
        // $("#src_port").blur();
        // $("#dest_port").blur();
        // $("#vlanid").blur();
        // $("#protocol").blur();
        // //disable node type
        // $("#sel_show").attr("disabled", true);
        // //disable connection type 
        // $("#con_type").attr("disabled", true);
        ne_flag = 1;
    });

    var node_match_flag = false;
    var connection_match_flag = false;
    var flow_match_flag = false;
    var ip_match_flag = false;
    var host_location_match_flag = false;
    var firewall_location_match_flag = false;
    // var forwarding_location_match_flag = false;
    var internet_location_match_flag = false;
    var mac_match_flag = true;

    var match_null = /^[\s ]*$/;
    var match_underflow_ip_pattern = /^\s*(?:(?:[01]?\d{1,2}|2[0-4]\d|25[0-5])\.){3}(?:[01]?\d{1,2}|2[0-4]\d|25[0-5])\s*$/;

    var match_underflow_port_pattern = /^([1-5]\d{0,4}|6[0-5]{2}[0-3][0-5]|[6-9]\d{0,3})$/;

    var bandwidth_flag = true;
    var bandwidth_null_flag = false;

    var flow_src_ip_flag = true;
    var flow_dest_ip_flag = true;
    var flow_src_port_flag = true;
    var flow_dest_port_flag = true;
    var flow_protocol_flag = true;
    var flow_vlanid_flag = true;
    var flow_src_ip_null_flag = false;
    var flow_dest_ip_null_flag = false;
    var flow_src_port_null_flag = false;
    var flow_dest_port_null_flag = false;
    var flow_protocol_null_flag = false;
    var flow_vlanid_null_flag = false;
    //node_name
    $("#node_name").blur(function () {
        var node_name_val = $("#node_name").val();
        //var node_match_flag = false;
        var match_null = /^[\s ]*$/;
        if (match_null.test(node_name_val)) {
            node_match_flag = false;
            $("#node_icon")[0].src = "src/app/nemo/images/alert.png";
        }
        else {
            node_match_flag = true;
            $("#node_icon")[0].src = "src/app/nemo/images/ok.png";
        }
    });

    //connection_name
    $("#connection_name").blur(function () {
        var connection_name_val = $("#connection_name").val();
        //var node_match_flag = false;
        var match_null = /^[\s ]*$/;
        if (match_null.test(connection_name_val)) {
            connection_match_flag = false;
            $("#connection_icon")[0].src = "src/app/nemo/images/alert.png";
        }
        else {
            connection_match_flag = true;
            $("#connection_icon")[0].src = "src/app/nemo/images/ok.png";
        }
    });

    //flow_name
    $("#flow_name").blur(function () {
        var flow_name_val = $("#flow_name").val();
        //var node_match_flag = false;
        var match_null = /^[\s ]*$/;
        if (match_null.test(flow_name_val)) {
            flow_match_flag = false;
            $("#flow_icon")[0].src = "src/app/nemo/images/alert.png";
        }
        else {
            flow_match_flag = true;
            $("#flow_icon")[0].src = "src/app/nemo/images/ok.png";
        }
    });

    //host_ip_address
    $("#host_ip_address").blur(function () {
        //var ip_match_flag = false;
        var host_ip = $("#host_ip_address").val();
        //var match_ip_pattern = /^\s*(?:(?:[01]?\d{1,2}|2[0-4]\d|25[0-5])\.){3}(?:[01]?\d{1,2}|2[0-4]\d|25[0-5])\s*$/;
        var match_ip_pattern = /^\s*(?:(?:[01]?\d{1,2}|2[0-4]\d|25[0-5])\.){3}(?:[01]?\d{1,2}|2[0-4]\d|25[0-5])(?:\/[1-9]|\/1[0-9]|\/2[0-4]){0,1}\s*$/;
        var match_null = /^[\s]*$/;
        if (match_ip_pattern.test(host_ip)) {
            ip_match_flag = true;
            $("#ip_icon")[0].src = "src/app/nemo/images/ok.png";
        }
        else if (match_null.test(host_ip)) {
            ip_match_flag = false;
            $("#ip_icon")[0].src = "src/app/nemo/images/alert.png";
            //$("#host_ip_address").val("please input ip addresa");
        }
        else {
            ip_match_flag = false;
            $("#ip_icon")[0].src = "src/app/nemo/images/error.png";

        }
    });

    //host_location
    $("#host_location").blur(function () {
        var host_loc = $("#host_location").val();
        //var location_match_flag = false;
        var match_null = /^[\s]*$/;
        if (match_null.test(host_loc)) {
            host_location_match_flag = false;
            $("#host_loc_icon")[0].src = "src/app/nemo/images/alert.png";
            //$("#host_ip_address").val("please input ip addresa");
        }
        else {
            host_location_match_flag = true;
            $("#host_loc_icon")[0].src = "src/app/nemo/images/ok.png";
        }
    });

    //host_mac_address
    $("#host_mac_address").blur(function () {
        //var mac_match_flag = false;
        var host_mac = $("#host_mac_address").val();
        var match_mac_pattern = /^\s*(?:(?:\d{2}):){5}(?:\d{2})\s*$/;
        var match_null = /^[\s]*$/;
        if (match_mac_pattern.test(host_mac)) {
            mac_match_flag = true;
            $("#mac_icon")[0].src = "src/app/nemo/images/ok.png";
        }
        else if (match_null.test(host_mac)) {
            mac_match_flag = true;
            $("#mac_icon")[0].src = "";
            //$("#host_ip_address").val("please input ip addresa");
        }
        else {
            mac_match_flag = false;
            $("#mac_icon")[0].src = "src/app/nemo/images/error.png";
        }
    });

    //firewall_location
    $("#firewall_location").blur(function () {
        var firewall_loc = $("#firewall_location").val();
        //var location_match_flag = false;
        var match_null = /^[\s]*$/;
        if (match_null.test(firewall_loc)) {
            firewall_location_match_flag = false;
            $("#firewall_loc_icon")[0].src = "src/app/nemo/images/alert.png";
            //$("#host_ip_address").val("please input ip addresa");
        }
        else {
            firewall_location_match_flag = true;
            $("#firewall_loc_icon")[0].src = "src/app/nemo/images/ok.png";
        }
    });

    //forwarding_location
    //$("#forwarding_location").blur(function () {
    //    var forwarding_loc = $("#forwarding_location").val().trim();
    //    //var location_match_flag = false;
    //    var match_null = /^[\s]*$/;
    //    if (match_null.test(forwarding_loc)) {
    //        forwarding_location_match_flag = false;
    //        $("#forwarding_loc_icon")[0].src = "src/app/nemo/images/alert.png";
    //        //$("#host_ip_address").val("please input ip addresa");
    //    }
    //    else {
    //        forwarding_location_match_flag = true;
    //        $("#forwarding_loc_icon")[0].src = "src/app/nemo/images/ok.png";
    //    }
    //});

    //internet_location
    $("#internet_location").blur(function () {
        var internet_loc = $("#internet_location").val();
        var match_null = /^[\s]*$/;
        if (match_null.test(internet_loc)) {
            internet_location_match_flag = false;
            $("#internet_loc_icon")[0].src = "src/app/nemo/images/alert.png";
        }
        else {
            internet_location_match_flag = true;
            $("#internet_loc_icon")[0].src = "src/app/nemo/images/ok.png";
        }
    });

    //bandwidth
    $("#bandwidth").blur(function () {
        var bandwidth = $("#bandwidth").val();
        var match_bandwidth = /^\+?[1-9][0-9]*$/;
        if (match_bandwidth.test(bandwidth)) {
            bandwidth_flag = true;
            bandwidth_null_flag = true;
            //$("#bandwidth_icon")[0].src = "src/app/nemo/images/ok.png";
        }
        else if (match_null.test(bandwidth)) {
            bandwidth_flag = true;
            bandwidth_null_flag = false;
           // $("#bandwidth_icon")[0].src = "src/app/nemo/images/null.gif";
        }
        else {
            bandwidth_flag = false;
            bandwidth_null_flag = true;
            //$("#bandwidth_icon")[0].src = "src/app/nemo/images/error.png";
        }
    });
    //validate data under flow
    //flow_src_ip
    $("#src_ip").blur(function () {
        var src_ip = $("#src_ip").val();
        //if (match_underflow_ip_pattern.test(src_ip)) {
           // flow_src_ip_flag = true;
           // flow_src_ip_null_flag = true;
            //$("#src_ip_icon")[0].src = "src/app/nemo/images/ok.png";
        //}
        //else if (match_null.test(src_ip)) {
          //  flow_src_ip_flag = true;
            //flow_src_ip_null_flag = false;
            //$("#src_ip_icon")[0].src = "src/app/nemo/images/null.gif";
       // }
        //else {
            //flow_src_ip_flag = false;
           // flow_src_ip_null_flag = true;
           // $("#src_ip_icon")[0].src = "src/app/nemo/images/error.png";
        //}
    });

    //flow_dest_ip
    $("#dest_ip").blur(function () {
        var dest_ip = $("#dest_ip").val();
        if (match_underflow_ip_pattern.test(dest_ip)) {
            flow_dest_ip_flag = true;
            flow_dest_ip_null_flag = true;
            //$("#dest_ip_icon")[0].src = "src/app/nemo/images/ok.png";
        }
        else if (match_null.test(dest_ip)) {
            flow_dest_ip_flag = true;
            flow_dest_ip_null_flag = false;
            //$("#dest_ip_icon")[0].src = "src/app/nemo/images/null.gif";
        }
        else {
            flow_dest_ip_flag = false;
            flow_dest_ip_null_flag = true;
            //$("#dest_ip_icon")[0].src = "src/app/nemo/images/error.png";
        }
    });

    //flow_src_port_flag
    $("#src_port").blur(function () {
        var src_port = $("#src_port").val();
        if (match_underflow_port_pattern.test(src_port)) {
            flow_src_port_flag = true;
            flow_src_port_null_flag = true;
            //$("#src_port_icon")[0].src = "src/app/nemo/images/ok.png";
        }
        else if (match_null.test(src_port)) {
            flow_src_port_flag = true;
            flow_src_port_null_flag = false;
            //$("#src_port_icon")[0].src = "src/app/nemo/images/null.gif";
        }
        else {
            flow_src_port_flag = false;
            flow_src_port_null_flag = true;
            //$("#src_port_icon")[0].src = "src/app/nemo/images/error.png";
        }
    });

    //flow_dest_port_flag
    $("#dest_port").blur(function () {
        var dest_port = $("#dest_port").val();
        if (match_underflow_port_pattern.test(dest_port)) {
            flow_dest_port_flag = true;
            flow_dest_port_null_flag = true;
            //$("#dest_port_icon")[0].src = "src/app/nemo/images/ok.png";
        }
        else if (match_null.test(dest_port)) {
            flow_dest_port_flag = true;
            flow_dest_port_null_flag = false;
            //$("#dest_port_icon")[0].src = "src/app/nemo/images/null.gif";
        }
        else {
            flow_dest_port_flag = false;
            flow_dest_port_null_flag = true;
            //$("#dest_port_icon")[0].src = "src/app/nemo/images/error.png";
        }
    });

    //protocol
    $("#protocol").blur(function () {
        var protocol_val = $("#protocol").val();
        var match_protocol_pattern = /^[\s\S]*$/;
        if (match_protocol_pattern.test(protocol_val)) {
            flow_protocol_flag = true;
            flow_protocol_null_flag = true;
            //$("#protocol_icon")[0].src = "src/app/nemo/images/ok.png";
        }
        else if (match_null.test(protocol_val)) {
            flow_protocol_flag = true;
            flow_protocol_null_flag = false;
            //$("#protocol_icon")[0].src = "src/app/nemo/images/null.gif";
        }
        else {
            flow_protocol_flag = false;
            flow_protocol_null_flag = true;
            //$("#protocol_icon")[0].src = "src/app/nemo/images/error.png";
        }
    });

    //vlanid
    $("#vlanid").blur(function () {
        var vlanid_val = $("#vlanid").val();
        var match_vlanid_pattern = /^[1-9]\d*$/;
        if (match_vlanid_pattern.test(vlanid_val)) {
            flow_vlanid_flag = true;
            flow_vlanid_null_flag = true;
            //$("#vlanid_icon")[0].src = "src/app/nemo/images/ok.png";
        }
        else if (match_null.test(vlanid_val)) {
            flow_vlanid_flag = true;
            flow_vlanid_null_flag = false;
            //$("#vlanid_icon")[0].src = "src/app/nemo/images/null.gif";
        }
        else {
            flow_vlanid_flag = false;
            flow_vlanid_null_flag = true;
            //$("#vlanid_icon")[0].src = "src/app/nemo/images/error.png";
        }
    });
    ///////////////////////////////////////////zm/////////////////////////////////////////////////////////////




    function Edge_Judgment(z, m) {
        //alert('begin to judge');
        for (var i = 1; i <= Edge_Id; i++) {
            var edg = edges.get(i);
            if (edg == null)
                continue;
            if (edg.from == z && edg.to == m)
                return 1;
            else if (edg.from == m && edg.to == z)
                return 1;
            else
                continue;
        }
        //alert('not exist!');
        return 0;
    }

    ///////////////////////////////////////////zm/////////////////////////////////////////////////////////////

    //save event
    $(".net_entity_save").click(function () {
         sevice_name_glb = $(".NE_up #sel_1").children('option:selected').val();
         entity_name_glb = $(".NE_up #sel_2").children('option:selected').text();
        var entity_col = sevice_name_glb + "__" + entity_name_glb.replace(':', '_');
        var json_str_col = localStorage.getItem(entity_col);
        console.log(json_str_col);
        
        var nemo_str = "";
        //validate data type
        var data_flag = false;
        var selected_entity_type = $("#entity_type").children('option:selected').val();
        var selected_node_type = $("#sel_show").children('option:selected').val();

        /********************************* CN/EN *************************************************/

        if (selected_entity_type == "node") {
            if (!node_match_flag) {
                alert("请输入一个正确的结点名称");
                return;
            }
            else if (selected_node_type == "host") {
                if (!ip_match_flag) {
                    alert("请输入一个正确的IP地址,形如：1.1.1.1");
                    return;
                } else if (!host_location_match_flag) {
                    alert("请输入一个正确的资源名称");
                    return;
                } else if (!mac_match_flag) {
                    alert("请输入一个正确的MAC地址");
                    return;
                }
            } else if (selected_node_type == "firewall") {
                if (!firewall_location_match_flag) {
                    alert("请输入一个正确的资源名称");
                    return;
                }
            } else if (selected_node_type == "forwarding") {
                //alert(forwarding_location_match_flag);

            } else if (selected_node_type == "internet") {
                if (!internet_location_match_flag) {
                    alert("请输入一个正确的资源名称");
                    return;
                    //alert("please input a correct internet location");
                }
            }

        } else if (selected_entity_type == "connection") {
            if (!connection_match_flag) {
                alert("请输入一个正确的连接名称");
                return;
            }
            else if (!bandwidth_flag) {
                alert("请输入一个带宽，为大于0的整数");
                return;
            }
        } else {
            if (!flow_match_flag) {
                alert("请输入一个正确的业务流名称");
                return;
            }
            else if (!flow_src_ip_flag) {
                alert("请输入一个正确的源IP地址，形如：1.1.1.1");
                return;
            }
            else if (!flow_dest_ip_flag) {
                alert("请输入一个正确的目的IP地址，形如：1.1.1.1");
                return;
            }
            else if (!flow_src_port_flag) {
                alert("请输入一个正确的源端口号：1-65535");
                return;
            }
            else if (!flow_dest_port_flag) {
                alert("请输入一个正确的目的端口号：1-65535");
                return;
            }
            else if (!flow_protocol_flag) {
                alert("请输入一个正确的协议类型");
                return;
            }
            else if (!flow_vlanid_flag) {
                alert("请输入一个正确的VLAN ID");
                return;
            }
        }
		if (selected_entity_type == "flow") 
		{
			var src_group=flow_get_group()[0];
			var dest_group=flow_get_group()[1];			
			if(src_group == '' || dest_group == '')
			{
				alert("Wrong ip address");
				return;
			}
			
				//no conn no flow (validate)
			var exist_flag = 0;
			for(var i=0; i<$("#service_svg path").length;i++)
			{
				if($("#service_svg path:eq("+i+")").attr("type") == "connection")
				{
					var node_name_old_1 = $("#service_svg path:eq("+i+")").attr("node_start");
					var node_name_old_2 = $("#service_svg path:eq("+i+")").attr("node_end");
					console.log("old:"+node_name_old_1+"  "+node_name_old_2);
					if(((src_group == node_name_old_1)&&(dest_group == node_name_old_2))||((src_group == node_name_old_2)&&(dest_group == node_name_old_1)))
					{
						exist_flag = 1;
					}		
				}	
			}
			if(exist_flag == 0)
			{
				alert("No connection!");
				return;
			}		
			
		}
		if(flag==false)
		{
			if(selected_entity_type == "node") 
			{
			if($("#sel_show").children('option:selected').val()=="chain-group"){
				if($("#chain_select_table tr").length == 0){
					alert("Cannot create chain-group with zero subnode");
					return;
				}
			}
			
			}
			if (selected_entity_type == "connection") 
			{
				for(var i=0; i<$("#service_svg path").length;i++)
				{
					if($("#service_svg path").attr("type") == "connection")
					{
						var exist_flag = 0;
						var node_name_1 = get_connection_node()[0];
						var node_name_2 = get_connection_node()[1];
						var node_name_old_1 = $("#service_svg path:eq("+i+")").attr("node_start");
						var node_name_old_2 = $("#service_svg path:eq("+i+")").attr("node_end");
						if(((node_name_1 == node_name_old_1)&&(node_name_2 == node_name_old_2))||((node_name_1 == node_name_old_2)&&(node_name_2 == node_name_old_1)))
						{
							alert("A connection has existed!");
							exist_flag = 1;
							return;
						}
							
					}
						
				}
			
			}
			
			
		}
		else if(flag==true)
		{
			var Net_Operation = $("#entity_type").children('option:selected').val();
			var Node_Type = $("#sel_show").children('option:selected').val();
			if(Net_Operation == "node")
			{
				var node_name_del_validate = $("#node_name").val().trim();
				if(Node_Type == "l2-group" || Node_Type == "l3-group")
				{
					for(var flow_cursor = 0;flow_cursor<$("#service_svg path").length;flow_cursor++)
					{
						if($("#service_svg path:eq("+flow_cursor+")").attr("type")!="flow")
							continue;
						if($("#service_svg path:eq("+flow_cursor+")").attr("node_start") == node_name_del_validate ||$("#service_svg path:eq("+flow_cursor+")").attr("node_end") == node_name_del_validate)
						{
							alert("There has been a flow related to this group,please delete the flow "+$("#service_svg path:eq("+flow_cursor+")").attr("id")+" firstly!");
							return;
						}
					}
				}
				else if(Node_Type == "chain-group" )
				{
					if($("#chain_select_table tr").length == 0)
					{
						alert("The chain-group will be deleted due to the delete of the last vas point of this chain!");	
						for(var connection_cursor = 0;connection_cursor<$("#service_svg path").length;connection_cursor++)
						{
							if($("#service_svg path:eq("+connection_cursor+")").attr("type")!="connection")
								continue;
							if($("#service_svg path:eq("+connection_cursor+")").attr("node_start") == node_name_del_validate ||$("#service_svg path:eq("+connection_cursor+")").attr("node_end") == node_name_del_validate)
							{
								alert("There has been a flow or a connection related to this group,please delete it firstly!");
								return;
							}
						}
					}
					
				}
				
			}
			else if(Net_Operation == "connection")
			{
				
			}
			else if(Net_Operation == "flow")
			{
				var flow_name_validate =  $("#flow_name").val().trim();
				for(var i=0;i<$("#service_svg path").length;i++)
				{
					if($("#service_svg path:eq("+i+")").attr("type") != "flow")
						continue;
					if($("#service_svg path:eq("+i+")").attr("id").indexOf(flow_name_validate)>-1)
					{
						if($("#service_svg path:eq("+i+")").attr("via").split(",")[0]!="none")
						{
							alert("The flow can't be edited,please delete the operation firstly!");
							return;
						}
					}	
				}
			}

		}
        sevice_name = $("#sel_1").children('option:selected').val();
        if (sevice_name == "") return;
        var entity_instance_list = localStorage.getItem(sevice_name + "_entity_instance_list");
        console.log(entity_instance_list);

        //create node


        //create entity list
        instance_name = "";
        if ($("#entity_type").children('option:selected').val() == "node") {

            instance_name = "node:" + $("#node_name").val().trim();
        }
        else if ($("#entity_type").children('option:selected').val() == "connection") {
            instance_name = "connection:" + $("#connection_name").val().trim();
        }
        else {
            instance_name = "flow:" + $("#flow_name").val().trim();
        }
        //validate name
        if (entity_instance_list && typeof (entity_instance_list) != "undefined" && entity_instance_list != 0) {
            entity_list = entity_instance_list.split(",");
            var validate = 0;
            if (flag == false) {
                for (var v = 0; v < entity_list.length; v++) {
                    if (entity_list[v] == instance_name) {
                        $("#ety_dialog").dialog({
                            height: 240,
                            width: 400,
                            modal: true,
                            open: function (event, ui) { $(".ui-dialog-titlebar-close").hide(); },
                            buttons: {
                                "Ok": function () { $(this).dialog("close"); }
                            }
                        });
                        validate = 1;
                        return false;
                    }
                }
            }
        }

        /********************************* CN/EN *************************************************/

        var $edit_option=$(".NE_up #sel_2 option:selected");
        //edit hande
        //console.log(flag);
        if (flag == true) {
            //trigger delete event
            //$('#NE_Delete').click();

            //trigger entity_type close event  
            var sevice_name = $(".NE_up #sel_1").children('option:selected').val();
            var entity_name = $(".NE_up #sel_2").children('option:selected').val();
            var position;
             //console.log(sevice_name+" "+entity_name);
            if (sevice_name == "" || entity_name == "") return;

            //alert(entity_name);
            if (!entity_name || typeof (entity_name) == "undefined") return;
            localStorage.removeItem(sevice_name + "__" + entity_name.replace(':', '_'));
            $(".NE_up #sel_2 option:selected").remove();
            //disable node type
            $("#sel_show").attr("disabled", true);
            //disable connection type 
            $("#con_type").attr("disabled", true);

        }

        if (validate == 1)
            return;
        if ($edit_option.hasClass("grey_background"))
            $(".NE_up #sel_2").prepend("<Option class='grey_background' value='" + instance_name + "'>" + instance_name + "</Option>");
        else
            $(".NE_up #sel_2").prepend("<Option value='" + instance_name + "'>" + instance_name + "</Option>");

        $(".NE_up #sel_2").get(0).selectedIndex = 0;

        //create json string
        json_str = '{';
        service_name = $(".NE_up #sel_1").children('option:selected').val();
        if (service_name == null) return;
        json_str += '"' + service_name + "__" + instance_name.replace(':', '_') + '":{';
        if ($("#entity_type").children('option:selected').val() == "node") {
           var node_type = $("#sel_show").children('option:selected').val();
            json_str += '"Entity_Type":"node",';
            if (node_type == 'l2-group'){
            	console.log("1");
            	if($("#node_name").val().trim().toLowerCase()=="dmz"){
            		 json_str += '"node_id":"b46cfa7f-93a3-43f4-ac20-09307c75feca",';           		
            		}
            	else if($("#node_name").val().trim().toLowerCase()=="interior"){
            		 json_str += '"node_id":"175425f7-c9c9-474a-962c-70cb6c180d4d",';         
            		}
            	else
            		json_str += '"node_id":"' + $("#node_id").val().trim() + '",';
           }
           else{
            json_str += '"node_id":"' + $("#node_id").val().trim() + '",';
        }

            json_str += '"node_name":"' + $("#node_name").val().trim() + '",';
           
            if (node_type == 'fw') {
                chain_aviable_node.push("fw:" + $("#node_name").val().trim());
                
                nemo_str += "<span class='key'>IMPORT Node </span>";
                nemo_str += $("#node_name").val().trim() + " ";
                nemo_str += "<span class='key'>Type</span> fw "
                nemo_str += "<span class='key'>Property </span>";
                if ($('#fw_location').val().trim() != "")
                    nemo_str += "location:" + '"'+$('#fw_location').val().trim() + '",';
                if ($('#fw_operating-mode').val().trim() != "")
                    nemo_str += "" + "operating-mode:" +'"'+ $('#fw_operating-mode').val().trim() + '",';
                nemo_str = nemo_str.substring(0,nemo_str.length-1);

                json_str += '"node_type":"fw",';
                json_str += '"property":{';
                json_str += '"location":"' + $('#fw_location').val().trim() + '",';
                json_str += '"operating-mode":"' + $('#fw_operating-mode').val().trim() + '"}';
                json_str += '}';
            }
            else if (node_type == 'lb') {

                chain_aviable_node.push("lb:" + $("#node_name").val().trim());

                nemo_str += "<span class='key'>IMPORT Node </span>";
                nemo_str += $("#node_name").val().trim() + " ";
                nemo_str += "<span class='key'>Type </span>lb "
                nemo_str += "<span class='key'>Property </span>";
                if ($('#lb_location').val().trim() != "")
                    nemo_str += "location:" + '"'+$('#lb_location').val().trim() + '",';
                if ($('#lb_operating-mode').val().trim() != "")
                    nemo_str += "" + "operating-mode:" +'"'+ $('#lb_operating-mode').val().trim() + '",';
                nemo_str = nemo_str.substring(0,nemo_str.length-1);

                json_str += '"node_type":"lb",';
                json_str += '"property":{';
                json_str += '"location":"' + $('#lb_location').val().trim() + '",';
                json_str += '"operating-mode":"' + $('#lb_operating-mode').val().trim() + '"}';
                json_str += '}';
            }
            else if (node_type == 'l2-group') {

                lgroup_aviable_node.push("l2-group:" + $("#node_name").val().trim());
                //conn_aviable_node.push("l2-group:" + $("#node_name").val().trim());

                nemo_str += "<span class='key'>CREATE Node </span>";
                nemo_str += $("#node_name").val().trim() + " ";
                nemo_str += "<span class='key'>Type </span>l2-group ";
                var use_node = new Array();
                //var use_group = new Array();              
               // console.log(lgroup_aviable_node)
                 lgroup_aviable_node = [];
                    $("#l2_addhost option").each(function(){
                    	lgroup_aviable_node.push($(this).val());
                    });

                $("#l2_select_table").find("tr").each(function () {
                    var node_name = $(this).find("td").eq(0).html();
                    use_node.push($(this).find("td").eq(0).html());
                    for (i in lgroup_aviable_node)
                    {
                        if (lgroup_aviable_node[i] == node_name){}
                            //lgroup_aviable_node.splice(i,1);
                    }
                });
                //console.log(lgroup_aviable_node);
                
                if (use_node.length > 0) {
                    nemo_str += "<span class='key'>Contain </span>";
                    for (var i = 0; i < use_node.length; i++) {                      
                        //nemo import host 
                    if(use_node[i].indexOf("host")>=0)
                    {
                    	 if (flag == false)
                        $("#nemo_str_show").append("<p><span class='key'>IMPORT Node </span>" + use_node[i].split(":")[1] + " <span class='key'>Type</span> host</p>");
                        nemo_str += use_node[i].split(":")[1] + ",";
                    }
                    else
                    {
                         nemo_str += use_node[i].split(":")[1] + ",";
                    }

                    }
                    nemo_str = nemo_str.substring(0, nemo_str.length - 1);
                    nemo_str += "";
                }
                
               
                nemo_str += "<span class='key'> Property </span>";
                if ($('#l2-group_ip-prefix').val().trim() != "")
                    nemo_str += "ip-prefix:" + '"'+$('#l2-group_ip-prefix').val().trim() + '",';
                if ($('#l2-group_gateway-ip').val().trim() != "")
                    nemo_str += "" + "gateway-ip:" +'"'+$('#l2-group_gateway-ip').val().trim() + '",';
                if ($('#l2-group_location').val().trim() != "")
                    nemo_str += "" + "location:" +'"'+$('#l2-group_location').val().trim() + '",';
               nemo_str = nemo_str.substring(0,nemo_str.length-1);

                json_str += '"node_type":"l2-group",';
                json_str += '"sub-node":{';
                json_str += '"sub-node":[';
                if (use_node.length > 0) {
                    for (var i = 0; i < use_node.length; i++) {
                        json_str += '"' + use_node[i] + '"' + ",";
                    }
                    json_str = json_str.substring(0, json_str.length - 1);
                }
                json_str += ']';
               
                json_str += '},';
                json_str += '"property":{';
                json_str += '"ip-prefix":"' + $('#l2-group_ip-prefix').val().trim() + '",';
                json_str += '"gateway-ip":"' + $('#l2-group_gateway-ip').val().trim() + '",';
                json_str += '"location":"' + $('#l2-group_location').val().trim() + '"}';
                json_str += '}';
            }
            else if (node_type == 'l3-group') {

                lgroup_aviable_node.push("l3-group:" + $("#node_name").val().trim());
                //conn_aviable_node.push("l3-group:" + $("#node_name").val().trim());

                nemo_str += "<span class='key'>CREATE Node </span>";
                nemo_str += $("#node_name").val().trim() + " ";
                nemo_str += "<span class='key'>Type </span>l3-group "
                var use_node = new Array();
                var use_group = new Array();
                lgroup_aviable_node = [];
                    $("#l3_addhost option").each(function(){
                    	lgroup_aviable_node.push($(this).val());
                    });

                $("#l3_select_table").find("tr").each(function () {
                    var node_name = $(this).find("td").eq(0).html();
                    use_node.push($(this).find("td").eq(0).html());
                    for (i in lgroup_aviable_node) {
                        if (lgroup_aviable_node[i] == node_name){}
                            //lgroup_aviable_node.splice(i, 1);
                    }
                });

                if (use_node.length > 0) {
                    nemo_str += "<span class='key'>Contain </span>";
                    for (var i = 0; i < use_node.length; i++) {
                        if(use_node[i].indexOf("host")>=0)
                    {
                    	 if (flag == false)
                        $("#nemo_str_show").append("<p><span class='key'>IMPORT Node </span>" + use_node[i].split(":")[1] + " <span class='key'>Type</span> host</p>");
                        nemo_str += use_node[i].split(":")[1] + ",";
                    }
                    else
                    {
                         nemo_str += use_node[i] + ",";
                    }
                    
                    }
                    nemo_str = nemo_str.substring(0, nemo_str.length - 1);
                    nemo_str += "";
                }
                
                nemo_str += "<span class='key'> Property </span>";
                if ($('#l3-group_ip-prefix').val().trim() != "")
                    nemo_str += "ip-prefix:" +'"'+$('#l3-group_ip-prefix').val().trim() + '"';
                //if ($('#l3-group_capacity').val().trim() != "")
                    //nemo_str += "," + "capacity:" + $('#l3-group_capacity').val().trim() + '';
                nemo_str += "";

                json_str += '"node_type":"l3-group",';
                json_str += '"sub-node":{';
                json_str += '"sub-node":[';
                if (use_node.length > 0) {
                    for (var i = 0; i < use_node.length; i++) {
                        json_str += '"' + use_node[i] + '"' + ",";
                    }
                    json_str = json_str.substring(0, json_str.length - 1);
                }
                json_str += ']';
                
                json_str += '},';
                json_str += '"property":{';
                json_str += '"ip-prefix":"' + $('#l3-group_ip-prefix').val().trim() + '"';
               // json_str += '"capacity":"' + $('#l3-group_capacity').val().trim() + '"}';
                json_str += '}}';
            }
            else if (node_type == 'ext-group') {
                //conn_aviable_node.push("ext-group:" + $("#node_name").val().trim());

                nemo_str += "<span class='key'>IMPORT Node </span>";
                nemo_str += $("#node_name").val().trim() + " ";
                nemo_str += "<span class='key'>Type </span>ext-group ";
                nemo_str += "<span class='key'> Property </span>";
               if ($('#ext-group_location').val().trim() != "")
                    nemo_str += "location:" + '"'+$('#ext-group_location').val().trim() + '",';
               if ($('#ext-group_ac-info-network').val().trim() != "")
                   nemo_str += "" + "ac-info-network:" +'"'+$('#ext-group_ac-info-network').val().trim() + '",';
               if ($('#ext-group_ac-info-protocol').val().trim() != "")
                   nemo_str += "" + "ac-info-protocol:" +'"'+ $('#ext-group_ac-info-protocol').val().trim() + '",';
               if ($('#ext-group_ip-prefix').val().trim() != "")
                   nemo_str += "" + "ip-prefix:" +'"'+ $('#ext-group_ip-prefix').val().trim() + '",';
                nemo_str += "";
                nemo_str = nemo_str.substring(0,nemo_str.length-1);

                json_str += '"node_type":"ext-group",';
     
                json_str += '"property":{';
                json_str += '"location":"' + $('#ext-group_location').val().trim() + '",';
                json_str += '"ac-info-network":"' + $('#ext-group_ac-info-network').val().trim() + '",';
                json_str += '"ac-info-protocol":"' + $('#ext-group_ac-info-protocol').val().trim() + '",';
                json_str += '"ip-prefix":"' + $('#ext-group_ip-prefix').val().trim() + '"}';
                json_str += '}';
            }
             else if (node_type == 'chain-group') {
               // chain_node.push("chain-group:" + $("#node_name").val().trim());

                nemo_str += "<span class='key'>CREATE Node </span>";
                nemo_str += $("#node_name").val().trim() + " ";
                nemo_str += "<span class='key'>Type </span>chain-group "
                var use_node = new Array();
                var use_group = new Array();
                chain_aviable_node = [];
                    $("#chain_addhost option").each(function(){
                    	chain_aviable_node.push($(this).val());
                    });
                    console.log(chain_aviable_node);
                $("#chain_select_table").find("tr").each(function () {
                    var node_name = $(this).find("td").eq(0).html();
                    use_node.push($(this).find("td").eq(0).html());
                    for (i in chain_aviable_node) {
                        if (chain_aviable_node[i] == node_name){}
                            //chain_aviable_node.splice(i, 1);
                    }
                });

                if (use_node.length > 0) {
                    nemo_str += "<span class='key'>Contain </span>";
                    for (var i = 0; i < use_node.length; i++) {
                        if(use_node[i].indexOf("host")>=0)
                    {
                        $("#nemo_str_show").append("<p><span class='key'>IMPORT Node </span>" + use_node[i].split(":")[1] + " <span class='key'>Type</span> host</p>");
                        nemo_str += use_node[i].split(":")[1] + ",";
                    }
                    else
                    {
                         nemo_str += use_node[i].split(":")[1]+ ",";
                    }
                    
                    }
                    nemo_str = nemo_str.substring(0, nemo_str.length - 1);
                    nemo_str += "";
                }
                
               
                json_str += '"node_type":"chain-group",';
                json_str += '"sub-node":{';
                json_str += '"sub-node":[';
                if (use_node.length > 0) {
                    for (var i = 0; i < use_node.length; i++) {
                        json_str += '"' + use_node[i] + '"' + ",";
                    }
                    json_str = json_str.substring(0, json_str.length - 1);
                }
                json_str += ']';
                
                json_str += '},';
                json_str += '"property":{';
               //json_str += '"ip-prefix":"' + $('#chain-group_ip-prefix').val().trim() + '"';
               // json_str += '"capacity":"' + $('#chain-group_capacity').val().trim() + '"}';
                json_str += '}}';

            } else if (node_type == 'cache') {
                chain_aviable_node.push("cache:" + $("#node_name").val().trim());

                nemo_str += "<span class='key'>IMPORT Node </span>";
                nemo_str += $("#node_name").val().trim() + " ";
                nemo_str += "<span class='key'>Type </span>cache ";
                nemo_str += "<span class='key'> Property </span>";
                if ($('#cache_location').val().trim() != "")
                    nemo_str += "location:" + '"' +$('#cache_location').val().trim() + '",';
               if ($('#cache_operating-mode').val().trim() != "")
                    nemo_str += "" + "operating-mode:" +'"'+$('#cache_operating-mode').val().trim() + '",';
                nemo_str = nemo_str.substring(0,nemo_str.length-1);

                json_str += '"node_type":"cache",';
     
                json_str += '"property":{';
                json_str += '"location":"' + $('#cache_location').val().trim() + '",';
                json_str += '"operating-mode":"' + $('#cache_operating-mode').val().trim() + '"}';
                json_str += '}';
            }

        }
        else if ($('#entity_type').children("option:selected").val() == 'connection') {
            nemo_str += "<span class='key'>CREATE Connection </span>";
            //nemo_str += $('#connection_id').val().trim() + " ";
            //nemo_str += "<span class='key'>Name </span>";
            nemo_str += $("#connection_name").val().trim() + " ";

            json_str += '"Entity_Type":"connection",'
            json_str += '"connection_id":"' + $('#connection_id').val().trim() + '",';
            json_str += '"connection_name":"' + $('#connection_name').val().trim() + '",';
            var conn_type = $('#con_type').children("option:selected").val();
            if (conn_type == 'p2p') {
                nemo_str += "<span class='key'>Type </span>";
                nemo_str += "p2p ";
                nemo_str += "<span class='key'>Endnodes </span>";
                var one_node_name = $("#p2p_node_group").find("tr").eq(1).find("td").eq(1).find("span").html();
                var other_node_name = $("#p2p_node_group").find("tr").eq(3).find("td").eq(1).find("span").html();
                nemo_str += "" + one_node_name + ",";
                nemo_str += "" + other_node_name + "";

                json_str += '"connection_type":"p2p",';
                json_str += '"End-nodes":{';
                json_str += '"one_node_name":"' + one_node_name + '",';
                json_str += '"other_node_name":"' + other_node_name + '"';
                json_str += '},';
                //json_str += '"property":{}'
                //json_str += '}';

            }
            else if (conn_type == 'p2mp') {
                nemo_str += "<span class='key'>Type </span>";
                nemo_str += "p2mp ";
                nemo_str += "<span class='key'>End-nodes </span>[";
                var one_node_name = $("#p2mp_node_group").find("tr").eq(1).find("td").eq(1).find("span").html();
                nemo_str += "one_node_name:" + one_node_name + " ";

                json_str += '"connection_type":"p2mp",';
                json_str += '"End-nodes":{';
                json_str += '"one_node_name":"' + one_node_name + '",';
                var data = [];
                $("#p2mp_node_group table").find("tr:gt(2)").each(function () {
                    if ($(this).find("span").length>0) {
                        data.push($(this).find("span").html());
                    }
                });
                //$("#p2mp_node-name2 table").find(":checkbox:checked").each(function () {
                //    var val = $(this).parent().next().text();
                //    data.push(val);
                //});
                json_str += '"other_node_name":';
                json_str += '[';
                var node_list = "";
                for (var k = 0; k < data.length; k++) {
                    node_list += '"' + data[k] + '",';
                }
                node_list = node_list.substring(0, node_list.length - 1);
                json_str += node_list;
                json_str += ']';

                json_str += '},';
                //json_str += '"property":{}'
                //json_str += '}';
                nemo_str += "other_node_name:" + data + "]";
            }
            else if (conn_type == 'mesh') {
                var data = [];
                $("#mesh_node_group table").find("tr:gt(0)").each(function () {
                    if ($(this).find("span").length > 0) {
                        data.push($(this).find("span").html());
                    }
                });
                nemo_str += "<span class='key'>Type</span> mesh ";
                nemo_str += "<span class='key'>Nodes </span>"
                nemo_str +=  data + " ";
       
                json_str += '"connection_type":"mesh",';

                var node_list = "";
                for (var k = 0; k < data.length; k++) {
                    node_list += '"' + data[k] + '",';
                }
                node_list = node_list.substring(0, node_list.length - 1);
                json_str += '"node":';
                json_str += '[';
                json_str += node_list;
                json_str += ']';
                json_str += ',';

                //json_str += '"property":{}'
                //json_str += '}';
            }
            else {//chain-group,useless
                var data = [];
                $("#chain_node_group table").find("tr:gt(0)").each(function () {
                    if ($(this).find("span").length > 0) {
                        data.push($(this).find("span").html());
                    }
                });
                nemo_str += "<span class='key'>Type</span> chain ";
                nemo_str += "<span class='key'>node</span>["
                nemo_str +=  data + "]";

                json_str += '"connection_type":"chain",';

                var node_list = "";
                for (var k = 0; k < data.length; k++) {
                    node_list += '"' + data[k] + '",';
                }
                node_list = node_list.substring(0, node_list.length - 1);
                json_str += '"node":';
                json_str += '[';
                json_str += node_list;
                json_str += ']';
                json_str += ',';

            }
           
            if ($("#bandwidth").val().trim() != "")
            {
                nemo_str += "<span class='key'>  Property </span>";
                nemo_str += "bandwidth:" + '"'+$("#bandwidth").val().trim()+'"';
            }
            if ($("#latency").val().trim() != "")
            {
               
                nemo_str += ",latency:" + $("#latency").val().trim();
            }
            if ($("#Jitter").val().trim() != "")
            {
                nemo_str += ",Jitter:" + $("#Jitter").val().trim();
            }
            nemo_str += "";

            json_str += '"property":{';
            json_str += '"bandwidth":"' + $("#bandwidth").val().trim() + '",';
            json_str += '"latency":"' + $("#latency").val().trim() + '",';
            json_str += '"Jitter":"' + $("#Jitter").val().trim() + '"}';
            json_str += '}';
        }
        else {
             eth_type = $("#eth_type").val().trim();
             src_mac = $("#src_mac").val().trim();
             dst_mac = $("#dst_mac").val().trim();
             protocol = $("#protocol").val().trim();
             src_ip = $("#src_ip").val().trim();
             dst_ip = $("#dst_ip").val().trim();
             src_port = $("#src_port").val().trim();
             dst_port = $("#dst_port").val().trim();

            nemo_str += "<span class='key'>CREATE Flow </span>"
            //nemo_str += $('#flow_id').val().trim() + ' ';
            //nemo_str += "<span class='key'>Name </span>" ;
            nemo_str += $('#flow_name').val().trim() + ' ';
            nemo_str += "<span class='key'>Match </span>";
            //nemo_str += "src_node:" + $("#src_node option:selected").text() + ",";
            //nemo_str += "dest_node:" + $("#dest_node option:selected").text();
            if (eth_type != "")
                nemo_str += "eth-type:" + '"'+eth_type +'",';
            if (src_mac != "")
                nemo_str += "src-mac:" +'"'+ src_mac +'",';
            if (dst_mac != "")
                nemo_str += "dst-mac:" +'"'+ dst_mac +'",';
            if (protocol != "")
                nemo_str += "protocol:" + '"'+protocol +'",';
            if (src_ip != "")
                nemo_str += "src-ip:" + '"'+src_ip +'",';
            if (dst_ip != "")
                nemo_str += "dst-ip:" +'"'+ dst_ip +'",';
            if (src_port != "")
                nemo_str += "src-port:" +'"'+ src_port +'",';
            if (dst_port != "")
                nemo_str += "dst-port:" +'"'+ dst_port +'",';

            nemo_str = nemo_str.substring(0,nemo_str.length-1);

            json_str += '"Entity_Type":"flow",'
            json_str += '"flow_id":"' + $('#flow_id').val().trim() + '",';
            json_str += '"flow_name":"' + $('#flow_name').val().trim() + '",';
            json_str += '"match_items":';
            json_str += '{';
            json_str += '"eth-type":"' + eth_type + '",';
            json_str += '"src-mac":"' + src_mac + '",';
            json_str += '"dst-mac":"' + dst_mac + '",';
            json_str += '"protocol":"' + protocol + '",';
            json_str += '"src-ip":"' + src_ip + '",';
            json_str += '"dst-ip":"' + dst_ip + '",';
            json_str += '"src-port":"' + src_port + '",';
            json_str += '"dst-port":"' + dst_port + '"';
            json_str += '}';
            json_str += '}';
        }
        json_str += '}';
        

        var lgroup = {};
        lgroup.lgroup_aviable_node=lgroup_aviable_node;
        localStorage.setItem(service_name+"_lgroup_aviable_node",JSON.stringify(lgroup));

        var chain_node = {};
        chain_node.chain_aviable_node=chain_aviable_node;
        //console.log(chain_node);
        localStorage.setItem(service_name+"_chain_aviable_node",JSON.stringify(chain_node));

        if (flag == true) {
            //the entity has been created,and this is edit
            //console.log("submited:" + $("#sel_2").children("option:selected").attr("class"));
        console.log("submited:" + $("#sel_2").html());
        
        if ($("#sel_2").children("option:selected").hasClass("grey_background")) {
            //nemo show the change property only
           var nemo_str_update="<span class='key'>UPDATE </span>";
            //var json_str = localStorage.getItem(entity);
        
           //if (!sevice_name || !entity_name ) return;
           //console.log(sevice_name+entity_name);
           //get json string
            var entity = sevice_name + "__" + instance_name.replace(':', '_');
            //var json_str = localStorage.getItem(entity);
            // console.log(entity);
            //console.log(json_str);
            if (!json_str_col || typeof (json_str_col) == "undefined") return;
            console.log(json_str_col);
            var $json =JSON.parse(json_str_col);

            if ($("#entity_type").children('option:selected').val() == "node") {
                    nemo_str_update+="<span class='key'>Node</span> "+$("#node_name").val().trim();
                    var node_type = $("#sel_show").children('option:selected').val();
                 if (node_type == 'fw') {
                   //nemo_str_update += "<span class='key'>Type</span> fw "
                    nemo_str_update += "<span class='key'> Property </span>";
                    if ($('#fw_location').val().trim() != "")
                    nemo_str_update += "location:" + '"'+$('#fw_location').val().trim() + '"';
                     if ($('#fw_operating-mode').val().trim() != "")
                    nemo_str_update += "" + "operating-mode:" +'"'+ $('#fw_operating-mode').val().trim() + '",';
                    nemo_str_update =  nemo_str_update.substring(0, nemo_str_update.length-1);					
                    }
                else if(node_type == 'lb'){
                   // nemo_str_update += "<span class='key'>Type </span>lb "
                    nemo_str_update += "<span class='key'> Property </span>";
                    if ($('#lb_location').val().trim() != "")
                    nemo_str_update += "location:" + '"'+$('#lb_location').val().trim() + '",';
                    if ($('#lb_operating-mode').val().trim() != "")
                    nemo_str_update += "" + "operating-mode:" +'"'+ $('#lb_operating-mode').val().trim() + '",';
                     nemo_str_update =  nemo_str_update.substring(0, nemo_str_update.length-1);	

                    }
                 else if(node_type == 'cache'){

               		 //nemo_str_update += "<span class='key'>Type </span>cache ";
                	 nemo_str_update += "<span class='key'> Property </span>";
              		 if ($('#cache_location').val().trim() != "")
                     nemo_str_update += "location:" + '"' +$('#cache_location').val().trim() + '",';
                    if ($('#cache_operating-mode').val().trim() != "")
                     nemo_str_update += "" + "operating-mode:" +'"'+$('#cache_operating-mode').val().trim() + '",';
                	 nemo_str_update =  nemo_str_update.substring(0, nemo_str_update.length-1);	

                    }
                 else if(node_type == 'ext-group'){

                    //nemo_str_update += "<span class='key'>Type </span>ext-group ";
              		nemo_str_update += "<span class='key'> Property </span>";
              		if ($('#ext-group_location').val().trim() != "")
              	    nemo_str_update += "location:" + '"'+$('#ext-group_location').val().trim() + '",';
              		if ($('#ext-group_ac-info-network').val().trim() != "")
              	     nemo_str_update += "" + "ac-info-network:" +'"'+$('#ext-group_ac-info-network').val().trim() + '",';
              		 if ($('#ext-group_ac-info-protocol').val().trim() != "")
                	 nemo_str_update += "" + "ac-info-protocol:" +'"'+ $('#ext-group_ac-info-protocol').val().trim() + '",';
             		 if ($('#ext-group_ip-prefix').val().trim() != "")
                  	 nemo_str_update += "" + "ip-prefix:" +'"'+ $('#ext-group_ip-prefix').val().trim() + '",';
              		  nemo_str_update =  nemo_str_update.substring(0, nemo_str_update.length-1);	

                    }
                else if(node_type == 'l2-group'){

                     //nemo_str_update += "<span class='key'>Type </span>l2-group ";
                     var use_node = new Array();
                    //var use_group = new Array();              
                    // console.log(lgroup_aviable_node)
                    $("#l2_select_table").find("tr").each(function () {
                    var node_name = $(this).find("td").eq(0).html();
                    use_node.push($(this).find("td").eq(0).html());
                    for (i in lgroup_aviable_node)
                    {
                        if (lgroup_aviable_node[i] == node_name)
                            lgroup_aviable_node.splice(i,1);
                    }

                });
                    if (use_node.length > 0) {
                    nemo_str_update += "<span class='key'> Contain </span>";
                    for (var i = 0; i < use_node.length; i++) {                      
                        
                    if(use_node[i].indexOf("host")>=0)
                    {
                        //$("#nemo_str_show").append("<p><span class='key'>IMPORT Node </span>" + use_node[i].split(":")[1] + " <span class='key'>Type</span> host</p>");
                        nemo_str_update += use_node[i].split(":")[1] + ",";
                    }
                    else
                    {
                         nemo_str_update += use_node[i].split(":")[1] + ",";
                    }
         
                    }
                    nemo_str_update = nemo_str_update.substring(0, nemo_str_update.length - 1);
                    nemo_str_update += "";
                   }

                      if($json[entity_col]["property"]["ip-prefix"]!=$("#l2-group_ip-prefix").val()||$json[entity_col]["property"]["gateway-ip"]!=$("#l2-group_gateway-ip").val())
                       {
                        nemo_str_update+="<span class='key'> Property</span> ";
                        if($json[entity_col]["property"]["ip-prefix"]!=$("#l2-group_ip-prefix").val()){
                            nemo_str_update+="ip-prefix:" + '"'+$("#l2-group_ip-prefix").val()+'"';
                            if($json[entity_col]["property"]["gateway-ip"]!=$("#l2-group_gateway-ip").val())
                                nemo_str_update+=",gateway-ip:" + '"'+$("#l2-group_gateway-ip").val()+'"';
                        }
                        else{
                             nemo_str_update+="gateway-ip:" +'"'+ $("#l2-group_gateway-ip").val()+'"';
                        }              
                       }
                    }
                else if(node_type == 'l3-group'){
                	//nemo_str_update += "<span class='key'>Type </span>l3-group "
                	var use_node = new Array();
                	var use_group = new Array();

               		 $("#l3_select_table").find("tr").each(function () {
                     var node_name = $(this).find("td").eq(0).html();
                     use_node.push($(this).find("td").eq(0).html());
                     for (i in lgroup_aviable_node) {
                        if (lgroup_aviable_node[i] == node_name)
                            lgroup_aviable_node.splice(i, 1);
                    }
                });

                if (use_node.length > 0) {
                    nemo_str_update += "<span class='key'> Contain </span>";
                    for (var i = 0; i < use_node.length; i++) {
                        if(use_node[i].indexOf("host")>=0)
                    {
                        //$("#nemo_str_show").append("<p><span class='key'>IMPORT Node </span>" + use_node[i].split(":")[1] + " <span class='key'>Type</span> host</p>");
                        nemo_str_update += use_node[i].split(":")[1] + ",";
                    }
                    else
                    {
                         nemo_str_update += use_node[i].split(":")[1] + ",";
                    }
                    
                    }
                    nemo_str_update = nemo_str_update.substring(0, nemo_str_update.length - 1);
                    nemo_str_update += "";
                   }
                        if($json[entity_col]["property"]["ip-prefix"]!=$("#l3-group_ip-prefix").val())
                       {
                            nemo_str_update+="<span class='key'> Property</span> ";                        
                            nemo_str_update+="ip-prefix:" + '"' +$("#l3-group_ip-prefix").val()+'"';                          
                        }

                    }else if(node_type == 'chain-group'){
                    	 //nemo_str_update += "<span class='key'>Type </span>chain-group "
                    var use_node = new Array();
                    var use_group = new Array();

                    chain_aviable_node = [];
                    $("#chain_addhost option").each(function(){
                    	chain_aviable_node.push($(this).val());
                    });
                    console.log(chain_aviable_node);
                    $("#chain_select_table").find("tr").each(function () {
                    var node_name = $(this).find("td").eq(0).html();
                    use_node.push($(this).find("td").eq(0).html());
                    for (i in chain_aviable_node) {
                        if (chain_aviable_node[i] == node_name){}
                            //chain_aviable_node.splice(i, 1);
                    }
                });

                if (use_node.length > 0) {
                    nemo_str_update += "<span class='key'> Contain </span>";
                    for (var i = 0; i < use_node.length; i++) {
                        if(use_node[i].indexOf("host")>=0)
                    {
                        //$("#nemo_str_show").append("<p><span class='key'>IMPORT Node </span>" + use_node[i].split(":")[1] + " <span class='key'>Type</span> host</p>");
                        //nemo_str_update += use_node[i].split(":")[1] + ",";
                    }
                    else
                    {
                         nemo_str_update += use_node[i].split(":")[1] + ",";
                    }
                    
                    }
                    nemo_str_update = nemo_str_update.substring(0, nemo_str_update.length - 1);
                    nemo_str_update += "";
                     }

                    }

            }
            else if($("#entity_type").children('option:selected').val() == "connection") {
                nemo_str_update+=" <span class='key'>Connection</span> "+$("#connection_name").val().trim();
                var conn_type = $('#con_type').children("option:selected").val();
                if (conn_type == 'p2p') {
					//graph edit 
					$("#"+$("#connection_name").val().trim()).remove();
				   nemo_str_update += "<span class='key'> Endnodes </span>";
                   var one_node_name = $("#p2p_node_group").find("tr").eq(1).find("td").eq(1).find("span").html();
                   var other_node_name = $("#p2p_node_group").find("tr").eq(3).find("td").eq(1).find("span").html();
                   nemo_str_update += "" + one_node_name + ",";
                   nemo_str_update += "" + other_node_name + "";
                    if($json[entity_col]["property"]["bandwidth"] != $("#bandwidth").val().trim())
                    {
                        nemo_str_update+="<span class='key'> Property</span> ";
                        nemo_str_update+="bandwidth:" + $("#bandwidth").val();
                    }

                }
                else if (conn_type == 'p2mp') {
                	
                     if($json[entity_col]["property"]["bandwidth"]!=$("#bandwidth").val())
                    {
                        nemo_str_update+="<span class='key'>Property</span> ";
                        nemo_str_update+="bandwidth:" + $("#bandwidth").val();
                    }
                }
                else{//mesh
                     if($json[entity_col]["property"]["bandwidth"]!=$("#bandwidth").val())
                    {
                        nemo_str_update+="<span class='key'>Property</span> ";
                        nemo_str_update+="bandwidth:" + $("#bandwidth").val();
                    }

                }

            }
            else{//flow
             var eth_type_ = $("#eth_type").val().trim();
             var src_mac_ = $("#src_mac").val().trim();
             var dst_mac_ = $("#dst_mac").val().trim();
             var protocol_ = $("#protocol").val().trim();
             var src_ip_ = $("#src_ip").val().trim();
             var dst_ip_ = $("#dst_ip").val().trim();
             var src_port_ = $("#src_port").val().trim();
             var dst_port_ = $("#dst_port").val().trim();

            nemo_str_update += "<span class='key'>Flow </span>"+$('#flow_name').val().trim() + ' ';
            nemo_str_update += "<span class='key'>Match </span>";
            if (eth_type != "")
                nemo_str_update += "eth-type:" +'"'+ eth_type_ +'",';
            if (src_mac != "")
                nemo_str_update += "src-mac:" + '"'+src_mac_ +'",';
            if (dst_mac != "")
                nemo_str_update += "dst-mac:" + '"'+dst_mac_ +'",';
            if (protocol != "")
                nemo_str_update += "protocol:" + '"'+protocol_ +'",';
            if (src_ip != "")
                nemo_str_update += "src-ip:" + '"'+src_ip_ +'",';
            if (dst_ip != "")
                nemo_str_update += "dst-ip:" + '"'+dst_ip_ +'",';
            if (src_port != "")
                nemo_str_update += "src-port:" + '"'+src_port_ +'",';
            if (dst_port != "")
                nemo_str_update += "dst-port:" + '"'+dst_port_ +'",';

            nemo_str_update = nemo_str_update.substring(0,nemo_str_update.length-1);
            }

            var f=0;
             $("#nemo_str_show p").each(function () {
                if($(this).hasClass('grey_background')) return true;

                if (($(this).text().indexOf($("#node_name").val() + " ") >= 0 && $(this).text().indexOf("Node") >= 0)
                      || ($(this).text().indexOf($("#connection_name").val() + " ") >= 0 && $(this).text().indexOf("Connection") >= 0)
                      || ($(this).text().indexOf($("#flow_name").val() + " ") >= 0 && $(this).text().indexOf("Flow") >= 0)) 
                        {
                            $(this).html(nemo_str_update);
                            f=1;
                            return false;
                        }
             });

             if(f==0){
                $("#nemo_str_show").append("<p>" + nemo_str_update + "</p>");
             }
                
            }
            else {
                $("#nemo_str_show p").each(function () {
                    if (($(this).text().indexOf($("#node_name").val() + " ") >= 0 && $(this).text().indexOf("Node") >= 0)
                      || ($(this).text().indexOf($("#connection_name").val() + " ") >= 0 && $(this).text().indexOf("Connection") >= 0)
                      || ($(this).text().indexOf($("#flow_name").val() + " ") >= 0 && $(this).text().indexOf("Flow") >= 0)) {
                        {
                            $(this).html(nemo_str);
                            return false;
                        }
                    }
                });
            }
        }
        else {
            $("#nemo_str_show").append("<p>" + nemo_str + "</p>");
            //put node into group_node
            if ($("#entity_type").val() == "node") {
                console.log($("#node_name").val());
                //group_node.push($("#node_name").val());
            }
        }
        flag = false;
        //set localstorsge
        console.log(nemo_str);
        console.log(json_str);
        //chain-group delete use 
        var chain_str = localStorage.getItem(sevice_name + "__" + instance_name.replace(':', '_'));
        localStorage.removeItem(sevice_name + "__" + instance_name.replace(':', '_'));
        localStorage.setItem(service_name + "__" + instance_name.replace(':', '_'), json_str);
        //clear match data
             eth_type = "";
             src_mac = "";
             dst_mac = "";
             protocol = "";
             src_ip = "";
             dst_ip = "";
             src_port = "";
             dst_port = "";
        ///////////////////////////////////////////zm/////////////////////////////////////////////////////////////
        var Net_Operation = $("#entity_type").children('option:selected').val();
        var Node_Type = $("#sel_show").children('option:selected').val();
	    if (Net_Operation == 'node' && flag == false) {
			try{
				if(ne_flag == 0){
					
					if(Node_Type == "l2-group"){
						var node_count_list = $("#l2_select_table tr").length;
						draw_group(node_count_list,"l2_select_table");
						node_count++;
						redraw_node_possition();	
						redraw_connection_possition();
						redraw_flow_possition();
					}
					else if(Node_Type == "l3-group"){
						var node_count_list = $("#l3_select_table tr").length;
						draw_group(node_count_list,"l3_select_table");
						node_count++;
						redraw_node_possition();	
						redraw_connection_possition();	
						redraw_flow_possition();
					}
					else if(Node_Type == "chain-group"){
						var node_count_list = $("#chain_select_table tr").length;
						draw_group(node_count_list,"chain_select_table");
						node_count++;
						redraw_node_possition();	
						redraw_connection_possition();	
						redraw_flow_possition();
					}
					else if(Node_Type == "ext-group"){
						draw_group(0,"ext");
						node_count++;
						redraw_node_possition();	
						redraw_connection_possition();
						redraw_flow_possition();
					}
				}
				else if(ne_flag == 1)
				{
					var node_del_name = $("#node_name").val().trim();
					var tx = $("#"+node_del_name+"_group").attr("cx");
					var ty = $("#"+node_del_name+"_group").attr("cy");
					$("#"+node_del_name).remove();
					if(Node_Type == "l2-group"){
						var node_count_list = $("#l2_select_table tr").length;
						draw_group(node_count_list,"l2_select_table");
						redraw_specific_node_possition(node_del_name,tx,ty);
					}
					else if(Node_Type == "l3-group"){
						var node_count_list = $("#l3_select_table tr").length;
						draw_group(node_count_list,"l3_select_table");
						redraw_specific_node_possition(node_del_name,tx,ty);
					}
					else if(Node_Type == "chain-group")
					{
						if($("#chain_select_table tr").length > 0)
						{
							var node_count_list = $("#chain_select_table tr").length;
							draw_group(node_count_list,"chain_select_table");
							redraw_specific_node_possition(node_del_name,tx,ty);	
						}
						else
						{
							//delete chain-group info
							redraw_node_possition();	
							redraw_connection_possition();
							redraw_flow_possition();
							//delete chain-group 
							var $dele_obj = $("#sel_2 option:selected");
							if($dele_obj.hasClass('grey_background')){
								$("#nemo_str_show p").append("<p><span class='key'>DELETE Node</span> "+$dele_obj.val().split(":")[1]+"</p>");
								
                                localStorage.setItem(sevice_name + "__delete_" + entity_name.replace(':', '_'), chain_str);
							}
							else{	
					       $("#nemo_str_show p").each(function () {
                            if ($(this).text().indexOf($("#node_name").val() + " ") >= 0 && $(this).text().indexOf("Node") >= 0 ) {                       
                            $(this).remove();
                            return false;
                        }
                    
                });					
							}

							localStorage.removeItem(sevice_name_glb+"__"+$dele_obj.val().replace(":","_"));
							$dele_obj.remove();
						}
					}
				}
			}
            catch (err) {
				alert(err);
            }
            /* var data = {
                nodes: nodes,
                edges: edges
            };

            var container = document.getElementById('graph');
            graph = new vis.Graph(container, data, options); */

        }			 
		       //connection
        else if (Net_Operation == 'connection') {
            //p2p
            if ($('#con_type').children("option:selected").val() == 'p2p') {
                //alert('p2p');
				var conn_name = $("#connection_name").val();
                if(conn_name!=null && typeof(conn_name)!="undefined")
                $("#"+conn_name).remove();
                try 
				{
					var node_name_1 = get_connection_node()[0];
					var node_name_2 = get_connection_node()[1];
					var node_cx_1 = $("#"+node_name_1+"_group").attr("cx");
					var node_cy_1 = $("#"+node_name_1+"_group").attr("cy");
					var node_cx_2 = $("#"+node_name_2+"_group").attr("cx");
					var node_cy_2 = $("#"+node_name_2+"_group").attr("cy");
					//var g = document.createElementNS('http://www.w3.org/2000/svg', 'g');
					var path = document.createElementNS('http://www.w3.org/2000/svg', 'path');
					var path_main = $(path).attr({
						id: $("#connection_name").val().trim(),
						node_start:node_name_1,
						node_end:node_name_2,
						type:"connection",
						d: 'M'+node_cx_1 + " " + node_cy_1+' L'+node_cx_2+' '+node_cy_2,
						'stroke':"black",
						'stroke-width':parseInt($("#bandwidth").val().trim())/100,
						fill:"black"
					});
					$('#service_svg').prepend(path_main);						
                }

                catch (err) {
                    alert(err);
                }
            }

                //p2mp

            else if ($('#con_type').children("option:selected").val() == 'p2mp') {
			}
            
                // mesh
            else {
			}
           
        }	
		
			else if (Net_Operation == 'flow' )
		{
			var flow_delete_name_temp = $("#flow_name").val().trim();
			var old_color;
			if(ne_flag == 1)
			{
				old_color = $("#"+flow_delete_name_temp).attr("stroke");
				$("#"+flow_delete_name_temp).remove();

			}
			var src_group=flow_get_group()[0];
			var dest_group=flow_get_group()[1];
			//get flow count
			var flow_count_temp = 0;
			while(1)
			{
				var end_flag = 0;
				for(var find_count = 0;find_count<$("#service_svg path").length;find_count++)
				{
					if($("#service_svg path:eq("+find_count+")").attr("type") != "flow")
						continue;
					if($("#service_svg path:eq("+find_count+")").attr("node_start") == src_group && $("#service_svg path:eq("+find_count+")").attr("node_end") == dest_group)
					{
						if(flow_count_temp == $("#service_svg path:eq("+find_count+")").attr("count"))
						{
							end_flag = 1;
							flow_count_temp++;
							break;
						}
		
					}
				}
				if(end_flag != 1)
					break;	
			}
		
			
				
			
			//get Radio
			var cir_r = parseInt($("#"+src_group+" circle:eq(0)").attr("r"));
			
			
			//get circle cx cy
			src_cx = parseInt($("#"+src_group+" circle:eq(0)").attr("cx"));
			src_cy = parseInt($("#"+src_group+" circle:eq(0)").attr("cy"));
			dest_cx = parseInt($("#"+dest_group+" circle:eq(0)").attr("cx"));
			dest_cy = parseInt($("#"+dest_group+" circle:eq(0)").attr("cy"));
			console.log(src_cx+"  "+src_cy+"  "+dest_cx+"   "+dest_cy);
			
			//calculate deg
			var tri_h = dest_cx-src_cx;
			var tri_l = src_cy-dest_cy;
			var tri_s = Math.sqrt(tri_l*tri_l + tri_h*tri_h);
			var deg = Math.asin(tri_l/tri_s);
			console.log(tri_h+"  "+tri_l+"  "+tri_s);
			//alert(deg);
			console.log(deg);
			
			
			//calculate offset
			var offset = ((20+(15*flow_count_temp))/180)*Math.PI;
			console.log("offset:  "+offset);
			
			//calculate path possition
			var path_src_x,path_src_y,path_dest_x,path_dest_y,mid_x,mid_y,b_x,b_y;
			if(tri_h >= 0)
			{
				mid_x = src_cx + tri_h/2;
				mid_y = src_cy - tri_l/2;
				path_src_x = src_cx + cir_r*(Math.cos(offset+deg));
				path_src_y = src_cy - cir_r*(Math.sin(offset+deg));
				path_dest_x = dest_cx - (cir_r+14)*(Math.cos(offset-deg));
				path_dest_y = dest_cy - (cir_r+14)*(Math.sin(offset-deg));
				if(tri_l >= 0)
				{			
					b_x = mid_x - (80+30*flow_count_temp)*Math.abs(Math.sin(deg));;
					b_y = mid_y - (80+30*flow_count_temp)*Math.abs(Math.cos(deg));
				}
				else
				{
					b_x = mid_x + (80+30*flow_count_temp)*Math.abs(Math.sin(deg));
					b_y = mid_y - (80+30*flow_count_temp)*Math.abs(Math.cos(deg));
				}
				
			}
			else
			{
				mid_x = src_cx + tri_h/2;
				mid_y = src_cy - tri_l/2;
				path_src_x = src_cx - cir_r*(Math.cos(offset-deg));
				path_src_y = src_cy + cir_r*(Math.sin(offset-deg));
				path_dest_x = dest_cx + (cir_r+14)*(Math.cos(-offset-deg));
				path_dest_y = dest_cy - (cir_r+14)*(Math.sin(-offset-deg));
				if(tri_l >= 0)
				{			
					b_x = mid_x - (80+30*flow_count_temp)*Math.abs(Math.sin(deg));
					b_y = mid_y + (80+30*flow_count_temp)*Math.abs(Math.cos(deg));
				}
				else
				{
					b_x = mid_x + (80+30*flow_count_temp)*Math.abs(Math.sin(deg));
					b_y = mid_y + (80+30*flow_count_temp)*Math.abs(Math.cos(deg));
				}
				
			}
			//path title
			var path_title = document.createElementNS('http://www.w3.org/2000/svg', 'path');
			
			
			
			//create path
			var path = document.createElementNS('http://www.w3.org/2000/svg', 'path');
			var path_main = $(path).attr({
				id: $("#flow_name").val().trim(),
				node_start:src_group,
				node_end:dest_group,
				type:"flow",
				sx:path_src_x,
				sy:path_src_y,
				mx:b_x,
				my:b_y,
				ex:path_dest_x,
				ey:path_dest_y,
				via:"none",
				count:flow_count_temp,
				d: 'M'+path_src_x + " " + path_src_y+' Q'+b_x+' '+b_y+' '+path_dest_x+' '+path_dest_y,
				'stroke':"rgb("+get_path_color()+","+get_path_color()+","+get_path_color()+")",
				'stroke-width':3,
				fill:"none",
				'stroke-dasharray':"6,6" ,
				'marker-end':"url(#idArrow)",
				'marker-mid':"url(#idtext)"		
			});
			if(ne_flag == 0)
				$(path).attr("stroke","rgb("+get_path_color()+","+get_path_color()+","+get_path_color()+")");
			else if(ne_flag == 1)
				$(path).attr("stroke",old_color);
			$('#service_svg').prepend(path_main);	

		}
					 
					 
		//save svg 
			var svg_str = $("#service_svg").html();
			localStorage.setItem(sevice_name+"_svg",svg_str);

        /////////////////////////////////////////zm/////////////////////////////////////////////////////////////		
        //trigger close click
        $(".NE_close_show").click();
        setentity_instance_list();
        setentity_instance_list_();
         //create nemo sentence
        setnemo_str();

        //goback change type and name
        $("#entity_type").attr("disabled", false);

        $("#sel_show").attr("disabled", true);
        $("#node_name").attr("disabled", false);

        $("#con_type").attr("disabled", false);
        $("#connection_name").attr("disabled", false);

        $("#flow_name").attr("disabled", false);

        $("#p2p_node-name1").attr("disabled", false);
        $("#p2p_node-name2").attr("disabled", false);
        $("#p2mp_node-name1").attr("disabled", false);
        $("#p2mp_node-name2 table input").attr("disabled", false);

    });

    //change event
    $(".match_items").change(function () {
        str = $(".match_items").children('option:selected').val();
        if ($(".match_items").get(0).selectedIndex == 0) {
            $(".match_value").val(src_ip);
        }
        if ($(".match_items").get(0).selectedIndex == 1) {
            $(".match_value").val(dest_ip);
        }
        if ($(".match_items").get(0).selectedIndex == 2) {
            $(".match_value").val(src_port);
        }
        if ($(".match_items").get(0).selectedIndex == 3) {
            $(".match_value").val(dest_port);
        }
        if ($(".match_items").get(0).selectedIndex == 4) {
            $(".match_value").val(protocol);
        }
        if ($(".match_items").get(0).selectedIndex == 5) {
            $(".match_value").val(vlanid);
        }

        $(".match_value").val();

    });
    $(".match_value").blur(function () {
        str = $(".match_items").children('option:selected').val();
        if (str == "src_ip") {
            src_ip = $(this).val().trim();
        }
        if (str == "dest_ip") {
            dest_ip = $(this).val().trim();
        }
        if (str == "src_port") {
            src_port = $(this).val().trim();
        }
        if (str == "dest_port") {
            dest_port = $(this).val().trim();
        }
        if (str == "protocol") {
            protocol = $(this).val().trim();
        }
        if (str == "vlanid") {
            vlanid = $(this).val().trim();
        }
    });
    var node_select = [];

    $("#con_type").change(function () {
        //$("#p2mp_node-name1,#p2mp_node-name2 ").empty();
      
        //if ($(this).get(0).selectedIndex == 1) {
        //    var service_name = $("#sel_1").children("option:selected").val();
        //    if (!service_name || typeof (service_name) == "undefined") return;
        //    var arr = new Array();
        //    var c = 0;
        //    if ($("#sel_2 option").length == 0)
        //        return;
        //    // $("p2mp_node-name1").empty();
        //    node_select.length = 0;
        //    $("#sel_2 option").each(function () {
        //        if ($(this).val().trim().indexOf("node") >= 0) {
        //            var j = service_name + "__" + $(this).val().replace(":", "_");
        //            var entity = localStorage.getItem(j)
        //            if (!entity || typeof (entity) == "undefined") return true;
        //            var json = jQuery.parseJSON(entity);
        //            if (json[j]["node_type"] == "firewall" || json[j]["node_type"] == "loadbalance" || json[j]["node_type"] == "group")
        //                return true;
        //            var str = $(this).val().split(":");
        //            $("#p2mp_node-name1").prepend("<Option value='" + str[1] + "'>" + str[1] + "</Option>");
        //            node_select.push(str[1]);
        //            arr[c] = str[1];
        //            c++;
        //        }
        //    });
        //    //CreateTable(c - 1, arr);
        //    console.log(" node_select:" + node_select);
        //}
    });

    //$("#p2mp_node-name1").change(function () {
    //    $("#p2mp_node-name2").empty();
    //    var st = $("#p2mp_node-name1").children("option:selected").val();
    //    var arr = new Array();
    //    var count = 0;
    //    for (var i = 0; i < node_select.length; i++) {
    //        if (node_select[i] == st)
    //            continue;
    //        arr[count] = node_select[i];
    //        count++;
    //        //alert(node_select[i]);
    //    }
    //    //$("#sel_2 option").each(function () {
    //    //    if ($(this).val().trim().indexOf("node") >= 0) {

    //    //        var str = $(this).val().split(":");
    //    //        if (str[1] == st)
    //    //            return true;
    //    //        arr[count] = str[1];
    //    //        count++;
    //    //    }
    //    //});
    //    CreateTable(count, arr);
    //});
    //$("#src_node").change(function () {
    //    get_nodes("dest_node");
    //    $("#dest_node option[value=" + $("#src_node").val() + "]").remove();
    //});
});
