/// <reference path="_references.js" />
var phy_hosts=[];

jQuery.noConflict();
jQuery(document).ready(function ($) {
     //localStorage.clear();
    $("#net_ent_init,#ser_poc_init,#advance_query").hide();
   
    $(".btn_item").click(function () {
        index = $(".btn_item").index($(this));
        if (index == 0 || index == 5)
        {
            flag = true;
            $("#net_ent_init,#ser_poc_init,#advance_query").hide();
        }
        if ($(this).hasClass("btn_item_click") == false) {
            $(this).siblings("button").removeClass("btn_item_click").addClass("btn_item_none");
            $(this).removeClass("btn_item_none").addClass("btn_item_click");

            $(".btn_item_show").hide();
            $(".btn_item_show").eq(index).show();

        }
        else {
            $(this).removeClass("btn_item_click").addClass("btn_item_none");
            $(".btn_item_show").hide();
        }
		if(index == 5 )
		{
            document.getElementById("service_svg").style.display = "none";
			document.getElementById("service_svg2").style.display = "none";
            document.getElementById("graph").style.display = "";
		}
		else{
			jQuery("#service_svg2").empty();
            document.getElementById("service_svg").style.display = "";
		    document.getElementById("service_svg2").style.display = "none";
            document.getElementById("graph").style.display = "none";
		}
        //hide service instance
        $("#ser_init").hide();
    });
    $(".btn_item_show .close_item_show img").click(function () {
        $(".btn_item").removeClass("btn_item_click").addClass("btn_item_none");
        $(".btn_item_show").hide();
    });

   var flag=true;
    $("#nemo-business-model").click(function(){
     if(flag)
     {
        flag=false;
        $("#net_ent_init,#ser_poc_init,#advance_query").show();
        $(".btn_item_show").hide();
     }   
     else
      {
       flag=true;
       $("#net_ent_init,#ser_poc_init,#advance_query").hide();
       $(".btn_item_show").hide();
      }
      $("#advance_query").hide();//hide advance query
     });

    $("#nemo-business-model").show();
    $("#tabs").tabs();
    $("#show_status").hide();

    //get physical topo
    // $("#nemo-business-model").click(function(){
    // $("#query_topo").click();
    // });
});


