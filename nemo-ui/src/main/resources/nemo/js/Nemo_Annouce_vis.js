   var ne_flag=0;
	// Node_List = a[Node_Id][x](x = 0,label;x = 1,title;x = 2,P2mpGroupId)
    var Node_List = new Array();
	// Edge_List = b[Edge_Id][y](y = 0,connection name;y = 1,from;y = 2,to)
    var Edge_List = new Array();
	
	var node_count=0;
	var node_X=[];

    var conn_Item=[];
	
	var vlandata1;


/////////////Main Data//////////////
	var nodes, edges, graph;
  var Node_Id = 0;
  var Edge_Id = 0;	
	var Edge_Count=0;

	
	var Node_Sp=[];
	var Node_Sp_Id=[];
	var Node_Sc=[];
	var Node_Sc_Id=[];
	var policy_count=[];
	var policy_set=[];
    var chain_count=[];
	var chain_set=[];
	var conn_set=[];
	

	//flow
	var flow_count = [];
	var ext_ip = [];			
	
    // create an array with nodes
    nodes = new vis.DataSet();
	
    // create an array with edges
    edges = new vis.DataSet();

/////////////P2mp Group Data//////////////
	var Group_Id = 0;
	var Group_List= new Array();
	
    var options = {
 		stabilize: true,
		//Manipulation:true,
		//hierarchicalLayout: true,
		nodes: {
          // default for all nodes
			shape: 'dot', 
			fontSize:16	,
			radius:16,
			fixed:true
		},
		edges: {
		smooth: false
			//length:1
		},
        groups: {
		    null: {
            color: {
              border: 'white',
              background: 'white',
              highlight: {
                border: 'white',
                background: '#white'
              }
            },
            fontSize: 0,
			radius:0,
            //fontFace: 'arial',
            shape: 'dot'
          },
          host: {
            color: {
              border: 'black',
              background: '#B0E2FF',
              highlight: {
                border: 'black',
                background: '#4F94CD'
              }
            },
            fontSize: 15,
			radius:15,
            fontFace: 'arial',
            shape: 'dot'
          },
          firewall: {	
            color: {
              border: 'black',
              background: '#FF0000',
              highlight: {
                border: 'black',
                background: '#FF7256'
              }
            },
            fontSize: 15,
			radius:15,
            fontFace: 'arial',
            shape: 'dot'
          },
		  internet: {
            color: {
              border: 'black',
              background: '#7FFF00',
              highlight: {
                border: 'black',
                background: '#6B8E23'
              }
            },
            fontSize: 15,
			radius:15,
            fontFace: 'arial',
            shape: 'dot'
          },
          forwarding: {
            color: {
              border: 'black',
              background: '#878787',
              highlight: {
                border: 'black',
                background: '#4A4A4A'
              }
            },
            fontSize: 15,
			radius:15,
            fontFace: 'arial',
            shape: 'dot'
          },
		   loadbalance: {
            color: {
              border: '#black',
              background: '#FF9933',
              highlight: {
                border: 'black',
                background: '#FDCEBF'
              }
            },
            fontSize: 15,
			radius:15,
            fontFace: 'arial',
            shape: 'dot'
          },
		   group: {
            color: {
              border: 'black',
              background: 'yellow',
              highlight: {
                border: 'black',
                background: '#ECE5D0'
              }
            },
            fontSize: 15,
			radius:15,
            fontFace: 'arial',
            shape: 'dot'
          }
        }
		
	};
	
	

	// used in Service_Policy
