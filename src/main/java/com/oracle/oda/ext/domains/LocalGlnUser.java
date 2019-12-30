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
 *  File Name       : LocalGlnUser.java
 * 
 *  Creation Date   : 2019年12月24日
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
public class LocalGlnUser {
	private String userId;
	private String userName;
	private String orgCode;
	private String localGlnUuid;
	private Float balance;

	public LocalGlnUser() {
	}

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode
	 *            the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return the localGlnUuid
	 */
	public String getLocalGlnUuid() {
		return localGlnUuid;
	}

	/**
	 * @param localGlnUuid
	 *            the localGlnUuid to set
	 */
	public void setLocalGlnUuid(String localGlnUuid) {
		this.localGlnUuid = localGlnUuid;
	}

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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the balance
	 */
	public Float getBalance() {
		return balance;
	}

	/**
	 * @param balance
	 *            the balance to set
	 */
	public void setBalance(Float balance) {
		this.balance = balance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LocalGlnUser [userId=" + userId + ", userName=" + userName
				+ ", orgCode=" + orgCode + ", localGlnUuid=" + localGlnUuid
				+ ", balance=" + balance + "]";
	}

}
