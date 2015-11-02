function guid() {
    function S4() {
        return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
    }
    return (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4());
}
jQuery(document).ready(function ($) {
    function get_bid() {
                var bid_flag = "false";
				var sevice_name = "";
                var entity_name = "";
                sevice_name = $(".NE_up #sel_1").children('option:selected').val();
                entity_name = $("#apply_entity option:selected").text();
                if (sevice_name == "" || entity_name == "")
                     return;
                if (!entity_name || typeof (entity_name) == "undefined") return;

                //get json string
                var entity = sevice_name + "__" + entity_name.replace(':', '_');
                var json_str = localStorage.getItem(entity);
                if (!json_str || typeof (json_str) == "undefined")
                       return;
                console.log(json_str);
				var json = jQuery.parseJSON(json_str);
				if(json[entity]["Entity_Type"] == "flow")
				{
					bid_flag=json[entity]["match_items"]["bidirect"];
				}
				return bid_flag;
								
		}

	function get_color(temp){
		while(1)
		{
		   var num1 = Math.floor(Math.random()*(16000000/10*temp));
		   if(num1<1600000)
			   continue;
		   return num1.toString(16);
		}			
	}
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
    function getChainSet(){
       var ser = $(".SP_up .select_item").children("option:selected").val();
        var st = localStorage.getItem(ser + "_entity_instance_list");
        if (!st ) return;
        st = st.split(",");
          $("#policy_data").empty();
        for (var i = 0; i < st.length; i++) {
         var j= ser +"__"+ st[i].replace(":","_");
         var json_str = localStorage.getItem(j);
         if(!json_str) return;
         //console.log(json_str);
       
         var $json = JSON.parse(json_str);
            if (st[i] != "" && $json[j]["node_type"]=="chain-group")
                $("#policy_data").append("<Option value='" + st[i].split(":")[1] + "'>" + st[i].split(":")[1] + "</Option>");
        }
        $("#policy_data").get(0).selectedIndex = 0;   
               
}
	function get_apply_entity() {
		        var apply_entity_name;
                var sevice_name = $(".NE_up #sel_1").children('option:selected').val();
                var entity_name = "policy_"+$("#policy_name_list option:selected").text();
                if (!sevice_name  || !entity_name ) return;

                //get json string
                var entity = sevice_name + "__" + entity_name;
                var json_str = localStorage.getItem(entity);
                if (!json_str ) return;
                console.log(json_str);
				var json = jQuery.parseJSON(json_str);
				apply_entity_name=json[entity]["apply_entity_id"];
				return apply_entity_name;
								
		}
   function get_sfc_flag() {
                var sfc_flag = 0;
				var sevice_name = "";
                var entity_name = "";
                sevice_name = $(".NE_up #sel_1").children('option:selected').val();
                entity_name = $("#policy_name_list option:selected").text();
                if (sevice_name == "" || entity_name == "")
                     return;
                if (!entity_name || typeof (entity_name) == "undefined") return;
                //get json string
                var entity = sevice_name + "__" + "policy_"+entity_name.replace(':', '_');
                var json_str = localStorage.getItem(entity);
                if (!json_str || typeof (json_str) == "undefined")
                       return;
                console.log(json_str);
				var json = jQuery.parseJSON(json_str);
				if(json[entity]["action"] == "go-through")
					sfc_flag=1;
				else sfc_flag=0;
				return sfc_flag;
		}
		
		
		function get_flow_flag() {
                var flow_flag = 0;
				var sevice_name = "";
                var entity_name = "";
                sevice_name = $(".NE_up #sel_1").children('option:selected').val();
                entity_name = $("#apply_entity option:selected").text();
                if (sevice_name == "" || entity_name == "")
                     return;
                if (!entity_name || typeof (entity_name) == "undefined") return;

                //get json string
                var entity = sevice_name + "__" + entity_name.replace(':', '_');
                var json_str = localStorage.getItem(entity);
                if (!json_str || typeof (json_str) == "undefined")
                       return;
                console.log(json_str);
				var json = jQuery.parseJSON(json_str);
				if(json[entity]["Entity_Type"] == "flow")
					flow_flag=1;
				else flow_flag=0;
				return flow_flag;
		}
		
		function get_flow_sd() {
                var node=[];
				var sevice_name = "";
                var entity_name = "";
                sevice_name = $(".NE_up #sel_1").children('option:selected').val();
                entity_name = $("#apply_entity option:selected").text();
                if (sevice_name == "" || entity_name == "")
                     return;
                if (!entity_name || typeof (entity_name) == "undefined") return;

                //get json string
                var entity = sevice_name + "__" + entity_name.replace(':', '_');
                var json_str = localStorage.getItem(entity);
                if (!json_str || typeof (json_str) == "undefined")
                       return;
                console.log(json_str);
				var json = jQuery.parseJSON(json_str);
				if(json[entity]["Entity_Type"] == "flow")
					flow_flag=1;
				else flow_flag=0;
				node[0]=json[entity]["match_items"]["src_node"];
				node[1]=json[entity]["match_items"]["dest_node"];
				return node;
		}
		function get_flow_sd_del() {
                var node=[];
				var sevice_name = "";
                var entity_name = "";
                sevice_name = $(".NE_up #sel_1").children('option:selected').val();
                entity_name =get_apply_entity();
                if (sevice_name == "" || entity_name == "")
                     return;
                if (!entity_name || typeof (entity_name) == "undefined") return;

                //get json string
                var entity = sevice_name + "__" + entity_name.replace(':', '_');
                var json_str = localStorage.getItem(entity);
                if (!json_str || typeof (json_str) == "undefined")
                       return;
                console.log(json_str);
				var json = jQuery.parseJSON(json_str);
				if(json[entity]["Entity_Type"] == "flow")
					flow_flag=1;
				else flow_flag = 0;
				if (json[entity]["match_items"])
				    node[0] = json[entity]["match_items"]["src_node"];
				if (json[entity]["match_items"])
				node[1]=json[entity]["match_items"]["dest_node"];
				return node;
		}
    //function
    function sp_getSevice_names() {
        var get_service_names = localStorage.getItem("service_names");
        console.log(get_service_names);
        if (!get_service_names && typeof (get_service_names) != "undefined" && get_service_names != 0)
            return;
        $(".SP_up .select_item").empty();
        if (get_service_names != null || get_service_names != "") {
            svc_name = get_service_names.split(",");
            for (var j = 0; j < svc_name.length; j++) {
                $(".SP_up .select_item").append("<Option value='" + svc_name[j] + "'>" + svc_name[j] + "</Option>");
            }
        }
        $(".SP_up .select_item").val(service_select).attr("disabled", true);
    }
    function setpolicy_list() {
        //$(".SP_up .select_item").get(0).selectedIndex = 0;
        var service_name = "";
        service_name = $(".SP_up .select_item").children("option:selected").val();
        if (service_name == "")
            return;
        current_policy_list = "";
        $("#policy_name_list option").each(function () {
            current_policy_list += $(this).val().trim() + ",";
        });
        //console.log("cur_policy_list:" + current_policy_list);
        current_policy_list = current_policy_list.substring(0, (current_policy_list.length - 1));
        console.log("setpolicy_list current_policy_list: " + current_policy_list);
        localStorage.setItem(service_name + "_policy_list", current_policy_list);
    }
    function setpolicy_list_() {
 
        var service_name = "";
        service_name = $(".SP_up .select_item").children("option:selected").val();
        if (service_name == "") return;
        current_policy_list = "";

        localStorage.setItem(service_name + "_policy_list_", $("#policy_name_list").html());
        console.log("setpolicy_list_: " + $("#policy_name_list").html());
    }
    function getpolicy_list() {
        $("#policy_name_list").empty();
        var service_name = "";
        service_name = $(".SP_up .select_item").children('option:selected').val();
        if (!service_name || typeof (service_name) == "undefined") return;
        var policy_list = localStorage.getItem(service_name + "_policy_list_");
        if(!policy_list)
        {
         policy_list = localStorage.getItem(service_name + "_policy_list");
        console.log(policy_list);
        if (!policy_list || typeof (policy_list) == "undefined")
            return;

        svc_name = policy_list.split(",");
        for (var k = 0; k < svc_name.length; k++) {
            if (svc_name[k] != "null" && svc_name[k]!=null && svc_name[k] != "")
                $("#policy_name_list").append("<Option value='" + svc_name[k] + "'>" + svc_name[k] + "</Option>");
        }
        } 
        else{
            $("#policy_name_list").html(policy_list); 
         }
    }
    function getpolicy_list_() {
        $("#policy_name_list").empty();
        var service_name = "";
        service_name = $(".SP_up .select_item").children('option:selected').val();
        if (!service_name || typeof (service_name) == "undefined") return;
        var policy_list = localStorage.getItem(service_name + "_policy_list_");
        console.log(policy_list);
        if (!policy_list || typeof (policy_list) == "undefined") return;

        $("#policy_name").remove().append(policy_list);
    }

    function entity_show() {
        var ser = $(".SP_up .select_item").children("option:selected").val();
        var st = localStorage.getItem(ser + "_entity_instance_list");
        if (!st ) return;
        st = st.split(",");
        for (var i = 0; i < st.length; i++) {
        var j = ser +"__"+st[i].replace(":","_");
         var json_str = localStorage.getItem(j);
         if(!json_str) return;
         var $json = JSON.parse(json_str);
          console.log($json);

            if (st[i] != "" && $json[j]["Entity_Type"]=="flow")
                $("#apply_entity").append("<Option value='" + st[i] + "'>" + st[i] + "</Option>");
        }
        $("#apply_entity").get(0).selectedIndex = 0;
        console.log(st);
    }
	function firewall_show() {
	    var ser = $(".SP_up .select_item").children("option:selected").val();
        var st = localStorage.getItem(ser + "_entity_instance_list");
        if (!st || typeof (st) == "undefined")
            return;
        st = st.split(",");
        for (var i = 0; i < st.length; i++) {
            if (st[i] != "" && st[i].indexOf("firewall"))
                $("#apply_entity").append("<Option value='" + st[i] + "'>" + st[i] + "</Option>");
        }
        $("#apply_entity").get(0).selectedIndex = 0;
        console.log(st);
	}
    function getnemo_str() {
        $("#nemo_str_show").empty();
        var service_name = "";
        service_name = $(".select_item").children("option:selected").val();
        if (!service_name || typeof (service_name) == "undefined")
            return;
        var str = localStorage.getItem(service_name + "_nemo_str");
        //console.log("get:"+str);
        //if (!str || typeof (str) == "undefined")
        //    return;
        //str = str.split("|");

        //for (s = 0; s < str.length; s++) {
        //    $("#nemo_str_show").append("<p class='key'>" + str[s] + "</p>");
        //}
        $("#nemo_str_show").append(str);

    }
    function setpolicy_nemo_str() {
        var service_name = "";
        service_name = $(".select_item").children("option:selected").val();
        if (!service_name || typeof (service_name) == "undefined")
            return;
        str = "";

        //$("#nemo_str_show p").each(function () {
        //    str += $(this).html() + "|";
        //});
       // str = str.substring(0, str.length - 1);
        //localStorage.setItem(service_name + "_nemo_str", str);
        //console.log("set:"+localStorage.getItem(service_name + "_nemo_str"));

        str = $("#nemo_str_show").html();
        //console.log("set_add_policy_nemo:" + str);
        localStorage.setItem(service_name + "_nemo_str", str);
    }
    //create table
    function sp_CreateTable(rowCount, arr) {
        var table = $("<table ' border=\"0\">");
        table.appendTo($(".list_nodes"));
        for (var i = 0; i < rowCount; i++) {
            var tr = $("<tr></tr>");
            tr.appendTo(table);
            var td = $("<td> <input type='checkbox'> </td><td>" + arr[i] + "</td>");
            td.appendTo(tr);
        }
        $(".list_nodes").append("</table>");
    }

    function table_show() {
        var ser = $(".SP_up .select_item").children("option:selected").val();
        var arr = new Array();
        var st = localStorage.getItem(ser + "_entity_instance_list");
        if (!st && typeof (st) != "undefined" && st != 0)
            return;
        count = 0;
        st = st.split(",");
        for (var k = 0; k < st.length; k++) {
            if (st[k].indexOf("node") >= 0) {
                s = st[k].split(':');
                arr.push(s[1]);
                count++;
            }
        }
        console.log(arr);
        sp_CreateTable(count, arr);
    }
    function get_flow_node() {
        var entity_name = $("#apply_entity option:selected").val();
        if (entity_name.indexOf("flow") >= 0) {
            var service_name = $(".select_item").children("option:selected").val();
            if (!service_name || typeof (service_name) == "undefined") return;
            var j = service_name + "__" + entity_name.replace(":", "_");
            var flow_info = localStorage.getItem(j);
            var json = jQuery.parseJSON(flow_info);
            var nodes = [];
            nodes.push(json[j]["match_items"]["src_node"]);
            nodes.push(json[j]["match_items"]["dest_node"]);
            return nodes;
        }
        return null;
    }
    function get_node() {
        var service_name = $(".SP_up .select_item").children("option:selected").val();
        if (!service_name || typeof (service_name) == "undefined") return;
        var str = localStorage.getItem(service_name + "_entity_instance_list");
        console.log("entity_list:" + str);
        if (!str || typeof (str) == "undefined") return;
        str = str.split(",");
        $("#adc_node").empty();
        for (var i = 0; i < str.length; i++) {
            var j = service_name + "__" + str[i].replace(":", "_");
            var json_str = localStorage.getItem(j);
            var json = jQuery.parseJSON(json_str);
            console.log("chain_node_type:" + json[j]["node_type"]);
            if (json[j]["node_type"] == "host" || json[j]["node_type"] == "internet")
                continue;

            if (str[i].indexOf("node") >= 0) {
                var s = str[i].split(":");
                $("#adc_node").append("<Option value='" + s[1] + "'>" + s[1] + "</Option>");
            }
        }
		
		//remove group node ,show group name
		/*for(var k=1;k<Group_List.length;k++)
		 {			
			 var ss=Group_List[k][0][2];
			 if(ss==null||ss.length<6) continue;
			 ss=ss.substring(6,ss.length-1);				
             console.log(Group_List[k][0][2]);
			 console.log(ss);
             st=ss.split(",");	
             console.log(st);			
			 for(var l=0;l<st.length;l++)
			 {	
		    $("#adc_node option[value='"+st[l]+"']").remove();
			}
			   $("#adc_node").append("<Option value='group'>" + Group_List[k][0][2] + "</Option>");
		 }*/
		
    }
	
    
	
	
    //init 
    $("#ser_poc_init").click(function () {
        $("#policy_action").get(0).selectedIndex = 0;
        $("#policy_action").change();
        sp_getSevice_names();
        getpolicy_list();
        $("#SP_close_show").click();
        //get_node();
        getnemo_str();
    });

    $("#SP_close_show").click(function () {
        $(".SP_add_show").hide();
        $(".SP_add_show input.cle").val("");

        $("#policy_name_list option").each(function () {
            if ($(this).val() == "empty")
                $(this).remove();
        });
        //$("#policy_name_list").get(0).selectedIndex = 0;
    });

    $("input.con_button").click(function () {
        if ($(".con_show:nth-child(3)").val().trim() == "" || $("input.ext_con_3").val().trim() == "")
            alert("Please input a number");
        else
        {
            if ($("#con_select").find("p").length==0 && $(".ext_con_1").val() != "none") {
                alert("第一个表达式与上一个条件的关系为 none");
            }
            else {
                var con_str = $(".con_show:nth-child(1)").children("option:selected").val() + " ";
                con_str += $(".con_show:nth-child(2)").children("option:selected").val() + " ";
                con_str += $(".con_show:nth-child(3)").val().trim() + " ";
                con_str += $(".ext_con_1").val().trim() + " ";
                con_str += $(".ext_con_3").val().trim() + " ";
                con_str += "(" + guid() + ")";
                $("#con_select").append("<p >" + con_str + "</p>");
            }
        }
    });
    $("#con_clear").click(function () {
        $("#con_select").empty();
    });
    var edit_flag = false;
    $("#policy_add").click(function () {
        var service_name = $(".SP_up .select_item").children("option:selected").val();
        if (!service_name || typeof (service_name) == "undefined")
            return;
        $("#SP_close_show").click();
        if (edit_flag == false)
            $("#policy_name_list").prepend("<Option value='empty'></Option>").get(0).selectedIndex = 0;
        edit_flag = false;

        $("#policy_id").val(guid);
        $("#policy_priority").val(0);
        $(".SP_add_show").show();
        $("#apply_entity").empty();
        entity_show();
        $(".list_nodes").empty();
        $(".con_show:nth-child(3)").val(); $("#con_select").empty();
        table_show();
        $("#policy_name").attr("disabled", false);
        $("#policy_name").blur();

    });

    $("#policy_delete").click(function () {
        var $dele_obj = $("#policy_name_list option:selected");
	    var sfc_flag = 0;
		var apply_entity_name=get_apply_entity();
		var node_sd=get_flow_sd_del();
		sfc_flag = get_sfc_flag();
		if(sfc_flag == 1)
		{
		    var service_name = $(".SP_up .select_item").children("option:selected").val();
			var policy_name = $("#policy_name_list option:selected").text();
			//alert($("#policy_name_list option:selected").text());
			console.log(service_name+"   "+policy_name);
			if (!service_name || typeof (service_name) == "undefined") return;
			if (!policy_name || typeof (policy_name) == "undefined") return;
			if (policy_name == 'empty') return;
			var policy = service_name + "__policy_" + policy_name;
			console.log(policy);
			var json_str = localStorage.getItem(policy);
			console.log(json_str);
			if (!json_str || typeof (json_str) == "undefined")
				return;
			var json = jQuery.parseJSON(json_str);
			var flow_name = json[policy]["apply_entity_id"].split(":")[1];
			console.log("flow: "+flow_name);
			//alert(flow_name);
			for(var i=0; i<$("#service_svg path").length;i++)
			{
				if($("#service_svg path:eq("+i+")").attr("type") != "flow")
					continue;
				if($("#service_svg path:eq("+i+")").attr("id").indexOf(flow_name) > -1)
				{
					var src_group = $("#service_svg path:eq("+i+")").attr("node_start");
					var dest_group = $("#service_svg path:eq("+i+")").attr("node_end");
					var flow_count = $("#service_svg path:eq("+i+")").attr("count");
					var flow_id = $("#service_svg path:eq("+i+")").attr("id").split("_")[0];
					var flow_color = $("#service_svg path:eq("+i+")").attr("stroke");
					lead_policy(src_group,dest_group,flow_count,flow_color,flow_id,"none",src_group,dest_group);		
					break;
				}
			}
			$("#"+flow_name+"_1").remove();
			$("#"+flow_name+"_2").remove();
		var sevice_name = $("#sel_1").val();
        if(!sevice_name) return;
		var svg_str = $("#service_svg").html();
		localStorage.setItem(sevice_name+"_svg",svg_str);
		}
        var sercice_name = $(".SP_up .select_item").children("option:selected").val();
        var policy = $(".SP_up #policy_name_list").children("option:selected").val();
        if (!sercice_name || typeof (sercice_name) == "undefined") return;
        if (!policy || typeof (policy) == "undefined") return;
        if (policy == 'empty') return;
     
        var $tmp_policy_obj=$(".SP_up #policy_name_list").children("option:selected");
        $(".SP_up #policy_name_list").children("option:selected").remove();
        setpolicy_list();
        setpolicy_list_();
        var $op_obj;
        $("#nemo_str_show p").each(function () {
            if ($(this).text().indexOf("Operation") >= 0 && $(this).text().indexOf(policy + " ") >= 0) {
                $op_obj = $(this);
                return false;
            }
        });
        console.log($op_obj.html());
        if ($tmp_policy_obj.hasClass("grey_background")) {
            var nemoSet=$op_obj.html().split(" ");
            console.log(nemoSet);
            $("#nemo_str_show").append("<p><span class='key'>DELETE Operation </span>" + $dele_obj.val() + "</p>");

            var str = localStorage.getItem(sercice_name + "__policy_" + policy);
            localStorage.setItem(sercice_name + "__delete_policy_" + policy,str);
            console.log(sercice_name + "__delete_policy_" + policy+" : "+str);
        }
        else {
            $op_obj.remove();
        }
        localStorage.removeItem(sercice_name + "__policy_" + policy);
        setpolicy_nemo_str();
		
		
		//delete topo
		

		
        $("#SP_close_show").click();
    });

    $("#policy_edit").click(function () {
        var sercice_name = $(".SP_up .select_item").children("option:selected").val();
        var policy = $(".SP_up #policy_name_list").children("option:selected").val();
        if (!sercice_name || typeof (sercice_name) == "undefined") return;
        if (!policy || typeof (policy) == "undefined") return;
        if (policy == 'empty') return;
        var service_name = $(".SP_up .select_item").children("option:selected").val();
        var policy_name = $(".SP_up #policy_name_list").children("option:selected").val();
        //clear before
        $("#con_select").empty(); $("con_show:nth-child(3)").val();
        console.log("<edit>policy_name:" + policy_name);
        edit_flag = true;//add event use this flag
        $("#policy_add").click();
        edit_flag = true;//save event use this flag
        console.log("<edit>policy_name:" + policy_name);
        var policy = service_name + "__policy_" + policy_name;
        var json_str = localStorage.getItem(service_name + "__policy_" + policy_name);
        if (!json_str || typeof (json_str) == "undefined") return;
        console.log("json_str:" + json_str);
        $("#policy_name").attr("disabled", true);

        var json = jQuery.parseJSON(json_str);
        $("#policy_id").val(json[policy]["policy_id"]);
        $("#policy_name").val(json[policy]["policy_name"]);
        $("#policy_priority").val(json[policy]["policy_priority"]);
        $("#policy_name").blur();
        $("#apply_entity option[value='" + json[policy]["apply_entity_id"] + "']").attr("selected", true);
        //$("#StartTime").val(json[policy]["condition"]["start_time"].replace("-", ":"));
        //$("#EndTime").val(json[policy]["condition"]["end_time"].replace("-", ":"));
        if (json[policy]["condition"].length > 0)
        {
            for (d in json[policy]["condition"])
            {
                $("#con_select").append("<p>" + json[policy]["condition"] [d]+ "</p>");
            }
        }
        $("#policy_action option[value='" + json[policy]["action"] + "']").attr("selected", true);
        if(json[policy]["action"] =="go-through" )
            $("#policy_action").get(0).selectedIndex=2;
        $("#policy_action").change();
        if (json[policy]["data"] == "chain") {
            if (typeof (json[policy]["chain_node_list"]) != "undefined") {
                if (json[policy]["chain_node_list"].length > 0) {
                    No = 1;
                    for (var d in json[policy]["chain_node_list"]) {
                        $("#table_tab tr:last").find("td").eq(0).text(No);
                        $("#table_tab tr:last").find("td").eq(1).text(json[policy]["chain_node_list"][d]);
                        $("#table_tab").append("<tr><td></td><td></td></tr>");

                        $("#num").prepend("<Option value='" + No + "'>" + No + "</Option>");
                        $("#num").get(0).selectedIndex = 0;
                        No++;
                    }
                }
            }
        }
		
		if (json[policy]["data"] == "firewall") {
            if (typeof (json[policy]["firewall_list"]) != "undefined") {
                if (json[policy]["firewall_list"].length > 0) {
                    FNo = 1;
                    for (var d in json[policy]["firewall_list"]) {
                                                                 
                        FNo++;
                    }
                }
            }
        }

        $(".list_nodes").children("input").attr("selected", false);
        if (json[policy]["constraint"]["exclusive_node"].length > 0)
            for (var d in json[policy]["constraint"]["exclusive_node"]) {
                $(".list_nodes table").find("tr").each(function () {
                    //alert($(this).html());
                    if ($(this).children('td').eq(1).text() == json[policy]["constraint"]["exclusive_node"][d]) {
                        $(this).children('td').eq(0).children('input').attr("checked", true);
                    }
                });
            }
            //$("#policy_data").empty();
            //$("#policy_data").append("<option value='"+json[policy]["data"]+"'>"+json[policy]["data"]+"</option>");
    });

    var policy_name_match_flag = false;
    //policy_name
    $("#policy_name").blur(function () {
        var policy_name = $("#policy_name").val().trim();
        var match_null = /^[\s]*$/;
        if (policy_name == "") {
            policy_name_match_flag = false;
            $("#policy_name_icon")[0].src = "src/app/nemo/images/alert.png";
        }
        else {
            policy_name_match_flag = true;
            $("#policy_name_icon")[0].src = "src/app/nemo/images/ok.png";
        }
    });
    //save event
    $("#sp_save").click(function () {
        //validate
        if (!policy_name_match_flag) {
            return;
        }
        //validate name
        service_name = $(".SP_up .select_item").children("option:selected").val();
        if (!service_name || typeof (service_name) == "undefined") return;
        var policy_list = localStorage.getItem(service_name + "_policy_list");
        policy_name = $("#policy_name").val().trim();
        if (policy_list && typeof (policy_list) != "undefined") {
            policy = policy_list.split(",");
            var validate = 0;
            if (edit_flag == false) {
                for (var v = 0; v < policy_list.length; v++) {
                    if (policy[v] == policy_name) {
                        $("#policy_dialog").dialog({
                            height: 240,
                            width: 400,
                            modal: true,
                            open: function (event, ui) { $(".ui-dialog-titlebar-close").hide(); },
                            buttons: {
                                "ok": function () { $(this).dialog("close"); }
                            }
                        });
                        validate = 1;
                        return false;
                    }
                }
            }
        }
        if (validate == 1)
            return;
		//policy_chain_save
		
		
		
		 if($("#policy_action option:selected").val() == "go-through")
		{
			var flow_name = $("#apply_entity option:selected").val().split(":")[1];
			var chain_name = $("#policy_data option:selected").val();
			var node_start = $("#"+flow_name).attr("node_start");
			var node_end = $("#"+flow_name).attr("node_end");
			var flow_count_cur = parseInt($("#"+flow_name).attr("count"));
			var flow_color = $("#"+flow_name).attr("stroke");
			if(edit_flag == true)
			{
				node_start = $("#"+flow_name+"_1").attr("node_start");
				node_end = $("#"+flow_name+"_1").attr("node_end");
				flow_count_cur = parseInt($("#"+flow_name+"_1").attr("count"));
				flow_color = $("#"+flow_name+"_1").attr("stroke");
			}
			var c1_flag = 0;
			var c2_flag = 0;
			for(var i=0; i<$("#service_svg path").length;i++)
			{
				if($("#service_svg path:eq("+i+")").attr("type") == "connection")
				{
					var node_name_old_1 = $("#service_svg path:eq("+i+")").attr("node_start");
					var node_name_old_2 = $("#service_svg path:eq("+i+")").attr("node_end");
					console.log("old:"+node_name_old_1+"  "+node_name_old_2);
					if(((node_start == node_name_old_1)&&(chain_name == node_name_old_2))||((node_start == node_name_old_2)&&(chain_name == node_name_old_1)))
					{
						c1_flag = 1;
					}		
				}	
			}
			for(var i=0; i<$("#service_svg path").length;i++)
			{
				if($("#service_svg path:eq("+i+")").attr("type") == "connection")
				{
					var node_name_old_1 = $("#service_svg path:eq("+i+")").attr("node_start");
					var node_name_old_2 = $("#service_svg path:eq("+i+")").attr("node_end");
					console.log("old:"+node_name_old_1+"  "+node_name_old_2);
					if(((node_end == node_name_old_1)&&(chain_name == node_name_old_2))||((node_end == node_name_old_2)&&(chain_name == node_name_old_1)))
					{
						c2_flag = 1;
					}		
				}	
			}
			if(c1_flag == 0 || c2_flag == 0)
			{
					alert("No connection!");
					return;
			}
			var op_due = $("#service_svg path").length;
			if(edit_flag == true)
			{
				$("#service_svg path[id^="+flow_name+"]").each(function(){
					$(this).remove();
				});
				/* for(var delete_op = 0;delete_op<op_due;delete_op++)
				{
					if($("#service_svg path:eq("+delete_op+")").attr("type") != "flow")
						continue;				
					if($("#service_svg path:eq("+delete_op+")").attr("id").indexOf(flow_name) > -1)
						$("#service_svg path:eq("+delete_op+")").remove();	
				} */
			}
			
			lead_policy(node_start,chain_name,flow_count_cur,flow_color,flow_name+"_1",chain_name,node_start,node_end)
			lead_policy(chain_name,node_end,flow_count_cur,flow_color,flow_name+"_2",chain_name,node_start,node_end)
			$("#"+flow_name).remove();		
		}				
		
		
		
		var flow_flag = 0;
		flow_flag = get_flow_flag();
        var nemo_str = "";
        //create json string  and nemo string
        nemo_str += "<span class='key'>CREATE Operation </span>"
        //nemo_str += $("#policy_id").val();
        //nemo_str += "<span class='key'> Name </span>";
        nemo_str += $("#policy_name").val();
        nemo_str += "<span class='key'> Target </span>";
		var entity_name_t=$("#apply_entity option:selected").val()
        nemo_str += $("#apply_entity option:selected").val().split(":")[1];
        //if ($("#StartTime").val() != "" || $("#EndTime").val() != "") {
        //    nemo_str += "<span class='key'> Condition</span>[";
        //    if ($("#StartTime").val() != "")
        //        nemo_str += "StartTime " + $("#StartTime").val();
        //    else
        //        nemo_str += "StartTime 00:00";
        //    if ($("#EndTime").val() != "")
        //        nemo_str += ",EndTime:" + $("#EndTime").val() + "]";
        //    else
        //        nemo_str += ",EndTime 23:59]";
        //}
        if ($("#con_select").children().length > 0)
        {
            nemo_str += "<span class='key'> Condition[</span>";
            $("#con_select").children().each(function () {
                nemo_str += $(this).html()+" , ";
            });
            nemo_str=nemo_str.substring(0,nemo_str.length-2)
            nemo_str += "]";
        }
        nemo_str += "<span class='key'> Action </span>";
        nemo_str += $("#policy_action option:selected").val();

        var sercice_name = $(".SP_up .select_item").children("option:selected").val();
        json_str = '{"';
        json_str += sercice_name + '__policy_' + $("#policy_name").val().trim() + '":{';
        json_str += '"policy_id":"' + $("#policy_id").val() + '",';
        json_str += '"policy_name":"' + $("#policy_name").val().trim() + '",';
        json_str += '"policy_priority":"' + $("#policy_priority").val().trim() + '",';
        json_str += '"apply_entity_id":"' + $("#apply_entity").children("option:selected").val() + '",';
        json_str += '"condition":[';
        if ($("#con_select").children().length > 0) {
            $("#con_select").children().each(function () {
                json_str += '"'+$(this).html() + '", ';
            });
            json_str = json_str.substring(0, json_str.length - 2)
        }
        //json_str += '"start_time":"' + $("#StartTime").val().trim().replace(":", "-") + '",';
        //json_str += '"end_time":"' + $("#EndTime").val().trim().replace(":", "-") + '"';
        json_str += "],";
        json_str += '"action":"' + $("#policy_action").children("option:selected").val() + '",';
        json_str += '"data":"' + $("#policy_data").children("option:selected").val() + '",';

        if ($("#policy_data").children("option:selected").val() == "chain") {
            if (chain_node_list.length > 0) {
                json_str += '"chain_node_list":[';
                var chain_str = "";
                for (var l = 0; l < chain_node_list.length; l++) {
                    chain_str += '"' + chain_node_list[l] + '",';
                }
                chain_str = chain_str.substring(0, chain_str.length - 1);
                json_str += chain_str + '],';

                nemo_str += "<span class='key'> Chain</span>[" + chain_node_list + "] ";
            }

        }
				
		if ($("#policy_data").children("option:selected").val() == "firewall") {
            if (firewall_list.length > 0) {
                json_str += '"firewall_list":[';
                var chain_str = "";
                for (var l = 0; l < firewall_list.length; l++) {
                    chain_str += '"' + firewall_list[l] + '",';
                }
                chain_str = chain_str.substring(0, chain_str.length - 1);
                json_str += chain_str + '],';
                nemo_str += "<span class='key'> Firewall</span>[" + firewall_list + "] ";
            }

        }
				
        json_str += '"constraint":{';

        var data = [];
        $(".list_nodes table").find(":checkbox:checked").each(function () {
            var val = $(this).parent().next().text();
            data.push(val);
        });
        json_str += '"exclusive_node":';
        json_str += '[';
        var node_list = "";
        for (var k = 0; k < data.length; k++) {
            node_list += '"' + data[k] + '",';
        }
        node_list = node_list.substring(0, node_list.length - 1);
        json_str += node_list;
        json_str += ']';
        json_str += '}';
        json_str += '}';
        json_str += '}';

        //nemo string continue
        if (node_list != "") {
            nemo_str += "<span class='key'> Constraint</span>[";
            nemo_str += node_list.replace(new RegExp(/(")/g), "");
            nemo_str += "]";
        }
        if($("#policy_data option:selected").val() !=null && typeof($("#policy_data option:selected").val())!="undefined")
        nemo_str += ": "+$("#policy_data option:selected").val();

        if (edit_flag == true) {
            var $op_ob;
            $("#nemo_str_show p").each(function () {
                if ($(this).text().indexOf("Operation") >= 0 && $(this).text().indexOf($("#policy_name").val() + " ") >= 0)
                {
                    $op_ob=$(this);
                    return false;
                }
            });
            if ($("#policy_name_list option:selected").hasClass("grey_background")) {
                $("#nemo_str_show").append("<p>" + nemo_str.replace("CREATE","UPDATE") + "</p>");              
            }
            else {
                $op_ob.html(nemo_str);
            }
           
        }
        else
            $("#nemo_str_show").append("<p>" + nemo_str + "</p>");

        localStorage.setItem(sercice_name + "_nemo_str", nemo_str);
        if (edit_flag == true){
            var $edit_obj=$("#policy_name_list option:selected");
            $("#policy_name_list").prepend($edit_obj);
            //$("#policy_name_list option:selected").remove();
        }
        else{
        $("#policy_name_list").prepend("<Option value='" + $("#policy_name").val().trim() + "'>" + $("#policy_name").val().trim() + "</Option>");
        }
        $("#policy_name_list").get(0).selectedIndex = 0;

        localStorage.setItem(sercice_name + "__policy_" + $("#policy_name").val().trim(), json_str);
        //console.log(":"+localStorage.getItem(sercice_name + "__policy_" + $("#policy_name").val().trim()));

        setpolicy_nemo_str();
        $("#SP_close_show").click();
        setpolicy_list();
        setpolicy_list_();
        console.log(json_str);
		var sevice_name = $("#sel_1").val();
        if(!sevice_name) return;
		var svg_str = $("#service_svg").html();
		localStorage.setItem(sevice_name+"_svg",svg_str);
		
		
        edit_flag = false;
    });
    
    var chain_node_list = new Array();   
   
	 var firewall_list = new Array();

    
    //change event
    $(".SP_up .select_item").change(function () {
        getpolicy_list();
    });
    $("#policy_action").change(function () {
       console.log('change');
     if ($(this).get(0).selectedIndex == 2 ) {
            //$("#policy_data").get(0).selectedIndex = 1;
            getChainSet();
            $("#policy_data").attr("disabled", false);
        }
        else {
            $("#policy_data").empty();
            //$("#policy_data").attr("disabled", true);
        }
    });

});
