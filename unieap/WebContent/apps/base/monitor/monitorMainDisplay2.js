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
          
     function createChartControl(projects)
     {
    	var ganttChartControl = new GanttChart();
        ganttChartControl.setImagePath("/Unieap/unieap/js/dhtmlxGantt/dhtmlxGantt/codebase/imgs/");
        ganttChartControl.setEditable(false);
    	if(projects&&projects.length>0){
    		Ext.Array.forEach(projects,function(project,index,projects){
    			var taskOwner = project.ownerName;
    			var mainTasks = project.mainTasks;
    			if(mainTasks&&mainTasks.length>0)
    			{
    				Ext.Array.forEach(mainTasks,function(mainTask,index,mainTasks){
    					var newproject = new GanttProjectInfo(mainTask.taskId,taskOwner+':'+mainTask.taskName,new Date(mainTask.startDate));
	    				var subTasks = mainTask.subTasks;
	    				Ext.Array.forEach(subTasks,function(subTask,index,subTasks){
	    					var newtask = new GanttTaskInfo(subTask.taskId,subTask.taskName, new Date(subTask.startDate),subTask.duration,subTask.completePer,subTask.preTaskId);
	    					newproject.addTask(newtask);
	    				});
	    				ganttChartControl.addProject(newproject);
    				});
    			}
    		});
    	}
    	ganttChartControl.create('GanttDiv');
    	/**
        var project1 = new GanttProjectInfo(1, "Applet redesign", new Date(2010, 5, 11));
        var parentTask1 = new GanttTaskInfo(1, "Old code review", new Date(2010, 5, 11), 208, 50, "");
        parentTask1.addChildTask(new GanttTaskInfo(2, "Convert to J#", new Date(2010, 5, 11), 100, 40, ""));
        parentTask1.addChildTask(new GanttTaskInfo(13, "Add new functions", new Date(2010, 5, 12), 80, 90, ""));

        var parentTask2 = new GanttTaskInfo(3, "Hosted Control", new Date(2010, 6, 7), 190, 80, "1");
        var parentTask5 = new GanttTaskInfo(5, "J# interfaces", new Date(2010, 6, 14), 60, 70, "6");
        var parentTask123 = new GanttTaskInfo(123, "use GUIDs", new Date(2010, 6, 14), 60, 70, "");
        parentTask5.addChildTask(parentTask123);
        parentTask2.addChildTask(parentTask5);
        parentTask2.addChildTask(new GanttTaskInfo(6, "Task D", new Date(2010, 6, 10), 30, 80, "14"));

        var parentTask4 = new GanttTaskInfo(7, "Unit testing", new Date(2010, 6, 15), 118, 80, "6");
        var parentTask8 = new GanttTaskInfo(8, "core (com)", new Date(2010, 6, 15), 100, 10, "");
        parentTask8.addChildTask(new GanttTaskInfo(55555, "validate uids", new Date(2010, 6, 20), 60, 10, ""));
        parentTask4.addChildTask(parentTask8);
        parentTask4.addChildTask(new GanttTaskInfo(9, "Stress test", new Date(2010, 6, 15), 80, 50, ""));
        parentTask4.addChildTask(new GanttTaskInfo(10, "User interfaces", new Date(2010, 6, 16), 80, 10, ""));
        parentTask2.addChildTask(parentTask4);

        parentTask2.addChildTask(new GanttTaskInfo(11, "Testing, QA", new Date(2010, 6, 21), 60, 100, "6"));
        parentTask2.addChildTask(new GanttTaskInfo(12, "Task B (Jim)", new Date(2010, 6, 8), 110, 1, "14"));
        parentTask2.addChildTask(new GanttTaskInfo(14, "Task A", new Date(2010, 6, 7), 8, 10, ""));
        parentTask2.addChildTask(new GanttTaskInfo(15, "Task C", new Date(2010, 6, 9), 110, 90, "14"));

        project1.addTask(parentTask1);
        project1.addTask(parentTask2);

        //project 2
        var project2 = new GanttProjectInfo(2, "Web Design", new Date(2010, 5, 17));

        var parentTask22 = new GanttTaskInfo(62, "Fill HTML pages", new Date(2010, 5, 17), 157, 50, "");
        parentTask22.addChildTask(new GanttTaskInfo(63, "Cut images", new Date(2010, 5, 22), 78, 40, ""));
        parentTask22.addChildTask(new GanttTaskInfo(64, "Manage CSS", null, 90, 90, ""));
        project2.addTask(parentTask22);

        var parentTask70 = new GanttTaskInfo(70, "PHP coding", new Date(2010, 5, 18), 120, 10, "");
        parentTask70.addChildTask(new GanttTaskInfo(71, "Purchase D control", new Date(2010, 5, 18), 50, 0, ""));
        project2.addTask(parentTask70);
		*/
        
    }     
          
          
          
});