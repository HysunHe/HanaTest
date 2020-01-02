/***************************************************************************
 *
 * PoC code for evaluating purpose only. No guarantee of quality.
 *
 ***************************************************************************/

package com.oracle.oda.ext.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.oracle.oda.ext.domains.WebViewParams;

/***************************************************************************
 * <PRE>
 *  Project Name    : ssp
 * 
 *  Package Name    : com.oracle.oda.ext.dao
 * 
 *  File Name       : WebViewParamsMapper.java
 * 
 *  Creation Date   : 2020年1月2日
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
@Mapper
public interface WebViewParamsMapper {
	void insert(WebViewParams o);

	WebViewParams get(@Param("id") String id);
}
