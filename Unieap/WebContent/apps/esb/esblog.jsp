<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>esb log</title>
    <script type="text/javascript">
    Ext.onReady(function(){
    	function dateFormat(value){ 
    	    if(null != value){ 
    	        return Ext.Date.format(new Date(value),'Y-m-d H:i:s'); 
    	    }else{ 
    	        return null; 
    	    } 
    	}
    	Ext.tip.QuickTipManager.init();
    	 var queryPara;
    	 var queryform = Ext.create('Ext.form.Panel',{
 			fieldDefaults:
 			{ labelAlign: 'left', labelWidth: 60 },
 	        layout: 'fit',
 	        width : '100%',
      	   	frame : true,
      	    items:[
      	           { id:'queryFieldset', width: '100%',xtype:'fieldset',title: '<%=UnieapConstants.getMessage("comm.search")%>',
   	                 collapsible: true, collapsed :false,defaultType: 'textfield',
   	                	items:
   	                		[
 								{ xtype: 'fieldcontainer',layout: 'hbox',
 									defaults : { 
										margins : "5" 
									},
 								    items: 
 								    [
 								        { xtype:'textfield',name: 'serviceNumber',fieldLabel: '<%=UnieapConstants.getMessage("esb.log.display.serviceNumber")%>'},
 								        { name: 'bizCode',fieldLabel: '<%=UnieapConstants.getMessage("esb.log.display.bizCode")%>',
 					    	                xtype: 'combo', anchor:'95%',emptyText: '...',editable:false,allowBlank:true,
 					    	                displayField:   'dicName',
 				                            valueField:     'dicCode',
 				                            store:  Ext.create('Ext.data.Store', {
 				                                fields : ['dicCode', 'dicName'],
 				                                data:UnieapDicdata ._bizHandlerOpt
 				                             })
 				    	                },
 								        { xtype:'textfield',name:'resultCode',fieldLabel: '<%=UnieapConstants.getMessage("esb.log.display.resultCode")%>'}
 								    ]
 								},
 								{xtype: 'fieldcontainer',layout: 'hbox',
 									defaults : { 
										margins : "5" 
									},
 					                items: 
 					                [
 						               	{name: 'requestTimeStart', fieldLabel: '<font color=red>*</font><%=UnieapConstants.getMessage("esb.log.display.requestTimeStart")%>',format: 'Y-m-d h:i:s', xtype: 'datefield',allowBlank:false,
 						               		value:Ext.util.Format.date(Ext.Date.add(new Date(),Ext.Date.DAY,-1),"Y-m-d h:i:s")	
 						               	},
 						                {name: 'requestTimeEnd', fieldLabel: '<font color=red>*</font><%=UnieapConstants.getMessage("esb.log.display.requestTimeEnd")%>', format: 'Y-m-d h:i:s',xtype: 'datefield',allowBlank:false,
 						               		value:Ext.util.Format.date(new Date(),"Y-m-d h:i:s")	
 						                },
 						                { xtype:'button',iconCls:'search-trigger',text:'<%=UnieapConstants.getMessage("comm.search")%>',iconAlign: 'right',
 					    	                handler: function (){
 					    	                	var form = queryform.getForm();
 					    	                	if (form.isValid()){
	 					    	                	var serviceNumber=queryform.getForm().findField("serviceNumber").getValue(); 
	 					    	                    var bizCode=queryform.getForm().findField("bizCode").getValue();
	 					    	                    var resultCode=queryform.getForm().findField("resultCode").getValue(); 
	 					    	                    var requestTimeStart=dateFormat(queryform.getForm().findField("requestTimeStart").getValue()); 
	 					    	                    var requestTimeEnd=dateFormat(queryform.getForm().findField("requestTimeEnd").getValue()); 
	 					    	                    queryPara = 
	 					    	                    	{
	 					    	                    		serviceNumber:serviceNumber,
	 					    	                    		bizCode:bizCode,
	 					    	                    		resultCode:resultCode,
	 					    	                         	requestTimeStart:requestTimeStart,
	 					    	                         	requestTimeEnd:requestTimeEnd
	 					   	                        	};
	 					    	                   gridstore.load({params:queryPara});
 					    	                	}
 					    	                }
 						                },
 						                { xtype:'button',iconCls:'clear-trigger',text:'<%=UnieapConstants.getMessage("comm.reset")%>',iconAlign: 'right',
 					    	                handler: function (){
 					    	                	queryform.getForm().reset(); 
 					    	                }
 						                }
 					                ]
 					            }
 					  	     ]
 					     }
      	           ]
 		});
    	 
    	Ext.define('datamodel', {
            extend: 'Ext.data.Model',
            fields:
            [
            	'logId','channelCode','bizCode','bizCodeDesc','serviceNumber','transactionId','requestTime','responseTime',
            	'systemCode','resultCode','resultDesc','executeTime','sourceSystem','destSystem'
            ],
            idProperty: 'logId'
        });
    	var gridstore = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: 15,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'esbController.do?method=esblogGrid',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'logId', direction: 'DESC'}]
        });
    	gridstore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryPara);
        });
       	var selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'single',listeners:{
  			select:function(model,record,index){
  				var logId= record.get("logId");
  				var myMask = new Ext.LoadMask(Ext.getBody(),{msg:'<%=UnieapConstants.getMessage("comm.process")%>'}); 
  				myMask.show(); 
	        	Ext.Ajax.request({
	                url: 'esbController.do?method=getEsblog',
	                params:{'logId':logId},
	                success: function(response, opts){
	                	if (myMask != undefined){ myMask.hide();}
	                	var result = Ext.JSON.decode(response.responseText);
	                	if(result.isSuccess == 'failed'){
	                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:result.message,
                  			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
	                    }else{
	                    	var requestInfoString = '<div><strong><%=UnieapConstants.getMessage("esb.log.display.serviceNumber")%></strong> : '+result.serviceNumber+'</div>'+
	    						'<div><strong><%=UnieapConstants.getMessage("esb.log.display.bizCode")%></strong> : '+result.bizCodeDesc+'</div>'+
	    						'<div><strong><%=UnieapConstants.getMessage("esb.log.display.requestInfoDetails")%></strong> :</div>'+
	    						'<div><xmp>'+result.requestInfoDetails+'</xmp></div>'
	                    	Ext.getCmp('requestInfoDetailsPanel').update(requestInfoString);
	    						
	    					var responseInfoString = '<div><strong><%=UnieapConstants.getMessage("esb.log.display.serviceNumber")%></strong> : '+result.serviceNumber+'</div>'+
	    						'<div><strong><%=UnieapConstants.getMessage("esb.log.display.bizCode")%></strong> : '+result.bizCodeDesc+'</div>'+
	    						'<div><strong><%=UnieapConstants.getMessage("esb.log.display.responseInfoDetails")%></strong> :</div>'+
	    						'<div><xmp>'+result.responseInfoDetails+'</xmp></div>'
	                    	Ext.getCmp('responseInfoDetailsPanel').update(responseInfoString);
	                    }
	                },
	                failure: function(response, opts){
	                		if (myMask != undefined){ myMask.hide();}
		                	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:response.responseText,
	             				buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
	                	}
	             });
	  			}
			}
		});
    	
        var datagrid = Ext.create('Ext.grid.Panel', 
               {el : 'datagrid',layout: 'fit',columnLines: true,autoScroll:true,
       	   	 	selModel:selModel,
          	   	store : gridstore,
       	   	   	columns:
       	   	   	[
       	   	   		{ text: "<%=UnieapConstants.getMessage("esb.log.display.logId")%>", dataIndex: 'logId',sortable: false,width:80},
       	   	  		{ text: "<%=UnieapConstants.getMessage("esb.log.display.channelCode")%>", dataIndex: 'channelCode', sortable: false,width:60},
       	   			{ text: "<%=UnieapConstants.getMessage("esb.log.display.bizCode")%>", dataIndex: 'bizCodeDesc', sortable: false,width:80,
		       	   	  	renderer: function (value, meta, record){
							var max = 150;
							meta.tdAttr = 'data-qtip="' + value + '"';
							return value.length < max ? value : value.substring(0, max - 3) + '...';
						}	
       	   			},
			       	{ text: "<%=UnieapConstants.getMessage("esb.log.display.serviceNumber")%>", dataIndex: 'serviceNumber', sortable: false,width:80},
			       	{ text: "<%=UnieapConstants.getMessage("esb.log.display.transactionId")%>", dataIndex: 'transactionId', sortable: false,width:120},
			       	{ text: "<%=UnieapConstants.getMessage("esb.log.display.requestTime")%>", dataIndex: 'requestTime', sortable: false,width:150},
			       	{ text: "<%=UnieapConstants.getMessage("esb.log.display.responseTime")%>", dataIndex: 'responseTime', sortable: false,width:150},
			       	{ text: "<%=UnieapConstants.getMessage("esb.log.display.resultCode")%>", dataIndex: 'resultCode', sortable: false,width:80},
			       	{ text: "<%=UnieapConstants.getMessage("esb.log.display.resultDesc")%>", dataIndex: 'resultDesc', sortable: false,width:120,
			       		renderer: function (value, meta, record){
							var max = 150;
							meta.tdAttr = 'data-qtip="' + value + '"';
							return value.length < max ? value : value.substring(0, max - 3) + '...';
						}	
			       	},
			       	{ text: "<%=UnieapConstants.getMessage("esb.log.display.executeTime")%>", dataIndex: 'executeTime', sortable: false,width:80},
			       	{ text: "<%=UnieapConstants.getMessage("esb.log.display.sourceSystem")%>", dataIndex: 'sourceSystem', sortable: false,width:80},
			       	{ text: "<%=UnieapConstants.getMessage("esb.log.display.destSystem")%>", dataIndex: 'destSystem', sortable: false,width:80}
       	   	   	],
	       	   	tbar:[queryform],
           	   	bbar:new Ext.PagingToolbar(
           	   	{ store : gridstore,displayInfo: true})
               	
               });
    	datagrid.render();
        var tabPanel = Ext.create('Ext.tab.Panel',{
	     	renderTo:'tabPanel',activeTab:0,layout: 'fit',
	        bodyStyle: 'padding:0px',
	        items:[
	               	{
	            	   xtype: 'panel',title:'<%=UnieapConstants.getMessage("comm.data")%>',closable: false,
	            	   items : [
		            	            {
		            	            	layout: 'column',
		            	                items: [
		            	                 {
		            	                	id:'requestInfoDetailsPanel',
		            	                    columnWidth: 1/2,
		            	                    padding: '5 0 5 5',
		            	                    title: '<%=UnieapConstants.getMessage("esb.log.display.requestInfoDetails")%>',
		            	                    autoScroll : true,
		            	                    startCollapsed:false,collapsible: true,
		            	                    html: "<div id='requestInfoDetailsDiv'></div>"
		            	                },{
		            	                	id:'responseInfoDetailsPanel',
		            	                    columnWidth: 1/2,
		            	                    padding: '5 0 5 5',
		            	                    title: '<%=UnieapConstants.getMessage("esb.log.display.responseInfoDetails")%>',
		            	                    autoScroll : true,
		            	                    startCollapsed: false,
		            	                    collapsible:true,
		            	                    html: "<div id='responseInfoDetailsDiv'></div>"
		            	                }]
		            	            }
	            	            ]
	            	}
               ]
	   });
	});
    </script>
</head>
<body>
    <div id="datagrid"></div>
    <div id="tabPanel"></div>
</body>
</html>
