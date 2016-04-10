<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/unieap/unieap_unlogin.jsp"%>
<%@page
	import="com.unieap.UnieapConstants,com.apps.esb.service.bss.vo.macre.customerprofile.CustomerProfileVO"%>
<%
	CustomerProfileVO vo = (CustomerProfileVO) session.getAttribute(UnieapConstants.USER);
%>
<link rel="stylesheet" href="<%=path%>/unieap/js/jquery/plugins/chosen_v1.4.2/chosen.css">
<script src="<%=path%>/unieap/js/jquery/plugins/chosen_v1.4.2/chosen.jquery.min.js" type="text/javascript"></script>

<link rel="stylesheet" href="<%=path%>/unieap/js/jquery/plugins/Guriddo_jqGrid_JS_5.0.2/css/ui.jqgrid.css">
<script src="<%=path%>/unieap/js/jquery/plugins/Guriddo_jqGrid_JS_5.0.2/js/jquery.jqGrid.min.js" type="text/javascript"></script>

<style>
.monthList {
	width: 50px;
	height: 50px;
	float: left;
}
</style>

<script type="text/javascript">
	$(document).ready(function(){
		$("#serviceCategory").chosen({});
	});
	$( "#startDate" ).datepicker({
		inline: false
	});
	$( "#endDate" ).datepicker({
		inline: true
	});
	
	jQuery('#grid').jqGrid({
		hoverrows:false,
		"viewrecords":true,
		"jsonReader":{"repeatitems":false,"subgrid":{"repeatitems":false}},
		"gridview":true,
		"url":"<%=path%>/mCareController.do?method=getUsageHistoryList",
		"loadonce": true,
		"rowNum":10,
		"height":200,
		"autowidth":true,
		//"sortname":"OrderID",
		"rowList":[10,30,40],
		"datatype":"json",
		"colModel":[
			{"name":"Call Type","index":"flowType","sorttype":"string","search":false,"editable":false},
			{"name":"Service No.","index":"serviceNumber","sorttype":"string","search":false,"editable":false},
			{"name":"Service Type","index":"serviceTypeName","sorttype":"string","search":false,"editable":false},
			{"name":"Start Time","index":"startTime","sorttype":"datetime","formatter":"date",
				"formatoptions":{"srcformat":"Y-m-d H:i:s","newformat":"m/d/Y"},
				"search":false,"editable":false
			},
			{"name":"Charge Amount","index":"actualChargeAmt","sorttype":"string","search":false,"editable":false},
			{"name":"Duration","index":"actualVolume","sorttype":"string","search":false,"editable":false},
			{"name":"Volume","index":"actualVolume","sorttype":"string","search":false,"editable":false},
			{"name":"Measure Unit","index":"measureUnit","sorttype":"string","search":false,"editable":false},
			{"name":"Offering Name","index":"offeringName","sorttype":"string","search":false,"editable":false}
		],
		"pager":"#pager"
	});
	// Toolbar searching
	//jQuery('#grid').jqGrid('filterToolbar',{"stringResult":true});

</script>
<div class="bizPageDisplay">
	<div class="bizPageTitle">
		<span class="bizPageTitleFont">>>${menuTitle}</span>
	</div>
	<div class="fl">
		<div class="img">
			<img src="<%=path%>/apps/mcare/images/ACCOUNTS_BALANCE_SEARCH.png"
				alt="my balance" width="120" />
		</div>
	</div>
	<div class="desc">
		<div class="optionsDesc">Query usage history that include call details, rental details etc; based on privacy request SMS verification code.</div>
		<div>
			<span class="color_6">My Name : </span><font class='descTitle1'>${customerName}</font>
		</div>
		<div>
			<span class="color_6">Service Number : </span><font
				class='descTitle1'>${serviceNumber}</font>
		</div>
	</div>
	<div class='cl'></div>
	<div style="border-top: 1px solid #00e1e1"></div>
	<div>
		<div>
			<span class="displayListTitle">Query Usage Per Duration</span>
		</div>
		<table>
			<tr>
				<td class="tableDispalyOneField"><span class="tableDispalyField">Service Category</span></td>
				<td colspan='5'>
					<select id="serviceCategory" data-placeholder="Choose a Country..." class="chosen-select" multiple style="width:350px;" tabindex="4">
						<option value=""></option>
		            	<option value="United States">United States</option>
		            	<option value="United Kingdom">United Kingdom</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="tableDispalyOneField"><span class="tableDispalyField">Start Date</span></td>
				<td>
					<input id="startDate"  class="inputText l mr5" type="text" value="" />
				</td>
				<td class="tableDispalyOneField"><span class="tableDispalyField">End Date</span></td>
				<td colspan='2'>
					<input id="endDate" class="inputText l mr5" type="text"  value="" />
				</td>
				<td>
					<div>
						<input id="sbmtButton" class="inputBut" type="button" tabindex="4"
							value="Query" />
					</div>
				</td>
			</tr>
		</table>
		<div style="border-top: 1px solid #00e1e1"></div>
		<div>
			<span class="displayListTitle">Usage Details</span>
		</div>
		<table id='grid'></table>
		<div id='pager'></div>
	</div>
</div>
