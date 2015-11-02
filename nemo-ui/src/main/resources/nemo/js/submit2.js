
jQuery(document).ready(function ($) {
	$("#submit").click(function(){
		 $("#show_status").show();
		 $('#loader').shCircleLoader();
        //show tran_info window 
		 $("#tabs ul li:nth-child(2) a").trigger("click");
	    //write tran_info string
		 $info = $("<p>当前事务正在运行中</p>");
	    $("#trans_info").append($info);
		 //var obj = document.getElementById("trans_info");
		 //obj.innerHTML = "<p>"+"事务"+"</p>";
		 //alert(obj.innerHTML);
        //scroll go down 
		 var nemo_show = $("#trans_info").get(0);
		 nemo_show.scrollTop = nemo_show.scrollHeight;

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

	});

});