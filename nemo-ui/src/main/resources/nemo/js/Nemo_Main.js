/// <reference path="_references.js" />
	function setCookie(name,value,Days) 
{ 
    if(!Days) Days = 3; 
    var exp = new Date(); 
    exp.setTime(exp.getTime() + Days*24*60*60*1000); 
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString()+';path=/'; 
} 
function getCookie(name) 
{ 
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
 
    if(arr=document.cookie.match(reg)) 
        return unescape(arr[2]); 
    else 
        return null; 
} 
jQuery.noConflict();
jQuery(document).ready(function ($) {
     //localStorage.clear();
     //show the tab
    $(".tabs").tabs();
    // $(".tabs ul li[aria-controls='intent_topo']").trigger('click');
});


