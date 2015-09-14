<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<%
	String project = (String)request.getAttribute("project");
%>
<html>
<head>
<title>My View</title>
    <style type="text/css">
    	.new-row .x-grid-cell {
            background-color: #FDBDB2;
        }
        .assign-row .x-grid-cell {
            background-color: #BDC5B5;
        }
        .confirmed-row .x-grid-cell {
            background-color: #ECEF91;
        }
        .pendingfix-row .x-grid-cell {
            background-color: #F4FAF9;
        }
        .reject-row .x-grid-cell {
            background-color: #F7210B;
        }
        .pendingretest-row .x-grid-cell {
            background-color: #F4FAF9;
        }
        .resolve-row .x-grid-cell {
            background-color: #9ECCC1;
        }
    </style>
    <script type="text/javascript">
    var project = "<%=project%>";
    Ext.onReady(function() {
    	function idRUL(value, metadata, record, rowIndex, colIndex, store){
    		if(UnieapButton.issue_edit!=null&&UnieapButton.issue_edit.abled==true){
		    	return '<a href="MantisController.do?method=issueDetail&defectId='+value+'" target="_self" title="View Issues">' + value + '</a>';
	    	}else{
	    		return value;
	    	}
	    }
    	function changeDesc(value, cellmeta, record, rowIndex, columnIndex, store) {
	   		var projectDesc = record.get('projectDesc');
	   		var testStreamDesc = record.get('testStreamDesc');
	   		var subStreamDesc = record.get('subStreamDesc');
	   		var title = record.get('title');
	   		var createDate = record.get('createDate');
	   	    return '['+projectDesc+']['+testStreamDesc+']['+subStreamDesc+']['+createDate+']:'+title;
	   	}
	    Ext.define('datamodel', {
	        extend: 'Ext.data.Model',
	        fields:[
	             	'defectId','prodVersionDesc','testStreamDesc','severityDesc','priorityDesc','subStreamDesc','testPlanDesc','tcId',
	             	'title','descpt','remark','steps','createDate','modifyDate','modifyBy','createBy','status','statusDesc','assignto','assigntoName',
	             	'project','projectDesc'
	             ],
	         idProperty: 'defectId'
	    });
	    var assignedToMeGridstore = Ext.create('Ext.data.Store', {
	        model: 'datamodel',
	        pageSize:10,
	        remoteSort: true,
	        proxy: 
	        { type: 'ajax', url:'MantisController.do?method=viewisGrid&operType=AssignedToMe&project='+project,
	            reader: 
	            {root: 'rows', totalProperty: 'totalCount'},
	            simpleSortMode: true
	        },
	        sorters: 
	        [
	        	{ property: 'defectId', direction: 'DESC'}
	        ]
	    });
	    var assignedToMeDatagrid = Ext.create('Ext.grid.Panel', 
	    	    {columnLines: true,
	    			el : 'div_assignedToMe',
	    		    layout: 'fit',
	    		    hideHeaders:true,
	    	   	    title: 'Assigned to me',
	    		   	store : assignedToMeGridstore,
	    		   	forceFit: true,
	    		   	autoExpandColumn:'title',
	    	   	   	columns:
	    	   	   	[
	    				{ text: "Defect Id",dataIndex: 'defectId',width:80,sortable:false,renderer:idRUL},
	    				{ text: "Title", dataIndex: 'title',width:700,sortable:false,renderer :changeDesc}
	    	   	   	],
		    	   	viewConfig:{getRowClass:function(record,rowIndex,rowParams,store){ 
			   	 			var status = record.get('status');
			   	 			if(status=='0'){
			   	 				return 'new-row';
			   	 			}
				   	 		if(status=='1'){
			   	 				return 'assign-row';
			   	 			}
					   	 	if(status=='2'){
			   	 				return 'confirmed-row';
			   	 			}
					   	 	if(status=='3'){
			   	 				return 'pendingfix-row';
			   	 			}
					   	 	if(status=='4'){
			   	 				return 'reject-row';
			   	 			}
					   	 	if(status=='5'){
			   	 				return 'pendingretest-row';
			   	 			}
					   	 	if(status=='6'){
			   	 				return 'resolve-row';
			   	 			}
			   	 			
	              	}},
	    	   	 	bbar:new Ext.PagingToolbar(
	    	  	    	   	{ store : assignedToMeGridstore,displayInfo: true, displayMsg: 'Displaying topics {0} - {1} of {2}', emptyMsg: "No topics to display"})

	    	    });
	    assignedToMeDatagrid.render();
	    assignedToMeGridstore.loadPage(1);
	 	var unassignedIssuedGridstore = Ext.create('Ext.data.Store', {
	        model: 'datamodel',
	        pageSize:10,
	        remoteSort: true,
	        proxy: 
	        { type: 'ajax', url:'MantisController.do?method=viewisGrid&operType=Unassigned&project='+project,
	            reader: 
	            {root: 'rows', totalProperty: 'totalCount'},
	            simpleSortMode: true
	        },
	        sorters: 
	        [
	        	{ property: 'defectId', direction: 'DESC'}
	        ]
	    });
	 
	  var unassignedIssuedDatagrid = Ext.create('Ext.grid.Panel', 
	    {columnLines: true,
		    layout: 'fit',
		    el : 'div_unassignedIssued',
		    hideHeaders:true,
	   	    title: 'Unassigned',
		   	store : unassignedIssuedGridstore,
		   	forceFit: true,
		   	autoExpandColumn:'title',
	   	   	columns:
	   	   	[
				{ text: "Defect Id",dataIndex: 'defectId',width:80,sortable:false,renderer:idRUL},
				{ text: "Title", dataIndex: 'title',width:700,sortable:false,renderer :changeDesc}
	   	   	],
		   	viewConfig:{getRowClass:function(record,rowIndex,rowParams,store){ 
		 			var status = record.get('status');
		 			if(status=='0'){
		 				return 'new-row';
		 			}
		   	 		if(status=='1'){
		 				return 'assign-row';
		 			}
			   	 	if(status=='2'){
		 				return 'confirmed-row';
		 			}
			   	 	if(status=='3'){
		 				return 'pendingfix-row';
		 			}
			   	 	if(status=='4'){
		 				return 'reject-row';
		 			}
			   	 	if(status=='5'){
		 				return 'pendingretest-row';
		 			}
			   	 	if(status=='6'){
		 				return 'resolve-row';
		 			}
		 			
	   		}},
	   	 	bbar:new Ext.PagingToolbar(
	  	    	   	{ store : unassignedIssuedGridstore,displayInfo: true, displayMsg: 'Displaying topics {0} - {1} of {2}', emptyMsg: "No topics to display"})

	    });
	  unassignedIssuedDatagrid.render();
	  unassignedIssuedGridstore.loadPage(1);
	  var reportedbyMeGridstore = Ext.create('Ext.data.Store', {
	        model: 'datamodel',
	        remoteSort: true,
	        pageSize:10,
	        proxy: 
	        { type: 'ajax', url:'MantisController.do?method=viewisGrid&operType=ReportedbyMe&project='+project,
	            reader: 
	            {root: 'rows', totalProperty: 'totalCount'},
	            simpleSortMode: true
	        },
	        sorters: 
	        [
	        	{ property: 'defectId', direction: 'DESC'}
	        ]
	    });
	 
	  var reportedbyMeDatagrid = Ext.create('Ext.grid.Panel', 
	    {columnLines: true,autoScroll:true,
		    el : 'div_reportedbyMe',
	   	    title: 'Reported by Me',
	   	 	layout: 'fit',
		    hideHeaders:true,
		   	store : reportedbyMeGridstore,
		   	forceFit: true,
		   	autoExpandColumn:'title',
	   	   	columns:
	   	   	[
				{ text: "Defect Id",dataIndex: 'defectId',width:80,sortable:false,renderer:idRUL},
				{ text: "Title", dataIndex: 'title',width:700,sortable:false,renderer :changeDesc}
	   	   	],
	   	 	viewConfig:{getRowClass:function(record,rowIndex,rowParams,store){ 
	 			var status = record.get('status');
	 			if(status=='0'){
	 				return 'new-row';
	 			}
	   	 		if(status=='1'){
	 				return 'assign-row';
	 			}
		   	 	if(status=='2'){
	 				return 'confirmed-row';
	 			}
		   	 	if(status=='3'){
	 				return 'pendingfix-row';
	 			}
		   	 	if(status=='4'){
	 				return 'reject-row';
	 			}
		   	 	if(status=='5'){
	 				return 'pendingretest-row';
	 			}
		   	 	if(status=='6'){
	 				return 'resolve-row';
	 			}
	 			
			}},
	   	 	bbar:new Ext.PagingToolbar(
	  	    	   	{ store : reportedbyMeGridstore,displayInfo: true, displayMsg: 'Displaying topics {0} - {1} of {2}', emptyMsg: "No topics to display"})

	    });
	  reportedbyMeDatagrid.render();
	  reportedbyMeGridstore.loadPage(1);
	  var resolvedGridstore = Ext.create('Ext.data.Store', {
	        model: 'datamodel',
	        pageSize:10,
	        remoteSort: true,
	        proxy: 
	        { type: 'ajax', url:'MantisController.do?method=viewisGrid&operType=Resolved&project='+project,
	            reader: 
	            {root: 'rows', totalProperty: 'totalCount'},
	            simpleSortMode: true
	        },
	        sorters: 
	        [
	        	{ property: 'defectId', direction: 'DESC'}
	        ]
	    });
	 
	  var resolvedDatagrid = Ext.create('Ext.grid.Panel', 
	    {   columnLines: true,autoScroll:true,
		    el : 'div_resolved',
	   	    title: 'Resolved',
	   	 	layout: 'fit',
		    hideHeaders:true,
		   	store : resolvedGridstore,
		   	forceFit: true,
		   	autoExpandColumn:'title',
	   	   	columns:
	   	   	[
				{ text: "Defect Id",dataIndex: 'defectId',width:80,sortable:false,renderer:idRUL},
				{ text: "Title", dataIndex: 'title',width:700,sortable:false,renderer :changeDesc}
	   	   	],
	   	 	viewConfig:{getRowClass:function(record,rowIndex,rowParams,store){ 
	 			var status = record.get('status');
	 			if(status=='0'){
	 				return 'new-row';
	 			}
	   	 		if(status=='1'){
	 				return 'assign-row';
	 			}
		   	 	if(status=='2'){
	 				return 'confirmed-row';
	 			}
		   	 	if(status=='3'){
	 				return 'pendingfix-row';
	 			}
		   	 	if(status=='4'){
	 				return 'reject-row';
	 			}
		   	 	if(status=='5'){
	 				return 'pendingretest-row';
	 			}
		   	 	if(status=='6'){
	 				return 'resolve-row';
	 			}
	 			
			}},
	   	 	bbar:new Ext.PagingToolbar(
	  	    	   	{ store : resolvedGridstore,displayInfo: true, displayMsg: 'Displaying topics {0} - {1} of {2}', emptyMsg: "No topics to display"})

	    });
	  resolvedDatagrid.render();
	  resolvedGridstore.loadPage(1);
	  var recentlyModifiedGridstore = Ext.create('Ext.data.Store', {
	        model: 'datamodel',
	        pageSize:10,
	        remoteSort: true,
	        proxy: 
	        { type: 'ajax', url:'MantisController.do?method=viewisGrid&operType=RecentlyModified&project='+project,
	            reader: 
	            {root: 'rows', totalProperty: 'totalCount'},
	            simpleSortMode: true
	        },
	        sorters: 
	        [
	        	{ property: 'modifyDate', direction: 'DESC'}
	        ]
	    });
	 
	  var recentlyModifiedDatagrid = Ext.create('Ext.grid.Panel', 
	    {columnLines: true,autoScroll:true,
		    el : 'div_recentlyModified',
	   	    title: 'Recently Modified',
	   	 	layout: 'fit',
		    hideHeaders:true,
		   	store : recentlyModifiedGridstore,
		   	forceFit: true,
		   	autoExpandColumn:'title',
	   	   	columns:
	   	   	[
				{ text: "Defect Id",dataIndex: 'defectId',width:80,sortable:false,renderer:idRUL},
				{ text: "Title", dataIndex: 'title',width:700,sortable:false,renderer :changeDesc}
	   	   	],
	   	 	viewConfig:{getRowClass:function(record,rowIndex,rowParams,store){ 
	 			var status = record.get('status');
	 			if(status=='0'){
	 				return 'new-row';
	 			}
	   	 		if(status=='1'){
	 				return 'assign-row';
	 			}
		   	 	if(status=='2'){
	 				return 'confirmed-row';
	 			}
		   	 	if(status=='3'){
	 				return 'pendingfix-row';
	 			}
		   	 	if(status=='4'){
	 				return 'reject-row';
	 			}
		   	 	if(status=='5'){
	 				return 'pendingretest-row';
	 			}
		   	 	if(status=='6'){
	 				return 'resolve-row';
	 			}
	 			
			}},
	   	 	bbar:new Ext.PagingToolbar(
	  	    	   	{ store : recentlyModifiedGridstore,displayInfo: true, displayMsg: 'Displaying topics {0} - {1} of {2}', emptyMsg: "No topics to display"})
	    });
	  recentlyModifiedDatagrid.render();
	  recentlyModifiedGridstore.loadPage(1);
    });	
    
    </script>
</head>
<body>
	<div style='height:600px'>
		<div id="div_assignedToMe"></div>
		<div id="div_unassignedIssued"></div>
		<div id="div_reportedbyMe"></div>
		<div id="div_resolved"></div>
		<div id="div_recentlyModified"></div>
	</div>
</body>
</html>
