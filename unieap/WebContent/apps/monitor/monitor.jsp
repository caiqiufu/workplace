<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>complain management</title>
    <script type="text/javascript">
    
    Ext.onReady(function(){
    	
    	Ext.tip.QuickTipManager.init();
    	/***chart configure*********************/
    	Ext.define('serviceRequestChartDatamodel', {
            extend: 'Ext.data.Model',
            fields:
            [
				'createDate','C002','querySubLifeCycle','C003'
            ],
            idProperty: 'createDate'
        });

    	var serviceRequestChartstore = Ext.create('Ext.data.Store', {
            model: 'serviceRequestChartDatamodel',
            pageSize: <%=SYSConfig.getConfig().get("pageSize")%>,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'monitorController.do?method=serviceRequestChart&bizCode=C002',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'createDate', direction: 'DESC'}]
        });
    	serviceRequestChartstore.load();
		var serviceRequestChart = Ext.create('Ext.chart.Chart', {
				style: 'background:#fff',
	            animate: true,
	            store: serviceRequestChartstore,
	            shadow: true,
	            legend: {
	                position: 'right'
	            },
	            axes: [{
	                type: 'Numeric',
	                minimum: 0,
	                maximum:50,
	                position: 'left',
	                fields: ['requestAmount'],
	                title: 'Number of Hits',
	                minorTickSteps: 1,
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
	                fields: ['createDate'],
	                title: 'Update Per 5 Minutes'
	            }],
	            series: [{
	                type: 'line',
	                highlight: {
	                    size: 7,
	                    radius: 7
	                },
	                axis: 'left',
	                xField: 'createDate',
	                yField: 'C002',
	                markerConfig: {
	                    type: 'cross',
	                    size: 4,
	                    radius: 4,
	                    'stroke-width': 0
	                }
	            },{
	                type: 'line',
	                highlight: {
	                    size: 7,
	                    radius: 7
	                },
	                axis: 'left',
	                xField: 'createDate',
	                yField: 'querySubLifeCycle',
	                markerConfig: {
	                    type: 'cross',
	                    size: 4,
	                    radius: 4,
	                    'stroke-width': 0
	                }
	            },{
	                type: 'line',
	                highlight: {
	                    size: 7,
	                    radius: 7
	                },
	                axis: 'left',
	                xField: 'createDate',
	                yField: 'C003',
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
					    items : [serviceRequestChart],
					    closable: false,
					    tbar: [{
					    	pressed :true,iconCls:'save',
				            text: '<%=UnieapConstants.getMessage("comm.save.chart")%>',
				            handler: function() {
				                Ext.MessageBox.confirm('<%=UnieapConstants.getMessage("comm.title.confirm.download")%>', '<%=UnieapConstants.getMessage("comm.confirm.save.chart")%>', function(choice){
				                    if(choice == 'yes'){
				                    	Ext.draw.engine.ImageExporter.defaultUrl = 'fileController.do?method=svgServer';
				                    	serviceRequestChart.save({
				                            type: 'image/png'
				                        });
				                    }
				                });
				            }
				        }, {
				        	id:'serviceRequestRefresh',pressed :true,iconCls:'refresh',
				            text: '<%=UnieapConstants.getMessage("comm.refresh")%>',
				            handler: function() {
				            	Ext.getCmp('serviceRequestRefresh').disable();
				            	serviceRequestChartstore.load();
				            	var d = new Ext.util.DelayedTask(function(){  
				            		Ext.getCmp('serviceRequestRefresh').enable();  
				                });  
				                d.delay(10000);
				                Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:'<%=UnieapConstants.getMessage("comm.success.refresh")%>',
	                         		buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
				            }
				        }]
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
