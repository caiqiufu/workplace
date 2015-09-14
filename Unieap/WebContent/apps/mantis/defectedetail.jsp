<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<%@page import="java.text.DecimalFormat;"%>
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
    	var dataWin = null;
	    var dataForm = null;
    	nodeEdit = function(obj,noteId){
    		var tbId = "td_"+noteId;
    		var nodeObj = Ext.getDom(tbId);
    		var oldDesc = nodeObj.innerText;
    		if (dataWin==null){
	            	dataForm = Ext.widget('form',
	            	{
	                    defaults:{labelAlign: 'left',anchor: '100% 100%'},
	                    bodyPadding:1,
	                    items:
                        [
                        	{ xtype:'textfield',hidden: true, name:'noteId'},
                        	{ xtype:'textfield',hidden: true, name:'defectId'},
                        	{ xtype:'textareafield', name:'description',growMin:300,growMax:800,allowBlank:false}
                        ],
	                    buttons: 
	                    [
		                    {id:'formCancel', text: 'Cancel',
		                        handler: function() 
		                        {
		                        	dataForm.getForm().reset();
		                        	dataWin.hide();
		                        }
		                    }, 
		                    {id:'formSubmit',text: 'Submit',
		                        handler: function() {
		                        	var form = dataForm.getForm();
		                        	 if (form.isValid()){
		                                 form.submit({
		                                     clientValidation: true,
		                                     waitMsg: 'Processing...',
		                                     method: 'POST',
		                                     params:{'operType':'Modify'},
		                                     url: 'MantisController.do?method=nodeDeal',
		                                     success: function(form, action) {
		                                    	var result = Ext.JSON.decode(action.response.responseText);
							                    if(result.isSuccess == 'failed')
							                    {
							                    	Ext.MessageBox.show({title: 'Status',msg:result.message,
			                                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
							                    }else{
	 		                                    	Ext.MessageBox.show({title: 'Status',msg:'Successfull',fn: showReloadResult,
		 		                               			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
							                    }
		                                     },
		                                     failure: function(form, action) {
		                                    	 Ext.MessageBox.show({title: 'Status',msg:action.response.responseText,
	                                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
		                                     }
		                                 });
		                        	 }
		                        }
		                    }
	                    ]
	                });
	                dataWin = Ext.widget('window', 
	                { title: 'Note Info', closeAction: 'hide', width: 700, height:300, layout: 'fit', modal: true, items: dataForm,defaultFocus: 'description' });
	            }
    		dataWin.show();
         	dataForm.getForm().setValues({'noteId':noteId,'defectId':Ext.getDom("defect_id_value").value,'description':oldDesc});
	    };
	    nodeDelete = function(obj,nodeId){
    		Ext.Ajax.request({
                url: 'MantisController.do?method=nodeDeal',
                params:{'operType':"Delete","noteId":nodeId,'defectId':Ext.getDom("defect_id_value").value},
                success: function(response, opts) 
                {
                	Ext.MessageBox.show({title: 'Status',msg:'Successfull',fn: showReloadResult,
            			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
                	//var tableObj = obj.parentNode.parentNode.parentNode.parentNode;
                	//tableObj.parentNode.removeChild(tableObj); 
            		//var tbObj = tableObj.childNodes;
            		//document.location.reload();
                },
                failure: function(response, opts) 
                {
                	Ext.MessageBox.show({title: 'Status',msg:response.responseText,
            			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
                }
             });
	    };
	    deleteAtt = function(obj,attaId){
	    	Ext.Ajax.request({
                url: 'MantisController.do?method=attDeal',
                params:{'operType':"Delete","attaId":attaId},
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
	    	
	    };
	    downLoad = function(obj,attaId){
	    	window.location.href = "UploadController.do?method=download&attaId="+attaId;
	    };
    	var statusV = Ext.create('Ext.form.field.ComboBox', {
    		renderTo: 'change_status_v',
	        hiddenName:'status',
	        valueField:'dicCode',
	        displayField: 'dicName',
	        width:200,
	        store: Ext.create('Ext.data.Store', 
	        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1003}),
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
    	var assigntoV = Ext.create('Ext.form.field.ComboBox', {
    		renderTo: 'assign_to_v',
    		hiddenName:'assignto',
	        valueField:'dicCode',
	        displayField: 'dicName',
            width: 200,
            queryMode: 'local',
	        forceSelection: true,
            store: repoter
        });
    	Ext.get('update_issue_b').on('click', function(e){
    		window.location.href = "MantisController.do?method=defectModify&defectId="+Ext.getDom("defect_id_value").value;
    	});
    	Ext.get('assign_to_b').on('click', function(e){
    		var assignUserCode = assigntoV.getValue();
    		Ext.Ajax.request({
                url: 'MantisController.do?method=updateDefect',
                params:{'operType':'AssignTo','assignto':assignUserCode,'defectId':Ext.getDom("defect_id_value").value},
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
    	
    	Ext.get('change_status_b').on('click', function(e){
    		var changeStatus = statusV.getValue();
    		var changeStatusOld = statusV.getRawValue();
    		if(changeStatus==changeStatusOld){
    			return;
    		}else{
	    		Ext.Ajax.request({
	                url: 'MantisController.do?method=updateDefect',
	                params:{'operType':'ChangeStatus','status':changeStatus,'defectId':Ext.getDom("defect_id_value").value},
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
    		}
    	});
    	
    	/***add node**/
    	Ext.get('add_node_b').on('click', function(e){
    		var nodeVlue = Ext.getDom("add_node_value").value;
    		if(nodeVlue==null||nodeVlue ==''){
    			Ext.MessageBox.show({
     	           title: 'Informational',
     	           msg: 'Please type in Note info.',
     	           buttons: Ext.MessageBox.OK,
     	           icon: Ext.MessageBox.INFO
     	       });
    		}else{
	    		Ext.Ajax.request({
	                url: 'MantisController.do?method=nodeDeal',
	                params:{'operType':"Add","description":nodeVlue,'defectId':Ext.getDom("defect_id_value").value},
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
    		}
    	});
    	function showReloadResult(btn){
    		document.location.reload();
    	}
    	function changeDesc(value, cellmeta, record, rowIndex, columnIndex, store) {
    		 var oldValue = record.get('oldValue');
    		 var newValue = record.get('newValue');
    	     return oldValue+' => '+newValue;
    	}
    	Ext.define('issueHistorydatamodel', {
	            extend: 'Ext.data.Model',
	            fields:
	            	[
	             	'logId','userName','modifyDate','modifyType','fieldName','oldValue','newValue','displayName'
	             ],
	             idProperty: 'logId'
	        });
    	 var gridstore = Ext.create('Ext.data.Store', {
	            model: 'issueHistorydatamodel',
	            remoteSort: true,
	            proxy: 
	            { type: 'ajax', url:'MantisController.do?method=viewIssueHistory&defectId='+Ext.getDom("defect_id_value").value,
	                reader: 
	                {root: 'rows', totalProperty: 'totalCount'},
	                simpleSortMode: true
	            },
	            sorters: 
	            [
	            	{ property: 'logId', direction: 'DESC'}
	            ]
	        });
    	 
    	  var selModel = Ext.create('Ext.selection.CheckboxModel');
	      var datagrid = Ext.create('Ext.grid.Panel', 
	        {el : 'issueHistorydatagrid',layout: 'fit',columnLines: true,autoScroll:true,
		   	 	selModel:selModel,
		   	    //title: 'Issues Change History',
	   	   		store : gridstore,
	   	   	    forceFit: true,
	   	   	    autoExpandColumn:'title',
		   	   	columns:
		   	   	[
					{ text: "Modify Date",dataIndex: 'modifyDate',width:100,renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
					{ text: "User Name", dataIndex: 'userName',width:80},
					{ text: "Field",dataIndex: 'displayName',width:150},
					{ text: "newValue",dataIndex: 'newValue',width:200,renderer :changeDesc}
		   	   	]
	        });
	        datagrid.render();
	        gridstore.loadPage(1);
	        /**upload files******************/
	        Ext.create('Ext.form.Panel', {
    	        width: 400,
    	        frame: true,
    	        renderTo: 'fi-form',   
    	        items: [{
    	            xtype: 'filefield',
    	            name: 'file',
    	            msgTarget: 'side',
    	            allowBlank: false,
    	            anchor: '100%',
    	            buttonText: 'Select a File...'
    	        }],
    	        buttons: [{
    	            text: 'Upload',
    	            handler: function() {
    	                var form = this.up('form').getForm();
    	                var defectId = Ext.getDom("defect_id_value").value;
    	                if(form.isValid()){
    	                    form.submit({
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
	                                    	Ext.MessageBox.show({title: 'Status',msg:'Successfull',fn: showReloadResult,
		                               			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
				                    }
                                  },
                                 failure: function(form, action) {
                                	 Ext.MessageBox.show({title: 'Status',msg:action.response.responseText,
                            			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
                                 }
    	                    });
    	                }
    	            }
    	        }]
    	    });
	        function showReloadResult(btn){
	    		document.location.reload();
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
	<input type="hidden" id='createBy_value' value="${createBy}" />
	<input type="hidden" id='status_value' value="${status}" />
	<input type="hidden" id='assignto_value' value="${assignto}"/>
	<input type="hidden" id='prodVersion_value' value="${prodVersion}"/>
	<input type="hidden" id='subStream_value' value="${subStream}"/>
	<input type="hidden" id='testPlan_value' value="${testPlan}"/>
	
	
	<table class="grouble_table" align="center" width='98%'>
		<tr>
			<td class="td_title">ID</td>
			<td class="td_title">Test Streams</td>
			<td class="td_title">Severity</td>
			<td class="td_title">Priority</td>
			<td class="td_title">Date Submitted</td>
			<td class="td_title" width='100%'>Last Updated</td>
		</tr>
		<tr>
			<td class="td_value" id='defect_id_td'>${defectId}</td>
			<td class="td_value" id='testStream_td'>${testStreamDesc}</td>
			<td class="td_value" id='severity_td'>${severityDesc}</td>
			<td class="td_value" id='priority_td'>${priorityDesc}</td>
			<td class="td_value">${createDate}</td>
			<td class="td_value">${modifyDate}</td>
		</tr>
		<tr>
			<td colspan=1 class="td_title">Reporter</td>
			<td colspan=5 class="td_value" id='createBy_td'>${createByName}</td>
		</tr>
		<tr>
			<td class="td_title">Status</td>
			<td colspan=5 class="td_value" id='status_td'>${statusDesc}</td>
		</tr>
		<tr>
			<td class="td_title">Assign To</td>
			<td colspan=5 class="td_value" id='assignto_td'>${assigntoName}</td>
		</tr>
		<tr>
			<td colspan=1 class="td_title">Product Version</td>
			<td colspan=5 class="td_value" id='prodVersion_td'>${prodVersionDesc}</td>
		</tr>
		<tr>
			<td colspan=1 class="td_title">Sub Stream/Sub Module</td>
			<td colspan=5 class="td_value" id='subStream_td'>${subStreamDesc}</td>
		</tr>
		<tr>
			<td colspan=1 class="td_title">Test Plan</td>
			<td colspan=5 class="td_value" id='testPlan_td'>${testPlanDesc}</td>
		</tr>
		<tr>
			<td colspan=1 class="td_title">Target Fix Date</td>
			<td colspan=5 class="td_value" id='testPlan_td'>${targetDate}</td>
		</tr>
		<tr>
			<td colspan=1 class="td_title">Test Case ID</td>
			<td colspan=5 class="td_value" id='tcId_td'>${tcId}</td>
		</tr>
		<tr>
			<td colspan=1 class="td_title">DTS</td>
			<td colspan=5 class="td_value" id='dts_td'>${dts}</td>
		</tr>
		<tr>
			<td colspan=1 class="td_title">Title</td>
			<td colspan=5 class="td_value" id='title_td'>${title}</td>
		</tr>
		<tr>
			<td colspan=1 class="td_title">Description</td>
			<td colspan=5 class="td_value" id='title_td'>
				<pre>${descpt}</pre>
			</td>
		</tr>
		<tr>
			<td colspan=1 class="td_title">Remark</td>
			<td colspan=5 class="td_value" id='title_td'>
				<pre>${remark}</pre>
			</td>
		</tr>
		<tr>
			<td class="td_title">Attached Files</td>
			<td colspan=5 class="td_value">
				<table width='98%' border="0" cellspacing="0" cellpadding="0">
					<c:forEach items="${attas}" var="c">
						<tr>
							<td>
								file name :
							</td>
							<td align="left">
								<a href="<%=path%>${c.filePath}">${c.fileName}</a>
							</td>
							<td>
								file size :
							</td>
							<td align="left">
								${c.fileSize} (kb)
							</td>
							<td>
								upload date :
							</td>
							<td align="left">
								${c.createDate}
							</td>
							<td align="left">
								<input type="button" style ="width:80px" onclick="deleteAtt(this,${c.attaId})"  value="Delete"/>
							</td>
						</tr>
						<c:if test="${c.fileType=='jpg'||c.fileType=='gif'||c.fileType=='png'}">
							<tr>
								<td colspan=7>
									<img height='200px' width='500px' src="<%=path%>${c.filePath}"  alt="${c.fileName}" />
								</td>
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan=6>
				<table align="left">
					<tr>
						<td width="120px" class ="grouble_table_td">
							<input type="button" style="display:block;width:100px" id="update_issue_b" value="Update Issue"/>
							<input type="button" style="display:none;width:100px" id="update_save_b" value="Update Save"/>
						</td>
						<td width="120px" class ="grouble_table_td">
							<input type="button" style ="width:80px" id="assign_to_b" value="Assign To"/>
						</td>
						<td width="120px" class ="grouble_table_td">
							<div id="assign_to_v"></div> 
						</td>
						<td width="120px" class ="grouble_table_td">
							<input type="button" style ="width:100px" id="change_status_b" value="Change Status To"/>
						</td>
						<td width="120px" class ="grouble_table_td">
							<div id="change_status_v"></div> 
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan=6 align ="left" style ="font-weight:bolder;font-size:12pt;color:black;">Upload File</td>
		</tr>
		<tr>
			<td class="td_title">Upload Files</td>
			<td colspan=5 class="td_value">
				<div id="fi-form"></div> 
			</td>
		</tr>
		<tr>
			<td colspan=6 align ="left" style ="font-weight:bolder;font-size:12pt;color:black;">Notes Info</td>
		</tr>
		<tr>
			<td colspan=6 valign="top">
				<c:forEach items="${notes}" var="c">
					<table class="grouble_table" align="center" style ="width:100%">
						<tr>
							<td class="td_title" height="50px">
								<table style ="width:100%">
									<tr class="grouble_table_tr" height="50px">
										<td colspan=2>
											${c.createByName}
										</td>
									</tr>
									<tr class="grouble_table_tr" height="50px">
										<td colspan=2>
											${c.createDate}
										</td>
									</tr>
									<tr class="grouble_table_tr" height="50px">
										<td>
											<input type="button" width="50px" onclick="nodeEdit(this,${c.noteId})"  value="Edit" />
										</td>
										<td>
											<input type="button" width="80px" onclick="nodeDelete(this,${c.noteId})" value="Delete"/>
										</td>
									</tr>
								</table>
							</td>
							<td id="td_${c.noteId}" rowspan=4>
								<pre id='descpt'>${c.description}</pre>
							</td>
						</tr>
					</table>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<td colspan=6>
				<table align="center" style ="width:100%" class="grouble_table">
					<tr class="grouble_table_tr">
						<td colspan=2 style ="font-weight:bolder;font-size:12pt;color:black;">Add Node</td>
					</tr>
					<tr class="grouble_table_tr">
						<td class="td_title">Node</td>
						<td>
							<textarea id='add_node_value' class="tt_textarea"></textarea>
						</td>
					</tr>
					<tr class="grouble_table_tr">
						<td colspan=2 align="center">
							<input type="button" style ="width:120px" id="add_node_b" value="Add Note"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan=6 align ="left" style ="font-weight:bolder;font-size:12pt;color:black;">Issue History</td>
		</tr>
		<tr>
			<td colspan=6 >
				<div id="issueHistorydatagrid" style='height:500px'></div>
			</td>
		</tr>
	</table>
</body>
</html>
