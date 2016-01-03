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
    
    jQuery('a[href="#/nemo"]').click(function(){
        jQuery("#page_logo").attr("src","src/app/nemo/images/logo_nemo.png").attr("ng-src","src/app/nemo/images/logo_nemo.png");
    })
    jQuery('a[href="#/nemo"]').click();
});


