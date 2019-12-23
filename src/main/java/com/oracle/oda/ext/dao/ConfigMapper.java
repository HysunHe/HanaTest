/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with Hysun He. This information 
 * shall not be distributed or copied without written permission from 
 * Hysun He.
 *
 ***************************************************************************/

package com.oracle.oda.ext.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.oracle.oda.ext.domains.Config;

/***************************************************************************
 * <PRE>
 *  Project Name    : bot-gateway
 * 
 *  Package Name    : com.oracle.oda.ext.dao
 * 
 *  File Name       : ConfigMapper.java
 * 
 *  Creation Date   : 2019年2月19日
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
public interface ConfigMapper {
	List<Config> listConfigByKind(String kind);

	void insertAll(List<Config> list);

	Config getProperty(@Param("kind") String kind, @Param("key") String key);

	void deleteKind(@Param("kind") String kind);
}
