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
		<div class="optionsDesc">Query personal profile information,
			address and contact information.</div>
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
		<table>
			<tr>
				<td class="tableDisplayTdField"><span class="tableDispalyField">Service Number</span></td>
				<td><span class="tableDispalyValue">${serviceNumber}</span></td>
				<td class="tableDisplayTdField"><span class="tableDispalyField">Status</span></td>
				<td><span class="tableDispalyValue">${statusDesc}</span></td>
				<td class="tableDisplayTdField"><span class="tableDispalyField">Status Reason</span></td>
				<td><span class="tableDispalyValue">${statusReasonDesc}</span></td>
			</tr>
			<tr>
				<td class="tableDisplayTdField"><span class="tableDispalyField">Payment Flag</span></td>
				<td><span class="tableDispalyValue">${paymentFlagDesc}</span></td>
				<td class="tableDisplayTdField"><span class="tableDispalyField">Activation Date</span></td>
				<td><span class="tableDispalyValue">${activateTime}</span></td>
				<td class="tableDisplayTdField"><span class="tableDispalyField">Suspesion Date</span></td>
				<td><span class="tableDispalyValue">${suspesionTime}</span></td>
			</tr>
			<tr>
				<td class="tableDisplayTdField"><span class="tableDispalyField">Deactivation Date</span></td>
				<td><span class="tableDispalyValue">${deactivationTime}</span></td>
				<td class="tableDisplayTdField"><span class="tableDispalyField">Expiry Date</span></td>
				<td><span class="tableDispalyValue">${expiryTime}</span></td>
				<td class="tableDisplayTdField"><span class="tableDispalyField">language</span></td>
				<td><span class="tableDispalyValue">${language}</span></td>
			</tr>
		</table>
		<div style="border-top: 1px solid #00e1e1"></div>
		<div>
			<span class="displayListTitle">Home Address</span>
		</div>
		<div style="border-top: 1px solid #00e1e1"></div>
		<table>
			<tr>
				<td class="tableDisplayTdField"><span class="tableDispalyField">Nationality</span></td>
				<td class="tableDisplayTdValue"><span class="tableDispalyValue">${addressNationality}</span></td>
				<td class="tableDisplayTdField"><span class="tableDispalyField">City/Province</span></td>
				<td colspan='3'><span class="tableDispalyValue">${addressProvince}</span></td>
			</tr>
			<tr>
				<td class="tableDisplayTdField"><span class="tableDispalyField">Details</span></td>
				<td colspan='5'><span class="tableDispalyValue">${addressDetails}</span></td>
			</tr>
		</table>
	</div>
</div>