<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%>
<html>
  <head>
    <title>Main menu</title>
	<script type="text/javascript">
			var projectObj;
			var methodNameV;
			function toFrame(methodName)
			{
				methodNameV = methodName;
				var project = projectObj.getValue();
				if(project=='-1'||project==null){
					project = '';
				}
				document.getElementById("mainframe").src="MantisController.do?method="+methodName+'&project='+project;
			}
			function toManaement()
			{
				document.getElementById("mainframe").src="MdmController.do?method=menu";
			}
			function logout(){
				window.location.href = "LoginController.do?method=logout";
			}
			Ext.onReady(function() {
		    	Ext.tip.QuickTipManager.init();
		    	projectObj = Ext.create('Ext.form.field.ComboBox', {
			        fieldLabel: 'Project:',
			        renderTo: 'project',
			        displayField: 'dicName',
			        valueField:'dicCode',
			        forceSelection: true,
			        value:'-1',
			        width:200,
			        labelWidth:45,
			        store: Ext.create('Ext.data.Store', 
			        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1014}),
			        queryMode: 'local'
			    });
		    	
		    	var defectId = new Ext.form.NumberField({
		    		//fieldLabel: 'Register Date',
		    		hideLabel:true,
		    		renderTo: 'jump_number',
		    	    name: 'defectId',
		    	    width: 190,
		    	    allowBlank: true,
		    	    //altFormats: 'Y-m-d',
		    	    //format: 'Y-m-d',
		    	    blankText: '', 
		    	    listeners:{
		    	    	'specialkey': function(field, e){
		    	    		if (e.getKey() == Ext.EventObject.ENTER){
		    	    			var defectId = Ext.get("jump_number").dom.value;
		    		    		if(check_validate(defectId)){
		    			    		document.getElementById("mainframe").src='MantisController.do?method=issueDetail&defectId='+defectId;
		    		    		}else{
		    		    			Ext.MessageBox.show({title: 'Status',msg:'Plese type in validate defect ID',
		                    			buttons:Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
		    		    		}
		    	    		}
		    	    	}
		    	    },
		    	    emptyText: 'Type in defect Id...' 
		    	});
		    	Ext.get('switch_b').on('click', function(e){
		    		var project = projectObj.getValue();
		    		if(project=='-1'||project==null){
						project = '';
					}
		    		if(methodNameV==null){
		    			methodNameV = 'myview';
		    		}
			    	document.getElementById("mainframe").src="MantisController.do?method="+methodNameV+'&project='+project;
		    	});
		    	function check_validate(value){
		    	    var reg = /^\d+$/;
		    	    if( value.constructor === String ){
		    	        var re = value.match( reg );
		    	        if(re==null){
		    	        	return false;
		    	        }else{
			    	        return true;
		    	        }
		    	    }
		    	    return false;
		    	}
		    	Ext.get('jump_b').on('click', function(e){
		    		var defectId = Ext.get("jump_number").dom.value;
		    		if(check_validate(defectId)){
			    		document.getElementById("mainframe").src='MantisController.do?method=issueDetail&defectId='+defectId;
		    		}else{
		    			Ext.MessageBox.show({title: 'Status',msg:'Plese type in validate defect ID',
                			buttons:Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
		    		}
		    	});
		    	
		    })
			
	</script>
  </head>
  <body>
     <table class="grouble_table" width="100%" >
        <tr class="grouble_table_tr"><td colspan="4" align ="center" style ="font-weight:bolder;font-size:20pt;color:#BDC5B5;">Altel NGBSS PROJECT</td></tr>
        <tr class="grouble_table_tr">
        	<td width="20px">Logged in as:<%=user.getUserName()%></td>
        	<td align ="center"><%=df.format(new Date())%></td>
        	<td width="50px" align="right"><div id="project"></div></td>
        	<td width="10px"><input id="switch_b" name="switch" type="button" value="switch"></td>
        </tr>
        <tr class="grouble_table_tr">
        	<td colspan="2" width="90%" align ="center">
        		<c:forEach items="${menus}" var="c">
        			<a style="text-decoration:underline" href="####" onclick="${c.href}" target="_self" title="${c.menuCode}">${c.menuName}</a> |
        		</c:forEach>
        	</td>
        	<td align =right>
        		<input style="width:145px;" type='text' id="jump_number" name='mantis_number' value=''/>
        	</td>
        	<td align ="center">
        		<input id="jump_b" name="Jump" value="Jump" type="button">
        	</td>
        </tr>
        <tr class="grouble_table_tr">
        	<td colspan="4" align ="left">
		      <iframe id="mainframe" src="" frameBorder=0 scrolling=yes width="100%" height='500px' onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
        	</td>
        </tr>
      </table>
  </body>
</html>