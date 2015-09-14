Ext.onReady(function(){
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
    	var DayData = Ext.create('DayData');
    	var Utils = Ext.create('Utils');
    	Ext.define('datamodel', {
            extend: 'Ext.data.Model',
            fields:
            [
            	'taskOwner','taskList'
            ],
            idProperty: 'userId'
        });
	    var store = Ext.create('Ext.data.Store', {
	        autoDestroy: true,
	        model: 'datamodel',
	        proxy:{type: 'memory'},
	        data: OwnerList,
	        sorters: [{
	            property: 'start',
	            direction: 'ASC'
	        }]
	    });
	    var beforedate = -5;
	    var enddate = 8;
	    var weekdate = getGridDate(beforedate,enddate);
	    var grid = Ext.create('Ext.grid.Panel', {
	    	layout: 'fit',columnLines: true,autoScroll:true,
	        store: store,
	        columns:weekdate,
	        renderTo: Ext.getBody(),
	        title: 'Monitor Main Task',
	        tbar: 
	        [
		        {
		            text: 'Add Employee',
		            iconCls: 'employee-add',
		            handler : function(){
		            	beforedate = beforedate+1;
		            	enddate = enddate+1;
		            	weekdate = getGridDate(beforedate,enddate);
		            	grid.reconfigure(store,weekdate);
		            }
		        }
		    ],
	        listeners: {
	            'selectionchange': function(view, records) {
	            }
	        }
	    });
	   function taskStatus(v, cellValues, rec){
	   		var headerText = cellValues.column.text;
	   		var taskDate = Ext.Date.format(v,'Y-m-d H:i:s'); 
	   		var taskList = rec.get('taskList');
	   		if(taskList!== null && taskList.length>0){
	   			var taskStartDate,taskEndDate,completePer;
	   			for(var i = 0 ; i< taskList.length ; i++){
	   				var obj = taskList[i];
	   				taskStartDate =obj.taskStartDate;
	   				taskEndDate = obj.taskEndDate;
	   				taskEndDate = obj.taskEndDate;
	   				completePer = obj.completePer;
	   				if(DayData.checkDateRange(headerText,taskStartDate,taskEndDate)){
	   					//return Utils.getGridStyle('red','green',completePer);
	   					cellValues.style = 'background-color:#42E61A';
	   					//cellValues.innerCls = 'background-color:yellow';
	   					return '<span style="color:red;">80.00%</span>';
	   					//return '<div style="background-color:green;">' +'50%'+ '</div>';
				    	//return '<div style="background-color:red;"><div style="background-color:green;width:25;height:20">' +'50%'+ '</div></div>';
	   				}else{
	   					continue;
	   				}
	   			} 
	   			return v;
	   		}
	   		//var taskEndDate = rec.get('taskEndDate');
	   		//if(){
	   		//}
	   }
	   function getGridDate(beforedate,enddate){
			var griddate = [];
			griddate.push({header: 'Task Name',width:80,dataIndex: 'taskOwner'});
			var date = null;
			for(var i = beforedate; i < 0; i++){
				date = DayData.makeDate(i);
				griddate.push({header:DayData.getGridDate(date),width:50, dataIndex:'task',renderer:taskStatus});
			}
			date = DayData.getToday();
			griddate.push({header:DayData.getGridDate(date),width:50, dataIndex:'task',renderer:taskStatus});
			for(var i = 0; i < enddate; i++){
				date = DayData.makeDate(i);
				griddate.push({header:DayData.getGridDate(date),width:50, dataIndex:'task',renderer:taskStatus});
			}
			return griddate;
		}
	var proxy = new Ext.dd.DDProxy("search_div"); 
	});