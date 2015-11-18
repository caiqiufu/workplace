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
        	background-color:#B5BCBB;
        	font-weight:bolder;
        	height:20px;
        	
        }
        .td_value {
        	background-color:#E9EAE2;
        	height:20px;
        	width:100px;
        	align:left;
        }
        .normal-row .x-grid-cell {
            background-color: #FDBDB2;
        }
        .pass-row .x-grid-cell {
            background-color: #BDC5B5;
        }
        .failed-row .x-grid-cell {
            background-color: #ECEF91;
        }
        .closed-row .x-grid-cell {
            background-color: #F4FAF9;
        }
        .block-row .x-grid-cell {
            background-color: #F7210B;
        }
    </style>
	<script type="text/javascript">
			var project = "<%=project%>";
			Ext.onReady(function() {
		    	Ext.tip.QuickTipManager.init();
		    	 
		    	function idRUL(value, metadata, record, rowIndex, colIndex, store){
		    		var tcId = record.get('tcId');
			    	return '<a href="MantisController.do?method=tcmodify&tcId='+tcId+'" target="_self" title="Modify Test Case">' + value + '</a>';
			    }
		    	var queryform = Ext.create('Ext.form.Panel',{
			        layout: 'fit',
			        width : '100%',
		     	   	frame : true,
		     	   	el:'queryform',
		     	   	id:'query_from',
		     	    items:[
		     	           { id:'queryFieldset', xtype:'fieldset',title: 'Search',
		  	                 startCollapsed: true,collapsible: true, defaultType: 'textfield',
		  	                	items:
		  	                		[
										{ xtype: 'fieldcontainer',layout: 'hbox',
										    items: 
										    [
												{ xtype:'textfield',name:'tcCode',fieldLabel: 'Test Code',labelWidth:100},
						    	                { name: 'subStream',fieldLabel: 'Sub Stream', labelWidth:100,
							    	                xtype: 'combo', 
							    	                emptyText:'any',
							    	                displayField:'dicName',
						                            valueField:'dicName',
						                            store:Ext.create('Ext.data.Store', 
						        			        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1007Opt}),
						    	                },
						    	                { xtype:'button',text:'Search',iconAlign: 'right',width:'60px',
							    	                handler: function (){
							    	                	var queryform =Ext.getCmp("query_from");
							    	                    var subStream=queryform.getForm().findField("subStream").getValue();
							    	                    var tcCode=queryform.getForm().findField("tcCode").getValue();
							    	                    var queryPara = 
							    	                    	{
							    	                    		'project':project==null?"":project,
							    	                         	'subStream':subStream==null?"":subStream,
							    	                         	'tcCode':tcCode==null?"":tcCode
							   	                        	};
							    	                    gridstore.load({params:queryPara});
							    	                }
								                },{width:'10px'},
								                { xtype:'button',text:'Reset',iconAlign: 'right',width:'60px',
							    	                handler: function ()
							    	                {
							    	                	Ext.getCmp("query_from").getForm().reset(); 
							    	                }
								                }
										    ]
										}
							  	     ]
							     }
		     	           ]
				});
				Ext.getCmp('queryFieldset').collapse(true);
		        queryform.render();
		        /***data grid********************************************/
		    	Ext.define('datamodel', {
		            extend: 'Ext.data.Model',
		            fields:
		            	[
		             	'tcId','tcCode','tcName','tcDescription','testStream','testStreamDesc','subStream',
		             	'subStreamDesc','tester','status','statusDesc','testResult','remark','createBy','createByName',
		             	'createDate','modifyBy','modifyByName','modifyDate'
		             ],
		             idProperty: 'tcId'
		        });
		        var gridstore = Ext.create('Ext.data.Store', {
		            model: 'datamodel',
		            remoteSort: true,
		            proxy: 
		            { type: 'ajax', url:'MantisController.do?method=testCasesGrid',
		                reader: 
		                {root: 'rows', totalProperty: 'totalCount'},
		                simpleSortMode: true
		            },
		            sorters: 
		            [{ property: 'tcId', direction: 'ASC'}],
		            groupField: 'subStream'
		        });
		        gridstore.on('beforeload', function (store, options){
		            Ext.apply(store.proxy.extraParams,{'project':project});
		        });
		        var datagrid = Ext.create('Ext.grid.Panel', {
		        	forceFit: true,
		        	layout: 'fit',
		            iconCls: 'icon-grid',
		            el : 'datagrid',
		            store: gridstore,
		            features: [{
		                id: 'group',
		                ftype: 'groupingsummary',
		                groupHeaderTpl: '{name}',
		                hideGroupedHeader: true,
		                enableGroupingMenu: false
		            }],
		            columns: [{
		                text: 'Test Case Code',
		                width: 100,
		                sortable: true,
		                renderer:idRUL,
		                dataIndex: 'tcCode',
		                hideable: false,
		                summaryType: 'count',
		                summaryRenderer: function(value, summaryData, dataIndex) {
		                    return ((value === 0 || value > 1) ? '(' + value + ' Test Cases)' : '(1 Test Cases)');
		                }
		            }, {
		                header: 'subStream',
		                width: 180,
		                sortable: false,
		                dataIndex: 'subStream'
		            }, {
		                header: 'tcName',
		                width: 280,
		                sortable: false,
		                dataIndex: 'tcName'
		            }, {
		                header: 'Status',
		                width: 180,
		                sortable: false,
		                dataIndex: 'statusDesc'
		            }, {
		                header: 'tester',
		                width: 180,
		                sortable: false,
		                dataIndex: 'tester'
		            }, {
		                header: 'testResult',
		                width: 180,
		                sortable: false,
		                dataIndex: 'testResult'
		            }],
		            viewConfig:{getRowClass:function(record,rowIndex,rowParams,store){ 
		   	 			var status = record.get('status');
		   	 			if(status=='1'){
		   	 				return 'normal-row';
		   	 			}
			   	 		if(status=='2'){
		   	 				return 'pass-row';
		   	 			}
				   	 	if(status=='3'){
		   	 				return 'failed-row';
		   	 			}
				   	 	if(status=='4'){
		   	 				return 'closed-row';
		   	 			}
				   	 	if(status=='5'){
		   	 				return 'block-row';
		   	 			}
		   	 			
                 }}
		        });
		        datagrid.render();
		        gridstore.loadPage(1);
		    });
			
	</script>
</head>
<body>
	<div id="queryform" style='height:200px'></div>
	<div id="datagrid" style='height:500px'></div>
	<p>
	<table class="grouble_table" align="center" style='width:100%'>
		<tr >
			<td style="background-color:#FDBDB2;width:20%">normal</td>
			<td style="background-color:#BDC5B5;width:20%">pass</td>
			<td style="background-color:#ECEF91;width:20%">failed</td>
			<td style="background-color:#F4FAF9;width:20%">closed</td>
			<td style="background-color:#F7210B;width:20%">block</td>
		</tr>
	</table>
</body>
</html>
