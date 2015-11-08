<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>User</title>
    <script type="text/javascript">
    Ext.onReady(function(){
    	Ext.tip.QuickTipManager.init();
    	
    	Ext.define('reload', {
    	    extend: 'Ext.data.Model',
    	    fields: [ 'id','reloadSummary','reloadDetails' ],
    	    idProperty: 'reloadSummary'
    	});
    	
    	 var myData = [
    	               ['1','Reload Handler Cache Data','Reload handler cache data as dictioanry data,app resource configure data etc'],
    	               ['2','Reload System Configure Data', 'Reload system configure data as system parameters, bizhandler,esb interface configure info etc']
    	           ];
    	 var store = Ext.create('Ext.data.ArrayStore', {
    	        model: 'reload',
    	        data: myData
    	    });
		var selectedRecord;
    	var grid = Ext.create('Ext.grid.Panel', {
    		 	el : 'datagrid',layout: 'fit', store: store, collapsible: false, multiSelect: false,
    	        forceFit: true, border: true, header: false,
    	        columns: [
    	            {text:'ID', width:20, sortable : false,dataIndex: 'id'},
    	            {text:'reloadSummary', width:80,sortable:true, dataIndex: 'reloadSummary'},
    	            {text:'reloadDetails',width:120,sortable : false, dataIndex: 'reloadDetails',
    	            	renderer: function (value, meta, record){
							var max = 150;
							meta.tdAttr = 'data-qtip="' + value + '"';
							return value.length < max ? value : value.substring(0, max - 3) + '...';
						}	
    	            },
    	            { menuDisabled: true,sortable: false, xtype: 'actioncolumn', text: "<%=UnieapConstants.getMessage("comm.operation")%>",width:80,
    	                items: [
    	                    {
	    	                	iconCls : 'refresh',
	    	                    tooltip: '<%=UnieapConstants.getMessage("comm.refresh")%>',
	    	                    handler: function(grid, rowIndex, colIndex) {
	    	                        selectedRecord = store.getAt(rowIndex);
	    	                        Ext.MessageBox.confirm('<%=UnieapConstants.getMessage("comm.title.confirm")%>', '<%=UnieapConstants.getMessage("comm.refresh.confirm")%>', refershDatas);
	    	                        
	    	                        
	    	                        
    	                    }
    	                }]
    	            }
    	        ]
    	    });
    	 grid.render();
    	 function refershDatas(btn){
    		 if(btn=='yes'){
    			 var id= selectedRecord.get("id");
    			 var myMask = new Ext.LoadMask(Ext.getBody(),{msg:'<%=UnieapConstants.getMessage("comm.processing")%>'}); 
    			 myMask.show(); 
		        	Ext.Ajax.request({
		                url: 'mdmController.do?method=refreshDeal',
		                params:{'id':id},
		                success: function(response, opts){
		                	if (myMask != undefined){ myMask.hide();}
		                	var result = Ext.JSON.decode(response.responseText);
		                	if(result.isSuccess == 'failed'){
		                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:result.message,
                      			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
		                    }else{
                          	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:'<%=UnieapConstants.getMessage("comm.success.refresh")%>',
                         		buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
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
    	
    </script>
</head>
<body>
    <div id="datagrid"></div>
</body>
</html>
