Ext.onReady(function(){
	Ext.Ajax.request({
         url: 'MonitorController.do?method=projectList',
         async:false,
         params:{'operType':""},
         success: function(response, opts) 
         {
         	var result = Ext.JSON.decode(response.responseText);
             if(result.isSuccess == 'success')
             {
         		var projects = Ext.JSON.decode(result.projects);
         		createChartControl(projects);
             }else
             {
             	var error = "don't exist already exist, please change to another";
             }
         },
         failure: function(response, opts) 
         {
         	alert(response.responseText);
         }
    });
    function linkTaskDetais(taskId)
    {
    	alert('taskId='+taskId);
    }
    function createChartControl(projects)
    {
    	var ganttChartControl = new GanttChart();
        ganttChartControl.setImagePath("/Unieap/unieap/js/dhtmlxGantt/dhtmlxGantt/codebase/imgs/");
        ganttChartControl.setEditable(false);
    	if(projects&&projects.length>0){
    		Ext.Array.forEach(projects,function(project,index,projects){
    			var projectName = project.taskGroup;
    			//Project
    			var newproject = new GanttProjectInfo(project.taskId,project.taskName,new Date(project.startDate));
    			var subTasks = project.subTasks;
    			if(subTasks&&subTasks.length>0)
    			{
    				var preTaskId = '';
    				Ext.Array.forEach(subTasks,function(subTask,index,subTasks){
    					if(subTask.depTasks.length>0)
    					{
    						//preTaskId = subTask.depTasks[0].taskId;
    					}else{
    						preTaskId = '';
    					}
    					//Level 1 task
    					//newcell.innerHTML="<a href='xxx'>xxx</a>"
    					var linkTaskName = "<a href='#' onclick='linkTaskDetais("+subTask.taskId+")'>"+subTask.taskName+"</a>";
    					var newtask = new GanttTaskInfo(subTask.taskId,linkTaskName, new Date(subTask.startDate),subTask.duration,subTask.completePer,preTaskId);
	    				if(subTask.subTasks.length>0)
	    				{
	    					var sub2Tasks = subTask.subTasks;
	    					Ext.Array.forEach(sub2Tasks,function(sub2Task,index,sub2Tasks){
		    					if(sub2Task.depTasks.length>0)
		    					{
		    						//preTaskId = sub2Task.depTasks[0].taskId;
		    					}else{
		    						preTaskId = '';
		    					}
		    					//Level2 task
		    					newtask.addChildTask(new GanttTaskInfo(sub2Task.taskId,sub2Task.taskName, new Date(sub2Task.startDate),sub2Task.duration,sub2Task.completePer,preTaskId));
		    				});
	    				};
	    				newproject.addTask(newtask);
    				});
    			}
    			ganttChartControl.addProject(newproject);
    		});
    	}
    	ganttChartControl.create('GanttDiv');
    }
});