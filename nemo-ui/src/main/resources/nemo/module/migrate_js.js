function Import() 
{ 
    for( var i=0; i<arguments.length; i++ )
    { 
        var file = arguments[i]; 
            document.write('<script type="text/javascript" src="' + file + '"></sc' + 'ript>'); 
    } 
};
Import('lib/jquery-1.11.3.js','lib/jquery-ui.min.js','lib/vis.js');
// Import('src/app/nemo/module/test/servicechain_userJson.js');
//Import('src/app/nemo/module/test/bod_userJson.js');