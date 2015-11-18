
Ext.onReady(function(){
	var beforedate = 5;
	var enddate = 5;
	_table=document.getElementById ("table");
	_table.border="1px";
	_table.width="800px";
	for(var i=1;i<10;i++){
　　		var row=document.createElement ("tr"); 
　　		row.id=i;
　　		for(var j=1;j<6;j++){
　　　		var cell=document.createElement ("td"); 
　　　		cell.id = j;
　　　		if(row.id=='1'||cell.id=='1'){
				if(row.id=='1'){
					cell.appendChild(document.createTextNode ("系统名称:"+cell.id)); 
				}else{
	　　　			cell.appendChild(document.createTextNode ("日期:"+row.id)); 
				}
			}else{
				cell.appendChild(document.createTextNode ("第"+cell.id+"列")); 
			}
　　　		row.appendChild (cell); 
　　		}
　　		document.getElementById("newbody").appendChild (row); 
　	};
	var proxy = new Ext.dd.DDProxy("search_div"); 
	/**
	function createTableHeader(beforedate,enddate){
		var griddate = [];
		griddate.push({header: 'Task Name',width:80,dataIndex: 'taskOwner'});
		var date = null;
		for(var i = beforedate; i < 0; i++){
			date = DayData.makeDate(i);
			griddate.push({header:DayData.getGridDate(date),width:50, dataIndex:'task',colspan:1,renderer:taskStatus});
		}
		date = DayData.getToday();
		griddate.push({header:DayData.getGridDate(date),width:50, dataIndex:'task',colspan:1,renderer:taskStatus});
		for(var i = 0; i < enddate; i++){
			date = DayData.makeDate(i);
			griddate.push({header:DayData.getGridDate(date),width:50, dataIndex:'task',colspan:1,renderer:taskStatus});
		}
		return griddate;
	}
	var OwnerList = 
		[	
			{taskOwner:'BSCS',taskList:
			[
				{taskOwner:'BSCS',taskName:'Bill Run1',taskStartDate:'2013-09-07 11:30:00',taskEndDate:'2013-09-08 11:30:00',status:'pending',completePer:'0.3'},
				{taskOwner:'BSCS',taskName:'Bill Run2',taskStartDate:'2013-09-10 11:30:00',taskEndDate:'2013-09-10 11:30:00',status:'pending',completePer:'0.5'}
			]
			},
			{taskOwner:'NSN',taskList:
			[
				{taskOwner:'NSN',taskName:'Bill Run1',taskStartDate:'2013-09-10 11:30:00',taskEndDate:'2013-09-10 11:30:00',status:'pending',completePer:'0.8'}
			]}
			
		];
	function createTask(columnHeaders,OwnerList){
		
	
	}*/
})