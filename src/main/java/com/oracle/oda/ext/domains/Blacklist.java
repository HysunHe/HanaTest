/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with Hysun He. This information 
 * shall not be distributed or copied without written permission from 
 * Hysun He.
 *
 ***************************************************************************/

package com.oracle.oda.ext.domains;

/***************************************************************************
 * <PRE>
 *  Project Name    : bot-gateway
 * 
 *  Package Name    : com.oracle.oda.ext.domains
 * 
 *  File Name       : Blacklist.java
 * 
 *  Creation Date   : 2019年2月18日
 * 
 *  Author          : hysun
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public class Blacklist extends BaseObject {
	private String user_id;
	private String user_agent;

	public Blacklist() {
		super();
	}

	/**
	 * @return the user_id
	 */
	public String getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the user_agent
	 */
	public String getUser_agent() {
		return user_agent;
	}

	/**
	 * @param user_agent the user_agent to set
	 */
	public void setUser_agent(String user_agent) {
		this.user_agent = user_agent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Blacklist [user_id=" + user_id + ", user_agent=" + user_agent + "]";
	}
}
