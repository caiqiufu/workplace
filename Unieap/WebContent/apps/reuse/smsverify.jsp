<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>验证码</title>
    <script type="text/javascript">
    Ext.onReady(function(){
    	Ext.tip.QuickTipManager.init();
    	/*******************dic group begin**********************************/
    	Ext.define('datamodel', {
            extend: 'Ext.data.Model',
            fields:
            [
            	'id','smsType','smsTypeDesc','fromBy','sendTo','content','verifyCode','sendDate','checked','checkedDesc','checkTimes','expired','expiredDesc','remark'
            ],
            idProperty: 'id'
        });
    	var gridstore = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: 15,
            remoteSort: true,
            proxy: 
            { type: 'ajax', url: 'ReuseController.do?method=smsverifyGrid',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: 
            [
            	{ property: 'createDate', direction: 'ASC'}
            ]
        });
    	var selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'single'});
    	var datagrid = Ext.create('Ext.grid.Panel', 
    	        {el : 'datagrid',layout: 'fit',columnLines: true,autoScroll:true,autoExpandColumn:'action',
    		   	 	selModel:selModel,title: '<%=UnieapConstants.getMessage("comm.title.list")%>',
    	   	   		store : gridstore,
    		   	   	columns:
    		   	   	[
    		   	   		{ text: "ID",dataIndex: 'id',sortable: false,width:80},
    		   	   		{ text: "验证码", dataIndex: 'verifyCode',sortable: false,width:120},
    		   	   		{ text: "请求渠道",dataIndex: 'fromBy',width:120,sortable: false},
    		   	   		{ text: "接收号码",dataIndex: 'sendTo',sortable: false,width:120},
    		   	   		{ text: "是否失效",dataIndex: 'expiredDesc',sortable: false},
    		   	   		{ text: "<%=UnieapConstants.getMessage("comm.remark")%>", dataIndex: 'remark',sortable: false}
    		   	   	],
	    		   	tbar:[
   		   	     	 {xtype: 'fieldcontainer',layout: 'hbox', id:'queryFields',
					    items: 
					    [
					        { xtype:'textfield',labelWidth:80, width:200,labelAlign: 'right',name: 'sendTo',fieldLabel: '接收号码'}
					    ]
					 },'-',
					 { xtype:'button',text:'<%=UnieapConstants.getMessage("comm.search")%>',iconAlign:'right',pressed :true,icon:'/Unieap/unieap/js/common/images/search-trigger.png',
	    	                handler: function (){
	    	                	query();
	    	                }
		                },'-',
		                { xtype:'button',text:'<%=UnieapConstants.getMessage("comm.reset")%>',iconAlign: 'right',pressed :true,icon :'/Unieap/unieap/js/common/images/clear-trigger.gif',
	    	                handler: function ()
	    	                {
	    	                	var queryFields = Ext.getCmp('queryFields').items.items;
	    	                	queryFields[0].setValue('');
	    	                }
		                }],
    	    	   	bbar:new Ext.PagingToolbar(
    	    	   	{ store : gridstore,displayInfo: true})
    	        	
    	        });
   	   datagrid.render();
       gridstore.loadPage(1);
       function query(){
    	 var queryFields = Ext.getCmp('queryFields').items.items;
       	 var sendTo=queryFields[0].getValue(); 
           queryPara = 
           	{
          		 sendTo:sendTo
              };
           gridstore.load({params:queryPara});
       }
 	});
    </script>
</head>
<body>
    <div id="datagrid"></div>
</body>
</html>