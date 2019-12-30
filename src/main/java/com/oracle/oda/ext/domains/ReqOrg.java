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
 *  File Name       : ReqOrg.java
 * 
 *  Creation Date   : 2019年12月30日
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
public class ReqOrg {
	private String orgCode;
	private String authSecret;

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
	 * @return the authSecret
	 */
	public String getAuthSecret() {
		return authSecret;
	}

	/**
	 * @param authSecret
	 *            the authSecret to set
	 */
	public void setAuthSecret(String authSecret) {
		this.authSecret = authSecret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReqOrg [orgCode=" + orgCode + ", authSecret=" + authSecret
				+ "]";
	}

}
