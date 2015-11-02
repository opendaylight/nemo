
jQuery(document).ready(function ($) {
    $("#ser_init,#SI_Save,#SI_Delete,#node_save,#connection_save,#flow_save,#NE_Delete,#sp_save,#policy_delete").click(function () {
        $("#tabs ul li:nth-child(1) a").trigger("click");
        var nemo_show = $("#nemo_str_show").get(0);
        nemo_show.scrollTop = nemo_show.scrollHeight;
    });
    
});