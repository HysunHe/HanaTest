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
	private String orgCode;
	private String localGlnUuid;

	public LocalGlnUser() {
	}

	public LocalGlnUser(String orgCode, String localGlnUuid) {
		this.orgCode = orgCode;
		this.localGlnUuid = localGlnUuid;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LocalGlnUser [orgCode=" + orgCode + ", localGlnUuid="
				+ localGlnUuid + "]";
	}
}
