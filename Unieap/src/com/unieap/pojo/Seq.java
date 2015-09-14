package com.unieap.pojo;

/**
 * Seq entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Seq implements java.io.Serializable {

	// Fields

	private String seqName;
	private Integer val;

	// Constructors

	/** default constructor */
	public Seq() {
	}

	/** full constructor */
	public Seq(String seqName, Integer val) {
		this.seqName = seqName;
		this.val = val;
	}

	// Property accessors

	public String getSeqName() {
		return this.seqName;
	}

	public void setSeqName(String seqName) {
		this.seqName = seqName;
	}

	public Integer getVal() {
		return this.val;
	}

	public void setVal(Integer val) {
		this.val = val;
	}

}