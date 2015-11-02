function guid() {
    function S4() {
        return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
    }
    return (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4());
}
jQuery(document).ready(function ($) {
    //init service Info
    function init_serviceMsg(){
    var user_log_info = localStorage.getItem("useinfo");
    if(!user_log_info) return;
    var user_name = user_log_info.split(",")[1];
    user_name =user_name+"_service1";
    $(".select_btn .select_item").empty();
    $(".select_btn .select_item").append("<option value='"+user_name+"'>"+user_name+"</option>");
    $("#SI_Add,#SI_Edit,#SI_Delete").attr("disabled","true");
    localStorage.setItem("service_names",user_name);
    
    var local_nemo=localStorage.getItem(user_name+"_nemo_str");
    if(local_nemo !=null && typeof(local_nemo) !="undefined"){
         var $e = $("<div>"+local_nemo+"</div>");
         $e.find("p").each(function(){
         $(this).removeClass('grey_background');
         $(this).find("span").addClass('key');
        });
          //localStorage.setItem(user_name+"_nemo_str", $e.html());
    }
   
    var local_entity_list =  localStorage.getItem(user_name+"_entity_instance_list_");
    if(local_entity_list !=null && typeof(local_entity_list) !="undefined"){
        //console.log(local_entity_list);
    var $e = $("<div>"+local_entity_list+"</div>>");
    $e.find("option").removeClass('grey_background');
    //localStorage.setItem(user_name+"_entity_instance_list_",$e.html())
    console.log($e.html());
}
    var local_policy_list =  localStorage.getItem(user_name+"_policy_list_");
    if(local_policy_list !=null && typeof(local_policy_list) !="undefined"){
        //console.log(local_policy_list);
    var $e = $("<div>"+local_policy_list+"</div>>");
    $e.find("option").removeClass('grey_background');
    //localStorage.setItem(user_name+"_policy_list_",$e.html())
    console.log($e.html());
}

}

    var service_name;
    //get nodes
    function get_entity_nodes() {
        service_name = $(".Si_group .select_item option:selected").val();
        if (!service_name || typeof (service_name) == "undefined") return null;
        var service_entities = localStorage.getItem(service_name + "_entity_instance_list");
       // console.log(service_entities);
        var nodes = new Array();
        if (!service_entities || typeof (service_entities) == "undefined") return nodes;
        var entities = service_entities.split(",");
        var count = 0;
        for (var i = 0; i < entities.length; i++) {
            var entity = service_name + "__" + entities[i].replace(":", "_");
            //console.log(entity);
            var json_str = localStorage.getItem(entity);
            var json = jQuery.parseJSON(json_str);
            //console.log(json_str);
            if (json[entity]["Entity_Type"] != "node")
                continue;
            // var entity_name = entity.split(":");        
            //console.log(json[entity]["node_name"]); 
            var str = "id:" + json[entity]["node_id"] + "<br/>";
            str += "name:" + json[entity]["node_name"] + "<br/>";
            if (json[entity]["node_type"] == "host") {
                str += "property:<br/>"
                str += "ip_address:" + json[entity]["property"]["ip_address"]+"<br/>";
                str += "location:" + json[entity]["property"]["location"]+"<br/>";
                str += "mac_address:" + json[entity]["property"]["mac_address"]+"<br/>";
            }
            else if (json[entity]["node_type"] == "firewall") {
                str += "property:<br/>"
                str += "location:" + json[entity]["property"]["location"] + "<br/>";
                str += "working_mode:"+json[entity]["property"]["working_mode"] + "<br/>";
            }
            else if (json[entity]["node_type"] == "forwarding") {
                str += "property:<br/>"
                str += "ipprefix:"+json[entity]["property"]["ipprefix"] + "<br/>";
            }
            else if (json[entity]["node_type"] == "internet") {
                str += "property:<br/>"
                str += "location:" + json[entity]["property"]["location"] + "<br/>";
                str += "ipprefix:" + json[entity]["property"]["ipprefix"] + "<br/>";
            }
            else {
                str += "property:<br/>"
                str += "location:" + json[entity]["property"]["location"] + "<br/>";
            }
            nodes[count] = [json[entity]["node_name"], json[entity]["node_type"],str];
            count++;
        }
        return nodes;
    }
    //get connection
    function get_entity_connections() {
        service_name = $(".Si_group .select_item option:selected").val();
        if (!service_name || typeof (service_name) == "undefined") return null;
        var service_entities = localStorage.getItem(service_name + "_entity_instance_list");
        // console.log(service_entities);
        var connections = new Array();
        if (!service_entities || typeof (service_entities) == "undefined") return null;
        var entities = service_entities.split(",");
        var count = 0;
        for (var i = 0; i < entities.length; i++) {
            var entity = service_name + "__" + entities[i].replace(":", "_");
            //console.log(entity);
            var json_str = localStorage.getItem(entity);
            var json = jQuery.parseJSON(json_str);
            //console.log(json_str);
            if (json[entity]["Entity_Type"] != "connection")
                continue;     
            if (json[entity]["connection_type"] == "p2p") {
                connections[count] = [json[entity]["connection_name"], json[entity]["End-nodes"]["one_node_name"], json[entity]["End-nodes"]["other_node_name"]];
            }
            else if (json[entity]["connection_type"] == "p2mp") {
                connections[count] = [json[entity]["connection_name"], json[entity]["End-nodes"]["one_node_name"], json[entity]["End-nodes"]["other_node_name"]];
            }
           
            count++;
        }
        return connections;
    }
    //get policy chain
    function get_policy_chain() {
        service_name = $(".Si_group .select_item option:selected").val();
        if (!service_name || typeof (service_name) == "undefined") return null;
        var policy_list = localStorage.getItem(service_name + "_policy_list");
        if (!policy_list || typeof (policy_list) == "undefined") return null;
        policy = policy_list.split(",");
        var data = [];
        for (var i = 0; i < policy.length; i++) {
            var j = service_name + "__policy_" + policy[i];
            var json_str = localStorage.getItem(j);
            if (!json_str) continue;
            var json = jQuery.parseJSON(json_str);
           
            var entity_name=json[j]["apply_entity_id"];
            if (entity_name.indexOf("flow") >= 0) {
                var js = service_name + "__" + entity_name.replace(":", "_");
                var flow_info = localStorage.getItem(js);
                var jsonflow = jQuery.parseJSON(flow_info);
                var nodes = [];
                nodes.push(jsonflow[js]["match_items"]["src_node"]);
               
                if (json[j]["data"] == "chain") {
                    if (typeof (json[j]["chain_node_list"]) != "undefined") {
                        for (d in json[j]["chain_node_list"])
                            nodes.push(json[j]["chain_node_list"][d]);
                    }
                    nodes.push(jsonflow[js]["match_items"]["dest_node"]);
                    data.push(nodes);
                }
                else {

                    continue;
                }
            }

        }
        return data;

    }
    //clear localStorage
    function clear_storage() {
        var ser_name = "";
        ser_name = $(".Si_group .select_item option:selected").val();

        if (ser_name == "" || typeof (ser_name) == "undefined") {
            //localStorage.clear();
        }
    }
    function setSevice_names() {
        current_service_names = "";
        $(".Si_group .select_item option").each(function () {
            current_service_names += $(this).text().trim() + ",";
        });
        //console.log(current_service_names);
        current_service_names = current_service_names.substring(0, current_service_names.length - 1);
        //console.log(current_service_names);
        localStorage.setItem("service_names", current_service_names);
        //alert(localStorage.getItem("service_names"));
    }
    function getSevice_names() {
        var get_service_names = localStorage.getItem("service_names");
        console.log(get_service_names);
        if (!get_service_names) return;
        $(".Si_group .select_item").empty();
        if (get_service_names) {       
            var svc_name = get_service_names.split(",");
            for (var j = 0; j < svc_name.length; j++) {
                console.log(svc_name[j]);
                if (svc_name[j] != "")
                    $(".Si_group .select_item").append("<Option value='" + svc_name[j] + "'>" + svc_name[j] + "</Option>");
            }
        }
    }
    function getnemo_str() {
        $("#nemo_str_show").empty();
        var service_name = "";
        service_name = $(".select_item").children("option:selected").val();
        if (!service_name || typeof (service_name) == "undefined")
            return;
        var str = localStorage.getItem(service_name + "_nemo_str");
        //if (!str || typeof (str) == "undefined")
        //    return;
        //str = str.split("|");

        //for (s = 0; s < str.length; s++) {
        //    $("#nemo_str_show").append("<p class='key'>" + str[s] + "</p>");
        //}
        console.log("get_nemo:" + str);
        $("#nemo_str_show").append(str);
    }
	
	function clear_topo() {
	    nodes.clear();
		edges.clear();
		Node_Id=0;
		Edge_Id=0;
	    var data = {
			nodes: nodes,
			edges: edges
		};
		container = document.getElementById('graph');
		graph = new vis.Graph(container, data, options);
	}

    //init
	$("#ser_init").click(function () {
	    $("#tabs ul li").eq(0).children().trigger("click");
        getSevice_names();
        clear_storage();
        //$("#ser_inst_show").show();
        init_serviceMsg();
        $(".Si_group .select_item").change();
        getnemo_str();
        $("#Add_show").hide();
        //var nodes = get_entity_nodes();
        //var connections = get_entity_connections();
        //console.log("<SI>nodes:" + nodes);
        //console.log("<SI>connections:" + connections);
        //var test = get_policy_chain();
        //console.log(test);

    });

    var flag = true;
    $("#SI_Add").click(function () {
        flag = true;
        $("#Add_show").hide();
        $("#Add_show .text_item").val("");

        $("#Add_show").show();
        //alert($(".Si_group .select_item option:first").val());
        if ($(".Si_group .select_item option:first").val() != "empty") {
            $(".Si_group .select_item").prepend("<option value='empty'></option>");
            $(".Si_group .select_item").get(0).selectedIndex = 0;
        }
        $(".Si_group .select_item").get(0).selectedIndex = 0;
        $(".Si_group .name_text").attr("disabled", false);
    });


    var edit_name = "";
    $("#SI_Edit").click(function () {
        edit_name = $(".Si_group .select_item option:selected").text().trim();
        if (edit_name == "")
            return;
        //$("#SI_Delete").click();
        $("#Add_show .name_text").val(edit_name);
        //alert(localStorage.getItem(edit_name));
        $("#Add_show .desc_text").val(localStorage.getItem(edit_name));
        $("#Add_show").show();
        flag = false;
        $(".Si_group .name_text").attr("disabled", true);
    });
    $("#SI_Delete").click(function () {
        var service_name = $(".Si_group .select_item").children("option:selected").text();
        if (!service_name || typeof (service_name) == "undefined")
            return;
        $("#del_ser_dialog").dialog({
            height: 240,
            width: 400,
            modal: true,
            open: function (event, ui) { $(".ui-dialog-titlebar-close").hide(); },
            buttons: {
                "ok": function () {
                    //remove service 
                    localStorage.removeItem($(".Si_group .select_item option:selected").val());
                    //remove service entity
                    var sevice_name = $(".Si_group .select_item").val();
                    //$("#select_item option:selected").remove();
                    localStorage.removeItem(sevice_name + "_entity_instance_list");
                    localStorage.removeItem(sevice_name + "_policy_list");
                    localStorage.removeItem(sevice_name + "_entity_instance_list_");
                    localStorage.removeItem(sevice_name + "_policy_list_");
                    localStorage.removeItem(service_name + "_nemo_str");
                    localStorage.removeItem(service_name + "_svg");
                    localStorage.removeItem(sevice_name + "_lgroup_aviable_node");
                    localStorage.removeItem(sevice_name + "_chain_aviable_node");
                    //update service list
                    //localStorage.clear();
                    $(".Si_group .select_item option:selected ").remove();
                    $("#Add_show .text_item").val("");
                    $("#Add_show").hide();
                    clear_storage();
                    //reload page
                    // location.reload();
                    getnemo_str();
                    setSevice_names();
                    $(".Si_group .select_item").change();
					
					//clear topo
					clear_topo();
					ne_flag=0;
                    Node_List = new Array();
                    Edge_List = new Array();
	
	                node_count=0;
	                node_X=[];
                    conn_Item=[];

	                nodes.clear(); edges.clear();
                    Node_Id = 0;
                    Edge_Id = 0;	
	                Edge_Count=0;

	
	                Node_Sp=[];
	                Node_Sp_Id=[];
	                Node_Sc=[];
	                Node_Sc_Id=[];
	                policy_count=[];
	                policy_set=[];
                    chain_count=[];
	                chain_set=[];
	                conn_set=[];
						  nodes.add({
                        id: 100000,
						group: 'null',
						x:700,
						y:400
                    });
                    $(this).dialog("close");
                },
                "cancle": function () {
                    $(this).dialog("close");
                }
            }
        });


    });
    $(".close_group").click(function () {
        $("#Add_show").hide();
        $(".Si_group .select_item option[value=empty]").remove();
        $(".Si_group .name_text").attr("disabled", false);
        flag = true;
    });
    //test
    //localStorage.setItem('data', "text");
    //alert(localStorage.getItem('data'));

    $("#SI_Save").click(function () {

        var name = $("#Add_show .name_text").val().trim();
        var f = 0;
        $(".Si_group .select_item option").each(function () {
            if ($(this).val() == name) {
                if (flag == true) {
                    $("#dialog").dialog({
                        height: 240,
                        width: 400,
                        modal: true,
                        open: function (event, ui) { $(".ui-dialog-titlebar-close").hide(); },
                        buttons: {
                            "确定": function () { $(this).dialog("close"); }
                        }
                    });
                    //alert(name + " is already existing,please input another name!");
                    f = 1;
                    return false;
                }
            }
        });
        if (f == 1) {
            return;
        }
        if (flag == false) {

            $(".Si_group .select_item option[value=" + edit_name + "]").remove();
            localStorage.removeItem(edit_name);
            flag = true;
        }
        if (name == "") {

            $("#ser_dialoge").dialog({
                height: 200,
                width: 350,
                modal: true,
                open: function (event, ui) { $(".ui-dialog-titlebar-close").hide(); },
                buttons: {
                    "确定": function () { $(this).dialog("close"); }
                }
            });
        }
        else {
            $(".Si_group .select_item option[value=empty]").remove();
            $(".Si_group .select_item").prepend("<option value='" + name + "'>" + name + "</option>");
            $(".Si_group .select_item").get(0).selectedIndex = 0;

            setSevice_names();

            localStorage.setItem(name, $(".desc_text").val());
            // alert(localStorage.getItem(name));
            $("#Add_show").hide();
            $("#Add_show .text_item").val("");
            $(".Si_group .select_item").change();
        }

    });
    //change event
    $(".Si_group .select_item").change(function () {
        service_select = $(this).val();
    });
    //trigger service instance click event, and get data
    $("#ser_init").click(); 
    //hide service instance info
    $("#ser_init").click(); 

});
