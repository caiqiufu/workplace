package com.apps.esb.service.bss.app.vo.account.transfer;

import com.unieap.base.vo.BaseVO;

public class TransferBalanceVO extends BaseVO{
	private TransferorVO transferorVO;
	private TransfereeVO transfereeVO;
	public TransferorVO getTransferorVO() {
		return transferorVO;
	}
	public void setTransferorVO(TransferorVO transferorVO) {
		this.transferorVO = transferorVO;
	}
	public TransfereeVO getTransfereeVO() {
		return transfereeVO;
	}
	public void setTransfereeVO(TransfereeVO transfereeVO) {
		this.transfereeVO = transfereeVO;
	}
	
	
}
