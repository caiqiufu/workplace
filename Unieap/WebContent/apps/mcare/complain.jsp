<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>complain management</title>
    <script type="text/javascript">
    Ext.onReady(function(){
    	Ext.tip.QuickTipManager.init();
    	 var queryPara;
    	 var queryform = Ext.create('Ext.form.Panel',{
 			fieldDefaults:
 			{ labelAlign: 'left', labelWidth: 90 },
 	        layout: 'fit',
 	        width : '100%',
      	   	frame : true,
      	    items:[
      	           { id:'queryFieldset', width: '100%',xtype:'fieldset',title: '<%=UnieapConstants.getMessage("comm.search")%>',
   	                 startCollapsed: true,collapsible: true, defaultType: 'textfield',
   	                	items:
   	                		[
 								{ xtype: 'fieldcontainer',layout: 'hbox',
 								    items: 
 								    [
 								        { xtype:'textfield',name: 'submitBy',fieldLabel: '<%=UnieapConstants.getMessage("mcare.complain.display.submitBy")%>'},
 								        {name: 'submitDateStart', fieldLabel: '<font color=red>*</font><%=UnieapConstants.getMessage("mcare.complain.display.submitDateStart")%>',format: 'Y-m-d h:i:s', xtype: 'datefield',allowBlank:false,
 						               		value:Ext.util.Format.date(Ext.Date.add(new Date(),Ext.Date.DAY,-1),"Y-m-d h:i:s")	
 						               	},
 						                {name: 'submitDateEnd', fieldLabel: '<font color=red>*</font><%=UnieapConstants.getMessage("mcare.complain.display.submitDateEnd")%>', format: 'Y-m-d h:i:s',xtype: 'datefield',allowBlank:false,
 						               		value:Ext.util.Format.date(new Date(),"Y-m-d h:i:s")	
 						                },
 						                { xtype:'button',iconCls:'search-trigger',text:'<%=UnieapConstants.getMessage("comm.search")%>',iconAlign: 'right',
 					    	                handler: function (){
 					    	                	var form = queryform.getForm();
 					    	                	if (form.isValid()){
	 					    	                	var submitBy=queryform.getForm().findField("submitBy").getValue(); 
	 					    	                    var submitDateStart=dateFormat(queryform.getForm().findField("submitDateStart").getValue()); 
	 					    	                    var submitDateEnd=dateFormat(queryform.getForm().findField("submitDateEnd").getValue()); 
	 					    	                    queryPara = 
	 					    	                    	{
	 					    	                    		submitBy:submitBy,
	 					    	                    		submitDateStart:submitDateStart,
	 					    	                    		submitDateEnd:submitDateEnd
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
            	'id','evalution','text','url','fileName','submitDate','submitBy'
            ],
            idProperty: 'id'
        });
    	var gridstore = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: 15,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'mcareController.do?method=complainGrid',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'submitBy', direction: 'DESC'}]
        });
    	gridstore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryPara);
        });
    	var selModel = Ext.create('Ext.selection.CheckboxModel');
        var datagrid = Ext.create('Ext.grid.Panel', 
               {el : 'datagrid',layout: 'fit',columnLines: true,autoScroll:true,
       	   	 	selModel:selModel,
          	   	store : gridstore,
       	   	   	columns:
       	   	   	[
       	   	   		{ text: "<%=UnieapConstants.getMessage("mcare.complain.display.evalution")%>", dataIndex: 'evalution',sortable: false,width:50},
       	   			{ text: "<%=UnieapConstants.getMessage("mcare.complain.display.text")%>", dataIndex: 'text',flex: true, sortable: false,width:500,
		       	   	  	renderer: function (value, meta, record){
							var max = 150;
							meta.tdAttr = 'data-qtip="' + value + '"';
							return value.length < max ? value : value.substring(0, max - 3) + '...';
						}	
       	   			},
			       	{ text: "<%=UnieapConstants.getMessage("mcare.complain.display.url")%>", dataIndex: 'url', sortable: false,width:150,
	       	   			renderer: function (value, meta, record){
	       	   				if(value!=null&&value!=''){
	       	   					var fileName = record.get("fileName");
	       	   					meta.tdAttr = 'data-qtip="' + fileName + '"';
								return '<span style="margin-right:10px"><a href="#"  onclick=showPicture("'+value+'")>'+fileName+'</a></span>';
	       	   				}else{
	       	   					return '';
	       	   				}
						}	
			       	},
			       	{ text: "<%=UnieapConstants.getMessage("mcare.complain.display.submitDate")%>", dataIndex: 'submitDate', sortable: false,width:150},
			       	{ text: "<%=UnieapConstants.getMessage("mcare.complain.display.submitBy")%>", dataIndex: 'submitBy', sortable: false,width:120}
       	   	   	],
	       	   	tbar:[queryform],
           	   	bbar:new Ext.PagingToolbar(
           	   	{ store : gridstore,displayInfo: true})
            });
    	datagrid.render();
	});
    	function showPicture(url){
    		var path = "<%=path%>";
    		url = path+url;
    		alert(url);
    		var dataWin = Ext.widget('window', 
    	                { title: '<%=UnieapConstants.getMessage("comm.data")%>', closeAction: 'close',width: 500, height: 550, autoScroll: true,  modal: true, 
    			 			buttons:[   
    			            	{text:"<%=UnieapConstants.getMessage("comm.cancel")%>",
    			            		handler: function(){
    			            			dataWin.close();
    		                        }	
    			            	}   
    			        	] ,
    			 			items:
    			 				[
										{  
										    xtype:'panel',  
										    html:'<img src="'+url+'"/>'  
										}
    			 			    ] 
    	                });
    		 dataWin.doLayout();
    		 dataWin.show();
    	}
    </script>
</head>
<body>
    <div id="datagrid"></div>
</body>
</html>