var options2 = {
 		stabilize: false,
		//Manipulation:true,
		//hierarchicalLayout: true,
		nodes: {
          // default for all nodes
			shape: 'dot', 
			fontSize:16	,
			radius:16,
			fixed:true
		},
		edges: {
		smooth: false
			//length:1
		},
        groups: {
			null: {
            color: {
              border: 'white',
              background: 'white',
              highlight: {
                border: 'white',
                background: '#white'
              }
            },
            fontSize: 0,
			radius:0,
            //fontFace: 'arial',
            shape: 'dot'
          },
          host: {
            color: {
              border: 'black',
              background: '#B0E2FF',
              highlight: {
                border: 'black',
                background: '#4F94CD'
              }
            },
            fontSize: 15,
			radius:15,
            fontFace: 'arial',
            shape: 'dot'
          },
          firewall: {	
            color: {
              border: 'black',
              background: '#FF0000',
              highlight: {
                border: 'black',
                background: '#FF7256'
              }
            },
            fontSize: 15,
			radius:15,
            fontFace: 'arial',
            shape: 'dot'
          },
		  internet: {
            color: {
              border: 'black',
              background: '#7FFF00',
              highlight: {
                border: 'black',
                background: '#6B8E23'
              }
            },
            fontSize: 15,
			radius:15,
            fontFace: 'arial',
            shape: 'dot'
          },
          forwarding: {
            color: {
              border: 'black',
              background: '#878787',
              highlight: {
                border: 'black',
                background: '#4A4A4A'
              }
            },
            fontSize: 15,
			radius:15,
            fontFace: 'arial',
            shape: 'dot'
          },
		   loadbalance: {
            color: {
              border: '#black',
              background: '#FF9933',
              highlight: {
                border: 'black',
                background: '#FDCEBF'
              }
            },
            fontSize: 15,
			radius:15,
            fontFace: 'arial',
            shape: 'dot'
          },
		   group: {
            color: {
              border: 'black',
              background: 'yellow',
              highlight: {
                border: 'black',
                background: '#ECE5D0'
              }
            },
			fontSize: 15,
			radius:15,
		  
        }
		},
	physics: {
      repulsion: {
        centralGravity: 0,
        springLength: 100,//弹簧长度
        springConstant: 0,//弹簧常数
        nodeDistance: 0,
        damping: 0 //阻尼，减幅，衰减
      }}
		
	};
	

    var DOMURL = window.URL || window.webkitURL || window;	
	var nemoModule = [
                        /* no host in mode */
                        '<svg xmlns="http://www.w3.org/2000/svg" width="200" height="200" opacity="1">' +
                        '<circle cx="100" cy="100" r="95" stroke-width="2" stroke="#000000" fill="#FFFFFF" opacity="1"/>'+
                        '</svg>',
                        /* 1 host in mode */
						'<svg xmlns="http://www.w3.org/2000/svg" width="300" height="235" opacity="1">' +
						'<circle cx="130" cy="140" r="80" stroke-width="5" stroke="#000000" fill="#FFFFFF" opacity="1"/>\
						<circle cx="130" cy="140" r="40"  stroke-width="2" stroke="red" fill="#FFFFFF"></circle>\
						<foreignObject x="20" y="100" width="40%" height="20%">\
						<div xmlns="http://www.w3.org/1999/xhtml">\
						<span style="padding-left:65px; padding-top:100px; color:black; font-size:25px;">\
						{{host1}}</span>\
						</div>\
						</foreignObject>\
						</svg>',
                        /* 2 hosts in mode */
                        '<svg xmlns="http://www.w3.org/2000/svg" width="300" height="235" opacity="1">' +
						'<circle cx="150" cy="150" r="80" stroke-width="5" stroke="#000000" fill="#FFFFFF" opacity="1"/>\
						<circle cx="115.5" cy="150" r="35"  stroke-width="2" stroke="red" fill="#FFFFFF"></circle>\
						<foreignObject x="20" y="100" width="50%" height="20%">\
						<div xmlns="http://www.w3.org/1999/xhtml">\
						<span style="padding-left:65px; padding-top:100px; color:black; font-size:25px;">\
						{{host1}}</span>\
						</div>\
						</foreignObject>\
						<circle cx="155" cy="150" r="35"  stroke-width="2" stroke="red" fill="#FFFFFF"></circle>\
						<foreignObject x="140" y="140" width="40%" height="20%">\
						<div xmlns="http://www.w3.org/1999/xhtml" style="font-size:40px">\
						<span style="padding-left:15px; color:black; font-size:25px;">\
						{{host2}}</span>\
						</div>\
						</foreignObject>\
						</svg>',
                        /* 3 hosts in mode */
                        '<svg xmlns="http://www.w3.org/2000/svg" width="300" height="235" opacity="1">\
						<circle cx="150" cy="150" r="80" stroke-width="5" stroke="#153345" fill="#FFFFFF" opacity="1"/>\
						<circle cx="150" cy="110" r="32"  stroke-width="2" stroke="red" fill="#FFFFFF"></circle>\
						<foreignObject x="85" y="90" width="40%" height="20%">\
						<div xmlns="http://www.w3.org/1999/xhtml" >\
						<span style="padding-left:50px; color:black; font-size:25px;">\
						{{host1}}</span>\
						</div>\
						</foreignObject>\
						<circle cx="110" cy="170" r="32"  stroke-width="2" stroke="red" fill="#FFFFFF"></circle>\
						<foreignObject x="45" y="160" width="40%" height="20%">\
						<div xmlns="http://www.w3.org/1999/xhtml">\
						<span style="padding-left:50px; color:black; font-size:25px;">\
						{{host2}}</span>\
						</div>\
						</foreignObject>\
						<circle cx="190" cy="170" r="32"  stroke-width="2" stroke="red" fill="#FFFFFF"></circle>\
						<foreignObject x="130" y="150" width="40%" height="20%">\
						<div xmlns="http://www.w3.org/1999/xhtml">\
						<span style="padding-left:50px; color:black; font-size:25px;">\            {{host3}}</span>\
						</div>\
						</foreignObject>\
						</svg>'	,
                        /* 4 hosts in mode */
                        '<svg xmlns="http://www.w3.org/2000/svg" width="300" height="235" opacity="1">' +
						'<circle cx="130" cy="140" r="80" stroke-width="5" stroke="#000000" fill="#FFFFFF" opacity="1"/>\
						<circle cx="100" cy="110" r="30"  stroke-width="2" stroke="red" fill="#FFFFFF"></circle>\
						<foreignObject x="20" y="100" width="40%" height="20%">\
						<div xmlns="http://www.w3.org/1999/xhtml">\
						<span style="padding-left:65px; padding-top:100px; color:black; font-size:25px;">\
						{{host1}}</span>\
						</div>\
						</foreignObject>\
						<circle cx="100" cy="170" r="30"  stroke-width="2" stroke="red" fill="#FFFFFF"></circle>\
						<foreignObject x="20" y="140" width="40%" height="20%">\
						<div xmlns="http://www.w3.org/1999/xhtml" style="font-size:40px">\
						<span style="padding-left:15px; color:black; font-size:25px;">\
						{{host3}}</span>\
						</div>\
						</foreignObject>\
						<circle cx="160" cy="110" r="30"  stroke-width="2" stroke="red" fill="#FFFFFF"></circle>\
						<foreignObject x="150" y="80" width="40%" height="20%">\
						<div xmlns="http://www.w3.org/1999/xhtml" style="font-size:40px">\
						<span style="padding-left:15px; color:black; font-size:25px;">\
						{{host2}}</span>\
						</div>\
						</foreignObject>\
						<circle cx="160" cy="170" r="30"  stroke-width="2" stroke="red" fill="#FFFFFF"></circle>\
						<foreignObject x="150" y="140" width="40%" height="20%">\
						<div xmlns="http://www.w3.org/1999/xhtml" style="font-size:40px">\
						<span style="padding-left:15px; color:black; font-size:25px;">\
						{{host4}}</span>\
						</div>\
						</foreignObject>\
						</svg>',
                        /* more than 4 hosts in mode */
                        '<svg xmlns="http://www.w3.org/2000/svg" width="200" height="235" opacity="1">' +
                        '<circle cx="100" cy="100" r="95" stroke-width="2" stroke="#000000" fill="#FFFFFF" opacity="1"/>'+
                        '<rect x="40" y="38" width="30%" height="30%" stroke-width="2" stroke="#000000" fill="#FFFFFF"></rect>' +
                        '<foreignObject x="15" y="55" width="40%" height="20%">' +
                        '<div xmlns="http://www.w3.org/1999/xhtml" >' +
                        '<span style="padding-left:40px;color:black; font-size:20px;">' +
                        '{{host1}}</span>' +
                        '</div>' +
                        '</foreignObject>' +
                        '<rect x="40" y="108" width="30%" height="30%" stroke-width="2" stroke="#000000" fill="#FFFFFF"></rect>' +
                        '<foreignObject x="15" y="155" width="40%" height="20%">' +
                        '<div xmlns="http://www.w3.org/1999/xhtml">' +
                        '<span style="padding-left:40px;color:black; font-size:20px;">' +
                        '{{host2}}</span>' +
                        '</div>' +
                        '</foreignObject>' +
                        '<rect x="108" y="38" width="30%" height="30%" stroke-width="2" stroke="#000000" fill="#FFFFFF"></rect>' +
                        '<foreignObject x="105" y="55" width="40%" height="20%">' +
                        '<div xmlns="http://www.w3.org/1999/xhtml">' +
                        '<span style="padding-left:20px; color:black; font-size:20px;">' +
                        '{{host3}}</span>' +
                        '</div>' +
                        '</foreignObject>' +
                        '<rect x="108" y="108" width="30%" height="30%" stroke-width="0" stroke="#000000" fill="#FFFFFF"></rect>' +
                        '<foreignObject x="105" y="155" width="40%" height="20%">' +
                        '<div xmlns="http://www.w3.org/1999/xhtml">' +
                        '<span style="padding-left:20px; color:black; font-size:20px;">' +
                        '...</span>' +
                        '</div>' +
                        '</foreignObject>' +
                        '</svg>'];
//function
	function calculatePos(n){
			var res = new Array();
			if(n <= 0)return res;
			var start;
			var offset = 2 * Math.PI / n;
			if(n % 2 == 1){
				start = Math.PI / 2;
			}else{
				start = Math.PI / 2 + Math.PI / n;
			}
			res.push(start);
			for(var i = 1; i < n; i++){
				start += offset;
				res.push(start);
			}
			return res;
		}

						
						
		
