<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/unieap/unieap.jsp" %>
<%
	String menuHtml = (String) request.getAttribute("MENU");
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
    var userName = '<%=user.getUserName()%>';
	var tabs;
    var menuJson = <%=menuHtml%>;
    function toFrame(href,title){
		openTab('',href,title,'');
	}
    Ext.onReady(function() {
        Ext.QuickTips.init();
        Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider'));
        //var menuJsons = Ext.JSON.decode();
        //var menuString = Ext.JSON.ecode(menuHtml);
        Ext.define('MenuTree', {
	        extend: 'Ext.data.Model',
	        fields: [{
	            name: "id",
	            type: "string"
	        },{
	            name: "text",
	            type: "string"
	        },{
	            name: "href1",
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
        		 text:'Menu',
        		 children: menuJson
        		 //children:[{id:11,text:'111a',leaf:true,href:'mdmController.do?method=dic'},{id:12,text:'112c',leaf:true}]
        	}
        });
        // Go ahead and create the TreePanel now so that we can use it below
         var treePanel = Ext.create('Ext.tree.Panel', {
            id: 'tree-panel',
            region:'north',
            split: true,
            loadMask: true,
            useArrows: true,
            animate: false,
            height: '100%',
            minSize: 150,
            rootVisible: false,
            autoScroll: true,
            store: store,
            listeners:{
        	  itemclick : function(view,record,item,index,e){   
        		e.stopEvent();
        	 	if(record.isLeaf()){ 
	        	     //record.appendChild({text:"草他吗的"}); 
        	 		//openTab(record.data.id,record.data.href,record.data.text,record.data.imgSrc);
        	 		toFrame(record.data.href,record.data.text,record.data.text);
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
	                    html:'<table><tr><td>Welcome : </td><td>'+userName+'</td><td><marquee scrollAmount=2 width=800 direction="right"><div id="tipInfo">Easy Mobile Platform</div></marquee></td></tr></table>'
	                }
	            }),
	            {
	                region: 'west',
	                stateId: 'navigation-panel',
	                id: 'west-panel', // see Ext.getCmp() below
	                title: 'Menu',
	                split: true,
	                width: 200,
	                minWidth: 175,
	                maxWidth: 400,
	                collapsible: false,
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
	            })
	          ]
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
			            html :'<iframe scrolling="yes" frameborder="0" border="0" frameBorder="0" marginwidth="0" marginheight="0" src="'+url+'" style="width:100%;height:100%;"></iframe>',
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
			            html :'<iframe scrolling="yes" frameborder="0" border="0" frameBorder="0" marginwidth="0" marginheight="0" src="'+url+'" style="width:100%;height:100%;"></iframe>',
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
