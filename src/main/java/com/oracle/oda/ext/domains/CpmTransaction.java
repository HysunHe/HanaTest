/***************************************************************************
 *
 * PoC code for evaluating purpose only. No guarantee of quality.
 *
 ***************************************************************************/

package com.oracle.oda.ext.domains;

/***************************************************************************
 * <PRE>
 *  Project Name    : ssp
 * 
 *  Package Name    : com.oracle.oda.ext.domains
 * 
 *  File Name       : CpmTransaction.java
 * 
 *  Creation Date   : 2019年12月25日
 * 
 *  Author          : Hysun He
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public class CpmTransaction {
	private String userId;
	private String reqOrgCode;
	private String reqOrgTxDate;
	private String reqOrgTxTime;
	private String glnTxNo;
	private String payCode;
	private String barCode;
	private String qrCode;
	private Integer validSecond;
	private Float origBalance;
	private Float newBalance;
	private Float txAmt;
	private String txCur;
	private Float txExRate;
	private String status;
	private String approveDateTime;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the reqOrgCode
	 */
	public String getReqOrgCode() {
		return reqOrgCode;
	}

	/**
	 * @param reqOrgCode
	 *            the reqOrgCode to set
	 */
	public void setReqOrgCode(String reqOrgCode) {
		this.reqOrgCode = reqOrgCode;
	}

	/**
	 * @return the reqOrgTxDate
	 */
	public String getReqOrgTxDate() {
		return reqOrgTxDate;
	}

	/**
	 * @param reqOrgTxDate
	 *            the reqOrgTxDate to set
	 */
	public void setReqOrgTxDate(String reqOrgTxDate) {
		this.reqOrgTxDate = reqOrgTxDate;
	}

	/**
	 * @return the reqOrgTxTime
	 */
	public String getReqOrgTxTime() {
		return reqOrgTxTime;
	}

	/**
	 * @param reqOrgTxTime
	 *            the reqOrgTxTime to set
	 */
	public void setReqOrgTxTime(String reqOrgTxTime) {
		this.reqOrgTxTime = reqOrgTxTime;
	}

	/**
	 * @return the glnTxNo
	 */
	public String getGlnTxNo() {
		return glnTxNo;
	}

	/**
	 * @param glnTxNo
	 *            the glnTxNo to set
	 */
	public void setGlnTxNo(String glnTxNo) {
		this.glnTxNo = glnTxNo;
	}

	/**
	 * @return the payCode
	 */
	public String getPayCode() {
		return payCode;
	}

	/**
	 * @param payCode
	 *            the payCode to set
	 */
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	/**
	 * @return the barCode
	 */
	public String getBarCode() {
		return barCode;
	}

	/**
	 * @param barCode
	 *            the barCode to set
	 */
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	/**
	 * @return the qrCode
	 */
	public String getQrCode() {
		return qrCode;
	}

	/**
	 * @param qrCode
	 *            the qrCode to set
	 */
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	/**
	 * @return the validSecond
	 */
	public Integer getValidSecond() {
		return validSecond;
	}

	/**
	 * @param validSecond
	 *            the validSecond to set
	 */
	public void setValidSecond(Integer validSecond) {
		this.validSecond = validSecond;
	}

	/**
	 * @return the origBalance
	 */
	public Float getOrigBalance() {
		return origBalance == null ? 0 : this.origBalance;
	}

	/**
	 * @param origBalance
	 *            the origBalance to set
	 */
	public void setOrigBalance(Float origBalance) {
		this.origBalance = origBalance;
	}

	/**
	 * @return the newBalance
	 */
	public Float getNewBalance() {
		return newBalance == null ? 0 : this.newBalance;
	}

	/**
	 * @param newBalance
	 *            the newBalance to set
	 */
	public void setNewBalance(Float newBalance) {
		this.newBalance = newBalance;
	}

	/**
	 * @return the txAmt
	 */
	public Float getTxAmt() {
		return txAmt;
	}

	/**
	 * @param txAmt
	 *            the txAmt to set
	 */
	public void setTxAmt(Float txAmt) {
		this.txAmt = txAmt;
	}

	/**
	 * @return the txCur
	 */
	public String getTxCur() {
		return txCur;
	}

	/**
	 * @param txCur
	 *            the txCur to set
	 */
	public void setTxCur(String txCur) {
		this.txCur = txCur;
	}

	/**
	 * @return the txExRate
	 */
	public Float getTxExRate() {
		return txExRate;
	}

	/**
	 * @param txExRate
	 *            the txExRate to set
	 */
	public void setTxExRate(Float txExRate) {
		this.txExRate = txExRate;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the approveDateTime
	 */
	public String getApproveDateTime() {
		return approveDateTime;
	}

	/**
	 * @param approveDateTime
	 *            the approveDateTime to set
	 */
	public void setApproveDateTime(String approveDateTime) {
		this.approveDateTime = approveDateTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CpmTransaction [userId=" + userId + ", reqOrgCode=" + reqOrgCode
				+ ", reqOrgTxDate=" + reqOrgTxDate + ", reqOrgTxTime="
				+ reqOrgTxTime + ", glnTxNo=" + glnTxNo + ", payCode=" + payCode
				+ ", barCode=" + barCode + ", qrCode=" + qrCode
				+ ", validSecond=" + validSecond + ", origBalance="
				+ origBalance + ", newBalance=" + newBalance + ", txAmt="
				+ txAmt + ", txCur=" + txCur + ", txExRate=" + txExRate
				+ ", status=" + status + ", approveDateTime=" + approveDateTime
				+ "]";
	}

}
