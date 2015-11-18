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
        .td_title_file {
        	background-color:#F1F1F1;
        	font-weight:bolder;
        	height:50px;
        	width:100px;
        	border-collapse:collapse; 
        	border:1px black solid;
        	
        }
        .td_value_file {
        	background-color:#FAFAFA;
        	height:50px;
        	width:500px;
        	border-collapse:collapse; 
        	border:1px black solid;
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
		        Ext.get('submit_b').on('click', function(e){
		    		if(projectObj.getValue()==null||projectObj.getValue()=='-1'){
		    			Ext.MessageBox.show({title: 'Status',msg:"[Project] is required and can't be All.",
                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
		    			return;
		    		}
		    		var furl=fileUpload.form.findField('filePath').getValue(); 
	            	if(furl==null||furl==''){
		    			Ext.MessageBox.show({title: 'Status',msg:"Please choose excel file.",
                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
		    			return;
		    		}
	            	var type=furl.substring(furl.lastIndexOf("."),furl.length).toString();  
	            	if(!(type=='.xls'||type=='.xlsx')){
	            		Ext.MessageBox.show({title: 'Status',msg:"Only can upload excel file.",
                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
		    			return;
	            	}
		        	if(furl!=null&&furl!=''){
			    		fileUpload.submit({
	                        url: 'UploadController.do?method=uploadExcel',
	                        params:{'handlerId':'2','parameters':Ext.JSON.encode({'project':project})},
	                        waitMsg: 'Uploading your file...',
	                        success: function(form, action) {
	                         	var result = Ext.JSON.decode(action.response.responseText);
			                    if(result.isSuccess == 'failed')
			                    {
			                    	Ext.MessageBox.show({title: 'Status',msg:result.message,
	                         			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
			                    }else{
			                    	//window.location.href ="MantisController.do?method=issueDetail&defectId="+defectId;
			                    }
	                          },
	                         failure: function(form, action) {
	                        	 Ext.MessageBox.show({title: 'Status',msg:action.response.responseText,
	                    			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
	                         }
	                    });
		        	}else{
		        		//window.location.href ="MantisController.do?method=issueDetail&defectId="+defectId;
		        	}
		    		
		        });
		    });
			
	</script>
</head>
<body>
	<table class="grouble_table"  align="center" width='98%'>
		<tr class="grouble_table_tr">
			<td class="td_title"><font color='red'>*</font>Project</td>
			<td class="td_value"><div id="project"></div></td>
		</tr>
		<tr class="grouble_table_tr">
			<td class="td_title_file" height="50px">Upload Files</td>
			<td class="td_value_file">
				<div id="fi-form"></div> 
			</td>
		</tr>
		<tr class="grouble_table_tr">
			<td style ="width:80px" align="center">
				<font color='red'>*Required</font>
			</td>
			<td class="td_value" align="center">
				<input type="button" style ="width:80px" id="submit_b" value="Submit"/>
			</td>
		</tr>
	</table>
	
</body>
</html>
