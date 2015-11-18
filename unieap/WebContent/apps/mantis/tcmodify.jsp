<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%
 
%>
<html>
<head>
<title>Modify Test Case</title>
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
    	var subStream = Ext.create('Ext.form.field.ComboBox', {
	        renderTo: 'subStream_div',
	        hiddenName:'subStream',
	        valueField:'dicCode',
	        displayField: 'dicName',
	        width:200,
	        value:Ext.getDom("subStream_value").value,
	        store: Ext.create('Ext.data.Store', 
	        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1007}),
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
	        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1015}),
	        queryMode: 'local',
	        forceSelection: true
	    });
    	Ext.get('update_save_b').on('click', function(e){
    		if(status.getValue()==null){
    			Ext.MessageBox.show({title: 'Status',msg:"[Status] is required.",
         			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
    			return;
    		}
    		if(testStreams.getValue()==null){
    			Ext.MessageBox.show({title: 'Status',msg:"[Test Stream] is required.",
         			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
    			return;
    		}
    		if(subStream.getValue()==null){
    			Ext.MessageBox.show({title: 'Status',msg:"[Sub Stream] is required.",
         			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
    			return;
    		}
    		if(Ext.get("tcCode").dom.value==''){
    			Ext.MessageBox.show({title: 'Status',msg:"[Test ID] is required.",
         			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
    			return;
    		}
    		if(Ext.get("tcName").dom.value==''){
    			Ext.MessageBox.show({title: 'Status',msg:"[Test Name] is required.",
         			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
    			return;
    		}
    		if(Ext.get("tcDescription").dom.value==''){
    			Ext.MessageBox.show({title: 'Status',msg:"[Description] is required.",
         			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
    			return;
    		}
    		if(Ext.get("testResult").dom.value==''){
    			Ext.MessageBox.show({title: 'Status',msg:"[Test Result] is required.",
         			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
    			return;
    		}
            var paramsAll = 
            	{
            		'operType':'UpdateTc',
            		'tcId':Ext.getDom("tcId_value").value,
    				'status':status.getValue(),
    				'testStream':testStreams.getValue(),
    				'subStream':subStream.getValue(),
    				'tcCode':Ext.getDom("tcCode").value,
    				'tcName':Ext.getDom("tcName").value,
    				'tcDescription':Ext.getDom("tcDescription").value,
    				'testResult':Ext.getDom("testResult").value,
    				'remark':Ext.getDom("remark").value
    			};
            Ext.MessageBox.wait('Processing...','Please wait...');
            Ext.Ajax.request({
                url: 'MantisController.do?method=updateTc',
                params:paramsAll,
                success: function(response, opts) 
                {
                	Ext.MessageBox.show({title: 'Status',msg:'Successfull',fn: showResult,
            			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
                	
                },
                failure: function(response, opts) 
                {
                	Ext.MessageBox.show({title: 'Status',msg:response.responseText,fn: showResult,
            			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
                }
             });
    	});
    	function showResult(btn){
    		Ext.MessageBox.hide();
    	}
    });
    </script>
</head>
<body>
	<input type="hidden" id='tcId_value' value="${tcId}" />
	<input type="hidden" id='tcCode_value' value="${tcCode}" />
	<input type="hidden" id='tcName_value' value="${tcName}" />
	<input type="hidden" id='tcDescription_value' value="${tcDescription}" />
	<input type="hidden" id='testStream_value' value="${testStream}" />
	<input type="hidden" id='testStreamDesc_value' value="${testStreamDesc}" />
	<input type="hidden" id='subStream_value' value="${subStream}" />
	<input type="hidden" id='subStreamDesc_value' value="${subStreamDesc}" />
	<input type="hidden" id='tester_value' value="${tester}"/>
	<input type="hidden" id='status_value' value="${status}"/>
	<input type="hidden" id='statusDesc_value' value="${statusDesc}"/>
	<input type="hidden" id='testResult_value' value="${testResult}"/>
	<input type="hidden" id='remark_value' value="${remark}"/>
	<input type="hidden" id='createDate_value' value="${createDate}"/>
	
	<table class="grouble_table" align="center" width='98%'>
		<tr>
			<td class="td_title">Tester</td>
			<td class="td_title"><font color='red'>*</font>Status</td>
			<td class="td_title"><font color='red'>*</font>Test Stream</td>
			<td class="td_title"><font color='red'>*</font>Test SubStream</td>
			<td class="td_title">Date Submitted</td>
			<td class="td_title" width='100%'>Last Updated</td>
		</tr>
		<tr>
			<td class="td_value">${tester}</td>
			<td class="td_value"><div id="status_div"></div></td>
			<td class="td_value" ><div id="testStream_div"></div></td>
			<td class="td_value" ><div id="subStream_div"></div></td>
			<td class="td_value">${createDate}</td>
			<td class="td_value">${modifyDate}</td>
		</tr>
		<tr>
			<td colspan=1 class="td_title"><font color='red'>*</font>ID</td>
			<td colspan=5 class="td_value"><input type='text' id='tcCode' name='tcCode' style="width:500px" value='${tcCode}'/></td>
		</tr>
		<tr>
			<td colspan=1 class="td_title"><font color='red'>*</font>Test Name</td>
			<td colspan=5 class="td_value"><input type='text' id='tcName' name='tcName' style="width:500px" value='${tcName}'/></td>
		</tr>
		<tr>
			<td colspan=1 class="td_title"><font color='red'>*</font>Description</td>
			<td colspan=5 class="td_value">
				<textarea id='tcDescription' class="tt_textarea">${tcDescription}</textarea>
			</td>
		</tr>
		<tr>
			<td colspan=1 class="td_title"><font color='red'>*</font>Test Result</td>
			<td colspan=5 class="td_value">
				<textarea id='testResult' class="tt_textarea">${testResult}</textarea>
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
