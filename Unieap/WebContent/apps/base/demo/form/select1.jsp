<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/unieap/unieap.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		function loaddata1(){
			$('#ff').form('load','form_data.json');
		}
		function loaddata2(){
			$('#ff').form('load',{
				name:'name2',
				email:'mymail@gmail.com',
				subject:'subject2',
				message:'message2',
				language:5
			});
		}
		function cleardata(){
			$('#ff').form('clear');
		}
		function loadData(){
			$('#ff').combobox({
				url:'CodeListAction.do?method=getCodeList&listCode=PRD_INST_TYPE',
				valueField:'id',
				textField:'text'
			});
		}
		function setValue(){
			$('#ff').combobox('setValue','bitem3');
		}
		function getValue(){
			var val = $('#cc').combobox('getValue');
			alert(val);
		}
		function disable(){
			$('#ff').combobox('disable');
		}
		function enable(){
			$('#ff').combobox('enable');
		}
		
		
	</script>
  </head>
  <body>
	<h1>Form Demo</h1>
<div>
	<a href="#" onclick="loaddata1()">Load1</a> 
	<a href="#" onclick="loaddata2()">Load2</a> 
	<a href="#" onclick="cleardata()">Clear</a> 
	
	<a href="#" onclick="loadData()">loadData</a>
	<input type='button' onclick="loadData()" value ='loadData'>
</div>
<div style="background:#fafafa;padding:10px;width:300px;height:300px;">
    <form id="ff" method="post">
        
        <div>
            <label for="language">Language:</label>
			<input class="easyui-combobox" 
					name="language"
					mode="remote",
					value="请选择",
					url="CodeListAction.do?method=getCodeList&listCode=OPR_USE_TYPE" 
					valueField="id" 
					textField="text" 
					panelHeight="auto">
        </div>
        <div>
            <input type="submit" value="Submit">
        </div>
    </form>
</div>
  </body>
</html>
