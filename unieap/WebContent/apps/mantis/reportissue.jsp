<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<%
	String project = (String)request.getAttribute("project");
%>
<html>
<head>
<title>Report Issue</title>
	<style type="text/css">
        .td_title {
        	background-color:#F1F1F1;
        	font-weight:bolder;
        	height:30px;
        	width:100px;
        	border-collapse:collapse; 
        	border:1px black solid;
        	
        }
        .td_value {
        	background-color:#FAFAFA;
        	height:30px;
        	width:500px;
        	border-collapse:collapse; 
        	border:1px black solid;
        }
        .tt_textarea{
			height:100px;
            width:90%;
            text-align:left;
		}
    </style>
	<script type="text/javascript">
		var project = "<%=project%>";
			Ext.onReady(function() {
		    	Ext.tip.QuickTipManager.init();
		    	var projectObj = Ext.create('Ext.form.field.ComboBox', {
			        renderTo: 'project',
			        hiddenName:'productVersion',
			        valueField:'dicCode',
			        displayField: 'dicName',
			        width:200,
			        labelWidth:45,
			        store: Ext.create('Ext.data.Store', 
			        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1014}),
	        		queryMode: 'local',
	    	        forceSelection: true
			    });
		    	var productVersion = Ext.create('Ext.form.field.ComboBox', {
			        renderTo: 'productVersion',
			        hiddenName:'productVersion',
			        valueField:'dicCode',
			        displayField: 'dicName',
			        width:200,
			        labelWidth:45,
			        store: Ext.create('Ext.data.Store', 
			        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1005Opt}),
	        		queryMode: 'local',
	    	        forceSelection: true
			    });
		    	var testStreams = Ext.create('Ext.form.field.ComboBox', {
			        renderTo: 'testStreams',
			        hiddenName:'testStreams',
			        valueField:'dicCode',
			        displayField: 'dicName',
			        width:200,
			        labelWidth:45,
			        store: Ext.create('Ext.data.Store', 
			        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1006}),
	        		queryMode: 'local',
	    	        forceSelection: true
			    });
		    	var severity = Ext.create('Ext.form.field.ComboBox', {
			        renderTo: 'severity',
			        hiddenName:'severity',
			        valueField:'dicCode',
			        displayField: 'dicName',
			        width:200,
			        labelWidth:45,
			        store: Ext.create('Ext.data.Store', 
			        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1002Opt}),
	        		queryMode: 'local',
	    	        forceSelection: true
			    });
		    	var priority = Ext.create('Ext.form.field.ComboBox', {
			        renderTo: 'priority',
			        hiddenName:'priority',
			        valueField:'dicCode',
			        displayField: 'dicName',
			        width:200,
			        labelWidth:45,
			        store: Ext.create('Ext.data.Store', 
			        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1004Opt}),
	        		queryMode: 'local',
	    	        forceSelection: true
			    });
		    	
		    	var targetDate = new Ext.form.DateField({
		    		hideLabel:true,
		    		renderTo: 'targetDate',
		    	    name: 'targetDate',
		    	    width: 190,
		    	    allowBlank: true,
		    	    altFormats: 'Y-m-d',
		    	    format: 'Y-m-d',
		    	    blankText: '', 
		    	    emptyText: 'Select a date ...' 
		    	});
		    	
		    	var subStream = Ext.create('Ext.form.field.ComboBox', {
			        renderTo: 'subStream',
			        hiddenName:'subStream',
			        valueField:'dicCode',
			        displayField: 'dicName',
			        width:200,
			        labelWidth:45,
			        store: Ext.create('Ext.data.Store', 
			        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1007}),
	        		queryMode: 'local',
	    	        forceSelection: true
			    });
		    	var testPlan = Ext.create('Ext.form.field.ComboBox', {
			        renderTo: 'testPlan',
			        hiddenName:'testPlan',
			        valueField:'dicCode',
			        displayField: 'dicName',
			        width:200,
			        labelWidth:45,
			        store: Ext.create('Ext.data.Store', 
			        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1008}),
	        		queryMode: 'local',
	    	        forceSelection: true
			    });
		    	
		    	var repoter = new Ext.data.Store({
		            proxy: {
		                type: 'ajax',
		                url: 'MantisController.do?method=getReporterList',
		                reader: {type: 'json'}
		            },
		            fields: ['dicCode','dicName']
		        });
		    	repoter.on('beforeload', function (store, options){
			            Ext.apply(store.proxy.extraParams,{'operType':'ViewIssue','project':Ext.getDom("project_id_value").value,'defectId':''});
			        });
		    	repoter.load();
		    	var assignto = Ext.create('Ext.form.field.ComboBox', {
			        renderTo: 'assignto',
			        hiddenName:'assignto',
			        valueField:'dicCode',
			        displayField: 'dicName',
			        width:200,
			        labelWidth:45,
			        store: repoter
			    });
		    	
		    	Ext.get('submit_b').on('click', function(e){
		    		if(projectObj.getValue()==null||projectObj.getValue()=='-1'){
		    			Ext.MessageBox.show({title: 'Status',msg:"[Project] is required and can't be All.",
                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
		    			return;
		    		}
		    		if(testStreams.getValue()==null){
		    			Ext.MessageBox.show({title: 'Status',msg:"[Test Streams] is required.",
                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
		    			return;
		    		}
		    		if(subStream.getValue()==null){
		    			Ext.MessageBox.show({title: 'Status',msg:"[Sub Stream/Sub Module] is required.",
                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
		    			return;
		    		}
		    		if(testPlan.getValue()==null){
		    			Ext.MessageBox.show({title: 'Status',msg:"[Test Plan] is required.",
                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
		    			return;
		    		}
		    		if(Ext.get("tcId").dom.value==''){
		    			Ext.MessageBox.show({title: 'Status',msg:"[Test Case ID] is required.",
                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
		    			return;
		    		}
		    		if(Ext.get("title").dom.value==''){
		    			Ext.MessageBox.show({title: 'Status',msg:"[Title] is required.",
                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
		    			return;
		    		}
		    		if(Ext.get("descpt").dom.value==''){
		    			Ext.MessageBox.show({title: 'Status',msg:"[Description] is required.",
                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
		    			return;
		    		}
		    		var parameters = {
		    				'project':projectObj.getValue(),
		    				'prodVersion':productVersion.getValue(),
		    				'testStream':testStreams.getValue(),
		    				'severity':severity.getValue(),
		    				'priority':priority.getValue(),
		    				'targetDate':targetDate.getValue()==null?'':Ext.util.Format.date(targetDate.getValue(),'Y-m-d'),
		    				'subStream':subStream.getValue(),
		    				'testPlan':testPlan.getValue(),
		    				'tcId':Ext.get("tcId").dom.value,
		    				'dts':Ext.get("dts").dom.value,
		    				'title':Ext.get("title").dom.value,
		    				'descpt':Ext.get("descpt").dom.value,
		    				'remark':Ext.get("remark").dom.value,
		    				'assignto':assignto.getValue(),
		    				'operType':"Add"
		    				};
		    		Ext.Ajax.request({
		                url: 'MantisController.do?method=reportIssueDeal',
		                params:parameters,
		                success: function(response, opts) 
		                {
		                	var result = Ext.JSON.decode(response.responseText);
		                	uploadFile(result.defectId);
		                	//window.location.href ="MantisController.do?method=issueDetail&defectId="+result.defectId;
		                },
		                failure: function(response, opts) 
		                {
		                	Ext.MessageBox.show({title: 'Status',msg:response.responseText,
		            			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
		                }
		             });
		        });
		    	/**upload files******************/
		        var fileUpload = Ext.create('Ext.form.Panel', {
	    	        width: 400,
	    	        frame: true,
	    	        renderTo: 'fi-form',   
	    	        items: [{
	    	            xtype: 'filefield',
	    	            name: 'file',
	    	            id:'filePath',
	    	            msgTarget: 'side',
	    	            allowBlank: true,
	    	            anchor: '100%',
	    	            buttonText: 'Select a File...'
	    	        }],
	    	        buttons: []
	    	    });
		        function showReloadResult(btn){
		        	
		    		//document.location.reload();
		    	}
		        function uploadFile(defectId){
		        	var filePath = Ext.getCmp("filePath").getValue();
		        	if(filePath!=null&&filePath!=''){
			    		fileUpload.submit({
	                        url: 'UploadController.do?method=upload',
	                        params:{'handlerId':'3','parameters':Ext.JSON.encode({'defectId':defectId})},
	                        waitMsg: 'Uploading your file...',
	                        success: function(form, action) {
	                         	var result = Ext.JSON.decode(action.response.responseText);
			                    if(result.isSuccess == 'failed')
			                    {
			                    	Ext.MessageBox.show({title: 'Status',msg:result.message,
	                         			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
			                    }else{
			                    	window.location.href ="MantisController.do?method=issueDetail&defectId="+defectId;
			                    }
	                          },
	                         failure: function(form, action) {
	                        	 Ext.MessageBox.show({title: 'Status',msg:action.response.responseText,
	                    			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
	                         }
	                    });
		        	}else{
		        		window.location.href ="MantisController.do?method=issueDetail&defectId="+defectId;
		        	}
		    	}
		    	
		    });
			
	</script>
</head>
<body>
	<input type="hidden" id='project_id_value' value="${project}" />
	<table class="grouble_table"  align="center" width='98%'>
		<tr class="grouble_table_tr">
			<td class="td_title"><font color='red'>*</font>Project</td>
			<td class="td_value"><div id="project"></div></td>
		</tr>
		<tr class="grouble_table_tr">
			<td class="td_title">Product Version</td>
			<td class="td_value"><div id="productVersion"></div></td>
		</tr>
		<tr class="grouble_table_tr">
			<td class="td_title"><font color='red'>*</font>Test Streams</td>
			<td class="td_value"><div id="testStreams"></div></td>
		</tr>
		<tr class="grouble_table_tr">
			<td class="td_title">Severity</td>
			<td class="td_value"><div id="severity"></div></td>
		</tr>
		<tr class="grouble_table_tr"> 
			<td class="td_title">Priority</td>
			<td class="td_value"><div id="priority"></div></td>
		</tr>
		<tr class="grouble_table_tr">
			<td class="td_title">Target Fix Date</td>
			<td class="td_value"><div id="targetDate"></div></td>
		</tr>
		<tr class="grouble_table_tr">
			<td class="td_title">Assign To</td>
			<td colspan=5 class="td_value" id='assignto_td'><div id="assignto"></div></td>
		</tr>
		<tr class="grouble_table_tr">
			<td class="td_title"><font color='red'>*</font>Sub Stream/Sub Module</td>
			<td class="td_value"><div id="subStream"></div></td>
		</tr>
		<tr class="grouble_table_tr">
			<td class="td_title"><font color='red'>*</font>Test Plan</td>
			<td class="td_value"><div id="testPlan"></div></td>
		</tr>
		<tr class="grouble_table_tr">
			<td class="td_title"><font color='red'>*</font>Test Case ID</td>
			<td class="td_value"><input type='text' id='tcId' style="width:200px" value=''/></td>
		</tr>
		<tr class="grouble_table_tr">
			<td class="td_title">DTS</td>
			<td class="td_value"><input type='text' id='dts' style="width:200px" value=''/></td>
		</tr>
		<tr class="grouble_table_tr">
			<td class="td_title"><font color='red'>*</font>Title</td>
			<td class="td_value"><input type='text' id='title' style="width:90%" name='title' value=''/></td>
		</tr>
		<tr class="grouble_table_tr">
			<td class="td_title"><font color='red'>*</font>Description</td>
			<td class="td_value">
				<textarea id='descpt' class="tt_textarea">Problem:
Expected Result:</textarea>
			</td>
		</tr>
		<tr class="grouble_table_tr">
			<td class="td_title">Remarks</td>
			<td class="td_value">
				<textarea id='remark' class="tt_textarea"></textarea>
			</td>
		</tr>
		<tr>
			<td class="td_title">Upload Files</td>
			<td colspan=5 class="td_value">
				<div id="fi-form"></div> 
			</td>
		</tr>
		<tr class="grouble_table_tr">
			<td style ="width:80px" align="center">
				<font color='red'>*Required</font>
			</td>
			<td align="center">
				<input type="button" style ="width:80px" id="submit_b" value="Submit"/>
			</td>
		</tr>
	</table>
</body>
</html>
