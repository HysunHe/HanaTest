/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with Hysun He. This information 
 * shall not be distributed or copied without written permission from 
 * Hysun He.
 *
 ***************************************************************************/

package com.oracle.oda.ext.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.oracle.oda.ext.domains.Blacklist;

/***************************************************************************
 * <PRE>
 *  Project Name    : bot-gateway
 * 
 *  Package Name    : com.oracle.oda.ext.dao
 * 
 *  File Name       : BlacklistMapper.java
 * 
 *  Creation Date   : 2019年2月20日
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
@Mapper
public interface BlacklistMapper {
	void save(Blacklist o);

	List<Blacklist> listBlacklist(@Param("offset") int offset, @Param("limit") int limit);

	void unblock(@Param("user_id") String userId);

	Blacklist get(@Param("user_id") String userId);

	List<Blacklist> queryByTime(@Param("from") Timestamp from, @Param("to") Timestamp to);
}
