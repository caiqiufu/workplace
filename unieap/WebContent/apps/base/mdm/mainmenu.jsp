<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<%@page import="java.text.SimpleDateFormat,com.unieap.base.vo.MenuVO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%>
<html>
  <head>
    <title>easy mobile</title>
    <style type="text/css">
    	.myBody
		{  
		    margin: 0;
		    background-color: #d9dee2; 
		}
    </style>
	<script type="text/javascript">
			
			function toManaement(href)
			{
				var url = basePathUrl+"/MdmController.do?method=menu";
				document.getElementById("mainframe").src=url;
			}
			function toFrame(href,title)
			{
				//var url = basePathUrl+"/"+href;
				//document.getElementById("mainframe").src=url;
				openTab('',href,title,'');
			}
			
			var userName = '<%=user.getUserName()%>';
			var tabs;
			Ext.onReady(function() {
		    	Ext.tip.QuickTipManager.init();
		    	
		    	var viewport = Ext.create('Ext.Viewport', {
		            id: 'border-example',
		            layout: 'border',
		            items: [
			            Ext.create('Ext.Component', {
			                region: 'north',
			                height: 60, // give north and south regions a height
			                autoEl: {
			                    tag: 'div',
			                    html:
			                    	'<table class="grouble_table" width="100%" >'+
			                    		'<tr class="grouble_table_tr">'+
						        			'<td colspan="3" align ="center" style ="font-weight:bolder;font-size:20pt;color:#4F4F4F;">Easy Mobile Management Platform</td>'+
						        			'<td width="20px" align ="right">Welcome : <%=user.getUserName()%></td>'+
						        		'</tr>'+
								        '<tr class="grouble_table_tr">'+
								        	'<td colspan="4" width="90%" align ="center">'+
								        		<% 
								        			MenuVO menu;
								        	        String href,title;
								        			List<MenuVO> menus = (List<MenuVO>)request.getAttribute("menus");
								        	
										        	for(int i = 0; i < menus.size(); i++)  
										            {  
										        		menu = menus.get(i);
										        		href = menu.getHref();
										        		title = menu.getMenuName();
										        		
										        %>
										             '<a style="text-decoration:underline" href="####" onclick="toFrame(\'<%=href%>\',\'<%=title%>\')" target="_self" title="<%=title%>"><%=title%></a> |'+
										        <%
										            }  
								        		%>
								        	'</td>'+
								        '</tr>'+
						      		'</table>'
			                }
			            }),
			            tabs = Ext.create('Ext.tab.Panel', {
			                readerTo:'center',
			                region: 'center', // a center region is ALWAYS required for border layout
			                activeTab: 0,
					        defaults :{
					            autoScroll: true,
					            bodyPadding: 0
					        },
			                items: []
			            })
			          ]
		        });
		    	
		    	
		    })
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
  <body class = 'myBody'></body>
</html>