var MappingResults='';

function getMappingResults(){
 	var MappingData=null;
		jQuery.ajax({
			url: "/restconf/config/intent-mapping-result:intent-vn-mapping-results/", 
			type:"GET",
			dataType:"json",
			async:false,
			success: function(data){
				if(data != null)
				{
					MappingData = data;
					MappingResults=data;
					console.log(MappingData);
				}
				else
					alert("No Mapping Data");
			},
			error:function(data){
			alert("Get Mapping Data Error!");
			}
		});
	return MappingData;
 }

 function getMappingDatas(){
 	var Datas=getMappingResults();
 	if(!Datas){
 	Datas=MappingJson;
 	}
 	MappingResults=Datas;
 	//return virtualDatas;
 }

getMappingResults();

 function getMappingResultById(user_id,Data){
    if(!Data) Data=MappingResults;
    if(!Data) return;
 	if(typeof(Data)=='string'){
    var user_json = JSON.parse(Data);
    }
    else
    {
    var user_json = Data;
    }
    console.log(user_json);
    var user_info = user_json['intent-vn-mapping-results']['user-intent-vn-mapping'];
 	for(var item in user_info){
 		if(user_id==user_info[item]['user-id']){
 			return user_info[item];
 		}
 	}
 }
