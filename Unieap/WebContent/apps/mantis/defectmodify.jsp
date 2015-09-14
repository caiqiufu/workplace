<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%
 
%>
<html>
<head>
<title>View Issue Detail</title>
    <style type="text/css">
        .td_title {
        	background-color:#F1F1F1;
        	font-weight:bolder;
        	height:30px;
        	width:200px;
        	border-collapse:collapse; 
        	border:1px black solid;
        	
        }
        .td_value {
        	background-color:#FAFAFA;
        	height:30px;
        	width:200px;
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
    Ext.onReady(function() {
    	var testStreams = Ext.create('Ext.form.field.ComboBox', {
    		renderTo: 'testStream_div',
	        valueField:'dicCode',
	        displayField: 'dicName',
	        width:200,
	        value:Ext.getDom("testStream_value").value,
	        store: Ext.create('Ext.data.Store', 
	        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1006}),
	        queryMode: 'local',
	        forceSelection: true
	    });
    	var severity = Ext.create('Ext.form.field.ComboBox', {
    		renderTo:'severity_div',
	        valueField:'dicCode',
	        displayField: 'dicName',
	        width:200,
	        value:Ext.getDom("severity_value").value,
	        store: Ext.create('Ext.data.Store', 
	        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1002Opt}),
	        queryMode: 'local',
	        forceSelection: true
	    });
    	var priority = Ext.create('Ext.form.field.ComboBox', {
    		renderTo:'priority_div',
	        valueField:'dicCode',
	        displayField: 'dicName',
	        width:200,
	        value:Ext.getDom("priority_value").value,
	        store: Ext.create('Ext.data.Store', 
	        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1004Opt}),
	        queryMode: 'local',
	        forceSelection: true
	    });
    	var targetDate = new Ext.form.DateField({
    		hideLabel:true,
    		renderTo: 'targetDate_div',
    	    name: 'targetDate',
    	    width: 190,
    	    allowBlank: true,
    	    altFormats: 'Y-m-d',
    	    format: 'Y-m-d',
    	    value:Ext.getDom("subStream_value").value==null?'':Ext.util.Format.date(Ext.getDom("targetDate_value").value,'Y-m-d'),
    	    blankText: '', 
    	    emptyText: 'Select a date ...' 
    	});
    	var subStream = Ext.create('Ext.form.field.ComboBox', {
    		renderTo:'subStream_div',
	        valueField:'dicCode',
	        displayField: 'dicName',
	        width:200,
	        value:Ext.getDom("subStream_value").value,
	        store: Ext.create('Ext.data.Store', 
	        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1007}),
	        queryMode: 'local',
	        forceSelection: true
	    });
    	
    	var testPlan = Ext.create('Ext.form.field.ComboBox', {
    		renderTo:'testPlan_div',
	        valueField:'dicCode',
	        displayField: 'dicName',
	        width:200,
	        value:Ext.getDom("testPlan_value").value,
	        store: Ext.create('Ext.data.Store', 
	        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1008}),
	        queryMode: 'local',
	        forceSelection: true
	    });
    	var status = Ext.create('Ext.form.field.ComboBox', {
    		renderTo:'status_div',
	        valueField:'dicCode',
	        displayField: 'dicName',
	        width:200,
	        value:Ext.getDom("status_value").value,
	        store: Ext.create('Ext.data.Store', 
	        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1003}),
	        queryMode: 'local',
	        forceSelection: true
	    });
    	
    	var prodVersion = Ext.create('Ext.form.field.ComboBox', {
    		renderTo:'prodVersion_div',
	        valueField:'dicCode',
	        displayField: 'dicName',
	        width:200,
	        value:Ext.getDom("prodVersion_value").value,
	        store: Ext.create('Ext.data.Store', 
	        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1005}),
	        queryMode: 'local',
	        forceSelection: true
	    });
    	
    	var repoter = new Ext.data.Store({
            proxy: {
                type: 'ajax',
                url: 'MantisController.do?method=getReporterList&operType=AssignTo',
                reader: {type: 'json'}
            },
            fields: ['dicCode','dicName']
        });
    	repoter.on('beforeload', function (store, options){
	            Ext.apply(store.proxy.extraParams,{'project':Ext.getDom("project_id_value").value,'defectId':Ext.getDom("defect_id_value").value});
	        });
    	repoter.load();
    	var assignto = Ext.create('Ext.form.field.ComboBox', {
    		renderTo:'assignto_div',
    		hiddenName:'assignto',
	        valueField:'dicCode',
	        displayField: 'dicName',
            width:200,
            value:Ext.getDom("assignto_value").value,
            queryMode: 'local',
	        forceSelection: true,
            store: repoter
        });
    	Ext.get('update_save_b').on('click', function(e){
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
            var paramsAll = 
            	{
            		'operType':'UpdateIssue',
            		'defectId':Ext.getDom("defect_id_value").value,
    				'prodVersion':prodVersion.getValue(),
    				'testStream':testStreams.getValue(),
    				'severity':severity.getValue(),
    				'priority':priority.getValue(),
    				'targetDate':targetDate.getValue()==null?'':Ext.util.Format.date(targetDate.getValue(),'Y-m-d'),
    				'subStream':subStream.getValue(),
    				'testPlan':testPlan.getValue(),
    				'tcId':Ext.getDom("tcId").value,
    				'dts':Ext.getDom("dts").value,
    				'title':Ext.getDom("title").value,
    				'descpt':Ext.getDom("descpt").value,
    				'remark':Ext.getDom("remark").value,
    				'status':status.getValue(),
    				'assignto':assignto.getValue()
    			};
            Ext.Ajax.request({
                url: 'MantisController.do?method=updateDefect',
                params:paramsAll,
                success: function(response, opts) 
                {
                	Ext.MessageBox.show({title: 'Status',msg:'Successfull',fn: showReloadResult,
            			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
                },
                failure: function(response, opts) 
                {
                	Ext.MessageBox.show({title: 'Status',msg:response.responseText,
            			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
                }
             });
    	});
    	function showReloadResult(btn){
    		window.location.href = "MantisController.do?method=issueDetail&defectId="+Ext.getDom("defect_id_value").value;
    	}
    });
    </script>
</head>
<body>
	<input type="hidden" id='defect_id_value' value="${defectId}" />
	<input type="hidden" id='project_id_value' value="${project}" />
	<input type="hidden" id='testStream_value' value="${testStream}" />
	<input type="hidden" id='severity_value' value="${severity}" />
	<input type="hidden" id='priority_value' value="${priority}" />
	<input type="hidden" id='targetDate_value' value="${targetDate}" />
	<input type="hidden" id='createBy_value' value="${createBy}" />
	<input type="hidden" id='status_value' value="${status}" />
	<input type="hidden" id='assignto_value' value="${assignto}"/>
	<input type="hidden" id='prodVersion_value' value="${prodVersion}"/>
	<input type="hidden" id='subStream_value' value="${subStream}"/>
	<input type="hidden" id='testPlan_value' value="${testPlan}"/>
	
	
	<table class="grouble_table" align="center" width='98%'>
		<tr>
			<td class="td_title">ID</td>
			<td class="td_title"><font color='red'>*</font>Test Streams</td>
			<td class="td_title">Severity</td>
			<td class="td_title">Priority</td>
			<td class="td_title">Date Submitted</td>
			<td class="td_title" width='100%'>Last Updated</td>
		</tr>
		<tr>
			<td class="td_value">${defectId}</td>
			<td class="td_value"><div id="testStream_div"></div></td>
			<td class="td_value" ><div id="severity_div"></div></td>
			<td class="td_value" ><div id="priority_div"></div></td>
			<td class="td_value">${createDate}</td>
			<td class="td_value">${modifyDate}</td>
		</tr>
		<tr>
			<td colspan=1 class="td_title">Reporter</td>
			<td colspan=5 class="td_value">${createByName}</td>
		</tr>
		<tr>
			<td class="td_title">Status</td>
			<td colspan=5 class="td_value"><div id="status_div"></div></td>
		</tr>
		<tr>
			<td class="td_title">Assign To</td>
			<td colspan=5 class="td_value"><div id="assignto_div"></div></td>
		</tr>
		<tr>
			<td colspan=1 class="td_title">Product Version</td>
			<td colspan=5 class="td_value"><div id="prodVersion_div"></div></td>
		</tr>
		<tr>
			<td colspan=1 class="td_title"><font color='red'>*</font>Sub Stream/Sub Module</td>
			<td colspan=5 class="td_value"><div id="subStream_div"></div></td>
		</tr>
		<tr>
		</tr>
		<tr>
			<td colspan=1 class="td_title"><font color='red'>*</font>Test Plan</td>
			<td colspan=5 class="td_value"><div id="testPlan_div"></div></td>
		</tr>
		<tr>
			<td colspan=1 class="td_title">Target Fix Date</td>
			<td colspan=5 class="td_value"><div id="targetDate_div"></div></td>
		</tr>
		<tr>
			<td colspan=1 class="td_title"><font color='red'>*</font>Test Case ID</td>
			<td colspan=5 class="td_value"><input type='text' id='tcId' name='tcId' style="width:200px" value='${tcId}'/></td>
		</tr>
		<tr>
			<td colspan=1 class="td_title">DTS</td>
			<td colspan=5 class="td_value"><input type='text' id='dts' name='dts' style="width:200px" value='${dts}'/></td>
		</tr>
		<tr>
			<td colspan=1 class="td_title"><font color='red'>*</font>Title</td>
			<td colspan=5 class="td_value"><input type='text' id='title' name='title' style="width:90%" value='${title}'/></td>
		</tr>
		<tr>
			<td colspan=1 class="td_title"><font color='red'>*</font>Description</td>
			<td colspan=5 class="td_value">
				<textarea id='descpt' class="tt_textarea">${descpt}</textarea>
			</td>
		</tr>
		<tr>
			<td colspan=1 class="td_title">Remark</td>
			<td colspan=5 class="td_value" id='title_td'>
				<textarea id='remark' class="tt_textarea">${remark}</textarea>
			</td>
		</tr>
		<tr>
			<td colspan=6 align="center">
				<input type="button" style="width:100px" id="update_save_b" value="Save"/>
			</td>
		</tr>
	</table>
</body>
</html>
