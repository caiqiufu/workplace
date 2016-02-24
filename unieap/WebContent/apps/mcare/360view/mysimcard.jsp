<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/unieap/unieap_unlogin.jsp"%>
<%@page
	import="com.unieap.UnieapConstants,com.apps.esb.service.bss.vo.macre.customerprofile.CustomerProfileVO"%>
<%
	CustomerProfileVO vo = (CustomerProfileVO) session.getAttribute(UnieapConstants.USER);
%>

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
		<div class="optionsDesc">To query your mobile number detail.</div>
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
	<div style="width:300px;height:50px;align:center;margin: 0 auto; ">
		<table>
			<tr>
				<td class="tableDispalyOneField"><span class="tableDispalyField">Service
						Number</span></td>
				<td><span id="serviceNumber" class="tableDispalyValue">${serviceNumber}</span></td>
			</tr>
			<tr>
				<td class="tableDispalyOneField"><span class="tableDispalyField">SIM Card No.</span></td>
				<td><span id="serviceNumber" class="tableDispalyValue">${iccid}</span></td>
			</tr>
			<tr>
				<td class="tableDispalyOneField"><span class="tableDispalyField">SIM Card Type</span></td>
				<td><span id="serviceNumber" class="tableDispalyValue">${simCardTypeDesc}</span></td>
			</tr>
			<tr>
				<td class="tableDispalyOneField"><span class="tableDispalyField">PIN1</span></td>
				<td><span id="serviceNumber" class="tableDispalyValue">${pin1}</span></td>
			</tr>
			<tr>
				<td class="tableDispalyOneField"><span class="tableDispalyField">PIN2</span></td>
				<td><span id="serviceNumber" class="tableDispalyValue">${pin2}</span></td>
			</tr>
			<tr>
				<td class="tableDispalyOneField"><span class="tableDispalyField">PUK1</span></td>
				<td><span id="serviceNumber" class="tableDispalyValue">${puk1}</span></td>
			</tr>
			<tr>
				<td class="tableDispalyOneField"><span class="tableDispalyField">PUK2</span></td>
				<td><span id="serviceNumber" class="tableDispalyValue">${puk2}</span></td>
			</tr>
		</table>
	</div>
</div>