<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/unieap/unieap_unlogin.jsp"%>
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
			<div class="optionsDesc">Query main account balance, bonus
				money, current available amount and usage amount.</div>
			<div>
				<span class="color_6">Primary Offering : </span><font class='descTitle1'>${pimaryOfferingName}</font>
			</div>
			<div>
				<span class="color_6">Service Number : </span><font
					class='descTitle1'>${serviceNumber}</font>
			</div>
			<div>
				<span class="color_6">Service Type : </span>
				Display subscriber main balance, bonus and next cycle.  
			</div>
		</div>
		<div class='cl'></div>
		<div style="border-top: 1px solid #00e1e1"></div>
		<div>
			<div class="fl bizPageDisplayTitle1">
				<span class="ft14">${year}</span><br>
				<span class="ft24">${month}</span>
			</div>
			<div class="fr bizPageDisplayTitle2">
				<span>Total Available Balance : </span><br>
				<span class="ft24wc">${totalAvailableAmount}</span>
				<div style="border-top: 1px solid #00e1e1"></div>
				<div>
					<div class="fl bizPageDisplayTitle3">
						<span>Main Balance : ${mainAccountBalance}</span>
					</div>
					<div class="fl bizPageDisplayTitle3">
						<span>Bonus Balance : ${bonusAccountBalance}</span>
					</div>
					<div class="fl bizPageDisplayTitle31">
						<span>Next Bill Cycle Date : ${nextCycle}</span>
					</div>
				</div>
			</div>
		</div>
    </div>