package com.apps.esb.service.bss.app.cbs.vo.queryaggregationamount;

public class AggregationInfoVO {
	private RecurringChargeVO recurringCharge;
	private NRecurringChargeVO nRecurringCharge;
	private UsageChargeVO usageCharge;
	private AdjustmentVO adjustment;
	private BillMediumChargeVO billMediumCharge;
	private MinCommitmentChargeVO minCommitmentCharge;
	private DiscountVO discount;
	private TaxVO tax;
	public RecurringChargeVO getRecurringCharge() {
		return recurringCharge;
	}
	public void setRecurringCharge(RecurringChargeVO recurringCharge) {
		this.recurringCharge = recurringCharge;
	}
	
	public NRecurringChargeVO getnRecurringCharge() {
		return nRecurringCharge;
	}
	public void setnRecurringCharge(NRecurringChargeVO nRecurringCharge) {
		this.nRecurringCharge = nRecurringCharge;
	}
	public UsageChargeVO getUsageCharge() {
		return usageCharge;
	}
	public void setUsageCharge(UsageChargeVO usageCharge) {
		this.usageCharge = usageCharge;
	}
	public AdjustmentVO getAdjustment() {
		return adjustment;
	}
	public void setAdjustment(AdjustmentVO adjustment) {
		this.adjustment = adjustment;
	}
	public BillMediumChargeVO getBillMediumCharge() {
		return billMediumCharge;
	}
	public void setBillMediumCharge(BillMediumChargeVO billMediumCharge) {
		this.billMediumCharge = billMediumCharge;
	}
	public MinCommitmentChargeVO getMinCommitmentCharge() {
		return minCommitmentCharge;
	}
	public void setMinCommitmentCharge(MinCommitmentChargeVO minCommitmentCharge) {
		this.minCommitmentCharge = minCommitmentCharge;
	}
	public DiscountVO getDiscount() {
		return discount;
	}
	public void setDiscount(DiscountVO discount) {
		this.discount = discount;
	}
	public TaxVO getTax() {
		return tax;
	}
	public void setTax(TaxVO tax) {
		this.tax = tax;
	}
	
}
