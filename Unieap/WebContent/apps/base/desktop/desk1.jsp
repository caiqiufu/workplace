<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/unieap/unieap.jsp" %>
<%
	String menuHtml = (String) request.getAttribute(UnieapConstants.MENU);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Complex Layout</title>
<style type="text/css">
p {
    margin:5px;
}
.settings {
    background-image:url(../shared/icons/fam/folder_wrench.png);
}
.nav {
    background-image:url(../shared/icons/fam/folder_go.png);
}
.info {
    background-image:url(../shared/icons/fam/information.png);
}
</style>
<script type="text/javascript">
	//alert('test');
	var tabs;
    var menuJson = <%=menuHtml%>;
    Ext.onReady(function() {
        Ext.QuickTips.init();
        Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));
        //var menuJsons = Ext.JSON.decode();
        //var menuString = Ext.JSON.ecode(menuHtml);
        Ext.define('MenuTree', {
	        extend: 'Ext.data.Model',
	        fields: [{
	            name: "menuCode",
	            type: "string"
	        },{
	            name: "text",
	            type: "string"
	        },{
	            name: "url",
	            type: "string"
	        },{
	            name: "imgSrc",
	            type: "string"
	        },{
	            name: "title",
	            type: "string"
	        }]
	    });
        
        var store = Ext.create('Ext.data.TreeStore',{
        	model: 'MenuTree',
        	root:{
        		 expanded:true,
        		 text:'Menu',
        		 children: menuJson
        	},
        	sorters: [{
                property: 'leaf',
                direction: 'ASC'
            }, {
               property: 'text',
                direction: 'ASC'
            }]
        });
        // Go ahead and create the TreePanel now so that we can use it below
         var treePanel = Ext.create('Ext.tree.Panel', {
            id: 'tree-panel',
            region:'north',
            split: true,
            height: 500,
            minSize: 150,
            rootVisible: false,
            autoScroll: true,
            store: store,
            listeners:{
        	  itemclick : function(view,record,item,index,e){   
        	 	if(record.isLeaf()){ 
	        	     //record.appendChild({text:"草他吗的"}); 
        	 		openTab(record.data.id,record.data.url,record.data.text,record.data.imgSrc);     
	            }
        	 }
            }
        });
        
        var viewport = Ext.create('Ext.Viewport', {
            id: 'border-example',
            layout: 'border',
            items: [
            Ext.create('Ext.Component', {
                region: 'north',
                height: 32, // give north and south regions a height
                autoEl: {
                    tag: 'div',
                    html:'<p>north - generally for menus, toolbars and/or advertisements</p>'
                }
            }),{
                region: 'west',
                stateId: 'navigation-panel',
                id: 'west-panel', // see Ext.getCmp() below
                title: 'West',
                split: true,
                width: 200,
                minWidth: 175,
                maxWidth: 400,
                collapsible: true,
                animCollapse: true,
                margins: '0 0 0 5',
                items:[treePanel]
            },
            tabs = Ext.create('Ext.tab.Panel', {
                readerTo:'center',
                region: 'center', // a center region is ALWAYS required for border layout
                activeTab: 0,
		        defaults :{
		            autoScroll: true,
		            bodyPadding: 10
		        },
                items: []
            })]
        });
      });

	/**functions**/
    function openTab(id,href,text,imgSrc){
    	addTab (true,id,href,text,imgSrc);
    }
    function addTab (closable,id,href,text,imgSrc) {
    	var url = basePathUrl+"/"+href;
    	var windowName = text;
    	var imgSrc = imgSrc;
    	var tabItems = tabs.items.items;
    	if(tabItems.length == 0){
    		tabs.add({
		        	title:windowName,
		            xtype: 'panel',
		            html :'<iframe scrolling="no" frameborder="0" border="0" frameBorder="0" marginwidth="0" marginheight="0" src="'+url+'" style="width:100%;height:100%;"></iframe>',
		        	closable: !!closable
		        }).show();
	        
    	}else{
    		var isExist = false;
	    	Ext.each(tabItems,function(tab){
	    		if(tab.title == windowName){
	    			isExist = true;
	    			tab.show();
	    			//tab.loader.load();
	    		}
	    	});
	    	if(!isExist){
		        tabs.add({
		        	title:windowName,
		            xtype: 'panel',
		            html :'<iframe scrolling="no" frameborder="0" border="0" frameBorder="0" marginwidth="0" marginheight="0" src="'+url+'" style="width:100%;height:100%;"></iframe>',
		        	closable: !!closable
		        }).show();
	    	}
    	}
    }
    </script>
</head>
<body>
</body>
</html>
