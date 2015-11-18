<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<%
	String project = (String)request.getAttribute("project");
%>
<html>
<head>
<script type="text/javascript"
	src="<%=path%>/unieap/js/ext-4.2.1/examples/example-data.js"></script>
<title>My View</title>
    <style type="text/css">
    	.new-row .x-grid-cell {
            background-color: #FDBDB2;
        }
        .assign-row .x-grid-cell {
            background-color: #BDC5B5;
        }
        .confirmed-row .x-grid-cell {
            background-color: #ECEF91;
        }
        .pendingfix-row .x-grid-cell {
            background-color: #F4FAF9;
        }
        .reject-row .x-grid-cell {
            background-color: #F7210B;
        }
        .pendingretest-row .x-grid-cell {
            background-color: #F4FAF9;
        }
        .resolve-row .x-grid-cell {
            background-color: #9ECCC1;
        }
    </style>
    <script type="text/javascript">
    var project = "<%=project%>";
    Ext.onReady(function() {
    	Ext.get('status_d_b').on('click', function(e){
    		closeAll();
    		defectStatusStore.load({params:{'project':project}});
    		panelStatusDaily.show();
    		
    	});
    	Ext.get('status_c_b').on('click', function(e){
    		closeAll();
    		defectStatusCurrentlyStore.load({params:{'project':project}});
    		panelStatusCurrently.show();
    		
    	});
    	
    	Ext.draw.engine.ImageExporter.defaultUrl = 'UploadController.do?method=svgServer';
    	var downloadChart = function(chart){
            Ext.MessageBox.confirm('Confirm Download', 'Would you like to download the chart as an image?', function(choice){
                if(choice == 'yes'){
                    chart.save({
                        type: 'image/png'
                    });
                }
            });
        };
        var defectStatusStore = new Ext.data.Store({
            proxy: {
                type: 'ajax',
                url: 'MantisController.do?method=getDefectReport&operType=Status',
                reader: {type: 'json'}
            },
            fields: ['datetime','new','assign','confirmed','pendingFix','reject','pendingRetest','resolve','close']
        });
        defectStatusStore.on('beforeload', function (store, options){
	            Ext.apply(store.proxy.extraParams,{'project':project});
	        });
        defectStatusStore.load();
    	var defectStatusChar = Ext.create('Ext.chart.Chart',{
    		style: 'background:#fff',
    		animate: true,
            store: defectStatusStore,
            shadow: true,
            legend: {
                position: 'right'
            },
            //insetPadding: 30,
            axes: [{
                type: 'Numeric',
                minimum: 0,
                position: 'left',
                fields: ['new','assign','confirmed','pendingFix','reject','pendingRetest','resolve','close'],
                title: 'Number',
                grid: {
                    odd: {
                        opacity: 1,
                        fill: '#ddd',
                        stroke: '#bbb',
                        'stroke-width': 0.5
                    }
                },
                label: {
                    renderer: Ext.util.Format.numberRenderer('0,0'),
                    font: '10px Arial'
                }
            },{
                type: 'Category',
                position: 'bottom',
                fields: ['datetime'],
                title: 'Date Time',
                label: {
                    font: '11px Arial',
                    rotate:{
                        degrees: 290
                    },
                    renderer: function(name) {
                        //return name.substr(0, 3) + ' 07';
                        return name;
                    }
                }
            }],
            series: [
              {
                type: 'line',
                axis: 'left',
                xField: 'datetime',
                yField: 'new',
                tips: {
                    trackMouse: true,
                    width: 80,
                    height: 40,
                    renderer: function(storeItem, item) {
                        this.setTitle(storeItem.get('datetime'));
                        this.update(storeItem.get('new'));
                    }
                },
                highlight:true
            },{
                type: 'line',
                axis: 'left',
                xField: 'datetime',
                yField: 'assign',
                tips: {
                    trackMouse: true,
                    width: 80,
                    height: 40,
                    renderer: function(storeItem, item) {
                        this.setTitle(storeItem.get('datetime'));
                        this.update(storeItem.get('assign'));
                    }
                },
                highlight:true
            },{
                type: 'line',
                axis: 'left',
                xField: 'datetime',
                yField: 'confirmed',
                tips: {
                    trackMouse: true,
                    width: 80,
                    height: 40,
                    renderer: function(storeItem, item) {
                        this.setTitle(storeItem.get('datetime'));
                        this.update(storeItem.get('confirmed'));
                    }
                },
                highlight:true
            },{
                type: 'line',
                axis: 'left',
                xField: 'datetime',
                yField: 'pendingFix',
                tips: {
                    trackMouse: true,
                    width: 80,
                    height: 40,
                    renderer: function(storeItem, item) {
                        this.setTitle(storeItem.get('datetime'));
                        this.update(storeItem.get('pendingFix'));
                    }
                },
                highlight:true
            },{
                type: 'line',
                axis: 'left',
                xField: 'datetime',
                yField: 'reject',
                tips: {
                    trackMouse: true,
                    width: 80,
                    height: 40,
                    renderer: function(storeItem, item) {
                        this.setTitle(storeItem.get('datetime'));
                        this.update(storeItem.get('reject'));
                    }
                },
                highlight:true
            },{
                type: 'line',
                axis: 'left',
                xField: 'datetime',
                yField: 'pendingRetest',
                tips: {
                    trackMouse: true,
                    width: 80,
                    height: 40,
                    renderer: function(storeItem, item) {
                        this.setTitle(storeItem.get('datetime'));
                        this.update(storeItem.get('pendingRetest'));
                    }
                },
                highlight:true
            },{
                type: 'line',
                axis: 'left',
                xField: 'datetime',
                yField: 'resolve',
                tips: {
                    trackMouse: true,
                    width: 80,
                    height: 40,
                    renderer: function(storeItem, item) {
                        this.setTitle(storeItem.get('datetime'));
                        this.update(storeItem.get('resolve'));
                    }
                },
                highlight:true
            },{
                type: 'line',
                axis: 'left',
                xField: 'datetime',
                yField: 'close',
                tips: {
                    trackMouse: true,
                    width: 80,
                    height: 40,
                    renderer: function(storeItem, item) {
                        this.setTitle(storeItem.get('datetime'));
                        this.update(storeItem.get('close'));
                    }
                },
                highlight:true
            }]
        });
    	var panelStatusDaily = Ext.create('widget.panel', {
            width:'100%',
            height: 500,
            title: 'Defect daily status statistic',
            renderTo:'statusDailyBody',
            layout: 'fit',
            tbar: [{
                text: 'Save Chart',
                handler: function(){ downloadChart(defectStatusChar); }
            }],
            items: defectStatusChar
        });
    	panelStatusDaily.hide();
    	
    	var defectStatusCurrentlyStore = new Ext.data.Store({
            proxy: {
                type: 'ajax',
                url: 'MantisController.do?method=getDefectReport&operType=StatusCurrently',
                reader: {type: 'json'}
            },
            fields: ['status','amount']
        });
    	defectStatusCurrentlyStore.on('beforeload', function (store, options){
	            Ext.apply(store.proxy.extraParams,{'project':project});
	        });
    	defectStatusCurrentlyStore.load();
    	var defectStatusCurrentlyChar = Ext.create('Ext.chart.Chart',{
    		style: 'background:#fff',
    		animate: true,
            store: defectStatusCurrentlyStore,
            shadow: true,
            axes: [{
                type: 'Numeric',
                minimum: 0,
                position: 'left',
                fields: ['amount'],
                title: 'Number',
                grid: {
                    odd: {
                        opacity: 1,
                        fill: '#ddd',
                        stroke: '#bbb',
                        'stroke-width': 0.5
                    }
                },
                label: {
                    renderer: Ext.util.Format.numberRenderer('0,0'),
                    font: '10px Arial'
                }
            },{
                type: 'Category',
                position: 'bottom',
                fields: ['status'],
                title: 'Defect status',
                label: {
                    font: '15px Arial',
                    rotate:{
                        degrees: 0
                    },
                    renderer: function(name) {
                        //return name.substr(0, 3) + ' 07';
                        return name;
                    }
                }
            }],
            series: [
              {
                type: 'column',
                axis: 'left',
                highlight: true,
                xField: 'status',
                yField: 'amount',
                tips: {
                    trackMouse: true,
                    width: 80,
                    height: 40,
                    renderer: function(storeItem, item) {
                        this.setTitle(storeItem.get('status'));
                        this.update(storeItem.get('amount'));
                    }
                },
                label:{
                      display: 'insideEnd',
                      'text-anchor': 'middle',
                      field: 'amount',
                      renderer: Ext.util.Format.numberRenderer('0'),
                      orientation: 'vertical',
                      color: '#333'
                  }
            }]
        });
    	var panelStatusCurrently = Ext.create('widget.panel', {
            width:'100%',
            height: 500,
            title: 'Defect currently status statistic',
            renderTo:'statusCurrentlyBody',
            layout: 'fit',
            tbar: [{
                text: 'Save Chart',
                handler: function(){ downloadChart(defectStatusCurrentlyChar); }
            }],
            items: defectStatusCurrentlyChar
        });
    	panelStatusCurrently.hide();
    	
    	function closeAll(){
    		panelStatusDaily.hide();
    		panelStatusCurrently.hide();
    	}
    });	
    
    </script>
</head>
<body>
	<div style='height:600px'>
		<table>
			<tr>
				<td>
					<input type="button" style ="width:120px" id="status_d_b" value="Daily report"/>
				</td>
				<td>
					<input type="button" style ="width:120px" id="status_c_b" value="Currently report"/>
				</td>
			</tr>
		</table>
		<div id="statusDailyBody"></div>
		<div id="statusCurrentlyBody"></div>
	</div>
</body>
</html>
