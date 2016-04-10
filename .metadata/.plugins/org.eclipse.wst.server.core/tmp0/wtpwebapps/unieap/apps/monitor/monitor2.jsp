<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>complain management</title>
    <script type="text/javascript">
    
    Ext.onReady(function(){
    	
    	Ext.tip.QuickTipManager.init();
    	/***chart configure*********************/
    	Ext.define('datamodel', {
            extend: 'Ext.data.Model',
            fields:
            [
				'datetime','newamount'
            ],
            idProperty: 'id'
        });
    	
    	var chartstore = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: <%=SYSConfig.getConfig().get("pageSize")%>,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'easyMobileController.do?method=messageGrid&type=N',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'id', direction: 'DESC'}]
        });
    	chartstore.load();
		var chartServiceRequest = Ext.create('Ext.chart.Chart', {
				style: 'background:#fff',
	            animate: true,
	            store: chartstore,
	            shadow: true,
	            theme: 'Category1',
	            legend: {
	                position: 'right'
	            },
	            axes: [{
	                type: 'Numeric',
	                minimum: 0,
	                position: 'left',
	                fields: ['requestAmount'],
	                title: 'Number of Hits',
	                //minorTickSteps: 1,
	                grid: {
	                    odd: {
	                        opacity: 1,
	                        fill: '#ddd',
	                        stroke: '#bbb',
	                        'stroke-width': 0.5
	                    }
	                }
	            }, {
	                type: 'Category',
	                position: 'bottom',
	                fields: ['datetime'],
	                title: 'Month of the Year'
	            }],
	            series: [{
	                type: 'line',
	                highlight: {
	                    size: 7,
	                    radius: 7
	                },
	                axis: 'left',
	                xField: 'datetime',
	                yField: 'requestAmount',
	                markerConfig: {
	                    type: 'cross',
	                    size: 4,
	                    radius: 4,
	                    'stroke-width': 0
	                }
	            }]
			})

    	var tabPanel = Ext.create('Ext.tab.Panel',{
	     	renderTo:'tabPanel',activeTab:0,layout: 'fit',
	     	defaults :{
	            autoScroll: true
	        },
	        items:
	        	[
					{
					    xtype: 'panel',layout: 'border',height:530,
					    title: '<%=UnieapConstants.getMessage("monitor.esb.request.title")%>',
					    items : [chartServiceRequest],
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
