<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>system configure management</title>
    <script type="text/javascript">
    
    Ext.onReady(function(){
    	
    	Ext.tip.QuickTipManager.init();
    	
    	/*** configure begin*********************************************************/
    	
    	Ext.define('datamodel', {
            extend: 'Ext.data.Model',
            fields:
            [
            	'id','type','typeDesc','name','value','description'
            ],
            idProperty: 'id'
        });
    	
    	var gridstore = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: <%=SYSConfig.getConfig().get("pageSize")%>,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'mdmController.do?method=configGrid',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'id', direction: 'DESC'}]
        });
    	var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
    		saveBtnText: '<%=UnieapConstants.getMessage("comm.save")%>', 
            cancelBtnText: '<%=UnieapConstants.getMessage("comm.cancel")%>', 
            clicksToMoveEditor: 1,
            autoCancel: false,
            listeners:{
            	edit:function(e){
            		Ext.MessageBox.confirm('<%=UnieapConstants.getMessage("comm.title.confirm")%>', 
            				'<%=UnieapConstants.getMessage("comm.confirm.update")%>', updateDatas);
            	}
            }
        });
    	var datagrid = Ext.create('Ext.grid.Panel', {
            store: gridstore,
            selModel: {
                selType: 'cellmodel'
            },
            viewConfig: {
                stripeRows: true
            },
            plugins: [rowEditing],
            columns: [
						{
						    text     : '<%=UnieapConstants.getMessage("comm.id")%>',
						    width    : 80,
						    sortable : false,
						    dataIndex: 'id'
						},
						{
						    text     : '<%=UnieapConstants.getMessage("comm.type")%>',
						    width    : 80,
						    sortable : false,
						    dataIndex: 'typeDesc'
						},{
						    text     : '<%=UnieapConstants.getMessage("comm.name")%>',
						    width    : 150,
						    sortable : false,
						    dataIndex: 'name'
						},{
						    text     : '<%=UnieapConstants.getMessage("comm.value")%>',
						    width    : 200,
						    sortable : false,
						    dataIndex: 'value',
						    editor: {
						        xtype: 'textfield'
						    }
						},{
						    text     : '<%=UnieapConstants.getMessage("comm.description")%>',
						    width    : 300,
						    sortable : false,
						    dataIndex: 'description'
						}
            ],
            flex: true,region: 'center'
        });
    	gridstore.load();
    	
    	function updateDatas(btn){
        	if(btn=='yes'){
        		//var record = datagrid.getSelectionModel().getSelection();
        		var record = gridstore.getModifiedRecords();
        		var id = record[0].data.id;
        		var newValue = record[0].data.value;
        		Ext.Ajax.request({
	                url: 'mdmController.do?method=configDeal',
	                params:{'operType':'Modify','id':id,'value':newValue},
	                success: function(response, opts){
	                	var result = Ext.JSON.decode(response.responseText);
	                	
	                	if(result.isSuccess == 'failed'){
	                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:result.message,
                     			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
	                    }else{
                         	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:'<%=UnieapConstants.getMessage("comm.success.save")%>',fn: showResult,
                        			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
	                    }
	                },
	                failure: function(form, action){
                   	 Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:action.response.responseText,
                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
                    }
	             });
        	}
        }
    	
    	function showResult(btn){
        	gridstore.reload();
        }
    	
    	/***tab panel***************************************************************/
    	var tabPanel = Ext.create('Ext.tab.Panel',{
	     	renderTo:'tabPanel',activeTab:0,layout: 'fit',
	     	defaults :{
	            autoScroll: true
	        },
	        items:
	        	[
					 {
						xtype: 'panel',layout: 'border',height:550,
					    title: '<%=UnieapConstants.getMessage("mdm.systemconfig.display.title")%>',
					    items : [
									datagrid
					             ],
					    closable: false
					}
	        	]
	   });
	});
    </script>
</head>
<body>
    <div id="tabPanel"></div>
</body>
</html>
