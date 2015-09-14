<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>Report Issue</title>
 	<style type="text/css">
        .td_title {
        	background-color:#B5BCBB;
        	font-weight:bolder;
        	height:20px;
        	
        }
        .td_value {
        	background-color:#E9EAE2;
        	height:20px;
        	width:100px;
        	align:left;
        }
    </style>
	<script type="text/javascript">
			Ext.onReady(function() {
		    	Ext.tip.QuickTipManager.init();
		    	var reporter = Ext.create('Ext.form.field.ComboBox', {
			        renderTo: 'reporter',
			        hiddenName:'reporter',
			        valueField:'dicId',
			        displayField: 'dicName',
			        blankText:'',
			        emptyText:'any',
			        store: Ext.create('Ext.data.Store', 
			        		{fields : ['dicId', 'dicName'],data:UnieapDicdata._140Opt}),
			        queryMode: 'local',
			        listConfig: {
			            getInnerTpl: function() {
			                return '<div data-qtip="{dicName}. {dicId}">{dicName} ({dicId})</div>';
			            }
			        }
			    });
		    	var assignedTo = Ext.create('Ext.form.field.ComboBox', {
			        renderTo: 'assignedTo',
			        hiddenName:'assignedTo',
			        valueField:'dicId',
			        displayField: 'dicName',
			        blankText:'',
			        emptyText:'any',
			        store: Ext.create('Ext.data.Store', 
			        		{fields : ['dicId', 'dicName'],data:UnieapDicdata._140Opt}),
			        queryMode: 'local',
			        listConfig: {
			            getInnerTpl: function() {
			                return '<div data-qtip="{dicName}. {dicId}">{dicName} ({dicId})</div>';
			            }
			        }
			    });
		    	var testStreams = Ext.create('Ext.form.field.ComboBox', {
			        renderTo: 'testStreams',
			        hiddenName:'testStreams',
			        valueField:'dicId',
			        displayField: 'dicName',
			        blankText:'',
			        emptyText:'any',
			        store: Ext.create('Ext.data.Store', 
			        		{fields : ['dicId', 'dicName'],data:UnieapDicdata._140Opt}),
			        queryMode: 'local',
			        listConfig: {
			            getInnerTpl: function() {
			                return '<div data-qtip="{dicName}. {dicId}">{dicName} ({dicId})</div>';
			            }
			        }
			    });
		    	var severity = Ext.create('Ext.form.field.ComboBox', {
			        renderTo: 'severity',
			        hiddenName:'severity',
			        valueField:'dicId',
			        displayField: 'dicName',
			        blankText:'',
			        emptyText:'any',
			        store: Ext.create('Ext.data.Store', 
			        		{fields : ['dicId', 'dicName'],data:UnieapDicdata._140Opt}),
			        queryMode: 'local',
			        listConfig: {
			            getInnerTpl: function() {
			                return '<div data-qtip="{dicName}. {dicId}">{dicName} ({dicId})</div>';
			            }
			        }
			    });
		    });
			
	</script>
</head>
<body>
	<table>
		<tr id='filterfileds'>
			<td>
				<table border="1" align="center">
					<tr>
						<td class="td_value">Reporter</td>
						<td class="td_value">Test Case Id</td>
						<td class="td_value">Assigned To</td>
						<td class="td_value">Test Streams</td>
						<td class="td_value">Severity</td>
						<td class="td_value">Profile</td>
					</tr>
					<tr>
						<td class="td_value"><div id="reporter"></div></td>
						<td class="td_value"><input type='text' id='tcId' value=''/></td>
						<td class="td_value"><div id="assignedTo"></div></td>
						<td class="td_value"><div id="testStreams"></div></td>
						<td class="td_value"><div id="severity"></div></td>
						<td class="td_value"><input type='text' id='title' name='title' value=''/></td>
					</tr>
					<tr>
						<td class="td_value">Status</td>
						<td class="td_value">Hide Status</td>
						<td class="td_value">Product Version</td>
						<td class="td_value">Priority</td>
						<td class="td_value">View Status</td>
						<td class="td_value">Sub Stream/Sub Module</td>
					</tr>
					<tr>
						<td class="td_value"><div id="status"></div></td>
						<td class="td_value"><div id="hideStatus"></div></td>
						<td class="td_value"><div id="productVersion"></div></td>
						<td class="td_value"><div id="priority"></div></td>
						<td class="td_value"><div id="viewStatus"></div></td>
						<td class="td_value"><div id="subStream"></div></td>
					</tr>
					<tr>
						<td align="left" colspan=6>
							<input type="button" style ="width:90px" id="submit_b" value="Apply Filter"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				
			</td>
		</tr>
	</table>
	
</body>
</html>
