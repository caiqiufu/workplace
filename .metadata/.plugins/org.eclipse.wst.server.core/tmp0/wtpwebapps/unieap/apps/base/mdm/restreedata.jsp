<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<script type="text/javascript">
	var resDefId = "<%=request.getParameter("resDefId")%>";
</script>
<title>Res Tree Data</title>
    <script type="text/javascript">
    Ext.onReady(function(){
        Ext.tip.QuickTipManager.init();
         Ext.define('ResDataTree', {
            extend: 'Ext.data.Model',
            idProperty: 'id',
            fields: [
				{name: 'id',     type: 'string'},
				{name: 'text',     type: 'string'},
                {name: 'resDataId',     type: 'string'},
                {name: 'resId',     type: 'string'},
                {name: 'resCode', type: 'string'},
                {name: 'resName',     type: 'string'},
                {name: 'resDefId',     type: 'string'}
            ]
        });
        var resdataTreestore = Ext.create('Ext.data.TreeStore', {
            model: 'ResDataTree',
            proxy: {
            	type: 'ajax',
                url: 'MdMController.do?method=resdataTree&resDefId='+resDefId
            },
            nodeParam : "id",
            root:{  
                id:-1,  
                text:'Root',  
                leaf:false,  
                expanded:false  
            },
            autoLoad : false,
            folderSort: true
        });
        var resdataTreePanel = Ext.create('Ext.tree.Panel', {
            //width: 500,
            height: 500,
            //el : 'treeresdata',
            renderTo: Ext.getBody(),
       		layout: 'fit',
       		autoScroll : true,
            collapsible: false,
            rootVisible: true,
            useArrows: true,
            store: resdataTreestore,
            animate: true
        });
        /***function******************************************/
    });
    </script>
</head>
<body>
</body>
</html>
