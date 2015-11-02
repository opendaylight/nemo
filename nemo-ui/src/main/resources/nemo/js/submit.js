var suce_count=0; //ajax get ok count 
var exec_count=0;//exec ajax count
var restful_exec_flag=0;

jQuery(document).ready(function($){

	function validate_success(length,callback_count,ok_count)
	{
		if (callback_count == (length+2)) 
		{
			if(ok_count == (length+2))
			{
				alert("提交成功");
				//intterval_id = setInterval("query_status_exec()",200);
			}
			else
			{
				alert("提交失败");
			}
		}
	}
	function validate_failure(length,callback_count,ok_count)
	{
		if (callback_count == (length+2)) 
		{
			alert("提交失败");
		}
	}

	jQuery.valid_exec=function() {
	console.log("suce_count:"+suce_count);
	console.log("exec_count:"+exec_count);
   if(suce_count==exec_count){
	exec_success();
	exec_after("Current transaction exec successfully");
    }
    else
    {
    	exec_after("Current transaction failed to exec,please try again later...");
    }
}

	function exec_before(){
       //$("#show_status").show();
	   //$('#loader').shCircleLoader();
        //show tran_info window 
		 $("#tabs ul li:nth-child(2) a").trigger("click");
	    //write tran_info string
		 $info = $("<p>Current transaction is running...</p>");
	    $("#trans_info").append($info);
        //scroll go down 
		 var nemo_show = $("#trans_info").get(0);
		 nemo_show.scrollTop = nemo_show.scrollHeight;
	}
    function exec_success()
	{
		 $("#nemo_str_show").find("p").each(function () {
		     $(this).addClass("grey_background").children().removeClass("key");
		 });
		 $("#sel_2,#policy_name_list").find("option").each(function () {
		     $(this).addClass("grey_background");

		     var sevice_name = "";
		     sevice_name = $("#sel_1").children('option:selected').val().trim();
		     if (sevice_name) {
		         current_entity_instance_list = "";
		         localStorage.setItem(sevice_name + "_entity_instance_list_", $(".NE_up #sel_2").html());
		         console.log($(".NE_up #sel_2").html());
		     }
		     sevice_name = $("#sel_1").children('option:selected').val().trim();
		     if (sevice_name) {
		         current_entity_instance_list = "";
		         localStorage.setItem(sevice_name + "_policy_list_", $("#policy_name_list").html());
		         console.log($("#policy_name_list").html());
		     }
		 });

		 var service_name = "";
	    //get service_name ,should use real name
		 service_name = $(".select_item").children("option:selected").val();

		 if (!service_name || typeof (service_name) == "undefined") return;
		 var str = localStorage.setItem(service_name + "_nemo_str", $("#nemo_str_show").html());
	}
   function exec_after(info){
	   restful_exec_flag = 0;
       //$("#show_status").hide();
        //show tran_info window 
		 $("#tabs ul li:nth-child(2) a").trigger("click");
	    //write tran_info string
		 $info = $("<p>"+info+"</p>");
	    $("#trans_info").append($info);
        //scroll go down 
		 var nemo_show = $("#trans_info").get(0);
		 nemo_show.scrollTop = nemo_show.scrollHeight;
		 suce_count=0; //ajax get ok count 
		 exec_count=0;//exec ajax count
	}

	$("#submit").click(function(){
		//just for test
    //exec_before();
    //exec_success();
    //exec_after("sucess!");
    //return;

	if(restful_exec_flag == 1)
		{
			alert("The last transaction has not been finished,Please Wait...");
			return 0;
		}
		else
		{
			exec_before();
			setTimeout('jQuery.valid_exec()', 6000);
			
			var ok_count = 0;
			var callback_count = 0;
			var service_name = $(".NE_up #sel_1").children('option:selected').val();
			create_json(service_name);
			var json_exec_temp = restful_json[service_name];
			var so_str = '{"input":{"user-id":"'+user_id+'","user-name":"'+user_name+'","user-password":"'+user_password+'","user-role":"'+user_role+'"}}';
			//var so_json = jQuery.parseJSON(so_str);
			console.log(so_str);
			//var so_str = {"input":{"user-id":user_id,"user-name":user_name,"user-password":user_password,"user-role":user_role}};
                        //console.log(so_str);
            exec_count++;
            restful_exec_flag = 1;
			$.ajax({ 
			    url: "/restconf/operations/nemo-intent:begin-transaction/", 
				type:"POST",
				//data:JSON.stringify(so_str),
				data:so_str,
				async: false,
				dataType:"json",
				contentType:"application/json",
				success: function(data){
					console.log(data);
					callback_count++;
					if(data["output"]["result-code"] == "ok")
					{
						//$("#show_status").show();
						//$('#loader').shCircleLoader();
						ok_count++;
						suce_count++;
			 for(var i=0;i<json_exec_temp.length;i++)
			    {
			    exec_count++;
				$.ajax({ 
			    url: "/restconf/operations/nemo-intent:structure-style-nemo-update", 
				type:"POST",
				async: false,
				data:json_exec_temp[i],
				dataType:"json",
				contentType:"application/json; charset=UTF-8",
				success: function(data){
					console.log(data);
					callback_count++;
					if(data["output"]["result-code"] == "ok")
					{
						ok_count++;
						suce_count++;
						console.log("update submit sucess!");
					}
					else
					{
						console.log("update submit fail!");
					}

					//validate_success(json_exec_temp.length,callback_count,ok_count);
				
				},
				error:function(data){
					console.log(data);
					callback_count++;
					//validate_failure(json_exec_temp.length,callback_count,ok_count);
				}
			  });
			}
					}
					else
					{			
						alert("begin failed");
					}
					//validate_success(json_exec_temp.length,callback_count,ok_count);
				},
				error:function(data){
					console.log(data);
					callback_count++;
					//validate_failure(json_exec_temp.length,callback_count,ok_count);
				},
			});

		    exec_count++;
			$.ajax({ 
			    url: "/restconf/operations/nemo-intent:end-transaction", 
				type:"POST",
				async: false,
				data:so_str,
				dataType:"json",
				contentType:"application/json; charset=UTF-8",
				success: function(data){
					console.log(data);
					callback_count++;
					if(data["output"]["result-code"] == "ok")
					{
						ok_count++;
						suce_count++;
					}
					//validate_success(json_exec_temp.length,callback_count,ok_count);
				},
				error:function(data){
					console.log(data);
					callback_count++;
					//validate_failure(json_exec_temp.length,callback_count,ok_count);
				}
			});
		}

       
	});

});
