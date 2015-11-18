package com.apps.esb.service.bss.app.cbs;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.apps.esb.service.bss.app.cbs.vo.recharge.BalanceChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.BalanceVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.CreditChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.FreeUnitItemVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.LifeCycleChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.LifeCycleStatusVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.LoanChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.RechargeBonusVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.RechargeResponseVO;

public class CBSServiceUtils {

	public static RechargeResponseVO getRechargeResponse(Document document){
		RechargeResponseVO rechargeResponseVO = new RechargeResponseVO();
		if (document.getElementsByTagName("ars:RechargeSerialNo").getLength() > 0){
			String rechargeSerialNo = document.getElementsByTagName("ars:RechargeSerialNo").item(0).getTextContent();
			rechargeResponseVO.setRechargeSerialNo(rechargeSerialNo);
		}
		if (document.getElementsByTagName("ars:BalanceChgInfo").getLength() > 0){
			NodeList balanceChgInfos = document.getElementsByTagName("ars:BalanceChgInfo");
			Node balanceChgInfo;
			List<BalanceChgInfoVO> balanceChgInfoVOs = new ArrayList<BalanceChgInfoVO>();
			rechargeResponseVO.setBalanceChgInfos(balanceChgInfoVOs);
			for (int i = 0; i < balanceChgInfos.getLength(); i++){
				BalanceChgInfoVO balanceChgInfoVO = new BalanceChgInfoVO();
				balanceChgInfoVOs.add(balanceChgInfoVO);
				balanceChgInfo = balanceChgInfos.item(i);
				NodeList nodes = balanceChgInfo.getChildNodes();
				for(int j = 0 ; j < nodes.getLength() ; j++){
					Node node = nodes.item(j);
					if ("arc:BalanceType".equals(node.getNodeName())) {
						balanceChgInfoVO.setBalanceType(node.getTextContent());
					}else if("arc:BalanceID".equals(node.getNodeName())) {
						balanceChgInfoVO.setBalanceID(node.getTextContent());
					}else if("arc:BalanceTypeName".equals(node.getNodeName())) {
						balanceChgInfoVO.setBalanceTypeName(node.getTextContent());
					}else if("arc:OldBalanceAmt".equals(node.getNodeName())) {
						balanceChgInfoVO.setOldBalanceAmt(node.getTextContent());
					}else if("arc:NewBalanceAmt".equals(node.getNodeName())) {
						balanceChgInfoVO.setNewBalanceAmt(node.getTextContent());
					} 
				}
			}
		}
		if (document.getElementsByTagName("ars:LoanChgInfo").getLength() > 0){
			NodeList loanChgInfos = document.getElementsByTagName("ars:LoanChgInfo");
			Node loanChgInfo;
			LoanChgInfoVO loanChgInfoVO = new LoanChgInfoVO();
			rechargeResponseVO.setLoanChgInfoVO(loanChgInfoVO);;
			for (int i = 0; i < loanChgInfos.getLength(); i++){
				loanChgInfo = loanChgInfos.item(i);
				NodeList nodes = loanChgInfo.getChildNodes();
				for(int j = 0 ; j < nodes.getLength(); j++){
					Node node = nodes.item(j);
					if ("arc:OldLoanAmt".equals(node.getNodeName())) {
						loanChgInfoVO.setOldLoanAmt(node.getTextContent());
					}else if("arc:NewLoanAmt".equals(node.getNodeName())) {
						loanChgInfoVO.setNewLoanAmt(node.getTextContent());
					}else if("arc:LoanPaymentAmt".equals(node.getNodeName())) {
						loanChgInfoVO.setLoanPaymentAmt(node.getTextContent());
					}else if("arc:LoanInterestAmt".equals(node.getNodeName())) {
						loanChgInfoVO.setLoanInterestAmt(node.getTextContent());
					}
				}
			}
		}
		if (document.getElementsByTagName("ars:RechargeBonus").getLength() > 0){
			NodeList rechargeBonuss = document.getElementsByTagName("ars:RechargeBonus");
			Node rechargeBonus;
			RechargeBonusVO rechargeBonusVO = new RechargeBonusVO();
			rechargeResponseVO.setRechargeBonusVO(rechargeBonusVO);
			for (int i = 0; i < rechargeBonuss.getLength(); i++){
				List<FreeUnitItemVO> freeUnitItemVOs = new ArrayList<FreeUnitItemVO>();
				List<BalanceVO> balanceVOs = new ArrayList<BalanceVO>();
				rechargeBonusVO.setFreeUnitItems(freeUnitItemVOs);
				rechargeBonusVO.setBalances(balanceVOs);
				rechargeBonus = rechargeBonuss.item(i);
				NodeList rechargeBonusNodes = rechargeBonus.getChildNodes();
				for(int j = 0 ; j< rechargeBonusNodes.getLength() ; j++){
					Node rechargeBonusNode = rechargeBonusNodes.item(j);
					if ("ars:FreeUnitItemList".equals(rechargeBonusNode.getNodeName())) {
						NodeList freeUnitItemList = rechargeBonusNode.getChildNodes();
						Node freeUnitItemNode;
						if(freeUnitItemList.getLength()>0){
							FreeUnitItemVO freeUnitItemVO = new FreeUnitItemVO();
							freeUnitItemVOs.add(freeUnitItemVO);
							for(int k = 0 ; k < freeUnitItemList.getLength() ;k++){
								freeUnitItemNode = freeUnitItemList.item(k);
								if("ars:FreeUnitID".equals(freeUnitItemNode.getNodeName())){
									freeUnitItemVO.setFreeUnitID(freeUnitItemNode.getTextContent());
								}else if("ars:FreeUnitType".equals(freeUnitItemNode.getNodeName())){
									freeUnitItemVO.setFreeUnitType(freeUnitItemNode.getTextContent());
								}else if("ars:FreeUnitTypeName".equals(freeUnitItemNode.getNodeName())){
									freeUnitItemVO.setFreeUnitTypeName(freeUnitItemNode.getTextContent());
								}else if("ars:MeasureUnit".equals(freeUnitItemNode.getNodeName())){
									freeUnitItemVO.setMeasureUnit(freeUnitItemNode.getTextContent());
								}else if("ars:MeasureUnitName".equals(freeUnitItemNode.getNodeName())){
									freeUnitItemVO.setMeasureUnitName(freeUnitItemNode.getTextContent());
								}else if("ars:BonusAmt".equals(freeUnitItemNode.getNodeName())){
									freeUnitItemVO.setBonusAmt(freeUnitItemNode.getTextContent());
								}else if("ars:BonusAmt".equals(freeUnitItemNode.getNodeName())){
									freeUnitItemVO.setBonusAmt(freeUnitItemNode.getTextContent());
								}else if("ars:EffectiveTime".equals(freeUnitItemNode.getNodeName())){
									freeUnitItemVO.setEffectiveTime(freeUnitItemNode.getTextContent());
								}else if("ars:ExpoireTime".equals(freeUnitItemNode.getNodeName())){
									freeUnitItemVO.setExpoireTime(freeUnitItemNode.getTextContent());
								}
								/*NodeList nodes = freeUnitItemNode.getChildNodes();
								for(int l = 0 ; l < nodes.getLength() ; l++){
									Node node = nodes.item(l);
								}*/
							}
						}
					}else if("ars:BalanceList".equals(rechargeBonusNode.getNodeName())){
						NodeList balanceList = rechargeBonusNode.getChildNodes();
						Node balanceNode;
						if(balanceList.getLength()>0){
							BalanceVO balanceVO = new BalanceVO();
							balanceVOs.add(balanceVO);
							for(int k = 0 ; k < balanceList.getLength() ;k++){
								balanceNode = balanceList.item(k);
								if("ars:BalanceType".equals(balanceNode.getNodeName())){
									balanceVO.setBalanceType(balanceNode.getTextContent());
								}else if("ars:BalanceID".equals(balanceNode.getNodeName())){
									balanceVO.setBalanceID(balanceNode.getTextContent());
								}else if("ars:BalanceTypeName".equals(balanceNode.getNodeName())){
									balanceVO.setBalanceTypeName(balanceNode.getTextContent());
								}else if("ars:BonusAmt".equals(balanceNode.getNodeName())){
									balanceVO.setBonusAmt(balanceNode.getTextContent());
								}else if("ars:EffectiveTime".equals(balanceNode.getNodeName())){
									balanceVO.setEffectiveTime(balanceNode.getTextContent());
								}else if("ars:ExpireTime".equals(balanceNode.getNodeName())){
									balanceVO.setExpireTime(balanceNode.getTextContent());
								}
								/*NodeList nodes = balanceNode.getChildNodes();
								for(int l = 0 ; l< nodes.getLength() ; l++){
									Node node = nodes.item(l);
								}*/
							}
						}
					}
					
				}
				if (document.getElementsByTagName("ars:LifeCycleChgInfo").getLength() > 0){
					NodeList lifeCyckeChgInfos = document.getElementsByTagName("ars:LifeCycleChgInfo");
					Node lifeCyckeChgInfo;
					LifeCycleChgInfoVO lifeCycleChgInfoVO = new LifeCycleChgInfoVO();
					for (int j = 0; j < lifeCyckeChgInfos.getLength(); j++){
						List<LifeCycleStatusVO> oldLifeCycleStatus = new ArrayList<LifeCycleStatusVO>();
						lifeCycleChgInfoVO.setOldLifeCycleStatus(oldLifeCycleStatus);
						List<LifeCycleStatusVO> newLifeCycleStatus = new ArrayList<LifeCycleStatusVO>();
						lifeCycleChgInfoVO.setNewLifeCycleStatus(newLifeCycleStatus);
						lifeCyckeChgInfo = lifeCyckeChgInfos.item(j);
						NodeList  lifeCyckeChgInfoNodes = lifeCyckeChgInfo.getChildNodes();
						for(int k = 0 ; k < lifeCyckeChgInfoNodes.getLength() ; k++){
							Node lifeCyckeChgInfoNode = lifeCyckeChgInfoNodes.item(k);
							if("ars:OldLifeCycleStatus".equals(lifeCyckeChgInfoNode.getNodeName())){
								LifeCycleStatusVO lifeCycleStatusVO = new LifeCycleStatusVO();
								oldLifeCycleStatus.add(lifeCycleStatusVO);
								NodeList oldLifeCycleStatusNodes = lifeCyckeChgInfoNode.getChildNodes();
								Node oldLifeCycleStatusNode;
								for (int l = 0; l < oldLifeCycleStatusNodes.getLength(); l++){
									oldLifeCycleStatusNode = oldLifeCycleStatusNodes.item(l);
									if("ars:StatusName".equals(oldLifeCycleStatusNode.getNodeName())){
										lifeCycleStatusVO.setStatusName(oldLifeCycleStatusNode.getTextContent());
									}else if("ars:StatusExpireTime".equals(oldLifeCycleStatusNode.getNodeName())){
										lifeCycleStatusVO.setStatusExpireTime(oldLifeCycleStatusNode.getTextContent());
									}else if("ars:StatusIndex".equals(oldLifeCycleStatusNode.getNodeName())){
										lifeCycleStatusVO.setStatusIndex(oldLifeCycleStatusNode.getTextContent());
									}
								}
								
							}else if("ars:NewLifeCycleStatus".equals(lifeCyckeChgInfoNode.getNodeName())){
								NodeList newLifeCycleStatusNodes = lifeCyckeChgInfoNode.getChildNodes();
								Node newLifeCycleStatusNode;
								
								LifeCycleStatusVO lifeCycleStatusVO = new LifeCycleStatusVO();
								newLifeCycleStatus.add(lifeCycleStatusVO);
								for (int l = 0; l < newLifeCycleStatusNodes.getLength(); l++){
									newLifeCycleStatusNode = newLifeCycleStatusNodes.item(l);
									if("ars:StatusName".equals(newLifeCycleStatusNode.getNodeName())){
										lifeCycleStatusVO.setStatusName(newLifeCycleStatusNode.getTextContent());
									}else if("ars:StatusExpireTime".equals(newLifeCycleStatusNode.getNodeName())){
										lifeCycleStatusVO.setStatusExpireTime(newLifeCycleStatusNode.getTextContent());
									}else if("ars:StatusIndex".equals(newLifeCycleStatusNode.getNodeName())){
										lifeCycleStatusVO.setStatusIndex(newLifeCycleStatusNode.getTextContent());
									}
									/*
									NodeList nodes = newLifeCycleStatusNode.getChildNodes();
									for(int m = 0 ; m < nodes.getLength() ; m ++){
									}*/
								}
								
							}
						}
					}
					
				}
				if (document.getElementsByTagName("ars:CreditChgInfo").getLength() > 0){
					NodeList creditChgInfos = document.getElementsByTagName("ars:CreditChgInfo");
					Node creditChgInfo;
					List<CreditChgInfoVO> creditChgInfoVOs = new ArrayList<CreditChgInfoVO>();
					rechargeResponseVO.setCreditChgInfos(creditChgInfoVOs);
					for (int j = 0; j < creditChgInfos.getLength(); j++){
						creditChgInfo = creditChgInfos.item(j);
						NodeList nodes = creditChgInfo.getChildNodes();
						CreditChgInfoVO creditChgInfoVO = new CreditChgInfoVO();
						creditChgInfoVOs.add(creditChgInfoVO);
						for(int k = 0 ; k < nodes.getLength(); k++){
							Node node = nodes.item(k);
							if("ars:CreditLimitID".equals(node.getNodeName())){
								creditChgInfoVO.setCreditLimitID(node.getTextContent());
							}else if("arc:CreditLimitType".equals(node.getNodeName())){
								creditChgInfoVO.setCreditLimitType(node.getTextContent());
							}else if("arc:CreditLimitTypeName".equals(node.getNodeName())){
								creditChgInfoVO.setCreditLimitTypeName(node.getTextContent());
							}else if("arc:OldLeftCreditAmt".equals(node.getNodeName())){
								creditChgInfoVO.setOldLeftCreditAmt(node.getTextContent());
							}else if("arc:NewLeftCreditAmt".equals(node.getNodeName())){
								creditChgInfoVO.setNewLeftCreditAmt(node.getTextContent());
							}else if("arc:MeasureUnit".equals(node.getNodeName())){
								creditChgInfoVO.setMeasureUnit(node.getTextContent());
							}
						}
					}
				}
			}
		}
		return rechargeResponseVO;
	}
	
}
